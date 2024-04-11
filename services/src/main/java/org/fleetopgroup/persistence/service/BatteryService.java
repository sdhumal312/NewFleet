package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.BatteryConstant;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.bl.BatteryBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.PendingVendorPaymentBL;
import org.fleetopgroup.persistence.dao.BatteryAmountRepository;
import org.fleetopgroup.persistence.dao.BatteryInvoiceRepository;
import org.fleetopgroup.persistence.dao.BatteryRepository;
import org.fleetopgroup.persistence.document.BatteryInvoiceDocument;
import org.fleetopgroup.persistence.dto.BatteryAmountDto;
import org.fleetopgroup.persistence.dto.BatteryDto;
import org.fleetopgroup.persistence.dto.BatteryHistoryDto;
import org.fleetopgroup.persistence.dto.BatteryInvoiceDto;
import org.fleetopgroup.persistence.dto.BatteryTypeDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.model.Battery;
import org.fleetopgroup.persistence.model.BatteryAmount;
import org.fleetopgroup.persistence.model.BatteryInvoice;
import org.fleetopgroup.persistence.model.BatteryInvoiceSequenceCounter;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.report.dao.IBatteryReportDao;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryAmountService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryInvoiceSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.web.util.ByteConvertor;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BatteryService implements IBatteryService {
	
	@PersistenceContext EntityManager entityManager;
	@Autowired private BatteryAmountRepository				batteryAmountRepository;
	@Autowired private IBatteryInvoiceSequenceService		batteryInvoiceSequenceService;
	@Autowired private BatteryInvoiceRepository				batteryInvoiceRepository;
	@Autowired private IBatteryInvoiceService				batteryInvoiceService;
	@Autowired private IBatteryAmountService				batteryAmountService;
	@Autowired private BatteryRepository					batteryRepository;
	@Autowired private IPartLocationsService				PartLocationsService;
	@Autowired private ICompanyConfigurationService			companyConfigurationService;
	@Autowired private IBatteryHistoryService				batteryHistoryService;
	@Autowired private IBatteryReportDao					BatteryReportDaoImpl;
	@Autowired private ISequenceCounterService				sequenceCounterService;
	@Autowired private MongoTemplate						mongoTemplate;
	@Autowired private IPendingVendorPaymentService			pendingVendorPaymentService;
	@Autowired private IBankPaymentService 					bankPaymentService; 
	@Autowired private ICashPaymentService					cashPaymentService;
	
	PartLocationsBL PLBL = new PartLocationsBL();
	
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private static final int PAGE_SIZE		 = 10;
	private static final int PAGE_SIZE_FIFTY = 50;

	BatteryBL	batteryBL = new BatteryBL();
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject saveBatteryDetails(ValueObject valueObject, MultipartFile file) throws Exception {
		ValueObject						outObject					= null;
		ArrayList<ValueObject> 			dataArrayObjColl 			= null;
		BatteryInvoiceSequenceCounter	sequenceCounter				= null;	
		List<BatteryAmount>				batteryAmountList			= null;
		HashMap<String, Object> 		configuration				= null;
		BatteryInvoice					validateBatteryInvoice		= null;
		CustomUserDetails 				userDetails 				= null;
		
		try {
			outObject				= new ValueObject();
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.BATTERY_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.getOrDefault("validateInvoiceNumber", false)) {
				validateBatteryInvoice	= batteryInvoiceService.getBatteryInvoiceByInvoiceNumber(valueObject.getString("invoiceNumber", null), userDetails.getCompany_id());
				
				if(validateBatteryInvoice != null) {
					valueObject.put("duplicateInvoiceNumber", true);
					return valueObject;
				}
				
			}
			BatteryInvoice	batteryInvoice	= batteryBL.getBatteryInvoice(valueObject,file);
			
			sequenceCounter	= batteryInvoiceSequenceService.findNextInvoiceNumber(batteryInvoice.getCompanyId());
			
			if(sequenceCounter == null) {
				outObject.put("saveMessage", "Sequence not found please contact to system Administrator !");
				return outObject;
			}
			
			batteryInvoice.setBatteryInvoiceNumber(sequenceCounter.getNextVal());
			
			BatteryInvoice battery = batteryInvoiceRepository.save(batteryInvoice);
			
			valueObject.put("invoiceId", batteryInvoice.getBatteryInvoiceId());
			
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("batteryDetails");
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				batteryAmountList	= new ArrayList<>();
				for (ValueObject object : dataArrayObjColl) {
					batteryAmountList.add(batteryBL.getBatteryAmountDetails(object, valueObject));
				}
				
				batteryAmountRepository.saveAll(batteryAmountList);
				
			}
			if(batteryInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && batteryInvoice.getInvoiceAmount() > 0) {
				PendingVendorPayment	payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForBI(batteryInvoice);
				pendingVendorPaymentService.savePendingVendorPayment(payment);
			}
			if(valueObject.getBoolean("allowBankPaymentDetails",false) && (PaymentTypeConstant.getPaymentTypeIdList().contains(batteryInvoice.getPaymentTypeId()) || batteryInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH )){
				ValueObject bankPaymentValueObject = JsonConvertor
						.toValueObjectFormSimpleJsonString(valueObject.getString("bankPaymentDetails"));
				bankPaymentValueObject.put("bankPaymentTypeId",batteryInvoice.getPaymentTypeId() );
				bankPaymentValueObject.put("userId", userDetails.getId());
				bankPaymentValueObject.put("userId", userDetails.getId());
				bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
				bankPaymentValueObject.put("moduleId", batteryInvoice.getBatteryInvoiceId());
				bankPaymentValueObject.put("moduleNo", batteryInvoice.getBatteryInvoiceNumber());
				bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.BATTRRY_INVENTORY);
				bankPaymentValueObject.put("amount", batteryInvoice.getInvoiceAmount());
				bankPaymentValueObject.put("remark", "Battery Inventory Created BI-"+batteryInvoice.getBatteryInvoiceNumber());
				//bankPaymentValueObject.put("paidDate", batteryInvoice.getInvoiceDate());
				
				if(PaymentTypeConstant.PAYMENT_TYPE_CASH == batteryInvoice.getPaymentTypeId())
					cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
				else
					bankPaymentService.addBankPaymentDetailsFromValueObject(bankPaymentValueObject);
			}
			
			if(file != null && !file.isEmpty() || valueObject.getString("base64String",null) != null) {
				saveBatteryDocument(battery, file,valueObject);
			}
			
			
			outObject.put("saveMessage", "Data Saved Succsessfully!");
			outObject.put("batteryInvoice", batteryInvoice.getBatteryInvoiceId());
			outObject.put("batteryInvoiceNumber", batteryInvoice.getBatteryInvoiceNumber());
			
			return outObject;
		} catch (Exception e) {
			throw e;
		}finally {
			outObject					= null;
			dataArrayObjColl 			= null;
		}
	}
	
	@Override
	public ValueObject get_list_BatteryInvoiceDetails(ValueObject	valueObject) throws Exception {
		BatteryInvoiceDto 				batteryInvoice				= null;
		BatteryInvoiceDto				batteryInvoiceDto			= null;
		CustomUserDetails				userDetails					= null;
		List<BatteryAmountDto>			batteryAmountList			= null;
		List<BatteryDto>				batteryList					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			batteryInvoiceDto	= new BatteryInvoiceDto();
			
			batteryInvoiceDto.setCompanyId(userDetails.getCompany_id());
			batteryInvoiceDto.setBatteryInvoiceId(valueObject.getLong("invoiceId", 0));
			
			batteryInvoice	   = batteryBL.getfinalBatteryInvoice(batteryInvoiceService.getBatteryInvoice(batteryInvoiceDto));
			batteryAmountList  = batteryAmountService.getBatteryAmountList(batteryInvoiceDto);
			
			batteryList		   = getBatteryList(batteryInvoiceDto);	
			
			valueObject.clear();
			valueObject.put("batteryInvoice", batteryInvoice);
			valueObject.put("batteryAmountList", batteryAmountList);
			valueObject.put("batteryList", batteryList);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			batteryInvoiceDto			= null;
			batteryInvoice				= null;
			userDetails					= null;
		}
	}
	
	@Override
	public List<BatteryDto> getBatteryList(BatteryInvoiceDto battery) throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.batteryId, BA.batterySerialNumber, BA.batteryManufacturerId, BA.batteryTypeId, BA.batteryAmount,"
							+ " BA.wareHouseLocationId, BA.batteryAmountId, BA.batteryInvoiceId, BA.vid, BA.batteryUsesOdometer, BA.usesNoOfTime,"
							+ " BA.openOdometer, BA.closedOdometer, BA.batteryPurchaseDate, BA.batteryAsignedDate, BA.batteryStatusId, BA.batteryUsesStatusId,"
							+ " BA.batteryScrapedDate, BM.manufacturerName, BT.batteryType, PL.partlocation_name "
							+ " FROM Battery AS BA "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BA.wareHouseLocationId "
							+ " INNER JOIN BatteryManufacturer BM ON BM.batteryManufacturerId = BA.batteryManufacturerId"
							+ " INNER JOIN BatteryType BT ON BT.batteryTypeId = BA.batteryTypeId "
							+ " where BA.batteryInvoiceId = "+battery.getBatteryInvoiceId()+" AND BA.companyId = "+battery.getCompanyId()+" AND  BA.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<BatteryDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					BatteryDto	batteryInvoice = new BatteryDto();
					
					batteryInvoice.setBatteryId((Long) result[0]);
					batteryInvoice.setBatterySerialNumber((String) result[1]);
					batteryInvoice.setBatteryManufacturerId((Long) result[2]);
					batteryInvoice.setBatteryTypeId((Long) result[3]);
					batteryInvoice.setBatteryAmount((Double) result[4]);
					batteryInvoice.setWareHouseLocationId((Integer) result[5]);
					batteryInvoice.setBatteryAmountId((Long) result[6]);
					batteryInvoice.setBatteryInvoiceId((Long) result[7]);
					batteryInvoice.setVid((Integer) result[8]);
					batteryInvoice.setBatteryUsesOdometer((Integer) result[9]);
					batteryInvoice.setUsesNoOfTime((Long) result[10]);
					batteryInvoice.setOpenOdometer((Integer) result[11]);
					batteryInvoice.setClosedOdometer((Integer) result[12]);
					batteryInvoice.setBatteryPurchaseDate((Timestamp) result[13]);
					batteryInvoice.setBatteryAsignedDate((Timestamp) result[14]);
					batteryInvoice.setBatteryStatusId((short) result[15]);
					batteryInvoice.setBatteryStatus(BatteryConstant.getBatteryAsignedStatusName((short) result[15]));
					batteryInvoice.setBatteryUsesStatusId((short) result[16]);
					batteryInvoice.setBatteryScrapedDate((Timestamp) result[17]);
					batteryInvoice.setManufacturerName((String) result[18]);
					batteryInvoice.setBatteryType((String) result[19]);
					batteryInvoice.setLocationName((String) result[20]);
					
					list.add(batteryInvoice);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject saveBatterySerialNumber(ValueObject valueObject) throws Exception {
		Optional<BatteryAmount>			batteryAmount				= null;
		ArrayList<ValueObject> 			dataArrayObjColl 			= null;
		List<Battery>					batteryList					= null;
		CustomUserDetails				userDetails					= null;
		List<Battery> 					validate					= null;
		int								duplicate					= 0;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			batteryAmount	=	batteryAmountRepository.findById(valueObject.getLong("batteryAmountId", 0));
			
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("batteryDetails");
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				batteryList	= new ArrayList<>();
				for (ValueObject object : dataArrayObjColl) {
					if(object.getString("batterySerialNumber") != null && !StringUtils.isEmpty(object.getString("batterySerialNumber").trim())) {
						validate	= batteryRepository.validateBatterySerialNumber(object.getString("batterySerialNumber"), userDetails.getCompany_id());
						if(validate == null || validate.isEmpty()) {
							batteryList.add(batteryBL.getBatteryDto(valueObject, object, batteryAmount.get()));
						}else {
							duplicate++;
						}
					}
				}
				if(batteryList != null && !batteryList.isEmpty()) {
					batteryRepository.saveAll(batteryList);
					batteryAmountService.updateBatteryAsignNumber(batteryList.size(), valueObject.getLong("batteryAmountId", 0), userDetails.getCompany_id());
					
				}
			}
			valueObject.clear();
			valueObject.put("batteryInvoice", batteryAmount.get().getBatteryInvoiceId());
			valueObject.put("duplicate", duplicate);
			valueObject.put("inserted", batteryList.size());
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			batteryAmount				= null;
			dataArrayObjColl 			= null;
			batteryList					= null;
			userDetails					= null;
			validate					= null;
			duplicate					= 0;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteBatteryDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails		= null;
		Optional<Battery>		battery			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			battery	= batteryRepository.findById(valueObject.getLong("batteryId", 0));
			
			batteryRepository.deleteBattery(DateTimeUtility.getCurrentTimeStamp(), userDetails.getId(), valueObject.getLong("batteryId", 0));
			batteryAmountService.AddBatteryAsignNumber(1, battery.get().getBatteryAmountId(), userDetails.getCompany_id());
			
			valueObject.put("deleted", true);
			valueObject.put("batteryInvoice", battery.get().getBatteryInvoiceId());
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
			battery			= null;
		}
	}
	
	@Override
	public ValueObject deleteBatteryInvoice(ValueObject valueObject) throws Exception {
		List<BatteryAmount>		batteryAmount			= null;
		CustomUserDetails		userDetails				= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			batteryAmount	= batteryAmountRepository.getBatteryAmountDetails(valueObject.getLong("batteryInvoiceId", 0));
			if(batteryAmount == null || batteryAmount.isEmpty()) {
				batteryInvoiceService.delete(userDetails.getId(), DateTimeUtility.getCurrentTimeStamp(), valueObject.getLong("batteryInvoiceId", 0));
				valueObject.put("deleted", true);
				
				pendingVendorPaymentService.deletePendingVendorPayment(valueObject.getLong("batteryInvoiceId", 0), PendingPaymentType.PAYMENT_TYPE_BATTERY_INVOICE);
				
				bankPaymentService.deleteBankPaymentDetailsIfTransactionDeleted(valueObject.getLong("batteryInvoiceId", 0), ModuleConstant.BATTRRY_INVENTORY,userDetails.getCompany_id(), userDetails.getId(),false);
			}else {
				valueObject.put("deleted", false);
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Page<Battery> getDeploymentLog_Location(Integer Location, Integer pageNumber, Integer companyId)
			throws Exception {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return batteryRepository.getDeployment_Page_InventoryLocation(Location, companyId, pageable);

	}
	
	@Override
	public Page<Battery> getDeploymentLog_Search_Battery(String Location, Integer pageNumber, Integer companyId)
			throws Exception {
		HashMap<String, Object>  configuration 		=	null;
		CustomUserDetails		 userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration = companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
					@SuppressWarnings("deprecation")
					Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
					if((boolean) configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
						return batteryRepository.getDeployment_Page_InventoryBattery(Location, companyId, userDetails.getId() ,pageable);
					}else {
						return batteryRepository.getDeployment_Page_InventoryBattery(Location, companyId, pageable);
					}
					

		} catch (Exception e) {
			throw e;
		}finally {
			
		}
	}
	
	@Override
	public List<BatteryDto> find_List_InventoryBattery_Location(Integer Location, Integer pageNumber, Integer companyId)
			throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.batteryId, BA.batterySerialNumber, BA.batteryManufacturerId, BA.batteryTypeId, BA.batteryAmount,"
							+ " BA.wareHouseLocationId, BA.batteryAmountId, BA.batteryInvoiceId, BA.vid, BA.batteryUsesOdometer, BA.usesNoOfTime,"
							+ " BA.openOdometer, BA.closedOdometer, BA.batteryPurchaseDate, BA.batteryAsignedDate, BA.batteryStatusId, BA.batteryUsesStatusId,"
							+ " BA.batteryScrapedDate, BM.manufacturerName, BT.batteryType, PL.partlocation_name, BA.batteryCapacityId, BC.batteryCapacity, "
							+ " BT.partNumber, BT.warrantyPeriod, BT.warrantyTypeId, V.vehicle_registration, V.vehicle_Odometer, BA.subLocationId, PSL.partlocation_name "
							+ " FROM Battery AS BA "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BA.wareHouseLocationId "
							+ " INNER JOIN BatteryManufacturer BM ON BM.batteryManufacturerId = BA.batteryManufacturerId"
							+ " INNER JOIN BatteryType BT ON BT.batteryTypeId = BA.batteryTypeId"
							+ " INNER JOIN BatteryCapacity BC ON BC.batteryCapacityId = BA.batteryCapacityId "
							+ " LEFT JOIN Vehicle AS V ON V.vid = BA.vid"
							+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = BA.subLocationId "
							+ " where BA.wareHouseLocationId = "+Location+" AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0 ", Object[].class);
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]>	results = queryt.getResultList();
			
			List<BatteryDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					BatteryDto	batteryInvoice = new BatteryDto();
					
					batteryInvoice.setBatteryId((Long) result[0]);
					batteryInvoice.setBatterySerialNumber((String) result[1]);
					batteryInvoice.setBatteryManufacturerId((Long) result[2]);
					batteryInvoice.setBatteryTypeId((Long) result[3]);
					batteryInvoice.setBatteryAmount((Double) result[4]);
					batteryInvoice.setWareHouseLocationId((Integer) result[5]);
					batteryInvoice.setBatteryAmountId((Long) result[6]);
					batteryInvoice.setBatteryInvoiceId((Long) result[7]);
					batteryInvoice.setVid((Integer) result[8]);
					batteryInvoice.setBatteryUsesOdometer((Integer) result[9]);
					batteryInvoice.setUsesNoOfTime((Long) result[10]);
					batteryInvoice.setOpenOdometer((Integer) result[11]);
					batteryInvoice.setClosedOdometer((Integer) result[12]);
					batteryInvoice.setBatteryPurchaseDate((Timestamp) result[13]);
					batteryInvoice.setBatteryAsignedDate((Timestamp) result[14]);
					batteryInvoice.setBatteryStatusId((short) result[15]);
					batteryInvoice.setBatteryStatus(BatteryConstant.getBatteryAsignedStatusName((short) result[15]));
					batteryInvoice.setBatteryUsesStatusId((short) result[16]);
					batteryInvoice.setBatteryUsesStatus(BatteryConstant.getBatteryUsesStatusName((short) result[16]));
					batteryInvoice.setBatteryScrapedDate((Timestamp) result[17]);
					batteryInvoice.setManufacturerName((String) result[18]);
					batteryInvoice.setBatteryType((String) result[19]);
					batteryInvoice.setLocationName((String) result[20]);
					batteryInvoice.setBatteryCapacityId((Long) result[21]);
					batteryInvoice.setBatteryCapacity((String) result[22]);
					batteryInvoice.setPartNumber((String) result[23]);
					batteryInvoice.setWarrantyPeriod((Integer) result[24]);
					batteryInvoice.setWarrantyTypeId((short) result[25]);
					batteryInvoice.setWarrantyType(BatteryTypeDto.getWarrantyTypeName((short) result[25]));
					batteryInvoice.setVehicle_registration((String) result[26]);
					
					if(batteryInvoice.getPartNumber() != null) {
						batteryInvoice.setBatteryType(batteryInvoice.getBatteryType() + "-"+batteryInvoice.getPartNumber()+"("+batteryInvoice.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(batteryInvoice.getWarrantyTypeId())+")");
					}else {
						batteryInvoice.setBatteryType(batteryInvoice.getBatteryType() +"("+batteryInvoice.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(batteryInvoice.getWarrantyTypeId())+")");
					}
					
					if(result[28] != null) {
						batteryInvoice.setSubLocationId((Integer) result[28]);
						batteryInvoice.setSubLocation((String) result[29]);
					}
					
					/*if(batteryInvoice.getUsesNoOfTime() != null && batteryInvoice.getBatteryAsignedDate() != null) {
						batteryInvoice.setUsesNoOfTime(batteryInvoice.getUsesNoOfTime() + DateTimeUtility.getDayDiffBetweenTwoDates(batteryInvoice.getBatteryAsignedDate(), DateTimeUtility.getCurrentTimeStamp()));
					}else if(batteryInvoice.getUsesNoOfTime() == null && batteryInvoice.getBatteryAsignedDate() != null) {
						batteryInvoice.setUsesNoOfTime(DateTimeUtility.getDayDiffBetweenTwoDates(batteryInvoice.getBatteryAsignedDate(), DateTimeUtility.getCurrentTimeStamp()));
					}*/
					
					list.add(batteryInvoice);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<BatteryDto> find_List_InventoryBattery_Search(String term, Integer pageNumber, Integer companyId)
			throws Exception {
		CustomUserDetails			userDetails		= null;
		HashMap<String, Object>		configuration	= null;
		TypedQuery<Object[]> 		queryt 			= null;
		List<BatteryDto>	list	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			if(!(boolean) configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
				queryt = entityManager
						.createQuery("SELECT BA.batteryId, BA.batterySerialNumber, BA.batteryManufacturerId, BA.batteryTypeId, BA.batteryAmount,"
								+ " BA.wareHouseLocationId, BA.batteryAmountId, BA.batteryInvoiceId, BA.vid, BA.batteryUsesOdometer, BA.usesNoOfTime,"
								+ " BA.openOdometer, BA.closedOdometer, BA.batteryPurchaseDate, BA.batteryAsignedDate, BA.batteryStatusId, BA.batteryUsesStatusId,"
								+ " BA.batteryScrapedDate, BM.manufacturerName, BT.batteryType, PL.partlocation_name, BA.batteryCapacityId, BC.batteryCapacity, "
								+ " BT.partNumber, BT.warrantyPeriod, BT.warrantyTypeId "
								+ " FROM Battery AS BA "
								+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BA.wareHouseLocationId "
								+ " INNER JOIN BatteryManufacturer BM ON BM.batteryManufacturerId = BA.batteryManufacturerId"
								+ " INNER JOIN BatteryType BT ON BT.batteryTypeId = BA.batteryTypeId"
								+ " INNER JOIN BatteryCapacity BC ON BC.batteryCapacityId = BA.batteryCapacityId "
								+ " where (lower(BA.batterySerialNumber) Like ('%" + term + "%') OR lower(BC.batteryCapacity) LIKE ('%" + term + "%') ) AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0 ", Object[].class);
			}else {
				queryt = entityManager
						.createQuery("SELECT BA.batteryId, BA.batterySerialNumber, BA.batteryManufacturerId, BA.batteryTypeId, BA.batteryAmount,"
								+ " BA.wareHouseLocationId, BA.batteryAmountId, BA.batteryInvoiceId, BA.vid, BA.batteryUsesOdometer, BA.usesNoOfTime,"
								+ " BA.openOdometer, BA.closedOdometer, BA.batteryPurchaseDate, BA.batteryAsignedDate, BA.batteryStatusId, BA.batteryUsesStatusId,"
								+ " BA.batteryScrapedDate, BM.manufacturerName, BT.batteryType, PL.partlocation_name, BA.batteryCapacityId, BC.batteryCapacity, "
								+ " BT.partNumber, BT.warrantyPeriod, BT.warrantyTypeId "
								+ " FROM Battery AS BA "
								+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BA.wareHouseLocationId "
								+ " INNER JOIN BatteryManufacturer BM ON BM.batteryManufacturerId = BA.batteryManufacturerId"
								+ " INNER JOIN BatteryType BT ON BT.batteryTypeId = BA.batteryTypeId"
								+ " INNER JOIN BatteryCapacity BC ON BC.batteryCapacityId = BA.batteryCapacityId "
								+ " INNER JOIN PartLocationPermission PLP ON PLP.partLocationId = BA.wareHouseLocationId AND PLP.user_Id = "+userDetails.getId()+""
								+ " where (lower(BA.batterySerialNumber) Like ('%" + term + "%') OR lower(BC.batteryCapacity) LIKE ('%" + term + "%') ) AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0 ", Object[].class);
			}
			
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE_FIFTY);
			queryt.setMaxResults(PAGE_SIZE_FIFTY);
			List<Object[]>	results = queryt.getResultList();
			
			
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					BatteryDto	batteryInvoice = new BatteryDto();
					
					batteryInvoice.setBatteryId((Long) result[0]);
					batteryInvoice.setBatterySerialNumber((String) result[1]);
					batteryInvoice.setBatteryManufacturerId((Long) result[2]);
					batteryInvoice.setBatteryTypeId((Long) result[3]);
					batteryInvoice.setBatteryAmount((Double) result[4]);
					batteryInvoice.setWareHouseLocationId((Integer) result[5]);
					batteryInvoice.setBatteryAmountId((Long) result[6]);
					batteryInvoice.setBatteryInvoiceId((Long) result[7]);
					batteryInvoice.setVid((Integer) result[8]);
					batteryInvoice.setBatteryUsesOdometer((Integer) result[9]);
					batteryInvoice.setUsesNoOfTime((Long) result[10]);
					batteryInvoice.setOpenOdometer((Integer) result[11]);
					batteryInvoice.setClosedOdometer((Integer) result[12]);
					batteryInvoice.setBatteryPurchaseDate((Timestamp) result[13]);
					batteryInvoice.setBatteryAsignedDate((Timestamp) result[14]);
					batteryInvoice.setBatteryStatusId((short) result[15]);
					batteryInvoice.setBatteryStatus(BatteryConstant.getBatteryAsignedStatusName((short) result[15]));
					batteryInvoice.setBatteryUsesStatusId((short) result[16]);
					batteryInvoice.setBatteryUsesStatus(BatteryConstant.getBatteryUsesStatusName((short) result[16]));
					batteryInvoice.setBatteryScrapedDate((Timestamp) result[17]);
					batteryInvoice.setManufacturerName((String) result[18]);
					batteryInvoice.setBatteryType((String) result[19]);
					batteryInvoice.setLocationName((String) result[20]);
					batteryInvoice.setBatteryCapacityId((Long) result[21]);
					batteryInvoice.setBatteryCapacity((String) result[22]);
					batteryInvoice.setPartNumber((String) result[23]);
					batteryInvoice.setWarrantyPeriod((Integer) result[24]);
					batteryInvoice.setWarrantyTypeId((short) result[25]);
					batteryInvoice.setWarrantyType(BatteryTypeDto.getWarrantyTypeName((short) result[25]));
					
					if(batteryInvoice.getPartNumber() != null) {
						batteryInvoice.setBatteryType(batteryInvoice.getBatteryType() + "-"+batteryInvoice.getPartNumber()+"("+batteryInvoice.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(batteryInvoice.getWarrantyTypeId())+")");
					}else {
						batteryInvoice.setBatteryType(batteryInvoice.getBatteryType() +"("+batteryInvoice.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(batteryInvoice.getWarrantyTypeId())+")");
					}
					
					if(batteryInvoice.getUsesNoOfTime() != null && batteryInvoice.getBatteryAsignedDate() != null) {
						batteryInvoice.setUsesNoOfTime(batteryInvoice.getUsesNoOfTime() + DateTimeUtility.getDayDiffBetweenTwoDates(batteryInvoice.getBatteryAsignedDate(), DateTimeUtility.getCurrentTimeStamp()));
					}else if(batteryInvoice.getUsesNoOfTime() == null && batteryInvoice.getBatteryAsignedDate() != null) {
						batteryInvoice.setUsesNoOfTime(DateTimeUtility.getDayDiffBetweenTwoDates(batteryInvoice.getBatteryAsignedDate(), DateTimeUtility.getCurrentTimeStamp()));
					}
					
					list.add(batteryInvoice);
				}
			}
			}
			return list;
		} catch (Exception e) {
			throw e;
		}finally {
			
		}
	}
	
	@Override
	public ValueObject getLocationBatteryList(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails								= null;
		Integer					pageNumber								= null;
		Integer					location								= null;
		Long 					locationWiseBatteryAvailableCount 		= null;
		Long					locationWisebatteryServiceCount 		= null;
		Long					locationWisebatteryScrapedCount 		= null;	
		try {
			userDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			location	= valueObject.getInt("location");
			pageNumber	= valueObject.getInt("pageNumber");
			Page<Battery> page = getDeploymentLog_Location(location, pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			
			locationWiseBatteryAvailableCount 	= getlocationWisebatteryCountByStatus(userDetails.getCompany_id(), BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE, location);
			locationWisebatteryServiceCount 	= getlocationWisebatteryCountByStatus(userDetails.getCompany_id(),BatteryConstant.BATTERY_ASIGNED_STATUS_IN_SERVICE, location);
			locationWisebatteryScrapedCount 	= getlocationWisebatteryCountByStatus(userDetails.getCompany_id(),BatteryConstant.BATTERY_ASIGNED_STATUS_IN_SCRAPED, location);


			valueObject.put("locationWiseBatteryAvailableCount", locationWiseBatteryAvailableCount);
			valueObject.put("locationWisebatteryServiceCount", locationWisebatteryServiceCount);
			valueObject.put("locationWisebatteryScrapedCount", locationWisebatteryScrapedCount);
			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("batteryQuentity", page.getTotalElements());

			List<BatteryDto> pageList = find_List_InventoryBattery_Location(location, pageNumber, userDetails.getCompany_id());

			valueObject.put("battery", batteryBL.getBatteryListDto(pageList));

			valueObject.put("PartLocations", PLBL.prepareListofBean(PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));

			valueObject.put("location", PartLocationsService.getPartLocations(location).getPartlocation_name());
			valueObject.put("locationId", location);
			
			return valueObject;

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getSearchBatteryList(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails		= null;
		Integer					pageNumber		= null;
		String					term		= null;
		try {
			userDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			term	= valueObject.getString("term");
			pageNumber	= valueObject.getInt("pageNumber");
			Page<Battery> page = getDeploymentLog_Search_Battery(term, pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("batteryQuentity", page.getTotalElements());

			List<BatteryDto> pageList = find_List_InventoryBattery_Search(term, pageNumber, userDetails.getCompany_id());

			valueObject.put("battery", pageList);

			valueObject.put("PartLocations", PLBL.prepareListofBean(PartLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));

			
			return valueObject;

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getBatteryInfo(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails		= null;
		BatteryDto				batteryDto		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			batteryDto	= getBatteryInfo(valueObject.getLong("batteryId",0), userDetails.getCompany_id());
			valueObject.put("battery", batteryBL.getFinalBatteryDto(batteryDto));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
			batteryDto		= null;
		}
	}
	
	@Override
	public BatteryDto getBatteryInfo(Long batteryId, Integer companyId) throws Exception {
		try {

			javax.persistence.Query query = entityManager.createQuery("SELECT BA.batteryId, BA.batterySerialNumber, BA.batteryManufacturerId, BA.batteryTypeId, BA.batteryAmount,"
							+ " BA.wareHouseLocationId, BA.batteryAmountId, BA.batteryInvoiceId, BA.vid, BA.batteryUsesOdometer, BA.usesNoOfTime,"
							+ " BA.openOdometer, BA.closedOdometer, BA.batteryPurchaseDate, BA.batteryAsignedDate, BA.batteryStatusId, BA.batteryUsesStatusId,"
							+ " BA.batteryScrapedDate, BM.manufacturerName, BT.batteryType, PL.partlocation_name, BA.batteryCapacityId, BC.batteryCapacity, "
							+ " BT.partNumber, BT.warrantyPeriod, BT.warrantyTypeId, BA.createdOn, BA.lastModifiedOn, V.vehicle_registration, V.Vehicle_Location, BA.vid,"
							+ " U.firstName, U2.firstName, U3.firstName, BA.batteryFirstAsignedDate, V.vehicle_Odometer,BA.subLocationId, PSL.partlocation_name, BA.dismountedBatteryStatusId  "
							+ " FROM Battery AS BA "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BA.wareHouseLocationId "
							+ " INNER JOIN BatteryManufacturer BM ON BM.batteryManufacturerId = BA.batteryManufacturerId"
							+ " INNER JOIN BatteryType BT ON BT.batteryTypeId = BA.batteryTypeId "
							+ " INNER JOIN BatteryCapacity BC ON BC.batteryCapacityId = BA.batteryCapacityId"
							+ " LEFT JOIN Vehicle V ON V.vid = BA.vid"
							+ " LEFT JOIN User U ON U.id = BA.createdById"
							+ " LEFT JOIN User U2 ON U2.id = BA.lastModifiedById"
							+ " LEFT JOIN User U3 ON U3.id = BA.battryScrapedById"
							+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = BA.subLocationId "
							+ " where BA.batteryId = "+batteryId+" AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0");
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			BatteryDto	batteryInvoice = null;
			
				if (result != null) {
					batteryInvoice = new BatteryDto();
					
					batteryInvoice.setBatteryId((Long) result[0]);
					batteryInvoice.setBatterySerialNumber((String) result[1]);
					batteryInvoice.setBatteryManufacturerId((Long) result[2]);
					batteryInvoice.setBatteryTypeId((Long) result[3]);
					if(result[4] != null)
					batteryInvoice.setBatteryAmount(Double.parseDouble(toFixedTwo.format(result[4])));
					batteryInvoice.setWareHouseLocationId((Integer) result[5]);
					batteryInvoice.setBatteryAmountId((Long) result[6]);
					batteryInvoice.setBatteryInvoiceId((Long) result[7]);
					batteryInvoice.setVid((Integer) result[8]);
					batteryInvoice.setBatteryUsesOdometer((Integer) result[9]);
					batteryInvoice.setUsesNoOfTime((Long) result[10]);
					batteryInvoice.setOpenOdometer((Integer) result[11]);
					batteryInvoice.setClosedOdometer((Integer) result[12]);
					batteryInvoice.setBatteryPurchaseDate((Timestamp) result[13]);
					batteryInvoice.setBatteryAsignedDate((Timestamp) result[14]);
					batteryInvoice.setBatteryStatusId((short) result[15]);
					batteryInvoice.setBatteryStatus(BatteryConstant.getBatteryAsignedStatusName((short) result[15]));
					batteryInvoice.setBatteryUsesStatusId((short) result[16]);
					batteryInvoice.setBatteryUsesStatus(BatteryConstant.getBatteryUsesStatusName((short) result[16]));
					batteryInvoice.setBatteryScrapedDate((Timestamp) result[17]);
					batteryInvoice.setManufacturerName((String) result[18]);
					batteryInvoice.setBatteryType((String) result[19]);
					batteryInvoice.setLocationName((String) result[20]);
					batteryInvoice.setBatteryCapacityId((Long) result[21]);
					batteryInvoice.setBatteryCapacity((String) result[22]);
					batteryInvoice.setPartNumber((String) result[23]);
					batteryInvoice.setWarrantyPeriod((Integer) result[24]);
					batteryInvoice.setWarrantyTypeId((short) result[25]);
					batteryInvoice.setWarrantyType(BatteryTypeDto.getWarrantyTypeName((short) result[25]));
					batteryInvoice.setCreatedOn((Timestamp) result[26]);
					batteryInvoice.setLastModifiedOn((Timestamp) result[27]);
					batteryInvoice.setVehicle_registration((String) result[28]);
					batteryInvoice.setVehicle_Location((String)result[29]);
					batteryInvoice.setVid((Integer) result[30]);
					batteryInvoice.setCreatedBy((String) result[31]);
					batteryInvoice.setLastmodifiedBy((String) result[32]);
					batteryInvoice.setBatteryScrapBy((String) result[33]);
					batteryInvoice.setBatteryFirstAsignedDate((Timestamp) result[34]);
					batteryInvoice.setVehicle_Odometer((Integer) result[35]);
					
					
					if(batteryInvoice.getPartNumber() != null) {
						batteryInvoice.setBatteryType(batteryInvoice.getBatteryType() + "-"+batteryInvoice.getPartNumber()+"("+batteryInvoice.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(batteryInvoice.getWarrantyTypeId())+")");
					}else {
						batteryInvoice.setBatteryType(batteryInvoice.getBatteryType() +"("+batteryInvoice.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(batteryInvoice.getWarrantyTypeId())+")");
					}
					
					if(batteryInvoice.getUsesNoOfTime() != null && batteryInvoice.getBatteryAsignedDate() != null) {
						if(batteryInvoice.getBatteryStatusId() == BatteryConstant.BATTERY_ASIGNED_STATUS_IN_SERVICE) {
							batteryInvoice.setUsesNoOfTime(batteryInvoice.getUsesNoOfTime() + DateTimeUtility.getDayDiffBetweenTwoDates(batteryInvoice.getBatteryAsignedDate(), DateTimeUtility.getCurrentTimeStamp()));
						}
						//batteryInvoice.setUsesNoOfTime(batteryInvoice.getUsesNoOfTime() + DateTimeUtility.getDayDiffBetweenTwoDates(batteryInvoice.getBatteryAsignedDate(), DateTimeUtility.getCurrentTimeStamp()));
					}else if(batteryInvoice.getUsesNoOfTime() == null && batteryInvoice.getBatteryAsignedDate() != null) {
						batteryInvoice.setUsesNoOfTime(DateTimeUtility.getDayDiffBetweenTwoDates(batteryInvoice.getBatteryAsignedDate(), DateTimeUtility.getCurrentTimeStamp()));
					}
					if(batteryInvoice.getBatteryUsesOdometer() != null && batteryInvoice.getBatteryUsesOdometer() > 0 && batteryInvoice.getVehicle_Odometer() != null && batteryInvoice.getOpenOdometer() != null) {
						batteryInvoice.setBatteryUsesOdometer(batteryInvoice.getBatteryUsesOdometer() + batteryInvoice.getVehicle_Odometer() - batteryInvoice.getOpenOdometer());
					}else {
						if(batteryInvoice.getVehicle_Odometer() != null && batteryInvoice.getOpenOdometer() != null) {
							batteryInvoice.setBatteryUsesOdometer(batteryInvoice.getVehicle_Odometer() - batteryInvoice.getOpenOdometer());
						}else {
							batteryInvoice.setBatteryUsesOdometer(0);
						}
					}
					
					if(result[36] != null) {
						batteryInvoice.setSubLocationId((Integer)result[36]);
						batteryInvoice.setSubLocation((String)result[37]);
					}
					if(result[38] != null && (short)result[38] > 0) {
						batteryInvoice.setDismountBatteryStatus(BatteryConstant.getOldBatteryMovedTo((short)result[38]));
					}else {
						batteryInvoice.setDismountBatteryStatus("");
					}
			}
			
			return batteryInvoice;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject updateBatterySerialNumber(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails		= null;
		List<Battery> 			validate		= null;
		Battery 				battery			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			validate	= batteryRepository.validateBatterySerialNumber(valueObject.getString("batterySerialNumber"), userDetails.getCompany_id());
			battery		= batteryRepository.getBatteryDetailsByBatteryId(valueObject.getLong("batteryId", 0), userDetails.getCompany_id());
			
			if(battery != null) {
				if(battery.getBatterySerialNumber().equals(valueObject.getString("batterySerialNumber"))) {
					updateBatterySerialNumber(valueObject.getString("batterySerialNumber"), valueObject.getLong("batteryId", 0) , userDetails.getCompany_id());
				}else {
					if(validate == null || validate.isEmpty()) {
						updateBatterySerialNumber(valueObject.getString("batterySerialNumber"), valueObject.getLong("batteryId", 0) , userDetails.getCompany_id());
						valueObject.put("updated",true);
					}else {
						valueObject.put("already",true);
					}
				}
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
			validate		= null;
		}
	}
	
	@Transactional
	private void updateBatterySerialNumber(String asigned, Long batteryId, Integer companyId) throws Exception {
		try {
			entityManager.createQuery("UPDATE Battery  SET batterySerialNumber = '"+asigned+"' "
					+ " where batteryId=" + batteryId+" AND companyId = "+companyId+"").executeUpdate();
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<BatteryDto> getBatteryListForDropdown(String term , Integer companyId, Long id) throws Exception {
		HashMap<String, Object>  	configuration 		=	null;
		TypedQuery<Object[]> queryt = null;
		List<BatteryDto>	list	= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			if(!(boolean) configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
				queryt = entityManager
						.createQuery("SELECT BA.batteryId, BA.batterySerialNumber, BA.batteryManufacturerId, BA.batteryTypeId, BA.batteryCapacityId "
								+ " FROM Battery AS BA "
								+ " where lower(BA.batterySerialNumber) Like ('%" + term + "%') AND BA.batteryStatusId = "+BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE+" AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0 ", Object[].class);
			}else {
				queryt = entityManager
						.createQuery("SELECT BA.batteryId, BA.batterySerialNumber, BA.batteryManufacturerId, BA.batteryTypeId, BA.batteryCapacityId "
								+ " FROM Battery AS BA "
								+ " INNER JOIN PartLocationPermission PLP ON PLP.partLocationId = BA.wareHouseLocationId AND PLP.user_Id = "+id+""
								+ " where lower(BA.batterySerialNumber) Like ('%" + term + "%') AND BA.batteryStatusId = "+BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE+" AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0 ", Object[].class);
			}
			
			
			List<Object[]>	results = queryt.getResultList();
			
			
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					BatteryDto	batteryInvoice = new BatteryDto();
					
					batteryInvoice.setBatteryId((Long) result[0]);
					batteryInvoice.setBatterySerialNumber((String) result[1]);
					batteryInvoice.setBatteryManufacturerId((Long) result[2]);
					batteryInvoice.setBatteryTypeId((Long) result[3]);
					batteryInvoice.setBatteryCapacityId((Long) result[4]);
					
					list.add(batteryInvoice);
				}
			}
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<BatteryDto> getBatteryListForDropdown(String term , Integer companyId, Long capacityId, Long id)
			throws Exception {
		HashMap<String, Object>  	configuration 		=	null;
		TypedQuery<Object[]> queryt = null;
		List<BatteryDto>	list	= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			if(!(boolean) configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
				queryt = entityManager
						.createQuery("SELECT BA.batteryId, BA.batterySerialNumber, BA.batteryManufacturerId, BA.batteryTypeId, BA.batteryCapacityId "
								+ " FROM Battery AS BA "
								+ " where lower(BA.batterySerialNumber) Like ('%" + term + "%') AND BA.batteryStatusId = "+BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE+""
								+ "AND BA.batteryCapacityId = "+capacityId+" AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0 ", Object[].class);
			}else {
				queryt = entityManager
						.createQuery("SELECT BA.batteryId, BA.batterySerialNumber, BA.batteryManufacturerId, BA.batteryTypeId, BA.batteryCapacityId "
								+ " FROM Battery AS BA "
								+ " INNER JOIN PartLocationPermission PLP ON PLP.partLocationId = BA.wareHouseLocationId AND PLP.user_Id = "+id+""
								+ " where lower(BA.batterySerialNumber) Like ('%" + term + "%') AND BA.batteryStatusId = "+BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE+""
								+ "AND BA.batteryCapacityId = "+capacityId+" AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0 ", Object[].class);
			}
			
			
			List<Object[]>	results = queryt.getResultList();
			
			
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					BatteryDto	batteryInvoice = new BatteryDto();
					
					batteryInvoice.setBatteryId((Long) result[0]);
					batteryInvoice.setBatterySerialNumber((String) result[1]);
					batteryInvoice.setBatteryManufacturerId((Long) result[2]);
					batteryInvoice.setBatteryTypeId((Long) result[3]);
					batteryInvoice.setBatteryCapacityId((Long) result[4]);
					
					list.add(batteryInvoice);
				}
			}
			}
			return list;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<BatteryDto> getBatteryListForDropdown(String term, Integer companyId, Integer fromLocation)
			throws Exception {
		List<BatteryDto>	list	= null;
		try {
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.batteryId, BA.batterySerialNumber, BA.batteryManufacturerId, BA.batteryTypeId, BA.batteryCapacityId "
							+ " FROM Battery AS BA "
							+ " where lower(BA.batterySerialNumber) Like ('%" + term + "%') AND BA.batteryStatusId = "+BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE+""
									+ "AND BA.wareHouseLocationId = "+fromLocation+" AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					BatteryDto	batteryInvoice = new BatteryDto();
					
					batteryInvoice.setBatteryId((Long) result[0]);
					batteryInvoice.setBatterySerialNumber((String) result[1]);
					batteryInvoice.setBatteryManufacturerId((Long) result[2]);
					batteryInvoice.setBatteryTypeId((Long) result[3]);
					batteryInvoice.setBatteryCapacityId((Long) result[4]);
					
					list.add(batteryInvoice);
				}
			}
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public BatteryDto getBatteryCapacity(Long batteryId) throws Exception {
		try {
			javax.persistence.Query query  = entityManager
					.createQuery("SELECT BA.batteryId, BA.batteryCapacityId, BC.batteryCapacity "
							+ " FROM Battery AS BA "
							+ " INNER JOIN BatteryCapacity BC ON BC.batteryCapacityId = BA.batteryCapacityId"
							+ " where BA.batteryId = "+batteryId+"");
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			BatteryDto	batteryInvoice = null;
			
				if (result != null) {
					batteryInvoice = new BatteryDto();
					
					batteryInvoice.setBatteryId((Long) result[0]);
					batteryInvoice.setBatteryCapacityId((Long) result[1]);
					batteryInvoice.setBatteryCapacity((String) result[2]);
				}
				return batteryInvoice;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void update_Assign_Battry_To_Vehicle_Mount(Integer VEHICLE_ID, Integer OPEN_ODOMETER,
			short TYRE_ASSIGN_STATUS, Date TYRE_ASSIGN_DATE, Long TYRE_ID, Integer companyId) throws Exception {
		
		batteryRepository.update_Assign_TYRE_To_Vehicle_Mount(VEHICLE_ID, OPEN_ODOMETER, TYRE_ASSIGN_STATUS, TYRE_ASSIGN_DATE, TYRE_ID, companyId);
	}
	
	@Override
	@Transactional
	public void update_Assign_Battry_To_Vehicle_Mount(Integer VEHICLE_ID, Integer OPEN_ODOMETER,
			short TYRE_ASSIGN_STATUS, Date TYRE_ASSIGN_DATE, Long TYRE_ID, Integer companyId,
			Timestamp batteryFirstAsignedDate) throws Exception {
		batteryRepository.update_Assign_TYRE_To_Vehicle_Mount(VEHICLE_ID, OPEN_ODOMETER, TYRE_ASSIGN_STATUS, TYRE_ASSIGN_DATE, TYRE_ID, companyId, batteryFirstAsignedDate);		
	}
	
	@Override
	@Transactional
	public void update_Assign_Battry_To_Vehicle_DisMount(Integer VEHICLE_ID, Integer CLOSE_ODOMETER,
			Integer batteryUsage, short batteryStatusId, Long batteryId, Long usesNoOfTime) throws Exception {
		batteryRepository.update_Assign_Battery_To_Vehicle_DisMount(VEHICLE_ID, CLOSE_ODOMETER, batteryUsage, batteryStatusId, batteryId, usesNoOfTime);
	}
	
	@Override
	public BatteryDto getVehicleBatteryDetails(Long layoutId, Long batteryId) throws Exception {
		try {
			javax.persistence.Query query  = entityManager
					.createQuery("SELECT BA.batteryId, BA.batteryCapacityId, BA.batteryManufacturerId , BM.manufacturerName, BA.batteryTypeId,"
							+ " BT.batteryType, BT.partNumber, BT.warrantyPeriod, BT.warrantyTypeId, BA.batterySerialNumber, BA.batteryAmount,"
							+ " BA.usesNoOfTime, BA.openOdometer, BA.batteryUsesOdometer, BA.batteryAsignedDate, VL.layoutId, BA.vid, VL.layoutPosition,"
							+ " V.vehicle_Odometer, BC.batteryCapacity  "
							+ " FROM Battery AS BA "
							+ " INNER JOIN BatteryManufacturer BM ON BM.batteryManufacturerId = BA.batteryManufacturerId"
							+ " INNER JOIN BatteryType BT ON BT.batteryTypeId = BA.batteryTypeId "
							+ " INNER JOIN BatteryCapacity BC ON BC.batteryCapacityId = BA.batteryCapacityId"
							+ " INNER JOIN VehicleBatteryLayout VL ON VL.batteryId = "+batteryId+""
							+ " INNER JOIN Vehicle V ON V.vid = BA.vid"
							+ " where BA.batteryId = "+batteryId+" AND BA.markForDelete = 0");
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			BatteryDto	batteryInvoice = null;
			
				if (result != null) {
					batteryInvoice = new BatteryDto();
					
					batteryInvoice.setBatteryId((Long) result[0]);
					batteryInvoice.setBatteryCapacityId((Long) result[1]);
					batteryInvoice.setBatteryManufacturerId((Long) result[2]);
					batteryInvoice.setManufacturerName((String) result[3]);
					batteryInvoice.setBatteryTypeId((Long) result[4]);
					batteryInvoice.setBatteryType((String) result[5]);
					batteryInvoice.setPartNumber((String) result[6]);
					batteryInvoice.setWarrantyPeriod((Integer) result[7]);
					batteryInvoice.setWarrantyTypeId( (short) result[8]);
					batteryInvoice.setWarrantyType(BatteryTypeDto.getWarrantyTypeName((short) result[8]));
					batteryInvoice.setBatterySerialNumber((String) result[9]);
					batteryInvoice.setBatteryAmount((Double) result[10]);
					batteryInvoice.setUsesNoOfTime((Long) result[11]);
					batteryInvoice.setOpenOdometer((Integer) result[12]);
					batteryInvoice.setBatteryUsesOdometer((Integer) result[13]);
					batteryInvoice.setBatteryAsignedDate((Timestamp) result[14]);
					batteryInvoice.setAsignedDate(DateTimeUtility.getDateFromTimeStamp(batteryInvoice.getBatteryAsignedDate(), DateTimeUtility.DD_MM_YYYY_HH_MM_AA));
					batteryInvoice.setLayoutId((Long) result[15]);
					batteryInvoice.setVid((Integer) result[16]);
					batteryInvoice.setLayoutPosition((short) result[17]);
					batteryInvoice.setVehicle_Odometer((Integer) result[18]);
					batteryInvoice.setBatteryCapacity((String) result[19]);

					if(batteryInvoice.getPartNumber() != null) {
						batteryInvoice.setBatteryType(batteryInvoice.getBatteryType() + "-"+batteryInvoice.getPartNumber()+"("+batteryInvoice.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(batteryInvoice.getWarrantyTypeId())+")");
					}else {
						batteryInvoice.setBatteryType(batteryInvoice.getBatteryType() +"("+batteryInvoice.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(batteryInvoice.getWarrantyTypeId())+")");
					}
					
					if(batteryInvoice.getUsesNoOfTime() != null) {
						batteryInvoice.setUsesNoOfTime(batteryInvoice.getUsesNoOfTime() + DateTimeUtility.getDayDiffBetweenTwoDates(batteryInvoice.getBatteryAsignedDate(), DateTimeUtility.getCurrentTimeStamp()));
					}else {
						batteryInvoice.setUsesNoOfTime(DateTimeUtility.getDayDiffBetweenTwoDates(batteryInvoice.getBatteryAsignedDate(), DateTimeUtility.getCurrentTimeStamp()));
					}
					
					if(batteryInvoice.getBatteryUsesOdometer() != null) {
						batteryInvoice.setBatteryUsesOdometer(batteryInvoice.getBatteryUsesOdometer() + (batteryInvoice.getVehicle_Odometer() - batteryInvoice.getOpenOdometer()));
					}else {
						batteryInvoice.setBatteryUsesOdometer((batteryInvoice.getVehicle_Odometer() - batteryInvoice.getOpenOdometer()));
					}
				}
				return batteryInvoice;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject viewBatteryHistory(ValueObject valueObject) throws Exception {
		List<BatteryHistoryDto>			batteryHistoryList		= null;
		try {
			batteryHistoryList	=	batteryHistoryService.getBatteryHistoryList(valueObject.getLong("batteryId", 0));
			valueObject.put("batteryHistoryList", batteryHistoryList);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			batteryHistoryList		= null;
		}
	}
	
	@Override
	public List<BatteryDto> getBatteryListToScrap(String query, Integer companyId) throws Exception {
		TypedQuery<Object[]> 		queryt 			= null;
		try {
			
				queryt = entityManager
						.createQuery("SELECT BA.batteryId, BA.batterySerialNumber, BA.batteryManufacturerId, BA.batteryTypeId, BA.batteryAmount,"
								+ " BA.wareHouseLocationId, BA.batteryAmountId, BA.batteryInvoiceId, BA.vid, BA.batteryUsesOdometer, BA.usesNoOfTime,"
								+ " BA.openOdometer, BA.closedOdometer, BA.batteryPurchaseDate, BA.batteryAsignedDate, BA.batteryStatusId, BA.batteryUsesStatusId,"
								+ " BA.batteryScrapedDate, BM.manufacturerName, BT.batteryType, PL.partlocation_name, BA.batteryCapacityId, BC.batteryCapacity, "
								+ " BT.partNumber, BT.warrantyPeriod, BT.warrantyTypeId, BA.batteryFirstAsignedDate "
								+ " FROM Battery AS BA "
								+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BA.wareHouseLocationId "
								+ " INNER JOIN BatteryManufacturer BM ON BM.batteryManufacturerId = BA.batteryManufacturerId"
								+ " INNER JOIN BatteryType BT ON BT.batteryTypeId = BA.batteryTypeId"
								+ " INNER JOIN BatteryCapacity BC ON BC.batteryCapacityId = BA.batteryCapacityId "
								+ " where BA.markForDelete = 0 "+query+" AND BA.companyId = "+companyId+" AND BA.batteryStatusId = "+BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE+" ", Object[].class);
			
			List<Object[]>	results = queryt.getResultList();
			
			List<BatteryDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					BatteryDto	batteryInvoice = new BatteryDto();
					
					batteryInvoice.setBatteryId((Long) result[0]);
					batteryInvoice.setBatterySerialNumber((String) result[1]);
					batteryInvoice.setBatteryManufacturerId((Long) result[2]);
					batteryInvoice.setBatteryTypeId((Long) result[3]);
					batteryInvoice.setBatteryAmount((Double) result[4]);
					batteryInvoice.setWareHouseLocationId((Integer) result[5]);
					batteryInvoice.setBatteryAmountId((Long) result[6]);
					batteryInvoice.setBatteryInvoiceId((Long) result[7]);
					batteryInvoice.setVid((Integer) result[8]);
					batteryInvoice.setBatteryUsesOdometer((Integer) result[9]);
					batteryInvoice.setUsesNoOfTime((Long) result[10]);
					batteryInvoice.setOpenOdometer((Integer) result[11]);
					batteryInvoice.setClosedOdometer((Integer) result[12]);
					batteryInvoice.setBatteryPurchaseDate((Timestamp) result[13]);
					batteryInvoice.setBatteryAsignedDate((Timestamp) result[14]);
					batteryInvoice.setBatteryStatusId((short) result[15]);
					batteryInvoice.setBatteryStatus(BatteryConstant.getBatteryAsignedStatusName((short) result[15]));
					batteryInvoice.setBatteryUsesStatusId((short) result[16]);
					batteryInvoice.setBatteryUsesStatus(BatteryConstant.getBatteryUsesStatusName((short) result[16]));
					batteryInvoice.setBatteryScrapedDate((Timestamp) result[17]);
					batteryInvoice.setManufacturerName((String) result[18]);
					batteryInvoice.setBatteryType((String) result[19]);
					batteryInvoice.setLocationName((String) result[20]);
					batteryInvoice.setBatteryCapacityId((Long) result[21]);
					batteryInvoice.setBatteryCapacity((String) result[22]);
					batteryInvoice.setPartNumber((String) result[23]);
					batteryInvoice.setWarrantyPeriod((Integer) result[24]);
					batteryInvoice.setWarrantyTypeId((short) result[25]);
					batteryInvoice.setWarrantyType(BatteryTypeDto.getWarrantyTypeName((short) result[25]));
					batteryInvoice.setBatteryFirstAsignedDate((Timestamp) result[26]);
					
					if(batteryInvoice.getPartNumber() != null) {
						batteryInvoice.setBatteryType(batteryInvoice.getBatteryType() + "-"+batteryInvoice.getPartNumber()+"("+batteryInvoice.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(batteryInvoice.getWarrantyTypeId())+")");
					}else {
						batteryInvoice.setBatteryType(batteryInvoice.getBatteryType() +"("+batteryInvoice.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(batteryInvoice.getWarrantyTypeId())+")");
					}
					
					if(batteryInvoice.getUsesNoOfTime() != null && batteryInvoice.getBatteryAsignedDate() != null) {
						batteryInvoice.setUsesNoOfTime(batteryInvoice.getUsesNoOfTime() + DateTimeUtility.getDayDiffBetweenTwoDates(batteryInvoice.getBatteryAsignedDate(), DateTimeUtility.getCurrentTimeStamp()));
					}else if(batteryInvoice.getUsesNoOfTime() == null && batteryInvoice.getBatteryAsignedDate() != null) {
						batteryInvoice.setUsesNoOfTime(DateTimeUtility.getDayDiffBetweenTwoDates(batteryInvoice.getBatteryAsignedDate(), DateTimeUtility.getCurrentTimeStamp()));
					}
					
					if(batteryInvoice.getBatteryFirstAsignedDate() != null) {
						Timestamp	currentDate 	= DateTimeUtility.getCurrentTimeStamp();
						Timestamp	warrantyDate	= null;
						if(batteryInvoice.getWarrantyTypeId() == BatteryTypeDto.WARRANTY_TYPE_MONTH) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(batteryInvoice.getBatteryFirstAsignedDate());
							cal.add(Calendar.MONTH, batteryInvoice.getWarrantyPeriod());
							
							warrantyDate	= new Timestamp(cal.getTime().getTime());
							
							if(!currentDate.after(warrantyDate)) {
								batteryInvoice.setWarrantyStatusId(BatteryConstant.BATTERY_WARRANTY_STATUS_UNDER_WARRANTY);
							}else {
								batteryInvoice.setWarrantyStatusId(BatteryConstant.BATTERY_WARRANTY_STATUS_OUT_OF_WARRANTY);
							}
							
						}else if(batteryInvoice.getWarrantyTypeId() == BatteryTypeDto.WARRANTY_TYPE_YEAR) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(batteryInvoice.getBatteryFirstAsignedDate());
							cal.add(Calendar.YEAR, batteryInvoice.getWarrantyPeriod());
							
							warrantyDate	= new Timestamp(cal.getTime().getTime());
							
							if(!currentDate.after(warrantyDate)) {
								batteryInvoice.setWarrantyStatusId(BatteryConstant.BATTERY_WARRANTY_STATUS_UNDER_WARRANTY);
							}else {
								batteryInvoice.setWarrantyStatusId(BatteryConstant.BATTERY_WARRANTY_STATUS_OUT_OF_WARRANTY);
							}
							
						}
					}else {
						batteryInvoice.setWarrantyStatusId(BatteryConstant.BATTERY_WARRANTY_STATUS_UNDER_WARRANTY);
					}
					
					list.add(batteryInvoice);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}finally {
			queryt 			= null;
		}
	}
	
	@Override
	@Transactional
	public void Update_Inventory_Battery_ScropStatus(Long scropedby, Date scropedDate, short Status,
			String Multiple_Tyre_ID, Integer companyId) throws Exception {
		entityManager.createQuery("update Battery set battryScrapedById=" + scropedby + ", batteryScrapedDate='"
				+ scropedDate + "', batteryStatusId='" + Status + "' where batteryId IN (" + Multiple_Tyre_ID + ") AND companyId = "+companyId+"")
				.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateScrapRemark(Long batteryId, String reason) throws Exception {
		entityManager.createQuery("update Battery set batteryScrapRemark= '" + reason + "' where batteryId = "+batteryId+"")
				.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateMultipleBatteryStatus(String batteryIds,short status,int companyId) {
		entityManager.createQuery("UPDATE From Battery SET batteryStatusId="+status+" WHERE batteryId IN ("+batteryIds+") AND companyId = "+companyId+"")
		.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateMultipleBatteryStatusAndLocation(String batteryIds,short status,int companyId,long wareHouseLocationId) {
		entityManager.createQuery("UPDATE From Battery SET batteryStatusId="+status+", wareHouseLocationId ="+wareHouseLocationId+" WHERE batteryId IN ("+batteryIds+") AND companyId = "+companyId+"")
		.executeUpdate();
	}
	
	
	@Override
	public Battery getBattery(Long batteryId) throws Exception {
		
		return batteryRepository.findById(batteryId).get();
	}
	
	@Override
	@Transactional
	public void Update_Transfer_Location_InventoryBattery(Integer tra_TO_LOCATION, Long batteryId, Integer companyId) {
		
		batteryRepository.Update_Transfer_Location_InventoryBattery(tra_TO_LOCATION, batteryId, companyId);
	}
	
	@Override
	public Long getBatteryCount(Long capacityId, Integer companyId) throws Exception {
		
		return batteryRepository.getBatteryCount(capacityId, companyId);
	}
	
	@Override
	public List<Battery> validateBatterySerialNumber(String number, Integer companyId) throws Exception {
		
		return batteryRepository.validateBatterySerialNumber(number, companyId);
	}
	
	@Override
	public void saveBattery(Battery battery) throws Exception {
		
		batteryRepository.save(battery);
	}
	
	@Override
	public ValueObject getBatteryScrapReport(ValueObject valueObject) throws Exception {
		Integer 				locationId				= 0;
		List<BatteryDto>		batterySrcapedList		= null;
		String					query					= "";
		Long					manufacturer			= (long) 0;
		Long					batteryTypeId			= (long) 0;
		Long					capacityId				= (long) 0;
		String					fromDate				= null;
		String					toDate					= null;
		ValueObject				dateRangeObj			= null;
		TypedQuery<Object[]> 	typedQuery				= null;
		List<Object[]> 			results 				= null;
		CustomUserDetails		userDetails				= null;
		try {
			
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			locationId		= valueObject.getInt("locationId",0);
			manufacturer	= valueObject.getLong("manufacurer", 0);
			batteryTypeId	= valueObject.getLong("batterryTypeId", 0);
			capacityId		= valueObject.getLong("batteryCapacityId", 0);
			dateRangeObj	= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"));
			fromDate		= dateRangeObj.getString(DateTimeUtility.FROM_DATE);
			toDate			= dateRangeObj.getString(DateTimeUtility.TO_DATE);
			fromDate = DateTimeUtility.getSqlStrDateFromStrDate(fromDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			toDate   = DateTimeUtility.getSqlStrDateFromStrDate(toDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			
			if(locationId != null && locationId > 0) {
				query	= query+ " AND BA.wareHouseLocationId = "+locationId+"";
			}
			
			if(manufacturer != null && manufacturer > 0) {
				query	= query+ " AND BA.batteryManufacturerId = "+manufacturer+"";
			}
			if(batteryTypeId != null && batteryTypeId > 0) {
				query	= query+ " AND BA.batteryTypeId = "+batteryTypeId+"";
			}
			if(capacityId != null && capacityId > 0) {
				query	= query+ " AND BA.batteryCapacityId = "+capacityId+"";
			}
			typedQuery = entityManager.createQuery(
					" SELECT BA.batteryId, BA.batterySerialNumber, BA.batteryManufacturerId, BA.batteryTypeId, BA.batteryCapacityId, BA.wareHouseLocationId,"
					+ "BA.batteryScrapedDate, BA.battryScrapedById, BA.usesNoOfTime, BA.batteryUsesOdometer,BM.manufacturerName,"
					+ " BT.batteryType, BC.batteryCapacity, U.firstName, U.lastName, PL.partlocation_name, BT.partNumber, BT.warrantyPeriod,"
					+ " BT.warrantyTypeId "
					+ " FROM Battery AS BA "
					+ " INNER JOIN BatteryManufacturer BM ON BM.batteryManufacturerId = BA.batteryManufacturerId"
					+ " INNER JOIN BatteryType	BT ON BT.batteryTypeId = BA.batteryTypeId"
					+ " INNER JOIN BatteryCapacity	BC ON BC.batteryCapacityId = BA.batteryCapacityId"
					+ " INNER JOIN User U ON U.id = BA.battryScrapedById"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BA.wareHouseLocationId"
					+ " WHERE BA.batteryScrapedDate BETWEEN '"+ fromDate +"' AND '" + toDate + "' " + query
					+ " AND BA.companyId = "+userDetails.getCompany_id()  
					+ " AND BA.markForDelete = 0  ORDER BY BA.batteryScrapedDate desc",
					Object[].class);
			
			results = typedQuery.getResultList();
		
			
			if (results != null && !results.isEmpty()) {
				batterySrcapedList = new ArrayList<BatteryDto>();
				BatteryDto	batteryDto	= null;
				for (Object[] result : results) {
					batteryDto	= new BatteryDto();
					
					batteryDto.setBatteryId((Long) result[0]);
					batteryDto.setBatterySerialNumber((String) result[1]);
					batteryDto.setBatteryManufacturerId((Long) result[2]);
					batteryDto.setBatteryTypeId((Long) result[3]);
					batteryDto.setBatteryCapacityId((Long) result[4]);
					batteryDto.setWareHouseLocationId((Integer) result[5]);
					batteryDto.setBatteryScrapedDate((Timestamp) result[6]);
					batteryDto.setBattryScrapedById((Long) result[7]);
					batteryDto.setUsesNoOfTime((Long) result[8]);
					batteryDto.setBatteryUsesOdometer((Integer) result[9]);
					batteryDto.setManufacturerName((String) result[10]);
					batteryDto.setBatteryType((String) result[11]);
					batteryDto.setBatteryCapacity((String) result[12]);
					batteryDto.setBatteryScrapBy((String) result[13]+"_"+(String) result[14]);
					batteryDto.setLocationName((String) result[15]);
					batteryDto.setPartNumber((String) result[16]);
					batteryDto.setWarrantyPeriod((Integer) result[17]);
					batteryDto.setWarrantyTypeId((short) result[18]);
					batteryDto.setWarrantyType(BatteryTypeDto.getWarrantyTypeName(batteryDto.getWarrantyTypeId()));
					
					
					if(batteryDto.getBatteryScrapedDate() != null) {
						batteryDto.setScrapedDate(DateTimeUtility.getDateFromTimeStamp(batteryDto.getBatteryScrapedDate(), DateTimeUtility.DD_MM_YYYY));
					}
					
					if(batteryDto.getPartNumber() != null) {
						batteryDto.setBatteryType(batteryDto.getBatteryType() + "-"+batteryDto.getPartNumber()+"("+batteryDto.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(batteryDto.getWarrantyTypeId())+")");
					}else {
						batteryDto.setBatteryType(batteryDto.getBatteryType() +"("+batteryDto.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(batteryDto.getWarrantyTypeId())+")");
					}
					
					batterySrcapedList.add(batteryDto);
				}
			}
			valueObject.put("BatterySrcapedList", batterySrcapedList);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<BatteryDto> getVehicleBatteryListForCost(Integer vid) throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BL.vid, VCF.costPerDay, B.batteryId, B.batterySerialNumber "
							+ " FROM VehicleBatteryLayout BL "
							+ " INNER JOIN Battery B ON B.batteryId = BL.batteryId "
							+ " INNER JOIN VehicleCostFixing VCF ON VCF.batteryTypeId = B.batteryTypeId AND VCF.costType = 2"
							+ " where BL.vid = "+vid+" AND BL.batteryAsigned = 1 AND  BL.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<BatteryDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					BatteryDto	batteryInvoice = new BatteryDto();
					
					batteryInvoice.setVid((Integer) result[0]);
					batteryInvoice.setBatteryAmount((Double) result[1]);
					batteryInvoice.setBatteryId((Long) result[2]);
					batteryInvoice.setBatterySerialNumber((String) result[3]);
					
					list.add(batteryInvoice);
				}
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<BatteryDto> getGroupVehicleBatteryListForCost(long vehicleGroupId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BL.vid, VCF.costPerDay, B.batteryId "
							+ " FROM VehicleBatteryLayout BL "
							+ " INNER JOIN Battery B ON B.batteryId = BL.batteryId"
							+ " INNER JOIN Vehicle V ON V.vid = B.vid"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId "
							+ " INNER JOIN VehicleCostFixing VCF ON VCF.batteryTypeId = B.batteryTypeId AND VCF.costType = 2"
							+ " where VG.gid = "+vehicleGroupId+" AND BL.batteryAsigned = 1 AND  BL.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<BatteryDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					BatteryDto	batteryInvoice = new BatteryDto();
					
					batteryInvoice.setVid((Integer) result[0]);
					batteryInvoice.setBatteryAmount((Double) result[1]);
					batteryInvoice.setBatteryId((Long) result[2]);
					
					
					list.add(batteryInvoice);
				}
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getBatteryStockReport(ValueObject valueObject) throws Exception {
		
		ArrayList<BatteryDto>				batteryStockList				= null;
		BatteryDto							batteryStock					= null;
		ValueObject							valOutObject					= null;
		CustomUserDetails					userDetails						= null;
		TypedQuery<Object[]> 				typedQuery						= null;
		List<Object[]> 						results 						= null;
		String								fromDate						= null;
		String								toDate							= null;
		ValueObject							dateRangeObj					= null;
		String 								Location 						= "";		
		String 								batteryCapacity 				= "";		
		String 								batteryType 					= "";		
		String 								batteryManufacurer 				= "";		
		long								LocationId						= 0;
		long								batteryCapacityId				= 0;
		long								batterryModelTypeId				= 0;
		long								batteryManufacurerId			= 0;
		
		try {
			userDetails							= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LocationId							= valueObject.getLong("LocationId",0);
			batteryCapacityId					= valueObject.getLong("batteryCapacityId",0);
			batterryModelTypeId					= valueObject.getLong("batterryModelTypeId",0);
			batteryManufacurerId				= valueObject.getLong("batteryManufacurer",0);
			dateRangeObj						= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"));
			fromDate							= dateRangeObj.getString(DateTimeUtility.FROM_DATE);
			toDate								= dateRangeObj.getString(DateTimeUtility.TO_DATE);
			
			fromDate = DateTimeUtility.getSqlStrDateFromStrDate(fromDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			toDate   = DateTimeUtility.getSqlStrDateFromStrDate(toDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			
			if (LocationId > 0) {
				Location = "AND B.wareHouseLocationId =" + LocationId + "";
				
			}
			if (batteryCapacityId > 0 ) {
				batteryCapacity = "AND B.batteryCapacityId=" + batteryCapacityId + "";
			}
			if (batterryModelTypeId > 0 ) {
				batteryType = "AND B.batteryTypeId=" + batterryModelTypeId + "";
			}
			if (batteryManufacurerId > 0 ) {
				batteryManufacurer = "AND B.batteryManufacturerId=" + batteryManufacurerId + "";
			}
			String query = "" + Location + " " + batteryCapacity + " " + batteryType + " " + batteryManufacurer + " ";
			
			typedQuery = entityManager.createQuery(
					" SELECT B.batterySerialNumber, BM.manufacturerName, BT.batteryType , BC.batteryCapacity, "
					+ " B.batteryAmount, B.wareHouseLocationId, V.vehicle_registration, B.batteryStatusId, B.usesNoOfTime, B.batteryUsesOdometer, PL.partlocation_name,"
					+ " V.vehicle_Odometer "
					+ " FROM Battery AS B "
					+ " INNER JOIN BatteryManufacturer BM ON BM.batteryManufacturerId = B.batteryManufacturerId "
					+ " INNER JOIN BatteryCapacity BC ON BC.batteryCapacityId = B.batteryCapacityId"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = B.wareHouseLocationId "
					+ " INNER JOIN BatteryType BT ON BT.batteryTypeId = B.batteryTypeId   "					
					+ " LEFT JOIN Vehicle V ON V.vid = B.vid"
					+ " WHERE B.batteryPurchaseDate BETWEEN '"+ fromDate +"' AND '" + toDate + "' " +query
					+ " AND B.companyId = "+userDetails.getCompany_id()  
					+ " AND B.markForDelete =0",
					Object[].class);
			
			 results = typedQuery.getResultList();
			
			if (results != null && !results.isEmpty()) {
				batteryStockList = new ArrayList<BatteryDto>();
				for (Object[] result : results) {
					batteryStock = new BatteryDto();
					batteryStock.setBatterySerialNumber((String) result[0]);
					batteryStock.setManufacturerName((String) result[1]);
					batteryStock.setBatteryType((String) result[2]);
					batteryStock.setBatteryCapacity((String) result[3]);
					batteryStock.setBatteryAmount((double)result[4]);
					batteryStock.setWareHouseLocationId((Integer)result[5]);
					batteryStock.setVehicle_registration((String) result[6]);
					batteryStock.setBatteryStatusId((short)(result[7]));
					batteryStock.setBatteryStatus(BatteryConstant.getBatteryAsignedStatusName(batteryStock.getBatteryStatusId()));
					batteryStock.setUsesNoOfTime((long)(result[8]));
					batteryStock.setBatteryUsesOdometer((Integer)(result[9]));
					batteryStock.setLocationName((String)(result[10]));
					batteryStock.setVehicle_Odometer((Integer)(result[11]));
					
					
					if(batteryStock.getUsesNoOfTime() != null && batteryStock.getBatteryAsignedDate() != null) {
						batteryStock.setUsesNoOfTime(batteryStock.getUsesNoOfTime() + DateTimeUtility.getDayDiffBetweenTwoDates(batteryStock.getBatteryAsignedDate(), DateTimeUtility.getCurrentTimeStamp()));
					}else if(batteryStock.getUsesNoOfTime() == null && batteryStock.getBatteryAsignedDate() != null) {
						batteryStock.setUsesNoOfTime(DateTimeUtility.getDayDiffBetweenTwoDates(batteryStock.getBatteryAsignedDate(), DateTimeUtility.getCurrentTimeStamp()));
					}

					if(batteryStock.getUsesNoOfTime() != null) {
						if(batteryStock.getBatteryAmount() != null && batteryStock.getBatteryAmount() > 0 && batteryStock.getUsesNoOfTime() != 0) {
							batteryStock.setCostPerDay(batteryStock.getBatteryAmount()/batteryStock.getUsesNoOfTime());
						}
						else {
							batteryStock.setCostPerDay(0.0);
						}
					}
					
					
					batteryStockList.add(batteryStock);
				}
			}

			valOutObject		= new ValueObject();
			valOutObject.put("BatteryStockList", batteryStockList);
			return valOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			batteryStockList				= null;  
			batteryStock					= null;  
			valOutObject					= null;  
			userDetails						= null;  
			typedQuery						= null;  
			results 						= null;  
			fromDate						= null;  
			toDate							= null;  
			dateRangeObj					= null;  
		}
	}
	
	@Override
	public ValueObject getBatteryPurchaseInvoiceReport(ValueObject valueObject) throws Exception {
		String							dateRange				= null;
		long							batterryModelTypeId		= 0;
		long							batteryManufacurerId	= 0;
		Integer							vendorId				= 0;
		CustomUserDetails				userDetails				= null;
		List<BatteryAmountDto> 			BatteryInvoice			= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			batteryManufacurerId				= valueObject.getLong("batteryManufacurer",0);
			batterryModelTypeId					= valueObject.getLong("batterryModelTypeId",0);
			vendorId							= valueObject.getInt("selectVendor", 0);
			dateRange							= valueObject.getString("dateRange");
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				String batteryManufacurer = "", batteryType = "",  Vendor_Name = "", Date = "";
				
				
				if (batteryManufacurerId > 0 ) {
					batteryManufacurer = "AND BA.batteryManufacturerId=" + batteryManufacurerId + "";
				}
				
				if (batterryModelTypeId > 0 ) {
					batteryType = "AND BA.batteryTypeId=" + batterryModelTypeId + "";
				}
				
				if(vendorId > 0 )
				{
					Vendor_Name = " AND BI.vendorId = "+ vendorId +" ";
				}
				
				Date =  " AND BI.invoiceDate between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;
				String query = "  "+ batteryManufacurer +" "+ batteryType +" "+ Vendor_Name +"  "+ Date +"  ";
				
				//Below Commented Code is of SlickGrid. 
				/*tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.PART_PURCHASE_INVOICE_TABLE_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);*/
				
				BatteryInvoice = BatteryReportDaoImpl.Battery_Purchase_Invoice_Report_List(query, userDetails.getCompany_id());

			}
			
			valueObject.put("BatteryPurchaseInvoiceReport", BatteryInvoice);
			
			if(batteryManufacurerId != 0 ) {
				if(BatteryInvoice != null)
				valueObject.put("BatteryManufacturer", BatteryInvoice.get(0).getManufacturerName());
			} else {
				valueObject.put("BatteryManufacturer", "All");
			}
			
			if(batterryModelTypeId != 0 ) {
				if(BatteryInvoice != null)
				valueObject.put("BatteryModel", BatteryInvoice.get(0).getBatteryType());
			} else {
				valueObject.put("BatteryModel", "All");
			}
			
			if(vendorId > 0) {
				if(BatteryInvoice != null)
				valueObject.put("BatteryVendor", BatteryInvoice.get(0).getVendorName());
			} else {
				valueObject.put("BatteryVendor", "All");
			}
			
			/*valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company",
					userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));*/
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			throw e;
		}finally {
			BatteryInvoice		= null;
			userDetails			= null;
			dateRange			= null;
		}
	}
	
	@Override
	public long getlocationWisebatteryCountByStatus(Integer companyId, short status, Integer location) throws Exception {
		try {
			return batteryRepository.getlocationWisebatteryCountByStatus(companyId,status,location );
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public ValueObject getBatteryCountListDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails	   					userDetails				= null;
		Integer				   					locationId				= null;
		short				   					status					= 0;
		List<BatteryDto>						batteryCountList		= null;
		String 									query					= "";
		Integer				   					pageNumber				= null;
		Page<Battery> 							page					= null;
		try {

			pageNumber	 		= valueObject.getInt("pageNo",0);
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			locationId			= valueObject.getInt("locationId",0);
			status				= valueObject.getShort("status");
			
			if(locationId > 0) {
				page 	= getDeployment_Page_ShowInLocationWiseBatteryList(pageNumber, status,locationId, userDetails.getCompany_id());
				query	= " BA.companyId ="+userDetails.getCompany_id()+"  AND BA.batteryStatusId ="+status+"  AND BA.wareHouseLocationId = "+locationId+"  AND BA.markForDelete = 0";
			}else {
				page 	= getDeployment_Page_ShowInBatteryList(pageNumber, status,userDetails.getCompany_id());
				query	= " BA.companyId ="+userDetails.getCompany_id()+"  AND BA.batteryStatusId ="+status+"  AND BA.markForDelete = 0";
			}
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());
			valueObject.put("SelectPage", pageNumber);
			

			batteryCountList = getBatteryCountList(query,pageNumber);

			valueObject.put("batteryCountList", batteryCountList);
		//	valueObject.put("SelectPage", pageNumber);
		
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			 userDetails				= null;
		}
	}
	
	@Override
	public List<BatteryDto> getBatteryCountList(String query, Integer pageNumber) throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.batteryId, BA.batterySerialNumber, BA.batteryManufacturerId, BM.manufacturerName, "
							+ " BT.batteryType, BA.batteryCapacityId, BC.batteryCapacity, BA.wareHouseLocationId, PL.partlocation_name, "
							+ " BA.vid, V.vehicle_registration, BA.batteryStatusId "
							+ " FROM Battery AS BA "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BA.wareHouseLocationId "
							+ " INNER JOIN BatteryManufacturer BM ON BM.batteryManufacturerId = BA.batteryManufacturerId"
							+ " INNER JOIN BatteryType BT ON BT.batteryTypeId = BA.batteryTypeId"
							+ " INNER JOIN BatteryCapacity BC ON BC.batteryCapacityId = BA.batteryCapacityId "
							+ " LEFT JOIN Vehicle AS V ON V.vid = BA.vid"
							+ " where "+query+" ", Object[].class);
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]>	results = queryt.getResultList();
			
			List<BatteryDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					BatteryDto	batteryInvoice = new BatteryDto();
					
					batteryInvoice.setBatteryId((Long) result[0]);
					batteryInvoice.setBatterySerialNumber((String) result[1]);
					batteryInvoice.setBatteryManufacturerId((Long) result[2]);
					batteryInvoice.setManufacturerName((String) result[3]);
					batteryInvoice.setBatteryType((String) result[4]);
					batteryInvoice.setBatteryCapacityId((Long) result[5]);
					batteryInvoice.setBatteryCapacity((String) result[6]);
					batteryInvoice.setWareHouseLocationId((Integer) result[7]);
					batteryInvoice.setLocationName((String) result[8]);
					batteryInvoice.setVid((Integer) result[9]);
					if(result[9] != null) {
						batteryInvoice.setVehicle_registration((String) result[10]);
					}else {
						batteryInvoice.setVehicle_registration("-");
					}
					batteryInvoice.setBatteryStatusId((short) result[11]);
					batteryInvoice.setBatteryStatus(BatteryConstant.getBatteryAsignedStatusName((short) result[11]));
					
					list.add(batteryInvoice);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Page<Battery> getDeployment_Page_ShowInLocationWiseBatteryList(Integer pageNumber,short status, Integer locationId, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "batteryId");
		return batteryRepository.getDeployment_Page_ShowInLocationWiseBatteryList(companyId,status,locationId, pageable);
	}
	
	public Page<Battery> getDeployment_Page_ShowInBatteryList(Integer pageNumber,short status, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "batteryId");
		return batteryRepository.getDeployment_Page_ShowInBatteryList(companyId,status, pageable);
	}
	@Transactional
	@Override
	public void saveBatteryDocument(BatteryInvoice battery, MultipartFile file, ValueObject valueObject) throws Exception {
		try {
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			org.fleetopgroup.persistence.document.BatteryInvoiceDocument batteryDoc = new org.fleetopgroup.persistence.document.BatteryInvoiceDocument();
			batteryDoc.setBatteryInvoiceId(battery.getBatteryInvoiceId());
			if(file != null) {
				byte[] bytes = file.getBytes();
				batteryDoc.setBatteryInvoiceFileName(file.getOriginalFilename());
				batteryDoc.setBatteryInvoiceContent(bytes);
				batteryDoc.setBatteryInvoiceContentType(file.getContentType());
			} else {
				if (valueObject.getString("base64String",null) != null) {
					byte[] bytes = ByteConvertor.base64ToByte(valueObject.getString("base64String",null));
					
					batteryDoc.setBatteryInvoiceFileName(valueObject.getString("imageName",null));
					batteryDoc.setBatteryInvoiceContent(bytes);
					batteryDoc.setBatteryInvoiceContentType(valueObject.getString("imageExt",null));
				}
			}	
			batteryDoc.setMarkForDelete(false);
			batteryDoc.setCreatedById(battery.getCreatedById());
			batteryDoc.setLastModifiedById(battery.getLastModifiedById());
			batteryDoc.setCreated(toDate);
			batteryDoc.setLastupdated(toDate);
			batteryDoc.setCompanyId(battery.getCompanyId());

			addBatteryInvoiceDocument(batteryDoc);
			updateBatteryInvoiceDocumentId(batteryDoc.get_id(), true, batteryDoc.getBatteryInvoiceId(), batteryDoc.getCompanyId());

		} catch (Exception e) {
			throw e;
		}
	}
	@Transactional
	public void addBatteryInvoiceDocument(org.fleetopgroup.persistence.document.BatteryInvoiceDocument batteryDoc) {
		batteryDoc.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_BATTERY_INVOICE_DOCUMENT));
		mongoTemplate.save(batteryDoc);
	}
	
	@Transactional
	public void updateBatteryInvoiceDocumentId(Long batteryInvoiceDocId, boolean batteryDocument, Long batteryInvoiceId, Integer companyId) {
		batteryInvoiceRepository.updateBatteryInvoiceDocumentId(batteryInvoiceDocId, batteryDocument, batteryInvoiceId, companyId);

	}
	
	@Override
	public org.fleetopgroup.persistence.document.BatteryInvoiceDocument getBatteryInvoiceDocumentDetails(long batteryDocumentId, Integer company_id)
			throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(batteryDocumentId).and("companyId").is(company_id).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.BatteryInvoiceDocument.class);
	}

	@Override
	public BatteryInvoiceDocument getBatteryInvoiceDocumentDetailsByInvoiceId(Long id, Integer companyId) throws Exception {// checking already exists or not
		
		Query query = new Query();
		query.addCriteria(Criteria.where("batteryInvoiceId").is(id).and("companyId").is(companyId).and("markForDelete").is(false));

		return mongoTemplate.findOne(query, BatteryInvoiceDocument.class);
	}
	
	
	@Transactional
	public ValueObject getLocationWiseBatteryQuantity(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> 						query 					= null;
		List<InventoryDto> 							inventoryDtoList 		= null;
		try {
			String condition = "select  count(*) , B.wareHouseLocationId ,PL.partlocation_name from Battery AS B "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = B.wareHouseLocationId "
					+ " where " ;
					if(!valueObject.getBoolean("withoutManufacturerType", false))
						condition += "B.batteryManufacturerId ="+valueObject.getLong("manufacturerId",0)+" and B.batteryTypeId="+valueObject.getLong("typeId",0)+" and " ;
						condition += "  B.batteryCapacityId = "+valueObject.getLong("sizeId",0)+" AND B.batteryStatusId="+BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE+"  AND B.companyId ="+valueObject.getInt("companyId")+" AND B.markForDelete=0 group by B.wareHouseLocationId";
			
			query = entityManager.createQuery(condition, Object[].class);
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				inventoryDtoList = new ArrayList<>();
				InventoryDto inventoryDto = null;
				for (Object[] result : results) {
					inventoryDto = new InventoryDto();
					inventoryDto.setQuantity((Long)result[0]+0.0);
					inventoryDto.setLocationId((Integer)result[1]);
					inventoryDto.setLocation((String)result[2]);
					
					inventoryDtoList.add(inventoryDto);
					
				}
			}

			valueObject.put("locationWisePartQuantity", inventoryDtoList);
			return 	valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	@Transactional
	public ValueObject getlocationWiseBatteryCount(ValueObject valueobject) throws Exception {
		try {
			valueobject.put("locationWiseCount", batteryRepository.getlocationWisebatteryCount(valueobject.getInt("companyId",0), BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE, valueobject.getInt("location",0),valueobject.getLong("manufacturer",0),valueobject.getLong("model",0), valueobject.getLong("capacity",0)));
			valueobject.put("otherLocationCount", batteryRepository.getAllLocationWisebatteryCount(valueobject.getInt("companyId",0), BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE, valueobject.getInt("location",0),valueobject.getLong("manufacturer",0),valueobject.getLong("model",0), valueobject.getLong("capacity",0)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueobject;
	}
	
	@Override
	@Transactional
	public ValueObject getlocationBatteryCount(ValueObject valueobject) throws Exception {
		try {
			valueobject.put("stockQty", batteryRepository.getlocationAndCapacityWiseCount(valueobject.getInt("companyId",0), BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE, valueobject.getInt("location",0), valueobject.getLong("capacity",0)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueobject;
	}
	@Override
	@Transactional
	public  List<Battery> getlocationWisebatteryList(Integer companyId, short status, Integer location ,long batteryManufacturerId ,long batteryModel,long batteryCapacity) throws Exception {
		return batteryRepository.getlocationWisebatteryList(companyId,status,location,batteryManufacturerId,batteryModel,batteryCapacity);
	}
	
	@Override
	@Transactional
	public  List<Battery> getbatteryListByIds(Integer companyId, short status, Integer location ,List<Long> ids) throws Exception {
		return batteryRepository.getbatteryListByIds(companyId,status,location,ids);
	}
	@Override
	public List<BatteryDto> getBatteryListDropdown(String term, Integer companyId, Integer fromLocation,long capacity)
			throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BA.batteryId, BA.batterySerialNumber, BA.batteryManufacturerId, BA.batteryTypeId, BA.batteryCapacityId "
							+ ",BM.manufacturerName,BT.batteryType,BC.batteryCapacity "
							+ " FROM Battery AS BA "
							+ " INNER JOIN BatteryManufacturer BM ON BM.batteryManufacturerId = BA.batteryManufacturerId"
							+ " INNER JOIN BatteryType BT ON BT.batteryTypeId = BA.batteryTypeId"
							+ " INNER JOIN BatteryCapacity BC ON BC.batteryCapacityId = BA.batteryCapacityId "
							+ " where lower(BA.batterySerialNumber) Like (:term)  AND BA.companyId = "+companyId+" AND BA.batteryStatusId = "+BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE+" AND BA.wareHouseLocationId = "+fromLocation+"  AND BA.batteryCapacityId ="+capacity+" AND BA.markForDelete = 0 ", Object[].class);
			queryt.setParameter("term", "%"+term+"%");
			List<Object[]>	results = queryt.getResultList();
			
			List<BatteryDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					BatteryDto	batteryInvoice = new BatteryDto();
					batteryInvoice.setBatteryId((Long) result[0]);
					batteryInvoice.setBatterySerialNumber((String) result[1]);
					batteryInvoice.setBatteryManufacturerId((Long) result[2]);
					batteryInvoice.setBatteryTypeId((Long) result[3]);
					batteryInvoice.setBatteryCapacityId((Long) result[4]);
					batteryInvoice.setManufacturerName((String) result[5]);
					batteryInvoice.setBatteryType((String) result[6]);
					batteryInvoice.setBatteryCapacity((String) result[7]);
					list.add(batteryInvoice);
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	
	@Override
	@Transactional
	public void updateDismountStatusId(Integer vid, Integer openOdometer, Integer useage, short batteryStatusId, short dismountStatusId, Long batteryId, Long usesNoOfTime) throws Exception {
		batteryRepository.update_Assign_Battery_To_Vehicle_DisMount(vid, openOdometer, useage, batteryStatusId, dismountStatusId, batteryId, usesNoOfTime);
	}
	
	@Override
	public List<BatteryDto> getMoveToRepairBattery(Integer companyId, Integer fromLocationId) throws Exception {
		TypedQuery<Object[]> queryt = null;
		List<BatteryDto>	list	= null;
		try {
			queryt = entityManager
					.createQuery("SELECT BA.batteryId, BA.batterySerialNumber, BA.batteryManufacturerId, BA.batteryTypeId, BA.batteryCapacityId "
							+ " FROM Battery AS BA "
							+ " where BA.batteryStatusId = "+BatteryConstant.BATTERY_ASIGNED_STATUS_IN_UNAVAILABLE+" AND BA.wareHouseLocationId = "+fromLocationId+" "
							+ " AND BA.dismountedBatteryStatusId = "+BatteryConstant.OLD_BATTERY_MOVED_TO_REPAIR+" AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0 ", Object[].class);
			
			
			List<Object[]>	results = queryt.getResultList();
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					BatteryDto	batteryInvoice = new BatteryDto();
					
					batteryInvoice.setBatteryId((Long) result[0]);
					batteryInvoice.setBatterySerialNumber((String) result[1]);
					batteryInvoice.setBatteryManufacturerId((Long) result[2]);
					batteryInvoice.setBatteryTypeId((Long) result[3]);
					batteryInvoice.setBatteryCapacityId((Long) result[4]);
					
					list.add(batteryInvoice);
				}
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	@Transactional
	public void updateInventoryBatteryRejectStatus(Long scropedby, Date scropedDate, short Status, short dismountStatus,
			String Multiple_Tyre_ID, Integer companyId) throws Exception {
		entityManager.createQuery("update Battery set battryScrapedById=" + scropedby + ", batteryScrapedDate='"
				+ scropedDate + "', batteryStatusId='" + Status + "', dismountedBatteryStatusId = "+dismountStatus+" where batteryId IN (" + Multiple_Tyre_ID + ") AND companyId = "+companyId+"")
				.executeUpdate();
	}

@Override
public List<BatteryDto> getLocationWiseBatteryList(Integer companyId, Integer fromLocationId) throws Exception {
	TypedQuery<Object[]> queryt = null;
	List<BatteryDto>	list	= null;
	try {
		queryt = entityManager
				.createQuery("SELECT BA.batteryId, BA.batterySerialNumber, BA.batteryManufacturerId, BA.batteryTypeId, BA.batteryCapacityId "
						+ " FROM Battery AS BA "
						+ " where BA.wareHouseLocationId = "+fromLocationId+" "
						+ " AND BA.companyId = "+companyId+" AND  BA.markForDelete = 0 ", Object[].class);
		
		
		List<Object[]>	results = queryt.getResultList();
		
		if(results != null && !results.isEmpty()) {
			list	=	new ArrayList<>();
			for (Object[] result : results) {
				BatteryDto	batteryInvoice = new BatteryDto();
				
				batteryInvoice.setBatteryId((Long) result[0]);
				batteryInvoice.setBatterySerialNumber((String) result[1]);
				batteryInvoice.setBatteryManufacturerId((Long) result[2]);
				batteryInvoice.setBatteryTypeId((Long) result[3]);
				batteryInvoice.setBatteryCapacityId((Long) result[4]);
				
				list.add(batteryInvoice);
			}
		}
		return list;
	} catch (Exception e) {
		throw e;
	}
}

@Transactional
public List<Battery> Search_Battery_History_Report(String battery) throws Exception {
	
	List<Battery> Dtos = null;
	CustomUserDetails userDetails = null;
	try {
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		TypedQuery<Object[]> queryt = null;
		if(battery != null && !battery.trim().equalsIgnoreCase("") && battery.indexOf('\'') != 0 ) {

			queryt = entityManager.createQuery("SELECT b.batteryId, b.batterySerialNumber FROM Battery AS b "
					+ " where  lower(b.batterySerialNumber) Like ('%" + battery + "%') and b.companyId = "
					+ userDetails.getCompany_id() + " and b.markForDelete = 0", Object[].class);

		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Battery>();
			Battery dropdown = null;
			for (Object[] result : results) {
				
				dropdown = new Battery();

				dropdown.setBatteryId((Long)result[0]);
				dropdown.setBatterySerialNumber((String)result[1]);
				
				Dtos.add(dropdown);
			}
		  }
	    }
		return Dtos;
	} catch (Exception e) {
		throw e;
	}
   }

}
