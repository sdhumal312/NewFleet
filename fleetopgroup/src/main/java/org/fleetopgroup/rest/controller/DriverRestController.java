package org.fleetopgroup.rest.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.DriverConfigurationContant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.DriverCommentBL;
import org.fleetopgroup.persistence.bl.DriverDocTypeBL;
import org.fleetopgroup.persistence.bl.DriverDocumentBL;
import org.fleetopgroup.persistence.bl.DriverJobTypeBL;
import org.fleetopgroup.persistence.bl.DriverReminderBL;
import org.fleetopgroup.persistence.bl.DriverTrainingTypeBL;
import org.fleetopgroup.persistence.bl.VehicleGroupBL;
import org.fleetopgroup.persistence.document.DriverDocument;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocTypeService;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IDriverJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IDriverTrainingTypeService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleCheckingDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.ByteConvertor;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class DriverRestController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired IVehicleCheckingDetailsService	vehicleCheckingDetailsService;
	@Autowired IDriverService 					driverService;
	@Autowired ICompanyConfigurationService 	companyConfigurationService;
	@Autowired IDriverDocTypeService 			driverDocTypeService;
	@Autowired IVehicleGroupService 			vehicleGroupService;
	@Autowired IDriverTrainingTypeService 		driverTrainingTypeService;
	@Autowired IDriverJobTypeService 			driverJobTypeService;
	@Autowired IVehicleService					vehicleService;
	@Autowired IDriverDocumentService			driverDocumentService;
	
	DriverTrainingTypeBL 	driverTrainingTypeBL 		= new DriverTrainingTypeBL();
	DriverJobTypeBL 		driverJobTypeBL		 		= new DriverJobTypeBL();
	DriverBL 				driverBL			 		= new DriverBL();
	DriverReminderBL 		driverReminderBL		 	= new DriverReminderBL();
	DriverDocumentBL 		driverDocumentBL		 	= new DriverDocumentBL();
	DriverCommentBL 		driverCommentBL			 	= new DriverCommentBL();
	DriverDocTypeBL 		driverDocTypeBL			 	= new DriverDocTypeBL();
	VehicleGroupBL 			vehicleGroupBL	 			= new VehicleGroupBL();

	@RequestMapping(value = "/CheckingEntry", method = RequestMethod.GET)
	public ModelAndView checkingEntry(final HttpServletRequest result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails 				userDetails 	          = null;
		HashMap<String, Object> 		configuration	          = null;
		try {
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
		model.put("configuration", configuration);
		
		} catch (Exception e) {
			LOGGER.error("CheckingEntry Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("addCheckingEntry", model);
	}
	
	@RequestMapping(value = "/saveCheckingDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveCheckingDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;

		try {
			
			object 				= new ValueObject(allRequestParams);
			//object.put("checkingData", JsonConvertor.toValueObjectFromJsonString(object.getString("checkingData")));
			
			return vehicleCheckingDetailsService.saveCheckingDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/CheckingEntry/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView checkingEntryList(@PathVariable Integer pageNumber, Model model) {
		try {
			model	= vehicleCheckingDetailsService.getCheckingReportList(pageNumber, model);
		} catch (Exception e) {
			LOGGER.error("CheckingEntry Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("CheckingEntryList");
	}
	
	@RequestMapping(value = "/deleteVehicleChecking")
	public ModelAndView deleteFuel(@RequestParam("ID") final Long id, final HttpServletResponse result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
				try {

					
					vehicleCheckingDetailsService.delete(id);

				} catch (Exception e) {

					model.put("errorFuel", true);
					return new ModelAndView("redirect:/CheckingEntry/1.in", model);
				}
				model.put("deleteFuel", true);
		} catch (Exception e1) {
			LOGGER.error("Checking Page:", e1);
			e1.printStackTrace();
			return new ModelAndView("redirect:/CheckingEntry/1.in?danger=true");
		}

		return new ModelAndView("redirect:/CheckingEntry/1.in?delete=true");
	}
	
	@RequestMapping(value = "/getDriverDLReminderDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDriverDLReminderDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		DriverReminderBL 	driverReminderBL		= null;
		CustomUserDetails	userDetails				= null;
		ValueObject			valOutObject			= null;
		
		try {
			
			object 				= new ValueObject(allRequestParams);
			driverReminderBL	= new DriverReminderBL();
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			valOutObject	= new ValueObject();
			valOutObject.put("DriverRemider", driverReminderBL.getDriverReminder_Showing_Details(driverService.listLatestDriverDLReminder(object.getInt("driverId", 0), userDetails.getCompany_id())));
			
			return valOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	
	@RequestMapping(value = "/getDriverEngagementAndPerformanceReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDriverEngagementAndPerformanceReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		ValueObject			valueOutObject 			= null;
		try {
			
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject 		= driverService.getDriverEngagementAndPerformanceReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@RequestMapping(value = "/addDriverDetails", method = RequestMethod.GET)
	public ModelAndView addVehicleDetails(final HttpServletRequest request) throws Exception {
		ModelAndView 					map 				= null;
		CustomUserDetails				userDetails			= null;
		HashMap<String, Object> 		configuration		= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			map = new ModelAndView("addDriverDetails");
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			
			map.addObject("vehiclegroup", driverBL.prepareListofDto(vehicleGroupService.getVehiclGroupByPermission(userDetails.getCompany_id())));
			map.addObject("driverTrainingType",driverTrainingTypeBL.DriTrainingTypeListofBean(driverTrainingTypeService.listDriverTrainingTypeByCompanyId(userDetails.getCompany_id())));
			map.addObject("driverJobType", driverJobTypeBL.DriJobTypeListofBean(driverJobTypeService.listDriverJobTypeByCompanyId(userDetails.getCompany_id())));
		//	map.addObject("driverDocType", driverDocTypeBL.DriDocTypeListofDto(driverDocTypeService.findAllByCompanyId(userDetails.getCompany_id())));
			map.addObject("driverDocType", driverDocTypeService.findByDocType(userDetails.getCompany_id()));
			map.addObject("flavorWiseDriverSalary", configuration.getOrDefault(DriverConfigurationContant.FLAVOR_WISE_DRIVER_SALARY, false));
			map.addObject("user", userDetails.getEmail());
			map.addObject("vehicle", vehicleService.getVehicleListByCompanyId(userDetails.getCompany_id()));
			map.addObject("configuration", configuration);
			return map;
		}  catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw e;
		}
	}
	
	@RequestMapping(value="/saveDriverDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveDriverDetails(@RequestParam HashMap<Object, Object> allRequestParams,
			@RequestParam("input-file-preview") MultipartFile uploadfile) throws Exception {

		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;

		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= driverService.saveDriverDetails(JsonConvertor.toValueObjectFormSimpleJsonString(valueInObject.getString("driverData")),uploadfile);
			
			return valueOutObject.getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value="/updateDriverDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateDriverDetails(/*@RequestParam("driverData") String allRequestParams*/  @RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;

		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= driverService.updateDriverDetails(valueInObject);
			
			return valueOutObject.getHtData();
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	
	@RequestMapping(value = "/getCountByDriverJObId", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getCountByDriverJObId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 					object 					= null;
		ValueObject						valueOutObject 			= null;
		CustomUserDetails				userDetails				= null;	
		HashMap<String, Object> 		configuration	         = null;
		
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
		
			object 				= new ValueObject(allRequestParams);
			object.put("companyId", userDetails.getCompany_id());
			object.put("autoGenerateEmpNo", configuration.getOrDefault(DriverConfigurationContant.AUTOGENERATE_EMP_NO, false));
			object.put("jobTypeWiseAutoGenerateEmpNo", configuration.getOrDefault(DriverConfigurationContant.JOBTYPE_WISE_AUTOGENERATE_EMP_NO, false));
			
			valueOutObject 		= driverService.getCountByDriverJObId(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	@PostMapping(path = "/getDriverDocuments", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  getDriverDocuments(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return driverService.getDriverDocuments(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} 
	}
	@PostMapping(path = "/getOverDueData", produces="application/json")
	public HashMap<Object, Object>  getOverDueData(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return driverService.getOverDueData(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@PostMapping(path="/addDriverRenewalReceipt" , produces="application/json")
	public HashMap<Object, Object> addDriverRenewalReceipt(@RequestParam("renewalReceipt") String allRequestParam , @RequestParam("file") MultipartFile  file) throws Exception {
		
		ValueObject object = null;
		try {
			object = JsonConvertor.toValueObjectFormSimpleJsonString(allRequestParam);
			driverService.addDriverRenewalReceipt(object , file);
			return object.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
	@PostMapping(path = "/deleteRenewalReciept", produces="application/json")
	public HashMap<Object, Object>  deleteRenewalReciept(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return driverService.deleteRenewalReciept(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@PostMapping(path = "/getDriverWiseIssuesList", produces="application/json")
	public HashMap<Object, Object>  getDriverWiseIssuesList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return driverService.getDriverDriverWiseIssueList(object).getHtData();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@PostMapping(path="/getDriverWiseCommentsList" ,produces=MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object,Object> getDriverWiseCommentsList(@RequestParam HashMap<Object,Object> allReqParam){
		ValueObject 		object                  = null;
		try {
			object = new ValueObject(allReqParam);
			object =driverService.getDriverCommentsList(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object.getHtData();
	}
	
	@PostMapping(path="/getDriverWiseFuelMileage" ,produces=MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object,Object> getDriverWiseFuelMileage(@RequestParam HashMap<Object,Object> allReqParam){
		ValueObject 		object                  = null;
		try {
			object = new ValueObject(allReqParam);
			return driverService.getDriverFuelMileage(object).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@RequestMapping(value="/getDriverStatus", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getDriverStatus(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object = driverService.getDriverCurrentStatusForTripsheet(object);
			if(object != null) {
				return object.getHtData();
			}else {
				return null;
			}
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			System.err.println("Exception "+e);
			throw e;
		} finally {
		}
	}

    
    @GetMapping("/downloadDriverDocumentsZip")
    public ResponseEntity<byte[]> downloadZip(@RequestParam String jsonData) {
        try {
            ObjectMapper  objectMapper      = new ObjectMapper();
            JsonNode	  jsonNode 		    = objectMapper.readTree(jsonData);
            String 		  companyId 		= jsonNode.get("companyId").asText();
            List<String>  driverIds 		= objectMapper.convertValue(jsonNode.get("driverIds"), List.class);
            
            List<DriverDocument>     finaldocumentsList = new ArrayList<DriverDocument>();
            List<DriverDocument>	 driversDocument   = null;
            boolean   				 entriesAdded 	   = false;
            for(String driverId : driverIds) {
            	driversDocument = driverDocumentService.listDriverDocument(Integer.parseInt(driverId), Integer.parseInt(companyId));
            	finaldocumentsList.addAll(driversDocument);
            }
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    
                	ZipOutputStream zipOut = new ZipOutputStream(baos)) {

                	zipOut.setMethod(ZipOutputStream.DEFLATED); // or ZipOutputStream.STORED for no compression
                	zipOut.setLevel(Deflater.DEFAULT_COMPRESSION); // adjust compression level
                    for (DriverDocument document : finaldocumentsList) {
                    	addToZip(zipOut, document);
                    	entriesAdded = true;
                    }
                    zipOut.close();
                    byte[] zipContent = baos.toByteArray();
                    
                    if (entriesAdded) {
	                    HttpHeaders headers = new HttpHeaders();
	                    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
	                    headers.setContentDispositionFormData("attachment", "output.zip");

	                    return ResponseEntity.ok()
	                            .headers(headers)
	                    		.body(zipContent);
                    }else {
                    	// No entries added to the zip file, return an appropriate response
                        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
    
    private void addToZip(ZipOutputStream zipOut, DriverDocument document) throws IOException {
    	byte[] content = document.getDriver_content();
 
        //String entryName = document.getDriver_documentname() + "_" + document.getDriver_id() +  "."+ 
        String entryName  =  document.getDriver_filename();
    	
    	document.getDriver_contentType().substring(document.getDriver_contentType().lastIndexOf("/")+1);
        // Create a new entry in the zip file
        zipOut.putNextEntry(new ZipEntry(entryName));
        zipOut.write(content);
        zipOut.closeEntry();
    }
    
}
