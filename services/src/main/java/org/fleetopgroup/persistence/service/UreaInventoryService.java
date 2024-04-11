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
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.constant.TallyVoucherTypeContant;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.PendingVendorPaymentBL;
import org.fleetopgroup.persistence.bl.UreaInventoryBL;
import org.fleetopgroup.persistence.dao.UreaEntriesRepository;
import org.fleetopgroup.persistence.dao.UreaInvoiceRepository;
import org.fleetopgroup.persistence.dao.UreaInvoiceToDetailsRepository;
import org.fleetopgroup.persistence.dao.UserAlertNotificationsRepository;
import org.fleetopgroup.persistence.document.UreaInvoiceDocument;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.UreaInvoiceDto;
import org.fleetopgroup.persistence.dto.UreaInvoiceToDetailsDto;
import org.fleetopgroup.persistence.model.UreaEntries;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.UreaInvoice;
import org.fleetopgroup.persistence.model.UreaInvoiceSequenceCounter;
import org.fleetopgroup.persistence.model.UreaInvoiceToDetails;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IUreaInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IUreaInvoiceSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IUreaInvoiceToDetailsService;
import org.fleetopgroup.web.util.ByteConvertor;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UreaInventoryService implements IUreaInventoryService {
	
	@PersistenceContext EntityManager entityManager;
	
	@Autowired	IUreaInvoiceSequenceService		ureaInvoiceSequenceService;
	@Autowired	UreaInvoiceRepository			ureaInvoiceRepository;
	@Autowired	UreaInvoiceToDetailsRepository	ureaInvoiceToDetailsRepository;
	@Autowired	IPartLocationsService			partLocationsService;
	@Autowired	IUreaInvoiceToDetailsService	ureaInvoiceToDetailsService;
	@Autowired private ISequenceCounterService	sequenceCounterService;
	@Autowired private MongoTemplate			mongoTemplate;
	@Autowired ICompanyConfigurationService				companyConfigurationService;
	@Autowired UserAlertNotificationsRepository     	userAlertNotificationsRepository;
	@Autowired private  IPendingVendorPaymentService	pendingVendorPaymentService;
	@Autowired	UreaEntriesRepository	ureaEntriesRepository;
	@Autowired	IBankPaymentService	bankPaymentService;
	@Autowired	ICashPaymentService	cashPaymentService;
	
	
	UreaInventoryBL		inventoryBL	= new UreaInventoryBL();
	PartLocationsBL 	PLBL 		= new PartLocationsBL();
	
	SimpleDateFormat tallyFormat		= new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat sqlDateFormat 	= new SimpleDateFormat("dd-MM-yyyy");
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	
	
	private static final int PAGE_SIZE = 10;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject saveUreaInventoryDetails(ValueObject valueObject, MultipartFile file) throws Exception {
		
		UreaInvoiceSequenceCounter			sequenceCounter						= null;
		ValueObject							outObject							= null;
		CustomUserDetails					userDetails							= null;
		ArrayList<ValueObject> 				dataArrayObjColl 					= null;
		List<UreaInvoiceToDetails>			ureaInvoiceToDetailsList			= null;
		try {
			outObject 		= new ValueObject();
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			sequenceCounter	= ureaInvoiceSequenceService.findNextInvoiceNumber(userDetails.getCompany_id());
			
			if(sequenceCounter == null) {
				outObject.put("saveMessage", "Sequence not found please contact to system Administrator !");
				return outObject;
			}
			
			UreaInvoice	ureaInvoice	= inventoryBL.getUreaInvoiceDto(valueObject,file);
			
			ureaInvoice.setUreaInvoiceNumber(sequenceCounter.getNextVal());
			
			UreaInvoice	savedUreaInvoice = ureaInvoiceRepository.save(ureaInvoice);
			
			valueObject.put("invoiceId", ureaInvoice.getUreaInvoiceId());
			
			
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("ureaDetails");
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				ureaInvoiceToDetailsList	= new ArrayList<>();
				
				for (ValueObject object : dataArrayObjColl) {
					ureaInvoiceToDetailsList.add(inventoryBL.getUreaInvoiceToDetails(object, valueObject));
				}
				
				ureaInvoiceToDetailsRepository.saveAll(ureaInvoiceToDetailsList);
				
				
			}
			
			if(ureaInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
				  PendingVendorPayment	payment	=	PendingVendorPaymentBL.createPendingVendorPaymentDTOForUI(ureaInvoice);
				  pendingVendorPaymentService.savePendingVendorPayment(payment);
			}
			
			if(file != null && !file.isEmpty()|| valueObject.getString("base64String",null) != null) {
				saveUreaDocument(savedUreaInvoice, file,valueObject);
			}
			
			if(valueObject.getBoolean("allowBankPaymentDetails",false) && (PaymentTypeConstant.getPaymentTypeIdList().contains(savedUreaInvoice.getPaymentTypeId()) || savedUreaInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)){
				ValueObject bankPaymentValueObject=JsonConvertor.toValueObjectFormSimpleJsonString(valueObject.getString("bankPaymentDetails"));
					bankPaymentValueObject.put("bankPaymentTypeId",savedUreaInvoice.getPaymentTypeId());
					bankPaymentValueObject.put("userId",userDetails.getId());
					bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
					bankPaymentValueObject.put("moduleId",savedUreaInvoice.getUreaInvoiceId());
					bankPaymentValueObject.put("moduleNo", savedUreaInvoice.getUreaInvoiceNumber());
					bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.UREA_INVENTORY);
					bankPaymentValueObject.put("amount",savedUreaInvoice.getInvoiceAmount());
					//bankPaymentValueObject.put("paidDate", savedUreaInvoice.getInvoiceDate());
					if(savedUreaInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)
						cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
					else
						bankPaymentService.addBankPaymentDetailsFromValueObject(bankPaymentValueObject);
				}
			
			return valueObject;
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			throw e;
		}finally {
			outObject					= null;
			sequenceCounter				= null;
			userDetails					= null;
			ureaInvoiceToDetailsList	= null;
		}
	}

	@Override
	public ValueObject getUreaInvoiceList(ValueObject valueObject) throws Exception {
		CustomUserDetails	   userDetails				= null;
		Integer				   pageNumber				= null;
		try {

			
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber				= valueObject.getInt("pageNumber",0);
			Page<UreaInvoice> page  = getDeployment_Page_UreaInvoice(pageNumber, userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());

			List<UreaInvoiceDto> pageList = getUreaInvoiceDtoList(pageNumber, userDetails.getCompany_id());

			valueObject.put("UreaInvoice", pageList);
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
	public Page<UreaInvoice> getDeployment_Page_UreaInvoice(Integer pageNumber, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "ureaInvoiceId");
		return ureaInvoiceRepository.getDeployment_Page_UreaInvoice(companyId, pageable);
	}
	
	@Override
	public List<UreaInvoiceDto> getUreaInvoiceDtoList(Integer pageNumber, Integer companyId) throws Exception {

		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]> 					resultList 				= null; 
		List<UreaInvoiceDto> 			clothInvoiceList 		= null;
		UreaInvoiceDto 					clothInvoiceDto			= null;

		try {

			typedQuery = entityManager.createQuery("SELECT BI.ureaInvoiceId, BI.ureaInvoiceNumber, BI.vendorId, V.vendorName, BI.wareHouseLocation,"
					+ " PL.partlocation_name, BI.invoiceNumber, BI.invoiceDate, BI.invoiceAmount, U.firstName , U.lastName, "
					+ " BI.quantity, BI.urea_document, BI.urea_document_id, BI.subLocationId, PSL.partlocation_name, BI.paymentTypeId, BI.vendorPaymentStatus "
					+ " FROM UreaInvoice AS BI"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BI.wareHouseLocation"
					+ " LEFT JOIN Vendor AS V ON V.vendorId = BI.vendorId "
					+ " INNER JOIN User U ON U.id = BI.createdById "
					+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = BI.subLocationId "
					+ " WHERE BI.companyId ="+companyId+" AND BI.markForDelete = 0 ORDER BY BI.ureaInvoiceId DESC", Object[].class);

			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				clothInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					clothInvoiceDto = new UreaInvoiceDto();
					
					clothInvoiceDto.setUreaInvoiceId((Long) result[0]);
					clothInvoiceDto.setUreaInvoiceNumber((Long) result[1]);
					clothInvoiceDto.setVendorId((Integer) result[2]);
					clothInvoiceDto.setVendorName((String) result[3]);
					clothInvoiceDto.setWareHouseLocation((Integer) result[4]);
					clothInvoiceDto.setLocationName((String) result[5]);
					clothInvoiceDto.setInvoiceNumber((String) result[6]);
					clothInvoiceDto.setInvoiceDate((Timestamp) result[7]);
					clothInvoiceDto.setInvoiceAmount((Double) result[8]);
					clothInvoiceDto.setCreatedBy((String) result[9]+"_"+(String) result[10]);
					clothInvoiceDto.setQuantity((Double) result[11]);
				

					if(clothInvoiceDto.getInvoiceDate() != null) {
						clothInvoiceDto.setInvoiceDateStr(DateTimeUtility.getDateFromTimeStamp(clothInvoiceDto.getInvoiceDate(), DateTimeUtility.DD_MM_YYYY));
					}
					if(clothInvoiceDto.getVendorName() == null) {
						clothInvoiceDto.setVendorName("--");
					}
					clothInvoiceDto.setUrea_document((boolean) result[12]);
					if(result[13] != null) {
						clothInvoiceDto.setUrea_document_id((Long)result[13]);
					}
					if(result[14] != null) {
						clothInvoiceDto.setSubLocationId((Integer)result[14]);
						clothInvoiceDto.setSubLocation((String)result[15]);
					}else {
						clothInvoiceDto.setSubLocation("");
					}
					clothInvoiceDto.setPaymentTypeId((short)result[16]);
					clothInvoiceDto.setVendorPaymentStatus((short)result[17]);
					clothInvoiceDto.setVendorPaymentStatusStr(FuelVendorPaymentMode.getPaymentMode((short)result[17]));
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
	public Page<UreaInvoice> getDeployment_Page_UreaInvoice(String term, Integer pageNumber, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "ureaInvoiceId");
		return ureaInvoiceRepository.getDeployment_Page_UreaInvoice(term, companyId, pageable);
	}
	
	@Override
	public List<UreaInvoiceDto> getUreaInvoiceDtoList(String term, Integer pageNumber, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]> 					resultList 				= null; 
		List<UreaInvoiceDto> 			clothInvoiceList 		= null;
		UreaInvoiceDto 					clothInvoiceDto			= null;

		try {
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			typedQuery = entityManager.createQuery("SELECT BI.ureaInvoiceId, BI.ureaInvoiceNumber, BI.vendorId, V.vendorName, BI.wareHouseLocation,"
					+ " PL.partlocation_name, BI.invoiceNumber, BI.invoiceDate, BI.invoiceAmount, U.firstName , U.lastName, BI.quantity"
					+ " FROM UreaInvoice AS BI"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BI.wareHouseLocation"
					+ " LEFT JOIN Vendor AS V ON V.vendorId = BI.vendorId "
					+ " INNER JOIN User U ON U.id = BI.createdById "
					+ " WHERE lower(BI.invoiceNumber) like '%"+term+"%' AND  BI.companyId ="+companyId+" AND BI.markForDelete = 0 ORDER BY BI.ureaInvoiceId DESC", Object[].class);

			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				clothInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					clothInvoiceDto = new UreaInvoiceDto();
					
					clothInvoiceDto.setUreaInvoiceId((Long) result[0]);
					clothInvoiceDto.setUreaInvoiceNumber((Long) result[1]);
					clothInvoiceDto.setVendorId((Integer) result[2]);
					clothInvoiceDto.setVendorName((String) result[3]);
					clothInvoiceDto.setWareHouseLocation((Integer) result[4]);
					clothInvoiceDto.setLocationName((String) result[5]);
					clothInvoiceDto.setInvoiceNumber((String) result[6]);
					clothInvoiceDto.setInvoiceDate((Timestamp) result[7]);
					clothInvoiceDto.setInvoiceAmount((Double) result[8]);
					clothInvoiceDto.setCreatedBy((String) result[9]+"_"+(String) result[10]);
					clothInvoiceDto.setQuantity((Double) result[11]);
				

					if(clothInvoiceDto.getInvoiceDate() != null) {
						clothInvoiceDto.setInvoiceDateStr(DateTimeUtility.getDateFromTimeStamp(clothInvoiceDto.getInvoiceDate(), DateTimeUtility.DD_MM_YYYY));
					}
					if(clothInvoiceDto.getVendorName() == null) {
						clothInvoiceDto.setVendorName("--");
					}
					
					clothInvoiceList.add(clothInvoiceDto);
				}
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
	public ValueObject getUreaInvoiceDetails(ValueObject valueObject) throws Exception {
		UreaInvoiceDto 						ureaInvoiceDto				= null;
		CustomUserDetails					userDetails					= null;
		List<UreaInvoiceToDetailsDto>		ureaInvoiceToDetailsDtos	= null;
		try {
			
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			ureaInvoiceDto	   			= getUreaInvoiceDetails(valueObject.getLong("invoiceId", 0), userDetails.getCompany_id());
			ureaInvoiceToDetailsDtos    = ureaInvoiceToDetailsService.getUreaInvoiceToDetailsDtoList(valueObject.getLong("invoiceId", 0), userDetails.getCompany_id());
			
			valueObject.clear();
			valueObject.put("ureaInvoiceDto", ureaInvoiceDto);
			valueObject.put("ureaInvoiceToDetailsDtos", ureaInvoiceToDetailsDtos);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			ureaInvoiceDto					= null;
			userDetails						= null;
			ureaInvoiceToDetailsDtos		= null;
		}
	}
	
	@Override
	public UreaInvoiceDto getUreaInvoiceDetails(Long invoiceId, Integer companyId) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT CI.ureaInvoiceId, CI.ureaInvoiceNumber, CI.wareHouseLocation, CI.purchaseOrderId, CI.invoiceNumber, CI.invoiceDate, CI.invoiceAmount, CI.vendorId,"
				+ " CI.paymentTypeId, CI.paymentNumber, CI.totalAmount, CI.description, CI.vendorPaymentStatus, CI.ureaApprovalId, CI.vendorPaymentDate, CI.createdById,"
				+ " CI.lastModifiedById, CI.createdOn, CI.lastModifiedBy, CI.poNumber, PL.partlocation_name, U.firstName, U2.firstName, PO.purchaseorder_Number,"
				+ " V.vendorName, V.vendorLocation, CI.tallyCompanyId, TC.companyName, CI.subLocationId, PSL.partlocation_name,VSA.approvalId,VA.approvalNumber   "
				+ " FROM UreaInvoice CI "
				+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CI.wareHouseLocation "
				+ " INNER JOIN User U ON U.id = CI.createdById"
				+ " INNER JOIN User U2 ON U2.id = CI.lastModifiedById "
				+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = CI.purchaseOrderId"
				+ " LEFT JOIN Vendor V ON V.vendorId = CI.vendorId "
				+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = CI.tallyCompanyId"
				+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = CI.subLocationId "
				+ " LEFT JOIN VendorSubApprovalDetails VSA ON VSA.invoiceId = CI.ureaInvoiceId AND VSA.approvalPlaceId = "+ApprovalType.APPROVAL_TYPE_INVENTORY_UREA_INVOICE+" "
				+ " LEFT JOIN VendorApproval VA ON VA.approvalId = VSA.approvalId "
				+ " where CI.ureaInvoiceId = "+invoiceId+" AND CI.companyId = "+companyId+" AND CI.markForDelete = 0");

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		UreaInvoiceDto select;
		if (result != null) {
			select = new UreaInvoiceDto();
			
			select.setUreaInvoiceId((Long) result[0]);
			select.setUreaInvoiceNumber((Long) result[1]);
			select.setWareHouseLocation((Integer) result[2]);
			select.setPurchaseOrderId((Long) result[3]);
			select.setInvoiceNumber((String) result[4]);
			select.setInvoiceDate((Timestamp) result[5]);
			select.setInvoiceAmount(Double.parseDouble(toFixedTwo.format(result[6])));
			select.setVendorId((Integer) result[7]);
			select.setPaymentTypeId((short) result[8]);
			select.setPaymentType(PaymentTypeConstant.getPaymentTypeName(select.getPaymentTypeId()));
			select.setPaymentNumber((String) result[9]);
			select.setTotalAmount((Double) result[10]);
			select.setDescription((String) result[11]);
			select.setVendorPaymentStatus((short) result[12]);
			select.setVendorPaymentStatusStr(FuelVendorPaymentMode.getPaymentMode((short) result[12]));
			select.setUreaApprovalId((Long) result[13]);
			select.setVendorPaymentDate((Timestamp) result[14]);
			select.setCreatedById((Long) result[15]);
			select.setLastModifiedById((Long) result[16]);
			select.setCreatedOn((Timestamp) result[17]);
			select.setLastModifiedBy((Timestamp) result[18]);
			select.setPoNumber((String) result[19]);
			select.setLocationName((String) result[20]);
			select.setCreatedBy((String) result[21]);
			select.setLastUpdatedBy((String) result[22]);
			select.setPurchaseOrderNumber((Long) result[23]);
			select.setVendorName((String) result[24]);
			select.setVendorLocation((String) result[25]);
			if(result[26] != null)
				select.setTallyCompanyId((Long) result[26]);
			if(result[27] != null)
				select.setTallyCompanyName((String) result[27]);
			
			
			select.setPaymentType(PaymentTypeConstant.getPaymentTypeName(select.getPaymentTypeId()));
			if(select.getInvoiceDate() != null)
				select.setInvoiceDateStr(DateTimeUtility.getDateFromTimeStamp(select.getInvoiceDate(), DateTimeUtility.DD_MM_YYYY_HH_MM_AA));
			if(select.getCreatedOn() != null)
				select.setCreatedOnStr(DateTimeUtility.getDateFromTimeStamp(select.getCreatedOn(), DateTimeUtility.DD_MM_YYYY_HH_MM_AA));
			if(select.getLastModifiedBy() != null)
				select.setLastModifiedOnStr(DateTimeUtility.getDateFromTimeStamp(select.getLastModifiedBy(), DateTimeUtility.DD_MM_YYYY_HH_MM_AA));
			if(result[28] != null) {
				select.setSubLocationId((Integer) result[28]);
				select.setSubLocation((String) result[29]);
			}
			if(result[30] != null) {
				select.setApprovalId((Long) result[30]);
				select.setApprovalNumber("A-"+(Long) result[31]);
			}
			
		} else {
			return null;
		}

		return select;
	}
	
	@Override
	@Transactional
	public ValueObject deleteUreaInvoice(ValueObject valueObject) throws Exception {
		List<UreaInvoiceToDetails>		ureaInventoryDetails			= null;
		CustomUserDetails				userDetails						= null;
		UreaEntries						ureaEntries						= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ureaEntries = ureaEntriesRepository.getUreaEntryByUreaInvoiceId(valueObject.getLong("invoiceId",0), valueObject.getInt("companyId", 0));
			
			if(ureaEntries != null) {
				valueObject.put("ureaEntryFound", true);
				return valueObject;
			}
			
			ureaInventoryDetails	= ureaInvoiceToDetailsService.getUreaInvoiceToDetailsByUreaInvoiceId(valueObject.getLong("invoiceId",0));
			UreaInvoice	ureaInvoice	= getUreaInvoiceByInvoiceId(valueObject.getLong("invoiceId"), userDetails.getCompany_id());
			if(ureaInventoryDetails == null || ureaInventoryDetails.isEmpty() ) {
				entityManager.createQuery("UPDATE UreaInvoice SET markForDelete = 1, lastModifiedById = "+userDetails.getId()+" "
						+ " , lastModifiedBy = '"+DateTimeUtility.getCurrentTimeStamp()+"' where ureaInvoiceId = "+valueObject.getLong("invoiceId", 0)+" ").executeUpdate();
				valueObject.put("deleted", true);
				ureaInvoiceToDetailsRepository.deleteUreaInventoryDetailsByUreaInvoiceId(valueObject.getLong("invoiceId"));
				
				if(ureaInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && ureaInvoice.getInvoiceAmount() > 0) {
					pendingVendorPaymentService.deletePendingVendorPaymentAmt(ureaInvoice.getUreaInvoiceId(), PendingPaymentType.PAYMENT_TYPE_UREA_INVOICE, ureaInvoice.getInvoiceAmount());
				}
				bankPaymentService.deleteBankPaymentDetailsIfTransactionDeleted(ureaInvoice.getUreaInvoiceId(), ModuleConstant.UREA_INVENTORY, userDetails.getCompany_id(),userDetails.getId(),false);
			}else {
				valueObject.put("deleted", false);
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteUreaInventoryDetails(ValueObject valueObject) throws Exception {
		UreaInvoiceToDetails		clothInventoryDetails		= null;
		UreaEntries					ureaEntries					= null;
		try {
			ureaEntries = ureaEntriesRepository.getUreaEntryByUreaInvoiceToDetailId(valueObject.getLong("ureaInvoiceToDetailsId", 0), valueObject.getInt("companyId", 0));
			if(ureaEntries != null) {
				valueObject.put("ureaEntryFound", true);
				return valueObject;
			}
			clothInventoryDetails	= ureaInvoiceToDetailsRepository.findById(valueObject.getLong("ureaInvoiceToDetailsId", 0)).get();
			ureaInvoiceToDetailsService.removeUreaInventoryDetailsFromInvoice(clothInventoryDetails);
			ureaInvoiceToDetailsRepository.deleteUreaInventoryDetails(valueObject.getLong("ureaInvoiceToDetailsId", 0));
			
			UreaInvoice	ureaInvoice	= getUreaInvoiceByInvoiceId(clothInventoryDetails.getUreaInvoiceId(), clothInventoryDetails.getCompanyId());
			
			if(ureaInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && ureaInvoice.getInvoiceAmount() > 0) {
				pendingVendorPaymentService.deletePendingVendorPaymentAmt(ureaInvoice.getUreaInvoiceId(), PendingPaymentType.PAYMENT_TYPE_UREA_INVOICE, clothInventoryDetails.getTotal());
			}
			CustomUserDetails userDetails = Utility.getObject(null);
			HashMap<String,Object> companyConfiguration =companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG );
			if((boolean) companyConfiguration.getOrDefault("allowBankPaymentDetails",false)) {
			ValueObject bankPaymentValueObject=new ValueObject();
			bankPaymentValueObject.put("oldPaymentTypeId",ureaInvoice.getPaymentTypeId());
			bankPaymentValueObject.put("bankPaymentTypeId", ureaInvoice.getPaymentTypeId());
			bankPaymentValueObject.put("currentPaymentTypeId", ureaInvoice.getPaymentTypeId());
			bankPaymentValueObject.put("userId",userDetails.getId());
			bankPaymentValueObject.put("companyId",userDetails.getCompany_id());
			bankPaymentValueObject.put("moduleId",ureaInvoice.getUreaInvoiceId());
			bankPaymentValueObject.put("moduleNo", ureaInvoice.getUreaInvoiceNumber());
			bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.UREA_INVENTORY);
			bankPaymentValueObject.put("amount",ureaInvoice.getInvoiceAmount());
			bankPaymentService.updatePaymentDetailsFromValuObject(bankPaymentValueObject);
			}
			
			valueObject.put("delete", true);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			clothInventoryDetails		= null;
		}
	}
	
	
	@Override
	public ValueObject searchUreaInvoiceByNumber(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails		= null;
		Long						invoiceId		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			invoiceId	=	ureaInvoiceRepository.searchUreaInvoiceByNumber(valueObject.getLong("ureaInvoiceNumber", 0), userDetails.getCompany_id());
			if(invoiceId != null)
				valueObject.put("invoiceId", invoiceId);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject searchUreaInvoice(ValueObject valueObject) throws Exception {
		CustomUserDetails	   userDetails				= null;
		Integer				   pageNumber				= null;
		String				   term						= null;
		try {

			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber			= valueObject.getInt("pageNumber",1);
			term				= valueObject.getString("term");
			
			Page<UreaInvoice> page  = getDeployment_Page_UreaInvoice(term, pageNumber, userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());

			List<UreaInvoiceDto> pageList = getUreaInvoiceDtoList(term, pageNumber, userDetails.getCompany_id());

			valueObject.put("UreaInvoice", pageList);
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
	public ValueObject locationStockDetails(ValueObject valueObject) throws Exception {
		Integer							 locationId			= null;
		List<UreaInvoiceToDetailsDto> 	 detailsDtos		= null;
		CustomUserDetails				 userDetails		= null;
		String 							 query				= "";
		try {
			locationId	= valueObject.getInt("locationId", 0);
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(locationId > 0) {
				detailsDtos	= ureaInvoiceToDetailsService.getLocationUreaStockDetails(locationId, userDetails.getCompany_id());
				valueObject.put("location", partLocationsService.getPartLocations(locationId).getPartlocation_name());
			}else {
				
				query =" group by CD.manufacturerId ";
				
				detailsDtos	= ureaInvoiceToDetailsService.getAllLocationUreaStockDetails(userDetails.getCompany_id(),query);
			}
			
			valueObject.put("detailsDtos", detailsDtos);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	@Transactional
	public void updateUreaInvoice(UreaInvoiceDto ureaInvoiceDto) throws Exception {
		try {
			entityManager.createQuery("Update UreaInvoice SET wareHouseLocation = "+ureaInvoiceDto.getWareHouseLocation()+" "
					+ ", poNumber = '"+ureaInvoiceDto.getPoNumber()+"', invoiceNumber = '"+ureaInvoiceDto.getInvoiceNumber()+"' "
					+ ", invoiceDate = '"+ureaInvoiceDto.getInvoiceDate()+"' , invoiceAmount = "+ureaInvoiceDto.getInvoiceAmount()+""
					+ ", vendorId = "+ureaInvoiceDto.getVendorId()+" , description = '"+ureaInvoiceDto.getDescription()+"', vendorPaymentStatus="+ureaInvoiceDto.getVendorPaymentStatus()+""
					+ ", lastModifiedById = "+ureaInvoiceDto.getLastModifiedById()+", lastModifiedBy = '"+ureaInvoiceDto.getLastModifiedBy()+"'"
					+ ", tallyCompanyId = "+ureaInvoiceDto.getTallyCompanyId()+",urea_document = "+ureaInvoiceDto.isUrea_document()+" ,paymentTypeId ="+ureaInvoiceDto.getPaymentTypeId()+" , sublocationId ="+ureaInvoiceDto.getSubLocationId()+"  "
					+ " where ureaInvoiceId = "+ureaInvoiceDto.getUreaInvoiceId()+" AND companyId = "+ureaInvoiceDto.getCompanyId()+" ").executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	@Override
	@Transactional
	public Map<String, Object> addMoreUreaInventoryInvoice(ValueObject valueObject) throws Exception {
		Map<String, Object>	 		model 						= null;
		List<UreaInvoiceToDetails> 	ureaInvoiceToDetailsList 	= null;
		UreaInvoiceToDetails		ureaInvoiceToDetails	 	= null;
		UreaInvoice					ureaInvoice				 	= null;
		Timestamp 					sqlDate 					= null;
		java.util.Date 				lastModifiedOn 				= null;
		CustomUserDetails			userDetails 				= null;
		final 						String[] ureaManufacturerArr;
		final 						String[] quantityArr;
		final 						String[] unitpriceArr;
		final 						String[] discountArr;
		final 						String[] taxArr;
		final 						String[] totalCostArr;
		long 						invoiceId 					= 0;
		Integer 					vendorId 					= 0;
		Integer 					locationId 					= 0;
		long 						updateQuantity 				= 0;
		double						updateCost					= 0.0;
		long 						manufacturerId 				= 0;
		double 						quantity 					= 0;
		double 						cost 						= 0;
		double 						discount 					= 0;
		double 						tax 						= 0;
		double 						total 						= 0;
		
		try {
			 model = new HashMap<String, Object>();
		if(valueObject != null) {
			invoiceId 			= valueObject.getLong("InvoiceId");
			vendorId 			= valueObject.getInt("vendorId");
			locationId 			= valueObject.getInt("locationId");
			ureaManufacturerArr = (String[]) valueObject.get("ureaManufacturer");
			quantityArr	 		= (String[]) valueObject.get("quantity");
			unitpriceArr	 	= (String[]) valueObject.get("unitprice");
			discountArr	 		= (String[]) valueObject.get("discount");
			taxArr		 	 	= (String[]) valueObject.get("tax");
			totalCostArr	 	= (String[]) valueObject.get("tatalcost");
		
			lastModifiedOn 		= new java.util.Date();
			sqlDate 			= new java.sql.Timestamp(lastModifiedOn.getTime());
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			ureaInvoice 	= getUreaInvoiceByInvoiceId(invoiceId,userDetails.getCompany_id());
			ureaInvoiceToDetailsList 	= new ArrayList<UreaInvoiceToDetails>();
			
			if(ureaInvoice != null) {
				for(int i =0; i< ureaManufacturerArr.length; i++) {
					/*Update value start */
					updateQuantity += Long.parseLong(quantityArr[i]);//total-Quantity Updated In UreaInvoiceTable
					updateCost	   += Double.parseDouble(totalCostArr[i]);//total-Cost Updated In UreaInvoiceTable
					/*update value end */
					
					/*add Values*/
					quantity		= Double.parseDouble(quantityArr[i]);
					cost 			= Double.parseDouble(unitpriceArr[i]);
					discount 		= Double.parseDouble(discountArr[i]);
					tax				= Double.parseDouble(taxArr[i]);
					total 			= Double.parseDouble(totalCostArr[i]);
					if(!ureaManufacturerArr[i].trim().equals("")) {
						manufacturerId	= Long.parseLong(ureaManufacturerArr[i]);
					}else {
						manufacturerId = 0;
					}
					
					ureaInvoiceToDetails		= new UreaInvoiceToDetails();
					
					ureaInvoiceToDetails.setCompanyId(userDetails.getCompany_id());
					ureaInvoiceToDetails.setLastupdated(sqlDate);
					ureaInvoiceToDetails.setLastModifiedById(userDetails.getId());
					ureaInvoiceToDetails.setManufacturerId(manufacturerId);
					ureaInvoiceToDetails.setQuantity(quantity);
					ureaInvoiceToDetails.setDiscount(discount);
					ureaInvoiceToDetails.setUnitprice(cost);
					ureaInvoiceToDetails.setTax(tax);
					ureaInvoiceToDetails.setUreaInvoiceId(invoiceId);
					ureaInvoiceToDetails.setVendor_id(vendorId);
					ureaInvoiceToDetails.setWareHouseLocation(locationId);
					ureaInvoiceToDetails.setTotal(total);
					ureaInvoiceToDetails.setCreated(new Date());
					ureaInvoiceToDetails.setCreatedById(userDetails.getId());
					ureaInvoiceToDetails.setStockQuantity(quantity);
					ureaInvoiceToDetails.setUsedQuantity(0.0);
					ureaInvoiceToDetails.setSubLocationId(ureaInvoice.getSubLocationId());
					ureaInvoiceToDetails.setTransferQuantity(0.0);
					ureaInvoiceToDetailsList.add(ureaInvoiceToDetails);
					
				}
				updateUreaInvoice(invoiceId,updateQuantity,updateCost,userDetails.getCompany_id());
				ureaInvoiceToDetailsRepository.saveAll(ureaInvoiceToDetailsList);
			}
			
			 }	
		} catch (Exception e) {
			throw e;
		}finally {
			
		}
		return model;
	}
	
	
	@Override
	@Transactional
	public void updateUreaInvoice(long  invoiceId,long finalQuantity,double finalCost,Integer companyId) throws Exception {
		try {
			entityManager.createQuery(" Update UreaInvoice SET quantity =  quantity+"+finalQuantity+","
					+ " totalAmount = totalAmount+"+finalCost+", invoiceAmount = invoiceAmount+"+ finalCost +" "
					+ " where ureaInvoiceId = "+invoiceId+" AND companyId = "+companyId+" ").executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public UreaInvoice getUreaInvoiceByInvoiceId(Long InvoiceId, Integer companyId) {	
		return ureaInvoiceRepository.getUreaInvoiceByInvoiceId(InvoiceId,companyId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> getUreaInvoiceListForTallyImport(String fromDate, String toDate, Integer companyId,
			String tallyCompany) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT SE.ureaInvoiceId, SE.ureaInvoiceNumber, VA.approvalCreateDate, V.vendorName,"
					+ " TC.companyName, SE.createdOn, SE.paymentTypeId, VSD.subApprovalpaidAmount, SE.isPendingForTally, "
					+ " SE.invoiceDate, SE.invoiceNumber"
					+ " FROM UreaInvoice SE "
					+ " INNER JOIN Vendor AS V ON V.vendorId = SE.vendorId"
					+ " INNER JOIN VendorApproval VA on VA.approvalId = SE.ureaApprovalId AND VA.markForDelete = 0"
					+ " INNER JOIN VendorSubApprovalDetails VSD ON VSD.approvalId = VA.approvalId AND VSD.invoiceId = SE.ureaInvoiceId AND VSD.approvalPlaceId = "+ApprovalType.APPROVAL_TYPE_INVENTORY_UREA_INVOICE+""
					+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = SE.tallyCompanyId AND TC.companyName = '"+tallyCompany+"'"
					+ " WHERE VA.approvalCreateDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND SE.companyId = "+companyId+" AND SE.markForDelete = 0 AND VSD.subApprovalpaidAmount > 0 AND VSD.markForDelete = 0");
			
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
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_UREA);
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
						
						 select.setRemark("Urea Invoice Date: "+sqlDateFormat.format((java.util.Date)vehicle[9])+" Invoice Number : "+(String)vehicle[10]+" From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						select.setTripSheetNumberStr("UI-"+select.getTripSheetNumber());
						
						Dtos.add(select);
					}
				}
				
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	@Override
	public void saveUreaDocument(UreaInvoice urea, MultipartFile file, ValueObject valueObject) throws Exception {
		try {
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			org.fleetopgroup.persistence.document.UreaInvoiceDocument ureaDoc = new org.fleetopgroup.persistence.document.UreaInvoiceDocument();
			ureaDoc.setUreaInvoiceId(urea.getUreaInvoiceId());
			if(file != null) {
				byte[] bytes = file.getBytes();
				ureaDoc.setUreaInvoiceFileName(file.getOriginalFilename());
				ureaDoc.setUreaInvoiceContent(bytes);
				ureaDoc.setUreaInvoiceContentType(file.getContentType());
			} else {
				if (valueObject.getString("base64String",null) != null) {
					byte[] bytes = ByteConvertor.base64ToByte(valueObject.getString("base64String",null));
					
					ureaDoc.setUreaInvoiceFileName(valueObject.getString("imageName",null));
					ureaDoc.setUreaInvoiceContent(bytes);
					ureaDoc.setUreaInvoiceContentType(valueObject.getString("imageExt",null));
				}
			}	
			ureaDoc.setMarkForDelete(false);
			ureaDoc.setCreatedById(urea.getCreatedById());
			ureaDoc.setLastModifiedById(urea.getLastModifiedById());
			ureaDoc.setCreated(toDate);
			ureaDoc.setLastupdated(toDate);
			ureaDoc.setCompanyId(urea.getCompanyId());

			addUreaInvoiceDocument(ureaDoc);
			updateUreaInvoiceDocumentId(ureaDoc.get_id(), true, ureaDoc.getUreaInvoiceId(), ureaDoc.getCompanyId());

		} catch (Exception e) {
			throw e;
		}
	}
	@Transactional
	public void addUreaInvoiceDocument(org.fleetopgroup.persistence.document.UreaInvoiceDocument ureaDoc) {
		ureaDoc.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_UREA_INVOICE_DOCUMENT));
		mongoTemplate.save(ureaDoc);
	}
	
	@Transactional
	public void updateUreaInvoiceDocumentId(Long ureaInvoiceDocId, boolean ureaDocument, Long ureaInvoiceId, Integer companyId) {
		ureaInvoiceRepository.updateUreaInvoiceDocumentId(ureaInvoiceDocId, ureaDocument, ureaInvoiceId, companyId);

	}
	
	@Override
	public org.fleetopgroup.persistence.document.UreaInvoiceDocument getUreaInvoiceDocumentDetails(long ureaDocumentId, Integer company_id)
			throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(ureaDocumentId).and("companyId").is(company_id).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.UreaInvoiceDocument.class);
	}
	
	@Override
	public UreaInvoiceDocument getUreaInvoiceDocumentDetailsByInvoiceId(Long id, Integer companyId) throws Exception {// checking already exists or not
		
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("ureaInvoiceId").is(id).and("companyId").is(companyId).and("markForDelete").is(false));

		return mongoTemplate.findOne(query, UreaInvoiceDocument.class);
	}
	
	@Transactional
	public void updateTotalTransferQuantity(Double ureaInventoryTransferQuantity, Long ureaInvoiceId, Integer locationId) throws Exception {
		try {
			entityManager.createQuery("UPDATE UreaInvoice AS UI SET UI.totalTransferQuantity  = "+ureaInventoryTransferQuantity+""
					+ " WHERE UI.ureaInvoiceId=" + ureaInvoiceId + " AND UI.wareHouseLocation = "+locationId+ " ").executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void update_Vendor_ApprovalTO_StatusUreaInvoice_ID(String approvalInvoiceID, Long approvalId,
			short approvalStatus, Integer companyId) throws Exception {
		
		entityManager.createQuery("UPDATE UreaInvoice AS UI SET UI.ureaApprovalId  = "+approvalId+" , UI.vendorPaymentStatus = "+approvalStatus+""
				+ " WHERE UI.ureaInvoiceId IN (" + approvalInvoiceID + ") ").executeUpdate();
		
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentDetails(Long approvalId, String paymentDate, short paymentStatus) throws Exception {

		entityManager.createQuery("  UPDATE UreaInvoice SET vendorPaymentDate ='" + paymentDate+"' , "
				+ " vendorPaymentStatus ="+paymentStatus+""
				+ " WHERE ureaApprovalId = "+approvalId+" ").executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception {
		entityManager.createQuery("  UPDATE UreaInvoice SET vendorPaymentDate =" + paymentDate+" , "
				+ " vendorPaymentStatus="+paymentStatus+", ureaApprovalId = "+approvalId+""
				+ " WHERE ureaInvoiceId = "+invoiceId+" ").executeUpdate();
		
	}
	
	@Override
	public List<TripSheetExpenseDto> getUreaInvoiceListForTallyImportATC(String fromDate, String toDate,
			Integer companyId, String tallyCompany) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT SE.ureaInvoiceId, SE.ureaInvoiceNumber, SE.invoiceDate, V.vendorName,"
					+ " SE.createdOn, SE.paymentTypeId, SE.invoiceAmount, SE.isPendingForTally, "
					+ " SE.invoiceDate, SE.invoiceNumber"
					+ " FROM UreaInvoice SE "
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
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_UREA);
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
						
						 select.setRemark("Urea Invoice Date: "+sqlDateFormat.format((java.util.Date)vehicle[8])+" Invoice Number : "+(String)vehicle[9]+" From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						select.setTripSheetNumberStr("UI-"+select.getTripSheetNumber());
						
						Dtos.add(select);
					}
				}
				
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
}
