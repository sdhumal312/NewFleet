package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.RequisitionConstant;
import org.fleetopgroup.persistence.bl.RequisitionBL;
import org.fleetopgroup.persistence.dao.RequisitionRepository;
import org.fleetopgroup.persistence.dao.SubRequisitionRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.RequisitionApprovalDetailsDto;
import org.fleetopgroup.persistence.dto.RequisitionDto;
import org.fleetopgroup.persistence.dto.SubRequisitionDto;
import org.fleetopgroup.persistence.dto.UreaInvoiceToDetailsDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.Requisition;
import org.fleetopgroup.persistence.model.RequisitionSequenceCounter;
import org.fleetopgroup.persistence.model.SubRequisition;
import org.fleetopgroup.persistence.serviceImpl.IBatteryService;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryStockTypeDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionApprovalService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionReceivelService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionsequenceService;
import org.fleetopgroup.persistence.serviceImpl.IUreaInvoiceToDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequisitionService implements IRequisitionService {
	
	
	@Autowired RequisitionRepository requisitionRepository;
	
	@Autowired SubRequisitionRepository  requisitionTypeRepository;
	
	@Autowired IRequisitionsequenceService requisitionsequenceService;
	
	@Autowired IInventoryService  inventoryService;
	
	@Autowired IClothInventoryStockTypeDetailsService clothStockDetailsService;

	@Autowired IInventoryTyreService inventoryTyreService;

	@Autowired IBatteryService batteryService;
	
	@Autowired IUreaInvoiceToDetailsService ureaInvoiceToDetailsService;
	
	@Autowired IRequisitionApprovalService requisitionApprovalService;
	
	@Autowired IRequisitionReceivelService requisitionReceivelService;
	
	@Autowired RequisitionBL requisitionBL;
	
	@Autowired IUserProfileService userProfileService;
	
	@PersistenceContext
	EntityManager entitymanager;
	
	private static final int PAGE_SIZE = 15;
	
	String companyId ="companyId";
	
	
	SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
	
	@Override
	@Transactional
	public ValueObject prepareRequisitionToSave(ValueObject valueObject) throws Exception {
		ValueObject valueOuObject = new ValueObject();
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<SubRequisition> reqTypeList = null;
		RequisitionSequenceCounter counter= null;
		try {
			Requisition reqToSave = requisitionBL.prepareRequition(valueObject, userDetails);
			counter = requisitionsequenceService.findNextNumber(userDetails.getCompany_id());
			if(counter != null) {
				reqToSave.setRequisitionNum(counter.getNextVal());
			}else {
				valueOuObject.put("sequenceCounterNotFound", true);
				return valueOuObject;
			}
			
			Requisition savedReq  = saveOnlyRequisition(reqToSave);
			if(savedReq != null) {
				valueOuObject.put("requisitionId", savedReq.getRequisitionId());
				reqTypeList =prepareObjectListforReq(valueObject, userDetails, savedReq.getRequisitionId());
				
				if(reqTypeList != null && !reqTypeList.isEmpty())
					saveAllRequisitionType(reqTypeList);
				
				
				//notification
				ValueObject object = new ValueObject();
				object.put("assignedTo", savedReq.getAssignTo());
				object.put(companyId, savedReq.getCompanyId());
				object.put("requisitionId", savedReq.getRequisitionId());
				object.put("requisitionNumber", savedReq.getRequisitionNum());
				object.put("status",RequisitionConstant.REQUISITION_CREATED);
				object.put("userId",userDetails.getId());
				requisitionReceivelService.sendNotification(object);
				
			
				
				valueOuObject.put("saved", true);
			}
			return valueOuObject;
		} catch (Exception e) {
			valueOuObject.put("saveFailed", true);
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<SubRequisition> prepareObjectListforReq(ValueObject valueObject,CustomUserDetails userDetails ,long requisitionId){

		List<SubRequisition> reqTypeList = null;
		try {
			List<ValueObject> finalReqTypeList = new ArrayList<>();
			List<ValueObject> list = JsonConvertor.toValueObjectFromJsonString(valueObject.getString("partList"));
			if(list != null && !list.isEmpty()) {
				finalReqTypeList.addAll(list);
			}
			list 		= 	JsonConvertor.toValueObjectFromJsonString(valueObject.getString("tyreList"));
			if(list != null && !list.isEmpty()) {
				finalReqTypeList.addAll(list);
			}
			 list 	=	JsonConvertor.toValueObjectFromJsonString(valueObject.getString("battaryList"));
			if(list != null && !list.isEmpty()) {
				finalReqTypeList.addAll(list);
			}
			list	=	JsonConvertor.toValueObjectFromJsonString(valueObject.getString("clothList"));
			if(list != null && !list.isEmpty()) {
				finalReqTypeList.addAll(list);
			}
			 list 		=	JsonConvertor.toValueObjectFromJsonString(valueObject.getString("ureaList"));
			if(list != null && !list.isEmpty()) {
				finalReqTypeList.addAll(list);
			}
			if(!finalReqTypeList.isEmpty()) {
			 reqTypeList = new ArrayList<>();
				for(ValueObject object : finalReqTypeList) {
					reqTypeList.add(requisitionBL.prepareSubRequiSition(object, requisitionId, userDetails));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reqTypeList;
	}
	
	
	@Transactional
	public Requisition saveOnlyRequisition(Requisition reqToSave) {
		return requisitionRepository.save(reqToSave);
	}
	
	@Transactional
	public void saveAllRequisitionType(List<SubRequisition> typeList) {
		requisitionTypeRepository.saveAll(typeList);
	}
	
	@Transactional
	public ValueObject getRequisitionList(ValueObject valueObject) {
		CustomUserDetails userDetails = null;
		ExecutorService es = Executors.newFixedThreadPool(2);
		try {
			userDetails = Utility.getObject(null);
			valueObject.put("userId",userDetails.getId());
			es.execute(()->getRequisitionCount(valueObject));
			es.execute(()->getPageWiseList(valueObject));
			es.shutdown();
			es.awaitTermination(18, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			es.shutdownNow();
		}
		return valueObject;
	}
	
	@Transactional
	public ValueObject getPageWiseList(ValueObject valueObject) {
		String queryStr ="";
		List<RequisitionDto> requisitionList  = null;
		try {
			short status =valueObject.getShort("status",(short) 0);
			Page<Requisition> page = getDeploymentPageRequisition(status, valueObject.getInt("pagenumber",0), valueObject.getInt(companyId,0),valueObject.getInt("userId",0));
			if(page != null) {
				int current = page.getNumber()+1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());
				valueObject.put("deploymentLog", page);
				valueObject.put("beginIndex", begin);
				valueObject.put("endIndex", end);
				valueObject.put("currentIndex", current);
			}
			if(valueObject.getShort("status",(short) 0) > 0) {
				queryStr="   R.requisitionStatusId ="+status+" AND ";
			}
			valueObject.put("statusId", status);
			valueObject.put("currentIndex", valueObject.getInt("pagenumber"));
			if(status == RequisitionConstant.LIST_REQUISITION_YOURTRANSFER || status == RequisitionConstant.LIST_REQUISITION_YOURRECEIVAL) {
				queryStr=" R.requisitionStatusId ="+RequisitionConstant.REQUISITION_APPROVED +" AND ";
				if(status == RequisitionConstant.LIST_REQUISITION_YOURTRANSFER)
					queryStr +=" A.approvedQuantity > A.transferedQuantity AND A.assignedTo="+valueObject.getInt("userId",0)+" AND A.markForDelete = 0 AND ";
				if(status == RequisitionConstant.LIST_REQUISITION_YOURRECEIVAL)
					queryStr +=" A.approvedStatus IN("+RequisitionConstant.APPROVAL_STATUS_TRANSFER_COMPLETE+","+RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED+") AND A.receiverId="+valueObject.getInt("userId",0)+" AND A.markForDelete = 0 AND ";

				String innerJoin =" Inner join SubRequisition as S on S.requisitionId = R.requisitionId "
						+ " Inner join ApprovedRequisitionDetails as A on A.subRequisitionId = S.subRequisitionId ";
				valueObject.put("innerJoin", innerJoin);
				requisitionList = getRequisitionById(valueObject,queryStr, valueObject.getInt(companyId,0));
				if(requisitionList != null) {
					requisitionList =requisitionList.stream().distinct().collect(Collectors.toList());
				}
			}else if (status == RequisitionConstant.LIST_REQUISITION_REQUISITION_ASSIGN_TO_YOU){
				queryStr=" R.assignTo ="+valueObject.getInt("userId",0)+" AND R.requisitionStatusId ="+RequisitionConstant.REQUISITION_CREATED +" AND ";
				requisitionList = getRequisitionById(valueObject,queryStr, valueObject.getInt(companyId,0));
			}else if(status == RequisitionConstant.LIST_REQUISITION_COMPLETE) {
				queryStr="  R.requisitionStatusId IN("+RequisitionConstant.REQUISITION_COMPLETE+","+RequisitionConstant.REQUISITION_MARKED_COMPLETE+") AND ";
				requisitionList = getRequisitionById(valueObject,queryStr, valueObject.getInt(companyId,0));
			}else {
				requisitionList = getRequisitionById(valueObject,queryStr, valueObject.getInt(companyId,0));
			}
			valueObject.put("requisitionList", requisitionList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueObject;
	}
	
	
	@Transactional
	public Page<Requisition> getDeploymentPageRequisition(short statusId, Integer pageNumber, Integer companyId,long userId){
		try {
			Pageable pageable =PageRequest.of(pageNumber - 1, PAGE_SIZE);
			if(statusId == RequisitionConstant.LIST_REQUISITION_ALL) {
				return requisitionRepository.getDeployablePage(companyId, pageable);
			}else if(statusId == RequisitionConstant.LIST_REQUISITION_YOURTRANSFER ){
				return requisitionRepository.getDeployableTransferPage(RequisitionConstant.APPROVAL_STATUS_CREATED,userId,companyId, pageable);
			}else if(statusId == RequisitionConstant.LIST_REQUISITION_YOURRECEIVAL){
				List<Short> statusList=Arrays.asList(RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED,RequisitionConstant.APPROVAL_STATUS_TRANSFER_COMPLETE);
				return requisitionRepository.getDeployableReceivePage(statusList,userId,companyId, pageable);
			}else if(statusId == RequisitionConstant.LIST_REQUISITION_REQUISITION_ASSIGN_TO_YOU){
				return requisitionRepository.getDeployablePageAsPerStatusAndUser(companyId, RequisitionConstant.REQUISITION_CREATED, userId, pageable);
			}else if(statusId ==RequisitionConstant.LIST_REQUISITION_COMPLETE) {
				List<Short> statusList = Arrays.asList(RequisitionConstant.REQUISITION_COMPLETE,RequisitionConstant.REQUISITION_MARKED_COMPLETE);
				return requisitionRepository.getDeployablePageAsPerStatus(companyId,statusList, pageable);
			}else {
				List<Short> statusList = Arrays.asList(statusId);
				return requisitionRepository.getDeployablePageAsPerStatus(companyId,statusList, pageable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ValueObject getRequisitionCount(ValueObject object) {
		Object[] result =null;
		try {
		Query query =entitymanager.createQuery("SELECT count(A.requisitionId)," 
				+ " (SELECT count(R.requisitionId) FROM Requisition AS R WHERE R.companyId ="+object.getInt(companyId,0)+" AND R.requisitionStatusId ="+RequisitionConstant.REQUISITION_CREATED+" AND R.markForDelete = 0)," 
				+ " (SELECT COUNT(RA.requisitionId) FROM Requisition AS RA WHERE RA.companyId ="+object.getInt(companyId,0)+" AND RA.requisitionStatusId="+RequisitionConstant.REQUISITION_CREATED+" AND RA.assignTo="+object.getLong("userId",0)+" AND RA.markForDelete = 0),"  
				+ " (SELECT COUNT(DISTINCT YT.requisitionId) FROM Requisition AS YT Inner join SubRequisition as S on S.requisitionId = YT.requisitionId "
				+ " Inner join ApprovedRequisitionDetails as App on App.subRequisitionId = S.subRequisitionId " 
				+ " WHERE YT.requisitionStatusId ="+RequisitionConstant.REQUISITION_APPROVED +" AND App.approvedQuantity > App.transferedQuantity AND App.assignedTo="+object.getLong("userId",0)+" AND App.companyId ="+object.getInt(companyId,0)+" AND YT.markForDelete=0 AND App.markForDelete = 0), "  
				+ " (SELECT COUNT(DISTINCT YR.requisitionId) FROM Requisition AS YR Inner join SubRequisition as SYR on SYR.requisitionId = YR.requisitionId "
				+ " Inner join ApprovedRequisitionDetails as AYR on AYR.subRequisitionId = SYR.subRequisitionId "
				+ " WHERE YR.requisitionStatusId ="+RequisitionConstant.REQUISITION_APPROVED +" AND AYR.approvedStatus IN ("+RequisitionConstant.APPROVAL_STATUS_PARTIALLY_RECEIVED+","+RequisitionConstant.APPROVAL_STATUS_TRANSFER_COMPLETE+") AND AYR.receiverId="+object.getLong("userId",0)+" AND AYR.companyId ="+object.getInt(companyId,0)+" AND YR.markForDelete=0 AND AYR.markForDelete = 0),"
				+ " (SELECT count(RC.requisitionId) FROM Requisition AS RC WHERE RC.companyId ="+object.getInt(companyId,0)+" AND RC.requisitionStatusId IN("+RequisitionConstant.REQUISITION_COMPLETE+","+RequisitionConstant.REQUISITION_MARKED_COMPLETE+") AND RC.markForDelete = 0),"
				+ " (SELECT count(RA.requisitionId) FROM Requisition AS RA WHERE RA.companyId ="+object.getInt(companyId,0)+" AND RA.markForDelete = 0)"
				+ " FROM Requisition AS A WHERE A.companyId ="+object.getInt(companyId,0)+" AND A.requisitionStatusId ="+RequisitionConstant.REQUISITION_APPROVED+" AND A.markForDelete = 0 "
				+ "");
		result =(Object[]) query.getSingleResult();
		object.put("approvedCount", result[0]);
		object.put("createdCount", result[1]);
		object.put("assignedToYouCount", result[2]);
		object.put("yourPendingTCount", result[3]);
		object.put("yourPendingRCount", result[4]);
		object.put("completeCount", result[5]);
		object.put("allCount", result[6]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public List<RequisitionDto> getRequisitionById(ValueObject valueObject,String queryStr,int companyId){
		TypedQuery<Object[]> query = null;
		List<Object[]> resultList = null;
		List<RequisitionDto> requisitionList = null;
		query  = entitymanager.createQuery("SELECT R.requisitionId,R.requisitionNum,R.requisitionStatusId,PL.partlocation_name, R.requireOn ,R.refNumber,R.remark"
				+ ",u.firstName ,u.lastName ,R.createdById,R.assignTo,R.location  "
				+ "FROM Requisition as R "
				+ "LEFT join User as u on u.id = R.assignTo "
				+ " "+valueObject.getString("innerJoin","")+" "
				+ "Inner join PartLocations as PL on PL.partlocation_id = R.location "
				+ "WHERE "+queryStr+" R.companyId ="+companyId+" AND R.markForDelete = 0 ORDER BY R.requisitionNum DESC  ",Object[].class);
		try {

			if(valueObject.getInt("currentIndex",0) > 0) {
				query.setFirstResult((valueObject.getInt("currentIndex",0) - 1) * PAGE_SIZE);
				query.setMaxResults(PAGE_SIZE);
			}
			resultList=query.getResultList();
			
			if(resultList != null && !resultList.isEmpty()) {
			requisitionList = new ArrayList<>();
			RequisitionDto requisition = null;
			for(Object[] result: resultList) {
				requisition = new RequisitionDto();
				requisition.setRequisitionId((Long) result[0]);
				requisition.setRequisitionNum((Long) result[1]);
				if(result[2] != null) {
					requisition.setRequisitionStatusId((short) result[2]);
					requisition.setRequisitionStatusName(RequisitionConstant.getRequasitionStatus(requisition.getRequisitionStatusId()));
				}
				requisition.setLocationName((String) result[3]);
				if(result[4] != null) {
					requisition.setRequireOn((Date) result[4]);
					requisition.setRequireOnStr(dateFormat.format(requisition.getRequireOn()));
				}
				requisition.setRefNumber((String) result[5]);
				requisition.setRemark((String) result[6]);
				requisition.setAssignToName(result[7] +" "+result[8]);
				requisition.setCreatedById((Long) result[9]);
				requisition.setAssignTo((Long) result[10]);
				requisition.setLocation((Long) result[11]);
				requisitionList.add(requisition);
			} 
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requisitionList;
	}
	
	public ValueObject getSubRequisitionDetails(ValueObject valueObject) throws Exception {
		ExecutorService es = Executors.newFixedThreadPool(2);
		try {
			String queryStr=" R.requisitionId ="+valueObject.getInt("requisitionId", 0)+" AND ";
			List<SubRequisitionDto> subRequisitionList = getSubRequisitionDetailsById(queryStr,valueObject.getInt(companyId, 0));
		
			if(subRequisitionList != null && !subRequisitionList.isEmpty()) {
				valueObject.put("subRequisitionList", subRequisitionList);
				
				es.execute(()-> getAllSubRequisition(valueObject));
				es.execute(()-> getAllApprovalList(valueObject));
				
				es.shutdown();
				es.awaitTermination(18, TimeUnit.SECONDS);
				
				valueObject.put("subRequisitionList", null);
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			es.shutdownNow();
		}
	}
	
	@Transactional
	public ValueObject getSubRequisitionBySubRequisitionId(ValueObject valueObject) throws Exception {
		try {
			String queryStr=" R.subRequisitionId ="+valueObject.getInt("subRequisitionId", 0)+" AND ";
			List<SubRequisitionDto> subRequisitionList = getSubRequisitionDetailsById(queryStr,valueObject.getInt(companyId, 0));
			if(subRequisitionList != null && !subRequisitionList.isEmpty()) {
				valueObject.put("subRequisition", subRequisitionList.get(0));
				if(!valueObject.getBoolean("dontGetStock",false))
					getLocationWiseTransactionStock(valueObject);
				
				short type = subRequisitionList.get(0).getRequisitionType();
				if(valueObject.getBoolean("fromReceive",false) && (type == RequisitionConstant.TYRE_RERUISITION || type == RequisitionConstant.BATTARY_RERUISITION))
					requisitionReceivelService.getTyreBattaryReceiveList(valueObject,type);
				
				if(valueObject.getBoolean("partialBoolean",false)) {
					valueObject.put("partiallyReceivedList", requisitionApprovalService.getPartiallyReceivedDetails(valueObject.getLong("approvalId",0), valueObject.getInt(companyId, 0)));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return valueObject;
	}
	
	@SuppressWarnings("unchecked")
	public ValueObject getAllApprovalList(ValueObject object){
		List<SubRequisitionDto> subRequisitionList= null;
		try {
			subRequisitionList = (List<SubRequisitionDto>) object.get("subRequisitionList");
			StringBuilder subRequisitionIds = new StringBuilder();
			for(SubRequisitionDto sub:subRequisitionList) {
				subRequisitionIds.append(sub.getSubRequisitionId()+",");
			}
			String query = " AND A.companyId ="+object.getInt(companyId, 0)+" AND A.subRequisitionId IN ("+Utility.removeLastComma(subRequisitionIds.toString())+") "; 
			List<RequisitionApprovalDetailsDto> approvalDetailsList =requisitionApprovalService.getApprovalListBySubReq(query);
			if(approvalDetailsList != null && !approvalDetailsList.isEmpty()) {
				Map<Long ,List<RequisitionApprovalDetailsDto>> approvalHashMap = approvalDetailsList.stream().collect(Collectors.groupingBy(RequisitionApprovalDetailsDto :: getSubRequisitionId));	
				object.put("approvalHashMap",approvalHashMap);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	@SuppressWarnings("unchecked")
	public ValueObject getAllSubRequisition(ValueObject object) {
		List<SubRequisitionDto> subRequisitionList= null;
		try {
			subRequisitionList = (List<SubRequisitionDto>) object.get("subRequisitionList");
			if(subRequisitionList != null && !subRequisitionList.isEmpty()) {
				Map<Short,List<SubRequisitionDto>> subRequisitionHashMap = subRequisitionList.stream().collect(Collectors.groupingBy(SubRequisitionDto::getRequisitionType));
				object.put("partList", subRequisitionHashMap.get(RequisitionConstant.PART_RERUISITION));
				object.put("tyreList", subRequisitionHashMap.get(RequisitionConstant.TYRE_RERUISITION));
				object.put("clothList", subRequisitionHashMap.get(RequisitionConstant.CLOTH_RERUISITION));
				object.put("battaryList", subRequisitionHashMap.get(RequisitionConstant.BATTARY_RERUISITION));
				object.put("ureaList", subRequisitionHashMap.get(RequisitionConstant.UREA_RERUISITION));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;	
	}
	@Override
	public List<SubRequisitionDto> getSubRequisitionDetailsById(String queryStr,int companyId){
		TypedQuery<Object[]> 			query 				= null;
		List<Object[]> 					resultList 			= null;
		List<SubRequisitionDto> 		subRequisitionList 	= null;
		try {
		query  = entitymanager.createQuery("SELECT R.subRequisitionId,R.subRequisitionStatusId,R.requisitionTypeId, "
				+ " R.transactionId,MP.partname,R.tyreModel,TM.TYRE_MODEL_SUBTYPE,R.tyreManufacturer,TMF.TYRE_MODEL "
				+ ",R.tyreSize,TS.TYRE_SIZE,R.batteryManufacturer,BM.manufacturerName,R.batteryModel,BT.batteryType"
				+ ",R.batteryCapacity,BS.batteryCapacity,R.transactionId,CT.clothTypeName,R.transactionId,UM.manufacturerName,R.quantity,"
				+ "R.remark,R.approvalQuantity,PMU.pmuName,MP.partnumber "
				+ " FROM SubRequisition as R "
				+ " LEFT join MasterParts as MP on MP.partid = R.transactionId AND R.requisitionTypeId = "+RequisitionConstant .PART_RERUISITION+" "
				+ " LEFT join PartMeasurementUnit PMU ON PMU.pmuid = MP.unitTypeId " 
				+ " LEFT join VehicleTyreModelSubType as TM on TM.TYRE_MST_ID = R.tyreModel " 			 //tyre Model
				+ " LEFT join VehicleTyreModelType as TMF on TMF.TYRE_MT_ID = R.tyreManufacturer "		 // tyre manufacturer
				+ " LEFT join VehicleTyreSize as TS on TS.TS_ID = R.tyreSize "							 // tyre Size
				+ " LEFT join BatteryManufacturer as BM on BM.batteryManufacturerId = R.batteryManufacturer "							 
				+ " LEFT join BatteryType as BT on BT.batteryTypeId = R.batteryModel "							
				+ " LEFT join BatteryCapacity as BS on BS.batteryCapacityId = R.batteryCapacity "							 
				+ " LEFT join ClothTypes as CT on CT.clothTypesId = R.transactionId AND  R.requisitionTypeId = "+RequisitionConstant.CLOTH_RERUISITION+" "							 
				+ " LEFT join UreaManufacturer as UM on UM.ureaManufacturerId = R.transactionId AND R.requisitionTypeId = "+RequisitionConstant.UREA_RERUISITION+" "							 
				+ " WHERE "+queryStr+" R.companyId ="+companyId+" AND R.markForDelete = 0 ORDER BY R.subRequisitionStatusId ASC ",Object[].class);
		resultList=query.getResultList();
		if(resultList != null && !resultList.isEmpty()) {
			subRequisitionList = new ArrayList<>();
			SubRequisitionDto subRequisition = null;
			for(Object[] result: resultList) {
				subRequisition= new SubRequisitionDto();
				subRequisition.setSubRequisitionId((Long) result[0]);
				if(result[1] != null) {
					subRequisition.setSubRequisitionStatus((short) result[1]);
					subRequisition.setSubRequisitionStatusName(RequisitionConstant.getSubRequasitionStatus(subRequisition.getSubRequisitionStatus()));
				}
				subRequisition.setRequisitionType((short) result[2]);
				if(result[3] != null)
				subRequisition.setPartId((Long) result[3]);
				if(result[4] != null)
				subRequisition.setPartName(result[25] +" - "+result[4] );
				if(result[5] != null)
				subRequisition.setTyreModel((Long) result[5]);
				if(result[6] != null)
				subRequisition.setTyreModelName((String) result[6]);
				if(result[7] != null)
				subRequisition.setTyreManufacturer((Long) result[7]);
				if(result[8] != null)
				subRequisition.setTyreManufacturerName((String) result[8]);
				if(result[9] != null)
				subRequisition.setTyreSize((Long) result[9]);
				if(result[10] != null)
				subRequisition.setTyreSizeName((String) result[10]);
				if(result[11] != null)
				subRequisition.setBatteryManufacturer((Long) result[11]);
				if(result[12] != null)
				subRequisition.setBatteryManufacturerName((String) result[12]);
				if(result[13] != null)
				subRequisition.setBatteryModel((Long) result[13]);
				if(result[14] != null)
				subRequisition.setBatteryModelName((String) result[14]);
				if(result[15] != null)
				subRequisition.setBatteryCapacity((Long) result[15]);
				if(result[16] != null)
				subRequisition.setBatteryCapacityName((String) result[16]);
				if(result[17] != null)
				subRequisition.setClothId((Long) result[17]);
				if(result[18] != null)
				subRequisition.setClothName((String) result[18]);
				if(result[19] != null)
				subRequisition.setUreaId((Long) result[19]);
				if(result[20] != null)
				subRequisition.setUreaName((String) result[20]);
				if(result[21] != null)
				subRequisition.setQuantity((Double) result[21]);
				if(result[22] != null)
				subRequisition.setRemark((String) result[22]);
				subRequisition.setApprovedQuantity((Double) result[23]);
				if(result[24] != null)
				subRequisition.setPmuName((String) result[24]);
				subRequisitionList.add(subRequisition);
			} 
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subRequisitionList;
	}
	
	
	@Transactional
	public ValueObject updateSubRequisitionById(ValueObject object) {
		ValueObject valueOutObject = new ValueObject();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			SubRequisition oldSubRequisition = getSubRequisitionById(object.getLong("requisitionEditId",0), userDetails.getCompany_id());
			SubRequisition subRequisition=requisitionBL.prepareSubRequiSition(object, oldSubRequisition.getRequisitionId(), userDetails);
			subRequisition.setCreatedById(oldSubRequisition.getCreatedById());
			subRequisition.setCreationOn(oldSubRequisition.getCreationOn());
			subRequisition.setSubRequisitionId(object.getLong("requisitionEditId",0));
			subRequisition.setLastModifiedById(userDetails.getId());
			subRequisition.setLastUpdatedOn(new Date());
			saveSubRequisition(subRequisition);
			valueOutObject.put("save",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueOutObject;
	}
	
	
	
	@Transactional
	@Override
	public ValueObject rejectSubRequisitionById(ValueObject object) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			SubRequisition subRequisition = getSubRequisitionById(object.getLong("subRequisitionId",0), userDetails.getCompany_id());
			if(subRequisition != null) {
				Requisition requisition = getRequisitionByRequisitionId(subRequisition.getRequisitionId(), userDetails.getCompany_id());
			if(requisition != null) {
				object.put("userDetails",userDetails);
				object.put("validateAssigneeId",requisition.getAssignTo());
				boolean authValidate =requisitionReceivelService.validateUserPermission(object);
				if(!authValidate){
					object.put("authFail", true);
					return object;
				}
			}
			updateSubRequisitionStatus(object.getLong("subRequisitionId",0), RequisitionConstant.SUBREQUISITION_REJECTED, userDetails.getId(), userDetails.getCompany_id(), object.getString("rejectRemark",""));
			object.put("save",true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject getLocationWiseTransactionStock(ValueObject object) {
		List<InventoryDto> 	inventoryDtoList = null;
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			int locationId =object.getInt("location",0);
			String query="";
			object.put(companyId, userDetails.getCompany_id());
			SubRequisition subReq =  getSubRequisitionById(object.getLong("subRequisitionId",0), userDetails.getCompany_id());
			if(subReq != null) {
			Short transactionType =subReq.getRequisitionTypeId();
			if(transactionType == RequisitionConstant.PART_RERUISITION) {
				object.put("stockQty", inventoryService.getLocationWisePartSum(subReq.getTransactionId(),locationId, userDetails.getCompany_id()));
			}else if (transactionType == RequisitionConstant.CLOTH_RERUISITION) {
				query=" AND B.wareHouseLocationId="+locationId+" ";
				object.put("queryStock", query);
				object.put("clothTypesId", subReq.getTransactionId());
				ValueObject temp=clothStockDetailsService.getLocationWiseUpholsteryQuantity(object);
				 	inventoryDtoList  =(List<InventoryDto>) temp.get("locationWisePartQuantity");
				if(inventoryDtoList != null && !inventoryDtoList.isEmpty()) {
					object.put("stockQty",inventoryDtoList.get(0).getQuantity());	
				}
			}else if (transactionType == RequisitionConstant.TYRE_RERUISITION) {
				object.put("model", subReq.getTyreModel());
				inventoryTyreService.getlocationTyreCount(object);
			}else if(transactionType == RequisitionConstant.BATTARY_RERUISITION){
				object.put("capacity", subReq.getBatteryCapacity());
				batteryService.getlocationBatteryCount(object);
			}else if(transactionType == RequisitionConstant.UREA_RERUISITION) {
				if(object.getBoolean("fromValidateStock",false)) {
					query =" AND CD.manufacturerId ="+object.getLong("tUreaManufacturer",0)+" AND CD.wareHouseLocation = "+locationId+" group by CD.wareHouseLocation " ;
				}else {
					query =" AND CD.wareHouseLocation = "+locationId+" group by CD.wareHouseLocation " ;
				}
				List<UreaInvoiceToDetailsDto> ureaList=ureaInvoiceToDetailsService.getAllLocationUreaStockDetails(userDetails.getCompany_id(), query);
				if(ureaList != null && !ureaList.isEmpty()) 
					object.put("stockQty",ureaList.get(0).getStockQuantity());
				
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	@Override
	@Transactional
	public ValueObject finalApproval(ValueObject object) {
		CustomUserDetails userDeatils = null;
		try {
			userDeatils= Utility.getObject(null);
			StringBuilder returnStrB = new StringBuilder();
			String queryStr=" R.requisitionId ="+object.getInt("requisitionId", 0)+" AND R.subRequisitionStatusId NOT IN("+RequisitionConstant.SUBREQUISITION_APPROVED+","+RequisitionConstant.SUBREQUISITION_REJECTED+") AND ";
			List<SubRequisitionDto> subRequisitionList = getSubRequisitionDetailsById(queryStr,userDeatils.getCompany_id());
			if(subRequisitionList != null && !subRequisitionList.isEmpty()) {
				for(SubRequisitionDto dto:subRequisitionList) {
					returnStrB.append(RequisitionConstant.getRequasitionName(dto.getRequisitionType())+","); 
				}
				object.put("inCompleteDetails",Utility.removeLastComma(returnStrB.toString()));
			}else{
				 queryStr=" R.requisitionId ="+object.getInt("requisitionId", 0)+" AND R.subRequisitionStatusId ="+RequisitionConstant.SUBREQUISITION_APPROVED+" AND ";
				 subRequisitionList = getSubRequisitionDetailsById(queryStr,userDeatils.getCompany_id());
				if(subRequisitionList == null || subRequisitionList.isEmpty())
					updateRequisitionStatus(object.getLong("requisitionId",0), RequisitionConstant.REQUISITION_COMPLETE, userDeatils.getId(), userDeatils.getCompany_id());	
				else
					updateRequisitionStatus(object.getLong("requisitionId",0), RequisitionConstant.REQUISITION_APPROVED, userDeatils.getId(), userDeatils.getCompany_id());
				
				object.put("status", true);
				return object;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

 
	
	@Override
	@Transactional
	public ValueObject getStockWiseBranchList(ValueObject object) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SubRequisition  subRequisition= getSubRequisitionById(object.getLong("subRequisitionId",0), userDetails.getCompany_id());
		ValueObject valueOutObject = new ValueObject();
		if(subRequisition != null){
		try {
			short transactionType =subRequisition.getRequisitionTypeId() ;
			object.put(companyId, userDetails.getCompany_id());
			if(transactionType == RequisitionConstant.PART_RERUISITION) {
				object.put("partId", subRequisition.getTransactionId());
				inventoryService.getLocationWisePartQuantity(object);
			}else if (transactionType == RequisitionConstant.CLOTH_RERUISITION) {
				object.put("clothTypesId", subRequisition.getTransactionId());
				clothStockDetailsService.getLocationWiseUpholsteryQuantity(object);
			}else if (transactionType == RequisitionConstant.TYRE_RERUISITION) {
				object.put("typeId",subRequisition.getTyreModel());
				object.put("withoutManufacturerType", true);
				inventoryTyreService.getLocationWiseTyreQuantity(object);
			}else if(transactionType == RequisitionConstant.BATTARY_RERUISITION){
				object.put("sizeId",subRequisition.getBatteryCapacity());
				object.put("withoutManufacturerType", true);
				batteryService.getLocationWiseBatteryQuantity(object);
			}else if(transactionType == RequisitionConstant.UREA_RERUISITION) {
				String query ="";
				if(object.getBoolean("getManufacturerWise",false)) {
					query =	" AND CD.wareHouseLocation = "+object.getLong("transferLocation",0)+"  group by CD.manufacturerId " ;
				}else {
					query =	" group by CD.wareHouseLocation " ;
				}
				List<UreaInvoiceToDetailsDto> ureaList=ureaInvoiceToDetailsService.getAllLocationUreaStockDetails(userDetails.getCompany_id(), query);
				List<InventoryDto> inventoryList = null;
				InventoryDto inventoryDto = null;
				if(ureaList != null && !ureaList.isEmpty()) {
					inventoryList = new ArrayList<>();
					for(UreaInvoiceToDetailsDto urea :ureaList) {
						inventoryDto = new InventoryDto();
						inventoryDto.setLocationId(urea.getWareHouseLocation());
						inventoryDto.setLocation(urea.getLocationName());
						inventoryDto.setPartid(urea.getManufacturerId());
						inventoryDto.setPartname(urea.getManufacturerName());	
						inventoryDto.setQuantity(urea.getStockQuantity());
						inventoryList.add(inventoryDto);
					}
				}
				object.put("locationWisePartQuantity", inventoryList);
			}
			
			if(!object.getBoolean("getSameLocation",false)) {
				Requisition requisition=getRequisitionByRequisitionId(subRequisition.getRequisitionId(), userDetails.getCompany_id());
				if (requisition!= null) {
					@SuppressWarnings("unchecked")
					List<InventoryDto> listInventory =(List<InventoryDto>) object.get("locationWisePartQuantity");
					if(listInventory != null && !listInventory.isEmpty())
						listInventory =listInventory.stream().filter(inventoryDto -> !inventoryDto.getLocationId().equals((int)(long)requisition.getLocation())).collect(Collectors.toList());
					object.put("locationWisePartQuantity", listInventory);
				}
			}
			valueOutObject.put("locationWisePartQuantity", object.get("locationWisePartQuantity"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		return valueOutObject;
	}	
	
	@Transactional
	@Override
	public ValueObject deleteRequisitionById(ValueObject object){
		try {
			CustomUserDetails userDetails = Utility.getObject(null);
			Requisition requisition =getRequisitionByRequisitionId(object.getLong("requisitionId",0), userDetails.getCompany_id());
			if(requisition != null) {
				if(requisition.getRequisitionStatus() == RequisitionConstant.REQUISITION_CREATED) {
					Collection<GrantedAuthority> permission = userDetails.getGrantedAuthoritiesList();	
					if(permission.contains(new SimpleGrantedAuthority("DELETE_REQUISITION")) || permission.contains(new SimpleGrantedAuthority("ALL_REQUISITION_PERMISSIONS"))) {
						deleteRequisition(requisition.getRequisitionId(), userDetails.getId(), userDetails.getCompany_id());
						object.put("success",true);
					}else {
						object.put("authFail",true);
					}
				}else {
					object.put("approvedFail",true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	
	@Override
	@Transactional
	public ValueObject searchRequisitionByNumber(ValueObject object) {
		try {
			Requisition requisition= requisitionRepository.getRequisitionbyNumber(object.getLong("requisitionNumber",0),object.getInt(companyId,0) );
			if(requisition != null) {
				object.put("requisitionId", requisition.getRequisitionId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	@Transactional
	public void updateSubRequisitionStatus(Long subRequisitionId,short subRequisitionStatus,long lastModifiedById,int companyId,String remark ) {
		requisitionTypeRepository.updateSubRequisitionStatus(subRequisitionId, subRequisitionStatus, lastModifiedById, new Date(), companyId,remark);
	}
	
	@Override
	@Transactional
	public void updateSubRequisitionStatus(Long subRequisitionId,short subRequisitionStatus,long lastModifiedById,int companyId) {
		requisitionTypeRepository.updateSubRequisitionStatus(subRequisitionId, subRequisitionStatus, lastModifiedById, new Date(), companyId);
	}
	@Transactional
	public void saveSubRequisition(SubRequisition subtype) {
		requisitionTypeRepository.save(subtype);
	}
	
	@Override
	@Transactional(readOnly =true)
	public SubRequisition getSubRequisitionById(Long subReqId,int companyId) {
		return requisitionTypeRepository.getSubrequisitionById(subReqId, companyId);
	}
	
	@Override
	@Transactional
	public void updateApprovalQunatity(Long subReqId ,short subStatusId,long updatedById,Double quantity,String remark) {
		requisitionTypeRepository.updateSubRequisitionStatusAndQty(subReqId, subStatusId,updatedById,new Date(),quantity, remark);
	}
	
	@Override
	@Transactional
	public void updateApprovalQunatity(Long subReqId ,long updatedById,Double quantity) {
		requisitionTypeRepository.updateSubRequisitionQty(subReqId, updatedById, new Date(), quantity);
	}
	
	@Override
	@Transactional
	public void updateRequisitionStatus(Long requisitionId ,short requisitionStatus,long updatedById,int companyId) {
		requisitionRepository.updateRequisitionStatus(requisitionId, requisitionStatus, updatedById, new Date(), companyId);
	}
	
	@Transactional
	public void deleteRequisition(Long requisitionId ,long updatedById,int companyId) {
		requisitionRepository.deleteRequisitionById(requisitionId, updatedById, new Date(), companyId);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Requisition getRequisitionByRequisitionId(long requisitionId,int companyId) {
		return requisitionRepository.getRequisitionbyId(requisitionId, companyId);
	}
	
	@Transactional
	public List<SubRequisition> getSubRequisitionListByStatus(long requisitionId,short status,int companyId) {
		return requisitionTypeRepository.getSubRequisitionListByStatus(requisitionId, status, companyId);
	}
	
	@Override
	public Map<String,Object> getPartRequsitionPrintData(long requisitionId) {
		Map<String,Object> model = new HashMap<>();
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String queryStr=" R.requisitionId ="+requisitionId+" AND ";
		try {

			List<RequisitionDto> requisitionDetailsList =getRequisitionById(new ValueObject(),queryStr, userDetails.getCompany_id());

			if(requisitionDetailsList != null && !requisitionDetailsList.isEmpty()) {
				RequisitionDto requisitionDto	= requisitionDetailsList.get(0)	;	

				queryStr =" AND SR.requisitionId =" + requisitionId+" AND A.approvedTypeId ="+RequisitionConstant.TRANSFER_BRANCH+" AND SR.requisitionTypeId = "+RequisitionConstant .PART_RERUISITION+" AND A.transferedQuantity > 0 ";
				List<RequisitionApprovalDetailsDto> requisitionApprovalDetailsDtoList;
				requisitionApprovalDetailsDtoList = requisitionApprovalService.getApprovalListWithSubReqDetails(queryStr);


				if(requisitionApprovalDetailsDtoList == null || requisitionApprovalDetailsDtoList.isEmpty()) {
					model.put("isPrintAllow", false);
					return model;
				}

				RequisitionApprovalDetailsDto requisitionApprovalDetailsDto	= requisitionApprovalDetailsDtoList.get(0)	;

				UserProfileDto company = userProfileService.findUserProfileByUser_email(userDetails.getEmail());

				model.put("requisitionDto", requisitionDto);
				model.put("requisitionApprovalDetailsDtoList", requisitionApprovalDetailsDtoList);
				model.put("requisitionApprovalDetailsDto", requisitionApprovalDetailsDto);
				model.put("company", company);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public List<RequisitionDto> getPartRequisitionStatusWiseReport(String queryStr,int companyId){
		
		TypedQuery<Object[]> query = null;
		
		List<Object[]> resultList = null;
		List<RequisitionDto> requisitionList = null;
		Map<String, RequisitionDto>   temHMp 	= null;
		
		query  = entitymanager.createQuery("SELECT R.requisitionId,R.requisitionNum,R.requisitionStatusId,PL.partlocation_name,"
				+ " R.creationOn, u.firstName, u.lastName, R.createdById, SR.requisitionTypeId "
				+ " FROM Requisition as R "
				+ " LEFT JOIN User as u on u.id = R.assignTo "
				+ " LEFT JOIN PartLocations as PL on PL.partlocation_id = R.location  "
				+ " LEFT JOIN SubRequisition as SR on SR.requisitionId = R.requisitionId "
				+ " WHERE  R.companyId ="+ companyId +" " + queryStr + " AND R.markForDelete = 0 ",Object[].class);
		
		try {
			resultList = query.getResultList();
			if(resultList != null && !resultList.isEmpty()) {
				temHMp	= new HashMap<>();
			requisitionList = new ArrayList<>();
			
			RequisitionDto requisition = null;
			for(Object[] result: resultList) {
				
				requisition = new RequisitionDto();
				requisition.setRequisitionId((Long) result[0]);
				requisition.setRequisitionNum((Long) result[1]);
				requisition.setRequisitionNumStr(requisition.getRequisitionNum()+"");
				if(result[2] != null) {
					requisition.setRequisitionStatusId((short) result[2]);
					requisition.setRequisitionStatusName(RequisitionConstant.getRequasitionStatus(requisition.getRequisitionStatusId()));
				}
				requisition.setLocationName((String) result[3]);
				if(result[4] != null) {
					requisition.setCreationOn((Date) result[4]);
					requisition.setCreationOnStr(dateFormat.format(requisition.getCreationOn()));
				}
				requisition.setAssignToName(result[5] +" "+result[6]);
				requisition.setCreatedById((Long) result[7]);
				requisition.setRequisitionType((short) result[8]);
				requisition.setRequisitionTypeName(RequisitionConstant.getRequasitionName(requisition.getRequisitionType()));
				if(temHMp.get(requisition.getRequisitionId()+"") == null)
					requisitionList.add(requisition);
				temHMp.put(requisition.getRequisitionId()+"", requisition);
			} 
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requisitionList;
	}
	
}
