package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.constant.InventoryStockTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.PurchaseOrderType;
import org.fleetopgroup.constant.RequisitionConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RequisitionApprovalDetailsDto;
import org.fleetopgroup.persistence.model.ApprovedRequisitionDetails;
import org.fleetopgroup.persistence.model.Inventory;
import org.fleetopgroup.persistence.model.InventoryAll;
import org.fleetopgroup.persistence.model.InventoryLocation;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.model.PartiallyReceivedDetails;
import org.fleetopgroup.persistence.model.PurchaseOrders;
import org.fleetopgroup.persistence.model.Requisition;
import org.fleetopgroup.persistence.model.SubRequisition;
import org.fleetopgroup.persistence.model.TransferQuantityDetails;
import org.fleetopgroup.persistence.model.TransferRequisitionDetails;
import org.fleetopgroup.persistence.model.TransferedInventoryDetails;
import org.fleetopgroup.persistence.model.UreaInvoiceToDetails;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDayWiseInventoryStockService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionApprovalService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionReceivelService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequisitionBL {
	
	@Autowired IRequisitionApprovalService approvalService;
	
	@Autowired private	IMasterPartsService 				masterPartsService;
	
	@Autowired private	CompanyConfigurationService			companyConfigurationService;
	
	@Autowired private	IDayWiseInventoryStockService		dayWiseInventoryStockService;
	
	@Autowired private IInventoryService 					inventoryService;
	
	@Autowired IRequisitionReceivelService requisitionReceivelService;
	
	
	public  List<RequisitionApprovalDetailsDto> prepareApprovalDto(List<ApprovedRequisitionDetails> approvalList){
		List<RequisitionApprovalDetailsDto> approvalDtoList = null;	
		RequisitionApprovalDetailsDto appDto = null;
		if(approvalList != null && !approvalList.isEmpty()) {
			approvalDtoList = new ArrayList<>();
			for(ApprovedRequisitionDetails appDetails:approvalList) {
				appDto = new RequisitionApprovalDetailsDto();
				appDto.setApprovedQuantity(appDetails.getApprovedQuantity());
				appDto.setApprovedRequisitionId(appDetails.getApprovedRequisitionId());
				appDto.setApprovedTypeId(appDetails.getApprovedTypeId());
				appDto.setApprovedTypeName(RequisitionConstant.getTransferType(appDetails.getApprovedTypeId()));
				appDto.setBranchId(appDetails.getBranchId());
				appDto.setPoId(appDetails.getPoId());
				appDto.setSubRequisitionId(appDetails.getSubRequisitionId());
				approvalDtoList.add(appDto);
			}
		}
		return approvalDtoList;
	}
	
	public  TransferRequisitionDetails prepareTransferReq(ValueObject object) {
		TransferRequisitionDetails transfer = new TransferRequisitionDetails();
		transfer.setApprovedRequisitionId(object.getLong("approvalId",0));
		transfer.setCreationOn(new Date());
		transfer.setCreatedById(object.getLong("userId",0));
		transfer.setCompanyId(object.getInt("companyId",0));
		transfer.setFromLocation(object.getLong("fromLocationId",0));
		transfer.setToLocation(object.getLong("toLocationId",0));
		transfer.setTransactionId(object.getLong("transactionId",0));
		transfer.setRequisitionTypeId(object.getShort("requisitionType",(short) 0));
		transfer.setTransferQuantity(object.getDouble("quantity",0));
		transfer.setReceiverId(object.getLong("receiver",0));
		transfer.setSubRequisitionId(object.getLong("subRequisitionId",0));
		transfer.setUreaManufacturerId(object.getLong("tUreaManufacturer",0));
		return transfer;
	}
	
	public ApprovedRequisitionDetails prepareapprovalRequistion(ValueObject valueObject,int companyId,Long userId,long subRequisitionId) {
		
		ApprovedRequisitionDetails approvedDetails = new ApprovedRequisitionDetails();
		approvedDetails.setApprovedTypeId(valueObject.getShort("transferType",(short)0));
		approvedDetails.setApprovedQuantity(valueObject.getInt("quantity", 0));
		approvedDetails.setCreationOn(new Date());
		approvedDetails.setApprovedStatus(valueObject.getShort("appStatus",(short)0));
		approvedDetails.setAssignedTo(valueObject.getLong("assignedTo",0));
		approvedDetails.setRemark(valueObject.getString("remark",""));
		approvedDetails.setCompanyId(companyId);
		approvedDetails.setSubRequisitionId(subRequisitionId);
		approvedDetails.setCreatedById(userId);
		approvedDetails.setBranchId(valueObject.getLong("branch",0));
		
		return approvedDetails;
		
	}
	
	public UreaInvoiceToDetails calculateAnsPreapreUreaInventory(ValueObject object,UreaInvoiceToDetails ureaDetails,TransferRequisitionDetails transferDetails) {
		UreaInvoiceToDetails finalUreaDetails = new UreaInvoiceToDetails();
		if(ureaDetails != null) {
			Double eachCost =0.0,discount=0.0,tax=0.0;
			short discountTaxTypeId =ureaDetails.getDiscountTaxTypeId();
				if(ureaDetails.getUnitprice() != null)
				eachCost +=ureaDetails.getUnitprice();
				if(ureaDetails.getDiscount() != null)
				discount +=ureaDetails.getDiscount();
				if(ureaDetails.getTax() != null)
				tax += ureaDetails.getTax();
			Double total=requisitionReceivelService.calculateInventoryTotal(discountTaxTypeId,object.getDouble("quantity",0),eachCost, discount, tax);
			finalUreaDetails.setTotal(total);
			finalUreaDetails.setCompanyId(object.getInt("companyId",0));
			finalUreaDetails.setUnitprice(eachCost);
			finalUreaDetails.setDiscount(discount);
			finalUreaDetails.setTax(tax);
			finalUreaDetails.setDiscountTaxTypeId(discountTaxTypeId);
			finalUreaDetails.setWareHouseLocation((int)(long)transferDetails.getToLocation());
			finalUreaDetails.setUreaInvoiceId(ureaDetails.getUreaInvoiceId());
			finalUreaDetails.setMarkForDelete(false);
			finalUreaDetails.setManufacturerId(ureaDetails.getManufacturerId());
			finalUreaDetails.setQuantity(object.getDouble("quantity",0));
			finalUreaDetails.setStockQuantity(object.getDouble("quantity",0));
			finalUreaDetails.setUsedQuantity((double) 0);
			finalUreaDetails.setVendor_id(ureaDetails.getVendor_id());
			finalUreaDetails.setCreatedById(object.getLong("userId",0));
			finalUreaDetails.setLastModifiedById(object.getLong("userId",0));
			finalUreaDetails.setCreated(new Date());
			finalUreaDetails.setLastupdated(new Date());
			finalUreaDetails.setTransferQuantity(0.0);
		}
		return finalUreaDetails;
	}
	
	public Inventory preparePart(ValueObject object,Inventory inventory,TransferRequisitionDetails transferDetails) throws Exception {

		refreshmentPrepare(object, inventory, transferDetails);			
		Inventory part = new Inventory();
		part.setPartid(inventory.getPartid());
		if (inventory.getMake() != null) {
			part.setMake(inventory.getMake());
		}
		part.setUnitprice(inventory.getUnitprice());
		part.setDiscountTaxTypeId(inventory.getDiscountTaxTypeId());
		part.setDiscount(inventory.getDiscount());
		part.setTax(inventory.getTax());
		part.setInvoice_number(inventory.getInvoice_number());
		part.setInvoice_amount(inventory.getInvoice_amount());
		if (inventory.getInvoice_date() != null) {
			part.setInvoice_date(inventory.getInvoice_date());
		}
		part.setVendor_id(inventory.getVendor_id());
		part.setDescription(inventory.getDescription());
		part.setCreatedById(object.getLong("userId",0));
		part.setLastModifiedById(object.getLong("userId",0));
		part.setCompanyId(object.getInt("companyId") );
		Timestamp toDate = DateTimeUtility.getCurrentTimeStamp();
		part.setCreated(toDate);
		part.setLastupdated(toDate);
		part.setMarkForDelete(false);

		part.setHistory_quantity(object.getDouble("rQuantity", 0));
		part.setQuantity(object.getDouble("rQuantity", 0));
		part.setLocationId((int)(long)transferDetails.getToLocation());
		part.setInventory_all_id(inventory.getInventory_all_id());
		List<InventoryLocation> validateLocation = inventoryService.Validate_Inventory_Location(inventory.getPartid(),(int)(long)transferDetails.getToLocation());
		if (validateLocation != null && !validateLocation.isEmpty()) {
				part.setInventory_location_id(validateLocation.get(0).getInventory_location_id());
			}
		 else {
			InventoryLocation inventoryLocation = new InventoryLocation();
			inventoryLocation.setLocation_quantity(object.getDouble("rQuantity", 0));
			inventoryLocation.setLocationId((int)(long)transferDetails.getToLocation());
			InventoryAll NewAll = new InventoryAll();
			NewAll.setInventory_all_id(inventory.getInventory_all_id());
			inventoryLocation.setInventoryall(NewAll);
			inventoryLocation.setCompanyId(object.getInt("companyId",0));
			inventoryLocation.setPartid(inventory.getPartid());
			inventoryLocation =inventoryService.addInventoryLocation(inventoryLocation);
			part.setInventory_location_id(inventoryLocation.getInventory_location_id());
		}
		return part;

	}
	
	public void refreshmentPrepare(ValueObject object,Inventory inventory,TransferRequisitionDetails transferDetails) {
		HashMap<String, Object> configuration;
		try {
			configuration = companyConfigurationService.getCompanyConfiguration(object.getInt("companyId",0), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
		if((boolean) configuration.get("showRefreshmentOption")) {
			MasterParts	masterParts	=	masterPartsService.getMasterParts(inventory.getPartid());
			if(masterParts != null && masterParts.isRefreshment()) {
					ValueObject	valueObject	= new ValueObject();
					valueObject.put("transactionDate", DateTimeUtility.getCurrentDate());
					valueObject.put("partId", inventory.getPartid());
					valueObject.put("locationId",  transferDetails.getFromLocation());
					valueObject.put("addedQuantity", - object.getDouble("rQuantity", 0));
					valueObject.put("usedQuantity", 0.0);
					valueObject.put("companyId",object.getInt("companyId",0));
					valueObject.put("numberType", "I-"+ inventory.getInventory_Number()+" Part Edit");
					valueObject.put("transactionId", inventory.getInventory_id());
					valueObject.put("primaryType", InventoryStockTypeConstant.STOCK_TYPE_TRANSFER);
					valueObject.put("transactionType", InventoryStockTypeConstant.STOCK_TYPE_TRANSFER);
					valueObject.put("quantity", - object.getDouble("rQuantity", 0));
					valueObject.put("isDateChanged", false);
					
					dayWiseInventoryStockService.processLocationWiseInventoryStockDetails(valueObject);	
			}
		}
		if((boolean) configuration.get("showRefreshmentOption")) {
			MasterParts	masterParts	=	masterPartsService.getMasterParts(inventory.getPartid());
			if(masterParts != null && masterParts.isRefreshment()) {
				ValueObject	valueObject	= new ValueObject();
				
				valueObject.put("partId",inventory.getPartid());
				valueObject.put("locationId", transferDetails.getToLocation());
				valueObject.put("quantity", object.getDouble("rQuantity", 0));
				valueObject.put("companyId",object.getInt("companyId",0));
				valueObject.put("addedQuantity",  object.getDouble("rQuantity", 0));
				valueObject.put("usedQuantity", 0.0);
				valueObject.put("numberType", "I-"+ inventory.getInventory_Number()+" Part transfer");
				valueObject.put("transactionId", inventory.getInventory_id());
				valueObject.put("primaryType", InventoryStockTypeConstant.STOCK_TYPE_TRANSFER);
				valueObject.put("transactionType", InventoryStockTypeConstant.STOCK_TYPE_TRANSFER);
				dayWiseInventoryStockService.processLocationWiseInventoryStockDetails(valueObject);	
				
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PurchaseOrders preparePO(ValueObject object, Requisition requisition) {

		PurchaseOrders purchase = new PurchaseOrders();
		purchase.setPurchaseorder_typeId(object.getShort("type",(short) 0));
		Timestamp toDate = DateTimeUtility.getCurrentTimeStamp();
		purchase.setPurchaseorder_vendor_id(object.getInt("selectVendor",0));
		purchase.setPurchaseorder_created_on(toDate);
		purchase.setPurchaseorder_requied_on(requisition.getRequireOn());
		purchase.setPurchaseorder_shipviaId(object.getShort("shipvia",(short) 0));
		purchase.setPurchaseorder_shiplocation_id((int)(long)requisition.getLocation());
		purchase.setPurchaseorder_statusId(PurchaseOrderType.PURCHASEORDER_STATUS_REQUISITION);
		purchase.setPurchaseorder_subtotal_cost(0.0);
		purchase.setPurchaseorder_totalcost(0.0);
		purchase.setPurchaseorder_termsId(object.getShort("terms",(short) 0));
		purchase.setPurchaseorder_freight(0.0);
		purchase.setPurchaseorder_totaltax_cost(0.0);
		purchase.setPurchaseorder_advancecost(0.0);
		purchase.setPurchaseorder_balancecost(0.0);
		purchase.setPurchaseorder_total_debitnote_cost(0.0);
		purchase.setPurchaseorder_document(false);
		purchase.setPurchaseorder_document_id((long) 0);
		purchase.setMarkForDelete(false);
		purchase.setPurchaseorder_buyer_id(object.getInt("companyId",0));
		purchase.setCreatedById(object.getLong("userId",0));
		purchase.setLastModifiedById(object.getLong("userId",0));
		purchase.setCompanyId(object.getInt("companyId",0));
		purchase.setCreated(toDate);
		purchase.setLastupdated(toDate);
		purchase.setTallyCompanyId((long)0);

		return purchase;
	}
	
	
	public SubRequisition prepareSubRequiSition(ValueObject object,Long reqId ,CustomUserDetails userDetails) {
		SubRequisition reqType = null;
		reqType = new SubRequisition();
		reqType.setRequisitionTypeId(object.getShort("reqType",(short)0));
		reqType.setTransactionId(object.getLong("transactionId",0));
		reqType.setTyreModel(object.getLong("tyremodel",0));
		reqType.setTyreManufacturer(object.getLong("manufacurer",0));
		reqType.setTyreSize(object.getLong("tyreSize",0));
		reqType.setBatteryManufacturer(object.getLong("batteryManufacturer",0));
		reqType.setBatteryModel(object.getLong("batterryTypeId",0));
		reqType.setBatteryCapacity(object.getLong("batteryCapacityId",0));
		reqType.setQuantity(object.getDouble("Qty",0));
		reqType.setRequisitionId(reqId);
		reqType.setCreatedById(userDetails.getId());
		reqType.setCompanyId(userDetails.getCompany_id());
		reqType.setCreationOn(new Date());
		
		return reqType;	
	}
	
	public Requisition prepareRequition(ValueObject valueObject,CustomUserDetails userDetails ) throws Exception {
		Requisition reqToSave = new Requisition();
		try {
		reqToSave.setLocation(valueObject.getLong("location", 0));
		reqToSave.setAssignTo(valueObject.getLong("assignToId", 0));
		reqToSave.setRefNumber(valueObject.getString("refNumber", ""));
		reqToSave.setRemark(valueObject.getString("remark", ""));
		reqToSave.setCreatedById(userDetails.getId());
		reqToSave.setCompanyId(userDetails.getCompany_id());
		reqToSave.setCreationOn(new Date());
		reqToSave.setRequireOn(DateTimeUtility.getDateFromString(valueObject.getString("requireDate"), DateTimeUtility.DD_MM_YYYY));
		reqToSave.setRequisitionStatus(RequisitionConstant.REQUISITION_CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reqToSave;
	}
	
	public PartiallyReceivedDetails preaprePartialReceive(ValueObject object) {
		PartiallyReceivedDetails partiallyReceivedDetails= new PartiallyReceivedDetails();
		partiallyReceivedDetails.setapprovedId(object.getLong("approvalId",0));
		partiallyReceivedDetails.setReceivedQuantity(object.getDouble("receiveQty",0));
		partiallyReceivedDetails.setRemark(object.getString("receiveRemark","") );
		partiallyReceivedDetails.setCompanyId(object.getInt("companyId",0));
		partiallyReceivedDetails.setCreatedById(object.getLong("userId",0));
		partiallyReceivedDetails.setCreationOn(new Date());
		partiallyReceivedDetails.setMarkForDelete(false);
		return partiallyReceivedDetails;
	}
	
	public TransferedInventoryDetails prepareTransferedInventoryDetails(ValueObject object,long newInventoryId,long transferDetailsId) {
		TransferedInventoryDetails transfedInventory=new TransferedInventoryDetails();
		transfedInventory.setCreatedById(object.getLong("userId",0));
		transfedInventory.setCompanyId(object.getInt("companyId",0) );
		transfedInventory.setApprovalRequisitionId(object.getLong("approvalId",0));
		transfedInventory.setSubRequisitionId(object.getLong("subRequisitionId",0));
		transfedInventory.setCreatedInventoryId(newInventoryId);
		transfedInventory.setTransferedInventoryDetailsId(transferDetailsId);
		
		return transfedInventory;
		
	}
	
	public TransferQuantityDetails preaprePartialTransfer(ValueObject object) {
		TransferQuantityDetails transferQuantityDetails= new TransferQuantityDetails();
		transferQuantityDetails.setApprovedId(object.getLong("approvalId",0));
		transferQuantityDetails.setTransferedQuantity(object.getInt("transferQty", 0));
		transferQuantityDetails.setRemark(object.getString("receiveRemark","") );
		transferQuantityDetails.setCompanyId(object.getInt("companyId",0));
		transferQuantityDetails.setCreatedById(object.getLong("userId",0));
		transferQuantityDetails.setCreationOn(DateTimeUtility.getCurrentTimeStamp());
		transferQuantityDetails.setMarkForDelete(false);
		return transferQuantityDetails;	
	
	}
}
