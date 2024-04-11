package org.fleetopgroup.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.RenealReminderStatus;
import org.fleetopgroup.constant.RenewalApprovalStatus;
import org.fleetopgroup.constant.RenewalReminderConfiguration;
import org.fleetopgroup.constant.VehicleAgentConstant;
import org.fleetopgroup.constant.VehicleOwnerShip;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.persistence.bl.DriverReminderBL;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.RenewalTypeBL;
import org.fleetopgroup.persistence.bl.VehicleAgentTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderApprovalDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.RenewalApprovalSequenceCounter;
import org.fleetopgroup.persistence.model.RenewalReminder;
import org.fleetopgroup.persistence.model.RenewalReminderApproval;
import org.fleetopgroup.persistence.model.RenewalReminderSequenceCounter;
import org.fleetopgroup.persistence.model.RenewalType;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.persistence.model.WorkOrders;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalApprovalSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalTypeService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentIncomeExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RenewalReminderApprovedController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IRenewalReminderService RenewalReminderService;
	@Autowired
	private IVehicleService vehicleService;

	@Autowired
	private IUserProfileService userProfileService;
	
	@Autowired
	private IRenewalReminderSequenceService	renewalReminderSequenceService;
	
	@Autowired
	private IDriverService	driverService;
	
	@Autowired
	private IWorkOrdersService	workOrdersService;
	
	@Autowired
	private IRenewalApprovalSequenceService	renewalApprovalSequenceService;
	
	@Autowired private IRenewalReminderDocumentService	documentService;
	
	@Autowired private IRenewalTypeService	renewalTypeService;
	
	@Autowired private ICompanyConfigurationService	companyConfigurationService;
	@Autowired IVehicleAgentTxnCheckerService			vehicleAgentTxnCheckerService;
	@Autowired private	IVehicleAgentIncomeExpenseDetailsService			vehicleAgentIncomeExpenseDetailsService;


	RenewalTypeBL DriDT = new RenewalTypeBL();
	RenewalReminderBL RRBL = new RenewalReminderBL();
	DriverReminderBL DriverRem = new DriverReminderBL();
	VehicleBL VBL = new VehicleBL();
	VehicleAgentTxnCheckerBL	agentTxnCheckerBL			= new VehicleAgentTxnCheckerBL();
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat sqlFormatTime = new SimpleDateFormat("dd-MM-yyyy hh:ss:mm");
	SimpleDateFormat sqlFormat	   = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping(value = "/searchRenRemApp", method = RequestMethod.POST)
	public ModelAndView searchRenRemApp(@RequestParam("Search") String SearchDate, final HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			model.put("renewal", RRBL.Only_Show_ListofRenewalReminderApprovalDto(
					RenewalReminderService.Search_RenewalReminderApproval(SearchDate)));

			model.put("SelectStatus", RenewalApprovalStatus.OPEN);
			model.put("SelectPage", 1);
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Renewal Reminder:", e);
		}
		return new ModelAndView("Renewal_Reminder_Approval", model);
	}

	@RequestMapping(value = "/searchRenRemAppShow", method = RequestMethod.POST)
	public ModelAndView searchRenRemAppShow(@RequestParam("Search") final Long renewalApproval_Number,
			final HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			RenewalReminderApprovalDto ShowApproval = RenewalReminderService.Get_RenewalReminderApprovalByApprovalNumber(renewalApproval_Number, userDetails.getCompany_id(), userDetails.getId());
			if (ShowApproval.getApprovalStatusId()	== RenewalApprovalStatus.OPEN) {
				// Note : Approval Status id Open the that page
				return new ModelAndView("redirect:/AddRenRemApproval.in?AID=" + ShowApproval.getRenewalApproval_id() + "&page=1", model);
			} else if (ShowApproval.getApprovalStatusId()	== RenewalApprovalStatus.APPROVED) {
				// Note : Approval Status id APPROVED the that page
				return new ModelAndView("redirect:/2/ShowRenRemAppPay.in?AID=" + ShowApproval.getRenewalApproval_id() + "&page=1", model);
			} else {
				// Note : Approval Status id PAID the show page

				DecimalFormat df = new DecimalFormat("##,##,##0");
				df.setMaximumFractionDigits(0);
				Double Total = ShowApproval.getApprovalPayment_Amount();
				if (ShowApproval.getApprovalPayment_Amount() == null) {
					Total = 0.0;
				}
				model.put("approvalTotal", df.format(Total));

				Double Approved_Amount = ShowApproval.getApprovalApproved_Amount();
				if (ShowApproval.getApprovalApproved_Amount() == null) {
					Approved_Amount = 0.0;
				}
				model.put("ApprovedAmount", df.format(Approved_Amount));

				Double Pending_Amount = ShowApproval.getApprovalPending_Amount();
				if (ShowApproval.getApprovalPending_Amount() == null) {
					Pending_Amount = 0.0;
				}
				model.put("PendingAmount", df.format(Pending_Amount));

				Double Cancel_Amount = ShowApproval.getApprovalCancel_Amount();
				if (ShowApproval.getApprovalCancel_Amount() == null) {
					Cancel_Amount = 0.0;
				}
				model.put("CancelAmount", df.format(Cancel_Amount));
				// get the Approval ID to Details
				model.put("approval", RRBL.getApprovalDetails(ShowApproval));

				List<RenewalReminderDto> pageList = RRBL.Show_Only_ApprovalListofRenewal(
						RenewalReminderService.Find_Approval_RenewalReminderList(ShowApproval.getRenewalApproval_id(), userDetails));

				model.put("renewal", pageList);
			}
			model.put("SelectStatus", RenewalApprovalStatus.OPEN);
			model.put("SelectPage", 1);

		} catch (NullPointerException e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/RenRemApp/1/1.in", model);
		} catch (Exception e) {
			LOGGER.error("Renewal Reminder:", e);
			model.put("NotFound", true);
			return new ModelAndView("redirect:/RenRemApp/1/1.in", model);
		}
		return new ModelAndView("RenewalReminderApproval_Show", model);
	}

	// Note: This /RenRemApp Controller is Renewal Reminder Approval New entries
	@RequestMapping(value = "/RenRemApp/{Status}/{pageNumber}", method = RequestMethod.GET)
	public String RenewalReminderList(@PathVariable("Status") short Status,
			@PathVariable("pageNumber") Integer pageNumber, Model model) throws Exception {

		long RenewalReminderCount = 0;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Page<RenewalReminderApproval> page = RenewalReminderService.getDeployment_Page_RenewalReminderApproval(Status,
					pageNumber, userDetails);
			if(page != null) {
				
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());
				
				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);
				RenewalReminderCount	= page.getTotalElements();
			}

			model.addAttribute("RenewalReminderCount", RenewalReminderCount);

			List<RenewalReminderApprovalDto> pageList = RRBL.Only_Show_ListofRenewalReminderApprovalDto(
					RenewalReminderService.Find_listRenewalReminderApproval(Status, pageNumber, userDetails));

			model.addAttribute("renewal", pageList);
			model.addAttribute("SelectStatus", RenewalApprovalStatus.getRenewalStatusName(Status));
			model.addAttribute("SelectStatusId", Status);
			model.addAttribute("SelectPage", pageNumber);

			if (pageList != null) {
				model.addAttribute("TodayDueRenewalRemindercount", pageList.size());
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("Renewal_Reminder Page:", e);
			e.printStackTrace();
		}
		return "Renewal_Reminder_Approval";
	}

	// Note: This /RenRemAppSearch Controller is Renewal Reminder to Search
	// RenewalReminder
	@RequestMapping(value = "/RenRemAppSearch", method = RequestMethod.POST)
	public ModelAndView DateRenewalReminder(@RequestParam("RRDate") String SearchDate,
			final HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the driver list in all
		try {

			if (SearchDate != "") {
				List<RenewalReminderDto> Renewal = RenewalReminderService.TodayRenewalReminderList(sqlFormat.format(dateFormat.parse(SearchDate)) + DateTimeUtility.DAY_END_TIME);
				model.put("renewal", RRBL.Approval_Show_ListofRenewalDto(Renewal));
				// show the vehicle Group service of the driver
				model.put("RRDate", SearchDate);

			}
			model.put("SelectStatus", RenealReminderStatus.OPEN);
			model.put("SelectPage", 1);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Renewal Reminder:", e);
			e.printStackTrace();
		}
		return new ModelAndView("Renewal_ReminderApproval_Filter", model);
	}

	/* Save Approval Details in back end process show main page of Issues */
	@RequestMapping(value = "/ApprovalRenewalReminderList", method = RequestMethod.POST)
	public ModelAndView ApprovalRenewalReminderList(
			@RequestParam("SearchDate") final String SearchDate,
			@RequestParam("Selectfuel_id") final String[] select_all, 
			final HttpServletRequest request,RedirectAttributes redir) throws Exception {

		Map<String, Object> 				model 						= new HashMap<String, Object>();
		CustomUserDetails					userDetails					= null;
		Long 								RenewalApproval_id 			= null;
		long 								renewal_R_Number			= 0;
		long 								renewalApproval_Number 		= 0;
		boolean 							VEHICLESTATUS 				= false;
		RenewalReminderApproval 			Approval					= null;
		RenewalApprovalSequenceCounter		sequenceCounter				= null;
		RenewalReminderSequenceCounter		counter						= null;
		RenewalReminderDto 					OldRenewal					= null;
		RenewalReminder 					renewalReminder				= null;
		VehicleDto 							CheckVehicleStatus			= null;
		List<RenewalReminderDto> 			renewal						= null;
		
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ValueObject		ownerShipObject		= null;
			if (select_all != null) {
				Approval 				= RRBL.prepare_RenewalReminderApproval(SearchDate);
				
				sequenceCounter 		= renewalApprovalSequenceService.findNextRenewalApproval_Number(userDetails.getCompany_id());
				renewalApproval_Number 	= sequenceCounter.getNextVal();
				
				Approval.setRenewalApproval_Number(renewalApproval_Number);

				// Note : Select Renewal Reminder to Create Approval Entries
				RenewalReminderService.add_RenewalReminderApproval(Approval);
				//renewalApprovalSequenceService.updateNextRenewalApproval_Number(renewalApproval_Number + 1, userDetails.getCompany_id());
				RenewalApproval_id 		= Approval.getRenewalApproval_id();
				// getting RenewalReminder ID in Multiple Search to Get Create New
				
				
				String TIDMandatory = "";
				
				if (select_all != null) {
					
					int i = 0;
					if (i != select_all.length) {
						
						for (String serviceEnt : select_all) {
								OldRenewal = RenewalReminderService.getRenewalReminderById(Long.parseLong(serviceEnt), userDetails);
							if (OldRenewal != null) {
								try {
									
									 renewalReminder 	= RRBL.prepare_Approval_to_RenewalRemider(OldRenewal);
									// Check Vehicle Status Current IN ACTIVE OR NOT
									 CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(renewalReminder.getVid());

									

									switch (CheckVehicleStatus.getvStatusId()) {
									case VehicleStatusConstant.VEHICLE_STATUS_ACTIVE:

										VEHICLESTATUS = true;
										break;
									case VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP:

										VEHICLESTATUS = true;
										break;
									case VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE:

										VEHICLESTATUS = true;
										break;
									case VehicleStatusConstant.VEHICLE_STATUS_SURRENDER:

										if (OldRenewal.getRenewal_type().equalsIgnoreCase("INSURANCE")) {
											VEHICLESTATUS = true;
										} else {

											VEHICLESTATUS = false;
										}
										break;

									default:
										VEHICLESTATUS = false;
										break;
									}
									Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
									
									if(permission.contains(new SimpleGrantedAuthority("ALLOW_ALL_STATUS_RENEWAL"))) {
										VEHICLESTATUS	= true;
									}

									if (VEHICLESTATUS) {

										// Here Checking the Renewal Reminder
										// Details
										// Date From and To
										// Value Details
										String Query = "RR.companyId = "+userDetails.getCompany_id()+" AND RR.markForDelete = 0 AND ( RR.vid="
												+ renewalReminder.getVid() + " "
												+ " AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId()
												+ " AND RR.renewal_Subid =" + renewalReminder.getRenewal_Subid()
												+ "  AND '" + renewalReminder.getRenewal_from()
												+ "' between RR.renewal_from AND RR.renewal_to  OR RR.vid="
												+ renewalReminder.getVid() + " "
												+ " AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId()
												+ " AND RR.renewal_Subid =" + renewalReminder.getRenewal_Subid()
												+ "  AND '" + renewalReminder.getRenewal_to()
												+ "' between RR.renewal_from AND RR.renewal_to ) ";
										// show Vehicle Name Renewal Type and
										// Renewal
										// SubType List
										 renewal = RRBL.prepareListofRenewalDto(RenewalReminderService.Validate_RenewalReminder(Query));

										if (renewal != null && !renewal.isEmpty()) {
											// Checking the Value Of Mandatory
											// Compliance
											for (RenewalReminderDto add : renewal) {
												TIDMandatory += "<span>" + add.getVehicle_registration()
												+ " Renewal Type <a href=\"showRenewalReminder.in?renewal_id="
												+ add.getRenewal_id() + "\" target=\"_blank\">"
												+ add.getRenewal_type() + " " + add.getRenewal_subtype()
												+ "  <i class=\"fa fa-external-link\"></i></a>  Already Available"
												+ add.getRenewal_from() + " To " + add.getRenewal_to()
												+ " </span>, <br>";
											}
											redir.addFlashAttribute("VMandatory", TIDMandatory);
											model.put("Already", true);
										} else {
											renewalReminder.setRenewal_approvedID(RenewalApproval_id);
											// Note: save Renewal Reminder */
											counter	= renewalReminderSequenceService.findNextRenewal_R_Number(userDetails.getCompany_id());
											if(counter == null) {
												model.put("sequenceNotFound", true);
												return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
											}
											renewal_R_Number  = counter.getNextVal();
											renewalReminder.setRenewal_R_Number(renewal_R_Number);
											
											RenewalReminderService.updateNewRRCreated(renewalReminder.getVid(), renewalReminder.getRenewalTypeId(), renewalReminder.getRenewal_Subid());

											RenewalReminderService.addRenewalReminder(renewalReminder);
												
											if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED ){
												ownerShipObject	= new ValueObject();
												ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
												ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, renewalReminder.getRenewal_id());
												ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, renewalReminder.getVid());
												ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, renewalReminder.getRenewal_Amount());
												ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
												ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_RENEWAL);
												ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
												ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, sqlFormat.parse(sqlFormat.format(renewalReminder.getRenewal_dateofpayment())));
												ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Renewal Entry");
												ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Renewal Number : "+renewalReminder.getRenewal_R_Number());
												ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
												ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, userDetails.getId());
												ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, -renewalReminder.getRenewal_Amount());
												
												VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
												vehicleAgentTxnCheckerService.save(agentTxnChecker);
												
												ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
												ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
												
											}
											//renewalReminderSequenceService.updateNextRenewal_R_Number(renewal_R_Number + 1, userDetails.getCompany_id());
										}

									} // checking Vehicle Status
									else {
										String Link = "";
										if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
											Link += "  <a href=\"getTripsheetDetails.in?tripSheetID="
													+ CheckVehicleStatus.getTripSheetID() + "\" target=\"_blank\">TS-"
													+ driverService.getCurrentTripSheetNumber(CheckVehicleStatus.getTripSheetID(), userDetails.getCompany_id())
													+ "  <i class=\"fa fa-external-link\"></i></a>";
										} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
											Link += " <a href=\"showWorkOrder?woId="
													+ CheckVehicleStatus.getTripSheetID() + "\" target=\"_blank\">WO-"
													+ workOrdersService.getWorkOrders(CheckVehicleStatus.getTripSheetID()).getWorkorders_Number()
													+ "  <i class=\"fa fa-external-link\"></i></a> ";
										}
										TIDMandatory += " <span> This " + CheckVehicleStatus.getVehicle_registration()
										+ " Vehicle in " + CheckVehicleStatus.getVehicle_Status()
										+ " Status you can't create Renewal Reminder " + " " + Link + ""
										+ " </span> ,";

									}

								} catch (Exception e) {
									// Note: Save Renewal Reminder Expense
									e.printStackTrace();
								}
							} // Close If Condition
						} // Close For loop String

						// Note: Update Approval Amount to Select Approval Renewal
						// Reminder Amount to Update Approval Amount
						RenewalReminderService.Update_ApproavalPayment_Amount_Get_Apprival_RR(RenewalApproval_id, userDetails.getCompany_id());
						if(ownerShipObject != null) {
							vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
						}
					}
				}

				// model.put("saveVendorApproval", true);

				redir.addFlashAttribute("VMandatory", TIDMandatory);
				model.put("closeStatus", true);

				return new ModelAndView("redirect:/AddRenRemApproval.in?AID=" + RenewalApproval_id + "", model);

			} else {
				model.put("EmptyApproval", true);
				return new ModelAndView("redirect:/ShowVendorFuelPayment.in?vendorId=" + RenewalApproval_id + "", model);

			}

		
	}
	

	// save Issues in databases
	@RequestMapping(value = "/AddRenRemApproval", method = RequestMethod.GET)
	public ModelAndView AddRenewalReminderApproval(@RequestParam("AID") final Long ApprovalID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			RenewalReminderApprovalDto ShowApproval = RenewalReminderService.Get_RenewalReminderApprovalDetails(ApprovalID, userDetails.getCompany_id());

			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(0);
			Double Total = ShowApproval.getApprovalPayment_Amount();
			if (ShowApproval.getApprovalPayment_Amount() == null) {
				Total = 0.0;
			}
			model.put("approvalTotal", df.format(Total));
			// get the Approval ID to Details
			model.put("approval", RRBL.getApprovalDetails(ShowApproval));

			model.put("SelectStatus", RenewalApprovalStatus.OPEN);
			model.put("SelectPage", 1);

			List<RenewalReminderDto> pageList = RRBL.Show_Only_ApprovalListofRenewal(
					RenewalReminderService.Find_Approval_RenewalReminderList(ApprovalID, userDetails));

			model.put("renewal", pageList);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("RenewalReminderApproval_Add", model);
	}

	// show the add Renewal Reminder page and field
	@RequestMapping("/addApprovalRenRem")
	public ModelAndView addApprovalRenewalReminder(@RequestParam("AID") final Long ApprovalID,
			final HttpServletRequest request) throws Exception {
		Map<String, Object>				 model 			= new HashMap<String, Object>();
		CustomUserDetails 				userDetails 	= null;
		HashMap<String, Object>			configuration	= null;

		userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
		
		model.put("userName", userDetails.getFirstName());
		model.put("userId", userDetails.getId());
		model.put("ApprovalID", ApprovalID);
		//model.put("PaymentType", PaymentTypeConstant.getPaymentTypeList());
		model.put("SelectStatus", RenewalApprovalStatus.OPEN);
		model.put("SelectPage", 1);
		model.put("configuration", configuration);

		return new ModelAndView("add_ApprovalRenewal", model);
	}

	/* Save the Renewal Reminder in renewal page code */
	@RequestMapping(value = "/saveApprovalRenewalReminder", method = RequestMethod.POST)
	public ModelAndView saveApprovalRenewalReminder(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			@RequestParam("renewal_approvedID") final Long RenewalApproval_id, final HttpServletRequest request,
			RedirectAttributes redir) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		String TIDMandatory = "";
		long	renewal_R_number = 0;
		boolean		VEHICLESTATUS	= false; 
		RenewalReminderSequenceCounter	counter	= null;
		try {
			// get the information in jsp page f information in one driver
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			RenewalReminder renewalReminder = prepare_APPROVAL_RenewalRemider(renewalReminderDto);
			
			VehicleDto CheckVehicleStatus = vehicleService
					.Get_Vehicle_Current_Status_TripSheetID(renewalReminderDto.getVid());

			switch (CheckVehicleStatus.getvStatusId()) {
			case VehicleStatusConstant.VEHICLE_STATUS_ACTIVE:

				VEHICLESTATUS = true;
				break;
			case VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP:

				VEHICLESTATUS = true;
				break;
			case VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE:

				VEHICLESTATUS = true;
				break;
			case VehicleStatusConstant.VEHICLE_STATUS_SURRENDER:
				RenewalType	renewalType	=	renewalTypeService.getRenewalTypeByID(renewalReminderDto.getRenewalTypeId());
				if (renewalType.getRenewal_Type().equalsIgnoreCase("INSURANCE")) {
					VEHICLESTATUS = true;
				} else {

					VEHICLESTATUS = false;
				}
				break;

			default:
				VEHICLESTATUS = false;
				break;
			}
			
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			
			if(permission.contains(new SimpleGrantedAuthority("ALLOW_ALL_STATUS_RENEWAL"))) {
				VEHICLESTATUS	= true;
			}
			
			if (VEHICLESTATUS) {

				// Here Checking the Renewal Reminder Details
				// Date From and To
				// Value Details
				String Query = " RR.companyId = "+userDetails.getCompany_id()+" AND RR.markForDelete = 0 AND ( RR.vid=" + renewalReminder.getVid() + " "
						+ " AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
						+ renewalReminder.getRenewal_Subid() + "  AND '" + renewalReminder.getRenewal_from()
						+ "' between RR.renewal_from AND RR.renewal_to  OR RR.vid="
						+ renewalReminder.getVid() + " " + " AND RR.renewalTypeId ="
						+ renewalReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
						+ renewalReminder.getRenewal_Subid() + "  AND '" + renewalReminder.getRenewal_to()
						+ "' between RR.renewal_from AND RR.renewal_to ) ";
				// show Vehicle Name Renewal Type and Renewal
				// SubType List
				List<RenewalReminderDto> renewal = RRBL
						.prepareListofRenewalDto(RenewalReminderService.Validate_RenewalReminder(Query));

				if (renewal != null && !renewal.isEmpty()) {
					// Checking the Value Of Mandatory
					// Compliance
					for (RenewalReminderDto add : renewal) {
						TIDMandatory += "<span>" + add.getVehicle_registration()
								+ " Renewal Type <a href=\"showRenewalReminder.in?renewal_id=" + add.getRenewal_id()
								+ "\" target=\"_blank\">" + add.getRenewal_type() + " " + add.getRenewal_subtype()
								+ "  <i class=\"fa fa-external-link\"></i></a>  Already Available" + add.getRenewal_from()
								+ " To " + add.getRenewal_to() + " </span>, <br>";
					}
					redir.addFlashAttribute("VMandatory", TIDMandatory);
					model.put("Already", true);
				} else {
					counter	= renewalReminderSequenceService.findNextRenewal_R_Number(userDetails.getCompany_id());
					if(counter == null) {
						model.put("sequenceNotFound", true);
						return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);
					}
					renewal_R_number  = counter.getNextVal();
					renewalReminder.setRenewal_approvedID(RenewalApproval_id);
					renewalReminder.setRenewal_R_Number(renewal_R_number);
					// Note: save Renewal Reminder */
					
					RenewalReminderService.updateNewRRCreated(renewalReminder.getVid(), renewalReminder.getRenewalTypeId(), renewalReminder.getRenewal_Subid());
					
					RenewalReminderService.addRenewalReminder(renewalReminder);
					ValueObject		ownerShipObject		= null;	
					if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED ){
						ownerShipObject	= new ValueObject();
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, renewalReminder.getRenewal_id());
						ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, renewalReminder.getVid());
						ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, renewalReminder.getRenewal_Amount());
						ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
						ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_RENEWAL);
						ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, sqlFormat.parse(sqlFormat.format(renewalReminder.getRenewal_dateofpayment())));
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Renewal Entry");
						ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Renewal Number : "+renewalReminder.getRenewal_R_Number());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
						ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, userDetails.getId());
						ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, -renewalReminder.getRenewal_Amount());
						
						VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
						vehicleAgentTxnCheckerService.save(agentTxnChecker);
						
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
						
					}

					// Note: Update Approval Amount to Select Approval Renewal
					RenewalReminderService.Update_ApproavalPayment_Amount_Get_Apprival_RR(RenewalApproval_id, userDetails.getCompany_id());
				//	renewalReminderSequenceService.updateNextRenewal_R_Number(renewal_R_number + 1, userDetails.getCompany_id());
					if(ownerShipObject != null) {
						vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
					}
					model.put("Success", true);
				}
			}else {
				long tripSheetNumber = 0;
				String Link = "";
				if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
					tripSheetNumber = driverService.getCurrentTripSheetNumber(CheckVehicleStatus.getTripSheetID(),
							userDetails.getCompany_id());
					Link += "  <a href=\"getTripsheetDetails.in?tripSheetID=" + CheckVehicleStatus.getTripSheetID()
							+ "\" target=\"_blank\">TS-" + tripSheetNumber
							+ "  <i class=\"fa fa-external-link\"></i></a>";
				} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
					WorkOrders workOrders = workOrdersService.getWorkOrders(CheckVehicleStatus.getTripSheetID());
					Link += " <a href=\"showWorkOrder?woId=" + CheckVehicleStatus.getTripSheetID()
							+ "\" target=\"_blank\">WO-" + workOrders.getWorkorders_Number()
							+ "  <i class=\"fa fa-external-link\"></i></a> ";
				}
				TIDMandatory += " <span> This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
						+ CheckVehicleStatus.getVehicle_Status() + " Status you can't create Renewal Reminder " + " "
						+ Link + "" + " </span> ,";
				redir.addFlashAttribute("VMandatory", TIDMandatory);
				model.put("closeStatus", true);

				return new ModelAndView("redirect:/addApprovalRenRem.in?AID="+RenewalApproval_id, model);
			}


		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/AddRenRemApproval.in?AID=" + RenewalApproval_id + "", model);
		}

		return new ModelAndView("redirect:/AddRenRemApproval.in?AID=" + RenewalApproval_id + "", model);
	}

	/* Delete the Renewal Reminder */
	@RequestMapping("/RemoveApprovalRenRem")
	public ModelAndView deleteRenewalReminder(@RequestParam("AID") final Long RenewalApproval_id,
			@RequestParam("RRID") final Long RenewalID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// delete method
			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
			RenewalReminderService.deleteRenewalReminder(RenewalID, userDetails.getCompany_id(),userDetails.getId(),toDate);

			// Note: Update Approval Amount to Select Approval Renewal
			// Reminder Amount to Update Approval Amount
			RenewalReminderService.Update_ApproavalPayment_Amount_Get_Apprival_RR(RenewalApproval_id, userDetails.getCompany_id());

			// this message alert of show method
			model.put("deleteRenewalReminder", true);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/AddRenRemApproval.in?AID=" + RenewalApproval_id + "", model);
	}

	/* Delete the Renewal Reminder */
	@RequestMapping(value = "/UpdateApprovalRenRemAmount", method = RequestMethod.POST)
	public ModelAndView UpdateApprovalRenRemAmount(@RequestParam("APPROVAL_ID") final Long RenewalApproval_id,
			@RequestParam("RENEWAL_ID") final Long RenewalID,
			@RequestParam("RENEWAL_AMOUNT") final Double RenewalAmount, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (RenewalApproval_id != null && RenewalID != null) {
				// Update Renewal Reminder Amount
				RenewalReminderService.Update_RenewalReminder_Approval_Amount(RenewalAmount, RenewalID, userDetails.getCompany_id());
				// Note: Update Approval Amount to Select Approval Renewal
				// Reminder Amount to Update Approval Amount
				RenewalReminderService.Update_ApproavalPayment_Amount_Get_Apprival_RR(RenewalApproval_id, userDetails.getCompany_id());

				// this message alert of show method
				model.put("Update", true);
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/AddRenRemApproval.in?AID=" + RenewalApproval_id + "", model);
	}

	/* Delete the Renewal Reminder */
	@RequestMapping("/{status}/CancelRenRemApproval")
	public ModelAndView CancelApprovalRenRem(@PathVariable("status") short status,
			@RequestParam("page") Integer pageNumber, @RequestParam("AID") final Long RenewalApproval_id,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// delete method
			RenewalReminderService.delete_RenewalReminder_To_Approval_ID(RenewalApproval_id, userDetails.getCompany_id());

			// Note: Update Approval Amount to Select Approval Renewal
			// Reminder Amount to Update Approval Amount
			// delete method
			RenewalReminderService.delete_RenewalReminderApproval_ID(RenewalApproval_id, userDetails.getCompany_id());

			// this message alert of show method
			model.put("deleteRenewalReminder", true);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/RenRemApp/" + status + "/" + pageNumber + ".in", model);
	}

	@RequestMapping(value = "/UpdateApprovalRenRem", method = RequestMethod.POST)
	public ModelAndView UpdateApprovalRenewalReminderList(@ModelAttribute("command") RenewalReminderApproval Approval,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= null;
		try {

			/** who Create this Issues get email id to user profile details */
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (userDetails.getId() == Approval.getApprovalCreated_ById()) {
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
				RenewalReminderService.update_RenewalReminderApproval_ApprovedBY_andDate(
						Approval.getApprovalCreated_ById(), toDate, Approval.getRenewalApproval_id(), userDetails.getCompany_id(), RenewalApprovalStatus.APPROVED);

				model.put("saveapproval", true);
			} else {
				// Note Create email id and Approval Email not Match
				model.put("another", true);
				return new ModelAndView("redirect:/AddRenRemApproval.in?AID=" + Approval.getRenewalApproval_id() + "",
						model);
			}

			// RenewalReminderService.Update_RenewalReminder_Status_Approval_ID(Approval.getRenewalApproval_id());

		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// return new ModelAndView("show_vendor_ApprovalList", model);
		return new ModelAndView(
				"redirect:/2/ShowRenRemAppPay.in?AID=" + Approval.getRenewalApproval_id() + "&page=1", model);
	}

	@RequestMapping(value = "/{status}/ShowRenRemAppPay", method = RequestMethod.GET)
	public ModelAndView ShowRenewalReminderApprovalPayment(@PathVariable("status") short status,
			@RequestParam("page") Integer pageNumber, @RequestParam("AID") final Long ApprovalID,
			final HttpServletRequest result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			RenewalReminderApprovalDto ShowApproval = RenewalReminderService.Get_RenewalReminderApprovalDetails(ApprovalID, userDetails.getCompany_id());

			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(0);
			Double Total = ShowApproval.getApprovalPayment_Amount();
			if (ShowApproval.getApprovalPayment_Amount() == null) {
				Total = 0.0;
			}
			model.put("approvalTotal", df.format(Total));
			// get the Approval ID to Details
			model.put("approval", RRBL.getApprovalDetails(ShowApproval));

			List<RenewalReminderDto> pageList = RRBL.Show_Only_ApprovalListofRenewal(
					RenewalReminderService.Find_Approval_RenewalReminderList(ApprovalID, userDetails));

			model.put("renewal", pageList);

			model.put("SelectStatus", status);
			model.put("SelectPage", pageNumber);

		
			model.put("userName", userDetails.getFirstName());
			model.put("userId", userDetails.getId());

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("addPayment_ApprovalRenewal", model);
	}

	@RequestMapping(value = "/UpdateApprovalRenRemPayment", method = RequestMethod.POST)
	public ModelAndView UpdateApprovalRenRemPayment(@ModelAttribute("command") RenewalReminderApprovalDto Approval,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		// save approval in vendor
		CustomUserDetails		userDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			java.util.Date date = dateFormat.parse(Approval.getApprovalPayment_Date());
			Timestamp ApprovalPayment_Date = new java.sql.Timestamp(date.getTime());

			/**
			 * who Create this get email id to user profile details
			 */
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			RenewalReminderService.Update_RenewalReminder_ApprovedPayment_Details(Approval.getApprovalPending_Amount(),
					Approval.getApprovalPay_Number(), ApprovalPayment_Date,
					Approval.getApprovalPayment_ById(),  userDetails.getId(), toDate, Approval.getRenewalApproval_id(), userDetails.getCompany_id(), RenewalApprovalStatus.PAID, Approval.getPaymentTypeId());

			RenewalReminderService.Update_RenewalReminderApproval_ID_To_Payment_Status_Change(Approval.getApprovalPay_Number(),
					Approval.getApprovalPayment_ById(), ApprovalPayment_Date,
					Approval.getRenewalApproval_id(), userDetails.getCompany_id(), RenealReminderStatus.IN_PROGRESS, Approval.getPaymentTypeId());

		} catch (Exception e1) {

			e1.printStackTrace();
			return new ModelAndView(
					"redirect:/3/ShowRenRemApproval.in?AID=" + Approval.getRenewalApproval_id() + "&page=1", model);
		}

		model.put("saveapproval", true);
		// return new ModelAndView("ApprovalPayment_New", model);
		return new ModelAndView(
				"redirect:/3/ShowRenRemApproval.in?AID=" + Approval.getRenewalApproval_id() + "&page=1", model);
	}

	// save Issues in databases
	@RequestMapping(value = "/{status}/ShowRenRemApproval", method = RequestMethod.GET)
	public ModelAndView ShowRenewalReminderApproval(@PathVariable("status") short status,
			@RequestParam("page") Integer pageNumber, @RequestParam("AID") final Long ApprovalID,
			final HttpServletRequest result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			RenewalReminderApprovalDto ShowApproval = RenewalReminderService.Get_RenewalReminderApprovalDetails(ApprovalID, userDetails.getCompany_id());

			if (ShowApproval.getApprovalStatusId() == RenewalApprovalStatus.OPEN) {
				// Note : Approval Status id Open the that page
				return new ModelAndView("redirect:/AddRenRemApproval.in?AID=" + ApprovalID + "&page=1", model);
			} else if (ShowApproval.getApprovalStatusId() == RenewalApprovalStatus.APPROVED) {
				// Note : Approval Status id APPROVED the that page
				return new ModelAndView("redirect:/2/ShowRenRemAppPay.in?AID=" + ApprovalID + "&page=1", model);
			} else {
				// Note : Approval Status id PAID the show page

				DecimalFormat df = new DecimalFormat("##,##,##0");
				df.setMaximumFractionDigits(0);
				Double Total = ShowApproval.getApprovalPayment_Amount();
				if (ShowApproval.getApprovalPayment_Amount() == null) {
					Total = 0.0;
				}
				model.put("approvalTotal", df.format(Total));

				Double Approved_Amount = ShowApproval.getApprovalApproved_Amount();
				if (ShowApproval.getApprovalApproved_Amount() == null) {
					Approved_Amount = 0.0;
				}
				model.put("ApprovedAmount", df.format(Approved_Amount));

				Double Pending_Amount = ShowApproval.getApprovalPending_Amount();
				if (ShowApproval.getApprovalPending_Amount() == null) {
					Pending_Amount = 0.0;
				}
				model.put("PendingAmount", df.format(Pending_Amount));

				Double Cancel_Amount = ShowApproval.getApprovalCancel_Amount();
				if (ShowApproval.getApprovalCancel_Amount() == null) {
					Cancel_Amount = 0.0;
				}
				model.put("CancelAmount", df.format(Cancel_Amount));
				// get the Approval ID to Details
				model.put("approval", RRBL.getApprovalDetails(ShowApproval));

				List<RenewalReminderDto> pageList = RRBL.Show_Only_ApprovalListofRenewal(
						RenewalReminderService.Find_Approval_RenewalReminderList(ApprovalID, userDetails));

				model.put("renewal", pageList);
			}
			model.put("SelectStatus", status);
			model.put("SelectPage", pageNumber);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("RenewalReminderApproval_Show", model);
	}

	@RequestMapping(value = "/PrintRenRemApproval", method = RequestMethod.GET)
	public ModelAndView PrintRenewalReminderApproval(@RequestParam("AID") final Long ApprovalID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName(); // get logged in user name

			model.put("company", userProfileService.findUserProfileByUser_email(name));

			RenewalReminderApprovalDto ShowApproval = RenewalReminderService.Get_RenewalReminderApprovalDetails(ApprovalID, userDetails.getCompany_id());

			// Note : Approval Status id PAID the show page

			DecimalFormat df = new DecimalFormat("##,##,##0");
			df.setMaximumFractionDigits(0);
			Double Total = ShowApproval.getApprovalPayment_Amount();
			if (ShowApproval.getApprovalPayment_Amount() == null) {
				Total = 0.0;
			}
			model.put("approvalTotal", df.format(Total));

			df.setMaximumFractionDigits(0);
			Double Approved_Amount = ShowApproval.getApprovalApproved_Amount();
			if (ShowApproval.getApprovalApproved_Amount() == null) {
				Approved_Amount = 0.0;
			}
			model.put("ApprovedAmount", df.format(Approved_Amount));

			df.setMaximumFractionDigits(0);
			Double Pending_Amount = ShowApproval.getApprovalPending_Amount();
			if (ShowApproval.getApprovalPending_Amount() == null) {
				Pending_Amount = 0.0;
			}
			model.put("PendingAmount", df.format(Pending_Amount));
			// get the Approval ID to Details
			model.put("approval", RRBL.getApprovalDetails(ShowApproval));

			List<RenewalReminderDto> pageList = RRBL.Show_Only_ApprovalListofRenewal(
					RenewalReminderService.Find_Approval_RenewalReminderList(ApprovalID, userDetails));

			model.put("renewal", pageList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showRenRemApprovalPrint", model);
	}

	// show the add Renewal Reminder page and field
	@RequestMapping("/approvalRenRemUpload")
	public ModelAndView approvalRenRemUpload(@RequestParam("RID") final Long Renewal_ID,
			final HttpServletRequest request) throws Exception {
		CustomUserDetails				userDetails			= null;
		RenewalReminderDto				renewalReminder		= null;
		HashMap<String, Object>		 	configuration		= null;
		Map<String, Object> 			model 				= new HashMap<String, Object>();

		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			renewalReminder	= RenewalReminderService.getRenewalReminderById(Renewal_ID, userDetails);
			
			model.put("userName", userDetails.getFirstName());
			model.put(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE, (boolean)configuration.get(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE));
			// show edit page the driverRemider List
			model.put("renewalReminder",RRBL.Get_AppRoval_RenewalReminder_Upload_File(renewalReminder));
			model.put("renewalApproval",RRBL.getApprovalDetails(RenewalReminderService.Get_RenewalReminderApprovalDetails(renewalReminder.getRenewal_approvedID(), userDetails.getCompany_id())));
			model.put("configuration", configuration);
			return new ModelAndView("Upload_ApprovalRenewal", model);
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;
			renewalReminder		= null;
		}
	
	}

	@RequestMapping(value = "/UploadApprovalRenRem", method = RequestMethod.POST)
	public ModelAndView UploadApprovalRenRem(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			@RequestParam("input-file-preview") MultipartFile file, RedirectAttributes redir) {
		CustomUserDetails			userDetails			= null;
		Map<String, Object> 		model 				= new HashMap<String, Object>();
		HashMap<String, Object> 	configuration		= null;
		String 						TIDMandatory 		= "";
		RenewalReminder 			renewalReminder		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			
			// get the information in jsp page f information in one driver
			renewalReminder = RRBL.prepare_Approval_Upload_File_RenewalRemider(renewalReminderDto);
			
			if (!file.isEmpty() || (boolean)configuration.get(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE)) {

				renewalReminder.setRenewal_document(true);

				// Here Checking the Renewal Reminder Details Receipt
				// Details
				String QueryReceipt = "RR.companyId= "+userDetails.getCompany_id()+" AND RR.markForDelete = 0 AND RR.renewal_receipt='" + renewalReminder.getRenewal_receipt()
						+ "' AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
						+ renewalReminder.getRenewal_Subid() + " ";
				// Renewal Reminder Receipt
				List<RenewalReminderDto> renewalReceipt = RRBL
						.prepareListofRenewalDto(RenewalReminderService.Validate_RenewalReminder(QueryReceipt));

				if (renewalReceipt != null && !renewalReceipt.isEmpty()) {

					try {
						// Checking the Value Of Mandatory Compliance
						for (RenewalReminderDto add : renewalReceipt) {
							TIDMandatory += "<span>" + add.getVehicle_registration()
									+ " Compliance <a href=\"showRenewalReminder.in?renewal_id=" + add.getRenewal_id()
									+ "\" target=\"_blank\">" + add.getRenewal_type() + " " + add.getRenewal_subtype()
									+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
									+ add.getRenewal_from() + " To " + add.getRenewal_to()
									+ " Receipt | Challan  Number " + add.getRenewal_receipt() + " </span>, <br>";
						}
						redir.addFlashAttribute("VMandatory", TIDMandatory);

					} catch (Exception e) {
						LOGGER.error("Renewal Reminder:", e);
					}

					model.put("ReceiptAlready", TIDMandatory);
					model.put("renewalReceiptAlready", true);
					return new ModelAndView("redirect:/RenewalReminder/1/1.in", model);

				} else {
					try {
						// Note : Upload Approval To Renewal Reminder Details
						// RECEIPT Number and Values
						RenewalReminderService.Update_RenewalReminder_Upload_File(renewalReminder.getRenewal_receipt(),
								renewalReminder.isRenewal_document(), renewalReminder.getRenewal_authorization(),
								renewalReminder.getRenewal_number(), renewalReminder.getRenewal_staus_id(),
								renewalReminder.getRenewal_id(), userDetails.getCompany_id());

						org.fleetopgroup.persistence.document.RenewalReminderDocument rewalDoc = new org.fleetopgroup.persistence.document.RenewalReminderDocument();
						if (!file.isEmpty()) {
							rewalDoc.setRenewal_id(renewalReminder.getRenewal_id());
							rewalDoc.setRenewal_R_Number(renewalReminder.getRenewal_R_Number());
							try {

								byte[] bytes = file.getBytes();
								rewalDoc.setRenewal_filename(file.getOriginalFilename());
								rewalDoc.setRenewal_content(bytes);
								rewalDoc.setRenewal_contentType(file.getContentType());

								// when Add Renewal Reminder to file Renewal
								// Reminder History id is null
								rewalDoc.setRenewalHis_id((long) 0);

								/** Set Status in Issues */
								rewalDoc.setMarkForDelete(false);

							
								/** Set Created by email Id */
								rewalDoc.setCreatedById(userDetails.getId());
								rewalDoc.setLastModifiedById(userDetails.getId());

								java.util.Date currentDateUpdate = new java.util.Date();
								Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

								/** Set Created Current Date */
								rewalDoc.setCreated(toDate);
								rewalDoc.setLastupdated(toDate);
								rewalDoc.setCompanyId(userDetails.getCompany_id());
							} catch (IOException e) {
								e.printStackTrace();
							}

							// Note: Save RenewalReminder To Document
							//RenewalReminderService.add_RenewalReminder_Document(rewalDoc);
							documentService.save(rewalDoc);
							// Note: This Update is Document ID Update Renewal
							// Reminder Details
							RenewalReminderService.Update_RenewalReminderDocument_ID_to_RenewalReminder(
									rewalDoc.get_id(), true, renewalReminder.getRenewal_id(), userDetails.getCompany_id());
						}
						

						model.put("saveRenewalReminder", true);
						return new ModelAndView(
								"redirect:/1/1/showRenewalReminder.in?renewal_id=" + renewalReminder.getRenewal_id() + "",
								model);

					} catch (Exception e) {

						return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
					}
				}

			} else {
				// messages
				return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/RenewalReminder/1/1.in?danger=true");
		}
	}

	/* Save the Renewal Reminder in renewal page code */
	@RequestMapping(value = "/{status}/uploadRenRemAppDocument", method = RequestMethod.POST)
	public ModelAndView saveRenewalReminder(@PathVariable("status") short status, @RequestParam("renewalApproval_id") Long renewalApproval_id, 
			@RequestParam("input-file-preview") MultipartFile file, RedirectAttributes redir) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {

			if (!file.isEmpty() && renewalApproval_id != null) {

				try {
					CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					org.fleetopgroup.persistence.document.RenewalReminderAppDocument rewalDoc = new org.fleetopgroup.persistence.document.RenewalReminderAppDocument();

					rewalDoc.setRenewalApproval_id(renewalApproval_id);
					try {

						byte[] bytes = file.getBytes();
						rewalDoc.setRenewal_filename(file.getOriginalFilename());
						rewalDoc.setRenewal_content(bytes);
						rewalDoc.setRenewal_contentType(file.getContentType());

						// when Add Renewal Reminder to file Renewal

						/** Set Status in Issues */
						rewalDoc.setMarkForDelete(false);

						
						/** Set Created by email Id */
						rewalDoc.setCreatedById(userDetails.getId());
						rewalDoc.setLastModifiedById(userDetails.getId());

						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

						/** Set Created Current Date */
						rewalDoc.setCreated(toDate);
						rewalDoc.setLastupdated(toDate);
					} catch (IOException e) {
						e.printStackTrace();
					}

					// Note: Save RenewalReminder To Document
					rewalDoc.setCompanyId(userDetails.getCompany_id());
					
					RenewalReminderService.add_RenewalReminderApproval_Document(rewalDoc);

					// Note: This Update is Document ID Update Renewal
					// Reminder Details
					RenewalReminderService.Update_RenewalReminderApprovalDocument_ID_to_RenewalReminderApproval(
							rewalDoc.get_id(), true, renewalApproval_id, userDetails.getCompany_id());

					model.put("upload", true);

				} catch (Exception e) {
					return new ModelAndView(
							"redirect:/3/ShowRenRemApproval.in?AID=" + renewalApproval_id + "&page=1&danger=true",
							model);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(
					"redirect:/3/ShowRenRemApproval.in?AID=" + renewalApproval_id + "&page=1&danger=true", model);
		}

		return new ModelAndView("redirect:/3/ShowRenRemApproval.in?AID=" + renewalApproval_id + "&page=1", model);
	}

	/* Should be Download Driver Reminder document */
	@RequestMapping("/download/RenRemAppDocument/{rendoc_id}")
	public String downloadReminder(@PathVariable("rendoc_id") Long renewal_id, HttpServletResponse response) {

		try {
			if (renewal_id == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				org.fleetopgroup.persistence.document.RenewalReminderAppDocument file = RenewalReminderService
						.Get_RenewalReminderApproval_Document(renewal_id);

				if (file != null) {
					if (file.getRenewal_content() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getRenewal_filename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getRenewal_contentType());
						response.getOutputStream().write(file.getRenewal_content());
						out.flush();
						out.close();

					}
				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {

		}
		return null;
	}

	/* Delete the Renewal Reminder */
	@RequestMapping("/UpdateDDnumberRenRem")
	public ModelAndView UpdateDDnumberRenRem(@RequestParam("AID_CANCEL") final Long RenewalApproval_id,
			RenewalReminderDto Reminder, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails			userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (RenewalApproval_id != null) {
			
				try {
					java.util.Date date = dateFormat.parse(Reminder.getRenewal_dateofpayment());
					java.sql.Date Renewal_dateofpayment = new Date(date.getTime());

					// Update Renewal Reminder DD Number in payment type and
					// payment
					// number paid by also value
					RenewalReminderService.update_RenewalReminder_DD_Number(
							Reminder.getRenewal_PayNumber(), userDetails.getId(), Renewal_dateofpayment,
							Reminder.getRenewal_id(), userDetails.getCompany_id(), Reminder.getPaymentTypeId());

				} catch (Exception e) {
					return new ModelAndView("redirect:/NotFound.in");
				}
				// this message alert of show method
				model.put("updateDDNo", true);
			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/3/ShowRenRemApproval.in?AID=" + RenewalApproval_id + "&page=1", model);
	}

	/* Delete the Renewal Reminder */
	@RequestMapping("/UpdateCancelRenRem")
	public ModelAndView UpdateCancelRenRem(@RequestParam("CANCEL_APPROVAL_ID") final Long CANCEL_APPROVAL_ID,
			@RequestParam("CANCEL_RENEWAL_ID") final Long CANCEL_RENEWAL_ID,
			@RequestParam("CANCEL_COMMENT") final String CANCEL_COMMENT, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		try {
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// System.out.println("CANCEL_APPROVAL_ID" + CANCEL_APPROVAL_ID);
			if (CANCEL_APPROVAL_ID != null) {

				RenewalReminderDto OldRenewal = RenewalReminderService.getRenewalReminderById(CANCEL_RENEWAL_ID, userDetails);
				
				try {

					// Note: here Old Renewal Reminder to Get Renewal_to add One
					// to next
					// Date
					// Ex: 16-08-2017 for Old Renewal_to Add one date 17-08-2017
					// this Date is new Approval Renewal_from date
					java.util.Date date = RRBL.Change_CurrentDate_To_Add_NumberofDate_InReal_Calendar(
							dateFormat.format(OldRenewal.getRenewal_from_Date()), -2);

					Calendar	calendar	= new GregorianCalendar();
					calendar.setTime(date);
					calendar.add(Calendar.HOUR, 23);
					calendar.add(Calendar.MINUTE, 59);
					calendar.add(Calendar.SECOND, 59);
					


					// Note: Create Cancel Comment also New Comment

					String CancelComment = "This vehicle " + OldRenewal.getVehicle_registration() + " Validaity "
							+ OldRenewal.getRenewal_from_Date() + " to " + OldRenewal.getRenewal_To_Date() + " Canceled by "
							+ userDetails.getFirstName() + " Reason: " + CANCEL_COMMENT + "";

					if (OldRenewal != null) {

						RenewalReminderService.update_Cancel_ApprovalID_to_Status_changeDate_From_To(RenealReminderStatus.CANCELLED,
								userDetails.getId(), CancelComment, date, calendar.getTime(), OldRenewal.getRenewal_id(), userDetails.getCompany_id());

						if (OldRenewal.getRenewal_approvedID() != 0) {

							// This Approval Id to Add Cancel Amount
							RenewalReminderService.Add_Approval_Cancel_Amount_Value(OldRenewal.getRenewal_Amount(),
									OldRenewal.getRenewal_approvedID(), userDetails.getCompany_id());
						}
					}

				} catch (Exception e) {
					return new ModelAndView("redirect:/NotFound.in");
				}
				// this message alert of show method
				model.put("updateDDNo", true);
			} else {
				return new ModelAndView("redirect:/NotFound.in");
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/3/ShowRenRemApproval.in?AID=" + CANCEL_APPROVAL_ID + "&page=1", model);
	}

	// get logic in Renewal Reminder Information
	public RenewalReminder prepare_APPROVAL_RenewalRemider(RenewalReminderDto renewalReminderDto) throws ParseException {

		RenewalReminder renewal = new RenewalReminder();

		renewal.setRenewal_id(renewalReminderDto.getRenewal_id());
		renewal.setVid(renewalReminderDto.getVid());

		String[] From_TO_Array = null;
		try {
			/*Vehicle vehiclename = vehicleService
					.getVehicle_Details_Renewal_Reminder_Entries(renewalReminderDto.getVid());
			if (vehiclename != null) {
				// show Vehicle name
				renewal.setVehicle_registration(vehiclename.getVehicle_registration());
			}*/
			String From_TO = renewalReminderDto.getRenewal_from();
			// System.out.println(renewalReminderDto.getRenewal_from());
			From_TO_Array = From_TO.split("  to  ");
			// System.out.println(From_TO_Array[0]);
			// System.out.println(From_TO_Array[1]);

			java.util.Date date = dateFormat.parse(From_TO_Array[0]);
			java.sql.Date fromDate = new Date(date.getTime());
			// System.out.println(fromDate);
			renewal.setRenewal_from(fromDate);
			
			renewal.setRenewal_to(sqlFormatTime.parse(From_TO_Array[1] + DateTimeUtility.DAY_END_TIME));

			renewal.setRenewal_dateofpayment(fromDate);

		} catch (Exception e) {
			LOGGER.error("Renewal Reminder:", e);
		}
		//renewal.setRenewal_type(renewalReminderDto.getRenewal_type());
		//renewal.setRenewal_subtype(renewalReminderDto.getRenewal_subtype());

		renewal.setRenewal_receipt(null);
		renewal.setRenewal_Amount(renewalReminderDto.getRenewal_Amount());

		//renewal.setRenewal_paymentType(null);
		renewal.setRenewal_PayNumber(null);
		renewal.setRenewal_authorization(renewalReminderDto.getRenewal_authorization());
		renewal.setRenewal_number(null);
		renewal.setRenewal_paidbyId(null);
		renewal.setRenewal_timethreshold(renewalReminderDto.getRenewal_timethreshold());
		renewal.setRenewal_periedthreshold(renewalReminderDto.getRenewal_periedthreshold());
		/* due end date */
		// get the date from database is 10/12/2015
		String duedate = From_TO_Array[1];
		// that date split the date & month & year date[0]=10,
		// date[1]=12,date[2]=2015
		/* due timethreshold and peried of reminder */
		Integer duetimeandperied;
		// calculation of the reminder before date of reminder
		Integer timeandperied = (renewalReminderDto.getRenewal_timethreshold())
				* (renewalReminderDto.getRenewal_periedthreshold());

		// timeandperied=3*0 days; or timeandperied=3*7 weeks or
		// timeandperied=3*28 month

		if (timeandperied == 0) {
			duetimeandperied = renewalReminderDto.getRenewal_timethreshold(); // 3
																				// days
		} else {
			duetimeandperied = timeandperied; // 21 days
		}

		String reminder_dateof = Change_CurrentDate_To_RenewalDate_SubTrackDate(duedate, duetimeandperied);
		// save renewal date
		renewal.setDateofRenewal(dateFormat.parse(reminder_dateof));

		/** Set Status in Issues */
		renewal.setMarkForDelete(false);

		/** who Create this Issues get email id to user profile details */
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		/** Set Created by email Id */
		renewal.setCreatedById(userDetails.getId());
		renewal.setLastModifiedById(userDetails.getId());
		renewal.setCompanyId(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		/** Set Created Current Date */
		renewal.setCreated(toDate);
		renewal.setLastupdated(toDate);

		// Note: Document ID is Now Zero
		renewal.setRenewal_document_id((long) 0);
		renewal.setRenewal_document(false);
		renewal.setRenewalTypeId(renewalReminderDto.getRenewalTypeId());
		renewal.setRenewal_Subid(renewalReminderDto.getRenewal_Subid());
		renewal.setRenewal_staus_id(RenealReminderStatus.OPEN);
		/** Set Status Not Approved Date */
		//renewal.setRenewal_status(RenealReminderStatus.OPEN_NAME);


		return renewal;
	}

	public String Change_CurrentDate_To_RenewalDate_SubTrackDate(String duedate, Integer duetimeandperied) {

		// get the date from database is 10-12-2015
		// that date split the date & month & year date[0]=10,
		// date[1]=12,date[2]=2015
		String due_EndDate[] = duedate.split("-");

		// convert string to integer in date
		Integer due_enddate = Integer.parseInt(due_EndDate[0]);

		// convert string to integer in month and one more remove 0 is month why
		// means calendar format is [0-11] only so i am subtract to -1 method
		Integer due_endmonth = (Integer.parseInt(due_EndDate[1].replaceFirst("^0+(?!$)", "")) - 1);

		// convert string to integer in year
		Integer due_endyear = Integer.parseInt(due_EndDate[2]);

		// create new calendar at specific date. Convert to java.util calendar
		// type
		Calendar due_endcalender = new GregorianCalendar(due_endyear, due_endmonth, due_enddate);

		// print date for default value
		// System.out.println("Past calendar : " + due_endcalender.getTime());

		// subtract 2 days from the calendar and the due_time and peried of
		// throsold integer
		due_endcalender.add(Calendar.DATE, -duetimeandperied);
		// System.out.println(duetimeandperied+"days ago: " +
		// due_endcalender.getTime());

		// this format this the [0-11] but real time month format this [1-12]
		Integer month = due_endcalender.get(Calendar.MONTH) + 1;
		// i am change the format to story the database in reminder date
		String reminder_dateof = "" + due_endcalender.get(Calendar.DATE) + "-" + month + "-"
				+ due_endcalender.get(Calendar.YEAR);

		return reminder_dateof;
	}

}