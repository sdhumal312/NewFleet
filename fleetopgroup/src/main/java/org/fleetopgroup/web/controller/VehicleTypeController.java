package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
/**
 * @author fleetop
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.CompanyConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.VehicleTypeBL;
import org.fleetopgroup.persistence.dao.VehicleTypeToServiceProgramRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleTypeDto;
import org.fleetopgroup.persistence.model.VehicleType;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class VehicleTypeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleTypeService vehicletypeService;
	@Autowired VehicleTypeToServiceProgramRepository	programRepository;
	@Autowired
	private ICompanyConfigurationService companyConfigurationService;

	public VehicleTypeController() {
		super();
	}

	VehicleTypeBL VehTypeBL = new VehicleTypeBL();

	@RequestMapping(value = "/addVehicleTypes/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView addVehicleType(@PathVariable Integer pageNumber, Model model, final HttpServletRequest request) {
		
		CustomUserDetails userDetails = null;
		List<VehicleTypeDto> pageList = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			
			Page<VehicleType> page = vehicletypeService.getDeployment_Page_VehileType(pageNumber, userDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("vehicleTypeCount", page.getTotalElements());
			
			if((boolean) configuration.getOrDefault("showOnlyCompanyVehicleType", false))
				pageList = vehicletypeService.getCompanyWiseVehcileType(pageNumber, userDetails);
			else
				pageList = vehicletypeService.GET_list_Of_VehileType(pageNumber, userDetails);

			model.addAttribute("vehicletypes", pageList);
			model.addAttribute("companyId", userDetails.getCompany_id());
			model.addAttribute("userId", userDetails.getId());

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("trip addTripRoute Page:", e);
			e.printStackTrace();
		}
	
		return new ModelAndView("addVehicleTypes");
	}

	// save the vehicle Type
	@RequestMapping(value = "/saveVehType", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveVehicleType(final VehicleTypeDto vehicleTypeDto, final HttpServletRequest request) {

		LOGGER.error("Registering VehicleType account with information: {}", vehicleTypeDto);
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails currentUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			final VehicleType getvehicleType = VehTypeBL.prepareModel(vehicleTypeDto);
				getvehicleType.setCompany_Id(currentUser.getCompany_id());
				if(currentUser.getCompany_id().equals(CompanyConstant.COMPANY_CODE_FLEETOP)) {
					getvehicleType.setCompany_Id(0);
					getvehicleType.setSuperProgramId(vehicleTypeDto.getServiceProgramId());
				}else {
					getvehicleType.setServiceProgramId(vehicleTypeDto.getServiceProgramId());
				}
			// find validate
			final VehicleType validate_type = vehicletypeService.findByVtype(getvehicleType.getVtype(), currentUser.getCompany_id());
			if (validate_type == null) {
					vehicletypeService.registerNewVehicleType(getvehicleType);
					model.put("saveVehicleType", true);
			} else {
				model.put("alreadyVehicleType", true);
				return new ModelAndView("redirect:/addVehicleTypes/1.html", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("add Vehicle Type Page:", e);
			model.put("alreadyVehicleType", true);
			return new ModelAndView("redirect:/addVehicleTypes/1.html", model);
		}
		return new ModelAndView("redirect:/addVehicleTypes/1.html", model);
	}

	@RequestMapping(value = "/editVehicleTypes", method = RequestMethod.GET)
	public ModelAndView editVehicleType(final Locale locale, @RequestParam("tid") final long tid) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails currentUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("vehicletype", vehicletypeService.getVehicleTypeDtoByID(tid, currentUser.getCompany_id()));
			//model.put("vehicleTypeCount", vehicletypeService.vehicleTypeCountForCompany(currentUser.getCompany_id()));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("add Vehicle Type Page:", e);
			model.put("alreadyVehicleType", true);
			return new ModelAndView("redirect:/addVehicleTypes/1.html", model);
		}
		return new ModelAndView("addVehicleTypeEdit", model);
	}

	@RequestMapping(value = "/updateVehType", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView updateVehicleType(final Locale locale, final VehicleTypeDto vehicleTypeDto,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails currentUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Date currentDateUpdate = new Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			VehicleTypeDto	vehicleType = vehicletypeService.getVehicleTypeDtoByID(vehicleTypeDto.getTid(), currentUser.getCompany_id());
				Integer companyId = currentUser.getCompany_id();
				if(currentUser.getCompany_id().equals(CompanyConstant.COMPANY_CODE_FLEETOP)) {
					companyId = 0;
				}
				if(vehicleTypeDto.getVtype().trim().equalsIgnoreCase( vehicleType.getVtype().trim())) {
					vehicletypeService.updateVehicleType(vehicleTypeDto.getVtype(), currentUser.getEmail(), toDate,
							vehicleTypeDto.getTid(), companyId, vehicleTypeDto.getMaxAllowedOdometer(), vehicleTypeDto.getServiceProgramId());
					model.put("updateVehicleType", true);
					return new ModelAndView("redirect:/addVehicleTypes/1.html", model);
				}
				
				final VehicleType validate_type = vehicletypeService.findByVtype(vehicleTypeDto.getVtype(), currentUser.getCompany_id());
			
			if(validate_type == null) {
				vehicletypeService.updateVehicleType(vehicleTypeDto.getVtype(), currentUser.getEmail(), toDate,
						vehicleTypeDto.getTid(), companyId, vehicleTypeDto.getMaxAllowedOdometer(), vehicleTypeDto.getServiceProgramId());
				model.put("updateVehicleType", true);
			}else {
				model.put("alreadyVehicleType", true);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Edit Vehicle Type Page:", e);
			model.put("alreadyVehicleType", true);
			return new ModelAndView("redirect:/addVehicleTypes/1.html", model);
		}
		return new ModelAndView("redirect:/addVehicleTypes/1.html", model);
	}

	@RequestMapping(value = "/deleteVehicleTypes", method = RequestMethod.GET)
	public ModelAndView deleteVehicleType(final Locale locale, 
			@RequestParam("tid") final long tid) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicletypeService.deleteVehicleType(tid, userDetails.getCompany_id());

			model.put("deleteVehicleType", true);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Delete Vehicle Type Page:", e);
			model.put("alreadyVehicleType", true);
			return new ModelAndView("redirect:/addVehicleTypes/1.html", model);
		}
		return new ModelAndView("redirect:/addVehicleTypes/1.html", model);
	}

	// select Ajax Drop down value
	@RequestMapping(value = "/getVehicleType", method = RequestMethod.GET)
	public void getVehicleType(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustomUserDetails currentUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(currentUser.getCompany_id(),PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);

		List<VehicleType> group =null;
		List<VehicleType> finalList =new ArrayList<>();

		if((boolean) configuration.getOrDefault("showOnlyCompanyVehicleType", false)) {
			group = vehicletypeService.findAllVehileTypeByOnlyCompanyId(currentUser.getCompany_id()); 
		}else {
			group = vehicletypeService.findAllVehileTypeByCompanyId(currentUser.getCompany_id());
		};

		if(group != null) {
			for (VehicleType add :group ) {
				VehicleType wadd = new VehicleType();

				wadd.setTid(add.getTid());
				wadd.setVtype(add.getVtype());
				// System.out.println(add.getVid());
				finalList.add(wadd);
			}
		}	
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(finalList));
	}

	@RequestMapping(value = "/getVehicleTypeByName", method = RequestMethod.POST)
	public void getVehicleTypeByName(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<VehicleType> addresses = new ArrayList<>();
			VehicleType wadd = null;
			List<VehicleType> vehicleType = vehicletypeService.getVehicleTypeByName(term, userDetails.getCompany_id());
			
			if (vehicleType != null && !vehicleType.isEmpty()) {
				for (VehicleType add : vehicleType) {
					wadd = add;
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