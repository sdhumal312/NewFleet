/**
 * 
 */
package org.fleetopgroup.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.persistence.bl.InventoryTyreInvoiceBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.InventoryTyreHistoryDto;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadAmountDto;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadDto;
import org.fleetopgroup.persistence.model.InventoryTyre;
import org.fleetopgroup.persistence.model.InventoryTyreHistory;
import org.fleetopgroup.persistence.model.InventoryTyreInvoice;
import org.fleetopgroup.persistence.model.InventoryTyreLifeHistory;
import org.fleetopgroup.persistence.model.InventoryTyreRetread;
import org.fleetopgroup.persistence.model.InventoryTyreRetreadAmount;
import org.fleetopgroup.persistence.model.InventoryTyreRetreadDocument;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.ITyreExpenseDetailsService;
import org.fleetopgroup.web.error.MailSentExistException;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

/**
 * @author fleetop
 *
 */
@Controller
public class InventoryTyreRetreadController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	
	VendorBL VenBL = new VendorBL();

	@Autowired
	private IInventoryTyreService 			iventoryTyreService;
	
	@Autowired
	private ITyreExpenseDetailsService 			tyreExpenseDetailsService;

	@Autowired
	private IPartLocationsService 			PartLocationsService;

	PartLocationsBL PLBL = new PartLocationsBL();
	InventoryTyreInvoiceBL ITBL = new InventoryTyreInvoiceBL();

	SimpleDateFormat SQLdateFormat 	= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	/** This controller request in open Tyre list */
	@RequestMapping(value = "/TyreRetreadNew/{pageNumber}", method = RequestMethod.GET)
	public String getTyreRetreadNew(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails				userDetails		= null;
		Page<InventoryTyreRetread> 		page			= null;
		List<InventoryTyreRetreadDto> 	pageList		= null;
		List<InventoryTyreRetreadDto> 	pageListBL		= null;
		
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			page 			= iventoryTyreService.getDeploymentInventoryTyreRetreadLog(pageNumber, userDetails.getCompany_id());
			int current 	= page.getNumber() + 1;
			int begin 		= Math.max(1, current - 5);
			int end 		= Math.min(begin + 10, page.getTotalPages());

			
			pageList 		= iventoryTyreService.find_List_InventoryTyreRetread(pageNumber, userDetails.getCompany_id());
			pageListBL 		= ITBL.prepareListof_InventoryTyreRetreadDto(pageList);
			
			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("TyreQuentity", page.getTotalElements());
			model.addAttribute("PartLocations", PLBL.prepareListofBean(PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
			model.addAttribute("location", "TYRE");
			model.addAttribute("TyreRetread", pageListBL);
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			LOGGER.error("Tyre Page:", e);
		}
		return "New_TyreRetread";
	}

	/** This controller request in Add Tyre Inventory list */
	@RequestMapping(value = "/RetreadFilter", method = RequestMethod.GET)
	public ModelAndView RetreadFilter(Model modelAdd, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Tyre_RetreadFilter", model);
	}

	/** This controller request in Filter Tyre Inventory list */
	@RequestMapping(value = "/SearchRetreadFilter", method = RequestMethod.POST)
	public ModelAndView SearchRetreadFilter(@RequestParam("TYRE_MUITPLE") final String TYRE_ID,
			@RequestParam("TYRE_USAGE") final String TYRE_USAGE, @ModelAttribute("command") InventoryTyre Tyre,
			InventoryTyreInvoice TyreInvoice, BindingResult result) {

		// PurchaseOrders POReport = RBL.preparePurchaseOrder(purchaseOrders);

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (TYRE_USAGE != "" && TYRE_USAGE != null) {
				String TYREID_Mutiple = "", MANUFACTURER = "", MODEL = "", SIZE = "", location = "", usage = "";

				if (TYRE_ID != "") {
					TYREID_Mutiple = "AND R.TYRE_ID IN (" + TYRE_ID + ")";
				}
				if (Tyre.getTYRE_MANUFACTURER_ID() != null && Tyre.getTYRE_MANUFACTURER_ID() > 0) {
					MANUFACTURER = "AND R.TYRE_MANUFACTURER_ID =" + Tyre.getTYRE_MANUFACTURER_ID() + "";
				}
				if (Tyre.getTYRE_MODEL_ID() != null && Tyre.getTYRE_MODEL_ID() > 0) {
					MODEL = "AND R.TYRE_MODEL_ID =" + Tyre.getTYRE_MODEL_ID() + "";
				}
				if (Tyre.getTYRE_SIZE_ID() != null && Tyre.getTYRE_SIZE_ID() > 0) {
					SIZE = "AND R.TYRE_SIZE_ID =" + Tyre.getTYRE_SIZE_ID() + "";
				}
				if (Tyre.getWAREHOUSE_LOCATION_ID() != null && Tyre.getWAREHOUSE_LOCATION_ID() > 0) {
					location = "AND R.WAREHOUSE_LOCATION_ID =" + Tyre.getWAREHOUSE_LOCATION_ID() + "";
				}
				if (TYRE_USAGE != "" && TYRE_USAGE != null) {
					String[] usagerFromTo = TYRE_USAGE.split(",");
					usage = "AND R.TYRE_USEAGE BETWEEN " + usagerFromTo[0] + " AND " + usagerFromTo[1] + "";
				}

				String Query = TYREID_Mutiple + " " + MANUFACTURER + " " + MODEL + " " + SIZE + " " + location + " "
						+ usage;
				List<InventoryTyreDto> pageList = iventoryTyreService.Filter_Retread_Tyre_Inventory(Query, userDetails.getCompany_id());

				model.put("Tyre", pageList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("Report_RetreadFilter", model);

	}

	/** This Controller save the saveTyreRetreadInfo Method */
	@RequestMapping(value = "/saveTyreRetreadInfo", method = RequestMethod.POST)
	public ModelAndView saveTyreRetreadInfo(@ModelAttribute("command") InventoryTyreRetreadDto TyreRetreadDto,
			@RequestParam("TyreID") final String[] TyreSerialID, Model modelAdd, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		Long TyreRetreadID = null;
		try {
			// getting Fuel ID in Multiple Format
			String Multiple_TYRE_id = "";
			if (TyreSerialID != null) {
				int i = 1;
				InventoryTyreRetread TyreRetread = prepare_Retread_Tyre(TyreRetreadDto);

				TyreRetread.setTR_SEND_TYRE(TyreSerialID.length);

				iventoryTyreService.add_Inventory_Retread_Tyre(TyreRetread);
				TyreRetreadID = TyreRetread.getTRID();
				
				// save to select Tyre to Retread process
				for (String TyreIDSelect : TyreSerialID) {

					if (TyreIDSelect != null) {

						// Get Tyre Details
						InventoryTyreDto Tyre = iventoryTyreService
								.Get_Only_TYRE_ID_And_TyreSize(Long.parseLong(TyreIDSelect), TyreRetread.getCOMPANY_ID());

						InventoryTyreRetreadAmount TyreRetreadAmount = prepare_RetreadAmount_Tyre(Tyre);

						TyreRetreadAmount.setInventoryTyreRetread(TyreRetread);

						// Create Sent Retread Details TyreRetread Entries
						iventoryTyreService.add_Inventory_Retread_Tyre_Amount(TyreRetreadAmount);

						// Create Tyre History in Sent Retread history
						InventoryTyreHistory TyreHistory = prepare_InventoryTyreHistory_SENT_RETREAD(Tyre);
						TyreHistory.setCOMPANY_ID(TyreRetread.getCOMPANY_ID());
						iventoryTyreService.add_Inventory_TYRE_History(TyreHistory);

						if (i != TyreSerialID.length) {
							Multiple_TYRE_id += TyreIDSelect + ",";
						} else {
							Multiple_TYRE_id += TyreIDSelect + "";
						}
						i++;

					}

				} // close For loop to save Retread Amount

				iventoryTyreService.Update_Inventory_Tyre_RetreadingStatus(TyreRetread.getTRID(), InventoryTyreDto.TYRE_ASSIGN_STATUS_SENT_RETREAD,
						Multiple_TYRE_id, TyreRetread.getCOMPANY_ID());
				/*
				 * modelAdd.addAttribute("successTyre", "" + countSAVE);
				 * modelAdd.addAttribute("DuplicateTyre", "" + countDuplicate);
				 * 
				 */
				model.put("saveTyre", true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreInventory/1.in?danger=true");
		}finally {
		}

		return new ModelAndView("redirect:/ShowRetreadTyre?RID=" + TyreRetreadID + "", model);
	}

	// Show WorkOrderParts Details
	@RequestMapping(value = "/ShowRetreadTyre", method = RequestMethod.GET)
	public ModelAndView ShowRetreadTyre(@RequestParam("RID") final Long TyreRetreadID, final HttpServletRequest request) throws Exception {
		Map<String, Object> 					model 			= new HashMap<String, Object>();
		List<InventoryTyreRetreadAmountDto> 	Dtos 			= null;
		CustomUserDetails						userDetails		= null;
		InventoryTyreRetreadDto 				TyreRetread		= null;
		
		try {
			if (TyreRetreadID != null) {
				
				userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				TyreRetread 		= iventoryTyreService.Get_InventoryTyreRetread(TyreRetreadID, userDetails.getCompany_id());
				
				model.put("Retread", TyreRetread);
				
				if (TyreRetread != null) {
					Dtos = ITBL.getInventoryTyreRetreadAmountList(iventoryTyreService.Get_InventoryTyreRetread_Amount(TyreRetreadID, userDetails.getCompany_id()));
				}
				
				model.put("RetreadAmount", Dtos);
				if (TyreRetread.getTR_STATUS_ID() == InventoryTyreRetreadDto.INVENTORY_TYRE_TR_STATUS_SENT_RETREAD) {

					return new ModelAndView("Show_RetreadTyre_SENT", model);
				}

				if (TyreRetread.getTR_STATUS_ID() == InventoryTyreRetreadDto.INVENTORY_TYRE_TR_STATUS_COMPLETED) {

					return new ModelAndView("Show_RetreadTyre_COMPLETED", model);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return new ModelAndView("Show_RetreadTyre_OPEN", model);
	}

	/** This Controller To Save Tyre Cost and Details */
	@RequestMapping(value = "/UpdateRetreadReceived", method = RequestMethod.POST)
	public ModelAndView UpdateRetreadReceived(@RequestParam("RID") final Long TyreRetreadID,
			@ModelAttribute("command") InventoryTyreRetreadAmount TyreRetreadAmount, final HttpServletRequest request)
			throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// save workOrder Task to Parts value
			InventoryTyreRetreadAmount TyreRetread = prepare_UPdate_TyreRetreadAmount(TyreRetreadAmount);
			TyreRetread.setCOMPANY_ID(userDetails.getCompany_id());
			iventoryTyreService.update_Inventory_Retread_Amount_Tyre_cost(TyreRetread);

			iventoryTyreService.update_Inventory_Retread_Tyre_cost(TyreRetread.getRETREAD_AMOUNT(), 1, TyreRetreadID, userDetails.getCompany_id());

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreRetreadNew/1.in?danger=true");
		}

		return new ModelAndView("redirect:/ShowRetreadTyre?RID=" + TyreRetreadID + "", model);
	}

	/** This Controller To EditRetreadReceived Tyre Cost and Details */
	@RequestMapping(value = "/EditRetreadReceived", method = RequestMethod.POST)
	public ModelAndView EditRetreadReceived(@RequestParam("TRID") final Long TyreRetreadID,
			@RequestParam("OLD_AMOUNT") final Double OLD_AMOUNT_TOTAL,
			@ModelAttribute("command") InventoryTyreRetreadAmount TyreRetreadAmount, final HttpServletRequest request)
			throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (TyreRetreadID != null && OLD_AMOUNT_TOTAL != null) {

				// Update Edit Value
				InventoryTyreRetreadAmount TyreRetread = prepare_UPdate_TyreRetreadAmount(TyreRetreadAmount);
				TyreRetread.setCOMPANY_ID(userDetails.getCompany_id());
				iventoryTyreService.update_Inventory_Retread_Amount_Tyre_cost(TyreRetread);
				
				InventoryTyreRetreadAmount inventoryTyreRetreadAmount = iventoryTyreService.getRetreadTyreAmountDetailsByTR_AMOUNT_ID(TyreRetread.getTR_AMOUNT_ID());
				
				tyreExpenseDetailsService.updateRetreadTyreExpenseDetails(inventoryTyreRetreadAmount);

				if (OLD_AMOUNT_TOTAL != 0.0) {
					// edit OlD TyreRetread Amount Subtract Details
					iventoryTyreService.update_Edit_Inventory_Retread_Tyre_cost_Subtract(OLD_AMOUNT_TOTAL,
							TyreRetreadID, userDetails.getCompany_id());
				}
				// edit Amount Update TyreRetread Details
				iventoryTyreService.update_Edit_Inventory_Retread_Tyre_cost_ADD(TyreRetread.getRETREAD_AMOUNT(),
						TyreRetreadID, userDetails.getCompany_id());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreRetreadNew/1.in?danger=true");
		}

		return new ModelAndView("redirect:/ShowRetreadTyre?RID=" + TyreRetreadID + "", model);
	}

	/** This Controller To Save Tyre Cost and Details */
	@RequestMapping(value = "/DeleteRetreadTyre", method = RequestMethod.GET)
	public ModelAndView DeleteRetreadTyre(@RequestParam("RID") final Long TyreRetreadID,
			@RequestParam("RAID") final Long TyreRetreadAmountID, final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// Search Tyre_id And TYRE RETREAD Cost
			InventoryTyreRetreadAmount TyreRetread = iventoryTyreService
					.Get_TYRE_ID_AND_TOTAL_AMOUNT_InventoryTyreRetreadAmount(TyreRetreadAmountID, userDetails.getCompany_id());
			if (TyreRetread != null) {
				// subtrack Deleted Retread Value Amount in TyreRetread Details
				iventoryTyreService.SUBTRACK_Inventory_Retread_Amount_Tyre_cost_DELETE(TyreRetread.getRETREAD_AMOUNT(),
						1, TyreRetreadID, userDetails.getCompany_id());
				// Deleted Retread Value Tyre_ID To Update Tyre Details
				iventoryTyreService.Update_Inventory_Tyre_AVALABLE_Status(InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE, TyreRetread.getTYRE_ID(), userDetails.getCompany_id());

				// Deleted TyreRetreadAmount Details
				iventoryTyreService.delete_Inventory_Retread_Amount_Tyre_cost(TyreRetreadAmountID, userDetails.getCompany_id());
				model.put("DeleteSuccess", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreRetreadNew/1.in?danger=true");
		}

		return new ModelAndView("redirect:/ShowRetreadTyre?RID=" + TyreRetreadID + "", model);
	}

	/** This Controller To Save Tyre Cost and Details */
	@RequestMapping(value = "/DeleteRetread", method = RequestMethod.GET)
	public ModelAndView DeleteRetread(@RequestParam("RID") final Long TyreRetreadID, final HttpServletRequest request)
			throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {

			if (TyreRetreadID != null) {

				List<InventoryTyreRetreadAmountDto> TyreRetread = iventoryTyreService
						.Get_InventoryTyreRetread_Amount(TyreRetreadID, userDetails.getCompany_id());
				if (TyreRetread != null && !TyreRetread.isEmpty()) {
					model.put("DeleteRetreadAmount", true);
					return new ModelAndView("redirect:/ShowRetreadTyre?RID=" + TyreRetreadID + "", model);
				} else {
					iventoryTyreService.delete_Tyre_InventoryRetread(TyreRetreadID, userDetails.getCompany_id());
					model.put("DeleteSuccess", true);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreRetreadNew/1.in?danger=true");
		}

		return new ModelAndView("redirect:/TyreRetreadNew/1.in", model);
	}

	/** This Controller To Save Tyre Cost and Details */
	@RequestMapping(value = "/SentRetread", method = RequestMethod.POST)
	public ModelAndView SentRetread(@RequestParam("RID") final Long TyreRetreadID,
			@RequestParam("DESCRIPTION") final String Descrition, final HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (TyreRetreadID != null) {

				

				Date currentDateUpdate = new Date();
				Timestamp Lastupdated = new java.sql.Timestamp(currentDateUpdate.getTime());

				iventoryTyreService.Update_Inventory_ReTread_Status_and_Description(InventoryTyreRetreadDto.INVENTORY_TYRE_TR_STATUS_SENT_RETREAD, Descrition,
						userDetails.getId(), Lastupdated, TyreRetreadID, userDetails.getCompany_id());
				model.put("UpdateSuccess", true);

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreRetreadNew/1.in?danger=true");
		}

		return new ModelAndView("redirect:/ShowRetreadTyre?RID=" + TyreRetreadID + "", model);
	}

	/** The Value code id Flagged save true box Show */
	@RequestMapping(value = "/updateTyreReceived", method = RequestMethod.POST)
	@ResponseBody
	public String updateTyreReceived(@RequestParam("TRAID") final Long TyreRetreadAmountID,
			@RequestParam("status") final short flagged,  final HttpServletRequest request) {
		
		LOGGER.error("Registering mailbox with information: {}", TyreRetreadAmountID);
		CustomUserDetails			userDetails					= null;
		InventoryTyreRetreadAmount 	inventoryTyreRetreadAmount 	= null; 
		InventoryTyre 				inventoryTyre 				= null;
		InventoryTyreLifeHistory 	tyreLifeHistory 			= null;
		InventoryTyreHistory 		tyreHistory 				= null;
		int 						retreadCount 				= 0;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			switch (flagged) {
			case InventoryTyreRetreadDto.INVENTORY_TYRE_TR_STATUS_RECEIVE:
				inventoryTyreRetreadAmount = iventoryTyreService.Get_TYRE_ID_AND_TOTAL_AMOUNT_InventoryTyreRetreadAmount(TyreRetreadAmountID, userDetails.getCompany_id());
			
				if (inventoryTyreRetreadAmount != null) {
					iventoryTyreService.Update_RECEVIED_RetreadAmount_Status(InventoryTyreRetreadAmountDto.TRA_STATUS_RECEIVED, TyreRetreadAmountID, userDetails.getCompany_id());
					inventoryTyre = iventoryTyreService.GET_RECEVIED_Retread_TYRE_Amount(inventoryTyreRetreadAmount.getTYRE_ID(), userDetails.getCompany_id());

					if (inventoryTyre != null) {
						tyreLifeHistory = prepare_SAVE_InventoryTyreLifeHistory(inventoryTyre);
						tyreLifeHistory.setCOMPANY_ID(userDetails.getCompany_id());
						iventoryTyreService.add_Tyre_Inventory_TyreLifeHistory(tyreLifeHistory);
					}
					
					if(inventoryTyre != null) {
						retreadCount = inventoryTyre.getTYRE_RETREAD_COUNT() + 1;
					}
					
					iventoryTyreService.Update_REtread_Amount_Usage_RetreadPeriod_Status_InventoryTYRE(0, inventoryTyreRetreadAmount.getRETREAD_AMOUNT(), retreadCount, InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE, inventoryTyreRetreadAmount.getTYRE_ID(), userDetails.getCompany_id());

					tyreHistory = prepare_InventoryTyreHistory_RECEIVE(inventoryTyre);
					tyreHistory.setCOMPANY_ID(userDetails.getCompany_id());
					iventoryTyreService.add_Inventory_TYRE_History(tyreHistory);
				}

				break;
			case InventoryTyreRetreadDto.INVENTORY_TYRE_TR_STATUS_REJECT:
				inventoryTyreRetreadAmount = iventoryTyreService.Get_TYRE_ID_AND_TOTAL_AMOUNT_InventoryTyreRetreadAmount(TyreRetreadAmountID, userDetails.getCompany_id());
				if (inventoryTyreRetreadAmount != null) {
					iventoryTyreService.Update_RECEVIED_RetreadAmount_Status(InventoryTyreRetreadAmountDto.TRA_STATUS_REJECT, TyreRetreadAmountID, userDetails.getCompany_id());

					iventoryTyreService.Update_Inventory_Tyre_AVALABLE_Status(InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE, inventoryTyreRetreadAmount.getTYRE_ID(), userDetails.getCompany_id());

					tyreHistory = prepare_InventoryTyreHistory_REJECT(inventoryTyreRetreadAmount);
					tyreHistory.setCOMPANY_ID(userDetails.getCompany_id());
					iventoryTyreService.add_Inventory_TYRE_History(tyreHistory);
				}
				break;
				
			default :
				System.err.println("default");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new MailSentExistException();
		}finally {
			userDetails = null;
		}

		return new String("success");
	}

	
	/** This controller request in Add Tyre Inventory list */
	@RequestMapping(value = "/ScrapFilter", method = RequestMethod.GET)
	public ModelAndView ScrapFilter(Model modelAdd, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Tyre_ScrapFilter", model);
	}

	/** This controller request in Filter Tyre Inventory list */
	@RequestMapping(value = "/SearchScrapFilter", method = RequestMethod.POST)
	public ModelAndView SearchScrapFilter(@RequestParam("TYRE_MUITPLE") final String TYRE_ID,
			@RequestParam("TYRE_USAGE") final String TYRE_USAGE,
			@RequestParam("WAREHOUSE_LOCATION_ID") final Integer WAREHOUSE_LOCATION, InventoryTyreInvoice TyreInvoice,
			BindingResult result) {

		// PurchaseOrders POReport = RBL.preparePurchaseOrder(purchaseOrders);

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (TYRE_USAGE != "" && TYRE_USAGE != null) {
				String TYREID_Mutiple = "", location = "", usage = "";
				if (TYRE_ID != "") {
					TYREID_Mutiple = "AND R.TYRE_ID IN (" + TYRE_ID + ")";
				}

				if (WAREHOUSE_LOCATION != null && WAREHOUSE_LOCATION > 0) {
					location = "AND R.WAREHOUSE_LOCATION_ID =" + WAREHOUSE_LOCATION + "";
				}
				if (TYRE_USAGE != "" && TYRE_USAGE != null) {
					String[] usagerFromTo = TYRE_USAGE.split(",");
					usage = "AND R.TYRE_USEAGE BETWEEN " + usagerFromTo[0] + " AND " + usagerFromTo[1] + "";
				}

				String Query = TYREID_Mutiple + " " + location + " " + usage;
				List<InventoryTyreDto> pageList = iventoryTyreService.Filter_Retread_Tyre_Inventory(Query, userDetails.getCompany_id());

				model.put("Tyre", pageList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("Report_ScrapFilter", model);

	}

	/** This Controller save the saveTyreScrapInfo Method */
	@RequestMapping(value = "/saveTyreScrapInfo", method = RequestMethod.POST)
	public ModelAndView saveTyreScrapInfo(@RequestParam("ScrapDescription") final String ScrapDescription,
			@RequestParam("TyreID") final String[] TyreSerialID, Model modelAdd, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// getting Fuel ID in Multiple Format
			String Multiple_TYRE_id = "";
			if (TyreSerialID != null) {
				int i = 1;
				// save to select Tyre to Scraped process
				for (String TyreIDSelect : TyreSerialID) {

					if (TyreIDSelect != null) {

						// Get Tyre Details
						InventoryTyreDto Tyre = iventoryTyreService
								.Get_Only_TYRE_ID_And_TyreSize(Long.parseLong(TyreIDSelect), userDetails.getCompany_id());
						// Create Tyre History in Sent Retread history
						InventoryTyreHistory TyreHistory = prepare_InventoryTyreHistory_SENT_SCRAPED(Tyre,ScrapDescription);
						TyreHistory.setCOMPANY_ID(userDetails.getCompany_id());
						iventoryTyreService.add_Inventory_TYRE_History(TyreHistory);

						if (i != TyreSerialID.length) {
							Multiple_TYRE_id += TyreIDSelect + ",";
						} else {
							Multiple_TYRE_id += TyreIDSelect + "";
						}
						i++;

					}

				} // close For loop to save Retread Amount

				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp scropedDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			

				iventoryTyreService.Update_Inventory_Tyre_ScropStatus(userDetails.getId(), scropedDate, InventoryTyreDto.TYRE_ASSIGN_STATUS_SCRAPED,
						Multiple_TYRE_id, userDetails.getCompany_id());
				/*
				 * modelAdd.addAttribute("successTyre", "" + countSAVE);
				 * modelAdd.addAttribute("DuplicateTyre", "" + countDuplicate);
				 * 
				 */
				model.put("ScrapSuccess", true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreInventoryNew/1.in?danger=true");
		}finally {
			userDetails = null;
		}

		return new ModelAndView("redirect:/TyreInventoryNew/1.in", model);
	}

	/** This Controller save the saveTyreScrapInfo Method */
	@RequestMapping(value = "/saveTyreAvailable", method = RequestMethod.POST)
	public ModelAndView saveTyreScrapInfo(@RequestParam("Description") final String Description,
			@RequestParam("TyreID") final Long TyreSerialID, Model modelAdd, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (TyreSerialID != null) {

				// Get Tyre Details
				InventoryTyreDto Tyre = iventoryTyreService.Get_Only_TYRE_ID_And_TyreSize(TyreSerialID, userDetails.getCompany_id());
				// Create Tyre History in Sent Retread history
				InventoryTyreHistory TyreHistory = prepare_InventoryTyreHistory_SCRAPED_TO_AVAILABLE(Tyre, Description);
				TyreHistory.setCOMPANY_ID(userDetails.getCompany_id());
				iventoryTyreService.add_Inventory_TYRE_History(TyreHistory);

			//	iventoryTyreService.Update_Inventory_Tyre_AVALABLE_Status(InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE, TyreSerialID, userDetails.getCompany_id());

				iventoryTyreService.updateInventoryTyreRejectStatus(userDetails.getId(),DateTimeUtility.getCurrentTimeStamp(), InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE,(short)0,
						TyreSerialID.toString(), userDetails.getCompany_id());
				model.put("AvailableSuccess", true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreInventoryNew/1.in?danger=true");
		}finally {
			userDetails = null;
		}

		return new ModelAndView("redirect:/showTyreInfo.in?Id=" + TyreSerialID + "", model);
	}

	// THis Controller Save Tyre Retread Document Details
	@RequestMapping(value = "/uploadTyreDocument", method = RequestMethod.POST)
	public ModelAndView uploadTyreDocument(@RequestParam("TRID") final Long TyreRetreadID,
			@ModelAttribute("command") InventoryTyreRetreadDocument TyreRetreadDocument,
			@RequestParam("input-file-preview") MultipartFile file) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			org.fleetopgroup.persistence.document.InventoryTyreRetreadDocument RetreadDocument = new org.fleetopgroup.persistence.document.InventoryTyreRetreadDocument();
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			try {
				RetreadDocument.setTRID(TyreRetreadID);

				byte[] bytes = file.getBytes();
				RetreadDocument.setTR_FILENAME(file.getOriginalFilename());
				RetreadDocument.setTR_CONTENT(bytes);
				RetreadDocument.setTR_CONTENT_TYRE(file.getContentType());
				RetreadDocument.setCOMPANY_ID(userDetails.getCompany_id());

				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				RetreadDocument.setCREATED_DATE(toDate);
				
				RetreadDocument.setCREATED_BY(userDetails.getEmail());

			} catch (IOException e) {
				e.printStackTrace();
			}

			org.fleetopgroup.persistence.document.InventoryTyreRetreadDocument doc = iventoryTyreService.Get_Validate_TYRE_Document(TyreRetreadID, userDetails.getCompany_id());
			
			if (doc != null) {
				RetreadDocument.set_id(doc.get_id());
				iventoryTyreService.update_Tyre_InventoryTyreRetreadDocument(RetreadDocument);
			} else {
				iventoryTyreService.add_Tyre_InventoryTyreRetreadDocument(RetreadDocument);
			}

			model.put("UploadSuccess", true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/TyreRetreadNew/1.in?danger=true");
		}
		return new ModelAndView("redirect:/ShowRetreadTyre?RID=" + TyreRetreadID + "", model);
	}

	// Should be Download Tyre Document
	@RequestMapping("/download/TyreDocument/{TyreRetreadID}")
	public String TyreDocument(@PathVariable("TyreRetreadID") Long TyreRetreadID, HttpServletResponse response)
			throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (TyreRetreadID == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				org.fleetopgroup.persistence.document.InventoryTyreRetreadDocument file = iventoryTyreService.Get_Validate_TYRE_Document(TyreRetreadID, userDetails.getCompany_id());

				if (file != null) {
					if (file.getTR_CONTENT() != null) {

						response.setHeader("Content-Disposition", "inline;filename=\"" + file.getTR_FILENAME() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getTR_CONTENT_TYRE());
						response.getOutputStream().write(file.getTR_CONTENT());
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

	/** load Tyre Inventory Retread Details vendor */
	private InventoryTyreRetread prepare_Retread_Tyre(InventoryTyreRetreadDto TyreRetreadDto) {

		InventoryTyreRetread TyreRetread = new InventoryTyreRetread();

		TyreRetread.setTRID(TyreRetreadDto.getTRID());

		if (TyreRetreadDto.getTR_OPEN_DATE() != null) {
			try {
				java.util.Date date = SQLdateFormat.parse(TyreRetreadDto.getTR_OPEN_DATE());
				java.sql.Date start_date = new java.sql.Date(date.getTime());
				TyreRetread.setTR_OPEN_DATE(start_date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (TyreRetreadDto.getTR_REQUIRED_DATE() != null && !TyreRetreadDto.getTR_REQUIRED_DATE().trim().equalsIgnoreCase("")) {
			try {
				java.util.Date date = SQLdateFormat.parse(TyreRetreadDto.getTR_REQUIRED_DATE());
				java.sql.Date start_date = new java.sql.Date(date.getTime());
				TyreRetread.setTR_REQUIRED_DATE(start_date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (TyreRetreadDto.getTR_VENDOR_ID() != 0) {
			TyreRetread.setTR_VENDOR_ID(TyreRetreadDto.getTR_VENDOR_ID());
			// get Vendor id to Vendor name
			//Vendor vendorname = vendorService.getVendor_Details_Fuel_entries(TyreRetreadDto.getTR_VENDOR_ID());
			// show Vehicle name
			//TyreRetread.setTR_VENDOR_NAME(vendorname.getVendorName());
			//TyreRetread.setTR_VENDOR_LOCATION(vendorname.getVendorLocation());
		} else {
			TyreRetread.setTR_VENDOR_ID(0);
			//TyreRetread.setTR_VENDOR_NAME(TyreRetreadDto.getTR_VENDOR_NAME());
			//TyreRetread.setTR_VENDOR_LOCATION(TyreRetreadDto.getTR_VENDOR_LOCATION());
		}

		// payment Type is credit set vendor pay mode is NOTPAID
		TyreRetread.setTR_PAYMENT_TYPE_ID(TyreRetreadDto.getTR_PAYMENT_TYPE_ID());
		TyreRetread.setTR_PAYMENT_NUMBER(TyreRetreadDto.getTR_PAYMENT_NUMBER());
		if (TyreRetread.getTR_PAYMENT_TYPE_ID() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
			TyreRetread.setTR_VENDOR_PAYMODE_STATUS_ID(InventoryTyreRetreadDto.TR_VENDOR_PAYMODE_STATUS_NOTPAID);
		} else {
			TyreRetread.setTR_VENDOR_PAYMODE_STATUS_ID(InventoryTyreRetreadDto.TR_VENDOR_PAYMODE_STATUS_PAID);
			try {
				java.util.Date currentDate3 = new java.util.Date();
				java.util.Date dateTo3 = dateFormatTime.parse(ft.format(currentDate3));
				TyreRetread.setTR_VENDOR_PAYMODE_DATE(dateTo3);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		TyreRetread.setTR_QUOTE_NO(TyreRetreadDto.getTR_QUOTE_NO());
		TyreRetread.setTR_MANUAL_NO(TyreRetreadDto.getTR_MANUAL_NO());
		TyreRetread.setTR_DESCRIPTION(TyreRetreadDto.getTR_DESCRIPTION());

		TyreRetread.setTR_AMOUNT(0.0);

		TyreRetread.setTR_ROUNT_AMOUNT(0.0);
		TyreRetread.setTR_RECEIVE_TYRE(0);

	//	TyreRetread.setTR_STATUS("OPEN");
		TyreRetread.setTR_STATUS_ID(InventoryTyreRetreadDto.INVENTORY_TYRE_TR_STATUS_OPEN);

		/** who Create this Issues get email id to user profile details */
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		//TyreRetread.setCREATEDBY(userDetails.getEmail());
		//TyreRetread.setLASTMODIFIEDBY(userDetails.getEmail());
		TyreRetread.setCREATEDBYID(userDetails.getId());
		TyreRetread.setLASTMODIFIEDBYID(userDetails.getId());
		TyreRetread.setMarkForDelete(false);
		TyreRetread.setCOMPANY_ID(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp CREATED_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());

		TyreRetread.setCREATED_DATE(CREATED_DATE);
		TyreRetread.setLASTUPDATED_DATE(CREATED_DATE);

		return TyreRetread;
	}

	/** load Tyre Inventory Retread Details vendor */
	private InventoryTyreRetreadAmount prepare_RetreadAmount_Tyre(InventoryTyreDto Tyre) {

		InventoryTyreRetreadAmount TyreRetread = new InventoryTyreRetreadAmount();

		TyreRetread.setTR_AMOUNT_ID(TyreRetread.getTR_AMOUNT_ID());

		TyreRetread.setTYRE_ID(Tyre.getTYRE_ID());
		// get Vendor id to Vendor name
		// show Tyre name
		TyreRetread.setTYRE_NUMBER(Tyre.getTYRE_NUMBER());
		//TyreRetread.setTYRE_SIZE(Tyre.getTYRE_SIZE());
		TyreRetread.setTYRE_SIZE_ID(Tyre.getTYRE_SIZE_ID());

		TyreRetread.setRETREAD_AMOUNT(0.0);
		TyreRetread.setRETREAD_COST(0.0);
		TyreRetread.setRETREAD_DISCOUNT(0.0);
		TyreRetread.setRETREAD_TAX(0.0);
		//TyreRetread.setTRA_STATUS("OPEN");
		TyreRetread.setTRA_STATUS_ID(InventoryTyreRetreadAmountDto.TRA_STATUS_OPEN);

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp UPDATED_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());
		TyreRetread.setUPDATED_DATE(UPDATED_DATE);

		/** who Create this Issues get email id to user profile details */
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		//TyreRetread.setUPDATEDBY(userDetails.getEmail());
		TyreRetread.setUPDATEDBYID(userDetails.getId());
		TyreRetread.setCOMPANY_ID(userDetails.getCompany_id());

		return TyreRetread;
	}

	/** load Tyre InventoryTyreHistory Details Sent Retread History vendor */
	private InventoryTyreHistory prepare_InventoryTyreHistory_SENT_RETREAD(InventoryTyreDto Tyre) {

		InventoryTyreHistory TyreHistory = new InventoryTyreHistory();

		TyreHistory.setTYRE_ID(Tyre.getTYRE_ID());
		TyreHistory.setTYRE_NUMBER(Tyre.getTYRE_NUMBER());

		TyreHistory.setVEHICLE_ID(0);
		//TyreHistory.setVEHICLE_REGISTRATION(null);

		TyreHistory.setPOSITION(null);
		TyreHistory.setAXLE(null);

		//TyreHistory.setTYRE_STATUS("SENT-RETREAD");
		TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_SENT_RETREAD);
		TyreHistory.setOPEN_ODOMETER(null);
		TyreHistory.setTYRE_USEAGE(Tyre.getTYRE_USEAGE());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp Retread_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());

		TyreHistory.setTYRE_ASSIGN_DATE(Retread_DATE);

		TyreHistory.setTYRE_COMMENT("Tyre Send to Retread Enter Created");

		return TyreHistory;
	}

	/** load Tyre InventoryTyreHistory Details Receive History vendor */
	private InventoryTyreHistory prepare_InventoryTyreHistory_RECEIVE(InventoryTyre Tyre) {

		InventoryTyreHistory TyreHistory = new InventoryTyreHistory();

		TyreHistory.setTYRE_ID(Tyre.getTYRE_ID());
		TyreHistory.setTYRE_NUMBER(Tyre.getTYRE_NUMBER());

		TyreHistory.setVEHICLE_ID(0);
		//TyreHistory.setVEHICLE_REGISTRATION(null);

		TyreHistory.setPOSITION(null);
		TyreHistory.setAXLE(null);

		TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_RECEIVE);
		TyreHistory.setOPEN_ODOMETER(null);
		TyreHistory.setTYRE_USEAGE(0);

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp Retread_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());

		TyreHistory.setTYRE_ASSIGN_DATE(Retread_DATE);

		TyreHistory.setTYRE_COMMENT("Retread done");

		return TyreHistory;
	}

	/** load Tyre InventoryTyreHistory Details Reject History vendor */
	private InventoryTyreHistory prepare_InventoryTyreHistory_REJECT(InventoryTyreRetreadAmount TyreScraped) {

		InventoryTyreHistory TyreHistory = new InventoryTyreHistory();

		TyreHistory.setTYRE_ID(TyreScraped.getTYRE_ID());
		TyreHistory.setTYRE_NUMBER(TyreScraped.getTYRE_NUMBER());

		TyreHistory.setVEHICLE_ID(0);
	//	TyreHistory.setVEHICLE_REGISTRATION(null);

		TyreHistory.setPOSITION(null);
		TyreHistory.setAXLE(null);

		TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_REJECT);
		TyreHistory.setOPEN_ODOMETER(null);
		TyreHistory.setTYRE_USEAGE(0);

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp Retread_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());

		TyreHistory.setTYRE_ASSIGN_DATE(Retread_DATE);

		TyreHistory.setTYRE_COMMENT("Reject To Retread Vendor");

		return TyreHistory;
	}

	/** load Tyre InventoryTyreHistory Details Sent SCRAPED History vendor */
	private InventoryTyreHistory prepare_InventoryTyreHistory_SENT_SCRAPED(InventoryTyreDto Tyre, String decription) {

		InventoryTyreHistory TyreHistory = new InventoryTyreHistory();

		TyreHistory.setTYRE_ID(Tyre.getTYRE_ID());
		TyreHistory.setTYRE_NUMBER(Tyre.getTYRE_NUMBER());

		TyreHistory.setVEHICLE_ID(0);
		//TyreHistory.setVEHICLE_REGISTRATION(null);

		TyreHistory.setPOSITION(null);
		TyreHistory.setAXLE(null);

		TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_SCRAPED);
		TyreHistory.setOPEN_ODOMETER(null);
		TyreHistory.setTYRE_USEAGE(Tyre.getTYRE_USEAGE());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp Retread_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());

		TyreHistory.setTYRE_ASSIGN_DATE(Retread_DATE);

		TyreHistory.setTYRE_COMMENT(decription);

		return TyreHistory;
	}

	/** load Tyre InventoryTyreHistory Details Sent SCRAPED History vendor */
	private InventoryTyreHistory prepare_InventoryTyreHistory_SCRAPED_TO_AVAILABLE(InventoryTyreDto Tyre,
			String decription) {

		InventoryTyreHistory TyreHistory = new InventoryTyreHistory();

		TyreHistory.setTYRE_ID(Tyre.getTYRE_ID());
		TyreHistory.setTYRE_NUMBER(Tyre.getTYRE_NUMBER());

		TyreHistory.setVEHICLE_ID(0);
		//TyreHistory.setVEHICLE_REGISTRATION(null);

		TyreHistory.setPOSITION(null);
		TyreHistory.setAXLE(null);

		TyreHistory.setTYRE_STATUS_ID(InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_AVAILABLE);
		TyreHistory.setOPEN_ODOMETER(null);
		TyreHistory.setTYRE_USEAGE(Tyre.getTYRE_USEAGE());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp Retread_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());

		TyreHistory.setTYRE_ASSIGN_DATE(Retread_DATE);

		TyreHistory.setTYRE_COMMENT(decription);

		return TyreHistory;
	}

	// get to save in prepareWorkOrders
	private InventoryTyreRetreadAmount prepare_UPdate_TyreRetreadAmount(InventoryTyreRetreadAmount TyreRetreadAmount) {

		InventoryTyreRetreadAmount TyreRetread = new InventoryTyreRetreadAmount();

		TyreRetread.setTR_AMOUNT_ID(TyreRetreadAmount.getTR_AMOUNT_ID());

		Double EachRtyrecost = 0.0;
		Double laberdisc = 0.0;
		Double labertax = 0.0;

		if (TyreRetreadAmount.getRETREAD_COST() != null) {
			EachRtyrecost = (TyreRetreadAmount.getRETREAD_COST());
		}
		if (TyreRetreadAmount.getRETREAD_DISCOUNT() != null) {
			laberdisc = TyreRetreadAmount.getRETREAD_DISCOUNT();
		}
		if (TyreRetreadAmount.getRETREAD_TAX() != null) {
			labertax = TyreRetreadAmount.getRETREAD_TAX();
		}

		Double discountCost = 0.0;
		Double TotalCost = 0.0;

		discountCost = (EachRtyrecost) - (EachRtyrecost * (laberdisc / 100));
		TotalCost = round(((discountCost) + (discountCost * (labertax / 100))), 2);

		TyreRetread.setRETREAD_COST(EachRtyrecost);
		TyreRetread.setRETREAD_DISCOUNT(laberdisc);
		TyreRetread.setRETREAD_TAX(labertax);

		TyreRetread.setRETREAD_AMOUNT(TotalCost);

		return TyreRetread;
	}

	// get to save in prepareWorkOrders
	private InventoryTyreLifeHistory prepare_SAVE_InventoryTyreLifeHistory(InventoryTyre RetreadTyre) {

		InventoryTyreLifeHistory TyreLifeHistory = new InventoryTyreLifeHistory();

		TyreLifeHistory.setTYRE_ID(RetreadTyre.getTYRE_ID());
		TyreLifeHistory.setTYRE_NUMBER(RetreadTyre.getTYRE_NUMBER());
		TyreLifeHistory.setTYRE_LIFE_COST(RetreadTyre.getTYRE_AMOUNT());
		TyreLifeHistory.setTYRE_LIFE_USAGE(RetreadTyre.getTYRE_USEAGE());

		Integer lIFE_PERIOD = RetreadTyre.getTYRE_RETREAD_COUNT();

		if (lIFE_PERIOD == 0) {
			TyreLifeHistory.setLIFE_PERIOD("NEW");
		} else {
			TyreLifeHistory.setLIFE_PERIOD(lIFE_PERIOD + " RETREAD");
		}

		Double Amount = RetreadTyre.getTYRE_AMOUNT();
		Integer usage = RetreadTyre.getTYRE_USEAGE();

		Double Km = Amount / usage;

		if (usage != 0) {
			TyreLifeHistory.setTYRE_KM_COST(round(Km, 2));
		} else {
			TyreLifeHistory.setTYRE_KM_COST(0.0);
		}

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp tYRE_RECEIVED_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());

		TyreLifeHistory.setTYRE_RECEIVED_DATE(tYRE_RECEIVED_DATE);

		return TyreLifeHistory;
	}
	
	@RequestMapping(value = "/PrintTyreRetread", method = RequestMethod.GET)
	public ModelAndView PrintBatteryInventory(@RequestParam("TRID") final Integer TRID,
			final HttpServletResponse resul) {
 			Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("TRID", TRID);
			
			
		} catch (Exception e) {
			System.err.println("exception"+e);
			/*LOGGER.error("Work Order:", e);*/
		}
		return new ModelAndView("PrintTyreRetreadPrint", model);
	}
	
	@RequestMapping(value = "/editRetreadTyre", method = RequestMethod.GET)
	public ModelAndView editRetreadTyre(@RequestParam("Id") final Long TyreRetreadID,
			final InventoryTyreInvoiceDto TyreInvoiceDto, final HttpServletRequest request) {
		Map<String, Object> 					model 				= new HashMap<String, Object>();
		CustomUserDetails						userDetails			= null;
		InventoryTyreRetreadDto 				TyreRetread			= null;
		List<InventoryTyreRetreadAmountDto>  	Dtos 				= null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TyreRetread 		= iventoryTyreService.Get_InventoryTyreRetread(TyreRetreadID, userDetails.getCompany_id());
			if (TyreRetread != null) {
				Dtos = ITBL.getInventoryTyreRetreadAmountList(iventoryTyreService.Get_InventoryTyreRetread_Amount(TyreRetreadID, userDetails.getCompany_id()));
			}
			
			model.put("Retread", TyreRetread);
			model.put("RetreadAmount", Dtos);
		} catch (Exception e) {
			LOGGER.error("Add Tyre Inventory Page:", e);
		}finally {
			userDetails		= null;
		}
		return new ModelAndView("Edit_TyreRetreadInventory", model);
	}
	
/*	@RequestMapping(value = "/getAllRetreadTyreList", method = RequestMethod.GET)
	public void getAllRetreadTyreList(Map<String, Object> map, final HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			List<InventoryTyreRetreadDto>	inventoryTyreRetreadDto  = iventoryTyreService.getAllRetreadTyreList();
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(inventoryTyreRetreadDto));
	}*/
	
	

	@RequestMapping(value = "/getAllRetreadTyreList", method = RequestMethod.POST)
	public void getAllRetreadTyreList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<InventoryTyreRetreadDto> Part = new ArrayList<InventoryTyreRetreadDto>();
		List<InventoryTyreRetreadDto> Parttype = iventoryTyreService.getAllRetreadTyreList(term);
		
		if (Parttype != null && !Parttype.isEmpty()) {
			for (InventoryTyreRetreadDto add : Parttype) {
				InventoryTyreRetreadDto wadd = new InventoryTyreRetreadDto();

				wadd.setTRID(add.getTRID());
				wadd.setTyre_Number(add.getTyre_Number());
				wadd.setTyre_Manufacturer(add.getTyre_Manufacturer());

				Part.add(wadd);
				
				
			}
		}
		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(Part));
	}
	

}
