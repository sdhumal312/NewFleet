package org.fleetopgroup.web.controller;

import java.util.ArrayList;
import java.util.Collection;
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
import org.fleetopgroup.constant.TripRouteFixedType;
import org.fleetopgroup.constant.TripSheetConfigurationConstant;
import org.fleetopgroup.persistence.bl.TripRouteBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripRouteDto;
import org.fleetopgroup.persistence.dto.TripRoutefixedIncomeDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.TripRoute;
import org.fleetopgroup.persistence.model.TripRouteHistory;
import org.fleetopgroup.persistence.model.TripRouteSequenceCounter;
import org.fleetopgroup.persistence.model.TripRoutefixedExpense;
import org.fleetopgroup.persistence.model.TripRoutefixedIncome;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IRouteFixedAllowanceService;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteExpenseRangeService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteHistoryService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteSequenceService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class TripRouteController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ITripRouteService TripRouteService;

	@Autowired
	private ITripRouteHistoryService tripRouteHistoryService;

	@Autowired
	private IUserProfileService userProfileService;

	@Autowired
	private ITripRouteSequenceService tripRouteSequenceService;

	@Autowired
	private IVehicleGroupService vehicleGroupService;
	
	@Autowired
	private ICompanyConfigurationService	companyConfigurationService;
	
	@Autowired 	
	ITripRouteExpenseRangeService   	tripRouteExpenseRangeService;
	
	@Autowired	private IDriverJobTypeService		driverJobTypeService;
	@Autowired	private ITripExpenseService			tripExpenseService;
	@Autowired	private	IRouteFixedAllowanceService	routeFixedAllowanceService;
	
	TripRouteBL VehStBL = new TripRouteBL();

	@RequestMapping(value = "/searchRoute", method = RequestMethod.POST)
	public ModelAndView searchRoute(@RequestParam("Route") final String Search, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("TripRoute",
					VehStBL.prepareListofBean(TripRouteService.SearchOnlyRouteNAME(Search, userDetails)));

		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("addTripRoute", model);
	}

	/* show main page of Trip */
	@RequestMapping(value = "/newTripRoute/{pageNumber}", method = RequestMethod.GET)
	public String addTripRoute(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails userDetails 			= null;
		HashMap<String, Object>  configuration	= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			Page<TripRoute> page = TripRouteService.getDeployment_Page_TripRoute(pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("TotalRoute", page.getTotalElements());
			List<TripRoute> pageList = VehStBL
					.prepareListofBean(TripRouteService.GET_list_Of_TripRoute(pageNumber, userDetails));

			model.addAttribute("TripRoute", pageList);
			model.addAttribute("subRouteTypeNeeded", configuration.getOrDefault(ICompanyConfigurationService.SUB_ROUTE_TYPE_NEEDED, false));
			model.addAttribute("companyId", userDetails.getCompany_id());
			model.addAttribute("userId", userDetails.getId());

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("trip addTripRoute Page:", e);
			e.printStackTrace();
		}finally {
			userDetails 	= null;
			configuration	= null;
			
		}
		return "addTripRoute";
	}

	// create Trip route
	@RequestMapping(value = "/createTripRoute", method = RequestMethod.GET) 
	public ModelAndView createVehicleType(@ModelAttribute("command") TripRoute TripRouteBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>  configuration	= null;
		boolean  expenseOutOfRange = false;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();

			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			HashMap<String, Object> routeConfig		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_ROUTE_CONFIGURATION_CONFIG);
		
			if(permission.contains(new SimpleGrantedAuthority("ADD_TRIP_ROUTE_EXPENSE_OUT_OF_RANGE"))) {
				expenseOutOfRange = true;
			}
			model.put("routeConfig", routeConfig);
			model.put("vehicleGroup", vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id()));
			model.put("subRouteTypeNeeded", configuration.getOrDefault(ICompanyConfigurationService.SUB_ROUTE_TYPE_NEEDED, false));
			model.put("routeList", TripRouteService.getTripRouteListByType(Integer.parseInt(TripRouteDto.ROUTE_TYPE_MAIN_ROUTE+""), userDetails));
			model.put("expenseOutOfRange", expenseOutOfRange);
			// model.put("TripRoute",
			// VehStBL.prepareListofBean(TripRouteService.listTripRoute()));
		} catch (Exception e) {
			LOGGER.error("Trip RouteService Type Page:", e);
		}
		return new ModelAndView("createTripRoute", model);
	}

	/* save the Trip Route */
	@RequestMapping(value = "/saveTripRoute", method = RequestMethod.POST)  
	public ModelAndView saveTripRoute(@ModelAttribute("command") TripRouteDto TripRoute,
			TripRoutefixedExpense TripSheetExpense, @RequestParam("expenseName") final String[] expenseName,
			@RequestParam("Amount") final String[] expenseAmount,
			@RequestParam("expenseRefence") final String[] expenseRefence,
			@RequestParam("incomeName") final String[] incomeName,
			@RequestParam("incomeAmount") final String[] incomeAmount,
			@RequestParam("subRouteTypeNeeded") final Boolean subRouteTypeNeeded,
			@RequestParam("maxAmount") final String[] maxAmount,
			@RequestParam("incomeRefence") final String[] incomeRefence, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Integer routeId = null;
		TripRoute route = null;
		CustomUserDetails userDetails = null;
		TripRouteSequenceCounter sequenceCounter = null;
		TripRoute tripRoute = null;
		ValueObject object = null;
		HashMap<String, Object>    	tripRouteConfig				= null;
		boolean expenseMaxtLimitConfig = false;
		boolean  expenseOutOfRange = false;
		try {
			object = new ValueObject();
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			if(permission.contains(new SimpleGrantedAuthority("ADD_TRIP_ROUTE_EXPENSE_OUT_OF_RANGE"))) {
				expenseOutOfRange = true;
			}
			
			tripRouteConfig	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIP_ROUTE_CONFIGURATION_CONFIG);
			expenseMaxtLimitConfig	= (boolean) tripRouteConfig.getOrDefault(TripSheetConfigurationConstant.TRIP_ROUTE_EXPENSE_MAX_LIMIT, false);
			String name = userDetails.getEmail();

			UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(name);
			Integer expensePlace = Orderingname.getBranch_id();

			TripRoute status = VehStBL.prepareModel(TripRoute, subRouteTypeNeeded);
			if(!subRouteTypeNeeded) 
			status.setRouteType(Integer.parseInt(TripRouteDto.ROUTE_TYPE_MAIN_ROUTE+""));
			status.setCompanyId(userDetails.getCompany_id());
			status.setCreatedById(userDetails.getId());
			status.setLastModifiedById(userDetails.getId());

			route = TripRouteService.validateTripRoute(status.getRouteName(), userDetails.getCompany_id());
			if (route != null) {
				model.put("alreadyTripRoute", true);
				return new ModelAndView("redirect:/createTripRoute", model);
			}
			sequenceCounter = tripRouteSequenceService.findNextSequenceNumber(userDetails.getCompany_id());
			if (sequenceCounter == null) {
				model.put("sequenceNotFound", true);
				return new ModelAndView("redirect:/createTripRoute", model);
			}
			status.setRouteNumber(sequenceCounter.getNextVal());
			tripRoute = TripRouteService.addTripRoute(status);

			// tripRouteSequenceService.updateNextSequenceNumber(sequenceCounter.getNextVal()
			// + 1, sequenceCounter.getSequence_Id());
			model.put("saveTripRoute", true);
			routeId = status.getRouteID();
			if (expenseName != null && expenseName.length > 0) {
				for (int i = 0; i < expenseName.length; i++) {
					if (!(expenseName[i].equalsIgnoreCase("0"))) {

						TripRoutefixedExpense TripExpense = new TripRoutefixedExpense();

						TripExpense.setExpenseId(Integer.parseInt("" + expenseName[i]));
						TripExpense.setExpenseAmount(Double.parseDouble("" + expenseAmount[i]));
						TripExpense.setTriproute(status);
						TripExpense.setCompanyId(userDetails.getCompany_id());
						TripExpense.setExpensePlaceId(expensePlace);
						TripExpense.setExpenseRefence(expenseRefence[i] + "");
						TripExpense.setFixedTypeId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_FIXED);

						List<TripRoutefixedExpense> validate = TripRouteService.ValidateTripRouteFixedExpene(
								Integer.parseInt("" + expenseName[i]), status.getRouteID(),
								userDetails.getCompany_id());

						if (validate != null && !validate.isEmpty()) {
							model.put("alreadyRouteExpense", true);
							return new ModelAndView("redirect:/createTripRoute", model);
						} else {
							TripRouteService.addTripRouteFixedExpene(TripExpense);

						}
					}
				}
			}
			if (incomeName != null && incomeName.length > 0) {

				for (int i = 0; i < incomeName.length; i++) {
					if (!(incomeName[i].equalsIgnoreCase("0"))) {

						TripRoutefixedIncome TripIncome = new TripRoutefixedIncome();

						TripIncome.setIncomeId(Integer.parseInt("" + incomeName[i]));
						TripIncome.setIncomeAmount(Double.parseDouble("" + incomeAmount[i]));
						TripIncome.setIncomePlaceId(expensePlace);
						TripIncome.setIncomeRefence("" + incomeRefence[i]);
						TripIncome.setTriproute(status);
						TripIncome.setCompanyId(userDetails.getCompany_id());
						TripIncome.setIncomeFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_FIXED);

						List<TripRoutefixedIncome> validate = TripRouteService.ValidateTripRouteFixedIncome(
								Integer.parseInt("" + incomeName[i]), status.getRouteID(), userDetails.getCompany_id());
						if (validate != null && !validate.isEmpty()) {
							model.put("alreadyRouteExpense", true);

						} else {
							TripRouteService.addTripRouteFixedIncome(TripIncome);

						}
					}

				}
			}
			
			
			if(expenseMaxtLimitConfig == true && expenseOutOfRange == false ) {
				ArrayList<ValueObject> arr = new ArrayList<>();
				
				for(int i =0 ; i< expenseName.length; i++){
					ValueObject list = new ValueObject();
					list.put("routeId", tripRoute.getRouteID());
					list.put("expenseId", expenseName[i]);
					if(maxAmount != null && maxAmount.length > 0) {
						list.put("expenseMaxLimt", maxAmount[i]);
					}else {
						list.put("expenseMaxLimt", 0);
					}
					arr.add(list);
				}
				object.put("finalList", arr);
			
				tripRouteExpenseRangeService.saveTripRouteExpenseRange(object);
			}

		} catch (Exception e) {
			model.put("alreadyTripRoute", true);
			e.printStackTrace();
			return new ModelAndView("redirect:/createTripRoute", model);
		} finally {
			userDetails = null;
			sequenceCounter = null;
		}

		return new ModelAndView("redirect:/showTripRoute.in?routeID=" + routeId + "", model);
	}

	// Show Trip Sheet
	@RequestMapping(value = "/showTripRoute", method = RequestMethod.GET)
	public ModelAndView showTripRoute(@ModelAttribute("command") TripRoute TripRouteBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		boolean  expenseMaxtLimitConfig = false;
		boolean  expenseOutOfRange = false;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			
			if(permission.contains(new SimpleGrantedAuthority("ADD_TRIP_ROUTE_EXPENSE_OUT_OF_RANGE"))) {
				expenseOutOfRange = true;
			}
			
			HashMap<String, Object> routeConfig	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_ROUTE_CONFIGURATION_CONFIG);
			expenseMaxtLimitConfig	= (boolean) routeConfig.getOrDefault(TripSheetConfigurationConstant.TRIP_ROUTE_EXPENSE_MAX_LIMIT, false);
			
			
			model.put("TripRoute",TripRouteService.getTripRouteDetails(TripRouteBean.getRouteID(), userDetails.getCompany_id()));
			model.put("TripRouteExpense",
					TripRouteService.listTripRouteFixedExpene(TripRouteBean.getRouteID(), userDetails.getCompany_id()));
			List<TripRoutefixedIncomeDto> routeDto=TripRouteService.listTripRouteFixedIncome(TripRouteBean.getRouteID(), userDetails.getCompany_id());
			model.put("TripRouteIncome",routeDto);
			model.put("driverJobType",driverJobTypeService.listDriverJobTypeByCompanyId(userDetails.getCompany_id()));
			model.put("tripExpense",tripExpenseService.listTripExpense(userDetails.getCompany_id()));
			model.put("routeConfig",routeConfig);
			model.put("allowanceList", routeFixedAllowanceService.getRouteFixedAllowanceList(TripRouteBean.getRouteID()));
			model.put("expenseMaxtLimitConfig", expenseMaxtLimitConfig);
			model.put("expenseOutOfRange", expenseOutOfRange);
			model.put("companyId", userDetails.getCompany_id());
			model.put("userId", userDetails.getId());

		} catch (Exception e) {
			System.err.println("Exception : "+e);
			LOGGER.error("Trip RouteService Type Page:", e);
		}
		return new ModelAndView("showTripRoute", model);
	}

	/* save the vehicle Status */
	@RequestMapping(value = "/updateTripRouteExpense", method = RequestMethod.POST)
	public ModelAndView updateTripRouteExpense(@ModelAttribute("command") TripRoute TripRoute,
			TripRoutefixedExpense TripSheetExpense, @RequestParam("expenseName") final String[] expenseName,
			@RequestParam("Amount") final String[] expenseAmount,
			@RequestParam("expenseRefence") final String[] expenseRefence, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String name = userDetails.getEmail();

			UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(name);
			Integer expensePlace = Orderingname.getBranch_id();

			TripRoute status = new TripRoute();
			status.setRouteID(TripRoute.getRouteID());

			for (int i = 0; i < expenseName.length; i++) {
				if (!(expenseName[i].equalsIgnoreCase("0"))) {

					TripRoutefixedExpense TripExpense = new TripRoutefixedExpense();

					TripExpense.setExpenseId(Integer.parseInt("" + expenseName[i]));
					TripExpense.setExpenseAmount(Double.parseDouble("" + expenseAmount[i]));
					TripExpense.setTriproute(status);
					TripExpense.setCompanyId(userDetails.getCompany_id());
					TripExpense.setExpensePlaceId(expensePlace);
					TripExpense.setExpenseRefence(expenseRefence[i] + "");
					TripExpense.setFixedTypeId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_FIXED);
					List<TripRoutefixedExpense> validate = TripRouteService.ValidateTripRouteFixedExpene(
							Integer.parseInt("" + expenseName[i]), TripRoute.getRouteID(), userDetails.getCompany_id());
					if (validate != null && !validate.isEmpty()) {
						model.put("alreadyRouteExpense", true);

					} else {
						TripRouteService.addTripRouteFixedExpene(TripExpense);
						model.put("updateRouteExpense", true);
					}

				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("redirect:/showTripRoute.in?routeID=" + TripRoute.getRouteID() + "", model);
	}

	@RequestMapping(value = "/updateTripRouteIncome", method = RequestMethod.POST)
	public ModelAndView updateTripRouteIncome(@ModelAttribute("command") TripRoute TripRoute,
			@RequestParam("incomeName") final String[] incomeName,
			@RequestParam("incomeAmount") final String[] incomeAmount,
			@RequestParam("incomeRefence") final String[] incomeRefence, final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		CustomUserDetails userDetails = null;
		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName();

			UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(name);
			Integer expensePlace = Orderingname.getBranch_id();

			TripRoute status = new TripRoute();
			status.setRouteID(TripRoute.getRouteID());

			if (incomeName != null && incomeName.length != 0) {

				for (int i = 0; i < incomeName.length; i++) {

					if (!(incomeName[i].equalsIgnoreCase("0"))) {

						TripRoutefixedIncome TripIncome = new TripRoutefixedIncome();
						
						TripIncome.setIncomeId(Integer.parseInt("" + incomeName[i]));
						TripIncome.setIncomeAmount(Double.parseDouble("" + incomeAmount[i]));
						TripIncome.setIncomePlaceId(expensePlace);
						TripIncome.setIncomeRefence("" + incomeRefence[i]);
						TripIncome.setTriproute(status);
						TripIncome.setCompanyId(userDetails.getCompany_id());
						TripIncome.setIncomeFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_FIXED);
						//TripIncome.setRoutefixedID(TripRoute.getRouteID());
						List<TripRoutefixedIncome> validate = TripRouteService.ValidateTripRouteFixedIncome(
								Integer.parseInt("" + incomeName[i]), status.getRouteID(), userDetails.getCompany_id());
						if (validate != null && !validate.isEmpty()) {
							model.put("alreadyRouteExpense", true);

						} else {
					
							TripRouteService.addTripRouteFixedIncome(TripIncome);

						}
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("redirect:/showTripRoute.in?routeID=" + TripRoute.getRouteID() + "", model);
	}

	// update of vehicle status
	@RequestMapping(value = "/uploadTripRoute", method = RequestMethod.POST)
	public ModelAndView uploadTripRoute(@ModelAttribute("command") TripRouteDto TripRoute,
			@RequestParam("subRouteTypeNeeded") final Boolean subRouteTypeNeeded,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		TripRoute route = null;
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			route = TripRouteService.getTripRoute(TripRoute.getRouteID());
			if (!route.getRouteName().trim().equalsIgnoreCase(TripRoute.getRouteName().trim())) {
				TripRoute validate_type = TripRouteService.validateTripRoute(TripRoute.getRouteName(),
						userDetails.getCompany_id());
				if (validate_type != null) {
					model.put("alreadyTripRoute", true);
					return new ModelAndView("redirect:/showTripRoute.in?routeID=" + TripRoute.getRouteID() + "", model);
				}
			}
			TripRoute status = VehStBL.prepareUpdate(TripRoute, subRouteTypeNeeded);
			if(!subRouteTypeNeeded) 
				status.setRouteType(Integer.parseInt(TripRouteDto.ROUTE_TYPE_MAIN_ROUTE+""));
			
			status.setCompanyId(userDetails.getCompany_id());
			status.setLastModifiedById(userDetails.getId());
			status.setCreatedById(route.getCreatedById());
			status.setCreated(route.getCreated());
			
			TripRouteHistory		tripRouteHistory	= VehStBL.prepareHistoryModel(route);
			
			TripRouteService.updateTripRoute(status);
			tripRouteHistoryService.addTripRouteHistory(tripRouteHistory);
			
			model.put("updateTripRoute", true);
		} catch (Exception e) {
			System.err.println("exception : "+e);
			model.put("notUpdateTripRoute", true);
			LOGGER.error("Trip RouteService Type Page:", e);

		}
		return new ModelAndView("redirect:/showTripRoute.in?routeID=" + TripRoute.getRouteID() + "", model);
	}

	@RequestMapping(value = "/editTripRoute", method = RequestMethod.GET)
	public ModelAndView editTripRoute(@ModelAttribute("command") TripRoute TripRouteBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			HashMap<String, Object> 	configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			HashMap<String, Object> 	routeConfig		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_ROUTE_CONFIGURATION_CONFIG);
			model.put("TripRoute",TripRouteService.getTripRouteDetails(TripRouteBean.getRouteID(), userDetails.getCompany_id()));
			model.put("vehicleGroup", vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id()));
			model.put("subRouteTypeNeeded", configuration.getOrDefault(ICompanyConfigurationService.SUB_ROUTE_TYPE_NEEDED, false));
			model.put("routeList", TripRouteService.getTripRouteListByType(Integer.parseInt(TripRouteDto.ROUTE_TYPE_MAIN_ROUTE+""), userDetails));
			model.put("routeConfig", routeConfig);
		} catch (Exception e) {
			LOGGER.error("Trip RouteService Type Page:", e);
		}
		return new ModelAndView("editTripRoute", model);
	}

	@RequestMapping(value = "/deleteTripRoute", method = RequestMethod.GET)
	public ModelAndView deleteTripRoute(@ModelAttribute("command") TripRoute TripRouteBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			TripRoute status = TripRouteService.getTripRoute(TripRouteBean.getRouteID());
			
			TripRouteHistory		tripRouteHistory	= VehStBL.prepareHistoryModel(status);
			
			TripRouteService.deleteTripRoute(status);
			tripRouteHistoryService.addTripRouteHistory(tripRouteHistory);
			
			model.put("deleteTripRoute", true);
		} catch (Exception e) {
			model.put("notdeleteTripRoute", true);
		}
		return new ModelAndView("redirect:/newTripRoute/1.in", model);
	}

	@RequestMapping(value = "/removeTripRouteExpense", method = RequestMethod.GET)
	public ModelAndView removeTripRouteExpense(@ModelAttribute("command") TripRoutefixedExpense TripRoutefixedExpense,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		Integer RouteID = null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			TripRoutefixedExpense statusfixedExpense = TripRouteService
					.getTripRoutefixedExpense(TripRoutefixedExpense.getRoutefixedID(), userDetails.getCompany_id());

			RouteID = statusfixedExpense.getTriproute().getRouteID();

			TripRouteService.deleteTripRoutefixedExpense(TripRoutefixedExpense.getRoutefixedID(),
					userDetails.getCompany_id());
			model.put("deleteTripRoute", true);
		} catch (Exception e) {
			model.put("notDeleteTripRoute", true);
		}
		return new ModelAndView("redirect:/showTripRoute.in?routeID=" + RouteID + "", model);
	}

	@RequestMapping(value = "/removeTripRouteIncome", method = RequestMethod.GET)
	public ModelAndView removeTripRouteIncome(@RequestParam("routefixedID") final Integer routefixedID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		Integer RouteID = null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			TripRoutefixedIncome statusfixedExpense = TripRouteService.getTripRoutefixedIncome(routefixedID,
					userDetails.getCompany_id());

			RouteID = statusfixedExpense.getTriproute().getRouteID();

			TripRouteService.deleteTripRoutefixedIncome(routefixedID, userDetails.getCompany_id());
			model.put("deleteTripRoute", true);
		} catch (Exception e) {
			model.put("notDeleteTripRoute", true);
		}
		return new ModelAndView("redirect:/showTripRoute.in?routeID=" + RouteID + "", model);
	}

	/*****************************************************************************
	 * Testing Loading datatable using Gson
	 ******************************************************************************/

	// select Ajax Drop down value
	@RequestMapping(value = "/getVehicleTripRoute", method = RequestMethod.GET)
	public void getVehicleTripRoute(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<TripRoute> group = new ArrayList<TripRoute>();
			for (TripRoute add : TripRouteService.listTripRoute(userDetails)) {
				TripRoute wadd = new TripRoute();

				wadd.setRouteName(add.getRouteName());
				wadd.setRouteID(add.getRouteID());
				// System.out.println(add.getVid());
				group.add(wadd);
			}

			Gson gson = new Gson();
			// System.out.println("jsonStudents = " + gson.toJson(addresses));
			response.getWriter().write(gson.toJson(group));

		} catch (Exception e) {
			throw e;
		}

	}
	@RequestMapping(value = "/getRouteByVehicleGroupId", method = RequestMethod.GET)
	public void getRouteByVehicleGroupId(@RequestParam(value = "vehicleGroupId", required = true) Long vehicleGroupId,
			Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		

		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<TripRouteDto> Route = new ArrayList<TripRouteDto>();
		List<TripRoute> routetype = TripRouteService.getRouteListByVehicleGroupId(vehicleGroupId, userDetails.getCompany_id());
		if (routetype != null && !routetype.isEmpty()) {
			for (TripRoute add : routetype) {
				TripRouteDto wadd = new TripRouteDto();
				wadd.setRouteID(add.getRouteID());
				wadd.setRouteName(add.getRouteName());
				
				Route.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(Route));
	}

	@RequestMapping(value = "/getTripRouteSubList", method = RequestMethod.POST)
	public void getTripRouteSubList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<TripRoute> TripRouteList = new ArrayList<TripRoute>();
			List<TripRoute> TripRoute = TripRouteService.SearchOnlyRoute_SUB_ROUTE_NAME(term, userDetails);
			if (TripRoute != null && !TripRoute.isEmpty()) {
				for (TripRoute Route : TripRoute) {

					TripRoute Dto = new TripRoute();
					Dto.setRouteID(Route.getRouteID());
					Dto.setRouteName(Route.getRouteName());
					Dto.setRouteNo(Route.getRouteNo());

					TripRouteList.add(Dto);
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(TripRouteList));
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/getTripRouteSubListById", method = RequestMethod.GET)
	public void getTripRouteSubListById(Map<String, Object> map, @RequestParam("vehicleGroup") final Integer routeID,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<TripRoute> TripRouteList = new ArrayList<TripRoute>();
			List<TripRoute> TripRoute = TripRouteService.SearchOnlyRoute_SUB_ROUTE_BY_RouteId(routeID, userDetails);
			if (TripRoute != null && !TripRoute.isEmpty()) {
				for (TripRoute Route : TripRoute) {

					TripRoute Dto = new TripRoute();
					Dto.setRouteID(Route.getRouteID());
					Dto.setRouteName(Route.getRouteName());
					Dto.setRouteNo(Route.getRouteNo());

					TripRouteList.add(Dto);
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(TripRouteList));
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/getTripRouteListById", method = RequestMethod.GET)
	public void getTripRouteListById(Map<String, Object> map, @RequestParam("vehicleGroup") final long vehicleGroup,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<TripRoute> TripRouteList = new ArrayList<TripRoute>();
			List<TripRoute> TripRoute = TripRouteService.SearchOnlyRoute_MAIN_ROUTE_BY_DEPOTID(vehicleGroup, userDetails);
			if (TripRoute != null && !TripRoute.isEmpty()) {
				for (TripRoute Route : TripRoute) {

					TripRoute Dto = new TripRoute();
					Dto.setRouteID(Route.getRouteID());
					Dto.setRouteName(Route.getRouteName());
					Dto.setRouteNo(Route.getRouteNo());

					TripRouteList.add(Dto);
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(TripRouteList));
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/getTripRouteSerachList", method = RequestMethod.GET)
	public void getTripRouteSerachList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<TripRoute> TripRouteList = new ArrayList<TripRoute>();
			List<TripRoute> TripRoute = TripRouteService.getTripRouteSerachList(term, userDetails);
			if (TripRoute != null && !TripRoute.isEmpty()) {
				for (TripRoute Route : TripRoute) {

					TripRoute Dto = new TripRoute();
					Dto.setRouteID(Route.getRouteID());
					Dto.setRouteName(Route.getRouteName());
					Dto.setRouteNo(Route.getRouteNo());

					TripRouteList.add(Dto);
				}
			}
			Gson gson = new Gson();

			response.getWriter().write(gson.toJson(TripRouteList));
		} catch (Exception e) {
			throw e;
		}
	}
}