package org.fleetopgroup.rest.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import org.fleetopgroup.persistence.serviceImpl.IStatusChangeRemarkService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusChangeRemarkWS {
	
	@Autowired  IStatusChangeRemarkService 		statusChangeRemarkService;
	Date 		currentDateUpdate 	= new Date();                                             
	Timestamp 	currentTimestamp 	= new java.sql.Timestamp(currentDateUpdate.getTime());   
	
	@PostMapping("/saveVehicleStatusRemark")
	public HashMap<Object, Object> saveVehicleStatusRemark(@RequestParam HashMap<Object, Object> valueAllObject) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(valueAllObject);
			statusChangeRemarkService.saveVehicleStatusRemark(valueObject);
			return valueObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@PostMapping("/getStatusChangeRemarkList")
	public HashMap<Object, Object> getStatusChangeRemarkHistory(@RequestParam HashMap<Object, Object> valueAllObject) throws Exception{
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject(valueAllObject);
			statusChangeRemarkService.getStatusChangeRemarkHistory(valueObject);
			return valueObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
}
