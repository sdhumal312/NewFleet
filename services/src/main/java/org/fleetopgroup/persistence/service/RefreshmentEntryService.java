package org.fleetopgroup.persistence.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.InventoryStockTypeConstant;
import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.RefreshmentEntriesBL;
import org.fleetopgroup.persistence.dao.RefreshmentEntryRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DayWiseInventoryStockDto;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.RefreshmentEntryDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.model.RefreshmentEntry;
import org.fleetopgroup.persistence.model.RefreshmentSequenceCounter;
import org.fleetopgroup.persistence.report.dao.RefreshmentEntryDao;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDayWiseInventoryStockService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IRefreshmentEntryService;
import org.fleetopgroup.persistence.serviceImpl.IRefreshmentSequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RefreshmentEntryService implements IRefreshmentEntryService {

	@PersistenceContext EntityManager entityManager;
	
	@Autowired private	RefreshmentEntryRepository			refreshmentEntryRepository;
	@Autowired private	IInventoryService					inventoryService;
	@Autowired private	IRefreshmentSequenceCounterService	refreshmentSequenceCounterService;
	@Autowired private  IUserProfileService					userProfileService;
	@Autowired private	RefreshmentEntryDao					refreshmentEntryDao;
	@Autowired private	ITripSheetService					tripSheetService;
	@Autowired private	ICompanyConfigurationService		companyConfigurationService;
	@Autowired private	IDayWiseInventoryStockService		dayWiseInventoryStockService;
	
	RefreshmentEntriesBL	refreshmentEntriesBL = new RefreshmentEntriesBL();
	
	SimpleDateFormat	format = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat	sqlFormat = new SimpleDateFormat("yyyy-MM-dd");
	DecimalFormat toFixedTwo= new DecimalFormat("#.##");

	private static final int PAGE_SIZE = 10;
	
	@Override
	public List<InventoryDto> getRefreshmentEntryList(String term, Integer companyId) throws Exception {

		TypedQuery<Object[]> 					typedQuery 				= null;
		List<Object[]> 							resultList 				= null; 
		List<InventoryDto> 						inventoryList 			= null;
		InventoryDto 							inventoryDto			= null;

		try {
			typedQuery = entityManager.createQuery("SELECT I.quantity, I.history_quantity, PL.partlocation_name, I.inventory_id, I.partInvoiceId,"
					+ " I.unitprice, I.discount, I.tax, I.locationId"
					+ " FROM Inventory AS I "
					+ " INNER JOIN PartInvoice PI ON PI.partInvoiceId = I.partInvoiceId AND PI.markForDelete = 0"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = I.locationId "
					+ " WHERE lower(PL.partlocation_name) Like ('%" + term + "%') AND I.companyId ="+companyId+" AND I.quantity > 0.0 AND I.markForDelete = 0 ", Object[].class);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				inventoryList = new ArrayList<>();

				for (Object[] result : resultList) {
					inventoryDto = new InventoryDto();
					
					inventoryDto.setQuantity((Double) result[0]);
					inventoryDto.setHistory_quantity((Double) result[1]);
					inventoryDto.setLocation((String) result[2]);
					inventoryDto.setInventory_id((Long) result[3]);
					inventoryDto.setPartInvoiceId((Long) result[4]);
					inventoryDto.setUnitprice((Double) result[5]);
					inventoryDto.setDiscount((Double) result[6]);
					inventoryDto.setTax((Double) result[7]);
					inventoryDto.setLocationId((Integer) result[8]);
					
					inventoryList.add(inventoryDto);
				}
			}

			return inventoryList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			inventoryList 			= null;
			inventoryDto			= null;
		}
	}

	@Override
	public ValueObject saveRefreshmentEntriesDetails(ValueObject valueObject) throws Exception {
		RefreshmentEntry			refreshmentEntry				= null;
		List<InventoryDto> 			get_InventoryList				= null;
		CustomUserDetails			userDetails						= null;
		Double						unitprice						= 0.0;
		Double						totalcost						= 0.0;
		Double						refreshmentQty					= 0.0;
		RefreshmentSequenceCounter	sequenceCounter					= null;
		ValueObject					valueStockObject				= null;
		String						query							= "" ; // actully use for normal part sublocation..
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			valueObject.put("userDetails", userDetails);
			get_InventoryList	= inventoryService.Get_WorkOrder_InventoryLocation_id_To_Inventory(valueObject.getLong("partid",0), userDetails.getCompany_id(),query);
			
			if (get_InventoryList != null && !get_InventoryList.isEmpty()) {
				
				refreshmentQty = valueObject.getDouble("quantity",0);
				
				for (InventoryDto inventoryDto : get_InventoryList) {
					if (refreshmentQty <= inventoryDto.getQuantity()) {
						sequenceCounter	= refreshmentSequenceCounterService.findNextNumber(userDetails.getCompany_id());
						if(sequenceCounter == null) {
							valueObject.put("sequenceCounter", true);
							return valueObject;
						}
						unitprice		= Utility.round((inventoryDto.getTotal() / inventoryDto.getHistory_quantity()), 2);
						totalcost 		= valueObject.getDouble("quantity", 0) * unitprice;
						
						valueObject.put("inventoryId", inventoryDto.getInventory_id());
						valueObject.put("quantity", refreshmentQty);
						valueObject.put("unitprice", unitprice);
						valueObject.put("totalcost", totalcost);
						valueObject.put("totalcost", totalcost);
						valueObject.put("refreshmentEntryNumber", sequenceCounter.getNextVal());
						valueObject.put("partid", inventoryDto.getPartid());
						
						refreshmentEntry = refreshmentEntriesBL.getRefreshmentEntry(valueObject);
						refreshmentEntryRepository.save(refreshmentEntry);
						
						inventoryService.Subtract_update_Inventory_from_Workorder(refreshmentQty,inventoryDto.getInventory_id());
						inventoryService.Subtract_update_InventoryLocation_from_Workorder(inventoryDto.getPartid(),inventoryDto.getLocationId(), userDetails.getCompany_id());
						inventoryService.Subtract_update_InventoryAll_from_Workorder(inventoryDto.getPartid(), userDetails.getCompany_id());
						

						valueStockObject	= new ValueObject();
						
						valueStockObject.put("transactionDate", refreshmentEntry.getAsignmentDate());
						valueStockObject.put("partId", refreshmentEntry.getPartid());
						valueStockObject.put("locationId", refreshmentEntry.getPartLocationId());
						valueStockObject.put("quantity", - refreshmentQty);
						valueStockObject.put("companyId", userDetails.getCompany_id());
						valueStockObject.put("addedQuantity", 0.0);
						valueStockObject.put("usedQuantity", refreshmentQty);
						valueStockObject.put("numberType", "I-"+ inventoryDto.getInventory_Number()+" Part Used");
						valueStockObject.put("transactionId", inventoryDto.getInventory_id());
						valueStockObject.put("primaryType", InventoryStockTypeConstant.STOCK_TYPE_USED);
						valueStockObject.put("transactionType", InventoryStockTypeConstant.STOCK_TYPE_USED);
						
						dayWiseInventoryStockService.processLocationWiseInventoryStockDetails(valueStockObject);	
					
						
						
						break;
					} else {
						
						unitprice 		= Utility.round(inventoryDto.getTotal() / inventoryDto.getHistory_quantity(), 2);
						totalcost 		= inventoryDto.getQuantity() * unitprice;
						sequenceCounter	= refreshmentSequenceCounterService.findNextNumber(userDetails.getCompany_id());
						if(sequenceCounter == null) {
							valueObject.put("sequenceCounter", false);
							return valueObject;
						}
						valueObject.put("inventoryId", inventoryDto.getInventory_id());
						valueObject.put("quantity", inventoryDto.getQuantity());
						valueObject.put("unitprice", unitprice);
						valueObject.put("totalcost", totalcost);
						valueObject.put("refreshmentEntryNumber", sequenceCounter.getNextVal());
						valueObject.put("partid", inventoryDto.getPartid());
						
						refreshmentEntry = refreshmentEntriesBL.getRefreshmentEntry(valueObject);
						refreshmentEntryRepository.save(refreshmentEntry);
						
						inventoryService.Subtract_update_Inventory_from_Workorder(inventoryDto.getQuantity(), inventoryDto.getInventory_id());

						inventoryService.Subtract_update_InventoryLocation_from_Workorder(inventoryDto.getPartid(),inventoryDto.getLocationId(), userDetails.getCompany_id());

						inventoryService.Subtract_update_InventoryAll_from_Workorder(inventoryDto.getPartid(),userDetails.getCompany_id());

						
						valueStockObject	= new ValueObject();
						
						valueStockObject.put("transactionDate", refreshmentEntry.getAsignmentDate());
						valueStockObject.put("partId", refreshmentEntry.getPartid());
						valueStockObject.put("locationId", refreshmentEntry.getPartLocationId());
						valueStockObject.put("quantity", - inventoryDto.getQuantity());
						valueStockObject.put("companyId", userDetails.getCompany_id());
						valueStockObject.put("addedQuantity", 0.0);
						valueStockObject.put("usedQuantity", inventoryDto.getQuantity());
						valueStockObject.put("numberType", "I-"+ inventoryDto.getInventory_Number()+" Part Used");
						valueStockObject.put("transactionId", inventoryDto.getInventory_id());
						valueStockObject.put("primaryType", InventoryStockTypeConstant.STOCK_TYPE_USED);
						valueStockObject.put("transactionType", InventoryStockTypeConstant.STOCK_TYPE_USED);
						
						dayWiseInventoryStockService.processLocationWiseInventoryStockDetails(valueStockObject);
						
						refreshmentQty = (refreshmentQty - inventoryDto.getQuantity());

					}
				} 
				
			}else {
				valueObject.put("noStock", true);	
			}
			
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			refreshmentEntry					= null;
			get_InventoryList					= null;
			userDetails							= null;
			unitprice							= 0.0;
			totalcost							= 0.0;
			refreshmentQty						= 0.0;
		}
	}
	
	@Override
	public ValueObject getRefreshmentEntryListByTripSheetId(Long tripsheetId) throws Exception {

		TypedQuery<Object[]> 					typedQuery 				= null;
		List<Object[]> 							resultList 				= null; 
		List<RefreshmentEntryDto> 				inventoryList 			= null;
		RefreshmentEntryDto 					inventoryDto			= null;
		ValueObject								valueObject				= null;
		Double  grandTotal 		 = 0.0;
		Double  totalQty   		 = 0.0;
		Double  totalRQty  		 = 0.0;
		Double  totalConsumption = 0.0;
		Double totalReturnAmount = 0.0;
		try {
			
			typedQuery = entityManager.createQuery(" SELECT R.refreshmentEntryId, R.vid, R.tripsheetId, R.partid, R.asignmentType,"
					+ " R.partLocationId, R.inventoryId, R.routeId, R.driverId, R.asignmentDate, R.returnQuantity, R.quantity,"
					+ " R.unitprice, R.discount, R.tax, R.totalAmount, R.comment, I.inventory_Number, MP.partname, PL.partlocation_name,"
					+ " R.refreshmentEntryNumber "
					+ " FROM RefreshmentEntry R "
					+ " INNER JOIN Inventory I ON I.inventory_id = R.inventoryId "
					+ " INNER JOIN MasterParts MP ON MP.partid = R.partid"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.partLocationId"
					+ " where R.tripsheetId = "+tripsheetId+" AND R.markForDelete = 0", Object[].class);

			resultList = typedQuery.getResultList();
			valueObject	= new ValueObject();
			if (resultList != null && !resultList.isEmpty()) {
				inventoryList = new ArrayList<>();
				valueObject	= new ValueObject();
				
				for (Object[] result : resultList) {
					inventoryDto = new RefreshmentEntryDto();
					inventoryDto.setRefreshmentEntryId((Long) result[0]);
					inventoryDto.setVid((Integer) result[1]);
					inventoryDto.setTripsheetId((Long) result[2]);
					inventoryDto.setPartid((Long) result[3]);
					inventoryDto.setAsignmentType((short) result[4]);
					inventoryDto.setPartLocationId((Integer) result[5]);
					inventoryDto.setInventoryId((Long) result[6]);
					inventoryDto.setRouteId((Integer) result[7]);
					inventoryDto.setDriverId((Integer) result[8]);
					inventoryDto.setAsignmentDate((Date) result[9]);
					if(result[10] != null)
					inventoryDto.setReturnQuantity(Double.parseDouble(toFixedTwo.format(result[10])));
					if(result[11] != null)
					inventoryDto.setQuantity(Double.parseDouble(toFixedTwo.format(result[11])));
					if(result[12] != null)
					inventoryDto.setUnitprice(Double.parseDouble(toFixedTwo.format(result[12])));
					if(result[13]!=null)
					inventoryDto.setDiscount(Double.parseDouble(toFixedTwo.format(result[13])));
					if(result[14]!=null)
					inventoryDto.setTax(Double.parseDouble(toFixedTwo.format(result[14])));
					if(result[15]!=null)
					inventoryDto.setTotalAmount(Double.parseDouble(toFixedTwo.format(result[15])));
					
					inventoryDto.setComment((String) result[16]);
					inventoryDto.setInventory_Number((Long) result[17]);
					inventoryDto.setPartname((String) result[18]);
					inventoryDto.setLocationName((String) result[19]);
					inventoryDto.setRefreshmentEntryNumber((Long) result[20]);
					
					if(inventoryDto.getAsignmentDate() != null) {
						inventoryDto.setAsignmentDateStr(format.format(inventoryDto.getAsignmentDate()));
					}
					
					inventoryList.add(inventoryDto);
					if(inventoryDto.getTotalAmount() != null)
						grandTotal += inventoryDto.getTotalAmount();
					if(inventoryDto.getQuantity() != null)
						totalQty += inventoryDto.getQuantity();
					if(inventoryDto.getReturnQuantity() != null)
						totalRQty += inventoryDto.getReturnQuantity();
					if(inventoryDto.getReturnQuantity() !=null && inventoryDto.getUnitprice() != null) {
						totalReturnAmount +=inventoryDto.getReturnQuantity() * inventoryDto.getUnitprice();
					}
					
					totalConsumption += totalQty - totalRQty;
				}
				
				valueObject.put("refreshment", inventoryList);
				valueObject.put("grandTotal", toFixedTwo.format(grandTotal));
				valueObject.put("totalQty", toFixedTwo.format(totalQty));
				valueObject.put("totalRQty", toFixedTwo.format(totalRQty));
				valueObject.put("totalConsumption",Utility.round(totalConsumption, 2));
				valueObject.put("totalReturnAmount",toFixedTwo.format(totalReturnAmount));
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			inventoryList 			= null;
			inventoryDto			= null;
		}
	}
	
	@Override
	public RefreshmentEntryDto getRefreshmentEntryListById(Long refreshmentEntryId, Integer companyId)
			throws Exception {

		Query query = entityManager.createQuery(" SELECT R.refreshmentEntryId, R.vid, R.tripsheetId, R.partid, R.asignmentType,"
				+ " R.partLocationId, R.inventoryId, R.routeId, R.driverId, R.asignmentDate, R.returnQuantity, R.quantity,"
				+ " R.unitprice, R.discount, R.tax, R.totalAmount, R.comment, I.inventory_Number, MP.partname, PL.partlocation_name,"
				+ " TR.routeName, D.driver_firstname, R.refreshmentEntryNumber "
				+ " FROM RefreshmentEntry R "
				+ " INNER JOIN Inventory I ON I.inventory_id = R.inventoryId "
				+ " INNER JOIN MasterParts MP ON MP.partid = R.partid"
				+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.partLocationId"
				+ " LEFT JOIN TripRoute TR ON TR.routeID = R.routeId"
				+ " LEFT JOIN Driver D ON D.driver_id = R.driverId"
				+ " where R.refreshmentEntryId = "+refreshmentEntryId+" AND R.companyId = "+companyId+" AND R.markForDelete = 0");

		
		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		
		RefreshmentEntryDto inventoryDto	= null;
		if (result != null) {
			inventoryDto = new RefreshmentEntryDto();
			inventoryDto.setRefreshmentEntryId((Long) result[0]);
			inventoryDto.setVid((Integer) result[1]);
			inventoryDto.setTripsheetId((Long) result[2]);
			inventoryDto.setPartid((Long) result[3]);
			inventoryDto.setAsignmentType((short) result[4]);
			inventoryDto.setPartLocationId((Integer) result[5]);
			inventoryDto.setInventoryId((Long) result[6]);
			inventoryDto.setRouteId((Integer) result[7]);
			inventoryDto.setDriverId((Integer) result[8]);
			inventoryDto.setAsignmentDate((Date) result[9]);
			inventoryDto.setReturnQuantity((Double) result[10]);
			inventoryDto.setQuantity((Double) result[11]);
			inventoryDto.setUnitprice((Double) result[12]);
			inventoryDto.setDiscount((Double) result[13]);
			inventoryDto.setTax((Double) result[14]);
			inventoryDto.setTotalAmount((Double) result[15]);
			inventoryDto.setComment((String) result[16]);
			inventoryDto.setInventory_Number((Long) result[17]);
			inventoryDto.setPartname((String) result[18]);
			inventoryDto.setLocationName((String) result[19]);
			inventoryDto.setRouteName((String) result[20]);
			inventoryDto.setDriverName((String) result[21]);
			inventoryDto.setRefreshmentEntryNumber((Long) result[22]);
		}
		return inventoryDto;
	}
	
	@Override
	@Transactional
	public ValueObject saveReturnRefreshment(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails				= null;
		RefreshmentEntryDto		refreshmentEntryDto		= null;
		Double					returnedQty				= 0.0;
		ValueObject				valueStockObject		= null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			refreshmentEntryDto	= getRefreshmentEntryListById(valueObject.getLong("refreshmentEntryId",0), userDetails.getCompany_id());
			
			if(refreshmentEntryDto != null) {
				if(refreshmentEntryDto.getReturnQuantity() != null) {
					returnedQty	= refreshmentEntryDto.getReturnQuantity();
				}
				if(refreshmentEntryDto.getQuantity() >= returnedQty + valueObject.getDouble("returnQuantity",0)) {
					updateReturnRefreshmentDetails(valueObject);
					inventoryService.Subtract_update_Inventory_from_Workorder(- valueObject.getDouble("returnQuantity",0),refreshmentEntryDto.getInventoryId());
					inventoryService.Subtract_update_InventoryLocation_from_Workorder(refreshmentEntryDto.getPartid(),refreshmentEntryDto.getPartLocationId(), userDetails.getCompany_id());
					inventoryService.Subtract_update_InventoryAll_from_Workorder(refreshmentEntryDto.getPartid(), userDetails.getCompany_id());

					valueStockObject	= new ValueObject();
					
					Date returnDate = format.parse(valueObject.getString("refreshmentDate"));
					
					valueStockObject.put("transactionDate", returnDate);
					valueStockObject.put("partId", refreshmentEntryDto.getPartid());
					valueStockObject.put("locationId", refreshmentEntryDto.getPartLocationId());
					valueStockObject.put("quantity",  valueObject.getDouble("returnQuantity",0));
					valueStockObject.put("companyId", userDetails.getCompany_id());
					valueStockObject.put("addedQuantity", valueObject.getDouble("returnQuantity",0));
					valueStockObject.put("usedQuantity", 0.0);
					valueStockObject.put("numberType", "I-"+ refreshmentEntryDto.getInventory_Number()+" Part Returned");
					valueStockObject.put("transactionId", refreshmentEntryDto.getInventoryId());
					valueStockObject.put("primaryType", InventoryStockTypeConstant.STOCK_TYPE_RETURNED);
					valueStockObject.put("transactionType", InventoryStockTypeConstant.STOCK_TYPE_RETURNED);
					
					dayWiseInventoryStockService.processLocationWiseInventoryStockDetails(valueStockObject);
					
				}else {
					valueObject.put("quantityMismatch", true);
				}
					
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updateReturnRefreshmentDetails(ValueObject valueObject) throws Exception {
		entityManager.createQuery(
				"Update RefreshmentEntry SET returnQuantity = returnQuantity + "+valueObject.getDouble("returnQuantity",0)+","
				+ " returnDate = '"+DateTimeUtility.getTimeStamp(valueObject.getString("refreshmentDate"))+"' "
				+ " WHERE refreshmentEntryId = " + valueObject.getLong("refreshmentEntryId",0) + "")
		.executeUpdate();
		
	}
	
	@Override
	public ValueObject getRefreshmentListDetails(ValueObject valueObject) throws Exception {
		ValueObject	valueOutObject	= null;
		try {
			valueOutObject	= getRefreshmentEntryListByTripSheetId(valueObject.getLong("tripsheetId", 0));
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueOutObject	= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject removeRefreshment(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails		= null;
		try {

			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("userDetails", userDetails);
			
			refreshmentEntryRepository.deleteRefreshmentEntryById(valueObject.getLong("refreshmentEntryId", 0));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
		}
	}
	
	@Override
	public ValueObject getRefreshmentConsumptionReport(ValueObject valueObject) throws Exception {
		Integer							vehicleId				= 0;
		String							dateRange				= "";
		CustomUserDetails				userDetails				= null;
		ValueObject						tableConfig				= null;
		List<RefreshmentEntryDto> 		refreshmentEntryList	= null;	
		String 							query					= "";
		int 							routeId					= 0 ;
		Long							partId					= null;
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			vehicleId						= valueObject.getInt("vehicleId", 0);
			dateRange						= valueObject.getString("date");
			routeId							= valueObject.getInt("routeId", 0);
			partId							= valueObject.getLong("partId",0);

			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;			

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				String route = "" , vehicle = "",date = "" , part = "";

				
				if(routeId > 0 )
				{
					route = " AND  R.routeId = "+ routeId +" ";
				}
				if(vehicleId > 0 )
				{
					vehicle = " AND  R.vid = "+ vehicleId +" ";
				}
				if(partId != null && partId > 0 ){
					part = " AND  R.partid = "+ partId +" ";
				}
				
				date		=	" AND R.asignmentDate between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;

				query       = " " + route + " "+ vehicle +" "+ date+" "+part+" ";
			}	

			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.REFRESHMENT_CONSUMPTION_REPORT_DATA_FILE_PATH);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);

			refreshmentEntryList		= refreshmentEntryDao.getRefreshmentConsumptionData(query,userDetails.getCompany_id(), userDetails.getId());

			if(routeId != 0 ) {
				if(refreshmentEntryList != null)
					valueObject.put("RouteKey", refreshmentEntryList.get(0).getRouteName());
			} else {
				valueObject.put("RouteKey", "All");
			}

			if(vehicleId > 0) {
				if(refreshmentEntryList != null)
					valueObject.put("VehicleKey", refreshmentEntryList.get(0).getVehicle_registration());
			} else {
				valueObject.put("VehicleKey", "All");
			}

			valueObject.put("refreshmentEntryList", refreshmentEntryList);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;	
			vehicleId				= 0;
			dateRange				= "";
			tableConfig				= null;
			query					= "";
			routeId					= 0 ;
			refreshmentEntryList	= null;
		}
	}
	
	@Override
	public ValueObject getTripSheetForDate(ValueObject valueObject) throws Exception {
		List<TripSheetDto>   tripSheetList		= null;
		String				 fromDate			= null;
		String				 toDate				= null;
		Date				 refreshmentDate	= null;
		try {
			refreshmentDate	= format.parse(valueObject.getString("refreshmentDate"));
			fromDate		= sqlFormat.format(refreshmentDate) + DateTimeUtility.DAY_START_TIME;
			toDate			= sqlFormat.format(refreshmentDate) + DateTimeUtility.DAY_END_TIME;
			
			tripSheetList	=	tripSheetService.getTripSheetForDate(valueObject.getInt("vid"), fromDate, toDate);
			
			valueObject.put("tripSheetList", tripSheetList);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			 	tripSheetList		= null;
				fromDate			= null;
				toDate				= null;
		}
	}
	
	@Override
	public ValueObject getPageWiseRefreshmentsDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails	   userDetails				= null;
		Integer				   pageNumber				= null;
		try {
			
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber				= valueObject.getInt("pageNumber",0);
			Page<RefreshmentEntry> page  = getDeployment_Page_Refreshment(pageNumber, userDetails.getCompany_id(), userDetails.getId());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());
			
			List<RefreshmentEntryDto> ureaEntriesList = refreshmentEntryDao.getRefreshmentEntryDtoList(pageNumber, userDetails.getCompany_id(), userDetails.getId());
			
			valueObject.put("ureaEntriesList", ureaEntriesList);
			valueObject.put("SelectPage", pageNumber);
		
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			 userDetails				= null;
		}
	}
	
	@Override
	public Page<RefreshmentEntry> getDeployment_Page_Refreshment(Integer pageNumber, Integer companyId, Long userId) throws Exception {		
		HashMap<String, Object>                 configuration			= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			@SuppressWarnings("deprecation")
			PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "refreshmentEntryId");
			if(!(boolean) configuration.get("vehicleGroupWisePermission")) {
				return refreshmentEntryRepository.getDeployment_Page_Refreshment(companyId, pageable);
			}
			return refreshmentEntryRepository.getDeployment_Page_Refreshment(companyId, userId, pageable);
		}catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	@Transactional
	public ValueObject deleteRefreshmentEntry(ValueObject valueObject) throws Exception {
			RefreshmentEntryDto		refreshmentEntry		= null;
			CustomUserDetails		userDetails				= null;
			try {
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				refreshmentEntry	=	getRefreshmentEntryListById(valueObject.getLong("refreshmentEntryId", 0), userDetails.getCompany_id());
				
				refreshmentEntryRepository.deleteRefreshmentEntryById(valueObject.getLong("refreshmentEntryId", 0));
				
				inventoryService.Subtract_update_Inventory_from_Workorder(- refreshmentEntry.getQuantity(),refreshmentEntry.getInventoryId());
				inventoryService.Subtract_update_InventoryLocation_from_Workorder(refreshmentEntry.getPartid(),refreshmentEntry.getPartLocationId(), userDetails.getCompany_id());
				inventoryService.Subtract_update_InventoryAll_from_Workorder(refreshmentEntry.getPartid(), userDetails.getCompany_id());

				
				ValueObject	valueStockObject	= new ValueObject();
				
				valueStockObject.put("transactionDate", refreshmentEntry.getAsignmentDate());
				valueStockObject.put("partId", refreshmentEntry.getPartid());
				valueStockObject.put("locationId",  refreshmentEntry.getPartLocationId());
				valueStockObject.put("quantity",  refreshmentEntry.getQuantity());
				valueStockObject.put("companyId", userDetails.getCompany_id());
				valueStockObject.put("addedQuantity", refreshmentEntry.getQuantity());
				valueStockObject.put("usedQuantity", 0.0);
				valueStockObject.put("numberType", "I-"+ refreshmentEntry.getInventory_Number()+" Refreshment Delete");
				valueStockObject.put("transactionId", refreshmentEntry.getInventoryId());
				valueStockObject.put("primaryType", InventoryStockTypeConstant.STOCK_TYPE_REFRESHMENT_DELETE);
				valueStockObject.put("transactionType", InventoryStockTypeConstant.STOCK_TYPE_REFRESHMENT_DELETE);
				
				dayWiseInventoryStockService.processLocationWiseInventoryStockDetails(valueStockObject);	
			
				
			} catch (Exception e) {
				throw e;
			}
			
		return valueObject;
	}
	
	@Override
	public ValueObject getRefreshmentStockReport(ValueObject valueObject) throws Exception {
		Integer							locationId				= 0;
		String							stockDate				= "";
		CustomUserDetails				userDetails				= null;
		ValueObject						tableConfig				= null;
		List<DayWiseInventoryStockDto> 	dayWiseInventoryStock	= null;	
		String 							query					= "";
		Long							partId					= null;
		Date							searchDate				= null;
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			locationId						= valueObject.getInt("locationId", 0);
			stockDate						= valueObject.getString("stockDate");
			partId							= valueObject.getLong("partId",0);
			
			if(!valueObject.containsKey("stockDate") && valueObject.getString("stockDate", null) != null) {
				valueObject.put("noStockDate", true);
				return valueObject;
			}
				searchDate	= format.parse(stockDate);
				
				String location = "", date = "" , part = "";

				
				if(locationId > 0 )
				{
					location = " AND  DS.locationId = "+ locationId +" ";
				}
				
				if(partId != null && partId > 0 ){
					part = " AND  DS.partId = "+ partId +" ";
				}
				
				date		=	" AND DS.transactionDate <= '"+sqlFormat.format(searchDate)+"' " ;

				query       = " " + part + " "+ location +" "+ date+" ";

			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.REFRESHMENT_STOCK_REPORT_DATA_FILE_PATH);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);

			dayWiseInventoryStock		= dayWiseInventoryStockService.getDayWiseInventoryStockList(query,userDetails.getCompany_id());

			if(locationId != 0 ) {
				if(dayWiseInventoryStock != null)
					valueObject.put("locationKey", dayWiseInventoryStock.get(0).getLocationName());
			} else {
				valueObject.put("locationKey", "All");
			}

			if(partId > 0) {
				if(dayWiseInventoryStock != null)
					valueObject.put("partKey", dayWiseInventoryStock.get(0).getPartName());
			} else {
				valueObject.put("partKey", "All");
			}

			valueObject.put("dayWiseInventoryStock", dayWiseInventoryStock);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;	
			tableConfig				= null;
			query					= "";
		}
	}
	
	@Override
	public ValueObject searchRefreshmentEntriesByNumber(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails		= null;
		Long						invoiceId		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			invoiceId	=	refreshmentEntryRepository.searchRefreshEntriesByNumber(valueObject.getLong("refreshmentInvoiceNumber", 0), userDetails.getCompany_id());
			if(invoiceId != null)
				valueObject.put("invoiceId", invoiceId);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
			invoiceId		= null;
		}
	}
	
	public List<RefreshmentEntryDto> getRefreshmentConsumptionList(ValueObject object) throws Exception {
		TypedQuery<Object[]> 					typedQuery 				= null;
		List<Object[]> 							resultList 				= null; 
		List<RefreshmentEntryDto> 				refreshmentList 			= null;
		RefreshmentEntryDto 					inventoryDto			= null;
		try {
			typedQuery = entityManager.createQuery(" SELECT R.refreshmentEntryId,R.refreshmentEntryNumber, R.vid, V.vehicle_registration, "
					+ " R.tripsheetId, T.tripSheetNumber, R.partid, MP.partname,R.asignmentDate, R.quantity, R.totalAmount, R.comment, R.returnQuantity "
					+ " FROM RefreshmentEntry R "
					+ " INNER JOIN Vehicle V ON V.vid = R.vid "
					+ " INNER JOIN MasterParts MP ON MP.partid = R.partid"
					+ " INNER JOIN TripSheet T ON T.tripSheetID = R.tripsheetId"
					+ " where R.companyId = "+object.getInt("companyId")+" AND "+object.getString("queryStr")+" AND  R.markForDelete = 0 ORDER BY R.asignmentDate DESC", Object[].class);
		
		/*	typedQuery.setFirstResult((object.getInt("pageNumber") - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);*/
			resultList = typedQuery.getResultList();

		if (resultList != null && !resultList.isEmpty()) {
			refreshmentList = new ArrayList<>();
			
			for (Object[] result : resultList) {
				inventoryDto = new RefreshmentEntryDto();
				inventoryDto.setRefreshmentEntryId((Long) result[0]);
				inventoryDto.setRefreshmentEntryNumber((Long) result[1]);
				inventoryDto.setVid((Integer) result[2]);
				inventoryDto.setVehicle_registration((String) result[3]);
				inventoryDto.setTripsheetId((Long) result[4]);
				inventoryDto.setTripSheetNumber((Long) result[5]);
				inventoryDto.setPartid((Long) result[6]);
				inventoryDto.setPartname((String) result[7]);
				inventoryDto.setAsignmentDateStr(format.format((Date) result[8]));
				inventoryDto.setQuantity((Double) result[9]);
				inventoryDto.setTotalAmount((Double) result[10]);
				inventoryDto.setComment((String) result[11]);
				inventoryDto.setReturnQuantity((Double) result[12]);
				refreshmentList.add(inventoryDto);
				
			}
		}
			return refreshmentList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
