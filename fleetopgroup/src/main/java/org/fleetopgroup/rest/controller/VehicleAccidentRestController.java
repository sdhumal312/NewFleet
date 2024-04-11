package org.fleetopgroup.rest.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.document.VehicleAccidentDocument;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAccidentDetailsService;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class VehicleAccidentRestController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired	IVehicleAccidentDetailsService		vehicleAccidentDetailsService;
	@Autowired  ICompanyConfigurationService 		companyConfigurationService;

	@GetMapping(path = "/addVehicleAccident")
	public ModelAndView addVehicleAccident(
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration 	= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			configuration =  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			model.put("serverDate",DateTimeUtility.getDateAfterNoOfDays(0));
			model.put("companyId",userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
			model.put("accessToken", Utility.getUniqueToken(request));
			model.put("configuration", configuration);
			
		} catch (Exception e) {
			logger.error("VehicleAccidentAdd Page:", e);
		}
		return new ModelAndView("VehicleAccidentAdd", model);
	}
	
	@PostMapping(path = "/saveVehicleAccidentDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveVehicleAccidentDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleAccidentDetailsService.saveVehicleAccidentDetails(object).getHtData();
			
			
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/updateVehicleAccidentDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  updateVehicleAccidentDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleAccidentDetailsService.updateVehicleAccidentDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@GetMapping(path = "/editVehicleAccident")
	public ModelAndView editVehicleAccident(@RequestParam("id") String id,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration 	= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			configuration =  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			model.put("serverDate",DateTimeUtility.getDateAfterNoOfDays(0));
			model.put("companyId",userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
			model.put("configuration", configuration);
			model.put("accidentId", id);
			
		} catch (Exception e) {
			logger.error("VehicleAccidentAdd Page:", e);
		}
		return new ModelAndView("VehicleAccidentEdit", model);
	}
	
	@GetMapping(path = "/viewVehicleAccidentList")
	public ModelAndView viewVehicleAccidentList() throws Exception {
		ModelAndView map = new ModelAndView("ViewVehicleAccidentList");
		try {
			
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			map.addObject("permissions", permission);
		}  catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	@PostMapping(path = "/getPageWiseVehicleAccidentDetails", produces="application/json")
	public HashMap<Object, Object>  getPageWiseVehicleAccidentDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.getPageWiseVehicleAccidentDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/showVehicleAccidentDetails", produces="application/json")
	public HashMap<Object, Object>  showVehicleAccidentDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.showVehicleAccidentDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getIncidentDocumentList", produces="application/json")
	public HashMap<Object, Object>  getIncidentDocumentList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.getIncidentDocumentList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@GetMapping(path = "/showVehicleAccidentDetails")
	public ModelAndView showVehicleAccidentDetails(
			@RequestParam("id") String id,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration 	= null;
		HashMap<String, Object> 	dseConfig 	= null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
			if(permission.contains(new SimpleGrantedAuthority("DEALER_SERVICE_ENTRIES"))){
				model.put("dealerServicePermission",true);
			}else {
				model.put("dealerServicePermission",false);
			}
			configuration 	=  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			dseConfig 		=  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DSE_CONFIGURATION_CONFIG);
			
			model.put("dseConfig",dseConfig);
			model.put("configuration",configuration);
			model.put("serverDate",DateTimeUtility.getDateAfterNoOfDays(0));
			model.put("companyId",userDetails.getCompany_id());
			model.put("userId", userDetails.getId());
			model.put("accidentId", id);
			model.put("dAccidentId",Long.parseLong(AESEncryptDecrypt.decryptBase64(id)));
			
		} catch (Exception e) {
			logger.error("ViewVehicleAccidentDetails Page:", e);
		}
		return new ModelAndView("ViewVehicleAccidentDetails", model);
	}
	
	@PostMapping(path = "/saveIncidentDocument", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveIncidentDocument(@RequestParam("FuelData") String allRequestParams, 
			@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {
		try {
			
			return vehicleAccidentDetailsService.saveIncidentDocument(JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams),uploadfile).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}

	@PostMapping(path = "/saveServeyorDetails", produces="application/json")
	public HashMap<Object, Object>  saveServeyorDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.saveServeyorDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/saveServeyCompletionDetails", produces="application/json")
	public HashMap<Object, Object>  saveServeyCompletionDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.saveServeyCompletionDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/saveFinalServeyForDamage", produces="application/json")
	public HashMap<Object, Object>  saveFinalServeyForDamage(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.saveFinalServeyForDamage(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/saveQuotationApprovalDetails", produces="application/json")
	public HashMap<Object, Object>  saveQuotationApprovalDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.saveQuotationApprovalDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping("/download/VehicleAccidentDocument/{driver_documentid}")
	public String download(@PathVariable("driver_documentid") Integer documentId, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		VehicleAccidentDocument file = vehicleAccidentDetailsService.getVehicleAccidentDocument(documentId, userDetails.getCompany_id());
		try {
			if (documentId == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				if (file != null && file.getContent() != null) {
						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getFileName() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getContentType());
						response.getOutputStream().write(file.getContent());
						out.flush();
						out.close();

				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (IOException e) {
			return null;
		}
		return null;
	}

	@PostMapping(path = "/saveAdvanceDetails", produces="application/json")
	public HashMap<Object, Object>  saveAdvanceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.saveAdvanceDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getAdvanceListByAccidentId", produces="application/json")
	public HashMap<Object, Object>  getAdvanceListByAccidentId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.getAdvanceListByAccidentId(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/saveExpenseDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveExpenseDetails(@RequestParam("ExpenseData") String allRequestParams, 
			@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {
		try {
			
			return vehicleAccidentDetailsService.saveExpenseDetails(JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParams),uploadfile).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getExpenseListByAccidentId", produces="application/json")
	public HashMap<Object, Object>  getExpenseListByAccidentId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.getExpenseListByAccidentId(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/saveFinalInspectionDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveFinalInspectionDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.saveFinalInspectionDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/savePaymentDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  savePaymentDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.savePaymentDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/removeIncidentDoc", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  removeIncidentDoc(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.removeIncidentDoc(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/removeAdvanceDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  removeAdvanceDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.removeAdvanceDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/removeAccidentExpense", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  removeAccidentExpense(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.removeExpense(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/deleteAccidentDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  deleteAccidentDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.deleteAccidentDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/getVehicleAccidentDetails", produces="application/json")
	public HashMap<Object, Object>  getVehicleAccidentDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.getVehicleAccidentDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/searchAccidentByNumber", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  searchAccidentByNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.searchAccidentByNumber(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/checkForOldServeyDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  checkForOldServeyDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.checkForOldServeyDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/saveUpdateAccidentTypeDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveUpdateAccidentTypeDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object.put("accidentPersonDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("accidentPersonDetails")));
			return vehicleAccidentDetailsService.saveUpdateAccidentTypeDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/getAccidentTypeDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getAccidentTypeDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.getAccidentTypeDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/removeAccidentPersonDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  removeAccidentPersonDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			 vehicleAccidentDetailsService.removeAccidentPersonDetails(object);
			 return object.getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/saveUpdateSpotSurveyorDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveSpotServeyorDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			vehicleAccidentDetailsService.saveUpdateSpotSurveyorDetails(object);
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/getSpotSurveyorDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getSpotSurveyorDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleAccidentDetailsService.getSpotSurveyorDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/getSpotSurveyorCompletionDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getSpotSurveyorCompletionDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleAccidentDetailsService.getSpotSurveyorDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/saveKeepOpenDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveKeepOpenDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleAccidentDetailsService.saveKeepOpenDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/saveBeforeEstimate", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveBeforeEstimate(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return vehicleAccidentDetailsService.saveBeforeEstimate(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/validateDocUploaded", produces="application/json")
	public HashMap<Object, Object>  validateDocUploaded(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		
		try {
			object 				= new ValueObject(allRequestParams);
			
			return vehicleAccidentDetailsService.checkDocumentExist(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
}
