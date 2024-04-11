package org.fleetopgroup.web.controller;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.VehicleInspectionStatus;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleInspectionBL;
import org.fleetopgroup.persistence.dao.VehicleDailyInspectionRepository;
import org.fleetopgroup.persistence.dao.VehicleInspctionSheetAsingmentRepository;
import org.fleetopgroup.persistence.dao.VehicleInspectionCompletionDetailsRepository;
import org.fleetopgroup.persistence.dao.VehicleInspectionSheetToParameterRepository;
import org.fleetopgroup.persistence.dao.VehicleRepository;
import org.fleetopgroup.persistence.document.InspectionToParameterDocument;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InspectionCompletionToParameterDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleDailyInspectionDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleInspctionSheetAsingmentDto;
import org.fleetopgroup.persistence.dto.VehicleInspectionCompletionDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleInspectionReportDetails;
import org.fleetopgroup.persistence.dto.VehicleInspectionSheetDto;
import org.fleetopgroup.persistence.dto.VehicleInspectionSheetToParameterDto;
import org.fleetopgroup.persistence.model.InspectionCompletionToParameter;
import org.fleetopgroup.persistence.model.InspectionParameter;
import org.fleetopgroup.persistence.model.Issues;
import org.fleetopgroup.persistence.model.TripRoutefixedExpense;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleDailyInspection;
import org.fleetopgroup.persistence.model.VehicleGroup;
import org.fleetopgroup.persistence.model.VehicleInspectionCompletionDetails;
import org.fleetopgroup.persistence.model.VehicleInspectionSheet;
import org.fleetopgroup.persistence.model.VehicleInspectionSheetToParameter;
import org.fleetopgroup.persistence.model.VehicleType;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInspectionCompletionToParameterService;
import org.fleetopgroup.persistence.serviceImpl.IInspectionToParameterDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDailyInspectionService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspctionSheetAsingmentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionCompletionDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionSheetService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionSheetToParameterService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTypeAssignmentDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTypeService;
import org.fleetopgroup.web.util.ByteConvertor;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Controller
public class VehicleInspectionSheetController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private @Autowired	IVehicleGroupService							vehicleGroupService;
	
	private @Autowired	IVehicleService									vehicleService;
	
	private @Autowired	IVehicleInspectionSheetService					vehicleInspectionSheetService;
	
	private @Autowired	IVehicleInspectionSheetToParameterService		sheetToParameterService;
	
	private	@Autowired 	IVehicleInspctionSheetAsingmentService			asingmentService;
	
	private @Autowired 	IVehicleDocumentService							vehicleDocumentService;
	
	private	@Autowired IUserProfileService 								userProfileService;
	
	private @Autowired IVehicleInspectionCompletionDetailsService		vehicleInspectionCompletionDetailsService;
	
	private @Autowired IInspectionCompletionToParameterService			completionToParameterService;
	
	private @Autowired IInspectionToParameterDocumentService			documentService;
	
	private @Autowired IVehicleDailyInspectionService					VehicleDailyInspectionService;
	
	private @Autowired VehicleDailyInspectionRepository					VehicleDailyInspectionRepository;
	
	private @Autowired VehicleInspectionSheetToParameterRepository		VehicleInspectionSheetToParameterRepository;
	
	@Autowired private VehicleInspctionSheetAsingmentRepository         vehicleInspctionSheetAsingmentRepository;
	
	@Autowired private ICompanyConfigurationService 							companyConfigurationService;
	
	@Autowired  private IVehicleTypeService									 vehicletypeService;
	
	@Autowired private IIssuesService                 					 issuesService;
	
	@Autowired private 	VehicleRepository				vehicleRepository;
	
	
	
	
	@Autowired private VehicleInspectionCompletionDetailsRepository   inspectionCompletionDetailsRepository;
	
	@Autowired private IVehicleTypeAssignmentDetailsService vehicleTypeAssignmentDetailsService;
	
	VehicleInspectionBL		vehicleInspectionBL	= new VehicleInspectionBL();
	VehicleBL 				VBL 				= new VehicleBL();
	
	SimpleDateFormat 		dateFormat 			= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat        dateFormat2         = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 		format 				= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static DecimalFormat df2 			= new DecimalFormat("#.##");
	

	@RequestMapping(value = "/ViewInspectionSheet", method = RequestMethod.GET)
	public ModelAndView ViewInspectionSheet(@ModelAttribute("command") InspectionParameter inspectionParameter,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<VehicleInspectionSheet>		inspectionSheetList		= null;
		HashMap<Long,VehicleType>    		vehicleTypeList			= null;	
		HashMap<Long, VehicleGroup>			vehicleGroupHM			= null;
		try {
			
			HashMap<String, Object> 		configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			model.put("configuration", configuration);
			
			inspectionSheetList	 	=	vehicleInspectionSheetService.getListVehicleInspectionSheet(userDetails.getCompany_id());
			if((boolean)configuration.getOrDefault("failedParameterPenalty",false)) {
				vehicleTypeList			=	vehicletypeService.getVehicleTypeHMByCompanyId(userDetails.getCompany_id());
				model.put("inspectionSheetList", vehicleInspectionBL.getVehicleTypeInspectionSheetDtoList(vehicleTypeList, inspectionSheetList));
			}else {
				vehicleGroupHM		=	vehicleGroupService.getVehicleGroupHMByCompanyId(userDetails.getCompany_id());
				model.put("inspectionSheetList", vehicleInspectionBL.getVehicleInspectionSheetDtoList(vehicleGroupHM, inspectionSheetList));
			}
			
		} catch (Exception e) {
			LOGGER.error("ViewInspectionSheet Page:", e);
		}
		return new ModelAndView("ViewInspectionSheet", model);
	}
	
	
	@RequestMapping(value = "/AddInspectionSheet", method = RequestMethod.GET)
	public ModelAndView AddInspectionSheet(@ModelAttribute("command") InspectionParameter inspectionParameter,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		currentUser		= null;
		try {
			currentUser	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> 		configuration			= companyConfigurationService.getCompanyConfiguration(currentUser.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			model.put("configuration", configuration);
			model.put("group",vehicletypeService.findAllVehileTypeByCompanyId(currentUser.getCompany_id()));
			model.put("vehicleGroup", vehicleGroupService.findAllVehicleGroupByCompanyId(currentUser.getCompany_id()));
			
			
			
		} catch (Exception e) {
			LOGGER.error("AddInspectionSheet Page :", e);
		}
		return new ModelAndView("AddInspectionSheet", model);
	}
	
	
	/* save the Trip Route */
	@RequestMapping(value = "/saveVehicleInspectionSheet", method = RequestMethod.POST)
	public ModelAndView saveTripRoute(@ModelAttribute("command") VehicleInspectionSheet vehicleInspectionSheet,
			TripRoutefixedExpense TripSheetExpense, 
			@RequestParam("inspectionParameter") final String[] inspectionParameter,
			@RequestParam("frequency") final String[] frequency,
			@RequestParam("requiredType") final String requiredType,
			@RequestParam("photoRequired") final String photoRequired,
			@RequestParam("textRequired") final String textRequired,
			@RequestParam("VehicleLocation") final Integer VehicleLocation,
			final HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		VehicleInspectionSheetToParameter			sheetToParameter		= null;	
		VehicleInspectionSheet    savedInspectionSheet  = null;
		String vehicleTypeIds = "";
		try {
			
			final String[] requiredGroup	= requiredType.split(",");
			final String[] photoGroup	= photoRequired.split(",");
			final String[] textGroup	= textRequired.split(",");
			
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> 		configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			boolean sheetToVehicleType = (boolean) configuration.getOrDefault("sheetToVehicleType",false);
			if(sheetToVehicleType) {
				vehicleTypeIds =vehicleInspectionSheet.getVehicleTypeId();
				String [] vehicleTypeArr = vehicleTypeIds.split(",");
				long assignedCount= vehicleTypeAssignmentDetailsService.checkIfSheetAssignedToVehicleType(vehicleTypeIds, userDetails.getCompany_id());
				if(vehicleTypeArr.length == (int)assignedCount) {
					model.put("configuration", configuration);
					model.put("group",vehicletypeService.findAllVehileTypeByCompanyId(userDetails.getCompany_id()));
					model.put("vehicleGroup", vehicleGroupService.findAllVehicleGroupByCompanyId(userDetails.getCompany_id()));
					model.put("AlreadyAssigned", true);
					return new ModelAndView("AddInspectionSheet", model);
				}
			}
			vehicleInspectionSheet	= vehicleInspectionBL.getVehicleInspectionSheet(vehicleInspectionSheet, userDetails);
			
			List<VehicleInspectionSheet>	validate	=	vehicleInspectionSheetService.validateVehicleInspectionSheetName(vehicleInspectionSheet.getInspectionSheetName(), userDetails.getCompany_id());
			
			if(validate == null || validate.isEmpty()) {
				savedInspectionSheet= vehicleInspectionSheetService.saveVehicleInspectionSheet(vehicleInspectionSheet);
				
				if (inspectionParameter != null && inspectionParameter.length > 0) {
					for (int i = 0; i < inspectionParameter.length; i++) {
						sheetToParameter	= new VehicleInspectionSheetToParameter();
						
						sheetToParameter.setInspectionParameterId(Long.parseLong(inspectionParameter[i]+""));
						sheetToParameter.setFrequency(Integer.parseInt(frequency[i]+""));
						sheetToParameter.setMandatory(Boolean.parseBoolean(requiredGroup[i]+""));
						sheetToParameter.setPhotoRequired(Boolean.parseBoolean(photoGroup[i]+""));
						sheetToParameter.setTextRequired(Boolean.parseBoolean(textGroup[i]+""));
						sheetToParameter.setInspectionSheetId(vehicleInspectionSheet.getVehicleInspectionSheetId());
						sheetToParameter.setCompanyId(userDetails.getCompany_id());
						sheetToParameterService.saveVehicleInspectionSheetToParameter(sheetToParameter);
					}
				}
				if(sheetToVehicleType &&savedInspectionSheet != null && savedInspectionSheet.getVehicleInspectionSheetId() != null) {
					asingmentService.prepareSheeetToAssignMultiVehicleType(vehicleTypeIds,savedInspectionSheet.getVehicleInspectionSheetId(),VehicleLocation);
				}
				model.put("saved", true);
			}else {
				model.put("duplicate", true);
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			model.put("error", true);
			e.printStackTrace();
			return new ModelAndView("redirect:/AddInspectionSheet.in", model);
		} finally {
		}

		return new ModelAndView("redirect:/ViewInspectionSheet.in", model);
		//return new ModelAndView("ViewInspectionSheet", model);
	}
	
	@RequestMapping(value = "/showInspectionSheetDetails", method = RequestMethod.GET)
	public ModelAndView showInspectionSheetDetails(@ModelAttribute("command") VehicleInspectionSheet vehicleInspectionSheet,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails								userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		VehicleInspectionSheet							inspectionSheet			= null;
		HashMap<Long, VehicleGroup>						vehicleGroupHM			= null;
		HashMap<Long, VehicleType>  				vehicleTypeList			= null;
		List<VehicleInspectionSheetToParameterDto>		sheetToParameterDtoList	= null;
		try {
			HashMap<String, Object> 		configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			model.put("configuration", configuration);
				inspectionSheet				=	vehicleInspectionSheetService.getVehicleInspectionSheetById(vehicleInspectionSheet.getVehicleInspectionSheetId());
			if((boolean)configuration.getOrDefault("sheetToVehicleType",false)) {
				vehicleTypeList			=	vehicletypeService.getVehicleTypeHMByCompanyId(userDetails.getCompany_id());
				ValueObject valueObject = asingmentService.getVehicleTypeInspectionSheetDto(vehicleTypeList, inspectionSheet);
				model.put("inspectionSheet",valueObject.get("inspectionSheet") );
				model.put("vehicleInspectionSheetDtoList",vehicleTypeAssignmentDetailsService.getvehicleTypeAssignmebtDetailsBySheetId(vehicleInspectionSheet.getVehicleInspectionSheetId()));
			
			}else {
				vehicleGroupHM				=	vehicleGroupService.getVehicleGroupHMByCompanyId(userDetails.getCompany_id());
				model.put("inspectionSheet", vehicleInspectionBL.getVehicleInspectionSheetDto(vehicleGroupHM, inspectionSheet));
			}
			sheetToParameterDtoList		=	sheetToParameterService.getVehicleInspectionSheetToParameterList(vehicleInspectionSheet.getVehicleInspectionSheetId(), userDetails.getCompany_id());
				model.put("sheetToParameterDtoList", sheetToParameterDtoList);
			
		} catch (Exception e) {
			LOGGER.error("showInspectionSheetDetails Page:", e);
		}
		return new ModelAndView("showInspectionSheetDetails", model);
	}
	
	@RequestMapping(value = "/deleteInspectionSheet")
	public ModelAndView deleteInspectionSheet(@RequestParam("ID") final Long asingmentId,final HttpServletResponse result) {
		Map<String, Object> model = new HashMap<String, Object>();

			try {
				CustomUserDetails		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				VehicleInspectionSheet	inspectionSheet	= vehicleInspectionSheetService.getVehicleInspectionSheetById(asingmentId);
				List<VehicleInspectionSheetToParameter>	vehicleInspectionSheetToParameter = sheetToParameterService.checkParametersInVehicleInspectionSheets(asingmentId,userDetails.getCompany_id());
					if((vehicleInspectionSheetToParameter == null || vehicleInspectionSheetToParameter.isEmpty()) && inspectionSheet != null  ) {
						if(inspectionSheet.getNoOfVehicleAsigned() > 0) {
							model.put("vehicleAssigned", true);
							return new ModelAndView("redirect:/ViewInspectionSheet.in", model);
						}else {
							vehicleInspectionSheetService.deleteVehicleInspectionSheet(asingmentId, userDetails.getCompany_id());
							model.put("delete", true);
						}
					}else {
						
					//	vehicleInspectionSheetService.deleteVehicleInspectionSheet(asingmentId, userDetails.getCompany_id());
						model.put("InspectionsheetHavingParameters", true);
					}

				} catch (Exception e) {

					model.put("error", true);
					return new ModelAndView("redirect:/ViewInspectionSheet.in", model);
				}
				model.put("deleteSheetAssignment", true);
				
			return new ModelAndView("redirect:/ViewInspectionSheet.in", model);
	}
	
	@RequestMapping("/ShowVehicleInspectionSheet")
	public ModelAndView ShowVehicleInspectionSheet(@RequestParam("vehid") final Integer VehicleID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		VehicleDto				vehicle	= null;
		List<VehicleInspectionSheet>		inspectionSheetList		= null;
		HashMap<Long, VehicleGroup>			vehicleGroupHM			= null;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vehicle	= vehicleService.Get_Vehicle_Header_Details(VehicleID);
			
			if(vehicle != null) {
				model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicle));
				
				/* list of driver reminder */
				VehicleInspctionSheetAsingmentDto	asingmentDto	=	asingmentService.getVehicleInspctionSheetAsingmentbyVid(VehicleID, userDetails.getCompany_id());
				
				if(asingmentDto == null) {
					return new ModelAndView("redirect:/AsignVehicleInspectionSheet.in?vehid="+VehicleID, model);
				}
				
				model.put("asingmentDto", asingmentDto);
				
				Object[] count = vehicleService.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(VehicleID);
				model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(VehicleID, userDetails.getCompany_id()));
				model.put("photo_Count", (Long) count[0]);
				model.put("purchase_Count", (Long) count[1]);
				model.put("reminder_Count", (Long) count[2]);
				model.put("fuelEntrie_Count", (Long) count[3]);
				model.put("serviceEntrie_Count", (Long) count[4]);
				model.put("serviceReminder_Count", (Long) count[5]);
				model.put("tripsheet_Count", (Long) count[6]);
				model.put("workOrder_Count", (Long) count[7]);
				model.put("issues_Count", (Long) count[8]);
				model.put("odometerhis_Count", (Long) count[9]);
				model.put("comment_Count", (Long) count[10]);
				model.put("breakDownCount", ((Long) count[4]+(Long) count[8]));
				model.put("dseCount", (Long) count[12]);
			}
			
			vehicleGroupHM		=	vehicleGroupService.getVehicleGroupHMByCompanyId(userDetails.getCompany_id());
			inspectionSheetList	=	vehicleInspectionSheetService.getListVehicleInspectionSheet(userDetails.getCompany_id());
			
			HashMap<String, Object> 	configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			model.put("configuration", configuration);
			model.put("inspectionSheetList", vehicleInspectionBL.getVehicleInspectionSheetDtoList(vehicleGroupHM, inspectionSheetList));

		} catch (Exception e) {
			System.err.println("Exception : "+e);
			LOGGER.error("Add Vehicle Purchase Info Page:", e);

		}
		return new ModelAndView("ShowVehicleAssignment", model);
	}
	
	@RequestMapping("/AsignVehicleInspectionSheet")
	public ModelAndView AsignVehicleInspectionSheet(@RequestParam("vehid") final Integer VehicleID,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		VehicleDto				vehicle	= null;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vehicle	= vehicleService.Get_Vehicle_Header_Details(VehicleID);
			
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			model.put("configuration", configuration);
			if(vehicle != null) {
				model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicle));
			
				
				Object[] count = vehicleService.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(VehicleID);
				model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(VehicleID, userDetails.getCompany_id()));
				model.put("photo_Count", (Long) count[0]);
				model.put("purchase_Count", (Long) count[1]);
				model.put("reminder_Count", (Long) count[2]);
				model.put("fuelEntrie_Count", (Long) count[3]);
				model.put("serviceEntrie_Count", (Long) count[4]);
				model.put("serviceReminder_Count", (Long) count[5]);
				model.put("tripsheet_Count", (Long) count[6]);
				model.put("workOrder_Count", (Long) count[7]);
				model.put("issues_Count", (Long) count[8]);
				model.put("odometerhis_Count", (Long) count[9]);
				model.put("comment_Count", (Long) count[10]);
				model.put("breakDownCount", ((Long) count[4]+(Long) count[8]));
				model.put("dseCount", (Long) count[12]);
			}

		} catch (Exception e) {
			System.err.println("Exception : "+e);
			LOGGER.error("Add Vehicle Purchase Info Page:", e);

		}
		return new ModelAndView("AsignVehicleInspectionSheet", model);
	}
	
	
	@RequestMapping(value = "/getInspectionSheetList", method = RequestMethod.POST)
	public void getTripRouteList(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<VehicleInspectionSheet> inspectionSheetList = new ArrayList<VehicleInspectionSheet>();
		List<VehicleInspectionSheet> inspectionSheet = vehicleInspectionSheetService.getListVehicleInspectionSheet(term, userDetails.getCompany_id());
		if (inspectionSheet != null && !inspectionSheet.isEmpty()) {
			for (VehicleInspectionSheet Route : inspectionSheet) {

				VehicleInspectionSheet Dto = new VehicleInspectionSheet();
				
					Dto.setVehicleInspectionSheetId(Route.getVehicleInspectionSheetId());
					Dto.setInspectionSheetName(Route.getInspectionSheetName());
					Dto.setVehicleGroupId(Route.getVehicleGroupId());

				inspectionSheetList.add(Dto);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(inspectionSheetList));
	}

	@RequestMapping(value = "/asignSheetToVehicle", method = RequestMethod.POST)
	public ModelAndView asignSheetToVehicle(@ModelAttribute("command") VehicleInspctionSheetAsingmentDto vehicleInspectionSheetAssignment, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<VehicleInspectionSheetToParameter> emptySheet			=   null;
		Vehicle vehicle = null; 
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			emptySheet = VehicleInspectionSheetToParameterRepository.getAllParametersInVehicleInspectionSheets(vehicleInspectionSheetAssignment.getInspectionSheetId(), userDetails.getCompany_id());
			if(emptySheet.isEmpty()) {
				model.put("emptySheetAssignment", true);
				return new ModelAndView("redirect:/ShowVehicleInspectionSheet.in?vehid="+vehicleInspectionSheetAssignment.getVid(), model);
			}
			
			VehicleInspctionSheetAsingmentDto	asingmentDto	=	asingmentService.getVehicleInspctionSheetAsingmentbyVid(vehicleInspectionSheetAssignment.getVid(), userDetails.getCompany_id());
			if(asingmentDto == null) {
				vehicle=vehicleRepository.getVehicel(vehicleInspectionSheetAssignment.getVid(), userDetails.getCompany_id());
				if(vehicle != null)
				vehicleInspectionSheetAssignment.setVehicleTypeId(vehicle.getVehicleTypeId());
				vehicleInspectionSheetService.addInspectionSheetToVehicle(vehicleInspectionSheetAssignment);
				
			}else {
				return new ModelAndView("redirect:/ShowVehicleInspectionSheet.in?vehid="+vehicleInspectionSheetAssignment.getVid(), model);
			}
			
		} catch (Exception e) {
			LOGGER.error("Asign Sheet To Vehicle:", e);
			model.put("alreadyJobType", true);
			return new ModelAndView("redirect:/ShowVehicleInspectionSheet.in?vehid="+vehicleInspectionSheetAssignment.getVid(), model);
		}
		return new ModelAndView("redirect:/ShowVehicleInspectionSheet.in?vehid="+vehicleInspectionSheetAssignment.getVid(), model);
	}
	
	@Transactional
	@RequestMapping(value = "/deleteSheetAssignment")
	public ModelAndView deleteSheetAssignment(@RequestParam("ID") final Long asingmentId,
			@RequestParam("vid") final Integer vid, final HttpServletResponse result) {
		Map<String, Object> model = new HashMap<String, Object>();

			try {
				CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String todaysDate = dateFormat.format(new Date());
					
				VehicleInspctionSheetAsingmentDto	asingmentDto	=	asingmentService.getVehicleInspctionSheetAsingmentbyVid(vid, userDetails.getCompany_id());
				if(asingmentDto != null) {
					int count =1;
					asingmentService.deleteVehicleAssignment(asingmentId, vid);
					
					vehicleInspectionSheetService.substractNoOfVehicleAssignment(asingmentDto.getInspectionSheetId(),count);
					
					VehicleDailyInspectionRepository.deleteInspectionByVehicle(vid, dateFormat.parse(todaysDate), userDetails.getCompany_id());
				}
					

				} catch (Exception e) {

					model.put("error", true);
					return new ModelAndView("redirect:/ShowVehicleInspectionSheet.in?vehid="+vid, model);
				}
				model.put("deleteSheetAssignment", true);
				
			return new ModelAndView("redirect:/ShowVehicleInspectionSheet.in?vehid="+vid, model);
	}
	
	
	@RequestMapping(value = "/VehicleInspection", method = RequestMethod.GET)
	public ModelAndView VehicleInspection(@ModelAttribute("command") InspectionParameter inspectionParameter,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
				long todaysCount	=	asingmentService.getTodaysVehicleInspectionCount(userDetails.getCompany_id(), DateTimeUtility.geTimeStampFromDate(new Date()));
				List<VehicleInspectionCompletionDetailsDto> inspectedList		  =	vehicleInspectionCompletionDetailsService.getTodaysInspectedList(userDetails.getCompany_id(), DateTimeUtility.geTimeStampFromDate(new Date()));
				List<VehicleInspctionSheetAsingmentDto> pendingInspectionList = vehicleInspectionCompletionDetailsService.getTodaysInspectionPendingList(userDetails.getCompany_id(), DateTimeUtility.geTimeStampFromDate(new Date()));	
				List<VehicleInspectionCompletionDetailsDto> inspectedSavedList		  =	vehicleInspectionCompletionDetailsService.getTodaysSavedInspectedList(userDetails.getCompany_id(), DateTimeUtility.geTimeStampFromDate(new Date()));
				
				if(inspectedList != null && !inspectedList.isEmpty()) {
					model.put("inspectedCount", inspectedList.size());
				}else {
					model.put("inspectedCount", 0);
				}
				
				if(pendingInspectionList != null && !pendingInspectionList.isEmpty()) {
					model.put("pendingInspectionList", pendingInspectionList.size());
				}else {
					model.put("pendingInspectionList", 0);
				}
				
				if(inspectedSavedList != null && !inspectedSavedList.isEmpty()) {
					model.put("inspectedSavedListCount", inspectedSavedList.size());
				}else {
					model.put("inspectedSavedListCount", 0);
				}
				
				model.put("todaysCount", todaysCount);
			
		} catch (Exception e) {
			LOGGER.error("ViewInspectionSheet Page:", e);
		}
		return new ModelAndView("VehicleInspection", model);
	}
	
	
	@RequestMapping(value = "/ViewVehicleInspectionList", method = RequestMethod.GET)
	public ModelAndView ViewVehicleInspectionList(@ModelAttribute("command") InspectionParameter inspectionParameter,
			BindingResult result) throws Exception {
		Map<String, Object> 	model 		= new HashMap<String, Object>();
		CustomUserDetails		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long 					todaysCount	= 0;
		long 					totalMissed	= 0;
		Integer 				totalSkiped	= 0;
		try {	
			Collection<GrantedAuthority>	permission				= userDetails.getGrantedAuthoritiesList();	
			HashMap<String, Object> 		configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			HashMap<String, Object> 		configInspection			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			List<VehicleDailyInspectionDto>  inspectionList 		= VehicleDailyInspectionService.getTodaysVehicleInspectionListNew(userDetails,permission,configuration);
			List<VehicleDailyInspectionDto>  inspectedList		    = VehicleDailyInspectionService.getTodaysInspectedListNew(userDetails,permission,configuration);
			List<VehicleDailyInspectionDto>  pendingInspectionList  = VehicleDailyInspectionService.getTodaysInspectionPendingListNew(userDetails,permission,configuration);	
			List<VehicleDailyInspectionDto>  inspectedSavedList	    = VehicleDailyInspectionService.getTodaysSavedInspectedListNew(userDetails,permission,configuration);
			
			if((boolean) configInspection.getOrDefault("skipInspection", false)) {
				List<VehicleDailyInspectionDto>  inspectionSkipedList	    = VehicleDailyInspectionService.getTodaysInspectionSkipedList(userDetails,permission,configuration);
				if(inspectionSkipedList != null && !inspectionSkipedList.isEmpty())
					totalSkiped = inspectionSkipedList.size();
				model.put("totalSkiped", totalSkiped);	
			}
			model.put("configInspection", configInspection);
			
			if(inspectionList == null) {
				inspectionList = new ArrayList<VehicleDailyInspectionDto>();
			}
			
			if(inspectedSavedList != null && !inspectedSavedList.isEmpty())
				inspectionList.addAll(inspectedSavedList);
			
			if(inspectedList != null)
				inspectionList.addAll(inspectedList);
			
				todaysCount	=	inspectionList.size();


			model.put("todaysCount", todaysCount);
			model.put("inspectionList", inspectionList);

			if(inspectedList != null && !inspectedList.isEmpty()) {
				model.put("inspectedCount", inspectedList.size());
			}else {
				model.put("inspectedCount", 0);
			}

			if(pendingInspectionList != null && !pendingInspectionList.isEmpty()) {
				model.put("pendingInspectionList", pendingInspectionList.size());
			}else {
				model.put("pendingInspectionList", 0);
			}

			if(inspectedSavedList != null && !inspectedSavedList.isEmpty()) {
				model.put("inspectedSavedListCount", inspectedSavedList.size());
			}else {
				model.put("inspectedSavedListCount", 0);
			}

			if((boolean)configuration.get("branchWiseVehicleInspection")  && !permission.contains(new SimpleGrantedAuthority("SHOW_ALL_BRANCH_INSPECTION"))){
				totalMissed = VehicleDailyInspectionRepository.totalCountOfMissedInspection(userDetails.getCompany_id(), DateTimeUtility.getCurrentDate(), VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_INSPECTED,userDetails.getId());
			}else {
				totalMissed = VehicleDailyInspectionRepository.totalCountOfMissedInspection(userDetails.getCompany_id(), DateTimeUtility.getCurrentDate(), VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_INSPECTED);
				
			}
			
			model.put("totalMissedInspectionCount", totalMissed);
			model.put("currentDate", DateTimeUtility.getCurrentDate());

		} catch (Exception e) {
			LOGGER.error("ViewInspectionSheet Page:", e);
		}
		return new ModelAndView("ViewVehicleInspectionList", model);
	}
	
	@RequestMapping(value = "/ViewVehicleInspectedList", method = RequestMethod.GET)
	public ModelAndView ViewVehicleInspectedList(@ModelAttribute("command") InspectionParameter inspectionParameter,
			BindingResult result) {
		Map<String, Object> 	model 		= new HashMap<String, Object>();
		CustomUserDetails		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long 					todaysCount	= 0;
		long 					totalMissed	= 0;
		Integer 				totalSkiped	= 0;
		try {
			Collection<GrantedAuthority>	permission				= userDetails.getGrantedAuthoritiesList();	
			HashMap<String, Object> 		configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			HashMap<String, Object> 		configInspection			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			List<VehicleDailyInspectionDto>  inspectionList 		= VehicleDailyInspectionService.getTodaysVehicleInspectionListNew(userDetails,permission,configuration);
			List<VehicleDailyInspectionDto>  inspectedList		    = VehicleDailyInspectionService.getTodaysInspectedListNew(userDetails,permission,configuration);
			List<VehicleDailyInspectionDto>  pendingInspectionList  = VehicleDailyInspectionService.getTodaysInspectionPendingListNew(userDetails,permission,configuration);	
			List<VehicleDailyInspectionDto>  inspectedSavedList	    = VehicleDailyInspectionService.getTodaysSavedInspectedListNew(userDetails,permission,configuration);
			
			if((boolean) configInspection.getOrDefault("skipInspection", false)) {
				List<VehicleDailyInspectionDto>  inspectionSkipedList	    = VehicleDailyInspectionService.getTodaysInspectionSkipedList(userDetails,permission,configuration);
				if(inspectionSkipedList != null && !inspectionSkipedList.isEmpty())
					totalSkiped = inspectionSkipedList.size();
				model.put("totalSkiped", totalSkiped);	
			}
				
				if(inspectionList == null) {
					inspectionList = new ArrayList<VehicleDailyInspectionDto>();
				}
					
				if(inspectedList != null)
					inspectionList.addAll(inspectedList);
				
				if(inspectedSavedList != null)
					inspectionList.addAll(inspectedSavedList);
				
					
					todaysCount	=	inspectionList.size();
				
				model.put("todaysCount", todaysCount);
				model.put("inspectionList", inspectionList);
				model.put("inspectedList", inspectedList);
				
				if(inspectedList != null && !inspectedList.isEmpty()) {
					model.put("inspectedCount", inspectedList.size());
				}else {
					model.put("inspectedCount", 0);
				}
				
				if(pendingInspectionList != null && !pendingInspectionList.isEmpty()) {
					model.put("pendingInspectionList", pendingInspectionList.size());
				}else {
					model.put("pendingInspectionList", 0);
				}
				
				if(inspectedSavedList != null && !inspectedSavedList.isEmpty()) {
					model.put("inspectedSavedListCount", inspectedSavedList.size());
				}else {
					model.put("inspectedSavedListCount", 0);
				}
				
				
				model.put("configInspection", configInspection);	
				
				totalMissed = VehicleDailyInspectionRepository.totalCountOfMissedInspection(userDetails.getCompany_id(), format.parse(DateTimeUtility.getCurrentDate()+" 00:00:00"), VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_INSPECTED);
				model.put("totalMissedInspectionCount", totalMissed);
				model.put("currentDate", DateTimeUtility.getCurrentDate());
			
		} catch (Exception e) {
			LOGGER.error("ViewInspectionSheet Page:", e);
		}
		return new ModelAndView("ViewVehicleInspectedList", model);
	}
	
	@RequestMapping(value = "/ViewVehicleInspectionPendingList", method = RequestMethod.GET)
	public ModelAndView ViewVehicleInspectionPendingList(@ModelAttribute("command") InspectionParameter inspectionParameter,
			BindingResult result) {
		Map<String, Object> 	model 		= new HashMap<String, Object>();
		CustomUserDetails		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long 					todaysCount	= 0;
		long 					totalMissed	= 0;
		Integer 				totalSkiped	= 0;
		try {
			Collection<GrantedAuthority>	permission				= userDetails.getGrantedAuthoritiesList();	
			HashMap<String, Object> 		configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			HashMap<String, Object> 		configInspection			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			List<VehicleDailyInspectionDto>  inspectionList 		= VehicleDailyInspectionService.getTodaysVehicleInspectionListNew(userDetails,permission,configuration);
			List<VehicleDailyInspectionDto>  inspectedList		    = VehicleDailyInspectionService.getTodaysInspectedListNew(userDetails,permission,configuration);
			List<VehicleDailyInspectionDto>  pendingInspectionList  = VehicleDailyInspectionService.getTodaysInspectionPendingListNew(userDetails,permission,configuration);	
			List<VehicleDailyInspectionDto>  inspectedSavedList	    = VehicleDailyInspectionService.getTodaysSavedInspectedListNew(userDetails,permission,configuration);
			
			if((boolean) configInspection.getOrDefault("skipInspection", false)) {
				List<VehicleDailyInspectionDto>  inspectionSkipedList	    = VehicleDailyInspectionService.getTodaysInspectionSkipedList(userDetails,permission,configuration);
				if(inspectionSkipedList != null && !inspectionSkipedList.isEmpty())
					totalSkiped = inspectionSkipedList.size();
				model.put("totalSkiped", totalSkiped);	
			}
			
			
			if(inspectionList == null ) {
				inspectionList	= new ArrayList<VehicleDailyInspectionDto>();
			}
			if(inspectedList != null) {
				inspectionList.addAll(inspectedList);
			}
			if(inspectedSavedList != null)
				inspectionList.addAll(inspectedSavedList);

			todaysCount	=	inspectionList.size();

			model.put("todaysCount", todaysCount);
			model.put("inspectionList", inspectionList);
			model.put("inspectedSavedList", inspectedSavedList);
			model.put("pendingInspectionList", pendingInspectionList);

			if(inspectedList != null && !inspectedList.isEmpty()) {
				model.put("inspectedCount", inspectedList.size());
			}else {
				model.put("inspectedCount", 0);
			}

			if(pendingInspectionList != null && !pendingInspectionList.isEmpty()) {
				model.put("pendingInspectionListCount", pendingInspectionList.size());
			}else {
				model.put("pendingInspectionListCount", 0);
			}

			if(inspectedSavedList != null && !inspectedSavedList.isEmpty()) {
				model.put("inspectedSavedListCount", inspectedSavedList.size());
			}else {
				model.put("inspectedSavedListCount", 0);
			}
			model.put("configInspection", configInspection);
			totalMissed = VehicleDailyInspectionRepository.totalCountOfMissedInspection(userDetails.getCompany_id(), format.parse(DateTimeUtility.getCurrentDate()+" 00:00:00"), VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_INSPECTED);
			model.put("totalMissedInspectionCount", totalMissed);
			model.put("currentDate", DateTimeUtility.getCurrentDate());

		} catch (Exception e) {
			LOGGER.error("ViewVehicleInspectionPendingList Page:", e);
		}
		return new ModelAndView("ViewVehicleInspectionPendingList", model);
	}
	
	@RequestMapping(value = "/ViewVehicleInspectionSavedList", method = RequestMethod.GET)
	public ModelAndView ViewVehicleInspectionSavedList(@ModelAttribute("command") InspectionParameter inspectionParameter,
			BindingResult result) {
		Map<String, Object> 	model 		= new HashMap<String, Object>();
		CustomUserDetails		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long 					todaysCount	= 0;
		long 					totalMissed	= 0;
		Integer 				totalSkiped	= 0;
		try {
			Collection<GrantedAuthority>	permission				= userDetails.getGrantedAuthoritiesList();	
			HashMap<String, Object> 		configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			HashMap<String, Object> 		configInspection		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			List<VehicleDailyInspectionDto>  inspectionList 		= VehicleDailyInspectionService.getTodaysVehicleInspectionListNew(userDetails,permission,configuration);
			List<VehicleDailyInspectionDto>  inspectedList		    = VehicleDailyInspectionService.getTodaysInspectedListNew(userDetails,permission,configuration);
			List<VehicleDailyInspectionDto>  pendingInspectionList  = VehicleDailyInspectionService.getTodaysInspectionPendingListNew(userDetails,permission,configuration);	
			List<VehicleDailyInspectionDto>  inspectedSavedList	    = VehicleDailyInspectionService.getTodaysSavedInspectedListNew(userDetails,permission,configuration);

			if((boolean) configInspection.getOrDefault("skipInspection", false)) {
				List<VehicleDailyInspectionDto>  inspectionSkipedList	    = VehicleDailyInspectionService.getTodaysInspectionSkipedList(userDetails,permission,configuration);
				if(inspectionSkipedList != null && !inspectionSkipedList.isEmpty())
					totalSkiped = inspectionSkipedList.size();
				model.put("totalSkiped", totalSkiped);	
			}
			
			if(inspectionList == null ) {
				inspectionList	= new ArrayList<VehicleDailyInspectionDto>();
			}
			if(inspectedList != null) {
				inspectionList.addAll(inspectedList);
			}
			if(inspectedSavedList != null)
				inspectionList.addAll(inspectedSavedList);

			todaysCount	=	inspectionList.size();

			model.put("todaysCount", todaysCount);

			model.put("inspectionList", inspectionList);
			model.put("inspectedSavedList", inspectedSavedList);
			model.put("pendingInspectionList", pendingInspectionList);

			if(inspectedList != null && !inspectedList.isEmpty()) {
				model.put("inspectedCount", inspectedList.size());
			}else {
				model.put("inspectedCount", 0);
			}

			if(pendingInspectionList != null && !pendingInspectionList.isEmpty()) {
				model.put("pendingInspectionListCount", pendingInspectionList.size());
			}else {
				model.put("pendingInspectionListCount", 0);
			}

			if(inspectedSavedList != null && !inspectedSavedList.isEmpty()) {
				model.put("inspectedSavedListCount", inspectedSavedList.size());
			}else {
				model.put("inspectedSavedListCount", 0);
			}
			model.put("configInspection", configInspection);
			totalMissed = VehicleDailyInspectionRepository.totalCountOfMissedInspection(userDetails.getCompany_id(), format.parse(DateTimeUtility.getCurrentDate()+" 00:00:00"), VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_INSPECTED);
			model.put("totalMissedInspectionCount", totalMissed);
			model.put("currentDate", DateTimeUtility.getCurrentDate());

		} catch (Exception e) {
			LOGGER.error("ViewVehicleInspectionSavedList Page:", e);
		}
		return new ModelAndView("ViewVehicleInspectionSavedList", model);
	}
	
	@RequestMapping(value = "/inspectVehicle", method = RequestMethod.GET)
	public ModelAndView inspectVehicle(@RequestParam("ID") final Long asingmentId,
			@RequestParam("vid") final Integer vid, @RequestParam("DR") final String dateRange, final HttpServletResponse result
			, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails	= null;
		VehicleInspctionSheetAsingmentDto				asingmentDto					= null;
		List<VehicleInspectionSheetToParameterDto>		inspectionSheetToParameter		= null;
		VehicleInspectionCompletionDetails           inspectionCompletionDetails = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> 		configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			
			UserProfileDto Orderingname = userProfileService
					.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());
			asingmentDto	=	asingmentService.getVehicleInspctionSheetAsingmentbyAssignmentId(asingmentId, userDetails.getCompany_id());
				
			inspectionSheetToParameter	= sheetToParameterService.getVehicleInspectionSheetToParameterListToINspect(asingmentId, vid, userDetails.getCompany_id(), dateRange);
			
			List<InspectionCompletionToParameter>	toParameterList	=	completionToParameterService.getInspectionCompletionToParameterByDateAndVid(vid, DateTimeUtility.getTimeStamp(dateRange, DateTimeUtility.YYYY_MM_DD));
			
			if((boolean) configuration.getOrDefault("failedParameterPenalty", false)) {
				inspectionCompletionDetails=inspectionCompletionDetailsRepository.getTodaysInspectionCompletionDetails(asingmentId, userDetails.getCompany_id() ,DateTimeUtility.getCurrentDate());
				model.put("inspectionCompletionDetails",inspectionCompletionDetails);
			}
			ObjectMapper objectMapper = new ObjectMapper();
			
			model.put("place", Orderingname.getBranch_name());
			model.put("placeId", Orderingname.getBranch_id());
			model.put("asingmentDto", asingmentDto);
			model.put("toParameterList", objectMapper.writeValueAsString(toParameterList));
			model.put("inspectionSheetToParameter", inspectionSheetToParameter);
			model.put("inspectedBy", userDetails.getFirstName());
			model.put("inspectedById", userDetails.getId());
			model.put("todaysDate", DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getCurrentTimeStamp(), DateTimeUtility.DD_MM_YYYY));
			model.put("dateRange", dateRange);
			model.put("dateRangeFormat",dateFormat2.format(dateFormat.parse(dateRange)));
			model.put("configuration",configuration);
			model.put("accessToken", Utility.getUniqueToken(request));
			model.put("penaltyAmountDouble", configuration.getOrDefault("penaltyAmount", 1000));
			
		} catch (Exception e) {
			LOGGER.error("ViewInspectionSheet Page:", e);
		}
		return new ModelAndView("VehicleInspectionAdd", model);
	}
	
	@RequestMapping(value = "/editInspectionDetails", method = RequestMethod.GET)
	public ModelAndView editInspectionDetails(@RequestParam("ID") final Long asingmentId,
			@RequestParam("vid") final Integer vid, final HttpServletResponse result) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails	= null;
		VehicleInspctionSheetAsingmentDto				asingmentDto					= null;
		VehicleInspectionCompletionDetails           inspectionCompletionDetails = null;
		List<VehicleInspectionSheetToParameterDto>		inspectionSheetToParameter		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> 		configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			UserProfileDto Orderingname = userProfileService
					.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());
			asingmentDto	=	asingmentService.getVehicleInspctionSheetAsingmentbyAssignmentId(asingmentId, userDetails.getCompany_id());
			inspectionSheetToParameter	= sheetToParameterService.getVehicleInspectionSheetToParameterListToINspect(asingmentId, vid, userDetails.getCompany_id(), DateTimeUtility.getCurrentDateInString());
			List<InspectionCompletionToParameter>	toParameterList	=	completionToParameterService.getInspectionCompletionToParameterByDateAndVid(vid, DateTimeUtility.geTimeStampFromDate(new Date()));
			if((boolean) configuration.getOrDefault("failedParameterPenalty", false)) {
				inspectionCompletionDetails=inspectionCompletionDetailsRepository.getTodaysInspectionCompletionDetails(asingmentId, userDetails.getCompany_id(),DateTimeUtility.getCurrentDate());
				model.put("inspectionCompletionDetails",inspectionCompletionDetails);
			}
			ObjectMapper objectMapper = new ObjectMapper();
			model.put("place", Orderingname.getBranch_name());
			model.put("placeId", Orderingname.getBranch_id());
			model.put("asingmentDto", asingmentDto);
			model.put("toParameterList", objectMapper.writeValueAsString(toParameterList));
			model.put("inspectionSheetToParameter", inspectionSheetToParameter);
			model.put("inspectedBy", userDetails.getFirstName());
			model.put("inspectedById", userDetails.getId());
			model.put("todaysDate", DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getCurrentTimeStamp(), DateTimeUtility.DD_MM_YYYY));
			model.put("dateRange", DateTimeUtility.getCurrentDate());
			model.put("configuration",configuration);
			model.put("penaltyAmountDouble", configuration.getOrDefault("penaltyAmount", 1000));
			
		} catch (Exception e) {
			LOGGER.error("ViewInspectionSheet Page:", e);
		}
		return new ModelAndView("VehicleInspectionEdit", model);
	}
	
	@Transactional
	@RequestMapping(value = "/saveVehicleInspectionDetails", method = RequestMethod.POST)
	public ModelAndView saveVehicleInspectionDetails(@ModelAttribute("command") VehicleInspectionCompletionDetails asingmentDto,
			@RequestParam("inspectionSheetToParameterId") final String[] inspectionSheetToParameterId,
			@RequestParam("testResult") final String testResult,
			@RequestParam("remark") final String[] remark,
			@RequestParam("compressed") final String[] compressed,
			@RequestParam("fileNames") final String fileNames,
			@RequestParam("saveTypeId") final short saveTypeId,
			@RequestParam("parameterIds") final String parameterIds,
			@RequestParam("completionToParameterId") final String[] completionToParameterIds,
			@RequestParam("dateRange") final String dateRange,
			@RequestParam("totalPenalty") final Double totalPenalty,
			@RequestParam("vehicleInspctionSheetAsingmentId") final Long vehicleInspctionSheetAsingmentId,
			@RequestParam("multiCompressed") final String[] multiCompressed,
			final HttpServletRequest request)
					throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails							userDetails				= null;
		String[] 									testResultArray			= null;
		String[] 									parameterInspectedIds	= null;
		VehicleDailyInspection						inspection				= null;
		InspectionToParameterDocument				document				= null;
		ValueObject                                 failedparameterIssues	= null;
		Issues                                      savedIssues             = null;
		try {

			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> 		configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			UserProfileDto profile = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());
			List<InspectionCompletionToParameter>	toParameterList	=	completionToParameterService.getInspectionCompletionToParameterByDateAndVid(asingmentDto.getVid(), DateTimeUtility.getTimeStamp(dateRange, DateTimeUtility.YYYY_MM_DD));

			HashMap<Long, InspectionCompletionToParameter>	hashMap	=	vehicleInspectionBL.getInspectionCompletionToParameterHM(toParameterList);
			List <InspectionToParameterDocument> imageList = new ArrayList<>();
			InspectionToParameterDocument doc = null;
			if((boolean)configuration.getOrDefault("multiCompress", false) && multiCompressed != null &&multiCompressed.length>0) {
				for(String str : multiCompressed) {
					if(!str.trim().equals("")) {
						doc = new InspectionToParameterDocument();
						String[] strArray =str.split("-");
						doc.set_id(Long.parseLong(strArray[0]));
						doc.setContent(ByteConvertor.base64ToByte(strArray[1]));
						imageList.add(doc);
					}
				}
			}

			Map<Long, List<InspectionToParameterDocument>> imageHash=vehicleInspectionBL.getImageHashMap(imageList);

			if(asingmentDto != null) {
				asingmentDto	= vehicleInspectionBL.getVehicleInspectionCompletionDetailsModel(asingmentDto, userDetails, profile, dateRange);
				asingmentDto.setInspectionStatusId(saveTypeId);
				vehicleInspectionCompletionDetailsService.saveVehicleInspectionCompletionDetails(asingmentDto);
				vehicleInspctionSheetAsingmentRepository.updateVehicleAssignmentStatus(saveTypeId, vehicleInspctionSheetAsingmentId, userDetails.getCompany_id());
				inspection = VehicleDailyInspectionRepository.findByDate(asingmentDto.getVid(), format.parse(dateRange+" 00:00:00"), userDetails.getCompany_id());
				if(inspection != null) {
					VehicleDailyInspectionRepository.updateInspectionStatus(saveTypeId, asingmentDto.getVid(), format.parse(dateRange+" 00:00:00"), userDetails.getCompany_id());
				}

				if(inspectionSheetToParameterId != null) {

					testResultArray			= testResult.split(",");
					parameterInspectedIds	= parameterIds.split(",");

					InspectionCompletionToParameter	completionToParameter	= null;
					for(int i = 0; i< inspectionSheetToParameterId.length; i++) {
						completionToParameter	= new InspectionCompletionToParameter();
						if(hashMap != null && hashMap.size() > 0 && completionToParameterIds.length >0 && !completionToParameterIds[i].trim().equals("")) 
							completionToParameter.setCompletionToParameterId(Long.parseLong(completionToParameterIds[i]+""));
						completionToParameter.setCompletionDetailsId(asingmentDto.getCompletionDetailsId());
						completionToParameter.setInspectionSheetId(asingmentDto.getInspectionSheetId());
						completionToParameter.setInspectionParameterId(Long.parseLong(inspectionSheetToParameterId[i]+""));

						if(hashMap != null && hashMap.size() > 0) {
							if(hashMap.get(completionToParameter.getCompletionToParameterId()) != null) {
								completionToParameter.setDocumentUploaded(hashMap.get(completionToParameter.getCompletionToParameterId()).isDocumentUploaded());
								completionToParameter.setDocumentId(hashMap.get(completionToParameter.getCompletionToParameterId()).getDocumentId());
							}
						}
						for(int j = 0; j < parameterInspectedIds.length; j++) {
							if(!parameterInspectedIds[j].equals("") && Long.parseLong(parameterInspectedIds[j]) == completionToParameter.getInspectionParameterId()) {

								if(Boolean.parseBoolean(testResultArray[j]+"")) {
									completionToParameter.setIsInspectionSuccess((short) 1);
									if(saveTypeId == 2 && (boolean)configuration.getOrDefault("createIssueOnparameterFail",false)) {
										issuesService.deleteExistingIssueIfUpdatedAsSuccess(completionToParameter.getCompletionToParameterId(), asingmentDto.getVid());
									}
								}else {
									completionToParameter.setIsInspectionSuccess((short) 2);
									if(saveTypeId == 2 && (boolean)configuration.getOrDefault("createIssueOnparameterFail",false)) {
										failedparameterIssues = new ValueObject();
										failedparameterIssues=	issuesService.prepareValueObject(completionToParameter.getInspectionParameterId(), asingmentDto.getVid(), remark[i] ,asingmentDto.getInspectionSheetId() ,completionToParameter.getCompletionToParameterId());
										ValueObject object	= issuesService.saveIssuesDetails(failedparameterIssues);
										savedIssues = (Issues)object.get("savedIssues");
										if(savedIssues != null)
											completionToParameter.setIssueId(savedIssues.getISSUES_ID());
										//completionToParameterService.savePenalty(asingmentDto.getInspectionSheetId(),completionToParameter.getInspectionParameterId(),asingmentDto.getVid(),vehicleInspctionSheetAsingmentId,(double)configuration.getOrDefault("penaltyAmount",0.0));
									}
								}
								completionToParameter.setInspectionDone(true);
								break;
							}

						}

						completionToParameter.setCompletionDateTime(DateTimeUtility.getCurrentTimeStamp());
						if(remark.length > i && !remark[i].isEmpty()) {
							completionToParameter.setDescription(remark[i]);
						}

						completionToParameter.setVid(asingmentDto.getVid());
						completionToParameter.setCompanyId(userDetails.getCompany_id());
						completionToParameter.setInspectedById(userDetails.getId());
						completionToParameterService.saveInspectionCompletionToParameter(completionToParameter);
						if((boolean) configuration.getOrDefault("compressImage", false)) {
							if(compressed != null && compressed.length != 0) {
								if(!compressed[i].isEmpty()) {
									document	= new InspectionToParameterDocument();
									byte[] bytes = ByteConvertor.base64ToByte( compressed[i]);
									document.setCompanyId(userDetails.getCompany_id());
									document.setContent(bytes);
									document.setContentType("image/jpeg");
									document.setName("Paramater"+(i+1)+".jpg");
									document.setCompletionToParameterId(completionToParameter.getCompletionToParameterId());
									document.setMarkForDelete(false);
									try {
										documentService.save(document);
									} catch (Exception e) {
										LOGGER.error("Exception : ", e);
									}
									completionToParameterService.updateDocumentIdToParameter(completionToParameter.getCompletionToParameterId(), document.get_id());
								}
							}
						}else {
							if(imageHash != null && !imageHash.isEmpty()) {
								List<InspectionToParameterDocument> compressedDoc =imageHash.get(completionToParameter.getInspectionParameterId());
								if(compressedDoc != null && !compressedDoc.isEmpty()) {
									int k =0;
									for(InspectionToParameterDocument inspectionDoc :compressedDoc) {
										document	= new InspectionToParameterDocument(); 
										document.setCompanyId(userDetails.getCompany_id());
										document.setContent(inspectionDoc.getContent());
										document.setContentType("image/jpeg");
										document.setName("Paramater"+(k++)+".jpg");
										document.setCompletionToParameterId(completionToParameter.getCompletionToParameterId());
										document.setMarkForDelete(false);
										try {
											documentService.save(document);
										} catch (Exception e) {
											LOGGER.error("Exception : ", e);
										}
									}
									completionToParameterService.updateDocumentToParameter(completionToParameter.getCompletionToParameterId());
								}
							}
						}
					}
				}
				model.put("saved", true);
			}

		} catch (Exception e) {
			System.err.println("Exception : "+e);
			model.put("error", true);
			e.printStackTrace();
			return new ModelAndView("redirect:/ViewVehicleInspectionList.in", model);
		} finally {
			userDetails				= null;
			//sheetToParameter		= null;
			testResultArray			= null;
			parameterInspectedIds	= null;
		}

		return new ModelAndView("redirect:/ViewVehicleInspectionList.in", model);
		//return new ModelAndView("ViewInspectionSheet", model);
	}
	
	@RequestMapping(value = "/viewInspectVehicleDetails", method = RequestMethod.GET)
	public ModelAndView viewInspectVehicleDetails(@RequestParam("ID") final Long asingmentId,
			@RequestParam("vid") final Integer vid, final HttpServletResponse result) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		                        userDetails						= null;
		VehicleInspctionSheetAsingmentDto				asingmentDto					= null;
		List<VehicleInspectionSheetToParameterDto>		inspectionSheetToParameter		= null;
		VehicleInspectionReportDetails		            details							= null;
		double passPer 																	= 0.0;
		double failPer 																	= 0.0;
		double notTestPer 																= 0.0;
		VehicleInspectionCompletionDetails           inspectionCompletionDetails 		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> 		configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			model.put("configuration", configuration);
			UserProfileDto Orderingname = userProfileService
					.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());
			
			asingmentDto	=	asingmentService.getVehicleInspctionSheetAsingmentbyCompletionID(asingmentId, userDetails.getCompany_id());
				
			List<InspectionCompletionToParameterDto>	toParameterList	=	completionToParameterService.getInspectionCompletionToParameterDetails(vid, asingmentDto.getCompletionDetailsId());
			
			if(toParameterList != null && !toParameterList.isEmpty()) {
				details		= new VehicleInspectionReportDetails();
				details.setNoOfRecord(toParameterList.size());
				
				for (InspectionCompletionToParameterDto		toParameterDto : toParameterList) {
						
						if(toParameterDto.getIsInspectionSuccess() == 1) {
							details.setNoOfPassRecord(details.getNoOfPassRecord() + 1);
						}else if(toParameterDto.getIsInspectionSuccess() == 2) {
							details.setNoOfFailRecord(details.getNoOfFailRecord() + 1);
						}else {
							details.setNoOfNotTestedRecord(details.getNoOfNotTestedRecord() + 1);
						}
				}
				
				details.setNoOfTestedRecord(details.getNoOfRecord() - details.getNoOfNotTestedRecord());
				
				passPer				= (double) (details.getNoOfPassRecord() * 100 ) / details.getNoOfTestedRecord();
				failPer				= (double) (details.getNoOfFailRecord() * 100 )/ details.getNoOfTestedRecord();
				notTestPer			= (double) (details.getNoOfNotTestedRecord() * 100 )/ details.getNoOfRecord();
				
				details.setTestPassPercentage(passPer);
				details.setTestFailPercentage(failPer);
				details.setNotTestPercentage(notTestPer);
			}
			
			
			if((boolean) configuration.getOrDefault("failedParameterPenalty", false)) {
				inspectionCompletionDetails=inspectionCompletionDetailsRepository.getInspectionCompletionDetailsByComplitionId(asingmentId, userDetails.getCompany_id());
				model.put("inspectionCompletionDetails",inspectionCompletionDetails);
			}
			model.put("passPer", df2.format(passPer));
			model.put("failPer", df2.format(failPer));
			model.put("notTestPer", df2.format(notTestPer));
			model.put("place", Orderingname.getBranch_name());
			model.put("placeId", Orderingname.getBranch_id());
			model.put("asingmentDto", asingmentDto);
			model.put("toParameterList", toParameterList);
			model.put("inspectionSheetToParameter", inspectionSheetToParameter);
			model.put("inspectedBy", asingmentDto.getInspectedBy());
			model.put("inspectedById", asingmentDto.getInspectedById());
			model.put("todaysDate", DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getCurrentTimeStamp(), DateTimeUtility.DD_MM_YYYY));
			
		} catch (Exception e) {
			LOGGER.error("ViewInspectionSheet Page:", e);
		}
		return new ModelAndView("VehicleInspectionView", model);
	}
	
	// Image Document the Document id
	@RequestMapping("/downloadParameterDocument/{documentId}")
	public String download(@PathVariable("documentId") Long documentId, HttpServletResponse response) {

		try {
			if (documentId == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				InspectionToParameterDocument file = documentService.getVehicleParameterDocument(documentId);
				// Check if file is actually retrieved from database.
				if (file != null) {
					if (file.getContent() != null) {

						response.setHeader("Content-Disposition", "inline;filename=\"" + file.getName() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getContentType());
						response.getOutputStream().write(file.getContent());
						out.flush();
						out.close();

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Add Vehicle Status Page:", e);
		}
		return null;
	}
	
	
	@RequestMapping(value = "/EditInspectionSheet", method = RequestMethod.GET)
	public ModelAndView EditInspectionSheet(@RequestParam("ISID_id") final long ISID_id,
			@ModelAttribute("command") InspectionParameter inspectionParameter,
			BindingResult result) {
		Map<String, Object> 			model 					= new HashMap<String, Object>();
		CustomUserDetails				currentUser				= null;
		VehicleInspectionSheetDto		inspectionSheetListDto	= null;
		HashMap<Long, VehicleGroup>		vehicleGroupHM			= null;
		VehicleInspectionSheet			inspectionSheet			= null;
		HashMap<Long, VehicleType>  	vehicleTypeList			= null;
		try {
			currentUser	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object> 		configuration			= companyConfigurationService.getCompanyConfiguration(currentUser.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			model.put("configuration", configuration);
			
			inspectionSheet			= vehicleInspectionSheetService.getVehicleInspectionSheetById(ISID_id);
			if((boolean)configuration.getOrDefault("sheetToVehicleType",false)) {
				vehicleTypeList			=	vehicletypeService.getVehicleTypeHMByCompanyId(currentUser.getCompany_id());
				ValueObject valueObject =	asingmentService.getVehicleTypeInspectionSheetDto(vehicleTypeList, inspectionSheet);
				model.put("inspectionSheetList",valueObject.get("inspectionSheet"));
			
			}else {
				
			vehicleGroupHM			= vehicleGroupService.getVehicleGroupHMByCompanyId(currentUser.getCompany_id());
			inspectionSheetListDto 	= vehicleInspectionBL.getVehicleInspectionSheetDto(vehicleGroupHM, inspectionSheet);
			model.put("inspectionSheetList", inspectionSheetListDto);
			}
			model.put("ISID_id",ISID_id);
			
		} catch (Exception e) {
			LOGGER.error("EditInspectionSheet Page :", e);
		}
		return new ModelAndView("EditInspectionSheet", model);
	}
	
	@GetMapping(value="/AssignInspectionSheetToVehicleType")
	public ModelAndView AssignInspectionSheet(final HttpServletRequest request,@RequestParam("ISID_id")final long ISID_id ) throws Exception {
		Map<String, Object> model = new HashMap<>();
		VehicleInspectionSheet vehicleInspectionSheet= vehicleInspectionSheetService.getVehicleInspectionSheetById(ISID_id);
		model.put("sheetId", ISID_id);
		model.put("sheetName",vehicleInspectionSheet.getInspectionSheetName());
		
		return new ModelAndView("AssignInspectionSheetToVehicleType" ,model);
	}
	
	
	
	@RequestMapping(value = "/ViewMissedInspectionList", method = RequestMethod.GET)
	public ModelAndView ViewMissedInspectionList(@ModelAttribute("command") InspectionParameter inspectionParameter,
			BindingResult result) {
		Map<String, Object> model 	= new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission				= userDetails.getGrantedAuthoritiesList();	
			HashMap<String, Object> 		configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			
			model.put("vehicleList", VehicleDailyInspectionService.getMissedInspectedVehicleList(userDetails,permission,configuration));
			
		} catch (Exception e) {
			LOGGER.error("ViewMissedInspectionList Page:", e);
		}
		return new ModelAndView("ViewMissedInspectionList", model);
	}
	
	@RequestMapping(value = "/ViewVehicleInspection/{pageNumber}", method = RequestMethod.GET)
	public String VehicleFuelDetails(@PathVariable Integer pageNumber, @RequestParam("vid") Integer Vid, Model model) {
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Page<VehicleInspectionCompletionDetails> page = vehicleInspectionSheetService.getVehicleInspectionPage(Vid, pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);

			model.addAttribute("fuelEntrie_Count", page.getTotalElements());
			

			VehicleDto vehicle = VBL.prepare_Vehicle_Header_Fuel_Entries_Show(
					vehicleService.Get_Vehicle_Header_Fuel_Entries_Details(Vid, userDetails.getCompany_id()));
			model.addAttribute("vehicle", vehicle);
			// show the driver list in all

			model.addAttribute("inspectionList", vehicleInspectionSheetService.getVehicleInspectionPageList(Vid, pageNumber, userDetails.getCompany_id()));

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			LOGGER.error("Vehicle Page:", e);
			e.printStackTrace();
		}

		return "Show_Vehicle_Inspection";
	}
	
	@PostMapping(value="/asignSheetToVehicleType")
	public ModelAndView AsignSheetToVehicleType(@RequestParam("inspectionSheetId") final Long inspectionSheetId,
			@RequestParam("VehicleTypeSelect") final Long vehicleTypeSelect,@RequestParam("inspectionStartDateStr") final String inspectionDate ,@RequestParam("VehicleLocation") Integer VehicleLocation ,HttpServletRequest request  ) throws Exception {
		Map<String, Object> model = null;
		try {
			 model = new HashMap<String, Object>();
			VehicleInspectionSheet vehicleInspectionSheet= vehicleInspectionSheetService.getVehicleInspectionSheetById(inspectionSheetId);
			model.put("sheetId", inspectionSheetId);
			model.put("sheetName",vehicleInspectionSheet.getInspectionSheetName());
			model.put("status",  asingmentService.AsignSheetByVehicleType(inspectionSheetId, vehicleTypeSelect, inspectionDate,(short)1,VehicleLocation));
			
			return new ModelAndView("AssignInspectionSheetToVehicleType", model);
		} catch (Exception e) {
			throw e;
}


	
	}
	
	@RequestMapping(value = "/ViewSkippedInspectionList", method = RequestMethod.GET)
	public ModelAndView ViewSkippedInspectionList(@ModelAttribute("command") InspectionParameter inspectionParameter,
			BindingResult result) throws Exception {
		Map<String, Object> 	model 		= new HashMap<String, Object>();
		CustomUserDetails		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long 					todaysCount	= 0;
		long 					totalMissed	= 0;
		Integer 				totalSkiped	= 0;
		try {	
			Collection<GrantedAuthority>	permission				= userDetails.getGrantedAuthoritiesList();	
			HashMap<String, Object> 		configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			HashMap<String, Object> 		configInspection			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
			List<VehicleDailyInspectionDto>  inspectionList 		= VehicleDailyInspectionService.getTodaysVehicleInspectionListNew(userDetails,permission,configuration);
			List<VehicleDailyInspectionDto>  inspectedList		    = VehicleDailyInspectionService.getTodaysInspectedListNew(userDetails,permission,configuration);
			List<VehicleDailyInspectionDto>  pendingInspectionList  = VehicleDailyInspectionService.getTodaysInspectionPendingListNew(userDetails,permission,configuration);	
			List<VehicleDailyInspectionDto>  inspectedSavedList	    = VehicleDailyInspectionService.getTodaysSavedInspectedListNew(userDetails,permission,configuration);
			
			if((boolean) configInspection.getOrDefault("skipInspection", false)) {
				List<VehicleDailyInspectionDto>  inspectionSkipedList	    = VehicleDailyInspectionService.getTodaysInspectionSkipedList(userDetails,permission,configuration);
				model.put("inspectionList", inspectionSkipedList);
				if(inspectionSkipedList != null && !inspectionSkipedList.isEmpty())
					totalSkiped = inspectionSkipedList.size();
				model.put("totalSkiped", totalSkiped);	
			}
			model.put("configInspection", configInspection);
			
			if(inspectionList == null) {
				inspectionList = new ArrayList<VehicleDailyInspectionDto>();
			}
			
			if(inspectedSavedList != null)
				inspectionList.addAll(inspectedSavedList);
			
			if(inspectedList != null)
				inspectionList.addAll(inspectedList);
			
				todaysCount	=	inspectionList.size();


			model.put("todaysCount", todaysCount);

			if(inspectedList != null && !inspectedList.isEmpty()) {
				model.put("inspectedCount", inspectedList.size());
			}else {
				model.put("inspectedCount", 0);
			}

			if(pendingInspectionList != null && !pendingInspectionList.isEmpty()) {
				model.put("pendingInspectionList", pendingInspectionList.size());
			}else {
				model.put("pendingInspectionList", 0);
			}

			if(inspectedSavedList != null && !inspectedSavedList.isEmpty()) {
				model.put("inspectedSavedListCount", inspectedSavedList.size());
			}else {
				model.put("inspectedSavedListCount", 0);
			}

			if((boolean)configuration.get("branchWiseVehicleInspection")  && !permission.contains(new SimpleGrantedAuthority("SHOW_ALL_BRANCH_INSPECTION"))){
				totalMissed = VehicleDailyInspectionRepository.totalCountOfMissedInspection(userDetails.getCompany_id(), format.parse(DateTimeUtility.getCurrentDate()+" 00:00:00"), VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_INSPECTED,userDetails.getId());
			}else {
				totalMissed = VehicleDailyInspectionRepository.totalCountOfMissedInspection(userDetails.getCompany_id(), format.parse(DateTimeUtility.getCurrentDate()+" 00:00:00"), VehicleInspectionStatus.VEHICLE_INSPECTION_STATUS_INSPECTED);
				
			}
			
			model.put("totalMissedInspectionCount", totalMissed);
			model.put("currentDate", DateTimeUtility.getCurrentDate());

		} catch (Exception e) {
			LOGGER.error("ViewInspectionSheet Page:", e);
		}
		return new ModelAndView("ViewSkippedInspectionList", model);
	}

}
