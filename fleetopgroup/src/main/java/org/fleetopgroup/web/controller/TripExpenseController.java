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

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.TripExpenseBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.TripExpense;
import org.fleetopgroup.persistence.model.TripExpenseHistory;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseHistoryService;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseService;
import org.fleetopgroup.web.util.ValueObject;
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

import com.google.gson.Gson;

@Controller
public class TripExpenseController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ITripExpenseService tripExpenseService;

	@Autowired
	private ITripExpenseHistoryService tripExpenseHistoryService;

	@Autowired
	CompanyConfigurationService	companyConfigurationService;
	
	TripExpenseBL VehStBL = new TripExpenseBL();

	@RequestMapping(value = "/addTripExpense/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView addTripIncome(@PathVariable Integer pageNumber, Model model, final HttpServletRequest request) {
		
		CustomUserDetails userDetails = null;
		HashMap<String, Object> 	        configuration				    = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			
			Page<TripExpense> page = tripExpenseService.getDeployment_Page_TripExpense(pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("configuration" , configuration);
			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("vehicleTypeCount", page.getTotalElements());
			List<TripExpense> pageList = (tripExpenseService.GET_list_Of_TripExpense(pageNumber, userDetails));

			model.addAttribute("TripExpense", pageList);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("trip addTripRoute Page:", e);
			e.printStackTrace();
		}
	
		return new ModelAndView("addTripExpense");
	}
	
	

	/* save the vehicle Status */
	@RequestMapping(value = "/saveTripExpense", method = RequestMethod.POST)
	public ModelAndView saveTripExpense(@ModelAttribute("command") TripExpense TripExpense,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		HashMap<String, Object> 	        configuration				    = null;
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			
			TripExpense 		status 		= VehStBL.prepareModel(TripExpense);
			List<TripExpense> 	validate 	= tripExpenseService.ValidateTripExpense(TripExpense.getExpenseName(), userDetails.getCompany_id());
			if (validate != null && !validate.isEmpty()) {

				model.put("alreadyTripExpense", true);
				return new ModelAndView("redirect:/addTripExpense/1.in", model);
			} else {
				
				status.setCompanyId(userDetails.getCompany_id());
				status.setCreatedById(userDetails.getId());
				status.setLastModifiedById(userDetails.getId());
				
				tripExpenseService.addTripExpense(status);
				model.put("saveTripExpense", true);
			}

		} catch (Exception e) {
			model.put("alreadyTripExpense", true);
			LOGGER.error("TripExpense Service Type Page:", e);
			return new ModelAndView("redirect:/addTripExpense/1.in", model);
		}
		return new ModelAndView("redirect:/addTripExpense/1.in", model);
	}

	// update of vehicle status
	@RequestMapping(value = "/uploadTripExpense", method = RequestMethod.POST)
	public ModelAndView uploadTripExpense(@ModelAttribute("command") TripExpense TripExpense,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		TripExpense			expense			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripExpense status = VehStBL.prepareUpdate(TripExpense);
			status.setLastModifiedById(userDetails.getId());
			status.setCompanyId(userDetails.getCompany_id());
			
			expense		= tripExpenseService.getTripExpense(TripExpense.getExpenseID(), userDetails.getCompany_id());
			
			if(!expense.getExpenseName().equalsIgnoreCase(status.getExpenseName().trim())) {
				List<TripExpense> validate = tripExpenseService.ValidateTripExpense(status.getExpenseName(), userDetails.getCompany_id());
				if(validate != null && !validate.isEmpty()) {
					model.put("alreadyTripExpense", true);
					return new ModelAndView("redirect:/addTripExpense/1.in", model);
				}
			}
			
			TripExpenseHistory		tripExpenseHistory		= VehStBL.prepareHistoryModel(expense);
			
			tripExpenseService.updateTripExpense(status);
			tripExpenseHistoryService.addTripExpenseHistory(tripExpenseHistory);
			
			model.put("uploadTripExpense", true);
		} catch (Exception e) {
			model.put("alreadyTripExpense", true);
			LOGGER.error("TripExpense Service Type Page:", e);
			return new ModelAndView("redirect:/addTripExpense/1.in", model);
		}
		return new ModelAndView("redirect:/addTripExpense/1.in", model);
	}

	@RequestMapping(value = "/editTripExpense", method = RequestMethod.GET)
	public ModelAndView editTripExpense(@ModelAttribute("command") TripExpense TripExpenseBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	        configuration				    = null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			model.put("configuration", configuration);
			model.put("TripExpense",
					VehStBL.prepareTripExpenseBean(tripExpenseService.getTripExpense(TripExpenseBean.getExpenseID(), userDetails.getCompany_id())));
		} catch (Exception e) {
			LOGGER.error("TripExpense Service Type Page:", e);
		}
		return new ModelAndView("editTripExpense", model);
	}

	@RequestMapping(value = "/deleteTripExpense", method = RequestMethod.GET)
	public ModelAndView deleteTripExpense(@ModelAttribute("command") TripExpense TripExpenseBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails		userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripExpenseHistory		tripExpenseHistory		= VehStBL.prepareHistoryModel(tripExpenseService.getTripExpense(TripExpenseBean.getExpenseID(), userDetails.getCompany_id()));
			
			tripExpenseService.deleteTripExpense(TripExpenseBean.getExpenseID());
			tripExpenseHistoryService.addTripExpenseHistory(tripExpenseHistory);
			
			model.put("deleteTripExpense", true);
		} catch (Exception e) {
			model.put("alreadyTripExpense", true);
			LOGGER.error("TripExpense Service Type Page:", e);
			return new ModelAndView("redirect:/addTripExpense/1.in", model);
		}
		return new ModelAndView("redirect:/addTripExpense/1.in", model);
	}

	/*****************************************************************************
	 * Testing Loading datatable using Gson
	 ******************************************************************************/

	@RequestMapping(value = "/getTripExpenseList", method = RequestMethod.GET)
	public void getTripExpenseList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustomUserDetails userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<TripExpense> TripExpenseList = new ArrayList<TripExpense>();

		for (TripExpense Expense : tripExpenseService.listTripExpense(userDetails.getCompany_id())) {

			TripExpense bean = new TripExpense();
			bean.setExpenseID(Expense.getExpenseID());
			bean.setExpenseName(Expense.getExpenseName());

			TripExpenseList.add(bean);
		}

		Gson gson = new Gson();

		// System.out.println("jsonApproval List = " +
		// gson.toJson(ApprovalList));

		response.getWriter().write(gson.toJson(TripExpenseList));
	}
	
	/** This below Search Expense Type Search Details */
	@RequestMapping(value = "/getSearchExpenseName", method = RequestMethod.POST)
	public void getSearchExpenseName(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails		 = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<TripExpense> Trip = new ArrayList<TripExpense>();
			List<TripExpense> Expense = tripExpenseService.Search_ExpenseName_select(term, userDetails.getCompany_id());
			if (Expense != null && !Expense.isEmpty()) {
				for (TripExpense add : Expense) {

					TripExpense wadd = new TripExpense();
					wadd.setExpenseID(add.getExpenseID());
					wadd.setExpenseName(add.getExpenseName());

					Trip.add(wadd);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(Trip));
		} catch (Exception e) {
			throw e;
		}
	
	}

}
