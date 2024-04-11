package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.constant.ClothInvoiceStockType;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PurchaseOrderType;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryAllDto;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.MasterPartsDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersToPartsDto;
import org.fleetopgroup.persistence.model.Battery;
import org.fleetopgroup.persistence.model.BatteryAmount;
import org.fleetopgroup.persistence.model.BatteryInvoice;
import org.fleetopgroup.persistence.model.ClothInventoryDetails;
import org.fleetopgroup.persistence.model.ClothInvoice;
import org.fleetopgroup.persistence.model.Inventory;
import org.fleetopgroup.persistence.model.InventoryAll;
import org.fleetopgroup.persistence.model.InventoryLocation;
import org.fleetopgroup.persistence.model.InventoryTyre;
import org.fleetopgroup.persistence.model.InventoryTyreAmount;
import org.fleetopgroup.persistence.model.InventoryTyreInvoice;
import org.fleetopgroup.persistence.model.PartInvoice;
import org.fleetopgroup.persistence.model.PurchaseOrders;
import org.fleetopgroup.persistence.model.PurchaseOrdersToDebitNote;
import org.fleetopgroup.persistence.model.PurchaseOrdersToParts;
import org.fleetopgroup.persistence.model.PurchaseOrdersToUrea;
import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrdersBL {
	
	@Autowired IInventoryService inventoryService;

	public PurchaseOrdersBL() {
	}
	Date currentDate = new Date();
	DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat dateFormat_name = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	DecimalFormat df2 = new DecimalFormat("#.##");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	VehicleBL VBL = new VehicleBL();

	// get to save in preparePurchaseOrders
	public PurchaseOrders UpdatePurchaseOrders(PurchaseOrdersDto PurchaseOrders) {

		PurchaseOrders Purchase = new PurchaseOrders();

		Purchase.setPurchaseorder_id(PurchaseOrders.getPurchaseorder_id());
		try {
			if (PurchaseOrders.getPurchaseorder_created_on() != null) {
				java.util.Date date = dateFormat.parse(PurchaseOrders.getPurchaseorder_created_on());
				java.sql.Date start_date = new java.sql.Date(date.getTime());
				Purchase.setPurchaseorder_created_on(start_date);
			}
			if (PurchaseOrders.getPurchaseorder_requied_on() != null) {
				java.util.Date requiddate = dateFormat.parse(PurchaseOrders.getPurchaseorder_requied_on());
				java.sql.Date requied_date = new java.sql.Date(requiddate.getTime());
				Purchase.setPurchaseorder_requied_on(requied_date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Purchase.setPurchaseorder_buyeraddress(PurchaseOrders.getPurchaseorder_buyeraddress());

		Purchase.setPurchaseorder_termsId(PurchaseOrders.getPurchaseorder_termsId());
		if (PurchaseOrders.getPurchaseorder_termsId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {

			Purchase.setPurchaseorder_vendor_paymodeId(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID);

		} else {
			Purchase.setPurchaseorder_vendor_paymodeId(InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_PAID);
			java.util.Date poPaiddate = new java.util.Date();
			Timestamp toDatePayment = new java.sql.Timestamp(poPaiddate.getTime());

			Purchase.setPurchaseorder_vendor_paymentdate(toDatePayment);
			Purchase.setPurchaseorder_vendor_approvalID((long) 0);
		}
		Purchase.setPurchaseorder_shipviaId(PurchaseOrders.getPurchaseorder_shipviaId());

		Purchase.setPurchaseorder_quotenumber(PurchaseOrders.getPurchaseorder_quotenumber());
		Purchase.setPurchaseorder_workordernumber(PurchaseOrders.getPurchaseorder_workordernumber());
		Purchase.setPurchaseorder_indentno(PurchaseOrders.getPurchaseorder_indentno());
		Purchase.setPurchaseorder_notes(PurchaseOrders.getPurchaseorder_notes());

		Purchase.setMarkForDelete(false);
		/** who Create this vehicle get email id to user profile details */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		Purchase.setCreatedById(userDetails.getId());
		Purchase.setLastModifiedById(userDetails.getId());
		Purchase.setCompanyId(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Purchase.setCreated(toDate);
		Purchase.setLastupdated(toDate);
		Purchase.setSubCompanyId(PurchaseOrders.getSubCompanyId());
		Purchase.setPurchaseorder_vendor_id(PurchaseOrders.getPurchaseorder_vendor_id());
		Purchase.setPurchaseorder_shiplocation_id(PurchaseOrders.getPurchaseorder_shiplocation_id());
		return Purchase;
	}

	// save PurchaseOrdersToParts_To InventoryAll
	public InventoryAll prepareInventoryAll(MasterPartsDto Master) {
		InventoryAll part = new InventoryAll();

		// part.setInventory_all_id(InventoryDto.getInventory_all_id());
		part.setPartid(Master.getPartid());
		// part.setPartnumber(Master.getPartnumber());
		// part.setPartname(Master.getPartname());
		// part.setPart_photoid(Master.getPart_photoid());
		// part.setCategory(Master.getCategory());
		// part.setParttype(Master.getParttype());
		// part.setPartTypeId(Master.getPartTypeId());
		// part.setAll_quantity(Master.getQuantity());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		part.setCreated(toDate);
		part.setLastupdated(toDate);

		part.setMarkForDelete(false);
		return part;
	}

	// save PurchaseOrdersToParts_To InventoryLocation Model
	public InventoryLocation prepareInventoryLocation(PurchaseOrdersDto purchaseOrder, MasterPartsDto Master)
			throws Exception {
		InventoryLocation part = new InventoryLocation();

		// part.setInventory_location_id(InventoryDto.getInventory_id());
		part.setPartid(Master.getPartid());
		// part.setPartnumber(Master.getPartnumber());
		// part.setPartname(Master.getPartname());
		// part.setPart_photoid(Master.getPart_photoid());
		// part.setCategory(Master.getCategory());
		// part.setParttype(Master.getParttype());
		// part.setPartTypeId(Master.getPartTypeId());
		part.setLocationId(purchaseOrder.getPurchaseorder_shiplocation_id());
		// ValidatePartLocations
		// part.setLocation_quantity(InventoryDto.getQuantity());

		return part;
	}
	
	public InventoryLocation prepareInventoryLocation(PurchaseOrders purchaseOrder, MasterPartsDto Master)
			throws Exception {
		InventoryLocation part = new InventoryLocation();

		part.setPartid(Master.getPartid());
		part.setLocationId(purchaseOrder.getPurchaseorder_shiplocation_id());

		return part;
	}

	// save the preparePurchaseOrdersToParts_To_Inventory
	public Inventory preparePurchaseOrdersToParts_To_Inventory(PurchaseOrdersToPartsDto purchaseOrdersToParts,
			PurchaseOrdersDto purchaseOrder, MasterPartsDto Master, Double eachUnitPrice, Double noOfQuantity) throws ParseException {
		Inventory part = new Inventory();

		// get search data to get part details
		part.setPartid(Master.getPartid());
		if (Master.getMake() != null) {
			part.setMake(Master.getMake());
		}

		Double InvenQuantity = 0.0;
		Double Inveneaccost = 0.0;
		Double Invendiscount = 0.0;
		Double Inventax = 0.0;

		if (purchaseOrdersToParts.getReceived_quantity() != null) {
			InvenQuantity = noOfQuantity;
		}
		if (purchaseOrdersToParts.getParteachcost() != null) {
			Inveneaccost = purchaseOrdersToParts.getParteachcost();
		}
		
		if (purchaseOrdersToParts.getDiscount() != null) {
			Invendiscount = purchaseOrdersToParts.getDiscount();
		}
		if (purchaseOrdersToParts.getTax() != null) {
			Inventax = purchaseOrdersToParts.getTax();
		}
		
		if(eachUnitPrice != null) {
			Inveneaccost = eachUnitPrice;
		}
		
		Double InvendiscountCost = 0.0;
		Double InvenTotalCost = 0.0;

		InvendiscountCost = (InvenQuantity * Inveneaccost) - ((InvenQuantity * Inveneaccost) * (Invendiscount / 100));
		InvenTotalCost = round(((InvendiscountCost) + (InvendiscountCost * (Inventax / 100))), 2);

		part.setQuantity(InvenQuantity);
		part.setHistory_quantity(InvenQuantity);

		part.setUnitprice(Inveneaccost);
		part.setDiscount(Invendiscount);
		part.setTax(Inventax);
		part.setTotal(round(InvenTotalCost, 2));

		part.setPurchaseorder_id(purchaseOrder.getPurchaseorder_id());

		part.setInvoice_number(purchaseOrder.getPurchaseorder_invoiceno());
		if (purchaseOrder.getPurchaseorder_invoice_date() != null) {
			java.util.Date date = dateFormat.parse(purchaseOrder.getPurchaseorder_invoice_date());
			java.sql.Date start_date = new java.sql.Date(date.getTime());
			part.setInvoice_date(start_date);
		}

		part.setInvoice_amount("" + purchaseOrder.getPurchaseorder_subtotal_cost());

		part.setVendor_id(purchaseOrder.getPurchaseorder_vendor_id());
		//part.setVendor_name(purchaseOrder.getPurchaseorder_vendor_name());
		//part.setVendor_location(purchaseOrder.getPurchaseorder_vendor_location());

		// part.setDescription(InventoryDto.getDescription());

		part.setMarkForDelete(false);

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		part.setCreated(toDate);
		part.setLastupdated(toDate);

		part.setMarkForDelete(false);
		// part.setParttype(Master.getParttype());
		// part.setPartTypeId(Master.getPartTypeId());

		return part;
	}
	
	// save the preparePurchaseOrdersToParts_To_Inventory
	public Inventory preparePurchaseOrdersToParts_To_Inventory(PurchaseOrdersToParts purchaseOrdersToParts,
			PurchaseOrders purchaseOrder, MasterPartsDto Master, Double eachUnitPrice, Double noOfQuantity) throws ParseException {
		Inventory part = new Inventory();

		// get search data to get part details
		part.setPartid(Master.getPartid());
		if (Master.getMake() != null) {
			part.setMake(Master.getMake());
		}

		Double InvenQuantity = 0.0;
		Double Inveneaccost = 0.0;
		Double Invendiscount = 0.0;
		Double Inventax = 0.0;

		if (purchaseOrdersToParts.getReceived_quantity() != null) {
			InvenQuantity = noOfQuantity;
		}
		if (purchaseOrdersToParts.getParteachcost() != null) {
			Inveneaccost = purchaseOrdersToParts.getParteachcost();
		}
		
		if (purchaseOrdersToParts.getDiscount() != null) {
			Invendiscount = purchaseOrdersToParts.getDiscount();
		}
		if (purchaseOrdersToParts.getTax() != null) {
			Inventax = purchaseOrdersToParts.getTax();
		}
		
		if(eachUnitPrice != null) {
			Inveneaccost = eachUnitPrice;
		}
		
		Double InvendiscountCost = 0.0;
		Double InvenTotalCost = 0.0;

		InvendiscountCost = (InvenQuantity * Inveneaccost) - ((InvenQuantity * Inveneaccost) * (Invendiscount / 100));
		InvenTotalCost = round(((InvendiscountCost) + (InvendiscountCost * (Inventax / 100))), 2);

		part.setQuantity(InvenQuantity);
		part.setHistory_quantity(InvenQuantity);

		part.setUnitprice(Inveneaccost);
		part.setDiscount(Invendiscount);
		part.setTax(Inventax);
		part.setTotal(round(InvenTotalCost, 2));

		part.setPurchaseorder_id(purchaseOrder.getPurchaseorder_id());

		part.setInvoice_number(purchaseOrder.getPurchaseorder_invoiceno());
		if (purchaseOrder.getPurchaseorder_invoice_date() != null) {
			part.setInvoice_date(purchaseOrder.getPurchaseorder_invoice_date());
		}

		part.setInvoice_amount("" + purchaseOrder.getPurchaseorder_subtotal_cost());

		part.setVendor_id(purchaseOrder.getPurchaseorder_vendor_id());
		//part.setVendor_name(purchaseOrder.getPurchaseorder_vendor_name());
		//part.setVendor_location(purchaseOrder.getPurchaseorder_vendor_location());

		// part.setDescription(InventoryDto.getDescription());

		part.setMarkForDelete(false);

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		part.setCreated(toDate);
		part.setLastupdated(toDate);

		part.setMarkForDelete(false);
		// part.setParttype(Master.getParttype());
		// part.setPartTypeId(Master.getPartTypeId());

		return part;
	}

	// get PurchaseOrders & PurchaseOrdersToParts To Save
	// PurchaseOrdersToDebitNote
	public PurchaseOrdersToDebitNote preparePurchaseOrdersToParts_To_PurchaseOrdersToDebitNote(
			PurchaseOrdersToPartsDto purchaseOrdersToParts) {

		PurchaseOrdersToDebitNote PurchaseDebitNode = new PurchaseOrdersToDebitNote();

		PurchaseDebitNode.setPartid(purchaseOrdersToParts.getPartid());

		//PurchaseDebitNode.setPurchaseorder_partname(purchaseOrdersToParts.getPurchaseorder_partname());
		//PurchaseDebitNode.setPurchaseorder_partnumber(purchaseOrdersToParts.getPurchaseorder_partnumber());

		Double NotReceved_Quantity = 0.0;
		Double eaccost = 0.0;
		Double discount = 0.0;
		Double tax = 0.0;
		// Get purchaseOrdersToParts to Notreceived_quantity to calculate
		// total_return_cost
		if (purchaseOrdersToParts.getNotreceived_quantity() != null) {
			NotReceved_Quantity = purchaseOrdersToParts.getNotreceived_quantity();
		}
		if (purchaseOrdersToParts.getParteachcost() != null) {
			eaccost = purchaseOrdersToParts.getParteachcost();
		}
		if (purchaseOrdersToParts.getDiscount() != null) {
			discount = purchaseOrdersToParts.getDiscount();
		}
		if (purchaseOrdersToParts.getTax() != null) {
			tax = purchaseOrdersToParts.getTax();
		}
		Double discountCost = 0.0;
		Double TotalReturn_Cost = 0.0;

		discountCost = (NotReceved_Quantity * eaccost) - ((NotReceved_Quantity * eaccost) * (discount / 100));
		TotalReturn_Cost = round(((discountCost) + (discountCost * (tax / 100))), 2);

		PurchaseDebitNode.setNotreceived_quantity(purchaseOrdersToParts.getNotreceived_quantity());
		PurchaseDebitNode.setParteachcost(purchaseOrdersToParts.getParteachcost());
		PurchaseDebitNode.setDiscount(purchaseOrdersToParts.getDiscount());
		PurchaseDebitNode.setTax(purchaseOrdersToParts.getTax());
		PurchaseDebitNode.setReceived_quantity_remark(purchaseOrdersToParts.getReceived_quantity_remark());

		PurchaseDebitNode.setTotal_return_cost(TotalReturn_Cost);

		PurchaseDebitNode.setPurchaseorder_id(purchaseOrdersToParts.getPurchaseorder_id());
		PurchaseDebitNode.setPurchaseorderto_partid(purchaseOrdersToParts.getPurchaseorderto_partid());

		return PurchaseDebitNode;
	}
	
	public PurchaseOrdersToDebitNote preparePurchaseOrdersToParts_To_PurchaseOrdersToDebitNote(
			PurchaseOrdersToParts purchaseOrdersToParts, double receiveQty) {

		PurchaseOrdersToDebitNote PurchaseDebitNode = new PurchaseOrdersToDebitNote();

		PurchaseDebitNode.setPartid(purchaseOrdersToParts.getPartid());

		//PurchaseDebitNode.setPurchaseorder_partname(purchaseOrdersToParts.getPurchaseorder_partname());
		//PurchaseDebitNode.setPurchaseorder_partnumber(purchaseOrdersToParts.getPurchaseorder_partnumber());

		Double NotReceved_Quantity = 0.0;
		Double eaccost = 0.0;
		Double discount = 0.0;
		Double tax = 0.0;
		// Get purchaseOrdersToParts to Notreceived_quantity to calculate
		// total_return_cost
		if (purchaseOrdersToParts.getNotreceived_quantity() != null) {
			NotReceved_Quantity = receiveQty;
		}
		if (purchaseOrdersToParts.getParteachcost() != null) {
			eaccost = purchaseOrdersToParts.getParteachcost();
		}
		if (purchaseOrdersToParts.getDiscount() != null) {
			discount = purchaseOrdersToParts.getDiscount();
		}
		if (purchaseOrdersToParts.getTax() != null) {
			tax = purchaseOrdersToParts.getTax();
		}
		Double discountCost = 0.0;
		Double TotalReturn_Cost = 0.0;

		discountCost = (NotReceved_Quantity * eaccost) - ((NotReceved_Quantity * eaccost) * (discount / 100));
		TotalReturn_Cost = round(((discountCost) + (discountCost * (tax / 100))), 2);

		PurchaseDebitNode.setNotreceived_quantity(receiveQty);
		PurchaseDebitNode.setParteachcost(purchaseOrdersToParts.getParteachcost());
		PurchaseDebitNode.setDiscount(purchaseOrdersToParts.getDiscount());
		PurchaseDebitNode.setTax(purchaseOrdersToParts.getTax());
		PurchaseDebitNode.setReceived_quantity_remark(purchaseOrdersToParts.getReceived_quantity_remark());

		PurchaseDebitNode.setTotal_return_cost(TotalReturn_Cost);

		PurchaseDebitNode.setPurchaseorder_id(purchaseOrdersToParts.getPurchaseorders().getPurchaseorder_id());
		PurchaseDebitNode.setPurchaseorderto_partid(purchaseOrdersToParts.getPurchaseorderto_partid());

		return PurchaseDebitNode;
	}

	// PurchaseOrdersToDebitNote
	public PurchaseOrdersToDebitNote preparePurchaseOrdersToParts_To_PurchaseOrdersToDebitNote_Tyre(
			PurchaseOrdersToPartsDto purchaseOrdersToParts) {

		PurchaseOrdersToDebitNote PurchaseDebitNode = new PurchaseOrdersToDebitNote();

		PurchaseDebitNode.setTYRE_MANUFACTURER_ID(purchaseOrdersToParts.getTYRE_MANUFACTURER_ID());
		PurchaseDebitNode.setTYRE_MODEL_ID(purchaseOrdersToParts.getTYRE_MODEL_ID());
		PurchaseDebitNode.setTYRE_SIZE_ID(purchaseOrdersToParts.getTYRE_SIZE_ID());
		PurchaseDebitNode.setBatteryManufacturerId(purchaseOrdersToParts.getBATTERY_MANUFACTURER_ID());
		PurchaseDebitNode.setBatteryTypeId(purchaseOrdersToParts.getBATTERY_TYPE_ID());
		PurchaseDebitNode.setBatteryCapacityId(purchaseOrdersToParts.getBATTERY_CAPACITY_ID());

		Double NotReceved_Quantity = 0.0;
		Double eaccost = 0.0;
		Double discount = 0.0;
		Double tax = 0.0;
		// Get purchaseOrdersToParts to Notreceived_quantity to calculate
		// total_return_cost
		if (purchaseOrdersToParts.getNotreceived_quantity() != null) {
			NotReceved_Quantity = purchaseOrdersToParts.getNotreceived_quantity();
		}
		if (purchaseOrdersToParts.getParteachcost() != null) {
			eaccost = purchaseOrdersToParts.getParteachcost();
		}
		if (purchaseOrdersToParts.getDiscount() != null) {
			discount = purchaseOrdersToParts.getDiscount();
		}
		if (purchaseOrdersToParts.getTax() != null) {
			tax = purchaseOrdersToParts.getTax();
		}
		Double discountCost = 0.0;
		Double TotalReturn_Cost = 0.0;

		discountCost = (NotReceved_Quantity * eaccost) - ((NotReceved_Quantity * eaccost) * (discount / 100));
		TotalReturn_Cost = round(((discountCost) + (discountCost * (tax / 100))), 2);

		PurchaseDebitNode.setNotreceived_quantity(purchaseOrdersToParts.getNotreceived_quantity());
		PurchaseDebitNode.setParteachcost(purchaseOrdersToParts.getParteachcost());
		PurchaseDebitNode.setDiscount(purchaseOrdersToParts.getDiscount());
		PurchaseDebitNode.setTax(purchaseOrdersToParts.getTax());
		PurchaseDebitNode.setReceived_quantity_remark(purchaseOrdersToParts.getReceived_quantity_remark());

		PurchaseDebitNode.setTotal_return_cost(TotalReturn_Cost);

		PurchaseDebitNode.setPurchaseorder_id(purchaseOrdersToParts.getPurchaseorder_id());
		PurchaseDebitNode.setPurchaseorderto_partid(purchaseOrdersToParts.getPurchaseorderto_partid());
		PurchaseDebitNode.setPurchaseorderto_partid(purchaseOrdersToParts.getPurchaseorderto_partid());

		return PurchaseDebitNode;
	}
	
	// PurchaseOrdersToDebitNote
	public PurchaseOrdersToDebitNote preparePurchaseOrdersToParts_To_PurchaseOrdersToDebitNote_Tyre(
			PurchaseOrdersToParts purchaseOrdersToParts, int receiveCount ) {

		PurchaseOrdersToDebitNote PurchaseDebitNode = new PurchaseOrdersToDebitNote();

		PurchaseDebitNode.setTYRE_MANUFACTURER_ID(purchaseOrdersToParts.getTYRE_MANUFACTURER_ID());
		PurchaseDebitNode.setTYRE_MODEL_ID(purchaseOrdersToParts.getTYRE_MODEL_ID());
		PurchaseDebitNode.setTYRE_SIZE_ID(purchaseOrdersToParts.getTYRE_SIZE_ID());
		PurchaseDebitNode.setBatteryManufacturerId(purchaseOrdersToParts.getBATTERY_MANUFACTURER_ID());
		PurchaseDebitNode.setBatteryTypeId(purchaseOrdersToParts.getBATTERY_TYPE_ID());
		PurchaseDebitNode.setBatteryCapacityId(purchaseOrdersToParts.getBATTERY_CAPACITY_ID());

		Double NotReceved_Quantity = 0.0;
		Double eaccost = 0.0;
		Double discount = 0.0;
		Double tax = 0.0;
		// Get purchaseOrdersToParts to Notreceived_quantity to calculate
		// total_return_cost
		if (purchaseOrdersToParts.getNotreceived_quantity() != null) {
			NotReceved_Quantity = purchaseOrdersToParts.getNotreceived_quantity() - receiveCount;
		}
		if (purchaseOrdersToParts.getParteachcost() != null) {
			eaccost = purchaseOrdersToParts.getParteachcost();
		}
		if (purchaseOrdersToParts.getDiscount() != null) {
			discount = purchaseOrdersToParts.getDiscount();
		}
		if (purchaseOrdersToParts.getTax() != null) {
			tax = purchaseOrdersToParts.getTax();
		}
		Double discountCost = 0.0;
		Double TotalReturn_Cost = 0.0;

		discountCost = (NotReceved_Quantity * eaccost) - ((NotReceved_Quantity * eaccost) * (discount / 100));
		TotalReturn_Cost = round(((discountCost) + (discountCost * (tax / 100))), 2);

		PurchaseDebitNode.setNotreceived_quantity(purchaseOrdersToParts.getNotreceived_quantity() - receiveCount);
		PurchaseDebitNode.setParteachcost(purchaseOrdersToParts.getParteachcost());
		PurchaseDebitNode.setDiscount(purchaseOrdersToParts.getDiscount());
		PurchaseDebitNode.setTax(purchaseOrdersToParts.getTax());
		PurchaseDebitNode.setReceived_quantity_remark(purchaseOrdersToParts.getReceived_quantity_remark());

		PurchaseDebitNode.setTotal_return_cost(TotalReturn_Cost);

		PurchaseDebitNode.setPurchaseorder_id(purchaseOrdersToParts.getPurchaseorders().getPurchaseorder_id());
		PurchaseDebitNode.setPurchaseorderto_partid(purchaseOrdersToParts.getPurchaseorderto_partid());

		return PurchaseDebitNode;
	}
	
	
	
	public PurchaseOrdersToDebitNote preparePurchaseOrdersToParts_To_PurchaseOrdersToDebitNote_Cloth(
			PurchaseOrdersToPartsDto purchaseOrdersToParts) {

		PurchaseOrdersToDebitNote PurchaseDebitNode = new PurchaseOrdersToDebitNote();

		PurchaseDebitNode.setClothTypesId(purchaseOrdersToParts.getClothTypesId());

		Double NotReceved_Quantity = 0.0;
		Double eaccost = 0.0;
		Double discount = 0.0;
		Double tax = 0.0;
		// Get purchaseOrdersToParts to Notreceived_quantity to calculate
		// total_return_cost
		if (purchaseOrdersToParts.getNotreceived_quantity() != null) {
			NotReceved_Quantity = purchaseOrdersToParts.getNotreceived_quantity();
		}
		if (purchaseOrdersToParts.getParteachcost() != null) {
			eaccost = purchaseOrdersToParts.getParteachcost();
		}
		if (purchaseOrdersToParts.getDiscount() != null) {
			discount = purchaseOrdersToParts.getDiscount();
		}
		if (purchaseOrdersToParts.getTax() != null) {
			tax = purchaseOrdersToParts.getTax();
		}
		Double discountCost = 0.0;
		Double TotalReturn_Cost = 0.0;

		discountCost = (NotReceved_Quantity * eaccost) - ((NotReceved_Quantity * eaccost) * (discount / 100));
		TotalReturn_Cost = round(((discountCost) + (discountCost * (tax / 100))), 2);

		PurchaseDebitNode.setNotreceived_quantity(purchaseOrdersToParts.getNotreceived_quantity());
		PurchaseDebitNode.setParteachcost(purchaseOrdersToParts.getParteachcost());
		PurchaseDebitNode.setDiscount(purchaseOrdersToParts.getDiscount());
		PurchaseDebitNode.setTax(purchaseOrdersToParts.getTax());
		PurchaseDebitNode.setReceived_quantity_remark(purchaseOrdersToParts.getReceived_quantity_remark());

		PurchaseDebitNode.setTotal_return_cost(TotalReturn_Cost);

		PurchaseDebitNode.setPurchaseorder_id(purchaseOrdersToParts.getPurchaseorder_id());
		PurchaseDebitNode.setPurchaseorderto_partid(purchaseOrdersToParts.getPurchaseorderto_partid());

		return PurchaseDebitNode;
	}
	
	// Save PurchaseOrder to PartTyreInvoice
		public PartInvoice prepare_PurchaseOrder_PartInvoice(PurchaseOrders Purchase, PurchaseOrdersDto purchaseOrderD) {
			
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			PartInvoice partInvoice			= new PartInvoice();
				if(purchaseOrderD.getPurchaseorder_id() != null) {
				partInvoice.setPurchaseorder_id(purchaseOrderD.getPurchaseorder_id());
				}
			
				partInvoice.setInvoiceNumber(purchaseOrderD.getPurchaseorder_invoiceno());
				
				Date currentDateUpdate = new Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
				partInvoice.setInvoiceDate(toDate);
		
				partInvoice.setInvoiceAmount(""+Purchase.getPurchaseorder_totalcost());
				partInvoice.setPaymentTypeId(PaymentTypeConstant.PAYMENT_TYPE_PO);
				
				if (Purchase.getPurchaseorder_notes() != null) {
				partInvoice.setDescription(Purchase.getPurchaseorder_notes());
				} else {
					partInvoice.setDescription("Excel Entry");
				}
				partInvoice.setCreatedById(userDetails.getId());
				partInvoice.setLastModifiedById(userDetails.getId());
				partInvoice.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
				partInvoice.setLastModifiedBy(DateTimeUtility.getCurrentTimeStamp());
				partInvoice.setCompanyId(userDetails.getCompany_id());
				
				if (Purchase.getPurchaseorder_vendor_id() != null) {
					partInvoice.setVendorId(Purchase.getPurchaseorder_vendor_id());
				} else {
					partInvoice.setVendorId(0);
				}
				
				partInvoice.setWareHouseLocation(Purchase.getPurchaseorder_shiplocation_id());
				
				final Long invoiceId = partInvoice.getPartInvoiceId();
				partInvoice.setPartInvoiceId(invoiceId);

			return partInvoice;
		}
		
		public PartInvoice prepare_PurchaseOrder_PartInvoice(PurchaseOrders purchase) {
			
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			PartInvoice partInvoice			= new PartInvoice();
				if(purchase.getPurchaseorder_id() != null) {
				partInvoice.setPurchaseorder_id(purchase.getPurchaseorder_id());
				}
			
				partInvoice.setInvoiceNumber(purchase.getPurchaseorder_invoiceno());
				
				Date currentDateUpdate = new Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
				partInvoice.setInvoiceDate(toDate);
		
				partInvoice.setInvoiceAmount(""+purchase.getPurchaseorder_totalcost());
				partInvoice.setPaymentTypeId(PaymentTypeConstant.PAYMENT_TYPE_PO);
				
				if (purchase.getPurchaseorder_notes() != null) {
				partInvoice.setDescription(purchase.getPurchaseorder_notes());
				} else {
					partInvoice.setDescription("Excel Entry");
				}
				partInvoice.setCreatedById(userDetails.getId());
				partInvoice.setLastModifiedById(userDetails.getId());
				partInvoice.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
				partInvoice.setLastModifiedBy(DateTimeUtility.getCurrentTimeStamp());
				partInvoice.setCompanyId(userDetails.getCompany_id());
				
				if (purchase.getPurchaseorder_vendor_id() != null) {
					partInvoice.setVendorId(purchase.getPurchaseorder_vendor_id());
				} else {
					partInvoice.setVendorId(0);
				}
				
				partInvoice.setWareHouseLocation(purchase.getPurchaseorder_shiplocation_id());
				
				final Long invoiceId = partInvoice.getPartInvoiceId();
				partInvoice.setPartInvoiceId(invoiceId);

			return partInvoice;
		}

	// Save PurchaseOrder to InventoryTyreInvoice
	public InventoryTyreInvoice prepare_PurchaseOrder_TyreInvoice(PurchaseOrders Purchase) {

		InventoryTyreInvoice Tyre = new InventoryTyreInvoice();

		Tyre.setWAREHOUSE_LOCATION_ID(Purchase.getPurchaseorder_shiplocation_id());

		Tyre.setPO_NUMBER("" + Purchase.getPurchaseorder_id());
		Tyre.setINVOICE_NUMBER(Purchase.getPurchaseorder_invoiceno());
		Tyre.setINVOICE_AMOUNT(Purchase.getPurchaseorder_totalcost());
		if (Purchase.getPurchaseorder_invoice_date() != null) {
			try {
				Tyre.setINVOICE_DATE(Purchase.getPurchaseorder_invoice_date());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Tyre.setVENDOR_ID(Purchase.getPurchaseorder_vendor_id());
		//Tyre.setVENDOR_NAME(Purchase.getPurchaseorder_vendor_name());
		//Tyre.setVENDOR_LOCATION(Purchase.getPurchaseorder_vendor_location());

		Tyre.setDESCRIPTION(Purchase.getPurchaseorder_notes());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		Tyre.setCREATED_DATE(toDate);
		Tyre.setLASTUPDATED_DATE(toDate);

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Tyre.setCREATEDBYID(userDetails.getId());
		Tyre.setLASTMODIFIEDBYID(userDetails.getId());
		Tyre.setCOMPANY_ID(userDetails.getCompany_id());

		Tyre.setMarkForDelete(false);
		
		Tyre.setPAYMENT_TYPE_ID(PaymentTypeConstant.PAYMENT_TYPE_PO);

		return Tyre;
	}
	
	public BatteryInvoice prepare_PurchaseOrder_BatteryInvoice(PurchaseOrders Purchase) {

		BatteryInvoice batteryInvoice = new BatteryInvoice();
		
		batteryInvoice.setWareHouseLocation(Purchase.getPurchaseorder_shiplocation_id());
		batteryInvoice.setPoNumber("" + Purchase.getPurchaseorder_Number());
		batteryInvoice.setPurchaseOrderId(Purchase.getPurchaseorder_id());
		batteryInvoice.setInvoiceNumber(Purchase.getPurchaseorder_invoiceno());
		batteryInvoice.setInvoiceAmount(Purchase.getPurchaseorder_totalcost());
		
		
		if (Purchase.getPurchaseorder_invoice_date() != null) {
			try {
				//Tyre.setINVOICE_DATE(Purchase.getPurchaseorder_invoice_date());
				batteryInvoice.setInvoiceDate(new Timestamp(Purchase.getPurchaseorder_invoice_date().getTime()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		batteryInvoice.setVendorId(Purchase.getPurchaseorder_vendor_id());
		batteryInvoice.setDescription(Purchase.getPurchaseorder_notes());

		batteryInvoice.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
		batteryInvoice.setLastModifiedBy(DateTimeUtility.getCurrentTimeStamp());
		

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		batteryInvoice.setCreatedById(userDetails.getId());
		batteryInvoice.setLastModifiedById(userDetails.getId());
		batteryInvoice.setCompanyId(userDetails.getCompany_id());

		batteryInvoice.setMarkForDelete(false);
		batteryInvoice.setPaymentTypeId(PaymentTypeConstant.PAYMENT_TYPE_CREDIT);
		

		return batteryInvoice;
	}
	
	public ClothInvoice prepare_PurchaseOrder_ClothInvoice(PurchaseOrders Purchase) {

		ClothInvoice batteryInvoice = new ClothInvoice();
		
		batteryInvoice.setWareHouseLocation(Purchase.getPurchaseorder_shiplocation_id());
		batteryInvoice.setPoNumber("" + Purchase.getPurchaseorder_Number());
		batteryInvoice.setPurchaseOrderId(Purchase.getPurchaseorder_id());
		batteryInvoice.setInvoiceNumber(Purchase.getPurchaseorder_invoiceno());
		batteryInvoice.setInvoiceAmount(Purchase.getPurchaseorder_totalcost());
		
		
		if (Purchase.getPurchaseorder_invoice_date() != null) {
			try {
				//Tyre.setINVOICE_DATE(Purchase.getPurchaseorder_invoice_date());
				batteryInvoice.setInvoiceDate(new Timestamp(Purchase.getPurchaseorder_invoice_date().getTime()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		batteryInvoice.setVendorId(Purchase.getPurchaseorder_vendor_id());
		batteryInvoice.setDescription(Purchase.getPurchaseorder_notes());

		batteryInvoice.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
		batteryInvoice.setLastModifiedBy(DateTimeUtility.getCurrentTimeStamp());
		

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		batteryInvoice.setCreatedById(userDetails.getId());
		batteryInvoice.setLastModifiedById(userDetails.getId());
		batteryInvoice.setCompanyId(userDetails.getCompany_id());

		batteryInvoice.setMarkForDelete(false);
		batteryInvoice.setPaymentTypeId(PaymentTypeConstant.PAYMENT_TYPE_PO);
		batteryInvoice.setClothTypeId(ClothInvoiceStockType.STOCK_TYPE_NEW);

		return batteryInvoice;
	}

	// Save PurchaseOrderPart to InventoryTyreInvoice To InventoryTyreAmount
	public InventoryTyreAmount prepare_PurchaseOrder_TyreAmount(PurchaseOrdersToPartsDto PurchaseToParts,
			PurchaseOrders Purchase) {

		InventoryTyreAmount TyreAmount = new InventoryTyreAmount();

		TyreAmount.setTYRE_MANUFACTURER_ID(PurchaseToParts.getTYRE_MANUFACTURER_ID());
		TyreAmount.setTYRE_MODEL_ID(PurchaseToParts.getTYRE_MODEL_ID());
		TyreAmount.setTYRE_SIZE_ID(PurchaseToParts.getTYRE_SIZE_ID());

		/* Here Received Quantity in Purchase Order */
		Double Quantity = PurchaseToParts.getReceived_quantity();
		Double eaccost = PurchaseToParts.getParteachcost();
		Double dis_Ca = PurchaseToParts.getDiscount();
		Double tax_Ca = PurchaseToParts.getTax();

		TyreAmount.setTYRE_QUANTITY(Quantity);

		/*
		 * Here Why Assign Zero meaning is Received Quantity in Purchase Order
		 */
		TyreAmount.setTYRE_ASSIGN_NO(0);

		TyreAmount.setUNIT_COST(eaccost);
		TyreAmount.setDISCOUNT(dis_Ca);
		TyreAmount.setTAX(tax_Ca);

		Double discountCost = 0.0;
		Double TotalCost = 0.0;

		
		discountCost = (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
		TotalCost = round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
		TyreAmount.setTOTAL_AMOUNT(TotalCost);

		TyreAmount.setWAREHOUSE_LOCATION_ID(Purchase.getPurchaseorder_shiplocation_id());

		return TyreAmount;
	}
	
	public BatteryAmount prepare_PurchaseOrder_BatteryAmount(PurchaseOrdersToPartsDto PurchaseToParts,
			PurchaseOrders Purchase) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		BatteryAmount	batteryAmount = new BatteryAmount();
		
		batteryAmount.setBatteryManufacturerId(PurchaseToParts.getBATTERY_MANUFACTURER_ID());
		batteryAmount.setBatteryTypeId(PurchaseToParts.getBATTERY_TYPE_ID());
		batteryAmount.setBatteryCapacityId(PurchaseToParts.getBATTERY_CAPACITY_ID());


		/* Here Received Quantity in Purchase Order */
		Double Quantity = PurchaseToParts.getReceived_quantity();
		Double eaccost = PurchaseToParts.getParteachcost();
		Double dis_Ca = PurchaseToParts.getDiscount();
		Double tax_Ca = PurchaseToParts.getTax();

		batteryAmount.setBatteryQuantity((new Double(Quantity)).longValue());

		/*
		 * Here Why Assign Zero meaning is Received Quantity in Purchase Order
		 */
		batteryAmount.setBatteryAsignNumber(0);
		batteryAmount.setUnitCost(eaccost);
		batteryAmount.setDiscount(dis_Ca);
		batteryAmount.setTax(tax_Ca);


		Double discountCost = 0.0;
		Double TotalCost = 0.0;

		
		discountCost = (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
		TotalCost = round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
		
		batteryAmount.setWareHouseLocation(Purchase.getPurchaseorder_shiplocation_id());
		batteryAmount.setTotalAmount(TotalCost);
		batteryAmount.setCompanyId(userDetails.getCompany_id());

		return batteryAmount;
	}
	
	public ClothInventoryDetails getClothInventoryDetails(PurchaseOrdersToPartsDto PurchaseToParts,
			PurchaseOrders Purchase) throws Exception{
		ClothInventoryDetails					batteryAmount				= null;
		CustomUserDetails						userDetails					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			batteryAmount	= new ClothInventoryDetails();
			
			Double Quantity = PurchaseToParts.getReceived_quantity();
			Double eaccost = PurchaseToParts.getParteachcost();
			Double dis_Ca = PurchaseToParts.getDiscount();
			Double tax_Ca = PurchaseToParts.getTax();
			
			Double discountCost = 0.0;
			Double TotalCost = 0.0;

			
			discountCost = (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
			TotalCost = round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
			
			
			batteryAmount.setUnitprice(PurchaseToParts.getParteachcost());
			batteryAmount.setDiscount(PurchaseToParts.getDiscount());
			batteryAmount.setTax(PurchaseToParts.getTax());
			batteryAmount.setTotal(TotalCost);
			batteryAmount.setWareHouseLocation(Purchase.getPurchaseorder_shiplocation_id());
			batteryAmount.setCompanyId(userDetails.getCompany_id());
			batteryAmount.setClothTypesId(PurchaseToParts.getClothTypesId());
			batteryAmount.setQuantity(PurchaseToParts.getReceived_quantity());
			batteryAmount.setVendor_id(Purchase.getPurchaseorder_vendor_id());
			batteryAmount.setCreatedById(userDetails.getId());
			batteryAmount.setLastModifiedById(userDetails.getId());
			batteryAmount.setCreated(new Date());
			batteryAmount.setLastupdated(new Date());
			
			return batteryAmount;
			
		} catch (Exception e) {
			throw e;
		}finally {
			batteryAmount		= null;
			userDetails			= null;
		}
	}

	// Save PurchaseOrderToTyre to InventoryTyre
	public InventoryTyre prepare_TyreAmount_TO_Tyre(InventoryTyreAmount TyreAmount) {

		InventoryTyre Tyre = new InventoryTyre();

		Tyre.setITYRE_AMOUNT_ID(TyreAmount.getITYRE_AMD_ID());
		Tyre.setITYRE_INVOICE_ID(TyreAmount.getInventoryTyreInvoice().getITYRE_ID());

		if (TyreAmount != null) {

			Double Tyreamount = 0.0;

			if (TyreAmount.getTYRE_QUANTITY() != 0.0 && TyreAmount.getTYRE_QUANTITY() != null) {
				Tyreamount = TyreAmount.getTOTAL_AMOUNT() / TyreAmount.getTYRE_QUANTITY();
			}
			Tyre.setTYRE_AMOUNT(round(Tyreamount, 0));
			Tyre.setTYRE_MANUFACTURER_ID(TyreAmount.getTYRE_MANUFACTURER_ID());
			Tyre.setTYRE_MODEL_ID(TyreAmount.getTYRE_MODEL_ID());
			Tyre.setTYRE_SIZE_ID(TyreAmount.getTYRE_SIZE_ID());
			//Tyre.setTYRE_SIZE(TyreAmount.getTYRE_SIZE());
			Tyre.setTYRE_TREAD(TyreAmount.getTYRE_TREAD());
			Tyre.setWAREHOUSE_LOCATION_ID(TyreAmount.getWAREHOUSE_LOCATION_ID());

			Tyre.setTYRE_RETREAD_COUNT(0);
			Tyre.setTYRE_USEAGE(0);

			Date currentDateUpdate = new Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			Tyre.setCREATED_DATE(toDate);
			Tyre.setLASTUPDATED_DATE(toDate);
			/* Tyre.setTYRE_ASSIGN_DATE(toDate); */
			Tyre.setTYRE_PURCHASE_DATE(toDate);

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Tyre.setCREATEDBYID(userDetails.getId());
			Tyre.setLASTMODIFIEDBYID(userDetails.getId());
			Tyre.setCOMPANY_ID(userDetails.getCompany_id());

			Tyre.setMarkForDelete(false);
		}
		return Tyre;
	}

	public Battery prepare_BatteryAmount_TO_Battery(BatteryAmount TyreAmount) {

		Battery battery = new Battery();
		
		battery.setBatteryAmountId(TyreAmount.getBatteryAmountId());
		battery.setBatteryInvoiceId(TyreAmount.getBatteryInvoiceId());


		if (TyreAmount != null) {

			Double Tyreamount = 0.0;

			if (TyreAmount.getBatteryQuantity() != null && TyreAmount.getBatteryQuantity() != 0.0) {
				Tyreamount = TyreAmount.getTotalAmount() / TyreAmount.getBatteryQuantity();
			}
			battery.setBatteryAmount(round(Tyreamount, 0));
			battery.setBatteryManufacturerId(TyreAmount.getBatteryManufacturerId());
			battery.setBatteryTypeId(TyreAmount.getBatteryTypeId());
			battery.setBatteryCapacityId(TyreAmount.getBatteryCapacityId());
			battery.setWareHouseLocationId(TyreAmount.getWareHouseLocation());
			battery.setBatteryUsesOdometer(0);
			battery.setUsesNoOfTime((long) 0);
			battery.setCreatedOn(DateTimeUtility.getCurrentTimeStamp());
			battery.setLastModifiedOn(DateTimeUtility.getCurrentTimeStamp());
			battery.setBatteryPurchaseDate(DateTimeUtility.getCurrentTimeStamp());
			


			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			
			battery.setCreatedById(userDetails.getId());
			battery.setLastModifiedById(userDetails.getId());
			battery.setCompanyId(userDetails.getCompany_id());

		}
		return battery;
	}
	
	/* list of PurchaseOrders get and Display to open PurchaseOrders 
	public List<PurchaseOrdersDto> prepareListofPurchaseOrders(List<PurchaseOrders> PurchaseOrdersList) {
		List<PurchaseOrdersDto> Dtos = null;
		if (PurchaseOrdersList != null && !PurchaseOrdersList.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersDto>();
			PurchaseOrdersDto Purchase = null;
			for (PurchaseOrders PurchaseOrders : PurchaseOrdersList) {
				Purchase = new PurchaseOrdersDto();

				Purchase.setPurchaseorder_id(PurchaseOrders.getPurchaseorder_id());
				Purchase.setPurchaseorder_Number(PurchaseOrders.getPurchaseorder_Number());
				Purchase.setPurchaseorder_vendor_id(PurchaseOrders.getPurchaseorder_vendor_id());
				Purchase.setPurchaseorder_vendor_name(PurchaseOrders.getPurchaseorder_vendor_name());
				Purchase.setPurchaseorder_vendor_location(PurchaseOrders.getPurchaseorder_vendor_location());
				if (PurchaseOrders.getPurchaseorder_created_on() != null) {
					Purchase.setPurchaseorder_created_on(
							dateFormat_name.format(PurchaseOrders.getPurchaseorder_created_on()));
				}
				if (PurchaseOrders.getPurchaseorder_created_on() != null) {
					Purchase.setPurchaseorder_requied_on(
							dateFormat_name.format(PurchaseOrders.getPurchaseorder_requied_on()));
				}
				Purchase.setPurchaseorder_buyer(PurchaseOrders.getPurchaseorder_buyer());
				Purchase.setPurchaseorder_buyeraddress(PurchaseOrders.getPurchaseorder_buyeraddress());
				Purchase.setPurchaseorder_terms(PurchaseOrders.getPurchaseorder_terms());
				Purchase.setPurchaseorder_quotenumber(PurchaseOrders.getPurchaseorder_quotenumber());
				Purchase.setPurchaseorder_indentno(PurchaseOrders.getPurchaseorder_indentno());
				Purchase.setPurchaseorder_workordernumber(PurchaseOrders.getPurchaseorder_workordernumber());

				Purchase.setPurchaseorder_shiplocation(PurchaseOrders.getPurchaseorder_shiplocation());
				Purchase.setPurchaseorder_status(PurchaseOrders.getPurchaseorder_status());

				Purchase.setPurchaseorder_document(PurchaseOrders.isPurchaseorder_document());
				Purchase.setPurchaseorder_document_id(PurchaseOrders.getPurchaseorder_document_id());

				Dtos.add(Purchase);
			}
		}
		return Dtos;
	}
*/
	
	public List<PurchaseOrdersDto> getListofPurchaseOrders(List<PurchaseOrdersDto> PurchaseOrdersList) {
		List<PurchaseOrdersDto> Dtos = null;
		if (PurchaseOrdersList != null && !PurchaseOrdersList.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersDto>();
			PurchaseOrdersDto Purchase = null;
			for (PurchaseOrdersDto PurchaseOrders : PurchaseOrdersList) {
				Purchase = new PurchaseOrdersDto();

				Purchase.setPurchaseorder_id(PurchaseOrders.getPurchaseorder_id());
				Purchase.setPurchaseorder_Number(PurchaseOrders.getPurchaseorder_Number());
				Purchase.setPurchaseorder_vendor_id(PurchaseOrders.getPurchaseorder_vendor_id());
				Purchase.setPurchaseorder_vendor_name(PurchaseOrders.getPurchaseorder_vendor_name());
				Purchase.setPurchaseorder_vendor_location(PurchaseOrders.getPurchaseorder_vendor_location());
				if (PurchaseOrders.getPurchaseorder_created() != null) {
					Purchase.setPurchaseorder_created_on(
							dateFormat_name.format(PurchaseOrders.getPurchaseorder_created()));
				}
				if (PurchaseOrders.getPurchaseorder_requied() != null) {
					Purchase.setPurchaseorder_requied_on(
							dateFormat_name.format(PurchaseOrders.getPurchaseorder_requied()));
				}
				Purchase.setPurchaseorder_buyer(PurchaseOrders.getPurchaseorder_buyer());
				Purchase.setPurchaseorder_buyeraddress(PurchaseOrders.getPurchaseorder_buyeraddress());
				Purchase.setPurchaseorder_termsId(PurchaseOrders.getPurchaseorder_termsId());
				Purchase.setPurchaseorder_terms(PaymentTypeConstant.getPaymentTypeName(PurchaseOrders.getPurchaseorder_termsId()));
				Purchase.setPurchaseorder_quotenumber(PurchaseOrders.getPurchaseorder_quotenumber());
				Purchase.setPurchaseorder_indentno(PurchaseOrders.getPurchaseorder_indentno());
				Purchase.setPurchaseorder_workordernumber(PurchaseOrders.getPurchaseorder_workordernumber());

				Purchase.setPurchaseorder_shiplocation(PurchaseOrders.getPurchaseorder_shiplocation());
				Purchase.setPurchaseorder_statusId(PurchaseOrders.getPurchaseorder_statusId());
				Purchase.setPurchaseorder_status(PurchaseOrderType.getPurchaseStatusName(PurchaseOrders.getPurchaseorder_statusId()));

				Purchase.setPurchaseorder_document(PurchaseOrders.isPurchaseorder_document());
				Purchase.setPurchaseorder_document_id(PurchaseOrders.getPurchaseorder_document_id());
				Purchase.setPurchaseorder_invoiceno(PurchaseOrders.getPurchaseorder_invoiceno());

				Dtos.add(Purchase);
			}
		}
		return Dtos;
	}

	/* list of PurchaseOrders get and Display to open PurchaseOrders 
	public List<PurchaseOrdersDto> prepareReport_ListofPurchaseOrders(List<PurchaseOrders> PurchaseOrdersList) {
		List<PurchaseOrdersDto> Dtos = null;
		if (PurchaseOrdersList != null && !PurchaseOrdersList.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersDto>();
			PurchaseOrdersDto Purchase = null;
			for (PurchaseOrders PurchaseOrders : PurchaseOrdersList) {
				Purchase = new PurchaseOrdersDto();

				Purchase.setPurchaseorder_id(PurchaseOrders.getPurchaseorder_id());
				Purchase.setPurchaseorder_Number(PurchaseOrders.getPurchaseorder_Number());
				Purchase.setPurchaseorder_vendor_id(PurchaseOrders.getPurchaseorder_vendor_id());
				Purchase.setPurchaseorder_vendor_name(PurchaseOrders.getPurchaseorder_vendor_name());
				Purchase.setPurchaseorder_vendor_location(PurchaseOrders.getPurchaseorder_vendor_location());
				if (PurchaseOrders.getPurchaseorder_created_on() != null) {
					Purchase.setPurchaseorder_created_on(
							dateFormat_name.format(PurchaseOrders.getPurchaseorder_created_on()));
				}
				if (PurchaseOrders.getPurchaseorder_created_on() != null) {
					Purchase.setPurchaseorder_requied_on(
							dateFormat_name.format(PurchaseOrders.getPurchaseorder_requied_on()));
				}
				Purchase.setPurchaseorder_buyer(PurchaseOrders.getPurchaseorder_buyer());
				Purchase.setPurchaseorder_buyeraddress(PurchaseOrders.getPurchaseorder_buyeraddress());
				Purchase.setPurchaseorder_terms(PurchaseOrders.getPurchaseorder_terms());
				Purchase.setPurchaseorder_quotenumber(PurchaseOrders.getPurchaseorder_quotenumber());
				Purchase.setPurchaseorder_workordernumber(PurchaseOrders.getPurchaseorder_workordernumber());

				Purchase.setPurchaseorder_shiplocation(PurchaseOrders.getPurchaseorder_shiplocation());
				Purchase.setPurchaseorder_status(PurchaseOrders.getPurchaseorder_status());

				Purchase.setPurchaseorder_totalcost(PurchaseOrders.getPurchaseorder_totalcost());
				Purchase.setPurchaseorder_balancecost(PurchaseOrders.getPurchaseorder_balancecost());

				Dtos.add(Purchase);
			}
		}
		return Dtos;
	}
*/	
	/* list of PurchaseOrders get and Display to open PurchaseOrders */
	public List<PurchaseOrdersDto> getReport_ListofPurchaseOrders(List<PurchaseOrdersDto> PurchaseOrdersList) {
		List<PurchaseOrdersDto> Dtos = null;
		if (PurchaseOrdersList != null && !PurchaseOrdersList.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersDto>();
			PurchaseOrdersDto Purchase = null;
			for (PurchaseOrdersDto PurchaseOrders : PurchaseOrdersList) {
				Purchase = new PurchaseOrdersDto();

				Purchase.setPurchaseorder_id(PurchaseOrders.getPurchaseorder_id());
				Purchase.setPurchaseorder_Number(PurchaseOrders.getPurchaseorder_Number());
				Purchase.setPurchaseorder_vendor_id(PurchaseOrders.getPurchaseorder_vendor_id());
				Purchase.setPurchaseorder_vendor_name(PurchaseOrders.getPurchaseorder_vendor_name());
				Purchase.setPurchaseorder_vendor_location(PurchaseOrders.getPurchaseorder_vendor_location());
				if (PurchaseOrders.getPurchaseorder_created() != null) {
					Purchase.setPurchaseorder_created_on(
							dateFormat_name.format(PurchaseOrders.getPurchaseorder_created()));
				}
				if (PurchaseOrders.getPurchaseorder_requied() != null) {
					Purchase.setPurchaseorder_requied_on(
							dateFormat_name.format(PurchaseOrders.getPurchaseorder_requied()));
				}
				Purchase.setPurchaseorder_buyer(PurchaseOrders.getPurchaseorder_buyer());
				Purchase.setPurchaseorder_buyeraddress(PurchaseOrders.getPurchaseorder_buyeraddress());
				Purchase.setPurchaseorder_termsId(PurchaseOrders.getPurchaseorder_termsId());
				Purchase.setPurchaseorder_terms(PaymentTypeConstant.getPaymentTypeName(PurchaseOrders.getPurchaseorder_termsId()));
				Purchase.setPurchaseorder_quotenumber(PurchaseOrders.getPurchaseorder_quotenumber());
				Purchase.setPurchaseorder_workordernumber(PurchaseOrders.getPurchaseorder_workordernumber());

				Purchase.setPurchaseorder_shiplocation(PurchaseOrders.getPurchaseorder_shiplocation());
				Purchase.setPurchaseorder_statusId(PurchaseOrders.getPurchaseorder_statusId());
				Purchase.setPurchaseorder_status(PurchaseOrderType.getPurchaseStatusName(PurchaseOrders.getPurchaseorder_statusId()));

				Purchase.setPurchaseorder_totalcost(PurchaseOrders.getPurchaseorder_totalcost());
				Purchase.setPurchaseorder_balancecost(PurchaseOrders.getPurchaseorder_balancecost());
				Purchase.setPurchaseorder_invoiceno(PurchaseOrders.getPurchaseorder_invoiceno());

				Dtos.add(Purchase);
			}
		}
		return Dtos;
	}

	public List<PurchaseOrdersDto> Vendor_Approal_ListofPurchaseOrders(List<PurchaseOrdersDto> PurchaseOrdersList) {
		List<PurchaseOrdersDto> Dtos = null;
		if (PurchaseOrdersList != null && !PurchaseOrdersList.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersDto>();
			PurchaseOrdersDto Purchase = null;
			for (PurchaseOrdersDto PurchaseOrders : PurchaseOrdersList) {
				Purchase = new PurchaseOrdersDto();

				Purchase.setPurchaseorder_id(PurchaseOrders.getPurchaseorder_id());
				Purchase.setPurchaseorder_Number(PurchaseOrders.getPurchaseorder_Number());
				Purchase.setPurchaseorder_vendor_id(PurchaseOrders.getPurchaseorder_vendor_id());
				Purchase.setPurchaseorder_vendor_name(PurchaseOrders.getPurchaseorder_vendor_name());
				if (PurchaseOrders.getPurchaseorder_invoice_date_On() != null) {
					Purchase.setPurchaseorder_invoice_date(
							dateFormat_name.format(PurchaseOrders.getPurchaseorder_invoice_date_On()));
				}
				Purchase.setPurchaseorder_invoiceno(PurchaseOrders.getPurchaseorder_invoiceno());
				Purchase.setPurchaseorder_termsId(PurchaseOrders.getPurchaseorder_termsId());
				Purchase.setPurchaseorder_terms(PaymentTypeConstant.getPaymentTypeName(PurchaseOrders.getPurchaseorder_termsId()));
				Purchase.setPurchaseorder_vendor_paymodeId(PurchaseOrders.getPurchaseorder_vendor_paymodeId());
				Purchase.setPurchaseorder_vendor_paymode(InventoryTyreInvoiceDto.getPaymentModeName(PurchaseOrders.getPurchaseorder_vendor_paymodeId()));
				Purchase.setPurchaseorder_statusId(PurchaseOrders.getPurchaseorder_statusId());
				Purchase.setPurchaseorder_status(PurchaseOrderType.getPurchaseStatusName(PurchaseOrders.getPurchaseorder_statusId()));
				if(PurchaseOrders.getPurchaseorder_totalcost() != null)
				Purchase.setPurchaseorder_totalcost(Double.parseDouble(df2.format(PurchaseOrders.getPurchaseorder_totalcost())));
				if(PurchaseOrders.getPurchaseorder_balancecost() != null)
				Purchase.setPurchaseorder_balancecost(Double.parseDouble(df2.format(PurchaseOrders.getPurchaseorder_balancecost())));
				if(PurchaseOrders.getPaidAmount() > 0)
				Purchase.setPaidAmount(Double.parseDouble(df2.format(PurchaseOrders.getPaidAmount())));
				if(PurchaseOrders.getBalanceAmount() > 0)
				Purchase.setBalanceAmount(Double.parseDouble(df2.format(PurchaseOrders.getBalanceAmount())));
				Purchase.setPurchaseorder_document_id(PurchaseOrders.getPurchaseorder_document_id());
				Dtos.add(Purchase);
			}
		}
		return Dtos;
	}

	/*// get PurchaseOrders information in database
	public PurchaseOrdersDto getPurchaseOrders(PurchaseOrders PurchaseOrders) throws Exception {

		PurchaseOrdersDto Purchase = new PurchaseOrdersDto();

		Purchase.setPurchaseorder_id(PurchaseOrders.getPurchaseorder_id());
		Purchase.setPurchaseorder_Number(PurchaseOrders.getPurchaseorder_Number());

		Purchase.setPurchaseorder_type(PurchaseOrders.getPurchaseorder_type());

		Purchase.setPurchaseorder_vendor_id(PurchaseOrders.getPurchaseorder_vendor_id());
		Purchase.setPurchaseorder_vendor_name(PurchaseOrders.getPurchaseorder_vendor_name());
		Purchase.setPurchaseorder_vendor_location(PurchaseOrders.getPurchaseorder_vendor_location());
		if (PurchaseOrders.getPurchaseorder_created_on() != null) {
			Purchase.setPurchaseorder_created_on(dateFormat.format(PurchaseOrders.getPurchaseorder_created_on()));
		}
		if (PurchaseOrders.getPurchaseorder_created_on() != null) {
			Purchase.setPurchaseorder_requied_on(dateFormat.format(PurchaseOrders.getPurchaseorder_requied_on()));
		}
		Purchase.setPurchaseorder_buyer(PurchaseOrders.getPurchaseorder_buyer());
		Purchase.setPurchaseorder_buyeraddress(PurchaseOrders.getPurchaseorder_buyeraddress());
		Purchase.setPurchaseorder_terms(PurchaseOrders.getPurchaseorder_terms());
		Purchase.setPurchaseorder_shipvia(PurchaseOrders.getPurchaseorder_shipvia());
		Purchase.setPurchaseorder_shiplocation_id(PurchaseOrders.getPurchaseorder_shiplocation_id());

		Purchase.setPurchaseorder_shiplocation(PurchaseOrders.getPurchaseorder_shiplocation());
		Purchase.setPurchaseorder_shiplocation_address(PurchaseOrders.getPurchaseorder_shiplocation_address());
		Purchase.setPurchaseorder_shiplocation_contact(PurchaseOrders.getPurchaseorder_shiplocation_contact());
		Purchase.setPurchaseorder_shiplocation_mobile(PurchaseOrders.getPurchaseorder_shiplocation_mobile());
		Purchase.setPurchaseorder_quotenumber(PurchaseOrders.getPurchaseorder_quotenumber());
		Purchase.setPurchaseorder_workordernumber(PurchaseOrders.getPurchaseorder_workordernumber());

		Purchase.setPurchaseorder_indentno(PurchaseOrders.getPurchaseorder_indentno());

		Purchase.setPurchaseorder_notes(PurchaseOrders.getPurchaseorder_notes());

		Purchase.setPurchaseorder_status(PurchaseOrders.getPurchaseorder_status());
		Purchase.setPurchaseorder_subtotal_cost(PurchaseOrders.getPurchaseorder_subtotal_cost());
		Purchase.setPurchaseorder_totaltax_cost(PurchaseOrders.getPurchaseorder_totaltax_cost());

		Purchase.setPurchaseorder_freight(PurchaseOrders.getPurchaseorder_freight());
		Purchase.setPurchaseorder_totalcost(PurchaseOrders.getPurchaseorder_totalcost());

		Purchase.setPurchaseorder_advancecost(PurchaseOrders.getPurchaseorder_advancecost());
		Purchase.setPurchaseorder_balancecost(PurchaseOrders.getPurchaseorder_balancecost());

		Purchase.setPurchaseorder_orderd_remark(PurchaseOrders.getPurchaseorder_orderd_remark());
		Purchase.setPurchaseorder_orderdby(PurchaseOrders.getPurchaseorder_orderdby());
		Purchase.setPurchaseorder_orderddate(PurchaseOrders.getPurchaseorder_orderddate());

		Purchase.setPurchaseorder_received_remark(PurchaseOrders.getPurchaseorder_received_remark());
		Purchase.setPurchaseorder_receivedby(PurchaseOrders.getPurchaseorder_receivedby());
		Purchase.setPurchaseorder_received_date(PurchaseOrders.getPurchaseorder_received_date());

		Purchase.setPurchaseorder_invoiceno(PurchaseOrders.getPurchaseorder_invoiceno());
		if (PurchaseOrders.getPurchaseorder_invoice_date() != null) {
			Purchase.setPurchaseorder_invoice_date(dateFormat.format(PurchaseOrders.getPurchaseorder_invoice_date()));
		}

		Purchase.setPurchaseorder_total_debitnote_cost(PurchaseOrders.getPurchaseorder_total_debitnote_cost());

		if (PurchaseOrders.getPurchaseorder_complete_date() != null) {
			Purchase.setPurchaseorder_complete_date(PurchaseOrders.getPurchaseorder_complete_date());
		}

		Purchase.setPurchaseorder_document(PurchaseOrders.isPurchaseorder_document());
		Purchase.setPurchaseorder_document_id(PurchaseOrders.getPurchaseorder_document_id());

		// Create and Last updated Display
		Purchase.setCreatedBy(PurchaseOrders.getCreatedBy());
		if (PurchaseOrders.getCreated() != null) {
			Purchase.setCreated(CreatedDateTime.format(PurchaseOrders.getCreated()));
		}
		Purchase.setLastModifiedBy(PurchaseOrders.getLastModifiedBy());
		if (PurchaseOrders.getLastupdated() != null) {
			Purchase.setLastupdated(CreatedDateTime.format(PurchaseOrders.getLastupdated()));
		}

		return Purchase;
	}
*/	
	
	public PurchaseOrdersDto getPurchaseOrders(PurchaseOrdersDto PurchaseOrders, HashMap<Long, User> userMap) throws Exception {
			
		PurchaseOrdersDto Purchase = new PurchaseOrdersDto();

		Purchase.setPurchaseorder_id(PurchaseOrders.getPurchaseorder_id());
		Purchase.setPurchaseorder_Number(PurchaseOrders.getPurchaseorder_Number());

		Purchase.setPurchaseorder_typeId(PurchaseOrders.getPurchaseorder_typeId());
		Purchase.setPurchaseorder_type(PurchaseOrderType.getPurchaseorderTypeName(PurchaseOrders.getPurchaseorder_typeId()));

		Purchase.setPurchaseorder_vendor_id(PurchaseOrders.getPurchaseorder_vendor_id());
		Purchase.setPurchaseorder_vendor_name(PurchaseOrders.getPurchaseorder_vendor_name());
		Purchase.setPurchaseorder_vendor_location(PurchaseOrders.getPurchaseorder_vendor_location());
		Purchase.setVendorAddress1(PurchaseOrders.getVendorAddress1());
		Purchase.setContactPerson(PurchaseOrders.getContactPerson());
		Purchase.setVendorFirPhone(PurchaseOrders.getVendorFirPhone());
		
		if (PurchaseOrders.getPurchaseorder_created() != null) {
			Purchase.setPurchaseorder_created_on(dateFormat.format(PurchaseOrders.getPurchaseorder_created()));
		}
		if (PurchaseOrders.getPurchaseorder_created() != null) {
			Purchase.setPurchaseorder_requied_on(dateFormat.format(PurchaseOrders.getPurchaseorder_requied()));
		}
		Purchase.setPurchaseorder_buyer(PurchaseOrders.getPurchaseorder_buyer());
		Purchase.setPurchaseorder_buyeraddress(PurchaseOrders.getPurchaseorder_buyeraddress());
		Purchase.setPurchaseorder_termsId(PurchaseOrders.getPurchaseorder_termsId());
		Purchase.setPurchaseorder_terms(PaymentTypeConstant.getPaymentTypeName(PurchaseOrders.getPurchaseorder_termsId()));
		Purchase.setPurchaseorder_shipviaId(PurchaseOrders.getPurchaseorder_shipviaId());
		Purchase.setPurchaseorder_shipvia(PurchaseOrderType.getTransferViaName(PurchaseOrders.getPurchaseorder_shipviaId()));
		Purchase.setPurchaseorder_shiplocation_id(PurchaseOrders.getPurchaseorder_shiplocation_id());

		Purchase.setPurchaseorder_shiplocation(PurchaseOrders.getPurchaseorder_shiplocation());
		Purchase.setPurchaseorder_shiplocation_address(PurchaseOrders.getPurchaseorder_shiplocation_address());
		Purchase.setPurchaseorder_shiplocation_contact(PurchaseOrders.getPurchaseorder_shiplocation_contact());
		Purchase.setPurchaseorder_shiplocation_mobile(PurchaseOrders.getPurchaseorder_shiplocation_mobile());
		Purchase.setPurchaseorder_quotenumber(PurchaseOrders.getPurchaseorder_quotenumber());
		Purchase.setPurchaseorder_workordernumber(PurchaseOrders.getPurchaseorder_workordernumber());

		Purchase.setPurchaseorder_indentno(PurchaseOrders.getPurchaseorder_indentno());

		Purchase.setPurchaseorder_notes(PurchaseOrders.getPurchaseorder_notes());

		Purchase.setPurchaseorder_statusId(PurchaseOrders.getPurchaseorder_statusId());
		Purchase.setPurchaseorder_status(PurchaseOrderType.getPurchaseStatusName(PurchaseOrders.getPurchaseorder_statusId()));
		if(PurchaseOrders.getPurchaseorder_subtotal_cost() != null)
		Purchase.setPurchaseorder_subtotal_cost(Double.parseDouble(df2.format(PurchaseOrders.getPurchaseorder_subtotal_cost())));
		if(PurchaseOrders.getPurchaseorder_totaltax_cost() != null)
		Purchase.setPurchaseorder_totaltax_cost(Double.parseDouble(df2.format(PurchaseOrders.getPurchaseorder_totaltax_cost())));
		if(PurchaseOrders.getPurchaseorder_freight() != null)
		Purchase.setPurchaseorder_freight(Double.parseDouble(df2.format(PurchaseOrders.getPurchaseorder_freight())));
		if(PurchaseOrders.getPurchaseorder_totalcost() != null)
		Purchase.setPurchaseorder_totalcost(Double.parseDouble(df2.format(PurchaseOrders.getPurchaseorder_totalcost())));
		if(PurchaseOrders.getPurchaseorder_advancecost() != null)
		Purchase.setPurchaseorder_advancecost(Double.parseDouble(df2.format(PurchaseOrders.getPurchaseorder_advancecost())));
		if(PurchaseOrders.getPurchaseorder_balancecost() != null)
		Purchase.setPurchaseorder_balancecost(Double.parseDouble(df2.format(PurchaseOrders.getPurchaseorder_balancecost())));

		Purchase.setPurchaseorder_orderd_remark(PurchaseOrders.getPurchaseorder_orderd_remark());
		if(userMap.get(PurchaseOrders.getPurchaseorder_orderdbyId()) != null) {
			Purchase.setPurchaseorder_orderdby(userMap.get(PurchaseOrders.getPurchaseorder_orderdbyId()).getFirstName()+"_"+userMap.get(PurchaseOrders.getPurchaseorder_orderdbyId()).getLastName());
		}else {
			Purchase.setPurchaseorder_orderdby("--");
		}
		Purchase.setPurchaseorder_orderddate(PurchaseOrders.getPurchaseorder_orderddate());

		Purchase.setPurchaseorder_received_remark(PurchaseOrders.getPurchaseorder_received_remark());
		
		if(userMap.get(PurchaseOrders.getPurchaseorder_receivedbyId()) != null) {
			Purchase.setPurchaseorder_receivedby(userMap.get(PurchaseOrders.getPurchaseorder_receivedbyId()).getFirstName()+"_"+userMap.get(PurchaseOrders.getPurchaseorder_receivedbyId()).getLastName());
		}else {
			Purchase.setPurchaseorder_receivedby("--");
		}
		
		Purchase.setPurchaseorder_received_date(PurchaseOrders.getPurchaseorder_received_date());

		Purchase.setPurchaseorder_invoiceno(PurchaseOrders.getPurchaseorder_invoiceno());
		if (PurchaseOrders.getPurchaseorder_invoice_date_On() != null) {
			Purchase.setPurchaseorder_invoice_date(dateFormat.format(PurchaseOrders.getPurchaseorder_invoice_date_On()));
			Purchase.setPurchaseorder_invoice_date_On(PurchaseOrders.getPurchaseorder_invoice_date_On());
		}
		if(PurchaseOrders.getPurchaseorder_total_debitnote_cost() != null)
		Purchase.setPurchaseorder_total_debitnote_cost(Double.parseDouble(df2.format(PurchaseOrders.getPurchaseorder_total_debitnote_cost())));
		
		if (PurchaseOrders.getPurchaseorder_complete_date() != null) {
			Purchase.setPurchaseorder_complete_date(PurchaseOrders.getPurchaseorder_complete_date());
		}

		Purchase.setPurchaseorder_document(PurchaseOrders.isPurchaseorder_document());
		Purchase.setPurchaseorder_document_id(PurchaseOrders.getPurchaseorder_document_id());

		// Create and Last updated Display
		//Purchase.setCreatedBy(PurchaseOrders.getCreatedBy());
		
		if(userMap.get(PurchaseOrders.getCreatedById()) != null) {
			Purchase.setCreatedBy(userMap.get(PurchaseOrders.getCreatedById()).getEmail());
		}else {
			Purchase.setCreatedBy("--");
		}
		
		if (PurchaseOrders.getCreatedOn() != null) {
			Purchase.setCreated(CreatedDateTime.format(PurchaseOrders.getCreatedOn()));
		}
		//Purchase.setLastModifiedBy(PurchaseOrders.getLastModifiedBy());
		if(userMap.get(PurchaseOrders.getLastModifiedById()) != null) {
			Purchase.setLastModifiedBy(userMap.get(PurchaseOrders.getLastModifiedById()).getEmail());
		}else {
			Purchase.setLastModifiedBy("--");
		}
		if (PurchaseOrders.getLastupdatedOn() != null) {
			Purchase.setLastupdated(CreatedDateTime.format(PurchaseOrders.getLastupdatedOn()));
		}
		
		Purchase.setTallyCompanyId(PurchaseOrders.getTallyCompanyId());
		Purchase.setTallyCompanyName(PurchaseOrders.getTallyCompanyName());

		Purchase.setSubCompanyId(PurchaseOrders.getSubCompanyId());
		Purchase.setVendorGstNumber(PurchaseOrders.getVendorGstNumber());
		Purchase.setBuyerGstNumber(PurchaseOrders.getBuyerGstNumber());
		Purchase.setPurchaseorder_vendor_paymodeId(PurchaseOrders.getPurchaseorder_vendor_paymodeId());
		Purchase.setPurchaseorder_vendor_paymode(PurchaseOrders.getPurchaseorder_vendor_paymode());
		Purchase.setPurchaseorder_vendor_approvalID(PurchaseOrders.getPurchaseorder_vendor_approvalID());
		Purchase.setVendorApprovalNumber(PurchaseOrders.getVendorApprovalNumber());
		Purchase.setPurchaseorder_document_id(PurchaseOrders.getPurchaseorder_document_id());
		Purchase.setPurchaseorderOrderdate(PurchaseOrders.getPurchaseorderOrderdate());
		Purchase.setPurchaseorderReceivedDate(PurchaseOrders.getPurchaseorderReceivedDate());

		Purchase.setApprovalStatus(PurchaseOrders.getApprovalStatus());
		Purchase.setApprovedById(PurchaseOrders.getApprovedById());
		Purchase.setApprovalRemark(PurchaseOrders.getApprovalRemark());
		Purchase.setApprovedBy(PurchaseOrders.getApprovedBy());
		Purchase.setApprovalStatusStr(PurchaseOrders.getApprovalStatusStr());
		
		return Purchase;
	}

	// get Purchase order Open Task Id
	public PurchaseOrders preparePurchaseOrdersToAddPart(PurchaseOrders purchaseOrders) {

		PurchaseOrders Purchase = new PurchaseOrders();

		Purchase.setPurchaseorder_id(purchaseOrders.getPurchaseorder_id());
		return Purchase;
	}

	// get Update Purchase ordered BY AND Details
	public PurchaseOrders GetOrderedPurchaseOrderUpdate(PurchaseOrders purchaseOrders) {

		PurchaseOrders Purchase = new PurchaseOrders();

		Purchase.setPurchaseorder_id(purchaseOrders.getPurchaseorder_id());
		Purchase.setPurchaseorder_orderd_remark(purchaseOrders.getPurchaseorder_orderd_remark());
		Purchase.setPurchaseorder_advancecost(purchaseOrders.getPurchaseorder_advancecost());
		Purchase.setPurchaseorder_orderdbyId(purchaseOrders.getPurchaseorder_orderdbyId());
		

		Purchase.setPurchaseorder_statusId(PurchaseOrderType.PURCHASEORDER_STATUS_ORDERED);

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		Purchase.setLastModifiedById(userDetails.getId());
		Purchase.setCompanyId(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Purchase.setPurchaseorder_orderddate(toDate);

		Purchase.setLastupdated(toDate);
		return Purchase;
	}

	// get Update Purchase ordered BY AND Details
	public PurchaseOrders GetReceivedPurchaseOrderUpdate(PurchaseOrdersDto purchaseOrders) {

		PurchaseOrders Purchase = new PurchaseOrders();

		Purchase.setPurchaseorder_id(purchaseOrders.getPurchaseorder_id());
		Purchase.setPurchaseorder_received_remark(purchaseOrders.getPurchaseorder_received_remark());
		Purchase.setPurchaseorder_receivedbyId(purchaseOrders.getPurchaseorder_receivedbyId());
		Purchase.setPurchaseorder_invoiceno(purchaseOrders.getPurchaseorder_invoiceno());
		if (purchaseOrders.getPurchaseorder_invoice_date() != null) {
			java.util.Date date;
			try {
				date = dateFormat.parse(purchaseOrders.getPurchaseorder_invoice_date());
				java.sql.Date invoice_date = new java.sql.Date(date.getTime());
				Purchase.setPurchaseorder_invoice_date(invoice_date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		Purchase.setPurchaseorder_statusId(PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED);

		/** who Create this vehicle get email id to user profile details */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		Purchase.setLastModifiedById(userDetails.getId());
		Purchase.setCompanyId(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Purchase.setPurchaseorder_received_date(toDate);

		Purchase.setLastupdated(toDate);
		return Purchase;
	}

	/**
	 * @param purchase
	 * @return
	 */
	public PurchaseOrders UPdate_Purchase_Freight_Cost(PurchaseOrders purchaseOrders, PurchaseOrders Purchase) {

		PurchaseOrders Purchase_Freight = new PurchaseOrders();

		Purchase_Freight.setPurchaseorder_id(Purchase.getPurchaseorder_id());

		Double TotalPurchaseOrder_Freight = 0.0;
		Double TotalPurchaseOrdercost = 0.0;
		Double TotalPurchaseOrder_advancecost = 0.0;
		Double TotalPurchaseOrder_balance = 0.0;

		Double UI_PurchaseOrder_Freight = 0.0;

		if (Purchase != null) {

			if (Purchase.getPurchaseorder_freight() != null) {

				TotalPurchaseOrder_Freight = Purchase.getPurchaseorder_freight();
			}
			if (Purchase.getPurchaseorder_totalcost() != null) {

				TotalPurchaseOrdercost = Purchase.getPurchaseorder_totalcost();
			}
			if (Purchase.getPurchaseorder_balancecost() != null) {

				TotalPurchaseOrder_balance = Purchase.getPurchaseorder_balancecost();
			}
			if (Purchase.getPurchaseorder_advancecost() != null) {

				TotalPurchaseOrder_advancecost = Purchase.getPurchaseorder_advancecost();
			}

			if (purchaseOrders.getPurchaseorder_freight() != null) {

				UI_PurchaseOrder_Freight = purchaseOrders.getPurchaseorder_freight();
			}

			// TotalPurchaseOrder_balance
			TotalPurchaseOrdercost += UI_PurchaseOrder_Freight;
			TotalPurchaseOrdercost -= TotalPurchaseOrder_Freight;
			// TotalPurchaseOrder_balance
			TotalPurchaseOrder_balance = (TotalPurchaseOrdercost - TotalPurchaseOrder_advancecost);
		}

		Purchase_Freight.setPurchaseorder_freight(round(purchaseOrders.getPurchaseorder_freight(), 2));
		Purchase_Freight.setPurchaseorder_totalcost(round(TotalPurchaseOrdercost, 2));
		Purchase_Freight.setPurchaseorder_balancecost(round(TotalPurchaseOrder_balance, 2));

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Purchase_Freight.setLastModifiedById(userDetails.getId());
		Purchase_Freight.setCompanyId(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Purchase_Freight.setLastupdated(toDate);

		return Purchase_Freight;
	}

	/**
	 * @param purchaseOrders
	 * @param purchase
	 * @return
	 */
	public PurchaseOrders Update_Purchase_TAX_Cost(PurchaseOrders purchaseOrders, PurchaseOrders Purchase) {

		PurchaseOrders Purchase_TaxCost = new PurchaseOrders();

		Purchase_TaxCost.setPurchaseorder_id(purchaseOrders.getPurchaseorder_id());

		Double TotalPurchaseOrder_TaxCost = 0.0;
		Double TotalPurchaseOrdercost = 0.0;
		Double TotalPurchaseOrder_advancecost = 0.0;
		Double TotalPurchaseOrder_balance = 0.0;

		Double UI_Purchaseorder_totaltax_cost = 0.0;

		if (Purchase != null) {

			if (Purchase.getPurchaseorder_totaltax_cost() != null) {

				TotalPurchaseOrder_TaxCost = Purchase.getPurchaseorder_totaltax_cost();
			}
			if (Purchase.getPurchaseorder_totalcost() != null) {

				TotalPurchaseOrdercost = Purchase.getPurchaseorder_totalcost();
			}
			if (Purchase.getPurchaseorder_balancecost() != null) {

				TotalPurchaseOrder_balance = Purchase.getPurchaseorder_balancecost();
			}
			if (Purchase.getPurchaseorder_advancecost() != null) {

				TotalPurchaseOrder_advancecost = Purchase.getPurchaseorder_advancecost();
			}

			if (purchaseOrders.getPurchaseorder_totaltax_cost() != null) {

				UI_Purchaseorder_totaltax_cost = purchaseOrders.getPurchaseorder_totaltax_cost();
			}

			// TotalPurchaseOrder_balance
			TotalPurchaseOrdercost += UI_Purchaseorder_totaltax_cost;
			TotalPurchaseOrdercost -= TotalPurchaseOrder_TaxCost;
			// TotalPurchaseOrder_balance
			TotalPurchaseOrder_balance = (TotalPurchaseOrdercost - TotalPurchaseOrder_advancecost);
		}

		Purchase_TaxCost.setPurchaseorder_totaltax_cost(round(purchaseOrders.getPurchaseorder_totaltax_cost(), 2));
		Purchase_TaxCost.setPurchaseorder_totalcost(round(TotalPurchaseOrdercost, 2));
		Purchase_TaxCost.setPurchaseorder_balancecost(round(TotalPurchaseOrder_balance, 2));

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Purchase_TaxCost.setLastModifiedById(userDetails.getId());
		Purchase_TaxCost.setCompanyId(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Purchase_TaxCost.setLastupdated(toDate);
		return Purchase_TaxCost;
	}

	/**
	 * @param purchase
	 * @return
	 */
	public PurchaseOrders Update_PurchaseOrder_Completed(PurchaseOrdersDto Purchase) {

		PurchaseOrders Purchase_complete = new PurchaseOrders();
		Purchase_complete.setPurchaseorder_id(Purchase.getPurchaseorder_id());

		// Update Main PurchaseOrder Advance and Balance And Statues IS
		// ORDERED Change

		Double TotalPurchaseOrder_debitnote = 0.0;
		Double TotalPurchaseOrder_balance = 0.0;
		Double TotalCost				  = 0.0;
		Double AdvanceCost				  = 0.0;

		if (Purchase != null) {

			if (Purchase.getPurchaseorder_total_debitnote_cost() != null) {

				TotalPurchaseOrder_debitnote = Purchase.getPurchaseorder_total_debitnote_cost();
			}
			if (Purchase.getPurchaseorder_totalcost() != null) {

				 TotalCost = Purchase.getPurchaseorder_totalcost();
			}
			
			if(Purchase.getPurchaseorder_advancecost() != null) {
				AdvanceCost	= Purchase.getPurchaseorder_advancecost();
			}

			// TotalPurchaseOrder_balance
			TotalPurchaseOrder_balance = TotalCost - (TotalPurchaseOrder_debitnote + AdvanceCost);

		}

		Purchase_complete.setPurchaseorder_balancecost(round(TotalPurchaseOrder_balance, 2));

		Purchase_complete.setPurchaseorder_statusId(PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED);

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Purchase_complete.setLastModifiedById(userDetails.getId());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Purchase_complete.setLastupdated(toDate);

		Purchase_complete.setPurchaseorder_complete_date(toDate);

		return Purchase_complete;
	}

	/**
	 * @param purchase
	 * @return
	 */
	public PurchaseOrders Update_PurchaseOrder_ReOpen(PurchaseOrdersDto Purchase) {

		PurchaseOrders Purchase_complete = new PurchaseOrders();
		Purchase_complete.setPurchaseorder_id(Purchase.getPurchaseorder_id());

		// Update Main PurchaseOrder Advance and Balance And Statues IS
		// ORDERED Change

		Double TotalPurchaseOrder_debitnote = 0.0;
		Double TotalPurchaseOrder_balance = 0.0;

		if (Purchase != null) {

			if (Purchase.getPurchaseorder_total_debitnote_cost() != null) {

				TotalPurchaseOrder_debitnote = Purchase.getPurchaseorder_total_debitnote_cost();
			}
			if (Purchase.getPurchaseorder_balancecost() != null) {

				TotalPurchaseOrder_balance = Purchase.getPurchaseorder_balancecost();
			}

			// TotalPurchaseOrder_balance
			TotalPurchaseOrder_balance -= TotalPurchaseOrder_debitnote;

		}

		Purchase_complete.setPurchaseorder_balancecost(round(TotalPurchaseOrder_balance, 2));

		Purchase_complete.setPurchaseorder_statusId(PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED);

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Purchase_complete.setLastModifiedById(userDetails.getId());
		Purchase_complete.setCompanyId(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Purchase_complete.setLastupdated(toDate);

		Purchase_complete.setPurchaseorder_complete_date(toDate);

		return Purchase_complete;
	}

	/**
	 * @param debitNote
	 * @return
	 */
	public PurchaseOrders Update_PurchaseOrder_DeleteReturnParts(PurchaseOrdersToDebitNote debitNote) {

		PurchaseOrders Purchase_complete = new PurchaseOrders();
		Purchase_complete.setPurchaseorder_id(debitNote.getPurchaseorder_id());

		// Update Main PurchaseOrder Advance and Balance And Statues IS
		// ORDERED Change

		Double TotalPurchaseOrder_debitnote = 0.0;
		Double TotalPurchaseOrder_balance = 0.0;

		if (debitNote != null) {

			if (debitNote.getTotal_return_cost() != null) {

				TotalPurchaseOrder_debitnote = debitNote.getTotal_return_cost();

				TotalPurchaseOrder_balance = debitNote.getTotal_return_cost();
			}

		}
		Purchase_complete.setPurchaseorder_total_debitnote_cost(TotalPurchaseOrder_debitnote);
		Purchase_complete.setPurchaseorder_balancecost(TotalPurchaseOrder_balance);

		Purchase_complete.setPurchaseorder_statusId(PurchaseOrderType.PURCHASEORDER_STATUS_ORDERED);

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Purchase_complete.setLastModifiedById(userDetails.getId());
		Purchase_complete.setCompanyId(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Purchase_complete.setLastupdated(toDate);

		Purchase_complete.setPurchaseorder_complete_date(toDate);

		return Purchase_complete;
	}

	/**
	 * @param purchase
	 * @return
	 */
	public List<PurchaseOrdersToPartsDto> prepare_Part_consuming_Report(List<PurchaseOrdersToPartsDto> purchase) {
		// Part_consuming Report
		List<PurchaseOrdersToPartsDto> Dtos = null;
		if (purchase != null && !purchase.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersToPartsDto>();
			PurchaseOrdersToPartsDto Purchase = null;
			for (PurchaseOrdersToPartsDto PurchaseOrders : purchase) {
				Purchase = new PurchaseOrdersToPartsDto();

				if (PurchaseOrders.getInventory_all_id() != 0) {

					Purchase.setPartid(PurchaseOrders.getPartid());
					Purchase.setPurchaseorder_partname(PurchaseOrders.getPurchaseorder_partname());
					Purchase.setPurchaseorder_partnumber(PurchaseOrders.getPurchaseorder_partnumber());
					Purchase.setInventory_all_id(PurchaseOrders.getInventory_all_id());

					Dtos.add(Purchase);
				}
			}
		}
		return Dtos;
	}

	public PurchaseOrders Update_PurchaseOrder_ReEnter(PurchaseOrdersDto Purchase) {
		PurchaseOrders purchaseOrders = null;
		CustomUserDetails userDetails = null;
		try {
			java.util.Date 	currentDateUpdate 	= new java.util.Date();
			Timestamp 		toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
			purchaseOrders 	= new PurchaseOrders();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			purchaseOrders.setPurchaseorder_id(Purchase.getPurchaseorder_id());
			purchaseOrders.setPurchaseorder_statusId(PurchaseOrderType.PURCHASEORDER_STATUS_REQUISITION);
			purchaseOrders.setLastModifiedById(userDetails.getId());
			purchaseOrders.setLastupdated(toDate);

			return purchaseOrders;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	
	}
	
	public PurchaseOrdersToUrea prepareUreaPurchaseOrder(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		                              
		try {

			PurchaseOrdersToUrea purchaseOrdersToUrea = new PurchaseOrdersToUrea();
			purchaseOrdersToUrea.setUreaManufacturerId(valueObject.getLong("ureaManufacturerId",0));
			purchaseOrdersToUrea.setQuantity(valueObject.getDouble("quantity",0));
			purchaseOrdersToUrea.setUnitCost(valueObject.getDouble("unitprice",0));
			purchaseOrdersToUrea.setDiscount(valueObject.getDouble("discount",0));
			purchaseOrdersToUrea.setTax(valueObject.getDouble("gst",0));
			purchaseOrdersToUrea.setTotalcost(valueObject.getDouble("totalCost",0));
			purchaseOrdersToUrea.setNotRecivedQuantity(valueObject.getDouble("NotReceivedQuantity",0));
			purchaseOrdersToUrea.setRecivedQuantity(valueObject.getDouble("receivedQuantity",0));
			purchaseOrdersToUrea.setReceived_quantity_remark(valueObject.getString("receivedRemark", ""));
			purchaseOrdersToUrea.setPurchaseOrderId(valueObject.getLong("purchaseOrderId",0));
			purchaseOrdersToUrea.setCompanyId(userDetails.getCompany_id());
			purchaseOrdersToUrea.setMarkForDelete(false);

			return purchaseOrdersToUrea;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			
		}
		

	}
	
	public PurchaseOrdersToDebitNote preparedPurchaseOrdersToDebitNote(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
	
		PurchaseOrdersToDebitNote 	PurchaseDebitNode 		= null;
		double						noReceivedQuantity		= 0;
		double						unitCost				= 0;
		double						tax						= 0;
		double						discount				= 0;
		Double						totalReturnCost			= 0.0;
		Double 						discountCost 			= 0.0;
		try {
			PurchaseDebitNode 	= new PurchaseOrdersToDebitNote();
			noReceivedQuantity	= valueObject.getDouble("noReceivedQuantity");
			unitCost			= valueObject.getDouble("unitprice");
			tax					= valueObject.getDouble("tax");
			discount			= valueObject.getDouble("discount");

			discountCost 	= (noReceivedQuantity * unitCost) - ((noReceivedQuantity * unitCost) * (discount / 100));
			totalReturnCost = round(((discountCost) + (discountCost * (tax / 100))), 2);
			
			PurchaseDebitNode.setNotreceived_quantity(valueObject.getDouble("noReceivedQuantity"));
			PurchaseDebitNode.setParteachcost(valueObject.getDouble("unitprice"));
			PurchaseDebitNode.setDiscount(valueObject.getDouble("discount"));
			PurchaseDebitNode.setTax(valueObject.getDouble("tax"));
			PurchaseDebitNode.setReceived_quantity_remark(valueObject.getString("receivedQuantityRemark"));
			PurchaseDebitNode.setTotal_return_cost(totalReturnCost);
			PurchaseDebitNode.setPurchaseorder_id(valueObject.getLong("purchaseOrderId"));
			PurchaseDebitNode.setCompanyId(userDetails.getCompany_id());
			PurchaseDebitNode.setPartid(valueObject.getLong("purchaseOrdersToUreaId")); // actu;;y this is purchase order subtable primary id
			PurchaseDebitNode.setMarkForDelete(false);
			return PurchaseDebitNode;
	
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
	}

	public ValueObject getTotalAmountOfPurchaseOrders(PurchaseOrdersDto purchase,Double totalOrdered,Double totalRecevied, Double totalEachCost, double totalDiscountCost, double TotalReceivedPartCost) throws Exception {
		try {
			ValueObject object = new ValueObject();
			object.put("totalOrdered", df2.format(totalOrdered));
			object.put("totalRecevied", df2.format(totalRecevied));
			object.put("totalEachCost", df2.format(totalEachCost));
			object.put("TotalDiscountCost", df2.format(totalDiscountCost));
			object.put("finalCost", df2.format((totalEachCost + purchase.getPurchaseorder_totaltax_cost() + purchase.getPurchaseorder_freight()) - totalDiscountCost));
			object.put("balanceCost",df2.format(((totalEachCost + purchase.getPurchaseorder_totaltax_cost() + purchase.getPurchaseorder_freight()) - (totalDiscountCost+purchase.getPurchaseorder_advancecost()))));
			object.put("TotalReceivedPartCost", df2.format(TotalReceivedPartCost));
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public PurchaseOrders preparePurchaseOrdersForOtherPart(ValueObject object,PurchaseOrders existPurchaseOrders) throws Exception {
		PurchaseOrders newPurchaseOrders 	= null;
	
		try {
			newPurchaseOrders 	= 	new PurchaseOrders();
			if(existPurchaseOrders != null) {
				newPurchaseOrders.setPurchaseorder_typeId(existPurchaseOrders.getPurchaseorder_typeId());
				newPurchaseOrders.setPurchaseorder_vendor_id(object.getInt("vendorId"));
				newPurchaseOrders.setPurchaseorder_created_on(existPurchaseOrders.getPurchaseorder_created_on());
				newPurchaseOrders.setPurchaseorder_requied_on(existPurchaseOrders.getPurchaseorder_requied_on());
				newPurchaseOrders.setPurchaseorder_termsId(existPurchaseOrders.getPurchaseorder_termsId());
				newPurchaseOrders.setPurchaseorder_vendor_paymodeId(existPurchaseOrders.getPurchaseorder_vendor_paymodeId());
				newPurchaseOrders.setPurchaseorder_vendor_paymentdate(existPurchaseOrders.getPurchaseorder_vendor_paymentdate());
				newPurchaseOrders.setPurchaseorder_vendor_approvalID(existPurchaseOrders.getPurchaseorder_vendor_approvalID());
				newPurchaseOrders.setPurchaseorder_shipviaId(existPurchaseOrders.getPurchaseorder_shipviaId());
				newPurchaseOrders.setPurchaseorder_shiplocation_id(existPurchaseOrders.getPurchaseorder_shiplocation_id());
				newPurchaseOrders.setPurchaseorder_quotenumber(existPurchaseOrders.getPurchaseorder_quotenumber());
				newPurchaseOrders.setPurchaseorder_workordernumber(existPurchaseOrders.getPurchaseorder_workordernumber());
				newPurchaseOrders.setPurchaseorder_indentno(existPurchaseOrders.getPurchaseorder_indentno());
				newPurchaseOrders.setPurchaseorder_notes(existPurchaseOrders.getPurchaseorder_notes());
				
				newPurchaseOrders.setPurchaseorder_statusId(PurchaseOrderType.PURCHASEORDER_STATUS_REQUISITION);
				// set default value in save time
				newPurchaseOrders.setPurchaseorder_subtotal_cost(0.0);
				newPurchaseOrders.setPurchaseorder_totalcost(0.0);
				newPurchaseOrders.setPurchaseorder_freight(0.0);
				newPurchaseOrders.setPurchaseorder_totaltax_cost(0.0);
				newPurchaseOrders.setPurchaseorder_advancecost(0.0);
				newPurchaseOrders.setPurchaseorder_balancecost(0.0);
				newPurchaseOrders.setPurchaseorder_total_debitnote_cost(0.0);
				newPurchaseOrders.setPurchaseorder_document(false);
				newPurchaseOrders.setPurchaseorder_document_id((long) 0);
				newPurchaseOrders.setPurchaseorder_buyer_id(existPurchaseOrders.getCompanyId());
				newPurchaseOrders.setCreatedById(existPurchaseOrders.getCreatedById());
				newPurchaseOrders.setLastModifiedById(existPurchaseOrders.getLastModifiedById());
				newPurchaseOrders.setCompanyId(existPurchaseOrders.getCompanyId());
				
				newPurchaseOrders.setCreated(DateTimeUtility.getCurrentTimeStamp());
				newPurchaseOrders.setLastupdated(DateTimeUtility.getCurrentTimeStamp());
				newPurchaseOrders.setTallyCompanyId(existPurchaseOrders.getTallyCompanyId());
				newPurchaseOrders.setSubCompanyId(existPurchaseOrders.getSubCompanyId());
				newPurchaseOrders.setMarkForDelete(false);
			}
			
			return newPurchaseOrders;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}	


public PurchaseOrdersToParts preparePurchaseOrdersTaskToPart(String partId,String quantityStr,String eachCostStr,String discountStr,String taxStr) {

		PurchaseOrdersToParts purchasePart = new PurchaseOrdersToParts();
		if(partId != null && !partId.trim().equals("")) {

			purchasePart.setPartid(Long.valueOf(partId));
			
			Double quantity = 0.0;
			Double eachCost = 0.0;
			Double discount = 0.0;
			Double tax = 0.0;
			
			if(quantityStr != null && !quantityStr.equals("")) 
				quantity = Double.valueOf(quantityStr);
			if(eachCostStr != null && ! eachCostStr.equals("")) 
				eachCost = Double.valueOf(eachCostStr);
			if(discountStr != null && ! discountStr.equals("")) 
				discount = Double.valueOf(discountStr);
			if(taxStr != null && ! taxStr.equals("")) 
				tax = Double.valueOf(taxStr);
			InventoryAllDto invent;
			try {
				invent = inventoryService.Purchase_Order_Validate_InventoryAll(purchasePart.getPartid());
				if (invent != null && invent.getAll_quantity() != null && invent.getAll_quantity() != 0.0) {
					purchasePart.setInventory_all_id(invent.getInventory_all_id());
					purchasePart.setInventory_all_quantity(invent.getAll_quantity());
			} else {
				purchasePart.setInventory_all_id((long) 0);
				purchasePart.setInventory_all_quantity(0.0);
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Double discountCost = 0.0;
			Double TotalCost = 0.0;

			discountCost 	= (quantity * eachCost) - ((quantity * eachCost) * (discount / 100));
			TotalCost 		= round(((discountCost) + (discountCost * (tax / 100))), 2);
			
			purchasePart.setQuantity(quantity);
			purchasePart.setReceived_quantity(0.0);
			purchasePart.setNotreceived_quantity(quantity);
			purchasePart.setRequestedQuantity(quantity);
			purchasePart.setParteachcost(eachCost);
			purchasePart.setDiscount(discount);
			purchasePart.setTax(tax);

			purchasePart.setTotalcost(TotalCost);
		}
		return purchasePart;
	}
}