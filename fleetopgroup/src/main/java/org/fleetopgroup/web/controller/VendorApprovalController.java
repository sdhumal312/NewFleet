package org.fleetopgroup.web.controller;
/**
 * @author fleetop
 *
 */

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.ApprovalType;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.ServiceEntriesType;
import org.fleetopgroup.constant.VendorTypeConfigurationConstants;
import org.fleetopgroup.persistence.bl.FuelBL;
import org.fleetopgroup.persistence.bl.ServiceEntriesBL;
import org.fleetopgroup.persistence.bl.VendorApprovalBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.dao.BatteryInvoiceRepository;
import org.fleetopgroup.persistence.dao.ClothInventoryRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreRetreadRepository;
import org.fleetopgroup.persistence.dao.UpholsterySendLaundryInvoiceRepository;
import org.fleetopgroup.persistence.dao.VendorApprovalRepository;
import org.fleetopgroup.persistence.dao.VendorSubApprovalDetailsRepository;
import org.fleetopgroup.persistence.dto.BatteryInvoiceDto;
import org.fleetopgroup.persistence.dto.ClothInvoiceDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadDto;
import org.fleetopgroup.persistence.dto.PartInvoiceDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.dto.UpholsterySendLaundryInvoiceDto;
import org.fleetopgroup.persistence.dto.VendorApprovalDto;
import org.fleetopgroup.persistence.dto.VendorDto;
import org.fleetopgroup.persistence.dto.VendorSubApprovalDetailsDto;
import org.fleetopgroup.persistence.model.BatteryInvoice;
import org.fleetopgroup.persistence.model.ClothInvoice;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.InventoryTyreInvoice;
import org.fleetopgroup.persistence.model.InventoryTyreRetread;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.PurchaseOrders;
import org.fleetopgroup.persistence.model.ServiceEntries;
import org.fleetopgroup.persistence.model.UpholsterySendLaundryInvoice;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.model.VendorApproval;
import org.fleetopgroup.persistence.model.VendorApprovalSequenceCounter;
import org.fleetopgroup.persistence.model.VendorSubApprovalDetails;
import org.fleetopgroup.persistence.model.VendorSubApprovalDetailsSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IBatteryInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IPartInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVendorApprovalSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IVendorApprovalService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.persistence.serviceImpl.IVendorSubApprovalDetailsSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IVendorSubApprovalDetailsService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class VendorApprovalController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVendorService vendorService;

	@Autowired
	private IVendorApprovalService vendorApprovalService;

	@Autowired
	private IVendorSubApprovalDetailsService vendorSubApprovalService;

	@Autowired
	private IUserProfileService userProfileService;

	@Autowired
	private IFuelService fuelService;
	
	@Autowired ICompanyConfigurationService 	companyConfigurationService;

	@Autowired
	private IServiceEntriesService ServiceEntriesService;
	ServiceEntriesBL SEBL = new ServiceEntriesBL();

	@Autowired
	private IInventoryTyreService iventoryTyreService;

	@Autowired
	IBatteryInvoiceService		batteryInvoiceService;
	
	@Autowired
	BatteryInvoiceRepository		batteryInvoiceRepository;
	
	@Autowired
	private IPurchaseOrdersService PurchaseOrdersService;
	
	@Autowired
	private IVendorApprovalSequenceService	approvalSequenceService;
	
	@Autowired
	private IVendorSubApprovalDetailsSequenceService	subApprovalSequenceService;

	@Autowired
	private VendorSubApprovalDetailsRepository	vendorSubApprovalDetailsRepository;
	
	@Autowired
	private InventoryTyreRetreadRepository	inventoryTyreRetreadRepository;
	
	@Autowired
	private VendorApprovalRepository	vendorApprovalRepository;
	
	@Autowired
	IPartInvoiceService			partInvoiceService;
	
	@Autowired IClothInventoryService			clothInventoryService;
	
	@Autowired ClothInventoryRepository			clothInventoryRepository;
	
	@Autowired	UpholsterySendLaundryInvoiceRepository		laundryInvoiceRepository;
	
	@Autowired IPendingVendorPaymentService		pendingVendorPaymentService;

	FuelBL FuBL = new FuelBL();
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");

	VendorApprovalBL approval = new VendorApprovalBL();
	VendorBL VenBL = new VendorBL();

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public static boolean arrayContains(String[] arr, String targetValue) {
		for (String s : arr) {
			if (s.matches(targetValue))
				return true;
		}
		return false;
	}

	@RequestMapping(value = "/searchVendorApproval", method = RequestMethod.POST)
	public ModelAndView searchVehicle(@ModelAttribute("command") VendorApprovalDto VendorApprovalDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("approval", approval.prepareListofVendorApproval(
					vendorApprovalService.SearchVendorApproval(VendorApprovalDto.getApprovalvendorName(), userDetails.getCompany_id())));
		} catch (Exception e) {

			LOGGER.error("Vendor Approval :", e);
		}
		return new ModelAndView("VendorApproval_Report", model);
	}

	/* show main page of Issues */
	@RequestMapping(value = "/VendorApproval/{pageNumber}", method = RequestMethod.GET)
	public String VendorApprovalList(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails				userDetails					= null;
		Page<VendorApproval> 			page						= null;
		List<VendorApprovalDto> 		pageList					= null;
		VendorApprovalDto 				pageListBL					= null;
		List<VendorApprovalDto> 		finalList					= null;
		HashMap<String, Object>			configuration 				= null;
		List<VendorSubApprovalDetails>  vendorSubApprovalDetails	= null;
		try {
			finalList		= new ArrayList<>();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			page 			= vendorApprovalService.getDeployment_Page_VendorApproval(FuelVendorPaymentMode.PAYMENT_MODE_APPROVED, pageNumber, userDetails.getCompany_id());
			
			if(page != null) {
				
				int current 	= page.getNumber() + 1;
				int begin 		= Math.max(1, current - 5);
				int end 		= Math.min(begin + 10, page.getTotalPages());
				
				pageList 		= vendorApprovalService.findAll_VendorApproval(FuelVendorPaymentMode.PAYMENT_MODE_APPROVED, pageNumber, userDetails.getCompany_id());
				
				if(pageList != null && !pageList.isEmpty()) {
					
					for(VendorApprovalDto dto : pageList) {
						vendorSubApprovalDetails 	= vendorSubApprovalDetailsRepository.getVendorSubApprovalDetails(dto.getApprovalId(),userDetails.getCompany_id());
						pageListBL 					= approval.prepareVendorApprovalList(dto, vendorSubApprovalDetails);
						finalList.add(pageListBL);
					}
					
					model.addAttribute("configuration", configuration);
					model.addAttribute("deploymentLog", page);
					model.addAttribute("beginIndex", begin);
					model.addAttribute("endIndex", end);
					model.addAttribute("currentIndex", current);
					model.addAttribute("approvalCount", page.getTotalElements());
					model.addAttribute("approval", finalList);
				}
			}

		} catch (NullPointerException e) {
			LOGGER.error("Err", e);
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("vendors Page:", e);
			e.printStackTrace();
		}
		return "VendorApproval_New";
	}

	/* Save Approval Details in back end process show main page of Issues */
	@RequestMapping(value = "/ApprovalList", method = RequestMethod.POST)// Fuel Entry For final Approval Flavor 2 srs
	public ModelAndView approvalList(@ModelAttribute("command") Vendor vendorDto,
			@RequestParam("Selectfuel_id") final String[] select_all, final HttpServletRequest request) throws Exception {
		
		CustomUserDetails								userDetails			= null;
		Map<String, Object> 							model 				= null;
		VendorDto 										vendor				= null;
		VendorApproval 									Approval			= null;
		VendorApprovalSequenceCounter					sequenceCounter		= null;
		VendorSubApprovalDetailsSequenceCounter			subSequenceCounter	= null;
		Fuel											fuel				= null;
		Double											invoiceAmount		= null;
		VendorSubApprovalDetails 						SubApproval			= null;
		String 											fuelID 				= "";
		Double 											AprovalAmount 		= 0.0;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model 		= new HashMap<String, Object>();
			vendor 		= VenBL.getVendorDetails(vendorService.getVendorDetails(vendorDto.getVendorId(), userDetails.getCompany_id()));
			
			if (select_all != null) {
				Approval 			= approval.prepareVendorApproval(vendor);
				sequenceCounter		= approvalSequenceService.findNextSequenceNumber(userDetails.getCompany_id());
				subSequenceCounter	= subApprovalSequenceService.findNextInvoiceNumber(userDetails.getCompany_id());
				
				if(sequenceCounter == null || subSequenceCounter == null) {
					model.put("SequenceNotFound", true);
					return new ModelAndView("redirect:/ShowVendorFuelPayment.in?vendorId=" + vendorDto.getVendorId() + "", model);
				}
				
				Approval.setApprovalCreateById(userDetails.getId());
				Approval.setApprovalStatusId(FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL);
				Approval.setApprovalPlaceId(ApprovalType.APPROVAL_TYPE_FUEL_ENTRIES);
				Approval.setApprovalNumber(sequenceCounter.getNextVal());
				
				/*NO USE OF THIS FOR LOOP */
				for(String dto:select_all) {
					String invoiceId = dto;
					Approval.setInvoiceId(Long.parseLong(invoiceId));
				}
				
				vendorApprovalService.addVendorApproval(Approval); // Save Approval
				
				for(String dto : select_all) { // Save Fuel SubApprovalDetails
					Long inoviceID		= Long.parseLong(dto);
					
					fuel 			= fuelService.getFuel(inoviceID, userDetails.getCompany_id());
					invoiceAmount	= fuel.getFuel_amount();
					
					PendingVendorPayment	payment	= pendingVendorPaymentService.getPendingVendorPaymentById(inoviceID,ApprovalType.APPROVAL_TYPE_FUEL_ENTRIES);
					
					SubApproval 	= approval.prepareVendorSubApprovalDetails(Approval,payment);
					
					AprovalAmount	+= fuel.getFuel_amount();// Total Approval Amount
					
					vendorSubApprovalService.saveSubApproval(SubApproval);
				}
					/**Updating TotalApprovalAmount of FuelApproval In Approval Table**/
					vendorApprovalService.updateVendorApproval_Aoumt(Approval.getApprovalId(), round(AprovalAmount, 0));
				
					fuelID = Utility.getStringArrayToString(select_all); // Multiple Fuel_id's
					
					/**Updating Status of FuelApproval In Fuel Table**/
					fuelService.update_Vendor_ApprovalTO_Status_MULTIP_FUEL_ID(fuelID, Approval.getApprovalId(),FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL, userDetails.getCompany_id());
					
				
				return new ModelAndView("redirect:/AddApproval.in?approvalId=" + Approval.getApprovalId() + "");
				
			} else {
				model.put("EmptyApproval", true);
				return new ModelAndView("redirect:/ShowVendorFuelPayment.in?vendorId=" + vendorDto.getVendorId() + "",model);
				
			}
		}catch(Exception e) {
			LOGGER.error("Err "+e);
			throw e;
		}finally {
			userDetails			= null;    
			model 				= null;    
			vendor				= null;    
			Approval			= null;    
			sequenceCounter		= null;    
			subSequenceCounter	= null;    
			fuel				= null;    
			invoiceAmount		= null;    
			SubApproval			= null;    
			fuelID 				= "";      
			AprovalAmount 		= 0.0;     
		}

	}

	@RequestMapping(value = "/approvedList", method = RequestMethod.GET)
	public ModelAndView approvedList(@ModelAttribute("command") VendorApproval Approval) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Double AprovalAmount = 0.0;

			// Update Approval Id in Fuel Tables
			List<FuelDto> ShowAmount = FuBL
					.prepareListofFuel(fuelService.getVendorApproval_IN_FuelTable_List(Approval.getApprovalId(), userDetails));
			if (ShowAmount != null && !ShowAmount.isEmpty()) {
				for (FuelDto fuel : ShowAmount) {
					AprovalAmount += fuel.getFuel_amount();
				}
				// List Fuel Approval Status List
				model.put("fuel", ShowAmount);
				// update Approval Total Amount
				vendorApprovalService.updateVendorApproval_Aoumt(Approval.getApprovalId(), round(AprovalAmount, 0));
			}

			VendorApprovalDto ShowApproval = vendorApprovalService.getVendorApprovalDetails(Approval.getApprovalId(), userDetails.getCompany_id());

			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(0);
			Double Total = ShowApproval.getApprovalTotal();
			if (ShowApproval.getApprovalTotal() == null) {
				Total = 0.0;
			}
			model.put("approvalTotal", df.format(Total));
			// get the Approval ID to Details
			model.put("approval", ShowApproval);
			// get Approval Vendor id to get all Details
			model.put("vendor", VenBL.getVendorDetails(vendorService.getVendorDetails(ShowApproval.getApprovalvendorID(), userDetails.getCompany_id())));

		} catch (Exception e) {

			e.printStackTrace();
		}

		model.put("saveVendorApproval", true);
		return new ModelAndView("VendorApproval_Add", model);

	}

	// save Issues in databases
	@RequestMapping(value = "/AddApproval", method = RequestMethod.GET)// fuel vendor approval final view
	public ModelAndView AddVendorApproval(@ModelAttribute("command") VendorApproval Approval, BindingResult result) {
		Map<String, Object> 					model				= null;
		CustomUserDetails						userDetails			= null;
		VendorApprovalDto 						ShowApproval 		= null;
		HashMap<String, Object>					configuration 		= null;
		List<FuelDto>							fuelDtoList			= null;
		List<FuelDto>							fuelDtoBL			= null;
		try {
			model = new HashMap<String, Object>();
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	=   companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			// get Fuel Table to approval Total amount

			ShowApproval 	= vendorApprovalService.getVendorApprovalDetails(Approval.getApprovalId(), userDetails.getCompany_id());
			
			fuelDtoList 	= fuelService.getVendorApproval_IN_FuelTable_List(Approval.getApprovalId(), userDetails);
			fuelDtoBL		= FuBL.prepareListofFuel(fuelDtoList);
			
			model.put("fuel",fuelDtoBL);
			
			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(0);
			Double Total = ShowApproval.getApprovalTotal();
			if (ShowApproval.getApprovalTotal() == null) {
				Total = 0.0;
			}
			model.put("configuration", configuration);
			model.put("approvalTotal", df.format(Total));
			// get the Approval ID to Details
			model.put("approval", ShowApproval);
			// get Approval Vendor id to get all Details
			model.put("vendor", VenBL.getVendorDetails(vendorService.getVendorDetails(ShowApproval.getApprovalvendorID(), userDetails.getCompany_id())));

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("VendorApproval_Add", model);
	}

	// save Issues in databases
	@RequestMapping(value = "/ShowApproval", method = RequestMethod.GET)
	public ModelAndView ShowVendorApproval(@RequestParam("approvalId") final Long approvalId,
			final HttpServletRequest result) throws Exception {
		Map<String, Object> 						model 						= new HashMap<String, Object>();
		List<FuelDto> 								fuelList 					= null;
		List<VendorSubApprovalDetailsDto>  			showSubApproval 			= null;
		double										totalPaidApprovalAmount		= 0;
		double										totalFuelPaidApprovalAmount	= 0;
		CustomUserDetails							userDetails					= null;
		VendorApprovalDto 							showApproval 				= null;
		VendorDto 									vendorDto 					= null;
		List<FuelDto> 								fuelBL						= null;	
		
		try {
			
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			showApproval 		= vendorApprovalService.getVendorApprovalDetails(approvalId, userDetails.getCompany_id());
			showSubApproval		= vendorApprovalService.getVendorSubApprovalDetailsList(approvalId);
			fuelList			= fuelService.getVendorApproval_IN_FuelTable_List(approvalId, userDetails);
			
			if(showApproval != null) {
				vendorDto = vendorService.getVendorDetails(showApproval.getApprovalvendorID(), userDetails.getCompany_id());
				model.put("approval", approval.Show_VendorApprovalDetails(showApproval));
				model.put("vendor", VenBL.getVendorDetails(vendorDto));
				
				if(showSubApproval != null && !showSubApproval.isEmpty()) {
					for(VendorSubApprovalDetailsDto dto : showSubApproval) {
						if(dto.getSubApprovalPaidAmount() != null)
							totalPaidApprovalAmount += dto.getSubApprovalPaidAmount();
					}
					
					model.put("ServiceEntries",showSubApproval);
					model.put("totalPaidApprovalAmount",toFixedTwo.format(totalPaidApprovalAmount));
				}
				
				
				if(fuelList != null) {
					fuelBL	= FuBL.prepareListofFuel(fuelList);
						
					for(FuelDto dto : fuelBL) {
						totalFuelPaidApprovalAmount += dto.getVendorApprovedAmount();
					}
					
					model.put("fuel",fuelBL);
					model.put("totalPaidApprovalAmount",toFixedTwo.format(totalFuelPaidApprovalAmount));
				}	
				
				
			}
			return new ModelAndView("VendorApproval_Show", model);
			
		} catch (Exception e) {
			System.err.println("Err"+e);
			throw e;
		}
		
	}	

	@RequestMapping(value = "/PrintApproval", method = RequestMethod.GET)
	public ModelAndView PrintApproval(@RequestParam("id") final Long approvalId, final HttpServletRequest request) {
		Map<String, Object> 						model 						= new HashMap<String, Object>();
		HashMap<String, Object>						configuration 				= null;
		HashMap<String, Object>						tripConfiguration 			= null;
		List<VendorSubApprovalDetailsDto>  			showSubApproval 			= null;
		long										totalPaidApprovalAmount		= 0;
		CustomUserDetails							userDetails					= null;
		VendorApprovalDto 							showApproval 				= null;
		VendorDto 									vendorDto 					= null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			tripConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			showApproval 		= vendorApprovalService.getVendorApprovalDetails(approvalId, userDetails.getCompany_id());
			showSubApproval		= vendorApprovalService.getVendorSubApprovalDetailsList(approvalId);
			
			if(showApproval != null) {
				vendorDto = vendorService.getVendorDetails(showApproval.getApprovalvendorID(), userDetails.getCompany_id());
				model.put("approval", approval.Show_VendorApprovalDetails(showApproval));
				model.put("vendor", VenBL.getVendorDetails(vendorDto));
				model.put("userName", userDetails.getFirstName());
				model.put("userId", userDetails.getId());
				model.put("configuration", configuration);
				model.put("tripConfiguration", tripConfiguration);
				DecimalFormat df = new DecimalFormat("##,##,##0");
				df.setMaximumFractionDigits(0);
				Double Total = showApproval.getApprovalTotal();
					if (showApproval.getApprovalTotal() == null) {
						Total = 0.0;
					}
				
				model.put("approvalTotal", df.format(Total));

				if(showSubApproval != null && !showSubApproval.isEmpty()) {
					for(VendorSubApprovalDetailsDto dto : showSubApproval) {
						if(dto.getSubApprovalPaidAmount() != null)
							totalPaidApprovalAmount += dto.getSubApprovalPaidAmount();
					}
					
					model.put("totalPaidApprovalAmount",toFixedTwo.format(totalPaidApprovalAmount));
					model.put("ServiceEntries",showSubApproval);
				}
			
			}
			model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showApprovalPrint", model);
	}

	@RequestMapping(value = "/UpdateApprovalList", method = RequestMethod.POST)
	public ModelAndView UpdateApprovalList(@ModelAttribute("command") VendorApprovalDto Approval, BindingResult result,
			@RequestParam("multipleApproval") final Long[] select_all,
			@RequestParam("approvalId") final Long[] approvalId,
			@RequestParam("paidAmount") final double[] paidAmount,
			@RequestParam("balanceAmount") final double[] discountAmount,
			@RequestParam("approvedPaymentStatusId") final short[] approvedPaymentStatusId,
			@RequestParam("expectedPaymentDate") final String[] expectedPaymentDate)
		  {
			Map<String, Object>			 	model 				= null;
			Timestamp 						toDate				= null;
			java.util.Date 					currentDate			= null;
			CustomUserDetails				userDetails			= null;
			HashMap<String, Object>			configuration 		= null;
			boolean							vendorFlavor3		= false;
			VendorApprovalDto				ShowApproval		= null;
			List<VendorSubApprovalDetails>	ShowSubApproval		= null;
			Double							paidAmountTotal 	=  0.0;
		
		try {
			
			currentDate 	= new java.util.Date();
			toDate 			= new java.sql.Timestamp(currentDate.getTime());
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model 			= new HashMap<String, Object>();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			vendorFlavor3	= (boolean) configuration.getOrDefault(VendorTypeConfigurationConstants.VENDOR_PAYMENT_FLAVOR_THREE, false);
			
			ShowApproval 	= vendorApprovalService.getVendorApprovalDetails(Approval.getApprovalId(), userDetails.getCompany_id());
			ShowSubApproval	= vendorSubApprovalDetailsRepository.getVendorSubApprovalDetails(Approval.getApprovalId(), userDetails.getCompany_id());
			
			if(paidAmount != null && paidAmount.length > 0) {
				for (int i = 0; i < paidAmount.length; i++) {
					paidAmountTotal += paidAmount[i];
				}
			}
			
			if(Approval != null) {
				vendorApprovalService.updateVendorApprovedBY_andDate(Approval.getApprovalId(), userDetails.getId(), toDate, FuelVendorPaymentMode.PAYMENT_MODE_APPROVED);
			}
				model.put("saveapproval", true);
				
				
			if(vendorFlavor3) {// changes in a invoice table As Well As in a SubApproval Table....
			for( VendorSubApprovalDetails dto : ShowSubApproval )	{	
				switch (dto.getApprovalPlaceId()){

					case ApprovalType.APPROVAL_TYPE_FUEL_ENTRIES:	
						for(int i=0; i<select_all.length; i++){
							fuelService.updatePaymentApprovedFuelDetails(select_all[i], Approval.getApprovalId(),FuelVendorPaymentMode.PAYMENT_MODE_APPROVED, DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD), userDetails.getCompany_id());
							vendorSubApprovalService.updateSubApproved_Details(select_all[i],approvedPaymentStatusId[i],Approval.getApprovalId(),DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD),paidAmount[i], userDetails.getCompany_id());
						}

					default:
					if(dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_PART_INVOICE) {
						for(int i=0; i<select_all.length; i++){	
							partInvoiceService.updatePaymentApprovedPartDetails(select_all[i], Approval.getApprovalId(),FuelVendorPaymentMode.PAYMENT_MODE_APPROVED, DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD), userDetails.getCompany_id());
							vendorSubApprovalService.updateSubApproved_Details(select_all[i],approvedPaymentStatusId[i],Approval.getApprovalId(),DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD),paidAmount[i], userDetails.getCompany_id());
						}
					}
					if(dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_BATTERY_INVOICE) {
						for(int i=0; i<select_all.length; i++){	
							batteryInvoiceService.updatePaymentApprovedBatteryDetails(select_all[i], Approval.getApprovalId(),FuelVendorPaymentMode.PAYMENT_MODE_APPROVED, DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD), userDetails.getCompany_id());
							vendorSubApprovalService.updateSubApproved_Details(select_all[i],approvedPaymentStatusId[i],Approval.getApprovalId(),DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD),paidAmount[i], userDetails.getCompany_id());
						}
					}
					if(dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_INVOICE) {
						for(int i=0; i<select_all.length; i++){	
							iventoryTyreService.updatePaymentApprovedTyreDetails(select_all[i], Approval.getApprovalId(),FuelVendorPaymentMode.PAYMENT_MODE_APPROVED, DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD), userDetails.getCompany_id());
							vendorSubApprovalService.updateSubApproved_Details(select_all[i],approvedPaymentStatusId[i],Approval.getApprovalId(),DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD),paidAmount[i], userDetails.getCompany_id());
						}
					}
					if(dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_RETREAD) {
						for(int i=0; i<select_all.length; i++){	
							iventoryTyreService.updatePaymentApprovedTyreReteadDetails(select_all[i], Approval.getApprovalId(),FuelVendorPaymentMode.PAYMENT_MODE_APPROVED, DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD), userDetails.getCompany_id());
							vendorSubApprovalService.updateSubApproved_Details(select_all[i],approvedPaymentStatusId[i],Approval.getApprovalId(),DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD),paidAmount[i], userDetails.getCompany_id());
						}
					}
					if(dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_CLOTH_INVOICE) {
						for(int i=0; i<select_all.length; i++){	
							clothInventoryService.updatePaymentApprovedUpholsteryDetails(select_all[i], Approval.getApprovalId(),FuelVendorPaymentMode.PAYMENT_MODE_APPROVED, DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD), userDetails.getCompany_id());
							vendorSubApprovalService.updateSubApproved_Details(select_all[i],approvedPaymentStatusId[i],Approval.getApprovalId(),DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD),paidAmount[i], userDetails.getCompany_id());
						}
					}
					if(dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_LAUNDRY_INVOICE) {
						for(int i=0; i<select_all.length; i++){	
							clothInventoryService.updatePaymentApprovedLaundryDetails(select_all[i], Approval.getApprovalId(),FuelVendorPaymentMode.PAYMENT_MODE_APPROVED, DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD), userDetails.getCompany_id());
							vendorSubApprovalService.updateSubApproved_Details(select_all[i],approvedPaymentStatusId[i],Approval.getApprovalId(),DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD),paidAmount[i], userDetails.getCompany_id());
						}
					}
					if(dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_SERVICE_ENTRIES) {
						for(int i=0; i<select_all.length; i++){	
							ServiceEntriesService.updatePaymentApprovedSEDetails(select_all[i], Approval.getApprovalId(),FuelVendorPaymentMode.PAYMENT_MODE_APPROVED, DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD), userDetails.getCompany_id());
							vendorSubApprovalService.updateSubApproved_Details(select_all[i],approvedPaymentStatusId[i],Approval.getApprovalId(),DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD),paidAmount[i], userDetails.getCompany_id());
						}
					}
					if(dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_PURCHASE_ORDER) {
						for(int i=0; i<select_all.length; i++){	
							PurchaseOrdersService.updatePaymentApprovedPurchaseOrderDetails(select_all[i], Approval.getApprovalId(),FuelVendorPaymentMode.PAYMENT_MODE_APPROVED, DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD), userDetails.getCompany_id());
							vendorSubApprovalService.updateSubApproved_Details(select_all[i],approvedPaymentStatusId[i],Approval.getApprovalId(),DateTimeUtility.getTimeStamp(expectedPaymentDate[i],DateTimeUtility.YYYY_MM_DD),paidAmount[i], userDetails.getCompany_id());
						}
					}
					break;
					}
			}
			 if(paidAmountTotal != null) {
				 vendorApprovalService.updateVendorApprovalPaidAmount(ShowApproval.getApprovalId(), paidAmountTotal);
			 }
			}

		} catch (Exception e1) {
			LOGGER.error("e1",e1);
			e1.printStackTrace();
		}
		return new ModelAndView("redirect:/ShowVendorFuelPayment.in?vendorId=" + Approval.getApprovalvendorID() + "", model);
		// return new ModelAndView("show_vendor_ApprovalList", model);
	}

	/* show main page of Issues */
	@RequestMapping(value = "/CancelApprovalList", method = RequestMethod.GET)
	public ModelAndView CancelApprovalList(@RequestParam("AID") Long ApprovalId, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<VendorSubApprovalDetails> ShowSubApproval = null;

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			VendorApprovalDto approvalDetails = vendorApprovalService.getVendorApprovalDetails(ApprovalId, userDetails.getCompany_id());

			ShowSubApproval		= vendorSubApprovalDetailsRepository.getVendorSubApprovalDetails(ApprovalId, userDetails.getCompany_id());
			
			if(ShowSubApproval != null && !ShowSubApproval.isEmpty()) {
				for(VendorSubApprovalDetails dto : ShowSubApproval) {
					switch (dto.getApprovalPlaceId()) {
					case ApprovalType.APPROVAL_TYPE_FUEL_ENTRIES:
						// Update Approval Id in Fuel Tables
						List<FuelDto> ShowAmount = FuBL.prepareListofFuel(
								fuelService.getVendorApproval_IN_FuelTable_List(approvalDetails.getApprovalId(), userDetails));
		
						// getting Fuel ID in Multiple Format
						String MultipleFuel_id = "";
						if (ShowAmount != null && !ShowAmount.isEmpty()) {
							int i = 1;
							// System.out.println(Mult_vehicle_workorder.size());
							for (FuelDto fuel : ShowAmount) {
								if (i != ShowAmount.size()) {
									MultipleFuel_id += fuel.getFuel_id() + ",";
								} else {
									MultipleFuel_id += fuel.getFuel_id() + "";
								}
								i++;
							}
						}
						if (MultipleFuel_id != "") {
							// System.out.println(MultipleFuel_id);
							// New code Update MultipleFuel_id one one below
							fuelService.update_Vendor_ApprovalTO_Status_MULTIP_FUEL_ID(MultipleFuel_id, (long) 0, FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID, userDetails.getCompany_id());
						}
						// delete vendor approval list
						vendorApprovalService.deleteVendorApproval(ApprovalId);
		
						// System.out.println("getApprovalId =
						// "+Approval.getApprovalId());
		
						model.put("deleteapproval", true);
						break;
		
					default:
		
						// getting Fuel ID in Multiple Format
						String MULT_SE_id = "";
						String MULT_PO_id = "";
						String MULT_TI_id = "";
						String MULT_TR_id = "";
						String MULT_BI_id = "";
						String MULT_PI_id = "";
						String MULT_CI_id = "";
		
						// check this approval Create in ServiceEntries Are not
						if (dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_SERVICE_ENTRIES) {
							try {
								// new code update multiple fuel id in one query
								// updating
								// Service Entries
		
								// Update Approval Id in Fuel Tables
								List<ServiceEntries> ShowServiceAmount = ServiceEntriesService
										.get_Amount_VendorApproval_IN_SERVICE_Entries(ApprovalId);
								if (ShowServiceAmount != null && !ShowServiceAmount.isEmpty()) {
									int i = 1;
									for (ServiceEntries service : ShowServiceAmount) {
										// System.out.println(SE_ID);
										if (i != ShowServiceAmount.size()) {
											MULT_SE_id += service.getServiceEntries_id() + ",";
										} else {
											MULT_SE_id += service.getServiceEntries_id() + "";
										}
										i++;
									}
		
									ServiceEntriesService.update_Vendor_ApprovalTO_Status_MULTIP_SERVICE_ID(MULT_SE_id,
											(long) 0, ServiceEntriesType.PAYMENT_MODE_NOT_PAID);
								}
		
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						if (dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_PURCHASE_ORDER) {
							try {
		
								// GET Approval ID To Purchase Order ID IN Change
								// Multiple Type
								List<PurchaseOrders> PurchaseInvoice = PurchaseOrdersService
										.get_Amount_VendorApproval_IN_PurchaseOrders(ApprovalId);
								if (PurchaseInvoice != null && !PurchaseInvoice.isEmpty()) {
									int p = 1;
									for (PurchaseOrders PO : PurchaseInvoice) {
										// System.out.println(SE_ID);
										if (p != PurchaseInvoice.size()) {
											MULT_PO_id += PO.getPurchaseorder_id() + ",";
										} else {
											MULT_PO_id += PO.getPurchaseorder_id() + "";
										}
										p++;
									}
									PurchaseOrdersService.update_Vendor_ApprovalTO_Status_MULTIP_PurchaseOrders(MULT_PO_id,
											(long) 0, InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
								}
							} catch (Exception e) {
								e.printStackTrace();
		
							}
		
						}
						// check this approval Create in Tyreinvoice Are not
						if (dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_INVOICE) {
							try {
		
								// Update Approval Id in Fuel Table
								List<InventoryTyreInvoiceDto> TyreInvoice = iventoryTyreService
										.get_Amount_VendorApproval_IN_InventoryTyreInvoice(ApprovalId, userDetails.getCompany_id());
								if (TyreInvoice != null && !TyreInvoice.isEmpty()) {
									int j = 1;
									for (InventoryTyreInvoiceDto Tyre : TyreInvoice) {
		
										if (j != TyreInvoice.size()) {
											MULT_TI_id += Tyre.getITYRE_ID() + ",";
										} else {
											MULT_TI_id += Tyre.getITYRE_ID() + "";
										}
										j++;
									}
		
									iventoryTyreService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreInvoice_ID(
											MULT_TI_id, (long) 0, InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						// check this approval Create in TyreRetread Are not
						if (dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_RETREAD) {
							try {
		
								// Update Approval Id in Fuel Table
								List<InventoryTyreRetreadDto> TyreRetread = iventoryTyreService
										.get_Amount_VendorApproval_IN_InventoryTyreRetread(ApprovalId, userDetails.getCompany_id());
								if (TyreRetread != null && !TyreRetread.isEmpty()) {
									int k = 1;
									for (InventoryTyreRetreadDto Tyre : TyreRetread) {
										if (k != TyreRetread.size()) {
											MULT_TR_id += Tyre.getTRID() + ",";
										} else {
											MULT_TR_id += Tyre.getTRID() + "";
										}
										k++;
									}
		
									iventoryTyreService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreRetread_ID(
											MULT_TR_id, (long) 0, InventoryTyreRetreadDto.TR_VENDOR_PAYMODE_STATUS_NOTPAID);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
		
						// check this approval Create in Tyreinvoice Are not
						if (dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_BATTERY_INVOICE) {
							try {
		
								// Update Approval Id in Fuel Table
								List<BatteryInvoiceDto> batteryInvoiceList = batteryInvoiceService.get_Amount_VendorApproval_IN_InventoryBatteryInvoice(ApprovalId, userDetails.getCompany_id());
								if (batteryInvoiceList != null && !batteryInvoiceList.isEmpty()) {
									int j = 1;
									for (BatteryInvoiceDto batteryInvoice : batteryInvoiceList) {
		
										if (j != batteryInvoiceList.size()) {
											MULT_BI_id += batteryInvoice.getBatteryInvoiceId() + ",";
										} else {
											MULT_BI_id += batteryInvoice.getBatteryInvoiceId() + "";
										}
										j++;
									}
		
									batteryInvoiceService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryBatteryInvoice_ID(MULT_BI_id, (long) 0, InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
						if (dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_PART_INVOICE) {
							try {
		
								// Update Approval Id in Fuel Table
								List<PartInvoiceDto> partInvoiceList = partInvoiceService.get_Amount_VendorApproval_IN_InventoryPartInvoice(ApprovalId, userDetails.getCompany_id());
								if (partInvoiceList != null && !partInvoiceList.isEmpty()) {
									int j = 1;
									for (PartInvoiceDto partInvoiceDto : partInvoiceList) {
		
										if (j != partInvoiceList.size()) {
											MULT_PI_id += partInvoiceDto.getPartInvoiceId() + ",";
										} else {
											MULT_PI_id += partInvoiceDto.getPartInvoiceId() + "";
										}
										j++;
									}
									
										
									partInvoiceService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryPartInvoice_ID(MULT_PI_id, (long) 0, InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						if (dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_CLOTH_INVOICE) {
							try {
		
								// Update Approval Id in Fuel Table    
								List<ClothInvoiceDto> clothInvoiceList = clothInventoryService.get_Amount_VendorApproval_IN_InventoryClothInvoice(ApprovalId, userDetails.getCompany_id());
								if (clothInvoiceList != null && !clothInvoiceList.isEmpty()) {
									int j = 1;
									for (ClothInvoiceDto clothInvoiceDto : clothInvoiceList) {
		
										if (j != clothInvoiceList.size()) {
											MULT_CI_id += clothInvoiceDto.getClothInvoiceId() + ",";
										} else {
											MULT_CI_id += clothInvoiceDto.getClothInvoiceId() + "";
										}
										j++;
									}
									
										
									clothInventoryService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryClothInvoice_ID(MULT_CI_id, (long) 0, InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
						model.put("deleteapproval", true);
						// delete vendor approval list
						
						break;
		
					}
				}
			}		
			vendorApprovalService.deleteVendorApproval(approvalDetails.getApprovalId());
			vendorSubApprovalService.deleteSubVendorApproval(approvalDetails.getApprovalId(),userDetails.getCompany_id());

		} catch (Exception e) {

			e.printStackTrace();
		}
		// return new ModelAndView("VendorApproval_New", model);
		return new ModelAndView("redirect:/VendorApprovalCreated/1.in", model);

	}

	/* show main page of Issues */
	@RequestMapping(value = "/RemoveApprovalList", method = RequestMethod.GET)
	public ModelAndView RemoveApprovalList(@ModelAttribute("command") FuelDto Fuel_id,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		Fuel fuel_idToAPProvalID =	null;

		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
			 fuel_idToAPProvalID = fuelService.getFuel(Fuel_id.getFuel_id(), userDetails.getCompany_id());

			// get Fuel ID To Update Status is NOTPAID
			fuelService.update_Vendor_ApprovalTO_Status(Fuel_id.getFuel_id(), (long) 0, FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID, userDetails.getCompany_id());

			Double AprovalAmount = 0.0;

			// Update Approval Id in Fuel Tables
			List<FuelDto> ShowAmount = FuBL.prepareListofFuel(
					fuelService.getVendorApproval_IN_FuelTable_List(fuel_idToAPProvalID.getFuel_vendor_approvalID(), userDetails));
			if (ShowAmount != null && !ShowAmount.isEmpty()) {
				for (FuelDto fuel : ShowAmount) {
					AprovalAmount += fuel.getFuel_amount();
				}

				// List Fuel Approval Status List
				model.put("fuel", ShowAmount);

			}
			// update Approval Total Amount
			vendorApprovalService.updateVendorApproval_Aoumt(fuel_idToAPProvalID.getFuel_vendor_approvalID(),
					round(AprovalAmount, 0));
			// System.out.println(AprovalAmount);

		} catch (Exception e) {

			e.printStackTrace();
		}

		// model.put("saveVendorApproval", true);

		// return new ModelAndView("VendorApproval_Add", model);

		return new ModelAndView(
				"redirect:/AddApproval.in?approvalId=" + fuel_idToAPProvalID.getFuel_vendor_approvalID() + "");

	}

	/*
	 * Cancel Selected Approval List of vehicle Details in the Service Entries
	 * And Fuel Entries Approval List
	 */
	@RequestMapping(value = "/CancelApprovalVender", method = RequestMethod.GET)
	public ModelAndView cancelApprovalVender(@RequestParam("VENID") Integer vendorID,
			@RequestParam("AID") Long approvalId, final HttpServletRequest request) {
		Map<String, Object> 					model 			= new HashMap<String, Object>();
		CustomUserDetails						userDetails		= null;
		VendorApprovalDto 						approvalDetails	= null;
		List<VendorSubApprovalDetails> 			showSubApproval	= null;
		PendingVendorPayment					pendingpayment  = null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			approvalDetails 	= vendorApprovalService.getVendorApprovalDetails(approvalId, userDetails.getCompany_id());
			showSubApproval		= vendorSubApprovalDetailsRepository.getVendorSubApprovalDetails(approvalId, userDetails.getCompany_id());
				if(approvalDetails != null) {
					
					if(showSubApproval != null && !showSubApproval.isEmpty()) {
						for (VendorSubApprovalDetails subApprovalDetails : showSubApproval) {
							vendorApprovalService.cancelVendorApprovalInvoices(subApprovalDetails.getInvoiceId(), subApprovalDetails.getApprovalPlaceId(), (long) 0,  FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
							vendorApprovalService.updateTransactionStatus(subApprovalDetails.getInvoiceId()+"", subApprovalDetails.getApprovalPlaceId(), (long)0, userDetails.getCompany_id(), FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
							pendingVendorPaymentService.updateVendorPaymentStatus(subApprovalDetails.getInvoiceId(), subApprovalDetails.getApprovalPlaceId(), PendingPaymentType.PAYMENT_STATUS_PENDING);
							
							pendingpayment = pendingVendorPaymentService.updateAfterVendorPaymentStatus(subApprovalDetails.getVid() , subApprovalDetails.getInvoiceId() , userDetails.getCompany_id());
							
							pendingVendorPaymentService.updatePendingVendorPayment(pendingpayment.getPendingPaymentId(),pendingpayment.getTransactionAmount());
						}
					}
					model.put("deleteapproval", true);
					vendorApprovalService.deleteVendorApproval(approvalDetails.getApprovalId());
					vendorSubApprovalService.deleteSubVendorApproval(approvalDetails.getApprovalId(),userDetails.getCompany_id());
				}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/VendorApprovalCreated/1.in", model);
	}

	/*********************************************************************************************
	 * Payment Mode
	 ************************************************************************************************/

	/* show main page of Issues */
	@RequestMapping(value = "/ApprovalPaymentList/{pageNumber}", method = RequestMethod.GET)
	public String approvalPaymentList(@PathVariable Integer pageNumber, Model model) throws Exception {

		CustomUserDetails				userDetails					= null;
		Page<VendorApproval> 			page						= null;
		VendorApprovalDto 				pageList					= null;
		List<VendorApprovalDto> 		finalList					= null;
		List<VendorApprovalDto> 		findAllList					= null;
		int 							current 					= 0;			
		int 							begin 	        			= 0;
		int 							end 	        			= 0;
		List<VendorSubApprovalDetails>	vendorSubApprovalDetails	= null;
		HashMap<String, Object>			configuration 				= null;
		
		try {
			finalList = new ArrayList<>();
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			page 		= vendorApprovalService.getDeployment_Page_VendorApproval(FuelVendorPaymentMode.PAYMENT_MODE_APPROVED, pageNumber, userDetails.getCompany_id());
			
			current = page.getNumber() + 1;
			begin 	= Math.max(1, current - 5);
			end 	= Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);

			model.addAttribute("approvalCount", page.getTotalElements());
			
			
			findAllList	= vendorApprovalService.findAll_VendorApproval(FuelVendorPaymentMode.PAYMENT_MODE_APPROVED, pageNumber, userDetails.getCompany_id());
			
			if(findAllList != null && !findAllList.isEmpty()){
				for(VendorApprovalDto dto : findAllList) {
					vendorSubApprovalDetails = vendorSubApprovalDetailsRepository.getVendorSubApprovalDetails(dto.getApprovalId(),userDetails.getCompany_id());
					
					pageList 	= approval.prepareVendorApprovalList(dto,vendorSubApprovalDetails);
					finalList.add(pageList);
				}
			}
			

			model.addAttribute("configuration", configuration);
			model.addAttribute("approval", finalList);

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("vendors Page:", e);
			e.printStackTrace();
		}
		return "ApprovalPayment_New";
	}

	/* show main page of Issues */
	@RequestMapping(value = "/ApprovalCompleted/{pageNumber}", method = RequestMethod.GET)
	public String ApprovalCompleted(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails			userDetails					= null;
		Page<VendorApproval> 		page						= null;
		VendorApprovalDto 	pageList					= null;
		List<VendorApprovalDto> 	finalList					= null;
		List<VendorApprovalDto> 	vendorApprovaldtoList		= null;
		List<VendorSubApprovalDetails>	vendorSubApprovalDetails	= null;
		HashMap<String, Object>			configuration 				= null;
		
		try {
			finalList 		= new ArrayList<>();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			page 			= vendorApprovalService.getDeployment_Page_VendorApproval(FuelVendorPaymentMode.PAYMENT_MODE_PAID, pageNumber, userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);

			model.addAttribute("approvalCount", page.getTotalElements());

			vendorApprovaldtoList = vendorApprovalService.findAll_VendorPaidApproval(FuelVendorPaymentMode.PAYMENT_MODE_PAID,FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID,FuelVendorPaymentMode.PAYMENT_MODE_NEGOTIABLE_PAID, pageNumber, userDetails.getCompany_id());

			if(vendorApprovaldtoList != null && !vendorApprovaldtoList.isEmpty()){
				for(VendorApprovalDto dto : vendorApprovaldtoList) {
					vendorSubApprovalDetails = vendorSubApprovalDetailsRepository.getVendorSubApprovalDetails(dto.getApprovalId(),userDetails.getCompany_id());
					
					pageList = approval.prepareVendorApprovalList(dto,vendorSubApprovalDetails);
					finalList.add(pageList);
				}
			}
			
			
			model.addAttribute("configuration", configuration);
			model.addAttribute("approval", finalList);

		} /*catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		}*/ catch (Exception e) {
			LOGGER.error("vendors Page:", e);
			e.printStackTrace();
			return "redirect:/NotFound.in";
		}
		return "ApprovalCompleted_New";
	}

	@RequestMapping(value = "/approvedPayment", method = RequestMethod.GET)
	public ModelAndView approvedPaymentShow(@RequestParam("approvalId") final Long approvalId,  
			final HttpServletRequest result) {
		
		Map<String, Object> 						model 						= new HashMap<String, Object>();
		HashMap<String, Object>						configuration 				= null;
		List<FuelDto> 								fuelList 					= null;
		HashMap<String, Object>						tripConfiguration 			= null;
		List<VendorSubApprovalDetailsDto>  			showSubApproval 			= null;
		double										totalPaidApprovalAmount		= 0;
		double										totalFuelPaidApprovalAmount	= 0;
		CustomUserDetails							userDetails					= null;
		VendorApprovalDto 							showApproval 				= null;
		VendorDto 									vendorDto 					= null;
		List<FuelDto> 								fuelBL						= null;
		Double 										TDSAmount					= null;
		Double										PayableAmount				= null;
		Double										TDSPercent					= null;
		try {
			
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			tripConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			showApproval 		= vendorApprovalService.getVendorApprovalDetails(approvalId, userDetails.getCompany_id());
			showSubApproval		= vendorApprovalService.getVendorSubApprovalDetailsList(approvalId);
			fuelList			= fuelService.getVendorApproval_IN_FuelTable_List(approvalId, userDetails);
			
			if(showApproval != null) {
				if(showApproval.getApprovalStatusId() == FuelVendorPaymentMode.PAYMENT_MODE_PAID
						|| showApproval.getApprovalStatusId() == FuelVendorPaymentMode.PAYMENT_MODE_NEGOTIABLE_PAID
						|| showApproval.getApprovalStatusId() == FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID) {
					return new ModelAndView("redirect:ShowApprovalPayment.in?approvalId="+showApproval.getApprovalId());
				}else if(showApproval.getApprovalStatusId() == FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL) {
					return new ModelAndView("redirect:AddServiceApproval.in?approvalId=="+showApproval.getApprovalId());
				}
				vendorDto = vendorService.getVendorDetails(showApproval.getApprovalvendorID(), userDetails.getCompany_id());
				model.put("approval", approval.Show_VendorApprovalDetails(showApproval));
				model.put("vendor", VenBL.getVendorDetails(vendorDto));
				model.put("userName", userDetails.getFirstName());
				model.put("userId", userDetails.getId());
				model.put("configuration", configuration);
				model.put("tripConfiguration", tripConfiguration);
				DecimalFormat df = new DecimalFormat("##,##,##0");
				df.setMaximumFractionDigits(0);
				Double Total = showApproval.getApprovalTotal();
					if (showApproval.getApprovalTotal() == null) {
						Total = 0.0;
					}
				
				model.put("approvalTotal", df.format(Total));

				if(showSubApproval != null && !showSubApproval.isEmpty()) {
					
					for(VendorSubApprovalDetailsDto dto : showSubApproval) {
						if(dto.getSubApprovalPaidAmount() != null)
						totalPaidApprovalAmount += dto.getSubApprovalPaidAmount();
					}
					
					model.put("ServiceEntries",showSubApproval);
					model.put("totalPaidApprovalAmount", toFixedTwo.format(totalPaidApprovalAmount));
				}
			
				
				if(fuelList != null) {
					fuelBL	= FuBL.prepareListofFuel(fuelList);
						
					for(FuelDto dto : fuelBL) {
						totalFuelPaidApprovalAmount += dto.getVendorApprovedAmount();
					}
					model.put("fuel",fuelBL);
					model.put("totalPaidApprovalAmount",toFixedTwo.format(totalFuelPaidApprovalAmount));
				}	
				if(showApproval.getVendorTDSPercent() != null && showApproval.getVendorTDSPercent() >0)
				{
					TDSPercent = showApproval.getVendorTDSPercent();
					TDSAmount = (TDSPercent/100) * totalFuelPaidApprovalAmount;
					PayableAmount = totalFuelPaidApprovalAmount - TDSAmount;
				}
				
				model.put("TDSAmount", TDSAmount);
				model.put("PayableAmount", PayableAmount);
			}
			
		} catch (Exception e1) {

			e1.printStackTrace();
		}finally {
			userDetails					= null;
			showApproval				= null;	
			fuelList					= null;
			vendorDto					= null;
			totalPaidApprovalAmount		= 0;
			totalFuelPaidApprovalAmount	= 0;
			showSubApproval				= null;
			tripConfiguration 			= null;
		}

		model.put("saveapproval", true);
		return new ModelAndView("ApprovalPayment_Add", model);
	}

	@RequestMapping(value = "/UpdateApprovalPayment", method = RequestMethod.POST)
	public ModelAndView UpdateApprovalPayment(@ModelAttribute("command") VendorApprovalDto Approval, BindingResult result) {
		
		Map<String, Object> 			model 					= new HashMap<String, Object>();
		CustomUserDetails				userDetails				= null;
		List<FuelDto> 					ShowAmount				= null;
		List<FuelDto> 					ShowAmountBL			= null;
		List<PurchaseOrders> 			PurchaseInvoice			= null;
		List<InventoryTyreInvoiceDto> 	TyreInvoice				= null;
		List<ServiceEntries> 			ShowServiceAmount		= null;
		List<PartInvoiceDto> 			partInvoiceList			= null;
		List<ClothInvoiceDto> 			clothInvoiceList		= null;
		java.util.Date 					currentDateUpdate		= null;
		Timestamp 						toDate					= null;
		Timestamp 						paymentDate				= null;
		String 							ServiceEntries_id 		= "";
		String 							totalApprovalAmountStr 	= null;
		Double 							totalApprovalAmount 	= null;
		HashMap<String, Object>			configuration 			= null;
		boolean							vendorFlavor3			= false;
		List<VendorSubApprovalDetails>	ShowSubApproval			= null;
		HashMap<String, Object>			tripConfiguration 		= null;
		VendorApproval					vendorApproval 			= null;
		List<UpholsterySendLaundryInvoiceDto> 			laundryInvoiceList		= null;
		
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			tripConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			vendorFlavor3		= (boolean) configuration.getOrDefault(VendorTypeConfigurationConstants.VENDOR_PAYMENT_FLAVOR_THREE, false);
			currentDateUpdate	= new java.util.Date();
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
			
			totalApprovalAmountStr 	= Approval.getApprovalTotalStr().replaceAll(",", "");
			totalApprovalAmount	 	= Double.parseDouble(totalApprovalAmountStr);
			
			String PaymentDateStr = Approval.getApprovalDateofpayment();
			
			String day		= PaymentDateStr.split("-")[0];
			String month	= PaymentDateStr.split("-")[1];
			String year		= PaymentDateStr.split("-")[2];
			
			String dateOfPaymentStr = year+"-"+month+"-"+day;
			
			paymentDate = DateTimeUtility.getTimeStamp(dateOfPaymentStr, DateTimeUtility.YYYY_MM_DD);
			
			vendorApproval = vendorApprovalRepository.getVendorApproval(Approval.getApprovalId(),userDetails.getCompany_id());
			ShowSubApproval	= vendorSubApprovalDetailsRepository.getVendorSubApprovalDetails(Approval.getApprovalId(), userDetails.getCompany_id());
			
			if(vendorFlavor3) {
				vendorApprovalService.updateApprovedPayment_Details(Approval.getApprovalId(),Approval.getApprovalPaymentTypeId(), Approval.getApprovalPayNumber(), paymentDate,
					userDetails.getId(), FuelVendorPaymentMode.PAYMENT_MODE_PAID, userDetails.getId(), toDate, totalApprovalAmount);
			}else {
				if(Approval.getVendorPaymentStatus() <= 0 || Approval.getVendorPaymentStatus() == 1) {
					vendorApprovalService.updateApprovedPayment_Details(Approval.getApprovalId(),
							Approval.getApprovalPaymentTypeId(), Approval.getApprovalPayNumber(), paymentDate,
							userDetails.getId(), FuelVendorPaymentMode.PAYMENT_MODE_PAID, userDetails.getId(), toDate, totalApprovalAmount);
				}else {
					vendorApprovalService.updateApprovedPayment_Details(Approval.getApprovalId(),
							Approval.getApprovalPaymentTypeId(), Approval.getApprovalPayNumber(), paymentDate,
							userDetails.getId(), FuelVendorPaymentMode.PAYMENT_MODE_PAID,userDetails.getId(), toDate, Approval.getPaidAmount());
				}
			}
			
			if((boolean) tripConfiguration.get("allowTallyIntegration") && Approval.getTallyCompanyId() != null && Approval.getTallyCompanyId() > 0) {
				vendorApprovalService.updateTallyCompany(Approval.getTallyCompanyId(), Approval.getApprovalId());
			}
			
			for(VendorSubApprovalDetails dto :ShowSubApproval) {
			
			switch (dto.getApprovalPlaceId()) {

			case ApprovalType.APPROVAL_TYPE_FUEL_ENTRIES:

				ShowAmount		= fuelService.getVendorApproval_IN_FuelTable_List(Approval.getApprovalId(), userDetails);
				ShowAmountBL 	= FuBL.prepareListofFuel(ShowAmount);

				if (ShowAmountBL != null && !ShowAmountBL.isEmpty()) {
					int i = 1;
					for (FuelDto fuel : ShowAmountBL) {
						if (i != ShowAmountBL.size()) {
							ServiceEntries_id += fuel.getFuel_id() + ",";
						} else {
							ServiceEntries_id += fuel.getFuel_id() + "";
						}
						i++;
					}
				}

				if (ServiceEntries_id != "") {
					if(vendorFlavor3) {
						fuelService.updatePaymentPaidFuelDetails(ServiceEntries_id,paymentDate, userDetails.getCompany_id(),dto.getApprovedPaymentStatusId());
						vendorSubApprovalService.updateSubApprovedPayment_Details(FuelVendorPaymentMode.PAYMENT_MODE_PAID,Approval.getApprovalPaymentTypeId(), Approval.getApprovalPayNumber(), paymentDate,userDetails.getId(),ServiceEntries_id,Approval.getApprovalId(), userDetails.getCompany_id());
					}
					if(!vendorFlavor3) {
						if(Approval.getVendorPaymentStatus() >= 0 && Approval.getPaidAmount()!= null && Approval.getDiscountAmount()!=  null) {
								fuelService.update_Vendor_ApprovalTO_Status_PayDate_Multiple_fuel_ID(ServiceEntries_id, paymentDate,
										Approval.getApprovalId(), FuelVendorPaymentMode.PAYMENT_MODE_PAID, userDetails.getCompany_id(),Approval.getPaidAmount(),Approval.getDiscountAmount());
							}else {
								fuelService.update_Vendor_ApprovalTO_Status_PayDate_Multiple_fuel_ID(ServiceEntries_id,paymentDate,
										Approval.getApprovalId(),Approval.getVendorPaymentStatus(), userDetails.getCompany_id(),Approval.getPaidAmount(),Approval.getDiscountAmount());
							}
						}
				}
				

			default:

				String MULT_SE_id = "";
				String MULT_PO_id = "";
				String MULT_TI_id = "";
				String MULT_TR_id = "";
				String MULT_BI_id = "";
				String MULT_PI_id = "";
				String MULT_CI_id = "";
				String MULT_LI_id = "";

				if (dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_SERVICE_ENTRIES) {
					try {
						ShowServiceAmount = ServiceEntriesService.get_Amount_VendorApproval_IN_SERVICE_Entries(Approval.getApprovalId());
						if (ShowServiceAmount != null && !ShowServiceAmount.isEmpty()) {
							int i = 1;
							for (ServiceEntries service : ShowServiceAmount) {
								if (i != ShowServiceAmount.size()) {
									MULT_SE_id += service.getServiceEntries_id() + ",";
								} else {
									MULT_SE_id += service.getServiceEntries_id() + "";
								}
								i++;
							}

							if (MULT_SE_id != "") {
								
								if(vendorFlavor3) {
									ServiceEntriesService.updatePaymentPaidSEDetails(MULT_SE_id,paymentDate, userDetails.getCompany_id(),dto.getApprovedPaymentStatusId());
									vendorSubApprovalService.updateSubApprovedPayment_Details(FuelVendorPaymentMode.PAYMENT_MODE_PAID,Approval.getApprovalPaymentTypeId(), Approval.getApprovalPayNumber(), paymentDate,userDetails.getId(),MULT_SE_id,Approval.getApprovalId(), userDetails.getCompany_id());
								}
								if(!vendorFlavor3) {
									if(Approval.getVendorPaymentStatus() >= 0 && Approval.getPaidAmount()!= null && Approval.getDiscountAmount()!=  null) {
											ServiceEntriesService.update_Vendor_ApprovalTO_Status_PayDate_Multiple_Service_ID(
													MULT_SE_id, paymentDate, Approval.getApprovalId(), ServiceEntriesType.PAYMENT_MODE_PAID, userDetails.getCompany_id(),Approval.getPaidAmount(),Approval.getDiscountAmount());
										}else {
											ServiceEntriesService.update_Vendor_ApprovalTO_Status_PayDate_Multiple_Service_ID(
													MULT_SE_id, paymentDate, Approval.getApprovalId(), Approval.getVendorPaymentStatus(), userDetails.getCompany_id(),Approval.getPaidAmount(),Approval.getDiscountAmount());
										}
									}
								}
							}
						}

					 catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_PURCHASE_ORDER) {
					try {
						PurchaseInvoice = PurchaseOrdersService.get_Amount_VendorApproval_IN_PurchaseOrders(Approval.getApprovalId());
						if (PurchaseInvoice != null && !PurchaseInvoice.isEmpty()) {
							int p = 1;
							for (PurchaseOrders PO : PurchaseInvoice) {
								if (p != PurchaseInvoice.size()) {
									MULT_PO_id += PO.getPurchaseorder_id() + ",";
								} else {
									MULT_PO_id += PO.getPurchaseorder_id() + "";
								}
								p++;
							}
							if (MULT_PO_id != "") {

								if(vendorFlavor3) {
									for (VendorSubApprovalDetails subApproval : ShowSubApproval) {
										PurchaseOrdersService.updatePaymentPaidPurchaseOrderDetails(subApproval,paymentDate, userDetails.getCompany_id(),dto.getApprovedPaymentStatusId());
									}
									vendorSubApprovalService.updateSubApprovedPayment_Details(FuelVendorPaymentMode.PAYMENT_MODE_PAID,Approval.getApprovalPaymentTypeId(), Approval.getApprovalPayNumber(), paymentDate,userDetails.getId(),MULT_PO_id,Approval.getApprovalId(), userDetails.getCompany_id());
								}
								if(!vendorFlavor3) {
									if(Approval.getVendorPaymentStatus() >= 0 && Approval.getPaidAmount()!= null && Approval.getDiscountAmount()!=  null) {
											PurchaseOrdersService.update_Vendor_ApprovalTO_Status_PayDate_Multiple_PurchaseOrders_ID(MULT_PO_id,
													paymentDate, Approval.getApprovalId(), InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_PAID, userDetails.getCompany_id(),Approval.getPaidAmount(),Approval.getDiscountAmount());									
										}else {
											PurchaseOrdersService.update_Vendor_ApprovalTO_Status_PayDate_Multiple_PurchaseOrders_ID(MULT_PO_id,
													paymentDate, Approval.getApprovalId(), Approval.getVendorPaymentStatus(), userDetails.getCompany_id(),Approval.getPaidAmount(),Approval.getDiscountAmount());									
										}
									}
								}
							}
						}
					 catch (Exception e) {
						e.printStackTrace();

					}

				}
				if (dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_INVOICE) {
					try {
						TyreInvoice = iventoryTyreService.get_Amount_VendorApproval_IN_InventoryTyreInvoice(Approval.getApprovalId(), userDetails.getCompany_id());
						if (TyreInvoice != null && !TyreInvoice.isEmpty()) {
							int j = 1;
							for (InventoryTyreInvoiceDto Tyre : TyreInvoice) {

								if (j != TyreInvoice.size()) {
									MULT_TI_id += Tyre.getITYRE_ID() + ",";
								} else {
									MULT_TI_id += Tyre.getITYRE_ID() + "";
								}
								j++;
							}
							if(vendorFlavor3) {
								iventoryTyreService.updatePaymentPaidTyreDetails(MULT_TI_id,paymentDate, userDetails.getCompany_id(),dto.getApprovedPaymentStatusId());
								vendorSubApprovalService.updateSubApprovedPayment_Details(FuelVendorPaymentMode.PAYMENT_MODE_PAID,Approval.getApprovalPaymentTypeId(), Approval.getApprovalPayNumber(), paymentDate,userDetails.getId(),MULT_TI_id,Approval.getApprovalId(), userDetails.getCompany_id());
							}
							if(!vendorFlavor3) {
								if(Approval.getVendorPaymentStatus() >= 0 && Approval.getPaidAmount()!= null && Approval.getDiscountAmount()!=  null) {
										iventoryTyreService.update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreInvoice_ID(
												MULT_TI_id, Approval.getApprovalId(), InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_PAID, paymentDate, userDetails.getCompany_id(),Approval.getPaidAmount(),Approval.getDiscountAmount());
									
									}else {
										iventoryTyreService.update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreInvoice_ID(
												MULT_TI_id, Approval.getApprovalId(),  Approval.getVendorPaymentStatus(), paymentDate, userDetails.getCompany_id(),Approval.getPaidAmount(),Approval.getDiscountAmount());
									}
								}
							}
						}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_RETREAD) {
					try {
						List<InventoryTyreRetreadDto> TyreRetread = iventoryTyreService
								.get_Amount_VendorApproval_IN_InventoryTyreRetread(Approval.getApprovalId(), userDetails.getCompany_id());
						if (TyreRetread != null && !TyreRetread.isEmpty()) {
							int k = 1;
							for (InventoryTyreRetreadDto Tyre : TyreRetread) {
								if (k != TyreRetread.size()) {
									MULT_TR_id += Tyre.getTRID() + ",";
								} else {
									MULT_TR_id += Tyre.getTRID() + "";
								}
								k++;
							}
							if(vendorFlavor3) {
								iventoryTyreService.updatePaymentPaidTyreRetreadDetails(MULT_TR_id,paymentDate, userDetails.getCompany_id(),dto.getApprovedPaymentStatusId());
								vendorSubApprovalService.updateSubApprovedPayment_Details(FuelVendorPaymentMode.PAYMENT_MODE_PAID,Approval.getApprovalPaymentTypeId(), Approval.getApprovalPayNumber(), paymentDate,userDetails.getId(),MULT_TR_id,Approval.getApprovalId(), userDetails.getCompany_id());
							}
							
							if(!vendorFlavor3) {
								if(Approval.getVendorPaymentStatus() >= 0 && Approval.getPaidAmount()!= null && Approval.getDiscountAmount()!=  null) {
										iventoryTyreService.update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreRetread_ID(
												MULT_TR_id, Approval.getApprovalId(), InventoryTyreRetreadDto.TR_VENDOR_PAYMODE_STATUS_PAID, 
												paymentDate, userDetails.getCompany_id(),Approval.getPaidAmount(),Approval.getDiscountAmount());
									
									}else {
										iventoryTyreService.update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreRetread_ID(
												MULT_TR_id, Approval.getApprovalId(), Approval.getVendorPaymentStatus(), 
												paymentDate, userDetails.getCompany_id(),Approval.getPaidAmount(),Approval.getDiscountAmount());
									}
								}
							}
						}
							
						catch (Exception e) {
						e.printStackTrace();
					}
				}

				// check this approval Create in Tyreinvoice Are not
				if (dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_BATTERY_INVOICE) {
					try {

						// Update Approval Id in Fuel Table
						List<BatteryInvoiceDto> batteryInvoiceList = batteryInvoiceService.get_Amount_VendorApproval_IN_InventoryBatteryInvoice(Approval.getApprovalId(), userDetails.getCompany_id());
						if (batteryInvoiceList != null && !batteryInvoiceList.isEmpty()) {
							int j = 1;
							for (BatteryInvoiceDto batteryInvoice : batteryInvoiceList) {

								if (j != batteryInvoiceList.size()) {
									MULT_BI_id += batteryInvoice.getBatteryInvoiceId() + ",";
								} else {
									MULT_BI_id += batteryInvoice.getBatteryInvoiceId() + "";
								}
								j++;
							}
							if(vendorFlavor3) {
								batteryInvoiceService.updatePaymentPaidBatteryDetails(MULT_BI_id,paymentDate, userDetails.getCompany_id(),dto.getApprovedPaymentStatusId());
								vendorSubApprovalService.updateSubApprovedPayment_Details(FuelVendorPaymentMode.PAYMENT_MODE_PAID,Approval.getApprovalPaymentTypeId(), Approval.getApprovalPayNumber(), paymentDate,userDetails.getId(),MULT_BI_id,Approval.getApprovalId(), userDetails.getCompany_id());
							}
							if(!vendorFlavor3) {
								if(Approval.getVendorPaymentStatus() >= 0 && Approval.getPaidAmount()!= null && Approval.getDiscountAmount()!=  null) {
										batteryInvoiceService.update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryBatteryInvoice_ID(MULT_BI_id, Approval.getApprovalId(), InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_PAID, paymentDate, userDetails.getCompany_id(),Approval.getPaidAmount(),Approval.getDiscountAmount());
									}
									else {
										batteryInvoiceService.update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryBatteryInvoice_ID(MULT_BI_id, Approval.getApprovalId(), Approval.getVendorPaymentStatus(), paymentDate, userDetails.getCompany_id(),Approval.getPaidAmount(),Approval.getDiscountAmount());
									}
								}
							}
						}
					 catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_PART_INVOICE) {
					try {
						// Update Approval Id in Fuel Table
						partInvoiceList = partInvoiceService.get_Amount_VendorApproval_IN_InventoryPartInvoice(Approval.getApprovalId(), userDetails.getCompany_id());
						
						if (partInvoiceList != null && !partInvoiceList.isEmpty()) {
							int j = 1;
							for (PartInvoiceDto partInvoice : partInvoiceList) {

								if (j != partInvoiceList.size()) {
									MULT_PI_id += partInvoice.getPartInvoiceId() + ",";
								} else {
									MULT_PI_id += partInvoice.getPartInvoiceId() + "";
								}
								j++;
							}
							if(vendorFlavor3) {
								partInvoiceService.updatePaymentPaidDetails(MULT_PI_id,paymentDate, userDetails.getCompany_id(),dto.getApprovedPaymentStatusId());
								vendorSubApprovalService.updateSubApprovedPayment_Details(FuelVendorPaymentMode.PAYMENT_MODE_PAID,Approval.getApprovalPaymentTypeId(), Approval.getApprovalPayNumber(), paymentDate,userDetails.getId(),MULT_PI_id,Approval.getApprovalId(), userDetails.getCompany_id());
							}
							if(!vendorFlavor3) {
								if(Approval.getVendorPaymentStatus() >= 0 && Approval.getPaidAmount()!= null && Approval.getDiscountAmount()!=  null) {
										partInvoiceService.update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryPartInvoice_ID(MULT_PI_id, Approval.getApprovalId(), Approval.getVendorPaymentStatus(), paymentDate, userDetails.getCompany_id(),Approval.getPaidAmount(),Approval.getDiscountAmount());
									}else {
										double discountAmount = 0.0;
										partInvoiceService.update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryPartInvoice_ID(MULT_PI_id, Approval.getApprovalId(), InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_PAID, paymentDate, userDetails.getCompany_id(),totalApprovalAmount,discountAmount);
									}
								}
							}
						}
					 catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_CLOTH_INVOICE) {
					try {
						// Update Approval Id in Fuel Table
						
						clothInvoiceList = clothInventoryService.get_Amount_VendorApproval_IN_InventoryClothInvoice(Approval.getApprovalId(), userDetails.getCompany_id());
						
						if (clothInvoiceList != null && !clothInvoiceList.isEmpty()) {
							int j = 1;
							for (ClothInvoiceDto clothInvoice : clothInvoiceList) {

								if (j != clothInvoiceList.size()) {
									MULT_CI_id += clothInvoice.getClothInvoiceId() + ",";
								} else {
									MULT_CI_id += clothInvoice.getClothInvoiceId() + "";
								}
								j++;
							}
							if(vendorFlavor3) {
								clothInventoryService.updatePaymentPaidUpholsteryDetails(MULT_CI_id,paymentDate, userDetails.getCompany_id(),dto.getApprovedPaymentStatusId());
								vendorSubApprovalService.updateSubApprovedPayment_Details(FuelVendorPaymentMode.PAYMENT_MODE_PAID,Approval.getApprovalPaymentTypeId(), Approval.getApprovalPayNumber(), paymentDate,userDetails.getId(),MULT_CI_id,Approval.getApprovalId(), userDetails.getCompany_id());
							}
							if(!vendorFlavor3) {
								if(Approval.getVendorPaymentStatus() >= 0 && Approval.getPaidAmount()!= null && Approval.getDiscountAmount()!=  null) {
									clothInventoryService.update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryClothInvoice_ID(MULT_CI_id, Approval.getApprovalId(), Approval.getVendorPaymentStatus(), paymentDate, userDetails.getCompany_id(),Approval.getPaidAmount(),Approval.getDiscountAmount());
									}else {
										double discountAmount = 0.0;
										clothInventoryService.update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryClothInvoice_ID(MULT_CI_id, Approval.getApprovalId(), InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_PAID, paymentDate, userDetails.getCompany_id(),totalApprovalAmount,discountAmount);
									}
								}
							}
						}
					 catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (dto.getApprovalPlaceId() == ApprovalType.APPROVAL_TYPE_INVENTORY_LAUNDRY_INVOICE) {
					try {
						// Update Approval Id in Fuel Table
						
						laundryInvoiceList = clothInventoryService.getVendorInventoryLaundryInvoice(Approval.getApprovalId(), userDetails.getCompany_id());
						
						if (laundryInvoiceList != null && !laundryInvoiceList.isEmpty()) {
							int j = 1;
							for (UpholsterySendLaundryInvoiceDto clothInvoice : laundryInvoiceList) {

								if (j != clothInvoiceList.size()) {
									MULT_LI_id += clothInvoice.getLaundryInvoiceId() + ",";
								} else {
									MULT_LI_id += clothInvoice.getLaundryInvoiceId() + "";
								}
								j++;
							}
							if(vendorFlavor3) {
								clothInventoryService.updatePaymentPaidLaundryDetails(MULT_LI_id,paymentDate, userDetails.getCompany_id(),dto.getApprovedPaymentStatusId());
								vendorSubApprovalService.updateSubApprovedPayment_Details(FuelVendorPaymentMode.PAYMENT_MODE_PAID,Approval.getApprovalPaymentTypeId(), Approval.getApprovalPayNumber(), paymentDate,userDetails.getId(),MULT_CI_id,Approval.getApprovalId(), userDetails.getCompany_id());
							}
							if(!vendorFlavor3) {
								if(Approval.getVendorPaymentStatus() >= 0 && Approval.getPaidAmount()!= null && Approval.getDiscountAmount()!=  null) {
									clothInventoryService.update_Payment_Vendor_ApprovalTO_Status_MULTIP_LaundryInvoice(MULT_LI_id, Approval.getApprovalId(), Approval.getVendorPaymentStatus(), paymentDate, userDetails.getCompany_id(),Approval.getPaidAmount(),Approval.getDiscountAmount());
								}else {
										double discountAmount = 0.0;
										clothInventoryService.update_Payment_Vendor_ApprovalTO_Status_MULTIP_LaundryInvoice(MULT_LI_id, Approval.getApprovalId(), InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_PAID, paymentDate, userDetails.getCompany_id(),totalApprovalAmount,discountAmount);
								}
								}
							}
						}
					 catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;

			}
			}
			
			

		} catch (Exception e1) {

			e1.printStackTrace();
		}finally {
			userDetails				= null;
			ShowAmount				= null;
			ShowAmountBL			= null;
			PurchaseInvoice			= null;
			TyreInvoice				= null;
			ShowServiceAmount		= null;
			partInvoiceList			= null;
			clothInvoiceList		= null;
			currentDateUpdate		= null;
			toDate					= null;
			paymentDate				= null;
			ServiceEntries_id 		= "";
			totalApprovalAmountStr 	= null;
			totalApprovalAmount 	= null;
			configuration 			= null;
			vendorFlavor3			= false;
			ShowSubApproval		= null;
		}

		model.put("saveapproval", true);
		return new ModelAndView("redirect:/ApprovalPaymentList/1.in", model);
	}

	@RequestMapping(value = "/ShowApprovalPayment", method = RequestMethod.GET) 
	public ModelAndView showVendorApprovalPayment(@RequestParam("approvalId") final Long approvalId,
			final HttpServletRequest result) {
		Map<String, Object> 				model 						= new HashMap<String, Object>();
		List<VendorSubApprovalDetailsDto> 	showSubApproval				= null;
		Double								totalPaidApprovalAmount		= 0.0;
		CustomUserDetails					userDetails					= null;
		VendorApprovalDto 					showApproval 				= null;
		VendorDto 							vendorDto 					= null;
		
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			showApproval 		= vendorApprovalService.getVendorApprovalDetails(approvalId, userDetails.getCompany_id());
			showSubApproval		= vendorApprovalService.getVendorSubApprovalDetailsList(approvalId); 
			if(showApproval != null) {
				vendorDto = vendorService.getVendorDetails(showApproval.getApprovalvendorID(), userDetails.getCompany_id());
				model.put("approval", approval.Show_VendorApprovalDetails(showApproval));
				model.put("vendor", VenBL.getVendorDetails(vendorDto));
				
				
				if(showSubApproval != null && !showSubApproval.isEmpty()) {
					for(VendorSubApprovalDetailsDto dto : showSubApproval) {
						if(dto.getSubApprovalPaidAmount() != null)
							totalPaidApprovalAmount += dto.getSubApprovalPaidAmount();
					}
					
					model.put("ServiceEntries",showSubApproval);
					model.put("totalPaidApprovalAmount",toFixedTwo.format(totalPaidApprovalAmount));
				}
				
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("ApprovalPayment_Show", model);
	}

	// Save Service Entries Approval List Process
	/* Save Approval Details in back end process show main page of Issues */
	@RequestMapping(value = "/ApprovalServiceEntriesList", method = RequestMethod.POST)
	public ModelAndView ApprovalServiceEntriesList(@ModelAttribute("command") Vendor vendorDto,
		   @RequestParam("SelectService_id") final String[] select_all, 
		   final HttpServletRequest request)throws Exception {
		
		Map<String, Object> 					model 				= new HashMap<String, Object>();
		VendorApproval 							Approval			= null;
		CustomUserDetails						userDetails			= null;
		VendorDto 								vendor 				= null;
		VendorApprovalSequenceCounter			sequenceCounter		= null;
		String 									Other 				= "";
		List<ServiceEntries> 					ShowAmount			= null;
		List<PurchaseOrders> 					PurchaseInvoice		= null; 
		List<InventoryTyreInvoiceDto> 			TyreInvoice			= null;
		List<InventoryTyreRetreadDto> 			TyreRetread			= null;
		List<BatteryInvoiceDto> 				batteryInvoiceList	= null;
		List<PartInvoiceDto> 					partInvoiceList		= null;
		List<ClothInvoiceDto> 					clothInvoiceList	= null;
		VendorSubApprovalDetailsSequenceCounter	subSequenceCounter	= null;
		VendorSubApprovalDetails 				SubApproval			= null;
		PartInvoiceDto							partInvoiceDto		= null;
		PurchaseOrders							purchaseOrders		= null;
		ServiceEntriesDto						serviceEntries		= null;
		InventoryTyreInvoice					inventoryTyreInvoice		= null;
		InventoryTyreRetread					inventoryTyreRetread		= null;
		BatteryInvoice							batteryInvoice		= null;
		ClothInvoice							clothInvoice		= null;
		Double									invoiceAmount		= null;
		List<UpholsterySendLaundryInvoiceDto> 	laundryInvoiceList	= null;
		UpholsterySendLaundryInvoice			laundryInvoice		= null;
	
			if (select_all != null) {
				
				userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				vendor 				= VenBL.getVendorDetails(vendorService.getVendorDetails(vendorDto.getVendorId(), userDetails.getCompany_id()));
				Approval 			= approval.prepareVendorApproval(vendor);
				sequenceCounter		= approvalSequenceService.findNextSequenceNumber(userDetails.getCompany_id());
				subSequenceCounter	= subApprovalSequenceService.findNextInvoiceNumber(userDetails.getCompany_id());
				SubApproval 		= new VendorSubApprovalDetails();
				
				if(sequenceCounter == null || subSequenceCounter == null) {
					model.put("SequenceNotFound", true);
					return new ModelAndView("redirect:/ShowVendorFuelPayment.in?vendorId=" + vendorDto.getVendorId() + "",model);
				}

				Approval.setApprovalNumber(sequenceCounter.getNextVal());
				Approval.setApprovalCreateById(userDetails.getId());
				Approval.setApprovalStatusId(FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL);
			for(String dto:select_all) {
				String invoiceId = dto.split("-")[1];
				Approval.setInvoiceId(Long.parseLong(invoiceId));
			}
				if (arrayContains(select_all, "SE-(.*)")) {
					// Do some stuff.
					Other = Other + "SE,";
					Approval.setApprovalPlaceId(ApprovalType.APPROVAL_TYPE_SERVICE_ENTRIES);
				}
				if (arrayContains(select_all, "PO-(.*)")) {
					// Do some stuff.
					Other = Other + "PO,";
					Approval.setApprovalPlaceId(ApprovalType.APPROVAL_TYPE_PURCHASE_ORDER);
				}
				if (arrayContains(select_all, "TI-(.*)")) {
					// Do some stuff.
					Other = Other + "TI,";
					Approval.setApprovalPlaceId(ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_INVOICE);
				}
				if (arrayContains(select_all, "TR-(.*)")) {
					// Do some stuff.
					Other = Other + "TR,";
					Approval.setApprovalPlaceId(ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_RETREAD);
				}

				if (arrayContains(select_all, "BI-(.*)")) {
					// Do some stuff.
					Other = Other + "BI,";
					Approval.setApprovalPlaceId(ApprovalType.APPROVAL_TYPE_INVENTORY_BATTERY_INVOICE);
				}
				if (arrayContains(select_all, "PI-(.*)")) {
					// Do some stuff.
					Other = Other + "PI,";
					Approval.setApprovalPlaceId(ApprovalType.APPROVAL_TYPE_INVENTORY_PART_INVOICE);
				}
				if (arrayContains(select_all, "CI-(.*)")) {
					// Do some stuff.
					Other = Other + "CI,";
					Approval.setApprovalPlaceId(ApprovalType.APPROVAL_TYPE_INVENTORY_CLOTH_INVOICE);
				}
				if (arrayContains(select_all, "LI-(.*)")) {
					// Do some stuff.
					Other = Other + "LI,";
					Approval.setApprovalPlaceId(ApprovalType.APPROVAL_TYPE_INVENTORY_LAUNDRY_INVOICE);
				}
				// ApprovalPlace in here which one he select to approval checking
				//Approval.setApprovalPlace(Other);

				// save approval in vendor
				vendorApprovalService.addVendorApproval(Approval);
				

				//approvalSequenceService.updateNextSequenceNumber(sequenceCounter.getNextVal() + 1, sequenceCounter.getSequence_Id());

				// getting Multiple Table to Get String Select ID Value
				List<String> 	ServiceEntries_id 	= new ArrayList<>();
				List<String> 	PurchaseOrder_id 	= new ArrayList<>();
				List<String> 	Tyre_id 			= new ArrayList<>();
				List<String> 	TyreRetread_id 		= new ArrayList<>();
				List<String> 	battery_id 			= new ArrayList<>();
				List<String> 	part_id 			= new ArrayList<>();
				List<String> 	cloth_id 			= new ArrayList<>();
				List<String> 	laundry_id 			= new ArrayList<>();
				// get the Selected Approval Id to Get details in value
				
				for (String serviceEnt : select_all) {
					if (serviceEnt.matches("SE-(.*)")) {
						ServiceEntries_id.add(serviceEnt.replace("SE-", ""));
						
					} else if (serviceEnt.matches("PO-(.*)")) {
						// here how many purchase order select in approval make
						// collection
						PurchaseOrder_id.add(serviceEnt.replace("PO-", ""));
						
					} else if (serviceEnt.matches("TI-(.*)")) {
						Tyre_id.add(serviceEnt.replace("TI-", ""));
						
					} else if (serviceEnt.matches("TR-(.*)")) {
						TyreRetread_id.add(serviceEnt.replace("TR-", ""));
						
					} else if(serviceEnt.matches("BI-(.*)")) {
						battery_id.add(serviceEnt.replace("BI-", ""));
						
					}
					 else if(serviceEnt.matches("PI-(.*)")) {
						part_id.add(serviceEnt.replace("PI-", ""));
					 }
					 else if(serviceEnt.matches("CI-(.*)")) {
						 cloth_id.add(serviceEnt.replace("CI-", ""));
					 }
					 else if(serviceEnt.matches("LI-(.*)")) {
						 laundry_id.add(serviceEnt.replace("LI-", ""));
					 }
				}

				// getting Fuel ID in Multiple Format
				String 			MULT_SE_id 			= "";
				String 			MULT_PO_id 			= "";
				String 			MULT_TI_id 			= "";
				String 			MULT_TR_id 			= "";
				String 			MULT_BI_id 			= "";
				String 			MULT_PI_id 			= "";
				String 			MULT_CI_id 			= "";
				String 			MULT_LI_id 			= "";

				// All Approval Amount Total
				Double 			AprovalAmount 		= 0.0;

				// Service Entries Approval List ID to Update and Select Amount Get
				if (ServiceEntries_id != null && !ServiceEntries_id.isEmpty()) {
					int i = 1;
					// Multiple Update in Approval Id to one Query
					for (String SE_ID : ServiceEntries_id) {
						// System.out.println(SE_ID);
						if (i != ServiceEntries_id.size()) {
							MULT_SE_id += SE_ID+ ",";
						} else {
							MULT_SE_id += SE_ID + "";
						}
						i++;
					}
					try {
						// new code update multiple fuel id in one query updating
						// Service Entries
						ServiceEntriesService.update_Vendor_ApprovalTO_Status_MULTIP_SERVICE_ID(MULT_SE_id,Approval.getApprovalId(), ServiceEntriesType.PAYMENT_MODE_CREATE_APPROVAL);
						// Update Approval Id in Fuel Tables
						ShowAmount = ServiceEntriesService.get_Amount_VendorApproval_IN_SERVICE_Entries(Approval.getApprovalId());
						
						if (ShowAmount != null && !ShowAmount.isEmpty()) {
							for (ServiceEntries fuel : ShowAmount) {
								if (fuel.getTotalservice_cost() != null) {
									AprovalAmount += fuel.getTotalservice_cost();
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView(
								"redirect:/ShowApprovalList.in?vendorId=" + Approval.getApprovalvendorID() + "", model);
					}
				}

				if (PurchaseOrder_id != null && !PurchaseOrder_id.isEmpty()) {
					int p = 1;
					// Multiple Update in Approval Id to one Query Format
					for (String PO_ID : PurchaseOrder_id) {
						// System.out.println(TI_ID);
						if (p != PurchaseOrder_id.size()) {
							MULT_PO_id += PO_ID + ",";
						} else {
							MULT_PO_id += PO_ID + "";
						}
						p++;
					}

					try {
						PurchaseOrdersService.update_Vendor_ApprovalTO_Status_MULTIP_PurchaseOrders(MULT_PO_id,Approval.getApprovalId(), InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_CREATE_APPROVAL);
						// Update Approval Id in Fuel Table
						PurchaseInvoice = PurchaseOrdersService.get_Amount_VendorApproval_IN_PurchaseOrders(Approval.getApprovalId());
						if (PurchaseInvoice != null && !PurchaseInvoice.isEmpty()) {
							for (PurchaseOrders PurchaseOrder : PurchaseInvoice) {

								if (PurchaseOrder.getPurchaseorder_totalcost() != null) {
									AprovalAmount += PurchaseOrder.getPurchaseorder_balancecost();
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView(
								"redirect:/ShowApprovalList.in?vendorId=" + Approval.getApprovalvendorID() + "", model);
					}
				}
				if (Tyre_id != null && !Tyre_id.isEmpty()) {
					int j = 1;
					// Multiple Update in Approval Id to one Query Format
					for (String TI_ID : Tyre_id) {
						// System.out.println(TI_ID);
						if (j != Tyre_id.size()) {
							MULT_TI_id += TI_ID + ",";
						} else {
							MULT_TI_id += TI_ID + "";
						}
						j++;
					}

					try {
						iventoryTyreService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreInvoice_ID(MULT_TI_id,
								Approval.getApprovalId(), InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_CREATE_APPROVAL);
						// Update Approval Id in Fuel Table
						TyreInvoice = iventoryTyreService.get_Amount_VendorApproval_IN_InventoryTyreInvoice(Approval.getApprovalId(), userDetails.getCompany_id());
						
						if (TyreInvoice != null && !TyreInvoice.isEmpty()) {
							for (InventoryTyreInvoiceDto Tyre : TyreInvoice) {

								if (Tyre.getINVOICE_AMOUNT() != null) {
									AprovalAmount += Tyre.getINVOICE_AMOUNT();
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView(
								"redirect:/ShowApprovalList.in?vendorId=" + Approval.getApprovalvendorID() + "", model);
					}
				}
				if (TyreRetread_id != null && !TyreRetread_id.isEmpty()) {
					int k = 1;
					// Multiple Update in Approval Id to one Query Format
					for (String TR_ID : TyreRetread_id) {
						// System.out.println(TR_ID);
						if (k != TyreRetread_id.size()) {
							MULT_TR_id += TR_ID + ",";
						} else {
							MULT_TR_id += TR_ID + "";
						}
						k++;
					}

					try {
						iventoryTyreService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreRetread_ID(MULT_TR_id, Approval.getApprovalId(), InventoryTyreRetreadDto.TR_VENDOR_PAYMODE_STATUS_CREATE_APPROVAL);
						// Update Approval Id in Fuel Table
						TyreRetread = iventoryTyreService.get_Amount_VendorApproval_IN_InventoryTyreRetread(Approval.getApprovalId(), userDetails.getCompany_id());
						if (TyreRetread != null && !TyreRetread.isEmpty()) {
							for (InventoryTyreRetreadDto Tyre : TyreRetread) {
								if (Tyre.getTR_ROUNT_AMOUNT() != null) {
									AprovalAmount += Tyre.getTR_ROUNT_AMOUNT();
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView(
								"redirect:/ShowApprovalList.in?vendorId=" + Approval.getApprovalvendorID() + "", model);

					}
				}

				if (battery_id != null && !battery_id.isEmpty()) {
					int j = 1;
					// Multiple Update in Approval Id to one Query Format
					for (String BI_ID : battery_id) {
						// System.out.println(TI_ID);
						if (j != battery_id.size()) {
							MULT_BI_id += BI_ID + ",";
						} else {
							MULT_BI_id += BI_ID + "";
						}
						j++;
					}

					try {
						batteryInvoiceService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryBatteryInvoice_ID(MULT_BI_id, Approval.getApprovalId(), InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_CREATE_APPROVAL);
						// Update Approval Id in Fuel Table
						batteryInvoiceList = batteryInvoiceService.get_Amount_VendorApproval_IN_InventoryBatteryInvoice(Approval.getApprovalId(), userDetails.getCompany_id());

						if (batteryInvoiceList != null && !batteryInvoiceList.isEmpty()) {
							for (BatteryInvoiceDto invoiceDto : batteryInvoiceList) {

								if (invoiceDto.getInvoiceAmount() != null) {
									AprovalAmount += invoiceDto.getInvoiceAmount();
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView(
								"redirect:/ShowApprovalList.in?vendorId=" + Approval.getApprovalvendorID() + "", model);
					}
				
					
				
				}
				if (part_id != null && !part_id.isEmpty()) {
					int j = 1;
					// Multiple Update in Approval Id to one Query Format
					for (String PI_ID : part_id) {
						if (j != part_id.size()) {
							MULT_PI_id += PI_ID + ",";
						} else {
							MULT_PI_id += PI_ID + "";
						}
						j++;
					}

					try {
						partInvoiceService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryPartInvoice_ID(MULT_PI_id, Approval.getApprovalId(), InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_CREATE_APPROVAL);
						// Update Approval Id in partinvoice Table
						
						partInvoiceList = partInvoiceService.get_Amount_VendorApproval_IN_InventoryPartInvoice(Approval.getApprovalId(), userDetails.getCompany_id());
						if (partInvoiceList != null && !partInvoiceList.isEmpty()) {
							for (PartInvoiceDto invoiceDto : partInvoiceList) {

								if (invoiceDto.getTotalPartAmount() != null) {
									AprovalAmount += invoiceDto.getTotalPartAmount();
									
								}
							} 
						}
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView(
								"redirect:/ShowApprovalList.in?vendorId=" + Approval.getApprovalvendorID() + "", model);
					}
				}
				
				if (cloth_id != null && !cloth_id.isEmpty()) {
					
					int j = 1;
					// Multiple Update in Approval Id to one Query Format
					for (String CI_ID : cloth_id) {
						if (j != cloth_id.size()) {
							MULT_CI_id += CI_ID + ",";
						} else {
							MULT_CI_id += CI_ID + "";
						}
						j++;
					}

					try {
						clothInventoryService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryClothInvoice_ID(MULT_CI_id, Approval.getApprovalId(), InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_CREATE_APPROVAL);
						// Update Approval Id in partinvoice Table
						
						clothInvoiceList = clothInventoryService.get_Amount_VendorApproval_IN_InventoryClothInvoice(Approval.getApprovalId(), userDetails.getCompany_id());
						if (clothInvoiceList != null && !clothInvoiceList.isEmpty()) {
							for (ClothInvoiceDto invoiceDto : clothInvoiceList) {
								if (invoiceDto.getTotalClothAmount() != null) {
									AprovalAmount += invoiceDto.getTotalClothAmount();
								}
							} 
						}
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView(
								"redirect:/ShowApprovalList.in?vendorId=" + Approval.getApprovalvendorID() + "", model);
					}
				}
				
				if (laundry_id != null && !laundry_id.isEmpty()) {
					
					int j = 1;
					// Multiple Update in Approval Id to one Query Format
					for (String CI_ID : laundry_id) {
						if (j != laundry_id.size()) {
							MULT_LI_id += CI_ID + ",";
						} else {
							MULT_LI_id += CI_ID + "";
						}
						j++;
					}

					try {
						clothInventoryService.update_Vendor_ApprovalTO_Status_LaundryInvoice_ID(MULT_LI_id, Approval.getApprovalId(), InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_CREATE_APPROVAL, userDetails.getCompany_id());
						// Update Approval Id in partinvoice Table
						
						laundryInvoiceList = clothInventoryService.getVendorInventoryLaundryInvoice(Approval.getApprovalId(), userDetails.getCompany_id());
						if (laundryInvoiceList != null && !laundryInvoiceList.isEmpty()) {
							for (UpholsterySendLaundryInvoiceDto invoiceDto : laundryInvoiceList) {

								if (invoiceDto.getTotalCost() != null) {
									AprovalAmount += invoiceDto.getTotalCost();
								}
							} 
						}
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView(
								"redirect:/ShowApprovalList.in?vendorId=" + Approval.getApprovalvendorID() + "", model);
					}
				}
				
				
				for(String dto:select_all) {
					String InvoiceStr 	= dto.split("-")[1];
					String placeIdStr 	= dto.split("-")[0];
					Long inoviceID		= Long.parseLong(InvoiceStr);
					short placeId = 0;
					if(placeIdStr.contains("PO")) {
						placeId = ApprovalType.APPROVAL_TYPE_PURCHASE_ORDER;
						purchaseOrders 	= PurchaseOrdersService.getPurchaseOrders(inoviceID);
						invoiceAmount	= purchaseOrders.getBalanceAmount();
					}
					if(placeIdStr.contains("SE")) {
						placeId = ApprovalType.APPROVAL_TYPE_SERVICE_ENTRIES;
						serviceEntries 	= ServiceEntriesService.getServiceEntriesDetails(inoviceID,userDetails.getCompany_id());
						invoiceAmount	= serviceEntries.getTotalservice_cost();
					}
					if(placeIdStr.contains("TI")) {
						placeId = ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_INVOICE;
						inventoryTyreInvoice 	=  iventoryTyreService.Get_list_InventoryTyreInvoice(inoviceID,userDetails.getCompany_id());
						invoiceAmount			= inventoryTyreInvoice.getINVOICE_AMOUNT();
					}
					if(placeIdStr.contains("TR")) {
						placeId = ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_RETREAD;
						inventoryTyreRetread = inventoryTyreRetreadRepository.Get_InventoryTyreRetread(inoviceID,userDetails.getCompany_id());
						invoiceAmount			= inventoryTyreRetread.getTR_ROUNT_AMOUNT();
					}
					if(placeIdStr.contains("BI")) {
						placeId = ApprovalType.APPROVAL_TYPE_INVENTORY_BATTERY_INVOICE;
						batteryInvoice = batteryInvoiceRepository.getBatteryInvoice(inoviceID,userDetails.getCompany_id());
						invoiceAmount			= batteryInvoice.getInvoiceAmount();
					}
					if(placeIdStr.contains("CI")) {
						placeId = ApprovalType.APPROVAL_TYPE_INVENTORY_CLOTH_INVOICE;
						clothInvoice = clothInventoryRepository.getClothInvoice(inoviceID,userDetails.getCompany_id());
						invoiceAmount	= clothInvoice.getTotalClothAmount();
					}
					if(placeIdStr.contains("PI")) {
						placeId 		= ApprovalType.APPROVAL_TYPE_INVENTORY_PART_INVOICE;
						partInvoiceDto 	= partInvoiceService.details_of_PartInvoice(inoviceID,userDetails.getCompany_id());
						invoiceAmount	= Double.parseDouble(partInvoiceDto.getInvoiceAmount());
					}
					if(placeIdStr.contains("LI")) {
						placeId 		= ApprovalType.APPROVAL_TYPE_INVENTORY_LAUNDRY_INVOICE;
						laundryInvoice 	= laundryInvoiceRepository.getLaundryInvoiceDetails(inoviceID,userDetails.getCompany_id());
						invoiceAmount	= laundryInvoice.getTotalCost();
					}
					
					PendingVendorPayment	payment	= pendingVendorPaymentService.getPendingVendorPaymentById(inoviceID,placeId);
					
					SubApproval = approval.prepareVendorSubApprovalDetails(Approval,payment);
					vendorSubApprovalService.saveSubApproval(SubApproval);
				}

				// update Approval Total Amount
				vendorApprovalService.updateVendorApproval_Aoumt(Approval.getApprovalId(), round(AprovalAmount, 0));

				// System.out.println(ServiceEntries_id);

				// show the VendorApproval List
				// model.put("saveVendorApproval", true);
				// return new ModelAndView("VendorApproval_Add", model);
				return new ModelAndView("redirect:/AddServiceApproval.in?approvalId=" + Approval.getApprovalId() + "");

			} else {
				model.put("EmptyApproval", true);
				return new ModelAndView("redirect:/ShowApprovalList.in?vendorId=" + vendorDto.getVendorId() + "", model);

			}
		
	}

	// save Issues in databases
	@RequestMapping(value = "/AddServiceApproval", method = RequestMethod.GET)
	public ModelAndView addServiceApproval(@RequestParam("approvalId") final Long approvalId,
			final HttpServletRequest result) {
		Map<String, Object> 					model 				= new HashMap<String, Object>();
		CustomUserDetails						userDetails			= null;
		VendorApprovalDto 						showApproval		= null;
		DecimalFormat 							decimalFormat		= null;
		Double 									Total				= 0.0;
		List<FuelDto>							FuelDto				= null;
		List<FuelDto>							FuelDtoBL			= null;
		List<ServiceEntriesDto> 				AllOtherEntries		= null;
		List<ServiceEntriesDto> 				ServiceEntries 		= null;
		List<PurchaseOrdersDto> 				PurchaseOrder	 	= null;
		List<InventoryTyreInvoiceDto> 			TyreEntries 		= null;
		List<InventoryTyreRetreadDto> 			TyreRetreadEntries 	= null;
		List<BatteryInvoiceDto> 				batteryInvoiceList 	= null;
		List<PartInvoiceDto> 					partInvoiceList 	= null;
		List<ClothInvoiceDto> 					clothInvoiceList 	= null;
		List<UpholsterySendLaundryInvoiceDto> 	laundryInvoiceList 	= null;
		HashMap<String, Object>					configuration 		= null;
		List<VendorSubApprovalDetailsDto>		showSubApproval		= null;
		
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			showApproval	= vendorApprovalService.getVendorApprovalDetails(approvalId, userDetails.getCompany_id());
			
			if(showApproval == null) {
				return new ModelAndView("redirect:VendorApprovalCreated/1.in", model);
			}
			
			if(showApproval.getApprovalStatusId() == FuelVendorPaymentMode.PAYMENT_MODE_PAID
					|| showApproval.getApprovalStatusId() == FuelVendorPaymentMode.PAYMENT_MODE_NEGOTIABLE_PAID
					|| showApproval.getApprovalStatusId() == FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID) {
				return new ModelAndView("redirect:ShowApprovalPayment.in?approvalId="+showApproval.getApprovalId());
			}else if(showApproval.getApprovalStatusId() == FuelVendorPaymentMode.PAYMENT_MODE_APPROVED) {
				return new ModelAndView("redirect:approvedPayment.in?approvalId=="+showApproval.getApprovalId());
			}
			
			showSubApproval	= vendorApprovalService.getVendorSubApprovalDetailsList(approvalId);
			
			
			configuration	=   companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			
			decimalFormat 	= new DecimalFormat("##,##,##0");
			decimalFormat.setMaximumFractionDigits(0);
			Total 		= showApproval.getApprovalTotal();
			if (showApproval.getApprovalTotal() == null) {
				Total = 0.0;
			}
			
			
			model.put("approvalTotal", decimalFormat.format(Total));
			// get the Approval ID to Details
			model.put("approval", showApproval);
			model.put("showSubApproval", showSubApproval);
			model.put("configuration", configuration);
			// get Approval Vendor id to get all Details
			model.put("vendor", VenBL.getVendorDetails(vendorService.getVendorDetails(showApproval.getApprovalvendorID(), userDetails.getCompany_id())));

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("VendorApproval_ServiceEntries_Add", model);
	}

	/* show main page of Issues */
	@RequestMapping(value = "/RemoveServiceApprovalList", method = RequestMethod.GET)
	public ModelAndView RemoveServiceApprovalList(@RequestParam("Id") Long serviceEntries_id,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		ServiceEntries Service_idToAPProvalID = ServiceEntriesService.getServiceEntries(serviceEntries_id);

		try {
			if (Service_idToAPProvalID != null) {
				// get Fuel ID To Update Status is NOTPAID
				ServiceEntriesService.update_Vendor_ApprovalTO_Status_MULTIP_SERVICE_ID("" + serviceEntries_id,
						(long) 0, ServiceEntriesType.PAYMENT_MODE_NOT_PAID);
				// update Approval Total Amount
				vendorApprovalService.update_Remove_VendorApproval_Aoumt(
						Service_idToAPProvalID.getService_vendor_approvalID(),
						round(Service_idToAPProvalID.getTotalservice_cost(), 2));
				// System.out.println(AprovalAmount);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		model.put("remove", true);

		return new ModelAndView("redirect:/AddServiceApproval.in?approvalId="
				+ Service_idToAPProvalID.getService_vendor_approvalID() + "", model);
	}

	/* show main page of Remove Purchase Order ApprovalList */
	@GetMapping(path = "/RemovePOApprovalList")
	public ModelAndView removePOApprovalList(@RequestParam("ID") Long invoiceId, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		PurchaseOrders invoiceDetails = PurchaseOrdersService.get_ApprovalID_AMOUNT_PurchaseOrders(invoiceId);

		try {

			if (invoiceDetails != null) {
				PurchaseOrdersService.update_Vendor_ApprovalTO_Status_MULTIP_PurchaseOrders("" + invoiceId, (long) 0,
						InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
				
				vendorApprovalService.update_Remove_VendorApproval_Aoumt(
						invoiceDetails.getPurchaseorder_vendor_approvalID(),
						round(invoiceDetails.getPurchaseorder_totalcost(), 2));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		model.put("remove", true);
		return new ModelAndView("redirect:/AddServiceApproval.in?approvalId="
				+ invoiceDetails.getPurchaseorder_vendor_approvalID() + "", model);
	}

	/* show main page of RemoveTyreApprovalList */
	@RequestMapping(value = "/RemoveTyreApprovalList", method = RequestMethod.GET)
	public ModelAndView RemoveTyreApprovalList(@RequestParam("Id") Long Tyre_id, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		InventoryTyreInvoice Service_idToAPProvalID = iventoryTyreService.Get_list_InventoryTyreInvoice(Tyre_id, userDetails.getCompany_id());

		try {

			if (Service_idToAPProvalID != null) {
				// get Fuel ID To Update Status is NOTPAID
				iventoryTyreService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreInvoice_ID("" + Tyre_id,
						(long) 0, InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
				// update Approval Total Amount
				vendorApprovalService.update_Remove_VendorApproval_Aoumt(Service_idToAPProvalID.getTYRE_APPROVAL_ID(),
						round(Service_idToAPProvalID.getINVOICE_AMOUNT(), 2));
				// System.out.println(AprovalAmount);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		model.put("remove", true);
		return new ModelAndView(
				"redirect:/AddServiceApproval.in?approvalId=" + Service_idToAPProvalID.getTYRE_APPROVAL_ID() + "",
				model);
	}

	/* show main page of RemoveTyreApprovalList */
	@RequestMapping(value = "/RemoveBatteryApprovalList", method = RequestMethod.GET)
	public ModelAndView RemoveBatteryApprovalList(@RequestParam("Id") Long batteryInvoiceId, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> 	model 					= null;
		CustomUserDetails		userDetails				= null;
		BatteryInvoiceDto 		batteryInvoiceDTO 		= null;
		BatteryInvoiceDto 		batteryInvoice 			= null;
		
		try {
			
			model 				= new HashMap<String, Object>();
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			batteryInvoiceDTO 	= new BatteryInvoiceDto();
			batteryInvoiceDTO.setBatteryInvoiceId(batteryInvoiceId);
			batteryInvoiceDTO.setCompanyId(userDetails.getCompany_id());
			
			batteryInvoice = batteryInvoiceService.getBatteryInvoice(batteryInvoiceDTO);
			
			if (batteryInvoice != null) {
				// get Fuel ID To Update Status is NOTPAID
				batteryInvoiceService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryBatteryInvoice_ID("" + batteryInvoiceId,
						(long) 0, InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
				// update Approval Total Amount
				vendorApprovalService.update_Remove_VendorApproval_Aoumt(batteryInvoice.getBatteryApprovalId(),
						round(batteryInvoice.getInvoiceAmount(), 2));
				// System.out.println(AprovalAmount);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		model.put("remove", true);
		return new ModelAndView(
				"redirect:/AddServiceApproval.in?approvalId=" + batteryInvoice.getBatteryApprovalId() + "",
				model);
	}

	/* show main page of RemoveTyreApprovalList */
	@RequestMapping(value = "/RemoveTyreRetApprovalList", method = RequestMethod.GET)
	public ModelAndView RemoveTyreRetApprovalList(@RequestParam("Id") Long TyreRetread_id,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		InventoryTyreRetread Service_idToAPProvalID = iventoryTyreService
				.Get_ApprovalID_InventoryTyreRetread(TyreRetread_id);

		try {

			// get Fuel ID To Update Status is NOTPAID
			iventoryTyreService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreRetread_ID("" + TyreRetread_id,
					(long) 0, InventoryTyreRetreadDto.TR_VENDOR_PAYMODE_STATUS_NOTPAID);

			// update Approval Total Amount
			vendorApprovalService.update_Remove_VendorApproval_Aoumt(Service_idToAPProvalID.getTR_APPROVAL_ID(),
					round(Service_idToAPProvalID.getTR_ROUNT_AMOUNT(), 2));
			// System.out.println(AprovalAmount);

		} catch (Exception e) {

			e.printStackTrace();
		}
		model.put("remove", true);

		return new ModelAndView(
				"redirect:/AddServiceApproval.in?approvalId=" + Service_idToAPProvalID.getTR_APPROVAL_ID() + "");
	}
	
	@RequestMapping(value = "/RemovePartApprovalList", method = RequestMethod.GET)
	public ModelAndView RemovePartApprovalList(@RequestParam("Id") Long partInvoiceId, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> 	model 					= null;
		CustomUserDetails		userDetails				= null;
		PartInvoiceDto 			partInvoiceDTO 			= null;
		PartInvoiceDto 			partInvoice 			= null;
		
		try {
			
			model 				= new HashMap<String, Object>();
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			partInvoiceDTO 	= new PartInvoiceDto();
			partInvoiceDTO.setPartInvoiceId(partInvoiceId);
			partInvoiceDTO.setCompanyId(userDetails.getCompany_id());
			
			partInvoice = partInvoiceService.getPartInvoice(partInvoiceDTO);
			
			
			if (partInvoice != null) {
				// get Fuel ID To Update Status is NOTPAID
				partInvoiceService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryPartInvoice_ID("" + partInvoiceId,
						(long) 0, InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);
				// update Approval Total Amount
				vendorApprovalService.update_Remove_VendorApproval_Aoumt(partInvoice.getPartApprovalId(),
						round(Double.parseDouble(partInvoice.getInvoiceAmount()), 2));
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		model.put("remove", true);
		return new ModelAndView(
				"redirect:/AddServiceApproval.in?approvalId=" + partInvoice.getPartApprovalId() + "",
				model);
	}


	/*****************************************************************************
	 * Testing Loading datatable using Gson
	 ******************************************************************************/

	@RequestMapping(value = "/getApprovalList", method = RequestMethod.GET)
	public void getDriverList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<VendorApproval> ApprovalList = new ArrayList<VendorApproval>();
		for (VendorApproval add : vendorApprovalService.findAllVendorApprovalList(userDetails.getCompany_id())) {
			VendorApproval Approval = new VendorApproval();
			Approval.setApprovalId(add.getApprovalId());
			Approval.setApprovalvendorID(add.getApprovalvendorID());
			//Approval.setApprovalvendorName(add.getApprovalvendorName());
			//Approval.setApprovalvendorType(add.getApprovalvendorType());
			Approval.setCreated(add.getCreated());
			Approval.setApprovalCreateById(add.getApprovalCreateById());
			Approval.setApprovalTotal(add.getApprovalTotal());
			Approval.setApprovalStatusId(add.getApprovalStatusId());
			ApprovalList.add(Approval);
		}

		Gson gson = new Gson();

		// System.out.println("jsonApproval List = " +
		// gson.toJson(ApprovalList));

		response.getWriter().write(gson.toJson(ApprovalList));
	}
	
	/* show main page of Issues */
	@GetMapping(path = "/VendorApprovalCreated/{pageNumber}")
	public String vendorApprovalCreated(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails				userDetails					= null;
		Page<VendorApproval> 			page						= null;
		List<VendorApprovalDto> 		pageList					= null;
		VendorApprovalDto 				pageListBL					= null;
		List<VendorApprovalDto> 		finalList					= null;
		HashMap<String, Object>			configuration 				= null;
		List<VendorSubApprovalDetails>  vendorSubApprovalDetails 	= null;
		try {
			finalList		= new ArrayList<>();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			page 			= vendorApprovalService.getDeployment_Page_VendorApproval(FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL, pageNumber, userDetails.getCompany_id());
			
			if(page != null) {
				
				int current 	= page.getNumber() + 1;
				int begin 		= Math.max(1, current - 5);
				int end 		= Math.min(begin + 10, page.getTotalPages());
				
				pageList 		= vendorApprovalService.findAll_VendorApproval(FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL, pageNumber, userDetails.getCompany_id());
				
				if(pageList != null && !pageList.isEmpty()) {
					for(VendorApprovalDto dto : pageList) {
						vendorSubApprovalDetails 	= vendorSubApprovalDetailsRepository.getVendorSubApprovalDetails(dto.getApprovalId(),userDetails.getCompany_id());
						pageListBL 					= approval.prepareVendorApprovalList(dto, vendorSubApprovalDetails);
						finalList.add(pageListBL);
					}
					
					model.addAttribute("configuration", configuration);
					model.addAttribute("deploymentLog", page);
					model.addAttribute("beginIndex", begin);
					model.addAttribute("endIndex", end);
					model.addAttribute("currentIndex", current);
					model.addAttribute("approvalCount", page.getTotalElements());
					model.addAttribute("approval", finalList);
				}
				
			}

		} catch (NullPointerException e) {
			LOGGER.error("Err", e);
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("vendors Page:", e);
			e.printStackTrace();
		}
		return	"ApprovalCreatedEntries";
	}

}
