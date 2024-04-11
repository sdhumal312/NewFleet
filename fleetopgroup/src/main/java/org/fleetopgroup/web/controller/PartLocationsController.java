package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
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

import org.fleetopgroup.constant.PartLocationsType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.User;
import org.fleetopgroup.persistence.dto.UserDto;
import org.fleetopgroup.persistence.model.PartLocations;
import org.fleetopgroup.persistence.model.PartLocationsHistory;
import org.fleetopgroup.persistence.service.UserService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.web.util.ValueObject;
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
public class PartLocationsController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IPartLocationsService PartLocationsService;

	@Autowired
	private IPartLocationsHistoryService partLocationsHistoryService;
	
	@Autowired
	private ICompanyConfigurationService	companyConfigurationService;

	PartLocationsBL VehStBL = new PartLocationsBL();

	// Show the the Vehicle Status of
	@RequestMapping(value = "/addPartLocations", method = RequestMethod.GET)
	public ModelAndView addVehicleType(@ModelAttribute("command") PartLocations PartLocationsBean,
			final HttpServletRequest request) {
		Map<String, Object> model			 = new HashMap<String, Object>();
		CustomUserDetails	userDetails		 = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			model.put("configuration",configuration);
			model.put("PartLocations", VehStBL.prepareListofBean(PartLocationsService.listOfAllTypePartLocation(userDetails.getCompany_id())));
		} catch (Exception e) {
			LOGGER.error("Part Locations Type Page:", e);

		}
		return new ModelAndView("newPartLocations", model);
	}

	// Show the the Vehicle Status of
	@RequestMapping(value = "/createPartLocations", method = RequestMethod.GET)
	public ModelAndView createVehicleType(@ModelAttribute("command") PartLocations PartLocationsBean,
			final HttpServletRequest request) throws Exception {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			model.put("mainLocationList", PartLocationsService.getPartLocationListByType(PartLocationsType.PART_LOCATION_TYPE_MAIN_LOCATION));
			model.put("subPartLocationTypeNeeded", configuration.getOrDefault(ICompanyConfigurationService.SUB_PART_LOCATION_TYPE_NEEDED, false));
			
			return new ModelAndView("addPartLocations", model);
		} catch (Exception e) {
			throw e;
		}
		
	}

	/* save the vehicle Status */
	@RequestMapping(value = "/savePartLocations", method = RequestMethod.POST)
	public ModelAndView savePartLocations(@ModelAttribute("command") PartLocations PartLocations,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		 = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			PartLocations GET_status = VehStBL.prepareModel(PartLocations);
			PartLocations validate = PartLocationsService.validatePartLocations(PartLocations.getPartlocation_name(), userDetails.getCompany_id());
			if (validate == null) {
				GET_status.setCompanyId(userDetails.getCompany_id());
				GET_status.setCreatedById(userDetails.getId());
				GET_status.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				PartLocationsService.addPartLocations(GET_status);
				model.put("savePartLocations", true);
			} else {
				return new ModelAndView("redirect:/addPartLocations.in?danger=true");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/addPartLocations.in?danger=true");
		}
		return new ModelAndView("redirect:/addPartLocations.in", model);
	}

	// show Part Location
	@RequestMapping(value = "/showPartLocations", method = RequestMethod.GET)
	public ModelAndView showPartLocations(@ModelAttribute("command") PartLocations PartLocationsBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("PartLocations", VehStBL.preparePartLocationsBean(
					PartLocationsService.validatePartLocation(PartLocationsBean.getPartlocation_id())));
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			model.put("subPartLocationTypeNeeded", configuration.getOrDefault(ICompanyConfigurationService.SUB_PART_LOCATION_TYPE_NEEDED, false));
			model.put("PartLocations", VehStBL.preparePartLocationsBeanDetails(
					PartLocationsService.getPartLocationsDetails(PartLocationsBean.getPartlocation_id())));
		} catch (Exception e) {
			LOGGER.error("Part Locations Type Page:", e);
		}
		return new ModelAndView("showPartLocations", model);
	}

	// update of vehicle status
	@RequestMapping(value = "/uploadPartLocations", method = RequestMethod.POST)
	public ModelAndView uploadPartLocations(@ModelAttribute("command") PartLocations PartLocations,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		 = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			PartLocations 	status = VehStBL.prepareModel(PartLocations);
			PartLocations	locations		=	PartLocationsService.validatePartLocation(status.getPartlocation_id());
		
			if(!locations.getPartlocation_name().trim().equalsIgnoreCase(status.getPartlocation_name().trim())) {
				PartLocations validate = PartLocationsService.validatePartLocations(PartLocations.getPartlocation_name(), userDetails.getCompany_id());
				if(validate != null) {
					model.put("alreadyPartLocation", true);
					return new ModelAndView("redirect:/addPartLocations.in?danger=true");
				}
			}
			status.setCompanyId(locations.getCompanyId());
			status.setCreatedById(locations.getCreatedById());
			status.setCreatedOn(locations.getCreatedOn());
			status.setLastModifiedById(userDetails.getId());
			status.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
			
			PartLocationsHistory	partLocationsHistory	= VehStBL.prepareHistoryModel(locations);
			
			PartLocationsService.updatePartLocations(status);
			partLocationsHistoryService.addPartLocationsHistory(partLocationsHistory);
			
			model.put("updatePartLocations", true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/addPartLocations.in?danger=true");
		}
		return new ModelAndView(
				"redirect:/showPartLocations.in?partlocation_id=" + PartLocations.getPartlocation_id() + "", model);
	}

	@RequestMapping(value = "/editPartLocations", method = RequestMethod.GET)
	public ModelAndView editPartLocations(@ModelAttribute("command") PartLocations PartLocationsBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			model.put("subPartLocationTypeNeeded", configuration.getOrDefault(ICompanyConfigurationService.SUB_PART_LOCATION_TYPE_NEEDED, false));
			model.put("mainLocationList", PartLocationsService.getPartLocationListByType(PartLocationsType.PART_LOCATION_TYPE_MAIN_LOCATION));
			model.put("PartLocations", VehStBL.preparePartLocationsBeanDetails(
					PartLocationsService.getPartLocationsDetails(PartLocationsBean.getPartlocation_id())));
		} catch (Exception e) {
			LOGGER.error("Part Locations Type Page:", e);
		}
		return new ModelAndView("editPartLocations", model);
	}

	@RequestMapping(value = "/deletePartLocations", method = RequestMethod.GET)
	public ModelAndView deletePartLocations(@ModelAttribute("command") PartLocations PartLocationsBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		 = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			PartLocations partlocation = PartLocationsService.validatePartLocation(PartLocationsBean.getPartlocation_id());
			
			if (partlocation.getPartlocation_name() != null) {

				boolean validate = true;
				Object[] count = PartLocationsService
						.vaildate_TotalPartName_Location_PART_TYRE_WO_PO(partlocation.getPartlocation_name(), userDetails.getCompany_id(), partlocation.getPartlocation_id());
				for (Object object : count) {
					Long cou = (Long) object;
					if (cou != 0) {
						validate = false;
					}
				}
				
				boolean validateSubLocation = true;
				Object[] count1 = PartLocationsService
						.validateSubLocationIsPresentInInventory_WO_SE_PO(partlocation.getPartlocation_name(), userDetails.getCompany_id(), partlocation.getPartlocation_id());
				for (Object object : count1) {
					Long cou = (Long) object;
					if (cou != 0) {
						validateSubLocation = false;
					}
				}
				
				

				if (validate && validateSubLocation) {
					
					PartLocationsHistory	partLocationsHistory	= VehStBL.prepareHistoryModel(partlocation);
					
					PartLocationsService.deletePartLocations(PartLocationsBean.getPartlocation_id(), userDetails.getCompany_id());
					partLocationsHistoryService.addPartLocationsHistory(partLocationsHistory);
					
					model.put("deletePartLocations", true);
				} else {
					model.put("deleteInside", true);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Part Locations Type Page:", e);
			return new ModelAndView("redirect:/addPartLocations.in?danger=true");
		}
		return new ModelAndView("redirect:/addPartLocations.in", model);
	}

	// select Ajax Drop down value
	@RequestMapping(value = "/getVehiclePartlocation", method = RequestMethod.GET)
	public void getVehiclePartlocation(Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			CustomUserDetails	userDetails		 = null;
			try {
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

				List<PartLocations> group = new ArrayList<PartLocations>();
				for (PartLocations add : PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())) {
					PartLocations wadd = new PartLocations();
					// wadd.setVid(add.getVid());
					wadd.setPartlocation_name(add.getPartlocation_name());
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

	// select Ajax Drop down value
	@RequestMapping(value = "/getWorkOrderPartlocation", method = RequestMethod.GET)
	public void getWorkOrderPartlocation(Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails		 = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<PartLocations> group = new ArrayList<PartLocations>();
			for (PartLocations add : PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())) {
				PartLocations wadd = new PartLocations();
				// wadd.setVid(add.getVid());
				wadd.setPartlocation_name(add.getPartlocation_name());
				wadd.setPartlocation_id(add.getPartlocation_id());
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

	/** This below Search PartLocations Type Search Details */
	@RequestMapping(value = "/getSearchPartLocations", method = RequestMethod.POST)
	public void getSearchPartLocations(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails		 = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<PartLocations> Tyre = new ArrayList<PartLocations>();
			List<PartLocations> Size = PartLocationsService.Search_PartLocations_select(term, userDetails.getCompany_id());
			if (Size != null && !Size.isEmpty()) {
				for (PartLocations add : Size) {

					PartLocations wadd = new PartLocations();
					wadd.setPartlocation_id(add.getPartlocation_id());
					wadd.setPartlocation_name(add.getPartlocation_name());

					Tyre.add(wadd);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(Tyre));
		} catch (Exception e) {
			throw e;
		}
	
	}

	/** This below Search All PartLocations Type Search Details */
	@RequestMapping(value = "/getAllPartLocations", method = RequestMethod.POST)
	public void getAllPartLocations(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails		 = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			List<PartLocations> Tyre = new ArrayList<PartLocations>();
			List<PartLocations> Size = PartLocationsService.getAllPartLocations(term, userDetails.getCompany_id());
			if (Size != null && !Size.isEmpty()) {
				for (PartLocations add : Size) {
					
					PartLocations wadd = new PartLocations();
					wadd.setPartlocation_id(add.getPartlocation_id());
					wadd.setPartlocation_name(add.getPartlocation_name());
					
					Tyre.add(wadd);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(Tyre));
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	
	@RequestMapping(value = "/getPartLocationsByMainLocationId", method = RequestMethod.POST)
	public void getPartLocationsByMainLocationId(Map<String, Object> map, @RequestParam("term") final String term,
			@RequestParam("locationType") final short locationType , @RequestParam("mainLocationId") final Integer mainLocationId ,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<PartLocations> setpartLocationList 		= null;
		List<PartLocations> getPartLocationList 		= null;	
		PartLocations 		partLocations 				= null;
		ValueObject         object                      = null;
		try {
			setpartLocationList = new ArrayList<>();
			getPartLocationList = PartLocationsService.searchPartLocationListByMainLocatinId(term, locationType,mainLocationId,object);
			if (getPartLocationList != null && !getPartLocationList.isEmpty()) {
				for (PartLocations add : getPartLocationList) {
					
					partLocations = new PartLocations();
					partLocations.setPartlocation_id(add.getPartlocation_id());
					partLocations.setPartlocation_name(add.getPartlocation_name());
					
					setpartLocationList.add(partLocations);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(setpartLocationList));
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@RequestMapping(value = "/getAllSubLocations", method = RequestMethod.POST)
	public void getAllSubLocations(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails		 = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			List<PartLocations> Tyre = new ArrayList<PartLocations>();
			List<PartLocations> Size = PartLocationsService.getAllSubLocations(term, userDetails.getCompany_id());
			if (Size != null && !Size.isEmpty()) {
				for (PartLocations add : Size) {
					
					PartLocations wadd = new PartLocations();
					wadd.setPartlocation_id(add.getPartlocation_id());
					wadd.setPartlocation_name(add.getPartlocation_name());
					
					Tyre.add(wadd);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(Tyre));
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@RequestMapping(value = "/allLocationDropdown", method = RequestMethod.GET)
	public void getCompanyInformationDetails(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<PartLocations> 		partLocationList 		= new ArrayList<>();
		ArrayList<PartLocations> 	partLocationFinalList	= null;
		try {
			partLocationList 	=  PartLocationsService.allLocationDropdown();
			partLocationFinalList = new ArrayList<>();
			if(partLocationList != null && !partLocationList.isEmpty()) {	
				for (PartLocations add : partLocationList) {
					PartLocations wadd = new PartLocations();
					wadd.setPartlocation_id(add.getPartlocation_id());
					wadd.setPartlocation_name(add.getPartlocation_name());

					partLocationFinalList.add(wadd);
				}
			}

			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(partLocationFinalList));
		}catch(Exception e) {
			LOGGER.error("Exception addClothTypes : ", e);
		}
	}
	
	@RequestMapping(value = "/getCreatedUser", method = RequestMethod.POST)
	public void getSearchUsers(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails		 = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			List<UserDto> ulist = new ArrayList<UserDto>();
			List<UserDto> user = PartLocationsService.Search_User(term, userDetails.getCompany_id());
			if (user != null && !user.isEmpty()) {
				for (UserDto add : user) {
					
					UserDto wadd = new UserDto();
					wadd.setFirstName(add.getFirstName());
					wadd.setLastName(add.getLastName());
					wadd.setId(add.getId());
					ulist.add(wadd);
				}
			}
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(ulist));
		} catch (Exception e) {
			throw e;
		}
	
	}
	

}