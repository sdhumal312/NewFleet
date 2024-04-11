package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.BatteryConstant;
import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.RepairConstant;
import org.fleetopgroup.constant.TyreAssignmentConstant;
import org.fleetopgroup.persistence.bl.RepairBL;
import org.fleetopgroup.persistence.dao.PartInvoiceRepository;
import org.fleetopgroup.persistence.dao.RepairFromAssetPartRepository;
import org.fleetopgroup.persistence.dao.RepairStockRepository;
import org.fleetopgroup.persistence.dao.RepairStockToPartDetailsRepository;
import org.fleetopgroup.persistence.dao.RepairToAssetPartRepository;
import org.fleetopgroup.persistence.dao.RepairToLabourDetailsRepository;
import org.fleetopgroup.persistence.dao.RepairToStockDetailsRepository;
import org.fleetopgroup.persistence.dto.BatteryDto;
import org.fleetopgroup.persistence.dto.BatteryHistoryDto;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.InventoryTyreHistoryDto;
import org.fleetopgroup.persistence.dto.PartWarrantyDetailsDto;
import org.fleetopgroup.persistence.dto.RepairStockDto;
import org.fleetopgroup.persistence.dto.RepairStockToPartDetailsDto;
import org.fleetopgroup.persistence.dto.RepairToLabourDetailsDto;
import org.fleetopgroup.persistence.dto.RepairToStockDetailsDto;
import org.fleetopgroup.persistence.model.BatteryHistory;
import org.fleetopgroup.persistence.model.Inventory;
import org.fleetopgroup.persistence.model.InventoryTyreHistory;
import org.fleetopgroup.persistence.model.InventoryTyreRetread;
import org.fleetopgroup.persistence.model.InventoryTyreRetreadAmount;
import org.fleetopgroup.persistence.model.RepairFromAssetPart;
import org.fleetopgroup.persistence.model.RepairFromAssetPartDto;
import org.fleetopgroup.persistence.model.RepairSequenceCounter;
import org.fleetopgroup.persistence.model.RepairStock;
import org.fleetopgroup.persistence.model.RepairStockToPartDetails;
import org.fleetopgroup.persistence.model.RepairToAssetPart;
import org.fleetopgroup.persistence.model.RepairToAssetPartDto;
import org.fleetopgroup.persistence.model.RepairToLabourDetails;
import org.fleetopgroup.persistence.model.RepairToStockDetails;
import org.fleetopgroup.persistence.serviceImpl.IBatteryHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IPartWarrantyDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IRepairSequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IRepairService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("RepairService")
@Transactional
public class RepairService implements IRepairService {
	private static final int PAGE_SIZE = 20;
	private static final int MAXSINGLERESULT = 1;
	SimpleDateFormat 	dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	public  static final Double 		WORK_ORDER_DEFALAT_AMOUNT 			= 0.0;
	public static final  long 			WORK_ORDER_DEFALAT_VALUE			= 0;
	SimpleDateFormat	formatDate = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat	format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	@PersistenceContext EntityManager entityManager;
	@Autowired private IRepairSequenceCounterService  			repairSequenceCounterService;
	@Autowired private IInventoryTyreService  					inventoryTyreService;
	@Autowired private IBatteryService  						batteryService;
	@Autowired private IBatteryHistoryService  					batteryHistoryService;
	@Autowired private RepairStockRepository  					repairStockRepository;
	@Autowired private RepairToStockDetailsRepository  			repairToStockDetailsRepository;
	@Autowired private RepairStockToPartDetailsRepository  		repairStockToPartDetailsRepository;
	@Autowired private RepairToLabourDetailsRepository  		repairToLabourDetailsRepository;
	@Autowired private IInventoryService 						inventoryService;
	@Autowired private IPartWarrantyDetailsService 				partWarrantyDetailsService;
	@Autowired private PartInvoiceRepository					partInvoiceRepository;
	@Autowired private RepairFromAssetPartRepository			repairFromAssetPartRepository;
	@Autowired private RepairToAssetPartRepository				repairToAssetPartRepository;
	@Autowired private IUserProfileService						userProfileService;
	
	RepairBL				repairBL 		= new RepairBL();
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	@Transactional
	@Override
	public ValueObject saveRepairStockInvoice(ValueObject valueObject) throws Exception {
		RepairStock 			repairStock 		= null;
		RepairSequenceCounter	sequenceCounter 	= null;
		try {
			repairStock 		= repairBL.prepareRepairStockInvoice(valueObject);
			sequenceCounter		= repairSequenceCounterService.findNextNumber(valueObject.getInt("companyId",0));
			if(sequenceCounter != null) {
				repairStock.setRepairNumber(sequenceCounter.getNextVal());
				repairStock = repairStockRepository.save(repairStock);
			}else {
				valueObject.put("sequenceNotFound", true);
				return valueObject;
			}
			valueObject.put("repairStock", repairStock);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public RepairStock getRepairInvoiceByRepairStockId(Long repairStockId, Integer companyId) throws Exception {
		RepairStock 				repairStock 		= null;
		try {
			repairStock = repairStockRepository.getRepairInvoiceByRepairStockId(repairStockId,companyId);
			return repairStock;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getRepairInvoice(ValueObject valueObject) throws Exception {
		RepairStockDto 				repairStockDto 		= null;
		try {
			repairStockDto = getRepairInvoiceById(valueObject);
			valueObject.put("repairStockDto", repairStockDto);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	@Override
	public RepairStockDto getRepairInvoiceById(ValueObject valueObject) throws Exception {
		RepairStockDto 				repairStockDto 		= null;
		try {
			Query query = entityManager.createQuery(
					  " SELECT RS.repairStockId, RS.repairNumber, RS.repairTypeId, RS.repairWorkshopId, "
					+ " RS.vendorId, VN.vendorName, RS.repairStatusId, RS.refNumber, RS.totalRepairingCost, "
					+ " RS.openDate, RS.requiredDate, RS.description, U1.firstName, U2.firstName, "
					+ " RS.creationOn, RS.lastUpdatedOn, RS.locationId, PL.partlocation_name, "
					+ " RS.partDiscountTaxTypeId, RS.labourDiscountTaxTypeId,RS.additionalPartLocationId, PL1.partlocation_name  FROM RepairStock AS RS "
					+ " INNER JOIN User U1 ON U1.id = RS.createdById "
					+ " INNER JOIN User U2 ON U2.id = RS.lastModifiedById "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = RS.locationId "
					+ " LEFT JOIN PartLocations PL1 ON PL1.partlocation_id = RS.additionalPartLocationId "
					+ " LEFT JOIN Vendor VN ON VN.vendorId = RS.vendorId "
					+ " where RS.companyId = "+valueObject.getInt("companyId")+" "
					+ " AND RS.repairStockId = "+valueObject.getLong("repairStockId")+" AND  RS.markForDelete = 0 ");
			
			
			Object[] results = null;
			try {
				results = (Object[]) query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			if (results != null ) {
				repairStockDto = new RepairStockDto();
				repairStockDto.setRepairStockId((Long) results[0]);
				repairStockDto.setRepairNumberStr("RS-"+(Long) results[1]);
				repairStockDto.setRepairTypeId((short) results[2]);
				repairStockDto.setRepairType(RepairConstant.getRepairType((short) results[2]));
				repairStockDto.setRepairWorkshopId((short) results[3]);
				repairStockDto.setRepairWorkshop(RepairConstant.getRepairWorkshop((short) results[3]));
				repairStockDto.setVendorId((Integer) results[4]);
				repairStockDto.setVendorName((String) results[5]);
				repairStockDto.setRepairStatusId((short) results[6]);
				repairStockDto.setRepairStatus(RepairConstant.getRepairStatus((short) results[6]));
				repairStockDto.setRefNumber((String) results[7]);
				repairStockDto.setTotalRepairingCost((Double) results[8]);
				repairStockDto.setOpenDateStr(format.format((Date) results[9]));
				repairStockDto.setRequiredDateStr(format.format((Date) results[10]));
				repairStockDto.setDescription((String) results[11]);
				repairStockDto.setCreatedBy((String) results[12]);
				repairStockDto.setLastModifiedBy((String) results[13]);
				repairStockDto.setCreationDate(format.format((Date) results[14]));
				repairStockDto.setLastUpdatedDate(format.format((Date) results[15]));
				repairStockDto.setLocationId((Integer) results[16]);
				repairStockDto.setLocation((String) results[17]);
				repairStockDto.setPartDiscountTaxTypeId((short) results[18]);
				repairStockDto.setPartDiscountTaxType(RepairConstant.getDiscoutTaxType((short) results[18]));
				repairStockDto.setLabourDiscountTaxTypeId((short) results[19]);
				repairStockDto.setLabourDiscountTaxType(RepairConstant.getDiscoutTaxType((short) results[19]));
				if(results[20] != null) {
					repairStockDto.setAdditionalPartLocationId((Integer) results[20]);
					repairStockDto.setAdditionalPartLocation((String) results[21]);
				}
			}
			return repairStockDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public ValueObject getRepairToStockDetails(ValueObject valueObject) throws Exception {
		List<RepairToStockDetailsDto> 			repairStockToDetailsList 			= null;
		try {
			repairStockToDetailsList 		= getRepairToStockDetailsById(valueObject);
			valueObject.put("repairStockToDetailsList", repairStockToDetailsList);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<RepairToStockDetailsDto> getRepairToStockDetailsById(ValueObject valueObject) throws Exception {
		List<RepairToStockDetailsDto> 		repairStockToDetailsList 		= null;
		RepairToStockDetailsDto 	repairStockToDetails 			= null;
		TypedQuery<Object[]> 		query 					= null;
		try {
			query = entityManager.createQuery(
					  " SELECT RTS.repairToStockDetailsId, RTS.repairStockId, RTS.repairStockPartId,"
					+ " M.partname, M.partnumber, B.batterySerialNumber, IT.TYRE_NUMBER, RTS.isRepairInWarranty, "
					+ " RTS.repairStockStatusId, RTS.workDetails, RTS.dateOfRemoved, RTS.remark, RTS.inventoryLocationId ,"
					+ " RTS.inventoryLocationPartId, I.serialNoAddedForParts, RTS.transferedInventoryId  "
					+ " FROM RepairToStockDetails AS RTS"
					+ " INNER JOIN RepairStock AS RS ON RS.repairStockId = RTS.repairStockId "
					+ " LEFT JOIN MasterParts M ON M.partid = RTS.repairStockPartId "
					+ " LEFT JOIN Battery B ON B.batteryId = RTS.repairStockPartId "
					+ " LEFT JOIN InventoryTyre IT ON IT.TYRE_ID = RTS.repairStockPartId "
					+ " LEFT JOIN Inventory I ON I.partid = RTS.repairStockPartId AND I.inventory_id = RTS.inventoryLocationPartId AND RS.repairTypeId = "+RepairConstant.REPAIR_TYPE_PART_ID+" "
				//	+ " LEFT JOIN PartWarrantyDetails PWD ON PWD.partId = RTS.repairStockPartId AND PWD.inventoryId = RTS.inventoryLocationPartId AND RS.repairTypeId = "+RepairConstant.REPAIR_TYPE_PART_ID+" "
					+ " where RTS.companyId = "+valueObject.getInt("companyId")+" AND RTS.repairStockId = "+valueObject.getLong("repairStockId")+" AND  RTS.markForDelete = 0 ",Object[].class);
			
			
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				repairStockToDetailsList = new ArrayList<>();
				
				for (Object[] result : results) {
					repairStockToDetails = new RepairToStockDetailsDto();
					repairStockToDetails.setRepairToStockDetailsId((Long)result[0]);
					repairStockToDetails.setRepairStockId((Long)result[1]);
					repairStockToDetails.setRepairStockPartId((Long)result[2]);
					if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_PART_ID && result[3] != null) {
						repairStockToDetails.setRepairToStockName((String)result[3]+" "+(String)result[4]);
					}else if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_BATTERY_ID && result[5] != null){
						repairStockToDetails.setRepairToStockName((String)result[5]);
					}else {
						if(result[6] != null) {
							repairStockToDetails.setRepairToStockName((String)result[6]);
						}
					}
					repairStockToDetails.setRepairInWarranty((boolean)result[7]);
					if((boolean)result[7]) {
						repairStockToDetails.setRepairInWarrantyStatus("Yse");
					}else {
						repairStockToDetails.setRepairInWarrantyStatus("No");
					}
					repairStockToDetails.setRepairStatusId((short)result[8]);
					repairStockToDetails.setRepairStatus(RepairConstant.getRepairStockStatus((short)result[8]));
					repairStockToDetails.setWorkDetails((String)result[9]);
					if(result[10] != null) {
						repairStockToDetails.setDateOfRemovedStr(format.format((Date) result[10]));
					}
					repairStockToDetails.setRemark((String)result[11]);
					repairStockToDetails.setInventoryLocationId((Long)result[12]);
					repairStockToDetails.setInventoryLocationPartId((Long)result[13]);
					if(result[14] != null) {
						repairStockToDetails.setSerialNoAddedForParts((int)result[14]);
					}
					if(result[15] != null) {
						repairStockToDetails.setTransferedInventoryId((Long)result[15]);
					}
					repairStockToDetailsList.add(repairStockToDetails);
					
				}
			}
			return repairStockToDetailsList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
/*	@SuppressWarnings("unchecked")
	@Override
	public ValueObject getRepairStockToPartAndLabourDetails(ValueObject valueObject) throws Exception {
		Map<Long,  List<RepairStockToPartDetailsDto>>	repairStockToPartDetailsHM		= null;
		Map<Long,  List<RepairToLabourDetailsDto>>		repairLabourDetailsHM			= null;
		StringBuilder repairToStockDetailsIds = new StringBuilder();
		List<Long> repairToStockIds = new ArrayList<>(); 
		List<RepairToStockDetailsDto>  repairStockList = null;
		try {
			valueObject 	= getRepairToStockDetailsHM(valueObject);
			valueObject 	= getRepairStockToPartDetails(valueObject);
			
			if(valueObject.getBoolean("partWiseLabourCofig")) {
				valueObject 	= getRepairStockToLabourDetails(valueObject);
			}else {
				valueObject 	= getDefaultRepairStockToLabourDetails(valueObject);
			}	
			
			repairStockToPartDetailsHM 	= (Map<Long, List<RepairStockToPartDetailsDto>>) valueObject.get("repairStockToPartDetailsHM");
			repairLabourDetailsHM 		= (Map<Long, List<RepairToLabourDetailsDto>>) valueObject.get("repairLabourDetailsHM");
			
			repairStockList = (List<RepairToStockDetailsDto>) valueObject.get("repairStockList");
			if(repairStockList != null && !repairStockList.isEmpty()) {
				for (RepairToStockDetailsDto dto : repairStockList) {
					if(dto.getRepairStatusId() != RepairConstant.REPAIR_STOCK_STATUS_REJECT) {
						repairToStockDetailsIds.append(dto.getRepairToStockDetailsId()+",");
						repairToStockIds.add(dto.getRepairToStockDetailsId());
					}
				}
				if(repairToStockDetailsIds != null && repairToStockDetailsIds.length() > 0) {
					repairToStockDetailsIds.deleteCharAt(repairToStockDetailsIds.length()-1);
				}
			}
			
			if((repairStockToPartDetailsHM == null || repairStockToPartDetailsHM.isEmpty() || repairStockToPartDetailsHM.size() <= 0) && (repairLabourDetailsHM == null || repairLabourDetailsHM.isEmpty() || repairLabourDetailsHM.size() <= 0 )) {
				valueObject.put("noAdditionalPartAndLabourFound", true);
				// those part are not rejected will change the status to open
				repairToStockDetailsRepository.updateStockStatus(RepairConstant.REPAIR_STOCK_STATUS_OPEN, repairToStockIds,valueObject.getInt("companyId"));
				repairStockRepository.updateDiscountTaxTypeId(RepairConstant.DEFAULT_DIS_TAX_TYPE_ID,valueObject.getLong("repairStockId"),valueObject.getInt("companyId"));
			}else if((repairStockToPartDetailsHM == null || repairStockToPartDetailsHM.isEmpty() || repairStockToPartDetailsHM.size() <= 0) && (repairLabourDetailsHM != null && !repairLabourDetailsHM.isEmpty() && repairLabourDetailsHM.size() > 0 )) {
				repairStockRepository.updatePartDiscountTaxTypeId(RepairConstant.DEFAULT_DIS_TAX_TYPE_ID,valueObject.getLong("repairStockId"),valueObject.getInt("companyId"));
			}else if((repairStockToPartDetailsHM != null && !repairStockToPartDetailsHM.isEmpty() && repairStockToPartDetailsHM.size() > 0) && (repairLabourDetailsHM == null || repairLabourDetailsHM.isEmpty() || repairLabourDetailsHM.size() <= 0 )) {
				repairStockRepository.updateLabourDiscountTaxTypeId(RepairConstant.DEFAULT_DIS_TAX_TYPE_ID,valueObject.getLong("repairStockId"),valueObject.getInt("companyId"));
			}else {
				valueObject.put("hasDiscountTaxTypeId", true);
			}
			
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/

	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject getRepairStockToPartAndLabourDetails(ValueObject valueObject) throws Exception {
		Map<Long,  List<RepairStockToPartDetailsDto>>	repairStockToPartDetailsHM		= null;
		Map<Long,  List<RepairToLabourDetailsDto>>		repairLabourDetailsHM			= null;
		try {
			valueObject 	= getRepairToStockDetailsHM(valueObject);
			valueObject 	= getRepairStockToPartDetails(valueObject);
			
			if(valueObject.getBoolean("partWiseLabourCofig")) {
				valueObject 	= getRepairStockToLabourDetails(valueObject);
			}else {
				valueObject 	= getDefaultRepairStockToLabourDetails(valueObject);
			}	
			repairStockToPartDetailsHM 	= (Map<Long, List<RepairStockToPartDetailsDto>>) valueObject.get("repairStockToPartDetailsHM");
			repairLabourDetailsHM 		= (Map<Long, List<RepairToLabourDetailsDto>>) valueObject.get("repairLabourDetailsHM");
			
			if((repairStockToPartDetailsHM == null || repairStockToPartDetailsHM.isEmpty() || repairStockToPartDetailsHM.size() <= 0) && (repairLabourDetailsHM == null || repairLabourDetailsHM.isEmpty() || repairLabourDetailsHM.size() <= 0 )) {
				valueObject.put("noAdditionalPartAndLabourFound", true);
			}else {
				valueObject.put("hasDiscountTaxTypeId", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ValueObject getRepairToStockDetailsHM(ValueObject valueObject) throws Exception {
		List<RepairToStockDetailsDto>  repairStockList = null;
		List<RepairToStockDetailsDto>  repairStockFinalList = null;
		Map<Long, List<RepairToStockDetailsDto>> repairStockHM = null;
		try {
			repairStockHM 	= new HashMap<>();
			repairStockList = getRepairToStockDetailsById(valueObject);
			if(repairStockList != null && !repairStockList.isEmpty()) {
				for(RepairToStockDetailsDto dto : repairStockList) {
					if(repairStockHM.containsKey(dto.getRepairToStockDetailsId())) {
						repairStockHM.get(dto.getRepairToStockDetailsId()).add(dto);
					}else {
						repairStockFinalList = new ArrayList<>();
						repairStockFinalList.add(dto);
						repairStockHM.put(dto.getRepairToStockDetailsId(), repairStockFinalList);
					}
				}
			}
			valueObject.put("repairStockList", repairStockList);
			valueObject.put("repairStockHM", repairStockHM);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getRepairStockToPartDetails(ValueObject valueObject) throws Exception {
		List<RepairStockToPartDetailsDto> 				repairStockToPartDetailsList 			= null;
		List<RepairStockToPartDetailsDto> 				repairStockToPartDetailsFinalList 		= null;
		Map<Long,  List<RepairStockToPartDetailsDto>>	repairStockToPartDetailsHM				= null;
		try {
			repairStockToPartDetailsHM		= new HashMap<Long, List<RepairStockToPartDetailsDto>>();
			repairStockToPartDetailsList 	= getRepairStockToPartDetailsList(valueObject);
			
			if(repairStockToPartDetailsList != null && !repairStockToPartDetailsList.isEmpty()) {
				for(RepairStockToPartDetailsDto dto : repairStockToPartDetailsList) {
					if(repairStockToPartDetailsHM.containsKey(dto.getRepairToStockDetailsId())) {
						repairStockToPartDetailsHM.get(dto.getRepairToStockDetailsId()).add(dto);
					}else {
						repairStockToPartDetailsFinalList = new ArrayList<>();
						repairStockToPartDetailsFinalList.add(dto);
						repairStockToPartDetailsHM.put(dto.getRepairToStockDetailsId(), repairStockToPartDetailsFinalList);
					}
				}
			}
			
			valueObject.put("repairStockToPartDetailsList", repairStockToPartDetailsList);
			valueObject.put("repairStockToPartDetailsHM", repairStockToPartDetailsHM);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getRepairStockToLabourDetails(ValueObject valueObject) throws Exception {
		List<RepairToLabourDetailsDto> 		repairLabourDetailsList 		= null;
		List<RepairToLabourDetailsDto> 		repairLabourDetailsFinalList 		= null;
		Map<Long,  List<RepairToLabourDetailsDto>>	repairLabourDetailsHM			= null;
		try {
			repairLabourDetailsHM		= new HashMap<Long, List<RepairToLabourDetailsDto>>();
			repairLabourDetailsList 	= getRepairStockToLabourDetailsList(valueObject);
			
			if(repairLabourDetailsList != null && !repairLabourDetailsList.isEmpty()) {
				for(RepairToLabourDetailsDto dto : repairLabourDetailsList) {
					if(repairLabourDetailsHM.containsKey(dto.getRepairToStockDetailsId())) {
						repairLabourDetailsHM.get(dto.getRepairToStockDetailsId()).add(dto);
					}else {
						repairLabourDetailsFinalList = new ArrayList<>();
						repairLabourDetailsFinalList.add(dto);
						repairLabourDetailsHM.put(dto.getRepairToStockDetailsId(), repairLabourDetailsFinalList);
					}
				}
			}
			
			valueObject.put("repairLabourDetailsHM", repairLabourDetailsHM);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	
	@Override
	public List<RepairStockToPartDetailsDto> getRepairStockToPartDetailsList(ValueObject valueObject) throws Exception {
		List<RepairStockToPartDetailsDto> 		repairStockToPartDetailsList 		= null;
		RepairStockToPartDetailsDto 			repairStockToPartDetails			= null;
		TypedQuery<Object[]> 		query 					= null;
		try {
			query = entityManager.createQuery(
					  " SELECT RTS.repairStockToPartDetailsId, RTS.partId,M.partname,  M.partnumber, RTS.unitCost, RTS.quantity, RTS.gst, "
					+ " RTS.discount, RTS.totalCost, RTS.repairToStockDetailsId,  M1.partname, M1.partnumber, B.batterySerialNumber, IT.TYRE_NUMBER, RTS.inventoryLocationId "
					+ " FROM RepairStockToPartDetails AS RTS "
					+ " INNER JOIN RepairToStockDetails RS ON RS.repairToStockDetailsId = RTS.repairToStockDetailsId "
					+ " LEFT JOIN MasterParts M ON M.partid = RTS.partId "
					+ " LEFT JOIN MasterParts M1 ON M1.partid = RS.repairStockPartId "
					+ " LEFT JOIN Battery B ON B.batteryId = RS.repairStockPartId "
					+ " LEFT JOIN InventoryTyre IT ON IT.TYRE_ID = RS.repairStockPartId "
					+ " where RTS.companyId = "+valueObject.getInt("companyId")+" AND RS.repairStockId = "+valueObject.getLong("repairStockId")+" AND  RTS.markForDelete = 0 ",Object[].class);
			
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				repairStockToPartDetailsList = new ArrayList<>();
				
				for (Object[] result : results) {
					repairStockToPartDetails = new RepairStockToPartDetailsDto();
					repairStockToPartDetails.setRepairStockToPartDetailsId((Long)result[0]);
					repairStockToPartDetails.setPartId((Long)result[1]);
					repairStockToPartDetails.setPartName((String)result[2]+ " "+ (String)result[3]);
					repairStockToPartDetails.setUnitCost((Double)result[4]);
					repairStockToPartDetails.setQuantity((Double)result[5]);
					repairStockToPartDetails.setDiscount((Double)result[6]);
					repairStockToPartDetails.setGst((Double)result[7]);
					repairStockToPartDetails.setTotalCost((Double)result[8]);
					repairStockToPartDetails.setRepairToStockDetailsId((Long)result[9]);
					if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_PART_ID && result[10] != null) {
						repairStockToPartDetails.setRepairToStockName((String)result[10] +" "+(String)result[11]);
					}else if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_TYRE_ID && result[12] != null){
						repairStockToPartDetails.setRepairToStockName((String)result[12]);
					}else {
						if(result[13] != null)
							repairStockToPartDetails.setRepairToStockName((String)result[13]);
					}
					if(result[14] != null) {
						repairStockToPartDetails.setInventoryLocationId((Long)result[14]);
					}
					repairStockToPartDetailsList.add(repairStockToPartDetails);
					
				}
			}
			return repairStockToPartDetailsList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public List<RepairToLabourDetailsDto> getRepairStockToLabourDetailsList(ValueObject valueObject) throws Exception {
		List<RepairToLabourDetailsDto> 		repairStockToLabourDetailsList 		= null;
		RepairToLabourDetailsDto 			repairStockToLabourDetails			= null;
		TypedQuery<Object[]> 		query 					= null;
		try {
			query = entityManager.createQuery(
					  " SELECT RTS.repairToLabourDetailsId, RTS.labourId, D.driver_firstname, RTS.hours, RTS.unitCost, RTS.gst, RTS.discount, RTS.totalCost, RTS.repairToStockDetailsId, M1.partnumber, B.batterySerialNumber, IT.TYRE_NUMBER   "
					+ " FROM RepairToLabourDetails AS RTS "
					+ " INNER JOIN RepairToStockDetails RS ON RS.repairToStockDetailsId = RTS.repairToStockDetailsId "
					+ " LEFT JOIN MasterParts M1 ON M1.partid = RS.repairStockPartId "
					+ " LEFT JOIN Battery B ON B.batteryId = RS.repairStockPartId "
					+ " LEFT JOIN InventoryTyre IT ON IT.TYRE_ID = RS.repairStockPartId "
					+ " LEFT JOIN Driver D ON D.driver_id = RTS.labourId "
					+ " where RTS.companyId = "+valueObject.getInt("companyId")+" AND RS.repairStockId = "+valueObject.getLong("repairStockId")+" AND  RTS.markForDelete = 0 ",Object[].class);
			
			
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				repairStockToLabourDetailsList = new ArrayList<>();
				
				for (Object[] result : results) {
					repairStockToLabourDetails = new RepairToLabourDetailsDto();
					repairStockToLabourDetails.setRepairToLabourDetailsId((Long)result[0]);
					repairStockToLabourDetails.setLabourId((Long)result[1]);
					repairStockToLabourDetails.setLabourName((String)result[2]);
					repairStockToLabourDetails.setHours((Double)result[3]);
					repairStockToLabourDetails.setUnitCost((Double)result[4]);
					repairStockToLabourDetails.setDiscount((Double)result[5]);
					repairStockToLabourDetails.setGst((Double)result[6]);
					repairStockToLabourDetails.setTotalCost((Double)result[7]);
					repairStockToLabourDetails.setRepairToStockDetailsId((Long)result[8]);
					if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_PART_ID && result[9] != null) {
						repairStockToLabourDetails.setRepairToStockName((String)result[9]);
					}else if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_TYRE_ID && result[10] != null){
						repairStockToLabourDetails.setRepairToStockName((String)result[10]);
					}else {
						if(result[11] != null)
							repairStockToLabourDetails.setRepairToStockName((String)result[11]);
					}
					
					repairStockToLabourDetailsList.add(repairStockToLabourDetails);
					
				}
			}
			return repairStockToLabourDetailsList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public ValueObject getDefaultRepairStockToLabourDetails(ValueObject valueObject) throws Exception {
		List<RepairToLabourDetailsDto>  defaultLabourList = null;
		try {
			defaultLabourList = getDefaultRepairStockToLabourDetailsList(valueObject);
			valueObject.put("defaultLabourList", defaultLabourList);
			return valueObject;
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}
	
	@Override
	public List<RepairToLabourDetailsDto> getDefaultRepairStockToLabourDetailsList(ValueObject valueObject) throws Exception {
		List<RepairToLabourDetailsDto> 		repairStockToLabourDetailsList 		= null;
		RepairToLabourDetailsDto 			repairStockToLabourDetails			= null;
		TypedQuery<Object[]> 		query 					= null;
		try {
			query = entityManager.createQuery(
					  " SELECT RTS.repairToLabourDetailsId, RTS.labourId, D.driver_firstname, RTS.hours, RTS.unitCost, RTS.gst, RTS.discount, RTS.totalCost  "
					+ " FROM RepairToLabourDetails AS RTS "
					+ " LEFT JOIN Driver D ON D.driver_id = RTS.labourId "
					+ " where RTS.companyId = "+valueObject.getInt("companyId")+" AND RTS.repairStockId = "+valueObject.getLong("repairStockId")+" AND  RTS.markForDelete = 0 ",Object[].class);
			
			
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				repairStockToLabourDetailsList = new ArrayList<>();
				
				for (Object[] result : results) {
					repairStockToLabourDetails = new RepairToLabourDetailsDto();
					repairStockToLabourDetails.setRepairToLabourDetailsId((Long)result[0]);
					repairStockToLabourDetails.setLabourId((Long)result[1]);
					repairStockToLabourDetails.setLabourName((String)result[2]);
					repairStockToLabourDetails.setHours((Double)result[3]);
					repairStockToLabourDetails.setUnitCost((Double)result[4]);
					repairStockToLabourDetails.setDiscount((Double)result[5]);
					repairStockToLabourDetails.setGst((Double)result[6]);
					repairStockToLabourDetails.setTotalCost((Double)result[7]);
					
					repairStockToLabourDetailsList.add(repairStockToLabourDetails);
					
				}
			}
			return repairStockToLabourDetailsList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public ValueObject getDismountTyreDetails(ValueObject valueObject) throws Exception {
		InventoryTyreHistoryDto 				inventoryTyreHistory 		= null;
		try {
			Query query = entityManager.createQuery(
					  " SELECT ITH.TYRE_HIS_ID, ITH.TYRE_ID, ITH.TYRE_NUMBER, ITH.TYRE_ASSIGN_DATE FROM  InventoryTyreHistory AS ITH"
					+ " where ITH.COMPANY_ID = "+valueObject.getInt("companyId")+" AND ITH.TYRE_ID = "+valueObject.getLong("tyreId",0)+" "
					+ " AND  ITH.markForDelete = 0 ORDER BY ITH.TYRE_HIS_ID DESC",
					Object[].class);
			
			Object[] results =  null;
			try {
				query.setMaxResults(MAXSINGLERESULT);
				results = (Object[]) query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			if (results != null ) {
				inventoryTyreHistory = new InventoryTyreHistoryDto();
				inventoryTyreHistory.setTYRE_HIS_ID((Long) results[0]);
				inventoryTyreHistory.setTYRE_ID((Long) results[1]);
				inventoryTyreHistory.setTYRE_NUMBER((String) results[2]);
				inventoryTyreHistory.setTYRE_ASSIGN_DATE(formatDate.format((Date) results[3]));
			}
			valueObject.put("inventoryTyreHistory", inventoryTyreHistory); ;
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public ValueObject getDismountBatteryDetails(ValueObject valueObject) throws Exception {
		BatteryHistoryDto 				batteryHistory 		= null;
		try {
			Query query = entityManager.createQuery(
					  " SELECT ITH.batteryHistoryId, ITH.batteryId, ITH.batterySerialNumber, ITH.batteryAsignDate FROM  BatteryHistory AS ITH"
					+ " where ITH.companyId = "+valueObject.getInt("companyId")+" AND ITH.batteryId = "+valueObject.getLong("batteryId",0)+" "
					+ " AND  ITH.markForDelete = 0 ORDER BY ITH.batteryHistoryId DESC",
					Object[].class);
			
			Object[] results =  null;
			try {
				query.setMaxResults(MAXSINGLERESULT);
				results = (Object[]) query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			if (results != null ) {
				batteryHistory = new BatteryHistoryDto();
				batteryHistory.setBatteryHistoryId((Long) results[0]);
				batteryHistory.setBatteryId((Long) results[1]);
				batteryHistory.setBatterySerialNumber((String) results[2]);
				batteryHistory.setBatteryAsignDateStr(formatDate.format((Date) results[3]));
			}
			valueObject.put("batteryHistory", batteryHistory); ;
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public ValueObject saveRepairToStockDetails(ValueObject valueObject) throws Exception {
		ArrayList<ValueObject>					repairToStockArrayObj			= null;
		RepairToStockDetails					repairToStockDetails	 		= null;
		List<RepairToStockDetails>				repairToStockDetailsList	 	= null;
		RepairToStockDetails					repairStockToDetailsCost	 	= null; // just to get data location id column are used in stock and part table thas why
		StringBuilder builder = new StringBuilder();
		try {
			repairToStockArrayObj 					= new ArrayList<>();
			repairToStockDetailsList 				= new ArrayList<>();
			repairToStockArrayObj					= (ArrayList<ValueObject>) valueObject.get("repairStockDetails");
		
			if(repairToStockArrayObj != null && !repairToStockArrayObj.isEmpty()) {
				for (ValueObject object : repairToStockArrayObj) {
					repairToStockDetails = repairBL.prepareRepairToStockDetails(object,valueObject);
					repairToStockDetailsList.add(repairToStockDetails);
					builder.append(repairToStockDetails.getRepairStockPartId()+",");
					
					if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_PART_ID ) {
						repairStockToDetailsCost = calculateInventoryStockForRepairStock(object, valueObject);
						repairToStockDetails.setInventoryLocationId(repairStockToDetailsCost.getInventoryLocationId());
						repairToStockDetails.setInventoryLocationPartId(repairStockToDetailsCost.getInventoryLocationPartId());
					}
				}
				if(builder.length() > 0)
				builder.deleteCharAt(builder.length()-1);
				repairToStockDetailsRepository.saveAll(repairToStockDetailsList);
				repairStockRepository.updatSentToRepair(valueObject.getString("remark"),valueObject.getLong("repairStockId"),valueObject.getInt("companyId"));
				
				if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_TYRE_ID) {
					updateTyreDismountStatus(TyreAssignmentConstant.REPAIR_IN_PROCESS,builder.toString() );
				}else if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_BATTERY_ID) {
					updateBatteryDismountStatus(BatteryConstant.REPAIR_IN_PROCESS,builder.toString() );
				}
				
				repairToStockDetailsRepository.updateStatusOfPart(RepairConstant.REPAIR_STOCK_STATUS_INPROCESS, valueObject.getLong("repairToStockDetailsId"),valueObject.getInt("companyId"));
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getRepairStockList(ValueObject valueObject) throws Exception {
		List<RepairStockDto> 		repairStockList 		= null;
		String						repairStatusQuery		= "";
		try {
			Page<RepairStock> 	page 			= getDeploymentPageRepairStock(valueObject.getShort("statusId",(short)0), valueObject.getInt("pagenumber"), valueObject.getInt("companyId"));
			Object[] 			statusCount 	= getRepairToStockCount(valueObject);
			
			if (page != null) {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				valueObject.put("deploymentLog", page);
				valueObject.put("beginIndex", begin);
				valueObject.put("endIndex", end);
				valueObject.put("currentIndex", current);
			}
			if(valueObject.getShort("statusId",(short)0) >  0) {
				repairStatusQuery = "AND RS.repairStatusId = "+valueObject.getShort("statusId")+" ";
			}
			valueObject.put("repairStatusQuery", repairStatusQuery);
			repairStockList = getRepairListByStatusId(valueObject);
			
			
			valueObject.put("allCount",statusCount[0]);
			valueObject.put("createdCount",statusCount[1]);
			valueObject.put("sentToRepairCount",statusCount[2]);
			valueObject.put("completeCount",statusCount[3]);

			valueObject.put("repairStockList", repairStockList);
			valueObject.put("statusId", valueObject.getShort("statusId"));
			valueObject.put("currentIndex", valueObject.getInt("pagenumber"));
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	@Transactional    
	public Page<RepairStock> getDeploymentPageRepairStock(short statusId, Integer pageNumber, Integer comapanyId) throws Exception {
		try {
			Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
			if(statusId == 0) {
				return repairStockRepository.getDeploymentPageRepairStock(comapanyId, pageable);
			}else {
				return repairStockRepository.getDeploymentPageRepairStock(statusId, comapanyId, pageable);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public Object[] getRepairToStockCount(ValueObject valueObject) throws Exception {
		try {
			Query queryt =	null;
			queryt = entityManager
					.createQuery("SELECT COUNT(RS1), "
							+ "(SELECT  COUNT(RS2) FROM RepairStock AS RS2 where RS2.repairStatusId =1 AND RS2.companyId = "+valueObject.getInt("companyId")+" AND RS2.markForDelete = 0 ) ,"
							+ "(SELECT  COUNT(RS3) FROM RepairStock AS RS3 where RS3.repairStatusId =2 AND RS3.companyId = "+valueObject.getInt("companyId")+" AND RS3.markForDelete = 0 ) ,"
							+ "(SELECT  COUNT(RS4) FROM RepairStock AS RS4 where RS4.repairStatusId =3 AND RS4.companyId = "+valueObject.getInt("companyId")+" AND RS4.markForDelete = 0 ) "
							+ " FROM RepairStock As RS1 where RS1.companyId = "+valueObject.getInt("companyId")+" AND RS1.markForDelete = 0 ");
	 
			Object[] count = (Object[]) queryt.getSingleResult();

			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Override
	public List<RepairStockDto> getRepairListByStatusId(ValueObject valueObject) throws Exception {
		List<RepairStockDto> 		repairStockList 		= null;
		RepairStockDto 				repairStockDto 			= null;
		TypedQuery<Object[]> 		query 					= null;
		try {
			 query = entityManager.createQuery(
					  " SELECT RS.repairStockId, RS.repairNumber, RS.repairTypeId, RS.repairWorkshopId, "
					+ " RS.vendorId, VN.vendorName, RS.repairStatusId, RS.refNumber, RS.totalRepairingCost, "
					+ " RS.openDate, RS.requiredDate, RS.dateOfSent, RS.completedDate, RS.description, "
					+ "  U1.firstName, U2.firstName, RS.creationOn, RS.lastUpdatedOn, RS.repairStatusId FROM RepairStock AS RS "
					+ " LEFT JOIN Vendor VN ON VN.vendorId = RS.vendorId "
					+ " INNER JOIN User U1 ON U1.id = RS.createdById "
					+ " INNER JOIN User U2 ON U2.id = RS.lastModifiedById "
					+ " where RS.companyId = "+valueObject.getInt("companyId")+" "+valueObject.getString("repairStatusQuery","")+""
					+ " AND  RS.markForDelete = 0 ORDER BY RS.repairStockId DESC ",Object[].class);
		
			if(valueObject.getInt("currentIndex",0) > 0) {
				query.setFirstResult((valueObject.getInt("currentIndex",0) - 1) * PAGE_SIZE);
				query.setMaxResults(PAGE_SIZE);
			}
			List<Object[]> resultList = query.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				repairStockList = new ArrayList<>();
				
				for (Object[] results : resultList) {
					repairStockDto = new RepairStockDto();
					repairStockDto.setRepairStockId((Long) results[0]);
					repairStockDto.setRepairNumberStr("RS-"+(Long) results[1]);
					repairStockDto.setRepairTypeId((short) results[2]);
					repairStockDto.setRepairType(RepairConstant.getRepairType((short) results[2]));
					repairStockDto.setRepairWorkshop(RepairConstant.getRepairWorkshop((short) results[3]));
					if( results[4] != null) {
					repairStockDto.setVendorId((Integer) results[4]);
					repairStockDto.setVendorName((String) results[5]);
					}
					repairStockDto.setRepairStatus(RepairConstant.getRepairStatus((short) results[6]));
					repairStockDto.setRefNumber((String) results[7]);
					repairStockDto.setTotalRepairingCost((Double) results[8]);
					repairStockDto.setOpenDateStr(format.format((Date) results[9]));
					repairStockDto.setRequiredDateStr(format.format((Date) results[10]));
					if(results[11] != null) {
						repairStockDto.setDateOfSentStr(format.format((Date) results[11]));
					}else {
						repairStockDto.setDateOfSentStr("");
					}
					if(results[12] != null) {
						repairStockDto.setCompletedDateStr(format.format((Date) results[12]));
					}else {
						repairStockDto.setCompletedDateStr("");
					}
					repairStockDto.setDescription((String) results[13]);
					repairStockDto.setCreatedBy((String) results[14]);
					repairStockDto.setLastModifiedBy((String) results[15]);
					repairStockDto.setCreationDate(format.format((Date) results[16]));
					repairStockDto.setLastUpdatedDate(format.format((Date) results[17]));
					repairStockDto.setRepairStatusId((short) results[18]);
					repairStockDto.setRepairStatus(RepairConstant.getRepairStatus((short) results[18]));
					repairStockList.add(repairStockDto);
				}
			}
			return repairStockList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject deleteRepairInvoice(ValueObject valueObject) throws Exception {
		try {
			repairStockRepository.deleteRepairInvoice(valueObject.getLong("repairStockId"),valueObject.getInt("companyId"));
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public ValueObject saveRepairStockToPartDetails(ValueObject valueObject) throws Exception {
		ArrayList<ValueObject>					repairStockToPartArrayObj			= null;
		RepairStockToPartDetails				repairStockToPartDetails	 		= null;
		RepairStockToPartDetails				repairStockToPartDetailsCost	 	= null;
		List<RepairStockToPartDetails>			repairStockToPartDetailsList	 	= null;
		try {
			
			repairStockToPartArrayObj 					= new ArrayList<>();
			repairStockToPartDetailsList 				= new ArrayList<>();
			repairStockToPartArrayObj					= (ArrayList<ValueObject>) valueObject.get("partDetails");
			if(repairStockToPartArrayObj != null && !repairStockToPartArrayObj.isEmpty()) {
				for (ValueObject object : repairStockToPartArrayObj) {
					repairStockToPartDetails = repairBL.prepareRepairStockToPartDetails(object,valueObject);
					repairStockToPartDetailsList.add(repairStockToPartDetails);
					if(valueObject.getShort("repairWorkshopId") == RepairConstant.REPAIR_WORKSHOP_OWN) {
						repairStockToPartDetailsCost = calculateInventoryStock(object,valueObject);
						repairStockToPartDetails.setUnitCost(repairStockToPartDetailsCost.getUnitCost());
						repairStockToPartDetails.setTotalCost(repairStockToPartDetailsCost.getTotalCost());
						repairStockToPartDetails.setInventoryLocationId(repairStockToPartDetailsCost.getInventoryLocationId());
						repairStockToPartDetails.setQuantity(repairStockToPartDetailsCost.getQuantity());
					}
				}
				repairStockToPartDetailsRepository.saveAll(repairStockToPartDetailsList);
				if(valueObject.getShort("repairStatusId") == RepairConstant.REPAIR_STOCK_STATUS_OPEN) {
					/*****status Id of repairToStockDetails*/
					repairToStockDetailsRepository.updateStatusOfPart(RepairConstant.REPAIR_STOCK_STATUS_INPROCESS, valueObject.getLong("repairToStockDetailsId"),valueObject.getInt("companyId"));
				}
				repairStockRepository.updatePartDiscountTaxTypeId(valueObject.getShort("finalPartDiscountTaxTypId",(short)0),valueObject.getLong("repairStockId"),valueObject.getInt("companyId"));
			}
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
public RepairStockToPartDetails calculateInventoryStock(ValueObject object ,ValueObject valueObject) throws Exception {
		
		List<InventoryDto> 					inventoryList						= null;
		String 								subLocationQuery 					= "";
		Double								useQuantity							= null;
		double								eachPrice							= 0;
		double								totalcost							= 0;
		RepairStockToPartDetails			repairStockToPartDetails			= null;
		try {
			repairStockToPartDetails = new RepairStockToPartDetails();
			inventoryList 		= inventoryService.Get_WorkOrder_InventoryLocation_id_To_Inventory(object.getLong("inventoryLocationId"),valueObject.getInt("companyId",0),subLocationQuery);
			
			if(inventoryList != null  && !inventoryList.isEmpty()) {
				for (InventoryDto inventoryDto : inventoryList) {
					if (inventoryDto.getQuantity() > 0) {
						if (object.getDouble("partQty") <= inventoryDto.getQuantity()) {
							useQuantity = object.getDouble("partQty");
						}else {
							useQuantity = inventoryDto.getQuantity();
						}
						
						eachPrice 		= round((inventoryDto.getTotal() / inventoryDto.getHistory_quantity()), 2);
						totalcost 		= useQuantity * eachPrice;
						
						inventoryService.Subtract_update_Inventory_from_Workorder(useQuantity,inventoryDto.getInventory_id(),valueObject.getInt("companyId",0));
						
						inventoryService.Subtract_update_InventoryLocation_from_Workorder(inventoryDto.getPartid(),inventoryDto.getLocationId(),valueObject.getInt("companyId",0));
							
						inventoryService.Subtract_update_InventoryAll_from_Workorder(inventoryDto.getPartid(),valueObject.getInt("companyId",0));
						
						partInvoiceRepository.update_anyPartNumberAsign_InPartInvoice(inventoryDto.getPartInvoiceId(),valueObject.getInt("companyId",0));
					
						repairStockToPartDetails.setInventoryLocationId(inventoryDto.getInventory_id());
						repairStockToPartDetails.setUnitCost(eachPrice);
						repairStockToPartDetails.setTotalCost(totalcost);
						repairStockToPartDetails.setQuantity(useQuantity);
						break;
					}
				}
			}
			return repairStockToPartDetails;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}	
		

public RepairToStockDetails calculateInventoryStockForRepairStock(ValueObject object ,ValueObject valueObject) throws Exception {
	
	List<InventoryDto> 					inventoryList						= null;
	String 								subLocationQuery 					= "";
	Double								useQuantity							= null;
	RepairToStockDetails				repairToStockDetails			= null;
	try {
		repairToStockDetails 	= new RepairToStockDetails();
		inventoryList 			= inventoryService.Get_WorkOrder_InventoryLocation_id_To_Inventory(object.getLong("inventoryLocationId"),valueObject.getInt("companyId",0),subLocationQuery);
		if(inventoryList != null  && !inventoryList.isEmpty()) {
			for (InventoryDto inventoryDto : inventoryList) {
				
				if (inventoryDto.getQuantity() > 0) {
					if(!valueObject.getBoolean("assetNumberFlag",false)) {
						if (object.getDouble("partQty") <= inventoryDto.getQuantity() && inventoryDto.getSerialNoAddedForParts() <= 0) {
							useQuantity = object.getDouble("partQty");
						}else if(inventoryDto.getSerialNoAddedForParts() > 0) {
							useQuantity = (double) 0;
						}else {
							useQuantity = inventoryDto.getQuantity();
						}
					}else {
						useQuantity = object.getDouble("partQty");
					}
					
					inventoryService.Subtract_update_Inventory_from_Workorder(useQuantity,inventoryDto.getInventory_id(),valueObject.getInt("companyId",0));
					
					inventoryService.Subtract_update_InventoryLocation_from_Workorder(inventoryDto.getPartid(),inventoryDto.getLocationId(),valueObject.getInt("companyId",0));
						
					inventoryService.Subtract_update_InventoryAll_from_Workorder(inventoryDto.getPartid(),valueObject.getInt("companyId",0));
					
					partInvoiceRepository.update_anyPartNumberAsign_InPartInvoice(inventoryDto.getPartInvoiceId(),valueObject.getInt("companyId",0));
				
					repairToStockDetails.setInventoryLocationId(object.getLong("inventoryLocationId"));
					repairToStockDetails.setInventoryLocationPartId(inventoryDto.getInventory_id());
					break;
				}
			}
		}
		return repairToStockDetails;
	
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	
}	
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public ValueObject saveRepairToLabourDetails(ValueObject valueObject) throws Exception {
		ArrayList<ValueObject>					repairStockToLabourArrayObj			= null;
		RepairToLabourDetails					repairToLabourDetails	 		= null;
		List<RepairToLabourDetails>				repairToLabourDetailsList	 	= null;
		try {
			repairStockToLabourArrayObj 			= new ArrayList<>();
			repairToLabourDetailsList 				= new ArrayList<>();
			repairStockToLabourArrayObj				= (ArrayList<ValueObject>) valueObject.get("labourDetails");
			if(repairStockToLabourArrayObj != null && !repairStockToLabourArrayObj.isEmpty()) {
				for (ValueObject object : repairStockToLabourArrayObj) {
					repairToLabourDetails = repairBL.prepareRepairToLabourDetailsDetails(object,valueObject);
					repairToLabourDetailsList.add(repairToLabourDetails);
				}
				repairToLabourDetailsRepository.saveAll(repairToLabourDetailsList);
				
				if(valueObject.getBoolean("partWiseLabourCofig",false) && valueObject.getShort("repairStatusId",(short)0) == RepairConstant.REPAIR_STOCK_STATUS_OPEN) {
					/*****status Id of repairToStockDetails*/
					repairToStockDetailsRepository.updateStatusOfPart(RepairConstant.REPAIR_STOCK_STATUS_INPROCESS, valueObject.getLong("repairToStockDetailsId"),valueObject.getInt("companyId"));
				}
				repairStockRepository.updateLabourDiscountTaxTypeId(valueObject.getShort("finalLabourDiscountTaxTypId",(short)0),valueObject.getLong("repairStockId"),valueObject.getInt("companyId"));
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Transactional
	@Override
	public ValueObject removeAdditionalPart(ValueObject valueObject) throws Exception {
		try {
			if(valueObject.getShort("repairWorkshopId") == RepairConstant.REPAIR_WORKSHOP_OWN) {
				addPartToInventory(valueObject);
			}
			repairStockToPartDetailsRepository.removeAdditionalPart(valueObject.getLong("repairStockToPartDetailsId"),valueObject.getInt("companyId"));
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public ValueObject addPartToInventory(ValueObject valueObject) throws Exception {
		InventoryDto                        inventoryParts                      = null;
		try {
			
			inventoryParts = inventoryService.getInventory(valueObject.getLong("inventoryLocationPartId"),valueObject.getInt("companyId"));
		
			if (inventoryParts != null) {
			Double workOrdersQuantity = valueObject.getDouble("partQty");
			
			inventoryService.Add_update_Inventory_from_Workorder(workOrdersQuantity, inventoryParts.getInventory_id(),valueObject.getInt("companyId"));

			inventoryService.Add_update_InventoryLocation_from_Workorder(workOrdersQuantity, inventoryParts.getInventory_location_id(), valueObject.getInt("companyId"));
			
			inventoryService.Add_update_InventoryAll_from_Workorder(workOrdersQuantity, inventoryParts.getInventory_all_id(), valueObject.getInt("companyId"));
		}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	@Override
	public ValueObject removeLabour(ValueObject valueObject) throws Exception {
		try {
			repairToLabourDetailsRepository.removeLabour(valueObject.getLong("repairToLabourDetailsId"),valueObject.getInt("companyId"));
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Transactional
	@Override
	public ValueObject moveToCreatedRepairInvoice(ValueObject valueObject) throws Exception {
		List<RepairToStockDetailsDto>  repairStockList = null;
		List<RepairFromAssetPart> 			repairFromAssetPartList 	= null;
		try {
			StringBuilder builder = new StringBuilder();
				repairStockList = getRepairToStockDetailsById(valueObject);
				if(repairStockList != null && !repairStockList.isEmpty()) {
					for (RepairToStockDetailsDto dto : repairStockList) {
						if(dto.getRepairStatusId() != RepairConstant.REPAIR_STOCK_STATUS_REJECT) {
							builder.append(dto.getRepairStockPartId()+",");
							if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_PART_ID) {
								repairFromAssetPartList = repairFromAssetPartRepository.getAssetByStockToDetailsId(dto.getRepairToStockDetailsId());
								if(repairFromAssetPartList != null && !repairFromAssetPartList.isEmpty()) {
									valueObject.put("partQty", repairFromAssetPartList.size());
								}else {
									valueObject.put("partQty", 1);
								}
								valueObject.put("inventoryLocationPartId", dto.getInventoryLocationPartId());
								addPartToInventory(valueObject);
								
								partWarrantyDetailsService.updateRepairStatusByrepairToStockDetailsId((short)0, dto.getRepairToStockDetailsId());
							}
						}
					}
				}
				if(builder.length() > 0) {
					builder.deleteCharAt(builder.length()-1);
				}
				
				if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_TYRE_ID) {
					updateTyreDismountStatus(TyreAssignmentConstant.OLD_TYRE_MOVED_TO_REPAIR,builder.toString() );
				}else if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_BATTERY_ID) {
					updateBatteryDismountStatus(BatteryConstant.OLD_BATTERY_MOVED_TO_REPAIR,builder.toString() );
				}
		
			repairStockRepository.updatStatusToCreated(valueObject.getLong("repairStockId"),valueObject.getInt("companyId"));
			repairToStockDetailsRepository.removeAllStock(valueObject.getLong("repairStockId"),valueObject.getInt("companyId"));
			
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
		
	@Override
	public ValueObject completeRepair(ValueObject valueObject) throws Exception {
		List<RepairToStockDetailsDto>  		repairToStockDetailsList 	= null;
		List<RepairFromAssetPart> 			repairFromAssetPartList 	= null;
		ValueObject 						object						= null;
		Inventory 							savedInventory				= null;
		try {
			object 										= new ValueObject();
			List<Long> 		repairStockPartIds 			= new ArrayList<>() ;
			List<Long> 		repairToStockIds 			= new ArrayList<>() ;
			
			repairToStockDetailsList = getRepairToStockDetailsById(valueObject);
			
			if(repairToStockDetailsList != null && !repairToStockDetailsList.isEmpty()) {
				
				for (RepairToStockDetailsDto dto : repairToStockDetailsList) {
					
					if(dto.getRepairStatusId() != RepairConstant.REPAIR_STOCK_STATUS_REJECT) {
						repairToStockIds.add(dto.getRepairToStockDetailsId());
						repairStockPartIds.add(dto.getRepairStockPartId());
					}
				
					if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_PART_ID) {
						//Asset Number List of (Repair Stock Part )
						repairFromAssetPartList = repairFromAssetPartRepository.getAssetByStockToDetailsId(dto.getRepairToStockDetailsId());
						
						valueObject.put("repairFromAssetPartList", repairFromAssetPartList);
						valueObject.put("inventoryId", dto.getInventoryLocationPartId());
						valueObject.put("inventoryLocationId", dto.getInventoryLocationId());
						valueObject.put("partId", dto.getRepairStockPartId());
						
						object 			=	transferInventoryToRepairLocation(valueObject);
						savedInventory 	= 	(Inventory) object.get("savedInventory");
						
						updateTransferdInventoryId(dto.getRepairToStockDetailsId(),savedInventory.getInventory_id());
					}
				}
				if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_PART_ID) {
					partWarrantyDetailsService.transferAssetNumber(valueObject.getLong("repairStockId"),valueObject.getInt("toLocationId",0));
				}
			
				if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_TYRE_ID) {
					changeTyreLocation(InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE,TyreAssignmentConstant.REPAIR_COMPLETED,repairStockPartIds,valueObject.getInt("toLocationId",0) );
				}else if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_BATTERY_ID) {
					changeBatteryLocation(BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE,BatteryConstant.REPAIR_COMPLETED,repairStockPartIds,valueObject.getInt("toLocationId",0) );
				}
				
				if(repairToStockIds != null && !repairToStockIds.isEmpty()) {
					repairToStockDetailsRepository.updateStockStatus(RepairConstant.REPAIR_STOCK_STATUS_REPAIRED, repairToStockIds, valueObject.getInt("companyId"));
				}
				repairStockRepository.updatStatusToCompleted(formatDate.parse(valueObject.getString("completedDate")),valueObject.getString("remark"),valueObject.getLong("repairStockId"),valueObject.getInt("companyId"));
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ValueObject transferInventoryToRepairLocation(ValueObject valueObject) throws Exception {
		try {
			
			inventoryService.transferInventoryToRepairLocation(valueObject);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject reopenRepair(ValueObject valueObject) throws Exception {
		List<RepairToStockDetailsDto> 	repairStockList 			= null;
		List<RepairFromAssetPart> 		repairFromAssetPartList 	= null;
		try {
			List<Long> 	repairToStockPartIds 	= new ArrayList<>() ;
			List<Long> 	repairToStockIds 		= new ArrayList<>() ;
			
			repairStockList = getRepairToStockDetailsById(valueObject);
			
			if(repairStockList != null && !repairStockList.isEmpty()) {
				
				for (RepairToStockDetailsDto dto : repairStockList) {
					
					if(dto.getRepairStatusId() == RepairConstant.REPAIR_STOCK_STATUS_REPAIRED) {
						repairToStockIds.add(dto.getRepairToStockDetailsId());
					}
					repairToStockPartIds.add(dto.getRepairStockPartId());
					
					
					if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_PART_ID) {
						repairFromAssetPartList = repairFromAssetPartRepository.getAssetByStockToDetailsId(dto.getRepairToStockDetailsId());
						valueObject.put("repairFromAssetPartList", repairFromAssetPartList);
						valueObject.put("inventoryLocationId", dto.getInventoryLocationId());
						valueObject.put("inventory_id",dto.getTransferedInventoryId());
						valueObject.put("partId", dto.getRepairStockPartId());
					//	deleteTransferdInventoryIdFromInventory(dto.getTransferedInventoryId());
						removeTransferdRepairStock(valueObject,valueObject);
						
					}
					
					
					
				}
				if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_TYRE_ID) {
					changeTyreLocation(InventoryTyreDto.TYRE_ASSIGN_STATUS_UNAVAILABLE,TyreAssignmentConstant.REPAIR_IN_PROCESS,repairToStockPartIds,valueObject.getInt("fromLocationId") );
				}else if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_BATTERY_ID) {
					changeBatteryLocation(BatteryConstant.BATTERY_ASIGNED_STATUS_IN_UNAVAILABLE,BatteryConstant.REPAIR_IN_PROCESS,repairToStockPartIds,valueObject.getInt("fromLocationId") );
				}
				
				if(repairToStockIds != null && !repairToStockIds.isEmpty()) {
					repairToStockDetailsRepository.updateStockStatus(RepairConstant.REPAIR_STOCK_STATUS_INPROCESS,repairToStockIds, valueObject.getInt("companyId"));
				}
				repairStockRepository.updatStatusToSentToRepair(valueObject.getLong("repairStockId"),valueObject.getInt("companyId"));
				
				partWarrantyDetailsService.transferAssetNumber(valueObject.getLong("repairStockId"),valueObject.getInt("fromLocationId",0));
			}
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject rejectStock(ValueObject valueObject) throws Exception {
		try {
			if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_TYRE_ID) {
				if(valueObject.getShort("rejectStockMoveTo") == RepairConstant.REJECT_STOCK_MOVE_TO_SCRAPED) {
					tyreMoveToScrap(valueObject);
				}else {
					tyreMoveToRetread(valueObject);
				}
			}else if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_BATTERY_ID){
				batteryMoveToScrap(valueObject);
			}
			repairToStockDetailsRepository.updateStatusOfPart(RepairConstant.REPAIR_STOCK_STATUS_REJECT, valueObject.getLong("repairToStockDetailsId"), valueObject.getInt("companyId"));
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject searchRepairByNumber(ValueObject valueObject) throws Exception {
		try {
			RepairStock repairStock	= repairStockRepository.searchRepairByNumber(valueObject.getLong("repairNumber"), valueObject.getInt("companyId"));
			if(repairStock != null) {
				valueObject.put("repairStockId", repairStock.getRepairStockId());
			}else {
				valueObject.put("noRecordFound", true);
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public ValueObject batteryMoveToScrap(ValueObject valueObject) throws Exception {
		BatteryDto 				battery  			= null; 
		BatteryHistory 			batteryHistory 		= null;
		String					repairStockPartId 	= "";
		try {
			repairStockPartId = valueObject.getLong("repairStockPartId")+"";
			battery = batteryService.getBatteryInfo(valueObject.getLong("repairStockPartId"), valueObject.getInt("companyId"));
			batteryHistory = repairBL.prepareBatteryHistoryToScrap(battery,valueObject);
			batteryHistory.setCompanyId(valueObject.getInt("companyId"));
			batteryHistoryService.save(batteryHistory);
			
			batteryService.updateScrapRemark(valueObject.getLong("repairStockPartId"), valueObject.getString("rejectRemark"));
			batteryService.updateInventoryBatteryRejectStatus(valueObject.getLong("userId"),DateTimeUtility.getCurrentTimeStamp(), BatteryConstant.BATTERY_ASIGNED_STATUS_IN_SCRAPED,BatteryConstant.REPAIR_REJECT,
					repairStockPartId,valueObject.getInt("companyId"));
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Override
	public ValueObject tyreMoveToScrap(ValueObject valueObject) throws Exception {
		InventoryTyreDto 		inventoryTyreDto 	= null; 
		InventoryTyreHistory 	tyreHistory 		= null;
		String					repairStockPartId 	= "";
		try {
			inventoryTyreDto 	= inventoryTyreService.Get_Only_TYRE_ID_And_TyreSize(valueObject.getLong("repairStockPartId"), valueObject.getInt("companyId"));
			tyreHistory 		= repairBL.prepareInventoryTyreHistory(inventoryTyreDto,valueObject.getString("rejectRemark"));
			tyreHistory.setCOMPANY_ID(valueObject.getInt("companyId"));
			repairStockPartId = valueObject.getLong("repairStockPartId")+"";
			inventoryTyreService.add_Inventory_TYRE_History(tyreHistory);
			
			
			inventoryTyreService.updateInventoryTyreRejectStatus(valueObject.getLong("userId"),DateTimeUtility.getCurrentTimeStamp(), InventoryTyreDto.TYRE_ASSIGN_STATUS_SCRAPED,TyreAssignmentConstant.REPAIR_REJECT,
					repairStockPartId,valueObject.getInt("companyId"));
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public ValueObject tyreMoveToRetread(ValueObject valueObject) throws Exception {
		InventoryTyreRetread 		inventoryTyreRetread 		= null;
		String						repairStockPartId 			= "";
		InventoryTyreDto 			inventoryTyreDto 			= null;
		InventoryTyreRetreadAmount 	tyreRetreadAmount 			= null;
		InventoryTyreHistory 		tyreHistory 				= null; 
		try {
			inventoryTyreRetread = repairBL.prepare_Retread_Tyre(valueObject);
			inventoryTyreService.add_Inventory_Retread_Tyre(inventoryTyreRetread);
			
			inventoryTyreDto 	= inventoryTyreService.Get_Only_TYRE_ID_And_TyreSize(valueObject.getLong("repairStockPartId"), valueObject.getInt("companyId"));
			
			tyreRetreadAmount = repairBL.prepare_RetreadAmount_Tyre(inventoryTyreDto);
			
			tyreRetreadAmount.setInventoryTyreRetread(inventoryTyreRetread);
			
			
			
			inventoryTyreService.add_Inventory_Retread_Tyre_Amount(tyreRetreadAmount);
			
			tyreHistory = repairBL.prepare_InventoryTyreHistory_SENT_RETREAD(inventoryTyreDto);
			tyreHistory.setCOMPANY_ID(valueObject.getInt("companyId"));
			inventoryTyreService.add_Inventory_TYRE_History(tyreHistory);
			
			repairStockPartId = valueObject.getLong("repairStockPartId")+"";
			
			inventoryTyreService.Update_Inventory_Tyre_RetreadingStatus(inventoryTyreRetread.getTRID(), InventoryTyreDto.TYRE_ASSIGN_STATUS_SENT_RETREAD,
					repairStockPartId, valueObject.getInt("companyId"));
			
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
public void changeTyreLocation(short availableId,short tyreInProcess,List<Long> tyreIds,Integer locationId ) {
	try {
		entityManager.createQuery("Update InventoryTyre SET TYRE_ASSIGN_STATUS_ID ="+availableId+", WAREHOUSE_LOCATION_ID = "+locationId+", "
				+ " dismountedTyreStatusId ="+tyreInProcess+" WHERE TYRE_ID IN ("+tyreIds+") ").executeUpdate();
	
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}
public void changeBatteryLocation(short availableId,short batteryStatusId,List<Long>batteryIds,Integer locationId  ) {
	try {
		entityManager.createQuery("Update Battery SET batteryStatusId ="+availableId+", wareHouseLocationId = "+locationId+", "
				+ " dismountedBatteryStatusId ="+batteryStatusId+" WHERE batteryId IN ("+batteryIds+") ").executeUpdate();
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}
public void updateTyreDismountStatus(short batteryStatusId,String tyreIds ) {
	try {
		entityManager.createQuery("Update InventoryTyre SET dismountedTyreStatusId ="+batteryStatusId+" WHERE TYRE_ID IN ("+tyreIds+") ").executeUpdate();
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}
public void updateBatteryDismountStatus(short batteryStatusId,String batteryIds ) {
	try {
		entityManager.createQuery("Update Battery SET dismountedBatteryStatusId ="+batteryStatusId+" WHERE batteryId IN ("+batteryIds+") ").executeUpdate();
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}
public ValueObject getAssetNumber(ValueObject valueObject)throws Exception {
	List<PartWarrantyDetailsDto> 	assetNumberList 			= null;
	List<RepairFromAssetPartDto> 		repairFromAssetPartList 	= null;
	try {
		assetNumberList = new ArrayList<>();
		assetNumberList 		= partWarrantyDetailsService.getAssetNumberByPartIdAndInventoryId(valueObject.getLong("partId",0),valueObject.getLong("inventoryId",0),valueObject.getInt("companyId",0));
		repairFromAssetPartList = getRepairAssetNumber(valueObject.getLong("repairToStockDetailsId",0),valueObject.getInt("companyId",0));
		
		valueObject.put("assetNumberList", assetNumberList);
		valueObject.put("repairFromAssetPartList", repairFromAssetPartList);
		return valueObject;
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}
	
@SuppressWarnings({ "unchecked", "null" })
public ValueObject sentAssetNumber(ValueObject valueObject)throws Exception {
	ArrayList<ValueObject>		repairToStockArrayObj		= null;
	RepairFromAssetPart			repairFromAssetPart	 		= null;
	List<RepairFromAssetPart>	repairFromAssetPartList	 	= null;
	try {
		valueObject.put("partQty", valueObject.getLong("partQty"));
		calculateInventoryStockForRepairStock(valueObject, valueObject);
		
		repairFromAssetPartList = new ArrayList<>();
		repairToStockArrayObj	= (ArrayList<ValueObject>) valueObject.get("assetDetails");
		if(repairToStockArrayObj != null && !repairToStockArrayObj.isEmpty()) {
			for (ValueObject object : repairToStockArrayObj) {
				repairFromAssetPart = repairBL.prepareRepairFromAssetPart(object);
				repairFromAssetPartList.add(repairFromAssetPart);
				partWarrantyDetailsService.updateRepairStatus(object.getLong("assetId"),RepairConstant.SENT_MAIN_ASSET_TO_REPAIR ,object.getLong("repairToStockDetailsId"));
			}
			repairFromAssetPartRepository.saveAll(repairFromAssetPartList);
		}
		
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

public List<RepairFromAssetPartDto> getRepairAssetNumber(long repairToStockDetailsId, int companyId){
	 List<RepairFromAssetPartDto> 	repairFromAssetPartList = null;
	 RepairFromAssetPartDto			repairFromAssetPartDto	= null;
	 TypedQuery<Object[]> 		query 					= null;
	 try {
		repairFromAssetPartList = new ArrayList<>();

		query = entityManager.createQuery(
				  " SELECT RFP.repairFromAssetPartId, RFP.assetId, PW.partSerialNumber "
				+ " FROM RepairFromAssetPart AS RFP "
				+ " INNER JOIN PartWarrantyDetails AS PW ON PW.partWarrantyDetailsId = RFP.assetId "
				+ " where RFP.companyId = "+companyId+"  AND RFP.repairToStockDetailsId = "+repairToStockDetailsId+" AND RFP.markForDelete = 0 ",Object[].class);
		
		List<Object[]> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			repairFromAssetPartList = new ArrayList<>();
			
			for (Object[] result : results) {
				repairFromAssetPartDto = new RepairFromAssetPartDto();
				repairFromAssetPartDto.setRepairFromAssetPartId((Long)result[0]);
				repairFromAssetPartDto.setAssetId((Long)result[1]);
				repairFromAssetPartDto.setAssetNumber((String)result[2]);
				
				repairFromAssetPartList.add(repairFromAssetPartDto);
			}
		}
		return repairFromAssetPartList;
	
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

public ValueObject removeAsset(ValueObject valueObject)throws Exception {
	try {
		repairFromAssetPartRepository.removeAsset(valueObject.getLong("repairFromAssetPartId"));
		partWarrantyDetailsService.updateRepairStatus(valueObject.getLong("assetId"),(short)0 ,(long)0);
		
		valueObject.getLong("inventoryLocationPartId");
		addPartToInventory(valueObject);
		
		return valueObject;
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

public ValueObject getAssetNumberForAdditionalPart(ValueObject valueObject)throws Exception {
	List<PartWarrantyDetailsDto> 	assetNumberList 			= null;
	List<RepairToAssetPartDto> 		repairToAssetPartList 		= null;
	try {
		assetNumberList = new ArrayList<>();
		assetNumberList 		= partWarrantyDetailsService.getAssetNumberByPartIdAndInventoryId(valueObject.getLong("partId",0),valueObject.getLong("inventoryId",0),valueObject.getInt("companyId",0));
		repairToAssetPartList 	= getRepairToAssetNumber(valueObject.getLong("repairToStockDetailsId",0),valueObject.getInt("companyId",0));
		
		valueObject.put("assetNumberList", assetNumberList);
		valueObject.put("repairToAssetPartList", repairToAssetPartList);
		return valueObject;
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

public List<RepairToAssetPartDto> getRepairToAssetNumber(long repairToStockDetailsId, int companyId){
	 List<RepairToAssetPartDto> 	repairToAssetPartList = null;
	 RepairToAssetPartDto			repairToAssetPartDto	= null;
	 TypedQuery<Object[]> 			query 					= null;
	 try {
		 repairToAssetPartList = new ArrayList<>();
		

		query = entityManager.createQuery(
				  " SELECT RFP.repairToAssetPartId, RFP.assetId, PW.partSerialNumber "
				+ " FROM RepairToAssetPart AS RFP "
				+ " INNER JOIN PartWarrantyDetails AS PW ON PW.partWarrantyDetailsId = RFP.assetId "
				+ " where RFP.companyId = "+companyId+"  AND RFP.repairStockToPartDetailsId = "+repairToStockDetailsId+" AND RFP.markForDelete = 0 ",Object[].class);
		
		List<Object[]> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			repairToAssetPartList = new ArrayList<>();
			
			for (Object[] result : results) {
				repairToAssetPartDto = new RepairToAssetPartDto();
				repairToAssetPartDto.setRepairToAssetPartId((Long)result[0]);
				repairToAssetPartDto.setAssetId((Long)result[1]);
				repairToAssetPartDto.setAssetNumber((String)result[2]);
				
				repairToAssetPartList.add(repairToAssetPartDto);
			}
		}
		return repairToAssetPartList;
	
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

@SuppressWarnings("unchecked")
public ValueObject saveAdditionalAsset(ValueObject valueObject)throws Exception {
	ArrayList<ValueObject>		repairToStockArrayObj		= null;
	RepairToAssetPart			repairToAssetPart	 		= null;
	List<RepairToAssetPart>		repairToAssetPartList	 	= null;
	try {
		repairToAssetPartList = new ArrayList<>();
		repairToStockArrayObj	= (ArrayList<ValueObject>) valueObject.get("assetDetails");
		if(repairToStockArrayObj != null && !repairToStockArrayObj.isEmpty()) {
			for (ValueObject object : repairToStockArrayObj) {
				repairToAssetPart = repairBL.prepareRepairToAssetPart(object);
				repairToAssetPartList.add(repairToAssetPart);
				
				partWarrantyDetailsService.updateRepairStatus(object.getLong("assetId"),RepairConstant.SENT_ADDITIONAL_ASSET_TO_REPAIR ,object.getLong("repairToStockDetailsId"));
			}
			repairToAssetPartRepository.saveAll(repairToAssetPartList);
		}
		
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

public ValueObject removeAdditionalAsset(ValueObject valueObject)throws Exception {
	try {
		repairToAssetPartRepository.removeAdditionalAsset(valueObject.getLong("repairToAssetPartId"));
		partWarrantyDetailsService.updateRepairStatus(valueObject.getLong("assetId"),(short)0 ,(long)0);
		
		return valueObject;
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

@SuppressWarnings("unchecked")
@Transactional
public ValueObject removeTransferdRepairStock(ValueObject object, ValueObject valueObject) throws Exception {
	List<InventoryDto> 					inventoryList						= null;
	String 								subLocationQuery 					= "";
	Double								useQuantity							= null;
	RepairToStockDetails				repairToStockDetails				= null;
	List<RepairFromAssetPart> 			repairFromAssetPartList 			= null;
	Inventory 							inventory							= null;
	try {
		repairFromAssetPartList = new ArrayList<>();
		repairToStockDetails 	= new RepairToStockDetails();
	//	subLocationQuery 		= " AND I.inventory_id <> "+object.getLong("inventory_id")+" ";
		
	//	inventory				= inventoryService.getInventoryDetails(object.getLong("inventory_id"), valueObject.getInt("companyId",0));
	//	inventoryList 			= inventoryService.Get_WorkOrder_InventoryLocation_id_To_Inventory(inventory.getInventory_location_id(),valueObject.getInt("companyId",0),subLocationQuery);
		
		
		repairFromAssetPartList = (List<RepairFromAssetPart>) valueObject.get("repairFromAssetPartList");
		
		if(repairFromAssetPartList != null && !repairFromAssetPartList.isEmpty()) {
			useQuantity = repairFromAssetPartList.size()+0.0;
		}else {
			useQuantity = 1.0;
		}
		
		inventoryService.deleteTransferdInventoryIdFromInventory(object.getLong("inventory_id"));
		
		inventoryService.update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(object.getLong("partId"),  valueObject.getInt("toLocationId"));
		
		inventoryService.update_InventoryAll_PARTID_PARTNAME_GENRAL(object.getLong("partId"));
		/*if(inventoryList != null  && !inventoryList.isEmpty()) {
			for (InventoryDto inventoryDto : inventoryList) {
				
				if (inventoryDto.getQuantity() > 0) {
					
			//		useQuantity = object.getDouble("partQty");
					
					
					
				//	inventoryService.Subtract_update_Inventory_from_Workorder(useQuantity,inventoryDto.getInventory_id(),valueObject.getInt("companyId",0));
					
				//	inventoryService.Subtract_update_InventoryLocation_from_Workorder(inventoryDto.getPartid(),inventoryDto.getLocationId(),valueObject.getInt("companyId",0));
						
				//	inventoryService.Subtract_update_InventoryAll_from_Workorder(inventoryDto.getPartid(),valueObject.getInt("companyId",0));
					
					partInvoiceRepository.update_anyPartNumberAsign_InPartInvoice(inventoryDto.getPartInvoiceId(),valueObject.getInt("companyId",0));
				
					repairToStockDetails.setInventoryLocationId(object.getLong("inventoryLocationId"));
					repairToStockDetails.setInventoryLocationPartId(inventoryDto.getInventory_id());
					break;
				}
			}
		}*/
		return valueObject;
	
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	
}

public void updateTransferdInventoryId(long repairToStockDetailsId, long transferedInventoryId ) {
	
	try {
		repairToStockDetailsRepository.updateTransferdInventoryId(repairToStockDetailsId,transferedInventoryId);
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}
public void deleteTransferdInventoryIdFromInventory(long repairToStockDetailsId)throws Exception {
	
	try {
		inventoryService.deleteTransferdInventoryIdFromInventory(repairToStockDetailsId);
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}
public ValueObject getRepairStockReportData(ValueObject valueObject)throws Exception {
	RepairToStockDetailsDto			repairToStockDetails			= null;
	List<RepairToStockDetailsDto>	repairToStockDetailsList		= null;
	TypedQuery<Object[]> 			query							= null;
	List<Object[]> 					results							= null;	
	ValueObject						tableConfig						= null;
	String							queryStr						= null;
	String 							stockTypeStr					= "";
	
	try {	
		tableConfig					= new ValueObject();
		
		if(valueObject.getShort("repairStockTypeId",(short)0) == 1 &&  valueObject.getLong("partId",0) > 0) {
			stockTypeStr   	= " AND RTD.repairStockPartId = "+valueObject.getLong("partId")+" ";
		}else if(valueObject.getShort("repairStockTypeId",(short)0) == 3 &&  valueObject.getLong("batteryId",0) > 0) {
			stockTypeStr   	= " AND RTD.repairStockPartId = "+valueObject.getLong("batteryId")+" ";
		}else if(valueObject.getShort("repairStockTypeId",(short)0) == 2 &&  valueObject.getLong("tyreId",0) > 0) {
			stockTypeStr   	= " AND RTD.repairStockPartId = "+valueObject.getLong("tyreId")+" ";
		}
		queryStr  = " "+stockTypeStr+" " ;
	 
		query = entityManager.createQuery(
			" SELECT RTD.repairToStockDetailsId, RTD.repairStockId, RTD.repairStockPartId, "
				+ " RTD.repairStockStatusId, RTD.workDetails, RS.dateOfSent, PL.partlocation_name, "
				+ " RS.repairNumber, RTD.repairStockPartId, MP.partnumber, MP.partname, B.batterySerialNumber, IT.TYRE_NUMBER   FROM RepairToStockDetails AS RTD "
				+ " INNER JOIN RepairStock AS RS ON RS.repairStockId = RTD.repairStockId "
				+ " INNER JOIN PartLocations AS PL ON PL.partlocation_id = RS.additionalPartLocationId "
				+ " LEFT JOIN MasterParts AS MP ON MP.partid = RTD.repairStockPartId  "
				+ " LEFT JOIN Battery B ON B.batteryId = RTD.repairStockPartId "
				+ " LEFT JOIN InventoryTyre IT ON IT.TYRE_ID = RTD.repairStockPartId "
				+ " WHERE RS.repairTypeId = "+valueObject.getLong("repairStockTypeId")+" AND RS.locationId = "+valueObject.getLong("locationId")+" "
				+ " AND RS.repairWorkshopId = "+valueObject.getLong("workShopId")+" "+queryStr+" ", Object[].class);
		
		results = query.getResultList();
		
		if (results != null && !results.isEmpty()) {
			repairToStockDetailsList = new ArrayList<>();
			
			for (Object[] result : results) {
				repairToStockDetails = new RepairToStockDetailsDto();
				repairToStockDetails.setRepairToStockDetailsId((Long)result[0]);
				repairToStockDetails.setRepairStockId((Long)result[1]);
				repairToStockDetails.setRepairStockPartId((Long)result[2]);
				repairToStockDetails.setRepairStockStatusId((short)result[3]);
				repairToStockDetails.setWorkDetails((String)result[4]);
				if(result[5] != null) {
					repairToStockDetails.setDateOfSentStr(format.format((Date) result[5]));
				}
				
				repairToStockDetails.setAdditionalPartDetails("<a style=\"color: blue; background: #ffc;\" onclick=showAdditionalPartDetails("+repairToStockDetails.getRepairToStockDetailsId()+") href=\"javascript:void(0)\" >Additional Parts</a>");
				repairToStockDetails.setLabourDetails("<a style=\"color: blue; background: #ffc;\" onclick=showLabourDetails("+repairToStockDetails.getRepairStockId()+") href=\"javascript:void(0)\" > Labour </a>");	
				repairToStockDetails.setAdditionalPartLocation((String)result[6]);
				repairToStockDetails.setRepairNumberStr("R-"+(Long)result[7]);
				if(result[8] != null) {
					repairToStockDetails.setRepairStockPartId((Long)result[8]);
					repairToStockDetails.setRepairStockPart((String)result[9]+""+(String)result[10]);
				}
				if(result[11] != null) {
					repairToStockDetails.setRepairStockPart((String)result[11]);
				}
				if(result[12] != null) {
					repairToStockDetails.setRepairStockPart((String)result[12]);
				}
				repairToStockDetailsList.add(repairToStockDetails);
			}
		}
		
		tableConfig.put("companyId", valueObject.getInt("companyId"));
		tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.REPAIR_STOCK_FILE_PATH);
		tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
		tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
		
		valueObject.put("tableConfig", tableConfig.getHtData());
		valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(valueObject.getLong("userId")));
		valueObject.put("repairToStockDetailsList", repairToStockDetailsList);
		
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}


@Override
public ValueObject getAdditionalPartDetails(ValueObject valueObject) throws Exception {
	List<RepairStockToPartDetailsDto>  repairStockToPartDetailsList = null;
	try {
		repairStockToPartDetailsList = new ArrayList<>();
		repairStockToPartDetailsList = getAdditionalPartDetailsByRepairToStockId(valueObject);
		valueObject.put("additionalPartDetails", repairStockToPartDetailsList);
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

public List<RepairStockToPartDetailsDto> getAdditionalPartDetailsByRepairToStockId(ValueObject valueObject) throws Exception {
	List<RepairStockToPartDetailsDto> 		repairStockToPartDetailsList 		= null;
	RepairStockToPartDetailsDto 			repairStockToPartDetails			= null;
	TypedQuery<Object[]> 		query 					= null;
	try {
		query = entityManager.createQuery(
				  " SELECT RTS.repairStockToPartDetailsId, RTS.partId,M.partname,  M.partnumber, RTS.unitCost, RTS.quantity, RTS.gst, "
				+ " RTS.discount, RTS.totalCost, RTS.repairToStockDetailsId,  M1.partname, M1.partnumber, B.batterySerialNumber, IT.TYRE_NUMBER, RTS.inventoryLocationId "
				+ " FROM RepairStockToPartDetails AS RTS "
				+ " INNER JOIN RepairToStockDetails RS ON RS.repairToStockDetailsId = RTS.repairToStockDetailsId "
				+ " LEFT JOIN MasterParts M ON M.partid = RTS.partId "
				+ " LEFT JOIN MasterParts M1 ON M1.partid = RS.repairStockPartId "
				+ " LEFT JOIN Battery B ON B.batteryId = RS.repairStockPartId "
				+ " LEFT JOIN InventoryTyre IT ON IT.TYRE_ID = RS.repairStockPartId "
				+ " where RTS.companyId = "+valueObject.getInt("companyId")+" AND RS.repairToStockDetailsId = "+valueObject.getLong("repairToStockDetailsId")+" AND  RTS.markForDelete = 0 ",Object[].class);
		
		List<Object[]> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			repairStockToPartDetailsList = new ArrayList<>();
			
			for (Object[] result : results) {
				repairStockToPartDetails = new RepairStockToPartDetailsDto();
				repairStockToPartDetails.setRepairStockToPartDetailsId((Long)result[0]);
				repairStockToPartDetails.setPartId((Long)result[1]);
				repairStockToPartDetails.setPartName((String)result[2]+ " "+ (String)result[3]);
				repairStockToPartDetails.setUnitCost((Double)result[4]);
				repairStockToPartDetails.setQuantity((Double)result[5]);
				repairStockToPartDetails.setDiscount((Double)result[6]);
				repairStockToPartDetails.setGst((Double)result[7]);
				repairStockToPartDetails.setTotalCost((Double)result[8]);
				repairStockToPartDetails.setRepairToStockDetailsId((Long)result[9]);
				if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_PART_ID && result[10] != null) {
					repairStockToPartDetails.setRepairToStockName((String)result[10] +" "+(String)result[11]);
				}else if(valueObject.getShort("repairTypeId") == RepairConstant.REPAIR_TYPE_TYRE_ID && result[12] != null){
					repairStockToPartDetails.setRepairToStockName((String)result[12]);
				}else {
					if(result[13] != null)
						repairStockToPartDetails.setRepairToStockName((String)result[13]);
				}
				if(result[14] != null) {
					repairStockToPartDetails.setInventoryLocationId((Long)result[14]);
				}
				repairStockToPartDetailsList.add(repairStockToPartDetails);
				
			}
		}
		
		return repairStockToPartDetailsList;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	
}

@Override
public ValueObject getAdditionalLabourDetails(ValueObject valueObject) throws Exception {
	List<RepairToLabourDetailsDto>  additionalLabourDetails = null;
	try {
		additionalLabourDetails = new ArrayList<>();
		additionalLabourDetails = getRepairStockToLabourDetailsList(valueObject);
		valueObject.put("additionalLabourDetails", additionalLabourDetails);
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

}