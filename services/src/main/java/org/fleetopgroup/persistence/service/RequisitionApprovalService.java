package org.fleetopgroup.persistence.service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.fleetopgroup.constant.BatteryConstant;
import org.fleetopgroup.constant.RequisitionConstant;
import org.fleetopgroup.persistence.bl.ClothInventoryBL;
import org.fleetopgroup.persistence.bl.InventoryBL;
import org.fleetopgroup.persistence.bl.InventoryTyreInvoiceBL;
import org.fleetopgroup.persistence.bl.RequisitionBL;
import org.fleetopgroup.persistence.bl.UreaInventoryBL;
import org.fleetopgroup.persistence.bl.UreaTransferDetailsBL;
import org.fleetopgroup.persistence.dao.ApprovalRequisitionRepository;
import org.fleetopgroup.persistence.dao.BatteryRepository;
import org.fleetopgroup.persistence.dao.ClothInventoryStockTypeDetailsRepository;
import org.fleetopgroup.persistence.dao.InventoryRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreTransferRepository;
import org.fleetopgroup.persistence.dao.PartInvoiceRepository;
import org.fleetopgroup.persistence.dao.PartiallyReceivedDetailsRepository;
import org.fleetopgroup.persistence.dao.SubRequisitionRepository;
import org.fleetopgroup.persistence.dao.TransferQuantityDetailsRepository;
import org.fleetopgroup.persistence.dao.TransferRequisitionRepositiory;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.RequisitionApprovalDetailsDto;
import org.fleetopgroup.persistence.dto.SubRequisitionDto;
import org.fleetopgroup.persistence.dto.UreaInvoiceToDetailsDto;
import org.fleetopgroup.persistence.model.ApprovedRequisitionDetails;
import org.fleetopgroup.persistence.model.Battery;
import org.fleetopgroup.persistence.model.ClothInventoryStockTypeDetails;
import org.fleetopgroup.persistence.model.Inventory;
import org.fleetopgroup.persistence.model.InventoryTyre;
import org.fleetopgroup.persistence.model.PartiallyReceivedDetails;
import org.fleetopgroup.persistence.model.Requisition;
import org.fleetopgroup.persistence.model.SubRequisition;
import org.fleetopgroup.persistence.model.TransferQuantityDetails;
import org.fleetopgroup.persistence.model.TransferRequisitionDetails;
import org.fleetopgroup.persistence.model.UreaInvoice;
import org.fleetopgroup.persistence.serviceImpl.IBatteryService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryUpholsteryTransferService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionApprovalService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionReceivelService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionService;
import org.fleetopgroup.persistence.serviceImpl.IUreaInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IUreaInvoiceToDetailsService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequisitionApprovalService implements IRequisitionApprovalService{

	@Autowired SubRequisitionRepository  		subRequisitionRepository;
	@Autowired IRequisitionService 				requisitionService;
	@Autowired ApprovalRequisitionRepository  	approvalRequisitionRepo;
	@Autowired TransferRequisitionRepositiory 	transferRequisitionRepo;
	@Autowired IInventoryService 				inventoryService;
	@Autowired BatteryRepository 				batteryRepository;
	@Autowired IBatteryService 					batteryService;
	@Autowired IInventoryTyreService 			inventoryTyreService;
	@Autowired InventoryTyreTransferRepository 	inventoryTyreTransferRepo;
	@Autowired IInventoryTyreService 			tyreService;
	@Autowired IInventoryUpholsteryTransferService upholsteryTransferService;
	@Autowired IUreaInvoiceToDetailsService		ureaInvoiceToDetailsService;
	@Autowired IUreaInventoryService			ureaInventoryService;
	@Autowired InventoryRepository 				inventoryRepository;
	@Autowired PartInvoiceRepository			partInvoiceRepository;
	@Autowired ClothInventoryStockTypeDetailsRepository		clothInventoryStockRepository;
	@Autowired IRequisitionReceivelService 		requisitionReceivelService;
	@Autowired PartiallyReceivedDetailsRepository  partiallyReceivedDetailsRepo;
	@Autowired 	TransferQuantityDetailsRepository  transferQuantityDetailsRepository;

	@Autowired RequisitionBL requisitionBL;

	@PersistenceContext
	EntityManager entityManager;

	ExecutorService es = null;

	InventoryBL 	inventoryBL			= new InventoryBL();


	InventoryTyreInvoiceBL tyreBL = new InventoryTyreInvoiceBL();

	UreaTransferDetailsBL	ureaTransferDetialsBL		= new UreaTransferDetailsBL();

	ClothInventoryBL clothInventoryBL = new ClothInventoryBL();

	UreaInventoryBL ureaInventoryBL = new UreaInventoryBL();

	String transactionId = "transactionId";



	@Override
	@Transactional
	public ValueObject approveSubRequisition(ValueObject object) throws Exception {
		Thread approvalThread = null;
		Thread subRequisitionThread = null;
		ValueObject valueOutObject = new ValueObject();
		try {
			CustomUserDetails userDetails =  Utility.getObject(null);

			SubRequisition sunRequisition=subRequisitionRepository.getSubrequisitionById(object.getLong("subRequisitionId",0), userDetails.getCompany_id());
			if(sunRequisition == null) {
				valueOutObject.put("notFound", true);
				return valueOutObject;
			}
			Requisition requisition = requisitionService.getRequisitionByRequisitionId(sunRequisition.getRequisitionId(), userDetails.getCompany_id());
			if(requisition != null) {
				object.put("userDetails",userDetails);
				object.put("validateAssigneeId",requisition.getAssignTo());
				boolean authValidate =requisitionReceivelService.validateUserPermission(object);
				if(!authValidate){
					object.put("authFail", true);
					return object;
				}
			}
			object.put("requisitionId", sunRequisition.getRequisitionId());
			Runnable approvalRun = () -> prepareApprovalRequisitionList(object,userDetails);
			approvalThread = new Thread(approvalRun);
			approvalThread.start();

			Runnable subRequisitionRun=() -> requisitionService.updateApprovalQunatity(object.getLong("subRequisitionId",0), RequisitionConstant.SUBREQUISITION_APPROVED, userDetails.getId(), object.getDouble("approvedQuantity",0),object.getString("subRemark",""));
			subRequisitionThread = new Thread(subRequisitionRun);	
			subRequisitionThread.start();

			subRequisitionThread.join();
			approvalThread.join();
			valueOutObject.put("saved",true);
			return valueOutObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Transactional
	public void prepareApprovalRequisitionList(ValueObject object,CustomUserDetails userDetails) {

		HashSet<Long> assignedHash = new HashSet<>();
		es = Executors.newFixedThreadPool(2);
		try {
			List<ApprovedRequisitionDetails> approvalList = new ArrayList<>();
			List<ValueObject> dataValueList =JsonConvertor.toValueObjectFromJsonString(object.getString("dataObject"));
			if(dataValueList != null && !dataValueList.isEmpty()) {
				for(ValueObject obj :dataValueList){
					approvalList.add(requisitionBL.prepareapprovalRequistion(obj,userDetails.getCompany_id(),userDetails.getId(),object.getLong("subRequisitionId",0)));
					assignedHash.add(obj.getLong("assignedTo",0));
				}
			}
			es.execute(()->{
				if(!approvalList.isEmpty())
					approvalRequisitionRepo.saveAll(approvalList);
			});
			es.execute(()->{
				if(!assignedHash.isEmpty()) {
					//notification
					ValueObject nottiObject = new ValueObject();
					try {
						object.put("companyId", userDetails.getCompany_id());
						object.put("requisitionId", object.getLong("requisitionId"));
						Requisition req =requisitionService.getRequisitionByRequisitionId(object.getLong("requisitionId"),  userDetails.getCompany_id());
						if(req != null)
							object.put("requisitionNumber", req.getRequisitionNum());
						object.put("status",RequisitionConstant.REQUISITION_SEND);
						object.put("userId",userDetails.getId());
						for(Long assinee:assignedHash) {
							object.put("assignedTo", assinee);
							requisitionReceivelService.sendNotification(nottiObject);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}	
			});
			es.shutdown();
			es.awaitTermination(18, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			es.shutdownNow();	
		}
	}


	public ValueObject addToApproveRequisition(ValueObject object) throws Exception {
		CustomUserDetails userDetails =  Utility.getObject(null);
		ValueObject valueOutObject = new ValueObject();
		Long sunRequisitionId = object.getLong("subRequisitionId",0);
		SubRequisition sunRequisition=subRequisitionRepository.getSubrequisitionById(sunRequisitionId, userDetails.getCompany_id());
		if(sunRequisition == null) {
			valueOutObject.put("notFound", true);
			return valueOutObject;
		}
		Requisition requisition = requisitionService.getRequisitionByRequisitionId(sunRequisition.getRequisitionId(), userDetails.getCompany_id());
		if(requisition != null) {
			object.put("userDetails",userDetails);
			object.put("validateAssigneeId",requisition.getAssignTo());
			boolean authValidate =requisitionReceivelService.validateUserPermission(object);
			if(!authValidate){
				object.put("authFail", true);
				return object;
			}
		}
		ApprovedRequisitionDetails approvedRequisitionDetails= requisitionBL.prepareapprovalRequistion(object, userDetails.getCompany_id(),userDetails.getId(), sunRequisitionId);
		approvalRequisitionRepo.save(approvedRequisitionDetails);
		Double totalQuantity= sunRequisition.getApprovalQuantity()+approvedRequisitionDetails.getApprovedQuantity();
		requisitionService.updateApprovalQunatity(sunRequisitionId, userDetails.getId(), totalQuantity);
		valueOutObject.put("saved", true);

		//notification
		ValueObject nottiObject = new ValueObject();
		object.put("companyId", userDetails.getCompany_id());
		object.put("requisitionId", sunRequisition.getRequisitionId());
		Requisition req =requisitionService.getRequisitionByRequisitionId(sunRequisition.getRequisitionId(),  userDetails.getCompany_id());
		if(req != null)
			object.put("requisitionNumber", req.getRequisitionNum());
		object.put("status",RequisitionConstant.REQUISITION_SEND);
		object.put("userId",userDetails.getId());
		object.put("assignedTo", approvedRequisitionDetails.getAssignedTo());
		requisitionReceivelService.sendNotification(nottiObject);
		return valueOutObject;
	}

	public ValueObject getApprovalDetailsById(ValueObject valueObject) {
		es = Executors.newFixedThreadPool(2);
		try {
			CustomUserDetails userDetails = Utility.getObject(null);
			long sunRequisitionId = valueObject.getLong("subRequisitionId",0);
			es.execute(() ->{
				try {
					String conditionQuery =" R.subRequisitionId ="+sunRequisitionId+" AND ";
					List <SubRequisitionDto>  subRequisitionList =requisitionService.getSubRequisitionDetailsById(conditionQuery, userDetails.getCompany_id());
					if(subRequisitionList != null && !subRequisitionList.isEmpty())
						valueObject.put("SubRequisition", subRequisitionList.get(0));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			es.execute(() -> {
				try {
					String conditionQuery = " AND A.subRequisitionId = "+sunRequisitionId+" AND A.companyId ="+userDetails.getCompany_id()+" ";
					List<RequisitionApprovalDetailsDto> approvalList = getApprovalListBySubReq(conditionQuery);
					valueObject.put("approvalList", approvalList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			es.shutdown();
			es.awaitTermination(18, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			es.shutdownNow();
		}
		return valueObject;
	}

	public List<RequisitionApprovalDetailsDto> getApprovalListBySubReq(String condition) throws Exception {

		TypedQuery<Object[]> query = null;
		List<Object[]> resultList = null;
		List<RequisitionApprovalDetailsDto> approvalList = null;
		RequisitionApprovalDetailsDto approval = null;
		try {
			query = entityManager.createQuery("SELECT A.approvedRequisitionId,A.subRequisitionId,A.approvedTypeId,A.branchId,A.poId,A.approvedQuantity,"
					+ "B.partlocation_name,A.remark,A.assignedTo,U.firstName,U.lastName,A.transferedQuantity,A.approvedStatus,A.receivedQuantity,"
					+ "U2.firstName,U2.lastName,PO.purchaseorder_Number,A.rejectRemark,A.receiverId,A.returnedQuantity,A.finalRemark,A.ureaManufacturerId,UM.manufacturerName "
					+ " FROM ApprovedRequisitionDetails AS A "
					+ " LEFT JOIN User as U on U.id = A.assignedTo "
					+ " LEFT JOIN User as U2 on U2.id = A.receiverId "
					+ " LEFT JOIN PurchaseOrders as PO on PO.purchaseorder_id = A.poId "
					+ " LEFT JOIN PartLocations as B on B.partlocation_id = A.branchId "
					+ " LEFT JOIN UreaManufacturer as UM on UM.ureaManufacturerId = A.ureaManufacturerId "
					+ " WHERE A.markForDelete = 0 "+condition+" ",Object[].class); 

			resultList = query.getResultList();
			if(resultList != null && !resultList.isEmpty()) {
				approvalList = new ArrayList<>();
				for(Object[] result : resultList) {
					approval = new RequisitionApprovalDetailsDto();
					approval.setApprovedRequisitionId((Long) result[0]);
					approval.setSubRequisitionId((Long) result[1]);
					approval.setApprovedTypeId((short) result[2]);
					approval.setApprovedTypeName(RequisitionConstant.getTransferType(approval.getApprovedTypeId()));
					approval.setBranchId((Long) result[3]);
					approval.setPoId((Long) result[4]);
					approval.setApprovedQuantity((Integer) result[5]);
					approval.setBranchName((String) result[6]);
					approval.setRemark((String) result[7]);
					approval.setAssignToId((Long) result[8]);
					
					if(result[9] != null)
						approval.setUserName(result[9]+" "+result[10]);
					
					approval.setTransferedQuantity((Integer) result[11]);
					
					if(result[12] != null)
						approval.setApprovedStatus((short) result[12]);
					
					approval.setReceivedQuantity((Integer) result[13]);
					
					if(result[14] != null)
						approval.setReceiverName(result[14]+" "+result[15]);
					
					approval.setPoNumber((Long) result[16]);
					approval.setRejectRemark((String) result[17]);
					approval.setReceiverId((Long) result[18]);
					approval.setReturnedQuantity((Integer) result[19]);
					
					if(result[20] != null)
						approval.setFinalRemark((String) result[20]);
					
					if(result[21] != null)
						approval.setManufacturerId((Long) result[21]);
					if(result[22] != null)
						approval.setManufacturerName((String) result[22]);
					approvalList.add(approval);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return approvalList;
	}

	@Override
	@Transactional
	public ValueObject deletedRequisitionApprovalById(ValueObject object){
		try {
			ApprovedRequisitionDetails approval =getApprovalById(object.getLong("approvalId", 0), object.getInt("companyId",0));
			CustomUserDetails userDetails = Utility.getObject(null);
			if(approval != null) {
				approvalRequisitionRepo.deletedApprovalById(object.getLong("approvalId", 0),object.getInt("companyId",0),userDetails.getId(),new Date());
				calculateAndUpdateSubReqApprovalQty(approval.getSubRequisitionId(), object.getInt("companyId",0));
				List<ApprovedRequisitionDetails> approvalList=	approvalRequisitionRepo.getApprovalListBySubRequisition(approval.getSubRequisitionId(), object.getInt("companyId",0));
				if(approvalList == null || approvalList.isEmpty()) {
					requisitionService.updateSubRequisitionStatus(approval.getSubRequisitionId(),(short)0, userDetails.getId(),object.getInt("companyId",0));
				}
			}
			object.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}


	@Transactional
	public ValueObject saveTransferQuantity(ValueObject object) {
		Double transferedQty =0.0;
		try {
			CustomUserDetails  userDetails = Utility.getObject(null);
			object.put("companyId", userDetails.getCompany_id());
			object.put("userId",userDetails.getId());
			object.put("userDetails",userDetails);
			Long approvalId =object.getLong("approvalId",0);
			ApprovedRequisitionDetails approval=getApprovalById(approvalId, userDetails.getCompany_id());
			if(approval != null) {
				object.put("location", approval.getBranchId());
				object.put("subRequisitionId", approval.getSubRequisitionId());
				object.put("validateAssigneeId", approval.getAssignedTo());
				boolean authValidate =requisitionReceivelService.validateUserPermission(object);
				if(!authValidate){
					object.put("authFail", true);
					return object;
				}
				validateBeforeTransfer(object);
				if(!object.getBoolean("validation",true)) {
					object.put("stockValidateFail", true);
					return object;
				}
				transferedQty = object.getDouble("transferQty",0);
				if(approval.getTransferedQuantity() != null)
					transferedQty += approval.getTransferedQuantity();


				if(approval.getApprovedQuantity() < transferedQty) {
					object.put("qntyValidateFail", true);
					return object;
				}

				SubRequisition 			   subRequisition    	=   requisitionService.getSubRequisitionById(approval.getSubRequisitionId(), userDetails.getCompany_id());
				Requisition 	           requisition 	  		=	requisitionService.getRequisitionByRequisitionId(subRequisition.getRequisitionId(), userDetails.getCompany_id());
				object.put("toLocationId", requisition.getLocation());
				object.put("fromLocationId", approval.getBranchId());
				object.put("subRequisition",subRequisition);
				object.put("requisition",requisition);
				object.put("approvalId",approval.getApprovedRequisitionId());
				object.put("requisitionType", subRequisition.getRequisitionTypeId());

				if(subRequisition.getRequisitionTypeId() == RequisitionConstant.BATTARY_RERUISITION) {
					updateBratteryToTransit(object);
				}else if (subRequisition.getRequisitionTypeId() == RequisitionConstant.TYRE_RERUISITION) {
					updateTyreToTransit(object);
				}else if (subRequisition.getRequisitionTypeId() == RequisitionConstant.UREA_RERUISITION) {
					ureaToTransit(object);
				}else if (subRequisition.getRequisitionTypeId() == RequisitionConstant.CLOTH_RERUISITION ) {
					updateClothToInTrasit(object);
				}else if (subRequisition.getRequisitionTypeId() == RequisitionConstant.PART_RERUISITION) {
					updateInventoryPart(object);
				}
		
				calculateAndUpdateTransferQty(approvalId,userDetails.getCompany_id());
				
				if(object.getBoolean("success",false)) {
					short appStatus = RequisitionConstant.APPROVAL_STATUS_TRANSFER_COMPLETE;
					
					if(approval.getApprovedQuantity() > object.getDouble("transferQty",0) && approval.getApprovedStatus() != RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED)
						appStatus = RequisitionConstant.APPROVAL_STATUS_PARTIALLY_TRANSFERED;
					else if (approval.getApprovedStatus() == RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED)
						appStatus = RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED;
					
					updateApprovalStatusAndReceiver(approvalId, userDetails.getCompany_id(), userDetails.getId(),appStatus,object.getLong("receiver",0));
					
					if(appStatus ==	RequisitionConstant.APPROVAL_STATUS_PARTIALLY_TRANSFERED || appStatus ==  RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED_COMPLETE) {
						TransferQuantityDetails transferQuantityDetails = requisitionBL.preaprePartialTransfer(object);
						transferQuantityDetailsRepository.save(transferQuantityDetails);
					}
					
					object.put("completeTransfer", true);
					//notification
					ValueObject nottiObject = new ValueObject();
					object.put("companyId", userDetails.getCompany_id());
					object.put("requisitionId", requisition.getRequisitionId());
					object.put("requisitionNumber", requisition.getRequisitionNum());
					object.put("status",RequisitionConstant.REQUISITION_APPROVED);
					object.put("userId",userDetails.getId());
					object.put("assignedTo", object.getLong("receiver",0));
					requisitionReceivelService.sendNotification(nottiObject);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	@Transactional
	public ValueObject rejectApproval(ValueObject object) {
		try {
			CustomUserDetails userDetails = Utility.getObject(null);
			ApprovedRequisitionDetails approval =getApprovalById(object.getLong("approvalId",0), userDetails.getCompany_id());
			object.put("userDetails",userDetails);
			if(approval != null) {
				object.put("validateAssigneeId",approval.getAssignedTo());
				boolean authValidate =requisitionReceivelService.validateUserPermission(object);
				if(!authValidate){
					object.put("authFail", true);
					return object;
				}
				object.put("subRequisitionId",approval.getSubRequisitionId()); 
				object.put("companyId",userDetails.getCompany_id()); 
				object.put("userId",userDetails.getId()); 
				updateApprovalStatusAndRemark(object.getLong("approvalId",0),userDetails.getCompany_id(), userDetails.getId(), RequisitionConstant.APPROVAL_STATUS_TRANSFER_REJECTED,object.getString("rejectRemark",""));
				requisitionReceivelService.updateRequisitionStatus(object);
				object.put("success", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	public ValueObject validateBeforeTransfer(ValueObject valueObject) {
		try {
			valueObject.put("fromValidateStock", true);
			requisitionService.getLocationWiseTransactionStock(valueObject);
			if( valueObject.getDouble("stockQty",0) <= 0 || valueObject.getDouble("stockQty",0) < valueObject.getDouble("transferQty",0)) {
				valueObject.put("validation", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueObject;
	}

	@Transactional
	public ValueObject updateBratteryToTransit(ValueObject object) {
		try {
			CustomUserDetails 		   userDetails 			= (CustomUserDetails) object.get("userDetails");
			long 					   transitQuantity      =  object.getLong("transferQty",0);
			if(transitQuantity <= 0) {
				object.put("invalidQty", true);
				return object;
			}
			StringBuilder batteryIds = new StringBuilder();
			List<TransferRequisitionDetails> requisitionTransferList = new ArrayList<>();
			String stringIds =object.getString("batteryId","");
			if(!stringIds.equals("")) {

				List<Long> idsList = Stream.of(stringIds.split(",")).map(Long::valueOf).collect(Collectors.toList()) ;
				List<Battery> batteryList=	batteryService.getbatteryListByIds(userDetails.getCompany_id(), BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE, object.getInt("fromLocationId",0),idsList);
				if(batteryList != null && !batteryList .isEmpty()) {
					TransferRequisitionDetails requisitionTransfer = null;
					for(Battery battary:batteryList){
						object.put(transactionId, battary.getBatteryId());
						object.put("quantity", 1);
						requisitionTransfer =requisitionBL.prepareTransferReq(object);
						requisitionTransferList.add(requisitionTransfer);
						batteryIds.append(battary.getBatteryId()+",");
					}
					Runnable transferRun = ()->transferRequisitionRepo.saveAll(requisitionTransferList);
					Thread transferThread = new Thread(transferRun);
					transferThread.start();

					Runnable batteryRun= () ->{
						try {
							batteryService.updateMultipleBatteryStatus(Utility.removeLastComma(batteryIds.toString()), BatteryConstant.BATTERY_ASIGNED_STATUS_IN_TRANSIT,  userDetails.getCompany_id());
						} catch (Exception e) {
							e.printStackTrace();
						}
					};
					Thread batteryThread = new Thread(batteryRun);
					batteryThread.start();

					transferThread.join();
					batteryThread.join();
					object.put("success", true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Transactional
	public ValueObject updateTyreToTransit(ValueObject object) {
		List<InventoryTyre> tyreList = null;

		try {
			long 					   transitQuantity 		=  object.getLong("transferQty",0);
			StringBuilder 			   tyreIds 				= new StringBuilder();
			if(transitQuantity <= 0) {
				object.put("invalidQty", true);
				return object;
			}
			object.put("location",object.getLong("fromLocationId",0));
			String stringList =object.getString("tyreId","");
			if(!stringList.equals("")) {
				List<Long> idsList =Stream.of(stringList.split(",")).map(Long::valueOf).collect(Collectors.toList());
				tyreList =inventoryTyreService.getTyreListByIds(idsList,object);
				if(tyreList != null && !tyreList.isEmpty()) {
					TransferRequisitionDetails requisitionTransfer = null;
					List<TransferRequisitionDetails> requisitionTransferList = new ArrayList<>();
					for(InventoryTyre tyre:tyreList) {
						object.put(transactionId,tyre.getTYRE_ID());
						object.put("quantity", 1);
						requisitionTransfer =requisitionBL.prepareTransferReq(object);
						requisitionTransferList.add(requisitionTransfer);
						tyreIds.append(tyre.getTYRE_ID()+",");
					}

					Runnable transferRun 	= ()->transferRequisitionRepo.saveAll(requisitionTransferList);
					Thread transferThread 	= new Thread(transferRun);
					transferThread.start();

					Runnable tyreRun=()->{
						try {
							tyreService.updateMultipleTyreStatus(InventoryTyreDto.TYRE_ASSIGN_STATUS_INTRANSIT, Utility.removeLastComma(tyreIds.toString()),object.getInt("companyId",0));
						} catch (Exception e) {
							e.printStackTrace();
						}
					};
					Thread tyreThread = new Thread(tyreRun);
					tyreThread.start();

					transferThread.join();
					tyreThread.join();
					object.put("success", true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Transactional
	public ValueObject ureaToTransit(ValueObject object) throws Exception {
		CustomUserDetails 		   userDetails    		= (CustomUserDetails) object.get("userDetails");
		Double 					   transitQuantity 		=  object.getDouble("transferQty",0);
		String   				   query 				=  "";
		if(transitQuantity <= 0) {
			object.put("invalidQty", true);
			return object;
		}
		query =" AND CD.wareHouseLocation ="+object.getLong("fromLocationId",0)+" AND CD.manufacturerId = "+object.getLong("tUreaManufacturer",0)+" ";
		List<UreaInvoiceToDetailsDto> ureaInvoiceToDetailsList	= 	ureaInvoiceToDetailsService.getLocationUreaStockDetailsByLocationId(query,userDetails.getCompany_id());
		if(ureaInvoiceToDetailsList != null && !ureaInvoiceToDetailsList.isEmpty()) {
			for(UreaInvoiceToDetailsDto dto:ureaInvoiceToDetailsList) {
				if(dto.getStockQuantity() >= transitQuantity) {
					object.put("ureaInventoryTransferQuantity",transitQuantity);
					object.put("invoiceId",dto.getUreaInvoiceId());
					updateUreaInvoice(object,dto);
					break;
				}else {
					transitQuantity =(transitQuantity-dto.getStockQuantity());
					object.put("ureaInventoryTransferQuantity",dto.getStockQuantity());
					object.put("invoiceId",dto.getUreaInvoiceId());
					updateUreaInvoice(object,dto);
				}
			}
			// updateureaManufacturerId(object.getLong("approvalId",0),object.getLong("tUreaManufacturer",0));
			object.put("success", true);
		}
		return object;
	}

	@Transactional
	public void updateUreaInvoice(ValueObject object,UreaInvoiceToDetailsDto ureaInvoiceToDetails) {
		UreaInvoice						ureaInvoice							= null	;
		double							updatedTotalTransferQunatity		= 0.0;
		double							ureaTransferQuantity				= 0;
		TransferRequisitionDetails 		requisitionTransfer 				= null;
		try {
			object.put(transactionId,ureaInvoiceToDetails.getUreaInvoiceToDetailsId());
			object.put("quantity", object.getDouble("ureaInventoryTransferQuantity",0));
			requisitionTransfer 		= requisitionBL.prepareTransferReq(object);
			transferRequisitionRepo.save(requisitionTransfer);
			ureaInvoiceToDetailsService.updateUreaTransferQuantity(object.getDouble("ureaInventoryTransferQuantity",0), ureaInvoiceToDetails.getUreaInvoiceToDetailsId(), object.getInt("userId",0), object.getInt("companyId",0));
			ureaInvoice 					= ureaInventoryService.getUreaInvoiceByInvoiceId(object.getLong("invoiceId",0), object.getInt("companyId",0));

			if(ureaInvoice != null) {
				if(ureaInvoice.getTotalTransferQuantity() != null) 
					ureaTransferQuantity = ureaInvoice.getTotalTransferQuantity();

				updatedTotalTransferQunatity 	= (ureaTransferQuantity+object.getDouble("ureaInventoryTransferQuantity"));
				ureaInventoryService.updateTotalTransferQuantity(updatedTotalTransferQunatity,object.getLong("invoiceId"),ureaInvoice.getWareHouseLocation());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public ValueObject updateClothToInTrasit(ValueObject object) {
		TransferRequisitionDetails 		requisitionTransfer 	= null;
		try {
			SubRequisition             subRequisition	 	= (SubRequisition) object.get("subRequisition");
			long 					   transitQuantity      =  object.getLong("transferQty",0);
			if(transitQuantity <= 0) {
				object.put("invalidQty", true);
				return object;
			}
			ClothInventoryStockTypeDetails clothStock=clothInventoryStockRepository.validateClothInventoryStockTypeDetails(subRequisition.getTransactionId(),object.getInt("fromLocationId",0));
			if(clothStock != null && clothStock.getNewStockQuantity() >= (double)transitQuantity) {
				object.put(transactionId,clothStock.getClothInventoryStockTypeDetailsId());
				object.put("quantity", transitQuantity);
				requisitionTransfer 		= requisitionBL.prepareTransferReq(object);
				transferRequisitionRepo.save(requisitionTransfer);
				upholsteryTransferService.updateStockQuantity(object.getDouble("quantity",0), object.getInt("fromLocationId",0), object.getInt("companyId",0),subRequisition.getTransactionId());
				object.put("success", true);
			}else {
				object.put("stockValidateFail", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;

	}

	@Transactional
	public ValueObject updateInventoryPart(ValueObject object) {
		try {

			SubRequisition             subRequisition	 		=  (SubRequisition) object.get("subRequisition");
			long 					   transitQuantityval 		=  object.getLong("transferQty",0);
			double 					   transitQuantity 			=  object.getDouble("transferQty",0);

			if(transitQuantityval <= 0) {
				object.put("invalidQty", true);
				return object;
			}

			List<Inventory> inventoryList=inventoryRepository.getInventoryPartQuantityLocation(subRequisition.getTransactionId(),object.getInt("fromLocationId",0), object.getInt("companyId",0));
			double quantity =0;
			if(inventoryList != null && !inventoryList.isEmpty()) {
				for(Inventory inventory:inventoryList) {
					if(transitQuantity <= inventory.getQuantity()) {
						quantity =transitQuantity;
						object.put("quantity",quantity);
						updatePartInventory(inventory, object);
						break;
					}else {
						transitQuantity =(transitQuantity - inventory.getQuantity());
						quantity =inventory.getQuantity();
						object.put("quantity",quantity);
						updatePartInventory(inventory, object);
					}
				}
				inventoryService.Subtract_update_InventoryAll_from_Workorder(subRequisition.getTransactionId(), object.getInt("companyId"));
				object.put("success", true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Transactional
	public void updatePartInventory(Inventory inventory,ValueObject object) {
		TransferRequisitionDetails 		requisitionTransfer 	= null;
		try {

			object.put(transactionId,inventory.getInventory_id());
			requisitionTransfer 		= requisitionBL.prepareTransferReq(object);
			transferRequisitionRepo.save(requisitionTransfer);

			inventoryService.Subtract_update_Inventory_from_Workorder(object.getDouble("quantity",0),inventory.getInventory_id());
			inventoryService.upadateInTransitQntyInInventory(object.getDouble("quantity",0),inventory.getInventory_id());

			inventoryService.Subtract_update_InventoryLocation_from_Workorder(inventory.getPartid(),inventory.getLocationId(), object.getInt("companyId"));
			inventoryService.upadateInTransitQntyInLoncationInv(inventory.getPartid(), inventory.getLocationId(),object.getInt("companyId"));

			inventoryService.Subtract_update_InventoryAll_from_Workorder(inventory.getPartid(), object.getInt("companyId"));
			// so inventory can not be removed from invoice after transfer
			partInvoiceRepository.update_anyPartNumberAsign_InPartInvoice(inventory.getPartInvoiceId(),object.getInt("companyId"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	@Transactional
	public void calculateAndUpdateSubReqApprovalQty(Long subRequisitionId,int companyId)  {
		entityManager.createQuery( "UPDATE SubRequisition AS SR SET SR.approvalQuantity=(SELECT COALESCE(SUM(A.approvedQuantity),0)FROM ApprovedRequisitionDetails AS A WHERE A.subRequisitionId = "+subRequisitionId+" AND A.approvedQuantity > 0 AND A.companyId = "+companyId+" AND A.markForDelete = 0) "
				+ "WHERE SR.subRequisitionId ="+subRequisitionId+" AND SR.companyId = "+companyId+" AND SR.markForDelete =0").executeUpdate();
	}
	@Transactional
	public void calculateAndUpdateTransferQty(Long approvalId,int companyId)  {
		entityManager.createQuery( "UPDATE ApprovedRequisitionDetails AS AR SET AR.transferedQuantity=(SELECT COALESCE(SUM(TR.transferQuantity),0)FROM TransferRequisitionDetails AS TR WHERE TR.approvedRequisitionId = "+approvalId+" AND TR.transferQuantity > 0 AND TR.companyId = "+companyId+" AND TR.markForDelete = 0) "
				+ "WHERE AR.approvedRequisitionId ="+approvalId+" AND AR.companyId = "+companyId+" AND AR.markForDelete =0").executeUpdate();
	}

	@Override
	@Transactional(readOnly=true)
	public  ApprovedRequisitionDetails getApprovalById (long approvedRequisitionId,int companyId) {
		return approvalRequisitionRepo.getApprovalById( approvedRequisitionId , companyId );
	}

	@Override
	@Transactional
	public  List<ApprovedRequisitionDetails> getApprovalListBySubReqIdAndStatus (List<Long> subRequisitionIdList,short status,int companyId) throws Exception {
		return approvalRequisitionRepo.getApprovalListByStatus(subRequisitionIdList,status, companyId );
	}


	@Override
	public  void updateApprovalStatusAndRemark (long approvedRequisitionId,int companyId,Long lastModifiedById,Short approvalStatus,String remark) {
		try {
			approvalRequisitionRepo.updateApprovalStatusAndRemark(approvedRequisitionId, companyId, lastModifiedById, new Date(), approvalStatus,remark);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public  void updateApprovalStatus(long approvedRequisitionId,int companyId,Short approvalStatus,double returnedQuantity) {
		try {
			approvalRequisitionRepo.updateApprovalStatus(approvedRequisitionId, companyId, approvalStatus,returnedQuantity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public  void updateApprovalStatus(long approvedRequisitionId,int companyId,Long lastModifiedById,Short approvalStatus) {
		try {
			approvalRequisitionRepo.updateApprovalStatus(approvedRequisitionId, companyId, lastModifiedById, new Date(), approvalStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public  void updateApprovalStatusAndReceiver (long approvedRequisitionId,int companyId,Long lastModifiedById,Short approvalStatus,long receiverId) {
		approvalRequisitionRepo.updateApprovalStatusAndReceiver(approvedRequisitionId, companyId, lastModifiedById, new Date(), approvalStatus,receiverId);
	}

	//	@Transactional
	//	public  void updateureaManufacturerId (long approvedRequisitionId,long manufacturer) {
	//		approvalRequisitionRepo.updateureaManufacturerId(approvedRequisitionId, manufacturer);
	//	}

	@Transactional
	public TransferRequisitionDetails saveTransferRequisition (TransferRequisitionDetails transfer) {
		return transferRequisitionRepo.save(transfer);
	}

	@Override
	@Transactional
	public List<PartiallyReceivedDetails> getPartiallyReceivedDetails(long approvedId,int companyId){
		return partiallyReceivedDetailsRepo.getPartialReceiveData(approvedId, companyId);
	}

	@Override
	@Transactional
	public boolean validateApprovalsComplete(Long requisitionId,int companyId,List<Short>statusList){
		List<Object> incompleteApprovalList =null;
		boolean validateFail = false;
		try {
			incompleteApprovalList =approvalRequisitionRepo.validateApprovalMarkToComplete(requisitionId, companyId,statusList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(incompleteApprovalList != null && !incompleteApprovalList.isEmpty()) {
			validateFail=true;
		}
		return validateFail;		
	}

	public List<RequisitionApprovalDetailsDto> getApprovalListWithSubReqDetails(String condition) throws Exception {

		TypedQuery<Object[]> query = null;
		List<Object[]> resultList = null;
		List<RequisitionApprovalDetailsDto> approvalList = null;
		RequisitionApprovalDetailsDto approval = null;
		try {
			query = entityManager.createQuery("SELECT A.approvedRequisitionId,A.subRequisitionId,A.approvedTypeId,A.branchId,A.poId,A.approvedQuantity,"
					+ "B.partlocation_name,A.remark,A.assignedTo,U.firstName,U.lastName,tqd.transferedQuantity,A.approvedStatus,A.receivedQuantity,"
					+ "U2.firstName,U2.lastName,PO.purchaseorder_Number,A.rejectRemark,A.receiverId,A.returnedQuantity,A.finalRemark,A.ureaManufacturerId,UM.manufacturerName,MP.partname,PMU.pmuName,MP.partnumber,PM.pmName,tqd.creationOn"
					+ " FROM ApprovedRequisitionDetails AS A "
					+ " INNER JOIN SubRequisition as SR on SR.subRequisitionId = A.subRequisitionId "
					+ " LEFT join MasterParts as MP on MP.partid = SR.transactionId "
					+ " LEFT join PartManufacturer as PM on PM.pmid = MP.makerId  "
					+ " LEFT join PartMeasurementUnit PMU ON PMU.pmuid = MP.unitTypeId " 
					+ " LEFT JOIN User as U on U.id = A.assignedTo "
					+ " LEFT JOIN User as U2 on U2.id = A.receiverId "
					+ " LEFT JOIN PurchaseOrders as PO on PO.purchaseorder_id = A.poId "
					+ " LEFT JOIN PartLocations as B on B.partlocation_id = A.branchId "
					+ " LEFT JOIN UreaManufacturer as UM on UM.ureaManufacturerId = A.ureaManufacturerId "
					+ " LEFT JOIN TransferQuantityDetails as tqd on tqd.approvedId = A.approvedRequisitionId "
					+ " WHERE A.markForDelete = 0 "+condition+" ",Object[].class); 

			resultList = query.getResultList();
			if(resultList != null && !resultList.isEmpty()) {
				approvalList = new ArrayList<>();
				for(Object[] result : resultList) {
					approval = new RequisitionApprovalDetailsDto();
					approval.setApprovedRequisitionId((Long) result[0]);
					approval.setSubRequisitionId((Long) result[1]);
					approval.setApprovedTypeId((short) result[2]);
					approval.setApprovedTypeName(RequisitionConstant.getTransferType(approval.getApprovedTypeId()));
					approval.setBranchId((Long) result[3]);
					approval.setPoId((Long) result[4]);
					approval.setApprovedQuantity((Integer) result[5]);
					approval.setBranchName((String) result[6]);
					approval.setRemark((String) result[7]);
					approval.setAssignToId((Long) result[8]);
					
					if(result[9] != null)
						approval.setUserName(result[9]+" "+result[10]);
				
					approval.setTransferedQuantity((Integer) result[11]);
					
					if(result[12] != null)
						approval.setApprovedStatus((short) result[12]);
					
					approval.setReceivedQuantity((Integer) result[13]);

					if(result[14] != null)
						approval.setReceiverName(result[14]+" "+result[15]);
					
					approval.setPoNumber((Long) result[16]);
					approval.setRejectRemark((String) result[17]);
					approval.setReceiverId((Long) result[18]);
					approval.setReturnedQuantity((Integer) result[19]);
					
					if(result[20] != null)
						approval.setFinalRemark((String) result[20]);
					
					if(result[21] != null)
						approval.setManufacturerId((Long) result[21]);
					
					if(result[22] != null)
						approval.setManufacturerName((String) result[22]);
					
					approval.setPartName((String) result[23]);
					approval.setPmuName((String) result[24]);
					approval.setPartNumber((String) result[25]);
					
					approval.setPmName((String) result[26]);
					
					if(result[27] != null) {
						approval.setCreationOn(Date.from(((Timestamp) result[27]).toInstant()));
						approval.setTransferedDateStr(DateTimeUtility.getDateFromTimeStampWithAMPM((Timestamp) result[27]));
					}
					
					approvalList.add(approval);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return approvalList;
	}


}
