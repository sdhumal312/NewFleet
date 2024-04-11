package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
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

import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.VehicleAgentConstant;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.constant.VehicleOwnerShip;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.UreaEntriesBL;
import org.fleetopgroup.persistence.bl.VehicleAgentTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.bl.VehicleProfitAndLossTxnCheckerBL;
import org.fleetopgroup.persistence.dao.UreaEntriesHistoryRepository;
import org.fleetopgroup.persistence.dao.UreaEntriesRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.UreaEntriesDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.model.UreaEntries;
import org.fleetopgroup.persistence.model.UreaEntriesHistory;
import org.fleetopgroup.persistence.model.UreaEntriesSequenceCounter;
import org.fleetopgroup.persistence.model.UreaInvoiceToDetails;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.report.dao.IUreaReportDao;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ITyreUsageHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IUreaEntriesSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IUreaEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IUreaInvoiceToDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentIncomeExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UreaEntriesService implements IUreaEntriesService {
	
	@PersistenceContext EntityManager entityManager;

	@Autowired	private	IUreaEntriesSequenceService					ureaEntriesSequenceService;
	@Autowired	private	UreaEntriesRepository						ureaEntriesRepository;
	@Autowired	private	UreaEntriesHistoryRepository				UreaEntriesHistoryRepository;
	@Autowired	private	IUreaInvoiceToDetailsService				ureaInvoiceToDetailsService;
	@Autowired	private	IPartLocationsService						partLocationsService;
	@Autowired  private IVehicleService 							vehicleService;
	@Autowired	private IServiceReminderService 					serviceReminderService;
	@Autowired	private IVehicleOdometerHistoryService  			vehicleOdometerHistoryService;
	@Autowired	private IVehicleProfitAndLossTxnCheckerService		vehicleProfitAndLossTxnCheckerService;
	@Autowired	private IVehicleProfitAndLossService				vehicleProfitAndLossService;
	@Autowired	private	IFuelService								fuelService;
	@Autowired 	private IUreaReportDao								UreaReportDaoImpl;
	@Autowired 	private IUserProfileService							userProfileService;
	@Autowired private	IVehicleAgentTxnCheckerService				vehicleAgentTxnCheckerService;
	@Autowired private	IVehicleAgentIncomeExpenseDetailsService	vehicleAgentIncomeExpenseDetailsService;
	@Autowired private	ITyreUsageHistoryService					tyreUsageHistoryService;
	
	UreaEntriesBL						ureaEntriesBL			= new UreaEntriesBL();
	PartLocationsBL 					PLBL 					= new PartLocationsBL();
	VehicleOdometerHistoryBL 			VOHBL					= new VehicleOdometerHistoryBL();
	VehicleProfitAndLossTxnCheckerBL	txnCheckerBL 			= new VehicleProfitAndLossTxnCheckerBL();
	VehicleAgentTxnCheckerBL			agentTxnCheckerBL		= new VehicleAgentTxnCheckerBL();
	
	SimpleDateFormat					format					= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat					dateFormat				= new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
	SimpleDateFormat 					formatSQL 	 		 	= new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
	DecimalFormat  toFixedTwo = new DecimalFormat("#.##");
	
	private static final int PAGE_SIZE = 10;
	
	@Override
	@Transactional
	public ValueObject saveUreaEntriesDetails(ValueObject valueObject) throws Exception {
		UreaEntries						ureaEntries				= null;
		CustomUserDetails				userDetails				= null;
		UreaEntriesSequenceCounter		sequenceCounter			= null;
		VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker	= null;
		Vehicle							vehicle					= null;
		List<ServiceReminderDto>		serviceList				= null;
		ValueObject						ownerShipObject			= null;
		UreaInvoiceToDetails			ureaInvoiceToDetails	= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			sequenceCounter	= ureaEntriesSequenceService.findNextInvoiceNumber(userDetails.getCompany_id());
			
			if(sequenceCounter != null) {
				ureaInvoiceToDetails =	ureaInvoiceToDetailsService.getUreaInvoiceToDetailsById(valueObject.getLong("ureaLocation"));
				if(ureaInvoiceToDetails != null && valueObject.getDouble("ureaLiters",0) > ureaInvoiceToDetails.getStockQuantity()) {
					valueObject.put("exceedLimit", true);
					return valueObject;
				}
				
				valueObject.put("userDetails", userDetails);
				valueObject.put("ureaEntriesNumber", sequenceCounter.getNextVal());
				
				ureaEntries	= ureaEntriesBL.getUreaEntriesDTO(valueObject);
				
				vehicle			= vehicleService.getVehicel1(ureaEntries.getVid());
				
				if (valueObject.getLong("tripsheetNumber", 0)  > 0) {
					ureaEntries.setTripSheetId(fuelService.getTripSheetIdByNumber(valueObject.getLong("tripsheetNumber", 0)));
				} else {
					ureaEntries.setTripSheetId((long) 0);
				}
				
				ureaEntriesRepository.save(ureaEntries);
				
				ureaInvoiceToDetailsService.updateStockQuantityOnUreaEntry(ureaEntries);
				
				if(ureaEntries.getUreaAmount() != null && ureaEntries.getUreaAmount() > 0) {
					
					ValueObject		object	= new ValueObject();
					
					object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					object.put(VehicleProfitAndLossDto.TRANSACTION_ID, ureaEntries.getUreaEntriesId());
					object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA);
					object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					object.put(VehicleProfitAndLossDto.TRANSACTION_VID, ureaEntries.getVid());
					object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, ureaEntries.getUreaEntriesId());
					
					profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);
					
					vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);
					VehicleDto	vehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(ureaEntries.getVid(), ureaEntries.getCompanyId());
					if(vehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED && ureaEntries.getUreaAmount() != null && ureaEntries.getUreaAmount() > 0){
						ownerShipObject	= new ValueObject();
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, ureaEntries.getUreaEntriesId());
						ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, ureaEntries.getVid());
						ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, ureaEntries.getUreaAmount());
						ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
						ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_VEHICLE_UREA);
						ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, ureaEntries.getCompanyId());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, ureaEntries.getUreaDate());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Urea Entry");
						ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Urea Number : "+ureaEntries.getUreaEntriesNumber());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, ureaEntries.getComments());
						ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, ureaEntries.getCreatedById());
						ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, -ureaEntries.getUreaAmount());
						
						VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
						vehicleAgentTxnCheckerService.save(agentTxnChecker);
						
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
						
					}
				}
				
				
				
				try {
					if (vehicle.getVehicle_Odometer() < ureaEntries.getUreaOdometer()) {
						
						vehicleService.updateCurrentOdoMeter(ureaEntries.getVid(), ureaEntries.getUreaOdometer().intValue(), userDetails.getCompany_id());
						serviceReminderService.updateCurrentOdometerToServiceReminder(ureaEntries.getVid(), ureaEntries.getUreaOdometer().intValue(), userDetails.getCompany_id());
						
						serviceList = serviceReminderService.OnlyVehicleServiceReminderList(ureaEntries.getVid(), userDetails.getCompany_id(), userDetails.getId());
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									
									serviceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
									//emailAlertQueueService.sendEmailServiceOdometer(list);
									//smsAlertQueueService.sendSmsServiceOdometer(list);
								}
							}
						}

						VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToUrea(ureaEntries);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
						valueObject.put("ureaEntriesId", ureaEntries.getUreaEntriesId());
						tyreUsageHistoryService.saveUreaTyreUsageHistory(valueObject);
					}else if(vehicle.getVehicle_Odometer() ==  ureaEntries.getUreaOdometer().intValue()) {
						VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToUrea(ureaEntries);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw e;

				}
				
				if(valueObject.getBoolean("isNextUreaEntry",false) == true) {
					updateNextOldOdometer(valueObject.getLong("nextUreaEntryId",0) ,valueObject.getDouble("ureaOdometer") ,valueObject.getInt("companyId"));
				}
				if(profitAndLossTxnChecker != null) {
					
					ValueObject		valueInObject	= new ValueObject();
					valueInObject.put("ureaEntries", ureaEntries);
					valueInObject.put("userDetails", userDetails);
					valueInObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
					valueInObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA);
					valueInObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					
					new Thread() {
						public void run() {
							try {
								vehicleProfitAndLossService.runThreadForVehicleExpenseDetails(valueInObject);
								vehicleProfitAndLossService.runThreadForDateWiseVehicleExpenseDetails(valueInObject);
								vehicleProfitAndLossService.runThreadForMonthWiseVehicleExpenseDetails(valueInObject);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}		
					}.start();
				}
				
				if(ownerShipObject != null) {
					vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
				}
				
			}else {
				valueObject.put("sequenceNotFound", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			ownerShipObject			= null;
			ureaEntries				= null;
			userDetails				= null;
			sequenceCounter			= null;
			profitAndLossTxnChecker	= null;
			vehicle					= null;
			serviceList				= null;
		}
	}

	
	@Override
	public ValueObject getPageWiseUreaEntriesDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails	   userDetails				= null;
		Integer				   pageNumber				= null;
		try {
			
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber				= valueObject.getInt("pageNumber",0);
			Page<UreaEntries> page  = getDeployment_Page_UreaInvoice(pageNumber, userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());
			
			List<UreaEntriesDto> ureaEntriesList = getUreaEntriesDtoList(pageNumber, userDetails.getCompany_id());
			
			valueObject.put("ureaEntriesList", ureaEntriesList);
			valueObject.put("SelectPage", pageNumber);
			valueObject.put("PartLocations", PLBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
		
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			 userDetails				= null;
		}
	}
	
	@Override
	public Page<UreaEntries> getDeployment_Page_UreaInvoice(Integer pageNumber, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "ureaEntriesId");
		return ureaEntriesRepository.getDeployment_Page_UreaEntries(companyId, pageable);
	}
	
	@Override
	public List<UreaEntriesDto> getUreaEntriesDtoList(Integer pageNumber, Integer companyId) throws Exception {

		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]> 					resultList 				= null; 
		List<UreaEntriesDto> 			ureaEntriesList 		= null;
		UreaEntriesDto 					ureaEntriesDto			= null;

		try {
			typedQuery = entityManager.createQuery("SELECT UE.ureaEntriesId, UE.ureaEntriesNumber, UE.vid, UE.tripSheetId, UE.ureaInvoiceToDetailsId,"
					+ " UE.locationId, UE.ureaDate, UE.ureaOdometer, UE.ureaOdometerOld, UE.ureaLiters , UE.ureaRate, UE.ureaAmount, T.tripSheetNumber,"
					+ " V.vehicle_registration"
					+ " FROM UreaEntries AS UE"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = UE.locationId"
					+ " INNER JOIN Vehicle V ON V.vid = UE.vid"
					+ " LEFT JOIN TripSheet T ON T.tripSheetID = UE.tripSheetId"
					+ " WHERE UE.companyId ="+companyId+" AND UE.markForDelete = 0 ORDER BY UE.ureaEntriesId DESC", Object[].class);

			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				ureaEntriesList = new ArrayList<>();

				for (Object[] result : resultList) {
					ureaEntriesDto = new UreaEntriesDto();
					
					ureaEntriesDto.setUreaEntriesId((Long) result[0]);
					ureaEntriesDto.setUreaEntriesNumber((Long) result[1]);
					ureaEntriesDto.setVid((Integer) result[2]);
					ureaEntriesDto.setTripSheetId((Long) result[3]);
					ureaEntriesDto.setUreaInvoiceToDetailsId((Long) result[4]);
					ureaEntriesDto.setLocationId((Integer) result[5]);
					ureaEntriesDto.setUreaDate((Date) result[6]);
					ureaEntriesDto.setUreaOdometer((Double) result[7]);
					ureaEntriesDto.setUreaOdometerOld((Double) result[8]);
					if(result[9] != null)
					ureaEntriesDto.setUreaLiters(Double.parseDouble(toFixedTwo.format(result[9])));
					if(result[10] != null)
					ureaEntriesDto.setUreaRate(Double.parseDouble(toFixedTwo.format(result[10])));
					if( result[11] != null)
					ureaEntriesDto.setUreaAmount(Double.parseDouble(toFixedTwo.format(result[11])));
					ureaEntriesDto.setTripSheetNumber((Long) result[12]);
					ureaEntriesDto.setVehicle_registration((String) result[13]);
					ureaEntriesDto.setUreaDateStr(format.format(ureaEntriesDto.getUreaDate()));
					
					ureaEntriesList.add(ureaEntriesDto);
					
				}
			}

			return ureaEntriesList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			ureaEntriesList 		= null;
			ureaEntriesDto			= null;
		}
	}
	
	@Override
	public ValueObject getShowUreaEntryDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails			= null;
		UreaEntriesDto			ureaEntriesDto		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			ureaEntriesDto	=	getUreaEntriesDetailsByInvcId(valueObject.getLong("ureaEntriesId", 0), userDetails.getCompany_id());
			
			valueObject.put("ureaEntriesDto", ureaEntriesDto);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public UreaEntriesDto getUreaEntriesDtoDetails(Long ureaEntriesId, Integer companyId) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT CI.ureaEntriesId, CI.ureaEntriesNumber, CI.vid, CI.tripSheetId, CI.ureaInvoiceToDetailsId, CI.locationId, CI.ureaDate, CI.ureaOdometer,"
				+ " CI.ureaOdometerOld, CI.ureaLiters, CI.ureaRate, CI.discount, CI.gst, CI.ureaAmount, CI.ureaManufacturerId, CI.reference,"
				+ " CI.driver_id, CI.secDriverID, CI.cleanerID, CI.routeID, CI.comments, CI.createdById, CI.lastModifiedById, CI.created,"
				+ " CI.lastupdated, PL.partlocation_name, D.driver_firstname, D.driver_Lastname,D2.driver_firstname, D2.driver_Lastname,"
				+ " D3.driver_firstname, D3.driver_Lastname, R.routeName, U.firstName, U2.firstName, V.vehicle_registration, VG.vGroup, "
				+ " T.tripSheetNumber "
				+ " FROM UreaEntries CI "
				+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CI.locationId"
				+ " INNER JOIN Vehicle V ON V.vid = CI.vid "
				+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId "
				+ " INNER JOIN User U ON U.id = CI.createdById"
				+ " INNER JOIN User U2 ON U2.id = CI.lastModifiedById "
				+ " LEFT JOIN TripSheet T ON T.tripSheetID = CI.tripSheetId"
				+ " LEFT JOIN Driver D ON D.driver_id = CI.driver_id"
				+ " LEFT JOIN Driver D2 ON D2.driver_id = CI.secDriverID"
				+ " LEFT JOIN Driver D3 ON D3.driver_id = CI.cleanerID"
				+ " LEFT JOIN TripRoute R ON R.routeID = CI.routeID"
				+ " where CI.ureaEntriesId = "+ureaEntriesId+" AND CI.companyId = "+companyId+" AND CI.markForDelete = 0");

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		UreaEntriesDto select;
		if (result != null) {
			select = new UreaEntriesDto();
			
			select.setUreaEntriesId((Long) result[0]);
			select.setUreaEntriesNumber((Long) result[1]);
			select.setVid((Integer) result[2]);
			select.setTripSheetId((Long) result[3]);
			select.setUreaInvoiceToDetailsId((Long) result[4]);
			select.setLocationId((Integer) result[5]);
			select.setUreaDate((Date) result[6]);
			select.setUreaOdometer((Double) result[7]);
			select.setUreaOdometerOld((Double) result[8]);
			select.setUreaLiters((Double) result[9]);
			select.setUreaRate((Double) result[10]);
			select.setDiscount((Double) result[11]);
			select.setGst((Double) result[12]);
			select.setUreaAmount((Double) result[13]);
			select.setUreaManufacturerId((Long) result[14]);
			select.setReference((String) result[15]);
			select.setDriver_id((Integer) result[16]);
			select.setSecDriverID((Integer) result[17]);
			select.setCleanerID((Integer) result[18]);
			select.setRouteID((Integer) result[19]);
			select.setComments((String) result[20]);
			select.setCreatedById((Long) result[21]);
			select.setLastModifiedById((Long) result[22]);
			select.setCreated((Date) result[23]);
			select.setLastupdated((Date) result[24]);
			select.setLocationName((String) result[25]);
			select.setFirsDriverName((String) result[26]+"_"+(String) result[27]);
			
			if(result[28] != null && result[29] != null) {
				select.setSecDriverName((String) result[28]+"_"+(String) result[29]);
			}else if(result[28] != null && result[29] == null) {
				select.setSecDriverName((String) result[28]);
			}else {
				select.setSecDriverName("--");
			}
			

			if(result[30] != null && result[31] != null) {
				select.setCleanerName((String) result[30]+"_"+(String) result[31]);
			}else if(result[30] != null && result[31] == null) {
				select.setCleanerName((String) result[30]);
			}else {
				select.setCleanerName("--");
			}
			
			
			select.setRouteName((String) result[32]);
			select.setCreatedBy((String) result[33]);
			select.setLastModifiedBy((String) result[34]);
			select.setVehicle_registration((String) result[35]);
			select.setVehicleGrpName((String) result[36]);
			
			if(result[37] != null) {
				select.setTripSheetNumber((long) result[37]);
			}else {
				select.setTripSheetNumber((long) 0);
			}
			
			if(select.getCreated() != null) {
				select.setCreatedOnStr(dateFormat.format(select.getCreated()));
			}else {
				select.setCreatedOnStr("--");
			}
			
			if(select.getLastupdated() != null) {
				select.setLastModifiedStr(dateFormat.format(select.getLastupdated()));
			}else {
				select.setLastModifiedStr("--");
			}
			
			if(select.getUreaDate() != null) {
				select.setUreaDateStr(format.format(select.getUreaDate()));
			}else {
				select.setUreaDateStr("--");	
			}
			
		} else {
			return null;
		}

		return select;
	}
	
	@Override
	public ValueObject searchUreaEntriesByNumber(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails		= null;
		Long						invoiceId		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			invoiceId	=	ureaEntriesRepository.searchUreaEntriesByNumber(valueObject.getLong("ureaInvoiceNumber", 0), userDetails.getCompany_id());
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
	
	@Override
	public ValueObject getdateWiseUreaEntryDetailsReport(ValueObject valueObject) throws Exception {// Date Wise Urea Report service
		Integer						vehicleId				= 0;
		String						dateRange				= "";
		CustomUserDetails			userDetails				= null;
		ValueObject					tableConfig				= null;
		List<UreaEntriesDto> 		UreaDtoList			   	= null;	
		String 						query					= "";
		int 						driverId				= 0 ;
		int 						secDriverId				= 0 ;				
		int 						routeId					= 0 ;
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			vehicleId						= valueObject.getInt("vehicleId", 0);
			dateRange						= valueObject.getString("date");
			driverId						= valueObject.getInt("driverId", 0);
			secDriverId						= valueObject.getInt("secDriverId", 0);
			routeId							= valueObject.getInt("routeId", 0);
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;			

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				
				String Driver_Name = "", Route = "" , Vehicle = "",Date = "", SEC_DRIVER ="";
				
				if(driverId > 0 )
				{
					Driver_Name = " AND U.driver_id = "+ driverId +" ";
				}
				if(routeId > 0 )
				{
					Route = " AND  U.routeID = "+ routeId +" ";
				}
				if(vehicleId > 0 )
				{
					Vehicle = " AND  U.vid = "+ vehicleId +" ";
				}
				if(secDriverId > 0 )
				{
					SEC_DRIVER = " AND  U.secDriverID = "+ secDriverId +" ";
				}
				
				Date		=	" AND U.ureaDate between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;
				
				
				query       = " " + Driver_Name + " " + SEC_DRIVER+ " " + Route + " "+ Vehicle +" "+ Date+" ";
			}	
			
			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.DATE_WISE_UREA_ENTRY_DATA_FILE_PATH);

			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);		
			
			UreaDtoList		= UreaReportDaoImpl.getUreaEntryDetails_Report(query,userDetails.getCompany_id());
			
			if(driverId != 0 ) {
				if(UreaDtoList != null)
					valueObject.put("DriverKey", UreaDtoList.get(0).getFirsDriverName());
			} else {
				valueObject.put("DriverKey", "All");
			}
			
			if(secDriverId != 0 ) {
				if(UreaDtoList != null)
					valueObject.put("SecDriverKey", UreaDtoList.get(0).getSecDriverName());
			} else {
				valueObject.put("SecDriverKey", "All");
			}
			
			
			if(routeId != 0 ) {
				if(UreaDtoList != null)
				valueObject.put("RouteKey", UreaDtoList.get(0).getRouteName());
			} else {
				valueObject.put("RouteKey", "All");
			}
			
			if(vehicleId > 0) {
				if(UreaDtoList != null)
				valueObject.put("VehicleKey", UreaDtoList.get(0).getVehicle_registration());
			} else {
				valueObject.put("VehicleKey", "All");
			}
			
			valueObject.put("fuelEntryDetails", UreaDtoList);
			
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;			
		}
	}	
	
	
	@Override
	public UreaEntriesDto getUreaEntriesDetailsByInvcId(Long ureaEntriesId, Integer companyId) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT CI.ureaEntriesId, CI.ureaEntriesNumber, CI.vid, CI.tripSheetId, CI.ureaInvoiceToDetailsId, CI.locationId, CI.ureaDate, "
				+ " CI.ureaOdometer, CI.ureaOdometerOld, CI.ureaLiters, CI.ureaRate, CI.discount, CI.gst, CI.ureaAmount, CI.ureaManufacturerId, "
				+ " CI.reference, CI.driver_id, CI.secDriverID, CI.cleanerID, CI.routeID, CI.comments, CI.createdById, CI.lastModifiedById, "
				+ " CI.created, CI.lastupdated, PL.partlocation_name, D.driver_firstname, D.driver_Lastname,D2.driver_firstname, D2.driver_Lastname, "
				+ " D3.driver_firstname, D3.driver_Lastname, R.routeName, U.firstName, U2.firstName, V.vehicle_registration, VG.vGroup, "
				+ " T.tripSheetNumber, V.vehicle_ExpectedOdameter, UID.quantity, UID.usedQuantity, UID.stockQuantity, UI.ureaInvoiceNumber, "
				+ " UM.manufacturerName, CI.meterWorkingStatus,D.driver_fathername,D2.driver_fathername,CI.filledLocationId,LO.partlocation_name ,CI.filledBy "
				+ " FROM UreaEntries CI "
				+ " INNER JOIN UreaInvoiceToDetails UID ON UID.ureaInvoiceToDetailsId = CI.ureaInvoiceToDetailsId "
				+ " INNER JOIN UreaInvoice UI ON UI.ureaInvoiceId = UID.ureaInvoiceId "
				+ " LEFT JOIN UreaManufacturer UM ON UM.ureaManufacturerId = UID.manufacturerId "
				+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CI.locationId "
				+ " INNER JOIN Vehicle V ON V.vid = CI.vid "
				+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId "
				+ " INNER JOIN User U ON U.id = CI.createdById"
				+ " INNER JOIN User U2 ON U2.id = CI.lastModifiedById "
				+ " LEFT JOIN TripSheet T ON T.tripSheetID = CI.tripSheetId"
				+ " LEFT JOIN Driver D ON D.driver_id = CI.driver_id"
				+ " LEFT JOIN Driver D2 ON D2.driver_id = CI.secDriverID"
				+ " LEFT JOIN Driver D3 ON D3.driver_id = CI.cleanerID"
				+ " LEFT JOIN TripRoute R ON R.routeID = CI.routeID"
				+ " LEFT JOIN PartLocations LO ON LO.partlocation_id = CI.filledLocationId "
				+ " where CI.ureaEntriesId = "+ureaEntriesId+" AND CI.companyId = "+companyId+" AND CI.markForDelete = 0");

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		UreaEntriesDto select;
		if (result != null) {
			select = new UreaEntriesDto();
			
			select.setUreaEntriesId((Long) result[0]);
			select.setUreaEntriesNumber((Long) result[1]);
			select.setVid((Integer) result[2]);
			select.setTripSheetId((Long) result[3]);
			select.setUreaInvoiceToDetailsId((Long) result[4]);
			select.setLocationId((Integer) result[5]);
			select.setUreaDate((Date) result[6]);
			
			select.setUreaOdometer((Double) result[7]);
			select.setUreaOdometerOld((Double) result[8]);
			if(result[9] != null)
			select.setUreaLiters(Double.parseDouble(toFixedTwo.format( result[9])));
			if(result[10] != null)
			select.setUreaRate(Double.parseDouble(toFixedTwo.format(result[10])));
			if(result[11] != null)
			select.setDiscount(Double.parseDouble(toFixedTwo.format(result[11])));
			if( result[12] != null)
			select.setGst(Double.parseDouble(toFixedTwo.format(result[12])));
			if(result[13] != null)
			select.setUreaAmount(Double.parseDouble(toFixedTwo.format(result[13])));
			
			select.setUreaManufacturerId((Long) result[14]);
			select.setReference((String) result[15]);
			select.setDriver_id((Integer) result[16]);
			select.setSecDriverID((Integer) result[17]);
			select.setCleanerID((Integer) result[18]);
			select.setRouteID((Integer) result[19]);
			select.setComments((String) result[20]);
			select.setCreatedById((Long) result[21]);
			select.setLastModifiedById((Long) result[22]);
			select.setCreated((Date) result[23]);
			select.setLastupdated((Date) result[24]);
			select.setLocationName((String) result[25]);
			
			if(result[26] != null) {
				select.setFirsDriverName((String) result[26]);
			} else {
				select.setFirsDriverName("");
			}
			if(result[27] != null) {
				select.setFirsDriverLastName((String) result[27]);
			}else {
				select.setFirsDriverLastName(" ");
			}
			
			if(result[28] != null) {
				select.setSecDriverName((String) result[28]);
			} else {
				select.setSecDriverName("");
			}
			if(result[29] != null) {
				select.setSecDriverLastName((String) result[29]);
			}else {
				select.setSecDriverLastName(" ");
			}
			
			if(result[30] != null) {
				select.setCleanerName((String) result[30]);
			} else {
				select.setCleanerName("");
			}
			if(result[31] != null)
			select.setCleanerLastName((String) result[31]);	
			
			if(result[32] != null) {
				select.setRouteName((String) result[32]);
			} else {
				select.setRouteName("");
			}
			
			select.setCreatedBy((String) result[33]);
			select.setLastModifiedBy((String) result[34]);
			select.setVehicle_registration((String) result[35]);
			select.setVehicleGrpName((String) result[36]);
			
			if(result[37] != null) {
				select.setTripSheetNumber((long) result[37]);
			}else {
				select.setTripSheetNumber((long) 0);
			}
			
			if(result[38] != null) {
				select.setVehicle_ExpectedOdameter((int) result[38]);
			} else {
				select.setVehicle_ExpectedOdameter(0);
			}
			if( result[39] != null)
			select.setQuantity(Double.parseDouble(toFixedTwo.format(result[39])));
			if( result[40] != null)
			select.setUsedQuantity(Double.parseDouble(toFixedTwo.format(result[40])));
			if( result[41] != null)
			select.setStockQuantity(Double.parseDouble(toFixedTwo.format(result[41])));
			select.setUreaInvoiceNumber((long) result[42]);
			if(result[43] != null) {
				select.setManufacturerName((String) result[43]);
			}else {
				select.setManufacturerName(" ");
			}
			
			if(select.getCreated() != null) {
				select.setCreatedOnStr(dateFormat.format(select.getCreated()));
			}else {
				select.setCreatedOnStr("--");
			}
			
			if(select.getLastupdated() != null) {
				select.setLastModifiedStr(dateFormat.format(select.getLastupdated()));
			}else {
				select.setLastModifiedStr("--");
			}
			
			if(select.getUreaDate() != null) {
				select.setUreaDateStr(format.format(select.getUreaDate()));
			}else {
				select.setUreaDateStr("--");	
			}
			select.setMeterWorkingStatus((boolean) result[44]);
			
			if(result[45] != null && !((String)result[45]).trim().equals(""))
				select.setFirsDriverLastName(select.getFirsDriverLastName()+" - "+result[45]);
			
			if(result[46] != null && !((String)result[46]).trim().equals(""))
				select.setSecDriverLastName(select.getSecDriverLastName()+" - "+result[46]);
			
			select.setFilledLocationId((Integer) result[47]);
			if(result[48] != null) {
				select.setLocationName((String) result[48]);
			}else{
				select.setLocationName(" ");
			}
			
			select.setFilledBy((String) result[49]);
			
		} else {
			return null;
		}

		return select;
	}
	
	@Override
	@Transactional
	public ValueObject updateUreaEntriesDetails(ValueObject valueObject) throws Exception {
		UreaEntries     		 		oldUreaEntry  					= null;
		UreaEntries						ureaEntries						= null;
		UreaEntriesHistory				history							= null;
		CustomUserDetails				userDetails						= null;
		VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker			= null;
		List<ServiceReminderDto>		serviceList						= null;
		double							newStckQty						= 0.0;
		String							previousUreaDate				= null;
		long							oldUreaInvoiceToDetailsId		= 0;
		double							oldUreaLiters					= 0.0;
		long							newUreaInvoiceToDetailsId		= 0;
		double							newUreaLiters					= 0.0;
		Date							previousDate					= null;
		Double							previousAmount					= 0.0;
		UreaInvoiceToDetails			ureaInvoiceToDetails			= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("userDetails", userDetails);
			
			oldUreaEntry 	 			= ureaEntriesRepository.getUreaEntryDetailsById(valueObject.getLong("ureaEntriesInvoiceId"), userDetails.getCompany_id());
			previousDate				= oldUreaEntry.getUreaDate();
			previousAmount				= oldUreaEntry.getUreaAmount();
			newStckQty       			= oldUreaEntry.getUreaLiters() - valueObject.getDouble("ureaLiters");
			previousUreaDate 			= format.format(oldUreaEntry.getUreaDate());
			oldUreaInvoiceToDetailsId   = oldUreaEntry.getUreaInvoiceToDetailsId();
			oldUreaLiters				= oldUreaEntry.getUreaLiters();
			history   					= ureaEntriesBL.updateUreaEntriesHistory(oldUreaEntry);
			
			ureaInvoiceToDetails 		= ureaInvoiceToDetailsService.getUreaInvoiceToDetailsById(valueObject.getLong("newUreaInvoiceToDetailsId"));
			
			if(valueObject.getLong("ureaInvoiceToDetailsId") == valueObject.getLong("newUreaInvoiceToDetailsId")) {
				if(ureaInvoiceToDetails != null && valueObject.getDouble("ureaLiters",0) > (oldUreaEntry.getUreaLiters()+ureaInvoiceToDetails.getStockQuantity())) {
					valueObject.put("exceedLimit", true);
					return valueObject;
				}
			}else {
				if(ureaInvoiceToDetails != null && valueObject.getDouble("ureaLiters",0) > (ureaInvoiceToDetails.getStockQuantity())) {
					valueObject.put("exceedLimit", true);
					return valueObject;
				}
			}
			
			UreaEntriesHistoryRepository.save(history);
			
			ureaEntries		 =	ureaEntriesBL.updateUreaEntriesDTO(valueObject);
			if (valueObject.getLong("tripsheetNumber", 0)  > 0) {
				ureaEntries.setTripSheetId(fuelService.getTripSheetIdByNumber(valueObject.getLong("tripsheetNumber", 0)));
			} else {
				ureaEntries.setTripSheetId((long) 0);
			}
			ureaEntriesRepository.save(ureaEntries);
			newUreaInvoiceToDetailsId = ureaEntries.getUreaInvoiceToDetailsId();
			newUreaLiters 			  = ureaEntries.getUreaLiters();
			
			if(valueObject.getLong("ureaInvoiceToDetailsId") == valueObject.getLong("newUreaInvoiceToDetailsId")) {
				ureaInvoiceToDetailsService.updateStockQuantityOnEditUreaEntry(ureaEntries, newStckQty);
			} else {
				ureaInvoiceToDetailsService.updateStckQtyOnEditDiffrentUreaEntry(oldUreaInvoiceToDetailsId, 
						oldUreaLiters, newUreaInvoiceToDetailsId, newUreaLiters);
			}
			
			VehicleDto	CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(ureaEntries.getVid(), ureaEntries.getCompanyId());
			ValueObject	ownerShipObject	= null;	
			if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED){
				if((ureaEntries.getUreaAmount() - previousAmount) != 0 || !(previousDate.equals(new Timestamp(ureaEntries.getUreaDate().getTime())))) {
					
					ownerShipObject	= new ValueObject();
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, ureaEntries.getUreaEntriesId());
					ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, ureaEntries.getVid());
					ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, ureaEntries.getUreaAmount());
					ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
					ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_VEHICLE_UREA);
					ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, ureaEntries.getCompanyId());
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, ureaEntries.getUreaDate());
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Urea Entry");
					ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Urea Number : "+ureaEntries.getUreaEntriesNumber());
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, ureaEntries.getComments());
					ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, ureaEntries.getCreatedById());
					if(previousDate.equals(new Timestamp(ureaEntries.getUreaDate().getTime()))) {
						ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, - (ureaEntries.getUreaAmount() - previousAmount));
						ownerShipObject.put("isDateChanged", false);
					}else {
						ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, - ureaEntries.getUreaAmount());
						ownerShipObject.put("isDateChanged", true);
						
					}
					ownerShipObject.put("previousAmount", -previousAmount);
					ownerShipObject.put("previousDate", previousDate);
					
					
					VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
					vehicleAgentTxnCheckerService.save(agentTxnChecker);
					
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
				}
					
				}
			if(!(DateTimeUtility.getDateTimeFromStr(previousUreaDate, DateTimeUtility.DD_MM_YYYY).equals(new Timestamp(ureaEntries.getUreaDate().getTime()))) || (ureaEntries.getUreaAmount() - previousAmount) != 0) {
				
				ValueObject		object	= new ValueObject();
				
				object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
				object.put(VehicleProfitAndLossDto.TRANSACTION_ID, ureaEntries.getUreaEntriesId());
				object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA);
				object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
				object.put(VehicleProfitAndLossDto.TRANSACTION_VID, ureaEntries.getVid());
				object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, ureaEntries.getUreaEntriesId());
				
				profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);
				
				vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);
			}
			
			try {
				Vehicle	vehicle	= vehicleService.getVehicel1(ureaEntries.getVid());
				
				if (vehicle.getVehicle_Odometer() < ureaEntries.getUreaOdometer()) {
					
					vehicleService.updateCurrentOdoMeter(ureaEntries.getVid(), ureaEntries.getUreaOdometer().intValue(), userDetails.getCompany_id());
					serviceReminderService.updateCurrentOdometerToServiceReminder(ureaEntries.getVid(), ureaEntries.getUreaOdometer().intValue(), userDetails.getCompany_id());
					
					serviceList = serviceReminderService.OnlyVehicleServiceReminderList(ureaEntries.getVid(), userDetails.getCompany_id(), userDetails.getId());
					if(serviceList != null) {
						for(ServiceReminderDto list : serviceList) {
							
							if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
								
								serviceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
								//emailAlertQueueService.sendEmailServiceOdometer(list);
								//smsAlertQueueService.sendSmsServiceOdometer(list);
							}
						}
					}

					VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToUrea(ureaEntries);
					vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
					vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;

			}
			
			if(valueObject.getBoolean("isNextUreaEntry",false) == true) {
				updateNextOldOdometer(valueObject.getLong("nextUreaEntryId",0) ,valueObject.getDouble("ureaOdometer") ,valueObject.getInt("companyId"));
			}
			
			if(!(DateTimeUtility.getDateTimeFromStr(previousUreaDate, DateTimeUtility.DD_MM_YYYY).equals(new Timestamp(ureaEntries.getUreaDate().getTime()))) || (ureaEntries.getUreaAmount() - previousAmount) != 0) {
				
				ValueObject		valueInObject	= new ValueObject();
				valueInObject.put("ureaEntries", ureaEntries);
				valueInObject.put("userDetails", userDetails);
				valueInObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
				valueInObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA);
				valueInObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				
				valueInObject.put("isUreaEdit", true);
				valueInObject.put("previousUreaAmount", previousAmount);
				valueInObject.put("ureaAmountToBeAdded", (ureaEntries.getUreaAmount() - previousAmount));
				
				if(DateTimeUtility.getDateTimeFromStr(previousUreaDate, DateTimeUtility.DD_MM_YYYY).equals(new Timestamp(ureaEntries.getUreaDate().getTime()))) {
					valueInObject.put("isUreaDateChanged", false);
				}else {
					valueInObject.put("isUreaDateChanged", true);
				}
				valueInObject.put("previousUreaDate", DateTimeUtility.getDateTimeFromStr(previousUreaDate, DateTimeUtility.DD_MM_YYYY));
				new Thread() {
					public void run() {
						try {
							vehicleProfitAndLossService.runThreadForVehicleExpenseDetails(valueInObject);
							vehicleProfitAndLossService.runThreadForDateWiseVehicleExpenseDetails(valueInObject);
							vehicleProfitAndLossService.runThreadForMonthWiseVehicleExpenseDetails(valueInObject);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}		
				}.start();
			}
			
			if(ownerShipObject != null) {
				vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	@Transactional
	public ValueObject deleteUreaEntryById(ValueObject valObj) throws Exception {
		CustomUserDetails				userDetails				= null;
		UreaEntries     		 		ureaEntries  			= null;
		Vehicle							vehicle					= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valObj.put("userDetails", userDetails);
			
			ureaEntries = ureaEntriesRepository.getUreaEntryDetailsById(valObj.getLong("ureaEntriesId"), userDetails.getCompany_id());
			if(ureaEntries != null) {
				vehicle  	= vehicleService.getVehicel(ureaEntries.getVid(), userDetails.getCompany_id());
				ValueObject	valueObject	= new ValueObject();
				valueObject.put("ureaEntries", ureaEntries);
				valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA);
				valueObject.put("txnAmount", ureaEntries.getUreaAmount());
				valueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(ureaEntries.getUreaDate()));
				valueObject.put("txnTypeId", ureaEntries.getUreaEntriesId());
				valueObject.put("vid", ureaEntries.getVid());
				valueObject.put("companyId", ureaEntries.getCompanyId());
				
				//1.if current odomter is greate than urea odomter then there will be next entry present 
				//2.if both odomter same then there are posibility to have next entry or pre entry on both cases odomter can not change
				
				if(vehicle.getVehicle_Odometer().equals(ureaEntries.getUreaOdometer().intValue())) { 
					VehicleOdometerHistory odometerHistoryLsit	= vehicleOdometerHistoryService.getVehicleOdometerHistoryByVidExceptCurrentEntry(ureaEntries.getUreaEntriesId(),ureaEntries.getVid(), userDetails.getCompany_id());
					if(odometerHistoryLsit != null ) {
						if(odometerHistoryLsit.getVehicle_Odometer() < ureaEntries.getUreaOdometer().intValue()) { //  found either pre entry or post entry
							vehicleService.updateCurrentOdoMeter(ureaEntries.getVid(), odometerHistoryLsit.getVehicle_Odometer(), userDetails.getCompany_id());
							vehicleOdometerHistoryService.deleteVehicleOdometerHistory(ureaEntries.getUreaEntriesId(), userDetails.getCompany_id());
						}
						
					}
					
				}
				
				ureaEntriesRepository.deleteUreaEntriesById(valObj.getLong("ureaEntriesId"), userDetails.getCompany_id());
				ureaInvoiceToDetailsService.updateStockQuantityOnDeleteUreaEntry(valObj.getLong("ureaEntriesId"));
				
				
				
				VehicleDto	CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(ureaEntries.getVid(), ureaEntries.getCompanyId());
				ValueObject	ownerShipObject	= null;
				if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED && ureaEntries.getUreaAmount() > 0){
					ownerShipObject	= new ValueObject();
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, ureaEntries.getUreaEntriesId());
					ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, ureaEntries.getVid());
					ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, ureaEntries.getUreaAmount());
					ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
					ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_VEHICLE_UREA);
					ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, ureaEntries.getCompanyId());
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, ureaEntries.getUreaDate());
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Urea Entry");
					ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Urea Number : "+ureaEntries.getUreaEntriesNumber());
					ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, ureaEntries.getComments());
					ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, ureaEntries.getCreatedById());
					ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, ureaEntries.getUreaAmount());
					
					vehicleAgentIncomeExpenseDetailsService.deleteVehicleAgentIncomeExpense(ownerShipObject);
				}
				new Thread() {
					public void run() {
						try {
							vehicleProfitAndLossService.runThreadForDeleteVehicleExpenseDetails(valueObject);
							vehicleProfitAndLossService.runThreadForDeleteDateWiseVehicleExpenseDetails(valueObject);
							vehicleProfitAndLossService.runThreadForDeleteMonthWiseVehicleExpenseDetails(valueObject);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}		
				}.start();
			}
			
			return valObj;
		}catch (Exception e) {
			throw e;
		}
	}	
	
	@Override
	public void deletePreviousVehicleUreaEntries(UreaEntries ureaEntries) throws Exception{
		
		ValueObject	valueObject	= new ValueObject();
		valueObject.put("ureaEntries", ureaEntries);
		valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA);
		valueObject.put("txnAmount", ureaEntries.getUreaAmount());
		valueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(ureaEntries.getUreaDate()));
		valueObject.put("txnTypeId", ureaEntries.getUreaEntriesId());
		valueObject.put("vid", ureaEntries.getVid());
		valueObject.put("companyId", ureaEntries.getCompanyId());
		
		new Thread() {
			public void run() {
				try {
					vehicleProfitAndLossService.runThreadForDeleteVehicleExpenseDetails(valueObject);
					vehicleProfitAndLossService.runThreadForDeleteDateWiseVehicleExpenseDetails(valueObject);
					vehicleProfitAndLossService.runThreadForDeleteMonthWiseVehicleExpenseDetails(valueObject);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}		
		}.start();
	}
	
	@Override
	@Transactional
	public void updateVidOfUreaEntries(long tripSheetId, int vid, int companyId) throws Exception {
		CustomUserDetails		 userDetails					= null;
		Date					 updatedDate					;
		try {
			userDetails					 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			updatedDate					 = new Date();
			java.sql.Timestamp sqlDate   = new java.sql.Timestamp(updatedDate.getTime());
			
			entityManager.createQuery(
				"Update UreaEntries SET vid = "+vid+", lastupdated = '"+sqlDate+"', "
					+ " lastModifiedById = "+userDetails.getId()+" "
					+ " where tripSheetId = "+tripSheetId+" "
					+ " AND companyId = "+companyId+" ")
			.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void saveVehicleProfitAndLossTxnCheckerForUrea(ValueObject valueObject) throws Exception {
		CustomUserDetails				userDetails					= null;
		UreaEntries						ureaEntries					= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ureaEntries			= (UreaEntries) valueObject.get("ureaEntries");
			ValueObject	object	= new ValueObject();
			
			object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
			object.put(VehicleProfitAndLossDto.TRANSACTION_ID, ureaEntries.getUreaEntriesId());
			object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
			object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA);
			object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
			object.put(VehicleProfitAndLossDto.TRANSACTION_VID, ureaEntries.getVid());
			object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, ureaEntries.getUreaEntriesId());
			
			VehicleProfitAndLossTxnChecker profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);
			vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);
			
			ValueObject	valueInObject = new ValueObject();
			valueInObject.put("ureaEntries", ureaEntries);
			valueInObject.put("userDetails", userDetails);
			valueInObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
			valueInObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA);
			valueInObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
			new Thread() {
				public void run() {
					try {
						vehicleProfitAndLossService.runThreadForVehicleExpenseDetails(valueInObject);
						vehicleProfitAndLossService.runThreadForDateWiseVehicleExpenseDetails(valueInObject);
						vehicleProfitAndLossService.runThreadForMonthWiseVehicleExpenseDetails(valueInObject);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}		
			}.start();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public List<UreaEntriesDto> getUreaEntriesDetailsByTripSheetId(Long tripSheetId, Integer companyId) throws Exception {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT CI.ureaEntriesId, CI.ureaEntriesNumber, CI.vid, CI.tripSheetId, CI.ureaInvoiceToDetailsId, CI.locationId, CI.ureaDate, "
				+ " CI.ureaOdometer, CI.ureaOdometerOld, CI.ureaLiters, CI.ureaRate, CI.discount, CI.gst, CI.ureaAmount, CI.ureaManufacturerId, "
				+ " CI.reference, CI.driver_id, CI.secDriverID, CI.cleanerID, CI.routeID, CI.comments, CI.createdById, CI.lastModifiedById, "
				+ " CI.created, CI.lastupdated, PL.partlocation_name, D.driver_firstname, D.driver_Lastname,D2.driver_firstname, D2.driver_Lastname, "
				+ " D3.driver_firstname, D3.driver_Lastname, R.routeName, U.firstName, U2.firstName, V.vehicle_registration, VG.vGroup, "
				+ " T.tripSheetNumber, V.vehicle_ExpectedOdameter, UID.quantity, UID.usedQuantity, UID.stockQuantity, UI.ureaInvoiceNumber, "
				+ " UM.manufacturerName "
				+ " FROM UreaEntries CI "
				+ " INNER JOIN UreaInvoiceToDetails UID ON UID.ureaInvoiceToDetailsId = CI.ureaInvoiceToDetailsId "
				+ " INNER JOIN UreaInvoice UI ON UI.ureaInvoiceId = UID.ureaInvoiceId "
				+ " INNER JOIN UreaManufacturer UM ON UM.ureaManufacturerId = UID.manufacturerId "
				+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CI.locationId "
				+ " INNER JOIN Vehicle V ON V.vid = CI.vid "
				+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId "
				+ " INNER JOIN User U ON U.id = CI.createdById"
				+ " INNER JOIN User U2 ON U2.id = CI.lastModifiedById "
				+ " LEFT JOIN TripSheet T ON T.tripSheetID = CI.tripSheetId"
				+ " LEFT JOIN Driver D ON D.driver_id = CI.driver_id"
				+ " LEFT JOIN Driver D2 ON D2.driver_id = CI.secDriverID"
				+ " LEFT JOIN Driver D3 ON D3.driver_id = CI.cleanerID"
				+ " LEFT JOIN TripRoute R ON R.routeID = CI.routeID"
				+ " where CI.tripSheetId = "+tripSheetId+" AND CI.companyId = "+companyId+" "
				+ " AND CI.markForDelete = 0 ORDER BY CI.ureaEntriesId DESC ",
				Object[].class);
		List<Object[]> results = query.getResultList();
		
		List<UreaEntriesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<UreaEntriesDto>();
			UreaEntriesDto select = null;
			for (Object[] result : results) {
				select = new UreaEntriesDto();
				
				select.setUreaEntriesId((Long) result[0]);
				select.setUreaEntriesNumber((Long) result[1]);
				select.setVid((Integer) result[2]);
				select.setTripSheetId((Long) result[3]);
				select.setUreaInvoiceToDetailsId((Long) result[4]);
				select.setLocationId((Integer) result[5]);
				select.setUreaDate((Date) result[6]);
				select.setUreaOdometer((Double) result[7]);
				select.setUreaOdometerOld((Double) result[8]);
				select.setUreaLiters((Double) result[9]);
				select.setUreaRate((Double) result[10]);
				select.setDiscount((Double) result[11]);
				select.setGst((Double) result[12]);
				select.setUreaAmount((Double) result[13]);
				select.setUreaManufacturerId((Long) result[14]);
				select.setReference((String) result[15]);
				select.setDriver_id((Integer) result[16]);
				select.setSecDriverID((Integer) result[17]);
				select.setCleanerID((Integer) result[18]);
				select.setRouteID((Integer) result[19]);
				select.setComments((String) result[20]);
				select.setCreatedById((Long) result[21]);
				select.setLastModifiedById((Long) result[22]);
				select.setCreated((Date) result[23]);
				select.setLastupdated((Date) result[24]);
				select.setLocationName((String) result[25]);
				
				if(result[26] != null) {
					select.setFirsDriverName((String) result[26]);
				} else {
					select.setFirsDriverName("");
				}
				if(result[27] != null)
				select.setFirsDriverLastName((String) result[27]);
				
				if(result[28] != null) {
					select.setSecDriverName((String) result[28]);
				} else {
					select.setSecDriverName("");
				}
				if(result[29] != null)
				select.setSecDriverLastName((String) result[29]);
				
				if(result[30] != null) {
					select.setCleanerName((String) result[30]);
				} else {
					select.setCleanerName("");
				}
				if(result[31] != null)
				select.setCleanerLastName((String) result[31]);	
				
				if(result[32] != null) {
					select.setRouteName((String) result[32]);
				} else {
					select.setRouteName("");
				}
				
				select.setCreatedBy((String) result[33]);
				select.setLastModifiedBy((String) result[34]);
				select.setVehicle_registration((String) result[35]);
				select.setVehicleGrpName((String) result[36]);
				
				if(result[37] != null) {
					select.setTripSheetNumber((long) result[37]);
				}else {
					select.setTripSheetNumber((long) 0);
				}
				
				if(result[38] != null) {
					select.setVehicle_ExpectedOdameter((int) result[38]);
				} else {
					select.setVehicle_ExpectedOdameter(0);
				}
				select.setQuantity((double) result[39]);
				select.setUsedQuantity((double) result[40]);
				select.setStockQuantity((double) result[41]);
				select.setUreaInvoiceNumber((long) result[42]);
				select.setManufacturerName((String) result[43]);
				
				if(select.getCreated() != null) {
					select.setCreatedOnStr(dateFormat.format(select.getCreated()));
				}else {
					select.setCreatedOnStr("--");
				}
				
				if(select.getLastupdated() != null) {
					select.setLastModifiedStr(dateFormat.format(select.getLastupdated()));
				}else {
					select.setLastModifiedStr("--");
				}
				
				if(select.getUreaDate() != null) {
					select.setUreaDateStr(format.format(select.getUreaDate()));
				}else {
					select.setUreaDateStr("--");	
				}

				
				Dtos.add(select);
			}

		}
		return Dtos;
		
	}
	
	@Override
	public List<UreaEntriesDto> getUreaEntriesDetailsByDateRange(Integer vid, String fromDate, String toDate)
			throws Exception {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT CI.ureaEntriesId, CI.ureaEntriesNumber, CI.vid,  CI.ureaDate, "
				+ " CI.ureaOdometer, CI.ureaOdometerOld, CI.ureaLiters, CI.ureaRate, CI.discount, CI.gst, CI.ureaAmount "
				+ " FROM UreaEntries CI "
				+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CI.locationId "
				+ " where CI.vid = "+vid+" AND CI.ureaDate between '"+fromDate+"' AND '"+toDate+"' "
				+ " AND CI.markForDelete = 0 ORDER BY CI.ureaEntriesId DESC ",
				Object[].class);
		List<Object[]> results = query.getResultList();
		
		List<UreaEntriesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<UreaEntriesDto>();
			UreaEntriesDto select = null;
			for (Object[] result : results) {
				select = new UreaEntriesDto();
				
				select.setUreaEntriesId((Long) result[0]);
				select.setUreaEntriesNumber((Long) result[1]);
				select.setVid((Integer) result[2]);
				select.setUreaDate((Date) result[3]);
				select.setUreaOdometer((Double) result[4]);
				select.setUreaOdometerOld((Double) result[5]);
				select.setUreaLiters((Double) result[6]);
				select.setUreaRate((Double) result[7]);
				select.setDiscount((Double) result[8]);
				select.setGst((Double) result[9]);
				select.setUreaAmount((Double) result[10]);
			
				if(select.getUreaDate() != null) {
					select.setUreaDateStr(format.format(select.getUreaDate()));
				}else {
					select.setUreaDateStr("--");	
				}

				
				Dtos.add(select);
			}

		}
		return Dtos;
		
	}
	
	public Map<Integer, List<UreaEntriesDto>> getAllUreaEntriesDetails(String startDate, String endDate) throws Exception {
		TypedQuery<Object[]> 									typedQuery  	= null;
		Map<Integer, List<UreaEntriesDto>>				companyHM		= null;
		List<UreaEntriesDto>							companyLIst 	= null;
		try {
			typedQuery = entityManager.createQuery(
				" SELECT U.ureaEntriesId, U.ureaEntriesNumber, V.vehicle_registration, U.ureaDate, U.ureaLiters, U.ureaRate ,"
					+ " U.ureaAmount, U.ureaOdometerOld, U.ureaOdometer,UM.manufacturerName, U.companyId  "
					+ " From UreaEntries as U "
					+ " INNER JOIN Vehicle as V ON V.vid = U.vid "
					+ " INNER JOIN UreaManufacturer as UM ON UM.ureaManufacturerId = U.ureaManufacturerId "
					+ " WHERE U.markForDelete = 0 AND U.ureaDate "
					+ " BETWEEN '"+startDate+"' AND '"+endDate+"'  ", Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();
			if (results != null && !results.isEmpty()) {
				
				companyHM	= new HashMap<Integer, List<UreaEntriesDto>>();
				
				UreaEntriesDto	ureaEntriesDto = null;
				for (Object[] result : results) {
					ureaEntriesDto	= new UreaEntriesDto();
					ureaEntriesDto.setUreaEntriesId((Long) result[0]);
					ureaEntriesDto.setUreaEntriesNumber((Long) result[1]);
					ureaEntriesDto.setVehicle_registration((String) result[2]);
					ureaEntriesDto.setUreaDateStr(format.format((Timestamp)result[3]));
					ureaEntriesDto.setUreaLiters((double) result[4]);
					ureaEntriesDto.setUreaRate((double) result[5]);
					ureaEntriesDto.setUreaAmount((double) result[6]);
					ureaEntriesDto.setUreaOdometerOld((double) result[7]);
					ureaEntriesDto.setUreaOdometer((double) result[8]);
					ureaEntriesDto.setManufacturerName((String) result[9]);
					ureaEntriesDto.setCompanyId((Integer)result[10]);
					
					companyLIst	= companyHM.get(ureaEntriesDto.getCompanyId());
					if(companyLIst == null) {
						companyLIst	= new ArrayList<>();
					}
					companyLIst.add(ureaEntriesDto);
					
					companyHM.put(ureaEntriesDto.getCompanyId(), companyLIst);
				
				}
				
			}
			return companyHM;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseUreaExpenseDtoByVid(Integer vid, String fromDate, String toDate,
			Integer companyId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.ureaAmount), MVE.companyId "
							+ " FROM UreaEntries MVE "
							+ " where MVE.vid = "+vid+" AND MVE.ureaDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
									+ " AND MVE.markForDelete = 0 AND MVE.ureaAmount > 0", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseType((short)10);
					mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
					if(result[0] != null)
						list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<MonthWiseVehicleExpenseDto> getMonthWiseUreaExpenseDtoByVid(Integer vid, String fromDate, String toDate,
			Integer companyId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.ureaAmount), MVE.companyId "
							+ " FROM UreaEntries MVE "
							+ " where MVE.vid = "+vid+" AND MVE.ureaDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
									+ " AND MVE.markForDelete = 0 AND MVE.ureaAmount > 0", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<MonthWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					MonthWiseVehicleExpenseDto	mExpenseDto = new MonthWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseType((short)10);
					mExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
					if(result[0] != null)
						list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseUreaExpenseDtoByRouteId(Integer vid, String fromDate,
			String toDate, Integer companyId, Integer routeId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.ureaAmount), MVE.companyId "
							+ " FROM UreaEntries MVE "
							+ " INNER JOIN TripSheet TS ON TS.tripSheetID = MVE.tripSheetId AND TS.routeID = "+routeId+""
							+ " where MVE.vid = "+vid+" AND MVE.ureaDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
									+ " AND MVE.markForDelete = 0 AND MVE.ureaAmount > 0", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseType((short)10);
					mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
					if(result[0] != null)
						list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseUreaExpenseDtoByVTId(Integer vid, String fromDate, String toDate,
			Integer companyId, Long routeId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.ureaAmount), MVE.companyId "
							+ " FROM UreaEntries MVE "
							+ " INNER JOIN Vehicle TS ON TS.vid = MVE.vid AND TS.vehicleTypeId = "+routeId+""
							+ " where MVE.vid = "+vid+" AND MVE.ureaDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
									+ " AND MVE.markForDelete = 0 AND MVE.ureaAmount > 0", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseType((short)10);
					mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
					if(result[0] != null)
						list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseUreaExpenseDtoByVTRouteId(Integer vid, String fromDate,
			String toDate, Integer companyId, Long vehicleTypeId, Integer routeId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.ureaAmount), MVE.companyId "
							+ " FROM UreaEntries MVE "
							+ " INNER JOIN TripSheet TS ON TS.tripSheetID = MVE.tripSheetID AND TS.routeID = "+routeId+""
							+ " INNER JOIN Vehicle V ON V.vid = MVE.vid AND V.vehicleTypeId = "+routeId+""
							+ " where MVE.vid = "+vid+" AND MVE.ureaDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
									+ " AND MVE.markForDelete = 0 AND MVE.ureaAmount > 0", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseType((short)10);
					mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
					if(result[0] != null)
						list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getPreNextUreaEntires(ValueObject object) throws Exception {
		int 			vid								= 0;
		int 			companyId						= 0;
		Long			tripSheetId						= (long) 0;
		String			preQuery						= "";
		String			nextQuery						= "";
		String 			start_time 						= "00:00";
		try {
			
			vid				= object.getInt("vid");
			companyId		= object.getInt("companyId");
			
			java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(object.getString("fuelDate"), start_time);
			String ureaDate = formatSQL.format(date);
			
			
			TripSheetDto tripActive = fuelService.getTripsheetDataAtTime(ureaDate, vid, companyId);
			if(tripActive != null) {
				/*if(object.getBoolean("bindMinMaxOdometerOnTripSheet",false)) {
					tripSheetId = tripActive.getTripSheetID();
				}
				object.put("tripActive", tripActive);*/
				tripSheetId = tripActive.getTripSheetID();
			}
			
			preQuery += " U.vid = "+vid+" AND U.companyId = "+companyId+" AND U.markForDelete = 0 AND U.ureaDate <= '"+ureaDate+"' ";
			/*if(tripSheetId > 0) {
				preQuery += " AND U.tripSheetId  = "+tripSheetId+" ";
			}*/
			if(object.getLong("ureaEntriesId",0) > 0) {
				preQuery += " AND U.ureaEntriesId <> "+object.getLong("ureaEntriesId",0)+" ";       
			}
			preQuery  += " ORDER BY U.ureaDate DESC ";
			
			
			nextQuery += " U.vid = "+vid+" AND U.companyId = "+companyId+" AND U.markForDelete = 0 AND U.ureaDate > '"+ureaDate+"' ";
			/*if(tripSheetId > 0) {
				nextQuery += " AND U.tripSheetId  = "+tripSheetId+" ";
			}*/
			
			if(object.getLong("ureaEntriesId",0) > 0) {
				nextQuery += " AND U.ureaEntriesId <> "+object.getLong("ureaEntriesId",0)+" ";
			}
			
			nextQuery += " ORDER BY U.ureaDate  ASC";
			
			VehicleDto previousFuelDeatils 	= getPreviousNextUreaEntryDetails(preQuery);
			VehicleDto nextFuelDeatils 		= getPreviousNextUreaEntryDetails(nextQuery);
			
			object.put("previousFuelDeatils", previousFuelDeatils);
			object.put("nextFuelDeatils", nextFuelDeatils);
			
			if(nextFuelDeatils != null && previousFuelDeatils != null) {
				object.put("minOdometer", previousFuelDeatils.getLastUreaOdometer() + 1 );
				object.put("maxOdometer", nextFuelDeatils.getUreaOdometer() - 1);
			}else if(nextFuelDeatils == null && previousFuelDeatils != null) {
				object.put("minOdometer", previousFuelDeatils.getLastUreaOdometer() + 1 );
				object.put("maxOdometer", previousFuelDeatils.getLastUreaOdometer() + object.getDouble("vehicle_ExpectedOdameter",2500));
				if(tripSheetId > 0 && tripActive.getTripClosingKM() != null && tripActive.getTripClosingKM() > 0) {
					object.put("maxOdometer", tripActive.getTripClosingKM());
				}
			}else if(nextFuelDeatils != null && previousFuelDeatils == null) {
				object.put("minOdometer", nextFuelDeatils.getLastUreaOdometer() + 1 );
				object.put("maxOdometer", nextFuelDeatils.getUreaOdometer() - object.getDouble("vehicle_ExpectedOdameter",2500));
				if(tripSheetId > 0) {
					object.put("minOdometer", tripActive.getTripOpeningKM());
				}
			}
			
			if(previousFuelDeatils != null) {
				object.put("minOdometer", previousFuelDeatils.getLastUreaOdometer()  );
			}else {
				if(tripSheetId > 0) {
					object.put("minOdometer", tripActive.getTripOpeningKM());
				}
			}
			
			if(nextFuelDeatils != null) {
				object.put("maxOdometer", nextFuelDeatils.getUreaOdometer() - 1);
			}else {
				if(tripSheetId > 0 && tripActive.getTripClosingKM() != null && tripActive.getTripClosingKM() > 0) {
					object.put("maxOdometer", tripActive.getTripClosingKM());
				}else {
					if(tripActive  !=  null)
					object.put("maxOdometer", object.getDouble("vehicle_ExpectedOdameter",2500) + tripActive.getTripOpeningKM());
				}
			}
			
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	

	@Override
	public VehicleDto getPreviousNextUreaEntryDetails(String queryStr) throws Exception {
		Object[] vehicle = null;
		try {
			Query query = null;
				query = entityManager.createQuery(
						"SELECT V.vid, V.vehicle_registration, V.vehicle_Odometer,  "
								+ " V.vehicleGroupId, V.vehicle_ExpectedOdameter, V.vehicleGPSId, U.ureaOdometer, "
								+ "	V.vStatusId, V.gpsVendorId, U.ureaDate, "
								+ " U.ureaEntriesId, U.ureaEntriesNumber, U.ureaLiters, U.ureaOdometerOld FROM Vehicle AS V " 
								+ " INNER JOIN UreaEntries AS U ON U.vid = V.vid AND U.markForDelete = 0 "
								+ " WHERE "+queryStr+" " );
			
			try {
				query.setMaxResults(1);
				vehicle = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			VehicleDto select = new VehicleDto();
			if (vehicle != null) {
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_Odometer((Integer) vehicle[2]);
				select.setVehicleGroupId((long) vehicle[3]);
				select.setVehicle_ExpectedOdameter((Integer) vehicle[4]);
				select.setVehicleGPSId((String) vehicle[5]);
				select.setUreaOdometer((Double) vehicle[6]);
				select.setvStatusId((short) vehicle[7]);
				select.setVehicle_Status(VehicleStatusConstant.getVehicleStatus(select.getvStatusId()));
				select.setGpsVendorId((Integer) vehicle[8]);
				if(vehicle[9] != null)
					select.setUreaDate((Date) vehicle[9]);
				if(vehicle[10] != null)
					select.setUreaEntriesId((long) vehicle[10]); 
				select.setUreaEntriesNumber(((long) vehicle[11]));
				select.setUreaLiters((double) vehicle[12]);
				select.setLastUreaOdometer((Double) vehicle[13]);
			}
			return select;
			
		} catch (Exception e) {
			System.err.println("Exception Previous Fuel Details : "+e);
			return null;
		}
	
	}
	
	
	@Override
	@Transactional
	public void updateNextOldOdometer(Long ureaEntriesId, Double oldOdometer, Integer companyId) throws Exception {
		try {
			ureaEntriesRepository.updateNextOldOdometer(ureaEntriesId,oldOdometer,companyId);
		} catch (Exception e) {
		throw e;
		}
	}
	
	@Override
	public ValueObject searchUreaEntries(ValueObject object) throws Exception {
		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]> 					resultList 				= null; 
		List<UreaEntriesDto> 			ureaEntriesList 		= null;
		UreaEntriesDto 					ureaEntriesDto			= null;
		CustomUserDetails				userDetails				= null;
		Integer				  			pageNumber				= null;
		String 							query					= "";
		String 							ureaNumber				= "";
		String							vehicle					= "";
		String							dateRange				= "";
		String []						startDateRangeArr		= null;	
		String []						endDateRangeArr			= null;		
		String 							startDatePagination		= null;	
		String 							endDatePagination		= null;		

		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			startDateRangeArr			= object.getString("startDate").split("-");
			endDateRangeArr				= object.getString("endDate").split("-");
			
			startDatePagination			= startDateRangeArr[2]+"-"+startDateRangeArr[1]+"-"+startDateRangeArr[0];
			endDatePagination			= endDateRangeArr[2]+"-"+endDateRangeArr[1]+"-"+endDateRangeArr[0]; 
			if(object.getLong("ureaNumber",0) > 0) {
				ureaNumber = "AND UE.ureaEntriesNumber = "+object.getLong("ureaNumber")+ " ";
			}
			if(object.getInt("vid",0) > 0) {
				vehicle = "AND UE.vid = "+object.getInt("vid")+ " ";
			}
			dateRange = "AND UE.ureaDate BETWEEN '"+object.getString("startDate")+"' AND '"+object.getString("endDate")+"' ";
			
			query = ""+ureaNumber+" "+vehicle+" "+dateRange+""; 
			
			
			pageNumber					= object.getInt("pageNumber",0);
			Page<UreaEntries> page 		= getDeployment_Page_SerachUreaEntries(pageNumber,DateTimeUtility.getTimeStamp(startDatePagination.replaceAll("\\s", "")),DateTimeUtility.getTimeStamp(endDatePagination.replaceAll("\\s", "")),userDetails.getCompany_id());
			
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			object.put("deploymentLog", page);
			object.put("beginIndex", begin);
			object.put("endIndex", end);
			object.put("currentIndex", current);

			object.put("invoiceCount", page.getTotalElements());
			object.put("SelectPage", pageNumber);

			typedQuery = entityManager.createQuery(
						"SELECT UE.ureaEntriesId, UE.ureaEntriesNumber, V.vehicle_registration, UE.ureaOdometer , UE.ureaOdometerOld ,"
								+ " UE.ureaDate, UE.ureaLiters, UE.ureaAmount, UE.tripSheetId, T.tripSheetNumber, UE.vid FROM UreaEntries AS UE " 
								+ " INNER JOIN Vehicle AS V ON V.vid = UE.vid "
								+ " LEFT JOIN TripSheet T ON T.tripSheetID = UE.tripSheetId "
								+ " WHERE UE.companyId ="+userDetails.getCompany_id()+" "+query+" AND UE.markForDelete = 0 ORDER BY UE.ureaEntriesId DESC", Object[].class);
			
			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				ureaEntriesList = new ArrayList<>();

				for (Object[] result : resultList) {
					ureaEntriesDto = new UreaEntriesDto();
					
					ureaEntriesDto.setUreaEntriesId((Long) result[0]);
					ureaEntriesDto.setUreaEntriesNumber((Long) result[1]);
					ureaEntriesDto.setVehicle_registration((String) result[2]);
					ureaEntriesDto.setUreaOdometer((Double) result[3]);
					ureaEntriesDto.setUreaOdometerOld((Double) result[4]);
					ureaEntriesDto.setUreaDate((Date) result[5]);
					ureaEntriesDto.setUreaLiters((Double) result[6]);
					ureaEntriesDto.setUreaAmount((Double) result[7]);
					ureaEntriesDto.setTripSheetId((Long) result[8]);
					ureaEntriesDto.setTripSheetNumber((Long) result[9]);
					ureaEntriesDto.setVid((Integer) result[10]);
					ureaEntriesDto.setUreaDateStr(format.format(ureaEntriesDto.getUreaDate()));
					
					ureaEntriesList.add(ureaEntriesDto);
					
				}
			}

			object.put("ureaEntries", ureaEntriesList);
			return object;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			ureaEntriesList 		= null;
			ureaEntriesDto			= null;
		}
	}
	
	@Override
	public Page<UreaEntries> getDeployment_Page_SerachUreaEntries(Integer pageNumber,Timestamp startDate, Timestamp endDate, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "ureaEntriesId");
		return ureaEntriesRepository.getDeployment_Page_SerachUreaEntries(companyId,startDate,endDate, pageable);
	}
	
	@Override
	public HashMap<Integer, Long> getUreaEntriesCountHMOnCreated(String startDate, String endDate) throws Exception {
		try {
			TypedQuery<Object[]> 	typedQuery = null;
			HashMap<Integer, Long>	map		   = null;

			typedQuery = entityManager.createQuery(
					"SELECT COUNT(F.ureaEntriesId), F.companyId "
							+ " From UreaEntries as F "
							+ " WHERE F.created between '"+startDate+"' AND '"+endDate+"' AND "
							+ " F.markForDelete = 0 GROUP BY F.companyId ",
							Object[].class);

			List<Object[]> results = typedQuery.getResultList();

			if (results != null && !results.isEmpty()) {

				map	= new HashMap<>();

				for (Object[] result : results) {

					map.put((Integer)result[1], (Long)result[0]);

				}
			}

			return map;
		} catch (Exception e) {
			throw e;
		}
	}
	

	@Override
	public ValueObject getMissingUreaEntryAlertByCompanyId(Integer companyId) throws Exception {
		List<VehicleDto>					vehicleList					= null;
		List<VehicleDto> 					vehicleFinalList			= null;
		ValueObject							valueObject 				= null;
		HashMap<Integer, VehicleDto>		finalUreaHM					= null;
		VehicleDto							vehicleDto					= null;
		try {
			valueObject				= new ValueObject();
			vehicleFinalList		= new ArrayList<>();
			
			vehicleList				= vehicleService.getActiveVehicleList(companyId);
			
			if(vehicleList != null && !vehicleList.isEmpty()) {
				finalUreaHM 	= getLastUreaEntryDetailList(companyId);
				for(VehicleDto dto : vehicleList){
					vehicleDto	= new VehicleDto();
					if((finalUreaHM != null && !finalUreaHM.isEmpty()) && (finalUreaHM.containsKey(dto.getVid())) && (dto.getVehicle_Odometer() > (dto.getVehicle_ExpectedOdameter() + finalUreaHM.get(dto.getVid()).getUreaOdometer()))) {
						vehicleDto.setVid(dto.getVid());
						vehicleDto.setUreaEntriesId(finalUreaHM.get(dto.getVid()).getUreaEntriesId());
						vehicleDto.setVehicle_registration(dto.getVehicle_registration());
						vehicleDto.setUreaEntriesNumber(finalUreaHM.get(dto.getVid()).getUreaEntriesNumber());
						vehicleDto.setUreaDateStr(finalUreaHM.get(dto.getVid()).getUreaDateStr());
						vehicleDto.setUreaOdometer(finalUreaHM.get(dto.getVid()).getUreaOdometer());
						vehicleDto.setVehicle_Odometer(dto.getVehicle_Odometer());
						vehicleDto.setVehicle_ExpectedOdameter(dto.getVehicle_ExpectedOdameter());
						vehicleFinalList.add(vehicleDto);
					}
				}
			}
			
			valueObject.put("vehicleFinalList", vehicleFinalList);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			vehicleFinalList = null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<Integer, VehicleDto> getLastUreaEntryDetailList(Integer companyId) throws Exception {
		Query 			query 		= null;
		HashMap<Integer, VehicleDto> 	fuelHM 		= null;
		try {
			fuelHM 	= new HashMap<>();
			query = entityManager.createNativeQuery(
					"SELECT MAX(UE.ureaEntriesId), MAX(UE.ureaEntriesNumber), UE.vid, MAX(UE.ureaDate), MAX(UE.ureaOdometer)"
							+ " From UreaEntries AS UE "
							+ " INNER JOIN Vehicle AS V ON V.vid = UE.vid AND V.vStatusId IN ("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE +","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE +","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP +")"
							+ " AND UE.markForDelete = 0 AND UE.companyId = "+companyId+" GROUP BY UE.vid ORDER BY MAX(UE.ureaDate) DESC ");
			
			List<Object[]> results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				
				VehicleDto select = null;
				for (Object[] vehicle : results) {
	
					select = new VehicleDto();
					select.setUreaEntriesId(Long.parseLong(vehicle[0]+""));
					select.setUreaEntriesNumber(Long.parseLong(vehicle[1]+""));
					select.setVid((int) vehicle[2]);
					select.setUreaDateStr(dateFormat.format((Timestamp) vehicle[3]));
					select.setUreaOdometer((Double) vehicle[4]);
					fuelHM.put(select.getVid(), select);
				}
				
			}
			
			return fuelHM;
		
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
@Override
	public List<UreaEntriesDto> getUreaConsumptionList(ValueObject object) throws Exception {
		TypedQuery<Object[]> 				typedQuery  	= null;
		List<UreaEntriesDto>				ureaList 	= null;
		try {
			typedQuery = entityManager.createQuery(
				" SELECT U.ureaEntriesId, U.ureaEntriesNumber,UM.manufacturerName, U.ureaLiters, "
					+ " U.ureaRate ,U.ureaAmount, U.vid, V.vehicle_registration, U.ureaDate "
					+ " From UreaEntries as U "
					+ " INNER JOIN Vehicle as V ON V.vid = U.vid "
					+ " INNER JOIN UreaManufacturer as UM ON UM.ureaManufacturerId = U.ureaManufacturerId "
					+ " WHERE U.companyId="+object.getInt("companyId")+" "+object.getString("queryStr") +" AND U.markForDelete = 0 ORDER BY U.ureaDate DESC", Object[].class);
			
			/*typedQuery.setFirstResult((object.getInt("pageNumber") - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);*/
			ureaList	= new ArrayList<>();
			List<Object[]> results = typedQuery.getResultList();
			if (results != null && !results.isEmpty()) {
				UreaEntriesDto	ureaEntriesDto = null;
				for (Object[] result : results) {
					ureaEntriesDto	= new UreaEntriesDto();
					ureaEntriesDto.setUreaEntriesId((Long) result[0]);
					ureaEntriesDto.setUreaEntriesNumber((Long) result[1]);
					ureaEntriesDto.setManufacturerName((String) result[2]);
					ureaEntriesDto.setUreaLiters((double) result[3]);
					ureaEntriesDto.setUreaRate((double) result[4]);
					ureaEntriesDto.setUreaAmount((double) result[5]);
					ureaEntriesDto.setVid((Integer) result[6]);
					ureaEntriesDto.setVehicle_registration((String) result[7]);
					ureaEntriesDto.setUreaDateStr(format.format((Timestamp)result[8]));
					
					ureaList.add(ureaEntriesDto);
				}
				
			}
			return ureaList;
		} catch (Exception e) {
			throw e;
		}
	}
	

}
