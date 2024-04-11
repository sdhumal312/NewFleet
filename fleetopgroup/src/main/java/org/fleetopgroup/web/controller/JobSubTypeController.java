package org.fleetopgroup.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
/**
 * @author fleetop
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.CompanyConstant;
import org.fleetopgroup.constant.MastersConfigurationConstants;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.JobTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.JobSubTypeDto;
import org.fleetopgroup.persistence.model.JobSubType;
import org.fleetopgroup.persistence.model.JobSubTypeHistory;
import org.fleetopgroup.persistence.model.JobType;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IJobSubTypeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IJobSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IJobTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import au.com.bytecode.opencsv.CSVReader;

@Controller
public class JobSubTypeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	CompanyConfigurationService	companyConfigurationService;
	
	@Autowired
	private IJobTypeService JobTypeService;

	@Autowired
	private IJobSubTypeHistoryService jobSubTypeHistoryService;

	@Autowired
	private IJobSubTypeService JobSubTypeService;
	JobTypeBL DriDT = new JobTypeBL();

	@RequestMapping(value = "/addJobSubType/{pageNumber}", method = RequestMethod.GET)
	public String newaddJobSubType(@PathVariable Integer pageNumber, Model model) throws Exception {
		HashMap<String, Object> 	configuration				= null;
		try {
			Page<JobSubType> page = JobSubTypeService.getDeployment_Page_JobSubType(pageNumber);
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			
			model.addAttribute("configuration", configuration);
			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("JobSubCount", page.getTotalElements());
			List<JobSubType> pageList = DriDT.getJobSubList(JobSubTypeService.listJobSubType(pageNumber, userDetails.getCompany_id()));

			model.addAttribute("JobSubType", pageList);
			model.addAttribute("JobType", DriDT.JobListofDto(JobTypeService.listJobTypeByCompanyId(userDetails.getCompany_id())));
			model.addAttribute("userCompanyId", userDetails.getCompany_id());

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("newWorkOrders Page:", e);
			e.printStackTrace();
		}
		return "addJobSubType";
	}

	@RequestMapping(value = "/saveJobSubType", method = RequestMethod.POST)
	public ModelAndView saveJobSubType(@ModelAttribute("command") JobSubTypeDto JobTypeDto,
			final HttpServletRequest request) {
		HashMap<String, Object> configuration	          	= null;
		boolean viewRotServiceReminder						= false;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			viewRotServiceReminder	= (boolean) configuration.getOrDefault(MastersConfigurationConstants.SHOW_ROT_IN_SERVICE_REMINDER, false);
			
			JobSubType DocType = DriDT.prepareJobSubTypeModel(JobTypeDto);
			JobSubType validate = JobSubTypeService.validateJobSubType(JobTypeDto.getJob_ROT(), userDetails.getCompany_id());
			
			if (validate == null) {
				DocType.setCompanyId(userDetails.getCompany_id());
				if(userDetails.getCompany_id() == CompanyConstant.COMPANY_CODE_FLEETOP) {
					DocType.setCompanyId(0);
				}
				DocType.setCreatedById(userDetails.getId());
				DocType.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				if(viewRotServiceReminder) {
					if(JobTypeDto.getROT_Service_Reminder() != null) {
					DocType.setJob_ROT_Service_Reminder(JobTypeDto.getROT_Service_Reminder());
					} else {
					DocType.setJob_ROT_Service_Reminder(false);	
				    }
				} else {
				DocType.setJob_ROT_Service_Reminder(true);	
				}
				
				JobSubTypeService.addJobSubType(DocType);
				model.put("saveJobSubType", true);
			} else {
				model.put("alreadyJobSubType", true);
				return new ModelAndView("redirect:/addJobSubType/1.in", model);
			}
		} catch (Exception e) {
			LOGGER.error("Job Sub Type Page:", e);
			model.put("alreadyJobSubType", true);
			return new ModelAndView("redirect:/addJobSubType/1.in", model);
		}
		return new ModelAndView("redirect:/addJobSubType/1.in", model);
	}

	@RequestMapping(value = "/updateJobSubType", method = RequestMethod.POST)
	public ModelAndView updateJobSubType(@ModelAttribute("command") JobSubTypeDto JobTypeDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> configuration	          	= null;
		boolean viewRotServiceReminder						= false;
		try {
			CustomUserDetails	userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			viewRotServiceReminder	= (boolean) configuration.getOrDefault(MastersConfigurationConstants.SHOW_ROT_IN_SERVICE_REMINDER, false);
			JobSubType 			DocType 		= DriDT.prepareJobSubTypeModel(JobTypeDto);
			JobSubType			jobSubType		= JobSubTypeService.getJobSubType(DocType.getJob_Subid());
			
			if(!jobSubType.getJob_ROT().equalsIgnoreCase(DocType.getJob_ROT().trim())) {
				JobSubType validate = JobSubTypeService.validateJobSubType(DocType.getJob_ROT(), userDetails.getCompany_id());
				if(validate != null) {
					model.put("alreadyJobSubType", true);
					return new ModelAndView("redirect:/addJobSubType/1.in", model);
				}
			}
			
			DocType.setLastModifiedById(userDetails.getId());
			DocType.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
			DocType.setCreatedById(jobSubType.getCreatedById());
			DocType.setCompanyId(jobSubType.getCompanyId());
			DocType.setCreatedOn(jobSubType.getCreatedOn());
			if(viewRotServiceReminder) {
				if(JobTypeDto.getROT_Service_Reminder() != null) {
				DocType.setJob_ROT_Service_Reminder(JobTypeDto.getROT_Service_Reminder());
				} else {
				DocType.setJob_ROT_Service_Reminder(false);	
			    }
			} else {
			DocType.setJob_ROT_Service_Reminder(true);	
			}
			
			JobSubTypeHistory	jobSubTypeHistory	= DriDT.prepareJobSubTypeHistoryModel(jobSubType);
			if(viewRotServiceReminder) {
				if(jobSubType.isJob_ROT_Service_Reminder() != null) {
					jobSubTypeHistory.setJob_ROT_Service_Reminder(jobSubType.isJob_ROT_Service_Reminder());
				} else {
					jobSubTypeHistory.setJob_ROT_Service_Reminder(false);	
				}
				} else {
					jobSubTypeHistory.setJob_ROT_Service_Reminder(true);	
				}
			
			
			JobSubTypeService.updateJobSubType(DocType);
			jobSubTypeHistoryService.addJobSubTypeHistory(jobSubTypeHistory);
			
			model.put("updateJobSubType", true);
		} catch (Exception e) {
			LOGGER.error("Job Sub Type Page:", e);
			model.put("alreadyJobSubType", true);
			return new ModelAndView("redirect:/addJobSubType/1.in", model);
		}
		return new ModelAndView("redirect:/addJobSubType/1.in", model);
	}

	@RequestMapping(value = "/editJobSubType", method = RequestMethod.GET)
	public ModelAndView editJobSub(@ModelAttribute("command") JobSubTypeDto JobSubTypeDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 	configuration				= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_CONFIGURATION_CONFIG);
			model.put("configuration", configuration);
			model.put("JobSubType",
					DriDT.prepareJobSubTypeDto(JobSubTypeService.getJobSubType(JobSubTypeDto.getJob_Subid(), userDetails.getCompany_id())));
			model.put("JobType", DriDT.JobListofDto(JobTypeService.listJobTypeByCompanyId(userDetails.getCompany_id())));
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			LOGGER.error("Job Sub Type Page:", e);
		}
		return new ModelAndView("editJobSubType", model);
	}

	@RequestMapping(value = "/deleteJobSubType", method = RequestMethod.GET)
	public ModelAndView deleteJobType(@ModelAttribute("command") JobSubTypeDto JobSubTypeDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			JobSubTypeHistory	jobSubTypeHistory	= DriDT.prepareJobSubTypeHistoryModel(JobSubTypeService.getJobSubType(JobSubTypeDto.getJob_Subid()));
			
			JobSubTypeService.deleteJobSubType(JobSubTypeDto.getJob_Subid(), userDetails.getCompany_id());
			jobSubTypeHistoryService.addJobSubTypeHistory(jobSubTypeHistory);
			
			model.put("deleteJobSubType", true);
		} catch (Exception e) {
			LOGGER.error("Job Sub Type Page:", e);
			model.put("alreadyJobSubType", true);
			return new ModelAndView("redirect:/addJobSubType/1.in", model);
		}
		return new ModelAndView("redirect:/addJobSubType/1.in", model);
	}
	
	@RequestMapping(value = "/importJobSubType", method = RequestMethod.POST)
	public ModelAndView importMasterPart(@ModelAttribute("command") JobSubTypeDto JobSubTypeDto,
			@RequestParam("import") MultipartFile file, HttpServletRequest request

	) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String rootPath = request.getSession().getServletContext().getRealPath("/");

		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
		List<JobType> 	jobTypeList		= JobTypeService.listJobTypeByCompanyId(userDetails.getCompany_id());

		try {
			try (InputStream is = file.getInputStream();
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
				int i;
				// write file to server
				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		} catch (IOException e) {
			/*
			 * model.put("msg", "failed to process file because : " +
			 * e.getMessage()); return "mainpage";
			 */
			e.printStackTrace();
		}

		// Count How many Import SuccessFully
		int CountSuccess = 0;
		int CountDuplicate = 0;

		String[] nextLine;
		try {
			// read file
			// CSVReader(fileReader, ';', '\'', 1) means
			// using separator ; and using single quote ' . Skip first line when
			// read

			try (FileReader fileReader = new FileReader(serverFile);
					CSVReader reader = new CSVReader(fileReader, ';', '\'', 1);) {
				while ((nextLine = reader.readNext()) != null) {
					// System.out.println("content : ");

					JobSubType type = new JobSubType();

					for (int i = 0; i < nextLine.length; i++) {
						try {

							// System.out.println(nextLine[i]);
							String[] importVehicle = nextLine[i].split(",");
							// getting import CSV file column 1
							// vehicle.setVid(vehicleDto.getVid());
							// getting import CSV file column 1 Redistration

							// part.setPartid();
							for(JobType jobType : jobTypeList) {
								if(jobType.getJob_Type().trim().equalsIgnoreCase(importVehicle[0].trim())) {
									type.setJob_Type(jobType.getJob_Type());
									type.setJob_TypeId(jobType.getJob_id());
									break;
								}
							}
							type.setJob_ROT(importVehicle[1]);
							type.setJob_ROT_number(importVehicle[2]);
							type.setJob_ROT_hour(importVehicle[3]);
							type.setJob_ROT_amount(importVehicle[4]);
							type.setCompanyId(userDetails.getCompany_id());
							type.setCreatedBy(userDetails.getEmail());
							type.setCreatedOn(new Timestamp(System.currentTimeMillis()));
							type.setCreatedById(userDetails.getId());

							// vehicleService.addVehicle(vehicle);
							try {
								JobSubType validate = JobSubTypeService.validateJobSubType(type.getJob_ROT(), userDetails.getCompany_id());
								if (validate != null) {
									++CountDuplicate;
									String Duplicate = "ROT Name =" + type.getJob_ROT() + "";
									model.put("CountDuplicate", CountDuplicate);
									model.put("Duplicate", Duplicate);
									model.put("importSaveAlreadyError", true);

								} else {
									try {
										JobSubTypeService.addJobSubType(type);
										CountSuccess++;
									} catch (final Exception e) {
										e.printStackTrace();
										++CountDuplicate;
										String Duplicate = "ROT Name =" + type.getJob_ROT() + "";
										model.put("CountDuplicate", CountDuplicate);
										model.put("Duplicate", Duplicate);
										model.put("importSaveAlreadyError", true);
									}

									try {
										JobType JobTypeValidate = JobTypeService.validateJobType(type.getJob_Type(), userDetails.getCompany_id());

										if (JobTypeValidate != null) {

										} else {
											JobType DocType = new JobType();
											DocType.setJob_Type(type.getJob_Type());
											DocType.setCompanyId(userDetails.getCompany_id());
											DocType.setCreatedBy(userDetails.getEmail());
											DocType.setCreatedOn(new Timestamp(System.currentTimeMillis()));
											JobTypeService.addJobType(DocType);
										}
									} catch (Exception e) {
										e.printStackTrace();
									}

								}
							} catch (final Exception e) {
								++CountDuplicate;
								String Duplicate = "ROT Name =" + type.getJob_ROT() + "";
								model.put("CountDuplicate", CountDuplicate);
								model.put("Duplicate", Duplicate);
								model.put("importSaveAlreadyError", true);
							}

						} catch (Exception e) { // catch for For loop in get
												// import file
							// System.out.println("error while reading csv and
							// put to db : " + e.getMessage());
							e.printStackTrace();
							try {
								model.put("JobType", DriDT.JobListofDto(JobTypeService.listJobTypeByCompanyId(userDetails.getCompany_id())));
								model.put("JobSubType", DriDT.getJobSubList(JobSubTypeService.listJobSubTypeByCompanyId(userDetails.getCompany_id())));
								model.put("importSaveError", true);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							return new ModelAndView("addJobSubType", model);
						}

					} // for loop close

				}
			}
		} catch (Exception e) {
			// System.out.println("error while reading csv and put to db : " +
			// e.getMessage());
			try {
				model.put("JobType", DriDT.JobListofDto(JobTypeService.listJobType()));
				model.put("JobSubType", DriDT.JobSubListofDto(JobSubTypeService.listJobSubTypeByCompanyId(userDetails.getCompany_id())));
				model.put("importSaveError", true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return new ModelAndView("addJobSubType", model);
		}

		// show the Vehicle List
		try {
			model.put("CountSuccess", CountSuccess);
			model.put("JobType", DriDT.JobListofDto(JobTypeService.listJobType()));
			model.put("JobSubType", DriDT.JobSubListofDto(JobSubTypeService.listJobSubTypeByCompanyId(userDetails.getCompany_id())));
			model.put("importSave", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("addJobSubType", model);
	}

}
