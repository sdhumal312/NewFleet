/**
 * 
 */
package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.fleetopgroup.persistence.model.EmployeeDetails;
import org.fleetopgroup.persistence.serviceImpl.IEmployeeDetailsService;
import org.fleetopgroup.web.error.MailSentExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author fleetop
 *
 */
@Controller
public class EmployeeDetailsController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IEmployeeDetailsService employeeDetailsService;

	@RequestMapping(value = "/Registration", method = RequestMethod.GET)
	public ModelAndView Registration(@ModelAttribute("command") EmployeeDetails empDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("emp_Registration", model);
	}

	/**
	 * this save mail in to own and also sent person
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveETR", method = RequestMethod.POST)
	public ModelAndView saveETR(@Valid final EmployeeDetails empDto, final HttpServletRequest request)
			throws Exception {
		
		LOGGER.error("Registering empDto with information: {}", empDto);

		try {
			List<EmployeeDetails> validate = employeeDetailsService.validate_EmployeeDetails(empDto.getEMP_NUMBER(),
					empDto.getEMP_EMAIL());
			if (validate != null && !validate.isEmpty()) {
				return new ModelAndView("redirect:/Registration?danger=true");
			} else {
				EmployeeDetails saveEmp = prepareEmployeeDetails(empDto);
				employeeDetailsService.add_EmployeeDetails(saveEmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new MailSentExistException();
		}
		return new ModelAndView("redirect:/Registration?success=true");
	}

	private EmployeeDetails prepareEmployeeDetails(EmployeeDetails empDto) {
		EmployeeDetails saveEmp = new EmployeeDetails();

		saveEmp.setEMPID(empDto.getEMPID());
		saveEmp.setEMP_FIRSTNAME(empDto.getEMP_FIRSTNAME());
		saveEmp.setEMP_LASTNAME(empDto.getEMP_LASTNAME());
		saveEmp.setEMP_NUMBER(empDto.getEMP_NUMBER());
		saveEmp.setEMP_ADDRESS(empDto.getEMP_ADDRESS());
		saveEmp.setEMP_STREET(empDto.getEMP_STREET());
		saveEmp.setEMP_STATE(empDto.getEMP_STATE());
		saveEmp.setEMP_CITY(empDto.getEMP_CITY());
		saveEmp.setEMP_COUNTRY(empDto.getEMP_COUNTRY());
		saveEmp.setEMP_PINCODE(empDto.getEMP_PINCODE());
		saveEmp.setEMP_LANDMARK(empDto.getEMP_LANDMARK());
		saveEmp.setEMP_MOBILE(empDto.getEMP_MOBILE());
		saveEmp.setEMP_ALTERMOBILE(empDto.getEMP_ALTERMOBILE());
		saveEmp.setEMP_EMAIL(empDto.getEMP_EMAIL());
		saveEmp.setEMP_COMPANY(empDto.getEMP_COMPANY());
		
		/** Set Status in Issues */
		saveEmp.setMarkForDelete(false);
		
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		/** Set Created Current Date */
		saveEmp.setCREATED(toDate);
		saveEmp.setLASTUPDATED(toDate);

		return saveEmp;
	}
}
