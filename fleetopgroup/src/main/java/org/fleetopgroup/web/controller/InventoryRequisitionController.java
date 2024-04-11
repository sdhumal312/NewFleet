/**
 * 
 */
package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.InventoryRequisitionStatus;
import org.fleetopgroup.constant.InventoryTransferStatus;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.UserAlertNOtificationsConstant;
import org.fleetopgroup.persistence.bl.InventoryBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.dao.UserAlertNotificationsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryRequisitionDto;
import org.fleetopgroup.persistence.dto.InventoryRequisitionPartsDto;
import org.fleetopgroup.persistence.dto.InventoryTransferDto;
import org.fleetopgroup.persistence.dto.PartLocationPermissionDto;
import org.fleetopgroup.persistence.model.InventoryRequisition;
import org.fleetopgroup.persistence.model.InventoryRequisitionParts;
import org.fleetopgroup.persistence.model.InventoryRequisitionSequenceCounter;
import org.fleetopgroup.persistence.model.InventoryTransfer;
import org.fleetopgroup.persistence.model.UserAlertNotifications;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryRequisitionSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryRequisitionService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author fleetop
 *
 */
@Controller
public class InventoryRequisitionController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IInventoryRequisitionService InventoryRequisitionService;

	@Autowired
	private IInventoryRequisitionSequenceService InventoryRequisitionSequenceService;

	@Autowired
	private IPartLocationPermissionService partLocationPermissionService;
	
	@Autowired IInventoryService 					inventoryService;
	
	@Autowired private UserAlertNotificationsRepository	userAlertNotificationsRepository;
	@Autowired private ICompanyConfigurationService		companyConfigurationService;

	InventoryBL INVBL = new InventoryBL();
	
	PartLocationsBL PLBL = new PartLocationsBL();

	@RequestMapping(value = "/searchInvenPartReq", method = RequestMethod.POST)
	public ModelAndView searchLocationInventory(@RequestParam("SearchInveReq") Long SearchInveReq,
			final HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<InventoryRequisitionDto> pageList = InventoryRequisitionService
					.Search_Of_All_Inventory_Requisition(SearchInveReq, userDetails.getCompany_id());

			model.put("InvRequisition", pageList);

			model.put("SelectStatus", 1);
			model.put("SelectPage", 1);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Inventory newInventoryRequisition Page:", e);
		}
		return new ModelAndView("newInventoryRequisition", model);
	}

	// This controller is Part Requisition of all requisition
	@RequestMapping(value = "/PartRequisition/{Status}/{pageNumber}", method = RequestMethod.GET)
	public String NewInventoryList(@PathVariable("Status") short Status, @PathVariable("pageNumber") Integer pageNumber,
			Model model) throws Exception {
			CustomUserDetails 					userDetails 		= null;
			Page<InventoryRequisition> 			page				= null;
			List<InventoryRequisitionDto> 		pageList			= null;
			List<InventoryRequisitionDto> 		partPageList		= null;
			List<InventoryRequisitionDto> 		pageListBL			= null;
			List<InventoryRequisitionDto> 		finalList			= null;
		try {
			finalList	= new ArrayList<InventoryRequisitionDto>();
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			page = InventoryRequisitionService.getDeployment_Page_InventoryRequisition(Status, pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);

			model.addAttribute("InventoryCount", page.getTotalElements());

			pageList 			= InventoryRequisitionService.list_Of_All_Inventory_Requisition(Status, pageNumber, userDetails.getCompany_id());
			if(pageList != null) {
				for(InventoryRequisitionDto dto : pageList) {
					partPageList		= InventoryRequisitionService.list_Of_All_InventoryParts_Requisition(dto.getINVRID(),userDetails.getCompany_id());
					if(partPageList != null) {
						pageListBL			= INVBL.listOfAllPartRequisition(dto , partPageList);
						finalList.addAll(pageListBL);
					}
					
				}
			}
			
			model.addAttribute("InvRequisition", finalList);

			model.addAttribute("SelectStatus", Status);
			model.addAttribute("SelectPage", pageNumber);

		} catch (NullPointerException e) {
			LOGGER.error("error Page:", e);
			e.printStackTrace();
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("newInventory Page:", e);
			e.printStackTrace();
		}
		return "newInventoryRequisition";
	}

	// This controller newly create Part Requisition page
	@RequestMapping(value = "/addPartRequisition", method = RequestMethod.GET)
	public ModelAndView PartRequisition(final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			model.put("partLocationPermission", PLBL.prepareListofPartLocation(partLocationPermissionService
					.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/PartRequisition/1/1.in", model);
		} finally {
			userDetails = null;
		}
		return new ModelAndView("PartRequisition", model);
	}

	// This controller save Part Requisition page
	@RequestMapping(value = "/savePartRequisition", method = RequestMethod.POST)
	public ModelAndView savePartRequisition(@ModelAttribute("command") InventoryRequisitionDto Inventory,
			@RequestParam("partid_many") final String[] partid, @RequestParam("quantity_many") final String[] quantity,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		InventoryRequisitionSequenceCounter sequenceCounter = null;
		Long Requisition_ID = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			sequenceCounter = InventoryRequisitionSequenceService
					.findNextInventoryRequisitionNumber(userDetails.getCompany_id());
			if (sequenceCounter == null) {
				model.put("sequenceNotFound", true);
				return new ModelAndView("redirect:/PartRequisition/1/1.in", model);
			}
			InventoryRequisition Requisition = INVBL.prepareInventoryRequisition(Inventory);
			Requisition.setCOMPANY_ID(userDetails.getCompany_id());
			Requisition.setINVRID_NUMBER(sequenceCounter.getNextVal());
			InventoryRequisitionService.add_InventoryRequisition(Requisition);
			/*
			 * InventoryRequisitionSequenceService.updateNextInventoryRequisitionNumber(
			 * sequenceCounter.getNextVal() + 1, sequenceCounter.getSequence_Id());
			 */

			Requisition_ID = Requisition.getINVRID();
			for (int i = 0; i < partid.length; i++) {

				try {
					InventoryRequisitionParts ReParts = new InventoryRequisitionParts();
					ReParts.setPART_ID(Long.parseLong(partid[i] + ""));
					ReParts.setPART_REQUITED_QTY(Double.parseDouble(quantity[i] + ""));
					ReParts.setPART_TRANSFER_QTY(0.0);
					ReParts.setCOMPANY_ID(userDetails.getCompany_id());
					ReParts.setmarkForDelete(false);

					ReParts.setINVRID(Requisition.getINVRID());

					InventoryRequisitionService.add_InventoryRequisitionPart(ReParts);
				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/PartRequisition/1/1.in?danger=true");
		}
		model.put("success", true);
		return new ModelAndView("redirect:/1/1/showInventoryReq.in?ID=" + Requisition_ID + "", model);
	}

	// This controller show Part Requisition page
	@RequestMapping(value = "/{Status}/{pageNumber}/showInventoryReq", method = RequestMethod.GET)
	public ModelAndView showInventoryReq(@PathVariable("Status") short Status,
			@PathVariable("pageNumber") Integer pageNumber, @RequestParam("ID") final Long INVRID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		boolean isPartPendingQtyZero=false;
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG);

			InventoryRequisitionDto inventoryall = InventoryRequisitionService.GET_Inventory_Requisition_ID(INVRID,
					userDetails.getCompany_id());

			model.put("InvRequisition", inventoryall);
			model.put("configuration", configuration);

			List<InventoryRequisitionPartsDto> inventoryPart = InventoryRequisitionService
					.LIST_Inventory_Requisition_Part_ID(INVRID, userDetails.getCompany_id());

			model.put("InvReqPart", inventoryPart);
			
			
			for(InventoryRequisitionPartsDto invReqPart :inventoryPart ) {
				if(invReqPart.getPART_PENDING_QTY() >0) {
					isPartPendingQtyZero=true;	
					break;
					}
				}

			model.put("SelectStatus", Status);
			model.put("SelectPage", pageNumber);
			model.put("isPartPendingQtyZero",isPartPendingQtyZero);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Inventory Page:", e);
		}
		return new ModelAndView("showInventoryRequisition", model);
	}

	// add more Parts
	@RequestMapping(value = "/saveREQADDMORE", method = RequestMethod.POST)
	public ModelAndView saveREQADDMORE(@RequestParam("INVRID") final Long INVRID,
			@RequestParam("partid_many") final String[] partid, @RequestParam("quantity_many") final String[] quantity,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;

		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (INVRID == null) {
				model.put("sequenceNotFound", true);
				return new ModelAndView("redirect:/PartRequisition/1/1.in", model);
			}
			for (int i = 0; i < partid.length; i++) {

				try {
					InventoryRequisitionParts ReParts = new InventoryRequisitionParts();
					ReParts.setPART_ID(Long.parseLong(partid[i] + ""));
					ReParts.setPART_REQUITED_QTY(Double.parseDouble(quantity[i] + ""));
					ReParts.setPART_TRANSFER_QTY(0.0);
					ReParts.setCOMPANY_ID(userDetails.getCompany_id());
					ReParts.setmarkForDelete(false);

					ReParts.setINVRID(INVRID);

					InventoryRequisitionService.add_InventoryRequisitionPart(ReParts);
				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/PartRequisition/1/1.in?danger=true");
		}
		model.put("success", true);
		return new ModelAndView("redirect:/1/1/showInventoryReq.in?ID=" + INVRID + "", model);
	}

	// This controller Remove Part Requisition page
	@RequestMapping(value = "/RemovePartRequisition", method = RequestMethod.GET)
	public ModelAndView RemovePartRequisition(@RequestParam("ID") final Long INVRID,
			@RequestParam("RID") final Long INVRPARTID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			InventoryRequisitionService.DELETE_InventoryRequisitionParts_MARK(INVRPARTID, userDetails.getCompany_id());

			model.put("Delete", true);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Inventory Page:", e);
			return new ModelAndView("redirect:/PartRequisition/1/1.in", model);
		}
		return new ModelAndView("redirect:/1/1/showInventoryReq.in?ID=" + INVRID + "", model);
	}

	// This controller sent create Part Requisition page
	@RequestMapping(value = "/SentPartRequisition", method = RequestMethod.POST)
	public ModelAndView SentPartRequisition(@RequestParam("INVRID") final Long INVRID,
			@RequestParam("REQUITED_REMARK") final String Remarks, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (INVRID != null) {

				Date currentDateUpdate = new Date();
				Timestamp Update_Date = new java.sql.Timestamp(currentDateUpdate.getTime());

				InventoryRequisitionService.Update_InventoryRequisition_SENT(InventoryRequisitionStatus.REQUISTION,
						Remarks, userDetails.getId(), Update_Date, INVRID, userDetails.getCompany_id());
				model.put("Update", true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Inventory Page:", e);
			return new ModelAndView("redirect:/PartRequisition/1/1.in", model);
		}
		return new ModelAndView("redirect:/1/1/showInventoryReq.in?ID=" + INVRID + "", model);
	}

	// This controller sent create Part Transfer page
	@RequestMapping(value = "/SentPartTransfer", method = RequestMethod.POST)
	public ModelAndView SentPartTransfer(@RequestParam("INVRID") final Long INVRID,
			@RequestParam("REQUITED_REMARK") final String Remarks, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (INVRID != null) {

				Date currentDateUpdate = new Date();
				Timestamp Update_Date = new java.sql.Timestamp(currentDateUpdate.getTime());

				InventoryRequisitionService.Update_InventoryRequisition_SENT(InventoryRequisitionStatus.TRANSFERED,
						Remarks, userDetails.getId(), Update_Date, INVRID, userDetails.getCompany_id());
				model.put("Update", true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Inventory Page:", e);
			return new ModelAndView("redirect:/PartRequisition/1/1.in", model);
		}
		return new ModelAndView("redirect:/1/1/showInventoryReq.in?ID=" + INVRID + "", model);
	}

	// This controller sent create Part Requisition page
	@RequestMapping(value = "/SentPartCompleted", method = RequestMethod.POST)
	public ModelAndView SentPartCompleted(@ModelAttribute("command") InventoryRequisitionDto inventory,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (inventory.getINVRID() != null) {
				Date currentDateUpdate = new Date();
				Timestamp Update_Date = new java.sql.Timestamp(currentDateUpdate.getTime());

				InventoryRequisitionService.Update_InventoryRequisition_SENT(InventoryRequisitionStatus.COMPLETED,
						inventory.getREQUITED_REMARK(), userDetails.getId(), Update_Date, inventory.getINVRID(), userDetails.getCompany_id());
				model.put("Update", true);
			}
			
			if(inventory.getUserId() != null) {
				UserAlertNotifications	notifications = new UserAlertNotifications();
				notifications.setUserId(inventory.getUserId());
				notifications.setAlertMsg(inventory.getAlertMsg());
				notifications.setCompanyId(userDetails.getCompany_id());
				notifications.setTxnTypeId(UserAlertNOtificationsConstant.ALERT_TYPE_PART_REQUISITION);
				notifications.setTransactionId(inventory.getINVRID());
				notifications.setCreatedById(userDetails.getId());
				notifications.setCreatedOn(new Date());
				notifications.setStatus((short) 1);
				userAlertNotificationsRepository.save(notifications);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Inventory Page:", e);
			return new ModelAndView("redirect:/PartRequisition/1/1.in", model);
		}
		return new ModelAndView("redirect:/1/1/showInventoryReq.in?ID=" + inventory.getINVRID() + "", model);
	}

	// This controller is markFordelete in inventory
	@RequestMapping(value = "/deleteAllInventoryReq", method = RequestMethod.GET)
	public ModelAndView deleteAllInventoryReq(@RequestParam("ID") final Long INVRID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Date currentDateUpdate = new Date();
			Timestamp Update_Date = new java.sql.Timestamp(currentDateUpdate.getTime());

			List<PartLocationPermissionDto> PartLocPermission = PLBL.prepareListofPartLocation(partLocationPermissionService
					.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

			if (INVRID != null) {

				InventoryRequisitionDto inventoryall = InventoryRequisitionService.GET_Inventory_Requisition_ID(INVRID,
						userDetails.getCompany_id());

				// check user branch location and workOrder location if same
				if (INVBL.isFilled_GET_Permission(inventoryall.getREQUITED_LOCATION_ID(), PartLocPermission)) {

					// save part Manufacturers Service in Master part
					try {
						// first validate part_id is there are not

						InventoryRequisitionService.DELETE_InventoryRequisition_MARK(userDetails.getId(),
								Update_Date, INVRID, userDetails.getCompany_id());

						model.put("deleteInventory", true);

					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
					}
				} else {
					model.put("NoAuthen", true);
					return new ModelAndView("redirect:/1/1/showInventoryReq.in?ID=" + INVRID + "", model);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/NewInventory/1.in?danger=true");
		}

		return new ModelAndView("redirect:/1/1/showInventoryReq.in?ID=" + INVRID + "", model);
	}
	
	@RequestMapping(value = "/RejectedPartRequisition/{pageNumber}", method = RequestMethod.GET)
	public String NewInventoryList( @PathVariable("pageNumber") Integer pageNumber, Model model) throws Exception {
		String 								status	   	 				= null;
		CustomUserDetails 					userDetails 				= null;
		Page<InventoryTransfer> 			page 						= null;
		List<InventoryTransferDto> 			inventoryTransferDto 		= null;

		try {
			
			inventoryTransferDto 	= new ArrayList<>();
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			page 					= inventoryService.getDeployment_InventoryAll_id_To_InventoryTransfer_transfer_receivedbyEmail(userDetails.getId(),pageNumber, userDetails.getCompany_id());
			status 					= ""+InventoryTransferStatus.REJECTED;
			
			inventoryTransferDto 	= inventoryService.getAllRejectedRequisition(userDetails.getId(), pageNumber,userDetails.getCompany_id(),status);
			
			if(inventoryTransferDto != null) {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());
				
				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);
				
				model.addAttribute("InventoryCount", page.getTotalElements());
				model.addAttribute("InventoryTransfer", inventoryTransferDto);
				model.addAttribute("rejectedCount", inventoryTransferDto.size());
				model.addAttribute("SelectPage", pageNumber);
			}else {
				model.addAttribute("noRecordFound", true);
				return "redirect:/PartRequisition/4/1.in";
			}

		} catch (NullPointerException e) {
			LOGGER.error("ERR"+e);
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("newInventory Page:", e);
			e.printStackTrace();
		}
		return "YourRejectedPart";
	}

	
}
