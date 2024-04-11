package org.fleetopgroup.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.model.PartLocations;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.ExceptionProcess;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkOrderControllerOnMob {
	@Autowired private  IWorkOrdersService						workOrdersService;
	@Autowired private	IDriverService 						    driverService;
	@Autowired private	IPartLocationsService 				    ipartLocationsService;
	@Autowired private	IVehicleService 				        vehicleService;

	@RequestMapping(value = "/searchWoByDifferentParameters", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  searchWoByDifferentParameters(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.searchWoByDifferentParameters(object).getHtData();
		} catch (Exception e) {
			throw e;
		}finally{
			object 		= null;
		}
	}
	@RequestMapping(value = "/getWorkOrderList", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getWorkOrderList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.getWorkOrderList(object).getHtData();
		} catch (Exception e) {
			throw e;
		}finally{
			object 		= null;
		}
	}
	@RequestMapping(value = "/getWorkOrderInitialDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getWorkOrderInitialDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.getWorkOrderInitialDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		}finally{
			object 		= null;
		}
	}
	@RequestMapping(value = "/getDriverSearchListWorkOrder", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getDriverSearchListWorkOrder(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object.put("driverList",driverService.searchByDriverName(object.getString("term",""),object.getInt("companyId",0),object.getLong("userId",0)));
			return	object.getHtData();
		} catch (Exception e) {
			throw e;
		}finally{
			object 		= null;
		}
	}
	@RequestMapping(value = "/getUserEmailId_Subscrible", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getUserEmailId_Subscrible(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.getUserEmailId_Subscrible(object).getHtData();
		} catch (Exception e) {
			throw e;
		}finally{
			object 		= null;
		}
	}
	@RequestMapping(path = "/saveWorkOrderDetailsFromMob", produces="application/json")
	public HashMap<Object, Object>  saveWorkOrderDetails(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			object.put("workOrderDetails", JsonConvertor.toValueObjectFromJsonString(object.getString("workOrderDetails")));
			return workOrdersService.saveWorkOrder(object).getHtData();
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	@RequestMapping(path = "/getOdometerDetailsForWO", produces="application/json")
	public HashMap<Object, Object>  getOdometerDetails(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.getVehicleOdoMeter(object.getInt("companyId",0),object.getInt("vid",0)).getHtData();
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	@RequestMapping(path = "/getJobTypeWorkOrder", produces="application/json")
	public HashMap<Object, Object>  getJobTypeWorkOrder(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.getJobTypeWorkOrder(object.getInt("companyId",0),object.getString("term","")).getHtData();
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	@RequestMapping(path = "/getJobSubTypeWorkOrder", produces="application/json")
	public HashMap<Object, Object>  getJobSubTypeWorkOrder(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.getJobSubTypeWorkOrder(object.getInt("companyId",0),object.getString("term","")).getHtData();
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	@RequestMapping(path = "/getJobDetailsFromSubJobId", produces="application/json")
	public HashMap<Object, Object>  getJobDetailsFromSubJobId(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.getJobDetailsFromSubJobId(object.getInt("subJobId",0),object.getInt("companyId",0)).getHtData();
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	@RequestMapping(path = "/searchPartLocationListByMainLocatinId", produces="application/json")
	public HashMap<Object, Object>  searchPartLocationListByMainLocatinId(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		List<PartLocations> partLocationList        = null;
		try {
			object 				= new ValueObject(allRequestParams);
			partLocationList =  ipartLocationsService.searchPartLocationListByMainLocatinId(object.getString("term",""),object.getShort("locationType",(short) 0),object.getInt("mainLocationId",0),object);
			object.put("partLocationList", partLocationList);
			return object.getHtData();
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	@RequestMapping(path = "/showWorkOrderDetailsFromMob", produces="application/json")
	public HashMap<Object, Object>  showWorkOrderDetailsFromMob(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.showWorkOrderDetailsFromMob(object).getHtData();
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	@RequestMapping(path = "/vehicleWiseWorkorderServiceDetailsFromMob", produces="application/json")
	public HashMap<Object, Object>  vehicleWiseWorkorderServiceDetails(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.vehicleWiseWorkorderServiceDetails(object).getHtData();
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	@RequestMapping(path = "/getInvoiceWisePartList", produces="application/json")
	public HashMap<Object, Object>  getInvoiceWisePartList(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.getInvoiceWisePartList(object).getHtData();
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	@RequestMapping(path = "/getTechinicianWorkOrderFromMob", produces="application/json")
	public HashMap<Object, Object>  getTechinicianWorkOrderFromMob(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.getTechinicianWorkOrder(object.getString("term",""),object.getInt("companyId",0)).getHtData();
		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	@RequestMapping(path = "/savePartForWorkOderTaskFromMob", produces ="application/json")
	public HashMap<Object, Object>  savePartForWorkOderTask(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.savePartForWorkOderTask(object).getHtData();

		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	@RequestMapping(path = "/saveLabourForWorkOderTaskFromMob", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object>  saveLabourForWorkOderTask(@RequestParam HashMap<Object, Object> allRequestParams, HttpServletRequest request) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.saveLabourForWorkOderTask(object).getHtData();

		} catch (Exception e) {
			return ExceptionProcess.execute(request, e).getHtData();
		} 
	}
	@RequestMapping(value = "/saveWorkOderTasksFromMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveWorkOderTasksFromMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.saveWorkOderTasks(object).getHtData();

		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/changeWorkorderStatusToHoldFromMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  changeWorkorderStatusToHold(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.changeWorkorderStatusToHold(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}

	@RequestMapping(value = "/changeWorkorderStatusToInProgressFromMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  changeWorkorderStatusToInProgress(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.changeWorkorderStatusToInProgress(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}

	@RequestMapping(value = "/changeWorkorderStatusToCompleteFromMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  changeWorkorderStatusToComplete(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.changeWorkorderStatusToComplete(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/saveMarkAsCompleteFromMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  saveMarkAsComplete(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.saveMarkAsComplete(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/deletePartOfWorkOrdertaskFromMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deletePartOfWorkOrdertask(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.deletePartOfWorkOrdertask(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}

	@RequestMapping(value = "/deleteLabourOfWorkOrdertaskFromMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteLabourOfWorkOrdertask(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.deleteLabourOfWorkOrdertask(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}

	@RequestMapping(value = "/deleteWorkOrdertaskFromMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteWorkOrdertask(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.deleteWorkOrdertask(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(path = "/approveWorkOrderDetailsFromMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  approveWorkOrderDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.approveWorkOrderDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value="/uploadWorkOrderDocumentFromMob", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  uploadWorkOrderDocument(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				valueInObject					= null;
		ValueObject 				valueOutObject					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			valueOutObject		= workOrdersService.uploadWorkOrderDocument(valueInObject,null);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
		}
	}
	@RequestMapping(value = "/deleteWorkOrderDetailsFromMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  deleteWorkOrderDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.deleteWorkOrderDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/updateWorkOrderDetailsFromMob", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  updateWorkOrderDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.updateWorkOrderDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value = "/getworkOrderEditInitialDetails", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getworkOrderEditInitialDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		try {
			object 				= new ValueObject(allRequestParams);
			return workOrdersService.getworkOrderEditInitialDetails(object).getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}
	@RequestMapping(value="/updateTaskRemarkByIdFromMob", produces="application/json", method=RequestMethod.POST)
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
	@RequestMapping(value="/getVehicleServiceReminderList", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getVehicleServiceReminderList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception{
		ValueObject					valueOutObject 					= null;
		ValueObject 				object 							= null;
		try {
			valueOutObject		= new ValueObject();
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= workOrdersService.getVehicleServiceReminderList(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			object 							= null;
		}
	}
	@RequestMapping(value = "/getIntangleByVehicleNumberFromMob", method = RequestMethod.POST)
	public HashMap<Object, Object> getIntangleByVehicleNumberFromMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 					model 						 = null;
		ValueObject 					valueObject 				= null;
		try {
			valueObject 				= new ValueObject(allRequestParams); 
			model                       = vehicleService.getIntangleByVehicleNumberFromMob(valueObject);
			return model.getHtData();
		} catch (Exception e) {
			throw e;
		}
	}
	@RequestMapping(value = "/markAllTaskCompletedFromMob", method = RequestMethod.POST)
	public HashMap<Object, Object> markAllTaskCompletedFromMob(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 					model 						 = null;
		ValueObject 					valueObject 				= null;
		try {
			valueObject 				= new ValueObject(allRequestParams); 
			model                       = workOrdersService.markAllTaskCompletedFromMob(valueObject);
			return model.getHtData();
		} catch (Exception e) {
			throw e;
		}
	}
}
