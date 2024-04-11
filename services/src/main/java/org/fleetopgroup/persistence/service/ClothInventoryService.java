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

import org.fleetopgroup.constant.ApprovalType;
import org.fleetopgroup.constant.ClothInvoiceStockType;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.constant.TallyVoucherTypeContant;
import org.fleetopgroup.persistence.bl.ClothInventoryBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.PendingVendorPaymentBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleLaundryDetailsBL;
import org.fleetopgroup.persistence.dao.ClothInventoryDetailsRepository;
import org.fleetopgroup.persistence.dao.ClothInventoryRepository;
import org.fleetopgroup.persistence.dao.ClothInventoryStockTypeDetailsRepository;
import org.fleetopgroup.persistence.dao.ClothTypesRepository;
import org.fleetopgroup.persistence.dao.InventoryDamageAndLostHistoryRepository;
import org.fleetopgroup.persistence.dao.SentLaundryClothDetailsRepository;
import org.fleetopgroup.persistence.dao.UpholsterySendLaundryInvoiceRepository;
import org.fleetopgroup.persistence.dao.VehicleClothInventoryDetailsRepository;
import org.fleetopgroup.persistence.dao.VehicleClothInventoryHistoryRepository;
import org.fleetopgroup.persistence.dao.VehicleClothMaxAllowedSettingRepository;
import org.fleetopgroup.persistence.dao.VehicleLaundryDetailsRepository;
import org.fleetopgroup.persistence.document.ClothInvoiceDocument;
import org.fleetopgroup.persistence.dto.ClothInventoryDetailsDto;
import org.fleetopgroup.persistence.dto.ClothInventoryStockTypeDetailsDto;
import org.fleetopgroup.persistence.dto.ClothInvoiceDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryDamageAndLostHistoryDto;
import org.fleetopgroup.persistence.dto.InventoryUpholsteryTransferDto;
import org.fleetopgroup.persistence.dto.LaundryClothReceiveHistoryDto;
import org.fleetopgroup.persistence.dto.SentLaundryClothDetailsDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.UpholsterySendLaundryInvoiceDto;
import org.fleetopgroup.persistence.dto.VehicleClothInventoryDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleClothInventoryHistoryDto;
import org.fleetopgroup.persistence.dto.VehicleClothMaxAllowedSettingDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VendorFixedLaundryRateDto;
import org.fleetopgroup.persistence.dto.VendorPaymentNotPaidDto;
import org.fleetopgroup.persistence.model.ClothInventoryDetails;
import org.fleetopgroup.persistence.model.ClothInventoryStockTypeDetails;
import org.fleetopgroup.persistence.model.ClothInvoice;
import org.fleetopgroup.persistence.model.ClothInvoiceSequenceCounter;
import org.fleetopgroup.persistence.model.InventoryDamageAndLostHistory;
import org.fleetopgroup.persistence.model.LaundryClothReceiveHistory;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.SentLaundryClothDetails;
import org.fleetopgroup.persistence.model.SentLaundryInvoiceSequenceCounter;
import org.fleetopgroup.persistence.model.UpholsterySendLaundryInvoice;
import org.fleetopgroup.persistence.model.VehicleClothInventoryDetails;
import org.fleetopgroup.persistence.model.VehicleClothInventoryHistory;
import org.fleetopgroup.persistence.model.VehicleClothMaxAllowedSetting;
import org.fleetopgroup.persistence.model.VehicleLaundryDetails;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.report.dao.IUpholsteryReportDao;
import org.fleetopgroup.persistence.report.dao.VehicleLaundryDetailsDao;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryStockTypeDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IClothInvoiceSequenceService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryDamageAndLostHistoryService;
import org.fleetopgroup.persistence.serviceImpl.ILaundryClothReceiveHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ISentLaundryClothDetailsService;
import org.fleetopgroup.persistence.serviceImpl.ISentLaundryInvoiceSequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IUpholsterySendLaundryInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleClothInventoryDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleClothInventoryHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVendorFixedLaundryRateService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.web.util.ByteConvertor;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClothInventoryService implements IClothInventoryService {
	
	@Autowired	IClothInvoiceSequenceService					clothInvoiceSequenceService;
	@Autowired	ClothInventoryRepository						clothInventoryRepository;
	@Autowired	ClothInventoryDetailsRepository					clothInventoryDetailsRepository;
	@Autowired	IPartLocationsService							partLocationsService;
	@Autowired	ClothInventoryStockTypeDetailsRepository		clothInventoryStockTypeDetailsRepository;
	@Autowired	IClothInventoryStockTypeDetailsService			clothInventoryStockTypeDetailsService;
	@Autowired	IClothInventoryDetailsService					clothInventoryDetailsService;
	@Autowired	IVehicleClothInventoryDetailsService			vehicleClothInventoryDetailsService;
	@Autowired	IVehicleService									vehicleService;
	@Autowired	IVehicleClothInventoryHistoryService			vehicleClothInventoryHistoryService;
	@Autowired	ISentLaundryInvoiceSequenceCounterService		laundrySequenceCounter;
	@Autowired	UpholsterySendLaundryInvoiceRepository			upholsterySendLaundryInvoiceRepository;
	@Autowired	SentLaundryClothDetailsRepository				sentLaundryClothDetailsRepository;
	@Autowired	IUpholsterySendLaundryInvoiceService			upholsterySendLaundryInvoiceService;
	@Autowired	ISentLaundryClothDetailsService					sentLaundryClothDetailsService;
	@Autowired	ILaundryClothReceiveHistoryService				laundryClothReceiveHistoryService;
	@Autowired  IUpholsteryReportDao							upholsteryReportDaoImpl;
	@Autowired private IUserProfileService						userProfileService;
	@Autowired  VehicleClothMaxAllowedSettingRepository			vehicleClothMaxAllowedSettingRepository;
	@Autowired	ClothTypesRepository							clothTypesRepository;
	@Autowired	VehicleClothInventoryHistoryRepository			vehicleClothInventoryHistoryRepository;
	@Autowired 	IInventoryDamageAndLostHistoryService			inventoryDamageAndLostHistoryService;
	@Autowired 	InventoryDamageAndLostHistoryRepository			inventoryDamageAndLostHistoryRepository;
	@Autowired 	VehicleClothInventoryDetailsRepository			vehicleClothInventoryDetailsRepository;
	@Autowired  IVendorFixedLaundryRateService					vendorFixedLaundryRateService;
	@Autowired private ISequenceCounterService					sequenceCounterService;
	@Autowired private MongoTemplate							mongoTemplate;
	@Autowired private VehicleLaundryDetailsRepository			vehicllDetailsRepository;
	@Autowired private VehicleLaundryDetailsDao					vehicleLaundryDetailsDao;
	@Autowired private IPendingVendorPaymentService				pendingVendorPaymentService;
	@Autowired private ICompanyConfigurationService				companyConfigurationService;
	@Autowired private IBankPaymentService						bankPaymentService;
	@Autowired private ICashPaymentService						cashPaymentService;
	@Autowired private IVendorService							vendorService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext EntityManager entityManager;
	
	private static final int PAGE_SIZE 					= 10;
	private static final int PAGE_SIZE_SERVICE 			= 50;
	private static final int PAGE_SIZE_DAMAGE 			= 50;
	private static final int PAGE_SIZE_WASHING 			= 50;
	
	ClothInventoryBL	inventoryBL	= new ClothInventoryBL();
	PartLocationsBL 	PLBL 		= new PartLocationsBL();
	VehicleBL 			VBL			= new VehicleBL();
	
	SimpleDateFormat	format = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat	format1 = new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY_HH_MM_AA);
	SimpleDateFormat tallyFormat		= new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat sqlDateFormat 	= new SimpleDateFormat("dd-MM-yyyy");
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public ValueObject saveClothInventoryDetails(ValueObject valueObject, MultipartFile file) throws Exception {
		
		ClothInvoiceSequenceCounter			sequenceCounter						= null;
		ValueObject							outObject							= null;
		CustomUserDetails					userDetails							= null;
		ArrayList<ValueObject> 				dataArrayObjColl 					= null;
		List<ClothInventoryDetails>			clothInventoryDetailsList			= null;
		ClothInventoryStockTypeDetails		typeDetails 						= null;
		HashMap<String, Object> 			configuration						= null;
		ClothInvoice						validateBatteryInvoice				= null;
		try {
			outObject 		= new ValueObject();
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			sequenceCounter	= clothInvoiceSequenceService.findNextInvoiceNumber(userDetails.getCompany_id());
			
			if(sequenceCounter == null) {
				outObject.put("saveMessage", "Sequence not found please contact to system Administrator !");
				return outObject;
			}
			
			configuration 			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.BATTERY_CONFIGURATION_CONFIG);
			validateBatteryInvoice	= getClothInvoiceByInvoiceNumber(valueObject.getString("invoiceNumber"), userDetails.getCompany_id());
			
			if((boolean) configuration.getOrDefault("validateInvoiceNumber", false)) {
				if(validateBatteryInvoice != null) {
					outObject.put("duplicateInvoiceNumber", true);
					return outObject;
				}
				
			}
			
			ClothInvoice	clothInvoice	= inventoryBL.getClothInvoiceDto(valueObject,file);
			
			clothInvoice.setClothInvoiceNumber(sequenceCounter.getNextVal());
			
			ClothInvoice	savedClothInvoice = clothInventoryRepository.save(clothInvoice);
			
			valueObject.put("invoiceId", clothInvoice.getClothInvoiceId());
			
			
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("clothDetails");
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				clothInventoryDetailsList	= new ArrayList<>();
				for (ValueObject object : dataArrayObjColl) {
					clothInventoryDetailsList.add(inventoryBL.getClothInventoryDetails(object, valueObject));
				}
				clothInventoryDetailsRepository.saveAll(clothInventoryDetailsList);
				
				if(clothInventoryDetailsList != null && !clothInventoryDetailsList.isEmpty()) {
					for(ClothInventoryDetails	clothInventoryDetails : clothInventoryDetailsList) {
						
						ClothInventoryStockTypeDetails	validate =		clothInventoryStockTypeDetailsRepository.validateClothInventoryStockTypeDetails(clothInventoryDetails.getClothTypesId(), clothInventoryDetails.getWareHouseLocation());
						
						typeDetails	= new ClothInventoryStockTypeDetails();
						typeDetails.setClothTypesId(clothInventoryDetails.getClothTypesId());
						typeDetails.setWareHouseLocationId(clothInventoryDetails.getWareHouseLocation());
						typeDetails.setCompanyId(userDetails.getCompany_id());
						if(validate == null) {
							if(clothInvoice.getClothTypeId() == ClothInvoiceStockType.STOCK_TYPE_NEW) {
								typeDetails.setUsedStockQuantity(0.0);
								typeDetails.setNewStockQuantity(clothInventoryDetails.getQuantity());
							}else {
								typeDetails.setUsedStockQuantity(clothInventoryDetails.getQuantity());
								typeDetails.setNewStockQuantity(0.0);
							
							}
							typeDetails.setInServiceQuantity(0.0);
							typeDetails.setInWashingQuantity(0.0);
							typeDetails.setDamagedQuantity(0.0);
							typeDetails.setLosedQuantity(0.0);
							typeDetails.setInTransferQuantity(0.0);
							
						}else {
							if(clothInvoice.getClothTypeId() == ClothInvoiceStockType.STOCK_TYPE_NEW) {
								typeDetails.setUsedStockQuantity(validate.getUsedStockQuantity());
								typeDetails.setNewStockQuantity(clothInventoryDetails.getQuantity() + validate.getNewStockQuantity());
							}else {
								typeDetails.setUsedStockQuantity(validate.getUsedStockQuantity() + clothInventoryDetails.getQuantity());
								typeDetails.setNewStockQuantity(validate.getNewStockQuantity());
							
							}
							typeDetails.setInServiceQuantity(validate.getInServiceQuantity());
							typeDetails.setInWashingQuantity(validate.getInWashingQuantity());
							typeDetails.setDamagedQuantity(validate.getDamagedQuantity());
							typeDetails.setLosedQuantity(validate.getLosedQuantity());
							typeDetails.setInTransferQuantity(validate.getInTransferQuantity());
							typeDetails.setClothInventoryStockTypeDetailsId(validate.getClothInventoryStockTypeDetailsId());
						}
						
						clothInventoryStockTypeDetailsRepository.save(typeDetails);
					}
				}
			}
			
			if(clothInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
				  PendingVendorPayment	payment	=	PendingVendorPaymentBL.createPendingVendorPaymentDTOForCI(clothInvoice);
				  pendingVendorPaymentService.savePendingVendorPayment(payment);
			}
			
			if(file != null && !file.isEmpty() || valueObject.getString("base64String",null) != null) {
				saveClothDocument(savedClothInvoice, file,valueObject);
			}

			if(valueObject.getBoolean("allowBankPaymentDetails",false) && (PaymentTypeConstant.getPaymentTypeIdList().contains(clothInvoice.getPaymentTypeId()) || clothInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH )) {
				ValueObject bankPaymentValueObject = JsonConvertor
						.toValueObjectFormSimpleJsonString(valueObject.getString("bankPaymentDetails"));
				bankPaymentValueObject.put("bankPaymentTypeId",clothInvoice.getPaymentTypeId());
				bankPaymentValueObject.put("userId", userDetails.getId());
				bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
				bankPaymentValueObject.put("moduleId", savedClothInvoice.getClothInvoiceId());
				bankPaymentValueObject.put("moduleNo", savedClothInvoice.getClothInvoiceNumber());
				bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.UPHOLSTERY_INVENTORY);
				bankPaymentValueObject.put("amount", savedClothInvoice.getInvoiceAmount());
				
				Vendor vendor = vendorService.getVendor(savedClothInvoice.getVendorId());
				
				bankPaymentValueObject.put("remark", "Cloth Invoice CI-"+savedClothInvoice.getClothInvoiceNumber() + " Vendor : "+vendor.getVendorName()+" Payment Type : "+PaymentTypeConstant.getPaymentTypeName(clothInvoice.getPaymentTypeId()));
				//bankPaymentValueObject.put("paidDate", savedClothInvoice.getInvoiceDate());
				if(PaymentTypeConstant.PAYMENT_TYPE_CASH == clothInvoice.getPaymentTypeId())
					cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
				else
					bankPaymentService.addBankPaymentDetailsFromValueObject(bankPaymentValueObject);
			}
			
			return valueObject;
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			throw e;
		}finally {
			outObject			= null;
			sequenceCounter		= null;
			userDetails			= null;
			typeDetails 		= null;
		}
	}

	@Override
	public ValueObject getClothInvoiceList(ValueObject valueObject) throws Exception {
		CustomUserDetails	   userDetails				= null;
		Integer				   pageNumber				= null;
		try {

			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber			= valueObject.getInt("pageNumber",0);
			Page<ClothInvoice> page = getDeployment_Page_ClothInvoice(pageNumber, userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());

			List<ClothInvoiceDto> pageList = getClothInvoiceList(pageNumber, userDetails.getCompany_id());

			valueObject.put("ClothInvoice", pageList);
			valueObject.put("SelectPage", pageNumber);
			valueObject.put("PartLocations", PLBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
		
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			 userDetails				= null;
		}
	}
	
	public Page<ClothInvoice> getDeployment_Page_ClothInvoice(Integer pageNumber, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "clothInvoiceId");
		return clothInventoryRepository.getDeployment_Page_ClothInvoice(companyId, pageable);
	}
	
	
	@Override
	public List<ClothInvoiceDto> getClothInvoiceList(Integer pageNumber, Integer companyId) throws Exception {

		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]> 					resultList 				= null; 
		List<ClothInvoiceDto> 			clothInvoiceList 		= null;
		ClothInvoiceDto 				clothInvoiceDto			= null;

		try {

			typedQuery = entityManager.createQuery("SELECT BI.clothInvoiceId, BI.clothInvoiceNumber, BI.vendorId, V.vendorName, BI.wareHouseLocation,"
					+ " PL.partlocation_name, BI.invoiceNumber, BI.invoiceDate, BI.invoiceAmount, U.firstName , U.lastName, BI.quantity,BI.cloth_document, BI.cloth_document_id, BI.vendorPaymentStatus , BI.subLocationId, PSL.partlocation_name , BI.paymentTypeId"
					+ " FROM ClothInvoice AS BI"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BI.wareHouseLocation"
					+ " LEFT JOIN Vendor AS V ON V.vendorId = BI.vendorId "
					+ " INNER JOIN User U ON U.id = BI.createdById "
					+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = BI.subLocationId "
					+ " WHERE BI.companyId ="+companyId+" AND BI.markForDelete = 0 ORDER BY BI.clothInvoiceId DESC", Object[].class);

			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				clothInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					clothInvoiceDto = new ClothInvoiceDto();
					
					clothInvoiceDto.setClothInvoiceId((Long) result[0]);
					clothInvoiceDto.setClothInvoiceNumber((Long) result[1]);
					clothInvoiceDto.setVendorId((Integer) result[2]);
					clothInvoiceDto.setVendorName((String) result[3]);
					clothInvoiceDto.setWareHouseLocation((Integer) result[4]);
					clothInvoiceDto.setClothLocation((String) result[5]);
					clothInvoiceDto.setInvoiceNumber((String) result[6]);
					clothInvoiceDto.setInvoiceDate((Timestamp) result[7]);
					clothInvoiceDto.setInvoiceAmount(Double.parseDouble(toFixedTwo.format(result[8])));
					clothInvoiceDto.setCreatedBy((String) result[9]+"_"+(String) result[10]);
					if(result[11] != null) {
						clothInvoiceDto.setQuantity(Double.parseDouble(toFixedTwo.format(result[11])));
					}else {
						clothInvoiceDto.setQuantity(0.0);
					}

					if(clothInvoiceDto.getInvoiceDate() != null) {
						clothInvoiceDto.setInvoiceDateStr(DateTimeUtility.getDateFromTimeStamp(clothInvoiceDto.getInvoiceDate(), DateTimeUtility.DD_MM_YYYY));
					}
					if(clothInvoiceDto.getVendorName() == null) {
						clothInvoiceDto.setVendorName("--");
					}
					clothInvoiceDto.setCloth_document((boolean) result[12]);
					if(result[13] != null ) {
						clothInvoiceDto.setCloth_document_id((Long)result[13]);
					}
					clothInvoiceDto.setVendorPaymentStatus((short)result[14]);
					clothInvoiceDto.setVendorPaymentStatusStr(FuelVendorPaymentMode.getPaymentMode(clothInvoiceDto.getVendorPaymentStatus()));
					if(result[15] != null ) {
						clothInvoiceDto.setSubLocationId((Integer)result[15]);
						clothInvoiceDto.setSubLocation((String)result[16]);
						clothInvoiceDto.setPaymentTypeId((short)result[17]);
						
					}else {
						clothInvoiceDto.setSubLocation("");
					}
					clothInvoiceList.add(clothInvoiceDto);
				}
			}

			return clothInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			clothInvoiceList 		= null;
			clothInvoiceDto			= null;
		}
	}
	
	@Override
	public ValueObject getlocationClothDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails		= null;
		Integer					pageNumber		= null;
		Integer					location		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			location	= valueObject.getInt("location");
			pageNumber	= valueObject.getInt("pageNumber");
			Page<ClothInventoryStockTypeDetails> page = getDeploymentLog_Location(location, pageNumber);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("clothQuentity", page.getTotalElements());

			List<ClothInventoryStockTypeDetailsDto> pageList = clothInventoryStockTypeDetailsService.getLocationClothDetails(location, pageNumber);
			valueObject.put("clothList", pageList);

			valueObject.put("PartLocations", PLBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));

			valueObject.put("location", partLocationsService.getPartLocations(location).getPartlocation_name());
			valueObject.put("locationId", location);
			
			return valueObject;

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Page<ClothInventoryStockTypeDetails> getDeploymentLog_Location(Integer Location, Integer pageNumber) throws Exception {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return clothInventoryStockTypeDetailsRepository.getDeploymentLog_Location(Location, pageable);

	}
	
	@Override
	public ValueObject get_list_ClothInvoiceDetails(ValueObject valueObject) throws Exception {
		ClothInvoiceDto 					clothInvoiceDto				= null;
		CustomUserDetails					userDetails					= null;
		List<ClothInventoryDetailsDto>		clothInventoryDetailsDtos	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			clothInvoiceDto	   			= getClothInvoiceDetails(valueObject.getLong("invoiceId", 0), userDetails.getCompany_id());
			clothInventoryDetailsDtos   = clothInventoryDetailsService.getClothInventoryDetailsListById(valueObject.getLong("invoiceId", 0), userDetails.getCompany_id());
			
			valueObject.clear();
			valueObject.put("clothInvoiceDto", clothInvoiceDto);
			valueObject.put("clothInventoryDetailsDtos", clothInventoryDetailsDtos);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			clothInvoiceDto					= null;
			userDetails						= null;
			clothInventoryDetailsDtos		= null;
		}
	}
	
	@Override
	public ClothInvoiceDto getClothInvoiceDetails(Long invoiceId, Integer companyId) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT CI.clothInvoiceId, CI.clothInvoiceNumber, CI.wareHouseLocation, CI.purchaseOrderId, CI.invoiceNumber, CI.invoiceDate, CI.invoiceAmount, CI.vendorId,"
				+ " CI.paymentTypeId, CI.paymentNumber, CI.totalClothAmount, CI.description, CI.vendorPaymentStatus, CI.clothApprovalId, CI.vendorPaymentDate, CI.createdById,"
				+ " CI.lastModifiedById, CI.createdOn, CI.lastModifiedBy, CI.poNumber, CI.clothTypeId, PL.partlocation_name, U.firstName, U2.firstName, PO.purchaseorder_Number,"
				+ " V.vendorName, V.vendorLocation, CI.tallyCompanyId, TC.companyName,CI.subLocationId, PSL.partlocation_name,VSA.approvalId,VA.approvalNumber  "
				+ " FROM ClothInvoice CI "
				+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CI.wareHouseLocation "
				+ " INNER JOIN User U ON U.id = CI.createdById"
				+ " INNER JOIN User U2 ON U2.id = CI.lastModifiedById "
				+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = CI.tallyCompanyId"
				+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = CI.purchaseOrderId"
				+ " LEFT JOIN Vendor V ON V.vendorId = CI.vendorId"
				+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = CI.subLocationId "
				+ " LEFT JOIN VendorSubApprovalDetails VSA ON VSA.invoiceId = CI.clothInvoiceId AND VSA.approvalPlaceId = "+ApprovalType.APPROVAL_TYPE_INVENTORY_CLOTH_INVOICE+" "
				+ " LEFT JOIN VendorApproval VA ON VA.approvalId = VSA.approvalId "
				+ " where CI.clothInvoiceId = "+invoiceId+" AND CI.companyId = "+companyId+" AND CI.markForDelete = 0");

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		ClothInvoiceDto select;
		if (result != null) {
			select = new ClothInvoiceDto();
			
			select.setClothInvoiceId((Long) result[0]);
			select.setClothInvoiceNumber((Long) result[1]);
			select.setWareHouseLocation((Integer) result[2]);
			select.setPurchaseOrderId((Long) result[3]);
			select.setInvoiceNumber((String) result[4]);
			select.setInvoiceDate((Timestamp) result[5]);
			select.setInvoiceAmount((Double) result[6]);
			select.setVendorId((Integer) result[7]);
			select.setPaymentTypeId((short) result[8]);
			select.setPaymentType(PaymentTypeConstant.getPaymentTypeName(select.getPaymentTypeId()));
			select.setPaymentNumber((String) result[9]);
			select.setTotalClothAmount((Double) result[10]);
			select.setDescription((String) result[11]);
			select.setVendorPaymentStatus((short) result[12]);
			select.setVendorPaymentStatusStr(FuelVendorPaymentMode.getPaymentMode(select.getVendorPaymentStatus()));
			select.setClothApprovalId((Long) result[13]);
			select.setVendorPaymentDate((Timestamp) result[14]);
			select.setCreatedById((Long) result[15]);
			select.setLastModifiedById((Long) result[16]);
			select.setCreatedOn((Timestamp) result[17]);
			select.setLastModifiedBy((Timestamp) result[18]);
			select.setPoNumber((String) result[19]);
			if(result[20] != null)
				select.setClothTypeId((short) result[20]);
			select.setClothLocation((String) result[21]);
			select.setCreatedBy((String) result[22]);
			select.setLastUpdatedBy((String) result[23]);
			select.setPurchaseOrderNumber((Long) result[24]);
			select.setVendorName((String) result[25]);
			select.setVendorLocation((String) result[26]);
			if(result[27] != null)
				select.setTallyCompanyId((Long) result[27]);
			if(result[28] != null)
				select.setTallyCompanyName((String) result[28]);
			
			
			select.setPaymentType(PaymentTypeConstant.getPaymentTypeName(select.getPaymentTypeId()));
			if(select.getInvoiceDate() != null)
				select.setInvoiceDateStr(DateTimeUtility.getDateFromTimeStamp(select.getInvoiceDate(), DateTimeUtility.DD_MM_YYYY));
			if(select.getCreatedOn() != null)
				select.setCreatedOnStr(DateTimeUtility.getDateFromTimeStamp(select.getCreatedOn(), DateTimeUtility.DD_MM_YYYY));
			if(select.getLastModifiedBy() != null)
				select.setLastModifiedOnStr(DateTimeUtility.getDateFromTimeStamp(select.getLastModifiedBy(), DateTimeUtility.DD_MM_YYYY));
			if(select.getClothTypeId() > 0) {
				select.setClothTypeStr(ClothInvoiceStockType.getClothInvoiceStockTypeNAme(select.getClothTypeId()));
			}else {
				select.setClothTypeStr("--");
			}	
			if( result[29] != null) {
				select.setSubLocationId((Integer)result[29] );
				select.setSubLocation((String)result[30] );
			}
			if(result[31] != null) {
				select.setApprovalId((Long) result[31]);
				select.setApprovalNumber("A-"+(Long) result[32]);
			}
		} else {
			return null;
		}

		return select;
	}
	
	@Override
	public ValueObject getClothDetailsToEdit(Long invoiceId, Integer companyId) throws Exception {
		ValueObject		valueObject		= null;
		try {
			valueObject	= new ValueObject();
			valueObject.put("clothInvoice", getClothInvoiceDetails(invoiceId, companyId));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject		= null;
		}
	}
	
	@Override
	@Transactional
	public void updateClothInvoice(ClothInvoiceDto clothInvoiceDto) throws Exception {
		try {
			entityManager.createQuery("Update ClothInvoice SET wareHouseLocation = "+clothInvoiceDto.getWareHouseLocation()+" "
					+ ", poNumber = '"+clothInvoiceDto.getPoNumber()+"', invoiceNumber = '"+clothInvoiceDto.getInvoiceNumber()+"' "
					+ ", invoiceDate = '"+clothInvoiceDto.getInvoiceDate()+"' , invoiceAmount = "+clothInvoiceDto.getInvoiceAmount()+""
					+ ", vendorId = "+clothInvoiceDto.getVendorId()+" , description = '"+clothInvoiceDto.getDescription()+"', vendorPaymentStatus = "+clothInvoiceDto.getVendorPaymentStatus()+""
					+ ", lastModifiedById = "+clothInvoiceDto.getLastModifiedById()+", lastModifiedBy = '"+clothInvoiceDto.getLastModifiedBy()+"'"
					+ ", tallyCompanyId = "+clothInvoiceDto.getTallyCompanyId()+", cloth_document = "+clothInvoiceDto.isCloth_document()+",paymentTypeId= "+clothInvoiceDto.getPaymentTypeId()+" , subLocationId="+clothInvoiceDto.getSubLocationId()+"  "
					+ " where clothInvoiceId = "+clothInvoiceDto.getClothInvoiceId()+" AND companyId = "+clothInvoiceDto.getCompanyId()+" ").executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteClothInventoryDetails(ValueObject valueObject) throws Exception {
		ClothInventoryDetails		clothInventoryDetails		= null;
		ClothInvoice				clothInvoice				= null;
		try {
			clothInventoryDetails	= clothInventoryDetailsRepository.findById(valueObject.getLong("clothInventoryDetailsId", 0)).get();
			clothInvoice			= clothInventoryRepository.findById(valueObject.getLong("invoiceId", 0)).get();
			
			removeClothInventoryDetailsFromInvoice(clothInventoryDetails);
			removeClothStockTypeDetails(clothInvoice, clothInventoryDetails.getClothTypesId());
			
			clothInventoryDetailsRepository.deleteClothInventoryDetails(valueObject.getLong("clothInventoryDetailsId", 0));
			
			if(clothInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && clothInvoice.getInvoiceAmount() > 0) {
				pendingVendorPaymentService.deletePendingVendorPaymentAmt(clothInvoice.getClothInvoiceId(), PendingPaymentType.PAYMENT_TYPE_CLOTH_INVOICE, clothInventoryDetails.getTotal());
			}
			clothInvoice			= clothInventoryRepository.findById(valueObject.getLong("invoiceId", 0)).get();
			CustomUserDetails userDetails = Utility.getObject(null);
			HashMap<String,Object> companyConfiguration =companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG );
			if((boolean) companyConfiguration.getOrDefault("allowBankPaymentDetails",false)) {
			ValueObject bankPaymentValueObject=new ValueObject();
			bankPaymentValueObject.put("oldPaymentTypeId",clothInvoice.getPaymentTypeId());
			bankPaymentValueObject.put("bankPaymentTypeId", clothInvoice.getPaymentTypeId());
			bankPaymentValueObject.put("currentPaymentTypeId", clothInvoice.getPaymentTypeId());
			bankPaymentValueObject.put("userId",userDetails.getId());
			bankPaymentValueObject.put("companyId",userDetails.getCompany_id());
			bankPaymentValueObject.put("moduleId",clothInvoice.getInvoiceAmount());
			bankPaymentValueObject.put("moduleNo", clothInvoice.getClothInvoiceNumber());
			bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.UPHOLSTERY_INVENTORY);
			bankPaymentValueObject.put("amount",clothInvoice.getInvoiceAmount());

			Vendor	vendor	=  vendorService.getVendor(clothInvoice.getVendorId());
			bankPaymentValueObject.put("remark", "Delete Cloth Invoice CI-"+clothInvoice.getClothInvoiceNumber()+" vendor : "+vendor.getVendorName());
			
			bankPaymentService.updatePaymentDetailsFromValuObject(bankPaymentValueObject);
			}
			valueObject.put("delete", true);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			clothInvoice				= null;
			clothInventoryDetails		= null;
		}
	}
	
	@Override
	@Transactional
	public void removeClothInventoryDetailsFromInvoice(ClothInventoryDetails inventoryDetailsDto) throws Exception {
		try {

			entityManager.createQuery("Update ClothInvoice SET invoiceAmount = invoiceAmount - "+inventoryDetailsDto.getTotal()+", "
					+ " totalClothAmount = totalClothAmount - "+inventoryDetailsDto.getTotal()+", quantity = quantity - "+inventoryDetailsDto.getQuantity()+""
					+ ", lastModifiedById = "+inventoryDetailsDto.getLastModifiedById()+", lastModifiedBy = '"+DateTimeUtility.getCurrentDate()+"'"
					+ " where clothInvoiceId = "+inventoryDetailsDto.getClothInvoiceId()+" AND companyId = "+inventoryDetailsDto.getCompanyId()+" ").executeUpdate();
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void removeClothStockTypeDetails(ClothInvoice	clothInvoice, Long clothTYpesId) throws Exception {
		try {
			
			if(clothInvoice.getClothTypeId() == ClothInvoiceStockType.STOCK_TYPE_NEW) {
				entityManager.createQuery("Update ClothInventoryStockTypeDetails SET newStockQuantity = newStockQuantity - "+clothInvoice.getQuantity()+" "
						+ " WHERE clothTypesId = "+clothTYpesId+" AND wareHouseLocationId = "+clothInvoice.getWareHouseLocation()+" AND companyId = "+clothInvoice.getCompanyId()+" ").executeUpdate();
			}else {
				entityManager.createQuery("Update ClothInventoryStockTypeDetails SET usedStockQuantity = usedStockQuantity - "+clothInvoice.getQuantity()+" "
						+ " WHERE clothTypesId = "+clothTYpesId+" AND wareHouseLocationId = "+clothInvoice.getWareHouseLocation()+" AND companyId = "+clothInvoice.getCompanyId()+" ").executeUpdate();
			
			}
			
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteClothInventory(ValueObject valueObject) throws Exception {
		List<ClothInventoryDetails>		clothInventoryDetails			= null;
		CustomUserDetails				userDetails						= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			clothInventoryDetails	= clothInventoryDetailsRepository.getClothInventoryDetailsList(valueObject.getLong("invoiceId", 0));
			if(clothInventoryDetails == null || clothInventoryDetails.isEmpty()) {
				entityManager.createQuery("UPDATE ClothInvoice SET markForDelete = 1, lastModifiedById = "+userDetails.getId()+" "
						+ " , lastModifiedBy = '"+DateTimeUtility.getCurrentTimeStamp()+"' where clothInvoiceId = "+valueObject.getLong("invoiceId", 0)+" ").executeUpdate();
				valueObject.put("deleted", true);
				pendingVendorPaymentService.deletePendingVendorPayment(valueObject.getLong("invoiceId", 0), PendingPaymentType.PAYMENT_TYPE_CLOTH_INVOICE);
				
				bankPaymentService.deleteBankPaymentDetailsIfTransactionDeleted(valueObject.getLong("invoiceId", 0), ModuleConstant.UPHOLSTERY_INVENTORY, userDetails.getCompany_id(),userDetails.getId(),false);
			}else {
				valueObject.put("deleted", false);
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject searchClothInvoice(ValueObject valueObject) throws Exception {
		CustomUserDetails	   userDetails				= null;
		Integer				   pageNumber				= null;
		String				   term						= null;
		try {

			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber			= valueObject.getInt("pageNumber",1);
			term				= valueObject.getString("term");
			Page<ClothInvoice> page = getDeployment_Page_ClothInvoice(term, pageNumber, userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("BatteryInvoiceCount", page.getTotalElements());

			List<ClothInvoiceDto> pageList = getClothInvoiceList(term, pageNumber, userDetails.getCompany_id());

			valueObject.put("ClothInvoice", pageList);
			valueObject.put("SelectPage", pageNumber);
			valueObject.put("PartLocations", PLBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
		
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			 userDetails				= null;
			 pageNumber					= null;
			 term						= null;
		}
	}
	
	@Override
	public List<ClothInvoiceDto> getClothInvoiceList(String term, Integer pageNumber, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]> 					resultList 				= null; 
		List<ClothInvoiceDto> 			batteryInvoiceList 		= null;
		ClothInvoiceDto 				batteryInvoiceDto		= null;

		try {
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			typedQuery = entityManager.createQuery("SELECT BI.clothInvoiceId, BI.clothInvoiceNumber, BI.vendorId, V.vendorName, BI.wareHouseLocation,"
					+ " PL.partlocation_name, BI.invoiceNumber, BI.invoiceDate, BI.invoiceAmount"
					+ " FROM ClothInvoice AS BI"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BI.wareHouseLocation"
					+ " LEFT JOIN Vendor AS V ON V.vendorId = BI.vendorId "
					+ " WHERE lower(BI.invoiceNumber) like '%"+term+"%' AND BI.companyId ="+companyId+" AND BI.markForDelete = 0 ORDER BY BI.clothInvoiceId DESC", Object[].class);

			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				batteryInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					batteryInvoiceDto = new ClothInvoiceDto();
					
					batteryInvoiceDto.setClothInvoiceId((Long) result[0]);
					batteryInvoiceDto.setClothInvoiceNumber((Long) result[1]);
					batteryInvoiceDto.setVendorId((Integer) result[2]);
					batteryInvoiceDto.setVendorName((String) result[3]);
					batteryInvoiceDto.setWareHouseLocation((Integer) result[4]);
					batteryInvoiceDto.setClothLocation((String) result[5]);
					batteryInvoiceDto.setInvoiceNumber((String) result[6]);
					batteryInvoiceDto.setInvoiceDate((Timestamp) result[7]);
					batteryInvoiceDto.setInvoiceAmount((Double) result[8]);
				

					if(batteryInvoiceDto.getInvoiceDate() != null) {
						batteryInvoiceDto.setInvoiceDateStr(DateTimeUtility.getDateFromTimeStamp(batteryInvoiceDto.getInvoiceDate(), DateTimeUtility.DD_MM_YYYY));
					}
					if(batteryInvoiceDto.getVendorName() == null) {
						batteryInvoiceDto.setVendorName("--");
					}
					
					batteryInvoiceList.add(batteryInvoiceDto);
				}
			}
			}
			return batteryInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			batteryInvoiceList 		= null;
			batteryInvoiceDto		= null;
		}
	}
	
	@Override
	public Page<ClothInvoice> getDeployment_Page_ClothInvoice(String term, Integer pageNumber, Integer companyId)
			throws Exception {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "clothInvoiceId");
		return clothInventoryRepository.getDeployment_Page_ClothInvoice(term, companyId, pageable);
	}
	
	@Override
	public ValueObject searchClothInvoiceByNumber(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails		= null;
		Long						invoiceId		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			invoiceId	=	clothInventoryRepository.searchClothInvoiceByNumber(valueObject.getLong("clothInvoiceNumber", 0), userDetails.getCompany_id());
			if(invoiceId != null)
				valueObject.put("invoiceId", invoiceId);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getVehicleCLothAsignmentDetails(ValueObject valueObject) throws Exception {
		
		List<VehicleClothInventoryDetailsDto>		clothInventoryDetailsList			= null;
		VehicleDto									vehicle								= null;
		double										maxAllowedQty						= 0.0;
		try {
			
			clothInventoryDetailsList	=	vehicleClothInventoryDetailsService.getVehicleClothInventoryDetailsList(valueObject.getInt("vid"));
			vehicle						=	VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(valueObject.getInt("vid")));
			
			if(clothInventoryDetailsList != null) {
				for(VehicleClothInventoryDetailsDto finalList : clothInventoryDetailsList ) {
					
					VehicleClothMaxAllowedSetting val = vehicleClothMaxAllowedSettingRepository.validateVehicleClothMaxAllowed(finalList.getClothTypesId(), valueObject.getInt("vid"));
					if(val != null) {
						finalList.setMaxAllowedQuantity(val.getMaxAllowedQuantity());
					} else {
						finalList.setMaxAllowedQuantity(maxAllowedQty);
					}
				}
			}
			
			valueObject.put("clothInventoryDetailsList", clothInventoryDetailsList);
			valueObject.put("vehicle", vehicle);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			clothInventoryDetailsList			= null;
			vehicle								= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject saveVehicleClothInventoryDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails							= null;
		VehicleClothInventoryDetails			vehicleClothInventoryDetails		= null;
		VehicleClothInventoryHistory			vehicleClothInventoryHistory		= null;	
		VehicleClothInventoryHistoryDto			vehicleClothInventoryPreHistory		= null;	
		VehicleClothInventoryDetails			validate							= null;
		ClothInventoryStockTypeDetailsDto		detailsDto							= null;
		long									clothTypeId							= 0;
		int										locationId							= 0;
		double									validateQty							= 0.0;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("userDetails", userDetails);
			clothTypeId   = valueObject.getLong("clothTypes");      
			locationId    = valueObject.getInt("locationId"); 
			
			VehicleClothMaxAllowedSetting val = vehicleClothMaxAllowedSettingRepository.validateVehicleClothMaxAllowed(valueObject.getLong("clothTypes"), valueObject.getInt("vid"));
			
			if(valueObject.getInt("vid") > 0 && val != null) {
				
				if(valueObject.getDouble("quantity") > val.getMaxAllowedQuantity()) {
					valueObject.put("QuantityExceeded", true);
					return valueObject;
				}
				
				detailsDto = clothInventoryStockTypeDetailsService.getClothLocationQuantity(clothTypeId, locationId, userDetails.getCompany_id());
				
				if(valueObject.getShort("typeOfCloth") == ClothInvoiceStockType.STOCK_TYPE_NEW) {
					validateQty = detailsDto.getNewStockQuantity();
				} else {
					validateQty = detailsDto.getUsedStockQuantity();
				}	
				
				if(valueObject.getDouble("quantity") > validateQty) {
					valueObject.put("QuantityExceeded", true);
					return valueObject;
				} 
				
				validate	= vehicleClothInventoryDetailsService.validateVehicleClothInventoryDetails(
						valueObject.getLong("clothTypes",0), valueObject.getInt("vid"), valueObject.getInt("locationId"));
				vehicleClothInventoryDetails	= inventoryBL.getVehicleClothInventoryDetailsDto(valueObject, validate);
				
				//checking.. vehicle already have that particular upholstery or not 
				vehicleClothInventoryPreHistory	= getPreVehicleClothInventoryHistory(valueObject.getInt("vid"),valueObject.getShort("clothTypes"),ClothInvoiceStockType.CLOTH_ASIGN_TYPE_ASIGN,userDetails.getCompany_id());
				valueObject.put("vehicleClothInventoryPreHistory", vehicleClothInventoryPreHistory);
				
				vehicleClothInventoryHistory	= inventoryBL.getVehicleClothInventoryHistory(valueObject);
				
				vehicleClothInventoryDetailsService.saveVehicleClothInventoryDetails(vehicleClothInventoryDetails);
				clothInventoryStockTypeDetailsService.updateLocationStockDetails(valueObject);
				vehicleClothInventoryHistoryService.saveVehicleClothInventoryHistory(vehicleClothInventoryHistory);
				valueObject.put("saved", true);
				
			}else {
				valueObject.put("alreadyExist", true);
				return valueObject;
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails						= null;
			vehicleClothInventoryDetails	= null;
			
		}
	}
	
	
	@Transactional
	public VehicleClothInventoryHistoryDto getPreVehicleClothInventoryHistory(Integer vid, short clothTypeId , short assignTypeId, Integer companyId) throws Exception {
		try {
			Query queryt = entityManager.createQuery(
					" Select VI.vehicleClothInventoryHistoryId, VI.asignType, VI.createdOn, VI.quantity FROM VehicleClothInventoryHistory AS VI "
							+ " WHERE  VI.vid = "+vid+" AND VI.asignType = "+assignTypeId+" AND VI.clothTypesId = "+clothTypeId+" "
							+ " AND VI.companyId = "+ companyId +" AND VI.markForDelete = 0 ORDER BY VI.vehicleClothInventoryHistoryId desc ");
							queryt.setMaxResults(1);
			
			Object[] result = null;
			VehicleClothInventoryHistoryDto select  = new VehicleClothInventoryHistoryDto();
			try {
				result = (Object[]) queryt.getSingleResult();
				
			} catch (NoResultException nre) {
				
				return null;
			}
			
			if (result != null) {
				
				select.setVehicleClothInventoryHistoryId((Long)result[0]);
				select.setAsignType((short)result[1]);
				select.setCreatedOn((Date)result[2]);
				select.setQuantity((Double)result[3]);
			}

			return select;
			} catch (Exception e) {
				LOGGER.error("Excep", e);
				throw e;
			}
		}

	
	
	@Override
	public ValueObject getVehicleClothInventoryDetails(ValueObject valueObject) throws Exception {
		VehicleClothInventoryDetailsDto			vehicleClothInventoryDetails			= null;
		try {
			
			vehicleClothInventoryDetails	=	vehicleClothInventoryDetailsService.getVehicleClothInventoryDetails(valueObject.getLong("vehicleClothInventoryDetailsId"));
			valueObject.put("vehicleClothInventoryDetails", vehicleClothInventoryDetails);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleClothInventoryDetails	 = null;
		}
	}
	
	@Override
	public ValueObject removeClothInventoryDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails							= null;
		VehicleClothInventoryHistory			vehicleClothInventoryHistory		= null;
		ClothInventoryStockTypeDetails			clothStockTypeDetails				= null;
		int										oldLocation							= 0;
		int										newLocation							= 0;
		VehicleClothInventoryHistoryDto			vehicleClothInventoryPreHistory		= null;	
		VehicleClothInventoryDetailsDto			vehicleClothInventoryDetails		= null;
		double									validateQty							= 0.0;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("userDetails", userDetails);
			
			vehicleClothInventoryDetails = vehicleClothInventoryDetailsService.getVehicleClothInventoryDetails(valueObject.getLong("vehicleClothInventoryDetailsId"));
			validateQty					 = vehicleClothInventoryDetails.getQuantity();
			
			if(valueObject.getDouble("removeQuantity") > validateQty) {
				valueObject.put("QuantityExceeded", true);
				return valueObject;
			}
				
			VehicleClothInventoryDetailsDto		detailsDto	= new VehicleClothInventoryDetailsDto();
			detailsDto.setVid(valueObject.getInt("vid"));
			detailsDto.setClothTypesId(valueObject.getLong("clothTypesId"));
			detailsDto.setLocationId(oldLocation);
			detailsDto.setQuantity(valueObject.getDouble("removeQuantity"));
			detailsDto.setVehicleClothInventoryDetailsId(valueObject.getLong("vehicleClothInventoryDetailsId"));
			detailsDto.setLastModifiedById(userDetails.getId());
			detailsDto.setLastModifiedOn(DateTimeUtility.getCurrentDate());
			
			vehicleClothInventoryPreHistory	= getPreVehicleClothInventoryHistory(valueObject.getInt("vid"),valueObject.getShort("clothTypesId"),ClothInvoiceStockType.CLOTH_ASIGN_TYPE_REMOVE,userDetails.getCompany_id());
			valueObject.put("vehicleClothInventoryPreHistory", vehicleClothInventoryPreHistory);
			vehicleClothInventoryHistory	=	inventoryBL.getRemoveVehicleClothInventoryHistory(valueObject);
			
			clothStockTypeDetails = clothInventoryStockTypeDetailsRepository.validateClothInventoryStockTypeDetails(
			valueObject.getLong("clothTypesId"),valueObject.getInt("removelocationId"));
			
			if (clothStockTypeDetails != null) {
				
				oldLocation = valueObject.getInt("locationId");
				newLocation = valueObject.getInt("removelocationId");
				
				vehicleClothInventoryDetailsService.removeClothDetailsFromVehicle(detailsDto);
				vehicleClothInventoryHistoryService.saveVehicleClothInventoryHistory(vehicleClothInventoryHistory);
			
				if(oldLocation == newLocation) {
					clothInventoryStockTypeDetailsService.removeVehicleClothToStockDetails(valueObject);
				} else {
					clothInventoryStockTypeDetailsService.removeVehicleUpdateStockDetailsOfUsedStckQty(valueObject);
					clothInventoryStockTypeDetailsService.removeVehicleUpdateStockDetailsOfInService(valueObject);
				}
				
			} else {
				
				vehicleClothInventoryDetailsService.removeClothDetailsFromVehicle(detailsDto);
				vehicleClothInventoryHistoryService.saveVehicleClothInventoryHistory(vehicleClothInventoryHistory);
				
				clothStockTypeDetails = inventoryBL.saveClothInventoryStockTypeDetails(valueObject);
				clothInventoryStockTypeDetailsRepository.save(clothStockTypeDetails);
				
				clothInventoryStockTypeDetailsService.removeVehicleUpdateStockDetailsOfInService(valueObject);
			}
				
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails							= null;
			vehicleClothInventoryHistory		= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject saveSentClothToLaundry(ValueObject valueObject) throws Exception {
		
		SentLaundryInvoiceSequenceCounter		sequenceCounter						= null;
		ValueObject								outObject							= null;
		CustomUserDetails						userDetails							= null;
		ArrayList<ValueObject> 					dataArrayObjColl 					= null;
		ArrayList<ValueObject> 					validateArrayObjColl 				= null;
		List<SentLaundryClothDetails>			clothInventoryDetailsList			= null;
		List<InventoryDamageAndLostHistory>		inventoryDamageAndLostHistoryList	= null;
		VendorFixedLaundryRateDto				detailsDto							= null;
		long									clothTypeId							= 0;
		int										locationId							= 0;
		int										vendorId							= 0;
		double									validateQty							= 0.0;
		try {
			outObject 		= new ValueObject();
			locationId		= valueObject.getInt("warehouselocation");
			vendorId		= valueObject.getInt("vendorId");
			
			validateArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("clothDetails");
			if(validateArrayObjColl != null && !validateArrayObjColl.isEmpty()) {
				
				for (ValueObject obj : validateArrayObjColl) {
					
					clothTypeId = obj.getLong("clothTypes");
					detailsDto = vendorFixedLaundryRateService.getVendorRateAndLocationQuantity(locationId, clothTypeId, vendorId);
					validateQty = detailsDto.getUsedStockQuantity();
					
					if(obj.getDouble("quantity") > validateQty) {
						valueObject.put("QuantityExceeded", true);
						return valueObject;
					}
				}
				
			}
			
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			sequenceCounter	= laundrySequenceCounter.findNextInvoiceNumber(userDetails.getCompany_id());
			
			if(sequenceCounter == null) {
				outObject.put("saveMessage", "Sequence not found please contact to system Administrator !");
				return outObject;
			}
			
			UpholsterySendLaundryInvoice	invoice	= inventoryBL.getUpholsterySendLaundryInvoice(valueObject);
			invoice.setLaundryInvoiceNumber(sequenceCounter.getNextVal());
			upholsterySendLaundryInvoiceRepository.save(invoice);
			
			valueObject.put("invoiceId", invoice.getLaundryInvoiceId());
			
			
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("clothDetails");
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				clothInventoryDetailsList			= new ArrayList<>();
				inventoryDamageAndLostHistoryList	= new ArrayList<>();
				for (ValueObject object : dataArrayObjColl) {
					clothInventoryDetailsList.add(inventoryBL.getSentLaundryClothDetails(object, valueObject));
					inventoryDamageAndLostHistoryList.add(inventoryBL.saveInventDmgAndLosHistWhileSendingLaundry(object, valueObject));
				}
				
				sentLaundryClothDetailsRepository.saveAll(clothInventoryDetailsList);
				inventoryDamageAndLostHistoryRepository.saveAll(inventoryDamageAndLostHistoryList);
				
				if(!clothInventoryDetailsList.isEmpty()) {
					for(SentLaundryClothDetails	clothInventoryDetails : clothInventoryDetailsList) {
						
						clothInventoryStockTypeDetailsService.updateLocationInWashingStockDetails(invoice.getWareHouseLocationId(), clothInventoryDetails.getClothTypesId(), clothInventoryDetails.getQuantity());
					}
				}
			}
			
			if(invoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
				  PendingVendorPayment	payment	=	PendingVendorPaymentBL.createPendingVendorPaymentDTOForLI(invoice);
				  pendingVendorPaymentService.savePendingVendorPayment(payment);
			}
			
			if(valueObject.getBoolean("allowBankPaymentDetails",false) && (PaymentTypeConstant.getPaymentTypeIdList().contains(invoice.getPaymentTypeId()) || invoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)) {
				ValueObject bankPaymentValueObject=JsonConvertor.toValueObjectFormSimpleJsonString(valueObject.getString("bankPaymentDetails"));
					bankPaymentValueObject.put("bankPaymentTypeId",invoice.getPaymentTypeId());
					bankPaymentValueObject.put("userId",userDetails.getId());
					bankPaymentValueObject.put("companyId",userDetails.getCompany_id());
					bankPaymentValueObject.put("moduleId",invoice.getLaundryInvoiceId());
					bankPaymentValueObject.put("moduleNo", invoice.getLaundryInvoiceNumber());
					bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.CLOTH_LAUNDRY);
					bankPaymentValueObject.put("amount",invoice.getTotalCost());
					Vendor vendor = vendorService.getVendor(invoice.getVendorId());
					
					bankPaymentValueObject.put("remark", "Laundry Invoice LI-"+invoice.getLaundryInvoiceNumber() + " Vendor : "+vendor.getVendorName()+" Payment Type : "+PaymentTypeConstant.getPaymentTypeName(invoice.getPaymentTypeId()));
					
					//bankPaymentValueObject.put("paidDate", new Date());
					if(PaymentTypeConstant.PAYMENT_TYPE_CASH == invoice.getPaymentTypeId())
						cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
					else
						bankPaymentService.addBankPaymentDetailsFromValueObject(bankPaymentValueObject);
				}
			
			
			return valueObject;
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			throw e;
		}finally {
			outObject			= null;
			sequenceCounter		= null;
			userDetails			= null;
		}
	}
	
	@Override
	public ValueObject getClothLaundryDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails	   userDetails				= null;
		Integer				   pageNumber				= null;
		try {

			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber			= valueObject.getInt("pageNumber",0);
			Page<UpholsterySendLaundryInvoice> page = getDeployment_Page_UpholsterySendLaundryInvoice(pageNumber, userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());

			List<UpholsterySendLaundryInvoiceDto> pageList = getUpholsterySendLaundryInvoiceDtoList(pageNumber, userDetails.getCompany_id());

			valueObject.put("LaundryInvoice", pageList);
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
	public Page<UpholsterySendLaundryInvoice> getDeployment_Page_UpholsterySendLaundryInvoice(Integer pageNumber, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "laundryInvoiceId");
		return upholsterySendLaundryInvoiceRepository.getDeployment_Page_UpholsterySendLaundryInvoice(companyId, pageable);
	}

	@Override
	public List<UpholsterySendLaundryInvoiceDto> getUpholsterySendLaundryInvoiceDtoList(Integer pageNumber,
			Integer companyId) throws Exception {

		TypedQuery<Object[]> 							typedQuery 				= null;
		List<Object[]> 									resultList 				= null; 
		List<UpholsterySendLaundryInvoiceDto> 			clothInvoiceList 		= null;
		UpholsterySendLaundryInvoiceDto 				clothInvoiceDto			= null;
		double											total					= 0.0;
		double											reaminingQty			= 0.0;

		try {

			typedQuery = entityManager.createQuery("SELECT BI.laundryInvoiceId, BI.laundryInvoiceNumber, BI.vendorId, V.vendorName, BI.wareHouseLocationId,"
					+ " PL.partlocation_name, BI.totalQuantity, BI.receivedQuantity, U.firstName , U.lastName, BI.losedQuantity, BI.damagedQuantity,"
					+ " BI.totalCost, BI.sentDate, BI.expectedReceiveDate"
					+ " FROM UpholsterySendLaundryInvoice AS BI"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BI.wareHouseLocationId"
					+ " LEFT JOIN Vendor AS V ON V.vendorId = BI.vendorId "
					+ " INNER JOIN User U ON U.id = BI.createdById "
					+ " WHERE BI.companyId ="+companyId+" AND BI.markForDelete = 0 ORDER BY BI.laundryInvoiceId DESC", Object[].class);

			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				clothInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					clothInvoiceDto = new UpholsterySendLaundryInvoiceDto();
					
					clothInvoiceDto.setLaundryInvoiceId((Long) result[0]);
					clothInvoiceDto.setLaundryInvoiceNumber((Long) result[1]);
					clothInvoiceDto.setVendorId((Integer) result[2]);
					clothInvoiceDto.setVendorName((String) result[3]);
					clothInvoiceDto.setWareHouseLocationId((Integer) result[4]);
					clothInvoiceDto.setLocationName((String) result[5]);
					if(result[6] != null)
					clothInvoiceDto.setTotalQuantity(Double.parseDouble(toFixedTwo.format(result[6])));
					if(result[7] != null)
					clothInvoiceDto.setReceivedQuantity(Double.parseDouble(toFixedTwo.format(result[7])));
					
					clothInvoiceDto.setCreatedBy((String) result[8]+"_"+(String) result[9]);
					if(result[10] != null)
					clothInvoiceDto.setLosedQuantity(Double.parseDouble(toFixedTwo.format(result[10])));
					if(result[11] != null)
					clothInvoiceDto.setDamagedQuantity(Double.parseDouble(toFixedTwo.format(result[11])));
					if(result[12] != null)
					clothInvoiceDto.setTotalCost(Double.parseDouble(toFixedTwo.format(result[12])));
					clothInvoiceDto.setSentDate((Date) result[13]);
					clothInvoiceDto.setExpectedReceiveDate((Date) result[14]);
					
					
					if(clothInvoiceDto.getVendorName() == null) {
						clothInvoiceDto.setVendorName("--");
					}
					if(clothInvoiceDto.getSentDate() != null) {
						clothInvoiceDto.setSentDateStr(format.format(clothInvoiceDto.getSentDate()));
					}else {
						clothInvoiceDto.setSentDateStr("--");
					}
					if(clothInvoiceDto.getExpectedReceiveDate() != null) {
						clothInvoiceDto.setExpectedReceiveDateStr(format.format(clothInvoiceDto.getExpectedReceiveDate()));
					}else {
						clothInvoiceDto.setExpectedReceiveDateStr("--");
					}
					
					total = clothInvoiceDto.getReceivedQuantity() + clothInvoiceDto.getDamagedQuantity() + clothInvoiceDto.getLosedQuantity();
					reaminingQty = clothInvoiceDto.getTotalQuantity() - total;
					clothInvoiceDto.setRemainingQuantity(Double.parseDouble(toFixedTwo.format(reaminingQty)));
					
					clothInvoiceList.add(clothInvoiceDto);
				}
			}

			return clothInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			clothInvoiceList 		= null;
			clothInvoiceDto			= null;
		}
	}
	
	@Override
	public ValueObject getLaundryInvoiceDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails							= null;
		UpholsterySendLaundryInvoiceDto			upholsterySendLaundryInvoiceDto		= null;
		List<SentLaundryClothDetailsDto>		sentLaundryClothDetailsList			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			upholsterySendLaundryInvoiceDto	=	upholsterySendLaundryInvoiceService.getLaundryInvoiceDetails(valueObject.getLong("invoiceId", 0), userDetails.getCompany_id());
			
			sentLaundryClothDetailsList	=	sentLaundryClothDetailsService.getSentLaundryClothDetailsDto(valueObject.getLong("invoiceId", 0), userDetails.getCompany_id());
			
			valueObject.put("upholsterySendLaundryInvoiceDto", upholsterySendLaundryInvoiceDto);
			valueObject.put("sentLaundryClothDetailsList", sentLaundryClothDetailsList);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject receiveClothFromLaundry(ValueObject valueObject) throws Exception {
		LaundryClothReceiveHistory			clothReceiveHistory		= null;
		UpholsterySendLaundryInvoiceDto		laundryInvoiceStatus	= null;
		CustomUserDetails					userDetails				= null;
		UpholsterySendLaundryInvoice		laundryInvoice			= null;
		double								totalQty				= 0.0;
		double								validateQty				= 0.0;
		
		
		try {
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("userDetails", userDetails);
			
			laundryInvoice = upholsterySendLaundryInvoiceRepository.getLaundryInvoiceDetails(valueObject.getLong("laundryInvoiceId"), userDetails.getCompany_id());
			totalQty 	   = laundryInvoice.getReceivedQuantity() + laundryInvoice.getDamagedQuantity() + laundryInvoice.getLosedQuantity();
			validateQty    = laundryInvoice.getTotalQuantity() - totalQty;
			
			if(valueObject.getDouble("receiveQuantity") > validateQty) {
				valueObject.put("QuantityExceeded", true);
				return valueObject;
			}
			
			clothReceiveHistory		=	inventoryBL.getLaundryClothReceiveHistory(valueObject);
			laundryClothReceiveHistoryService.saveLaundryClothReceiveHistory(clothReceiveHistory);
			
			clothInventoryStockTypeDetailsService.updateLocationStockDetailsToReceive(valueObject);
			upholsterySendLaundryInvoiceService.updateInvoiceDetailsToReceive(valueObject.getLong("laundryInvoiceId"), valueObject.getDouble("receiveQuantity"));
			sentLaundryClothDetailsService.updateSentLaundryDetailsToReceive(valueObject);
			
			laundryInvoiceStatus = upholsterySendLaundryInvoiceService.getLaundryInvoiceStatus(valueObject.getLong("laundryInvoiceId"), userDetails.getCompany_id());
			if(laundryInvoiceStatus.getFinalReceivedQty() == 0.0)
			upholsterySendLaundryInvoiceService.updateLaundryInvoiceSatus(valueObject.getLong("laundryInvoiceId"), userDetails.getCompany_id());
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			clothReceiveHistory		= null;
			userDetails				= null;
		}
	}
	
	@Override
	public ValueObject getReceivedClothDetails(ValueObject valueObject) throws Exception {
		List<LaundryClothReceiveHistoryDto>		historyList		= null;
		try {
			historyList		=	laundryClothReceiveHistoryService.getReceivedClothDetailsList(valueObject.getLong("laundryInvoiceId"), valueObject.getLong("laundryClothDetailsId"));
			
			valueObject.put("historyList", historyList);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getUpholsteryPurchaseInvoiceReport(ValueObject valueObject) throws Exception {
		String										dateRange				= null;
		int											vendorId				= 0;
		long										clothTypeId				= 0;
		CustomUserDetails							userDetails				= null;
		List<ClothInventoryDetailsDto> 				ClothInventory			= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			clothTypeId		= valueObject.getLong("clothTypes", 0);
			vendorId		= valueObject.getInt("clothVendor", 0);
			dateRange		= valueObject.getString("dateRange");
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				String Vendor_Name = "", Cloth_Type = "", Date = "";
				
				
				if(clothTypeId > 0)
				{
					Cloth_Type = " AND CID.clothTypesId = "+ clothTypeId +" ";
				}
				
				if(vendorId > 0 )
				{
					Vendor_Name = " AND CI.vendorId = "+ vendorId +" ";
				}
				
				Date =  " AND CI.invoiceDate between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;
				String query = " " + Vendor_Name + " " + Cloth_Type + " " + Date + " ";
				
				
				ClothInventory = upholsteryReportDaoImpl.Upholstery_Purchase_Invoice_Report_List(query, userDetails.getCompany_id());

			}
			valueObject.put("ClothInventory", ClothInventory);
			
			if(vendorId > 0 ) {
				if(ClothInventory != null)
				valueObject.put("VendorName", ClothInventory.get(0).getVendorName());
			} else {
			valueObject.put("VendorName", "All");
			}
			if(clothTypeId > 0  ) {
				if(ClothInventory != null)
				valueObject.put("ClothType", ClothInventory.get(0).getClothTypesName());
			} else {
				valueObject.put("ClothType", "All");
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			ClothInventory		= null;
			userDetails			= null;
			dateRange			= null;
		}
	}
	
	//Laundry Upholstery receive report start services
	@Override
	public ValueObject getLaundryUpholsteryReceiveReport(ValueObject valueObject) throws Exception {
		
		String												dateRange				= null;
		Integer												vendorId				= 0;
		CustomUserDetails									userDetails				= null;
		List<LaundryClothReceiveHistoryDto> 				Laundry			        = null;		
		ValueObject						                  tableConfig				= null;
		
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dateRange		= valueObject.getString("laundryDateRange");
			vendorId		= valueObject.getInt("laundryVendor", 0);
			
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				String vendor_Name = "", Date = "";
				
				if(vendorId != 0  )
				{					
					vendor_Name = " AND U.vendorId = "+ vendorId +" ";
				}				
				
				Date =  " AND LCR.receiveDate between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ; 
				String query = " " + vendor_Name + "  " + Date + " ";
				
				
				tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.LAUNDRY_UPHOLSTERY_RECEIVE_TABLE_DATA_FILE_PATH);

				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				
				Laundry = upholsteryReportDaoImpl.getLaundry_Upholstery_Receive_Report(query, userDetails.getCompany_id());

			}
			valueObject.put("Laundry", Laundry);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company",
				userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
			Laundry	= null;
			userDetails			= null;
			dateRange			= null;
		}
	}
	//Laundry Upholstery receive report stop services
	
	//Upholstery Stock Report Start Services
	@Override
	public ValueObject getUpholsteryStockReport(ValueObject valueObject) throws Exception {		
		
		Integer												upholsteryId			= 0;
		Integer												warehouseLocationId		= 0;
		CustomUserDetails									userDetails				= null;
		List<ClothInventoryStockTypeDetailsDto> 				stock			    = null;		
		ValueObject						                  tableConfig				= null;
		
		try {
				userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
				upholsteryId					= valueObject.getInt("clothTypes", 0);
				warehouseLocationId				= valueObject.getInt("warehouselocation", 0);
			
				String upholstery_Name = "", warehouse_Location_Name = "";
				
				if(upholsteryId != 0  )
				{					
					upholstery_Name = " AND CT.clothTypesId = "+ upholsteryId +" ";
				}				
				if(warehouseLocationId != 0  )
				{					
					warehouse_Location_Name = " AND CS.wareHouseLocationId = "+ warehouseLocationId +" ";
				}				
				
				String query = " " + upholstery_Name + "  "+warehouse_Location_Name+" ";				
				
				tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.UPHOLSTERY_STOCK_TABLE_DATA_FILE_PATH);

				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);		
				stock = upholsteryReportDaoImpl.getUpholstery_Stock_Report(query, userDetails.getCompany_id());
				
				valueObject.put("stock", stock);
				valueObject.put("tableConfig", tableConfig.getHtData());
				valueObject.put("company",
				userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
			stock	= null;
			userDetails			= null;			
		}
	}
	//Upholstery Stock Report Stop Services
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject saveVehicleClothMaxAllowed(ValueObject valueObject) throws Exception {
		
		CustomUserDetails						userDetails							= null;
		ArrayList<ValueObject> 					dataArrayObjColl 					= null;
		List<VehicleClothMaxAllowedSetting>		VehicleClothMaxAllowedSettingList	= null;
		VehicleClothMaxAllowedSetting			typeDetails 						= null;
		List<VehicleClothMaxAllowedSettingDto> 	VehicleClothMaxAllowedList			= null;
		int 									duplicateCount						= 0;
		int 									actualCount							= 0;
		try {
			
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("VehicleClothMaxAllowed");
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				VehicleClothMaxAllowedSettingList	= new ArrayList<>();
				for (ValueObject object : dataArrayObjColl) {
					VehicleClothMaxAllowedSettingList.add(inventoryBL.getVehicleClothMaxAllowed(object, valueObject));
				}
			}	
			
			if(VehicleClothMaxAllowedSettingList != null && !VehicleClothMaxAllowedSettingList.isEmpty()) {
				for(VehicleClothMaxAllowedSetting	MaxAllowedDetails : VehicleClothMaxAllowedSettingList) {
					
					VehicleClothMaxAllowedSetting validate = vehicleClothMaxAllowedSettingRepository.validateVehicleClothMaxAllowed(MaxAllowedDetails.getClothTypesId(), MaxAllowedDetails.getVid());
					
					if(validate == null) {
					typeDetails	= new VehicleClothMaxAllowedSetting();
					
					typeDetails.setVid(MaxAllowedDetails.getVid());
					typeDetails.setClothTypesId(MaxAllowedDetails.getClothTypesId());
					typeDetails.setMaxAllowedQuantity(MaxAllowedDetails.getMaxAllowedQuantity());
					typeDetails.setRemark(MaxAllowedDetails.getRemark());
					typeDetails.setCompanyId(userDetails.getCompany_id());
					typeDetails.setCreatedById(userDetails.getId());
					typeDetails.setLastModifiedById(userDetails.getId());
					typeDetails.setCreationDate(new Date());
					typeDetails.setLastUpdatedDate(new Date());
					typeDetails.setMarkForDelete(false);
					
					vehicleClothMaxAllowedSettingRepository.save(typeDetails);
					actualCount++;
					valueObject.put("saved", true);
					
					} else {
						valueObject.put("alreadyExist", true);
						duplicateCount++;
						//return valueObject;
					}
				}
			}	
			VehicleClothMaxAllowedList	=	getVehicleClothMaxAllowedList(userDetails.getCompany_id());
			if(VehicleClothMaxAllowedList != null && !VehicleClothMaxAllowedList.isEmpty())
			valueObject.put("VehicleClothMaxAllowed", VehicleClothMaxAllowedList);
			valueObject.put("duplicateCount", duplicateCount);
			valueObject.put("actualCount", actualCount);
			
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;
			typeDetails 		= null;
		}
	}
	
	@Override
	public ValueObject getVehicleClothMaxAllowed(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails					= null;
		List<VehicleClothMaxAllowedSettingDto> 	VehicleClothMaxAllowedList	= null;
		Integer				   					pageNumber					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber			= valueObject.getInt("pageNumber",0);

			Page<VehicleClothMaxAllowedSetting> page = getDeployment_Page_VehicleClothMaxAllowedSetting(pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());
			valueObject.put("SelectPage", pageNumber);
			
			VehicleClothMaxAllowedList	=	viewVehicleClothMaxAllowedList(pageNumber, userDetails.getCompany_id());
			if(VehicleClothMaxAllowedList != null && !VehicleClothMaxAllowedList.isEmpty())
			valueObject.put("VehicleClothMaxAllowed", VehicleClothMaxAllowedList);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails					= null;
			VehicleClothMaxAllowedList	= null;
		}
	}
	
	@Override
	public List<VehicleClothMaxAllowedSettingDto> viewVehicleClothMaxAllowedList(Integer pageNumber, Integer companyId) throws Exception {

		TypedQuery<Object[]> 							typedQuery 				= null;
		List<Object[]> 									resultList 				= null; 
		List<VehicleClothMaxAllowedSettingDto> 			maxAllowedList 			= null;
		VehicleClothMaxAllowedSettingDto 				maxAllowedDto			= null;

		try {

			typedQuery = entityManager.createQuery(
				"SELECT VM.maxAllowedSettingId, V.vehicle_registration, C.clothTypeName, VM.maxAllowedQuantity, VM.remark "
					+ " FROM VehicleClothMaxAllowedSetting as VM "
					+ " INNER JOIN Vehicle V ON V.vid = VM.vid "
					+ " INNER JOIN ClothTypes C ON C.clothTypesId = VM.clothTypesId "
					+ " Where VM.companyId = "+companyId+" AND VM.markForDelete = 0 ORDER BY VM.maxAllowedSettingId DESC ", Object[].class);
			
			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				maxAllowedList = new ArrayList<>();

				for (Object[] result : resultList) {
					maxAllowedDto = new VehicleClothMaxAllowedSettingDto();
					
					maxAllowedDto.setMaxAllowedSettingId((long) result[0]);
					maxAllowedDto.setVehicle_registration((String) result[1]);
					maxAllowedDto.setClothTypeName((String) result[2]);
					maxAllowedDto.setMaxAllowedQuantity((Double) result[3]);
					maxAllowedDto.setRemark((String) result[4]);
					
					maxAllowedList.add(maxAllowedDto);
				}
			}

			return maxAllowedList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			maxAllowedList 			= null;
			maxAllowedDto			= null;
		}
	}
	
	@Override
	public List<VehicleClothMaxAllowedSettingDto> getVehicleClothMaxAllowedList(Integer companyId) throws Exception {

		TypedQuery<Object[]> 							typedQuery 				= null;
		List<Object[]> 									resultList 				= null; 
		List<VehicleClothMaxAllowedSettingDto> 			maxAllowedList 			= null;
		VehicleClothMaxAllowedSettingDto 				maxAllowedDto			= null;

		try {

			typedQuery = entityManager.createQuery(
				"SELECT VM.maxAllowedSettingId, V.vehicle_registration, C.clothTypeName, VM.maxAllowedQuantity, VM.remark "
					+ " FROM VehicleClothMaxAllowedSetting as VM "
					+ " INNER JOIN Vehicle V ON V.vid = VM.vid "
					+ " INNER JOIN ClothTypes C ON C.clothTypesId = VM.clothTypesId "
					+ " Where VM.companyId = "+companyId+" AND VM.markForDelete = 0 ", Object[].class);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				maxAllowedList = new ArrayList<>();

				for (Object[] result : resultList) {
					maxAllowedDto = new VehicleClothMaxAllowedSettingDto();
					
					maxAllowedDto.setMaxAllowedSettingId((long) result[0]);
					maxAllowedDto.setVehicle_registration((String) result[1]);
					maxAllowedDto.setClothTypeName((String) result[2]);
					maxAllowedDto.setMaxAllowedQuantity((Double) result[3]);
					maxAllowedDto.setRemark((String) result[4]);
					
					maxAllowedList.add(maxAllowedDto);
				}
			}

			return maxAllowedList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			maxAllowedList 			= null;
			maxAllowedDto			= null;
		}
	}
	
	@Override
	public ValueObject getVehicleClothMaxAllowedById(ValueObject valueObject) throws Exception {
		try {
			VehicleClothMaxAllowedSettingDto maxAllowedSettingId  = findVehicleClothMaxAllowedById(valueObject.getLong("maxAllowedSettingId"));
			valueObject.put("maxAllowedSettingId", maxAllowedSettingId);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public VehicleClothMaxAllowedSettingDto findVehicleClothMaxAllowedById(Long maxAllowedSettingId) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT VM.maxAllowedSettingId, V.vehicle_registration, C.clothTypeName, VM.maxAllowedQuantity, "
						+ "	VM.remark, VM.vid, VM.clothTypesId "
						+ " FROM VehicleClothMaxAllowedSetting as VM "
						+ " INNER JOIN Vehicle V ON V.vid = VM.vid "
						+ " INNER JOIN ClothTypes C ON C.clothTypesId = VM.clothTypesId "
						+ " Where VM.maxAllowedSettingId = "+maxAllowedSettingId+" AND VM.markForDelete = 0 ");
		
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		VehicleClothMaxAllowedSettingDto select;
		if (vehicle != null) {
			select = new VehicleClothMaxAllowedSettingDto();
			
			select.setMaxAllowedSettingId((long) vehicle[0]);
			select.setVehicle_registration((String) vehicle[1]);
			select.setClothTypeName((String) vehicle[2]);
			select.setMaxAllowedQuantity((Double) vehicle[3]);
			select.setRemark((String) vehicle[4]);
			select.setVid((int) vehicle[5]);
			select.setClothTypesId((long) vehicle[6]);

		} else {
			return null;
		}
		return select;
	}
	
	@Override
	@Transactional
	public ValueObject updateVehicleClothMaxAllowedById(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails					= null;
		VehicleClothMaxAllowedSetting 			typeDetails					= null;
		List<VehicleClothMaxAllowedSettingDto> 	VehicleClothMaxAllowedList	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("userDetails", userDetails);
			
			VehicleClothMaxAllowedSettingDto maxAllowedSettingId  = findVehicleClothMaxAllowedById(valueObject.getLong("maxAllowedSettingId"));
			if(maxAllowedSettingId != null) {
				
				if(maxAllowedSettingId.getClothTypesId().equals(valueObject.getLong("editUpholsteryType", 0))) {
					typeDetails	= new VehicleClothMaxAllowedSetting();
					
					typeDetails.setMaxAllowedSettingId(valueObject.getLong("maxAllowedSettingId", 0));
					typeDetails.setVid(valueObject.getInt("editVehicleId", 0));
					typeDetails.setClothTypesId(valueObject.getLong("editUpholsteryType", 0));
					typeDetails.setMaxAllowedQuantity(valueObject.getDouble("editMaxQuantity", 0));
					typeDetails.setRemark(valueObject.getString("editDescription"));
					typeDetails.setCompanyId(userDetails.getCompany_id());
					typeDetails.setCreatedById(userDetails.getId());
					typeDetails.setLastModifiedById(userDetails.getId());
					typeDetails.setCreationDate(maxAllowedSettingId.getCreationDate());
					typeDetails.setLastUpdatedDate(new Date());
					typeDetails.setMarkForDelete(false);
					
					vehicleClothMaxAllowedSettingRepository.save(typeDetails);
					valueObject.put("saved", true);
				} else {
					
					VehicleClothMaxAllowedSetting validate = vehicleClothMaxAllowedSettingRepository.validateVehicleClothMaxAllowed(valueObject.getLong("editUpholsteryType"), valueObject.getInt("editVehicleId"));
					
					if(validate == null) {
						typeDetails	= new VehicleClothMaxAllowedSetting();
						
						typeDetails.setVid(valueObject.getInt("editVehicleId"));
						typeDetails.setClothTypesId(valueObject.getLong("editUpholsteryType"));
						typeDetails.setMaxAllowedQuantity(valueObject.getDouble("editMaxQuantity"));
						typeDetails.setRemark(valueObject.getString("editDescription"));
						typeDetails.setCompanyId(userDetails.getCompany_id());
						typeDetails.setCreatedById(userDetails.getId());
						typeDetails.setLastModifiedById(userDetails.getId());
						typeDetails.setCreationDate(new Date());
						typeDetails.setLastUpdatedDate(new Date());
						typeDetails.setMarkForDelete(false);
						
						vehicleClothMaxAllowedSettingRepository.save(typeDetails);
						valueObject.put("saved", true);
						
						vehicleClothMaxAllowedSettingRepository.deleteVehicleClothMaxAllowedById(maxAllowedSettingId.getMaxAllowedSettingId());
						
						} else {
							valueObject.put("alreadyExist", true);
							return valueObject;
						}
					
				}
			}
			
			VehicleClothMaxAllowedList	=	getVehicleClothMaxAllowedList(userDetails.getCompany_id());
			if(VehicleClothMaxAllowedList != null && !VehicleClothMaxAllowedList.isEmpty())
			valueObject.put("VehicleClothMaxAllowed", VehicleClothMaxAllowedList);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails					= null;
			VehicleClothMaxAllowedList	= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteVehicleClothMaxAllowedById(ValueObject valueObject) throws Exception {
		try {
			vehicleClothMaxAllowedSettingRepository.deleteVehicleClothMaxAllowedById(valueObject.getLong("maxAllowedSettingId"));
			valueObject.put("deleted", true);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getShowClothAssignDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails					= null;
		List<VehicleClothInventoryHistoryDto> 	ShowClothAssignDetailsList	= null;
		Integer				   					pageNumber					= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber			= valueObject.getInt("pageNumber",0);

			Page<VehicleClothInventoryHistory> page = getDeployment_Page_ShowClothAssignDetails(pageNumber, valueObject.getInt("vid"), userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());
			valueObject.put("SelectPage", pageNumber);
			
			ShowClothAssignDetailsList	=	getShowClothAssignDetailsList(pageNumber, valueObject.getInt("vid"), userDetails.getCompany_id());
			if(ShowClothAssignDetailsList != null && !ShowClothAssignDetailsList.isEmpty())
			valueObject.put("ShowClothAssignDetailsList", ShowClothAssignDetailsList);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails					= null;
			ShowClothAssignDetailsList	= null;
		}
	}
	
	@Override
	public List<VehicleClothInventoryHistoryDto> getShowClothAssignDetailsList(Integer pageNumber, Integer vid, Integer companyId) throws Exception {

		TypedQuery<Object[]> 							typedQuery 				= null;
		List<Object[]> 									resultList 				= null; 
		List<VehicleClothInventoryHistoryDto> 			clothDeatilsList 		= null;
		VehicleClothInventoryHistoryDto 				clothDeatils			= null;

		try {

			typedQuery = entityManager.createQuery(
				"SELECT VH.vehicleClothInventoryHistoryId, VH.locationId, VH.clothTypesId, VH.quantity, VH.stockTypeId, "
					+ " PL.partlocation_name, C.clothTypeName, U.firstName, VH.createdOn, VH.asignType "
					+ " FROM VehicleClothInventoryHistory as VH "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = VH.locationId "
					+ " INNER JOIN ClothTypes C ON C.clothTypesId = VH.clothTypesId "
					+ " INNER JOIN User U ON U.id = VH.createdById "
					+ " WHERE VH.companyId = "+companyId+" AND VH.vid ="+vid+" "
					+ " AND VH.markForDelete = 0 ORDER BY VH.vehicleClothInventoryHistoryId DESC ",
					Object[].class);
			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				clothDeatilsList = new ArrayList<>();

				for (Object[] result : resultList) {
					clothDeatils = new VehicleClothInventoryHistoryDto();
					
					clothDeatils.setVehicleClothInventoryHistoryId((long) result[0]);
					clothDeatils.setLocationId((int) result[1]);
					clothDeatils.setClothTypesId((long) result[2]);
					if( result[3] != null)
					clothDeatils.setQuantity(Double.parseDouble(toFixedTwo.format(result[3])));
					clothDeatils.setStockTypeId((short) result[4]);
					clothDeatils.setStockTypeName(ClothInvoiceStockType.getClothInvoiceStockTypeNAme((short) result[4]));
					clothDeatils.setLocationName((String) result[5]);
					clothDeatils.setClothTypeName((String) result[6]);
					clothDeatils.setCreatedByName((String) result[7]);
					clothDeatils.setCreatedOnStr(format1.format((Date) result[8]));
					clothDeatils.setAsignType((short) result[9]);
					clothDeatils.setAsignTypeStr(ClothInvoiceStockType.getClothInvoiceAsignNAme((short) result[9]));
					
					clothDeatilsList.add(clothDeatils);
				}
			}

			return clothDeatilsList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			clothDeatilsList 		= null;
			clothDeatils			= null;
		}
	}
	
	public Page<VehicleClothInventoryHistory> getDeployment_Page_ShowClothAssignDetails(Integer pageNumber, Integer vid, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "vehicleClothInventoryHistoryId");
		return vehicleClothInventoryHistoryRepository.getDeployment_Page_ShowClothAssignDetails(companyId, vid, pageable);
	}
	
	public Page<VehicleClothMaxAllowedSetting> getDeployment_Page_VehicleClothMaxAllowedSetting(Integer pageNumber, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "maxAllowedSettingId");
		return vehicleClothMaxAllowedSettingRepository.getDeployment_Page_VehicleClothMaxAllowedSetting(companyId, pageable);
	}
	
	public Page<VehicleClothInventoryDetails> getDeployment_Page_ShowInServiceList(Integer pageNumber, 
			long clothTypeId, int locationId, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE_SERVICE, Sort.Direction.DESC, "vehicleClothInventoryDetailsId");
		return vehicleClothInventoryDetailsRepository.getDeployment_Page_ShowInServiceList(companyId, clothTypeId, locationId, pageable);
	}
	
	public Page<VehicleClothInventoryHistory> getDeployment_Page_ShowInAssignUpholsteryToVehicleList(Integer pageNumber,Timestamp startDate, Timestamp endDate, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "vehicleClothInventoryHistoryId");
		return vehicleClothInventoryHistoryRepository.getDeployment_Page_ShowInAssignUpholsteryToVehicleList(companyId,startDate,endDate, pageable);
	}
	
	@Override
	public ValueObject getInServiceVehicle(ValueObject valueObject) throws Exception {
		List<VehicleClothInventoryDetailsDto> 	InServiceVehicleDetailsList	 = null;
		Integer				   					pageNumber					 = null;
		CustomUserDetails						userDetails					 = null;
		try {
			userDetails	 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber	 = valueObject.getInt("pageNumber",0);
			
			//Below is Commented Code For Showing on a Page instead of Modal.
			
			Page<VehicleClothInventoryDetails> page = getDeployment_Page_ShowInServiceList(pageNumber, 
					valueObject.getLong("clothTypesId"), valueObject.getInt("wareHouseLocationId"), userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());
			valueObject.put("SelectPage", pageNumber);
			
			InServiceVehicleDetailsList	=	getInServiceVehicleList(pageNumber, valueObject.getLong("clothTypesId"), valueObject.getInt("wareHouseLocationId"));
			if(InServiceVehicleDetailsList != null && !InServiceVehicleDetailsList.isEmpty())
			valueObject.put("InServiceVehicleDetailsList", InServiceVehicleDetailsList);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			InServiceVehicleDetailsList	= null;
		}
	}
	
	@Override
	public List<VehicleClothInventoryDetailsDto> getInServiceVehicleList(Integer pageNumber, long clothTypeId, Integer locationId) throws Exception {

		TypedQuery<Object[]> 							typedQuery 				= null;
		List<Object[]> 									resultList 				= null; 
		List<VehicleClothInventoryDetailsDto> 			inServiceDetailsList 	= null;
		VehicleClothInventoryDetailsDto 				inServiceDetails		= null;
		CustomUserDetails								userDetails				= null;

		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			typedQuery = entityManager.createQuery(
				"SELECT VH.vehicleClothInventoryDetailsId, VH.locationId, VH.clothTypesId, VH.quantity, PL.partlocation_name, "
					+ " C.clothTypeName, U.firstName, VH.lastModifiedOn, V.vehicle_registration "
					+ " FROM VehicleClothInventoryDetails as VH "
					+ " INNER JOIN Vehicle V ON V.vid = VH.vid "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = VH.locationId "
					+ " INNER JOIN ClothTypes C ON C.clothTypesId = VH.clothTypesId "
					+ " INNER JOIN User U ON U.id = VH.lastModifiedById "
					+ " WHERE VH.clothTypesId = "+clothTypeId+" AND VH.locationId = "+locationId+" AND VH.quantity > 0 "
					+ " AND VH.companyId = "+userDetails.getCompany_id()+" AND VH.markForDelete = 0 ORDER BY VH.lastModifiedOn DESC, VH.vehicleClothInventoryDetailsId DESC ",
					Object[].class);
			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE_SERVICE);
			typedQuery.setMaxResults(PAGE_SIZE_SERVICE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				inServiceDetailsList = new ArrayList<>();

				for (Object[] result : resultList) {
					inServiceDetails = new VehicleClothInventoryDetailsDto();
					
					inServiceDetails.setVehicleClothInventoryDetailsId((long) result[0]);
					inServiceDetails.setLocationId((int) result[1]);
					inServiceDetails.setClothTypesId((long) result[2]);
					inServiceDetails.setQuantity((Double) result[3]);
					inServiceDetails.setLocationName((String) result[4]);
					inServiceDetails.setClothTypesName((String) result[5]);
					inServiceDetails.setLastModifiedBy((String) result[6]);
					inServiceDetails.setLastModifiedOnstr(format1.format((Date) result[7]));
					inServiceDetails.setVehicle_registration((String) result[8]);
					
					inServiceDetailsList.add(inServiceDetails);
				}
			}
			return inServiceDetailsList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			inServiceDetailsList 	= null;
			inServiceDetails		= null;
		}
	}
	
	@Override
	public ValueObject getDamageWashingQtyDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails							= null;
		UpholsterySendLaundryInvoiceDto			upholsterySendLaundryInvoiceDto		= null;
		SentLaundryClothDetailsDto				sentLaundryClothDetailsList			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			upholsterySendLaundryInvoiceDto	=	upholsterySendLaundryInvoiceService.getLaundryInvoiceDetails(valueObject.getLong("invoiceId", 0), userDetails.getCompany_id());
			if(upholsterySendLaundryInvoiceDto != null)
			valueObject.put("upholsterySendLaundryInvoiceDto", upholsterySendLaundryInvoiceDto);	
			
			sentLaundryClothDetailsList	=	sentLaundryClothDetailsService.getDamageWashingQtyDetails(valueObject.getLong("clothDetailsId"), userDetails.getCompany_id());
			if(sentLaundryClothDetailsList != null)
			valueObject.put("sentLaundryClothDetailsList", sentLaundryClothDetailsList);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject saveDamageWashingQty(ValueObject valueObject) throws Exception { 
		LaundryClothReceiveHistory			clothReceiveHistory		= null;
		UpholsterySendLaundryInvoiceDto		laundryInvoiceStatus	= null;
		CustomUserDetails					userDetails				= null;
		UpholsterySendLaundryInvoice		laundryInvoice			= null;
		double								totalQty				= 0.0;
		double								validateQty				= 0.0;
		try {
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("userDetails", userDetails);
			
			laundryInvoice = upholsterySendLaundryInvoiceRepository.getLaundryInvoiceDetails(valueObject.getLong("damagelaundryInvoiceId"), userDetails.getCompany_id());
			totalQty 	   = laundryInvoice.getReceivedQuantity() + laundryInvoice.getDamagedQuantity() + laundryInvoice.getLosedQuantity();
			validateQty    = laundryInvoice.getTotalQuantity() - totalQty;
			
			if(valueObject.getDouble("damageQuantity") > validateQty) {
				valueObject.put("QuantityExceeded", true);
				return valueObject;
			}
			
			clothReceiveHistory		=	inventoryBL.getDamageWashingQty(valueObject);
			laundryClothReceiveHistoryService.saveLaundryClothReceiveHistory(clothReceiveHistory);
			
			clothInventoryStockTypeDetailsService.updateLocationStockDetailsToDamage(valueObject);
			upholsterySendLaundryInvoiceService.updateInvoiceDetailsToDamage(valueObject.getLong("damagelaundryInvoiceId"), valueObject.getDouble("damageQuantity"));
			sentLaundryClothDetailsService.updateSentLaundryDetailsToDamage(valueObject);
			
			laundryInvoiceStatus = upholsterySendLaundryInvoiceService.getLaundryInvoiceStatus(valueObject.getLong("damagelaundryInvoiceId"), userDetails.getCompany_id());
			if(laundryInvoiceStatus.getFinalReceivedQty() == 0.0)
			upholsterySendLaundryInvoiceService.updateLaundryInvoiceSatus(valueObject.getLong("damagelaundryInvoiceId"), userDetails.getCompany_id());
			
			inventoryDamageAndLostHistoryService.updateDamageQty(
					valueObject.getLong("damagelaundryInvoiceId"), valueObject.getLong("damageclothTypesId"), valueObject.getDouble("damageQuantity"));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			clothReceiveHistory		= null;
			userDetails				= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject saveLostWashingQty(ValueObject valueObject) throws Exception {
		LaundryClothReceiveHistory			clothReceiveHistory		= null;
		UpholsterySendLaundryInvoiceDto		laundryInvoiceStatus	= null;
		CustomUserDetails					userDetails				= null;
		UpholsterySendLaundryInvoice		laundryInvoice			= null;
		double								totalQty				= 0.0;
		double								validateQty				= 0.0;
		try {
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("userDetails", userDetails);
			
			laundryInvoice = upholsterySendLaundryInvoiceRepository.getLaundryInvoiceDetails(valueObject.getLong("lostlaundryInvoiceId"), userDetails.getCompany_id());
			totalQty 	   = laundryInvoice.getReceivedQuantity() + laundryInvoice.getDamagedQuantity() + laundryInvoice.getLosedQuantity();
			validateQty    = laundryInvoice.getTotalQuantity() - totalQty;
			
			if(valueObject.getDouble("lostQuantity") > validateQty) {
				valueObject.put("QuantityExceeded", true);
				return valueObject;
			}
			
			clothReceiveHistory		=	inventoryBL.getLostWashingQty(valueObject);
			laundryClothReceiveHistoryService.saveLaundryClothReceiveHistory(clothReceiveHistory);
			
			clothInventoryStockTypeDetailsService.updateLocationStockDetailsToLost(valueObject);
			upholsterySendLaundryInvoiceService.updateInvoiceDetailsToLost(valueObject.getLong("lostlaundryInvoiceId"), valueObject.getDouble("lostQuantity"));
			sentLaundryClothDetailsService.updateSentLaundryDetailsToLost(valueObject);
			
			laundryInvoiceStatus = upholsterySendLaundryInvoiceService.getLaundryInvoiceStatus(valueObject.getLong("lostlaundryInvoiceId"), userDetails.getCompany_id());
			if(laundryInvoiceStatus.getFinalReceivedQty() == 0.0)
			upholsterySendLaundryInvoiceService.updateLaundryInvoiceSatus(valueObject.getLong("lostlaundryInvoiceId"), userDetails.getCompany_id());
			
			inventoryDamageAndLostHistoryService.updateLostQty(
					valueObject.getLong("lostlaundryInvoiceId"), valueObject.getLong("lostclothTypesId"), valueObject.getDouble("lostQuantity"));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			clothReceiveHistory		= null;
			userDetails				= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject saveClothDamageDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails					userDetails				= null;
		InventoryDamageAndLostHistory		inventoryDamageHistory	= null;
		ClothInventoryStockTypeDetailsDto	detailsDto				= null;
		long								clothTypeId				= 0;
		int									locationId				= 0;
		double								validateQty				= 0.0;
		
		try {
			userDetails	  = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("userDetails", userDetails);
			clothTypeId   = valueObject.getLong("damageClothTypes");      
			locationId    = valueObject.getInt("damagelocationId");      
			
			detailsDto = clothInventoryStockTypeDetailsService.getClothLocationQuantity(clothTypeId, locationId, userDetails.getCompany_id());
			
			if(valueObject.getShort("damageTypeOfCloth") == ClothInvoiceStockType.STOCK_TYPE_NEW) {
				validateQty = detailsDto.getNewStockQuantity();
			} else {
				validateQty = detailsDto.getUsedStockQuantity();
			}	
			
			if(valueObject.getDouble("damageQuantity") > validateQty) {
				valueObject.put("QuantityExceeded", true);
				return valueObject;
			} 
				
			inventoryDamageHistory = inventoryBL.getInventoryDamageHistoryQty(valueObject);
			inventoryDamageAndLostHistoryService.saveInventoryDamageAndLostHistory(inventoryDamageHistory);
			
			clothInventoryStockTypeDetailsService.updateClothDamageDetails(valueObject);
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails				= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject saveClothLostDetails(ValueObject valueObject) throws Exception {
		
		CustomUserDetails					userDetails				= null;
		InventoryDamageAndLostHistory		inventoryLostHistory	= null;
		ClothInventoryStockTypeDetailsDto	detailsDto				= null;
		long								clothTypeId				= 0;
		int									locationId				= 0;
		double								validateQty				= 0.0;
      
		try {
			userDetails					 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("userDetails", userDetails);
			clothTypeId  				 = valueObject.getLong("lostClothTypes");      
			locationId   				 = valueObject.getInt("lostlocationId"); 
			
			detailsDto = clothInventoryStockTypeDetailsService.getClothLocationQuantity(clothTypeId, locationId, userDetails.getCompany_id());
			
			if(valueObject.getShort("lostTypeOfCloth") == ClothInvoiceStockType.STOCK_TYPE_NEW) {
				validateQty = detailsDto.getNewStockQuantity();
			} else {
				validateQty = detailsDto.getUsedStockQuantity();
			}
			
			if(valueObject.getDouble("lostQuantity") > validateQty) {
				valueObject.put("QuantityExceeded", true);
				return valueObject;
			} 
				
			inventoryLostHistory = inventoryBL.getInventoryLostHistoryQty(valueObject);
			inventoryDamageAndLostHistoryService.saveInventoryDamageAndLostHistory(inventoryLostHistory);
			
			clothInventoryStockTypeDetailsService.updateClothLostDetails(valueObject);
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails				= null;
		}
	}
	
	public Page<InventoryDamageAndLostHistory> getDeployment_Page_ShowInDamageAndLost(Integer pageNumber, 
			long clothTypeId, int locationId, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE_DAMAGE, Sort.Direction.DESC, "InventoryDamageAndLostHistoryId");
		return inventoryDamageAndLostHistoryRepository.getDeployment_Page_ShowInDamageAndLost(companyId, clothTypeId, locationId, pageable);
	}
	
	@Override
	public ValueObject getInDamageDetails(ValueObject valueObject) throws Exception {
		List<InventoryDamageAndLostHistoryDto> 	InDamageDetailsList	 		 = null;
		Integer				   					pageNumber					 = null;
		CustomUserDetails						userDetails					 = null;
		try {
			
			userDetails	 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber	 = valueObject.getInt("pageNumber",0);
			
			Page<InventoryDamageAndLostHistory> page = getDeployment_Page_ShowInDamageAndLost(pageNumber, 
					valueObject.getLong("clothTypesId"), valueObject.getInt("wareHouseLocationId"), userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());
			valueObject.put("SelectPage", pageNumber);
			
			InDamageDetailsList	=	inventoryDamageAndLostHistoryService.getInDamageDetailsList(pageNumber, valueObject.getLong("clothTypesId"), valueObject.getInt("wareHouseLocationId"));
			if(InDamageDetailsList != null && !InDamageDetailsList.isEmpty())
			valueObject.put("InDamageDetailsList", InDamageDetailsList);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			InDamageDetailsList	= null;
		}
	}
	
	@Override
	public ValueObject getInLostDetails(ValueObject valueObject) throws Exception {
		List<InventoryDamageAndLostHistoryDto> 	InLostDetailsList	 		 = null;
		Integer				   					pageNumber					 = null;
		CustomUserDetails						userDetails					 = null;
		try {
			userDetails	 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber	 = valueObject.getInt("pageNumber",0);
			
			Page<InventoryDamageAndLostHistory> page = getDeployment_Page_ShowInDamageAndLost(pageNumber, 
					valueObject.getLong("clothTypesId"), valueObject.getInt("wareHouseLocationId"), userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());
			valueObject.put("SelectPage", pageNumber);
			
			InLostDetailsList	=	inventoryDamageAndLostHistoryService.getInLostDetailsList(pageNumber, valueObject.getLong("clothTypesId"), valueObject.getInt("wareHouseLocationId"));
			if(InLostDetailsList != null && !InLostDetailsList.isEmpty())
			valueObject.put("InLostDetailsList", InLostDetailsList);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			InLostDetailsList	= null;
		}
	}
	
	public Page<UpholsterySendLaundryInvoice> getDeployment_Page_ShowInWashing(Integer pageNumber, 
			long clothTypeId, int locationId, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE_WASHING, Sort.Direction.DESC, "laundryInvoiceId");
		return upholsterySendLaundryInvoiceRepository.getDeployment_Page_ShowInWashing(companyId, clothTypeId, locationId, pageable);
	}
	
	@Override
	public ValueObject getInWashingDetails(ValueObject valueObject) throws Exception {
		List<UpholsterySendLaundryInvoiceDto> 	InWashingDetailsList	 	 = null;
		Integer				   					pageNumber					 = null;
		CustomUserDetails						userDetails					 = null;
		try {
			userDetails	 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber	 = valueObject.getInt("pageNumber",0);
			
			Page<UpholsterySendLaundryInvoice> page = getDeployment_Page_ShowInWashing(pageNumber, 
					valueObject.getLong("clothTypesId",0), valueObject.getInt("wareHouseLocationId",0), userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());
			valueObject.put("SelectPage", pageNumber);
			
			InWashingDetailsList	=	upholsterySendLaundryInvoiceService.getInWashingDetailsList(pageNumber, valueObject.getLong("clothTypesId"), valueObject.getInt("wareHouseLocationId"));
			if(InWashingDetailsList != null && !InWashingDetailsList.isEmpty())
			valueObject.put("InWashingDetailsList", InWashingDetailsList);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			InWashingDetailsList	= null;
		}
	}
	
	
	@Override
	public ValueObject getUpholsteryAssignmentReport(ValueObject valueObject) throws Exception {		
		
		CustomUserDetails									userDetails				= null;
		List<VehicleClothInventoryHistoryDto> 				assignUpholstery		= null;		
		ValueObject						                 	tableConfig				= null;
		
		Integer												vehicleGroupId			= 0;
		String												vehicleId				= null;
		Integer												upholsteryId			= 0;
		Integer												warehouseLocationId		= 0;
		String												dateRange				= null;
		String[] 											From_TO_DateRange 		= null;	
		String 												dateRangeFrom 			= null;
		String 												dateRangeTo 			= null;
		
		String 												vehicleGroup 			= "";
		String 												vehiclesRegstraion		= "";
		String 												upholstery_Name 		= "";
		String 												warehouse_Location_Name = "";
		String 												date 					= null;
		String 												clothAssignType 		= null;
		
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			vehicleGroupId					= valueObject.getInt("vehicleGroup", 0);
			if(valueObject.containsKey("vehicleArr") == true) {
				vehicleId					= valueObject.getString("vehicleArr");
			}
			upholsteryId					= valueObject.getInt("clothTypes", 0);
			warehouseLocationId				= valueObject.getInt("warehouselocation", 0);
			
			dateRange						= valueObject.getString("dateRange");
			From_TO_DateRange	= dateRange.split(" to ");
			dateRangeFrom 		= From_TO_DateRange[0];
			dateRangeTo 		= From_TO_DateRange[1];
			dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

		
			
			if(upholsteryId != 0  )
			{					
				upholstery_Name 			= " AND VI.clothTypesId = "+ upholsteryId +" ";
			}				
			if(warehouseLocationId != 0  )
			{					
				warehouse_Location_Name 	= " AND VI.locationId = "+ warehouseLocationId +" ";
			}				
			if(vehicleGroupId != 0  )
			{					
				vehicleGroup     			= " AND VG.gid = "+ vehicleGroupId +" ";
			}				
			if(vehicleId != null)
			{		
				vehiclesRegstraion     		= " AND VI.vid IN("+vehicleId+") ";
			}	
			
			date 							= " AND VI.createdOn between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;
			clothAssignType 				= " AND VI.asignType = "+ClothInvoiceStockType.CLOTH_ASIGN_TYPE_ASIGN+" ";
			
			String query = " " + upholstery_Name + "  "+warehouse_Location_Name+" "+ vehicleGroup +" "+date+" "+ clothAssignType + " "+vehiclesRegstraion+" ";				
			
			tableConfig						= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.UPHOLSTERY_ASSIGNMENT_TABLE_DATA_FILE_PATH);
	
			tableConfig						= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig						= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);		
			
			assignUpholstery 				= upholsteryReportDaoImpl.getUpholstery_Assignment_Report(query, userDetails.getCompany_id());
			
			valueObject.put("assignUpholstery", assignUpholstery);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company",
			userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
		
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
			assignUpholstery	= null;
			userDetails			= null;			
		}
	}
	
	
	
	@Override
	public ValueObject getUpholsteryStockTransferReport(ValueObject valueObject) throws Exception {		
		
		CustomUserDetails									userDetails						= null;
		List<InventoryUpholsteryTransferDto> 				stockTransferUpholstery			= null;		
		ValueObject						                 	tableConfig						= null;
		
		Integer												fromWarehouseLocationId			= 0;
		Integer												toWarehouseLocationId			= 0;
		Integer												upholsteryId					= 0;
		short												status							= 0;
		String												dateRange						= null;
		String[] 											From_TO_DateRange 				= null;	
		String 												dateRangeFrom 					= null;
		String 												dateRangeTo 					= null;
		
		String 												upholstery_Name 				= "";
		String 												from_Warehouse_Location_Name 	= "";
		String 												to_Warehouse_Location_Name 		= "";
		String 												upholsteryStatus 				= "";
		String 												date 							= null;
		
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			fromWarehouseLocationId			= valueObject.getInt("fromWarehouselocation", 0);
			toWarehouseLocationId			= valueObject.getInt("toWarehouselocation", 0);
			upholsteryId					= valueObject.getInt("clothTypes", 0);
			status							= valueObject.getShort("status");
			dateRange						= valueObject.getString("dateRange");
			From_TO_DateRange				= dateRange.split(" to ");
			dateRangeFrom 					= From_TO_DateRange[0];
			dateRangeTo 					= From_TO_DateRange[1];
			dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

		
			if(fromWarehouseLocationId != 0  )
			{					
				from_Warehouse_Location_Name = " AND VT.fromLocationId = "+ fromWarehouseLocationId +" ";
			}				
			if(toWarehouseLocationId != 0  )
			{					
				to_Warehouse_Location_Name 	 = " AND VT.toLocationId = "+ toWarehouseLocationId +" ";
			}	
			if(upholsteryId != 0  )
			{					
				upholstery_Name 			 = " AND VT.clothTypesId = "+ upholsteryId +" ";
			}	
			if(status != 0  )
			{					
				upholsteryStatus 			 = " AND VT.transferStatusId = "+ status +" ";
			}				
			
			date 							 = " AND VT.transferDate between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;
			
			String query = " "+from_Warehouse_Location_Name+" "+to_Warehouse_Location_Name+" " +upholstery_Name+ " "+upholsteryStatus+" "+date+"  ";				
			
			tableConfig						= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.UPHOLSTERY_STOCK_TRANSFER_TABLE_DATA_FILE_PATH);
	
			tableConfig						= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig						= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);		
			
			stockTransferUpholstery 		= upholsteryReportDaoImpl.getUpholstery_Stock_Transfer_Report(query, userDetails.getCompany_id());
			
			valueObject.put("stockTransferUpholstery", stockTransferUpholstery);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company",
			userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
		
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig				= null;
			stockTransferUpholstery	= null;
			userDetails				= null;			
		}
	}
	
	
	
	
	@Override
	public ValueObject getVehicleClothListBySearch(ValueObject valueObject) throws Exception {
		List<VehicleClothMaxAllowedSettingDto> 	vehicleSearchList	 	= null;
		CustomUserDetails						userDetails				= null;
		try {
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleSearchList	=	vehicleClothListSearchByVid(valueObject.getInt("vehId"), userDetails.getCompany_id());
			if(vehicleSearchList != null && !vehicleSearchList.isEmpty())
			valueObject.put("VehicleClothMaxAllowed", vehicleSearchList);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleSearchList	= null;
		}
	}
	
	@Override
	public List<VehicleClothMaxAllowedSettingDto> vehicleClothListSearchByVid(Integer vid, Integer companyId) throws Exception {

		TypedQuery<Object[]> 							typedQuery 				= null;
		List<Object[]> 									resultList 				= null; 
		List<VehicleClothMaxAllowedSettingDto> 			maxAllowedList 			= null;
		VehicleClothMaxAllowedSettingDto 				maxAllowedDto			= null;

		try {

			typedQuery = entityManager.createQuery(
				"SELECT VM.maxAllowedSettingId, V.vehicle_registration, C.clothTypeName, VM.maxAllowedQuantity, VM.remark "
					+ " FROM VehicleClothMaxAllowedSetting as VM "
					+ " INNER JOIN Vehicle V ON V.vid = VM.vid "
					+ " INNER JOIN ClothTypes C ON C.clothTypesId = VM.clothTypesId "
					+ " Where VM.companyId = "+companyId+" AND VM.vid = "+vid+" AND VM.markForDelete = 0 ORDER BY VM.maxAllowedSettingId DESC ", 
					Object[].class);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				maxAllowedList = new ArrayList<>();

				for (Object[] result : resultList) {
					maxAllowedDto = new VehicleClothMaxAllowedSettingDto();
					
					maxAllowedDto.setMaxAllowedSettingId((long) result[0]);
					maxAllowedDto.setVehicle_registration((String) result[1]);
					maxAllowedDto.setClothTypeName((String) result[2]);
					maxAllowedDto.setMaxAllowedQuantity((Double) result[3]);
					maxAllowedDto.setRemark((String) result[4]);
					
					maxAllowedList.add(maxAllowedDto);
				}
			}

			return maxAllowedList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			maxAllowedList 			= null;
			maxAllowedDto			= null;
		}
	}
	
	//Upholstery Sent To Laundry Report Services Start
	@Override
	public ValueObject getUpholsterySentToLaundryReport(ValueObject valueObject) throws Exception {
		String 												locationId				= null;								
		String												clothTypesId			= null;
		Integer												vendorId				= 0;
		String												dateRange				= null;
		CustomUserDetails									userDetails				= null;
		List<UpholsterySendLaundryInvoiceDto> 				Laundry			        = null;
		ValueObject						                  tableConfig				= null;
		
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vendorId		= valueObject.getInt("laundryVendor", 0);
			dateRange		= valueObject.getString("laundryDateRange");
			locationId 		= valueObject.getString("warehouselocation");
			clothTypesId	= valueObject.getString("clothTypes");
			
			if(dateRange != null) {
				
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				
				String warehouse_location = "",cloth_Types = "", laundry_vendor_Name = "", Date = "";
				
				if(!locationId.isEmpty() ){					
					warehouse_location = " AND LI.wareHouseLocationId IN ( "+ locationId  +" )";
				}
				
				if(!clothTypesId.isEmpty() ){					
					cloth_Types = " AND SL.clothTypesId IN ( "+ clothTypesId +" )" ;
				}
				
				if(vendorId != 0  ){					
					laundry_vendor_Name = " AND LI.vendorId = "+ vendorId +" ";
				}
				
				Date =  " AND LI.sentDate between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;  
				String query = " " + warehouse_location + "  " + cloth_Types + " "+laundry_vendor_Name+" "+Date;
				
				tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.UPHOLSTERY_SENT_TO_LAUNDRY_REPORT_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				
				Laundry = upholsteryReportDaoImpl.getUpholstery_Sent_ToLaundryReport(query, userDetails.getCompany_id());
			}
			valueObject.put("Laundry", Laundry);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company",
				userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
			Laundry	= null;
			userDetails			= null;
			dateRange			= null;
		}
	}
	//Upholstery Sent To Laundry Report Services Stop
/*Vendor Credit Payment start  (Many Mehtods ) End At Line 2648 ...*/
	@Override
	public List<ClothInvoiceDto> FilterVendorCreditListInventoryClothInvoice(Integer vendorId, String dateRangeFrom, String dateRangeTo, Integer company_id) throws Exception {

		List<ClothInvoiceDto>			clothInvoiceList		= null;
		ClothInvoiceDto					clothInvoice 			= null;
		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]>					results 				= null;

		try {
			
			typedQuery = entityManager
					.createQuery("SELECT CI.clothInvoiceId, CI.clothInvoiceNumber, CI.invoiceNumber, CI.invoiceDate, "
							+ " CI.invoiceAmount, CI.vendorId, CI.paymentTypeId, CI.vendorPaymentStatus, V.vendorName, "
							+ " CI.purchaseOrderId, CI.paidAmount, CI.balanceAmount, CI.cloth_document_id, CI.invoiceNumber  "
							+ " FROM ClothInvoice AS CI "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = CI.vendorId "
							+ " where CI.invoiceDate BETWEEN '"+dateRangeFrom+"' AND '"+dateRangeTo+"'"
							+ " AND CI.vendorId = "+vendorId+" AND CI.paymentTypeId = "+PaymentTypeConstant.PAYMENT_TYPE_CREDIT
							+ " AND CI.companyId = "+company_id+" AND  CI.markForDelete = 0 AND (purchaseOrderId is null OR purchaseOrderId < 0) "
							+ " AND (CI.vendorPaymentStatus = " + FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID + " "
							+ " OR CI.vendorPaymentStatus = " + FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID + "AND CI.balanceAmount > 0 ) ORDER BY CI.invoiceDate DESC "
							 , Object[].class);
							
			
			results = typedQuery.getResultList();
			
			if(results != null && !results.isEmpty()) {
				clothInvoiceList	=	new ArrayList<>();
				for (Object[] result : results) {
					
					clothInvoice = new ClothInvoiceDto();
					clothInvoice.setClothInvoiceId((Long) result[0]);
					clothInvoice.setClothInvoiceNumber((Long) result[1]);
					clothInvoice.setInvoiceNumber((String) result[2]);
					clothInvoice.setInvoiceDate((Timestamp) result[3]);
					if(result[4] != null)
					clothInvoice.setInvoiceAmount(Double.parseDouble(toFixedTwo.format(result[4])));
					clothInvoice.setVendorId((Integer) result[5]);
					clothInvoice.setPaymentTypeId((short) result[6]);
					if(result[7] != null ) {
						clothInvoice.setVendorPaymentStatus((short) result[7]);
					}
					clothInvoice.setVendorName((String) result[8]);
					if(result[9]!=null) {
						clothInvoice.setPurchaseOrderId((long) result[9]);
					}
					if(result[10]!= null) {
						clothInvoice.setPaidAmount(Double.parseDouble(toFixedTwo.format(result[10])));
					}
					if(result[11]!= null) {
						clothInvoice.setBalanceAmount(Double.parseDouble(toFixedTwo.format(result[11])));
					}
					if(result[12]!= null) {
						clothInvoice.setCloth_document_id((Long)result[12]);
					}
					
					clothInvoice.setInvoiceNumber((String) result[13]);
				
					
					clothInvoiceList.add(clothInvoice);
					
				}
			}
			
			return clothInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			clothInvoiceList		= null;
			clothInvoice 			= null;
			typedQuery 				= null;
			results 				= null;
		}
	}

	
	@Override
	public List<ClothInvoiceDto> getVendorApproval_IN_InventoryClothInvoice_List(Long approvalId, Integer company_id) throws Exception {

		List<ClothInvoiceDto>			clothInvoiceList		= null;
		ClothInvoiceDto					clothInvoice 			= null;
		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]>					results 				= null;

		try {
			
			typedQuery = entityManager
					.createQuery("SELECT CI.clothInvoiceId, CI.clothInvoiceNumber, CI.invoiceNumber, CI.invoiceDate, CI.invoiceAmount,"
							+ " CI.description, CI.vendorPaymentStatus, CI.paymentTypeId,"
							+ " CI.vendorPaymentDate, PL.partlocation_name, CI.wareHouseLocation, V.vendorName, V.vendorLocation,"
							+ " CI.vendorId,CI.paidAmount "
							+ " FROM ClothInvoice AS CI "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CI.wareHouseLocation "
							+ " LEFT JOIN Vendor V ON V.vendorId = CI.vendorId"
							+ " where CI.clothApprovalId = "+approvalId+" AND CI.companyId = "+company_id+" AND  CI.markForDelete = 0 ", Object[].class);
			
			results = typedQuery.getResultList();
			
			if(results != null && !results.isEmpty()) {
				clothInvoiceList	=	new ArrayList<>();
				for (Object[] result : results) {
					clothInvoice = new ClothInvoiceDto();
					
					clothInvoice.setClothInvoiceId((Long) result[0]);
					clothInvoice.setClothInvoiceNumber((Long) result[1]);
					clothInvoice.setInvoiceNumber((String) result[2]);
					clothInvoice.setInvoiceDate((Timestamp) result[3]);
					clothInvoice.setInvoiceAmount((double) result[4]);
					clothInvoice.setDescription((String) result[5]);
					clothInvoice.setVendorPaymentStatus((short) result[6]);
					clothInvoice.setPaymentTypeId((short) result[7]);
					clothInvoice.setVendorPaymentDate((Timestamp) result[8]);
					clothInvoice.setClothLocation((String) result[9]);
					clothInvoice.setWareHouseLocation((Integer) result[10]);
					clothInvoice.setVendorName((String) result[11]);
					clothInvoice.setVendorLocation((String) result[12]);
					clothInvoice.setVendorId((Integer) result[13]);
					
					if((Double) result[14] != null) {
						clothInvoice.setPaidAmount((Double) result[14]);
					} else {
						clothInvoice.setPaidAmount((double)0);
					}
					
					clothInvoiceList.add(clothInvoice);
					
					
				}
			}
			
			return clothInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			clothInvoiceList		= null;
			clothInvoice 			= null;
			typedQuery 				= null;
			results 				= null;
		}
	}
	
	@Override
	public List<UpholsterySendLaundryInvoiceDto> getVendorApprovalLaundryInvoiceList(Long approvalId,
			Integer companyId) throws Exception {

		List<UpholsterySendLaundryInvoiceDto>			clothInvoiceList		= null;
		UpholsterySendLaundryInvoiceDto					clothInvoice 			= null;
		TypedQuery<Object[]> 							typedQuery 				= null;
		List<Object[]>									results 				= null;

		try {
			
			typedQuery = entityManager
					.createQuery("SELECT CI.laundryInvoiceId, CI.laundryInvoiceNumber, CI.paymentNumber, CI.sentDate, CI.totalCost,"
							+ " CI.description, CI.vendorPaymentStatus, CI.paymentTypeId,"
							+ " PL.partlocation_name, CI.wareHouseLocationId, V.vendorName, V.vendorLocation,"
							+ " CI.vendorId, CI.paidAmount, CI.balanceAmount "
							+ " FROM UpholsterySendLaundryInvoice AS CI "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CI.wareHouseLocationId "
							+ " LEFT JOIN Vendor V ON V.vendorId = CI.vendorId"
							+ " where CI.laundryApprovalId = "+approvalId+" AND CI.companyId = "+companyId+" AND  CI.markForDelete = 0 ", Object[].class);
			
			results = typedQuery.getResultList();
			
			if(results != null && !results.isEmpty()) {
				clothInvoiceList	=	new ArrayList<>();
				for (Object[] result : results) {
					clothInvoice = new UpholsterySendLaundryInvoiceDto();
					
					clothInvoice.setLaundryInvoiceId((Long) result[0]);
					clothInvoice.setLaundryInvoiceNumber((Long) result[1]);
					clothInvoice.setPaymentNumber((String) result[2]);
					clothInvoice.setSentDate((Date) result[3]);
					clothInvoice.setTotalCost((Double) result[4]);
					clothInvoice.setDescription((String) result[5]);
					clothInvoice.setVendorPaymentStatus((short) result[6]);
					clothInvoice.setPaymentTypeId((short) result[7]);
					clothInvoice.setLocationName((String) result[8]);
					clothInvoice.setWareHouseLocationId((Integer) result[9]);
					clothInvoice.setVendorName((String) result[10]);
					clothInvoice.setVendorLocation((String) result[11]);
					clothInvoice.setVendorId((Integer) result[12]);
					clothInvoice.setPaidAmount((Double) result[13]);
					clothInvoice.setBalanceAmount((Double) result[14]);
					
					clothInvoice.setVendorPaymentStatusStr(FuelVendorPaymentMode.getPaymentMode(clothInvoice.getVendorPaymentStatus()));
					clothInvoice.setPaymentType(PaymentTypeConstant.getPaymentTypeName(clothInvoice.getPaymentTypeId()));
					if(clothInvoice.getSentDate() != null)
						clothInvoice.setInvoiceDateStr(format.format(clothInvoice.getSentDate()));
					
					clothInvoiceList.add(clothInvoice);
					
					
				}
			}
			
			return clothInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			clothInvoiceList		= null;
			clothInvoice 			= null;
			typedQuery 				= null;
			results 				= null;
		}
	}
	
	@Transactional
	public void update_Vendor_ApprovalTO_Status_MULTIP_InventoryClothInvoice_ID(String ApprovalInvoice_ID,
			Long approvalId, short approval_Status) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		entityManager.createQuery(" UPDATE ClothInvoice SET clothApprovalId=" + approvalId
				+ ", vendorPaymentStatus=" + approval_Status + " WHERE clothInvoiceId IN (" + ApprovalInvoice_ID + ") AND companyId = "+userDetails.getCompany_id()+" ")
				.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentDetails(Long approvalId, String paymentDate, short paymentStatus) throws Exception {

		entityManager.createQuery("  UPDATE ClothInvoice SET vendorPaymentDate ='" + paymentDate+"' , "
				+ " vendorPaymentStatus ="+paymentStatus+""
				+ " WHERE clothApprovalId = "+approvalId+" ").executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception {
		entityManager.createQuery("  UPDATE ClothInvoice SET vendorPaymentDate =" + paymentDate+" , "
				+ " vendorPaymentStatus="+paymentStatus+", clothApprovalId = "+approvalId+""
				+ " WHERE clothInvoiceId = "+invoiceId+" ").executeUpdate();
		
	}
	
	@Override
	@Transactional
	public void update_Vendor_ApprovalTO_Status_LaundryInvoice_ID(String approvalInvoiceID, Long approvalId,
			short approvalStatus, Integer companyId) throws Exception {
		
		entityManager.createQuery(" UPDATE UpholsterySendLaundryInvoice SET laundryApprovalId=" + approvalId
				+ ", vendorPaymentStatus=" + approvalStatus + " WHERE laundryInvoiceId IN (" + approvalInvoiceID + ") AND companyId = "+companyId+" ")
				.executeUpdate();
		
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentDetailsLI(Long approvalId, short paymentStatus) throws Exception {

		entityManager.createQuery("  UPDATE UpholsterySendLaundryInvoice SET vendorPaymentStatus ="+paymentStatus+""
				+ " WHERE laundryApprovalId = "+approvalId+" ").executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentCancelDetailsLI(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception {
		entityManager.createQuery("  UPDATE UpholsterySendLaundryInvoice SET laundryApprovalId = "+approvalId+" , vendorPaymentStatus ="+paymentStatus+" "
				+ " WHERE laundryInvoiceId = "+invoiceId+" ").executeUpdate();
		
	}
	
	@Transactional
	public List<ClothInvoiceDto> get_Amount_VendorApproval_IN_InventoryClothInvoice(Long VendorApproval_Id, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT CI.clothInvoiceId, CI.invoiceAmount From ClothInvoice as CI WHERE CI.clothApprovalId=:approval AND CI.companyId =:companyId AND CI.markForDelete = 0",
				Object[].class);
		queryt.setParameter("approval", VendorApproval_Id);
		queryt.setParameter("companyId", companyId);

		List<Object[]> results = queryt.getResultList();

		List<ClothInvoiceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ClothInvoiceDto>();
			ClothInvoiceDto list = null;
			for (Object[] result : results) {
				list = new ClothInvoiceDto();

				list.setClothInvoiceId((Long) result[0]);
				list.setTotalClothAmount((double)result[1]);

				Dtos.add(list);
			}
		}

		return Dtos;
	}
	
	@Override
	@Transactional
	public List<UpholsterySendLaundryInvoiceDto> getVendorInventoryLaundryInvoice(Long approvalId, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT CI.laundryInvoiceId, CI.totalCost From UpholsterySendLaundryInvoice as CI WHERE CI.laundryApprovalId=:approval AND CI.companyId =:companyId AND CI.markForDelete = 0",
				Object[].class);
		queryt.setParameter("approval", approvalId);
		queryt.setParameter("companyId", companyId);

		List<Object[]> results = queryt.getResultList();

		List<UpholsterySendLaundryInvoiceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<UpholsterySendLaundryInvoiceDto>();
			UpholsterySendLaundryInvoiceDto list = null;
			for (Object[] result : results) {
				list = new UpholsterySendLaundryInvoiceDto();

				list.setLaundryInvoiceId((Long) result[0]);
				list.setTotalCost((double)result[1]);

				Dtos.add(list);
			}
		}

		return Dtos;
	}
	
	
	@Transactional
	public void update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryClothInvoice_ID(String ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp approval_date, Integer companyId,double paidAmount,double discountAmount) throws Exception {
		double balanceAmount =0;

		entityManager.createQuery("  UPDATE ClothInvoice SET clothApprovalId=" + Approval_ID
				+ ",vendorPaymentDate='" + approval_date + "', vendorPaymentStatus='" + approval_Status + "', paidAmount ='"+paidAmount+"', discountAmount ='"+discountAmount+"',balanceAmount='"+balanceAmount+"' "
				+ " WHERE clothInvoiceId IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();
	}

	@Override
	@Transactional
	public void update_Payment_Vendor_ApprovalTO_Status_MULTIP_LaundryInvoice(String ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp approval_date, Integer companyId,double paidAmount,double discountAmount) throws Exception {
		double balanceAmount = 0;

		entityManager.createQuery("  UPDATE UpholsterySendLaundryInvoice SET laundryApprovalId=" + Approval_ID
				+ ",vendorPaymentDate='" + approval_date + "', vendorPaymentStatus='" + approval_Status + "', paidAmount ='"+paidAmount+"', "
						+ " discountAmount ='"+discountAmount+"',balanceAmount='"+balanceAmount+"' "
						+ " WHERE laundryInvoiceId IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();
	}
	
	//dirct payment of upholstery
	@Transactional
	public void update_ClothInvoiveApprovalAmount(Object clothInvoiceId, Object recievedAmount, Object balanceAmt,Object discountAmt,short vendorPaymentStatusId, long approvalId, Date date)
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		try {
			
			entityManager.createQuery(
					"UPDATE ClothInvoice SET paidAmount= "+recievedAmount+ ", "
							+ " balanceAmount= "+balanceAmt+", discountAmount= "+discountAmt+", clothApprovalId = " +approvalId + ","
							+ " vendorPaymentDate = '"+date+"', lastModifiedById = "+userDetails.getId()+", "
							+ " vendorPaymentStatus= " + vendorPaymentStatusId +" " 
							+ " WHERE clothInvoiceId = "+clothInvoiceId+" AND companyId = "+userDetails.getCompany_id()+" "
							+ " AND markForDelete = 0 ")
			.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}

	}
	/*Vendor Credit Payment End...*/
	
	@Override
	public ValueObject getAllUpholsteryAssigndToVehicleByDate(ValueObject valueObject) throws Exception {
		CustomUserDetails							userDetails							= null;
		String										startDate							= null;								
		String										endDate								= null;	
		TypedQuery<Object[]> 						typedQuery 							= null;
		List<Object[]>								results 							= null;
		List<VehicleClothInventoryHistoryDto>		vehicleClothInventoryHistoryList	= null;
		VehicleClothInventoryHistoryDto				vehicleClothInventoryHistoryDto		= null;
		Integer				   						pageNumber					 		= null;
		Timestamp				   					startDateTimeStamp					= null;
		Timestamp				   					endDateTimeStamp					= null;

		try {
			userDetails	 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			startDate			= valueObject.getString("startDate") + DateTimeUtility.DAY_START_TIME;
			endDate				= valueObject.getString("endDate") + DateTimeUtility.DAY_END_TIME;
			startDateTimeStamp 	= DateTimeUtility.getTimeStamp(startDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDateTimeStamp 	= DateTimeUtility.getTimeStamp(endDate, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			pageNumber	 = valueObject.getInt("pageNumber",0);
			
			Page<VehicleClothInventoryHistory> page = getDeployment_Page_ShowInAssignUpholsteryToVehicleList(pageNumber, startDateTimeStamp,endDateTimeStamp,userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());
			valueObject.put("SelectPage", pageNumber);
			
						
			
			typedQuery = entityManager
					.createQuery("SELECT VC.vehicleClothInventoryHistoryId, VC.vid, V.vehicle_registration ,"
							+ " VC.clothTypesId, c.clothTypeName, VC.quantity, VC.createdOn, VC.createdById, U.firstName, "
							+ " VC.asignType ,VC.stockTypeId "
							+ " FROM VehicleClothInventoryHistory as VC "
							+ " INNER JOIN ClothTypes as c on c.clothTypesId = VC.clothTypesId "
							+ " INNER JOIN Vehicle as V on V.vid = VC.vid "
							+ " INNER JOIN User as U on U.id = VC.createdById "
							+ " where VC.createdOn Between '"+startDate+"' AND '"+endDate+"' "
							+ " AND VC.companyId = "+userDetails.getCompany_id()+" AND  VC.markForDelete = 0  ORDER BY VC.createdOn DESC", Object[].class);
			
			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);
			results = typedQuery.getResultList();
			
			if(results != null && !results.isEmpty()) {
				vehicleClothInventoryHistoryList	=	new ArrayList<>();
				for (Object[] result : results) {
					vehicleClothInventoryHistoryDto = new VehicleClothInventoryHistoryDto();
					
					vehicleClothInventoryHistoryDto.setVehicleClothInventoryHistoryId((Long) result[0]);
					vehicleClothInventoryHistoryDto.setVid((Integer) result[1]);
					vehicleClothInventoryHistoryDto.setVehicleRegistration((String) result[2]);
					vehicleClothInventoryHistoryDto.setClothTypesId((Long) result[3]);
					vehicleClothInventoryHistoryDto.setClothTypeName((String) result[4]);
					vehicleClothInventoryHistoryDto.setQuantity((Double)result[5]);
					vehicleClothInventoryHistoryDto.setCreatedOnStr(format1.format((Date)result[6]));
					vehicleClothInventoryHistoryDto.setCreatedById((Long)result[7]);
					vehicleClothInventoryHistoryDto.setCreatedByName((String)result[8]);
					vehicleClothInventoryHistoryDto.setAsignType((short)result[9]);
					vehicleClothInventoryHistoryDto.setAsignTypeStr(ClothInvoiceStockType.getClothInvoiceAsignNAme((short) result[9]));
					vehicleClothInventoryHistoryDto.setStockTypeId((short)result[10]);
					vehicleClothInventoryHistoryDto.setStockTypeName(ClothInvoiceStockType.getClothInvoiceStockTypeNAme((short) result[10]));
					vehicleClothInventoryHistoryList.add(vehicleClothInventoryHistoryDto);
					
					
				}
				valueObject.put("vehicleClothInventoryHistoryList", vehicleClothInventoryHistoryList);
			}
			
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
		}
	}
	
	public Map<Integer, List<VehicleClothInventoryHistoryDto>> getAllUpholsteryDetails(String startDate, String endDate) throws Exception {
		TypedQuery<Object[]> 											typedQuery  	= null;
		Map<Integer, List<VehicleClothInventoryHistoryDto>>				companyHM		= null;
		List<VehicleClothInventoryHistoryDto>							companyLIst 	= null;
		try {
			typedQuery = entityManager.createQuery("SELECT VC.vehicleClothInventoryHistoryId, VC.vid, V.vehicle_registration ,"
					+ " VC.clothTypesId, c.clothTypeName, VC.quantity, VC.createdOn, VC.createdById, U.firstName, "
					+ " VC.asignType ,VC.stockTypeId, VC.companyId, CIST.newStockQuantity, CIST.usedStockQuantity, VCID.quantity "//this quantity is Total Assign Quantity
					+ " FROM VehicleClothInventoryHistory as VC "
					+ " INNER JOIN ClothTypes as c on c.clothTypesId = VC.clothTypesId "
					+ " INNER JOIN Vehicle as V on V.vid = VC.vid "
					+ " INNER JOIN User as U on U.id = VC.createdById "
					+ " INNER JOIN ClothInventoryStockTypeDetails as CIST on CIST.clothTypesId = VC.clothTypesId AND CIST.wareHouseLocationId = VC.locationId AND CIST.markForDelete =0 "
					+ " INNER JOIN VehicleClothInventoryDetails as VCID on VCID.vid = VC.vid and VCID.clothTypesId = VC.clothTypesId "
					+ " where VC.createdOn Between '"+startDate+"' AND '"+endDate+"' "
					+ " AND VC.markForDelete = 0  ORDER BY VC.asignType ",
					Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();
			if (results != null && !results.isEmpty()) {
				
				companyHM	= new HashMap<Integer, List<VehicleClothInventoryHistoryDto>>();
				
				VehicleClothInventoryHistoryDto	VehicleClothInventoryHistoryDto = null;
				for (Object[] result : results) {
					VehicleClothInventoryHistoryDto	= new VehicleClothInventoryHistoryDto();
					VehicleClothInventoryHistoryDto.setVehicleClothInventoryHistoryId((Long) result[0]);                                     
					VehicleClothInventoryHistoryDto.setVid((Integer) result[1]);                                                             
					VehicleClothInventoryHistoryDto.setVehicleRegistration((String) result[2]);                                              
					VehicleClothInventoryHistoryDto.setClothTypesId((Long) result[3]);                                                       
					VehicleClothInventoryHistoryDto.setClothTypeName((String) result[4]);                                                    
					VehicleClothInventoryHistoryDto.setQuantity((Double)result[5]);                                                          
					VehicleClothInventoryHistoryDto.setCreatedOnStr(format.format((Date)result[6]));                                        
					VehicleClothInventoryHistoryDto.setCreatedById((Long)result[7]);                                                         
					VehicleClothInventoryHistoryDto.setCreatedByName((String)result[8]);                                                     
					VehicleClothInventoryHistoryDto.setAsignType((short)result[9]);                                                          
					VehicleClothInventoryHistoryDto.setAsignTypeStr(ClothInvoiceStockType.getClothInvoiceAsignNAme((short) result[9]));      
					VehicleClothInventoryHistoryDto.setStockTypeId((short)result[10]);                                                       
					VehicleClothInventoryHistoryDto.setStockTypeName(ClothInvoiceStockType.getClothInvoiceStockTypeNAme((short) result[10]));
					VehicleClothInventoryHistoryDto.setCompanyId((Integer)result[11]);
					VehicleClothInventoryHistoryDto.setNewStockQuantity((Double)result[12]);
					VehicleClothInventoryHistoryDto.setOldStockQuantity((Double)result[13]);
					if(result[14] != null) {
						VehicleClothInventoryHistoryDto.setTotalAssignQuantity((Double)result[14]);
					}
					companyLIst	= companyHM.get(VehicleClothInventoryHistoryDto.getCompanyId());
					
					if(companyLIst == null) {
						companyLIst	= new ArrayList<>();
					}
					companyLIst.add(VehicleClothInventoryHistoryDto);
					
					companyHM.put(VehicleClothInventoryHistoryDto.getCompanyId(), companyLIst);
				
				}
				
			}
			return companyHM;
		} catch (Exception e) {
			throw e;
		}finally {
			typedQuery  	= null;
			companyHM		= null;
			companyLIst 	= null;
		}
	}

	@Override
	public ValueObject getUpholsteryLossReport(ValueObject valueObject) throws Exception {
		
		Integer 											vid						= 0;
		Integer 											driverId				= 0;
		String												dateRange				= null;
		CustomUserDetails									userDetails				= null;
		List<InventoryDamageAndLostHistoryDto> 				Loss			        = null;
		ValueObject						                  tableConfig				= null;
		
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vid				= valueObject.getInt("vid", 0);
			driverId		= valueObject.getInt("driverId", 0);
			dateRange		= valueObject.getString("laundryDateRange");
			
			if(dateRange != null) {
				
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				
				String 	vehicleId="",driverIdentity="",Date = "";
				
				if(vid != 0) {
					vehicleId		 =  "  AND  I.vid = " + vid +" ";
				}
				if(driverId != 0) {
					driverIdentity	 =  "  AND  I.driverId = " + driverId +" ";
				}
				
				Date =  " AND I.createdDate BETWEEN '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ; 
				
				String query = " "+vehicleId+"  "+driverIdentity+" "+Date;
				
				tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.UPHOLSTERY_LOSS_REPORT_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				
				Loss = upholsteryReportDaoImpl.getUpholsteryLossReport(query, userDetails.getCompany_id());
				
			}
			valueObject.put("Loss", Loss);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company",
				userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
			Loss	= null;
			userDetails			= null;
			dateRange			= null;
		}
	}
	
	
	@Transactional//Approval Approved Here
	public void updatePaymentApprovedUpholsteryDetails(Long ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId) throws Exception {

		entityManager.createQuery("  UPDATE ClothInvoice SET clothApprovalId=" + Approval_ID
				+ ",expectedPaymentDate='" + expectedPaymentDate + "', vendorPaymentStatus='" + approval_Status + "' "
				+ " WHERE clothInvoiceId IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();
	}
	
	@Override
	@Transactional
	public void updatePaymentApprovedLaundryDetails(Long ApprovalInvoice_ID, Long Approval_ID, short approval_Status,
			Timestamp expectedPaymentDate, Integer companyId) throws Exception {

		entityManager.createQuery("  UPDATE UpholsterySendLaundryInvoice SET laundryApprovalId=" + Approval_ID
				+ ",expectedPaymentDate='" + expectedPaymentDate + "', vendorPaymentStatus='" + approval_Status + "' "
				+ " WHERE laundryInvoiceId IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();
	}

	@Transactional//Approval Paid Here
	public void updatePaymentPaidUpholsteryDetails(String ApprovalInvoice_ID, Timestamp approval_date, Integer companyId, short vendorPaymentStatus) throws Exception {
		
		entityManager.createQuery("UPDATE ClothInvoice SET vendorPaymentDate='" + approval_date+"', vendorPaymentStatus = "+vendorPaymentStatus+"  WHERE clothInvoiceId IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();
	}
	
	@Override
	@Transactional
	public void updatePaymentPaidLaundryDetails(String ApprovalInvoice_ID, Timestamp approval_date, Integer companyId, short vendorPaymentStatus) throws Exception {
		entityManager.createQuery("UPDATE UpholsterySendLaundryInvoice SET vendorPaymentDate='" + approval_date+"', "
				+ " vendorPaymentStatus = "+vendorPaymentStatus+"  WHERE laundryInvoiceId IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> getClothInvoiceListForTallyImport(String fromDate, String toDate,
			Integer companyId, String tallyCompany) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT SE.clothInvoiceId, SE.clothInvoiceNumber, VA.approvalCreateDate, V.vendorName,"
					+ " TC.companyName, SE.createdOn, SE.paymentTypeId, VSD.subApprovalpaidAmount, SE.isPendingForTally,"
					+ " SE.invoiceDate, SE.invoiceNumber"
					+ " FROM ClothInvoice SE "
					+ " INNER JOIN Vendor AS V ON V.vendorId = SE.vendorId"
					+ " INNER JOIN VendorApproval VA on VA.approvalId = SE.clothApprovalId AND VA.markForDelete = 0"
					+ " INNER JOIN VendorSubApprovalDetails VSD ON VSD.approvalId = VA.approvalId AND VSD.invoiceId = SE.clothInvoiceId AND VSD.approvalPlaceId = "+ApprovalType.APPROVAL_TYPE_INVENTORY_CLOTH_INVOICE+""
					+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = SE.tallyCompanyId AND TC.companyName = '"+tallyCompany+"'"
					+ " WHERE VA.approvalCreateDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND SE.companyId = "+companyId+" AND SE.markForDelete = 0 AND VSD.subApprovalpaidAmount > 0");
			
			List<Object[]> results = null;
			try {
				results = query.getResultList();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			List<TripSheetExpenseDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripSheetExpenseDto>();
				TripSheetExpenseDto select;
				for (Object[] vehicle : results) {
					if (vehicle != null) {
						select = new TripSheetExpenseDto();
						select.setTripExpenseID((Long) vehicle[0]);
						select.setTripSheetNumber((Long) vehicle[1]);
						select.setVoucherDate(tallyFormat.format((java.util.Date)vehicle[2]));
						select.setVendorName((String) vehicle[3]);
						select.setTallyCompanyName((String) vehicle[4]);
						select.setCreatedOn((Timestamp) vehicle[5]);
						select.setExpenseFixedId((short) vehicle[6]);
						select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
						select.setExpenseAmount((Double) vehicle[7]);
						select.setPendingForTally((boolean) vehicle[8]);
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_UPHOLSTERY_INVENTORY);
						select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
						select.setTripSheetId(select.getTripExpenseID());
						select.setCredit(false);
						select.setVid(0);
						select.setLedgerName("Motor Part Expense");
						select.setExpenseName("Motor Part Expense");
						
						if(select.getExpenseFixedId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
							select.setCredit(true);
						}else {
							select.setCredit(false);
						}
						
						if(select.getVendorName() == null ) {
							select.setVendorName("--");
						}
						if(vehicle[2] != null ) {
							select.setCreatedStr(sqlDateFormat.format((java.util.Date)vehicle[2]));
						}
						
						 select.setRemark("Cloth Invoice Date: "+sqlDateFormat.format((java.util.Date)vehicle[9])+" Invoice Number : "+(String)vehicle[10]+" From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						select.setTripSheetNumberStr("CI-"+select.getTripSheetNumber());
						
						Dtos.add(select);
					}
				}
				
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<TripSheetExpenseDto> getClothInvoiceListForTallyImportATC(String fromDate, String toDate,
			Integer companyId, String tallyCompany) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT SE.clothInvoiceId, SE.clothInvoiceNumber, SE.invoiceDate, V.vendorName,"
					+ " SE.createdOn, SE.paymentTypeId, SE.invoiceAmount, SE.isPendingForTally,"
					+ " SE.invoiceDate, SE.invoiceNumber"
					+ " FROM ClothInvoice SE "
					+ " INNER JOIN Vendor AS V ON V.vendorId = SE.vendorId"
					+ " WHERE SE.invoiceDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND SE.companyId = "+companyId+" AND SE.markForDelete = 0 AND SE.invoiceAmount > 0");
			
			List<Object[]> results = null;
			try {
				results = query.getResultList();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			List<TripSheetExpenseDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripSheetExpenseDto>();
				TripSheetExpenseDto select;
				for (Object[] vehicle : results) {
					if (vehicle != null) {
						select = new TripSheetExpenseDto();
						select.setTripExpenseID((Long) vehicle[0]);
						select.setTripSheetNumber((Long) vehicle[1]);
						select.setVoucherDate(tallyFormat.format((java.util.Date)vehicle[2]));
						select.setVendorName((String) vehicle[3]);
						select.setTallyCompanyName(tallyCompany);
						select.setCreatedOn((Timestamp) vehicle[4]);
						select.setExpenseFixedId((short) vehicle[5]);
						select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
						select.setExpenseAmount((Double) vehicle[6]);
						select.setPendingForTally((boolean) vehicle[7]);
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_UPHOLSTERY_INVENTORY);
						select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
						select.setTripSheetId(select.getTripExpenseID());
						select.setCredit(false);
						select.setVid(0);
						select.setLedgerName("Motor Part Expense");
						select.setExpenseName("Motor Part Expense");
						
						if(select.getExpenseFixedId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
							select.setCredit(true);
						}else {
							select.setCredit(false);
						}
						
						if(select.getVendorName() == null ) {
							select.setVendorName("--");
						}
						if(vehicle[2] != null ) {
							select.setCreatedStr(sqlDateFormat.format((java.util.Date)vehicle[2]));
						}
						
						 select.setRemark("Cloth Invoice Date: "+sqlDateFormat.format((java.util.Date)vehicle[8])+" Invoice Number : "+(String)vehicle[9]+" From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						select.setTripSheetNumberStr("CI-"+select.getTripSheetNumber());
						
						Dtos.add(select);
					}
				}
				
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VendorPaymentNotPaidDto> vendorPaymentNotPaidClothInvoiceList(Integer vendor_id, String dateFrom, String dateTo, Integer companyId) throws Exception {
		List<VendorPaymentNotPaidDto>			clothInvoiceList		= null;
		VendorPaymentNotPaidDto					list 					= null;
		TypedQuery<Object[]> 					typedQuery 				= null;
		List<Object[]>							results 				= null;
		try {
			typedQuery = entityManager
					.createQuery("SELECT CI.clothInvoiceId, CI.clothInvoiceNumber, V.vendorName, CI.invoiceNumber, "
							+ "  CI.invoiceDate, CI.paymentTypeId, CI.invoiceAmount, CI.balanceAmount  "
							+ " FROM ClothInvoice AS CI "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = CI.vendorId "
							+ " where CI.invoiceDate BETWEEN '"+dateFrom+"' AND '"+dateTo+"'"
							+ " AND CI.vendorId = "+vendor_id+" AND CI.paymentTypeId = "+PaymentTypeConstant.PAYMENT_TYPE_CREDIT
							+ " AND CI.companyId = "+companyId+" AND  CI.markForDelete = 0 AND (purchaseOrderId is null OR purchaseOrderId < 0) "
							+ " AND (CI.vendorPaymentStatus = " + FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID + " "
							+ " OR CI.vendorPaymentStatus = " + FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID + "AND CI.balanceAmount > 0 ) "
							 , Object[].class);
			
			results = typedQuery.getResultList();
			
			if(results != null && !results.isEmpty()) {
				clothInvoiceList	=	new ArrayList<>();
				for (Object[] result : results) {
					
					list = new VendorPaymentNotPaidDto();
					
					list.setSerialNumberId((Long) result[0]);
					list.setSerialNumber((Long) result[1]);
					list.setSerialNumberStr("CI-"+list.getSerialNumber());
					list.setVendorName((String) result[2]);
					
					if(result[3] != null) {
						list.setInvoiceNumber((String) result[3]);
					} else {
						list.setInvoiceNumber("-");
					}
					
					list.setInvoiceDateStr(format.format((java.util.Date) result[4]));
					list.setPaymentType(PaymentTypeConstant.getPaymentTypeName((short) result[5]));
					
					if(result[6] != null) {
						list.setInvoiceAmount((Double) result[6]);
					} else {
						list.setInvoiceAmount(0.0);
					}
					
					if(result[7] != null) {
						list.setBalanceAmount((Double) result[7]);
					} else {
						list.setBalanceAmount(0.0);
					}
					
					clothInvoiceList.add(list);
					
				}
			}
			
			return clothInvoiceList;
			
		} catch (Exception e) {
			throw e;
		} finally {
			clothInvoiceList		= null;
			list 					= null;
			typedQuery 				= null;
			results 				= null;
		}
	}
	
	@Transactional
	@Override
	public void saveClothDocument(ClothInvoice clothInvoice, MultipartFile file, ValueObject valueObject) throws Exception {
		try {
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			org.fleetopgroup.persistence.document.ClothInvoiceDocument clothDoc = new org.fleetopgroup.persistence.document.ClothInvoiceDocument();
			clothDoc.setClothInvoiceId(clothInvoice.getClothInvoiceId());
			if(file != null) {
				byte[] bytes = file.getBytes();
				clothDoc.setClothInvoiceFileName(file.getOriginalFilename());
				clothDoc.setClothInvoiceContent(bytes);
				clothDoc.setClothInvoiceContentType(file.getContentType());
			} else {
				if (valueObject.getString("base64String",null) != null) {
					byte[] bytes = ByteConvertor.base64ToByte(valueObject.getString("base64String",null));
					
					clothDoc.setClothInvoiceFileName(valueObject.getString("imageName",null));
					clothDoc.setClothInvoiceContent(bytes);
					clothDoc.setClothInvoiceContentType(valueObject.getString("imageExt",null));
				}
			}	
			clothDoc.setMarkForDelete(false);
			clothDoc.setCreatedById(clothInvoice.getCreatedById());
			clothDoc.setLastModifiedById(clothInvoice.getLastModifiedById());
			clothDoc.setCreated(toDate);
			clothDoc.setLastupdated(toDate);
			clothDoc.setCompanyId(clothInvoice.getCompanyId());

			addClothInvoiceDocument(clothDoc);
			updateClothInvoiceDocumentId(clothDoc.get_id(), true, clothDoc.getClothInvoiceId(), clothDoc.getCompanyId());

		} catch (Exception e) {
			throw e;
		}
	}
	@Transactional
	public void addClothInvoiceDocument(org.fleetopgroup.persistence.document.ClothInvoiceDocument clothDoc) {
		clothDoc.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_CLOTH_INVOICE_DOCUMENT));
		mongoTemplate.save(clothDoc);
	}
	
	@Transactional
	public void updateClothInvoiceDocumentId(Long clothInvoiceDocId, boolean clothDocument, Long clothInvoiceId, Integer companyId) {
		clothInventoryRepository.updateClothInvoiceDocumentId(clothInvoiceDocId, clothDocument, clothInvoiceId, companyId);

	}
	
	@Override
	public org.fleetopgroup.persistence.document.ClothInvoiceDocument getClothInvoiceDocumentDetails(long clothDocumentId, Integer company_id)
			throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(clothDocumentId).and("companyId").is(company_id).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.ClothInvoiceDocument.class);
	}
	
	@Override
	public ClothInvoiceDocument getClothInvoiceDocumentDetailsByInvoiceId(Long id, Integer companyId) throws Exception {// checking already exists or not
		
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("clothInvoiceId").is(id).and("companyId").is(companyId).and("markForDelete").is(false));

		return mongoTemplate.findOne(query, ClothInvoiceDocument.class);
	}
	
	@Override
	public ClothInvoice getClothInvoiceByClothInvoiceId(Long clothInvoiceId, Integer companyId) throws Exception{		
		ClothInvoice clothInvoice= null;
		try {
			clothInvoice = clothInventoryRepository.getClothInvoice(clothInvoiceId, companyId);
			return clothInvoice;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject saveVehicleLaundryDetails(ValueObject valueObject) throws Exception {
		VehicleLaundryDetails			laundryDetails				= null;
		CustomUserDetails				userDetails					= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			valueObject.put("companyId", userDetails.getCompany_id());
			laundryDetails	= VehicleLaundryDetailsBL.getVehicleLaundryDetailsDTO(valueObject);
			vehicllDetailsRepository.save(laundryDetails);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getVehicleLaundryDetailsList(ValueObject valueObject) throws Exception {
		try {
			valueObject.put("vehicleLaundryList", vehicleLaundryDetailsDao.getVehicleLaundryDetailsList(valueObject.getLong("laundryInvoiceId",0), valueObject.getLong("clothTypeId",0)));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject removeVehicleLaundry(ValueObject valueObject) throws Exception {
		try {
			vehicllDetailsRepository.removeVehicleLaundry(valueObject.getLong("vehicleLaundryDetailsId",0));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject updateClothToLaundry(ValueObject valueObject) throws Exception {
		
		UpholsterySendLaundryInvoice		laundryInvoice		= null;
		CustomUserDetails					userDetails			= null;
		PendingVendorPayment				payment				= null;
		short 								oldPaymentTypeId 	= 0;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			laundryInvoice	= upholsterySendLaundryInvoiceRepository.getLaundryInvoiceDetails(valueObject.getLong("lostlaundryInvoiceId"), userDetails.getCompany_id());
			oldPaymentTypeId = laundryInvoice.getPaymentTypeId();
			valueObject.put("laundryInvoice", laundryInvoice);
			valueObject.put("userDetails", userDetails);
			
			laundryInvoice	= inventoryBL.getUpholsterySendLaundryInvoiceToUpdate(valueObject);
			
			upholsterySendLaundryInvoiceRepository.save(laundryInvoice);
			
			pendingVendorPaymentService.deletePendingVendorPayment(laundryInvoice.getLaundryInvoiceId(), PendingPaymentType.PAYMENT_TYPE_LAUNDRY_INVOICE);
			
			if(laundryInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
				payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForLIEdit(laundryInvoice);
				pendingVendorPaymentService.savePendingVendorPayment(payment);
			}

			if(valueObject.getBoolean("allowBankPaymentDetails",false)) {
			ValueObject bankPaymentValueObject=JsonConvertor.toValueObjectFormSimpleJsonString(valueObject.getString("bankPaymentDetails"));
			bankPaymentValueObject.put("oldPaymentTypeId",oldPaymentTypeId);
			bankPaymentValueObject.put("bankPaymentTypeId", laundryInvoice.getPaymentTypeId());
			bankPaymentValueObject.put("currentPaymentTypeId", laundryInvoice.getPaymentTypeId());
			bankPaymentValueObject.put("userId",userDetails.getId());
			bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
			bankPaymentValueObject.put("moduleId",laundryInvoice.getLaundryInvoiceId());
			bankPaymentValueObject.put("moduleNo", laundryInvoice.getLaundryInvoiceNumber());
			bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.CLOTH_LAUNDRY);
			bankPaymentValueObject.put("amount",laundryInvoice.getTotalCost());

			Vendor	vendor	=  vendorService.getVendor(laundryInvoice.getVendorId());
			bankPaymentValueObject.put("remark", "Update Laundry Invoice LI-"+laundryInvoice.getLaundryInvoiceNumber()+" vendor : "+vendor.getVendorName());
			
			bankPaymentService.updatePaymentDetailsFromValuObject(bankPaymentValueObject);
			}
			
			valueObject.clear();
			
			valueObject.put("invoiceId", laundryInvoice.getLaundryInvoiceId());
			valueObject.put("success", true);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteClothLaundryInvoice(ValueObject valueObject) throws Exception {
		CustomUserDetails					userDetails			= null;
		List<SentLaundryClothDetails> 		laundryClothList	= null;
		UpholsterySendLaundryInvoice		invoice				= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			laundryClothList	= sentLaundryClothDetailsRepository.getSentLaundryClothDetailsList(valueObject.getLong("laundryInvoiceId",0), userDetails.getCompany_id());
			invoice				= upholsterySendLaundryInvoiceRepository.getLaundryInvoiceDetails(valueObject.getLong("laundryInvoiceId",0), userDetails.getCompany_id());
			if(invoice != null && invoice.getReceivedQuantity() == 0 && invoice.getDamagedQuantity() == 0 && invoice.getLosedQuantity() == 0) {
				if(laundryClothList != null && !laundryClothList.isEmpty()) {
					for(SentLaundryClothDetails	clothDetails : laundryClothList) {
						clothInventoryStockTypeDetailsService.updateStockDetailsForLaundryDelete(clothDetails.getClothTypesId(), invoice.getWareHouseLocationId(), clothDetails.getQuantity());
					}
				}
				sentLaundryClothDetailsRepository.deleteLaundryClothDetails(valueObject.getLong("laundryInvoiceId",0), userDetails.getCompany_id());
				upholsterySendLaundryInvoiceRepository.deleteLaundryInvoice(valueObject.getLong("laundryInvoiceId",0), userDetails.getCompany_id());
				
				valueObject.put("deleted", true);
			}else {
				valueObject.put("inProcess", true);
			}
			if(invoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
				pendingVendorPaymentService.deletePendingVendorPayment(valueObject.getLong("laundryInvoiceId", 0), PendingPaymentType.PAYMENT_TYPE_LAUNDRY_INVOICE);
			}
			bankPaymentService.deleteBankPaymentDetailsIfTransactionDeleted(valueObject.getLong("laundryInvoiceId", 0), ModuleConstant.CLOTH_LAUNDRY, userDetails.getCompany_id(),userDetails.getId(),false);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> getLaundryInvoiceListForTallyImport(String fromDate, String toDate,
			Integer companyId, String tallyCompany) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT SE.laundryInvoiceId, SE.laundryInvoiceNumber, VA.approvalCreateDate, V.vendorName,"
					+ " TC.companyName, SE.creationDate, SE.paymentTypeId, VSD.subApprovalpaidAmount, SE.isPendingForTally, SE.sentDate,"
					+ " SE.paymentNumber"
					+ " FROM UpholsterySendLaundryInvoice SE "
					+ " INNER JOIN LaundryClothReceiveHistory LRH ON LRH.laundryInvoiceId = SE.laundryInvoiceId"
					+ " INNER JOIN Vendor AS V ON V.vendorId = SE.vendorId"
					+ " INNER JOIN VendorApproval VA on VA.approvalId = SE.laundryApprovalId AND VA.markForDelete = 0"
					+ " INNER JOIN VendorSubApprovalDetails VSD ON VSD.approvalId = VA.approvalId AND VSD.invoiceId = SE.laundryInvoiceId AND VSD.approvalPlaceId = "+ApprovalType.APPROVAL_TYPE_INVENTORY_LAUNDRY_INVOICE+""
					+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = SE.tallyCompanyId AND TC.companyName = '"+tallyCompany+"'"
					+ " WHERE VA.approvalCreateDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND SE.companyId = "+companyId+" AND SE.markForDelete = 0 AND VSD.subApprovalpaidAmount > 0");
			
			List<Object[]> results = null;
			try {
				results = query.getResultList();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			List<TripSheetExpenseDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripSheetExpenseDto>();
				TripSheetExpenseDto select;
				for (Object[] vehicle : results) {
					if (vehicle != null) {
						select = new TripSheetExpenseDto();
						select.setTripExpenseID((Long) vehicle[0]);
						select.setTripSheetNumber((Long) vehicle[1]);
						select.setVoucherDate(tallyFormat.format((java.util.Date)vehicle[2]));
						select.setVendorName((String) vehicle[3]);
						select.setTallyCompanyName((String) vehicle[4]);
						select.setCreatedOn((Timestamp) vehicle[5]);
						select.setExpenseFixedId((short) vehicle[6]);
						select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
						select.setExpenseAmount((Double) vehicle[7]);
						select.setPendingForTally((boolean) vehicle[8]);
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_LAUNDRY);
						select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
						select.setTripSheetId(select.getTripExpenseID());
						select.setCredit(false);
						select.setVid(0);
						select.setLedgerName("Laundry Expense");
						select.setExpenseName("Laundry Expense");
						
						
						if(select.getExpenseFixedId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
							select.setCredit(true);
						}else {
							select.setCredit(false);
						}
						
						if(select.getVendorName() == null ) {
							select.setVendorName("--");
						}
						if(vehicle[2] != null ) {
							select.setCreatedStr(sqlDateFormat.format((java.util.Date)vehicle[2]));
						}
						
						String invoiceNumber = "";
						if(vehicle[10] != null) {
							invoiceNumber = (String)vehicle[10];
						}
						
						 select.setRemark("Laundry Invoice Date: "+sqlDateFormat.format((java.util.Date)vehicle[9])+" Invoice Number : "+invoiceNumber+" From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						select.setTripSheetNumberStr("LI-"+select.getTripSheetNumber());
						
						Dtos.add(select);
					}
				}
				
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<TripSheetExpenseDto> getLaundryInvoiceListForTallyImportATC(String fromDate, String toDate,
			Integer companyId, String tallyCompany) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT SE.laundryInvoiceId, SE.laundryInvoiceNumber, SE.sentDate, V.vendorName,"
					+ " SE.creationDate, SE.paymentTypeId, SE.totalCost, SE.isPendingForTally, SE.sentDate,"
					+ " SE.paymentNumber"
					+ " FROM UpholsterySendLaundryInvoice SE "
					+ " INNER JOIN LaundryClothReceiveHistory LRH ON LRH.laundryInvoiceId = SE.laundryInvoiceId"
					+ " INNER JOIN Vendor AS V ON V.vendorId = SE.vendorId"
					+ " WHERE SE.sentDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND SE.companyId = "+companyId+" AND SE.markForDelete = 0 AND SE.totalCost > 0");
			
			List<Object[]> results = null;
			try {
				results = query.getResultList();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			List<TripSheetExpenseDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripSheetExpenseDto>();
				TripSheetExpenseDto select;
				for (Object[] vehicle : results) {
					if (vehicle != null) {
						select = new TripSheetExpenseDto();
						select.setTripExpenseID((Long) vehicle[0]);
						select.setTripSheetNumber((Long) vehicle[1]);
						select.setVoucherDate(tallyFormat.format((java.util.Date)vehicle[2]));
						select.setVendorName((String) vehicle[3]);
						select.setTallyCompanyName(tallyCompany);
						select.setCreatedOn((Timestamp) vehicle[4]);
						select.setExpenseFixedId((short) vehicle[5]);
						select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
						select.setExpenseAmount((Double) vehicle[6]);
						select.setPendingForTally((boolean) vehicle[7]);
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_LAUNDRY);
						select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
						select.setTripSheetId(select.getTripExpenseID());
						select.setCredit(false);
						select.setVid(0);
						select.setLedgerName("Laundry Expense");
						select.setExpenseName("Laundry Expense");
						
						if(select.getExpenseFixedId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
							select.setCredit(true);
						}else {
							select.setCredit(false);
						}
						
						if(select.getVendorName() == null ) {
							select.setVendorName("--");
						}
						if(vehicle[2] != null ) {
							select.setCreatedStr(sqlDateFormat.format((java.util.Date)vehicle[2]));
						}
						
						String invoiceNumber = "";
						if(vehicle[10] != null) {
							invoiceNumber = (String)vehicle[9];
						}
						
						 select.setRemark("Laundry Invoice Date: "+sqlDateFormat.format((java.util.Date)vehicle[8])+" Invoice Number : "+invoiceNumber+" From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						select.setTripSheetNumberStr("LI-"+select.getTripSheetNumber());
						
						Dtos.add(select);
					}
				}
				
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<UpholsterySendLaundryInvoiceDto> FilterVendorLaundryInvoice(Integer vendorId, String dateRangeFrom,
			String dateRangeTo, Integer companyId) throws Exception {

		List<UpholsterySendLaundryInvoiceDto>			clothInvoiceList		= null;
		UpholsterySendLaundryInvoiceDto					clothInvoice 			= null;
		TypedQuery<Object[]> 							typedQuery 				= null;
		List<Object[]>									results 				= null;

		try {
			
			typedQuery = entityManager
					.createQuery("SELECT CI.laundryInvoiceId, CI.laundryInvoiceNumber, CI.paymentNumber, CI.sentDate, "
							+ " CI.totalCost, CI.vendorId, CI.paymentTypeId, CI.vendorPaymentStatus, V.vendorName, "
							+ " CI.paidAmount, CI.balanceAmount "
							+ " FROM UpholsterySendLaundryInvoice AS CI "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = CI.vendorId "
							+ " where CI.sentDate BETWEEN '"+dateRangeFrom+"' AND '"+dateRangeTo+"'"
							+ " AND CI.vendorId = "+vendorId+" AND CI.paymentTypeId = "+PaymentTypeConstant.PAYMENT_TYPE_CREDIT
							+ " AND CI.companyId = "+companyId+" AND  CI.markForDelete = 0  "
							+ " AND (CI.vendorPaymentStatus = " + FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID + " "
							+ " OR CI.vendorPaymentStatus = " + FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID + "AND CI.balanceAmount > 0 ) ORDER BY CI.sentDate DESC "
							 , Object[].class);
							
			
			results = typedQuery.getResultList();
			
			if(results != null && !results.isEmpty()) {
				clothInvoiceList	=	new ArrayList<>();
				for (Object[] result : results) {
					
					clothInvoice = new UpholsterySendLaundryInvoiceDto();
				
					clothInvoice.setLaundryInvoiceId((Long) result[0]);
					clothInvoice.setLaundryInvoiceNumber((Long) result[1]);
					clothInvoice.setPaymentNumber((String) result[2]);
					clothInvoice.setSentDate((Date) result[3]);
					if(result[4] != null)
					clothInvoice.setTotalCost(Double.parseDouble(toFixedTwo.format(result[4])));
					clothInvoice.setVendorId((Integer) result[5]);
					clothInvoice.setPaymentTypeId((short) result[6]);
					clothInvoice.setVendorPaymentStatus((short) result[7]);
					clothInvoice.setVendorName((String) result[8]);
					if(result[9] != null)
					clothInvoice.setPaidAmount(Double.parseDouble(toFixedTwo.format( result[9])));
					if(result[10] != null)
					clothInvoice.setBalanceAmount(Double.parseDouble(toFixedTwo.format(result[10])));
					
					clothInvoice.setVendorPaymentStatusStr(FuelVendorPaymentMode.getPaymentMode(clothInvoice.getVendorPaymentStatus()));
					clothInvoice.setPaymentType(PaymentTypeConstant.getPaymentTypeName(clothInvoice.getPaymentTypeId()));
					clothInvoice.setInvoiceDateStr(format.format(clothInvoice.getSentDate()));
					clothInvoice.setVendorId(clothInvoice.getVendorId());
					if(clothInvoice.getVendorName() == null)
						clothInvoice.setVendorName("--");
					
					clothInvoiceList.add(clothInvoice);
					
				}
			}
			
			return clothInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			clothInvoiceList		= null;
			clothInvoice 			= null;
			typedQuery 				= null;
			results 				= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> getVehicleLaundryExpListForTally(String fromDate, String toDate, Integer companyId,
			String tallyCompany) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT VL.vehicleLaundryDetailsId, WO.laundryInvoiceNumber, WO.sentDate, "
					+ " VH.vehicle_registration, VH.ledgerName, TC.companyName, WO.creationDate, VL.vid, "
					+ " SCD.clothTotal, WO.isPendingForTally, WO.paymentTypeId, SCD.quantity, VL.quantity , V.vendorName, WO.paymentNumber"
					+ " FROM UpholsterySendLaundryInvoice WO "
					+ " INNER JOIN VehicleLaundryDetails VL ON VL.laundryInvoiceId = WO.laundryInvoiceId"
					+ " INNER JOIN SentLaundryClothDetails SCD ON SCD.laundryClothDetailsId = VL.laundryClothDetailsId"
					+ " INNER JOIN Vehicle VH ON VH.vid = VL.vid "
					+ " INNER JOIN Vendor V ON V.vendorId = WO.vendorId"
					+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = WO.tallyCompanyId AND TC.companyName = '"+tallyCompany+"'"
					+ " WHERE WO.sentDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND WO.companyId = "+companyId+" AND WO.markForDelete = 0 AND SCD.clothTotal > 0" );
				
			
			List<Object[]> results = null;
			try {
				results = query.getResultList();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			List<TripSheetExpenseDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<TripSheetExpenseDto>();
				TripSheetExpenseDto select;
				for (Object[] vehicle : results) {
					if (vehicle != null) {
						select = new TripSheetExpenseDto();
						select.setTripExpenseID((Long) vehicle[0]);
						select.setTripSheetNumber((Long) vehicle[1]);
						select.setVoucherDate(tallyFormat.format((java.util.Date)vehicle[2]));
						select.setVehicle_registration((String) vehicle[3]);
						select.setLedgerName((String) vehicle[4]);
						select.setTallyCompanyName((String) vehicle[5]);
						select.setCreated((Date) vehicle[6]);
						select.setVid((Integer) vehicle[7]);
						select.setExpenseAmount((Double) vehicle[8]);
						select.setPendingForTally((boolean) vehicle[9]);
						select.setExpenseFixedId((short) vehicle[10]);
						select.setExpenseAmount((select.getExpenseAmount()/(Double) vehicle[11]) * (Double) vehicle[12]);
						select.setVendorName((String) vehicle[13]);
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_LAUNDRY);
						select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
						select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
						
						select.setTripSheetNumberStr("LI-"+select.getTripSheetNumber());
						select.setTripSheetId(select.getTripExpenseID());
						select.setExpenseName("Laundry Expenses");
						
						
						if(select.getExpenseFixedId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
							select.setCredit(true);
						}else {
							select.setCredit(false);
						}
						
						if(select.getVendorName() == null ) {
							select.setVendorName("--");
						}
						if(select.getCreated() != null ) {
							select.setCreatedStr(sqlDateFormat.format(select.getCreated()));
						}
						
						
						
						 select.setRemark("Laundry Sent On Vehicle "+select.getVehicle_registration()+" Date: "+sqlDateFormat.format((java.util.Date)vehicle[2]));
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						
						Dtos.add(select);
					}
				}
				
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ClothInvoice getClothInvoiceByInvoiceNumber(String invoiceNumber, Integer companyId)throws Exception{
		try {
			
			javax.persistence.Query query = entityManager.createQuery(
					"SELECT CI.clothInvoiceId, CI.clothInvoiceNumber FROM ClothInvoice  as CI WHERE "
					+ " CI.invoiceNumber = :invoiceNumber  AND CI.companyId ="+companyId+" AND  CI.markForDelete = 0");
		
			
			Object[] result = null;
			try {
				query.setParameter("invoiceNumber", invoiceNumber);
				query.setMaxResults(1);
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			ClothInvoice	clothInvoice = null;
			if (result != null) {
				clothInvoice = new ClothInvoice();
				clothInvoice.setClothInvoiceId((Long) result[0]);
			}
			return clothInvoice;
		} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
		
}
}
