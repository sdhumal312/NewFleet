package org.fleetopgroup.rest.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.fleetopgroup.constant.WorkOrdersType;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/workOrderWS")
public class WorkOrderWS {
	
	@Autowired IWorkOrdersService   	workOrdersService;
	@Autowired ICompanyService 			companyService;
	
	@RequestMapping(value="/getWorkOrderPrint", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getWorkOrderPrint(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{

		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		CustomUserDetails			userDetails						= null;
		long						workorders_id					= 0;

		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			workorders_id		= object.getLong("workorders_id", 0);
			
			valueOutObject.put("company_id", userDetails.getCompany_id());
			valueOutObject.put("companyDetails", companyService.getCompanyByID(userDetails.getCompany_id()));
			valueOutObject.put("WorkOrder", finalizeWorkOrders(workOrdersService.getWorkOrdersWithVehicleDetails(workorders_id, userDetails.getCompany_id())));
			valueOutObject.put("WorkOrderTasks", workOrdersService.getWorkOrderTasksWithMechanic(workorders_id, userDetails.getCompany_id()));
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
			userDetails						= null;
			workorders_id					= 0;   
		}
	}

	@RequestMapping(value="/updateTaskRemarkById", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateTaskRemarkById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		
		try {
			
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			
			valueOutObject		= workOrdersService.updateTaskRemarkById(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}

	private WorkOrdersDto finalizeWorkOrders(WorkOrdersDto workOrdersDto) throws Exception {
		
		SimpleDateFormat dateFormat = null;
		
		try {
			
			dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			
			if (workOrdersDto.getStart_dateOn() != null) {
				workOrdersDto.setStart_date(dateFormat.format(workOrdersDto.getStart_dateOn()));
			} else {
				workOrdersDto.setStart_date("--");				
			}
			
			if (workOrdersDto.getDue_dateOn() != null) {
				workOrdersDto.setDue_date(dateFormat.format(workOrdersDto.getDue_dateOn()));
			} else {
				workOrdersDto.setDue_date("--");				
			}
			
			if (workOrdersDto.getCompleted_dateOn() != null) {
				workOrdersDto.setCompleted_date(dateFormat.format(workOrdersDto.getCompleted_dateOn()));
			} else {
				workOrdersDto.setCompleted_date("--");
			}

			workOrdersDto.setWorkorders_status(WorkOrdersType.getStatusName(workOrdersDto.getWorkorders_statusId()));
			
			if(workOrdersDto.getWorkorders_route() != null) {
				workOrdersDto.setWorkorders_route(workOrdersDto.getWorkorders_route());
			} else {
				workOrdersDto.setWorkorders_route("--");				
			}
			
			return workOrdersDto;
		} catch (Exception e) {
			throw e;
		} finally {
			dateFormat = null;
		}
	}	
	
	@RequestMapping(value = "/getWorkOrderAndDse_Details", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getWorkOrderAndDse_Details(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 				= null;
		ValueObject					valueOutObject 		= null;
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = workOrdersService.getWorkOrderAndDseDetails(object);
			return valueOutObject.getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
}