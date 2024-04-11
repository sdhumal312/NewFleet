package org.fleetopgroup.persistence.service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.fleetopgroup.constant.BatteryConstant;
import org.fleetopgroup.constant.ClothInvoiceStockType;
import org.fleetopgroup.constant.RequisitionConstant;
import org.fleetopgroup.constant.UserAlertNOtificationsConstant;
import org.fleetopgroup.mobilenotifications.NotificationBllImpl;
import org.fleetopgroup.mobilenotifications.NotificationConstant;
import org.fleetopgroup.persistence.bl.ClothInventoryBL;
import org.fleetopgroup.persistence.bl.RequisitionBL;
import org.fleetopgroup.persistence.dao.ApprovalRequisitionRepository;
import org.fleetopgroup.persistence.dao.BatteryRepository;
import org.fleetopgroup.persistence.dao.BatteryTransferRepository;
import org.fleetopgroup.persistence.dao.ClothInventoryStockTypeDetailsRepository;
import org.fleetopgroup.persistence.dao.InventoryRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreTransferRepository;
import org.fleetopgroup.persistence.dao.MobileAppUserRegistrationRepository;
import org.fleetopgroup.persistence.dao.MobileNotificationRepository;
import org.fleetopgroup.persistence.dao.PartiallyReceivedDetailsRepository;
import org.fleetopgroup.persistence.dao.PurchaseOrdersRepository;
import org.fleetopgroup.persistence.dao.SubRequisitionRepository;
import org.fleetopgroup.persistence.dao.TransferRequisitionRepositiory;
import org.fleetopgroup.persistence.dao.TransferedInventoryDetailsRepository;
import org.fleetopgroup.persistence.dao.UreaInvoiceToDetailsRepository;
import org.fleetopgroup.persistence.dao.UserAlertNotificationsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.SubRequisitionDto;
import org.fleetopgroup.persistence.model.ApprovedRequisitionDetails;
import org.fleetopgroup.persistence.model.ClothInventoryStockTypeDetails;
import org.fleetopgroup.persistence.model.Inventory;
import org.fleetopgroup.persistence.model.MobileNotifications;
import org.fleetopgroup.persistence.model.PartiallyReceivedDetails;
import org.fleetopgroup.persistence.model.PurchaseOrders;
import org.fleetopgroup.persistence.model.PurchaseOrdersSequeceCounter;
import org.fleetopgroup.persistence.model.Requisition;
import org.fleetopgroup.persistence.model.SubRequisition;
import org.fleetopgroup.persistence.model.TransferRequisitionDetails;
import org.fleetopgroup.persistence.model.TransferedInventoryDetails;
import org.fleetopgroup.persistence.model.UreaInvoiceToDetails;
import org.fleetopgroup.persistence.model.UserAlertNotifications;
import org.fleetopgroup.persistence.serviceImpl.IBatteryService;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryStockTypeDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryUpholsteryTransferService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionApprovalService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionReceivelService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionService;
import org.fleetopgroup.persistence.serviceImpl.IUreaInvoiceToDetailsService;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequisitionReceiveService implements IRequisitionReceivelService {
		@Autowired 	IRequisitionApprovalService 	    approvalService;
		@Autowired 	SubRequisitionRepository  			subRequisitionRepository;
		@Autowired 	IRequisitionService 				requisitionService;
		@Autowired 	ApprovalRequisitionRepository  		approvalRequisitionRepo;
		@Autowired 	TransferRequisitionRepositiory 		transferRequisitionRepo;
		@Autowired 	IInventoryService 					inventoryService;
		@Autowired 	BatteryRepository 					batteryRepository;
		@Autowired 	BatteryTransferRepository 			batteryTransferRepository;
		@Autowired 	IBatteryService 					batteryService;
		@Autowired 	InventoryTyreTransferRepository 	inventoryTyreTransferRepo;
		@Autowired 	IInventoryTyreService 				tyreService;
		@Autowired 	InventoryTyreRepository				tyreRepository;
		@Autowired 	IInventoryUpholsteryTransferService upholsteryTransferService;
		@Autowired 	UreaInvoiceToDetailsRepository		ureaInvoiceToDetailsRepository;
		@Autowired 	IUreaInvoiceToDetailsService		ureaInvoiceToDetailsService;
		@Autowired 	InventoryRepository 				inventoryRepository;
		@Autowired 	ClothInventoryStockTypeDetailsRepository	clothInventoryStockRepository;
		@Autowired 	IClothInventoryStockTypeDetailsService	clothInventoryStockDetailsService;
		@Autowired 	IClothInventoryStockTypeDetailsService	clothInventoryStockTypeDetailsService;
		@Autowired 	IPurchaseOrdersSequenceService 		iPurchaseOrdersSequenceService;
		@Autowired 	PurchaseOrdersRepository 			purchaseOrdersRepo;
		@Autowired 	MobileAppUserRegistrationRepository			mobileAppUserRegistrationRepository;
		@Autowired 	MobileNotificationRepository				mobileNotificationRepository;
		@Autowired 	UserAlertNotificationsRepository	userAlertNotificationsRepository;
		@Autowired 	PartiallyReceivedDetailsRepository  partiallyReceivedDetailsRepo;
		@Autowired 	RequisitionBL 						requisitionBL;
		@Autowired TransferedInventoryDetailsRepository inventoryDetailsRepository;
		
		
		
		ClothInventoryBL clothInventoryBL = new ClothInventoryBL();
		
		ExecutorService es = null;
		
		String success="success";
		String companyId="companyId";
		String userId="userId";
		String requisitionIdStr = "requisitionId";
		String approvalIdStr	= "approvalId";
		
	
		@Override
		@Transactional
		public ValueObject receiveAllPartApprovals(ValueObject object) throws Exception{
		try {
			int authFailedCount=0,successCount=0;
			ValueObject valueOutObject= new ValueObject();
			List<Long> idsList=subRequisitionRepository.getSubRequisitionIdsByStatus(object.getLong(requisitionIdStr),RequisitionConstant.PART_RERUISITION,RequisitionConstant.SUBREQUISITION_APPROVED,object.getInt(companyId));
			System.err.println("idsList "+idsList);
			if(idsList != null && !idsList.isEmpty()) {
				idsList = approvalRequisitionRepo.getApprovalIdsByStatusAndSubReqIds(idsList, Arrays.asList(RequisitionConstant.APPROVAL_STATUS_CREATED,RequisitionConstant.APPROVAL_STATUS_TRANSFER_COMPLETE,RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED,RequisitionConstant.APPROVAL_STATUS_PARTIALLY_TRANSFERED), object.getInt("companyId"));
				System.err.println("idsList2 "+idsList);
				if(idsList != null && !idsList.isEmpty()) {
					for(long id : idsList) {
						object = new ValueObject();
						object.put("fromReceiveAll",true);
						object.put(approvalIdStr,id);
						object =receiveTransferByApprovalId(object);
						if(object.get("authFail")!= null)
							authFailedCount++;
						if(object.get(success)!= null)
							successCount++;
					}
				}
			}
			valueOutObject.put("msg", ""+successCount+" approvals received , unauthorised to receive count - "+authFailedCount+" ");
			return valueOutObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject receiveTransferByApprovalId(ValueObject object) {
		try {
			CustomUserDetails  userDetails = Utility.getObject(null);
			Long approvalId =object.getLong(approvalIdStr,0);
			ApprovedRequisitionDetails approval=approvalService.getApprovalById(approvalId, userDetails.getCompany_id());
			if(approval != null) {
				object.put(companyId,userDetails.getCompany_id());
				object.put(userId,userDetails.getId());
				object.put("validateAssigneeId",approval.getReceiverId());
				object.put("userDetails",userDetails);
				boolean authValidate =validateUserPermission(object);
				if(!authValidate){
					object.put("authFail", true);
					return object;
				}
				List<TransferRequisitionDetails> transferList = null;
				if(approval.getApprovedStatus() == RequisitionConstant.APPROVAL_STATUS_TRANSFER_COMPLETE || approval.getApprovedStatus() == RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED || approval.getApprovedStatus() == RequisitionConstant.APPROVAL_STATUS_PARTIALLY_TRANSFERED) {
					object.put("transferQuantity", approval.getTransferedQuantity());
					object.put("subRequisitionId", approval.getSubRequisitionId());
					transferList =transferRequisitionRepo.gettransferListByApprovalId(approvalId, userDetails.getCompany_id());
					if(transferList != null && !transferList.isEmpty()) {
						if(object.getBoolean("fromReceiveAll",false)) {
							double receiveQty=approval.getTransferedQuantity();
							if(approval.getReceivedQuantity() != null)
								receiveQty=approval.getTransferedQuantity() - approval.getReceivedQuantity();
							object.put("receiveQty",receiveQty);
						}
						short approvalStatus =RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED;
						double totalReceivedQuantity = object.getDouble("receiveQty",0);
//						if(approval.getReceivedQuantity()!= null)
//							totalReceivedQuantity+=approval.getReceivedQuantity();
						if(totalReceivedQuantity == approval.getApprovedQuantity()) {
							approvalStatus =RequisitionConstant.APPROVAL_STATUS_TRANSFER_RECEIVED;
						}
						object.put("approvalStatus",approvalStatus);
						receiveTransfer(object, transferList,approval);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	@Transactional
	public ValueObject receiveTransfer(ValueObject object,List<TransferRequisitionDetails> transferList,ApprovedRequisitionDetails approval) throws Exception {
		short subRequisitionType = transferList.get(0).getRequisitionTypeId();
		double totalPartialQuantity =object.getDouble("receiveQty",0);
		
		boolean validateQtyFail  =validateQuantity(object, subRequisitionType);
		if(validateQtyFail) {
			object.put("qtyFail", true);
			return object;
		}
		if(approval.getReceivedQuantity() != null && approval.getReceivedQuantity() > 0) {
			totalPartialQuantity  +=approval.getReceivedQuantity();
			object.put("receivedQuantity", approval.getReceivedQuantity());
			if(totalPartialQuantity == approval.getApprovedQuantity()) {
				object.put("approvalStatus", RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED_COMPLETE);
			}
		}
		if(totalPartialQuantity>approval.getTransferedQuantity()) {
			object.put("qtyFail", true);
			return object;
		}
		if(subRequisitionType == RequisitionConstant.BATTARY_RERUISITION){
			receiveBattary(object, transferList);
		}else if (subRequisitionType == RequisitionConstant.TYRE_RERUISITION){
			receiveTyre(object, transferList);
		}else if (subRequisitionType == RequisitionConstant.UREA_RERUISITION){
			receiveUrea(object, transferList);
		}else if (subRequisitionType == RequisitionConstant.CLOTH_RERUISITION){
			receiveUpholstory(object, transferList);
		}else if (subRequisitionType == RequisitionConstant.PART_RERUISITION){
			receivePart(object, transferList);
		}
		if(object.getBoolean(success,false)) {
			updateRequisitionStatus(object);
			if(object.getShort("approvalStatus",(short) 0) ==	RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED ||object.getShort("approvalStatus",(short) 0) ==  RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED_COMPLETE){
				PartiallyReceivedDetails partiallyReceivedDetails=requisitionBL.preaprePartialReceive(object);
				partiallyReceivedDetailsRepo.save(partiallyReceivedDetails);
			}
		}
	
		return object;
	}
	
	@Transactional
	public ValueObject receiveBattary(ValueObject object,List<TransferRequisitionDetails> transferList) {
		try {
			es=Executors.newFixedThreadPool(3);
			long location = transferList.get(0).getToLocation();
			String ids =object.getString("ids","");
			if(!ids.equals("")) {
				List<Long> idsList =Stream.of(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
				transferList =transferList.parallelStream().filter(e->idsList.parallelStream().anyMatch(id->id.equals(e.getTransactionId()))).collect(Collectors.toList());
				object.put("transferQuantity", transferList.size());
				getTransactionIds(object,transferList);
				String transferBatteryIds =object.getString("transactionIdString");
				@SuppressWarnings("unchecked")
				List<Long> transferRequistionIds =(List<Long>) object.get("transferRequistionIds");
				es.execute(()-> batteryService.updateMultipleBatteryStatusAndLocation(transferBatteryIds , BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE,object.getInt(companyId,0) ,location));
				es.execute(()-> transferRequisitionRepo.updateTransferListByApprovalIds(transferRequistionIds, RequisitionConstant.APPROVAL_STATUS_TRANSFER_RECEIVED, object.getInt(companyId,0)));
				es.execute(()-> updateApprovalStatusForTyreBattery(object));
				
				es.shutdown();
				es.awaitTermination(18, TimeUnit.SECONDS);
				object.put(success, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			es.shutdownNow();	
		}
		return object;
	}
	@Transactional
	public ValueObject receiveTyre(ValueObject object,List<TransferRequisitionDetails> transferList) {
		try {
			es=Executors.newFixedThreadPool(3);
			long location =transferList.get(0).getToLocation();
			
			String ids =object.getString("ids","");
			if(!ids.equals("")) {
			List<Long> idsList =Stream.of(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
			transferList =transferList.parallelStream().filter(e->idsList.parallelStream().anyMatch(id->id.equals(e.getTransactionId()))).collect(Collectors.toList());
			object.put("transferQuantity", transferList.size());
			getTransactionIds(object,transferList);
			@SuppressWarnings("unchecked")
			List<Long> transferRequistionIds =(List<Long>) object.get("transferRequistionIds");
			String transactionIds = object.getString("transactionIdString","");
			es.execute(()-> tyreService.updateMultipleTyreStatusAndLocation(InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE, transactionIds,object.getInt(companyId,0), location));
			es.execute(()-> transferRequisitionRepo.updateTransferListByApprovalIds(transferRequistionIds, RequisitionConstant.APPROVAL_STATUS_TRANSFER_RECEIVED, object.getInt(companyId,0)));
			es.execute(()-> updateApprovalStatusForTyreBattery(object));
			es.shutdown();
			es.awaitTermination(18, TimeUnit.SECONDS);
			object.put(success, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			es.shutdownNow();
		}
		return object;
	}

	
	@Transactional
	public ValueObject receiveUrea(ValueObject object,List<TransferRequisitionDetails> transferList) {
		Map<Long,List<TransferRequisitionDetails>>transferMap=null;
		try {
			double receiveQty = object.getDouble("receiveQty",0);
			double quantity = 0;
			double transferSize =0;
			double finalReceivedQty=0;
			List<TransferRequisitionDetails> tansferDetailsList = null;
			getTransactionIds(object, transferList);
			@SuppressWarnings("unchecked")
			List<Long> transactionIds = (List<Long>) object.get("transactionIdList");
			transferMap = transferList.parallelStream().collect(Collectors.groupingBy(TransferRequisitionDetails::getTransactionId)) ; 
			List<UreaInvoiceToDetails> ureaList = ureaInvoiceToDetailsRepository.getUreaInvoiceToDetailsList(transactionIds, object.getInt(companyId,0));
			if(ureaList != null && !ureaList.isEmpty()) {
				for(UreaInvoiceToDetails ureaDetails :ureaList) {
					tansferDetailsList=transferMap.get(ureaDetails.getUreaInvoiceToDetailsId());
					if(tansferDetailsList != null && !tansferDetailsList.isEmpty()) {
						for(TransferRequisitionDetails transferDetails:tansferDetailsList) {
							if(transferDetails != null) {
								short status = RequisitionConstant.APPROVAL_STATUS_TRANSFER_RECEIVED; 
								if(receiveQty >0) {
									quantity = transferDetails.getTransferQuantity();
								if(transferDetails.getTransferStatusId() == RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED && transferDetails.getReceivedQuantity() != null && transferDetails.getTransferQuantity() - transferDetails.getReceivedQuantity() >0) {
								quantity = transferDetails.getTransferQuantity() - transferDetails.getReceivedQuantity();
								}
							if(quantity > receiveQty) {
								quantity = receiveQty;
								status =RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED;
							}
							finalReceivedQty+=quantity;
							object.put("quantity", quantity);
							UreaInvoiceToDetails ureaInvoiceToDetails =requisitionBL.calculateAnsPreapreUreaInventory(object, ureaDetails, transferDetails);
							ureaInvoiceToDetails =ureaInvoiceToDetailsRepository.save(ureaInvoiceToDetails);
							 transferSize = quantity;
							if(transferDetails.getTransferStatusId() == RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED && transferDetails.getReceivedQuantity() != null) {
								transferSize+=transferDetails.getReceivedQuantity();
							}
							saveNewCreatedInventoryDetails(object, ureaInvoiceToDetails.getUreaInvoiceToDetailsId(), transferDetails.getTransferRequisitionId());
							transferRequisitionRepo.updateTransferStatusQunatityAndNewInven(status,transferDetails.getTransferRequisitionId(),transferSize);
							
							receiveQty -= quantity;
						}else {
							break;
						}
					}
				}
					}
				}
				if(finalReceivedQty > 0) {
				object.put("finalReceivedQty",finalReceivedQty);
				updateApprovalStatus(object);
				object.put(success, true);
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return object;
	}

	public ValueObject receiveUpholstory(ValueObject object,List<TransferRequisitionDetails> transferList) {
		ClothInventoryStockTypeDetails toClothStockTypeDetails= null;
		try {
			double enteredReceiveQty =object.getDouble("receiveQty",0);
			double quantity=object.getDouble("receiveQty",0);
			double finalReceivedQty =0;
			for(TransferRequisitionDetails transfer:transferList) {
				if(enteredReceiveQty>0) {
					short status = RequisitionConstant.APPROVAL_STATUS_TRANSFER_RECEIVED;
					ClothInventoryStockTypeDetails clothStockTypeDetails =clothInventoryStockRepository.getClothInventoryStockTypeDetails(transfer.getTransactionId());
					if(clothStockTypeDetails != null) {
						toClothStockTypeDetails=clothInventoryStockRepository.validateClothInventoryStockTypeDetails(clothStockTypeDetails.getClothTypesId(),(int)(long)transfer.getToLocation());
						if(transfer.getTransferQuantity()<quantity) {
							quantity =transfer.getTransferQuantity();
							status =RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED;	
						}		

						if (toClothStockTypeDetails != null) {
							clothInventoryStockDetailsService.updateNewStckQty(clothStockTypeDetails.getClothTypesId(), (int)(long)transfer.getToLocation(),quantity);
							clothInventoryStockTypeDetailsService.updateTransferQty(clothStockTypeDetails.getClothTypesId(), (int)(long)transfer.getFromLocation(), quantity);
						} else {
							SubRequisition subRequisition= requisitionService.getSubRequisitionById(object.getLong("subRequisitionId",0), object.getInt(companyId,0)); 
							object.put("receiveStockTypeId", ClothInvoiceStockType.STOCK_TYPE_NEW); 
							object.put("receiveQuantityId", quantity); 
							object.put("receiveClothTypeId", subRequisition.getTransactionId()); 
							object.put("receivetoLocationId", transfer.getToLocation()); 
							clothStockTypeDetails = clothInventoryBL.updateNewAndOldStockQty(object);
							clothInventoryStockRepository.save(clothStockTypeDetails);
						}
						double transferSize = quantity;
						if( transfer.getTransferStatusId() == RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED && transfer.getReceivedQuantity() != null) {
							transferSize+=transfer.getReceivedQuantity();
						}
						transferRequisitionRepo.updateTransferStatusQunatityAndNewInven(status,transfer.getTransferRequisitionId(),transferSize);
						enteredReceiveQty-=quantity;
						finalReceivedQty +=quantity;
					}
				}
			}
			if(finalReceivedQty>0) {
			object.put("finalReceivedQty", finalReceivedQty);
			updateApprovalStatus(object);
			object.put(success, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	@Transactional
	public ValueObject receivePart(ValueObject object,List<TransferRequisitionDetails> transferList) {
		Map<Long,List<TransferRequisitionDetails>>    transferHash	  			= null;
		List<TransferRequisitionDetails>              transferReqList          	= null;
		Inventory                                     ine 						= null;
		double 										  transferSize 				= 0;
		try {
			getTransactionIds(object,transferList);
			double receiveQty= object.getDouble("receiveQty",0);
			double finalReceivedQty=0;
			@SuppressWarnings("unchecked")
			List<Long> inventoryIds = (List<Long>) object.get("transactionIdList");
			List<Inventory> inventoryList=inventoryRepository.getInventoryPartList(inventoryIds, object.getInt(companyId,0));
			transferHash=transferList.parallelStream().collect(Collectors.groupingBy(TransferRequisitionDetails::getTransactionId));
			if(inventoryList != null && !inventoryList.isEmpty()) {
				for(Inventory inventory:inventoryList) {
					transferReqList=transferHash.get(inventory.getInventory_id());
					if(transferReqList!=null && !transferReqList.isEmpty()) {
						for(TransferRequisitionDetails transfer:transferReqList) {
							if(receiveQty > 0) {
							short status = RequisitionConstant.APPROVAL_STATUS_TRANSFER_RECEIVED; 
							double quantity = transfer.getTransferQuantity();
							if(transfer.getTransferStatusId() == RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED && transfer.getReceivedQuantity() != null &&transfer.getTransferQuantity() - transfer.getReceivedQuantity() >0) {
								quantity = transfer.getTransferQuantity() - transfer.getReceivedQuantity();
							}
							if(quantity>receiveQty) {
								quantity =	receiveQty;
								status 	 =  RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED;							
							}
							finalReceivedQty+=quantity;
							object.put("rQuantity", quantity);
						 ine = requisitionBL.preparePart(object, inventory, transfer);
						ine.setTotal(calculateInventoryTotal(inventory.getDiscountTaxTypeId(), quantity, inventory.getUnitprice(), inventory.getDiscount(), inventory.getTax())); 
						ine =inventoryRepository.save(ine);
						 transferSize = quantity;
						if(transfer.getReceivedQuantity() != null) {
							transferSize+=transfer.getReceivedQuantity();
						}
						saveNewCreatedInventoryDetails(object, ine.getInventory_id(), transfer.getTransferRequisitionId());
						transferRequisitionRepo.updateTransferStatusQunatityAndNewInven(status,transfer.getTransferRequisitionId(),transferSize);
						inventoryService.upadateInTransitQntyInInventory(-quantity,inventory.getInventory_id());
						receiveQty-=quantity;
						}
						}
						}else {
							break;
						}
				}
				inventoryService.Subtract_update_InventoryLocation_from_Workorder(inventoryList.get(0).getPartid(),(int)(long)transferList.get(0).getToLocation() , object.getInt(companyId,0));
				inventoryService.Subtract_update_InventoryAll_from_Workorder(inventoryList.get(0).getPartid(), object.getInt(companyId,0));
				inventoryService.upadateInTransitQntyInLoncationInv(inventoryList.get(0).getPartid(),(int)(long)transferList.get(0).getToLocation(),object.getInt(companyId,0));
				if(finalReceivedQty>0) {
				object.put("finalReceivedQty",finalReceivedQty);
				updateApprovalStatus(object);
				object.put(success, true);	
				}
				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	@Transactional
	public void saveNewCreatedInventoryDetails(ValueObject object,long newInventoryId,long transferDetailsId ) {
		TransferedInventoryDetails transferedInventoryDetails=	requisitionBL.prepareTransferedInventoryDetails(object, newInventoryId, transferDetailsId);
		inventoryDetailsRepository.save(transferedInventoryDetails);
	}
	
	
	public void updateApprovalStatus(ValueObject object) {
		try {
			double transferSize = object.getDouble("finalReceivedQty",object.getDouble("receiveQty",0));
			if(object.getBoolean("fromPartial",false) && (object.getShort("approvalStatus",(short)0) == RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED || object.getShort("approvalStatus",(short)0) == RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED_COMPLETE)) {
				transferSize+=object.getDouble("receivedQuantity",0);
				approvalRequisitionRepo.updateApprovalStatusAndReceiveQty(object.getLong(approvalIdStr,0),object.getInt(companyId,0), object.getLong(userId,0),new Date(),object.getShort("approvalStatus",(short) 0), transferSize);
			}else {
				approvalRequisitionRepo.updateApprovalStatusAndReceiveQty(object.getLong(approvalIdStr,0),object.getInt(companyId,0), object.getLong(userId,0),new Date(),object.getShort("approvalStatus",(short)0),transferSize,object.getString("receiveRemark",""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateApprovalStatusForTyreBattery(ValueObject object) {
		try {
			double transferSize = object.getDouble("transferQuantity",0);
			if(object.getBoolean("fromPartial",false) && (object.getShort("approvalStatus",(short)0) == RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED || object.getShort("approvalStatus",(short)0) == RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED_COMPLETE)) {
				transferSize+=object.getDouble("receivedQuantity",0); //already ApprovedQuantity
				approvalRequisitionRepo.updateApprovalStatusAndReceiveQty(object.getLong(approvalIdStr,0),object.getInt(companyId,0), object.getLong(userId,0),new Date(),object.getShort("approvalStatus",(short)0),transferSize);
			}else {
				approvalRequisitionRepo.updateApprovalStatusAndReceiveQty(object.getLong(approvalIdStr,0),object.getInt(companyId,0), object.getLong(userId,0),new Date(),object.getShort("approvalStatus",(short)0),transferSize,object.getString("receiveRemark",""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public ValueObject rejectReceivalApprove(ValueObject object) {
		try {
			CustomUserDetails  userDetails = Utility.getObject(null);
			Long approvalId =object.getLong(approvalIdStr,0);
			object.put(companyId,userDetails.getCompany_id());
			object.put(userId,userDetails.getId());
			ApprovedRequisitionDetails approval=approvalService.getApprovalById(approvalId, userDetails.getCompany_id());
			if(approval != null) {
				object.put("validateAssigneeId",approval.getReceiverId());
				object.put("userDetails",userDetails);
				boolean authValidate =validateUserPermission(object);
				if(!authValidate){
					object.put("authFail", true);
					return object;
				}
				if(approval.getApprovedStatus() == RequisitionConstant.APPROVAL_STATUS_TRANSFER_COMPLETE || approval.getApprovedStatus() == RequisitionConstant.APPROVAL_STATUS_PARTIALLY_TRANSFERED) {
					object.put("subRequisitionId", approval.getSubRequisitionId());
					List<TransferRequisitionDetails> transferList = null;
					transferList =transferRequisitionRepo.gettransferListByApprovalId(object.getLong(approvalIdStr,0), object.getInt(companyId,0));
					rejectApproval(object,transferList);
					if(object.getBoolean(success,false)) {
						approvalService.updateApprovalStatusAndRemark(object.getLong(approvalIdStr,0),object.getInt(companyId,0), object.getLong(userId,0), RequisitionConstant.APPROVAL_STATUS_TRANSFER_RECEIVAL_REJECTED,object.getString("rejectRemark",""));
						 updateRequisitionStatus(object);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	
	@Override
	public void updateRequisitionStatus(ValueObject object) {
		SubRequisition subReq= requisitionService.getSubRequisitionById(object.getLong("subRequisitionId",0) , object.getInt(companyId,0));
		if(subReq != null) {
			List<Short> statusList = Arrays.asList(RequisitionConstant.APPROVAL_STATUS_CREATED,RequisitionConstant.APPROVAL_STATUS_TRANSFER_COMPLETE,RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED,RequisitionConstant.APPROVAL_STATUS_PARTIALLY_TRANSFERED);
			boolean reqComplete= approvalService.validateApprovalsComplete(subReq.getRequisitionId(), object.getInt(companyId,0),statusList);						
			if(!reqComplete) 
			requisitionService.updateRequisitionStatus(subReq.getRequisitionId(),RequisitionConstant.REQUISITION_COMPLETE,object.getLong("userId",0), object.getInt(companyId,0));
		}
	}
	
	public ValueObject rejectApproval(ValueObject object,List<TransferRequisitionDetails> transferList) {
		if(transferList != null && !transferList.isEmpty()) {
			short subRequisitionType = transferList.get(0).getRequisitionTypeId();
			if(subRequisitionType == RequisitionConstant.BATTARY_RERUISITION){
				rejectBattary(object, transferList);
			}else if (subRequisitionType == RequisitionConstant.TYRE_RERUISITION){
				rejectTyre(object, transferList);
			}else if (subRequisitionType == RequisitionConstant.UREA_RERUISITION){
				rejectUrea(object, transferList);
			}else if (subRequisitionType == RequisitionConstant.CLOTH_RERUISITION){
				rejectUpholstory(object, transferList);
			}else if (subRequisitionType == RequisitionConstant.PART_RERUISITION){
				rejectPart(object, transferList);
			}
		}
		return object;
	}
	
	
	
	public ValueObject rejectTyre(ValueObject object,List<TransferRequisitionDetails> transferList) {
		try {
			getTransactionIds(object,transferList);
			@SuppressWarnings("unchecked")
			List<Long> transferRequistionIds =(List<Long>) object.get("transferRequistionIds");
			String transactionIds = object.getString("transactionIdString","");
			es=Executors.newFixedThreadPool(2);
			
			if(!transactionIds.trim().equals(""))
			es.execute(()-> tyreService.updateMultipleTyreStatus(InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE, transactionIds,object.getInt(companyId,0)));
			if(!transferRequistionIds.isEmpty())
			es.execute(()-> transferRequisitionRepo.updateTransferListByApprovalIds(transferRequistionIds, RequisitionConstant.APPROVAL_STATUS_TRANSFER_RECEIVAL_REJECTED, object.getInt(companyId,0)));
			
			es.shutdown();
			es.awaitTermination(18, TimeUnit.SECONDS);
			object.put("returnedQuantity", transferList.size());
			object.put(success, true);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			es.shutdownNow();
		}
		return object;
	}
	
	public ValueObject rejectBattary(ValueObject object,List<TransferRequisitionDetails> transferList) {
		try {
			es=Executors.newFixedThreadPool(2);
			getTransactionIds(object, transferList);
			String transferBatteryIds =object.getString("transactionIdString","");
			@SuppressWarnings("unchecked")
			List<Long> transferRequistionIds =(List<Long>) object.get("transferRequistionIds");
			es.execute(()-> batteryService.updateMultipleBatteryStatus(transferBatteryIds , BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE,object.getInt(companyId,0) ));
			es.execute(()-> transferRequisitionRepo.updateTransferListByApprovalIds(transferRequistionIds, RequisitionConstant.APPROVAL_STATUS_TRANSFER_RECEIVAL_REJECTED, object.getInt(companyId,0)));
			es.shutdown();
			es.awaitTermination(18, TimeUnit.SECONDS);
			object.put("returnedQuantity", transferList.size());
			object.put(success, true);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			es.shutdownNow();	
		}
		return object;
	}
	
	public ValueObject rejectUrea(ValueObject object,List<TransferRequisitionDetails> transferList) {
		try {
			double returnedQuantity = 0;
			for(TransferRequisitionDetails transfer: transferList) {
				double rejectQty = transfer.getTransferQuantity();
				short status =RequisitionConstant.APPROVAL_STATUS_TRANSFER_RECEIVAL_REJECTED;
				if(object.getBoolean("fromMarkAsComplete",false)) {
					if(transfer.getReceivedQuantity() != null)
					rejectQty = transfer.getTransferQuantity()-transfer.getReceivedQuantity();
					status =RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED_CLOSED;
				}
				returnedQuantity+=rejectQty;
				ureaInvoiceToDetailsService.updateUreaTransferQuantity(-rejectQty, transfer.getTransactionId(), object.getInt(userId,0), object.getInt(companyId,0));
				transferRequisitionRepo.updateTransferListByApprovalId(transfer.getTransferRequisitionId(),status,  object.getInt(companyId,0));
			}
			object.put("returnedQuantity", returnedQuantity);
			object.put(success, true);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return object;
	}
	

	public ValueObject rejectUpholstory(ValueObject object,List<TransferRequisitionDetails> transferList) {
		try {
			double returnedQuantity=0;
			for(TransferRequisitionDetails transfer:transferList) {
				double rejectQty = transfer.getTransferQuantity();
				if(object.getBoolean("fromMarkAsComplete",false) && transfer.getReceivedQuantity() != null)
					rejectQty = transfer.getTransferQuantity()-transfer.getReceivedQuantity();

				upholsteryTransferService.addRejectedStockQuantity(rejectQty, object.getInt(companyId,0), transfer.getTransactionId());	
				returnedQuantity+=rejectQty;
				short status =RequisitionConstant.APPROVAL_STATUS_TRANSFER_RECEIVAL_REJECTED;
				if(object.getBoolean("fromMarkAsComplete",false)) 
					status =RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED_CLOSED;
				transferRequisitionRepo.updateTransferListByApprovalId(transfer.getTransferRequisitionId(),status, object.getInt(companyId,0));

			}
			object.put("returnedQuantity", returnedQuantity);	
			object.put(success, true);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
		}
		return object;
	}
	
	
	public ValueObject rejectPart(ValueObject object,List<TransferRequisitionDetails> transferList) {
		try {
			getTransactionIds(object,transferList);
			double returnedQuantity =0;
			@SuppressWarnings("unchecked")
			List<Long> inventoryIds = (List<Long>) object.get("transactionIdList");
			List<Inventory> inventoryList=inventoryRepository.getInventoryPartList(inventoryIds, object.getInt(companyId,0));
			for(TransferRequisitionDetails transfer:transferList) {
				double rejectQty = transfer.getTransferQuantity();
				short status =RequisitionConstant.APPROVAL_STATUS_TRANSFER_RECEIVAL_REJECTED;
				if(object.getBoolean("fromMarkAsComplete",false)) {
					if(transfer.getReceivedQuantity() != null)
					rejectQty = transfer.getTransferQuantity()-transfer.getReceivedQuantity();
					status =RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED_CLOSED;
				}
				returnedQuantity+=rejectQty;
				inventoryService.Subtract_update_Inventory_from_Workorder(-rejectQty,transfer.getTransactionId());
				inventoryService.upadateInTransitQntyInInventory(-rejectQty,transfer.getTransactionId());
				transferRequisitionRepo.updateTransferListByApprovalId(transfer.getTransferRequisitionId(),status, object.getInt(companyId,0));
			}
			if(inventoryList != null && !inventoryList.isEmpty()) {
				inventoryService.Subtract_update_InventoryLocation_from_Workorder(inventoryList.get(0).getPartid(),(int)(long)transferList.get(0).getFromLocation(), object.getInt(companyId,0));
				inventoryService.upadateInTransitQntyInLoncationInv(inventoryList.get(0).getPartid(),(int)(long)transferList.get(0).getFromLocation(),object.getInt(companyId,0));
				inventoryService.Subtract_update_InventoryAll_from_Workorder(inventoryList.get(0).getPartid(), object.getInt(companyId,0));
			}
			object.put("returnedQuantity", returnedQuantity);
			object.put(success, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	

	@Override
	public Double calculateInventoryTotal(short discountTaxTypeId,Double quantity,Double eachCost,double disCa,Double taxCa) {
		Double totalCost =0.0;
		try {
			Double discountCost =null;

			if(discountTaxTypeId == 1) {
				discountCost 	= (quantity * eachCost) - ((quantity * eachCost) * (disCa / 100));
				totalCost 		= Utility.round(((discountCost) + (discountCost * (taxCa / 100))), 2);
			} else {
				discountCost 	= ((quantity * eachCost) - disCa);
				totalCost 		= Utility.round((discountCost + taxCa), 2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalCost;
	}
	
	@Override
	@Transactional
	public ValueObject createPOFromRequisition(ValueObject object) {
		PurchaseOrdersSequeceCounter 	sequeceCounter 		= null;
		CustomUserDetails userDetails= null;
		try {
			userDetails= Utility.getObject(null);
			ApprovedRequisitionDetails approval=approvalService.getApprovalById(object.getLong(approvalIdStr,0), userDetails.getCompany_id());
			
			SubRequisition 			   subRequisition    	=   requisitionService.getSubRequisitionById(approval.getSubRequisitionId(), userDetails.getCompany_id());
			Requisition 	           requisition 	  		=	requisitionService.getRequisitionByRequisitionId(subRequisition.getRequisitionId(), userDetails.getCompany_id());
			if(requisition != null) {
				object.put("type", subRequisition.getRequisitionTypeId());
				PurchaseOrders purchaseOrd=requisitionBL.preparePO(object,requisition);
				sequeceCounter = iPurchaseOrdersSequenceService.findNextPurchaseOrderNumber(userDetails.getCompany_id());
				if (sequeceCounter == null) {
					object.put("sequenceNotDefined", true);
					return object;
				}
				purchaseOrd.setPurchaseorder_Number(sequeceCounter.getNextVal());

				purchaseOrd=purchaseOrdersRepo.save(purchaseOrd);
				object.put(success, true);
				object.put("poId", purchaseOrd.getPurchaseorder_id());
				object.put("subRequisitionId", subRequisition.getSubRequisitionId());
				approvalRequisitionRepo.updateApprovalStatusAndPO(object.getLong(approvalIdStr,0),object.getInt(companyId,0), object.getLong(userId,0),new Date(), RequisitionConstant.APPROVAL_STATUS_PO_CREATED,purchaseOrd.getPurchaseorder_id());
				updateRequisitionStatus(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	@Override
	public ValueObject getTransactionName(Long subRequisitionId,int companyId) {

		ValueObject object = new ValueObject();
		try {
			List<SubRequisitionDto> subRequList =requisitionService.getSubRequisitionDetailsById("R.subRequisitionId = "+subRequisitionId+" AND ", companyId);
			if(subRequList != null && !subRequList.isEmpty()) {
				SubRequisitionDto subreq =subRequList.get(0) ;
				long transactionId =0,modelId=0,sizeId=0;
				String transactionName="",modelName="",sizeName="";
				short subRequisitionType = subreq.getRequisitionType();
				if(subRequisitionType == RequisitionConstant.BATTARY_RERUISITION){
					transactionId=subreq.getBatteryManufacturer();transactionName=subreq.getBatteryManufacturerName();
					modelId=subreq.getBatteryModel();modelName=subreq.getBatteryModelName();
					sizeId =subreq.getBatteryCapacity();sizeName= subreq.getBatteryCapacityName();
				}else if (subRequisitionType == RequisitionConstant.TYRE_RERUISITION){
					transactionId=subreq.getTyreManufacturer();transactionName=subreq.getTyreManufacturerName();
					modelId=subreq.getTyreModel();modelName=subreq.getTyreModelName();
					sizeId =subreq.getTyreSize();sizeName= subreq.getTyreSizeName();
				}else if (subRequisitionType == RequisitionConstant.UREA_RERUISITION){
					transactionId=subreq.getUreaId();transactionName =subreq.getUreaName();
				}else if (subRequisitionType == RequisitionConstant.CLOTH_RERUISITION){
					transactionId=subreq.getClothId();transactionName=subreq.getClothName();
				}else if (subRequisitionType == RequisitionConstant.PART_RERUISITION){
					transactionId=subreq.getPartId();transactionName=subreq.getPartName();
				}
				object.put("transactionId", transactionId);
				object.put("modelId", modelId);
				object.put("sizeId", sizeId);
				object.put("transactionName", transactionName);
				object.put("modelName", modelName);
				object.put("sizeName", sizeName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	} 

	@Override
	public void sendNotification(ValueObject object) {
		StringBuilder     notificationString           =   null;
		try {
			short status =object.getShort("status",(short) 0);
			notificationString = new StringBuilder();
			String notificationStatus = "";
			if(status== RequisitionConstant.REQUISITION_CREATED) {//req assigned
				notificationStatus =NotificationConstant.NOTI_REQUISITION_ASSIGNED ;
			}else if (status == RequisitionConstant.REQUISITION_SEND ) {//approval Send
				notificationStatus =NotificationConstant.NOTI_REQUISITION_TRANSFER ;
			}else if(status == RequisitionConstant.REQUISITION_APPROVED ) { //receval assinged
				notificationStatus =NotificationConstant.NOTI_REQUISITION_RECEIVAL ;
			}
			String tokenForNotification   = mobileAppUserRegistrationRepository.getTokenForNotification(object.getLong("assignedTo"),object.getLong(companyId,0));
		 	if(tokenForNotification!=null && tokenForNotification.length() > 0){
			notificationString.append(" Requisition Number R - "+object.getLong("requisitionNumber",0));
			notificationString.append(" ,"+notificationStatus+" Assigned to you Please review and take action accordingly.");
			UserAlertNotifications	notifications = new UserAlertNotifications();
			notifications.setUserId(object.getLong("assignedTo"));
			notifications.setAlertMsg(notificationString.toString());
			notifications.setCompanyId(object.getInt(companyId,0));
			notifications.setTxnTypeId(UserAlertNOtificationsConstant.ALERT_TYPE_REQUISITION);
			notifications.setTransactionId(object.getLong(requisitionIdStr));
			notifications.setCreatedById(object.getLong(userId));
			notifications.setCreatedOn(new Date());
			notifications.setStatus((short) 1);
			userAlertNotificationsRepository.save(notifications);
			
			NotificationBllImpl.MobileNotification(notificationString.toString(),tokenForNotification, notificationStatus);
			MobileNotifications mobileNotifications = new MobileNotifications();
			mobileNotifications.setCompanyId(object.getLong(companyId,0));
			mobileNotifications.setUserId(object.getLong("assignedTo"));
			mobileNotifications.setNotification(notificationString.toString());
			mobileNotifications.setNotificationModuleIdentifier(NotificationConstant.REQUISITION_NOTIFICATION_IDENTIFIER);
			mobileNotificationRepository.save(mobileNotifications);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public ValueObject getTransactionIds(ValueObject object,List<TransferRequisitionDetails> transferList) throws Exception {
		StringBuilder transactionIds = new StringBuilder();
		List<Long>transactionIdList  = new ArrayList<>();
		List<Long> transferRequistionIds  = new ArrayList<>();
		for(TransferRequisitionDetails dto :transferList) {
			transactionIds.append(dto.getTransactionId()+",");
			transactionIdList.add(dto.getTransactionId());
			transferRequistionIds.add(dto.getTransferRequisitionId());
		}
		object.put("transactionIdString", Utility.removeLastComma(transactionIds.toString()));
		object.put("transactionIdList", transactionIdList);
		object.put("transferRequistionIds", transferRequistionIds);
		return object;
	}
	
	public boolean validateUserPermission(ValueObject object) {
		boolean retrunBoolean = false;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) object.get("userDetails");
			Collection<GrantedAuthority> permission = userDetails.getGrantedAuthoritiesList();
			if(permission.contains(new SimpleGrantedAuthority("ALL_REQUISITION_PERMISSIONS")) || userDetails.getId() == object.getInt("validateAssigneeId",0)){
			 retrunBoolean=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retrunBoolean;
		
	}
	public boolean validateQuantity(ValueObject object,short approvalTye){
		boolean booleanReturn 		= 	false;
		double 	quantity			=	0;
		
		if(approvalTye == RequisitionConstant.UREA_RERUISITION){
			quantity =object.getDouble("receiveQty",0);
		}else{
			quantity =Math.round(object.getDouble("receiveQty",0));
		}
		if(quantity <=0) {
			booleanReturn = true;
		}
		return booleanReturn;
	}
	
	public ValueObject markRequisitionAsComplete(ValueObject object) {
		List<Long> subReqIdList =null;
		try {
			CustomUserDetails userDetails= Utility.getObject(null);
			Requisition requisition= requisitionService.getRequisitionByRequisitionId(object.getLong(requisitionIdStr,0), userDetails.getCompany_id());
			if(requisition != null && requisition.getRequisitionStatus() == RequisitionConstant.REQUISITION_APPROVED) {
				List<Short> statusList = Arrays.asList(RequisitionConstant.APPROVAL_STATUS_CREATED,RequisitionConstant.APPROVAL_STATUS_TRANSFER_COMPLETE,RequisitionConstant.APPROVAL_STATUS_PARTIALLY_TRANSFERED);
				boolean validateFail =approvalService.validateApprovalsComplete(object.getLong(requisitionIdStr,0), userDetails.getCompany_id(),statusList);
				if(validateFail) {
					object.put("inCompleteTask", true);
					return object;
				}
				List<SubRequisition> subReqList=requisitionService.getSubRequisitionListByStatus(requisition.getRequisitionId(), RequisitionConstant.SUBREQUISITION_APPROVED, userDetails.getCompany_id());
				if(subReqList != null && !subReqList.isEmpty()) {
					subReqIdList = subReqList.parallelStream().map(SubRequisition::getSubRequisitionId).collect(Collectors.toList());
					List<ApprovedRequisitionDetails> approvalPendingList =	approvalService.getApprovalListBySubReqIdAndStatus(subReqIdList,RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED, userDetails.getCompany_id());
					if(approvalPendingList != null && !approvalPendingList.isEmpty()) {
						object.put(companyId,userDetails.getCompany_id());
						object.put(userId,userDetails.getId());
						for(ApprovedRequisitionDetails approval:approvalPendingList) {
							if(approval.getTransferedQuantity()>approval.getReceivedQuantity()) {
								object.put("subRequisitionId", approval.getSubRequisitionId());
								object.put("fromMarkAsComplete", true);
								List<TransferRequisitionDetails> transferList = null;
								transferList =transferRequisitionRepo.gettransferListByApprovalId(approval.getApprovedRequisitionId(),userDetails.getCompany_id());
								rejectApproval(object,transferList);
								if(object.getBoolean(success,false))
								approvalService.updateApprovalStatus(approval.getApprovedRequisitionId(), userDetails.getCompany_id(), RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED_CLOSED,object.getDouble("returnedQuantity",0));
							}else if(approval.getTransferedQuantity().equals(approval.getReceivedQuantity()) && approval.getApprovedStatus()== RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED) {
								approvalService.updateApprovalStatus(approval.getApprovedRequisitionId(), userDetails.getCompany_id(),userDetails.getId(), RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED_CLOSED);
								object.put(success, true);
							}
						}
						requisitionService.updateRequisitionStatus(object.getLong(requisitionIdStr,0),RequisitionConstant.REQUISITION_MARKED_COMPLETE, userDetails.getId(), userDetails.getCompany_id());
					}	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  object;
	}
	
	@Transactional
	public ValueObject getTyreBattaryReceiveList(ValueObject object,short type) throws Exception {
		if(type == RequisitionConstant.BATTARY_RERUISITION) {
			object.put("list", batteryRepository.getbatteryListToReceive(object.getLong(approvalIdStr), object.getInt(companyId)));
		}else if(type == RequisitionConstant.TYRE_RERUISITION) {
			object.put("list",tyreRepository.getTyreListToReceive(object.getLong(approvalIdStr), object.getInt(companyId)));
		}
		object.put("type", type);
		return object;
	}
}
