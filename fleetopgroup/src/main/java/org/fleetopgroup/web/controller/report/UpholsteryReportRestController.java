package org.fleetopgroup.web.controller.report;

import java.util.HashMap;
import java.util.Map;

import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class UpholsteryReportRestController {
	
	@Autowired IClothInventoryService ClothInventoryService ;
	
	@RequestMapping("/UR")
	public ModelAndView FuelReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("UR", model);
	}
	
	@RequestMapping("/upholsteryPurchaseInvoiceReport")
	public ModelAndView PartWiseConsumptionReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("upholsteryPurchaseInvoiceReport", model);
	}
	
	@RequestMapping(value = "/getUpholsteryPurchaseInvoiceReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getConductorHistoryReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		ValueObject		valueOutObject 	= null;
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = ClothInventoryService.getUpholsteryPurchaseInvoiceReport(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	//Laundry Upholstery receive report start Step 1
	@RequestMapping("/laundryUpholsteryReceiveReport")
	public ModelAndView laundryUpholsteryReceiveReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("laundryUpholsteryReceiveReport", model);
	}	
	//Laundry Upholstery receive report stop Step 1
	
	
	
	//Laundry Upholstery receive report start Step 2
	@RequestMapping(value = "/getLaundryUpholsteryReceiveReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getLaundryUpholsteryReceiveReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		ValueObject		valueOutObject 	= null;
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = ClothInventoryService.getLaundryUpholsteryReceiveReport(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}	

	@RequestMapping("/upholsteryStockReport")
	public ModelAndView upholsteryStockReport() {
		Map<String, Object> model=new HashMap<String, Object>();
		return new 	ModelAndView("upholsteryStockReport",model);	
	}	
	
	@RequestMapping(value = "/getUpholsteryStockReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getUpholsteryStockReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		ValueObject		valueOutObject 	= null;		
		try {
			object = new ValueObject(allRequestParams);			
			valueOutObject = ClothInventoryService.getUpholsteryStockReport(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}	
	
	@RequestMapping("/upholsteryAssignmentReport")
	public ModelAndView upholsteryAssignmentReport() {
		Map<String, Object> model=new HashMap<String, Object>();
		return new 	ModelAndView("upholsteryAssignmentReport",model);	
	}	
	
	@RequestMapping(value = "/getUpholsteryAssignmentReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getUpholsteryAssignmentReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		ValueObject		valueOutObject 	= null;		
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = ClothInventoryService.getUpholsteryAssignmentReport(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}	
	
	@RequestMapping("/upholsteryStockTransferReport")
	public ModelAndView stockTransferReport() {
		Map<String, Object> model=new HashMap<String, Object>();
		return new 	ModelAndView("upholsteryStockTransferReport",model);	
	}	
	
	@RequestMapping(value = "/getUpholsteryStockTransferReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getstockTransferReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		ValueObject		valueOutObject 	= null;		
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = ClothInventoryService.getUpholsteryStockTransferReport(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	
	//Upholstery Sent To Laundry Report Start Step1
	@RequestMapping("/upholsterySentToLaundryReport")
	public ModelAndView upholsterySentToLaundryReport() {
			Map<String, Object> model = new HashMap<String, Object>();
			return new 	ModelAndView("upholsterySentToLaundryReport",model);	
	}	
	//Upholstery Sent To Laundry Report Stop Step1	
	
	//Upholstery Sent To Laundry Report Start Step2
	@RequestMapping(value = "/getUpholsterySentToLaundryReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getUpholsterySentToLaundryReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 	object 			= null;
		ValueObject		valueOutObject 	= null;
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = ClothInventoryService.getUpholsterySentToLaundryReport(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}	
	//Upholstery Sent To Laundry Report Stop Step2
	
	@RequestMapping("/upholsteryLossReport")
	public ModelAndView upholsteryLossReport() {
		
			Map<String, Object> model = new HashMap<String, Object>();
			return new 	ModelAndView("upholsteryLossReport",model);	
	}
	
	@RequestMapping(value = "/getUpholsteryLossReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getUpholsteryLossReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		
		ValueObject 	object 			= null;
		ValueObject		valueOutObject 	= null;
		
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = ClothInventoryService.getUpholsteryLossReport(object);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}	
	
}