package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.service.RequisitionReportService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RequisitionReportController {
	
	@Autowired RequisitionReportService requisitionReportService;
	
	@GetMapping("/RequisitionReport")
	public ModelAndView Requisition () {
		return new ModelAndView("RequisitionReport");
	}
	@PostMapping(path="/getRequisitionReportDetails",produces="application/json")
	public HashMap<Object, Object> getRequisitionReportDetails(@RequestParam HashMap<Object, Object> allRequestParam) throws Exception{
		try {
			return requisitionReportService.getRequisitionReportDetails( new ValueObject(allRequestParam)).getHtData();
		} catch (Exception e) {
			throw e;
		}
	}
}
