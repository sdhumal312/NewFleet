package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 */
import java.util.HashMap;
import java.util.Map;

import org.fleetopgroup.constant.CompanyConstant;
import org.fleetopgroup.persistence.bl.JobTypeBL;
import org.fleetopgroup.persistence.bl.ReasonTypeBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.JobTypeDto;
import org.fleetopgroup.persistence.dto.ReasonForRepairDto;
import org.fleetopgroup.persistence.model.JobType;
import org.fleetopgroup.persistence.model.JobTypeHistory;
import org.fleetopgroup.persistence.model.ReasonForRepairType;
import org.fleetopgroup.persistence.model.ReasonTypeHistory;
import org.fleetopgroup.persistence.serviceImpl.IJobTypeHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IReasonRepairTypeService;
import org.fleetopgroup.persistence.serviceImpl.IReasonTypeHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JobTypeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IJobTypeService JobTypeService;

	@Autowired
	private IJobTypeHistoryService jobTypeHistoryService;
	
	@Autowired
	private IReasonRepairTypeService ReasonTypeService;
	
	@Autowired
	private IReasonTypeHistoryService reasonTypeHistoryService;
	
	JobTypeBL DriDT = new JobTypeBL();
	
	ReasonTypeBL RTBL = new ReasonTypeBL();

	@RequestMapping(value = "/addJobType", method = RequestMethod.GET)
	public ModelAndView addJobType(@ModelAttribute("command") JobTypeDto JobTypeBean, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	customUserDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("JobType", DriDT.JobListofDto(JobTypeService.listJobTypeByCompanyId(customUserDetails.getCompany_id())));
			model.put("userCompanyId", customUserDetails.getCompany_id());
		} catch (Exception e) {
			LOGGER.error("Job Type Page:", e);
		}
		return new ModelAndView("addJobType", model);
	}

	@RequestMapping(value = "/saveJobType", method = RequestMethod.POST)
	public ModelAndView saveJobType(@ModelAttribute("command") JobTypeDto JobTypeBean, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			JobType DocType = DriDT.prepareJobTypeModel(JobTypeBean);

			JobType validate = JobTypeService.validateJobType(DocType.getJob_Type(), userDetails.getCompany_id());
			if (validate == null) {
				if(userDetails.getCompany_id() == CompanyConstant.COMPANY_CODE_FLEETOP) {
					DocType.setCompanyId(0);
				}else {
					DocType.setCompanyId(userDetails.getCompany_id());
				}
				
				DocType.setCreatedById(userDetails.getId());
				DocType.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				JobTypeService.addJobType(DocType);
				model.put("saveJobType", true);
			} else {
				model.put("alreadyJobType", true);
				return new ModelAndView("redirect:/addJobType.in", model);
			}
		} catch (Exception e) {
			LOGGER.error("Job Type Page:", e);
			model.put("alreadyJobType", true);
			return new ModelAndView("redirect:/addJobType.in", model);
		}
		return new ModelAndView("redirect:/addJobType.in", model);
	}

	@RequestMapping(value = "/updateJobType", method = RequestMethod.POST)
	public ModelAndView updateJobType(@ModelAttribute("command") JobTypeDto JobTypeBean, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			JobType DocType = DriDT.prepareJobTypeModel(JobTypeBean);
			JobType validate = JobTypeService.validateJobType(DocType.getJob_Type(), userDetails.getCompany_id());
			model.put("updateJobType", true);
			if(validate == null) {
				DocType.setLastModifiedById(userDetails.getId());
				DocType.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
				DocType.setCompanyId(userDetails.getCompany_id());
				if(userDetails.getCompany_id() == CompanyConstant.COMPANY_CODE_FLEETOP) {
					DocType.setCompanyId(0);
				}
				
				JobTypeHistory		jobTypeHistory	= DriDT.prepareHistoryModel(JobTypeService.getJobType(JobTypeBean.getJob_id(), userDetails.getCompany_id()));
				
				JobTypeService.updateJobType(DocType);
				jobTypeHistoryService.addJobTypeHistory(jobTypeHistory);
				
				model.put("updateJobType", true);
			}else {
				model.put("alreadyJobType", true);
			}
			
		} catch (Exception e) {
			System.out.println("inside catch : "+e);
			LOGGER.error("Job Type Page:", e);
			model.put("alreadyJobType", true);
			return new ModelAndView("redirect:/addJobType.in", model);
		}
		return new ModelAndView("redirect:/addJobType.in", model);
	}

	@RequestMapping(value = "/editJobTypes", method = RequestMethod.GET)
	public ModelAndView editJob(@ModelAttribute("command") JobTypeDto JobTypeBean, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			model.put("JobType", DriDT.prepareJobTypeDto(JobTypeService.getJobType(JobTypeBean.getJob_id(), userDetails.getCompany_id())));
		} catch (Exception e) {
			LOGGER.error("Job Type Page:", e);
		}
		return new ModelAndView("editJobType", model);
	}

	@RequestMapping(value = "/deleteJobType", method = RequestMethod.GET)
	public ModelAndView deleteJobType(@ModelAttribute("command") JobTypeDto JobTypeBean, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			JobTypeHistory		jobTypeHistory	= DriDT.prepareHistoryModel(JobTypeService.getJobType(JobTypeBean.getJob_id(), userDetails.getCompany_id()));
			
			JobTypeService.deleteJobType(JobTypeBean.getJob_id(), userDetails.getCompany_id());
			jobTypeHistoryService.addJobTypeHistory(jobTypeHistory);
			
			model.put("deleteJobType", true);
		} catch (Exception e) {
			LOGGER.error("Job Type Page:", e);
			model.put("alreadyJobType", true);
			return new ModelAndView("redirect:/addJobType.in", model);
		}
		return new ModelAndView("redirect:/addJobType.in", model);
	}
	  
		  @RequestMapping(value = "/addReasonForRepairType", method = RequestMethod.GET)
			public ModelAndView addReasonForRepairType(@ModelAttribute("command") ReasonForRepairDto JobTypeBean, BindingResult result) {
				Map<String, Object> model = new HashMap<String, Object>();
				try {
					CustomUserDetails	customUserDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					model.put("ReasonRepairType", RTBL.ReasonListofDto(ReasonTypeService.listReasonTypeByCompanyId(customUserDetails.getCompany_id())));
					
					model.put("userCompanyId", customUserDetails.getCompany_id());
				} catch (Exception e) {
					LOGGER.error("Job Type Page:", e);
				}
				return new ModelAndView("addReasonForRepairType", model);
			}	
		
		  @RequestMapping(value = "/saveReasonForRepairType", method =RequestMethod.POST) public ModelAndView
		  saveReasonForRepairType(@ModelAttribute("command") ReasonForRepairDto
			  JobTypeBean, BindingResult result) { Map<String, Object> model = new
			  HashMap<String, Object>();
			  
			try { 

			  CustomUserDetails userDetails =(CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			  ReasonForRepairType reasonType = RTBL.prepareReasonForRepairType(JobTypeBean);
		  
			  ReasonForRepairType validate =ReasonTypeService.validateReasonRepairType(reasonType.getReason_Type(),userDetails.getCompany_id()); 
			  if (validate == null) {
			  if(userDetails.getCompany_id() == CompanyConstant.COMPANY_CODE_FLEETOP) {
				  reasonType.setCompanyId(0); 
			  }else {
				  reasonType.setCompanyId(userDetails.getCompany_id()); }
			  
			  reasonType.setCreatedById(userDetails.getId()); 
			  reasonType.setCreatedOn(new Timestamp(System.currentTimeMillis())); 
			  
			  ReasonTypeService.addResonRepairType(reasonType);
			  
			  model.put("saveJobType", true); } else { model.put("alreadyJobType", true);
			  
			  return new ModelAndView("redirect:/addReasonForRepairType.in", model); } 
			  
			  }
			  catch (Exception e) {
				  LOGGER.error("Reason For Repair Page:", e);
				  model.put("ReasonForRepairType", true); 
				  return new ModelAndView("redirect:/addReasonForRepairType.in", model); 
			  } 
			   return new ModelAndView("redirect:/addReasonForRepairType.in", model);
		  }
			
			@RequestMapping(value = "/updateReasonType", method = RequestMethod.POST)
			public ModelAndView updateReasonType(@ModelAttribute("command") ReasonForRepairDto JobTypeBean, BindingResult result) {
				Map<String, Object> model = new HashMap<String, Object>();
				try {
					CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					ReasonForRepairType DocType = RTBL.prepareReasonTypeModel(JobTypeBean);
					
					ReasonForRepairType validate = ReasonTypeService.validateReasonType(DocType.getReason_Type(), userDetails.getCompany_id());
					model.put("updateReasonType", true);
					if(validate == null) {
						DocType.setLastModifiedById(userDetails.getId());
						DocType.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
						DocType.setCompanyId(userDetails.getCompany_id());
						if(userDetails.getCompany_id() == CompanyConstant.COMPANY_CODE_FLEETOP) {
							DocType.setCompanyId(0);
						}
						
						ReasonTypeHistory		reasonTypeHistory	= RTBL.prepareReasonHistoryModel(ReasonTypeService.getReasonType(JobTypeBean.getReason_id() , userDetails.getCompany_id()));
						
						ReasonTypeService.updateReasonType(DocType);
						
						reasonTypeHistoryService.addReasonTypeHistory(reasonTypeHistory);
						
						model.put("updateReasonType", true);
					}else {
						model.put("alreadyReasonType", true);
					}
					
				} catch (Exception e) {
					LOGGER.error("Reason Repair Type Page:", e);
					model.put("alreadyReasonType", true);
					return new ModelAndView("redirect:/addReasonForRepairType.in", model);
				}
				return new ModelAndView("redirect:/addReasonForRepairType.in", model);
			}
			
			@RequestMapping(value = "/editReasonType", method = RequestMethod.GET)
			public ModelAndView editReasonType(@ModelAttribute("command") ReasonForRepairDto JobTypeBean, BindingResult result) {
				Map<String, Object> model = new HashMap<String, Object>();
				try {
					CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

					model.put("ReasonRepairType", RTBL.prepareReasonTypeDto(ReasonTypeService.getReasonType(JobTypeBean.getReason_id() , userDetails.getCompany_id())));
				
				} catch (Exception e) {
					LOGGER.error("Reason Repair Type Page:", e);
				}
				return new ModelAndView("editReasonType", model);
			}
			

			
			@RequestMapping(value = "/deleteReasonType", method = RequestMethod.GET)
			public ModelAndView deleteReasonType(@ModelAttribute("command") ReasonForRepairDto ReasonTypeBean, BindingResult result) {
				Map<String, Object> model = new HashMap<String, Object>();
				try {
					CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

					ReasonTypeHistory		jobTypeHistory	= RTBL.prepareReasonHistoryModel(ReasonTypeService.getReasonType(ReasonTypeBean.getReason_id() , userDetails.getCompany_id()));
					
					ReasonTypeService.deleteReasonType(ReasonTypeBean.getReason_id(), userDetails.getCompany_id());
					reasonTypeHistoryService.addReasonTypeHistory(jobTypeHistory);
					
					model.put("deleteReasonType", true);
				} catch (Exception e) {
					LOGGER.error("Reason For Repair Type Page:", e);
					model.put("alreadyReasonType", true);
					return new ModelAndView("redirect:/addReasonForRepairType.in", model);
				}
				return new ModelAndView("redirect:/addReasonForRepairType.in", model);
			}

}
