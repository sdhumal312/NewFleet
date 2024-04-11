package org.fleetopgroup.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.fleetopgroup.persistence.bl.BatteryManufacturerBL;
import org.fleetopgroup.persistence.bl.VehicleCostFixingBL;
import org.fleetopgroup.persistence.dto.BatteryManufacturerDto;
import org.fleetopgroup.persistence.dto.BatteryTypeDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.BatteryCapacity;
import org.fleetopgroup.persistence.model.BatteryManufacturer;
import org.fleetopgroup.persistence.model.BatteryType;
import org.fleetopgroup.persistence.model.VehicleCostFixing;
import org.fleetopgroup.persistence.serviceImpl.IBatteryCapacityService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryManufacturerService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryTypeService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleCostFixingService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BatteryMasterRestController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired IBatteryManufacturerService	batteryManufacturerService;
	@Autowired IBatteryTypeService			batteryTypeService;
	@Autowired IBatteryCapacityService		batteryCapacityService;
	@Autowired IVehicleCostFixingService	vehicleCostFixingService;
	
	BatteryManufacturerBL batteryManufacturerBL = new BatteryManufacturerBL();	

	@RequestMapping(value = "/addBatteryManfaturer", method = RequestMethod.GET)
	public ModelAndView addBatteryManfaturer(@ModelAttribute("command") BatteryManufacturer batteryManufacturer,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("manufacturer", batteryManufacturerService.findAll());
		} catch (Exception e) {
			LOGGER.error("Job Type Page:", e);
		}
		return new ModelAndView("addBatteryManfaturer", model);
	}
	
	@RequestMapping(value = "/saveBatteryManufacturer", method = RequestMethod.POST)
	public ModelAndView saveTyreModelType(@ModelAttribute("command") BatteryManufacturerDto BatteryManufacturerDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<BatteryManufacturer>   validate	= null;
		try {
			
			BatteryManufacturer batteryManufacturer = batteryManufacturerBL.getBatteryManufacturerDto(BatteryManufacturerDto);

			validate	= batteryManufacturerService.validateBatteryManufacturer(BatteryManufacturerDto.getManufacturerName());
			
			if (validate == null || validate.isEmpty()) {
				batteryManufacturerService.save(batteryManufacturer);
				model.put("saved", true);
			} else {
				model.put("already", true);
				return new ModelAndView("redirect:/addBatteryManfaturer.in", model);
			}
		} catch (Exception e) {
			LOGGER.error("Battery Manufacurer Page:", e);
			model.put("already", true);
			return new ModelAndView("redirect:/addBatteryManfaturer.in", model);
		}finally {
			
		}
		return new ModelAndView("redirect:/addBatteryManfaturer.in", model);
	}
	
	@RequestMapping(value = "/addBatteryType", method = RequestMethod.GET)
	public ModelAndView addBatteryType(@ModelAttribute("command") BatteryTypeDto	batteryTypeDto,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("manufacturer", batteryManufacturerService.findAll());
			model.put("batteryType", batteryTypeService.findAllBatteryType(userDetails.getCompany_id()));
		} catch (Exception e) {
			LOGGER.error("Job Type Page:", e);
		}
		return new ModelAndView("addBatteryType", model);
	}
	
	@RequestMapping(value = "/editbatteryManufacturer", method = RequestMethod.GET)
	public ModelAndView deleteEmployee(final Locale locale, @RequestParam("batteryManufacturerId") final long batteryManufacturerId) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			model.put("batteryManufacturer", batteryManufacturerService.getBatteryManufactureByID(batteryManufacturerId));
			//model.put("vehiclePhoTypeCount", batteryManufacturerService.countBatteryManufactureByCompanyId(userDetails.getCompany_id()));

		} catch (Exception e) {
			LOGGER.error("Registering editbatteryManufacturer account with information: {}", e);
		}
		return new ModelAndView("editbatteryManufacturer", model);
	}

	
	@RequestMapping(value = "/editbatteryType", method = RequestMethod.GET)
	public ModelAndView editBattery(final Locale locale, @RequestParam("batteryTypeId") final long batteryTypeId) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {

			model.put("BatteryType", batteryTypeService.getBatteryTypeDto(batteryTypeId));
			model.put("manufacturer", batteryManufacturerService.findAll());
			//model.put("vehiclePhoTypeCount", batteryManufacturerService.countBatteryManufactureByCompanyId(userDetails.getCompany_id()));

		} catch (Exception e) {
			System.err.println("exc "+e);
			LOGGER.error("Registering editbatteryType account with information: {}", e);
		}
		return new ModelAndView("editbatteryType", model);
	}

	
	
	
	@RequestMapping(value = "/saveBatteryType", method = RequestMethod.POST)
	public ModelAndView saveBatteryType(@ModelAttribute("command") BatteryTypeDto batteryTypeDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<BatteryType>   validate	= null;
		
		try {
			BatteryType batteryType = batteryManufacturerBL.getBatteryTypeDto(batteryTypeDto);
		
			validate	= batteryTypeService.validateType(batteryType.getBatteryType(), batteryType.getBatteryManufacturerId(), batteryType.getPartNumber(), batteryType.getWarrantyPeriod());
			
			if (validate == null || validate.isEmpty()) {
				batteryTypeService.save(batteryType);
				model.put("saved", true);
			} else {
				model.put("already", true);
				return new ModelAndView("redirect:/addBatteryType.in", model);
			}
		} catch (Exception e) {
			LOGGER.error("Battery Manufacurer Page:", e);
			model.put("already", true);
			return new ModelAndView("redirect:/addBatteryType.in", model);
		}finally {
			validate	= null;
		}
		return new ModelAndView("redirect:/addBatteryType.in", model);
	}
	
	@RequestMapping(value = "/addBatteryCapacity", method = RequestMethod.GET)
	public ModelAndView addBatteryCapacity(@ModelAttribute("command") BatteryManufacturer batteryManufacturer,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("batteryCapacity", batteryCapacityService.getBatteryCapacityList());
		} catch (Exception e) {
			
			LOGGER.error("Job Type Page:", e);
		}
		return new ModelAndView("addBatteryCapacity", model);
	}
	
	@RequestMapping(value = "/updateBatteryManufacturer", method = RequestMethod.POST)
	public ModelAndView updateTyreModelType(@ModelAttribute("command") BatteryManufacturerDto BatteryManufacturerDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<BatteryManufacturer>   validate	= null;
		BatteryManufacturer		manufacturer	= null;
		try {
			manufacturer	= batteryManufacturerService.getBatteryManufactureByID(BatteryManufacturerDto.getBatteryManufacturerId());
			BatteryManufacturer batteryManufacturer = batteryManufacturerBL.getBatteryManufacturerDto(BatteryManufacturerDto);
			if(!manufacturer.getManufacturerName().equalsIgnoreCase(batteryManufacturer.getManufacturerName())) {
				validate	= batteryManufacturerService.validateBatteryManufacturer(BatteryManufacturerDto.getManufacturerName());
			}
			
			if (validate == null || validate.isEmpty()) {
				batteryManufacturerService.save(batteryManufacturer);
				model.put("update", true);
			} else {
				model.put("already", true);
				return new ModelAndView("redirect:/addBatteryManfaturer.in", model);
			}
		} catch (Exception e) {
			LOGGER.error("Battery Manufacurer Page:", e);
			model.put("already", true);
			return new ModelAndView("redirect:/addBatteryManfaturer.in", model);
		}finally {
			
		}
		return new ModelAndView("redirect:/addBatteryManfaturer.in", model);
	}
	
	
	@RequestMapping(value = "/updateBatteryType.in", method = RequestMethod.POST)
	public ModelAndView updateTyreModelType(@ModelAttribute("command") BatteryTypeDto BatteryTypeDto,
			BindingResult result) {
		
		Map<String, Object> model 		= new HashMap<String, Object>();
		List<BatteryType>   validate	= null;
		BatteryType			Type		= null;
		
		try {
			Type					= batteryTypeService.getBatteryTypeByID(BatteryTypeDto.getBatteryTypeId());
			BatteryType BatteryType = batteryManufacturerBL.getBatteryTypeDto(BatteryTypeDto);
			if(!Type.getBatteryType().equalsIgnoreCase(BatteryTypeDto.getBatteryType())) {
					validate= batteryTypeService.validateBatteryType(BatteryTypeDto.getBatteryType());
			}
			if (validate == null || validate.isEmpty()) {
				batteryTypeService.save(BatteryType);
				model.put("update", true);
			} else {
				
				model.put("already", true);
				return new ModelAndView("redirect:/addBatteryType.in", model);
			}
		} catch (Exception e) {
			
			
			LOGGER.error("Battery Type Page:", e);
			model.put("already", true);
			return new ModelAndView("redirect:/addBatteryType.in", model);
		}finally {
			
		}
		return new ModelAndView("redirect:/addBatteryType.in", model);
	}


	
	@RequestMapping(value = "/saveBatteryCapacity", method = RequestMethod.POST)
	public ModelAndView saveBatteryCapacity(@ModelAttribute("command") BatteryCapacity capacity,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<BatteryCapacity>   validate	= null;
		try {
			
			BatteryCapacity batteryCapacity = batteryManufacturerBL.getBatteryCapacityDto(capacity);

			validate	= batteryCapacityService.validateBatteryCapacity(capacity.getBatteryCapacity());
			
			if (validate == null || validate.isEmpty()) {
				batteryCapacityService.save(batteryCapacity);
				model.put("saved", true);
			} else {
				model.put("already", true);
				return new ModelAndView("redirect:/addBatteryCapacity.in", model);
			}
		} catch (Exception e) {
			LOGGER.error("Battery Manufacurer Page:", e);
			model.put("already", true);
			return new ModelAndView("redirect:/addBatteryCapacity.in", model);
		}finally {
			
		}
		return new ModelAndView("redirect:/addBatteryCapacity.in", model);
	}
	
	@RequestMapping(value = "/editCostPerDay", method = RequestMethod.POST)
	public ModelAndView editCostPerDay(@ModelAttribute("command") BatteryTypeDto batteryTypeDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails		=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(batteryTypeDto.getCostPerDay() != null && batteryTypeDto.getCostPerDay() > 0.0) {
				
				
				VehicleCostFixing	costFixing	= new VehicleCostFixing();
				costFixing.setCompanyId(userDetails.getCompany_id());
				if(batteryTypeDto.getVehicleCostFixingId() != null && batteryTypeDto.getVehicleCostFixingId() > 0)
					costFixing.setVehicleCostFixingId(batteryTypeDto.getVehicleCostFixingId());
				costFixing.setCostPerDay(batteryTypeDto.getCostPerDay());
				costFixing.setBatteryTypeId(batteryTypeDto.getBatteryTypeId());
				costFixing.setCreatedById(userDetails.getId());
				costFixing.setLastModifiedById(userDetails.getId());
				costFixing.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
				costFixing.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
				costFixing.setCostType(VehicleCostFixingBL.COST_TYPE_BATTERY);
				
				vehicleCostFixingService.saveVehicleCostFixing(costFixing);
				
				model.put("costUpdated", true);
			}
			
		

		} catch (Exception e) {
			LOGGER.error("Job Sub Type Page:", e);
			model.put("alreadyModelSubType", true);
			return new ModelAndView("redirect:/addBatteryType.in", model);
		}
		return new ModelAndView("redirect:/addBatteryType.in", model);
	}


}
