package org.fleetopgroup.web.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IDriverAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AttendanceReportRestController {
	
		@Autowired private IDriverAttendanceService		driverAttendanceService;
	
		@RequestMapping(value = "/getAttandanceReprtAjax", method = RequestMethod.POST, produces="application/json")
		public HashMap<String, Object> getAttandanceReprtAjax(@RequestParam HashMap<String, Object> allRequestParams) throws Exception {
			try {
				HashMap<String, Object> respone = new HashMap<>();
			    	 respone	= driverAttendanceService.getAttandanceReport(allRequestParams);
			      return respone;
			} catch (Exception e) {
				throw e;
			}
		
	   }

}
