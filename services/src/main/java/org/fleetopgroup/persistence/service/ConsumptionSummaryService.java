/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.ConsumptionSummaryConstant;
import org.fleetopgroup.persistence.dao.BatteryHistoryRepository;
import org.fleetopgroup.persistence.dao.FuelRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreHistoryRepository;
import org.fleetopgroup.persistence.dao.RefreshmentEntryRepository;
import org.fleetopgroup.persistence.dao.ServiceEntriesTasksToPartsRepository;
import org.fleetopgroup.persistence.dao.UreaEntriesRepository;
import org.fleetopgroup.persistence.dao.VehicleClothInventoryHistoryRepository;
import org.fleetopgroup.persistence.dao.WorkOrdersTasksToPartsRepository;
import org.fleetopgroup.persistence.dto.BatteryHistoryDto;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.InventoryTyreHistoryDto;
import org.fleetopgroup.persistence.dto.RefreshmentEntryDto;
import org.fleetopgroup.persistence.dto.UreaEntriesDto;
import org.fleetopgroup.persistence.dto.VehicleClothInventoryHistoryDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToPartsDto;
import org.fleetopgroup.persistence.model.BatteryHistory;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.InventoryTyreHistory;
import org.fleetopgroup.persistence.model.RefreshmentEntry;
import org.fleetopgroup.persistence.model.ServiceEntriesTasksToParts;
import org.fleetopgroup.persistence.model.UreaEntries;
import org.fleetopgroup.persistence.model.VehicleClothInventoryHistory;
import org.fleetopgroup.persistence.model.WorkOrdersTasksToParts;
import org.fleetopgroup.persistence.report.dao.IFuelReportDao;
import org.fleetopgroup.persistence.report.dao.IPartReportDao;
import org.fleetopgroup.persistence.serviceImpl.IBatteryHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IConsumptionSummaryService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IRefreshmentEntryService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IUreaEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleClothInventoryHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("ConsumptionSummaryService")
@Transactional
public class ConsumptionSummaryService implements IConsumptionSummaryService {
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired  public IFuelReportDao								fuelReportService;
	@Autowired  public IUreaEntriesService							ureaEntriesService;
	@Autowired  public IVehicleClothInventoryHistoryService			clothInventoryHistoryService;
	@Autowired 	IPartReportDao										partReportDao;
	@Autowired  public IWorkOrdersService							WO_Service;
	@Autowired  public IServiceEntriesService						SE_Service;
	@Autowired  public IInventoryService							inventoryService;
	@Autowired  public IBatteryHistoryService						batteryInvoiceService;
	@Autowired  public IInventoryTyreService						inventoryTyreService;
	@Autowired  public IRefreshmentEntryService						refreshmentEntryService;
	
	@Autowired  public FuelRepository								fuelRepository;
	@Autowired  public UreaEntriesRepository							ureaEntriesRepository;
	@Autowired  public VehicleClothInventoryHistoryRepository			clothInventoryHistoryRepository;
	@Autowired  public WorkOrdersTasksToPartsRepository			workOrdersTasksToPartsRepository;
	@Autowired  public ServiceEntriesTasksToPartsRepository			serviceEntriesTasksToPartsRepository;
	@Autowired  public InventoryTyreHistoryRepository			inventoryTyreHistoryRepository;
	@Autowired  public BatteryHistoryRepository			batteryHistoryRepository;
	@Autowired  public RefreshmentEntryRepository			refreshmentEntryRepository;
	
	
	private static final int PAGE_SIZE 					= 10;
	@Override
	public ValueObject getFuelConsumptionCount(ValueObject object) throws Exception {
		String 		queryStr 				= "";
		
		try {
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {
				queryStr = " AND F.fuel_date between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}else{
				queryStr = " AND F.created between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}
			
			Query query = entityManager.createQuery(
					"SELECT COUNT(*),sum(F.fuel_liters) From Fuel as F "
						+ " WHERE F.companyId = "+object.getInt("companyId")+" "+queryStr+" AND F.markForDelete = 0 ");
			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			
			if (result != null) {
				object.put("fuelEntryCount", (Long) result[0]);
				object.put("fuelConsumptionCount", (Double) result[1]);
			}
			
			return object;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	@Override
	public ValueObject getUreaConsumptionCount(ValueObject object) throws Exception {
		String 		queryStr 				= "";
		
		try {
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {
				queryStr = " AND U.ureaDate between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}else{
				queryStr = " AND U.created between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}
			
			Query query = entityManager.createQuery(
					"SELECT COUNT(*),sum(U.ureaLiters) From UreaEntries as U "
						+ " WHERE U.companyId = "+object.getInt("companyId")+" "+queryStr+" AND U.markForDelete = 0 ");

			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			
			if (result != null) {
				object.put("ureaEntryCount", (Long) result[0]);
				object.put("ureaConsumptionCount", (Double) result[1]);
			}
			return object;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	@Override
	public ValueObject getUpholsteryConsumptionCount(ValueObject object) throws Exception {
		String 		queryStr 				= "";
		try {
			if(object.getString("startDate","") != "" && object.getString("endDate,","") != "") {
				queryStr = " AND VC.createdOn between '"+object.getString("startDate","")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate,","")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}
			
			Query query = entityManager.createQuery(
					"SELECT COUNT(*),sum(VC.quantity) From VehicleClothInventoryHistory as VC "
						+ " WHERE VC.companyId = "+object.getInt("companyId")+" "+queryStr+" AND VC.markForDelete = 0 ");
			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			
			if (result != null) {
				object.put("upholteryEntryCount", (Long) result[0]);
				object.put("upholsteryConsumptionCount", (Double) result[1]);
			}
			return object;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	@Override
	public ValueObject getPartConsumptionCount(ValueObject object) throws Exception {
		Long 		WO_PartEntryCount 	= (long) 0;
		Long 		SE_PartEntryCount 	= (long) 0;
		String 		queryStr 				= "";
		
		try {
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {
			queryStr = " AND WS.completed_date between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}else{
				queryStr = " AND WS.created between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}
			object.put("queryStr", queryStr);
			
			WO_PartEntryCount = getWO_PartConsumptionCount(object).getLong("WO_PartEntryCount");
			SE_PartEntryCount = getSE_PartConsumptionCount(object).getLong("SE_PartEntryCount");
			
			
			object.put("WO_PartEntryCount", WO_PartEntryCount);
			object.put("SE_PartConsumptionCount", SE_PartEntryCount);
			object.put("WO_PartConsumptionCount", getWO_PartConsumptionCount(object).getDouble("WO_PartConsumptionCount"));
			object.put("SE_PartConsumptionCount", getSE_PartConsumptionCount(object).getDouble("SE_PartConsumptionCount"));
			object.put("totalPartConsumptionCount", (WO_PartEntryCount+SE_PartEntryCount));
			
			return object;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	public ValueObject getWO_PartConsumptionCount(ValueObject object) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT COUNT(*),sum(WTP.quantity) From WorkOrdersTasksToParts as WTP "
						+ " INNER JOIN WorkOrders WS ON WS.workorders_id= WTP.workorders_id "
						+ " INNER JOIN MasterParts MP ON MP.partid = WTP.partid "
						+ " WHERE WS.companyId = "+object.getInt("companyId")+" "+object.getString("queryStr")+" AND  WTP.markForDelete = 0 ");
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			
			if (result != null) {
				object.put("WO_PartEntryCount", (Long) result[0]);
				object.put("WO_PartConsumptionCount", (Double) result[1]);
			}
			return object;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public ValueObject getSE_PartConsumptionCount(ValueObject object) throws Exception {
		try {
			Query query = entityManager.createQuery(
					
					"SELECT COUNT(*),sum(WTP.quantity) From ServiceEntriesTasksToParts as WTP "
						+ " INNER JOIN ServiceEntries WS ON WS.serviceEntries_id= WTP.serviceEntries_id "
						+ " INNER JOIN MasterParts MP ON MP.partid = WTP.partid "
						+ " WHERE  WS.companyId = "+object.getInt("companyId")+" "+object.getString("queryStr","")+" AND WTP.markForDelete = 0 ");
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			
			if (result != null) {
				object.put("SE_PartEntryCount", (Long) result[0]);
				object.put("SE_PartConsumptionCount", (Double) result[1]);
			}
			return object;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	
	@Override
	public Long getTyreConsumptionCount(ValueObject object) throws Exception {
		Long 		tyreConsumpionCount 	= (long) 0;
		String 		queryStr 				= "";
		
		try {
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {
				queryStr = " AND IH.TYRE_ASSIGN_DATE between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}else{
				queryStr = " AND IH.createdOn between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}
			
			Query query = entityManager.createQuery(
					"SELECT COUNT(*) From InventoryTyreHistory as IH "
					+ " INNER JOIN Vehicle as V on V.vid = IH.VEHICLE_ID "
						+ " WHERE IH.COMPANY_ID = "+object.getInt("companyId")+" "+queryStr+" AND IH.markForDelete = 0 ");
			
			try {
				
				if((Long) query.getSingleResult() != null) {
					tyreConsumpionCount = (Long) query.getSingleResult();
				}
				
			} catch (NoResultException nre) {
			}
			return tyreConsumpionCount;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Override
	public Long getBatteryConsumptionCount(ValueObject object) throws Exception {
		Long 		batteryConsumpionCount 	= (long) 0;
		String 		queryStr 				= "";
		
		try {
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {
				queryStr = " AND BH.batteryAsignDate between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}else{
				queryStr = " AND BH.createdOn between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}
			
			Query query = entityManager.createQuery(
					"SELECT COUNT(*) From BatteryHistory as BH "
							+ " WHERE BH.companyId = "+object.getInt("companyId")+" "+queryStr+" AND  BH.markForDelete = 0 ");
			
			try {
				
				if((Long) query.getSingleResult() != null) {
					batteryConsumpionCount = (Long) query.getSingleResult();
				}
				
			} catch (NoResultException nre) {
			}
			return batteryConsumpionCount;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Override
	public ValueObject getRefreshmentConsumptionCount(ValueObject object) throws Exception {
		String 		queryStr 				= "";
		
		try {
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {
				queryStr = " AND R.asignmentDate between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}else{
				queryStr = " AND R.created between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}
			
			Query query = entityManager.createQuery(
					"SELECT COUNT(*),sum(R.quantity)-sum(R.returnQuantity) From RefreshmentEntry as R "
							+ " WHERE R.companyId = "+object.getInt("companyId",0)+" "+queryStr+" AND R.markForDelete = 0 ");
			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			
			if (result != null) {
				object.put("refreshmentEntryCount", (Long) result[0]);
				object.put("refreshmentConsumptionCount", (Double) result[1]);
			}
			return object;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	/*Consumption summary list*/
	@Override
	public ValueObject getConsumptionSummaryData(ValueObject object) throws Exception {
		try {
			switch (object.getShort("consumptionType")) {
			case ConsumptionSummaryConstant.CONSUMPTION_TYPE_FUEL:
				object =	getFuelConsumption(object);
				break;
			case ConsumptionSummaryConstant.CONSUMPTION_TYPE_UREA:
				object =	getUreaConsumption(object);
				break;
			case ConsumptionSummaryConstant.CONSUMPTION_TYPE_UPHOLSTERY:
				object =	getUpholsteryConsumption(object);
				break;
			case ConsumptionSummaryConstant.CONSUMPTION_TYPE_WO:
				object =	getWorkOrderConsumption(object);
				break;
			case ConsumptionSummaryConstant.CONSUMPTION_TYPE_SE:
				object =	getServiceEntryConsumption(object);
				break;
			case ConsumptionSummaryConstant.CONSUMPTION_TYPE_TYRE:
				object =	getTyreConsumption(object);
				break;
			case ConsumptionSummaryConstant.CONSUMPTION_TYPE_BATTERY:
				object =	getBatteryConsumption(object);
				break;
				
			case ConsumptionSummaryConstant.CONSUMPTION_TYPE_REFRESHMENT:
				object =	getRefreshmentConsumption(object);
				break;

			default:
				break;
			}
			
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public ValueObject getFuelConsumption(ValueObject object)throws Exception{
		String 			queryStr 			= "";
		List<FuelDto> 	fuelList 			= null;
	/* Integer			pageNumber			= null;
		Page<Fuel> 		page 				= null;*/
		try {
			
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {	
				queryStr = " AND F.fuel_date between '"+object.getString("startDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+""+DateTimeUtility.DAY_END_TIME+"' ";
			}else{
				queryStr = " AND F.created between '"+object.getString("startDate")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+""+DateTimeUtility.DAY_END_TIME+"' ";
			}
			object.put("queryStr", queryStr);
			
			/*pageNumber		= object.getInt("pageNumber",0);
			page 			= getDeploymentPageFuelConsumption(object);
			
			int current 	= page.getNumber() + 1;
			int begin 		= Math.max(1, current - 5);
			int end 		= Math.min(begin + 10, page.getTotalPages());
	
			object.put("deploymentLog", page);
			object.put("beginIndex", begin);
			object.put("endIndex", end);
			object.put("currentIndex", current);*/
			
			fuelList 		= fuelReportService.getFuelConsumptionList(object);
			/*object.put("fuelList", fuelList);
			object.put("SelectPage", pageNumber);*/
			object.put("entryCount", getFuelConsumptionCount(object).get("fuelEntryCount"));
			object.put("totalConsumption", getFuelConsumptionCount(object).get("fuelConsumptionCount"));
			object.put("fuelList", fuelList);
			return object;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	public Page<Fuel> getDeploymentPageFuelConsumption(ValueObject object) throws Exception {		
		
		try {
			PageRequest pageable = new PageRequest(object.getInt("pageNumber") - 1, PAGE_SIZE, Sort.Direction.DESC, "fuel_id");
			Timestamp 	fromDate = DateTimeUtility.getTimeStamp(object.getString("startDate"), DateTimeUtility.YYYY_MM_DD);
			Timestamp 	toDate   = DateTimeUtility.getTimeStamp(object.getString("endDate"), DateTimeUtility.YYYY_MM_DD);
			
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {	
				return fuelRepository.getPageFuelConsumptionByTransactionDate(fromDate,toDate,object.getInt("companyId"), pageable);
			}else {
				return fuelRepository.getPageFuelConsumptionByCreatedDate(fromDate,toDate,object.getInt("companyId"), pageable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public ValueObject getUreaConsumption(ValueObject object)throws Exception{
		String 					queryStr 			= "";
		List<UreaEntriesDto> 	ureaList 			= null;
		/*Integer					pageNumber			= null;
		Page<UreaEntries> 		page 				= null;*/
		try {
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {
				queryStr = " AND U.ureaDate between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}else{
				queryStr = " AND U.created between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}
			object.put("queryStr", queryStr);
			
			/*pageNumber		= object.getInt("pageNumber",0);
			page 			= getDeploymentPageUreaConsumption(object);
			
			int current 	= page.getNumber() + 1;
			int begin 		= Math.max(1, current - 5);
			int end 		= Math.min(begin + 10, page.getTotalPages());
			
	
			object.put("deploymentLog", page);
			object.put("beginIndex", begin);
			object.put("endIndex", end);
			object.put("SelectPage", pageNumber);
			object.put("currentIndex", current);*/
			ureaList	 	= ureaEntriesService.getUreaConsumptionList(object);
			object.put("ureaList", ureaList);
			object.put("entryCount", getUreaConsumptionCount(object).get("ureaEntryCount"));
			
			return object;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	public Page<UreaEntries> getDeploymentPageUreaConsumption(ValueObject object) throws Exception {	
		try {
			PageRequest pageable 	= new PageRequest(object.getInt("pageNumber") - 1, PAGE_SIZE, Sort.Direction.DESC, "ureaEntriesId");
			Timestamp 	fromDate 	= DateTimeUtility.getTimeStamp(object.getString("startDate"), DateTimeUtility.YYYY_MM_DD);
			Timestamp 	toDate   	= DateTimeUtility.getTimeStamp(object.getString("endDate"), DateTimeUtility.YYYY_MM_DD);
			
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {	
				return ureaEntriesRepository.getPageUreaConsumptionByTransactionDate(fromDate,toDate,object.getInt("companyId"), pageable);
			}else {
				return ureaEntriesRepository.getPageUreaConsumptionByCreatedDate(fromDate,toDate,object.getInt("companyId"), pageable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public ValueObject getUpholsteryConsumption(ValueObject object)throws Exception{
		String 									queryStr 			= "";
		List<VehicleClothInventoryHistoryDto> 	upholsteryList  	= null;
		Integer									pageNumber			= null;
		Page<VehicleClothInventoryHistory> 		page 				= null;
		try {
			if(object.getString("startDate","") != "" && object.getString("endDate,","") != "") {
				queryStr = " AND VC.createdOn between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}
			object.put("queryStr", queryStr);

			pageNumber		= object.getInt("pageNumber",0);
			page 			= getDeploymentPageUpholsteryConsumption(object);
			
			int current 	= page.getNumber() + 1;
			int begin 		= Math.max(1, current - 5);
			int end 		= Math.min(begin + 10, page.getTotalPages());
			
	
			object.put("deploymentLog", page);
			object.put("beginIndex", begin);
			object.put("endIndex", end);
			object.put("currentIndex", current);
			
			upholsteryList 	= clothInventoryHistoryService.getUpholsteryConsumptionList(object);
			object.put("upholsteryList", upholsteryList);
			object.put("SelectPage", pageNumber);
			
			object.put("entryCount", getUpholsteryConsumptionCount(object).getInt("upholteryEntryCount"));
			object.put("totalConsumptionCount", getUpholsteryConsumptionCount(object).getDouble("upholsteryConsumptionCount"));
			return object;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	public Page<VehicleClothInventoryHistory> getDeploymentPageUpholsteryConsumption(ValueObject object) throws Exception {		

		try {
			PageRequest pageable = new PageRequest(object.getInt("pageNumber") - 1, PAGE_SIZE, Sort.Direction.DESC, "vehicleClothInventoryHistoryId");
			Timestamp fromDate 	= DateTimeUtility.getTimeStamp(object.getString("startDate"), DateTimeUtility.YYYY_MM_DD);
			Timestamp toDate   	= DateTimeUtility.getTimeStamp(object.getString("endDate"), DateTimeUtility.YYYY_MM_DD);
			
			return clothInventoryHistoryRepository.getPageUpholsteryConsumption(fromDate,toDate,object.getInt("companyId"), pageable);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public ValueObject getWorkOrderConsumption(ValueObject object)throws Exception{
		String 								queryStr 			= "";
		List<WorkOrdersTasksToPartsDto> 	workorderList 		= null;
		/*Integer								pageNumber			= null;
		Page<WorkOrdersTasksToParts> 		page 				= null;*/
		try {
			//do not change alias name
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {
				queryStr = " AND WS.completed_date between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}else{
				queryStr = " AND WS.created between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}
			object.put("queryStr", queryStr);
		
			/*pageNumber		= object.getInt("pageNumber",0);
			page 			= getDeploymentPageWorkOrderConsumption(object);
			
			int current 	= page.getNumber() + 1;
			int begin 		= Math.max(1, current - 5);
			int end 		= Math.min(begin + 10, page.getTotalPages());
	
			object.put("deploymentLog", page);
			object.put("beginIndex", begin);
			object.put("endIndex", end);
			object.put("SelectPage", pageNumber);
			object.put("currentIndex", current);*/
			
			workorderList 	= partReportDao.getWOPartConsumptionList(object);
			object.put("workOrderList", workorderList);
			object.put("entryCount", getWO_PartConsumptionCount(object).getDouble("WO_PartEntryCount"));
				return object;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	public Page<WorkOrdersTasksToParts> getDeploymentPageWorkOrderConsumption(ValueObject object) throws Exception {		
		try {
			PageRequest pageable = new PageRequest(object.getInt("pageNumber") - 1, PAGE_SIZE, Sort.Direction.DESC, "workordertaskto_partid");
			Timestamp 	fromDate = DateTimeUtility.getTimeStamp(object.getString("startDate"), DateTimeUtility.YYYY_MM_DD);
			Timestamp 	toDate   = DateTimeUtility.getTimeStamp(object.getString("endDate"), DateTimeUtility.YYYY_MM_DD);
			
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {	
				return workOrdersTasksToPartsRepository.getPageWorkOrderConsumptionByTransactionDate(fromDate,toDate,object.getInt("companyId"), pageable);
			}else {
				return workOrdersTasksToPartsRepository.getPageWorkOrderConsumptionByCreatedDate(fromDate,toDate,object.getInt("companyId"), pageable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public ValueObject getServiceEntryConsumption(ValueObject object)throws Exception{
		String 									queryStr 			= "";
		List<WorkOrdersTasksToPartsDto> 		serviceEntryList  	= null;
		Integer									pageNumber			= null;
		Page<ServiceEntriesTasksToParts> 		page 				= null;
		try {
			//do not change alias name
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {
				queryStr 	= " AND WS.completed_date between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}else{
				queryStr 	= " AND WS.created between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}
			object.put("queryStr", queryStr);
		
			pageNumber			= object.getInt("pageNumber",0);
			page 				= getDeploymentPageServiceEntryConsumption(object);
			
			int current 		= page.getNumber() + 1;
			int begin		 	= Math.max(1, current - 5);
			int end 			= Math.min(begin + 10, page.getTotalPages());
	
			object.put("deploymentLog", page);
			object.put("beginIndex", begin);
			object.put("endIndex", end);
			object.put("currentIndex", current);
			serviceEntryList 	= partReportDao.getSEPartConsumptionList(object);
			object.put("serviceEntryList", serviceEntryList);
			object.put("SelectPage", pageNumber);
			object.put("entryCount", getSE_PartConsumptionCount(object).getDouble("SE_PartEntryCount"));
		
			return object;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	public Page<ServiceEntriesTasksToParts> getDeploymentPageServiceEntryConsumption(ValueObject object) throws Exception {		

		try {
			PageRequest pageable = new PageRequest(object.getInt("pageNumber") - 1, PAGE_SIZE, Sort.Direction.DESC, "serviceEntriesTaskto_partid");
			Timestamp 	fromDate = DateTimeUtility.getTimeStamp(object.getString("startDate"), DateTimeUtility.YYYY_MM_DD);
			Timestamp 	toDate   = DateTimeUtility.getTimeStamp(object.getString("endDate"), DateTimeUtility.YYYY_MM_DD);
			
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {	
				return serviceEntriesTasksToPartsRepository.getPageServiceEntryConsumptionByTransactionDate(fromDate,toDate,object.getInt("companyId"), pageable);
			}else {
				return serviceEntriesTasksToPartsRepository.getPageServiceEntryConsumptionByCreatedDate(fromDate,toDate,object.getInt("companyId"), pageable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public ValueObject getTyreConsumption(ValueObject object)throws Exception{
		String 								queryStr 			= "";
		List<InventoryTyreHistoryDto> 		tyreList  			= null;
	/*	Integer								pageNumber			= null;
		Page<InventoryTyreHistory> 			page 				= null;*/
		try {
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {
				queryStr 	= " IH.TYRE_ASSIGN_DATE between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+""+DateTimeUtility.DAY_END_TIME+"' ";
			}else{
				queryStr 	= " IH.createdOn between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+""+DateTimeUtility.DAY_END_TIME+"' ";
			}
			object.put("queryStr", queryStr);
			
			/*pageNumber		= object.getInt("pageNumber",0);
			page 			= getDeploymentPageTyreConsumption(object);
			
			int current 	= page.getNumber() + 1;
			int begin 		= Math.max(1, current - 5);
			int end 		= Math.min(begin + 10, page.getTotalPages());
			
			
			object.put("deploymentLog", page);
			object.put("beginIndex", begin);
			object.put("endIndex", end);
			object.put("currentIndex", current);
			object.put("SelectPage", pageNumber);*/
			tyreList 		= inventoryTyreService.getTyreConsumptionList(object);
			object.put("tyreList", tyreList);
			object.put("totalConsumptionCount", getTyreConsumptionCount(object));
			return object;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	public Page<InventoryTyreHistory> getDeploymentPageTyreConsumption(ValueObject object) throws Exception {		

		try {
			PageRequest pageable 	= new PageRequest(object.getInt("pageNumber") - 1, PAGE_SIZE, Sort.Direction.DESC, "TYRE_HIS_ID");
			Timestamp   fromDate 	= DateTimeUtility.getTimeStamp(object.getString("startDate"), DateTimeUtility.YYYY_MM_DD);
			Timestamp 	toDate   	= DateTimeUtility.getTimeStamp(object.getString("endDate"), DateTimeUtility.YYYY_MM_DD);
			
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {	
				return inventoryTyreHistoryRepository.getPageTyreConsumptionByTransactionDate(fromDate,toDate,object.getInt("companyId"), pageable);
			}else {
				return inventoryTyreHistoryRepository.getPageTyreConsumptionByCreatedDate(fromDate,toDate,object.getInt("companyId"), pageable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ValueObject getBatteryConsumption(ValueObject object)throws Exception{
		String 							queryStr 			= "";
		List<BatteryHistoryDto> 		batteryList  		= null;
		/*Integer							pageNumber			= null;
		Page<BatteryHistory> 			page 				= null;*/
		try {
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {
				queryStr = " BH.batteryAsignDate between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}else{
				queryStr = " BH.createdOn between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}
			object.put("queryStr", queryStr);
			
			/*pageNumber		= object.getInt("pageNumber");
			page 			= getDeploymentPageBatteryConsumption(object);
			
			int current 	= page.getNumber() + 1;
			int begin 		= Math.max(1, current - 5);
			int end 		= Math.min(begin + 10, page.getTotalPages());
			
			
			object.put("deploymentLog", page);
			object.put("beginIndex", begin);
			object.put("endIndex", end);
			object.put("currentIndex", current);
			object.put("SelectPage", pageNumber);*/
			batteryList 	= batteryInvoiceService.getBatteryConsumptionList(object);
			object.put("batteryList", batteryList);
			object.put("totalConsumptionCount", getBatteryConsumptionCount(object));
			
			return object;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	public Page<BatteryHistory> getDeploymentPageBatteryConsumption(ValueObject object) throws Exception {		

		try {
			PageRequest pageable 	= new PageRequest(object.getInt("pageNumber") - 1, PAGE_SIZE, Sort.Direction.DESC, "batteryHistoryId");
			Timestamp   fromDate 	= DateTimeUtility.getTimeStamp(object.getString("startDate"), DateTimeUtility.YYYY_MM_DD);
			Timestamp 	toDate   	= DateTimeUtility.getTimeStamp(object.getString("endDate"), DateTimeUtility.YYYY_MM_DD);
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {	
				return batteryHistoryRepository.getPageBatteryConsumptionByTransactionDate(fromDate,toDate,object.getInt("companyId"), pageable);
			}else {
				return batteryHistoryRepository.getPageBatteryConsumptionByCreatedDate(fromDate,toDate,object.getInt("companyId"), pageable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ValueObject getRefreshmentConsumption(ValueObject object)throws Exception{
		String 							queryStr 			= "";
		List<RefreshmentEntryDto> 		refreshmentList  	= null;
		/*Integer							pageNumber			= null;
		Page<RefreshmentEntry> 			page 				= null;*/
		try {
			
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {
				queryStr = " R.asignmentDate between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}else{
				queryStr = " R.created between '"+object.getString("startDate")+" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
			}
			object.put("queryStr", queryStr);
			
			/*pageNumber		= object.getInt("pageNumber",0);
			page 			= getDeploymentPageRefreshmentConsumption(object);
			
			int current 	= page.getNumber() + 1;
			int begin 		= Math.max(1, current - 5);
			int end 		= Math.min(begin + 10, page.getTotalPages());
			
			
			object.put("deploymentLog", page);
			object.put("beginIndex", begin);
			object.put("endIndex", end);
			object.put("currentIndex", current);
			object.put("SelectPage", pageNumber);*/
			refreshmentList 	= refreshmentEntryService.getRefreshmentConsumptionList(object);
			object.put("refreshmentList", refreshmentList);
			
			object.put("entryCount", getRefreshmentConsumptionCount(object).getInt("refreshmentEntryCount"));
			return object;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	public Page<RefreshmentEntry> getDeploymentPageRefreshmentConsumption(ValueObject object) throws Exception {		

		try {
			PageRequest pageable 	= new PageRequest(object.getInt("pageNumber") - 1, PAGE_SIZE, Sort.Direction.DESC, "refreshmentEntryId");
			Timestamp   fromDate 	= DateTimeUtility.getTimeStamp(object.getString("startDate"), DateTimeUtility.YYYY_MM_DD);
			Timestamp 	toDate   	= DateTimeUtility.getTimeStamp(object.getString("endDate"), DateTimeUtility.YYYY_MM_DD);
			
			if(object.getShort("dateType") == ConsumptionSummaryConstant.DATE_TYPE_TRANSACION) {	
				return refreshmentEntryRepository.getPageRefreshmentConsumptionByTransactionDate(fromDate,toDate,object.getInt("companyId"), pageable);
			}else {
				return refreshmentEntryRepository.getPageRefreshmentConsumptionByCreatedDate(fromDate,toDate,object.getInt("companyId"), pageable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}


