package org.fleetopgroup.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.BatteryConstant;
import org.fleetopgroup.constant.ClothInvoiceStockType;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.PurchaseOrderState;
import org.fleetopgroup.constant.PurchaseOrderType;
import org.fleetopgroup.constant.PurchaseOrdersConfigurationConstant;
import org.fleetopgroup.constant.SequenceTypeContant;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.DriverReminderBL;
import org.fleetopgroup.persistence.bl.InventoryBL;
import org.fleetopgroup.persistence.bl.MasterPartsBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.PendingVendorPaymentBL;
import org.fleetopgroup.persistence.bl.PurchaseOrdersBL;
import org.fleetopgroup.persistence.bl.PurchaseOrdersToPartsHistoryBL;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.ServiceReminderBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.dao.BatteryAmountRepository;
import org.fleetopgroup.persistence.dao.BatteryInvoiceRepository;
import org.fleetopgroup.persistence.dao.ClothInventoryDetailsRepository;
import org.fleetopgroup.persistence.dao.ClothInventoryRepository;
import org.fleetopgroup.persistence.dao.ClothInventoryStockTypeDetailsRepository;
import org.fleetopgroup.persistence.dao.PurchaseOrdersToPartsHistoryRepository;
import org.fleetopgroup.persistence.dto.BankPaymentDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryAllDto;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.MasterPartsDto;
import org.fleetopgroup.persistence.dto.PartLocationPermissionDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersToDebitNoteDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersToPartsDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.model.Battery;
import org.fleetopgroup.persistence.model.BatteryAmount;
import org.fleetopgroup.persistence.model.BatteryInvoice;
import org.fleetopgroup.persistence.model.BatteryInvoiceSequenceCounter;
import org.fleetopgroup.persistence.model.ClothInventoryDetails;
import org.fleetopgroup.persistence.model.ClothInventoryStockTypeDetails;
import org.fleetopgroup.persistence.model.ClothInvoice;
import org.fleetopgroup.persistence.model.ClothInvoiceSequenceCounter;
import org.fleetopgroup.persistence.model.Inventory;
import org.fleetopgroup.persistence.model.InventoryAll;
import org.fleetopgroup.persistence.model.InventoryLocation;
import org.fleetopgroup.persistence.model.InventorySequenceCounter;
import org.fleetopgroup.persistence.model.InventoryTyre;
import org.fleetopgroup.persistence.model.InventoryTyreAmount;
import org.fleetopgroup.persistence.model.InventoryTyreInvoice;
import org.fleetopgroup.persistence.model.PartInvoice;
import org.fleetopgroup.persistence.model.PartInvoiceSequenceCounter;
import org.fleetopgroup.persistence.model.PartLocations;
import org.fleetopgroup.persistence.model.PartMeasurementUnit;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.PurchaseOrderToBattery;
import org.fleetopgroup.persistence.model.PurchaseOrders;
import org.fleetopgroup.persistence.model.PurchaseOrdersDocument;
import org.fleetopgroup.persistence.model.PurchaseOrdersSequeceCounter;
import org.fleetopgroup.persistence.model.PurchaseOrdersToDebitNote;
import org.fleetopgroup.persistence.model.PurchaseOrdersToParts;
import org.fleetopgroup.persistence.model.PurchaseOrdersToPartsHistory;
import org.fleetopgroup.persistence.model.PurchaseOrdersToTyre;
import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.model.WorkOrders;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryInvoiceSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IClothInvoiceSequenceService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventorySequenceService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.IPartInvoiceSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IPartInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IPartMeasurementUnitService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersToPartsService;
import org.fleetopgroup.persistence.serviceImpl.IPurchasePartForVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionReceivelService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.persistence.serviceImpl.IVendorTypeService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.registration.listener.SendHTMLEmail;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

@Controller
public class PurchaseOrdersController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IPartLocationsService PartLocationsService;
	@Autowired IInventorySequenceService	inventorySequenceService;
	PartLocationsBL PartBL = new PartLocationsBL();

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment env;
	SendHTMLEmail htmlemail = new SendHTMLEmail();

	@Autowired
	private IPurchaseOrdersService PurchaseOrdersService;

	@Autowired
	private IPartLocationsService iPartLocationsService;
	@Autowired
	private IPurchaseOrdersToPartsService purchaseOrdersToPartsService;
	

	@Autowired PurchaseOrdersToPartsHistoryRepository purchaseOrdersToPartsHistoryRepository;
	
	@Autowired private IBatteryService						batteryService;
	@Autowired private IBatteryInvoiceSequenceService		batteryInvoiceSequenceService;
	@Autowired private BatteryInvoiceRepository				batteryInvoiceRepository;
	@Autowired private BatteryAmountRepository				batteryAmountRepository;
	@Autowired private IClothInvoiceSequenceService			clothInvoiceSequenceService;
	@Autowired private ClothInventoryRepository					clothInventoryRepository;
	@Autowired private ClothInventoryDetailsRepository				clothInventoryDetailsRepository;
	@Autowired private ClothInventoryStockTypeDetailsRepository	clothInventoryStockTypeDetailsRepository;
	@Autowired private ICompanyConfigurationService 		companyConfigurationService;
	@Autowired private IPurchasePartForVehicleService 		purchasePartForVehicleService;
	@Autowired private IInventoryService InventoryService;
	@Autowired private IPendingVendorPaymentService			pendingVendorPaymentService;
	@Autowired private	IPartMeasurementUnitService 		partMeasurementUnitService;
	@Autowired private IBankPaymentService					bankPaymentService;
	@Autowired private ICashPaymentService					cashPaymentService;
	MasterPartsBL MPBL = new MasterPartsBL();
	PartLocationsBL	PLBL = new PartLocationsBL();

	@Autowired
	private IUserProfileService userProfileService;

	@Autowired
	private IVendorService vendorService;
	
	@Autowired
	private IVendorTypeService vendorTypeService;

	@Autowired
	private IPartLocationPermissionService partLocationPermissionService;

	VendorBL VenBL = new VendorBL();

	InventoryBL InvenBL = new InventoryBL();

	@Autowired
	private IWorkOrdersService WorkOrdersService;

	@Autowired
	private IMasterPartsService MasterPartsService;
	MasterPartsBL MasBL = new MasterPartsBL();

	@Autowired
	private IInventoryTyreService iventoryTyreService;

	@Autowired
	private IPurchaseOrdersSequenceService iPurchaseOrdersSequenceService;
	
	@Autowired
	IPartInvoiceSequenceService			partInvoiceSequenceService;
	
	@Autowired 
	IPartInvoiceService					partInvoiceService;
	
	@Autowired IRequisitionService requisitionService;
	
	@Autowired IRequisitionReceivelService receiveService;

	PurchaseOrdersBL POBL = new PurchaseOrdersBL();
	RenewalReminderBL RRBL = new RenewalReminderBL();
	DriverReminderBL DriverRem = new DriverReminderBL();

	VehicleBL VBL = new VehicleBL();
	ServiceReminderBL SRBL = new ServiceReminderBL();
	DriverBL DBL = new DriverBL();

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat mailDate = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	DecimalFormat df2 = new DecimalFormat("#.##");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	// Search PurchaseOrders
	@RequestMapping(value = "/searchPurchaseOrder", method = RequestMethod.POST)
	public ModelAndView searchPurchaseOrders(@RequestParam("Search") final String Purchaseorder_id,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("PurchaseOrder", POBL.getListofPurchaseOrders(
					PurchaseOrdersService.SearchPurchaseOrders(Purchaseorder_id, userDetails)));
			model.put("PurchaseOrderCount", PurchaseOrdersService.countPurchaseOrder(userDetails.getCompany_id()));
			model.put("isSearched", true);
		} catch (Exception e) {
			LOGGER.error("PurchaseOrder Page:", e);
		}
		return new ModelAndView("PONewRequistion", model);
	}

	// Search PurchaseOrders
	@RequestMapping(value = "/searchPurchaseOrderShow", method = RequestMethod.POST)
	public ModelAndView searchPurchaseOrdersShow(@RequestParam("Search") final Long Purchaseorder_Number,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails 		userDetails 				= null;
		PurchaseOrders			poDetails					= null;
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			poDetails	=	PurchaseOrdersService.getPurchaseOrderDeatils(Purchaseorder_Number, userDetails.getCompany_id());
			
			if(poDetails == null) {
				model.put("NotFound", true);
				return new ModelAndView("redirect:/newPurchaseOrders/1.in", model);
			}else {
				return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID="+poDetails.getPurchaseorder_id(), model);
			}

		} catch (NullPointerException e) {
			System.err.println("xception  : "+e);
			model.put("NotFound", true);
			return new ModelAndView("redirect:/newPurchaseOrders/1.in", model);
		} catch (Exception e) {
			System.err.println("xceptiondsd  : "+e);
			model.put("NotFound", true);
			return new ModelAndView("redirect:/newPurchaseOrders/1.in", model);
		} finally {
			userDetails = null;
		}

	}

	/*
	 * New PurchaseOrders This Controller RequestMapping Getting the value Of
	 * Requisition the Values List In PO and Also Count the Total Purchase Orders
	 * Values And the Total Requisition Values Also
	 */
	/* show main page of Trip */
	@RequestMapping(value = "/newPurchaseOrders/{pageNumber}", method = RequestMethod.GET)
	public String newPurchaseOrders(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails userDetails = null;
		HashMap<String, Object> 				poConfiguration       	 		= null;
		try {
			
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			poConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			
			Page<PurchaseOrders> page = PurchaseOrdersService.getDeployment_Page_PurchaseOrders(PurchaseOrderType.PURCHASEORDER_STATUS_REQUISITION,
					pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("StatuesCount", page.getTotalElements());
			model.addAttribute("configuration" , poConfiguration);
			List<PurchaseOrdersDto> pageList = POBL.getListofPurchaseOrders(PurchaseOrdersService
					.listOpenPurchaseOrders(PurchaseOrderType.PURCHASEORDER_STATUS_REQUISITION, pageNumber, userDetails.getCompany_id()));

			Object[] count = PurchaseOrdersService.countTotalPurchaseOrder(userDetails);
			model.addAttribute("REQUISITION", (Long) count[1]);
			model.addAttribute("ORDERED", (Long) count[2]);
			model.addAttribute("RECEIVED", (Long) count[3]);
			model.addAttribute("COMPLETED", (Long) count[4]);
			model.addAttribute("REQUISITIONAPPROVED", (Long) count[5]);
			model.addAttribute("PurchaseOrder", pageList);

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("PurchaseOrdersDto Page:", e);
			e.printStackTrace();
		}
		return "PONewRequistion";
	}

	/*
	 * PurchaseOrders_ORDERED This Controller RequestMapping Getting the value Of
	 * Ordered the Values List In PO and Also Count the Total Purchase Orders Values
	 * And the Total Ordered Values Also
	 */
	@RequestMapping(value = "/PurchaseOrders_ORDERED/{pageNumber}", method = RequestMethod.GET)
	public String PurchaseOrders_ORDERED(@PathVariable Integer pageNumber, Model model) throws Exception {

		CustomUserDetails userDetails = null;
		HashMap<String, Object> 				poConfiguration       	 		= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			poConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			Page<PurchaseOrders> page = PurchaseOrdersService.getDeployment_Page_PurchaseOrders(PurchaseOrderType.PURCHASEORDER_STATUS_ORDERED, pageNumber,
					userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("StatuesCount", page.getTotalElements());
			List<PurchaseOrdersDto> pageList = POBL.getListofPurchaseOrders(
					PurchaseOrdersService.listOpenPurchaseOrders(PurchaseOrderType.PURCHASEORDER_STATUS_ORDERED, pageNumber, userDetails.getCompany_id()));

			Object[] count = PurchaseOrdersService.countTotalPurchaseOrder(userDetails);			
			model.addAttribute("REQUISITION", (Long) count[1]);
			model.addAttribute("ORDERED", (Long) count[2]);
			model.addAttribute("RECEIVED", (Long) count[3]);
			model.addAttribute("COMPLETED", (Long) count[4]);
			model.addAttribute("REQUISITIONAPPROVED", (Long) count[5]);
			
			model.addAttribute("PurchaseOrder", pageList);
			model.addAttribute("configuration" , poConfiguration);
			
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("PurchaseOrdersDto Page:", e);
			e.printStackTrace();
		}
		return "PONewOrdered";
	}

	/*
	 * PurchaseOrders_RECEIVED This Controller RequestMapping Getting the value Of
	 * Received the Values List In PO and Also Count the Total Purchase Orders
	 * Values And the Total Received Values Also
	 */
	@RequestMapping(value = "/PurchaseOrders_RECEIVED/{pageNumber}", method = RequestMethod.GET)
	public String PurchaseOrders_RECEIVED(@PathVariable Integer pageNumber, Model model) throws Exception {

		CustomUserDetails userDetails = null;
		HashMap<String, Object> poConfiguration = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			poConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			
			Page<PurchaseOrders> page = PurchaseOrdersService.getDeployment_Page_PurchaseOrders(PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED, pageNumber,
					userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("StatuesCount", page.getTotalElements());
			List<PurchaseOrdersDto> pageList = POBL.getListofPurchaseOrders(
					PurchaseOrdersService.listOpenPurchaseOrders(PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED, pageNumber, userDetails.getCompany_id()));

			Object[] count = PurchaseOrdersService.countTotalPurchaseOrder(userDetails);
			model.addAttribute("REQUISITION", (Long) count[1]);
			model.addAttribute("ORDERED", (Long) count[2]);
			model.addAttribute("RECEIVED", (Long) count[3]);
			model.addAttribute("COMPLETED", (Long) count[4]);
			model.addAttribute("REQUISITIONAPPROVED", (Long) count[5]);
		
			model.addAttribute("PurchaseOrder", pageList);
			model.addAttribute("configuration" , poConfiguration);
			
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("PurchaseOrdersDto Page:", e);
			e.printStackTrace();
		}
		return "PONewReceived";
	}

	/*
	 * PurchaseOrders_COMPLETED This Controller RequestMapping Getting the value Of
	 * Completed the Values List In PO and Also Count the Total Purchase Orders
	 * Values And the Total Completed Values Also
	 */
	@RequestMapping(value = "/PurchaseOrders_COMPLETED/{pageNumber}", method = RequestMethod.GET)
	public String PurchaseOrders_COMPLETED(@PathVariable Integer pageNumber, Model model) throws Exception {

		CustomUserDetails userDetails = null;
		HashMap<String, Object> poConfiguration = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			poConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			Page<PurchaseOrders> page = PurchaseOrdersService.getDeployment_Page_PurchaseOrders(PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED, pageNumber,
					userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("StatuesCount", page.getTotalElements());
			List<PurchaseOrdersDto> pageList = POBL.getListofPurchaseOrders(
					PurchaseOrdersService.listOpenPurchaseOrders(PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED, pageNumber, userDetails.getCompany_id()));

			Object[] count = PurchaseOrdersService.countTotalPurchaseOrder(userDetails);			
			model.addAttribute("REQUISITION", (Long) count[1]);
			model.addAttribute("ORDERED", (Long) count[2]);
			model.addAttribute("RECEIVED", (Long) count[3]);
			model.addAttribute("COMPLETED", (Long) count[4]);
			model.addAttribute("REQUISITIONAPPROVED", (Long) count[5]);
			
			model.addAttribute("PurchaseOrder", pageList);
			model.addAttribute("configuration" , poConfiguration);
			
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("PurchaseOrdersDto Page:", e);
			e.printStackTrace();
		}
		return "PONewCompleted";
	}

	/*
	 * addPurchaseOrderThis Controller RequestMapping Getting the value Of Create
	 * New PO
	 */
	@RequestMapping(value = "/addPurchaseOrder", method = RequestMethod.GET)
	public ModelAndView addPurchaseOrders(@ModelAttribute("command") PurchaseOrdersDto PurchaseOrders, final HttpServletRequest request) throws Exception {
		Map<String, Object> 			model 					= null;
		HashMap<String, Object> 		configuration	        = null;
		HashMap<String, Object> 		poConfiguration	        = null;
		HashMap<String, Object> 		companyConfiguration 	= null;
		CustomUserDetails 				userDetails 	        = null;
		try {
			model			= new HashMap<String, Object>();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_CONFIGURATION_CONFIG);
			poConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			
			companyConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			model.put("configuration",configuration);
			model.put("poConfiguration",poConfiguration);
			model.put("companyConfiguration",companyConfiguration);
			model.put("company",userDetails.getId());
			model.put("PurchaseOrders",PurchaseOrders);
			model.put("PartLocations",PLBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("addPurchaseOrders", model);
	}

	/*
	 * addPurchaseOrderThis Controller RequestMapping Getting the value Of Create
	 * New PO
	 */
	@RequestMapping(value = "/editPurchaseOrder", method = RequestMethod.GET)
	public ModelAndView addPurchaseOrders(@RequestParam("ID") final Long Purchaseorder_id,
			final HttpServletRequest request) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		HashMap<String, Object>  purchaseConfig	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
		HashMap<String, Object>  vendorConfig	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_CONFIGURATION_CONFIG);
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>  threadHM	=  PurchaseOrdersService.getPurchaseOrderDetailsHM(Purchaseorder_id, userDetails);
		
		@SuppressWarnings("unchecked")
		PurchaseOrdersDto Purchase = POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
		
		
		model.put("purchaseConfig", purchaseConfig);
		model.put("vendorConfig", vendorConfig);
		model.put("PO", Purchase);
		model.put("configuration", configuration);
		model.put("PartLocations",PLBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
		return new ModelAndView("EditPurchaseOrders", model);
	}

	/*
	 * UpdatePurchaseOrder Controller RequestMapping Getting the value Of Save New
	 * PO
	 */
	@RequestMapping(value = "/UpdatePurchaseOrder", method = RequestMethod.POST)
	public ModelAndView UpdateEditPurchaseOrders(@ModelAttribute("command") PurchaseOrdersDto PurchaseOrders,@ModelAttribute BankPaymentDto bankPaymentDto,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		Long Purchaseorders_id;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			PurchaseOrders oldPurchaseOrders = PurchaseOrdersService.getPurchaseOrders(PurchaseOrders.getPurchaseorder_id());
			if(oldPurchaseOrders != null) {
			PurchaseOrders PurchaseOrd = POBL.UpdatePurchaseOrders(PurchaseOrders);
			PurchaseOrdersService.UpdateEdit_PurchaseOrders_details(PurchaseOrd.getPurchaseorder_created_on(),
					PurchaseOrd.getPurchaseorder_requied_on(),
					PurchaseOrd.getPurchaseorder_termsId(), PurchaseOrd.getPurchaseorder_shipviaId(),
					PurchaseOrd.getPurchaseorder_quotenumber(), PurchaseOrd.getPurchaseorder_indentno(),
					PurchaseOrd.getPurchaseorder_workordernumber(), PurchaseOrd.getPurchaseorder_notes(),
					PurchaseOrd.getLastModifiedById(), PurchaseOrd.getLastupdated(), PurchaseOrd.getPurchaseorder_id(),PurchaseOrd.getPurchaseorder_vendor_paymodeId(),
					PurchaseOrd.getCompanyId(),PurchaseOrd.getSubCompanyId(),PurchaseOrd.getPurchaseorder_vendor_id(),PurchaseOrd.getPurchaseorder_shiplocation_id());

			model.put("updatePurchaseOrder", true);
			
			if(bankPaymentDto.isAllowBankPaymentDetails()) {
				ValueObject bankPaymentValueObject=new ValueObject();
				bankPaymentValueObject.put("oldPaymentTypeId", oldPurchaseOrders.getPurchaseorder_termsId());
				bankPaymentValueObject.put("currentPaymentTypeId",PurchaseOrd.getPurchaseorder_termsId());
				bankPaymentValueObject.put("userId", userDetails.getId());
				bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
				bankPaymentValueObject.put("moduleId",PurchaseOrders.getPurchaseorder_id());
				bankPaymentValueObject.put("moduleNo", PurchaseOrders.getPurchaseorder_Number());
				bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.PURCHASE_ORDER);
				bankPaymentValueObject.put("amount",oldPurchaseOrders.getPurchaseorder_totalcost());
				
				Vendor	vendor	= vendorService.getVendor(PurchaseOrders.getPurchaseorder_vendor_id());
				bankPaymentValueObject.put("remark","Purchase Order Edit On PO-"+PurchaseOrders.getPurchaseorder_Number()+" Vendor : "+vendor.getVendorName()+"  ");
				
				bankPaymentService.updatePaymentDetails(bankPaymentValueObject, bankPaymentDto);
				}
			}
			Purchaseorders_id = PurchaseOrders.getPurchaseorder_id();

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in", model);
		}

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + Purchaseorders_id + "", model);
	}

	/*
	 * SavePurchaseOrder Controller RequestMapping Getting the value Of Save New PO
	 */
	@RequestMapping(value = "/SavePurchaseOrder", method = RequestMethod.POST)
	public ModelAndView savePurchaseOrders(@ModelAttribute("command") PurchaseOrdersDto PurchaseOrders,@ModelAttribute BankPaymentDto bankPaymentDto,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> 			model 				= new HashMap<String, Object>();
		Long 							Purchaseorders_id;
		PurchaseOrdersSequeceCounter 	sequeceCounter 		= null;
		PurchaseOrders 					PurchaseOrd			= null;
		HashMap<String, Object> 		configuration		= null;
		try {
			// get the PurchaseOrders Dto to save PurchaseOrders
			PurchaseOrd = SavePurchaseOrders(PurchaseOrders);
			
			configuration	= companyConfigurationService.getCompanyConfiguration(PurchaseOrd.getCompanyId(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			
			
			sequeceCounter = iPurchaseOrdersSequenceService.findNextPurchaseOrderNumber(PurchaseOrd.getCompanyId());
			if (sequeceCounter == null) {
				model.put("sequenceNotDefined", true);
				return new ModelAndView("redirect:/newPurchaseOrders/1.in", model);
			}
			PurchaseOrd.setPurchaseorder_Number(sequeceCounter.getNextVal());
			
			if((boolean) configuration.getOrDefault("poApprovalRequired", false))
				PurchaseOrd.setApprovalStatus(PurchaseOrderType.PURCHASE_STATUS_NOT_APPROVED);
			else
				PurchaseOrd.setApprovalStatus(PurchaseOrderType.PURCHASE_STATUS_APPROVED);

			PurchaseOrdersService.addPurchaseOrders(PurchaseOrd);
			

			/*
			 * iPurchaseOrdersSequenceService.updateNextPurchaseOrderNumber(sequeceCounter.
			 * getNextVal() + 1, sequeceCounter.getSequence_Id());
			 */

			model.put("savePurchaseOrder", true);
			Purchaseorders_id = PurchaseOrd.getPurchaseorder_id();
			if (bankPaymentDto.isAllowBankPaymentDetails()) {
				if(PurchaseOrd.getPurchaseorder_termsId() == PaymentTypeConstant.PAYMENT_TYPE_CASH || PaymentTypeConstant.getPaymentTypeIdList().contains(PurchaseOrd.getPurchaseorder_termsId()) ) {
						ValueObject bankPaymentValueObject=new ValueObject();
						bankPaymentValueObject.put("currentPaymentTypeId",PurchaseOrd.getPurchaseorder_termsId());
						bankPaymentValueObject.put("userId", PurchaseOrd.getCreatedById());
						bankPaymentValueObject.put("companyId", PurchaseOrd.getCompanyId());
						bankPaymentValueObject.put("moduleId",Purchaseorders_id);
						bankPaymentValueObject.put("moduleNo", PurchaseOrd.getPurchaseorder_Number());
						bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.PURCHASE_ORDER);
						bankPaymentValueObject.put("amount",0);
						bankPaymentValueObject.put("remark", "Purchase Order Created PO-"+PurchaseOrd.getPurchaseorder_Number());
						//bankPaymentValueObject.put("paidDate", PurchaseOrd.getPurchaseorder_invoice_date());
						if (PurchaseOrd.getPurchaseorder_termsId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)
							cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
						else
							bankPaymentService.addBankPaymentDetailsAndSendToIv(bankPaymentDto, bankPaymentValueObject);
				}
				}
				
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in", model);
		}

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + Purchaseorders_id + "", model);
	}

	/*
	 * SavePurchaseOrder Controller RequestMapping Getting the value Of Show
	 * PurchaseOrderParts Details
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/PurchaseOrders_Parts", method = RequestMethod.GET)
	public ModelAndView addPurchaseOrdersParts(@RequestParam("ID") final Long Purchaseorder_id, @ModelAttribute("command") PurchaseOrdersToPartsDto poDto ,  final HttpServletRequest request) throws Exception {
		Map<String, Object> 					model							= null;
		CustomUserDetails						userDetails 					= null;
		HashMap<String, Object>  				threadHM						= null;
		PurchaseOrdersDto 						Purchase		 				= null;
		HashMap<String, Object> 				configuration	       	 		= null;
		HashMap<String, Object> 				poConfiguration	       	 		= null;
		HashMap<String, Object> 				tyreConfiguration	       	 	= null;
		boolean 								totalGstCostConfig				= false;
		List<PurchaseOrdersToDebitNoteDto> 		purchaseToDebitNote 			= null;
		ValueObject 							object						 	= null;
		
		try {
			model 			= new HashMap<String, Object>();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_CONFIGURATION_CONFIG);
			tyreConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TYRE_INVENTORY_CONFIG);
			poConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			threadHM		= PurchaseOrdersService.getPurchaseOrderDetailsHM(Purchaseorder_id, userDetails);
			Purchase 		= POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
			String uniqueID 		= UUID.randomUUID().toString();
			totalGstCostConfig = (boolean) poConfiguration.getOrDefault("totalGstCost", false);
			if (Purchase != null) {
				model.put("PurchaseOrder", Purchase);
				model.put("CreatedBy", userDetails.getFirstName() + "_" + userDetails.getLastName());
				model.put("CreatedById", userDetails.getId());
				model.put("configuration",configuration);
				model.put("poConfiguration",poConfiguration);
				request.getSession().setAttribute(uniqueID, uniqueID);
				model.put("companyId", userDetails.getCompany_id());
				model.put("accessToken", uniqueID);
				
				if(poDto != null && poDto.isFromTransfer()) {
				ValueObject returnObject=receiveService.getTransactionName(poDto.getSubRequisitionId(), userDetails.getCompany_id());
				model.put("fromTransfer", true);
				model.put("rTransactionId", returnObject.getInt("transactionId",0));
				model.put("rModelId", returnObject.getInt("modelId",0));
				model.put("rSizeId",returnObject.getInt("sizeId",0));
				model.put("rTransactionName",returnObject.getString("transactionName"));
				model.put("rModelName", returnObject.getString("modelName"));
				model.put("rSizeName", returnObject.getString("sizeName"));	
				model.put("rQuantity", poDto.getApprovalQuantity());	
				}
				switch (Purchase.getPurchaseorder_typeId()) {

				case PurchaseOrderType.PURCHASEORDER_TYPE_PART_PO:
					purchaseToDebitNote = PurchaseOrdersService.getPurchaseOrdersTasksToDebitNote(Purchaseorder_id, userDetails.getCompany_id());
					object	 = PurchaseOrdersService.getTotalAmountOfPurchaseOrders(Purchase);
					model.put("partFound", object.getBoolean("partFound",false));
					if(totalGstCostConfig) {
						model.put("totalEachCost", object.getDouble("totalEachCost"));
						model.put("TotalDiscountCost", object.getDouble("TotalDiscountCost"));
						model.put("finalCost",object.getDouble("finalCost"));
						model.put("balanceCost",object.getDouble("balanceCost"));

					}
					
					model.put("TotalReceivedPartCost",object.getDouble("TotalReceivedPartCost"));
					
					model.put("PurchaseOrderPart", object.get("PurchaseOrderPart"));
					model.put("TotalOrdered", object.getDouble("totalOrdered"));
					model.put("TotalRecevied", object.getDouble("totalRecevied"));
					
					model.put("allowToReceivePartial", (boolean) poConfiguration.getOrDefault("allowToReceivePartial", false));
					
					
					switch (Purchase.getPurchaseorder_statusId()) {
						case PurchaseOrderType.PURCHASEORDER_STATUS_ORDERED:
							return new ModelAndView("addPurchaseOrdersPartsORDERED", model);
						
						case PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED:
							return new ModelAndView("addPurchaseOrdersPartsRECEIVED", model);
						
						case PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED:
							model.put("PurchaseOrderDebitNote", purchaseToDebitNote);
							model.put("balanceCost", df2.format((((object.getDouble("totalEachCost") + Purchase.getPurchaseorder_totaltax_cost() + Purchase.getPurchaseorder_freight()) - (object.getDouble("TotalDiscountCost")+Purchase.getPurchaseorder_advancecost())))-Purchase.getPurchaseorder_total_debitnote_cost()));
							return new ModelAndView("addPurchaseOrdersPartsCOMPLETED", model);
						
						default:
							return new ModelAndView("addPurchaseOrdersParts", model);
					}

				case PurchaseOrderType.PURCHASEORDER_TYPE_TYRE_PO:
					purchaseToDebitNote = PurchaseOrdersService.getPurchaseOrdersTasksToDebitNote(Purchaseorder_id, userDetails.getCompany_id());
					
					object	 = PurchaseOrdersService.getTotalAmountOfPurchaseOrders(Purchase);
					model.put("partFound", object.getBoolean("partFound",false));
					if(totalGstCostConfig) {
						model.put("totalEachCost", object.getDouble("totalEachCost"));
						model.put("TotalDiscountCost", object.getDouble("TotalDiscountCost"));
						model.put("finalCost",object.getDouble("finalCost"));
						model.put("balanceCost",object.getDouble("balanceCost"));
					}
					
					model.put("TotalReceivedPartCost",object.getDouble("TotalReceivedPartCost"));
					
					model.put("PurchaseOrderPart", object.get("PurchaseOrderPart"));
					model.put("TotalOrdered", object.getDouble("totalOrdered"));
					model.put("TotalRecevied", object.getDouble("totalRecevied"));
					model.put("tyreConfiguration",tyreConfiguration);

					switch (Purchase.getPurchaseorder_statusId()) {
						case PurchaseOrderType.PURCHASEORDER_STATUS_ORDERED:
							return new ModelAndView("addPurchaseOrdersTyreORDERED", model);
						
						case PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED:
							return new ModelAndView("addPurchaseOrdersTyreRECEIVED", model);
						
						case PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED:
							model.put("PurchaseOrderDebitNote", purchaseToDebitNote);
							model.put("balanceCost", df2.format((((object.getDouble("totalEachCost") + Purchase.getPurchaseorder_totaltax_cost() + Purchase.getPurchaseorder_freight()) - (object.getDouble("TotalDiscountCost")+Purchase.getPurchaseorder_advancecost())))-Purchase.getPurchaseorder_total_debitnote_cost()));
							return new ModelAndView("addPurchaseOrdersTyreCOMPLETED", model);
	
						default:
							return new ModelAndView("addPurchaseOrdersTyre", model);

					}
					
				case PurchaseOrderType.PURCHASEORDER_TYPE_BATTERY_PO:
					purchaseToDebitNote = PurchaseOrdersService.getPurchaseOrdersTasksToDebitNote(Purchaseorder_id, userDetails.getCompany_id());
					
					
					object	 = PurchaseOrdersService.getTotalAmountOfPurchaseOrders(Purchase);
					model.put("partFound", object.getBoolean("partFound",false));
					if(totalGstCostConfig) {
						model.put("totalEachCost", object.getDouble("totalEachCost"));
						model.put("TotalDiscountCost", object.getDouble("TotalDiscountCost"));
						model.put("finalCost",object.getDouble("finalCost"));
						model.put("balanceCost",object.getDouble("balanceCost"));
					}
					
					model.put("TotalReceivedPartCost",object.getDouble("TotalReceivedPartCost"));
					
					model.put("PurchaseOrderPart", object.get("PurchaseOrderPart"));
					model.put("TotalOrdered", object.getDouble("totalOrdered"));
					model.put("TotalRecevied", object.getDouble("totalRecevied"));

					switch (Purchase.getPurchaseorder_statusId()) {
						case PurchaseOrderType.PURCHASEORDER_STATUS_ORDERED:
							return new ModelAndView("addPurchaseOrdersBatteryORDERED", model);
						
						case PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED:
							return new ModelAndView("addPurchaseOrdersBatteryRECEIVED", model);
						
						case PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED:
							model.put("PurchaseOrderDebitNote", purchaseToDebitNote);
							model.put("balanceCost", df2.format((((object.getDouble("totalEachCost") + Purchase.getPurchaseorder_totaltax_cost() + Purchase.getPurchaseorder_freight()) - (object.getDouble("TotalDiscountCost")+Purchase.getPurchaseorder_advancecost())))-Purchase.getPurchaseorder_total_debitnote_cost()));
							return new ModelAndView("addPurchaseOrdersBatteryCOMPLETED", model);
	
						default:
							return new ModelAndView("addPurchaseOrdersBattery", model);

					}
				case PurchaseOrderType.PURCHASEORDER_TYPE_CLOTH_PO:
					purchaseToDebitNote = PurchaseOrdersService.getPurchaseOrdersTasksToDebitNoteForCloth(Purchaseorder_id, userDetails.getCompany_id());
					
					object	 = PurchaseOrdersService.getTotalAmountOfPurchaseOrders(Purchase);
					model.put("partFound", object.getBoolean("partFound",false));
					if(totalGstCostConfig) {
						model.put("totalEachCost", object.getDouble("totalEachCost"));
						model.put("TotalDiscountCost", object.getDouble("TotalDiscountCost"));
						model.put("finalCost",object.getDouble("finalCost"));
						model.put("balanceCost",object.getDouble("balanceCost"));
					}
					
					model.put("TotalReceivedPartCost",object.getDouble("TotalReceivedPartCost"));
					
					model.put("PurchaseOrderPart", object.get("PurchaseOrderPart"));
					model.put("TotalOrdered", object.getDouble("totalOrdered"));
					model.put("TotalRecevied", object.getDouble("totalRecevied"));
					// Checking Purchase OrderTo part in Status Type which one value
					switch (Purchase.getPurchaseorder_statusId()) {
						case PurchaseOrderType.PURCHASEORDER_STATUS_ORDERED:
							return new ModelAndView("addPurchaseOrdersClothsORDERED", model);
						
						case PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED:
							return new ModelAndView("addPurchaseOrdersClothRECEIVED", model);
						
						case PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED:
							model.put("PurchaseOrderDebitNote", purchaseToDebitNote);
							model.put("balanceCost", df2.format((((object.getDouble("totalEachCost") + Purchase.getPurchaseorder_totaltax_cost() + Purchase.getPurchaseorder_freight()) - (object.getDouble("TotalDiscountCost")+Purchase.getPurchaseorder_advancecost())))-Purchase.getPurchaseorder_total_debitnote_cost()));
							return new ModelAndView("addPurchaseOrdersClothCOMPLETED", model);
	
						default:
							return new ModelAndView("addPurchaseOrdersCloth", model);
					}
					
				case PurchaseOrderType.PURCHASEORDER_TYPE_UREA_PO:
					object	 = PurchaseOrdersService.getTotalAmountOfPurchaseOrders(Purchase);
					model.put("partFound", object.getBoolean("partFound",false));
					if(totalGstCostConfig) {
						model.put("totalEachCost", object.getDouble("totalEachCost"));
						model.put("TotalDiscountCost", object.getDouble("TotalDiscountCost"));
						model.put("finalCost",object.getDouble("finalCost"));
						model.put("balanceCost",object.getDouble("balanceCost"));
					}
					
					model.put("TotalReceivedPartCost",object.getDouble("TotalReceivedPartCost"));
					
					model.put("PurchaseOrderPart", object.get("PurchaseOrderPart"));
					model.put("TotalOrdered", object.getDouble("totalOrdered"));
					model.put("TotalRecevied", object.getDouble("totalRecevied"));
					// Checking Purchase OrderTo part in Status Type which one value
					switch (Purchase.getPurchaseorder_statusId()) {
						case PurchaseOrderType.PURCHASEORDER_STATUS_ORDERED:
							return new ModelAndView("orderedPurchaseOrdersUrea", model);
						
						case PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED:
							return new ModelAndView("receivedPurchaseOrderUrea", model);
						
						case PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED:
							model.put("PurchaseOrderDebitNote", purchaseToDebitNote);
							model.put("balanceCost", df2.format((((object.getDouble("totalEachCost") + Purchase.getPurchaseorder_totaltax_cost() + Purchase.getPurchaseorder_freight()) - (object.getDouble("TotalDiscountCost")+Purchase.getPurchaseorder_advancecost())))-Purchase.getPurchaseorder_total_debitnote_cost()));
							return new ModelAndView("completedPurchaseOrderUrea", model);
	
						default:
							return new ModelAndView("addPurchaseOrdersUrea", model);
					}
					
				default:
					return new ModelAndView("addPurchaseOrdersTyre", model);

				}
			}
			return new ModelAndView("addPurchaseOrdersParts", model);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/PrintPurchaseOrder", method = RequestMethod.GET)
	public ModelAndView showUserProfilePrint(@RequestParam("id") final Long PrintId, final HttpServletRequest request) {
		Map<String, Object> 				model 								= null;
		CustomUserDetails					userDetails 						= null;
		HashMap<String, Object>  			threadHM							= null;
		PurchaseOrdersDto 					Purchase 							= null;
		List<PurchaseOrdersToPartsDto> 		purchaseToParts 					= null;
		List<PurchaseOrdersToDebitNoteDto> 	PurchaseOrdersToDebitNoteDtoList 	= null;
		ValueObject							valueObject							= null;
		Double 								TotalOrdered 						= 0.0;
		Double 								TotalRecevied 						= 0.0;
		HashMap<String, Object> 				poConfiguration	        = null;
		
		
		try {
			model 		= new HashMap<String, Object>();
			valueObject = new ValueObject();
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			poConfiguration						= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			threadHM							= PurchaseOrdersService.getPurchaseOrderDetailsHM(PrintId, userDetails);
			Purchase 							= POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
			
			
			
			switch (Purchase.getPurchaseorder_typeId()) {
			case PurchaseOrderType.PURCHASEORDER_TYPE_UREA_PO:
				valueObject.put("purchaseOrderId",Purchase.getPurchaseorder_id());
				valueObject.put("purchaseOrderTypeId", Purchase.getPurchaseorder_typeId());
				
				purchaseToParts 					= purchaseOrdersToPartsService.getPurchaseOrdersPartDetailsByType(valueObject, userDetails);
				PurchaseOrdersToDebitNoteDtoList 	= purchaseOrdersToPartsService.getPurchaseOrdersDebitNoteDetailsByPurchaseType(valueObject,userDetails);
				break;
			case PurchaseOrderType.PURCHASEORDER_TYPE_BATTERY_PO:
				purchaseToParts 					= PurchaseOrdersService.getPurchaseOrdersToPartsforBattery(Purchase.getPurchaseorder_id(), userDetails.getCompany_id());
				PurchaseOrdersToDebitNoteDtoList 		= PurchaseOrdersService.getPurchaseOrdersTasksToDebitNote(Purchase.getPurchaseorder_id(), userDetails.getCompany_id());
				break;
				
			default:
				purchaseToParts 					= PurchaseOrdersService.getPurchaseOrdersTasksToParts(Purchase.getPurchaseorder_id(), userDetails.getCompany_id());
				PurchaseOrdersToDebitNoteDtoList 		= PurchaseOrdersService.getPurchaseOrdersTasksToDebitNote(Purchase.getPurchaseorder_id(), userDetails.getCompany_id());
				break;
			}
			
			
			if (purchaseToParts != null) {
				for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToParts) {

					TotalOrdered += purchaseOrdersToParts.getQuantity();
					TotalRecevied += purchaseOrdersToParts.getReceived_quantity();
				}
			}
			
			model.put("TotalOrdered",df2.format(TotalOrdered));
			model.put("TotalRecevied", df2.format(TotalRecevied));
			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));
			model.put("PurchaseOrder", Purchase);
			model.put("PurchaseOrderPart", purchaseToParts);
			model.put("poConfiguration", poConfiguration);
			
			
			
			
			
			if((boolean) poConfiguration.get("showCustomPurchaseOrderPrint"))
			{
				if (Purchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_ORDERED)
				{
					return new ModelAndView((String) poConfiguration.get("customPuchaseOrderprint") , model);
				}
				else if(Purchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED)
				{
					model.put("PurchaseOrderDebitNote", PurchaseOrdersToDebitNoteDtoList);
					return new ModelAndView((String) poConfiguration.get("customPuchaseOrderprint") , model);
				}
				else if (Purchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED) {
					model.put("PurchaseOrderDebitNote", PurchaseOrdersToDebitNoteDtoList);
					return new ModelAndView((String) poConfiguration.get("customPuchaseOrderprint") , model);
				}
				else
				{
					return new ModelAndView((String) poConfiguration.get("customPuchaseOrderprint"), model);
				}
			}
			
			else
			{
				if (Purchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_ORDERED) {
					return new ModelAndView("showPurchasePrintRequisition", model);
				}
				else if (Purchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED) {
					model.put("PurchaseOrderDebitNote", PurchaseOrdersToDebitNoteDtoList);
					return new ModelAndView("showPurchasePrintRecevied", model);

				} else if (Purchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED) {
					model.put("PurchaseOrderDebitNote", PurchaseOrdersToDebitNoteDtoList);
					return new ModelAndView("showPurchasePrintComplete", model);
				}
				
			}
			
			
			/*if (Purchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_ORDERED) {
				return new ModelAndView("showPurchasePrintRequisition", model);
				
			}*/
			
		
			
			/*
			 * if (Purchase.getPurchaseorder_statusId() ==
			 * PurchaseOrderType.PURCHASEORDER_STATUS_ORDERED) { return new
			 * ModelAndView("showPurchasePrintRequisition", model); } else if
			 * (Purchase.getPurchaseorder_statusId() ==
			 * PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED) {
			 * model.put("PurchaseOrderDebitNote", PurchaseOrdersToDebitNoteDtoList); return
			 * new ModelAndView("showPurchasePrintRecevied", model);
			 * 
			 * } else if (Purchase.getPurchaseorder_statusId() ==
			 * PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED) {
			 * model.put("PurchaseOrderDebitNote", PurchaseOrdersToDebitNoteDtoList); return
			 * new ModelAndView("showPurchasePrintComplete", model); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showPurchasePrintRequisition", model);
	}

	// Show PurchaseOrderDebitNote Details
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/PurchaseOrders_PartsDebitNote", method = RequestMethod.GET)
	public ModelAndView DebitNoteTyre(@RequestParam("ID") final Long Purchaseorder_id, final HttpServletRequest request)
			throws Exception {
			
		CustomUserDetails						userDetails				= null;
		Map<String, Object> 					model 					= null;
		HashMap<String, Object>  				threadHM				= null; 
		PurchaseOrdersDto 						Purchase 				= null;
		List<PurchaseOrdersToPartsDto> 			purchaseToParts 		= null;
		List<PurchaseOrdersToDebitNoteDto> 		purchaseToDebitNote 	= null; 
		HashMap<String, Object> 				poConfiguration	        = null;
		
		try {
			model 				= new HashMap<String, Object>();
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			threadHM			= PurchaseOrdersService.getPurchaseOrderDetailsHM(Purchaseorder_id, userDetails);
			Purchase 			= POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
			purchaseToParts 	= PurchaseOrdersService.getPurchaseOrdersTasksToParts(Purchase.getPurchaseorder_id(), userDetails.getCompany_id());
			purchaseToDebitNote = PurchaseOrdersService.getPurchaseOrdersTasksToDebitNote(Purchaseorder_id, userDetails.getCompany_id());
			poConfiguration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);	
			model.put("PurchaseOrder", Purchase);
			model.put("PurchaseOrderDebitNote", purchaseToDebitNote);
			model.put("poConfiguration", poConfiguration);
			Double TotalOrdered = 0.0;
			Double TotalRecevied = 0.0;

				switch (Purchase.getPurchaseorder_typeId()) {
				
				case PurchaseOrderType.PURCHASEORDER_TYPE_PART_PO:
					switch (Purchase.getPurchaseorder_statusId()) {
					case PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED:

						if (purchaseToParts != null) {
							for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToParts) {
								TotalOrdered += purchaseOrdersToParts.getQuantity();
								TotalRecevied += purchaseOrdersToParts.getReceived_quantity();
							}
						}
						model.put("TotalOrdered",df2.format(TotalOrdered));
						model.put("TotalRecevied",df2.format(TotalRecevied));

						return new ModelAndView("addPurchaseOrdersParts_RETURN", model);
					case PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED:

						if (purchaseToParts != null) {
							for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToParts) {

								TotalOrdered += purchaseOrdersToParts.getQuantity();
								TotalRecevied += purchaseOrdersToParts.getReceived_quantity();
							}
						}
						model.put("TotalOrdered", df2.format(TotalOrdered));
						model.put("TotalRecevied", df2.format(TotalRecevied));

						return new ModelAndView("addPurchaseOrdersPartsCOMPLETED", model);
					}
				case PurchaseOrderType.PURCHASEORDER_TYPE_TYRE_PO:
					// Checking Purchase OrderTo part in Status Type which one value
					switch (Purchase.getPurchaseorder_statusId()) {
					case PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED:

						if (purchaseToParts != null) {
							for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToParts) {

								TotalOrdered += purchaseOrdersToParts.getQuantity();
								TotalRecevied += purchaseOrdersToParts.getReceived_quantity();
							}
						}
						model.put("TotalOrdered", df2.format(TotalOrdered));
						model.put("TotalRecevied", df2.format(TotalRecevied));

						return new ModelAndView("addPurchaseOrdersTyre_RETURN", model);
					case PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED:

						if (purchaseToParts != null) {
							for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToParts) {

								TotalOrdered += purchaseOrdersToParts.getQuantity();
								TotalRecevied += purchaseOrdersToParts.getReceived_quantity();
							}
						}
						model.put("TotalOrdered", df2.format(TotalOrdered));
						model.put("TotalRecevied", df2.format(TotalRecevied));

						return new ModelAndView("addPurchaseOrdersTyreCOMPLETED", model);
					}
				case PurchaseOrderType.PURCHASEORDER_TYPE_BATTERY_PO:
					// Checking Purchase OrderTo part in Status Type which one value
					switch (Purchase.getPurchaseorder_statusId()) {
					case PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED:

						if (purchaseToParts != null) {
							for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToParts) {

								TotalOrdered += purchaseOrdersToParts.getQuantity();
								TotalRecevied += purchaseOrdersToParts.getReceived_quantity();
							}
						}
						model.put("TotalOrdered",df2.format(TotalOrdered));
						model.put("TotalRecevied", df2.format(TotalRecevied));

						return new ModelAndView("addPurchaseOrdersBattery_RETURN", model);
					case PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED:

						if (purchaseToParts != null) {
							for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToParts) {

								TotalOrdered += purchaseOrdersToParts.getQuantity();
								TotalRecevied += purchaseOrdersToParts.getReceived_quantity();
							}
						}
						model.put("TotalOrdered", df2.format(TotalOrdered));
						model.put("TotalRecevied", df2.format(TotalRecevied));

						return new ModelAndView("addPurchaseOrdersBatteryCOMPLETED", model);
					}
					
				case PurchaseOrderType.PURCHASEORDER_TYPE_UREA_PO:
					switch (Purchase.getPurchaseorder_statusId()) {
					case PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED:
						return new ModelAndView("returnPurchaseOrderUrea", model);
					case PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED:
						return new ModelAndView("completedPurchaseOrderUrea", model);
					}
					
				default:

					return new ModelAndView("addPurchaseOrdersParts_RETURN", model);
				}

		} catch (Exception e) {
				System.err.println("Exception : "+e);
				throw e;
			}
		}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/PrintPurchaseReturn", method = RequestMethod.GET)
	public ModelAndView showUserProfileReturn(@RequestParam("id") final Long PrintId,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 				poConfiguration	        = null;
		try {

			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			poConfiguration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));
			model.put("poConfiguration",poConfiguration);

			 HashMap<String, Object>  threadHM	=  PurchaseOrdersService.getPurchaseOrderDetailsHM(PrintId, userDetails);
				
			PurchaseOrdersDto Purchase = POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
				
			model.put("PurchaseOrder", Purchase);

			List<PurchaseOrdersToPartsDto> purchaseToParts = PurchaseOrdersService
					.getPurchaseOrdersTasksToParts(Purchase.getPurchaseorder_id(), userDetails.getCompany_id());
			// model.put("PurchaseOrderPart", purchaseToParts);

			// model.put("JobSubType", JobTypeService.listJobSubType());
			if (Purchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED) {

				Double TotalOrdered = 0.0;
				Double TotalRecevied = 0.0;
				if (purchaseToParts != null) {
					for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToParts) {

						TotalOrdered += purchaseOrdersToParts.getQuantity();
						TotalRecevied += purchaseOrdersToParts.getReceived_quantity();
					}
				}
				model.put("TotalOrdered", df2.format(TotalOrdered));
				model.put("TotalRecevied",df2.format(TotalRecevied));

				model.put("PurchaseOrderDebitNote",
						PurchaseOrdersService.getPurchaseOrdersTasksToDebitNote(Purchase.getPurchaseorder_id(), userDetails.getCompany_id()));

				return new ModelAndView("showPurchasePrintReturn", model);

			} else if (Purchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED) {

				List<PurchaseOrdersToDebitNoteDto> purchaseToDebitNote = PurchaseOrdersService
						.getPurchaseOrdersTasksToDebitNote(Purchase.getPurchaseorder_id(), userDetails.getCompany_id());
				model.put("PurchaseOrderDebitNote", purchaseToDebitNote);

				Double TotalOrdered = 0.0;
				Double TotalRecevied = 0.0;
				if (purchaseToParts != null) {
					for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToParts) {

						TotalOrdered += purchaseOrdersToParts.getQuantity();
						TotalRecevied += purchaseOrdersToParts.getReceived_quantity();
					}
				}
				model.put("TotalOrdered",df2.format(TotalOrdered));
				model.put("TotalRecevied",df2.format(TotalRecevied));

				return new ModelAndView("showPurchasePrintComplete", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showPurchasePrintReturn", model);
	}

	/*
	 * savePurchaseOrderPart This Controller RequestMapping Getting the value Of
	 * Save PurchaseOrder Part the values in parts
	 */
	@PostMapping(value = "/savePurchaseOrderPart")
	public ModelAndView savePurchaseOrderPart(@ModelAttribute("command") PurchaseOrdersToParts purchaseOrdersTasksToParts, 
			PurchaseOrders purchaseOrders, final HttpServletRequest request,@RequestParam("partid") final String[] partid, 
			@RequestParam("quantity") final String[] quantity,
			@RequestParam("parteachcost") final String[] parteachcost,
			@RequestParam("discount") final String[] discount,
			@RequestParam("tax") final String[] tax,
			@RequestParam("totalcost") final String[] totalcost) throws Exception {
		
		System.err.println("purchaseOrdersTasksToParts"+(purchaseOrdersTasksToParts));
		

		
		HashMap<String, Object> 		configuration	        	= null;
		boolean					 		totalGstCostConfig	      	= false;
		Map<String, Object>	 			model 						= null;
		Double 							gstCost 					= 0.0;
		Double 							poGstCost 					= 0.0;
		Double 							poGstTotalCost 				= 0.0;
		ValueObject 					object 						= null;
		CustomUserDetails 				userDetails 				= null;
		PurchaseOrdersToParts 			purchaseTaskToParts 		= null;
		PurchaseOrders 					purchaseOrder 				= null;
		PurchaseOrders 					getPurchaseOrders		 	= null;
		boolean					 		makeApprovalConfig	      	= false;

		try {	
			if(partid !=null && partid.length > 0) {
				
			
			model 				= new HashMap<String, Object>();
			object 				= new ValueObject();
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			totalGstCostConfig	= (boolean) configuration.getOrDefault(PurchaseOrdersConfigurationConstant.TOTAL_GST_COST, false);
			makeApprovalConfig	= (boolean) configuration.getOrDefault(PurchaseOrdersConfigurationConstant.MAKE_APPROVAL, false);
			getPurchaseOrders	= PurchaseOrdersService.getPurchaseOrders(purchaseOrders.getPurchaseorder_id());

			purchaseOrder		= POBL.preparePurchaseOrdersToAddPart(purchaseOrders);
			for(int i =0 ;i<partid.length;i++) {
		
				purchaseTaskToParts= POBL.preparePurchaseOrdersTaskToPart(partid[i],quantity[i],parteachcost[i],discount[i],tax[i]);
				purchaseTaskToParts.setCompanyId(userDetails.getCompany_id());
				purchaseTaskToParts.setPurchaseorders(purchaseOrder);
				purchaseTaskToParts.setVendorId(purchaseOrder.getPurchaseorder_vendor_id());
				if(makeApprovalConfig) {
					purchaseTaskToParts.setApprovalPartStatusId(PurchaseOrderState.NOT_APPROVED);
				}else {
					purchaseTaskToParts.setApprovalPartStatusId(PurchaseOrderState.APPROVED);
				}
				PurchaseOrdersService.addPurchaseOrdersToParts(purchaseTaskToParts);
				try {
					if(getPurchaseOrders != null && totalGstCostConfig) {
						poGstCost		= getPurchaseOrders.getPurchaseorder_totaltax_cost();
						gstCost			= (purchaseTaskToParts.getQuantity() * purchaseTaskToParts.getParteachcost())* (purchaseTaskToParts.getTax() / 100);
						poGstTotalCost	= poGstCost+gstCost;

						object.put("gstCost", poGstTotalCost);
						object.put("purchaseOrderId", purchaseOrders.getPurchaseorder_id());
						object.put("companyId", userDetails.getCompany_id());
						PurchaseOrdersService.updatePurchaseOrderVariousTotalCost(object);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			PurchaseOrdersService.updatePurchaseOrderMainTotalCost(purchaseOrders.getPurchaseorder_id());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrders.getPurchaseorder_id() + "", model);
	}
	
	@PostMapping(value = "/savePurchaseOrderCloth")
	public ModelAndView savePurchaseOrderCloth( @ModelAttribute("command") PurchaseOrdersToParts PurchaseOrdersTasksToParts, PurchaseOrders purchaseOrders,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 		configuration	        	= null;
		boolean					 		totalGstCostConfig	      	= false;
		Double 							gstCost 					= 0.0;
		Double 							poGstCost 					= 0.0;
		Double 							poGstTotalCost 				= 0.0;
		ValueObject 					object 						= null;
		PurchaseOrders 					getPurchaseOrders		 	= null;
		boolean					 		makeApprovalConfig	      	= false;

		Long Purchaseorders_id = null;
		try {
			object 				= new ValueObject();
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			makeApprovalConfig	= (boolean) configuration.getOrDefault(PurchaseOrdersConfigurationConstant.MAKE_APPROVAL, false);
			totalGstCostConfig	= (boolean) configuration.getOrDefault(PurchaseOrdersConfigurationConstant.TOTAL_GST_COST, false);
			getPurchaseOrders	= PurchaseOrdersService.getPurchaseOrders(purchaseOrders.getPurchaseorder_id());

			PurchaseOrdersToParts purchaseTaskToParts = preparePurchaseOrdersTaskToPart(PurchaseOrdersTasksToParts);
			purchaseTaskToParts.setCompanyId(userDetails.getCompany_id());
			PurchaseOrders PurchaseOrders = POBL.preparePurchaseOrdersToAddPart(purchaseOrders);
			purchaseTaskToParts.setPurchaseorders(PurchaseOrders);
			if(makeApprovalConfig) {
				purchaseTaskToParts.setApprovalPartStatusId(PurchaseOrderState.NOT_APPROVED);
			}else {
				purchaseTaskToParts.setApprovalPartStatusId(PurchaseOrderState.APPROVED);
			}
			PurchaseOrdersService.addPurchaseOrdersToParts(purchaseTaskToParts);

			// Update Main PurchaseOrder Total cost
			try {

				PurchaseOrdersService.updatePurchaseOrderMainTotalCost(purchaseOrders.getPurchaseorder_id());
				if(getPurchaseOrders != null && totalGstCostConfig) {
					poGstCost		= getPurchaseOrders.getPurchaseorder_totaltax_cost();
					gstCost			= (purchaseTaskToParts.getQuantity() * purchaseTaskToParts.getParteachcost())* (purchaseTaskToParts.getTax() / 100);
					poGstTotalCost	= poGstCost+gstCost;
					
					object.put("gstCost", poGstTotalCost);
					object.put("purchaseOrderId", purchaseOrders.getPurchaseorder_id());
					object.put("companyId", userDetails.getCompany_id());
					
					PurchaseOrdersService.updatePurchaseOrderVariousTotalCost(object);
				} 

			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		Purchaseorders_id = purchaseOrders.getPurchaseorder_id();

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + Purchaseorders_id + "", model);
	}

	/*
	 * savePurchaseOrderTyre This Controller RequestMapping Getting the value Of
	 * Save PurchaseOrder Part the values in parts
	 */
	@PostMapping(value = "/savePurchaseOrderTyre")
	public ModelAndView savePurchaseOrderTyre( @ModelAttribute("command") PurchaseOrdersToParts PurchaseOrdersTasksToParts, 
			PurchaseOrders purchaseOrders, final HttpServletRequest request) throws Exception {
		Map<String, Object> 			model 						= new HashMap<String, Object>();
		Long 							Purchaseorders_id 			= null;
		HashMap<String, Object> 		configuration	        	= null;
		boolean					 		totalGstCostConfig	      	= false;
		PurchaseOrders 					getPurchaseOrders		 	= null;
		Double 							gstCost 					= 0.0;
		Double 							poGstCost 					= 0.0;
		Double 							poGstTotalCost 				= 0.0;
		ValueObject 					object 						= null;
		boolean					 		makeApprovalConfig	      	= false;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			totalGstCostConfig	= (boolean) configuration.getOrDefault(PurchaseOrdersConfigurationConstant.TOTAL_GST_COST, false);
			makeApprovalConfig	= (boolean) configuration.getOrDefault(PurchaseOrdersConfigurationConstant.MAKE_APPROVAL, false);
			getPurchaseOrders	= PurchaseOrdersService.getPurchaseOrders(purchaseOrders.getPurchaseorder_id());
			object 				= new ValueObject();
			PurchaseOrdersToParts purchaseTaskToParts = preparePurchaseOrdersTask_Tyre(PurchaseOrdersTasksToParts);

			PurchaseOrders PurchaseOrders = POBL.preparePurchaseOrdersToAddPart(purchaseOrders);
			if(makeApprovalConfig) {
				purchaseTaskToParts.setApprovalPartStatusId(PurchaseOrderState.NOT_APPROVED);
			}else {
				purchaseTaskToParts.setApprovalPartStatusId(PurchaseOrderState.APPROVED);
			}
		
			purchaseTaskToParts.setPurchaseorders(PurchaseOrders);
			purchaseTaskToParts.setCompanyId(userDetails.getCompany_id());
			PurchaseOrdersService.addPurchaseOrdersToParts(purchaseTaskToParts);

			// Update Main PurchaseOrder Total cost
			try {

				PurchaseOrdersService.updatePurchaseOrderMainTotalCost(purchaseOrders.getPurchaseorder_id());
				
				if(getPurchaseOrders != null && totalGstCostConfig) {
					poGstCost		= getPurchaseOrders.getPurchaseorder_totaltax_cost();
					gstCost			= (purchaseTaskToParts.getQuantity() * purchaseTaskToParts.getParteachcost())* (purchaseTaskToParts.getTax() / 100);
					poGstTotalCost	= poGstCost+gstCost;
					
					object.put("gstCost", poGstTotalCost);
					object.put("purchaseOrderId", purchaseOrders.getPurchaseorder_id());
					object.put("companyId", userDetails.getCompany_id());
					
					PurchaseOrdersService.updatePurchaseOrderVariousTotalCost(object);
				}

			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		Purchaseorders_id = purchaseOrders.getPurchaseorder_id();

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + Purchaseorders_id + "", model);
	}
	
	@PostMapping(value = "/savePurchaseOrderBattery")
	public ModelAndView savePurchaseOrderBattery( @ModelAttribute("command") PurchaseOrdersToParts PurchaseOrdersTasksToParts,
			PurchaseOrders purchaseOrders, final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 		configuration	        	= null;
		boolean					 		totalGstCostConfig	      	= false;
		Double 							gstCost 					= 0.0;
		Double 							poGstCost 					= 0.0;
		Double 							poGstTotalCost 				= 0.0;
		boolean					 		makeApprovalConfig	      	= false;

		Long Purchaseorders_id = null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			makeApprovalConfig	= (boolean) configuration.getOrDefault(PurchaseOrdersConfigurationConstant.MAKE_APPROVAL, false);
			totalGstCostConfig	= (boolean) configuration.getOrDefault(PurchaseOrdersConfigurationConstant.TOTAL_GST_COST, false);
			ValueObject object = new  ValueObject();
			PurchaseOrdersToParts purchaseTaskToParts = preparePurchaseOrdersTask_Battery(PurchaseOrdersTasksToParts);
			PurchaseOrders getPurchaseOrders	= PurchaseOrdersService.getPurchaseOrders(purchaseOrders.getPurchaseorder_id());

			PurchaseOrders PurchaseOrders = POBL.preparePurchaseOrdersToAddPart(purchaseOrders);
			purchaseTaskToParts.setPurchaseorders(PurchaseOrders);
			purchaseTaskToParts.setCompanyId(userDetails.getCompany_id());
			if(makeApprovalConfig) {
				purchaseTaskToParts.setApprovalPartStatusId(PurchaseOrderState.NOT_APPROVED);
			}else {
				purchaseTaskToParts.setApprovalPartStatusId(PurchaseOrderState.APPROVED);
			}
			PurchaseOrdersService.addPurchaseOrdersToParts(purchaseTaskToParts);

			// Update Main PurchaseOrder Total cost
			try {

				PurchaseOrdersService.updatePurchaseOrderMainTotalCost(purchaseOrders.getPurchaseorder_id());
				
				if(getPurchaseOrders != null && totalGstCostConfig) {
					poGstCost		= getPurchaseOrders.getPurchaseorder_totaltax_cost();
					gstCost			= (purchaseTaskToParts.getQuantity() * purchaseTaskToParts.getParteachcost())* (purchaseTaskToParts.getTax() / 100);
					poGstTotalCost	= poGstCost+gstCost;
					
					object.put("gstCost", poGstTotalCost);
					object.put("purchaseOrderId", purchaseOrders.getPurchaseorder_id());
					object.put("companyId", userDetails.getCompany_id());
					
					PurchaseOrdersService.updatePurchaseOrderVariousTotalCost(object);
				}

			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		Purchaseorders_id = purchaseOrders.getPurchaseorder_id();

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + Purchaseorders_id + "", model);
	}

	/** This Controller save the saveTyreInventory Method */
	@PostMapping(value = "/PurchaseOrdersaveTyreInfo")
	public ModelAndView saveTyreInfo(@ModelAttribute("command") InventoryTyreInvoiceDto tyreInvoiceDto,
			@RequestParam("TyreSerialno") final String[] tyreSerialno,
			@RequestParam("RECEIVED_REMARKS") final String receivedRemark,
			@RequestParam("PO_ID") final Long purchaseOrderId,
			@RequestParam("TYRE_PART_ID") final Long purchaseOrderPartId, Model modelAdd,
			final HttpServletRequest request) {
		Map<String, Object> 	model 						= null;
		CustomUserDetails 		userDetails 				= null;
		Integer 				countSAVE 					= 0;
		Integer 				countDuplicate 				= 0;
		PurchaseOrdersToParts 	purchaseOrdersToParts 		= null;
		int 					receivedCount				= 0;
		Double 					receivedQuantity 			= 0.0;
		Double 					notReceivedQuantity 		= 0.0;
		PurchaseOrders			Purchase					= null;
		try {
			model 					= new HashMap<String, Object>();
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			purchaseOrdersToParts 	= PurchaseOrdersService.getPurchaseOrdersToParts(purchaseOrderPartId);
			Purchase				= PurchaseOrdersService.getPurchaseOrders(purchaseOrderId);
//			if(Purchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED) {
//				model.put("alreadyReceived", true);
//				return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrderId + "", model);
	//		}
			
			
			if(tyreSerialno == null ) {
				return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrderId + "", model);
			}
			for (int j = 0; j < tyreSerialno.length; j++) {
				if (tyreSerialno[j] != null && !tyreSerialno[j].equalsIgnoreCase("")) {
					receivedCount += 1;
				}
			}
			
			if((purchaseOrdersToParts != null ) && ((receivedCount + purchaseOrdersToParts.getReceived_quantity()) > purchaseOrdersToParts.getQuantity())) {
				model.put("invalidReceivedQty", true);
				return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrderId + "", model);
			}

			
			if (tyreSerialno != null) {
				for (int i = 0; i < tyreSerialno.length; i++) {
					if (tyreSerialno[i] != null && !tyreSerialno[i].equalsIgnoreCase("")) {
						PurchaseOrdersToTyre purchaseOrdersToTyre = new PurchaseOrdersToTyre();

						purchaseOrdersToTyre.setTYRE_SERIAL_NUMBER(tyreSerialno[i]);
						purchaseOrdersToTyre.setPO_ID(purchaseOrderId);
						purchaseOrdersToTyre.setTYRE_PART_ID(purchaseOrderPartId);
						purchaseOrdersToTyre.setRECEIVED_REMARKS(receivedRemark);
						purchaseOrdersToTyre.setCompanyId(userDetails.getCompany_id());

						try {
							InventoryTyre validate = iventoryTyreService.Validate_InventoryTyre(tyreSerialno[i], userDetails.getCompany_id());
							if (validate == null) {
								PurchaseOrdersService.addPurchaseOrders_Tyre_Serial_Number(purchaseOrdersToTyre);
								countSAVE++;
							} else {
								countDuplicate++;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} 

				try {
				

					if (purchaseOrdersToParts != null) {
						if (purchaseOrdersToParts.getNotreceived_quantity() != null) {

							notReceivedQuantity = purchaseOrdersToParts.getNotreceived_quantity();
						}
						if (purchaseOrdersToParts.getReceived_quantity() != null) {

							receivedQuantity = purchaseOrdersToParts.getReceived_quantity();
						}

						// Get UI To Received Quantity
						if (countSAVE != 0) {
							receivedQuantity 		+= countSAVE;
							notReceivedQuantity 	-= countSAVE;
							
							
							//PurchaseOrdersService.deleteDebitNoteByPOId(Purchase.getPurchaseorder_id(), userDetails.getCompany_id());
							
							if(notReceivedQuantity > 0) {
								PurchaseOrdersToDebitNote DebitNode = POBL
										.preparePurchaseOrdersToParts_To_PurchaseOrdersToDebitNote_Tyre(
												purchaseOrdersToParts, countSAVE);
								DebitNode.setCompanyId(userDetails.getCompany_id());
								// DebitNode.setDebitNoteNumber(sequenceCounter.getNextVal());
								PurchaseOrdersService.addPurchaseOrdersToDebitNote(DebitNode);
							}
						}
						
						PurchaseOrdersService.updatePurchaseOrderPart_ReceivedQuantity( receivedQuantity, receivedRemark, notReceivedQuantity, purchaseOrderPartId);
					}

				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
				} finally {
					userDetails = null;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrderId + "", model);
	}
	
	/** This Controller save the saveTyreInventory Method */
	@RequestMapping(value = "/saveBatteryInfo", method = RequestMethod.POST)
	public ModelAndView saveBatteryInfo(@ModelAttribute("command") InventoryTyreInvoiceDto inventoryTyreInvoiceDto,
			@RequestParam("batterySerialNumber") final String[] batterySerialno,
			@RequestParam("RECEIVED_REMARKS") final String receivedRemark,
			@RequestParam("PO_ID") final Long purchaseOrderId,
			@RequestParam("TYRE_PART_ID") final Long purchaseOrderPartId, Model modelAdd,
			final HttpServletRequest request) {
		
		
		Map<String, Object> 		model 					= null;
		CustomUserDetails 			userDetails 			= null;
		Integer 					countSAVE 				= 0;
		Integer 					countDuplicate 			= 0;
		PurchaseOrderToBattery 		battery 				= null;
		List<Battery>  				validate		 		= null;
		PurchaseOrdersToParts 		purchaseOrdersToParts 	= null;
		Double 						receivedQuantity 		= 0.0;
		Double 						notReceivedQuantity 	= 0.0;
		int 						receivedCount			= 0;
		
		try {
			model 					= new HashMap<String, Object>();
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			purchaseOrdersToParts 	= PurchaseOrdersService.getPurchaseOrdersToParts(purchaseOrderPartId);
			if(batterySerialno == null || batterySerialno.length < 0) {
				return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrderId + "", model);
			}
			for (int j = 0; j < batterySerialno.length; j++) {
				if (batterySerialno[j] != null && !batterySerialno[j].equalsIgnoreCase("")) {
					receivedCount += 1;
				}
			}
			
			if((purchaseOrdersToParts != null ) && ((receivedCount + purchaseOrdersToParts.getReceived_quantity()) > purchaseOrdersToParts.getQuantity())) {
				model.put("invalidReceivedQty", true);
				return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrderId + "", model);
			}

			
			if (batterySerialno != null) {
				for (int i = 0; i < batterySerialno.length; i++) {
					if (!batterySerialno[i].equalsIgnoreCase("") && batterySerialno[i] != null) {
						battery = new PurchaseOrderToBattery();
						
						battery.setBatterySerialNumber(batterySerialno[i]);
						battery.setPurchaseOrderId(purchaseOrderId);
						battery.setPurchaseOrderToPartId(purchaseOrderPartId);
						battery.setReceivedRemark(receivedRemark);
						battery.setCompanyId(userDetails.getCompany_id());

						try {
							validate =		batteryService.validateBatterySerialNumber(batterySerialno[i], userDetails.getCompany_id());
							
							if (validate == null || validate.isEmpty()) {
								PurchaseOrdersService.addPurchaseOrders_Battery(battery);
								countSAVE++;
							} else {
								countDuplicate++;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				try {
					if (purchaseOrdersToParts != null) {
						if (purchaseOrdersToParts.getNotreceived_quantity() != null) {

							notReceivedQuantity = purchaseOrdersToParts.getNotreceived_quantity();
						}
						if (purchaseOrdersToParts.getReceived_quantity() != null) {

							receivedQuantity = purchaseOrdersToParts.getReceived_quantity();
						}

						if (countSAVE != 0) {
							receivedQuantity += countSAVE;
							notReceivedQuantity -= countSAVE;
						}
						//PurchaseOrdersService.deleteDebitNoteByPOId(purchaseOrdersToParts.getPurchaseorders().getPurchaseorder_id(), userDetails.getCompany_id());
						if(notReceivedQuantity > 0) {
							// get PurchaseOrderToPart To
							// SavePurchaseOrderToDebitNote Save
							PurchaseOrdersToDebitNote DebitNode = POBL
									.preparePurchaseOrdersToParts_To_PurchaseOrdersToDebitNote_Tyre(
											purchaseOrdersToParts, countSAVE);
							DebitNode.setCompanyId(userDetails.getCompany_id());
							// DebitNode.setDebitNoteNumber(sequenceCounter.getNextVal());
							PurchaseOrdersService.addPurchaseOrdersToDebitNote(DebitNode);
						}

						PurchaseOrdersService.updatePurchaseOrderPart_ReceivedQuantity( receivedQuantity, receivedRemark, notReceivedQuantity, purchaseOrderPartId);
					}

				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
				} finally {
					userDetails = null;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrderId + "", model);
	}

	/*
	 * OrderedPurchaseOrder This Controller RequestMapping Getting the value Of Save
	 * PurchaseOrder Part the values in parts
	 */
	@RequestMapping(value = "/OrderedPurchaseOrder", method = RequestMethod.POST)
	public ModelAndView OrderedPurchaseOrder(@ModelAttribute("command") PurchaseOrders purchaseOrders,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PurchaseOrders OrderedPurchase_BY = POBL.GetOrderedPurchaseOrderUpdate(purchaseOrders);

		// Update Main PurchaseOrder Advance and Balance And Statues IS ORDERED
		// Change
		try {
			PurchaseOrders Purchase = PurchaseOrdersService.getPurchaseOrders(purchaseOrders.getPurchaseorder_id());

			Double TotalPurchaseOrdercost = 0.0;
			Double TotalPurchaseOrder_balance = 0.0;

			if (Purchase != null) {

				if (Purchase.getPurchaseorder_totalcost() != null) {

					TotalPurchaseOrdercost = Purchase.getPurchaseorder_totalcost();
				}
				if (Purchase.getPurchaseorder_balancecost() != null) {

					TotalPurchaseOrder_balance = Purchase.getPurchaseorder_balancecost();
				}

				// TotalPurchaseOrder_balance
				TotalPurchaseOrder_balance = (TotalPurchaseOrdercost - purchaseOrders.getPurchaseorder_advancecost());
			}
			OrderedPurchase_BY.setPurchaseorder_balancecost(TotalPurchaseOrder_balance);

			PurchaseOrdersService.updatePurchaseOrderedBYAdavanceCost(OrderedPurchase_BY);

			switch (Purchase.getPurchaseorder_typeId()) {
			case PurchaseOrderType.PURCHASEORDER_TYPE_PART_PO:

				if (purchaseOrders.getPurchaseorder_id() != null) {
					sentOrderd_DetailsVendorEmail(purchaseOrders.getPurchaseorder_id(), request.getLocale(), userDetails);
					// Send message
				}
				break;

			case PurchaseOrderType.PURCHASEORDER_TYPE_TYRE_PO:

				if (purchaseOrders.getPurchaseorder_id() != null) {
					sent_Tyre_Orderd_DetailsVendorEmail(purchaseOrders.getPurchaseorder_id(), request.getLocale(), userDetails);
					// Send message
				}

				break;
			case PurchaseOrderType.PURCHASEORDER_TYPE_BATTERY_PO:

				if (purchaseOrders.getPurchaseorder_id() != null) {
					sent_Battery_Orderd_DetailsVendorEmail(purchaseOrders.getPurchaseorder_id(), request.getLocale(), userDetails);
					// Send message
				}

				break;
			case PurchaseOrderType.PURCHASEORDER_TYPE_CLOTH_PO:

				if (purchaseOrders.getPurchaseorder_id() != null) {
					sent_Cloth_Orderd_DetailsVendorEmail(purchaseOrders.getPurchaseorder_id(), request.getLocale(), userDetails);
					// Send message
				}

				break;	
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		return new ModelAndView(
				"redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrders.getPurchaseorder_id() + "&email=true", model);
	}

	// Save PurchaseOrder Part the values in parts
	@PostMapping(value = "/PurchaseOrdersUpdate_ReceivedQuantity")
	public ModelAndView PurchaseOrdersUpdate_ReceivedQuantity(@ModelAttribute("command") PurchaseOrdersToParts purchaseOrdersTasksToParts, 
			PurchaseOrders purchaseOrders, final HttpServletRequest request) throws Exception {
		Map<String, Object> 		model 										= null;
		PurchaseOrdersToParts 		purchaseOrdersToParts 						= null;
		Long 						purchaseOrderId 							= null;
		Double 						totalOrderdQuantity 						= 0.0;
		Double 						totalReceivedQuantity 						= 0.0;
		Double 						totalRejectedQuantity 						= 0.0;
		PurchaseOrders				purchase									= null;
		PartInvoiceSequenceCounter	seqCounter									= null;
		PartInvoice					partInvoice									= null;
		Inventory					inventory									= null;
		try {
			
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			model = new HashMap<String, Object>();
			purchaseOrdersToParts 	= PurchaseOrdersService.getPurchaseOrdersToParts(purchaseOrdersTasksToParts.getPurchaseorderto_partid());
			purchaseOrderId 		= purchaseOrdersToParts.getPurchaseorders().getPurchaseorder_id();
			
			purchase				= PurchaseOrdersService.getPurchaseOrders(purchaseOrderId);
			/*if(Purchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED) {
				model.put("alreadyReceived", true);
				return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrderId + "", model);
			}*/
			
			if(purchaseOrdersTasksToParts.getReceived_quantity() < 0) {
				return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrderId + "", model);
			}
			if((purchaseOrdersTasksToParts.getReceived_quantity()+purchaseOrdersToParts.getReceived_quantity()) > purchaseOrdersToParts.getQuantity()){
				model.put("validateReceivedQuantity", true);
				return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrderId + "", model);
			}
			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
			if (purchaseOrdersToParts != null) {
				if (purchaseOrdersToParts.getQuantity() != null) {
					totalOrderdQuantity = purchaseOrdersToParts.getQuantity();
				}
				totalReceivedQuantity = purchaseOrdersTasksToParts.getReceived_quantity() + purchaseOrdersToParts.getReceived_quantity();
				totalRejectedQuantity = (totalOrderdQuantity - totalReceivedQuantity);
				

				PurchaseOrdersService.updatePurchaseOrderPart_ReceivedQuantity(totalReceivedQuantity, 
						purchaseOrdersTasksToParts.getReceived_quantity_remark(), totalRejectedQuantity, purchaseOrdersTasksToParts.getPurchaseorderto_partid());
			
				//history save
				
				PurchaseOrdersToPartsHistory 	purchaseOrdersHistory 	= new PurchaseOrdersToPartsHistoryBL().purchaseOrdersToPartsHistory(purchaseOrdersToParts, purchaseOrdersTasksToParts);
				
				purchaseOrdersToPartsHistoryRepository.save(purchaseOrdersHistory);
				
				if(purchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED) {
					
					partInvoice = partInvoiceService.validatePartInvoiceToPO(purchase.getPurchaseorder_id(), userDetails.getCompany_id());
					
					
					Double eachPartTotalCost	= 0.0;
					if(partInvoice != null && partInvoice.getQuantity() > 0) {
						
						inventory			= InventoryService.getInventoryDetailsByPartId(purchaseOrdersToParts.getPurchaseorderto_partid(), userDetails.getCompany_id());
						if(inventory != null) {
							
							inventory.setHistory_quantity(inventory.getHistory_quantity() + purchaseOrdersTasksToParts.getReceived_quantity());
							inventory.setQuantity(inventory.getQuantity() + purchaseOrdersTasksToParts.getReceived_quantity());
							inventory.setMainQuantity(inventory.getMainQuantity() + purchaseOrdersTasksToParts.getReceived_quantity());
							inventory.setLastModifiedById(userDetails.getId());
							inventory.setLastupdated(new Date());
							
							
							Double InvendiscountCost = 0.0;
							Double InvenTotalCost = 0.0;
							
							
							  InvendiscountCost = (inventory.getHistory_quantity() *
							  purchaseOrdersToParts.getParteachcost()) - ((inventory.getHistory_quantity()
							  * purchaseOrdersToParts.getParteachcost()) *
							  (purchaseOrdersToParts.getDiscount() / 100));
							  InvenTotalCost = round(((InvendiscountCost) + (InvendiscountCost *
									  				(purchaseOrdersToParts.getTax() / 100))), 2);
							 
							inventory.setTotal(round(InvenTotalCost , 2));
							//inventory.setInvoice_amount(( Double.parseDouble(inventory.getInvoice_amount()) + eachPartTotalCost )+"");
							eachPartTotalCost = InvenTotalCost/inventory.getQuantity();
							
							
							InventoryService.add_Inventory(inventory);
							
							InventoryService
							.update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(
									inventory.getPartid(),
									inventory.getLocationId());
							
							InventoryService
							.update_InventoryAll_PARTID_PARTNAME_GENRAL(inventory.getPartid());

						}else {
							addToInventory(purchase, purchaseOrdersToParts, purchaseOrdersTasksToParts, userDetails, partInvoice);
						}
												
					}else {
						partInvoice 	= POBL.prepare_PurchaseOrder_PartInvoice(purchase);
						seqCounter 		= partInvoiceSequenceService.findNextInventoryNumber(userDetails.getCompany_id(), SequenceTypeContant.SEQUENCE_TYPE_PART_INVENTORY);
						partInvoice.setPartInvoiceNumber(seqCounter.getNextVal());
						partInvoiceService.saveInvoice(partInvoice);
						addToInventory(purchase, purchaseOrdersToParts, purchaseOrdersTasksToParts, userDetails, partInvoice);
						
					}
					if(partInvoice.getQuantity() == null)
						partInvoice.setQuantity(purchaseOrdersTasksToParts.getReceived_quantity());
					else
						partInvoice.setQuantity(partInvoice.getQuantity() + purchaseOrdersTasksToParts.getReceived_quantity());
					
					partInvoice.setInvoiceAmount(( Double.parseDouble(partInvoice.getInvoiceAmount()) + eachPartTotalCost )+"");
					
					partInvoiceService.saveInvoice(partInvoice);
					PurchaseOrdersService.deleteDebitNoteByPOIdByPartId(purchaseOrdersToParts.getPurchaseorderto_partid(), purchaseOrdersToParts.getCompanyId());
					
					if(totalRejectedQuantity > 0) {
						
						PurchaseOrdersToDebitNote DebitNode = POBL
								.preparePurchaseOrdersToParts_To_PurchaseOrdersToDebitNote(
										purchaseOrdersToParts, totalRejectedQuantity);
						DebitNode.setCompanyId(purchaseOrdersToParts.getCompanyId());
						DebitNode.setPurchaseorderto_partid(purchaseOrdersToParts.getPurchaseorderto_partid());
						// DebitNode.setDebitNoteNumber(sequenceCounter.getNextVal());
						PurchaseOrdersService.addPurchaseOrdersToDebitNote(DebitNode);
					}
				}
				
				
			}
		
			return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrderId + "", model);
		
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

	}
	
	
	private void addToInventory(PurchaseOrders	purchase, PurchaseOrdersToParts	purchaseOrdersToParts,
				PurchaseOrdersToParts	purchaseOrdersTasksToParts, CustomUserDetails	userDetails, PartInvoice	partInvoice) throws Exception {
		MasterPartsDto				master										= null;
		if (purchaseOrdersTasksToParts.getReceived_quantity() != null
				&& purchaseOrdersTasksToParts.getReceived_quantity() != 0.0) {

			// Create purchaseOrdersToParts To Inventory
			Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
			PartMeasurementUnit	 	measurementUnit	= null;
			Double					noOfParts		= 0.0;
			Double					partEachCost	= 0.0;

			try {
				master = MasterPartsService
						.getMasterParts(purchaseOrdersToParts.getPartid(), userDetails.getCompany_id());
				
				measurementUnit	= measermentHM.get(Integer.parseInt(master.getUnitTypeId()+""));
				
				Double originalQuantity		= purchaseOrdersTasksToParts.getReceived_quantity();
				Double originalUnitPrice	= purchaseOrdersToParts.getParteachcost();
				
				if(measurementUnit != null && measurementUnit.getConvertTo() != null && measurementUnit.getConvertTo() > 0) {
					noOfParts		= measurementUnit.getConversionRate() * purchaseOrdersTasksToParts.getReceived_quantity();
					partEachCost	= purchaseOrdersToParts.getParteachcost() / measurementUnit.getConversionRate();
				}else {
					partEachCost	= purchaseOrdersToParts.getParteachcost();
					noOfParts		= purchaseOrdersTasksToParts.getReceived_quantity();
				}
				
				
				InventoryAll Inventoryall = POBL.prepareInventoryAll(master);
				Inventoryall.setCompanyId(userDetails.getCompany_id());
				InventoryLocation InventoryLocation = POBL.prepareInventoryLocation(purchase,
						master);
				PartLocations locations = iPartLocationsService.ValidatePartLocations(
						purchase.getPurchaseorder_shiplocation_id(), userDetails.getCompany_id());
				InventoryLocation.setCompanyId(userDetails.getCompany_id());
				if (locations != null)
					InventoryLocation.setLocationId(locations.getPartlocation_id());
				
				// Get PurchaseOrderToPart to Save Inventory
				Inventory Part = POBL.preparePurchaseOrdersToParts_To_Inventory(
						purchaseOrdersToParts, purchase, master, partEachCost, noOfParts);
				Part.setLocationId(InventoryLocation.getLocationId());
				Part.setCompanyId(userDetails.getCompany_id());
				Part.setPartInvoiceId(partInvoice.getPartInvoiceId());
				Part.setSerialNoAddedForParts(0);
				
				Part.setInvoice_date(purchase.getPurchaseorder_invoice_date());
				Part.setMainQuantity(originalQuantity);
				Part.setMainUnitprice(originalUnitPrice);
				Part.setUnitprice(partEachCost);
				Part.setHistory_quantity(noOfParts);
				Part.setQuantity(noOfParts);
				// save part Manufacturers Service in Master
				// part
				try {
					// first validate part_id is there are
					// not
					List<InventoryAll> validate = InventoryService.Validate_InventoryAll(
							master.getPartid(), userDetails.getCompany_id());
					if (validate != null && !validate.isEmpty()) {

						Long Inventory_all_id = (long) 0;
						Long Inventory_Location_id = (long) 0;
						
						for (InventoryAll updateInventory : validate) {
							// get part and AllQuantity in
							// all_Ready
							// in db
							// Add New Quantity

							Inventory_all_id = updateInventory.getInventory_all_id();

							break;
						}
						List<InventoryLocation> validate_Location = InventoryService
								.Validate_Inventory_Location(master.getPartid(),
										purchase.getPurchaseorder_shiplocation_id());
						if (validate_Location != null && !validate_Location.isEmpty()) {
							for (InventoryLocation validateLocation : validate_Location) {
								// get part and Location
								// Quantity in
								// all_Ready in db Add New
								// Quantity

								Inventory_Location_id = validateLocation
										.getInventory_location_id();
								break;
							}
						} else {

							// get part and Location
							// Quantity
							// Search
							// that
							// location part not in db
							// Create
							// new
							InventoryLocation.setLocation_quantity(
									noOfParts);
							InventoryAll NewAll = new InventoryAll();
							NewAll.setInventory_all_id(Inventory_all_id);
							InventoryLocation.setInventoryall(NewAll);
							InventoryService.add_InventoryLocation(InventoryLocation);

							Inventory_Location_id = InventoryLocation
									.getInventory_location_id();

						}

						// Quantity all ready save to update
						// in
						// all
						// ,
						// Location Quantity details

						Part.setInventory_all_id(Inventory_all_id);
						Part.setInventory_location_id(Inventory_Location_id);
						Part.setPartInvoiceId(partInvoice.getPartInvoiceId());
						Part.setSerialNoAddedForParts(0);
						InventoryService.save_Inventory(Part);

						// Insert Inventory QUANTITY Details
						// to Update
						// LOCATION AND ALL LOCATION
						// QUANTITY DETAILS

						InventoryService
								.update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(
										master.getPartid(),
										purchase.getPurchaseorder_shiplocation_id());

						InventoryService
								.update_InventoryAll_PARTID_PARTNAME_GENRAL(master.getPartid());

					} else {

						// get part and AllQuantity save
						Inventoryall.setAll_quantity(noOfParts);
						InventoryService.add_InventoryAll(Inventoryall);

						// get part and Location Quantity
						// save
						InventoryLocation.setLocation_quantity(noOfParts);
						InventoryLocation.setInventoryall(Inventoryall);
						InventoryService.add_InventoryLocation(InventoryLocation);

						Part.setInventory_all_id(Inventoryall.getInventory_all_id());
						Part.setInventory_location_id(
								InventoryLocation.getInventory_location_id());
						Part.setSerialNoAddedForParts(0);
						InventoryService.save_Inventory(Part);
					}
				//	model.put("saveInventory", true);

				} catch (Exception e) {
					e.printStackTrace();
					//return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
				}

			} catch (Exception e) {
				e.printStackTrace();
				//return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
			}

		}
	}

	/*
	 * This ReceivedPurchaseOrder Controller is Save to Part Inventory In Reveiced
	 * Part all to Save Inventory
	 */
	@RequestMapping(value = "/ReceivedPurchaseOrder", method = RequestMethod.POST)
	public ModelAndView ReceivedPurchaseOrder(@ModelAttribute("command") PurchaseOrdersDto purchaseOrders, 
			final HttpServletRequest request, RedirectAttributes redir) throws Exception {	
		
		Map<String, Object> 					model 				= new HashMap<String, Object>();
		List<PurchaseOrdersToPartsDto> 			purchaseToParts 	= null;
		PartInvoiceSequenceCounter 				seqCounter 			= null;
		CustomUserDetails 						userDetails 		= null;
		SimpleDateFormat 						sdf1  				= null;
		PurchaseOrders 							PurchaseOrder		= null;
		PartInvoice	 							partInvoice			= null;
		double 									quantityPart		= 0.0;
		
		try {
			
			sdf1 			= new SimpleDateFormat(DateTimeUtility.DD_MM_YY);
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			PurchaseOrder 	= (PurchaseOrdersService.getPurchaseOrders(purchaseOrders.getPurchaseorder_id()));
									
			if(PurchaseOrder.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED) {
				model.put("alreadyReceived", true);
				return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrders.getPurchaseorder_id() + "", model);
			}
			
			PartInvoice  validateInvoice = partInvoiceService.validatePartInvoiceToPO(purchaseOrders.getPurchaseorder_id(), userDetails.getCompany_id());
			
			partInvoice 	= POBL.prepare_PurchaseOrder_PartInvoice(PurchaseOrder,purchaseOrders);
			
			if(validateInvoice != null) {
				partInvoice.setPartInvoiceId(validateInvoice.getPartInvoiceId());
				partInvoice.setPartInvoiceNumber(validateInvoice.getPartInvoiceNumber());
			}else {
				seqCounter 		= partInvoiceSequenceService.findNextInventoryNumber(userDetails.getCompany_id(), SequenceTypeContant.SEQUENCE_TYPE_PART_INVENTORY);
				partInvoice.setPartInvoiceNumber(seqCounter.getNextVal());
			}
			
			
			purchaseToParts = PurchaseOrdersService.getPurchaseOrdersTasksToParts(purchaseOrders.getPurchaseorder_id(), userDetails.getCompany_id());
			
			for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToParts) {
				if(purchaseOrdersToParts.getApprovalPartStatusId() == PurchaseOrderState.APPROVED) {
					quantityPart += purchaseOrdersToParts.getReceived_quantity();
				}
			}
			partInvoice.setQuantity(quantityPart);
			if(quantityPart > 0)
			partInvoiceService.saveInvoice(partInvoice);
			
			List<PartLocationPermissionDto> PartLocPermission = PLBL.prepareListofPartLocation(partLocationPermissionService
					.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

			HashMap<String, Object>  threadHM	=  PurchaseOrdersService.getPurchaseOrderDetailsHM(purchaseOrders.getPurchaseorder_id(), userDetails);
			
			@SuppressWarnings("unchecked")
			PurchaseOrdersDto Purchase = POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
			

			// check user branch location and workOrder location if same
			if (InvenBL.isFilled_GET_Permission(Purchase.getPurchaseorder_shiplocation_id(), PartLocPermission)) {
				
				// code to save part invoice details in PartInvoice table
				seqCounter = partInvoiceSequenceService.findNextInventoryNumber(userDetails.getCompany_id(), SequenceTypeContant.SEQUENCE_TYPE_PART_INVENTORY);
				if (seqCounter == null) {
					model.put("sequenceNotFound", true);
					return new ModelAndView("redirect:/NewInventory/1.in", model);
				}
				

				List<PurchaseOrders> PurchaseInvoiceValidate = PurchaseOrdersService
						.Validate_PurchaseOrder_Received_Invoice(Purchase.getPurchaseorder_vendor_id(),
								purchaseOrders.getPurchaseorder_invoiceno());
				if (PurchaseInvoiceValidate != null && !PurchaseInvoiceValidate.isEmpty()) {

					String OldID = "";
					for (PurchaseOrders OldPo : PurchaseInvoiceValidate) {
						OldID += "<a href=\"PurchaseOrders_Parts.in?ID=" + OldPo.getPurchaseorder_id()
								+ "\" target=\"_blank\">PO-" + OldPo.getPurchaseorder_Number()
								+ "  <i class=\"fa fa-external-link\"></i></a> ,";
					}
					redir.addFlashAttribute("alreadyInvoice", OldID);
					return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID="
							+ purchaseOrders.getPurchaseorder_id() + "&Invoice=true", model);
				} else {

					PurchaseOrders ReceivedPurchase_BY = POBL.GetReceivedPurchaseOrderUpdate(purchaseOrders);

					// update PurchaseOrder to Received Done & stutes
					PurchaseOrdersService.updatePurchaseOrderedToReceived_Quantity(ReceivedPurchase_BY);

					// Update PurchaseOrder Received to order_by Add Inventory in
					// Received
					// Parts
					try {

						 purchaseToParts = PurchaseOrdersService
								.getPurchaseOrdersTasksToParts(purchaseOrders.getPurchaseorder_id(), userDetails.getCompany_id());
						if (purchaseToParts != null && !purchaseToParts.isEmpty()) {
							Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
							PartMeasurementUnit	 	measurementUnit	= null;
							MasterPartsDto 			Master 			= null;
							Double					partEachCost	= 0.0;
							Double					noOfParts		= 0.0;
							for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToParts) {
								
								
								
								if (purchaseOrdersToParts.getReceived_quantity() != 0.0
										&& purchaseOrdersToParts.getReceived_quantity() != null && purchaseOrdersToParts.getApprovalPartStatusId() == PurchaseOrderState.APPROVED) {

									// Create purchaseOrdersToParts To Inventory

									try {
										Master = MasterPartsService
												.getMasterParts(purchaseOrdersToParts.getPartid(), userDetails.getCompany_id());
										
										measurementUnit	= measermentHM.get(Integer.parseInt(Master.getUnitTypeId()+""));
										
										Double originalQuantity		= purchaseOrdersToParts.getReceived_quantity();
										Double originalUnitPrice	= purchaseOrdersToParts.getParteachcost();
										
										if(measurementUnit != null && measurementUnit.getConvertTo() != null && measurementUnit.getConvertTo() > 0) {
											noOfParts		= measurementUnit.getConversionRate() * purchaseOrdersToParts.getReceived_quantity();
											partEachCost	= purchaseOrdersToParts.getParteachcost() / measurementUnit.getConversionRate();
										}else {
											partEachCost	= purchaseOrdersToParts.getParteachcost();
											noOfParts		= purchaseOrdersToParts.getReceived_quantity();
										}
										
										
										InventoryAll Inventoryall = POBL.prepareInventoryAll(Master);
										Inventoryall.setCompanyId(userDetails.getCompany_id());
										InventoryLocation InventoryLocation = POBL.prepareInventoryLocation(Purchase,
												Master);
										PartLocations locations = iPartLocationsService.ValidatePartLocations(
												Purchase.getPurchaseorder_shiplocation_id(), userDetails.getCompany_id());
										InventoryLocation.setCompanyId(userDetails.getCompany_id());
										if (locations != null)
											InventoryLocation.setLocationId(locations.getPartlocation_id());
										
										// Get PurchaseOrderToPart to Save Inventory
										Inventory Part = POBL.preparePurchaseOrdersToParts_To_Inventory(
												purchaseOrdersToParts, Purchase, Master, partEachCost, noOfParts);
										Part.setLocationId(InventoryLocation.getLocationId());
										Part.setCompanyId(userDetails.getCompany_id());
										Part.setPartInvoiceId(partInvoice.getPartInvoiceId());
										Part.setSerialNoAddedForParts(0);
										
										java.util.Date date = sdf1.parse(purchaseOrders.getPurchaseorder_invoice_date());
										java.sql.Date sqlInvoiceDate = new java.sql.Date(date.getTime());
										Part.setInvoice_date(sqlInvoiceDate);
										Part.setMainQuantity(originalQuantity);
										Part.setMainUnitprice(originalUnitPrice);
										Part.setUnitprice(partEachCost);
										Part.setHistory_quantity(noOfParts);
										Part.setQuantity(noOfParts);
										Part.setPurchaseorderto_partid(purchaseOrdersToParts.getPurchaseorderto_partid());
										
										// save part Manufacturers Service in Master
										// part
										try {
											// first validate part_id is there are
											// not
											List<InventoryAll> validate = InventoryService.Validate_InventoryAll(
													Master.getPartid(), userDetails.getCompany_id());
											if (validate != null && !validate.isEmpty()) {

												Long Inventory_all_id = (long) 0;
												Long Inventory_Location_id = (long) 0;
												
												for (InventoryAll updateInventory : validate) {
													// get part and AllQuantity in
													// all_Ready
													// in db
													// Add New Quantity

													Inventory_all_id = updateInventory.getInventory_all_id();

													break;
												}
												List<InventoryLocation> validate_Location = InventoryService
														.Validate_Inventory_Location(Master.getPartid(),
																Purchase.getPurchaseorder_shiplocation_id());
												if (validate_Location != null && !validate_Location.isEmpty()) {
													for (InventoryLocation validateLocation : validate_Location) {
														// get part and Location
														// Quantity in
														// all_Ready in db Add New
														// Quantity

														Inventory_Location_id = validateLocation
																.getInventory_location_id();
														break;
													}
												} else {

													// get part and Location
													// Quantity
													// Search
													// that
													// location part not in db
													// Create
													// new
													InventoryLocation.setLocation_quantity(
															noOfParts);
													InventoryAll NewAll = new InventoryAll();
													NewAll.setInventory_all_id(Inventory_all_id);
													InventoryLocation.setInventoryall(NewAll);
													InventoryService.add_InventoryLocation(InventoryLocation);

													Inventory_Location_id = InventoryLocation
															.getInventory_location_id();

												}

												// Quantity all ready save to update
												// in
												// all
												// ,
												// Location Quantity details

												Part.setInventory_all_id(Inventory_all_id);
												Part.setInventory_location_id(Inventory_Location_id);
												Part.setPartInvoiceId(partInvoice.getPartInvoiceId());
												Part.setSerialNoAddedForParts(0);
												InventoryService.save_Inventory(Part);

												// Insert Inventory QUANTITY Details
												// to Update
												// LOCATION AND ALL LOCATION
												// QUANTITY DETAILS

												InventoryService
														.update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(
																Master.getPartid(),
																Purchase.getPurchaseorder_shiplocation_id());

												InventoryService
														.update_InventoryAll_PARTID_PARTNAME_GENRAL(Master.getPartid());

											} else {

												// get part and AllQuantity save
												Inventoryall.setAll_quantity(noOfParts);
												InventoryService.add_InventoryAll(Inventoryall);

												// get part and Location Quantity
												// save
												InventoryLocation.setLocation_quantity(noOfParts);
												InventoryLocation.setInventoryall(Inventoryall);
												InventoryService.add_InventoryLocation(InventoryLocation);

												Part.setInventory_all_id(Inventoryall.getInventory_all_id());
												Part.setInventory_location_id(
														InventoryLocation.getInventory_location_id());
												Part.setSerialNoAddedForParts(0);
												InventoryService.save_Inventory(Part);
											}
											model.put("saveInventory", true);

										} catch (Exception e) {
											e.printStackTrace();
											return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
										}

									} catch (Exception e) {
										e.printStackTrace();
										return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
									}

								}

								// below code create Debit Note function
								if (purchaseOrdersToParts.getNotreceived_quantity() != 0.0
										&& purchaseOrdersToParts.getNotreceived_quantity() != null && purchaseOrdersToParts.getApprovalPartStatusId() == PurchaseOrderState.APPROVED) {
									// debitNoteSequenceService.findNextDebitNoteNumber(userDetails.getCompany_id());
									try {

										//PurchaseOrdersService.deleteDebitNoteByPOId(purchaseOrders.getPurchaseorder_id(), purchaseOrders.getCompanyId());
										// get PurchaseOrderToPart To
										// SavePurchaseOrderToDebitNote Save
										PurchaseOrdersToDebitNote DebitNode = POBL
												.preparePurchaseOrdersToParts_To_PurchaseOrdersToDebitNote(
														purchaseOrdersToParts);
										DebitNode.setCompanyId(userDetails.getCompany_id());
										// DebitNode.setDebitNoteNumber(sequenceCounter.getNextVal());
										PurchaseOrdersService.addPurchaseOrdersToDebitNote(DebitNode);
										// debitNoteSequenceService.updateNextDebitNoteNumber(sequenceCounter.getNextVal()
										// + 1, sequenceCounter.getSequence_Id());
										// Update Main PurchaseOrder Total cost
										/*PurchaseOrdersDto Purchase_Debit = POBL.getPurchaseOrders(PurchaseOrdersService
												.getPurchaseOrders(purchaseOrders.getPurchaseorder_id()));
									*/	
										HashMap<String, Object>  threadHM2	=  PurchaseOrdersService.getPurchaseOrderDetailsHM(purchaseOrders.getPurchaseorder_id(), userDetails);
										
										@SuppressWarnings("unchecked")
										PurchaseOrdersDto Purchase_Debit = POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM2.get("purchaseOrders"), (HashMap<Long, User>) threadHM2.get("userMap"));
										

										Double TotalPurchaseOrder_DebitNotecost = 0.0;

										if (Purchase_Debit != null) {
											if (Purchase_Debit.getPurchaseorder_total_debitnote_cost() != null) {

												TotalPurchaseOrder_DebitNotecost = Purchase_Debit
														.getPurchaseorder_total_debitnote_cost();
											}
											
											TotalPurchaseOrder_DebitNotecost += DebitNode.getTotal_return_cost();
											

											PurchaseOrdersService.updatePurchaseOrder_Total_DebitCost(
													TotalPurchaseOrder_DebitNotecost,
													purchaseOrders.getPurchaseorder_id());
										}

									} catch (Exception e) {
										e.printStackTrace();
										return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
									}
								}
							}
						}

						model.put("SaveInventory", true);

					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
					}
				}
			} else {
				model.put("NoAuthen", true);
				return new ModelAndView(
						"redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrders.getPurchaseorder_id() + "", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrders.getPurchaseorder_id() + "",
				model);
	}

	/*
	 * This ReceivedPurchaseOrder Controller is Save to Part Inventory In Reveiced
	 * Part all to Save Inventory
	 */
	@RequestMapping(value = "/UpdateInvoicePO", method = RequestMethod.POST)
	public ModelAndView UpdateInvoicePO(@ModelAttribute("command") PurchaseOrdersDto purchaseOrders,
			final HttpServletRequest request, RedirectAttributes redir) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<PartLocationPermissionDto> PartLocPermission = PLBL.prepareListofPartLocation(partLocationPermissionService
					.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

			/*PurchaseOrdersDto Purchase = POBL
					.getPurchaseOrders(PurchaseOrdersService.getPurchaseOrders(purchaseOrders.getPurchaseorder_id()));
*/
			
			HashMap<String, Object>  threadHM	=  PurchaseOrdersService.getPurchaseOrderDetailsHM(purchaseOrders.getPurchaseorder_id(), userDetails);
			
			@SuppressWarnings("unchecked")
			PurchaseOrdersDto Purchase = POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
			
			
			// check user branch location and workOrder location if same
			if (InvenBL.isFilled_GET_Permission(Purchase.getPurchaseorder_shiplocation_id(), PartLocPermission)) {

				List<PurchaseOrders> PurchaseInvoiceValidate = PurchaseOrdersService
						.Validate_PurchaseOrder_Received_Invoice(Purchase.getPurchaseorder_vendor_id(),
								purchaseOrders.getPurchaseorder_invoiceno());
				if (PurchaseInvoiceValidate != null && !PurchaseInvoiceValidate.isEmpty()) {

					String OldID = "";
					for (PurchaseOrders OldPo : PurchaseInvoiceValidate) {
						OldID += "<a href=\"PurchaseOrders_Parts.in?ID=" + OldPo.getPurchaseorder_id()
								+ "\" target=\"_blank\">PO-" + OldPo.getPurchaseorder_Number()
								+ "  <i class=\"fa fa-external-link\"></i></a> ,";
					}
					redir.addFlashAttribute("alreadyInvoice", OldID);
					return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID="
							+ purchaseOrders.getPurchaseorder_id() + "&Invoice=true", model);
				} else {

					PurchaseOrders ReceivedPurchase_BY = POBL.GetReceivedPurchaseOrderUpdate(purchaseOrders);

					// update PurchaseOrder to Received Done & stutes
					PurchaseOrdersService.updatePurchaseOrderedToReceived_Quantity(ReceivedPurchase_BY);

				}
			} else {
				model.put("NoAuthen", true);
				return new ModelAndView(
						"redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrders.getPurchaseorder_id() + "", model);
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("newInventory Page:", e);
			e.printStackTrace();
		} finally {
			userDetails = null;
		}

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrders.getPurchaseorder_id() + "",
				model);
	}
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/PurchaseOrders_PartsReEnter")
	public ModelAndView PurchaseOrders_PartsReEnter(@RequestParam("ID") final Long Purchaseorder_id,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> 			model 				= new HashMap<String, Object>();
		CustomUserDetails 				userDetails 		= null;
		List<PartLocations> 			partLocationsList 	= null;
		List<PartLocationPermissionDto> partLocPermissionBL = null;
		HashMap<String, Object>  		threadHM			= null;
		PurchaseOrdersDto 				purchase 			= null;
		PurchaseOrders 					purchaseComplete 	= null;
		HashMap<String, Object>  		configuration		= null;
		try {
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			partLocationsList 		= partLocationPermissionService.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id());
			partLocPermissionBL		= PLBL.prepareListofPartLocation(partLocationsList);
			
			threadHM				= PurchaseOrdersService.getPurchaseOrderDetailsHM(Purchaseorder_id, userDetails);
			purchase 				= POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
			
	
			// check user branch location and workOrder location if same
			if (InvenBL.isFilled_GET_Permission(purchase.getPurchaseorder_shiplocation_id(), partLocPermissionBL)) {
				purchaseComplete = POBL.Update_PurchaseOrder_ReEnter(purchase);
				purchaseOrdersToPartsService.purchaseOrdersUpdateFromReceivedToOrderedStatus(Purchaseorder_id, userDetails.getCompany_id());
				PurchaseOrdersService.updatePurchaseOrder_RE_Enter(purchaseComplete);
				
				if((boolean) configuration.getOrDefault("poApprovalRequired", false))
					PurchaseOrdersService.updatePOApprovalStatus(PurchaseOrderType.PURCHASE_STATUS_NOT_APPROVED, Purchaseorder_id);
	
			} else {
				model.put("NoAuthen", true);
				return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + Purchaseorder_id + "", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + Purchaseorder_id + "", model);
	}

	/*
	 * This ReceivedPurchaseOrderTyre Controller is Save to Part Inventory In
	 * Reveiced Part all to Save Inventory
	 */
	@RequestMapping(value = "/ReceivedPurchaseOrderTyre", method = RequestMethod.POST)
	public ModelAndView ReceivedPurchaseOrderTyre(@ModelAttribute("command") PurchaseOrdersDto purchaseOrders,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		InventorySequenceCounter		sequenceCounter	= null;
		PurchaseOrders getPurchase = (PurchaseOrdersService.getPurchaseOrders(purchaseOrders.getPurchaseorder_id()));
		if(getPurchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED) {
			model.put("alreadyReceived", true);
			return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + getPurchase.getPurchaseorder_id() + "", model);
		}
		
		PurchaseOrders ReceivedPurchase_BY = POBL.GetReceivedPurchaseOrderUpdate(purchaseOrders);

		// update PurchaseOrder to Received Done & stutes
		PurchaseOrdersService.updatePurchaseOrderedToReceived_Quantity(ReceivedPurchase_BY);

		// Update PurchaseOrder Received to order_by Add Inventory in Received
		// Parts
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			PurchaseOrders Purchase = (PurchaseOrdersService.getPurchaseOrders(purchaseOrders.getPurchaseorder_id()));

				
			
			/**
			 * This insert to invoice in PurchaseOrders to Tyre Invoice Amount
			 */
			InventoryTyreInvoice TyreInvoice = POBL.prepare_PurchaseOrder_TyreInvoice(Purchase);
			sequenceCounter	= inventorySequenceService.findNextInventoryNumber(userDetails.getCompany_id(), SequenceTypeContant.SEQUENCE_TYPE_TYRE_INVENTORY);
			
			TyreInvoice.setITYRE_NUMBER(sequenceCounter.getNextVal());
			
			iventoryTyreService.add_Tyre_Inventory_Invoice(TyreInvoice);

			List<PurchaseOrdersToTyre> purchaseToTyre = PurchaseOrdersService
					.getPurchaseOrdersTasksToTyre(Purchase.getPurchaseorder_id(), TyreInvoice.getCOMPANY_ID());

			List<PurchaseOrdersToPartsDto> purchaseToParts = PurchaseOrdersService
					.getPurchaseOrdersTasksToParts(purchaseOrders.getPurchaseorder_id(), userDetails.getCompany_id());
			if (purchaseToParts != null && !purchaseToParts.isEmpty()) {
				for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToParts) {
					if(purchaseOrdersToParts.getApprovalPartStatusId() == PurchaseOrderState.APPROVED) {
					if (purchaseOrdersToParts.getReceived_quantity() != 0.0
							|| purchaseOrdersToParts.getReceived_quantity() != null) {

						// Create purchaseOrdersToParts To Inventory

						try {
							InventoryTyreAmount TyReAm = POBL.prepare_PurchaseOrder_TyreAmount(purchaseOrdersToParts,
									Purchase);
							TyReAm.setCOMPANY_ID(userDetails.getCompany_id());
							TyReAm.setInventoryTyreInvoice(TyreInvoice);
							iventoryTyreService.add_Tyre_Inventory_Amount(TyReAm);

							// Save PurchaseOrdersToTyre To inventory Tyre
							// Details
							if (purchaseToTyre != null && !purchaseToTyre.isEmpty()) {
								for (PurchaseOrdersToTyre poToTyre : purchaseToTyre) {

									if (poToTyre.getTYRE_PART_ID()
											.equals(purchaseOrdersToParts.getPurchaseorderto_partid())) {

										InventoryTyre Tyre = POBL.prepare_TyreAmount_TO_Tyre(TyReAm);

										Tyre.setTYRE_NUMBER(poToTyre.getTYRE_SERIAL_NUMBER());

										Tyre.setTYRE_ASSIGN_STATUS_ID(InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE);

										InventoryTyre validate = iventoryTyreService.Validate_InventoryTyre(
												poToTyre.getTYRE_SERIAL_NUMBER(), userDetails.getCompany_id());
										if (validate == null) {
											iventoryTyreService.save_Inventory_TYRE(Tyre);

										}

									} // save inventoryTyre If statement
								} // close For loop condition
							} // close checking empty or not if condition

						} catch (Exception e) {
							e.printStackTrace();
							return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
						}

					}

					// below code create Debit Note function
					if (purchaseOrdersToParts.getNotreceived_quantity() != 0.0
							&& purchaseOrdersToParts.getNotreceived_quantity() != null) {
						// DebitNoteSequenceCounter sequenceCounter =
						// debitNoteSequenceService.findNextDebitNoteNumber(userDetails.getCompany_id());
						try {

							// get PurchaseOrderToPart To
							// SavePurchaseOrderToDebitNote Save
							
							//PurchaseOrdersService.deleteDebitNoteByPOId(purchaseOrders.getPurchaseorder_id(), purchaseOrders.getCompanyId());
							
							PurchaseOrdersToDebitNote DebitNode = POBL
									.preparePurchaseOrdersToParts_To_PurchaseOrdersToDebitNote_Tyre(
											purchaseOrdersToParts);
							DebitNode.setCompanyId(userDetails.getCompany_id());
							// DebitNode.setDebitNoteNumber(sequenceCounter.getNextVal());
							PurchaseOrdersService.addPurchaseOrdersToDebitNote(DebitNode);
							// debitNoteSequenceService.updateNextDebitNoteNumber(sequenceCounter.getNextVal()
							// + 1, sequenceCounter.getSequence_Id());

							// Update Main PurchaseOrder Total cost
							/*PurchaseOrdersDto Purchase_Debit = POBL.getPurchaseOrders(
									PurchaseOrdersService.getPurchaseOrders(purchaseOrders.getPurchaseorder_id()));
*/
							HashMap<String, Object>  threadHM	=  PurchaseOrdersService.getPurchaseOrderDetailsHM(purchaseOrders.getPurchaseorder_id(), userDetails);
							
							@SuppressWarnings("unchecked")
							PurchaseOrdersDto Purchase_Debit = POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
							
							
							Double TotalPurchaseOrder_DebitNotecost = 0.0;

							if (Purchase_Debit != null) {
								if (Purchase_Debit.getPurchaseorder_total_debitnote_cost() != null) {

									TotalPurchaseOrder_DebitNotecost = Purchase_Debit
											.getPurchaseorder_total_debitnote_cost();
								}

								TotalPurchaseOrder_DebitNotecost += DebitNode.getTotal_return_cost();
								
								
								PurchaseOrdersService.updatePurchaseOrder_Total_DebitCost(
										TotalPurchaseOrder_DebitNotecost, purchaseOrders.getPurchaseorder_id());
							}

						} catch (Exception e) {
							e.printStackTrace();
							return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
						} finally {
						}
					}
					}
				}
			}

			model.put("SaveInventory", true);

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrders.getPurchaseorder_id() + "",
				model);
	}
	
	@RequestMapping(value = "/ReceivedPurchaseOrderBattery", method = RequestMethod.POST)
	public ModelAndView ReceivedPurchaseOrderBattery(@ModelAttribute("command") PurchaseOrdersDto purchaseOrders, final HttpServletRequest request) throws Exception {

		Map<String, Object> 				model 					= null;
		BatteryInvoiceSequenceCounter		sequenceCounter			= null;
		PurchaseOrders 						getPurchase 			= null; 
		int 								compId 					= 0;
		PurchaseOrders 						ReceivedPurchase_BY 	= null;
		CustomUserDetails 					userDetails 			= null;
		BatteryInvoice						batteryInvoice			= null;
		PurchaseOrders 						Purchase 				= null;
		List<PurchaseOrderToBattery> 		purchaseToTyre 			= null;
		List<PurchaseOrdersToPartsDto>	 	purchaseToParts 		= null;
		try {
			model = new HashMap<String, Object>();
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			compId 		= userDetails.getCompany_id();
			getPurchase = (PurchaseOrdersService.getPurchaseOrders(purchaseOrders.getPurchaseorder_id()));
			
			if(getPurchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED) {
				model.put("alreadyReceived", true);
				return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrders.getPurchaseorder_id() + "", model);
			}
			
			ReceivedPurchase_BY = POBL.GetReceivedPurchaseOrderUpdate(purchaseOrders);
			PurchaseOrdersService.updatePurchaseOrderedToReceived_Quantity(ReceivedPurchase_BY);
			
			// Update PurchaseOrder Received to order_by Add Inventory in Received

			Purchase 		= (PurchaseOrdersService.getPurchaseOrders(purchaseOrders.getPurchaseorder_id()));
			batteryInvoice	= POBL.prepare_PurchaseOrder_BatteryInvoice(Purchase);
			sequenceCounter	= batteryInvoiceSequenceService.findNextInvoiceNumber(userDetails.getCompany_id());
			
			batteryInvoice.setBatteryInvoiceNumber(sequenceCounter.getNextVal());
			
			batteryInvoiceRepository.save(batteryInvoice);

			purchaseToTyre 	= PurchaseOrdersService.getPurchaseOrdersTasksToBattery(Purchase.getPurchaseorder_id(), batteryInvoice.getCompanyId());
			purchaseToParts = PurchaseOrdersService.getPurchaseOrdersToPartsforBattery(purchaseOrders.getPurchaseorder_id(), userDetails.getCompany_id());
			
			
			if (purchaseToParts != null && !purchaseToParts.isEmpty()) {
				for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToParts) {
					if(purchaseOrdersToParts.getApprovalPartStatusId() == PurchaseOrderState.APPROVED) {
					if (purchaseOrdersToParts.getReceived_quantity() != 0.0 || purchaseOrdersToParts.getReceived_quantity() != null) {
						try {
							BatteryAmount TyReAm = POBL.prepare_PurchaseOrder_BatteryAmount(purchaseOrdersToParts, Purchase);
							
							TyReAm.setBatteryInvoiceId(batteryInvoice.getBatteryInvoiceId());
							batteryAmountRepository.save(TyReAm);

							// Save PurchaseOrdersToTyre To inventory Tyre
							// Details
							if (purchaseToTyre != null && !purchaseToTyre.isEmpty()) {
								for (PurchaseOrderToBattery poToTyre : purchaseToTyre) {

									if (poToTyre.getPurchaseOrderToPartId()
											.equals(purchaseOrdersToParts.getPurchaseorderto_partid())) {
									Battery	battery	=	 POBL.prepare_BatteryAmount_TO_Battery(TyReAm);
										
									battery.setBatterySerialNumber(poToTyre.getBatterySerialNumber());
									battery.setBatteryStatusId(BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE);
									battery.setBatteryUsesStatusId(BatteryConstant.BATTERY_USES_STATUS_NEW);

									List<Battery> validate = batteryService.validateBatterySerialNumber(poToTyre.getBatterySerialNumber(), battery.getCompanyId());
										
										if (validate == null || validate.isEmpty()) {
											batteryService.saveBattery(battery);

										}

									} // save inventoryTyre If statement
								} // close For loop condition
							} // close checking empty or not if condition

						} catch (Exception e) {
							e.printStackTrace();
							return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
						}

					}

					// below code create Debit Note function
					if (purchaseOrdersToParts.getNotreceived_quantity() != 0.0
							&& purchaseOrdersToParts.getNotreceived_quantity() != null) {
						// DebitNoteSequenceCounter sequenceCounter =
						// debitNoteSequenceService.findNextDebitNoteNumber(userDetails.getCompany_id());
						try {

							// get PurchaseOrderToPart To
							// SavePurchaseOrderToDebitNote Save
							
							//PurchaseOrdersService.deleteDebitNoteByPOId(purchaseOrders.getPurchaseorder_id(), purchaseOrders.getCompanyId());
							
							PurchaseOrdersToDebitNote DebitNode = POBL
									.preparePurchaseOrdersToParts_To_PurchaseOrdersToDebitNote_Tyre(
											purchaseOrdersToParts);
							DebitNode.setCompanyId(compId);
							// DebitNode.setDebitNoteNumber(sequenceCounter.getNextVal());
							PurchaseOrdersService.addPurchaseOrdersToDebitNote(DebitNode);
							// debitNoteSequenceService.updateNextDebitNoteNumber(sequenceCounter.getNextVal()
							// + 1, sequenceCounter.getSequence_Id());

							// Update Main PurchaseOrder Total cost
							/*PurchaseOrdersDto Purchase_Debit = POBL.getPurchaseOrders(
									PurchaseOrdersService.getPurchaseOrders(purchaseOrders.getPurchaseorder_id()));
*/
							HashMap<String, Object>  threadHM	=  PurchaseOrdersService.getPurchaseOrderDetailsHM(purchaseOrders.getPurchaseorder_id(), userDetails);
							
							@SuppressWarnings("unchecked")
							PurchaseOrdersDto Purchase_Debit = POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
							
							
							Double TotalPurchaseOrder_DebitNotecost = 0.0;

							if (Purchase_Debit != null) {
								if (Purchase_Debit.getPurchaseorder_total_debitnote_cost() != null) {

									TotalPurchaseOrder_DebitNotecost = Purchase_Debit
											.getPurchaseorder_total_debitnote_cost();
								}

								TotalPurchaseOrder_DebitNotecost += DebitNode.getTotal_return_cost();
								
								
								PurchaseOrdersService.updatePurchaseOrder_Total_DebitCost(
										TotalPurchaseOrder_DebitNotecost, purchaseOrders.getPurchaseorder_id());
							}

						} catch (Exception e) {
							e.printStackTrace();
							return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
						} finally {
							//userDetails = null;
						}
					}
					}
				}
			}

			model.put("SaveInventory", true);

			return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrders.getPurchaseorder_id() + "",
					model);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}
	
	@RequestMapping(value = "/ReceivedPurchaseOrderCloth", method = RequestMethod.POST)
	public ModelAndView ReceivedPurchaseOrderCloth(@ModelAttribute("command") PurchaseOrdersDto purchaseOrders,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		
		PurchaseOrders Purchase = (PurchaseOrdersService.getPurchaseOrders(purchaseOrders.getPurchaseorder_id()));
		if(Purchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED) {
			model.put("alreadyReceived", true);
			return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrders.getPurchaseorder_id() + "", model);
		}
		ClothInvoiceSequenceCounter		sequenceCounter	= null;
		PurchaseOrders ReceivedPurchase_BY = POBL.GetReceivedPurchaseOrderUpdate(purchaseOrders);

		// update PurchaseOrder to Received Done & stutes
		PurchaseOrdersService.updatePurchaseOrderedToReceived_Quantity(ReceivedPurchase_BY);

		// Update PurchaseOrder Received to order_by Add Inventory in Received
		// Parts
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			

				
			
			
		//	InventoryTyreInvoice TyreInvoice = POBL.prepare_PurchaseOrder_TyreInvoice(Purchase);
			ClothInvoice		clothInvoice	= POBL.prepare_PurchaseOrder_ClothInvoice(Purchase);
			sequenceCounter	= clothInvoiceSequenceService.findNextInvoiceNumber(userDetails.getCompany_id());
			
			clothInvoice.setClothInvoiceNumber(sequenceCounter.getNextVal());
			
			clothInventoryRepository.save(clothInvoice);

			/*List<PurchaseOrderToBattery> purchaseToTyre = PurchaseOrdersService
					.getPurchaseOrdersTasksToBattery(Purchase.getPurchaseorder_id(), clothInvoice.getCompanyId());*/

			List<PurchaseOrdersToPartsDto> purchaseToParts = PurchaseOrdersService
					.getPurchaseOrdersTasksToCloth(purchaseOrders.getPurchaseorder_id(), userDetails.getCompany_id());
			if (purchaseToParts != null && !purchaseToParts.isEmpty()) {
				for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToParts) {
					if(purchaseOrdersToParts.getApprovalPartStatusId() == PurchaseOrderState.APPROVED) {
					if (purchaseOrdersToParts.getReceived_quantity() != 0.0
							|| purchaseOrdersToParts.getReceived_quantity() != null) {

						// Create purchaseOrdersToParts To Inventory

						try {
							ClothInventoryDetails inventoryDetails = POBL.getClothInventoryDetails(purchaseOrdersToParts,
									Purchase);
							
							inventoryDetails.setClothInvoiceId(clothInvoice.getClothInvoiceId());
							
							clothInventoryDetailsRepository.save(inventoryDetails);

							// Save PurchaseOrdersToTyre To inventory Tyre
							// Details
							if (inventoryDetails != null ) {
								
								ClothInventoryStockTypeDetails	validate =		clothInventoryStockTypeDetailsRepository.validateClothInventoryStockTypeDetails(inventoryDetails.getClothTypesId(), inventoryDetails.getWareHouseLocation());
								
								ClothInventoryStockTypeDetails	typeDetails	= new ClothInventoryStockTypeDetails();
								typeDetails.setClothTypesId(inventoryDetails.getClothTypesId());
								typeDetails.setWareHouseLocationId(inventoryDetails.getWareHouseLocation());
								typeDetails.setCompanyId(userDetails.getCompany_id());
								if(validate == null) {
									if(clothInvoice.getClothTypeId() == ClothInvoiceStockType.STOCK_TYPE_NEW) {
										typeDetails.setUsedStockQuantity(0.0);
										typeDetails.setNewStockQuantity(inventoryDetails.getQuantity());
									}else {
										typeDetails.setUsedStockQuantity(inventoryDetails.getQuantity());
										typeDetails.setNewStockQuantity(0.0);
									
									}
									typeDetails.setInServiceQuantity(0.0);
									typeDetails.setInWashingQuantity(0.0);
									
								}else {
									if(clothInvoice.getClothTypeId() == ClothInvoiceStockType.STOCK_TYPE_NEW) {
										typeDetails.setUsedStockQuantity(validate.getUsedStockQuantity());
										typeDetails.setNewStockQuantity(inventoryDetails.getQuantity() + validate.getNewStockQuantity());
									}else {
										if(validate.getUsedStockQuantity() == null) {
											validate.setUsedStockQuantity((double)0);
										}
										typeDetails.setUsedStockQuantity(validate.getUsedStockQuantity() + inventoryDetails.getQuantity());
										typeDetails.setNewStockQuantity(validate.getNewStockQuantity());
									}
									typeDetails.setInServiceQuantity(validate.getInServiceQuantity());
									typeDetails.setInWashingQuantity(validate.getInWashingQuantity());
									typeDetails.setClothInventoryStockTypeDetailsId(validate.getClothInventoryStockTypeDetailsId());
								}
								
								clothInventoryStockTypeDetailsRepository.save(typeDetails);
							}

						} catch (Exception e) {
							e.printStackTrace();
							return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
						}

					}

					// below code create Debit Note function
					if (purchaseOrdersToParts.getNotreceived_quantity() != 0.0
							&& purchaseOrdersToParts.getNotreceived_quantity() != null) {
						// DebitNoteSequenceCounter sequenceCounter =
						// debitNoteSequenceService.findNextDebitNoteNumber(userDetails.getCompany_id());
						try {

							
							//PurchaseOrdersService.deleteDebitNoteByPOId(purchaseOrders.getPurchaseorder_id(), purchaseOrdersToParts.getCompanyId());
							
							// get PurchaseOrderToPart To
							// SavePurchaseOrderToDebitNote Save
							PurchaseOrdersToDebitNote DebitNode = POBL
									.preparePurchaseOrdersToParts_To_PurchaseOrdersToDebitNote_Cloth(
											purchaseOrdersToParts);
							DebitNode.setCompanyId(userDetails.getCompany_id());
							// DebitNode.setDebitNoteNumber(sequenceCounter.getNextVal());
							PurchaseOrdersService.addPurchaseOrdersToDebitNote(DebitNode);
							// debitNoteSequenceService.updateNextDebitNoteNumber(sequenceCounter.getNextVal()
							// + 1, sequenceCounter.getSequence_Id());

							// Update Main PurchaseOrder Total cost
							/*PurchaseOrdersDto Purchase_Debit = POBL.getPurchaseOrders(
									PurchaseOrdersService.getPurchaseOrders(purchaseOrders.getPurchaseorder_id()));
*/
							HashMap<String, Object>  threadHM	=  PurchaseOrdersService.getPurchaseOrderDetailsHM(purchaseOrders.getPurchaseorder_id(), userDetails);
							
							@SuppressWarnings("unchecked")
							PurchaseOrdersDto Purchase_Debit = POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
							
							
							Double TotalPurchaseOrder_DebitNotecost = 0.0;

							if (Purchase_Debit != null) {
								if (Purchase_Debit.getPurchaseorder_total_debitnote_cost() != null) {

									TotalPurchaseOrder_DebitNotecost = Purchase_Debit
											.getPurchaseorder_total_debitnote_cost();
								}

								TotalPurchaseOrder_DebitNotecost += DebitNode.getTotal_return_cost();
								
								
								PurchaseOrdersService.updatePurchaseOrder_Total_DebitCost(
										TotalPurchaseOrder_DebitNotecost, purchaseOrders.getPurchaseorder_id());
							}

						} catch (Exception e) {
							e.printStackTrace();
							return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
						} finally {
							//userDetails = null;
						}
					}
					}
				}
			}

			model.put("SaveInventory", true);

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrders.getPurchaseorder_id() + "",
				model);
	}

	// Save PurchaseOrders Tax Cost
	@RequestMapping(value = "/PurchaseOrdersUpdate_freight", method = RequestMethod.POST)
	public ModelAndView PurchaseOrdersUpdate_freight(@ModelAttribute("command") PurchaseOrders purchaseOrders,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			// Update Main PurchaseOrder Advance and Balance And Statues IS
			// ORDERED Change
			try {
				PurchaseOrders OLD_Purchase = PurchaseOrdersService
						.getPurchaseOrders(purchaseOrders.getPurchaseorder_id());

				PurchaseOrders Purchase_Freight = POBL.UPdate_Purchase_Freight_Cost(purchaseOrders, OLD_Purchase);

				PurchaseOrdersService.updatePurchaseOrder_Freight(Purchase_Freight);

			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrders.getPurchaseorder_id() + "",
				model);
	}

	// Save PurchaseOrders Tax Cost
	@RequestMapping(value = "/PurchaseOrdersUpdate_Taxcost", method = RequestMethod.POST)
	public ModelAndView PurchaseOrdersUpdate_tax_cost(@ModelAttribute("command") PurchaseOrders purchaseOrders,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			// Update Main PurchaseOrder Advance and Balance And Statues IS
			// ORDERED Change
			try {
				PurchaseOrders OLD_Purchase = PurchaseOrdersService
						.getPurchaseOrders(purchaseOrders.getPurchaseorder_id());

				PurchaseOrders Purchase_TaxCost = POBL.Update_Purchase_TAX_Cost(purchaseOrders, OLD_Purchase);

				PurchaseOrdersService.updatePurchaseOrder_TaxCost(Purchase_TaxCost);

			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrders.getPurchaseorder_id() + "",
				model);
	}

	// Delete to PurchaseOrders Task_To_Parts
	@GetMapping(value = "/deletePurchaseOrderToPart")
	public ModelAndView deletePurchaseOrderToPart(@ModelAttribute("command") PurchaseOrdersToParts PurchaseOrdersToPart,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> 			model 						= new HashMap<String, Object>();
		Long 							purchaseOrderId 			= null;
		HashMap<String, Object> 		configuration	        	= null;
		boolean					 		totalGstCostConfig	      	= false;
		double							gstCost						= 0;
		double							poGstCost					= 0;
		double							poGstTotalCost				= 0;
		try {
			ValueObject		object	= new  ValueObject();
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			totalGstCostConfig	= (boolean) configuration.getOrDefault(PurchaseOrdersConfigurationConstant.TOTAL_GST_COST, false);
			PurchaseOrdersToParts purchaseOrdersToParts = PurchaseOrdersService.getPurchaseOrdersToParts(PurchaseOrdersToPart.getPurchaseorderto_partid());
			PurchaseOrders getPurchaseOrders	= PurchaseOrdersService.getPurchaseOrders(purchaseOrdersToParts.getPurchaseorders().getPurchaseorder_id());

			PurchaseOrdersService.deletePurchaseOrdersTOParts(PurchaseOrdersToPart.getPurchaseorderto_partid(),userDetails.getCompany_id());
			purchasePartForVehicleService.deletePurchasePartForVehicleByPurchaseOrderToPartId(PurchaseOrdersToPart.getPurchaseorderto_partid(),purchaseOrdersToParts.getPurchaseorders().getPurchaseorder_id());
			// Update Main PurchaseOrder Advance and Balance And Statues IS
			// ORDERED Change
			try { 

				PurchaseOrdersService.updatePurchaseOrderMainTotalCost(purchaseOrdersToParts.getPurchaseorders().getPurchaseorder_id());

				if(getPurchaseOrders != null && totalGstCostConfig) {
					gstCost			= (purchaseOrdersToParts.getQuantity() * purchaseOrdersToParts.getParteachcost())* (purchaseOrdersToParts.getTax() / 100);
					poGstCost		= getPurchaseOrders.getPurchaseorder_totaltax_cost();
					poGstTotalCost	= poGstCost - gstCost ;
					object.put("gstCost", poGstTotalCost);
					object.put("purchaseOrderId",purchaseOrdersToParts.getPurchaseorders().getPurchaseorder_id());
					object.put("companyId", userDetails.getCompany_id());
					
					PurchaseOrdersService.updatePurchaseOrderVariousTotalCost(object);
				}
					
				purchaseOrderId = purchaseOrdersToParts.getPurchaseorders().getPurchaseorder_id();

			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + purchaseOrderId + "", model);
	}

	@RequestMapping(value = "/uploadPurchaseOrderDocument", method = RequestMethod.POST)
	public ModelAndView uploadPurchaseOrderDocument(
			@ModelAttribute("command") PurchaseOrdersDocument PurchaseordersDocument,
			@RequestParam("input-file-preview") MultipartFile file) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			org.fleetopgroup.persistence.document.PurchaseOrdersDocument PurchaseDocument = new org.fleetopgroup.persistence.document.PurchaseOrdersDocument();
			
			PurchaseDocument.setPurchaseorder_id(PurchaseordersDocument.getPurchaseorder_id());
			PurchaseDocument.setPurchaseorder_document(PurchaseordersDocument.getPurchaseorder_document());
			PurchaseDocument.setCompanyId(userDetails.getCompany_id());
			PurchaseDocument.setCreatedById(userDetails.getId());
			PurchaseDocument.setLastModifiedById(userDetails.getId());

			if (!file.isEmpty()) {
				try {

					byte[] bytes = file.getBytes();

					PurchaseDocument.setPurchaseorder_filename(file.getOriginalFilename());
					PurchaseDocument.setPurchaseorder_content(bytes);
					PurchaseDocument.setPurchaseorder_contentType(file.getContentType());

					java.util.Date currentDateUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

					PurchaseDocument.setCreated(toDate);
					PurchaseDocument.setLastupdated(toDate);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
			}

			// check in PurchaseOrdersDocument this PurchaseOrders_id this there
			// or not
			org.fleetopgroup.persistence.document.PurchaseOrdersDocument doc = PurchaseOrdersService.ValidatePurchaseOrdersDocument(
					PurchaseordersDocument.getPurchaseorder_id(), PurchaseordersDocument.getPurchaseorder_document());
			if (doc != null) {
				// Note: there mean Update same ID in PurchaseOrdersDocument
				// Table
				PurchaseDocument.set_id(doc.get_id());
				PurchaseOrdersService.updateOldPurchaseOrdersDocument(PurchaseDocument);

				// Note: This Purchase Order Document Available Update in PO
				PurchaseOrdersService.Update_PurchaseOrders_To_Document_True(
						PurchaseDocument.get_id(), true,
						PurchaseordersDocument.getPurchaseorder_id(), userDetails.getCompany_id());
			} else {
				// Note: Not there mean Save PurchaseOrdersDocument Table
				PurchaseOrdersService.uploadPurchaseOrdersDocument(PurchaseDocument);

				// Note: This Purchase Order Document Available Update in PO
				PurchaseOrdersService.Update_PurchaseOrders_To_Document_True(
						PurchaseDocument.get_id(), true,
						PurchaseordersDocument.getPurchaseorder_id(), userDetails.getCompany_id());
			}

			model.put("UploadSuccess", true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		return new ModelAndView(
				"redirect:/PurchaseOrders_Parts.in?ID=" + PurchaseordersDocument.getPurchaseorder_id() + "", model);

	}

	// Should be Download PurchaseOrder Document
	@RequestMapping("/download/PurchaseorderDocument/{document}/{Purchaseorder_id}")
	public String downloadReminder(@PathVariable("document") String document,
			@PathVariable("Purchaseorder_id") Long Purchaseorder_id, HttpServletResponse response) throws Exception {
		try {
			
			if (Purchaseorder_id == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				org.fleetopgroup.persistence.document.PurchaseOrdersDocument file = PurchaseOrdersService.ValidatePurchaseOrdersDocument(Purchaseorder_id,
						document);

				if (file != null) {
					if (file.getPurchaseorder_content() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getPurchaseorder_filename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getPurchaseorder_contentType());
						response.getOutputStream().write(file.getPurchaseorder_content());
						out.flush();
						out.close();

					}
				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			return "redirect:/newPurchaseOrders/1.in?danger=true";
		}
		return null;
	}

	@RequestMapping("/download/PurchaseorderDocument/{Purchaseorder_id}")
	public String downloadPurchaseOrderDoc(
			@PathVariable("Purchaseorder_id") Long Purchaseorder_id, HttpServletResponse response) throws Exception {
		try {
			if (Purchaseorder_id == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				org.fleetopgroup.persistence.document.PurchaseOrdersDocument file = PurchaseOrdersService.getPurchaseOrdersDocumentDetails(Purchaseorder_id);

				if (file != null) {
					if (file.getPurchaseorder_content() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getPurchaseorder_filename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getPurchaseorder_contentType());
						response.getOutputStream().write(file.getPurchaseorder_content());
						out.flush();
						out.close();

					}
				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			return "redirect:/newPurchaseOrders/1.in?danger=true";
		}
		return null;
	}

	
	// Save PurchaseOrders Tax Cost
	@RequestMapping(value = "/PurchaseOrders_Complete", method = RequestMethod.GET)
	public ModelAndView PurchaseOrdersUpdate_Complete(@RequestParam("ID") final Long Purchaseorder_id,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<PartLocationPermissionDto> PartLocPermission = PLBL.prepareListofPartLocation(partLocationPermissionService
					.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

			HashMap<String, Object>  threadHM	=  PurchaseOrdersService.getPurchaseOrderDetailsHM(Purchaseorder_id, userDetails);
			
			@SuppressWarnings("unchecked")
			PurchaseOrdersDto Purchase = POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
			
			Purchase.setCompanyId(userDetails.getCompany_id());

			// check user branch location and workOrder location if same
			if (InvenBL.isFilled_GET_Permission(Purchase.getPurchaseorder_shiplocation_id(), PartLocPermission)) {

				PurchaseOrders Purchase_complete = POBL.Update_PurchaseOrder_Completed(Purchase);

				PurchaseOrdersService.updatePurchaseOrder_Complete(Purchase_complete);
				
				pendingVendorPaymentService.deletePendingVendorPayment(Purchaseorder_id, PendingPaymentType.PAYMENT_TYPE_PURCHASE_ORDER);
				if(Purchase.getPurchaseorder_termsId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					  PendingVendorPayment	payment	=	PendingVendorPaymentBL.createPendingVendorPaymentDTOForPO(Purchase);
					  payment.setTransactionAmount(Purchase_complete.getPurchaseorder_balancecost());
					  payment.setBalanceAmount(Purchase_complete.getPurchaseorder_balancecost());
					  pendingVendorPaymentService.savePendingVendorPayment(payment);
				}
				
				HashMap<String,Object> configuration =companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG );
				if((boolean) configuration.getOrDefault("allowBankPaymentDetails",false)) {
				ValueObject bankPaymentValueObject=new ValueObject();
				bankPaymentValueObject.put("oldPaymentTypeId",Purchase.getPurchaseorder_termsId());
				bankPaymentValueObject.put("bankPaymentTypeId", Purchase.getPurchaseorder_termsId());
				bankPaymentValueObject.put("currentPaymentTypeId", Purchase.getPurchaseorder_termsId());
				bankPaymentValueObject.put("userId",userDetails.getId());
				bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
				bankPaymentValueObject.put("moduleId", Purchase.getPurchaseorder_id());
				bankPaymentValueObject.put("moduleNo", Purchase.getPurchaseorder_Number());
				bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.PURCHASE_ORDER);
				bankPaymentValueObject.put("amount",Purchase.getPurchaseorder_totalcost());
				
				Vendor	vendor	=  vendorService.getVendor(Purchase.getPurchaseorder_vendor_id());
				bankPaymentValueObject.put("remark", " PO-"+Purchase.getPurchaseorder_Number()+" vendor : "+vendor.getVendorName());
				
				
				bankPaymentService.updatePaymentDetailsFromValuObject(bankPaymentValueObject);
				}
			} else {
				model.put("NoAuthen", true);
				return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + Purchaseorder_id + "", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + Purchaseorder_id + "", model);
	}

	// Re Open PurchaseOrders
	@RequestMapping(value = "/PurchaseOrders_Reopen", method = RequestMethod.GET)
	public ModelAndView PurchaseOrders_Reopen(@RequestParam("ID") final Long Purchaseorder_id,
			final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<PartLocationPermissionDto> PartLocPermission = PLBL.prepareListofPartLocation(partLocationPermissionService
					.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

			/*PurchaseOrdersDto Purchase = POBL
					.getPurchaseOrders(PurchaseOrdersService.getPurchaseOrders(Purchaseorder_id));*/
			HashMap<String, Object>  threadHM	=  PurchaseOrdersService.getPurchaseOrderDetailsHM(Purchaseorder_id, userDetails);
			
			@SuppressWarnings("unchecked")
			PurchaseOrdersDto Purchase = POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
			

			// check user branch location and workOrder location if same
			if (InvenBL.isFilled_GET_Permission(Purchase.getPurchaseorder_shiplocation_id(), PartLocPermission)) {

				PurchaseOrders Purchase_complete = POBL.Update_PurchaseOrder_ReOpen(Purchase);

				PurchaseOrdersService.updatePurchaseOrder_ReOpen(Purchase_complete);
				
				pendingVendorPaymentService.deletePendingVendorPayment(Purchase.getPurchaseorder_id(), PendingPaymentType.PAYMENT_TYPE_PURCHASE_ORDER);

			} else {
				model.put("NoAuthen", true);
				return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + Purchaseorder_id + "", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + Purchaseorder_id + "", model);
	}

	// Delete PurchaseOrders Return Parts
	@RequestMapping(value = "/deleteReturnPO", method = RequestMethod.GET)
	public ModelAndView deleteReturnPO(@RequestParam("ID") final Long Purchaseorder_id,
			@RequestParam("PDID") final Long purchaseorderto_debitnoteid, final HttpServletRequest request)
			throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<PartLocationPermissionDto> PartLocPermission = PLBL.prepareListofPartLocation(partLocationPermissionService
					.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));
			HashMap<String, Object>  threadHM	=  PurchaseOrdersService.getPurchaseOrderDetailsHM(Purchaseorder_id, userDetails);
			
			@SuppressWarnings("unchecked")
			PurchaseOrdersDto Purchase = POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
			

			// check user branch location and workOrder location if same
			if (InvenBL.isFilled_GET_Permission(Purchase.getPurchaseorder_shiplocation_id(), PartLocPermission)) {

				List<Inventory> validateOldRecevied = InventoryService.Get_Validate_InventoryPurchaseOrderId_Details(
						"" + Purchaseorder_id, userDetails.getCompany_id());
				if (validateOldRecevied != null && !validateOldRecevied.isEmpty()) {

					model.put("deleteInventory", true);
					return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + Purchaseorder_id + "", model);
				} else {

					// DebitNote Id to Get Details
					PurchaseOrdersToDebitNote DebitNote = PurchaseOrdersService.Get_PurchaseOrdersTasks_ToDebitNoteID(
							purchaseorderto_debitnoteid, userDetails.getCompany_id());
					PurchaseOrders Purchase_complete = POBL.Update_PurchaseOrder_DeleteReturnParts(DebitNote);

					PurchaseOrdersService.Update_PurchaseOrder_Delete_DebitNote_Amount(Purchase_complete);

					model.put("DeleteReturn", true);

					// Delete DebitNote Id in To database
					PurchaseOrdersService.Delete_PurchaseOrders_ToDebitNoteID(purchaseorderto_debitnoteid,
							userDetails.getCompany_id());
				}
			} else {
				model.put("NoAuthen", true);
				return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + Purchaseorder_id + "", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}

		return new ModelAndView("redirect:/PurchaseOrders_Parts.in?ID=" + Purchaseorder_id + "", model);
	}

	// Delete to PurchaseOrders
	@RequestMapping(value = "/deletePurchaseOrder", method = RequestMethod.GET)
	public ModelAndView deletePurchaseOrderTask(@RequestParam("ID") final Long Purchaseorder_id,
			final HttpServletRequest request) throws Exception {

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			List<PurchaseOrdersToParts> PurchaseOrdertask = PurchaseOrdersService
					.getPurchaseOrdersApprovedTasksToParts(Purchaseorder_id);
			if (PurchaseOrdertask != null && !PurchaseOrdertask.isEmpty()) {

				return new ModelAndView("redirect:/newPurchaseOrders/1.in?deleteAllTask=true");

			} else {
				PurchaseOrdersService.deletePurchaseOrders(Purchaseorder_id, userDetails.getCompany_id(),userDetails.getId());
				PurchaseOrdersService.deletePurchaseOrdersParts(Purchaseorder_id, userDetails.getCompany_id());
				bankPaymentService.deleteBankPaymentDetailsIfTransactionDeleted(Purchaseorder_id, ModuleConstant.PURCHASE_ORDER, userDetails.getCompany_id(),userDetails.getId(),false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/newPurchaseOrders/1.in?danger=true");
		}
		return new ModelAndView("redirect:/newPurchaseOrders/1.in?deleteSuccess=true");
	}

	/***************************************************************************************************************************************
	 ************** Get Vehicle List drop down less Loading to Search
	 ***************************************************************************************************************************************/

	@RequestMapping(value = "/getPurchasePartLocation", method = RequestMethod.POST)
	public void getVehicleList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<PartLocations> addresses = new ArrayList<PartLocations>();
		List<PartLocations> location = PartLocationsService.SearchOnlyPartLocations(term, userDetails.getCompany_id());
		if (location != null && !location.isEmpty()) {
			for (PartLocations add : location) {
				PartLocations wadd = new PartLocations();
				wadd.setPartlocation_id(add.getPartlocation_id());
				wadd.setPartlocation_name(add.getPartlocation_name());

				addresses.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(addresses));
	}

	@RequestMapping(value = "/getWorkorderID", method = RequestMethod.POST)
	public void getJobTypeSearchListPurchaseOrder(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		List<WorkOrders> WORK_ID = new ArrayList<WorkOrders>();
		List<WorkOrdersDto> WORK = WorkOrdersService.SearchWorkOrders(term, userDetails.getCompany_id(),
				userDetails.getId());
		if (WORK != null && !WORK.isEmpty()) {
			for (WorkOrdersDto add : WORK) {
				WorkOrders wadd = new WorkOrders();
				wadd.setWorkorders_id(add.getWorkorders_id());
				wadd.setWorkorders_Number(add.getWorkorders_Number());

				WORK_ID.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(WORK_ID));
	}

	// get Company Buyer to get company address
	/*@RequestMapping(value = "/getCompanyAddress", method = RequestMethod.GET)
	public void getCompanyAddress(Map<String, Object> map, @RequestParam("companyname") final String companyname,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		Company company = CompanyService.validateCompanyName(companyname);
		Company com = new Company();
		if (company != null) {
			com.setCompany_address(company.getCompany_address());
			com.setCompany_city(company.getCompany_city());
			com.setCompany_state(company.getCompany_state());
			com.setCompany_country(company.getCompany_country());
			com.setCompany_pincode(company.getCompany_pincode());
		}

		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(com));
	}*/

	/** load search vendor part and service vendor name */
	@RequestMapping(value = "/getVendorSearchListPurchase", method = RequestMethod.POST)
	public void getVendorSearchListPurchase(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<Vendor> addresses = new ArrayList<Vendor>();
		List<Vendor> vendor = vendorService.SearchOnly_PART_VendorName(term, userDetails.getCompany_id());
		if (vendor != null && !vendor.isEmpty()) {
			for (Vendor add : vendor) {
				Vendor wadd = new Vendor();
				if (add != null) {
					wadd.setVendorId(add.getVendorId());
					wadd.setVendorName(add.getVendorName());
					wadd.setVendorLocation(add.getVendorLocation());
					addresses.add(wadd);
				}
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(addresses));
	}

	// get to save in preparePurchaseOrders
	public PurchaseOrders SavePurchaseOrders(PurchaseOrdersDto PurchaseOrders) {

		PurchaseOrders Purchase = new PurchaseOrders();

		Purchase.setPurchaseorder_typeId(PurchaseOrders.getPurchaseorder_typeId());
		try {
			if (PurchaseOrders.getPurchaseorder_vendor_id() != null  && PurchaseOrders.getPurchaseorder_vendor_id() != 0) {
				Purchase.setPurchaseorder_vendor_id(PurchaseOrders.getPurchaseorder_vendor_id());
			} else {
				Purchase.setPurchaseorder_vendor_id(0);
			}
			if (PurchaseOrders.getPurchaseorder_created_on() != null) {
				java.util.Date date = dateFormat.parse(PurchaseOrders.getPurchaseorder_created_on());
				java.sql.Date start_date = new java.sql.Date(date.getTime());
				Purchase.setPurchaseorder_created_on(start_date);
			}
			if (PurchaseOrders.getPurchaseorder_requied_on() != null) {
				java.util.Date requiddate = dateFormat.parse(PurchaseOrders.getPurchaseorder_requied_on());
				java.sql.Date requied_date = new java.sql.Date(requiddate.getTime());
				Purchase.setPurchaseorder_requied_on(requied_date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Purchase.setPurchaseorder_termsId(PurchaseOrders.getPurchaseorder_termsId());
		if (PurchaseOrders.getPurchaseorder_termsId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {

			Purchase.setPurchaseorder_vendor_paymodeId(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);

		} else {
			Purchase.setPurchaseorder_vendor_paymodeId(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_PAID);
			java.util.Date poPaiddate = new java.util.Date();
			Timestamp toDatePayment = new java.sql.Timestamp(poPaiddate.getTime());

			Purchase.setPurchaseorder_vendor_paymentdate(toDatePayment);
			Purchase.setPurchaseorder_vendor_approvalID((long) 0);
		}
		Purchase.setPurchaseorder_shipviaId(PurchaseOrders.getPurchaseorder_shipviaId());

		try {

			if (PurchaseOrders.getPurchaseorder_shiplocation_id() != 0) {
				Purchase.setPurchaseorder_shiplocation_id(PurchaseOrders.getPurchaseorder_shiplocation_id());
			} else {
				Purchase.setPurchaseorder_shiplocation_id(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Purchase.setPurchaseorder_quotenumber(PurchaseOrders.getPurchaseorder_quotenumber());
		Purchase.setPurchaseorder_workordernumber(PurchaseOrders.getPurchaseorder_workordernumber());
		Purchase.setPurchaseorder_indentno(PurchaseOrders.getPurchaseorder_indentno());
		Purchase.setPurchaseorder_notes(PurchaseOrders.getPurchaseorder_notes());

		Purchase.setPurchaseorder_statusId(PurchaseOrderType.PURCHASEORDER_STATUS_REQUISITION);
		// set default value in save time
		Purchase.setPurchaseorder_subtotal_cost(0.0);
		Purchase.setPurchaseorder_totalcost(0.0);
		Purchase.setPurchaseorder_freight(0.0);
		Purchase.setPurchaseorder_totaltax_cost(0.0);
		Purchase.setPurchaseorder_advancecost(0.0);
		Purchase.setPurchaseorder_balancecost(0.0);
		Purchase.setPurchaseorder_total_debitnote_cost(0.0);

		Purchase.setPurchaseorder_document(false);
		Purchase.setPurchaseorder_document_id((long) 0);

		Purchase.setMarkForDelete(false);
		/** who Create this vehicle get email id to user profile details */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		Purchase.setPurchaseorder_buyer_id(userDetails.getCompany_id());

		Purchase.setCreatedById(userDetails.getId());
		Purchase.setLastModifiedById(userDetails.getId());
		Purchase.setCompanyId(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Purchase.setCreated(toDate);
		Purchase.setLastupdated(toDate);
		
		if(PurchaseOrders.getTallyCompanyId() != null) {
			Purchase.setTallyCompanyId(PurchaseOrders.getTallyCompanyId());
		} else {
			Purchase.setTallyCompanyId((long)0);
		}
		if(PurchaseOrders.getSubCompanyId() > 0) {
			Purchase.setSubCompanyId(PurchaseOrders.getSubCompanyId());
		}

		return Purchase;
	}


	public PurchaseOrdersToParts preparePurchaseOrdersTaskToPart(PurchaseOrdersToParts PurchaseOrdersTasksToParts) {
	// get to save in preparePurchaseOrders

		PurchaseOrdersToParts PurchasePart = new PurchaseOrdersToParts();

		PurchasePart.setPartid(PurchaseOrdersTasksToParts.getPartid());
		PurchasePart.setClothTypesId(PurchaseOrdersTasksToParts.getClothTypesId());
		try {
			if (PurchaseOrdersTasksToParts.getPartid() != null) {
				InventoryAllDto invent = InventoryService.Purchase_Order_Validate_InventoryAll(PurchaseOrdersTasksToParts.getPartid());

				if (invent != null) {
					if (invent.getAll_quantity() != null && invent.getAll_quantity() != 0.0) {
						PurchasePart.setInventory_all_id(invent.getInventory_all_id());
						PurchasePart.setInventory_all_quantity(invent.getAll_quantity());
					} else {
						PurchasePart.setInventory_all_id((long) 0);
						PurchasePart.setInventory_all_quantity(0.0);
					}
				} else {
					PurchasePart.setInventory_all_id((long) 0);
					PurchasePart.setInventory_all_quantity(0.0);
				}
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}

		Double Quantity = 0.0;
		Double eaccost = 0.0;
		Double discount = 0.0;
		Double tax = 0.0;

		if (PurchaseOrdersTasksToParts.getQuantity() != null) {
			Quantity = PurchaseOrdersTasksToParts.getQuantity();
		}
		if (PurchaseOrdersTasksToParts.getParteachcost() != null) {
			eaccost = PurchaseOrdersTasksToParts.getParteachcost();
		}
		if (PurchaseOrdersTasksToParts.getDiscount() != null) {
			discount = PurchaseOrdersTasksToParts.getDiscount();
		}
		if (PurchaseOrdersTasksToParts.getTax() != null) {
			tax = PurchaseOrdersTasksToParts.getTax();
		}
		Double discountCost = 0.0;
		Double TotalCost = 0.0;
		

		discountCost 	= (Quantity * eaccost) - ((Quantity * eaccost) * (discount / 100));
		TotalCost 		= round(((discountCost) + (discountCost * (tax / 100))), 2);
		
		PurchasePart.setQuantity(PurchaseOrdersTasksToParts.getQuantity());
		PurchasePart.setReceived_quantity(0.0);
		PurchasePart.setNotreceived_quantity(PurchaseOrdersTasksToParts.getQuantity());
		PurchasePart.setRequestedQuantity(PurchaseOrdersTasksToParts.getQuantity());
		PurchasePart.setParteachcost(PurchaseOrdersTasksToParts.getParteachcost());
		PurchasePart.setDiscount(PurchaseOrdersTasksToParts.getDiscount());
		PurchasePart.setTax(PurchaseOrdersTasksToParts.getTax());

		PurchasePart.setTotalcost(TotalCost);

		return PurchasePart;
	}

	// get to save in preparePurchaseOrders
	public PurchaseOrdersToParts preparePurchaseOrdersTask_Tyre(PurchaseOrdersToParts PurchaseOrdersTasksToParts) {

		PurchaseOrdersToParts PurchasePart = new PurchaseOrdersToParts();

		PurchasePart.setPartid(PurchaseOrdersTasksToParts.getPartid());

		PurchasePart.setTYRE_MANUFACTURER_ID(PurchaseOrdersTasksToParts.getTYRE_MANUFACTURER_ID());
		PurchasePart.setTYRE_MODEL_ID(PurchaseOrdersTasksToParts.getTYRE_MODEL_ID());
		PurchasePart.setTYRE_SIZE_ID(PurchaseOrdersTasksToParts.getTYRE_SIZE_ID());

		try {
			// check Tyre Size empty or not
			if (PurchaseOrdersTasksToParts.getTYRE_SIZE_ID() != null && PurchaseOrdersTasksToParts.getTYRE_SIZE_ID() > 0) {
				List<InventoryTyreDto> invent = iventoryTyreService
						.Purchase_Order_Validate_InventoryTyre(PurchaseOrdersTasksToParts.getTYRE_SIZE_ID());
				Integer TotalTyreAvailable = 0, NewTyre = 0, RtTyre = 0;
				if (invent != null && !invent.isEmpty()) {
					for (InventoryTyreDto inventoryTyre : invent) { 
						TotalTyreAvailable += 1;
						if (inventoryTyre.getTYRE_RETREAD_COUNT() == 0) {
							NewTyre += 1;
						} else {
							RtTyre += 1;
						}
					}
					PurchasePart.setINVENTORY_TYRE_QUANTITY(TotalTyreAvailable);
					PurchasePart.setINVENTORY_TYRE_NEW_RT("NEW= " + NewTyre + " & RT= " + RtTyre + "");
				} else {
					PurchasePart.setINVENTORY_TYRE_QUANTITY(0);
					PurchasePart.setINVENTORY_TYRE_NEW_RT(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Double Quantity = 0.0;
		Double eaccost = 0.0;
		Double discount = 0.0;
		Double tax = 0.0;

		if (PurchaseOrdersTasksToParts.getQuantity() != null) {
			Quantity = PurchaseOrdersTasksToParts.getQuantity();
		}
		if (PurchaseOrdersTasksToParts.getParteachcost() != null) {
			eaccost = PurchaseOrdersTasksToParts.getParteachcost();
		}
		if (PurchaseOrdersTasksToParts.getDiscount() != null) {
			discount = PurchaseOrdersTasksToParts.getDiscount();
		}
		if (PurchaseOrdersTasksToParts.getTax() != null) {
			tax = PurchaseOrdersTasksToParts.getTax();
		}
		Double discountCost = 0.0;
		Double TotalCost = 0.0;

		discountCost = (Quantity * eaccost) - ((Quantity * eaccost) * (discount / 100));
		TotalCost = round(((discountCost) + (discountCost * (tax / 100))), 2);

		PurchasePart.setQuantity(PurchaseOrdersTasksToParts.getQuantity());
		PurchasePart.setReceived_quantity(0.0);
		PurchasePart.setNotreceived_quantity(PurchaseOrdersTasksToParts.getQuantity());

		PurchasePart.setParteachcost(PurchaseOrdersTasksToParts.getParteachcost());
		PurchasePart.setDiscount(PurchaseOrdersTasksToParts.getDiscount());
		PurchasePart.setTax(PurchaseOrdersTasksToParts.getTax());

		PurchasePart.setTotalcost(TotalCost);

		return PurchasePart;
	}
	
	public PurchaseOrdersToParts preparePurchaseOrdersTask_Battery(PurchaseOrdersToParts PurchaseOrdersTasksToParts) {

		PurchaseOrdersToParts PurchasePart = new PurchaseOrdersToParts();

		PurchasePart.setPartid(PurchaseOrdersTasksToParts.getPartid());

		PurchasePart.setBATTERY_MANUFACTURER_ID(PurchaseOrdersTasksToParts.getBATTERY_MANUFACTURER_ID());
		PurchasePart.setBATTERY_TYPE_ID(PurchaseOrdersTasksToParts.getBATTERY_TYPE_ID());
		PurchasePart.setBATTERY_CAPACITY_ID(PurchaseOrdersTasksToParts.getBATTERY_CAPACITY_ID());

		try {
			// check Tyre Size empty or not
			if (PurchaseOrdersTasksToParts.getBATTERY_CAPACITY_ID() != null && PurchaseOrdersTasksToParts.getBATTERY_CAPACITY_ID() > 0) {
				Long invent = batteryService.getBatteryCount(PurchaseOrdersTasksToParts.getBATTERY_CAPACITY_ID(), PurchaseOrdersTasksToParts.getCompanyId());
				if (invent != null ) {
					
					PurchasePart.setBATTERY_QUANTITY(invent);
				} else {
					PurchasePart.setBATTERY_QUANTITY((long) 0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Double Quantity = 0.0;
		Double eaccost = 0.0;
		Double discount = 0.0;
		Double tax = 0.0;

		if (PurchaseOrdersTasksToParts.getQuantity() != null) {
			Quantity = PurchaseOrdersTasksToParts.getQuantity();
		}
		if (PurchaseOrdersTasksToParts.getParteachcost() != null) {
			eaccost = PurchaseOrdersTasksToParts.getParteachcost();
		}
		if (PurchaseOrdersTasksToParts.getDiscount() != null) {
			discount = PurchaseOrdersTasksToParts.getDiscount();
		}
		if (PurchaseOrdersTasksToParts.getTax() != null) {
			tax = PurchaseOrdersTasksToParts.getTax();
		}
		Double discountCost = 0.0;
		Double TotalCost = 0.0;

		discountCost = (Quantity * eaccost) - ((Quantity * eaccost) * (discount / 100));
		TotalCost = round(((discountCost) + (discountCost * (tax / 100))), 2);

		PurchasePart.setQuantity(PurchaseOrdersTasksToParts.getQuantity());
		PurchasePart.setReceived_quantity(0.0);
		PurchasePart.setNotreceived_quantity(PurchaseOrdersTasksToParts.getQuantity());

		PurchasePart.setParteachcost(PurchaseOrdersTasksToParts.getParteachcost());
		PurchasePart.setDiscount(PurchaseOrdersTasksToParts.getDiscount());
		PurchasePart.setTax(PurchaseOrdersTasksToParts.getTax());

		PurchasePart.setTotalcost(TotalCost);

		return PurchasePart;
	}

	// send Ordered Details in vendor email Id
	@SuppressWarnings("unchecked")
	private final MimeMessage sentOrderd_DetailsVendorEmail(final Long ID, final Locale locale, CustomUserDetails	userDetails) throws Exception {

		
		 	HashMap<String, Object>  threadHM	=  PurchaseOrdersService.getPurchaseOrderDetailsHM(ID, userDetails);
			
			PurchaseOrdersDto Purchase = POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
			
			
		Vendor vendoremail = vendorService.getVendor(Purchase.getPurchaseorder_vendor_id());

		List<PurchaseOrdersToPartsDto> purchaseToParts = PurchaseOrdersService
				.getPurchaseOrdersTasksToParts(Purchase.getPurchaseorder_id(), userDetails.getCompany_id());
		 //System.err.println(vendoremail.getVendorFirEmail());

		String purchasePartsHTML = "";
		for (PurchaseOrdersToPartsDto PurchaseOrdersParts : purchaseToParts) {
			purchasePartsHTML += "										<tr data-object-id=\"\" class=\"ng-scope\">"
					+ "											<td>"
					+ PurchaseOrdersParts.getPurchaseorder_partnumber() + " "
					+ PurchaseOrdersParts.getPurchaseorder_partname() + "</td>" + ""
					+ "											<td>" + PurchaseOrdersParts.getQuantity() + "</td>"
					+ "											<td>" + PurchaseOrdersParts.getParteachcost() + "</td>"
					+ "											<td>" + PurchaseOrdersParts.getDiscount() + " %</td>"
					+ "											<td>" + PurchaseOrdersParts.getTax() + " %</td>"
					+ "											<td>" + PurchaseOrdersParts.getTotalcost() + "</td>"
					+ "" + "" + "										</tr>";
		}

		final String sendHTML_Email_PO = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
				+ "<title>Fleetop.in</title>" + "</head>" + "<body>" + "	<div class=\"block\">"
				+ "		<!-- Start of preheader -->"
				+ "		<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"preheader\">"
				+ "			<tbody>" + "				<tr>" + "					<td width=\"100%\">"
				+ "						<table width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\">"
				+ "							<tbody>" + "								<!-- Spacing -->"
				+ "								<tr>"
				+ "									<td width=\"100%\" height=\"5\"></td>"
				+ "								</tr>" + "								<!-- Spacing -->"
				+ "								<tr>"
				+ "									<td align=\"right\" valign=\"middle\" style=\"font-family: Helvetica, arial, sans-serif; font-size: 10px;color: #999999\" st-content=\"preheader\">"
				+ "										If you cannot read this email, please  <a class=\"hlite\" href=\"#\" style=\"text-decoration: none; color: #0db9ea\">click here</a> "
				+ "									</td>" + "								</tr>"
				+ "								<!-- Spacing -->" + "								<tr>"
				+ "									<td width=\"100%\" height=\"5\"></td>"
				+ "								</tr>" + "								<!-- Spacing -->"
				+ "							</tbody>" + "						</table>" + "					</td>"
				+ "				</tr>" + "			</tbody>" + "		</table>" + "		<!-- End of preheader -->"
				+ "	</div>" + "	<div class=\"block\">" + "		<!-- start of header -->"
				+ "		<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"header\">"
				+ "			<tbody>" + "				<tr>" + "					<td>"
				+ "						<table width=\"680\" bgcolor=\"#ffde12\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\" hlitebg=\"edit\" shadow=\"edit\">"
				+ "							<tbody>" + "								<tr>"
				+ "									<td>" + "										"
				+ "										<!-- company -->"
				+ "										<table width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"right\" class=\"devicewidth\">"
				+ "											<tbody>"
				+ "												<tr>"
				+ "													<td width=\"270\" valign=\"middle\" style=\"font-family: Helvetica, Arial, sans-serif;font-size: 24px; color: #ffffff;line-height: 24px; padding: 10px 0;\" align=\"center\" class=\"menu\" st-content=\"menu\">"
				+ "														<a href=\"#\" style=\"text-decoration: none; color: #222;\">"
				+ Purchase.getPurchaseorder_buyer() + "</a>"
				+ "													</td>"
				+ "													<td width=\"20\"></td>"
				+ "												</tr>"
				+ "											</tbody>"
				+ "										</table>"
				+ "										<!-- End of Menu -->"
				+ "									</td>" + "								</tr>"
				+ "							</tbody>" + "						</table>" + "					</td>"
				+ "				</tr>" + "			</tbody>" + "		</table>" + "		<!-- end of header -->"
				+ "	</div><div class=\"block\">" + ""
				+ "	<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"bigimage\">"
				+ "		<tbody>" + "			<tr>" + "				<td>"
				+ "					<table  width=\"680\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" bgcolor=\"#999999\">"
				+ "						<tr>" + "							<td>"
				+ "								<table  width=\"680\" cellspacing=\"0\" cellpadding=\"10\" border=\"0\">"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td width=\"33%\" height=\"40\"> </td>"
				+ "										<td width=\"34%\" style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\" st-title=\"3col-title1\">Purchase Order "
				+ Purchase.getPurchaseorder_Number() + "</td>"
				+ "										<td width=\"33%\"> </td>"
				+ "									</tr>"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td colspan=\"2\">Vendor : "
				+ Purchase.getPurchaseorder_vendor_name() + " </td>"
				+ "										<td>Terms:	" + Purchase.getPurchaseorder_terms() + "</td>"
				+ "									</tr>"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td height=\"67\" > Order Date: "
				+ Purchase.getPurchaseorder_created_on() + "</td>"
				+ "										<td>Buyer Name:	" + Purchase.getPurchaseorder_buyer() + "</td>"
				+ "										<td>Ship To:	" + Purchase.getPurchaseorder_shiplocation()
				+ "</td>" + "									</tr>"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td height=\"67\" >"
				+ "											Required date: "
				+ Purchase.getPurchaseorder_requied_on() + ""
				+ "										</td>"
				+ "										<td>Buyer Address :	" + Purchase.getPurchaseorder_buyeraddress()
				+ "" + "										</td>"
				+ "										<td>Ship Address:	"
				+ Purchase.getPurchaseorder_shiplocation_address() + "</td>"
				+ "									</tr>"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td height=\"67\" >"
				+ "											Quote No : " + Purchase.getPurchaseorder_quotenumber()
				+ "</td>" + "											<td>"
				+ "												Ship via :	" + Purchase.getPurchaseorder_shipvia()
				+ "</td>" + "												<td>"
				+ "													Contact: "
				+ Purchase.getPurchaseorder_shiplocation_mobile() + "</td>"
				+ "												</tr>"
				+ "											</table>" + "										</td>"
				+ "									</tr>" + "								</table>"
				+ "							</td>" + "						</tr>" + "					</tbody>"
				+ "				</table>" + "" + "" + "				<!-- image + text -->"
				+ "				<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"bigimage\">"
				+ "					<tbody>" + "						<tr>" + "							<td>"
				+ "								<table bgcolor=\"#ffffff\" width=\"680\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"devicewidth\" modulebg=\"edit\">"
				+ "									<tbody>" + "										<tr>"
				+ "											<td>"
				+ "												<table width=\"680\" align=\"left\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"devicewidthinner\" >"
				+ "													<tbody>" + ""
				+ "														<!-- title -->"
				+ "														<tr style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\" st-title=\"rightimage-title\">"
				+ "" + "															<td width=\"33%\" height=\"67\">"
				+ "																Parts Details"
				+ "															</td>" + ""
				+ "														</tr>"
				+ "														<!-- end of title -->"
				+ "													</tbody>"
				+ "												</table>"
				+ "											</td>" + "										</tr>"
				+ "									</tbody>" + "								</table>"
				+ "							</td>" + "						</tr>" + "					</tbody>"
				+ "				</table>" + "			</div>" + "" + "			<div class=\"block\">"
				+ "				<!-- parts Details -->  "
				+ "				<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"3columns\">"
				+ "					<tbody>" + "						<tr>" + "							<td>"
				+ "								<table bgcolor=\"#ffffff\" width=\"680\" cellpadding=\"10\" cellspacing=\"0\" border=\"1\" align=\"center\" class=\"devicewidth\" modulebg=\"edit\">"
				+ "									<thead>"
				+ "										<tr class=\"breadcrumb\">"
				+ "											<th>Part</th>"
				+ "											<th>Qty</th>"
				+ "											<th>Each</th>"
				+ "											<th>Dis</th>"
				+ "											<th>Tax</th>"
				+ "											<th>Total</th>"
				+ "										</tr>" + "									</thead>"
				+ "									<tbody>" + purchasePartsHTML
				+ "									</tbody>" + "								</table>"
				+ "							</td>" + "						</tr>" + "					</tbody>"
				+ "				</table>" + "				<!-- end parts Details -->" + "			</div>" + "" + ""
				+ "" + "			<div class=\"block\">" + "				<!-- 3-columns -->  "
				+ "				<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"3columns\">"
				+ "					<tbody>" + "						<tr>" + "							<td>"
				+ "								<table bgcolor=\"#ffffff\" width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\" modulebg=\"edit\">"
				+ "									<tbody>" + "										<tr>"
				+ "											<td width=\"100%\" height=\"20\"></td>"
				+ "										</tr>" + "										<tr>"
				+ "											<td>"
				+ "												<table width=\"540\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\">"
				+ "													<tbody>"
				+ "														<tr>"
				+ "															<td>"
				+ "																<table width=\"260\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidth\">"
				+ "																	<tbody>" + ""
				+ "																		<!-- Spacing -->"
				+ "																		<tr>"
				+ "																			<td width=\"100%\" height=\"15\" style=\"font-size:1px; line-height:1px; mso-line-height-rule: exactly;\"> </td>"
				+ "																		</tr>"
				+ "																		<!-- Spacing -->"
				+ "																		<tr>"
				+ "																			<td>"
				+ "																				<!-- start of text content table -->"
				+ "																				<table width=\"260\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidthinner\">"
				+ "																					<tbody>"
				+ "																						<!-- title -->"
				+ "																						<tr>"
				+ "																							<td width=\"100%\"  height=\"15\">"
				+ "																								Order Date:"
				+ "																								:"
				+ Purchase.getPurchaseorder_orderddate() + "</td>"
				+ "																							</tr>"
				+ "																							<tr  height=\"15\">"
				+ "																								<td >"
				+ "																									Order By:"
				+ "																									"
				+ Purchase.getPurchaseorder_orderdby() + "</td>"
				+ "																								</tr> "
				+ "																								<tr  height=\"15\">"
				+ ""
				+ "																									<td >"
				+ "																										Remarks:"
				+ "																										"
				+ Purchase.getPurchaseorder_orderd_remark() + "</td>"
				+ "																									</tr>                                                                           "
				+ "																								</tbody>"
				+ "																							</table>"
				+ "																						</td>"
				+ "																					</tr>"
				+ "																					<!-- end of text content table -->"
				+ "																				</tbody>"
				+ "																			</table>"
				+ "																			<!-- end of left column -->"
				+ "" + ""
				+ "																			<!-- end of col 2 -->" + ""
				+ "																			<!-- col 3 -->"
				+ "																			<table width=\"280\" align=\"right\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidth\">"
				+ "																				<tbody>" + ""
				+ "																					<tr>"
				+ "																						<td>"
				+ "																							<!-- start of text content table -->  "
				+ "																							<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidthinner\">"
				+ "																								<tbody>"
				+ ""
				+ "																									<!-- title -->"
				+ "																									<tr>"
				+ "																										<td style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\" st-title=\"3col-title3\">"
				+ "																											<table width=\"170\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidthinner\">"
				+ "																												<tbody style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\" st-title=\"3col-title3\">"
				+ ""
				+ "																													<!-- title -->"
				+ "																													<tr height=\"30\">"
				+ "																														<td >SubTotal :</td><td>"
				+ Purchase.getPurchaseorder_subtotal_cost() + ""
				+ "																													</td>"
				+ "																												</tr>"
				+ ""
				+ "																												<tr height=\"30\">"
				+ "																													<td  >Freight :	</td><td> "
				+ Purchase.getPurchaseorder_freight() + "</td></tr>"
				+ "																													<tr height=\"30\"><td>Taxes :	 </td><td>"
				+ Purchase.getPurchaseorder_totaltax_cost() + "</td>" + ""
				+ "																													</tr>"
				+ "																													<tr height=\"40\"><td>Total :	</td><td>"
				+ Purchase.getPurchaseorder_totalcost() + "</td></tr>"
				+ "																													<tr height=\"30\"><td>Advance Paid:</td><td>"
				+ Purchase.getPurchaseorder_advancecost() + "</td></tr>"
				+ "																													<tr height=\"30\"><td>Balance :	 </td><td>"
				+ Purchase.getPurchaseorder_balancecost() + "</td>"
				+ "																													</td>"
				+ "																												</tr>"
				+ "																												<!-- end of title -->"
				+ ""
				+ "																											</tbody>"
				+ "																										</table>"
				+ ""
				+ "																									</td>"
				+ "																								</tr>"
				+ "																								<!-- end of title -->"
				+ ""
				+ "																							</tbody>"
				+ "																						</table>"
				+ "																					</td>"
				+ "																				</tr>"
				+ "																				<!-- end of text content table -->"
				+ "																			</tbody>"
				+ "																		</table>"
				+ "																	</td>"
				+ "																	<!-- spacing -->"
				+ "																	<!-- end of spacing -->"
				+ "																</tr>"
				+ "															</tbody>"
				+ "														</table>"
				+ "													</td>"
				+ "												</tr>"
				+ "												<tr>"
				+ "													<td width=\"100%\" height=\"20\"></td>"
				+ "												</tr>"
				+ "											</tbody>"
				+ "										</table>" + "									</td>"
				+ "								</tr>" + "							</tbody>"
				+ "						</table>" + "						<!-- end of 3-columns --> "
				+ "					</div>" + "<div class=\"block\">" + "		<!-- start of header -->"
				+ "		<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"header\">"
				+ "			<tbody>" + "				<tr>" + "					<td>"
				+ "						<table width=\"680\" bgcolor=\"\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\" hlitebg=\"edit\" shadow=\"edit\">"
				+ "							<tbody>" + "								<tr>"
				+ "									<td>" + "										<!-- logo -->"
				+ "										<table width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"left\" class=\"devicewidth\">"
				+ "											<tbody>"
				+ "												<tr>"
				+ "													<td width=\"200\" height=\"48\" align=\"center\" class=\"devicewidth\"><a href=\"Http://www.fleetop.com\">"
				+ "														<img src=\"http://i1.goself.xyz/QhyvOb0m3EjE7A4/css/FleetopLogo.png\" alt=\"fleetop\" border=\"0\" width=\"180\" height=\"48\" style=\"display:block; border:none; outline:none; text-decoration:none;\" class=\"col3img\"></a>"
				+ "													</td>"
				+ "												</tr>"
				+ "											</tbody>"
				+ "										</table>"
				+ "										<!-- End of logo -->"
				+ "									</td>" + "								</tr>"
				+ "							</tbody>" + "						</table>" + "					</td>"
				+ "				</tr>" + "			</tbody>" + "		</table>" + "		<!-- end of header -->"
				+ "	</div>" + "" + "					<div class=\"block\">"
				+ "						<!-- Start of preheader -->"
				+ "						<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"postfooter\">"
				+ "							<tbody>" + "								<tr>"
				+ "									<td width=\"100%\">"
				+ "										<table width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\">"
				+ "											<tbody>"
				+ "												<!-- Spacing -->"
				+ "												<tr>"
				+ "													<td width=\"100%\" height=\"5\"></td>"
				+ "												</tr>"
				+ "												<!-- Spacing -->"
				+ "												<tr>"
				+ "													<td align=\"center\" valign=\"middle\" style=\"font-family: Helvetica, arial, sans-serif; font-size: 10px;color: #999999\" st-content=\"preheader\">"
				+ "														If you receive updates. please  <a class=\"hlite\" href=\"http://www.fleetop.com\" style=\"text-decoration: none; color: #0db9ea\">www.fleetop.com</a> "
				+ "													</td>"
				+ "												</tr>"
				+ "												<!-- Spacing -->"
				+ "												<tr>"
				+ "													<td width=\"100%\" height=\"5\"></td>"
				+ "												</tr>"
				+ "												<!-- Spacing -->"
				+ "											</tbody>"
				+ "										</table>" + "									</td>"
				+ "								</tr>" + "							</tbody>"
				+ "						</table>" + "						<!-- End of preheader -->"
				+ "					</div>" + "" + "				</body></html>";

		mailSender.send(new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				messageHelper.setTo(vendoremail.getVendorFirEmail());
				messageHelper.setSubject("Order Parts Details by");
				messageHelper.setReplyTo(env.getProperty("support.email"));

				/* sent html email text */
				messageHelper.setText(sendHTML_Email_PO, true);
			}
		});

		return null;

	}

	// send Ordered Details in vendor email Id
	@SuppressWarnings("unchecked")
	private final MimeMessage sent_Tyre_Orderd_DetailsVendorEmail(final Long ID, final Locale locale, CustomUserDetails userDetails) throws Exception {

		HashMap<String, Object>  threadHM	=  PurchaseOrdersService.getPurchaseOrderDetailsHM(ID, userDetails);
		
		PurchaseOrdersDto Purchase = POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
		

		Vendor vendoremail = vendorService.getVendor(Purchase.getPurchaseorder_vendor_id());

		List<PurchaseOrdersToPartsDto> purchaseToParts = PurchaseOrdersService
				.getPurchaseOrdersTasksToParts(Purchase.getPurchaseorder_id(), userDetails.getCompany_id());
		// System.out.println(vendoremail.getVendorFirEmail());

		String purchaseTyreHTML = "";
		for (PurchaseOrdersToPartsDto PurchaseOrdersParts : purchaseToParts) {
			purchaseTyreHTML += "										<tr data-object-id=\"\" class=\"ng-scope\">"
					+ "											<td>" + PurchaseOrdersParts.getTYRE_MANUFACTURER()
					+ " - " + PurchaseOrdersParts.getTYRE_MODEL() + " - " + PurchaseOrdersParts.getTYRE_SIZE() + "</td>"
					+ "" + "											<td>" + PurchaseOrdersParts.getQuantity()
					+ "</td>" + "											<td>"
					+ PurchaseOrdersParts.getParteachcost() + "</td>"
					+ "											<td>" + PurchaseOrdersParts.getDiscount() + " %</td>"
					+ "											<td>" + PurchaseOrdersParts.getTax() + " %</td>"
					+ "											<td>" + PurchaseOrdersParts.getTotalcost() + "</td>"
					+ "" + "" + "										</tr>";
		}

		final String sendHTML_Email_PO = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
				+ "<title>Fleetop.in</title>" + "</head>" + "<body>" + "	<div class=\"block\">"
				+ "		<!-- Start of preheader -->"
				+ "		<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"preheader\">"
				+ "			<tbody>" + "				<tr>" + "					<td width=\"100%\">"
				+ "						<table width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\">"
				+ "							<tbody>" + "								<!-- Spacing -->"
				+ "								<tr>"
				+ "									<td width=\"100%\" height=\"5\"></td>"
				+ "								</tr>" + "								<!-- Spacing -->"
				+ "								<tr>"
				+ "									<td align=\"right\" valign=\"middle\" style=\"font-family: Helvetica, arial, sans-serif; font-size: 10px;color: #999999\" st-content=\"preheader\">"
				+ "										If you cannot read this email, please  <a class=\"hlite\" href=\"#\" style=\"text-decoration: none; color: #0db9ea\">click here</a> "
				+ "									</td>" + "								</tr>"
				+ "								<!-- Spacing -->" + "								<tr>"
				+ "									<td width=\"100%\" height=\"5\"></td>"
				+ "								</tr>" + "								<!-- Spacing -->"
				+ "							</tbody>" + "						</table>" + "					</td>"
				+ "				</tr>" + "			</tbody>" + "		</table>" + "		<!-- End of preheader -->"
				+ "	</div>" + "	<div class=\"block\">" + "		<!-- start of header -->"
				+ "		<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"header\">"
				+ "			<tbody>" + "				<tr>" + "					<td>"
				+ "						<table width=\"680\" bgcolor=\"#ffde12\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\" hlitebg=\"edit\" shadow=\"edit\">"
				+ "							<tbody>" + "								<tr>"
				+ "									<td>" + "										"
				+ "										<!-- company -->"
				+ "										<table width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"right\" class=\"devicewidth\">"
				+ "											<tbody>"
				+ "												<tr>"
				+ "													<td width=\"270\" valign=\"middle\" style=\"font-family: Helvetica, Arial, sans-serif;font-size: 24px; color: #ffffff;line-height: 24px; padding: 10px 0;\" align=\"center\" class=\"menu\" st-content=\"menu\">"
				+ "														<a href=\"#\" style=\"text-decoration: none; color: #222;\">"
				+ Purchase.getPurchaseorder_buyer() + "</a>"
				+ "													</td>"
				+ "													<td width=\"20\"></td>"
				+ "												</tr>"
				+ "											</tbody>"
				+ "										</table>"
				+ "										<!-- End of Menu -->"
				+ "									</td>" + "								</tr>"
				+ "							</tbody>" + "						</table>" + "					</td>"
				+ "				</tr>" + "			</tbody>" + "		</table>" + "		<!-- end of header -->"
				+ "	</div><div class=\"block\">" + ""
				+ "	<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"bigimage\">"
				+ "		<tbody>" + "			<tr>" + "				<td>"
				+ "					<table  width=\"680\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" bgcolor=\"#999999\">"
				+ "						<tr>" + "							<td>"
				+ "								<table  width=\"680\" cellspacing=\"0\" cellpadding=\"10\" border=\"0\">"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td width=\"33%\" height=\"40\"> </td>"
				+ "										<td width=\"34%\" style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\" st-title=\"3col-title1\">Purchase Order "
				+ Purchase.getPurchaseorder_Number() + "</td>"
				+ "										<td width=\"33%\"> </td>"
				+ "									</tr>"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td colspan=\"2\">Vendor : "
				+ Purchase.getPurchaseorder_vendor_name() + " </td>"
				+ "										<td>Terms:	" + Purchase.getPurchaseorder_terms() + "</td>"
				+ "									</tr>"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td height=\"67\" > Order Date: "
				+ Purchase.getPurchaseorder_created_on() + "</td>"
				+ "										<td>Buyer Name:	" + Purchase.getPurchaseorder_buyer() + "</td>"
				+ "										<td>Ship To:	" + Purchase.getPurchaseorder_shiplocation()
				+ "</td>" + "									</tr>"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td height=\"67\" >"
				+ "											Required date: "
				+ Purchase.getPurchaseorder_requied_on() + ""
				+ "										</td>"
				+ "										<td>Buyer Address :	" + Purchase.getPurchaseorder_buyeraddress()
				+ "" + "										</td>"
				+ "										<td>Ship Address:	"
				+ Purchase.getPurchaseorder_shiplocation_address() + "</td>"
				+ "									</tr>"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td height=\"67\" >"
				+ "											Quote No : " + Purchase.getPurchaseorder_quotenumber()
				+ "</td>" + "											<td>"
				+ "												Ship via :	" + Purchase.getPurchaseorder_shipvia()
				+ "</td>" + "												<td>"
				+ "													Contact: "
				+ Purchase.getPurchaseorder_shiplocation_mobile() + "</td>"
				+ "												</tr>"
				+ "											</table>" + "										</td>"
				+ "									</tr>" + "								</table>"
				+ "							</td>" + "						</tr>" + "					</tbody>"
				+ "				</table>" + "" + "" + "				<!-- image + text -->"
				+ "				<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"bigimage\">"
				+ "					<tbody>" + "						<tr>" + "							<td>"
				+ "								<table bgcolor=\"#ffffff\" width=\"680\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"devicewidth\" modulebg=\"edit\">"
				+ "									<tbody>" + "										<tr>"
				+ "											<td>"
				+ "												<table width=\"680\" align=\"left\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"devicewidthinner\" >"
				+ "													<tbody>" + ""
				+ "														<!-- title -->"
				+ "														<tr style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\" st-title=\"rightimage-title\">"
				+ "" + "															<td width=\"33%\" height=\"67\">"
				+ "																Tyre Details"
				+ "															</td>" + ""
				+ "														</tr>"
				+ "														<!-- end of title -->"
				+ "													</tbody>"
				+ "												</table>"
				+ "											</td>" + "										</tr>"
				+ "									</tbody>" + "								</table>"
				+ "							</td>" + "						</tr>" + "					</tbody>"
				+ "				</table>" + "			</div>" + "" + "			<div class=\"block\">"
				+ "				<!-- Tyre Details -->  "
				+ "				<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"3columns\">"
				+ "					<tbody>" + "						<tr>" + "							<td>"
				+ "								<table bgcolor=\"#ffffff\" width=\"680\" cellpadding=\"10\" cellspacing=\"0\" border=\"1\" align=\"center\" class=\"devicewidth\" modulebg=\"edit\">"
				+ "									<thead>"
				+ "										<tr class=\"breadcrumb\">"
				+ "											<th>Tyre</th>"
				+ "											<th>Qty</th>"
				+ "											<th>Each</th>"
				+ "											<th>Dis</th>"
				+ "											<th>Tax</th>"
				+ "											<th>Total</th>"
				+ "										</tr>" + "									</thead>"
				+ "									<tbody>" + purchaseTyreHTML
				+ "									</tbody>" + "								</table>"
				+ "							</td>" + "						</tr>" + "					</tbody>"
				+ "				</table>" + "				<!-- end parts Details -->" + "			</div>" + "" + ""
				+ "" + "			<div class=\"block\">" + "				<!-- 3-columns -->  "
				+ "				<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"3columns\">"
				+ "					<tbody>" + "						<tr>" + "							<td>"
				+ "								<table bgcolor=\"#ffffff\" width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\" modulebg=\"edit\">"
				+ "									<tbody>" + "										<tr>"
				+ "											<td width=\"100%\" height=\"20\"></td>"
				+ "										</tr>" + "										<tr>"
				+ "											<td>"
				+ "												<table width=\"540\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\">"
				+ "													<tbody>"
				+ "														<tr>"
				+ "															<td>"
				+ "																<table width=\"260\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidth\">"
				+ "																	<tbody>" + ""
				+ "																		<!-- Spacing -->"
				+ "																		<tr>"
				+ "																			<td width=\"100%\" height=\"15\" style=\"font-size:1px; line-height:1px; mso-line-height-rule: exactly;\"> </td>"
				+ "																		</tr>"
				+ "																		<!-- Spacing -->"
				+ "																		<tr>"
				+ "																			<td>"
				+ "																				<!-- start of text content table -->"
				+ "																				<table width=\"260\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidthinner\">"
				+ "																					<tbody>"
				+ "																						<!-- title -->"
				+ "																						<tr>"
				+ "																							<td width=\"100%\"  height=\"15\">"
				+ "																								Order Date:"
				+ "																								:"
				+ Purchase.getPurchaseorder_orderddate() + "</td>"
				+ "																							</tr>"
				+ "																							<tr  height=\"15\">"
				+ "																								<td >"
				+ "																									Order By:"
				+ "																									"
				+ Purchase.getPurchaseorder_orderdby() + "</td>"
				+ "																								</tr> "
				+ "																								<tr  height=\"15\">"
				+ ""
				+ "																									<td >"
				+ "																										Remarks:"
				+ "																										"
				+ Purchase.getPurchaseorder_orderd_remark() + "</td>"
				+ "																									</tr>                                                                           "
				+ "																								</tbody>"
				+ "																							</table>"
				+ "																						</td>"
				+ "																					</tr>"
				+ "																					<!-- end of text content table -->"
				+ "																				</tbody>"
				+ "																			</table>"
				+ "																			<!-- end of left column -->"
				+ "" + ""
				+ "																			<!-- end of col 2 -->" + ""
				+ "																			<!-- col 3 -->"
				+ "																			<table width=\"280\" align=\"right\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidth\">"
				+ "																				<tbody>" + ""
				+ "																					<tr>"
				+ "																						<td>"
				+ "																							<!-- start of text content table -->  "
				+ "																							<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidthinner\">"
				+ "																								<tbody>"
				+ ""
				+ "																									<!-- title -->"
				+ "																									<tr>"
				+ "																										<td style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\" st-title=\"3col-title3\">"
				+ "																											<table width=\"170\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidthinner\">"
				+ "																												<tbody style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\" st-title=\"3col-title3\">"
				+ ""
				+ "																													<!-- title -->"
				+ "																													<tr height=\"30\">"
				+ "																														<td >SubTotal :</td><td>"
				+ Purchase.getPurchaseorder_subtotal_cost() + ""
				+ "																													</td>"
				+ "																												</tr>"
				+ ""
				+ "																												<tr height=\"30\">"
				+ "																													<td  >Freight :	</td><td> "
				+ Purchase.getPurchaseorder_freight() + "</td></tr>"
				+ "																													<tr height=\"30\"><td>Taxes :	 </td><td>"
				+ Purchase.getPurchaseorder_totaltax_cost() + "</td>" + ""
				+ "																													</tr>"
				+ "																													<tr height=\"40\"><td>Total :	</td><td>"
				+ Purchase.getPurchaseorder_totalcost() + "</td></tr>"
				+ "																													<tr height=\"30\"><td>Advance Paid:</td><td>"
				+ Purchase.getPurchaseorder_advancecost() + "</td></tr>"
				+ "																													<tr height=\"30\"><td>Balance :	 </td><td>"
				+ Purchase.getPurchaseorder_balancecost() + "</td>"
				+ "																													</td>"
				+ "																												</tr>"
				+ "																												<!-- end of title -->"
				+ ""
				+ "																											</tbody>"
				+ "																										</table>"
				+ ""
				+ "																									</td>"
				+ "																								</tr>"
				+ "																								<!-- end of title -->"
				+ ""
				+ "																							</tbody>"
				+ "																						</table>"
				+ "																					</td>"
				+ "																				</tr>"
				+ "																				<!-- end of text content table -->"
				+ "																			</tbody>"
				+ "																		</table>"
				+ "																	</td>"
				+ "																	<!-- spacing -->"
				+ "																	<!-- end of spacing -->"
				+ "																</tr>"
				+ "															</tbody>"
				+ "														</table>"
				+ "													</td>"
				+ "												</tr>"
				+ "												<tr>"
				+ "													<td width=\"100%\" height=\"20\"></td>"
				+ "												</tr>"
				+ "											</tbody>"
				+ "										</table>" + "									</td>"
				+ "								</tr>" + "							</tbody>"
				+ "						</table>" + "						<!-- end of 3-columns --> "
				+ "					</div>" + "<div class=\"block\">" + "		<!-- start of header -->"
				+ "		<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"header\">"
				+ "			<tbody>" + "				<tr>" + "					<td>"
				+ "						<table width=\"680\" bgcolor=\"\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\" hlitebg=\"edit\" shadow=\"edit\">"
				+ "							<tbody>" + "								<tr>"
				+ "									<td>" + "										<!-- logo -->"
				+ "										<table width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"left\" class=\"devicewidth\">"
				+ "											<tbody>"
				+ "												<tr>"
				+ "													<td width=\"200\" height=\"48\" align=\"center\" class=\"devicewidth\"><a href=\"Http://www.fleetop.com\">"
				+ "														<img src=\"http://i1.goself.xyz/QhyvOb0m3EjE7A4/css/FleetopLogo.png\" alt=\"fleetop\" border=\"0\" width=\"180\" height=\"48\" style=\"display:block; border:none; outline:none; text-decoration:none;\" class=\"col3img\"></a>"
				+ "													</td>"
				+ "												</tr>"
				+ "											</tbody>"
				+ "										</table>"
				+ "										<!-- End of logo -->"
				+ "									</td>" + "								</tr>"
				+ "							</tbody>" + "						</table>" + "					</td>"
				+ "				</tr>" + "			</tbody>" + "		</table>" + "		<!-- end of header -->"
				+ "	</div>" + "" + "					<div class=\"block\">"
				+ "						<!-- Start of preheader -->"
				+ "						<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"postfooter\">"
				+ "							<tbody>" + "								<tr>"
				+ "									<td width=\"100%\">"
				+ "										<table width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\">"
				+ "											<tbody>"
				+ "												<!-- Spacing -->"
				+ "												<tr>"
				+ "													<td width=\"100%\" height=\"5\"></td>"
				+ "												</tr>"
				+ "												<!-- Spacing -->"
				+ "												<tr>"
				+ "													<td align=\"center\" valign=\"middle\" style=\"font-family: Helvetica, arial, sans-serif; font-size: 10px;color: #999999\" st-content=\"preheader\">"
				+ "														If you receive updates. please  <a class=\"hlite\" href=\"http://www.fleetop.com\" style=\"text-decoration: none; color: #0db9ea\">www.fleetop.com</a> "
				+ "													</td>"
				+ "												</tr>"
				+ "												<!-- Spacing -->"
				+ "												<tr>"
				+ "													<td width=\"100%\" height=\"5\"></td>"
				+ "												</tr>"
				+ "												<!-- Spacing -->"
				+ "											</tbody>"
				+ "										</table>" + "									</td>"
				+ "								</tr>" + "							</tbody>"
				+ "						</table>" + "						<!-- End of preheader -->"
				+ "					</div>" + "" + "				</body></html>";

		mailSender.send(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				messageHelper.setTo(vendoremail.getVendorFirEmail());
				messageHelper.setSubject("Order Tyres Details by");
				messageHelper.setReplyTo(env.getProperty("support.email"));

				/* sent html email text */
				messageHelper.setText(sendHTML_Email_PO, true);

			}

		});

		return null;

	}
	
	@SuppressWarnings("unchecked")
	private final MimeMessage sent_Battery_Orderd_DetailsVendorEmail(final Long ID, final Locale locale, CustomUserDetails userDetails) throws Exception {

		HashMap<String, Object>  threadHM	=  PurchaseOrdersService.getPurchaseOrderDetailsHM(ID, userDetails);
		
		PurchaseOrdersDto Purchase = POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
		

		Vendor vendoremail = vendorService.getVendor(Purchase.getPurchaseorder_vendor_id());

		List<PurchaseOrdersToPartsDto> purchaseToParts = PurchaseOrdersService
				.getPurchaseOrdersToPartsforBattery(Purchase.getPurchaseorder_id(), userDetails.getCompany_id());
		// System.out.println(vendoremail.getVendorFirEmail());

		String purchaseTyreHTML = "";
		for (PurchaseOrdersToPartsDto PurchaseOrdersParts : purchaseToParts) {
			purchaseTyreHTML += "										<tr data-object-id=\"\" class=\"ng-scope\">"
					+ "											<td>" + PurchaseOrdersParts.getTYRE_MANUFACTURER()
					+ " - " + PurchaseOrdersParts.getTYRE_MODEL() + " - " + PurchaseOrdersParts.getTYRE_SIZE() + "</td>"
					+ "" + "											<td>" + PurchaseOrdersParts.getQuantity()
					+ "</td>" + "											<td>"
					+ PurchaseOrdersParts.getParteachcost() + "</td>"
					+ "											<td>" + PurchaseOrdersParts.getDiscount() + " %</td>"
					+ "											<td>" + PurchaseOrdersParts.getTax() + " %</td>"
					+ "											<td>" + PurchaseOrdersParts.getTotalcost() + "</td>"
					+ "" + "" + "										</tr>";
		}

		final String sendHTML_Email_PO = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
				+ "<title>Fleetop.in</title>" + "</head>" + "<body>" + "	<div class=\"block\">"
				+ "		<!-- Start of preheader -->"
				+ "		<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"preheader\">"
				+ "			<tbody>" + "				<tr>" + "					<td width=\"100%\">"
				+ "						<table width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\">"
				+ "							<tbody>" + "								<!-- Spacing -->"
				+ "								<tr>"
				+ "									<td width=\"100%\" height=\"5\"></td>"
				+ "								</tr>" + "								<!-- Spacing -->"
				+ "								<tr>"
				+ "									<td align=\"right\" valign=\"middle\" style=\"font-family: Helvetica, arial, sans-serif; font-size: 10px;color: #999999\" st-content=\"preheader\">"
				+ "										If you cannot read this email, please  <a class=\"hlite\" href=\"#\" style=\"text-decoration: none; color: #0db9ea\">click here</a> "
				+ "									</td>" + "								</tr>"
				+ "								<!-- Spacing -->" + "								<tr>"
				+ "									<td width=\"100%\" height=\"5\"></td>"
				+ "								</tr>" + "								<!-- Spacing -->"
				+ "							</tbody>" + "						</table>" + "					</td>"
				+ "				</tr>" + "			</tbody>" + "		</table>" + "		<!-- End of preheader -->"
				+ "	</div>" + "	<div class=\"block\">" + "		<!-- start of header -->"
				+ "		<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"header\">"
				+ "			<tbody>" + "				<tr>" + "					<td>"
				+ "						<table width=\"680\" bgcolor=\"#ffde12\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\" hlitebg=\"edit\" shadow=\"edit\">"
				+ "							<tbody>" + "								<tr>"
				+ "									<td>" + "										"
				+ "										<!-- company -->"
				+ "										<table width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"right\" class=\"devicewidth\">"
				+ "											<tbody>"
				+ "												<tr>"
				+ "													<td width=\"270\" valign=\"middle\" style=\"font-family: Helvetica, Arial, sans-serif;font-size: 24px; color: #ffffff;line-height: 24px; padding: 10px 0;\" align=\"center\" class=\"menu\" st-content=\"menu\">"
				+ "														<a href=\"#\" style=\"text-decoration: none; color: #222;\">"
				+ Purchase.getPurchaseorder_buyer() + "</a>"
				+ "													</td>"
				+ "													<td width=\"20\"></td>"
				+ "												</tr>"
				+ "											</tbody>"
				+ "										</table>"
				+ "										<!-- End of Menu -->"
				+ "									</td>" + "								</tr>"
				+ "							</tbody>" + "						</table>" + "					</td>"
				+ "				</tr>" + "			</tbody>" + "		</table>" + "		<!-- end of header -->"
				+ "	</div><div class=\"block\">" + ""
				+ "	<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"bigimage\">"
				+ "		<tbody>" + "			<tr>" + "				<td>"
				+ "					<table  width=\"680\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" bgcolor=\"#999999\">"
				+ "						<tr>" + "							<td>"
				+ "								<table  width=\"680\" cellspacing=\"0\" cellpadding=\"10\" border=\"0\">"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td width=\"33%\" height=\"40\"> </td>"
				+ "										<td width=\"34%\" style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\" st-title=\"3col-title1\">Purchase Order "
				+ Purchase.getPurchaseorder_Number() + "</td>"
				+ "										<td width=\"33%\"> </td>"
				+ "									</tr>"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td colspan=\"2\">Vendor : "
				+ Purchase.getPurchaseorder_vendor_name() + " </td>"
				+ "										<td>Terms:	" + Purchase.getPurchaseorder_terms() + "</td>"
				+ "									</tr>"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td height=\"67\" > Order Date: "
				+ Purchase.getPurchaseorder_created_on() + "</td>"
				+ "										<td>Buyer Name:	" + Purchase.getPurchaseorder_buyer() + "</td>"
				+ "										<td>Ship To:	" + Purchase.getPurchaseorder_shiplocation()
				+ "</td>" + "									</tr>"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td height=\"67\" >"
				+ "											Required date: "
				+ Purchase.getPurchaseorder_requied_on() + ""
				+ "										</td>"
				+ "										<td>Buyer Address :	" + Purchase.getPurchaseorder_buyeraddress()
				+ "" + "										</td>"
				+ "										<td>Ship Address:	"
				+ Purchase.getPurchaseorder_shiplocation_address() + "</td>"
				+ "									</tr>"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td height=\"67\" >"
				+ "											Quote No : " + Purchase.getPurchaseorder_quotenumber()
				+ "</td>" + "											<td>"
				+ "												Ship via :	" + Purchase.getPurchaseorder_shipvia()
				+ "</td>" + "												<td>"
				+ "													Contact: "
				+ Purchase.getPurchaseorder_shiplocation_mobile() + "</td>"
				+ "												</tr>"
				+ "											</table>" + "										</td>"
				+ "									</tr>" + "								</table>"
				+ "							</td>" + "						</tr>" + "					</tbody>"
				+ "				</table>" + "" + "" + "				<!-- image + text -->"
				+ "				<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"bigimage\">"
				+ "					<tbody>" + "						<tr>" + "							<td>"
				+ "								<table bgcolor=\"#ffffff\" width=\"680\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"devicewidth\" modulebg=\"edit\">"
				+ "									<tbody>" + "										<tr>"
				+ "											<td>"
				+ "												<table width=\"680\" align=\"left\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"devicewidthinner\" >"
				+ "													<tbody>" + ""
				+ "														<!-- title -->"
				+ "														<tr style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\" st-title=\"rightimage-title\">"
				+ "" + "															<td width=\"33%\" height=\"67\">"
				+ "																Battery Details"
				+ "															</td>" + ""
				+ "														</tr>"
				+ "														<!-- end of title -->"
				+ "													</tbody>"
				+ "												</table>"
				+ "											</td>" + "										</tr>"
				+ "									</tbody>" + "								</table>"
				+ "							</td>" + "						</tr>" + "					</tbody>"
				+ "				</table>" + "			</div>" + "" + "			<div class=\"block\">"
				+ "				<!-- Battery Details -->  "
				+ "				<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"3columns\">"
				+ "					<tbody>" + "						<tr>" + "							<td>"
				+ "								<table bgcolor=\"#ffffff\" width=\"680\" cellpadding=\"10\" cellspacing=\"0\" border=\"1\" align=\"center\" class=\"devicewidth\" modulebg=\"edit\">"
				+ "									<thead>"
				+ "										<tr class=\"breadcrumb\">"
				+ "											<th>Battery</th>"
				+ "											<th>Qty</th>"
				+ "											<th>Each</th>"
				+ "											<th>Dis</th>"
				+ "											<th>Tax</th>"
				+ "											<th>Total</th>"
				+ "										</tr>" + "									</thead>"
				+ "									<tbody>" + purchaseTyreHTML
				+ "									</tbody>" + "								</table>"
				+ "							</td>" + "						</tr>" + "					</tbody>"
				+ "				</table>" + "				<!-- end parts Details -->" + "			</div>" + "" + ""
				+ "" + "			<div class=\"block\">" + "				<!-- 3-columns -->  "
				+ "				<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"3columns\">"
				+ "					<tbody>" + "						<tr>" + "							<td>"
				+ "								<table bgcolor=\"#ffffff\" width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\" modulebg=\"edit\">"
				+ "									<tbody>" + "										<tr>"
				+ "											<td width=\"100%\" height=\"20\"></td>"
				+ "										</tr>" + "										<tr>"
				+ "											<td>"
				+ "												<table width=\"540\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\">"
				+ "													<tbody>"
				+ "														<tr>"
				+ "															<td>"
				+ "																<table width=\"260\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidth\">"
				+ "																	<tbody>" + ""
				+ "																		<!-- Spacing -->"
				+ "																		<tr>"
				+ "																			<td width=\"100%\" height=\"15\" style=\"font-size:1px; line-height:1px; mso-line-height-rule: exactly;\"> </td>"
				+ "																		</tr>"
				+ "																		<!-- Spacing -->"
				+ "																		<tr>"
				+ "																			<td>"
				+ "																				<!-- start of text content table -->"
				+ "																				<table width=\"260\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidthinner\">"
				+ "																					<tbody>"
				+ "																						<!-- title -->"
				+ "																						<tr>"
				+ "																							<td width=\"100%\"  height=\"15\">"
				+ "																								Order Date:"
				+ "																								:"
				+ Purchase.getPurchaseorder_orderddate() + "</td>"
				+ "																							</tr>"
				+ "																							<tr  height=\"15\">"
				+ "																								<td >"
				+ "																									Order By:"
				+ "																									"
				+ Purchase.getPurchaseorder_orderdby() + "</td>"
				+ "																								</tr> "
				+ "																								<tr  height=\"15\">"
				+ ""
				+ "																									<td >"
				+ "																										Remarks:"
				+ "																										"
				+ Purchase.getPurchaseorder_orderd_remark() + "</td>"
				+ "																									</tr>                                                                           "
				+ "																								</tbody>"
				+ "																							</table>"
				+ "																						</td>"
				+ "																					</tr>"
				+ "																					<!-- end of text content table -->"
				+ "																				</tbody>"
				+ "																			</table>"
				+ "																			<!-- end of left column -->"
				+ "" + ""
				+ "																			<!-- end of col 2 -->" + ""
				+ "																			<!-- col 3 -->"
				+ "																			<table width=\"280\" align=\"right\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidth\">"
				+ "																				<tbody>" + ""
				+ "																					<tr>"
				+ "																						<td>"
				+ "																							<!-- start of text content table -->  "
				+ "																							<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidthinner\">"
				+ "																								<tbody>"
				+ ""
				+ "																									<!-- title -->"
				+ "																									<tr>"
				+ "																										<td style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\" st-title=\"3col-title3\">"
				+ "																											<table width=\"170\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidthinner\">"
				+ "																												<tbody style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\" st-title=\"3col-title3\">"
				+ ""
				+ "																													<!-- title -->"
				+ "																													<tr height=\"30\">"
				+ "																														<td >SubTotal :</td><td>"
				+ Purchase.getPurchaseorder_subtotal_cost() + ""
				+ "																													</td>"
				+ "																												</tr>"
				+ ""
				+ "																												<tr height=\"30\">"
				+ "																													<td  >Freight :	</td><td> "
				+ Purchase.getPurchaseorder_freight() + "</td></tr>"
				+ "																													<tr height=\"30\"><td>Taxes :	 </td><td>"
				+ Purchase.getPurchaseorder_totaltax_cost() + "</td>" + ""
				+ "																													</tr>"
				+ "																													<tr height=\"40\"><td>Total :	</td><td>"
				+ Purchase.getPurchaseorder_totalcost() + "</td></tr>"
				+ "																													<tr height=\"30\"><td>Advance Paid:</td><td>"
				+ Purchase.getPurchaseorder_advancecost() + "</td></tr>"
				+ "																													<tr height=\"30\"><td>Balance :	 </td><td>"
				+ Purchase.getPurchaseorder_balancecost() + "</td>"
				+ "																													</td>"
				+ "																												</tr>"
				+ "																												<!-- end of title -->"
				+ ""
				+ "																											</tbody>"
				+ "																										</table>"
				+ ""
				+ "																									</td>"
				+ "																								</tr>"
				+ "																								<!-- end of title -->"
				+ ""
				+ "																							</tbody>"
				+ "																						</table>"
				+ "																					</td>"
				+ "																				</tr>"
				+ "																				<!-- end of text content table -->"
				+ "																			</tbody>"
				+ "																		</table>"
				+ "																	</td>"
				+ "																	<!-- spacing -->"
				+ "																	<!-- end of spacing -->"
				+ "																</tr>"
				+ "															</tbody>"
				+ "														</table>"
				+ "													</td>"
				+ "												</tr>"
				+ "												<tr>"
				+ "													<td width=\"100%\" height=\"20\"></td>"
				+ "												</tr>"
				+ "											</tbody>"
				+ "										</table>" + "									</td>"
				+ "								</tr>" + "							</tbody>"
				+ "						</table>" + "						<!-- end of 3-columns --> "
				+ "					</div>" + "<div class=\"block\">" + "		<!-- start of header -->"
				+ "		<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"header\">"
				+ "			<tbody>" + "				<tr>" + "					<td>"
				+ "						<table width=\"680\" bgcolor=\"\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\" hlitebg=\"edit\" shadow=\"edit\">"
				+ "							<tbody>" + "								<tr>"
				+ "									<td>" + "										<!-- logo -->"
				+ "										<table width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"left\" class=\"devicewidth\">"
				+ "											<tbody>"
				+ "												<tr>"
				+ "													<td width=\"200\" height=\"48\" align=\"center\" class=\"devicewidth\"><a href=\"Http://www.fleetop.com\">"
				+ "														<img src=\"http://i1.goself.xyz/QhyvOb0m3EjE7A4/css/FleetopLogo.png\" alt=\"fleetop\" border=\"0\" width=\"180\" height=\"48\" style=\"display:block; border:none; outline:none; text-decoration:none;\" class=\"col3img\"></a>"
				+ "													</td>"
				+ "												</tr>"
				+ "											</tbody>"
				+ "										</table>"
				+ "										<!-- End of logo -->"
				+ "									</td>" + "								</tr>"
				+ "							</tbody>" + "						</table>" + "					</td>"
				+ "				</tr>" + "			</tbody>" + "		</table>" + "		<!-- end of header -->"
				+ "	</div>" + "" + "					<div class=\"block\">"
				+ "						<!-- Start of preheader -->"
				+ "						<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"postfooter\">"
				+ "							<tbody>" + "								<tr>"
				+ "									<td width=\"100%\">"
				+ "										<table width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\">"
				+ "											<tbody>"
				+ "												<!-- Spacing -->"
				+ "												<tr>"
				+ "													<td width=\"100%\" height=\"5\"></td>"
				+ "												</tr>"
				+ "												<!-- Spacing -->"
				+ "												<tr>"
				+ "													<td align=\"center\" valign=\"middle\" style=\"font-family: Helvetica, arial, sans-serif; font-size: 10px;color: #999999\" st-content=\"preheader\">"
				+ "														If you receive updates. please  <a class=\"hlite\" href=\"http://www.fleetop.com\" style=\"text-decoration: none; color: #0db9ea\">www.fleetop.com</a> "
				+ "													</td>"
				+ "												</tr>"
				+ "												<!-- Spacing -->"
				+ "												<tr>"
				+ "													<td width=\"100%\" height=\"5\"></td>"
				+ "												</tr>"
				+ "												<!-- Spacing -->"
				+ "											</tbody>"
				+ "										</table>" + "									</td>"
				+ "								</tr>" + "							</tbody>"
				+ "						</table>" + "						<!-- End of preheader -->"
				+ "					</div>" + "" + "				</body></html>";

		mailSender.send(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				messageHelper.setTo(vendoremail.getVendorFirEmail());
				messageHelper.setSubject("Order Battery Details by");
				messageHelper.setReplyTo(env.getProperty("support.email"));

				/* sent html email text */
				messageHelper.setText(sendHTML_Email_PO, true);

			}

		});

		return null;

	}

	
	@SuppressWarnings("unchecked")
	private final MimeMessage sent_Cloth_Orderd_DetailsVendorEmail(final Long ID, final Locale locale, CustomUserDetails userDetails) throws Exception {

		HashMap<String, Object>  threadHM	=  PurchaseOrdersService.getPurchaseOrderDetailsHM(ID, userDetails);
		
		PurchaseOrdersDto Purchase = POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
		

		Vendor vendoremail = vendorService.getVendor(Purchase.getPurchaseorder_vendor_id());

		List<PurchaseOrdersToPartsDto> purchaseToParts = PurchaseOrdersService
				.getPurchaseOrdersTasksToCloth(Purchase.getPurchaseorder_id(), userDetails.getCompany_id());
		// System.out.println(vendoremail.getVendorFirEmail());

		String purchaseTyreHTML = "";
		for (PurchaseOrdersToPartsDto PurchaseOrdersParts : purchaseToParts) {
			purchaseTyreHTML += "										<tr data-object-id=\"\" class=\"ng-scope\">"
					+ "											<td>" + PurchaseOrdersParts.getClothTypeName() + "</td>"
					+ "" + "											<td>" + PurchaseOrdersParts.getQuantity()
					+ "</td>" + "											<td>"
					+ PurchaseOrdersParts.getParteachcost() + "</td>"
					+ "											<td>" + PurchaseOrdersParts.getDiscount() + " %</td>"
					+ "											<td>" + PurchaseOrdersParts.getTax() + " %</td>"
					+ "											<td>" + PurchaseOrdersParts.getTotalcost() + "</td>"
					+ "" + "" + "										</tr>";
		}

		final String sendHTML_Email_PO = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
				+ "<title>Fleetop.in</title>" + "</head>" + "<body>" + "	<div class=\"block\">"
				+ "		<!-- Start of preheader -->"
				+ "		<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"preheader\">"
				+ "			<tbody>" + "				<tr>" + "					<td width=\"100%\">"
				+ "						<table width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\">"
				+ "							<tbody>" + "								<!-- Spacing -->"
				+ "								<tr>"
				+ "									<td width=\"100%\" height=\"5\"></td>"
				+ "								</tr>" + "								<!-- Spacing -->"
				+ "								<tr>"
				+ "									<td align=\"right\" valign=\"middle\" style=\"font-family: Helvetica, arial, sans-serif; font-size: 10px;color: #999999\" st-content=\"preheader\">"
				+ "										If you cannot read this email, please  <a class=\"hlite\" href=\"#\" style=\"text-decoration: none; color: #0db9ea\">click here</a> "
				+ "									</td>" + "								</tr>"
				+ "								<!-- Spacing -->" + "								<tr>"
				+ "									<td width=\"100%\" height=\"5\"></td>"
				+ "								</tr>" + "								<!-- Spacing -->"
				+ "							</tbody>" + "						</table>" + "					</td>"
				+ "				</tr>" + "			</tbody>" + "		</table>" + "		<!-- End of preheader -->"
				+ "	</div>" + "	<div class=\"block\">" + "		<!-- start of header -->"
				+ "		<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"header\">"
				+ "			<tbody>" + "				<tr>" + "					<td>"
				+ "						<table width=\"680\" bgcolor=\"#ffde12\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\" hlitebg=\"edit\" shadow=\"edit\">"
				+ "							<tbody>" + "								<tr>"
				+ "									<td>" + "										"
				+ "										<!-- company -->"
				+ "										<table width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"right\" class=\"devicewidth\">"
				+ "											<tbody>"
				+ "												<tr>"
				+ "													<td width=\"270\" valign=\"middle\" style=\"font-family: Helvetica, Arial, sans-serif;font-size: 24px; color: #ffffff;line-height: 24px; padding: 10px 0;\" align=\"center\" class=\"menu\" st-content=\"menu\">"
				+ "														<a href=\"#\" style=\"text-decoration: none; color: #222;\">"
				+ Purchase.getPurchaseorder_buyer() + "</a>"
				+ "													</td>"
				+ "													<td width=\"20\"></td>"
				+ "												</tr>"
				+ "											</tbody>"
				+ "										</table>"
				+ "										<!-- End of Menu -->"
				+ "									</td>" + "								</tr>"
				+ "							</tbody>" + "						</table>" + "					</td>"
				+ "				</tr>" + "			</tbody>" + "		</table>" + "		<!-- end of header -->"
				+ "	</div><div class=\"block\">" + ""
				+ "	<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"bigimage\">"
				+ "		<tbody>" + "			<tr>" + "				<td>"
				+ "					<table  width=\"680\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" bgcolor=\"#999999\">"
				+ "						<tr>" + "							<td>"
				+ "								<table  width=\"680\" cellspacing=\"0\" cellpadding=\"10\" border=\"0\">"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td width=\"33%\" height=\"40\"> </td>"
				+ "										<td width=\"34%\" style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\" st-title=\"3col-title1\">Purchase Order "
				+ Purchase.getPurchaseorder_Number() + "</td>"
				+ "										<td width=\"33%\"> </td>"
				+ "									</tr>"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td colspan=\"2\">Vendor : "
				+ Purchase.getPurchaseorder_vendor_name() + " </td>"
				+ "										<td>Terms:	" + Purchase.getPurchaseorder_terms() + "</td>"
				+ "									</tr>"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td height=\"67\" > Order Date: "
				+ Purchase.getPurchaseorder_created_on() + "</td>"
				+ "										<td>Buyer Name:	" + Purchase.getPurchaseorder_buyer() + "</td>"
				+ "										<td>Ship To:	" + Purchase.getPurchaseorder_shiplocation()
				+ "</td>" + "									</tr>"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td height=\"67\" >"
				+ "											Required date: "
				+ Purchase.getPurchaseorder_requied_on() + ""
				+ "										</td>"
				+ "										<td>Buyer Address :	" + Purchase.getPurchaseorder_buyeraddress()
				+ "" + "										</td>"
				+ "										<td>Ship Address:	"
				+ Purchase.getPurchaseorder_shiplocation_address() + "</td>"
				+ "									</tr>"
				+ "									<tr bgcolor=\"#ffffff\">"
				+ "										<td height=\"67\" >"
				+ "											Quote No : " + Purchase.getPurchaseorder_quotenumber()
				+ "</td>" + "											<td>"
				+ "												Ship via :	" + Purchase.getPurchaseorder_shipvia()
				+ "</td>" + "												<td>"
				+ "													Contact: "
				+ Purchase.getPurchaseorder_shiplocation_mobile() + "</td>"
				+ "												</tr>"
				+ "											</table>" + "										</td>"
				+ "									</tr>" + "								</table>"
				+ "							</td>" + "						</tr>" + "					</tbody>"
				+ "				</table>" + "" + "" + "				<!-- image + text -->"
				+ "				<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"bigimage\">"
				+ "					<tbody>" + "						<tr>" + "							<td>"
				+ "								<table bgcolor=\"#ffffff\" width=\"680\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"devicewidth\" modulebg=\"edit\">"
				+ "									<tbody>" + "										<tr>"
				+ "											<td>"
				+ "												<table width=\"680\" align=\"left\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"devicewidthinner\" >"
				+ "													<tbody>" + ""
				+ "														<!-- title -->"
				+ "														<tr style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\" st-title=\"rightimage-title\">"
				+ "" + "															<td width=\"33%\" height=\"67\">"
				+ "																Battery Details"
				+ "															</td>" + ""
				+ "														</tr>"
				+ "														<!-- end of title -->"
				+ "													</tbody>"
				+ "												</table>"
				+ "											</td>" + "										</tr>"
				+ "									</tbody>" + "								</table>"
				+ "							</td>" + "						</tr>" + "					</tbody>"
				+ "				</table>" + "			</div>" + "" + "			<div class=\"block\">"
				+ "				<!-- Battery Details -->  "
				+ "				<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"3columns\">"
				+ "					<tbody>" + "						<tr>" + "							<td>"
				+ "								<table bgcolor=\"#ffffff\" width=\"680\" cellpadding=\"10\" cellspacing=\"0\" border=\"1\" align=\"center\" class=\"devicewidth\" modulebg=\"edit\">"
				+ "									<thead>"
				+ "										<tr class=\"breadcrumb\">"
				+ "											<th>Upholstery</th>"
				+ "											<th>Qty</th>"
				+ "											<th>Each</th>"
				+ "											<th>Dis</th>"
				+ "											<th>Tax</th>"
				+ "											<th>Total</th>"
				+ "										</tr>" + "									</thead>"
				+ "									<tbody>" + purchaseTyreHTML
				+ "									</tbody>" + "								</table>"
				+ "							</td>" + "						</tr>" + "					</tbody>"
				+ "				</table>" + "				<!-- end parts Details -->" + "			</div>" + "" + ""
				+ "" + "			<div class=\"block\">" + "				<!-- 3-columns -->  "
				+ "				<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"3columns\">"
				+ "					<tbody>" + "						<tr>" + "							<td>"
				+ "								<table bgcolor=\"#ffffff\" width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\" modulebg=\"edit\">"
				+ "									<tbody>" + "										<tr>"
				+ "											<td width=\"100%\" height=\"20\"></td>"
				+ "										</tr>" + "										<tr>"
				+ "											<td>"
				+ "												<table width=\"540\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\">"
				+ "													<tbody>"
				+ "														<tr>"
				+ "															<td>"
				+ "																<table width=\"260\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidth\">"
				+ "																	<tbody>" + ""
				+ "																		<!-- Spacing -->"
				+ "																		<tr>"
				+ "																			<td width=\"100%\" height=\"15\" style=\"font-size:1px; line-height:1px; mso-line-height-rule: exactly;\"> </td>"
				+ "																		</tr>"
				+ "																		<!-- Spacing -->"
				+ "																		<tr>"
				+ "																			<td>"
				+ "																				<!-- start of text content table -->"
				+ "																				<table width=\"260\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidthinner\">"
				+ "																					<tbody>"
				+ "																						<!-- title -->"
				+ "																						<tr>"
				+ "																							<td width=\"100%\"  height=\"15\">"
				+ "																								Order Date:"
				+ "																								:"
				+ Purchase.getPurchaseorder_orderddate() + "</td>"
				+ "																							</tr>"
				+ "																							<tr  height=\"15\">"
				+ "																								<td >"
				+ "																									Order By:"
				+ "																									"
				+ Purchase.getPurchaseorder_orderdby() + "</td>"
				+ "																								</tr> "
				+ "																								<tr  height=\"15\">"
				+ ""
				+ "																									<td >"
				+ "																										Remarks:"
				+ "																										"
				+ Purchase.getPurchaseorder_orderd_remark() + "</td>"
				+ "																									</tr>                                                                           "
				+ "																								</tbody>"
				+ "																							</table>"
				+ "																						</td>"
				+ "																					</tr>"
				+ "																					<!-- end of text content table -->"
				+ "																				</tbody>"
				+ "																			</table>"
				+ "																			<!-- end of left column -->"
				+ "" + ""
				+ "																			<!-- end of col 2 -->" + ""
				+ "																			<!-- col 3 -->"
				+ "																			<table width=\"280\" align=\"right\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidth\">"
				+ "																				<tbody>" + ""
				+ "																					<tr>"
				+ "																						<td>"
				+ "																							<!-- start of text content table -->  "
				+ "																							<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidthinner\">"
				+ "																								<tbody>"
				+ ""
				+ "																									<!-- title -->"
				+ "																									<tr>"
				+ "																										<td style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\" st-title=\"3col-title3\">"
				+ "																											<table width=\"170\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"devicewidthinner\">"
				+ "																												<tbody style=\"font-family: Helvetica, arial, sans-serif; font-size: 18px; color: #333333; text-align:left;line-height: 20px;\" st-title=\"3col-title3\">"
				+ ""
				+ "																													<!-- title -->"
				+ "																													<tr height=\"30\">"
				+ "																														<td >SubTotal :</td><td>"
				+ Purchase.getPurchaseorder_subtotal_cost() + ""
				+ "																													</td>"
				+ "																												</tr>"
				+ ""
				+ "																												<tr height=\"30\">"
				+ "																													<td  >Freight :	</td><td> "
				+ Purchase.getPurchaseorder_freight() + "</td></tr>"
				+ "																													<tr height=\"30\"><td>Taxes :	 </td><td>"
				+ Purchase.getPurchaseorder_totaltax_cost() + "</td>" + ""
				+ "																													</tr>"
				+ "																													<tr height=\"40\"><td>Total :	</td><td>"
				+ Purchase.getPurchaseorder_totalcost() + "</td></tr>"
				+ "																													<tr height=\"30\"><td>Advance Paid:</td><td>"
				+ Purchase.getPurchaseorder_advancecost() + "</td></tr>"
				+ "																													<tr height=\"30\"><td>Balance :	 </td><td>"
				+ Purchase.getPurchaseorder_balancecost() + "</td>"
				+ "																													</td>"
				+ "																												</tr>"
				+ "																												<!-- end of title -->"
				+ ""
				+ "																											</tbody>"
				+ "																										</table>"
				+ ""
				+ "																									</td>"
				+ "																								</tr>"
				+ "																								<!-- end of title -->"
				+ ""
				+ "																							</tbody>"
				+ "																						</table>"
				+ "																					</td>"
				+ "																				</tr>"
				+ "																				<!-- end of text content table -->"
				+ "																			</tbody>"
				+ "																		</table>"
				+ "																	</td>"
				+ "																	<!-- spacing -->"
				+ "																	<!-- end of spacing -->"
				+ "																</tr>"
				+ "															</tbody>"
				+ "														</table>"
				+ "													</td>"
				+ "												</tr>"
				+ "												<tr>"
				+ "													<td width=\"100%\" height=\"20\"></td>"
				+ "												</tr>"
				+ "											</tbody>"
				+ "										</table>" + "									</td>"
				+ "								</tr>" + "							</tbody>"
				+ "						</table>" + "						<!-- end of 3-columns --> "
				+ "					</div>" + "<div class=\"block\">" + "		<!-- start of header -->"
				+ "		<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"header\">"
				+ "			<tbody>" + "				<tr>" + "					<td>"
				+ "						<table width=\"680\" bgcolor=\"\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\" hlitebg=\"edit\" shadow=\"edit\">"
				+ "							<tbody>" + "								<tr>"
				+ "									<td>" + "										<!-- logo -->"
				+ "										<table width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"left\" class=\"devicewidth\">"
				+ "											<tbody>"
				+ "												<tr>"
				+ "													<td width=\"200\" height=\"48\" align=\"center\" class=\"devicewidth\"><a href=\"Http://www.fleetop.com\">"
				+ "														<img src=\"http://i1.goself.xyz/QhyvOb0m3EjE7A4/css/FleetopLogo.png\" alt=\"fleetop\" border=\"0\" width=\"180\" height=\"48\" style=\"display:block; border:none; outline:none; text-decoration:none;\" class=\"col3img\"></a>"
				+ "													</td>"
				+ "												</tr>"
				+ "											</tbody>"
				+ "										</table>"
				+ "										<!-- End of logo -->"
				+ "									</td>" + "								</tr>"
				+ "							</tbody>" + "						</table>" + "					</td>"
				+ "				</tr>" + "			</tbody>" + "		</table>" + "		<!-- end of header -->"
				+ "	</div>" + "" + "					<div class=\"block\">"
				+ "						<!-- Start of preheader -->"
				+ "						<table width=\"100%\" bgcolor=\"#f6f4f5\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"backgroundTable\" st-sortable=\"postfooter\">"
				+ "							<tbody>" + "								<tr>"
				+ "									<td width=\"100%\">"
				+ "										<table width=\"680\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" class=\"devicewidth\">"
				+ "											<tbody>"
				+ "												<!-- Spacing -->"
				+ "												<tr>"
				+ "													<td width=\"100%\" height=\"5\"></td>"
				+ "												</tr>"
				+ "												<!-- Spacing -->"
				+ "												<!-- Spacing -->"
				+ "												<tr>"
				+ "													<td width=\"100%\" height=\"5\"></td>"
				+ "												</tr>"
				+ "												<!-- Spacing -->"
				+ "											</tbody>"
				+ "										</table>" + "									</td>"
				+ "								</tr>" + "							</tbody>"
				+ "						</table>" + "						<!-- End of preheader -->"
				+ "					</div>" + "" + "				</body></html>";

		mailSender.send(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				messageHelper.setTo(vendoremail.getVendorFirEmail());
				messageHelper.setSubject("Order Upholstery Details by");
				messageHelper.setReplyTo(env.getProperty("support.email"));

				/* sent html email text */
				messageHelper.setText(sendHTML_Email_PO, true);

			}

		});

		return null;

	}
	
	
	@RequestMapping(value = "/vendorTypeByName",  produces="application/json", method = RequestMethod.POST)
	public void vendorTypeByName(@RequestParam HashMap<Object, Object> allRequestParams, final HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= vendorTypeService.getVendorTypeByName(valueInObject);
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(valueOutObject));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@RequestMapping(value = "/getVendorListByVendorType", method = RequestMethod.POST)
	public void getVendorListByVendorType(Map<String, Object> map, @RequestParam("term") final String term,@RequestParam("text") final String vendorTypeId,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<Vendor> addresses = new ArrayList<Vendor>();
		List<Vendor> vendor = vendorService.searchVendorByNameAndType(term, vendorTypeId, userDetails.getCompany_id());
		if (vendor != null && !vendor.isEmpty()) {
			for (Vendor add : vendor) {
				Vendor wadd = new Vendor();
				if (add != null) {
					wadd.setVendorId(add.getVendorId());
					wadd.setVendorName(add.getVendorName());
					wadd.setVendorLocation(add.getVendorLocation());
					addresses.add(wadd);
				}
			}
		}
		Gson gson = new Gson();
	
	

		response.getWriter().write(gson.toJson(addresses));
	}
	
	
	@GetMapping(value ="/getLastPartDetails")
	public void getLastPartDetails(@RequestParam(value = "PARTID", required = true) Long PART_ID,@RequestParam("partType") short partType,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<PurchaseOrdersToPartsDto> purchaseOrdersToPartsDtoList =PurchaseOrdersService.getLeatestPartFromPO(PART_ID, partType);
		if(purchaseOrdersToPartsDtoList == null)
			purchaseOrdersToPartsDtoList = new ArrayList<>();
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(purchaseOrdersToPartsDtoList));
	}
	
	@RequestMapping(value ="/getLastTyreDetails", method = RequestMethod.GET)
	public void getLastTyreDetails(@RequestParam(value = "manufacturer", required = true) Long manufacturer,@RequestParam("tyremodel") Long tyremodel,@RequestParam("PARTID") Long tyreSize,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		PurchaseOrdersToPartsDto purchaseOrdersToPartsDto =PurchaseOrdersService.getLeatestTyreFromPO(tyreSize, manufacturer, tyremodel);
		PurchaseOrdersToPartsDto watt= new PurchaseOrdersToPartsDto();
		if (purchaseOrdersToPartsDto != null) {
			watt.setPurchaseorderto_partid(purchaseOrdersToPartsDto.getPurchaseorderto_partid());
			watt.setParteachcost(purchaseOrdersToPartsDto.getParteachcost());
			watt.setQuantity(purchaseOrdersToPartsDto.getQuantity());
			watt.setTax(purchaseOrdersToPartsDto.getTax());
			watt.setDiscount(purchaseOrdersToPartsDto.getDiscount());
			watt.setPurchaseorder_id(purchaseOrdersToPartsDto.getPurchaseorder_id());
			watt.setPurchaseorder_Number(purchaseOrdersToPartsDto.getPurchaseorder_Number());
			watt.setTotalcost(purchaseOrdersToPartsDto.getTotalcost());
			
		}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(watt));
	}
	
	@RequestMapping(value ="/getLastBatteryDetails", method = RequestMethod.GET)
	public void getLastBatteryDetails(@RequestParam(value = "manufacturer", required = true) Long manufacturer,@RequestParam("batterrymodel") Long batterrymodel,@RequestParam("PARTID") Long batteryCapacity,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		PurchaseOrdersToPartsDto purchaseOrdersToPartsDto =PurchaseOrdersService.getLeatestBatteryFromPO(batteryCapacity, manufacturer, batterrymodel);
		PurchaseOrdersToPartsDto watt= new PurchaseOrdersToPartsDto();
		if (purchaseOrdersToPartsDto != null) {
			watt.setPurchaseorderto_partid(purchaseOrdersToPartsDto.getPurchaseorderto_partid());
			watt.setParteachcost(purchaseOrdersToPartsDto.getParteachcost());
			watt.setQuantity(purchaseOrdersToPartsDto.getQuantity());
			watt.setTax(purchaseOrdersToPartsDto.getTax());
			watt.setDiscount(purchaseOrdersToPartsDto.getDiscount());
			watt.setPurchaseorder_id(purchaseOrdersToPartsDto.getPurchaseorder_id());
			watt.setPurchaseorder_Number(purchaseOrdersToPartsDto.getPurchaseorder_Number());
			watt.setTotalcost(purchaseOrdersToPartsDto.getTotalcost());
			
		}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(watt));
	}
	
	@RequestMapping(value ="/getLastUreaDetails", method = RequestMethod.GET)
	public void getLastUreaDetails(@RequestParam(value = "manufacturerId", required = true) Long manufacturer,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		PurchaseOrdersToPartsDto purchaseOrdersToPartsDto =PurchaseOrdersService.getLastUreaDetailsFromPO(manufacturer);
		PurchaseOrdersToPartsDto watt= new PurchaseOrdersToPartsDto();
		if (purchaseOrdersToPartsDto != null) {
			watt.setPurchaseorderto_partid(purchaseOrdersToPartsDto.getPurchaseorderto_partid());
			watt.setParteachcost(purchaseOrdersToPartsDto.getParteachcost());
			watt.setQuantity(purchaseOrdersToPartsDto.getQuantity());
			watt.setTax(purchaseOrdersToPartsDto.getTax());
			watt.setDiscount(purchaseOrdersToPartsDto.getDiscount());
			watt.setPurchaseorder_id(purchaseOrdersToPartsDto.getPurchaseorder_id());
			watt.setPurchaseorder_Number(purchaseOrdersToPartsDto.getPurchaseorder_Number());
			watt.setTotalcost(purchaseOrdersToPartsDto.getTotalcost());
			
		}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(watt));
	}
	
	//PurchaseOrders_APPROVED
	@RequestMapping(value = "/PurchaseOrders_APPROVED/{pageNumber}", method = RequestMethod.GET)
	public String PurchaseOrders_APPROVED(@PathVariable Integer pageNumber, Model model) throws Exception {

		CustomUserDetails userDetails = null;
		HashMap<String,Object> poConfiguration = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			poConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			Page<PurchaseOrders> page = PurchaseOrdersService.getDeployment_Page_PurchaseOrdersApproval(PurchaseOrderType.PURCHASE_STATUS_APPROVED, pageNumber,
					userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("StatuesCount", page.getTotalElements());
			List<PurchaseOrdersDto> pageList = POBL.getListofPurchaseOrders(
					PurchaseOrdersService.listOpenPurchaseOrdersByApprovalStatus(PurchaseOrderType.PURCHASE_STATUS_APPROVED, pageNumber, userDetails.getCompany_id()));

			Object[] count = PurchaseOrdersService.countTotalPurchaseOrder(userDetails);			
			model.addAttribute("REQUISITION", (Long) count[1]);
			model.addAttribute("ORDERED", (Long) count[2]);
			model.addAttribute("RECEIVED", (Long) count[3]);
			model.addAttribute("COMPLETED", (Long) count[4]);
			model.addAttribute("REQUISITIONAPPROVED", (Long) count[5]);
			
			model.addAttribute("PurchaseOrder", pageList);
			model.addAttribute("configuration", poConfiguration);
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("PurchaseOrdersDto Page:", e);
			e.printStackTrace();
		}
		return "PoNewApproved";
	}
}