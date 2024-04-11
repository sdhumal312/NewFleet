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
import org.fleetopgroup.persistence.bl.TripIncomeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.TripIncome;
import org.fleetopgroup.persistence.model.TripIncomeHistory;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ITripIncomeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.ITripIncomeService;
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
public class TripIncomeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ITripIncomeService 						tripIncomeService;

	@Autowired
	private ITripIncomeHistoryService 				tripIncomeHistoryService;
	
	@Autowired 
	private ICompanyConfigurationService			companyConfigurationService;

	TripIncomeBL tripIncomeBL = new TripIncomeBL();

	@RequestMapping(value = "/addTripIncome/{incomeType}/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView addTripIncome(@PathVariable Integer incomeType, @PathVariable Integer pageNumber, Model model, final HttpServletRequest request) {
		
		CustomUserDetails 					userDetails 			= null;
		HashMap<String, Object>  			configuration 			= null;
		try {
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			Page<TripIncome> page 	= tripIncomeService.getDeployment_Page_TripIncome(incomeType,pageNumber, userDetails);
			int current 			= page.getNumber() + 1;
			int begin 				= Math.max(1, current - 5);
			int end 				= Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("configuration", configuration);
			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("vehicleTypeCount", page.getTotalElements());
			List<TripIncome> pageList = (tripIncomeService.GET_list_Of_TripIncome(incomeType, pageNumber, userDetails));

			model.addAttribute("TripIncome", pageList);
			model.addAttribute("IncomeType", incomeType);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("trip addTripRoute Page:", e);
			e.printStackTrace();
		}
	
		return new ModelAndView("addTripIncome");
	}
	
	
	@RequestMapping(value = "/addTripRouteIncome", method = RequestMethod.GET)
	public ModelAndView addTripRouteIncome(@ModelAttribute("command") TripIncome TripIncomeBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			model.put("TripIncome", tripIncomeBL.prepareListofBean(tripIncomeService.listTrip_ROUTE_Income(userDetails.getCompany_id())));
			model.put("SelectStatus", 2);
		} catch (Exception e) {
			LOGGER.error("TripIncome Service Type Page:", e);
		}
		return new ModelAndView("addTripIncome", model);
	}
	
	@RequestMapping(value = "/addTripCashbookIncome", method = RequestMethod.GET)
	public ModelAndView addTripCashbookIncome(@ModelAttribute("command") TripIncome TripIncomeBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			model.put("TripIncome", tripIncomeBL.prepareListofBean(tripIncomeService.listTrip_CASHBOOK_Income(userDetails.getCompany_id())));
			model.put("SelectStatus", 3);
		} catch (Exception e) {
			LOGGER.error("TripIncome Service Type Page:", e);
		}
		return new ModelAndView("addTripIncome", model);
	}

	/* save the vehicle Status */
	@RequestMapping(value = "/saveTripIncome", method = RequestMethod.POST)
	public ModelAndView saveTripIncome(@ModelAttribute("command") TripIncome TripIncome,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripIncome status = tripIncomeBL.prepareModel(TripIncome);
			TripIncome validate = tripIncomeService.validateTripIncome(TripIncome.getIncomeName(), userDetails.getCompany_id());
			if (validate == null) {
				status.setCreatedById(userDetails.getId());
				status.setLastModifiedById(userDetails.getId());
				status.setCompanyId(userDetails.getCompany_id());
				
				tripIncomeService.addTripIncome(status);
				model.put("saveTripIncome", true);
			} else {
				model.put("alreadyTripIncome", true);
				return new ModelAndView("redirect:/addTripIncome/1/1.in", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("TripIncome Service Type Page:", e);
			model.put("alreadyTripIncome", true);
			return new ModelAndView("redirect:/addTripIncome/1/1.in", model);
		}
		return new ModelAndView("redirect:/addTripIncome/1/1.in", model);
	}

	// update of vehicle status
	@RequestMapping(value = "/uploadTripIncome", method = RequestMethod.POST)
	public ModelAndView uploadTripIncome(@ModelAttribute("command") TripIncome tripIncome,
			final HttpServletRequest request) {
		Map<String, Object> model 			= new HashMap<String, Object>();
		TripIncome			income			= null;
		CustomUserDetails	userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TripIncome status = tripIncomeBL.prepareUpdate(tripIncome);
			income	= tripIncomeService.getTripIncome(status.getIncomeID(), userDetails.getCompany_id());
			if(!income.getIncomeName().equalsIgnoreCase(status.getIncomeName().trim())) {
				TripIncome validate = tripIncomeService.validateTripIncome(status.getIncomeName(), userDetails.getCompany_id());
				if(validate != null) {
					model.put("alreadyTripIncome", true);
					return new ModelAndView("redirect:/addTripIncome/1/1.in", model);
				}
			}
			status.setLastModifiedById(userDetails.getId());
			status.setCompanyId(userDetails.getCompany_id());
			
			TripIncomeHistory		tripIncomeHistory		= tripIncomeBL.prepareHistoryModel(income);
			
			tripIncomeService.updateTripIncome(status);
			tripIncomeHistoryService.addTripIncomeHistory(tripIncomeHistory);
			
			model.put("uploadTripIncome", true);
		} catch (Exception e) {
			LOGGER.error("TripIncome Service Type Page:", e);
			model.put("alreadyTripIncome", true);
			return new ModelAndView("redirect:/addTripIncome/1/1.in", model);
		}
		return new ModelAndView("redirect:/addTripIncome/1/1.in", model);
	}

	@RequestMapping(value = "/editTripIncome", method = RequestMethod.GET)
	public ModelAndView editTripIncome(@ModelAttribute("command") TripIncome TripIncomeBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("TripIncome",
					tripIncomeBL.prepareTripIncomeBean(tripIncomeService.getTripIncome(TripIncomeBean.getIncomeID(), userDetails.getCompany_id())));
		} catch (Exception e) {
			LOGGER.error("TripIncome Service Type Page:", e);
		}
		return new ModelAndView("editTripIncome", model);
	}

	@RequestMapping(value = "/deleteTripIncome", method = RequestMethod.GET)
	public ModelAndView deleteTripIncome(@ModelAttribute("command") TripIncome TripIncomeBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails		userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			TripIncomeHistory		tripIncomeHistory		= tripIncomeBL.prepareHistoryModel(tripIncomeService.getTripIncome(TripIncomeBean.getIncomeID(), userDetails.getCompany_id()));
			
			tripIncomeService.deleteTripIncome(TripIncomeBean.getIncomeID(), userDetails.getCompany_id());
			tripIncomeHistoryService.addTripIncomeHistory(tripIncomeHistory);
			
			model.put("deleteTripIncome", true);
		} catch (Exception e) {
			LOGGER.error("TripIncome Service Type Page:", e);
			model.put("alreadyTripIncome", true);
			return new ModelAndView("redirect:/addTripIncome/1/1.in", model);
		}
		return new ModelAndView("redirect:/addTripIncome/1/1.in", model);
	}

	/*****************************************************************************
	 * Testing Loading datatable using Gson
	 ******************************************************************************/

	@RequestMapping(value = "/getTripIncomeList", method = RequestMethod.GET)
	public void getTripIncomeList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustomUserDetails		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<TripIncome> TripIncomeList = new ArrayList<TripIncome>();

		for (TripIncome Income : tripIncomeService.listTripIncome(userDetails.getCompany_id())) {

			TripIncome bean = new TripIncome();
			bean.setIncomeID(Income.getIncomeID());
			bean.setIncomeName(Income.getIncomeName());

			TripIncomeList.add(bean);
		}

		Gson gson = new Gson();

		// System.out.println("jsonApproval List = " +
		// gson.toJson(ApprovalList));

		response.getWriter().write(gson.toJson(TripIncomeList));
	}
	
	@RequestMapping(value = "/tripIncomeListRoute", method = RequestMethod.GET)
	public void tripIncomeList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<TripIncome> addresses = new ArrayList<TripIncome>();
		List<TripIncome> income = tripIncomeService.listOnlyIncomeName_FixedIncome(userDetails.getCompany_id());
		if (income != null && !income.isEmpty()) {
			for (TripIncome add : income) {
				TripIncome wadd = new TripIncome();
				wadd.setIncomeID(add.getIncomeID());
				wadd.setIncomeName(add.getIncomeName());
				// System.out.println(add.getVid());
				addresses.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(addresses));
	}
	
	@RequestMapping(value = "/getTripIncomeByName", method = RequestMethod.POST)
	public void getTripIncomeByName(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<TripIncome> addresses = new ArrayList<TripIncome>();
			TripIncome wadd = null;
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<TripIncome> DateVehicle = tripIncomeService.getTripIncomeByName(term, userDetails.getCompany_id());
			if (DateVehicle != null && !DateVehicle.isEmpty()) {
				for (TripIncome add : DateVehicle) {
					wadd = new TripIncome();

					wadd.setIncomeID(add.getIncomeID());
					wadd.setIncomeName(add.getIncomeName());

					addresses.add(wadd);
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(addresses));
		} catch (Exception e) {
			throw e;
		}
	}

}