package org.fleetopgroup.rest.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleServiceProgramDto;
import org.fleetopgroup.persistence.dto.VehicleTypeDto;
import org.fleetopgroup.persistence.model.JobSubType;
import org.fleetopgroup.persistence.model.JobType;
import org.fleetopgroup.persistence.model.ServiceProgramSchedules;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.VehicleServiceProgram;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IJobSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IServiceProgramService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import au.com.bytecode.opencsv.CSVReader;

@RestController
public class ServiceProgramController {

	@Autowired	IServiceProgramService	serviceProgramService;
	@Autowired	ICompanyConfigurationService	companyConfigurationService;
	@Autowired	IJobSubTypeService				jobSubTypeService;
	@Autowired	IJobTypeService					jobTypeService;
	
	@RequestMapping("/serviceProgram")
	public ModelAndView addServiceReminder(@ModelAttribute("command") ServiceReminder ServiceReminderDto,
			BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_PROGRAM_CONFIGURATION_CONFIG);
		model.put("companyId", userDetails.getCompany_id());
		model.put("userId", userDetails.getId());
		model.put("configuration", configuration);
		return new ModelAndView("serviceProgram", model);
	}
	
	@RequestMapping("/viewServiceProgram")
	public ModelAndView viewServiceReminder(@RequestParam("Id") final Long vehicleServiceProgramId,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority>	permission	= userDetails.getGrantedAuthoritiesList();
		HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_PROGRAM_CONFIGURATION_CONFIG);
		model.put("companyId", userDetails.getCompany_id());
		model.put("userId", userDetails.getId());
		model.put("configuration", configuration);
		model.put("permissions", permission);
		model.put("deleteServiceShedule", permission.contains(new SimpleGrantedAuthority("DELETE_SERVICE_SCHEDULE")));
		model.put("deleteServiceAssignment", permission.contains(new SimpleGrantedAuthority("DELETE_SERVICE_ASSIGNMENT")));
		model.put("vehicleServiceProgramId", vehicleServiceProgramId);
		
		return new ModelAndView("viewServiceProgram", model);
	}
	
	@RequestMapping(value = "/getServiceProgramList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getServiceProgramList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceProgramService.getServiceProgramList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteServiceProgram", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteServiceProgram(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceProgramService.deleteServiceProgram(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveServiceProgram", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveServiceProgram(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceProgramService.saveServiceProgram(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getServiceProgramDetailsById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getServiceProgramDetailsById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceProgramService.getServiceProgramDetailsById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/saveServiceProramSchedule", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveServiceProramSchedule(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceProgramService.saveServiceProramSchedule(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/deleteServiceSchedule", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteServiceSchedule(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceProgramService.deleteServiceSchedule(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getVehicleServiceProgramDetailsById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleServiceProgramDetailsById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceProgramService.getVehicleServiceProgramDetailsById(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/updateServiceProgram", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateServiceProgram(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceProgramService.updateServiceProgram(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getVehicleServiceProgram", method = RequestMethod.GET)
	public void getVehicleServiceProgram(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<VehicleServiceProgram> job = new ArrayList<VehicleServiceProgram>();
		List<VehicleServiceProgram> jobType =serviceProgramService.getVehicleServiceProgram(term, userDetails.getCompany_id());
		if (jobType != null && !jobType.isEmpty()) {
			for (VehicleServiceProgram add : jobType) {

				VehicleServiceProgram wadd = new VehicleServiceProgram();

				wadd.setProgramName(add.getProgramName());
				wadd.setVehicleServiceProgramId(add.getVehicleServiceProgramId());

				job.add(wadd);
			} 
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(job));
	}
	
	@RequestMapping(value = "/getVehicleServiceProgramByTypeId", method = RequestMethod.GET)
	public void getVehicleServiceProgramByTypeId(Map<String, Object> map, @RequestParam("vType") final Long term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<VehicleTypeDto> job = new ArrayList<VehicleTypeDto>();
		List<VehicleTypeDto> jobType =serviceProgramService.getVehicleServiceProgramByTypeId(term, userDetails.getCompany_id());
		if (jobType != null && !jobType.isEmpty()) {
			for (VehicleTypeDto add : jobType) {

				VehicleTypeDto wadd = new VehicleTypeDto();

				wadd.setServiceProgramId(add.getServiceProgramId());
				wadd.setProgramName(add.getProgramName());
				wadd.setTid(add.getTid());

				job.add(wadd);
			} 
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));
		response.getWriter().write(gson.toJson(job));
	}
	
	@RequestMapping(value = "/getVehicleServiceProgramByTypeAndModalId", method = RequestMethod.GET)
	public void getVehicleServiceProgramByTypeAndModalId(Map<String, Object> map, @RequestParam("vType") final Long term,
			 @RequestParam("vehicleModal") final Long vehicleModal,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<VehicleTypeDto> job = new ArrayList<VehicleTypeDto>();
		List<VehicleTypeDto> jobType =serviceProgramService.getVehicleServiceProgramByTypeId(term, vehicleModal, userDetails.getCompany_id());
		if (jobType != null && !jobType.isEmpty()) {
			for (VehicleTypeDto add : jobType) {

				VehicleTypeDto wadd = new VehicleTypeDto();

				wadd.setServiceProgramId(add.getServiceProgramId());
				wadd.setProgramName(add.getProgramName());
				wadd.setTid(add.getTid());

				job.add(wadd);
			} 
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));
		response.getWriter().write(gson.toJson(job));
	}
	
	@GetMapping(value = "/downloadServiceProgramSheet")
	public String downloadServiceProgramSheet(HttpServletResponse response) {
		XSSFWorkbook 						hssfWorkbook			= null;
		XSSFSheet 							hssfSheet				= null;
		XSSFRow 							rowZero 				= null;
		XSSFRow 							hssfRow					= null; 
		DataValidationHelper 				validationHelper 		= null;
		try {
			hssfWorkbook				= new XSSFWorkbook();
			hssfSheet					= hssfWorkbook.createSheet("serviceProgramSheet");
			rowZero 					= hssfSheet.createRow((short) 0);
			hssfRow 					= hssfSheet.createRow((short) 1);
		
		    validationHelper  		= new XSSFDataValidationHelper(hssfSheet);
		    hssfSheet.addMergedRegion(new CellRangeAddress(0,0,0,9));
		    rowZero.createCell(0).setCellValue("Note : (*) marked filled are mandatory.Please Do not Add Duplicate Service Program For Same Vehicle AND Interval Type Must Be (Day, Month or Year)");
		    hssfRow.createCell(0).setCellValue("Program Name (*)");
		    hssfRow.createCell(1).setCellValue("Description");
		    hssfRow.createCell(2).setCellValue("Job Type (*)");
		    hssfRow.createCell(3).setCellValue("Sub-Job Type (*)");
		    hssfRow.createCell(4).setCellValue("Meter Interval");
		    hssfRow.createCell(5).setCellValue("Time Interval");
		    hssfRow.createCell(6).setCellValue("Interval Type (*)");
		    hssfRow.createCell(7).setCellValue("Due Meter Threshold (*)");
		    hssfRow.createCell(8).setCellValue("Due Time Threshold (*)");
			hssfRow.createCell(9).setCellValue("Threshold Interval Type (*)");
			
			hssfSheet = serviceProgramService.getHssfSheetOfServiceProgram(validationHelper,hssfSheet);
		    for (int i=0; i < hssfRow.getLastCellNum(); i++){
		    	hssfSheet.autoSizeColumn(i);
		    }

			FileOutputStream fileOut = new FileOutputStream(new File("ServiceProgram.xlsx"));
			hssfWorkbook.write(fileOut);
			fileOut.close();

			//Code to download
			File fileToDownload = new File("ServiceProgram.xlsx");
			InputStream in = new FileInputStream(fileToDownload);

			// Gets MIME type of the file
			String mimeType = new MimetypesFileTypeMap().getContentType("ServiceProgram.xlsx");

			if (mimeType == null) {
				// Set to binary type if MIME mapping not found
				mimeType = "application/octet-stream";
			}
			// Modifies response
			response.setContentType(mimeType);
			response.setContentLength((int) fileToDownload.length());

			// Forces download
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", fileToDownload.getName());
			response.setHeader(headerKey, headerValue);

			// obtains response's output stream
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = in.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			in.close();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@PostMapping(value = "/importServiceProgram")
	public ModelAndView importServiceProgram(@ModelAttribute("command") VehicleServiceProgramDto photo,
			@RequestParam("import") MultipartFile file, HttpServletRequest request) throws Exception {
		CustomUserDetails			userDetails						= null;
		VehicleServiceProgram		validateVehicleServiceProgram 	= null;
		String 						rootPath 						= null;
		Map<String, Object> 		model 							= null;
		File 						dir 							= null;
		File 						serverFile 						= null;
		int 						CountSuccess 					= 0;
		int 						CountDuplicate 					= 0;
		int 						countNoJobTypeMatch 			= 0;
		String					 	Duplicate 						= ""; 
		HashMap<String, String[] > vehicleServiceProgramHM			= null;
		JobSubType						jobSubTypeValidate			= null;
		JobType 						jobType						= null;
		JobSubType 						jobSubType					= null;
		int 						jobTypeId						= 0;
		int 						jobSubTypeId					= 0;
		try {
			vehicleServiceProgramHM			= new HashMap<>();
			model 							= new HashMap<String, Object>();
			rootPath 						= request.getSession().getServletContext().getRealPath("/");
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dir 							= new File(rootPath + File.separator + "uploadedfile");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());

			try {
				try (InputStream is = file.getInputStream(); BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
					int i;
					// write file to server
					while ((i = is.read()) != -1) {
						stream.write(i);
					}
					stream.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Count How many Import SuccessFully
			String[] nextLine;
			try {
				// read file
				// CSVReader(fileReader, ';', '\'', 1) means
				// using separator ; and using single quote ' . Skip first line when
				// read

				try (FileReader fileReader = new FileReader(serverFile); CSVReader reader = new CSVReader(fileReader, ';', '\'', 2);) {
					while ((nextLine = reader.readNext()) != null) {

						VehicleServiceProgram vehicleServiceProgram = new VehicleServiceProgram();

						for (int i = 0; i < nextLine.length; i++) {
							try {

								String[] importVehicle = nextLine[i].split(",");

								try {
									validateVehicleServiceProgram = serviceProgramService.getVehicleServiceProgramDetailsByName(importVehicle[0], userDetails.getCompany_id());
									if (validateVehicleServiceProgram != null ) {
										++CountDuplicate;
										Duplicate = "Service program name =" + validateVehicleServiceProgram.getProgramName() + " "; 
										model.put("CountDuplicate", CountDuplicate);
										model.put("Duplicate", Duplicate);
										model.put("importSaveAlreadyError", true);
									} else {

										if(!vehicleServiceProgramHM.containsKey(importVehicle[0])) {
											vehicleServiceProgramHM.put(importVehicle[0], importVehicle);
											vehicleServiceProgram.setProgramName(importVehicle[0]);
											vehicleServiceProgram.setDescription(importVehicle[1]);
											vehicleServiceProgram.setCompanyId(userDetails.getCompany_id());
											vehicleServiceProgram.setCreatedById(userDetails.getId());
											vehicleServiceProgram.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
											vehicleServiceProgram.setLastModifiedById(userDetails.getId());
											vehicleServiceProgram.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
											vehicleServiceProgram.setMarkForDelete(false);
											vehicleServiceProgram.setVendorProgram(false);
											vehicleServiceProgram = serviceProgramService.saveServiceProgram(vehicleServiceProgram);

											ServiceProgramSchedules	serviceProgramSchedules	= new ServiceProgramSchedules();
											serviceProgramSchedules.setVehicleServiceProgramId(vehicleServiceProgram.getVehicleServiceProgramId());
											jobType = jobTypeService.validateJobType(importVehicle[2], userDetails.getCompany_id());
											jobSubType = jobSubTypeService.validateJobSubType(importVehicle[3],userDetails.getCompany_id());
											if(jobType != null) {
												jobTypeId = jobType.getJob_id();
											}
											if(jobSubType != null) {
												jobSubTypeId = jobSubType.getJob_Subid();
											}
											serviceProgramSchedules.setJobTypeId(jobTypeId);
											serviceProgramSchedules.setJobSubTypeId(jobSubTypeId);
											jobSubTypeValidate = jobSubTypeService.getJobSubType(serviceProgramSchedules.getJobSubTypeId());
											if(jobSubTypeValidate != null && !jobSubTypeValidate.getJob_TypeId().equals(serviceProgramSchedules.getJobTypeId())) {
												++countNoJobTypeMatch;
												model.put("noJobtypeMatch", "");
												model.put("countNoJobTypeMatch", countNoJobTypeMatch);
											}else {
												serviceProgramSchedules.setMeterInterval(Integer.parseInt(importVehicle[4]));
												serviceProgramSchedules.setTimeInterval(Integer.parseInt(importVehicle[5]));
												if(importVehicle[6].equals("Day")) {
													serviceProgramSchedules.setTimeIntervalType((short)1);
												}else if(importVehicle[6].equals("Month")) {
													serviceProgramSchedules.setTimeIntervalType((short)2);
												}else {
													serviceProgramSchedules.setTimeIntervalType((short)3);
												}
												serviceProgramSchedules.setMeterThreshold(Integer.parseInt(importVehicle[7]));
												serviceProgramSchedules.setTimeThreshold(Integer.parseInt(importVehicle[8]));
												if(importVehicle[9].equals("Day")) {
													serviceProgramSchedules.setTimeThresholdType((short)1);
												}else if(importVehicle[9].equals("Month")) {
													serviceProgramSchedules.setTimeThresholdType((short)2);
												}else {
													serviceProgramSchedules.setTimeThresholdType((short)3);
												}
												serviceProgramSchedules.setCompanyId(userDetails.getCompany_id());
												
												serviceProgramService.saveServiceProramSchedule(serviceProgramSchedules);
											}
										}
									}
								} catch (final Exception e) {
									e.printStackTrace();
								}

							} catch (Exception e) {
								e.printStackTrace();
								try {
									model.put("importSaveError", true);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
								return new ModelAndView("redirect:/serviceProgram.in", model);
							}

						} // for loop close
					}
				}
			} catch (Exception e) {
				try {
					model.put("importSaveError", true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				return new ModelAndView("redirect:/serviceProgram.in", model);
			}

			// show the Vehicle List
			try {
				model.put("CountSuccess", CountSuccess);
				model.put("importSave", true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/serviceProgram.in", model);

		} catch (Exception e) {
			throw e;
		}
	}
	
	@PostMapping(path = "/saveServiceProramAsign", produces="application/json")
	public HashMap<Object, Object>  saveServiceProramAsign(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceProgramService.saveServiceProramAsign(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@PostMapping(path = "/deleteServiceProgramAssignment", produces="application/json")
	public HashMap<Object, Object>  deleteServiceProgramAssignment(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceProgramService.deleteServiceProgramAssignment(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	
	@RequestMapping(value = "/getVehicleSheduleProgramListById", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getVehicleSheduleProgramListById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceProgramService.getVehicleSheduleProgramList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@RequestMapping(value = "/getServiceReminderByserviceScheduleId", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getServiceReminderByserviceScheduleId(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			return serviceProgramService.getVehicleReminderList(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	
}
