package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.bl.DepartmentBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DepartmentDto;
import org.fleetopgroup.persistence.model.Department;
import org.fleetopgroup.persistence.model.DepartmentHistory;
import org.fleetopgroup.persistence.serviceImpl.IDepartmentHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IDepartmentService;
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
public class DepartmentController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IDepartmentService DepartmentService;

	@Autowired
	private IDepartmentHistoryService departmentHistoryService;
	
	DepartmentBL DriDT = new DepartmentBL();

	
	@RequestMapping(value = "/addDepartment", method = RequestMethod.GET)
	public ModelAndView addDepartment(@ModelAttribute("command") DepartmentDto DepartmentDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// SearchDepartmentLisrCompanyID
			CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			model.put("companyId", currentUser.getCompany_id());
			if (currentUser != null) {
				model.put("department", DriDT.DriDocTypeListofDto(
						DepartmentService.SearchDepartmentLisrCompanyID(currentUser.getCompany_id())));
			}

		} catch (Exception e) {
			LOGGER.error("Driver Document Page:", e);
		}
		return new ModelAndView("addDepartment", model);
	}

	@RequestMapping(value = "/saveDepartment", method = RequestMethod.POST)
	public ModelAndView saveDepartment(@ModelAttribute("command") DepartmentDto DepartmentDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Department GET_Type = DriDT.prepareDepatmentSave(DepartmentDto);
			List<Department> validate = DepartmentService.validateDepartmentName(DepartmentDto.getDepart_name(),
					DepartmentDto.getCompany_id());
			CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			if (validate.isEmpty() || validate == null) {
				try {
					GET_Type.setCompany_id(currentUser.getCompany_id());
					GET_Type.setCreatedById(currentUser.getId());
					GET_Type.setCreatedOn(new Timestamp(System.currentTimeMillis()));
					DepartmentService.registerNewDepartment(GET_Type);
					model.put("saveDepartment", true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				model.put("alreadyDepartment", true);
				return new ModelAndView("redirect:/addDepartment.html", model);
			}

		} catch (Exception e) {
			model.put("alreadyDepartment", true);
			LOGGER.error("Driver Document Page:", e);
			return new ModelAndView("redirect:/addDepartment.html", model);
		}
		return new ModelAndView("redirect:/addDepartment.html", model);
	}

	@RequestMapping(value = "/editDepartment", method = RequestMethod.GET)
	public ModelAndView editdriver(@ModelAttribute("command") DepartmentDto DepartmentDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("department",
					DriDT.prepareDepartmentDto(DepartmentService.getDepartmentByID(DepartmentDto.getDepart_id())));

		} catch (Exception e) {
			LOGGER.error("Driver Document Page:", e);
		}
		return new ModelAndView("editDepartment", model);
	}

	@RequestMapping(value = "/updateDepartment", method = RequestMethod.POST)
	public ModelAndView updateDepartment(@ModelAttribute("command") DepartmentDto DepartmentDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Department DocType = DriDT.prepareDepatmentUpdate(DepartmentDto, currentUser.getId());
			
			DepartmentHistory		departmentHistory	= DriDT.prepareDepartmentHistoryDTO(DepartmentService.getDepartmentByID(DepartmentDto.getDepart_id()));
			
			try {

				DocType.setCompany_id(currentUser.getCompany_id());
				DepartmentService.updateDepartment(DocType);
				departmentHistoryService.registerNewDepartmentHistory(departmentHistory);
				model.put("updateDepartment", true);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			model.put("alreadyDepartment", true);
			LOGGER.error("Driver Document Page:", e);
			return new ModelAndView("redirect:/addDepartment.html", model);
		}
		return new ModelAndView("redirect:/addDepartment.html", model);
	}

	@RequestMapping(value = "/deleteDepartment", method = RequestMethod.GET)
	public ModelAndView deleteDepartment(@ModelAttribute("command") DepartmentDto DepartmentDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			
			DepartmentHistory		departmentHistory	= DriDT.prepareDepartmentHistoryDTO(DepartmentService.getDepartmentByID(DepartmentDto.getDepart_id()));
			
			DepartmentService.deleteDepartment(DepartmentDto.getDepart_id(), userDetails.getCompany_id());
			departmentHistoryService.registerNewDepartmentHistory(departmentHistory);

			model.put("deleteDepartment", true);

		} catch (Exception e) {
			model.put("alreadyDepartment", true);
			LOGGER.error("Driver Document Page:", e);
			return new ModelAndView("redirect:/addDepartment.html", model);
		}
		return new ModelAndView("redirect:/addDepartment.html", model);
	}
}