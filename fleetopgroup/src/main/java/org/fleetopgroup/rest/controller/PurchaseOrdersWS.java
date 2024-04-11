package org.fleetopgroup.rest.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.DriverReminderBL;
import org.fleetopgroup.persistence.bl.InventoryBL;
import org.fleetopgroup.persistence.bl.MasterPartsBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.PurchaseOrdersBL;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.ServiceReminderBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.IBatteryService;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryStockTypeDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersService;
import org.fleetopgroup.registration.listener.SendHTMLEmail;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class PurchaseOrdersWS {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	@Autowired private IPurchaseOrdersService 					PurchaseOrdersService;
	@Autowired private IInventoryService 						inventoryService;
	@Autowired private IBatteryService 							batteryService;
	@Autowired private IInventoryTyreService 					tyreService;
	@Autowired private IClothInventoryStockTypeDetailsService 	clothStockService;
	@Autowired private IInventoryService 						InventoryService;
	
	
	MasterPartsBL 			MPBL 		= new MasterPartsBL();
	PartLocationsBL			PLBL 		= new PartLocationsBL();
	PartLocationsBL 		PartBL 		= new PartLocationsBL();
	VendorBL 				VenBL		= new VendorBL();
	InventoryBL 			InvenBL 	= new InventoryBL();
	MasterPartsBL 			MasBL 		= new MasterPartsBL();
	PurchaseOrdersBL 		POBL 		= new PurchaseOrdersBL();
	RenewalReminderBL		RRBL 		= new RenewalReminderBL();
	DriverReminderBL 		DriverRem 	= new DriverReminderBL();
	VehicleBL 				VBL 		= new VehicleBL();
	ServiceReminderBL 		SRBL 		= new ServiceReminderBL();
	DriverBL 				DBL 		= new DriverBL();
	SendHTMLEmail 			htmlemail 	= new SendHTMLEmail();

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat mailDate = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	
	@RequestMapping(value="/getPurchaseOrdersPartDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getPurchaseOrdersPartDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= PurchaseOrdersService.getPurchaseOrdersPartDetails(valueInObject);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@PostMapping(value="/addPurchaseOrderPart", produces="application/json")
	public HashMap<Object, Object>  addUreaToPurchaseOrder(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			
			valueInObject		= PurchaseOrdersService.savePurchaseOrderPart(valueInObject);
			return valueInObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/deletePurchaseOrderPart", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  deletePurchaseOrderPart(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= PurchaseOrdersService.deletePurchaseOrderPart(valueInObject);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	//common for all
	@RequestMapping(value="/sentToPurchaseOrder", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  sentToPurchaseOrder(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= PurchaseOrdersService.sentToPurchaseOrder(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	//common for all
	@RequestMapping(value="/receivedQuantityToPurchaseOrder", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  receivedQuantityToPurchaseOrder(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= PurchaseOrdersService.receivedQuantityToPurchaseOrder(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	
	@RequestMapping(value="/reEnterPurchaseOrder", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  reEnterPurchaseOrder(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= PurchaseOrdersService.reEnterPurchaseOrder(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/receivedPartFromPurchaseOrder", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  receivedPartFromPurchaseOrder(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueInObject.put("ureaDetails", JsonConvertor.toValueObjectFromJsonString(valueInObject.getString("ureaDetails")));
			valueOutObject		= new ValueObject();
			
			valueOutObject		= PurchaseOrdersService.receivedPartFromPurchaseOrder(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/deleteReturnPurchaseOrdersDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  deleteReturnPurchaseOrdersDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= PurchaseOrdersService.deleteReturnPurchaseOrdersDetails(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/completePurchaseOrder", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  completePurchaseOrder(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueInObject.put("ureaDetails", JsonConvertor.toValueObjectFromJsonString(valueInObject.getString("ureaDetails")));
			valueOutObject		= new ValueObject();
			
			valueOutObject		= PurchaseOrdersService.completePurchaseOrder(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/getPurchaseOrdersDebitNoteDetails", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  getPurchaseOrdersDebitNoteDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;
		try {
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= PurchaseOrdersService.getPurchaseOrdersDebitNoteDetails(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/saveVendorInPO", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveVendorInPO(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {

		ValueObject					valueInObject 					= null;
		ValueObject 				valueOutObject 					= null;

		try {
			
			valueInObject		= new ValueObject(allRequestParams);
			valueOutObject		= new ValueObject();
			
			valueOutObject		= PurchaseOrdersService.updateVendorDetailsInPurchaseOrder(valueInObject);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 					= null;
			valueInObject 					= null;
		}
	}
	
	@RequestMapping(value="/saveTallyCompanyIdInPO", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  saveTallyCompanyIdInPO(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return PurchaseOrdersService.updateTallyCompaanyDetailsInPurchaseOrder(object).getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@RequestMapping(value="/updatePartPOStatusFromReceivedToOrdered", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updatePartPOStatusFromReceivedToOrdered(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return PurchaseOrdersService.updatePartPOStatusFromReceivedToOrdered(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@RequestMapping(value="/updateBatteryPOStatusFromReceivedToOrdered", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateBatteryPOStatusFromReceivedToOrdered(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return PurchaseOrdersService.updateBatteryPOStatusFromReceivedToOrdered(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@RequestMapping(value="/updateTyrePOStatusFromReceivedToOrdered", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateTyrePOStatusFromReceivedToOrdered(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return PurchaseOrdersService.updateTyrePOStatusFromReceivedToOrdered(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@RequestMapping(value="/updateUpholsteryPOStatusFromReceivedToOrdered", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateUpholsteryPOStatusFromReceivedToOrdered(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return PurchaseOrdersService.updateUpholsteryPOStatusFromReceivedToOrdered(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@RequestMapping(value="/updateUreaPOStatusFromReceivedToOrdered", produces="application/json", method=RequestMethod.POST)
	public HashMap<Object, Object>  updateUreaPOStatusFromReceivedToOrdered(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return PurchaseOrdersService.updateUreaPOStatusFromReceivedToOrdered(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	@PostMapping(value="/updatePurchaseOrderPartDetails", produces="application/json")
	public HashMap<Object, Object>  updatePurchaseOrderPartDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return PurchaseOrdersService.updatePurchaseOrderPartDetails(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@PostMapping(value="/getLocationWisePartQuantity", produces="application/json")
	public HashMap<Object, Object>  getPartLocationQuantity(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return inventoryService.getLocationWisePartQuantity(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	@PostMapping(value="/getLocationWiseBatteryQuantity", produces="application/json")
	public HashMap<Object, Object>  getLocationWiseBatteryQuantity(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return batteryService.getLocationWiseBatteryQuantity(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@PostMapping(value="/getLocationWiseTyreQuantity", produces="application/json")
	public HashMap<Object, Object>  getLocationWiseTyreQuantity(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return tyreService.getLocationWiseTyreQuantity(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	@PostMapping(value="/getLocationWiseUpholsteryQuantity", produces="application/json")
	public HashMap<Object, Object>  getLocationWiseUpholsteryQuantity(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			return clothStockService.getLocationWiseUpholsteryQuantity(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	@PostMapping(value="/createPurchaseOrderPartApproval", produces="application/json")
	public HashMap<Object, Object>  createPurchaseOrderPartApproval(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			object		= new ValueObject(allRequestParams);
			object.put("tyreExpenseDetailsList", JsonConvertor.toValueObjectFromJsonString(object.getString("purchaseOrderToPartList")));
			return PurchaseOrdersService.createPurchaseOrderPartApproval(object).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	

	@RequestMapping(value ="/getlocationWisePartCount", method = RequestMethod.GET , produces="application/json")
	public HashMap<Object, Object> getlocationWisePartCount(@RequestParam(value = "PARTID", required = true) Long PART_ID,@RequestParam("shipLocationId") int locationId) throws Exception {
		ValueObject					object 	= null;
		CustomUserDetails userDetails = null;
		try {
			userDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object 					= new ValueObject();	
			object.put("locationWiseCount", Utility.round(InventoryService.getLocationWisePartSum(PART_ID,locationId,userDetails.getCompany_id()),2));
			object.put("otherLocationCount", Utility.round(InventoryService.getOtherWisePartSum(PART_ID,locationId,userDetails.getCompany_id()),2));

			return object.getHtData();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	
	@RequestMapping(value ="/getlocationWiseBatteryCount", method = RequestMethod.GET , produces="application/json")
	public HashMap<Object, Object> getlocationWiseBatteryCount(@RequestParam HashMap<Object, Object> object) throws Exception {
		ValueObject					valueObject 	= null;
		CustomUserDetails userDetails = null;
		try {
			valueObject = new ValueObject(object);
			userDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("companyId", userDetails.getCompany_id());
			return batteryService.getlocationWiseBatteryCount(valueObject).getHtData();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	
	@RequestMapping(value ="/getlocationWiseTyreCount", method = RequestMethod.GET , produces="application/json")
	public HashMap<Object, Object> getlocationWiseTyreCount(@RequestParam HashMap<Object, Object> object) throws Exception {
		ValueObject					valueObject 	= null;
		CustomUserDetails userDetails = null;
		try {
			valueObject = new ValueObject(object);
			userDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("companyId", userDetails.getCompany_id());
			return tyreService.getlocationWiseTyreCount(valueObject).getHtData();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	
	@PostMapping(value="/getSubPartList", produces="application/json")
	public HashMap<Object, Object>  getSubPartList(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object		= new ValueObject(allRequestParams);
			object.put("partList", inventoryService.getSubstitudePartList(object.getString("partId","0"), userDetails.getCompany_id()));
			return object.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	@PostMapping(value="/approvalPO", produces="application/json")
	public HashMap<Object, Object>  approvalPO(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object		= new ValueObject(allRequestParams);
			object.put("companyId", userDetails.getCompany_id());
			object.put("userId", userDetails.getId());
			return PurchaseOrdersService.approvalPO(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
	
	@RequestMapping(value = "/getPurchaseOrderStatusWiseReport", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getPartPurchaseInvoiceReport(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 				object 				= null;
		ValueObject					valueOutObject 		= null;
		try {
			object = new ValueObject(allRequestParams);
			valueOutObject = PurchaseOrdersService.getPurchaseOrderStatusWiseReport(object);
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@PostMapping(value="/savePoRemark", produces="application/json")
	public HashMap<Object, Object>  savePoRemark(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object		= new ValueObject(allRequestParams);
			object.put("companyId", userDetails.getCompany_id());
			object.put("userId", userDetails.getId());
			return PurchaseOrdersService.SavePoRemark(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}

	@PostMapping(value="/getPoRemark", produces="application/json")
	public HashMap<Object, Object>  getRemark(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject					object 					= null;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object		= new ValueObject(allRequestParams);
			object.put("companyId", userDetails.getCompany_id());
			object.put("userId", userDetails.getId());
			return PurchaseOrdersService.getPoRemark(object).getHtData();
			
		} catch (Exception e) {
			throw e;
		} finally {
			object 	= null;
		}
	}
	
}
