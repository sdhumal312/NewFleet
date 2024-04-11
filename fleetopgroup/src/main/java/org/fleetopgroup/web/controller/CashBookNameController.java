package org.fleetopgroup.web.controller;

import java.util.ArrayList;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.bl.CashBookBL;
import org.fleetopgroup.persistence.dao.CashBookVoucherSequenceCounterRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.CashBookName;
import org.fleetopgroup.persistence.model.CashBookNameHistory;
import org.fleetopgroup.persistence.model.CashBookVoucherSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameHistoryService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class CashBookNameController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ICashBookNameService cashBookNameService;

	@Autowired
	private ICashBookNameHistoryService cashBookNameHistoryService;
	
	@Autowired
	private IVehicleGroupService	vehicleGroupService;
	
	@Autowired	private CashBookVoucherSequenceCounterRepository	cashBookVoucherRepository;

	CashBookBL cashBookBL = new CashBookBL();

	// Show the the Vehicle Status of
	@RequestMapping(value = "/addCashBookName", method = RequestMethod.GET)
	public ModelAndView addCashBookName(@ModelAttribute("command") CashBookName TripExpenseBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails			= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("CashBook", cashBookBL.getCashBookName_List(cashBookNameService.list_CashBookName(userDetails.getCompany_id())));
			model.put("VehicleGroup", vehicleGroupService.findAllVehicleGroupByCompanyId(userDetails.getCompany_id()));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("CashBookName Service Type Page:", e);

		}
		return new ModelAndView("add_CashBookName", model);
	}

	/* save the vehicle Status */
	@RequestMapping(value = "/saveCashBookName", method = RequestMethod.POST)
	public ModelAndView saveCashBookName(@ModelAttribute("command") CashBookName cashBookName,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails			= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			CashBookName bookName = cashBookBL.prepare_CashBookName(cashBookName);
			CashBookName validate = cashBookNameService.Validate_CashBookName(cashBookName.getCASHBOOK_NAME(),
					cashBookName.getCASHBOOK_NAME(), userDetails.getCompany_id(), cashBookName.getVEHICLE_GROUP_ID());
			if (validate == null) {
				bookName.setCREATEDBYID(userDetails.getId());
				bookName.setCOMPANY_ID(userDetails.getCompany_id());
				
				cashBookNameService.add_CashBookName(bookName);
				
				cashBookVoucherRepository.save(new CashBookVoucherSequenceCounter(bookName.getNAMEID(),(long) 1, userDetails.getCompany_id()));
				
				model.put("saveSuccess", true);
			} else {
				if(validate.getVEHICLE_GROUP_ID() == bookName.getVEHICLE_GROUP_ID()) {
					model.put("alreadyVehicleGroup", true);
				}else {
					model.put("already", true);
				}
				return new ModelAndView("redirect:/addCashBookName.in", model);
			}

		} catch (Exception e) {
			model.put("already", true);
			LOGGER.error("CashBookName Service Type Page:", e);
			return new ModelAndView("redirect:/addCashBookName.in", model);
		}
		return new ModelAndView("redirect:/addCashBookName.in", model);
	}

	@RequestMapping(value = "/editCashBookName", method = RequestMethod.GET)
	public ModelAndView editTripExpense(@RequestParam("Id") final Integer CashNameID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (CashNameID != null) {
				CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				model.put("CashBook", cashBookNameService.get_CashBookName(CashNameID, userDetails.getCompany_id()));
				model.put("VehicleGroup", vehicleGroupService.findAllVehicleGroupByCompanyId(userDetails.getCompany_id()));
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			model.put("already", true);
			LOGGER.error("CashBookName Service Type Page:", e);
			return new ModelAndView("redirect:/addCashBookName.in", model);
		}
		return new ModelAndView("edit_CashBookName", model);
	}

	// update of vehicle status
	@RequestMapping(value = "/uploadCashBookName", method = RequestMethod.POST)
	public ModelAndView uploadTripExpense(@ModelAttribute("command") CashBookName CashBookName,
			final HttpServletRequest request) {
		Map<String, Object> model	 = new HashMap<String, Object>();
		CashBookName	bookName	= null;
		CustomUserDetails		userDetails			= null;
		CashBookName status		 =	null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			status		 	= cashBookBL.prepare_CashBookNameForUpdate(CashBookName);
			bookName	 	= cashBookNameService.get_CashBookName(status.getNAMEID());	
			
			if(!bookName.getCASHBOOK_NAME().equalsIgnoreCase(status.getCASHBOOK_NAME().trim())) {
				CashBookName validate_type = cashBookNameService.Validate_CashBookName(CashBookName.getCASHBOOK_NAME(),
						CashBookName.getCASHBOOK_NAME().substring(0, 3), userDetails.getCompany_id(), CashBookName.getVEHICLE_GROUP_ID());
				if(validate_type != null && validate_type.getVEHICLE_GROUP_ID() != bookName.getVEHICLE_GROUP_ID()) {
					if(validate_type.getVEHICLE_GROUP_ID() == status.getVEHICLE_GROUP_ID()) {
						model.put("alreadyVehicleGroup", true);
					}else {
						model.put("already", true);
					}
					return new ModelAndView("redirect:/addCashBookName.in", model);
				}
			}
			
			status.setLASTMODIFIEDBYID(userDetails.getId());
			status.setCOMPANY_ID(userDetails.getCompany_id());

			CashBookNameHistory		cashBookNameHistory		= cashBookBL.prepareHistoryModel(bookName);
			
			cashBookNameService.update_CashBookName(status);
			cashBookNameHistoryService.add_CashBookNameHistory(cashBookNameHistory);
			
			model.put("uploadSuccess", true);
		} catch (Exception e) {
			model.put("already", true);
			LOGGER.error("CashBookName Service Type Page:", e);
			return new ModelAndView("redirect:/addCashBookName.in", model);
		}
		return new ModelAndView("redirect:/addCashBookName.in", model);
	}

	@RequestMapping(value = "/deleteCashBookName", method = RequestMethod.GET)
	public ModelAndView deleteTripExpense(@RequestParam("Id") final Integer CashNameID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (CashNameID != null) {
				CashBookNameHistory		cashBookNameHistory		= cashBookBL.prepareHistoryModel(cashBookNameService.get_CashBookName(CashNameID));
				
				cashBookNameService.delete_CashBookName(CashNameID, userDetails.getCompany_id());
				cashBookNameHistoryService.add_CashBookNameHistory(cashBookNameHistory);
				
				model.put("deleteSuccess", true);
			}
		} catch (Exception e) {
			model.put("already", true);
			LOGGER.error("CashBookName Service Type Page:", e);
			return new ModelAndView("redirect:/addCashBookName.in", model);
		}
		return new ModelAndView("redirect:/addCashBookName.in", model);
	}

	/*****************************************************************************
	 * Testing Loading datatable using Gson
	 ******************************************************************************/

	@RequestMapping(value = "/getCashBookNameList", method = RequestMethod.GET)
	public void getTripExpenseList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustomUserDetails		userDetails			= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<CashBookName> TripExpenseList = new ArrayList<CashBookName>();

			for (CashBookName Expense : cashBookNameService.list_CashBookNameByPermission(userDetails)) {

				CashBookName bean = new CashBookName();
				bean.setNAMEID(Expense.getNAMEID());
				bean.setCASHBOOK_NAME(Expense.getCASHBOOK_NAME());

				TripExpenseList.add(bean);
			}

			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(TripExpenseList));
			} catch (Exception e) {
				throw e;
			}
		
	}

}