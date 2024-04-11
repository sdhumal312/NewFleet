package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TyreAssignmentConstant;
import org.fleetopgroup.constant.TyreModalConstant;
import org.fleetopgroup.persistence.bl.TyreUsageHistoryBL;
import org.fleetopgroup.persistence.bl.VehicleModelTyreLayoutBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.bl.VehicleTyreAssignmentBL;
import org.fleetopgroup.persistence.dao.VehicleRepository;
import org.fleetopgroup.persistence.dao.VehicleTyreLayoutPositionRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.InventoryTyreHistoryDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleTyreLayoutPositionDto;
import org.fleetopgroup.persistence.dto.VehicleTyreModelSubTypeDto;
import org.fleetopgroup.persistence.model.InventoryTyre;
import org.fleetopgroup.persistence.model.InventoryTyreHistory;
import org.fleetopgroup.persistence.model.Issues;
import org.fleetopgroup.persistence.model.IssuesTasks;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehicleTyreLayoutPosition;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ITyreUsageHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreAssignmentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreLayoutService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreModelSubTypeService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("VehicleTyreAssignmentService")
@Transactional
public class VehicleTyreAssignmentService implements IVehicleTyreAssignmentService {
	@PersistenceContext EntityManager entityManager;
	@Autowired	private VehicleTyreLayoutPositionRepository 		vehicleTyreLayoutPositionRepository;
	@Autowired	private VehicleRepository 		vehicleRepository;
	
	@Autowired private IVehicleTyreLayoutService 		vehicleTyreLayoutService;
	@Autowired private IServiceReminderService 			serviceReminderService;
	@Autowired private IVehicleOdometerHistoryService 	vehicleOdometerHistoryService;
	@Autowired private IInventoryTyreService 			iventoryTyreService;
	@Autowired private IVehicleService 					vehicleService;
	@Autowired private IIssuesService 					issuesService;
	@Autowired private ICompanyConfigurationService 	companyConfigurationService;
	@Autowired private ITyreUsageHistoryService 		tyreUsageHistoryService;
	@Autowired private IVehicleTyreModelSubTypeService 	vehicleTyreModelSubTypeService;
	
	SimpleDateFormat 			dateFormat 				= new SimpleDateFormat("dd-MM-yyyy");
	VehicleOdometerHistoryBL 	VOHBL 					= new VehicleOdometerHistoryBL();
	VehicleTyreAssignmentBL 	assignmentBL 			= new VehicleTyreAssignmentBL();
	VehicleModelTyreLayoutBL	vModelTyreLayoutBL 		= new VehicleModelTyreLayoutBL();
	TyreUsageHistoryBL			tyreUsageHistoryBL 		= new TyreUsageHistoryBL();
	
	@Override
	public ValueObject getVehicleTyreLayoutPosition(ValueObject valueObject) throws Exception {
		try {
			
			valueObject =	getVehicleTyreLayoutPositionByVid(valueObject);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject tyreAssignToVehicle(ValueObject valueObject) throws Exception {
		long 							currentLP_ID 					= 0;	
		long 							currentvid 						= 0;
		String 							currentTyrePosition 			= "";
		ArrayList<ValueObject>			tyreDataArrayObj				= null;
		List<ServiceReminderDto>		serviceList						= null;
		VehicleTyreLayoutPosition	 		vehicleTyreLayoutPositionFrom  		= null;
		VehicleTyreLayoutPosition	 		vehicleTyreLayoutPositionTo  		= null;
		ValueObject							tyreModelFromoObj						= null;
		ValueObject							tyreModelToObj						= null;
		VehicleTyreModelSubTypeDto 			vehicleFromTyreModelSubType			= null;
		VehicleTyreModelSubTypeDto 			vehicleToTyreModelSubType			= null;
		
		try {
			tyreModelFromoObj 	= new ValueObject();
			tyreModelToObj 	= new ValueObject();
			tyreDataArrayObj 	= new ArrayList<>();
			tyreDataArrayObj	= (ArrayList<ValueObject>) valueObject.get("tyreDetails");
			Integer currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(valueObject.getInt("vid"));
			valueObject.put("vehiclePreOdometer", currentODDMETER);
			for (ValueObject object : tyreDataArrayObj) {
				if(valueObject.getShort("assignFromId",(short)0) == TyreAssignmentConstant.ASSIGN_FROM_OTHER_VEHICLE_SPARE) {
					vehicleTyreLayoutPositionFrom =	vehicleTyreLayoutPositionRepository.Get_Position_name_to_GetDetails(valueObject.getInt("assignFromVid",0), "SP-0", valueObject.getInt("companyId"));
					vehicleTyreLayoutPositionTo 	= vehicleTyreLayoutService.Get_Position_name_to_GetDetails(valueObject.getInt("vid"),object.getString("tyrePositionId"), valueObject.getInt("companyId"));
				
					tyreModelFromoObj.put("TYRE_MST_ID", vehicleTyreLayoutPositionFrom.getTyreModelId());
					tyreModelFromoObj.put("companyId", valueObject.getInt("companyId"));
					tyreModelFromoObj =	vehicleTyreModelSubTypeService.getVehicleTyreModelSubTypeDetails(tyreModelFromoObj);
					vehicleFromTyreModelSubType = (VehicleTyreModelSubTypeDto) tyreModelFromoObj.get("tyreModelDetails");
					
					
					
					tyreModelToObj.put("TYRE_MST_ID", vehicleTyreLayoutPositionTo.getTyreModelId());
					tyreModelToObj.put("companyId", valueObject.getInt("companyId"));
					tyreModelToObj =	vehicleTyreModelSubTypeService.getVehicleTyreModelSubTypeDetails(tyreModelToObj);
					vehicleToTyreModelSubType = (VehicleTyreModelSubTypeDto) tyreModelToObj.get("tyreModelDetails");
					
					
					if(vehicleFromTyreModelSubType != null && vehicleToTyreModelSubType != null && !vehicleFromTyreModelSubType.getTyreModelSizeId().equals(vehicleToTyreModelSubType.getTyreModelSizeId())) {
						valueObject.put("differentSize", true);
						return valueObject;
					}
					
				}
				
				/*if(object.getShort("oldTyreMoveId") == TyreAssignmentConstant.OLD_TYRE_MOVED_TO_SPARE) {
					object.put("rotateFromPositionId", object.getString("tyrePositionId") );
					object.put("rotateToPositionId", "SP-0");
					object.put("tyreFromLP_ID", object.getLong("oldTyreLP_ID") );
					object.put("tyreToLP_ID", object.getLong("LP_ID",0));
					rotateTyre(valueObject, object);
					continue;
				}*/
				
				valueObject 		= validateTyreAssignToVehicle(valueObject,object);
				
				currentLP_ID 		= object.getLong("LP_ID",0);
				currentvid 			= valueObject.getLong("vid",0);
				currentTyrePosition	= object.getString("tyrePositionId"); 
				
				if(valueObject.getBoolean("alredyAssigned",false)) {// if already tyre is present this will remove exsiting tyre
					valueObject.put("dismountDate", valueObject.getString("assignDate",""));
					valueObject.put("remark",TyreAssignmentConstant.getOldTyreMovedTo(object.getShort("oldTyreMoveId")) );
					valueObject.put("dismountOdometer",object.getInt("vehicleOdometer",0));
					object.put("tyreId",object.getLong("oldTyreId",0));
					object.put("tyreNumber",object.getLong("oldTyreNumber",0));
					object.put("oldTyreFlag",true);
					tyreRemoveFromVehicle(valueObject,object);
				}
				
				if(valueObject.getShort("assignFromId",(short)0) == TyreAssignmentConstant.ASSIGN_FROM_OTHER_VEHICLE_SPARE) {
					
					valueObject.put("dismountDate", valueObject.getString("assignDate",""));
					valueObject.put("remark", "Used In "+valueObject.getString("vehicleReg")+"");
					valueObject.put("vid",valueObject.getInt("assignFromVid",0));
					valueObject.put("dismountOdometer",valueObject.getInt("vehicleOdometer",0));
					object.put("LP_ID",valueObject.getLong("otherVehicleLP_ID",0));
					object.put("tyreId",object.getLong("newTyreId",0));
					object.put("tyreNumber",object.getLong("newTyreNumber",0));
					object.put("tyrePositionId","SP-0");
					object.put("oldTyreFlag",false);
					tyreRemoveFromVehicle(valueObject,object);// remove SP tyre from other vehicle
				}
					
				valueObject.put("vid",currentvid); 
				object.put("LP_ID",currentLP_ID); // because  for dismounting LP_ID key is same as mount
				object.put("tyrePositionId",currentTyrePosition); 
				
				updateMountTyreHistoryDetails(valueObject, object);
				
				updateTyreInVehicleTyreLayoutPosition(valueObject, object);
				
				resolveIssue(valueObject, object);
				
				updateTyreOdometerHistory(valueObject,object);
				
			//	tyreUsageHistory = tyreUsageHistoryBL.prepareTyreUsageHistoryBL(valueObject, object);
			//	tyreUsageHistoryRepository.save(tyreUsageHistory);
				
				
			}
			if (currentODDMETER < valueObject.getInt("vehicleOdometer",0)) {
				vehicleService.updateCurrentOdoMeter(valueObject.getInt("vid"), valueObject.getInt("vehicleOdometer",0), valueObject.getInt("companyId"));
				serviceReminderService.updateCurrentOdometerToServiceReminder(valueObject.getInt("vid"), valueObject.getInt("vehicleOdometer",0), valueObject.getInt("companyId"));
				serviceList = serviceReminderService.OnlyVehicleServiceReminderList(valueObject.getInt("vid"), valueObject.getInt("vehicleOdometer",0), valueObject.getLong("userId"));
				if(serviceList != null) {
					for(ServiceReminderDto list : serviceList) {
						if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
							serviceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
						}
					}
				}
			}
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	} 
	
	public void updateMountTyreHistoryDetails(ValueObject valueObject, ValueObject object)throws Exception{
		InventoryTyreHistoryDto			inventoryTyreHistoryDto 		= null;
		InventoryTyreHistory 			TyreHistory 					= null;
		CustomUserDetails				userDetails						= null;
		HashMap<String, Object> 		configuration 					= null;

		try {
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 			=  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			TyreHistory = new InventoryTyreHistory();
			TyreHistory = vModelTyreLayoutBL.prepareInventoryTyreHistory(valueObject,object);
			inventoryTyreHistoryDto = iventoryTyreService.getPreTyreMountDismountHistory(object.getLong("newTyreId"),TyreHistory,InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_DISMOUNT);// used to get pre record of this tyre
			
			if(inventoryTyreHistoryDto != null) {
				TyreHistory.setPRE_OPEN_ODOMETER(inventoryTyreHistoryDto.getOPEN_ODOMETER());
				TyreHistory.setPRE_TYRE_ASSIGN_DATE(inventoryTyreHistoryDto.getTYRE_ASSIGN_DATE_TIMESTAMP());
				TyreHistory.setPRE_TYRE_NUMBER(inventoryTyreHistoryDto.getTYRE_NUMBER());
			}
			if(valueObject.getShort("assignFromId",(short)0) == TyreAssignmentConstant.ASSIGN_FROM_OTHER_VEHICLE_SPARE && valueObject.getShort("transactionTypeId",(short)0) == 0 &&  valueObject.getLong("transactionId",0) == 0 ) {
				valueObject = assignmentBL.prepareIssue(valueObject,configuration);
				valueObject.put("LP_ID",object.getLong("LP_ID"));
				valueObject.put("userDetails", userDetails);
				valueObject = 	issuesService.saveIssuesDetails(valueObject);
				TyreHistory.setIssueId(valueObject.getLong("decryptIssueId",0));
			}
			
			iventoryTyreService.add_Inventory_TYRE_History(TyreHistory);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void updateTyreInVehicleTyreLayoutPosition(ValueObject valueObject, ValueObject object)throws Exception{
		//VehicleTyreLayoutPosition 		vehicleTyreLayoutPosition 		= null;
		try {
			java.util.Date date = dateFormat.parse(valueObject.getString("assignDate"));
			java.sql.Date assignDate = new java.sql.Date(date.getTime());
			
			
			iventoryTyreService.update_Assign_TYRE_To_Vehicle_Mount(valueObject.getInt("vid"), valueObject.getInt("vehicleOdometer",0),
					InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE, assignDate, object.getLong("newTyreId"), valueObject.getInt("companyId"),(short)0);
			entityManager.createQuery(
					"Update VehicleTyreLayoutPosition SET TYRE_ID ="+object.getLong("newTyreId",0)+", TYRE_SERIAL_NO ='"+object.getString("newTyreNumber","")+"'  "
							+", alignment ="+object.getShort("alignmentId",(short)0)+", kinpin ="+object.getShort("kinpinId",(short)0)+"  "
							+", oldTyreId ="+object.getLong("oldTyreId",0)+", oldTyreMoveId ="+object.getShort("oldTyreMoveId",(short)0)+"  "
							+", tyreTypeId ="+object.getShort("tyreTypeId",(short)0)+", tyreGauge ="+object.getInt("tyreGauge",0)+", TYRE_ASSIGNED = "+true+"  "
							+", transactionTypeId ="+valueObject.getShort("transactionTypeId",(short)0)+", transactionId ="+valueObject.getLong("transactionId",0)+", transactionSubId = "+valueObject.getLong("transactionSubId",0)+" , tyreModelId = "+object.getInt("tyreModelId",0)+" "
							+ "WHERE LP_ID = " + object.getLong("LP_ID") + "  AND COMPANY_ID = " + valueObject.getInt("companyId")+ " ").executeUpdate();
			
			
		/*	vehicleTyreLayoutPosition = vModelTyreLayoutBL.prepareVehicleTyreLayoutPosition(valueObject,object);
			
			vehicleTyreLayoutPositionRepository.save(vehicleTyreLayoutPosition);*/
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void resolveIssue(ValueObject valueObject, ValueObject object)throws Exception{
		Issues							issue							= null;
		IssuesTasks						issueTask						= null;	
		try {
			if(valueObject.getLong("transactionId",0) > 0) {
				if(valueObject.getShort("transactionTypeId",(short)0) == TyreAssignmentConstant.TRANSACTION_TYPE_ISSUE) {
					issue 		= issuesService.getIssueDetailsByIssueId(valueObject.getLong("transactionId"), valueObject.getInt("companyId"));
					if(issue != null) {
						issueTask 	= assignmentBL.prepareIssuesTaskDetailsForTyreAssignment(issue,valueObject);
						issuesService.registerNew_IssuesTasks(issueTask);
						issuesService.updateTyreAssignmentIssueStatus(IssuesTypeConstant.ISSUES_STATUS_RESOLVED, valueObject.getLong("userId"), issueTask.getISSUES_CREATED_DATE(), object.getLong("LP_ID"), valueObject.getLong("transactionId"),valueObject.getInt("companyId"));
					}
				}
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Override
	public ValueObject validateTyreAssignToVehicle(ValueObject valueObject,ValueObject object) throws Exception {
		try {
			VehicleTyreLayoutPosition vehicleTyreLayoutPosition	= vehicleTyreLayoutPositionRepository.Get_Position_name_to_GetDetails(valueObject.getInt("vid"), object.getString("tyrePositionId"),valueObject.getInt("companyId"));
			if(vehicleTyreLayoutPosition != null && vehicleTyreLayoutPosition.getTYRE_ID() != null) {
				valueObject.put("alredyAssigned", true);
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public ValueObject getVehicleTyreLayoutPositionByVid(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> 				query 							= null;
		List<VehicleTyreLayoutPositionDto> 	vehicleTyreLayoutPositionList 	= null;
		VehicleTyreLayoutPositionDto 		vehicleTyreLayoutPositionDto 	= null;
		try {
			query = entityManager.createQuery(
				"SELECT VTLP.LP_ID, VTLP.AXLE, VTLP.POSITION, VTLP.tyreModelId , TMS.TYRE_MODEL_SUBTYPE, TMS.tyreModelTypeId, "
					+ " TMS.gauageMeasurementLine, TMS.tyreGauge, TMS.tyreTubeTypeId, TMS.ply, TMS.psi,VTS.TS_ID, VTS.TYRE_SIZE, VTLP.TYRE_ID, IT.TYRE_NUMBER "
					+ " FROM VehicleTyreLayoutPosition AS VTLP "
					+ " INNER JOIN VehicleTyreModelSubType TMS ON TMS.TYRE_MST_ID = VTLP.tyreModelId "
					+ " LEFT JOIN VehicleTyreSize VTS ON VTS.TS_ID = TMS.tyreModelSizeId "
					+ " LEFT JOIN InventoryTyre IT ON IT.TYRE_ID = VTLP.TYRE_ID "
					+ " WHERE VTLP.VEHICLE_ID = "+valueObject.getLong("vid",0)+""
					+ " AND VTLP.COMPANY_ID = "+valueObject.getInt("companyId")+" AND VTLP.markForDelete = 0",
					Object[].class);
			

			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				vehicleTyreLayoutPositionList = new ArrayList<VehicleTyreLayoutPositionDto>();
				
				for (Object[] result : results) {
					vehicleTyreLayoutPositionDto = new VehicleTyreLayoutPositionDto();
					vehicleTyreLayoutPositionDto.setLP_ID((Long)result[0]);
					vehicleTyreLayoutPositionDto.setAXLE((Integer)result[1]);
					vehicleTyreLayoutPositionDto.setPOSITION((String)result[2]);
					vehicleTyreLayoutPositionDto.setTyreModelId((Integer)result[3]);
					vehicleTyreLayoutPositionDto.setTyreModel((String)result[4]);
					if(result[5] != null) {
						vehicleTyreLayoutPositionDto.setTyreModelType(TyreModalConstant.getTyreModelType((short)result[5]));
					}
					if(result[6] != null) {
						vehicleTyreLayoutPositionDto.setGauageMeasurementLine((Integer)result[6]);
					}
					if(result[7] != null) {
						vehicleTyreLayoutPositionDto.setTyreGauge((Integer)result[7]);
					}
					if(result[8] != null) {
						vehicleTyreLayoutPositionDto.setTyreTubeType(TyreModalConstant.getTyreTubeType((short)result[8]));
					}
					if(result[9] != null) {
						vehicleTyreLayoutPositionDto.setPly((Integer)result[9]);
					}
					if(result[10] != null) {
						vehicleTyreLayoutPositionDto.setPsi((Integer)result[10]);
					}
					vehicleTyreLayoutPositionDto.setTYRE_SIZE_ID((Integer)result[11]);
					vehicleTyreLayoutPositionDto.setTyreModelSize((String)result[12]);
					if(result[13] != null) {
						vehicleTyreLayoutPositionDto.setTYRE_ID((Long)result[13]);
						vehicleTyreLayoutPositionDto.setTYRE_SERIAL_NO((String)result[14]);
					}else {
						vehicleTyreLayoutPositionDto.setTYRE_SERIAL_NO("");
						
					}
					vehicleTyreLayoutPositionList.add(vehicleTyreLayoutPositionDto);
					
					}
				}
			
			valueObject.put("vehicleTyreLayoutPositionList", vehicleTyreLayoutPositionList);
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getTyreAssignedToVehicleDetails(ValueObject valueObject) throws Exception {
		VehicleTyreLayoutPositionDto vehicleTyreLayoutPositionDto = null;
		try {
			
			Query query = entityManager.createQuery(
					"SELECT VTLP.LP_ID, VTLP.AXLE, VTLP.POSITION, VTLP.TYRE_ID, IT.TYRE_NUMBER, VTLP.tyreTypeId, VTLP.oldTyreId, "
						+ " IT.TYRE_NUMBER, VTLP.oldTyreMoveId,VTLP.alignment,VTLP.kinpin,VTLP.assignFromId, TMS.TYRE_MODEL_SUBTYPE, "
						+ " TMS.tyreModelTypeId, TMS.gauageMeasurementLine, TMS.tyreGauge, TMS.tyreTubeTypeId, TMS.ply, TMS.psi, VTS.TYRE_SIZE, VTLP.tyreModelId, IT.TYRE_ASSIGN_DATE "
						+ " FROM VehicleTyreLayoutPosition AS VTLP "
						+ " INNER JOIN InventoryTyre IT ON IT.TYRE_ID = VTLP.TYRE_ID "
						+ " INNER JOIN VehicleTyreModelSubType TMS ON TMS.TYRE_MST_ID = IT.TYRE_MODEL_ID "
						+ " LEFT JOIN VehicleTyreSize VTS ON VTS.TS_ID = TMS.tyreModelSizeId "
						+ " WHERE VTLP.POSITION = '"+valueObject.getString("position","")+"' AND VTLP.TYRE_ASSIGNED =1 AND VTLP.VEHICLE_ID = "+valueObject.getLong("vid",0)+""
						+ " AND VTLP.COMPANY_ID = "+valueObject.getInt("companyId")+" AND VTLP.markForDelete = 0");
			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			
			vehicleTyreLayoutPositionDto = new VehicleTyreLayoutPositionDto();
			if (result != null) {
				vehicleTyreLayoutPositionDto.setLP_ID((Long)result[0]);
				vehicleTyreLayoutPositionDto.setAXLE((Integer)result[1]);
				vehicleTyreLayoutPositionDto.setPOSITION((String)result[2]);
				vehicleTyreLayoutPositionDto.setTYRE_ID((Long)result[3]);
				vehicleTyreLayoutPositionDto.setTYRE_SERIAL_NO((String)result[4]);
				if(result[5] != null) {
					vehicleTyreLayoutPositionDto.setTyreType(TyreModalConstant.getTyreModelType((short)result[5]));
				}
				vehicleTyreLayoutPositionDto.setOldTyreId((Long)result[6]);
				vehicleTyreLayoutPositionDto.setOldTyre((String)result[7]);
				if(result[8] != null) {
					vehicleTyreLayoutPositionDto.setOldTyreMove(TyreAssignmentConstant.getOldTyreMovedTo((short)result[8]) );
				}
				if(result[9] != null) {
					vehicleTyreLayoutPositionDto.setAlignmentStr(TyreAssignmentConstant.getAlignment((short)result[9]) );
				}
				if(result[10] != null) {
					vehicleTyreLayoutPositionDto.setKinpinStr(TyreAssignmentConstant.getKinpin((short)result[10]) );
				}
				if(result[11] != null) {
					vehicleTyreLayoutPositionDto.setAssignFrom(TyreAssignmentConstant.getTyreAssignFrom((short)result[11]));
				}
				vehicleTyreLayoutPositionDto.setTyreModel((String)result[12]);
				vehicleTyreLayoutPositionDto.setTyreModelType(TyreModalConstant.getTyreModelType((short)result[13]));
				vehicleTyreLayoutPositionDto.setGauageMeasurementLine((Integer)result[14]);
				vehicleTyreLayoutPositionDto.setTyreGauge((Integer)result[15]);
				vehicleTyreLayoutPositionDto.setTyreTubeType(TyreModalConstant.getTyreTubeType((short)result[16]));
				vehicleTyreLayoutPositionDto.setPly((Integer)result[17]);
				vehicleTyreLayoutPositionDto.setPsi((Integer)result[18]);
				vehicleTyreLayoutPositionDto.setTyreModelSize((String)result[19]);
				vehicleTyreLayoutPositionDto.setTyreModelId((Integer)result[20]);
				if(result[21] != null) {
					vehicleTyreLayoutPositionDto.setMountDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[21], DateTimeUtility.DD_MM_YYYY));
				}
				
			} 
			
			
			valueObject.put("vehicleTyreLayoutPosition", vehicleTyreLayoutPositionDto);
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}
	
	@Override
	public ValueObject getTyreDetailsOfPosition(ValueObject valueObject) throws Exception {
		VehicleTyreLayoutPositionDto vehicleTyreLayoutPositionDto = null;
		try {

			Query query = entityManager.createQuery(
					"SELECT VTLP.LP_ID, VTLP.AXLE, VTLP.POSITION, VTLP.TYRE_ID, IT.TYRE_NUMBER, VTLP.tyreTypeId, VTLP.oldTyreId, "
							+ " IT.TYRE_NUMBER, VTLP.oldTyreMoveId,VTLP.alignment,VTLP.kinpin,VTLP.assignFromId, TMS.TYRE_MODEL_SUBTYPE, "
							+ " TMS.tyreModelTypeId, TMS.gauageMeasurementLine, TMS.tyreGauge, TMS.tyreTubeTypeId, TMS.ply, TMS.psi, VTS.TS_ID, VTS.TYRE_SIZE, VTLP.tyreModelId , IT.TYRE_ASSIGN_DATE "
							+ " FROM VehicleTyreLayoutPosition AS VTLP "
							+ " LEFT JOIN InventoryTyre IT ON IT.TYRE_ID = VTLP.TYRE_ID "
							+ " LEFT JOIN VehicleTyreModelSubType TMS ON TMS.TYRE_MST_ID = VTLP.tyreModelId "
							+ " LEFT JOIN VehicleTyreSize VTS ON VTS.TS_ID = TMS.tyreModelSizeId "
							+ " WHERE VTLP.POSITION = '"+valueObject.getString("position")+"'  AND VTLP.VEHICLE_ID = "+valueObject.getLong("vid")+""
							+ " AND VTLP.COMPANY_ID = "+valueObject.getInt("companyId")+" AND VTLP.markForDelete = 0");

			Object[] result = null;
			try {
				query.setMaxResults(1);
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}


			vehicleTyreLayoutPositionDto = new VehicleTyreLayoutPositionDto();
			if (result != null) {
				vehicleTyreLayoutPositionDto.setLP_ID((Long)result[0]);
				vehicleTyreLayoutPositionDto.setAXLE((Integer)result[1]);
				vehicleTyreLayoutPositionDto.setPOSITION((String)result[2]);
				vehicleTyreLayoutPositionDto.setTYRE_ID((Long)result[3]);
				vehicleTyreLayoutPositionDto.setTYRE_SERIAL_NO((String)result[4]);
				vehicleTyreLayoutPositionDto.setTyreType("");
				vehicleTyreLayoutPositionDto.setOldTyreId((Long)result[6]);
				vehicleTyreLayoutPositionDto.setOldTyre((String)result[7]);
				vehicleTyreLayoutPositionDto.setOldTyreMove("");
				vehicleTyreLayoutPositionDto.setAlignmentStr("");
				vehicleTyreLayoutPositionDto.setKinpinStr("");
				vehicleTyreLayoutPositionDto.setAssignFrom("");
				vehicleTyreLayoutPositionDto.setTyreModel((String)result[12]);
				if(result[13] != null) {
					vehicleTyreLayoutPositionDto.setTyreModelType(TyreModalConstant.getTyreModelType((short)result[13]));
				}
				vehicleTyreLayoutPositionDto.setGauageMeasurementLine((Integer)result[14]);
				vehicleTyreLayoutPositionDto.setTyreGauge((Integer)result[15]);
				if(result[16] != null) {
					vehicleTyreLayoutPositionDto.setTyreTubeType(TyreModalConstant.getTyreTubeType((short)result[16]));
				}
				if(result[17] != null) {
					vehicleTyreLayoutPositionDto.setPly((Integer)result[17]);
				}
				if(result[18] != null) {
					vehicleTyreLayoutPositionDto.setPsi((Integer)result[18]);
				}
				if(result[19] != null) {
					vehicleTyreLayoutPositionDto.setTYRE_SIZE_ID((Integer)result[19]);
				}
				if(result[20] != null) {
					vehicleTyreLayoutPositionDto.setTyreModelSize((String)result[20]);
				}
				if(result[21] != null) {
					vehicleTyreLayoutPositionDto.setTyreModelId((Integer)result[21]);
					
				}
				if(result[22] != null) {
					vehicleTyreLayoutPositionDto.setMountDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[22], DateTimeUtility.DD_MM_YYYY));
				}
			} 


			valueObject.put("vehicleTyreLayoutPosition", vehicleTyreLayoutPositionDto);
			return valueObject;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject singleTyreRemoveFromVehicle(ValueObject valueObject,ValueObject object) throws Exception {
		InventoryTyre 					Tyre 							= null;
		try {
			/** Below Search Tyre Inventory Id To Details */
			Tyre 					= iventoryTyreService.Get_TYRE_ID_InventoryTyre(object.getLong("tyreId"));
			
			if(Tyre == null) {
				return valueObject;
			}
			updateDismountTyreHistoryDetails(Tyre,valueObject,object);
			
			updateInventoryTyreUsageAndStatusDetails(Tyre,valueObject,object);

			vehicleTyreLayoutService.update_Position_Assign_TYRE_To_Vehicle(null, null, false, object.getLong("LP_ID"),valueObject.getString("remark",""));
			
			updateSingleDismountTyreOdometerHistory(valueObject,object);

			return valueObject;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	public void updateSingleDismountTyreOdometerHistory(ValueObject valueObject, ValueObject object) throws Exception {
		List<ServiceReminderDto>		serviceList						= null;
		try {
			Integer currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(valueObject.getInt("vid"));
			if (currentODDMETER< valueObject.getInt("dismountOdometer")) {
				vehicleService.updateCurrentOdoMeter(valueObject.getInt("vid"), valueObject.getInt("dismountOdometer",0), valueObject.getInt("companyId"));
				serviceReminderService.updateCurrentOdometerToServiceReminder(valueObject.getInt("vid"), valueObject.getInt("dismountOdometer",0), valueObject.getInt("companyId"));
				serviceList = serviceReminderService.OnlyVehicleServiceReminderList(valueObject.getInt("vid"), valueObject.getInt("dismountOdometer",0), valueObject.getLong("userId"));
				if(serviceList != null) {
					for(ServiceReminderDto list : serviceList) {
						if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
							serviceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
						}
					}
				}
			
				
				VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign(
						valueObject.getInt("vid"), valueObject.getString("vehicleReg"), valueObject.getInt("dismountOdometer",0), object.getLong("tyreId"));
				vehicleUpdateHistory.setCompanyId(valueObject.getInt("companyId"));
				vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
				
				tyreUsageHistoryService.saveTyreDismountUsageHistory(valueObject,object);

			}else if(currentODDMETER== valueObject.getInt("dismountOdometer")) {
				
				VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign(valueObject.getInt("vid"), valueObject.getString("vehicleReg"),  valueObject.getInt("dismountOdometer",0), object.getLong("tyreId"));
				vehicleUpdateHistory.setCompanyId(valueObject.getInt("companyId"));
				vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
	}
	
	
	@Override
	public ValueObject tyreRemoveFromVehicle(ValueObject valueObject,ValueObject object) throws Exception {
		InventoryTyre 					Tyre 							= null;
		try {
			/** Below Search Tyre Inventory Id To Details */
			Tyre 					= iventoryTyreService.Get_TYRE_ID_InventoryTyre(object.getLong("tyreId"));
			if(Tyre == null) {
				return valueObject;
			}
			updateDismountTyreHistoryDetails(Tyre,valueObject,object);
			
			updateInventoryTyreUsageAndStatusDetails(Tyre,valueObject,object);

			vehicleTyreLayoutService.update_Position_Assign_TYRE_To_Vehicle(null, null, false, object.getLong("LP_ID"),valueObject.getString("remark",""));
			
			updateDismountTyreOdometerHistory(valueObject,object);

			return valueObject;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	
	public void updateDismountTyreHistoryDetails(InventoryTyre inventoryTyre , ValueObject valueObject, ValueObject object)throws Exception{
		InventoryTyreHistoryDto			inventoryTyreHistoryDto 		= null;
		InventoryTyreHistory 			TyreHistory 					= null;
		CustomUserDetails				userDetails						= null;
		HashMap<String, Object> 		configuration 					= null;

		try {
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 			=  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			TyreHistory 			= vModelTyreLayoutBL.prepareInventoryTyreDismountHistory(valueObject,inventoryTyre,object);
			inventoryTyreHistoryDto = iventoryTyreService.getPreTyreMountDismountHistory(inventoryTyre.getTYRE_ID(),TyreHistory,InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_MOUNT);

			if(inventoryTyreHistoryDto != null) {
				TyreHistory.setPRE_OPEN_ODOMETER(inventoryTyreHistoryDto.getOPEN_ODOMETER());
				TyreHistory.setPRE_TYRE_ASSIGN_DATE(inventoryTyreHistoryDto.getTYRE_ASSIGN_DATE_TIMESTAMP());
				TyreHistory.setPRE_TYRE_NUMBER(inventoryTyreHistoryDto.getTYRE_NUMBER());
			}
			
			valueObject = assignmentBL.prepareDismountIssue(valueObject,configuration);
			if(valueObject.getLong("decryptIssueId",0) > 0) {
				issuesService.updateTyreAssignmentIssueStatusByIssueId(IssuesTypeConstant.ISSUES_STATUS_RESOLVED, valueObject.getLong("decryptIssueId",0),valueObject.getInt("companyId"));
			}

			iventoryTyreService.add_Inventory_TYRE_History(TyreHistory);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void updateInventoryTyreUsageAndStatusDetails(InventoryTyre inventoryTyre ,ValueObject valueObject ,ValueObject object) throws Exception {
		Integer		 					Usage 							= 0;
		try {
			
			if(object.getString("tyrePositionId").equals("SP-0")) {
				Usage = inventoryTyre.getTYRE_USEAGE();
			}else {
				if (inventoryTyre.getOPEN_ODOMETER() != null) {
					if (valueObject.getInt("dismountOdometer",0) > inventoryTyre.getOPEN_ODOMETER()) {
						Usage = valueObject.getInt("dismountOdometer",0) - inventoryTyre.getOPEN_ODOMETER();
					}
				}
				
			}

			
			if(!object.getBoolean("oldTyreFlag",false) && valueObject.getShort("assignFromId",(short)0) == TyreAssignmentConstant.ASSIGN_FROM_OTHER_VEHICLE_SPARE) {
				iventoryTyreService.updateTyreStatusToAvailable(valueObject.getInt("vid"), valueObject.getInt("dismountOdometer",0),
						Usage, InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE,(short)0,  valueObject.getLong("tyreId"));
			}else {
				if(object.getInt("oldTyreMoveId",0) == TyreAssignmentConstant.OLD_TYRE_MOVED_TO_WORKSHOP ) {
					iventoryTyreService.updateTyreStatusToAvailable(valueObject.getInt("vid"), valueObject.getInt("dismountOdometer",0),
							Usage, InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE,object.getShort("oldTyreMoveId"),  object.getLong("tyreId"));
				}else {
					iventoryTyreService.updateTyreStatusToAvailable(valueObject.getInt("vid"), valueObject.getInt("dismountOdometer",0),
							Usage, InventoryTyreDto.TYRE_ASSIGN_STATUS_UNAVAILABLE,object.getShort("oldTyreMoveId"),  object.getLong("tyreId"));
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void updateDismountTyreOdometerHistory(ValueObject valueObject, ValueObject object) throws Exception {
		try {
			if (valueObject.getInt("vehiclePreOdometer") < valueObject.getInt("dismountOdometer")) {
				
				VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign(
						valueObject.getInt("vid"), valueObject.getString("vehicleReg"), valueObject.getInt("dismountOdometer",0), object.getLong("tyreId"));
				vehicleUpdateHistory.setCompanyId(valueObject.getInt("companyId"));
				vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
				
				tyreUsageHistoryService.saveTyreDismountUsageHistory(valueObject,object);

			}else if(valueObject.getInt("vehiclePreOdometer") == valueObject.getInt("dismountOdometer")) {
				
				VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign(valueObject.getInt("vid"), valueObject.getString("vehicleReg"),  valueObject.getInt("dismountOdometer",0), object.getLong("tyreId"));
				vehicleUpdateHistory.setCompanyId(valueObject.getInt("companyId"));
				vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject tyreRotation(ValueObject valueObject) throws Exception {
		ArrayList<ValueObject>				tyreDataArrayObj					= null;
		List<ServiceReminderDto>		serviceList						= null;
		try {
			tyreDataArrayObj 	= new ArrayList<>();
			tyreDataArrayObj	= (ArrayList<ValueObject>) valueObject.get("tyreRotationDetails");
			if(tyreDataArrayObj != null && !tyreDataArrayObj.isEmpty()) {
				Integer currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(valueObject.getInt("vid"));
				valueObject.put("vehiclePreOdometer", currentODDMETER);
				for (ValueObject object : tyreDataArrayObj) {
					valueObject =	rotateTyre(valueObject, object);
				}
				if (currentODDMETER < valueObject.getInt("vehicleOdometer",0)) {
					vehicleService.updateCurrentOdoMeter(valueObject.getInt("vid"), valueObject.getInt("vehicleOdometer",0), valueObject.getInt("companyId"));
					serviceReminderService.updateCurrentOdometerToServiceReminder(valueObject.getInt("vid"), valueObject.getInt("vehicleOdometer",0), valueObject.getInt("companyId"));
					serviceList = serviceReminderService.OnlyVehicleServiceReminderList(valueObject.getInt("vid"), valueObject.getInt("vehicleOdometer",0), valueObject.getLong("userId"));
					if(serviceList != null) {
						for(ServiceReminderDto list : serviceList) {
							if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
								serviceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
							}
						}
					}
				}
			}
		
			return valueObject;		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ValueObject rotateTyre( ValueObject valueObject, ValueObject object)throws Exception{
		String								fromPosition 						= "";
		VehicleTyreLayoutPosition	 		vehicleTyreLayoutPositionTo  		= null;
		VehicleTyreLayoutPosition			vehicleTyreLayoutPositionFrom		= null;
		ValueObject							tyreModelFromoObj						= null;
		ValueObject							tyreModelToObj						= null;
		VehicleTyreModelSubTypeDto 			vehicleFromTyreModelSubType			= null;
		VehicleTyreModelSubTypeDto 			vehicleToTyreModelSubType			= null;
		try {
			tyreModelFromoObj 	= new ValueObject();
			tyreModelToObj 	= new ValueObject();
			fromPosition = object.getString("rotateFromPositionId");
			if(fromPosition.equals(object.getString("rotateToPositionId"))) {
				valueObject.put("samePosition", true);
				return valueObject;
			}else {
				
				vehicleTyreLayoutPositionFrom 	= vehicleTyreLayoutService.Get_Position_ID_to_GetDetails(object.getLong("tyreFromLP_ID"), valueObject.getInt("companyId"));
				vehicleTyreLayoutPositionTo 	= vehicleTyreLayoutService.Get_Position_ID_to_GetDetails(object.getLong("tyreToLP_ID"), valueObject.getInt("companyId"));
				
				object.put("tyreFromId", vehicleTyreLayoutPositionFrom.getTYRE_ID());
				object.put("tyreToId", vehicleTyreLayoutPositionTo.getTYRE_ID());
				if(vehicleTyreLayoutPositionFrom != null && vehicleTyreLayoutPositionFrom.getTYRE_ID() == null) {
					valueObject.put("tyreNotAssign", true);
					return valueObject;
				}
				if(vehicleTyreLayoutPositionTo != null && !valueObject.getBoolean("assignDiffTyreModelConfig") && !vehicleTyreLayoutPositionTo.getTyreModelId().equals(vehicleTyreLayoutPositionFrom.getTyreModelId()) ) {
					valueObject.put("tyreModelNotSame", true);
					return valueObject;
				}
				
				tyreModelFromoObj.put("TYRE_MST_ID", vehicleTyreLayoutPositionFrom.getTyreModelId());
				tyreModelFromoObj.put("companyId", valueObject.getInt("companyId"));
				tyreModelFromoObj =	vehicleTyreModelSubTypeService.getVehicleTyreModelSubTypeDetails(tyreModelFromoObj);
				
				
				vehicleFromTyreModelSubType = (VehicleTyreModelSubTypeDto) tyreModelFromoObj.get("tyreModelDetails");
				
				
				
				tyreModelToObj.put("TYRE_MST_ID", vehicleTyreLayoutPositionTo.getTyreModelId());
				tyreModelToObj.put("companyId", valueObject.getInt("companyId"));
				tyreModelToObj =	vehicleTyreModelSubTypeService.getVehicleTyreModelSubTypeDetails(tyreModelToObj);
				
				vehicleToTyreModelSubType = (VehicleTyreModelSubTypeDto) tyreModelToObj.get("tyreModelDetails");
				
				
				if(vehicleFromTyreModelSubType != null && vehicleToTyreModelSubType != null && !vehicleFromTyreModelSubType.getTyreModelSizeId().equals(vehicleToTyreModelSubType.getTyreModelSizeId())) {
					valueObject.put("differentSize", true);
					return valueObject;
				}
				
				if(vehicleTyreLayoutPositionTo != null) {
					
					if(vehicleTyreLayoutPositionTo.getPOSITION().equals("SP-0")) {
						iventoryTyreService.updateOpenOdometer(vehicleTyreLayoutPositionFrom.getTYRE_ID(),valueObject.getInt("vehicleOdometer",0),valueObject.getInt("companyId"));
						
					}
					
					updateTyreRotateFromDetails(vehicleTyreLayoutPositionFrom, valueObject, object);
					
					
					if(vehicleTyreLayoutPositionTo.getTYRE_ID() != null ) {
						updateTyreRotateToDetails(vehicleTyreLayoutPositionTo, valueObject, object);
					}
					
					//set tyre on "rotate TO position
					vehicleTyreLayoutService.update_Position_Assign_TYRE_To_Vehicle(vehicleTyreLayoutPositionFrom.getTYRE_ID(),
							vehicleTyreLayoutPositionFrom.getTYRE_SERIAL_NO(), vehicleTyreLayoutPositionFrom.isTYRE_ASSIGNED(), vehicleTyreLayoutPositionTo.getLP_ID(),valueObject.getString("remark",""));
					
					//set tyre on "rotate From position
					if(vehicleTyreLayoutPositionTo.getTYRE_ID() != null ) {
						vehicleTyreLayoutService.update_Position_Assign_TYRE_To_Vehicle(vehicleTyreLayoutPositionTo.getTYRE_ID(),
								vehicleTyreLayoutPositionTo.getTYRE_SERIAL_NO(), vehicleTyreLayoutPositionTo.isTYRE_ASSIGNED(), vehicleTyreLayoutPositionFrom.getLP_ID(),valueObject.getString("remark",""));
					}else {
						vehicleTyreLayoutService.update_Position_Assign_TYRE_To_Vehicle(null, null, false, vehicleTyreLayoutPositionFrom.getLP_ID(),valueObject.getString("remark",""));
					}
					
					
					updateRotateTyreOdometerHistory(valueObject,object);
					
					
					
				}
			}
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void updateTyreRotateToDetails(VehicleTyreLayoutPosition vehicleTyreLayoutPositionTo , ValueObject valueObject, ValueObject object)throws Exception{
		InventoryTyre 						inventoryTyreTo 					= null;
		InventoryTyreHistory 				inventoryTyreHistoryTo 				= null;
		InventoryTyreHistoryDto 			inventoryTyreHistoryDto 			= null;
		try {
			inventoryTyreTo 			= iventoryTyreService.Get_TYRE_ID_InventoryTyre(vehicleTyreLayoutPositionTo.getTYRE_ID());
			inventoryTyreHistoryTo 		= vModelTyreLayoutBL.prepareInventoryTyreRotationToHistory(valueObject,inventoryTyreTo,object);
			
			inventoryTyreHistoryDto 	= iventoryTyreService.getPreTyreRotationHistory(vehicleTyreLayoutPositionTo.getTYRE_ID(),inventoryTyreHistoryTo,InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_ROTATION);// used to get pre record of this tyre
			
			if(inventoryTyreHistoryDto != null ) {
				if(inventoryTyreHistoryDto.getTYRE_ASSIGN_DATE_TIMESTAMP() != null) {
					inventoryTyreHistoryTo.setPRE_TYRE_ASSIGN_DATE(inventoryTyreHistoryDto.getTYRE_ASSIGN_DATE_TIMESTAMP());
				}
				if(inventoryTyreHistoryDto.getTYRE_NUMBER() != null) {
					inventoryTyreHistoryTo.setPRE_TYRE_NUMBER(inventoryTyreHistoryDto.getTYRE_NUMBER());
				}
				if(inventoryTyreHistoryDto.getOPEN_ODOMETER() != null) {
					inventoryTyreHistoryTo.setPRE_OPEN_ODOMETER(inventoryTyreHistoryDto.getOPEN_ODOMETER());
				}
			}
			iventoryTyreService.add_Inventory_TYRE_History(inventoryTyreHistoryTo);
			iventoryTyreService.update_Assign_TYRE_To_Vehicle_Rotation(inventoryTyreHistoryTo.getTYRE_USEAGE(), vehicleTyreLayoutPositionTo.getTYRE_ID());
	
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void updateTyreRotateFromDetails(VehicleTyreLayoutPosition vehicleTyreLayoutPositionFrom , ValueObject valueObject,ValueObject object)throws Exception{
		InventoryTyre 						inventoryTyreFrom 					= null;
		InventoryTyreHistory 				inventoryTyreHistoryFrom 			= null;
		InventoryTyreHistoryDto 			inventoryTyreHistoryDto 			= null;
		try {
			if(vehicleTyreLayoutPositionFrom != null) {
				if(vehicleTyreLayoutPositionFrom.getTYRE_ID() != null ) {
					
					inventoryTyreFrom 			=  iventoryTyreService.Get_TYRE_ID_InventoryTyre(vehicleTyreLayoutPositionFrom.getTYRE_ID());
					inventoryTyreHistoryFrom 	= vModelTyreLayoutBL.prepareInventoryTyreRotationFromHistory(valueObject,inventoryTyreFrom,object);
					
					inventoryTyreHistoryDto 	= iventoryTyreService.getPreTyreRotationHistory(vehicleTyreLayoutPositionFrom.getTYRE_ID(),inventoryTyreHistoryFrom,InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_ROTATION);// used to get pre record of this tyre
					
					if(inventoryTyreHistoryDto != null ) {
						if(inventoryTyreHistoryDto.getTYRE_ASSIGN_DATE_TIMESTAMP() != null) {
							inventoryTyreHistoryFrom.setPRE_TYRE_ASSIGN_DATE(inventoryTyreHistoryDto.getTYRE_ASSIGN_DATE_TIMESTAMP());
						}
						if(inventoryTyreHistoryDto.getTYRE_NUMBER() != null) {
							inventoryTyreHistoryFrom.setPRE_TYRE_NUMBER(inventoryTyreHistoryDto.getTYRE_NUMBER());
						}
						if(inventoryTyreHistoryDto.getOPEN_ODOMETER() != null) {
							inventoryTyreHistoryFrom.setPRE_OPEN_ODOMETER(inventoryTyreHistoryDto.getOPEN_ODOMETER());
						}
					}
					
					
					iventoryTyreService.add_Inventory_TYRE_History(inventoryTyreHistoryFrom);
					
					iventoryTyreService.update_Assign_TYRE_To_Vehicle_Rotation(inventoryTyreHistoryFrom.getTYRE_USEAGE(), inventoryTyreHistoryFrom.getTYRE_ID());
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void updateTyreOdometerHistory(ValueObject valueObject,ValueObject object)throws Exception{
		try {

			if ( valueObject.getInt("vehiclePreOdometer",0) < valueObject.getInt("vehicleOdometer",0)) {
				VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign( valueObject.getInt("vid"), valueObject.getString("vehicleReg"), valueObject.getInt("vehicleOdometer",0), object.getLong("newTyreId"));
				vehicleUpdateHistory.setCompanyId(valueObject.getInt("companyId"));
				vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
				
				tyreUsageHistoryService.saveTyreMountUsageHistory(valueObject,object);
			
			}else if(valueObject.getInt("vehiclePreOdometer",0) ==  valueObject.getInt("vehicleOdometer",0)) {
				VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign(valueObject.getInt("vid"), valueObject.getString("vehicleReg"),  valueObject.getInt("vehicleOdometer",0), object.getLong("newTyreId"));
				vehicleUpdateHistory.setCompanyId(valueObject.getInt("companyId"));
				vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public void updateRotateTyreOdometerHistory(ValueObject valueObject,ValueObject object)throws Exception{
		try {
			
			if ( valueObject.getInt("vehiclePreOdometer",0) < valueObject.getInt("vehicleOdometer",0)) {
				VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign( valueObject.getInt("vid"), valueObject.getString("vehicleReg"), valueObject.getInt("vehicleOdometer",0), object.getLong("newTyreId"));
				vehicleUpdateHistory.setCompanyId(valueObject.getInt("companyId"));
				vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
				
				tyreUsageHistoryService.saveTyreRotateFromUsageHistory(valueObject,object);
				tyreUsageHistoryService.saveTyreRotateToUsageHistory(valueObject,object);
				
			}else if(valueObject.getInt("vehiclePreOdometer",0) ==  valueObject.getInt("vehicleOdometer",0)) {
				VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign(valueObject.getInt("vid"), valueObject.getString("vehicleReg"),  valueObject.getInt("vehicleOdometer",0), object.getLong("newTyreId"));
				vehicleUpdateHistory.setCompanyId(valueObject.getInt("companyId"));
				vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject ownSpareAssignment(ValueObject valueObject) throws Exception {
		String								fromPosition 						= "";
		String								toPosition 							= "";
		VehicleTyreLayoutPosition	 		vehicleTyreLayoutPositionFrom  		= null;
		VehicleTyreLayoutPosition	 		vehicleTyreLayoutPositionTo  		= null;
		ArrayList<ValueObject>				tyreDataArrayObj					= null;
		ValueObject							tyreModelFromoObj						= null;
		ValueObject							tyreModelToObj						= null;
		VehicleTyreModelSubTypeDto 			vehicleFromTyreModelSubType			= null;
		VehicleTyreModelSubTypeDto 			vehicleToTyreModelSubType			= null;
		try {
			tyreModelFromoObj 	= new ValueObject();
			tyreModelToObj 	= new ValueObject();
			tyreDataArrayObj 	= new ArrayList<>();
			tyreDataArrayObj	= (ArrayList<ValueObject>) valueObject.get("tyreDetails");
			
			Integer currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(valueObject.getInt("vid"));
			valueObject.put("vehiclePreOdometer", currentODDMETER);
			
			if(tyreDataArrayObj != null && !tyreDataArrayObj.isEmpty()) {
				for (ValueObject object : tyreDataArrayObj) {
				
					vehicleTyreLayoutPositionFrom =	vehicleTyreLayoutPositionRepository.Get_Position_name_to_GetDetails(valueObject.getInt("vid"), "SP-0", valueObject.getInt("companyId"));
				
					tyreModelFromoObj.put("TYRE_MST_ID", vehicleTyreLayoutPositionFrom.getTyreModelId());
					tyreModelFromoObj.put("companyId", valueObject.getInt("companyId"));
					tyreModelFromoObj =	vehicleTyreModelSubTypeService.getVehicleTyreModelSubTypeDetails(tyreModelFromoObj);
					
					
					vehicleFromTyreModelSubType = (VehicleTyreModelSubTypeDto) tyreModelFromoObj.get("tyreModelDetails");
					if(vehicleTyreLayoutPositionFrom != null && vehicleTyreLayoutPositionFrom.getTYRE_ID() != null) {
						fromPosition 	= "SP-0";
						toPosition 		= object.getString("tyrePositionId");

						if(fromPosition.equals(toPosition)) {
							valueObject.put("samePosition", true);
							return valueObject;
						}else {
							vehicleTyreLayoutPositionTo 	= vehicleTyreLayoutService.Get_Position_name_to_GetDetails(valueObject.getInt("vid"),toPosition, valueObject.getInt("companyId"));

							if(vehicleTyreLayoutPositionTo != null && !valueObject.getBoolean("assignDiffTyreModelConfig") && !vehicleTyreLayoutPositionTo.getTyreModelId().equals(vehicleTyreLayoutPositionFrom.getTyreModelId()) ) {
								valueObject.put("tyreModelNotSame", true);
								return valueObject;
							}
							
							
							if(vehicleTyreLayoutPositionTo != null ) {
								
								tyreModelToObj.put("TYRE_MST_ID", vehicleTyreLayoutPositionTo.getTyreModelId());
								tyreModelToObj.put("companyId", valueObject.getInt("companyId"));
								tyreModelToObj =	vehicleTyreModelSubTypeService.getVehicleTyreModelSubTypeDetails(tyreModelToObj);
								
								vehicleToTyreModelSubType = (VehicleTyreModelSubTypeDto) tyreModelToObj.get("tyreModelDetails");
								
								
								if(vehicleFromTyreModelSubType != null && vehicleToTyreModelSubType != null && !vehicleFromTyreModelSubType.getTyreModelSizeId().equals(vehicleToTyreModelSubType.getTyreModelSizeId())) {
									valueObject.put("differentSize", true);
									return valueObject;
								}
								
								object.put("tyreFromLP_ID", vehicleTyreLayoutPositionFrom.getLP_ID());
								object.put("tyreToLP_ID", vehicleTyreLayoutPositionTo.getLP_ID());
								object.put("tyreFromId", vehicleTyreLayoutPositionFrom.getTYRE_ID());
								object.put("tyreToId", vehicleTyreLayoutPositionTo.getTYRE_ID());
								object.put("tyreToNumber", vehicleTyreLayoutPositionTo.getTYRE_SERIAL_NO());
								
								if(vehicleTyreLayoutPositionTo.getTYRE_ID() != null ) {
									updateVehicleTyreLayoutPositionToDetails (vehicleTyreLayoutPositionTo,valueObject,object);
								}

								updateVehicleTyreLayoutPositionFromDetails (vehicleTyreLayoutPositionFrom,valueObject,object);
								
								resolveIssue(valueObject, object);
								
								vehicleTyreLayoutService.update_Position_Assign_TYRE_To_Vehicle(vehicleTyreLayoutPositionFrom.getTYRE_ID(),
										vehicleTyreLayoutPositionFrom.getTYRE_SERIAL_NO(), vehicleTyreLayoutPositionFrom.isTYRE_ASSIGNED(), vehicleTyreLayoutPositionTo.getLP_ID(),valueObject.getString("remark",""));

								if(vehicleTyreLayoutPositionTo.getTYRE_ID() != null && object.getShort("oldTyreMoveId") == TyreAssignmentConstant.OLD_TYRE_MOVED_TO_SPARE) {
									vehicleTyreLayoutService.update_Position_Assign_TYRE_To_Vehicle(vehicleTyreLayoutPositionTo.getTYRE_ID(),
											vehicleTyreLayoutPositionTo.getTYRE_SERIAL_NO(), vehicleTyreLayoutPositionTo.isTYRE_ASSIGNED(), vehicleTyreLayoutPositionFrom.getLP_ID(),valueObject.getString("remark",""));
								}else {
									vehicleTyreLayoutService.update_Position_Assign_TYRE_To_Vehicle(null, null, false, vehicleTyreLayoutPositionFrom.getLP_ID(),valueObject.getString("remark",""));
								}

							}
						}
					}
					
					updateSpareTyreOdometerHistory(valueObject,object);
					
						
					}
					
			}else {
				valueObject.put("noSpareTyre", true);
				return valueObject;
			}
			
			return valueObject;		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public void updateVehicleTyreLayoutPositionToDetails(VehicleTyreLayoutPosition vehicleTyreLayoutPositionTo , ValueObject valueObject, ValueObject object)throws Exception{
		InventoryTyre 						inventoryTyreTo 					= null;
		InventoryTyreHistory 				inventoryTyreHistoryTo 				= null;
		try {
			inventoryTyreTo 			= iventoryTyreService.Get_TYRE_ID_InventoryTyre(vehicleTyreLayoutPositionTo.getTYRE_ID());
			inventoryTyreHistoryTo 		= vModelTyreLayoutBL.prepareOwnSpareAssignToHistory(valueObject,inventoryTyreTo,object );

			iventoryTyreService.add_Inventory_TYRE_History(inventoryTyreHistoryTo);
			
			if(object.getInt("oldTyreMoveId",0) != TyreAssignmentConstant.OLD_TYRE_MOVED_TO_SPARE ) {
				if(object.getInt("oldTyreMoveId",0) == TyreAssignmentConstant.OLD_TYRE_MOVED_TO_WORKSHOP ) {
					iventoryTyreService.updateTyreStatusToAvailable(valueObject.getInt("vid"), valueObject.getInt("dismountOdometer",0),
							inventoryTyreHistoryTo.getTYRE_USEAGE(), InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE,object.getShort("oldTyreMoveId"),  vehicleTyreLayoutPositionTo.getTYRE_ID());
				}else {
					iventoryTyreService.updateTyreStatusToAvailable(valueObject.getInt("vid"), valueObject.getInt("dismountOdometer",0),
							inventoryTyreHistoryTo.getTYRE_USEAGE(), InventoryTyreDto.TYRE_ASSIGN_STATUS_UNAVAILABLE,object.getShort("oldTyreMoveId"),  vehicleTyreLayoutPositionTo.getTYRE_ID());
				}
			}
		

			iventoryTyreService.update_Assign_TYRE_To_Vehicle_Rotation(inventoryTyreHistoryTo.getTYRE_USEAGE(), vehicleTyreLayoutPositionTo.getTYRE_ID());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void updateVehicleTyreLayoutPositionFromDetails(VehicleTyreLayoutPosition vehicleTyreLayoutPositionFrom , ValueObject valueObject, ValueObject object)throws Exception{
		InventoryTyre 						inventoryTyreFrom 					= null;
		InventoryTyreHistory 				inventoryTyreHistoryFrom 			= null;
		InventoryTyreHistoryDto 			inventoryTyreHistoryDto 			= null;
		HashMap<String, Object> 			configuration 						= null;
		CustomUserDetails					userDetails							= null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		=  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			inventoryTyreFrom 			=  iventoryTyreService.Get_TYRE_ID_InventoryTyre(vehicleTyreLayoutPositionFrom.getTYRE_ID());
			inventoryTyreHistoryFrom 	= vModelTyreLayoutBL.prepareOwnSpareAssignFromHistory(valueObject,inventoryTyreFrom,object);

			inventoryTyreHistoryDto 	= iventoryTyreService.getPreTyreRotationHistory(vehicleTyreLayoutPositionFrom.getTYRE_ID(),inventoryTyreHistoryFrom,InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_ROTATION);// used to get pre record of this tyre

			if(inventoryTyreHistoryDto != null ) {
				if(inventoryTyreHistoryDto.getTYRE_ASSIGN_DATE_TIMESTAMP() != null) {
					inventoryTyreHistoryFrom.setPRE_TYRE_ASSIGN_DATE(inventoryTyreHistoryDto.getTYRE_ASSIGN_DATE_TIMESTAMP());
				}
				if(inventoryTyreHistoryDto.getTYRE_NUMBER() != null) {
					inventoryTyreHistoryFrom.setPRE_TYRE_NUMBER(inventoryTyreHistoryDto.getTYRE_NUMBER());
				}
				if(inventoryTyreHistoryDto.getOPEN_ODOMETER() != null) {
					inventoryTyreHistoryFrom.setPRE_OPEN_ODOMETER(inventoryTyreHistoryDto.getOPEN_ODOMETER());
				}
			}
			// if this will create from issue so in then block it can't create issue again
		//	if(valueObject.getShort("transactionTypeId",(short)0) ==  0 &&  valueObject.getLong("transactionId",0) == 0 ) {
				
				valueObject.put("LP_ID", vehicleTyreLayoutPositionFrom.getLP_ID());
				valueObject.put("tyreNumber", object.getString("tyreToNumber",""));
				valueObject = assignmentBL.prepareIssue(valueObject,configuration);
				valueObject.put("userDetails", userDetails);
				valueObject = 	issuesService.saveIssuesDetails(valueObject);
		//	}
			
			inventoryTyreHistoryFrom.setIssueId(valueObject.getLong("decryptIssueId",0));
			iventoryTyreService.add_Inventory_TYRE_History(inventoryTyreHistoryFrom);

			iventoryTyreService.update_Assign_TYRE_To_Vehicle_Rotation(inventoryTyreHistoryFrom.getTYRE_USEAGE(), inventoryTyreHistoryFrom.getTYRE_ID());

			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public void updateSpareTyreOdometerHistory(ValueObject valueObject, ValueObject object) throws Exception {
		List<ServiceReminderDto>		serviceList						= null;
		try {
			Integer currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(valueObject.getInt("vid"));
			
			if (currentODDMETER < valueObject.getInt("vehicleOdometer",0)) {
				
				vehicleService.updateCurrentOdoMeter(valueObject.getInt("vid"), valueObject.getInt("vehicleOdometer",0), valueObject.getInt("companyId"));
				// update current Odometer update Service Reminder
				serviceReminderService.updateCurrentOdometerToServiceReminder(valueObject.getInt("vid"), valueObject.getInt("vehicleOdometer",0), valueObject.getInt("companyId"));
				
				tyreUsageHistoryService.saveTyreRotateFromUsageHistory(valueObject,object);
				tyreUsageHistoryService.saveTyreRotateToUsageHistory(valueObject,object);
				serviceList = serviceReminderService.OnlyVehicleServiceReminderList(valueObject.getInt("vid"), valueObject.getInt("vehicleOdometer",0), valueObject.getLong("userId"));
				if(serviceList != null) {
					for(ServiceReminderDto list : serviceList) {
						if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
							serviceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
						}
					}
				}

				VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign(
						valueObject.getInt("vid"), valueObject.getString("vehicleReg"), valueObject.getInt("vehicleOdometer",0), object.getLong("tyreFromId"));
				vehicleUpdateHistory.setCompanyId(valueObject.getInt("companyId"));
				vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
			}else if(currentODDMETER ==  valueObject.getInt("vehicleOdometer",0)) {
				VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToTyreAssign(valueObject.getInt("vid"), valueObject.getString("vehicleReg"),  valueObject.getInt("vehicleOdometer",0), object.getLong("tyreFromId"));
				vehicleUpdateHistory.setCompanyId(valueObject.getInt("companyId"));
				vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
	}
	
	@Override
	public List<VehicleTyreLayoutPositionDto> getTyreAssignmentByTransactionTypeAndTransactionId(short transactionType, Long transactionId, Integer companyId) throws Exception {
		TypedQuery<Object[]> 				query 							= null;
		List<VehicleTyreLayoutPositionDto> 	vehicleTyreLayoutPositionList 	= null;
		VehicleTyreLayoutPositionDto 		vehicleTyreLayoutPositionDto 	= null;
		try {
			query = entityManager.createQuery(
				"SELECT VTLP.LP_ID, VTLP.AXLE, VTLP.POSITION, VTLP.tyreModelId , TMS.TYRE_MODEL_SUBTYPE, TMS.tyreModelTypeId, "
					+ " TMS.gauageMeasurementLine, TMS.tyreGauge, TMS.tyreTubeTypeId, TMS.ply, TMS.psi, VTS.TYRE_SIZE, VTLP.TYRE_ID, "
					+ " IT.TYRE_NUMBER, VTLP.transactionSubId  "
					+ " FROM VehicleTyreLayoutPosition AS VTLP "
					+ " INNER JOIN VehicleTyreModelSubType TMS ON TMS.TYRE_MST_ID = VTLP.tyreModelId "
					+ " LEFT JOIN VehicleTyreSize VTS ON VTS.TS_ID = TMS.tyreModelSizeId "
					+ " LEFT JOIN InventoryTyre IT ON IT.TYRE_ID = VTLP.TYRE_ID "
					+ " WHERE VTLP.transactionTypeId = "+transactionType+" AND VTLP.transactionId = "+transactionId+" "
					+ " AND VTLP.COMPANY_ID = "+companyId+" AND VTLP.markForDelete = 0",
					Object[].class);
			

			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				vehicleTyreLayoutPositionList = new ArrayList<VehicleTyreLayoutPositionDto>();
				
				for (Object[] result : results) {
					vehicleTyreLayoutPositionDto = new VehicleTyreLayoutPositionDto();
					vehicleTyreLayoutPositionDto.setLP_ID((Long)result[0]);
					vehicleTyreLayoutPositionDto.setAXLE((Integer)result[1]);
					vehicleTyreLayoutPositionDto.setPOSITION((String)result[2]);
					vehicleTyreLayoutPositionDto.setTyreModelId((Integer)result[3]);
					vehicleTyreLayoutPositionDto.setTyreModel((String)result[4]);
					if(result[5] != null) {
						vehicleTyreLayoutPositionDto.setTyreModelType(TyreModalConstant.getTyreModelType((short)result[5]));
					}
					if(result[6] != null) {
						vehicleTyreLayoutPositionDto.setGauageMeasurementLine((Integer)result[6]);
					}
					if(result[7] != null) {
						vehicleTyreLayoutPositionDto.setTyreGauge((Integer)result[7]);
					}
					if(result[8] != null) {
						vehicleTyreLayoutPositionDto.setTyreTubeType(TyreModalConstant.getTyreTubeType((short)result[8]));
					}
					if(result[9] != null) {
						vehicleTyreLayoutPositionDto.setPly((Integer)result[9]);
					}
					if(result[10] != null) {
						vehicleTyreLayoutPositionDto.setPsi((Integer)result[10]);
					}
					vehicleTyreLayoutPositionDto.setTyreModelSize((String)result[11]);
					if(result[12] != null) {
						vehicleTyreLayoutPositionDto.setTYRE_ID((Long)result[12]);
						vehicleTyreLayoutPositionDto.setTYRE_SERIAL_NO((String)result[13]);
					}else {
						vehicleTyreLayoutPositionDto.setTYRE_SERIAL_NO("");
						
					}
					vehicleTyreLayoutPositionDto.setTransactionSubId((Long)result[14]);
					vehicleTyreLayoutPositionList.add(vehicleTyreLayoutPositionDto);
					
					}
				}
			return vehicleTyreLayoutPositionList;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}

	@Override
	public VehicleTyreLayoutPositionDto getTyreModelByPositon(Integer vid, String position, Integer companyId) throws Exception {
		VehicleTyreLayoutPositionDto vehicleTyreLayoutPositionDto = null;
		try {
			
			Query query = entityManager.createQuery(
					"SELECT VTLP.LP_ID, TMS.TYRE_MODEL_SUBTYPE "
						+ " FROM VehicleTyreLayoutPosition AS VTLP "
						+ " INNER JOIN VehicleTyreModelSubType TMS ON TMS.TYRE_MST_ID = VTLP.tyreModelId "
						+ " WHERE VTLP.POSITION = '"+position+"'  AND VTLP.VEHICLE_ID = "+vid+""
						+ " AND VTLP.COMPANY_ID = "+companyId+" AND VTLP.markForDelete = 0");
			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			
			vehicleTyreLayoutPositionDto = new VehicleTyreLayoutPositionDto();
			if (result != null) {
				vehicleTyreLayoutPositionDto.setLP_ID((Long)result[0]);
				vehicleTyreLayoutPositionDto.setTyreModel((String)result[1]);
				
			} 
			
			
			return vehicleTyreLayoutPositionDto;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<VehicleTyreLayoutPositionDto> getAssignedTyreOfVehicle(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> 				query 							= null;
		List<VehicleTyreLayoutPositionDto> 	vehicleTyreLayoutPositionList 	= null;
		VehicleTyreLayoutPositionDto 		vehicleTyreLayoutPositionDto 	= null;
		try {
			query = entityManager.createQuery(
				"SELECT VTLP.LP_ID, VTLP.TYRE_ID, VTLP.POSITION "
					+ " FROM VehicleTyreLayoutPosition AS VTLP "
					+ " WHERE VTLP.VEHICLE_ID = "+valueObject.getLong("vid",0)+" AND VTLP.TYRE_ASSIGNED = 1 AND VTLP.TYRE_ID IS NOT NULL "
					+ " AND VTLP.COMPANY_ID = "+valueObject.getInt("companyId")+" AND VTLP.markForDelete = 0",
					Object[].class);
			

			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				vehicleTyreLayoutPositionList = new ArrayList<VehicleTyreLayoutPositionDto>();
				
				for (Object[] result : results) {
					vehicleTyreLayoutPositionDto = new VehicleTyreLayoutPositionDto();
					vehicleTyreLayoutPositionDto.setLP_ID((Long)result[0]);
					vehicleTyreLayoutPositionDto.setTYRE_ID((Long)result[1]);
					vehicleTyreLayoutPositionDto.setPOSITION((String)result[2]);
					vehicleTyreLayoutPositionList.add(vehicleTyreLayoutPositionDto);
					
					}
				}
			
			return vehicleTyreLayoutPositionList;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Override
	public VehicleTyreLayoutPositionDto getTyreModelByLP_ID(Integer vid, Long LP_ID, Integer companyId) throws Exception {
		VehicleTyreLayoutPositionDto vehicleTyreLayoutPositionDto = null;
		try {
			Query query = entityManager.createQuery(
					"SELECT VTLP.LP_ID, TMS.TYRE_MODEL_SUBTYPE, VTLP.POSITION "
						+ " FROM VehicleTyreLayoutPosition AS VTLP "
						+ " INNER JOIN VehicleTyreModelSubType TMS ON TMS.TYRE_MST_ID = VTLP.tyreModelId "
						+ " WHERE VTLP.LP_ID = '"+LP_ID+"'  AND VTLP.VEHICLE_ID = "+vid+""
						+ " AND VTLP.COMPANY_ID = "+companyId+" AND VTLP.markForDelete = 0");
			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			
			vehicleTyreLayoutPositionDto = new VehicleTyreLayoutPositionDto();
			if (result != null) {
				vehicleTyreLayoutPositionDto.setLP_ID((Long)result[0]);
				vehicleTyreLayoutPositionDto.setTyreModel((String)result[1]);
				vehicleTyreLayoutPositionDto.setPOSITION((String)result[2]);
				
			} 
			
			
			return vehicleTyreLayoutPositionDto;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	

	@Override
	public List<VehicleTyreLayoutPositionDto> getVehicleTyreLayoutPositionList(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> 				query 							= null;
		List<VehicleTyreLayoutPositionDto> 	vehicleTyreLayoutPositionList 	= null;
		VehicleTyreLayoutPositionDto 		vehicleTyreLayoutPositionDto 	= null;
		try {
			
			 query = entityManager.createQuery(
					"SELECT VTLP.LP_ID, VTLP.AXLE, VTLP.POSITION, VTLP.TYRE_ID, IT.TYRE_NUMBER, VTLP.tyreTypeId, VTLP.oldTyreId, "
						+ " IT.TYRE_NUMBER, VTLP.oldTyreMoveId,VTLP.alignment,VTLP.kinpin,VTLP.assignFromId, TMS.TYRE_MODEL_SUBTYPE, "
						+ " TMS.tyreModelTypeId, TMS.gauageMeasurementLine, TMS.tyreGauge, TMS.tyreTubeTypeId, TMS.ply, TMS.psi, "
						+ " VTS.TYRE_SIZE, VTLP.tyreModelId, IT.TYRE_ASSIGN_DATE,VTLP.VEHICLE_ID, V.vehicle_registration, V.vehicleModalId "
						+ " FROM VehicleTyreLayoutPosition AS VTLP "
						+ " INNER JOIN Vehicle V ON V.vid = VTLP.VEHICLE_ID "
						+ " LEFT JOIN InventoryTyre IT ON IT.TYRE_ID = VTLP.TYRE_ID "
						+ " LEFT JOIN VehicleTyreModelSubType TMS ON TMS.TYRE_MST_ID = IT.TYRE_MODEL_ID "
						+ " LEFT JOIN VehicleTyreSize VTS ON VTS.TS_ID = TMS.tyreModelSizeId "
						+ " WHERE VTLP.COMPANY_ID = "+valueObject.getInt("companyId")+"   "+valueObject.getString("query","")+""
						+ " AND  VTLP.markForDelete = 0 ORDER BY VTLP.VEHICLE_ID DESC " ,
						Object[].class);
			 
			 
			
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				vehicleTyreLayoutPositionList = new ArrayList<VehicleTyreLayoutPositionDto>();
				
				for (Object[] result : results) {
					vehicleTyreLayoutPositionDto = new VehicleTyreLayoutPositionDto();
					vehicleTyreLayoutPositionDto.setLP_ID((Long)result[0]);
					vehicleTyreLayoutPositionDto.setAXLE((Integer)result[1]);
					vehicleTyreLayoutPositionDto.setPOSITION((String)result[2]);
					if(result[3] != null) {
						vehicleTyreLayoutPositionDto.setTYRE_ID((Long)result[3]);
						vehicleTyreLayoutPositionDto.setTYRE_SERIAL_NO((String)result[4]);
					}
					vehicleTyreLayoutPositionDto.setTyreType("");
					vehicleTyreLayoutPositionDto.setOldTyreId((Long)result[6]);
					vehicleTyreLayoutPositionDto.setOldTyre((String)result[7]);
					vehicleTyreLayoutPositionDto.setOldTyreMove("");
					vehicleTyreLayoutPositionDto.setAlignmentStr("");
					vehicleTyreLayoutPositionDto.setKinpinStr("");
					vehicleTyreLayoutPositionDto.setAssignFrom("");
					vehicleTyreLayoutPositionDto.setTyreModel((String)result[12]);
					if(result[13] != null) {
						vehicleTyreLayoutPositionDto.setTyreModelType(TyreModalConstant.getTyreModelType((short)result[13]));
					}
					vehicleTyreLayoutPositionDto.setGauageMeasurementLine((Integer)result[14]);
					vehicleTyreLayoutPositionDto.setTyreGauge((Integer)result[15]);
					if(result[16] != null) {
						vehicleTyreLayoutPositionDto.setTyreTubeType(TyreModalConstant.getTyreTubeType((short)result[16]));
					}
					if(result[17] != null) {
						vehicleTyreLayoutPositionDto.setPly((Integer)result[17]);
					}
					if(result[18] != null) {
						vehicleTyreLayoutPositionDto.setPsi((Integer)result[18]);
					}
					if(result[19] != null) {
						vehicleTyreLayoutPositionDto.setTyreModelSize((String)result[19]);
					}
					if(result[20] != null) {
						vehicleTyreLayoutPositionDto.setTyreModelId((Integer)result[20]);
						
					}
					if(result[21] != null) {
						vehicleTyreLayoutPositionDto.setMountDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[21], DateTimeUtility.DD_MM_YYYY));
					}
					vehicleTyreLayoutPositionDto.setVEHICLE_ID((Integer)result[22]);
					vehicleTyreLayoutPositionDto.setVehicleRegistration((String)result[23]);
					vehicleTyreLayoutPositionDto.setVehicleModelId((Long)result[24]);
					vehicleTyreLayoutPositionList.add(vehicleTyreLayoutPositionDto);
				}
			}
			
			return vehicleTyreLayoutPositionList;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
