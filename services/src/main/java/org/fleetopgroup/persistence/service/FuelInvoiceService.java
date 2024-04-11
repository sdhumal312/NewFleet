package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.FuelInvoiceConstant;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.bl.FuelInvoiceBL;
import org.fleetopgroup.persistence.bl.PendingVendorPaymentBL;
import org.fleetopgroup.persistence.dao.FuelInvoiceHistoryRepository;
import org.fleetopgroup.persistence.dao.FuelInvoiceRepository;
import org.fleetopgroup.persistence.dao.FuelStockDetailsRepository;
import org.fleetopgroup.persistence.dao.FuelStockHistoryRepository;
import org.fleetopgroup.persistence.dao.FuelTransferDetailsRepository;
import org.fleetopgroup.persistence.document.FuelInvoiceDocument;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.FuelInvoiceDto;
import org.fleetopgroup.persistence.dto.FuelInvoiceHistoryDto;
import org.fleetopgroup.persistence.dto.FuelStockDetailsDto;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.FuelInvoice;
import org.fleetopgroup.persistence.model.FuelInvoiceHistory;
import org.fleetopgroup.persistence.model.FuelInvoiceSequenceCounter;
import org.fleetopgroup.persistence.model.FuelStockDetails;
import org.fleetopgroup.persistence.model.FuelStockHistory;
import org.fleetopgroup.persistence.model.FuelTransferDetails;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IFuelInvoiceSequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IFuelInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
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

import com.antkorwin.xsync.XMutexFactoryImpl;

@Service
public class FuelInvoiceService implements IFuelInvoiceService {
	
	@PersistenceContext EntityManager entityManager;
	
	@Autowired private	IFuelService							fuelService;
	@Autowired private	IFuelInvoiceSequenceCounterService		fuelSequenceCounterService;
	@Autowired private	FuelInvoiceRepository					fuelInvoiceRepository;
	@Autowired private  ISequenceCounterService					sequenceCounterService;
	@Autowired private  MongoTemplate							mongoTemplate;
	@Autowired private	FuelStockDetailsRepository				fuelStockDetailsRepository;
	@Autowired private	FuelStockHistoryRepository				fuelStockHistoryRepository;
	@Autowired private	FuelInvoiceHistoryRepository			fuelInvoiceHistoryRepository;
	@Autowired private	IPendingVendorPaymentService			pendingVendorPaymentService;
	@Autowired private FuelTransferDetailsRepository			fuelTransferDetailsRepository;
	@Autowired private IBankPaymentService						bankPaymentService;
	@Autowired private ICashPaymentService						cashPaymentService;
	@Autowired private IVendorService							vendorService;
	
	
	SimpleDateFormat	dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat	sqlFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat	format	   = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	DecimalFormat  toFixedTwo = new DecimalFormat("#.##");
	
	XMutexFactoryImpl<Integer> xMutexFactory = new XMutexFactoryImpl<Integer>();
	
	XMutexFactoryImpl<Long> xMutexFact = new XMutexFactoryImpl<Long>();
	
	private static final int PAGE_SIZE = 10;
	
	@Override
	@Transactional
	public ValueObject saveFuelInventoryDetails(ValueObject valueInObject) throws Exception {
		FuelInvoice						fuelInvoice			= null;
		FuelInvoiceSequenceCounter		sequenceCounter		= null;
		MultipartFile 					file				= null;
		FuelStockDetails				fuelStockDetails	= null;
		FuelStockHistory				fuelStockHistory	= null;
		ValueObject						valueOutObject		= null;
		FuelInvoice						savedFuelInvoice	= null;
		try {
			valueOutObject	= new ValueObject();
			sequenceCounter	= fuelSequenceCounterService.findNextNumber(valueInObject.getInt("companyId", 0));
			if(sequenceCounter != null) {
				
				file	= (MultipartFile) valueInObject.get("file");
				
				fuelInvoice	 =	FuelInvoiceBL.getFuelInvoice(valueInObject);
				fuelInvoice.setFuelInvoiceNumber(sequenceCounter.getNextVal());
				
				savedFuelInvoice = fuelInvoiceRepository.save(fuelInvoice);
				
				valueInObject.put("fuelInvoiceId", savedFuelInvoice.getFuelInvoiceId());
				
				FuelStockDetails	preStockDetails	= fuelStockDetailsRepository.getFuelStockDetailsByPetrolPumpId(fuelInvoice.getPetrolPumpId(), fuelInvoice.getCompanyId());
				
				if(preStockDetails != null) {
					fuelStockHistory	=	FuelInvoiceBL.getFuelStockHistoryDTO(preStockDetails, fuelInvoice.getFuelInvoiceId());
					fuelStockHistoryRepository.save(fuelStockHistory);
					Double rate = (preStockDetails.getTotalFuelCost() + fuelInvoice.getTotalAmount())/(preStockDetails.getStockQuantity() + fuelInvoice.getQuantity()) ;
					updateFuelStockDetails(fuelInvoice.getPetrolPumpId(), rate, fuelInvoice.getQuantity(), fuelInvoice.getTotalAmount());
				}else {
					fuelStockDetails	= FuelInvoiceBL.getFuelStockDetailsDTO(preStockDetails, valueInObject);
					saveFuelStockDetails(fuelStockDetails);
				}
				
				if(fuelInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					  PendingVendorPayment	payment	=	PendingVendorPaymentBL.createPendingVendorPaymentDTOForFI(fuelInvoice);
					  pendingVendorPaymentService.savePendingVendorPayment(payment);
				}
				
				if(valueInObject.getBoolean("allowBankPaymentDetails",false) && (PaymentTypeConstant.getPaymentTypeIdList().contains(fuelInvoice.getPaymentTypeId()) || fuelInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)) {
					ValueObject bankPaymentValueObject=JsonConvertor.toValueObjectFormSimpleJsonString(valueInObject.getString("bankPaymentDetails"));
						bankPaymentValueObject.put("bankPaymentTypeId",fuelInvoice.getPaymentTypeId());
						bankPaymentValueObject.put("userId",valueInObject.getLong("userId",0));
						bankPaymentValueObject.put("companyId", valueInObject.getInt("companyId", 0));
						bankPaymentValueObject.put("moduleId",savedFuelInvoice.getFuelInvoiceId());
						bankPaymentValueObject.put("moduleNo", savedFuelInvoice.getFuelInvoiceNumber());
						bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.FUEL_INVENTORY);
						bankPaymentValueObject.put("amount",fuelInvoice.getInvoiceAmount());
						
						Vendor	vendor	= vendorService.getVendor(savedFuelInvoice.getVendorId());
						
						bankPaymentValueObject.put("remark", "Fuel Invoice Created "+savedFuelInvoice.getFuelInvoiceNumber()+" Vendor : "+vendor.getVendorName()+" Payment Type : "+PaymentTypeConstant.getPaymentTypeName(fuelInvoice.getPaymentTypeId()));
						
						if(fuelInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)
							cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
						else
							bankPaymentService.addBankPaymentDetailsFromValueObject(bankPaymentValueObject);
					}
				
				valueOutObject.put("fuelInvoiceId", fuelInvoice.getFuelInvoiceId());
				
				if(file != null && !file.isEmpty()) {
					saveFuelInvoiceDocument(savedFuelInvoice, file);
					
				}
				
			}else {
				valueOutObject.put("sequenceNotFound", true);
			}
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void saveFuelInvoiceDocument(FuelInvoice	fuelInvoice, MultipartFile	file) throws Exception{

		try {
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			FuelInvoiceDocument fuelDoc = new FuelInvoiceDocument();
			fuelDoc.setFuelInvoiceId(fuelInvoice.getFuelInvoiceId());
			if(file != null) {
				byte[] bytes = file.getBytes();
				fuelDoc.setFilename(file.getOriginalFilename());
				fuelDoc.setContent(bytes);
				fuelDoc.setContentType(file.getContentType());
			} 
			fuelDoc.setMarkForDelete(false);
			fuelDoc.setCreatedById(fuelInvoice.getCreatedById());
			fuelDoc.setLastModifiedById(fuelInvoice.getLastUpdatedById());
			fuelDoc.setCreated(toDate);
			fuelDoc.setLastupdated(toDate);
			fuelDoc.setCompanyId(fuelInvoice.getCompanyId());


			fuelDoc.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_FUEL_INVOICE_DOCUMENT));
			
			mongoTemplate.save(fuelDoc);
		
			fuelInvoiceRepository.updateDocumentId(fuelDoc.get_id(), fuelInvoice.getFuelInvoiceId());

		} catch (Exception e) {
			throw e;
		}
	
	}

	@Override
	public ValueObject getPageWiseFuelInvoiceDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails	   userDetails				= null;
		Integer				   pageNumber				= null;
		try {

			
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber				= valueObject.getInt("pageNumber",0);
			Page<FuelInvoice> page  = getDeploymentFuelInvoice(pageNumber, userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());

			List<FuelInvoiceDto> pageList = getFuelInvoiceDtoList(pageNumber, userDetails.getCompany_id());

			valueObject.put("FuelInvoice", pageList);
			valueObject.put("SelectPage", pageNumber);
		
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			 userDetails				= null;
		}
	}
	
	@Override
	public Page<FuelInvoice> getDeploymentFuelInvoice(Integer pageNumber, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "fuelInvoiceId");
		return fuelInvoiceRepository.getDeploymentFuelInvoice(companyId, pageable);
	}
	
	
	@Override
	public List<FuelInvoiceDto> getFuelInvoiceDtoList(Integer pageNumber, Integer companyId) throws Exception {

		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]> 					resultList 				= null; 
		List<FuelInvoiceDto> 			fuelInvoiceList 		= null;
		FuelInvoiceDto 					fuelInvoiceDto			= null;

		try {
			
			SimpleDateFormat	dateFormat	= new SimpleDateFormat("dd-MM-yyyy");

			typedQuery = entityManager.createQuery("SELECT BI.fuelInvoiceId, BI.fuelInvoiceNumber, BI.quantity, BI.rate, BI.discount,"
					+ " BI.gst, BI.discountGstTypeId, BI.totalAmount, BI.invoiceDate, BI.invoiceNumber , BI.invoiceAmount, BI.vendorId, BI.petrolPumpId,"
					+ " V.vendorName, V2.vendorName, BI.documentId, BI.balanceStock ,BI.transferedFrom "
					+ " FROM FuelInvoice AS BI"
					+ " INNER JOIN Vendor AS V ON V.vendorId = BI.vendorId "
					+ " INNER JOIN Vendor AS V2 ON V2.vendorId = BI.petrolPumpId "
					+ " WHERE BI.companyId ="+companyId+" AND BI.markForDelete = 0 ORDER BY BI.fuelInvoiceId DESC", Object[].class);

			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				fuelInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					fuelInvoiceDto = new FuelInvoiceDto();
					
					fuelInvoiceDto.setFuelInvoiceId((Long) result[0]);
					fuelInvoiceDto.setFuelInvoiceNumber((Long) result[1]);
					if( result[2] != null)
					fuelInvoiceDto.setQuantity(Double.parseDouble(toFixedTwo.format(result[2])));
					if( result[3] != null)
					fuelInvoiceDto.setRate(Double.parseDouble(toFixedTwo.format(result[3])));
					if(result[4] != null)
					fuelInvoiceDto.setDiscount(Double.parseDouble(toFixedTwo.format(result[4])));
					if(result[5] != null)
					fuelInvoiceDto.setGst(Double.parseDouble(toFixedTwo.format(result[5])));
					fuelInvoiceDto.setDiscountGstTypeId((short) result[6]);
					if(result[7] != null)
					fuelInvoiceDto.setTotalAmount(Double.parseDouble(toFixedTwo.format(result[7])));
					fuelInvoiceDto.setInvoiceDate((Date) result[8]);
					fuelInvoiceDto.setInvoiceNumber((String) result[9]);
					if(result[10] != null)
					fuelInvoiceDto.setInvoiceAmount(Double.parseDouble(toFixedTwo.format(result[10])));
					fuelInvoiceDto.setVendorId((Integer) result[11]);
					fuelInvoiceDto.setPetrolPumpId((Integer) result[12]);
					fuelInvoiceDto.setVendorName((String) result[13]);
					fuelInvoiceDto.setPetrolPumpName((String) result[14]);
					fuelInvoiceDto.setDocumentId((Long) result[15]);
					
					fuelInvoiceDto.setInvoiceDateStr(dateFormat.format(fuelInvoiceDto.getInvoiceDate()));
					if(result[16] != null) {
						fuelInvoiceDto.setBalanceStock(Double.parseDouble(toFixedTwo.format(result[16])));
					}
					fuelInvoiceDto.setTransferedFromInvoiceNumber((Long) result[17]);
					if(fuelInvoiceDto.getTransferedFromInvoiceNumber()!=null && fuelInvoiceDto.getTransferedFromInvoiceNumber() >0 )
						fuelInvoiceDto.setCreatedAsTransfered(true);
					fuelInvoiceList.add(fuelInvoiceDto);
				}
			}

			return fuelInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			fuelInvoiceList 		= null;
			fuelInvoiceDto			= null;
		}
	}
	
	@Override
	public FuelInvoiceDocument getFuelInvoiceDocumentDetails(long documentId, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(documentId).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, FuelInvoiceDocument.class);
	}
	
	@Override
	public ValueObject getFuelInvoiceDetails(ValueObject valueObject) throws Exception {
		try {
			
			valueObject.put("fuelInvoice", getFuelInvoiceDetailsById(valueObject.getLong("fuelInvoiceId",0), valueObject.getInt("companyId",0)));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public FuelInvoiceDto getFuelInvoiceDetailsById(Long fuelInvoiceId, Integer companyId) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT FI.fuelInvoiceId, FI.fuelInvoiceNumber, FI.quantity, FI.rate, FI.discount, FI.gst, FI.discountGstTypeId, FI.totalAmount,"
							+ " FI.invoiceDate, FI.invoiceNumber, FI.invoiceAmount, FI.vendorId, FI.paymentTypeId, FI.tallyCompanyId, FI.paymentNumber,"
							+ " FI.poNumber, FI.description, FI.companyId, FI.documentId, FI.createdById, FI.lastUpdatedById, FI.createdOn, FI.lastUpdatedOn,"
							+ " FI.petrolPumpId, V.vendorName, V2.vendorName, u.firstName, u.lastName, u2.firstName, u2.lastName, TC.companyName, V.vendorLocation, FI.balanceStock,"
							+ " FI.updatedStock, FI.stockTypeId ,FI.transferedFrom ,FII.fuelInvoiceNumber ,FT.transferQuantity  "
							+ " FROM FuelInvoice FI " 
							+ " INNER JOIN Vendor  AS V ON V.vendorId = FI.vendorId "
							+ " INNER JOIN Vendor AS V2 ON V2.vendorId = FI.petrolPumpId "
							+ " INNER JOIN User u on u.id = FI.createdById"
							+ " INNER JOIN User u2 on u2.id = FI.lastUpdatedById "
							+ " LEFT JOIN FuelInvoice FII ON FII.fuelInvoiceId = FI.transferedFrom "
							+ " LEFT JOIN FuelTransferDetails FT ON FT.createdInvoiceId = FI.fuelInvoiceId "
							+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = FI.tallyCompanyId"
							+ " WHERE FI.fuelInvoiceId =:id AND FI.companyId =:companyId AND FI.markForDelete = 0");

			query.setParameter("id", fuelInvoiceId);
			query.setParameter("companyId", companyId);
			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			
			FuelInvoiceDto select = null;
			if (result != null) {
				select = new FuelInvoiceDto();
				
				select.setFuelInvoiceId((Long) result[0]);
				select.setFuelInvoiceNumber((Long) result[1]);
				if(result[2] != null)
				select.setQuantity(Double.parseDouble(toFixedTwo.format(result[2])));
				if(result[3] != null)
				select.setRate(Double.parseDouble(toFixedTwo.format(result[3])));
				if(result[4] != null) {
					select.setDiscount(Double.parseDouble(toFixedTwo.format(result[4])));
				}else {
					select.setDiscount(0.0);
				}
				if(result[5] != null)
				select.setGst(Double.parseDouble(toFixedTwo.format(result[5])));
				select.setDiscountGstTypeId((short) result[6]);
				if(result[7] != null)
				select.setTotalAmount(Double.parseDouble(toFixedTwo.format(result[7])));
				select.setInvoiceDate((Date) result[8]);
				select.setInvoiceNumber((String) result[9]);
				if(result[10] != null)
				select.setInvoiceAmount(Double.parseDouble(toFixedTwo.format(result[10])));
				select.setVendorId((Integer) result[11]);
				select.setPaymentTypeId((short) result[12]);
				select.setTallyCompanyId((Long) result[13]);
				select.setPaymentNumber((String) result[14]);
				select.setPoNumber((String) result[15]);
				select.setDescription((String) result[16]);
				select.setCompanyId((Integer) result[17]);
				select.setDocumentId((Long) result[18]);
				select.setCreatedById((Long) result[19]);
				select.setLastUpdatedById((Long) result[20]);
				select.setCreatedOn((Date) result[21]);
				select.setLastUpdatedOn((Date) result[22]);
				select.setPetrolPumpId((Integer) result[23]);
				select.setVendorName((String) result[24]);
				select.setPetrolPumpName((String) result[25]);
				select.setCreatedBy((String) result[26] +"_"+(String) result[27]);
				select.setLastUpdatedBy((String) result[28] +"_"+(String) result[29]);
				select.setTallyCompanyName((String) result[30]);
				select.setVendorLocation((String) result[31]);
				select.setInvoiceDateStr(dateFormat.format(select.getInvoiceDate()));
				select.setCreatedOnStr(format.format(select.getCreatedOn()));
				select.setLastUpdatedOnStr(format.format(select.getLastUpdatedOn()));
				select.setPaymentType(PaymentTypeConstant.getPaymentTypeName(select.getPaymentTypeId()));
				select.setDiscountGstType(FuelInvoiceBL.getDiscountTypeName(select.getDiscountGstTypeId()));
				if(result[32] != null)
				select.setBalanceStock(Double.parseDouble(toFixedTwo.format(result[32])));
				if(result[33] != null)
				select.setUpdatedStock(Double.parseDouble(toFixedTwo.format(result[33])));
				if(result[34] != null)
				select.setStockType(FuelInvoiceConstant.getStockType((short) result[34]));
				select.setTransferedFromInvoiceId((Long) result[35]);
				if(select.getTransferedFromInvoiceId()!= null && select.getTransferedFromInvoiceId() >0)
					select.setCreatedAsTransfered(true);
				select.setTransferedFromInvoiceNumber((Long) result[36]);
				if(result[37] != null)
				select.setTransferedQuantity((Double) result[37]);
			}

			return select;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteFuelInvoice(ValueObject valueObject) throws Exception {
		FuelInvoice			fuelInvoice			= null;
		FuelStockDetails	preStockDetails		= null;
		FuelStockHistory	fuelStockHistory	= null;
		Double				preFuelCost			= 0.0;
		Double				preFuelStock		= 0.0;
		Double 				avgRate 			= 0.0;
		Fuel				fuel				= null;
		FuelInvoiceHistory			fuelInvoiceHistory			= null;
		FuelInvoiceHistory			fuelInvoiceHistoryDetail			= null;
		try {
			DecimalFormat	decimalFormat = new DecimalFormat("#.00");
			fuel 				= fuelService.getfueldetailsbyfuelInvoiceId(valueObject.getLong("fuelInvoiceId",0),valueObject.getInt("companyId",0));
			fuelInvoiceHistoryDetail = fuelInvoiceHistoryRepository.getStatusWiseInvoiceHistory(valueObject.getLong("fuelInvoiceId",0),valueObject.getInt("companyId",0));
			if(fuel != null) {
				valueObject.put("fuelEntryExist", true);
				return valueObject;
			}
			if(fuelInvoiceHistoryDetail != null) {
				valueObject.put("fuelEntryExist", true);
				return valueObject;
			}
			
			fuelInvoice	= fuelInvoiceRepository.getFuelInvoiceById(valueObject.getLong("fuelInvoiceId",0), valueObject.getInt("companyId",0));
			fuelInvoiceHistory = FuelInvoiceBL.prepareFuelInvoiceHistory(fuelInvoice);
			fuelInvoiceHistoryRepository.save(fuelInvoiceHistory);
			if(fuelInvoice != null) {
				preStockDetails			= fuelStockDetailsRepository.getFuelStockDetailsByPetrolPumpId(fuelInvoice.getPetrolPumpId(), fuelInvoice.getCompanyId());
				fuelStockHistory	=	FuelInvoiceBL.getFuelStockHistoryDTO(preStockDetails, fuelInvoice.getFuelInvoiceId());
				fuelStockHistoryRepository.save(fuelStockHistory);
				
				preFuelStock	= preStockDetails.getStockQuantity() - fuelInvoice.getQuantity();
				preFuelCost		= preStockDetails.getTotalFuelCost() - fuelInvoice.getTotalAmount();
				if(preFuelCost > 0 && preFuelStock > 0) {
					avgRate = Double.parseDouble(decimalFormat.format(preFuelCost/preFuelStock));
				}
				
				fuelInvoiceRepository.deleteFuelInvoice(valueObject.getLong("fuelInvoiceId",0));
				
				if(fuelInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && fuelInvoice.getInvoiceAmount() > 0) {
					pendingVendorPaymentService.deletePendingVendorPaymentAmt(fuelInvoice.getFuelInvoiceId(), PendingPaymentType.PAYMENT_TYPE_FUEL_INVOICE, fuelInvoice.getInvoiceAmount());
				}
				updateFuelStockDetails(fuelInvoice.getPetrolPumpId(), avgRate, - fuelInvoice.getQuantity(), - fuelInvoice.getTotalAmount());
				
				bankPaymentService.deleteBankPaymentDetailsIfTransactionDeleted(fuelInvoice.getFuelInvoiceId(), ModuleConstant.FUEL_INVENTORY, valueObject.getInt("companyId",0),valueObject.getLong("userId",0),false);
			}
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	
	@Override
	@Transactional
	public void saveFuelStockDetails(FuelStockDetails	fuelStockDetails) throws Exception{
		synchronized (xMutexFactory.getMutex(fuelStockDetails.getPetrolPumpId())) {
			fuelStockDetailsRepository.save(fuelStockDetails);
		}
	}
	
	@Override
	public List<FuelStockDetailsDto> getMinimumfuelStock(Integer companyId ,Integer threashold) throws Exception {
		TypedQuery<Object[]> query= null;
		List<Object[]> result = null;
		List<FuelStockDetailsDto> fuelStockDetailsDtoList = null;

		FuelStockDetailsDto fuelStockDetailsDto = null;
		try {

			query= entityManager.createQuery("SELECT v.vendorId, v.vendorName, fi.avgFuelRate, fi.stockQuantity ,fi.totalFuelCost FROM FuelStockDetails AS fi "
					+ " INNER JOIN Vendor AS v ON v.vendorId = fi.petrolPumpId WHERE  fi.companyId = "+companyId+" AND fi.stockQuantity < "+threashold+"  AND fi.markForDelete = 0  AND v.markForDelete = 0", Object[].class);

			result=query.getResultList();

			if(result != null && !result.isEmpty()) {
				fuelStockDetailsDtoList = new ArrayList<>();

				for(Object [] results :result) {
					fuelStockDetailsDto = new FuelStockDetailsDto();
					fuelStockDetailsDto.setPetrolPumpId((int)results[0]);
					fuelStockDetailsDto.setVendorName((String)results[1]);
					fuelStockDetailsDto.setAvgFuelRate((Double)results[2]);
					if(results[3] != null)
					fuelStockDetailsDto.setStockQuantity(Double.parseDouble(toFixedTwo.format(results[3])));
					fuelStockDetailsDto.setTotalFuelCost((Double)results[4]);
					fuelStockDetailsDtoList.add(fuelStockDetailsDto);
				}

			}
			return fuelStockDetailsDtoList;
		}catch(Exception e) {
			throw e;
		}	
	}
	
	
	@Override
	public ValueObject getFuelStockDetails(ValueObject valueObject) throws Exception {
		
		try {
			if(valueObject.getBoolean("getStockFromInventoryConfig",false)) {
				valueObject.put("stockDetails",getBalanceStockFromInvoice(valueObject.getInt("petrolPumpId"), valueObject.getInt("companyId")));
			}else {
				valueObject.put("stockDetails", fuelStockDetailsRepository.getFuelStockDetailsByPetrolPumpId(valueObject.getInt("petrolPumpId"), valueObject.getInt("companyId")));
			}
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getFuelStockDetail(ValueObject valueObject) throws Exception {
		try {
			if(valueObject.getBoolean("getStockFromInventoryConfig",false)) {
				FuelInvoice fuelInvoice = null;
				if(valueObject.getInt("petrolPumpId",0) == valueObject.getInt("oldPetrolPumpId",0)){
					 fuelInvoice=fuelInvoiceRepository.getFuelInvoiceById(valueObject.getLong("fuelInvoiceId",0),valueObject.getInt("companyId"));
					if(fuelInvoice != null)
						fuelInvoice.setRate(fuelService.eachPriceForFuleInvoice(fuelInvoice));
					valueObject.put("stockDetails",fuelInvoice);
				}else {
					 fuelInvoice =getFuelInvoiceBalanceStockDetails(valueObject.getInt("petrolPumpId"), valueObject.getInt("companyId"));
					if(fuelInvoice != null)
					fuelInvoice.setRate(fuelService.eachPriceForFuleInvoice(fuelInvoice));
					valueObject.put("stockDetails",fuelInvoice);
				}
			}else {
				valueObject.put("stockDetails", fuelStockDetailsRepository.getFuelStockDetailsByPetrolPumpId(valueObject.getInt("petrolPumpId"), valueObject.getInt("companyId")));
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public FuelStockDetails getFuelStockDetailsByPetrolPumpId(Integer petrolPumpId, Integer companyId)
			throws Exception {
		return fuelStockDetailsRepository.getFuelStockDetailsByPetrolPumpId(petrolPumpId, companyId);
	}
	
	@Override
	@Transactional
	public void updateFuelStockDetails(Integer petrolPumpId, Double rate, Double quantity, Double price)
			throws Exception {
			synchronized (xMutexFactory.getMutex(petrolPumpId)) {
				entityManager.createQuery(
						" UPDATE FuelStockDetails SET avgFuelRate = "+rate+", stockQuantity = stockQuantity + "+quantity+", totalFuelCost = totalFuelCost + "+price+" "
								+ " WHERE petrolPumpId = "+petrolPumpId+" ").executeUpdate();
			}
	}
	
	@Override
	public FuelInvoice getFuelInvoiceBalanceStockDetails(Integer vendorId,Integer companyId)throws Exception {
		try {
			return fuelInvoiceRepository.getFuelInvoiceBalanceStockDetails(vendorId,companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public Double getBalanceStockFromInvoice(Integer vendorId , Integer companyId) throws Exception {
		
		return Utility.round(fuelInvoiceRepository.petrolPumpWiseBalanceStock(vendorId, companyId),3);
	}
	@Override
	public FuelInvoice getFuelInvoiceDetailsByFuelInvoiceId(Long fuelInvoiceId,Integer companyId)throws Exception {
		try {
			return fuelInvoiceRepository.getFuelInvoiceById(fuelInvoiceId,companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updateFuelStockDetailsInFuelInvoice(ValueObject object) throws Exception {
		String 	query	= "";	
		try {
			if(object.getShort("stockTypeId") == FuelInvoiceConstant.STOCK_TYPE_SHORT || object.getShort("stockTypeId") == FuelInvoiceConstant.STOCK_TYPE_FE_CREATE) {
				double updatedStock = object.getDouble("updatedStock");
				query = " balanceStock = balanceStock-"+updatedStock+" ,";
			}else if(object.getShort("stockTypeId") == FuelInvoiceConstant.STOCK_TYPE_EXCESS || object.getShort("stockTypeId") == FuelInvoiceConstant.STOCK_TYPE_FE_DELETE ) {
				query = " balanceStock = balanceStock+"+object.getDouble("updatedStock")+", ";
			}else if(object.getShort("stockTypeId") == FuelInvoiceConstant.STOCK_TYPE_FE_EDIT) {
				double updatedStock = object.getDouble("updatedStock");
				query = " balanceStock = balanceStock+"+(updatedStock)+", ";
			}else if(object.getShort("stockTypeId") == FuelInvoiceConstant.STOCK_TYPE_FE_EDIT && object.getBoolean("oldVendorOwnPump",false)) {
				query = " balanceStock = balanceStock+"+object.getDouble("updatedStock")+", ";
			}
			synchronized (xMutexFact.getMutex(object.getLong("fuelInvoiceId",0))) {
			entityManager.createQuery(
					" UPDATE FuelInvoice SET stockTypeId = "+object.getShort("stockTypeId")+", "
					+ " updatedStock = "+object.getDouble("updatedStock")+", "+query+"  remark ='"+object.getString("remark","")+"' "
					+ " WHERE fuelInvoiceId = "+object.getLong("fuelInvoiceId",0)+" ").executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}	
		
	}
	@Override
	@Transactional
	public ValueObject updateShortExcessQuantity(ValueObject object) throws Exception {
		FuelInvoice				fuelInvoice					= null;
		FuelInvoiceHistory		fuelInvoiceHistory 			= null;
		FuelStockDetails 		fuelStockDetails			= null;
		
		try {
			fuelInvoice					= getFuelInvoiceDetailsByFuelInvoiceId(object.getLong("fuelInvoiceId",0), object.getInt("companyId"));
			fuelInvoiceHistory			= FuelInvoiceBL.prepareFuelInvoiceHistory(fuelInvoice);
			fuelInvoiceHistoryRepository.save(fuelInvoiceHistory);
			updateFuelStockDetailsInFuelInvoice(object);
			
			
			fuelStockDetails			= getFuelStockDetailsByPetrolPumpId(fuelInvoice.getPetrolPumpId(),fuelInvoice.getCompanyId());
			if(object.getShort("stockTypeId") == FuelInvoiceConstant.STOCK_TYPE_SHORT) {
				updateFuelStockDetails(object.getInt("petrolPumpId",0), fuelStockDetails.getAvgFuelRate(), -object.getDouble("updatedStock"), -(fuelStockDetails.getAvgFuelRate()*object.getDouble("updatedStock")));
			}else if(object.getShort("stockTypeId") == FuelInvoiceConstant.STOCK_TYPE_EXCESS) {
				updateFuelStockDetails(object.getInt("petrolPumpId",0), fuelStockDetails.getAvgFuelRate(),  object.getDouble("updatedStock"),  (fuelStockDetails.getAvgFuelRate()*object.getDouble("updatedStock")));
			}
		
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}	
		
	}

	
	@Override
	public ValueObject getFuelInvoiceHistoryByInvoiceId(ValueObject valueObject) throws Exception {

		TypedQuery<Object[]> 			typedQuery 					= null;
		List<Object[]> 					resultList 					= null; 
		List<FuelInvoiceHistoryDto> 	fuelInvoiceHistoryList 		= null;
		FuelInvoiceHistoryDto 			fuelInvoiceHistoryDto		= null;

		try {
			
			SimpleDateFormat	dateFormat	= new SimpleDateFormat("dd-MM-yyyy");

			typedQuery = entityManager.createQuery("SELECT BI.fuelInvoiceHistoryId, BI.fuelInvoiceId, BI.fuelInvoiceNumber, BI.quantity, BI.rate, BI.discount,"
					+ " BI.gst, BI.discountGstTypeId, BI.totalAmount, BI.invoiceDate, BI.invoiceNumber , BI.invoiceAmount, BI.vendorId, BI.petrolPumpId,"
					+ " V.vendorName, V2.vendorName, BI.documentId, BI.balanceStock, BI.updatedStock, BI.stockTypeId, BI.remark "
					+ " FROM FuelInvoiceHistory AS BI"
					+ " INNER JOIN Vendor AS V ON V.vendorId = BI.vendorId "
					+ " INNER JOIN Vendor AS V2 ON V2.vendorId = BI.petrolPumpId "
					+ " WHERE BI.companyId ="+valueObject.getInt("companyId",0)+" AND BI.fuelInvoiceId = "+valueObject.getLong("fuelInvoiceId",0)+" "
					+ " AND BI.markForDelete = 0 ORDER BY BI.fuelInvoiceId DESC", Object[].class);

			typedQuery.setFirstResult((valueObject.getInt("pageNumber") - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				fuelInvoiceHistoryList = new ArrayList<>();

				for (Object[] result : resultList) {
					fuelInvoiceHistoryDto = new FuelInvoiceHistoryDto();
					fuelInvoiceHistoryDto.setFuelInvoiceHistoryId((Long) result[0]);
					fuelInvoiceHistoryDto.setFuelInvoiceId((Long) result[1]);
					fuelInvoiceHistoryDto.setFuelInvoiceNumber((Long) result[2]);
					fuelInvoiceHistoryDto.setQuantity((Double) result[3]);
					fuelInvoiceHistoryDto.setRate((Double) result[4]);
					fuelInvoiceHistoryDto.setDiscount((Double) result[5]);
					fuelInvoiceHistoryDto.setGst((Double) result[6]);
					fuelInvoiceHistoryDto.setDiscountGstTypeId((short) result[7]);
					fuelInvoiceHistoryDto.setTotalAmount((Double) result[8]);
					fuelInvoiceHistoryDto.setInvoiceDate((Date) result[9]);
					fuelInvoiceHistoryDto.setInvoiceNumber((String) result[10]);
					fuelInvoiceHistoryDto.setInvoiceAmount((Double) result[11]);
					fuelInvoiceHistoryDto.setVendorId((Integer) result[12]);
					fuelInvoiceHistoryDto.setPetrolPumpId((Integer) result[13]);
					fuelInvoiceHistoryDto.setVendorName((String) result[14]);
					fuelInvoiceHistoryDto.setPetrolPumpName((String) result[15]);
					fuelInvoiceHistoryDto.setDocumentId((Long) result[16]);
					fuelInvoiceHistoryDto.setInvoiceDateStr(dateFormat.format(fuelInvoiceHistoryDto.getInvoiceDate()));
					fuelInvoiceHistoryDto.setBalanceStock((double) result[17]);
					fuelInvoiceHistoryDto.setUpdatedStock((double) result[18]);
					fuelInvoiceHistoryDto.setStockType(FuelInvoiceConstant.getStockType((short) result[19]));
					fuelInvoiceHistoryDto.setRemark((String) result[20]);
					fuelInvoiceHistoryList.add(fuelInvoiceHistoryDto);
				}
			}
			valueObject.put("fuelInvoiceHistoryList", fuelInvoiceHistoryList);
			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 					= null;
			resultList 					= null;
			fuelInvoiceHistoryList 		= null;
			fuelInvoiceHistoryDto		= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject updateFuelInventoryDetails(ValueObject valueInObject) throws Exception {
		FuelInvoice						preFuelInvoice			= null;
		FuelInvoiceHistory				fuelInvoiceHistory		= null;
		FuelInvoice						fuelInvoice				= null;
		FuelStockDetails				preStockDetails			= null;
		MultipartFile 					file					= null;
		FuelStockDetails				fuelStockDetails		= null;
		FuelStockHistory				fuelStockHistory		= null;
		ValueObject						valueOutObject			= null;
		FuelInvoiceHistory				fuelInvoiceHistoryDetail= null;
		Fuel							fuel					= null;
		try {
			valueOutObject			= new ValueObject();
			file					= (MultipartFile) valueInObject.get("file");
			
			fuel 					= fuelService.getfueldetailsbyfuelInvoiceId(valueInObject.getLong("fuelInvoiceId",0),valueInObject.getInt("companyId",0));
			fuelInvoiceHistoryDetail = fuelInvoiceHistoryRepository.getStatusWiseInvoiceHistory(valueInObject.getLong("fuelInvoiceId",0),valueInObject.getInt("companyId",0));
			if(fuel != null) {
				valueInObject.put("fuelEntryExist", true);
				return valueInObject;
			}
			if(fuelInvoiceHistoryDetail != null) {
				valueInObject.put("fuelEntryExist", true);
				return valueInObject;
			}
			
			
			preFuelInvoice			= getFuelInvoiceDetailsByFuelInvoiceId(valueInObject.getLong("fuelInvoiceId"), valueInObject.getInt("companyId"));
			fuelInvoiceHistory		= FuelInvoiceBL.prepareFuelInvoiceHistory(preFuelInvoice);
			fuelInvoiceHistoryRepository.save(fuelInvoiceHistory);
			
			fuelInvoice	 			= FuelInvoiceBL.getFuelInvoice(valueInObject);
			fuelInvoice.setFuelInvoiceNumber(preFuelInvoice.getFuelInvoiceNumber());
			
			preStockDetails			= fuelStockDetailsRepository.getFuelStockDetailsByPetrolPumpId(preFuelInvoice.getPetrolPumpId(), preFuelInvoice.getCompanyId());
			
			if(preStockDetails != null) {
				fuelStockHistory	=	FuelInvoiceBL.getFuelStockHistoryDTO(preStockDetails, preFuelInvoice.getFuelInvoiceId());
				fuelStockHistoryRepository.save(fuelStockHistory);
				
				
				Double rate = ((preStockDetails.getTotalFuelCost()-preFuelInvoice.getTotalAmount()) + fuelInvoice.getTotalAmount())/((preStockDetails.getStockQuantity()- preFuelInvoice.getQuantity()) + fuelInvoice.getQuantity()) ;
				
				Double quanity = fuelInvoice.getQuantity()-preFuelInvoice.getQuantity();
				Double amt  = fuelInvoice.getTotalAmount()-preFuelInvoice.getTotalAmount();
				
				updateFuelStockDetails(fuelInvoice.getPetrolPumpId(), rate, quanity, amt);
				
			}else {
				fuelStockDetails	= FuelInvoiceBL.getFuelStockDetailsDTO(preStockDetails, valueInObject);
				saveFuelStockDetails(fuelStockDetails);
			}

			
			fuelInvoiceRepository.save(fuelInvoice);
			
			if(file != null && !file.isEmpty()) {
				saveFuelInvoiceDocument(fuelInvoice, file);

			}
			if (valueInObject.getBoolean("allowBankPaymentDetails",false)) {
					ValueObject bankPaymentValueObject=JsonConvertor.toValueObjectFormSimpleJsonString(valueInObject.getString("bankPaymentDetails"));
					bankPaymentValueObject.put("oldPaymentTypeId",preFuelInvoice.getPaymentTypeId());
					bankPaymentValueObject.put("bankPaymentTypeId", fuelInvoice.getPaymentTypeId());
					bankPaymentValueObject.put("currentPaymentTypeId", fuelInvoice.getPaymentTypeId());
					bankPaymentValueObject.put("userId",valueInObject.getLong("userId",0));
					bankPaymentValueObject.put("companyId", valueInObject.getInt("companyId", 0));
					bankPaymentValueObject.put("moduleId",fuelInvoice.getFuelInvoiceId());
					bankPaymentValueObject.put("moduleNo", fuelInvoice.getFuelInvoiceNumber());
					bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.FUEL_INVENTORY);
					bankPaymentValueObject.put("amount",fuelInvoice.getInvoiceAmount());
					
					Vendor	vendor	=  vendorService.getVendor(fuelInvoice.getVendorId());
					bankPaymentValueObject.put("remark", "Update Fuel Invoice FI-"+fuelInvoice.getFuelInvoiceNumber()+" vendor : "+vendor.getVendorName());
				
					bankPaymentService.updatePaymentDetailsFromValuObject(bankPaymentValueObject);
			}

			return valueOutObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Override
	@Transactional
	public void updateApprovalIdToInvoice(String fuelInvoiceIds, Long approvalId, short status, Integer companyId)
			throws Exception {
		entityManager.createQuery(
				" UPDATE FuelInvoice SET approvalId = "+approvalId+", paymentStatusId =  "+status+" "
						+ " WHERE fuelInvoiceId IN ( "+fuelInvoiceIds+") AND companyId = "+companyId+" ").executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentDetails(Long approvalId, short paymentStatus) throws Exception {

		entityManager.createQuery("  UPDATE FuelInvoice paymentStatusId ="+paymentStatus+""
				+ " WHERE approvalId = "+approvalId+" ").executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception {
		entityManager.createQuery("  UPDATE FuelInvoice SET approvalId = "+approvalId+" , paymentStatusId ="+paymentStatus+" "
				+ " WHERE fuelInvoiceId = "+invoiceId+" ").executeUpdate();
		
	}
	
	@Override
	public List<FuelInvoiceDto> getFuelInvoiceListByPetrolPump(Integer companyId ,long petrolPumpId) throws Exception {
		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]> 					resultList 				= null; 
		List<FuelInvoiceDto> 			fuelInvoiceList 		= null;
		FuelInvoiceDto 					fuelInvoiceDto			= null;

		try {
			typedQuery = entityManager.createQuery("SELECT BI.fuelInvoiceId ,BI.balanceStock ,BI.fuelInvoiceNumber "
					+ " FROM FuelInvoice AS BI"
					+ " INNER JOIN Vendor AS V ON V.vendorId = BI.vendorId "
					+ " INNER JOIN Vendor AS V2 ON V2.vendorId = BI.petrolPumpId "
					+ " WHERE BI.companyId ="+companyId+" AND BI.petrolPumpId="+petrolPumpId+" AND BI.balanceStock > 0 AND  BI.markForDelete = 0 ORDER BY BI.fuelInvoiceId DESC", Object[].class);


			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				fuelInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					fuelInvoiceDto = new FuelInvoiceDto();
					
					fuelInvoiceDto.setFuelInvoiceId((Long) result[0]);
					if(result[1] != null) {
						fuelInvoiceDto.setBalanceStock((Double)result[1]);
					}
					fuelInvoiceDto.setFuelInvoiceNumber((Long) result[2]);
					fuelInvoiceList.add(fuelInvoiceDto);
				}
			}

			return fuelInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			fuelInvoiceList 		= null;
			fuelInvoiceDto			= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject saveTransferFuelDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		ValueObject valueOutObject = new ValueObject();
		FuelStockDetails fuelStockDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			FuelInvoiceDto fuelInvoice=getFuelInvoiceDetailsById(valueObject.getLong("invoiceList",0),userDetails.getCompany_id());
			if(fuelInvoice!= null) {
				if(fuelInvoice.getBalanceStock() < valueObject.getDouble("quantity",0)) {
					valueOutObject.put("balanceStockValidationFail", true);
					return valueOutObject;
				}
				valueOutObject= saveFuelTransferDetails(valueObject, fuelInvoice, userDetails);
				//subtracting from invoice stock
				deductFuleStrockFromFuelInvoice(valueObject.getDouble("quantity",0),  userDetails.getCompany_id(), valueObject.getLong("invoiceList",0), userDetails.getId());

				fuelStockDetails=fuelStockDetailsRepository.getFuelStockDetailsByPetrolPumpId(valueObject.getInt("toPetrolPumpId",0),userDetails.getCompany_id());
				if(fuelStockDetails == null) {
					fuelStockDetails= FuelInvoiceBL.prepareFuelStockDetails(valueObject,valueObject.getInt("toPetrolPumpId",0),valueObject.getDouble("quantity",0));
					saveFuelStockDetails(fuelStockDetails);
				}else {
					//adding to stockDetails
					updateFuelStockDetails(valueObject.getInt("toPetrolPumpId",0), fuelStockDetails.getAvgFuelRate(), valueObject.getDouble("quantity",0), (fuelStockDetails.getAvgFuelRate()*valueObject.getDouble("quantity",0)));
				}
				fuelStockDetails=fuelStockDetailsRepository.getFuelStockDetailsByPetrolPumpId(valueObject.getInt("fromPetrolPumpId",0),userDetails.getCompany_id());
					//subtracting from stockDetails
					updateFuelStockDetails(valueObject.getInt("fromPetrolPumpId",0), fuelStockDetails.getAvgFuelRate(), - valueObject.getDouble("quantity",0), - (fuelStockDetails.getAvgFuelRate()*valueObject.getDouble("quantity",0)));

			}else {
				valueOutObject.put("noInvoiceFound", true);
			}
			return valueOutObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public ValueObject saveFuelTransferDetails(ValueObject valueObject , FuelInvoiceDto fuelInvoice,CustomUserDetails userDetails) throws Exception {
		Double totalCost =0.0,discount =0.0,gst=0.0;
		FuelInvoice savedFuelInvoice		= new FuelInvoice();
		FuelInvoiceSequenceCounter		sequenceCounter		= null;
		try {
		sequenceCounter	= fuelSequenceCounterService.findNextNumber(userDetails.getCompany_id());
		if(fuelInvoice.getDiscountGstTypeId() == 1) {
		totalCost = valueObject.getDouble("quantity",0) * fuelInvoice.getRate();
		discount=totalCost * fuelInvoice.getDiscount()/100;
		totalCost -=discount;
		gst=totalCost*fuelInvoice.getGst()/100;
		totalCost+=gst;
		}else if(fuelInvoice.getDiscountGstTypeId() == 2) {
			totalCost = valueObject.getDouble("quantity",0) * fuelInvoice.getRate();
			totalCost -=fuelInvoice.getDiscount();
			totalCost+=fuelInvoice.getGst();	
		}
		FuelInvoice newFuelInvoice=	FuelInvoiceBL.prepareFuelinvoice(valueObject, fuelInvoice, userDetails);
		newFuelInvoice.setFuelInvoiceNumber(sequenceCounter.getNextVal());
		newFuelInvoice.setTotalAmount(totalCost);
		newFuelInvoice.setInvoiceAmount(totalCost);
		savedFuelInvoice=fuelInvoiceRepository.save(newFuelInvoice);
		
		FuelTransferDetails fuelTransferDetails =FuelInvoiceBL.prepareFuelTransfer(valueObject, fuelInvoice, userDetails);		
		fuelTransferDetails.setTotalFuelCost(totalCost);
		fuelTransferDetails.setCreatedInvoiceId(savedFuelInvoice.getFuelInvoiceId());
		fuelTransferDetailsRepository.save(fuelTransferDetails);
		valueObject.put("tatalcost", totalCost);
		valueObject.put("companyId", userDetails.getCompany_id());
		valueObject.put("savedFuelInvoice", savedFuelInvoice);
		valueObject.put("saved", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueObject;
	}


	@Transactional
	public void deductFuleStrockFromFuelInvoice (Double quantity,int companyId,Long fuelInvoiceId,Long userId) {
		try {
			entityManager.createQuery(" UPDATE FuelInvoice SET balanceStock = balanceStock -"+quantity+" ,lastUpdatedOn= '"+DateTimeUtility.getCurrentDate()+"' , lastUpdatedById ="+userId+" Where fuelInvoiceId ="+fuelInvoiceId+" AND companyId ="+companyId+" ").executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public List<FuelInvoiceDto> getFuelInvoiceDtoList(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]> 					resultList 				= null; 
		List<FuelInvoiceDto> 			fuelInvoiceList 		= null;
		FuelInvoiceDto 					fuelInvoiceDto			= null;

		try {
			
			SimpleDateFormat	dateFormat	= new SimpleDateFormat("dd-MM-yyyy");

			typedQuery = entityManager.createQuery("SELECT BI.fuelInvoiceId, BI.fuelInvoiceNumber, BI.quantity, BI.rate, BI.discount,"
					+ " BI.gst, BI.discountGstTypeId, BI.totalAmount, BI.invoiceDate, BI.invoiceNumber , BI.invoiceAmount, BI.vendorId, BI.petrolPumpId,"
					+ " V.vendorName, V2.vendorName, BI.documentId, BI.balanceStock "
					+ " FROM FuelInvoice AS BI"
					+ " INNER JOIN Vendor AS V ON V.vendorId = BI.vendorId "
					+ " INNER JOIN Vendor AS V2 ON V2.vendorId = BI.petrolPumpId "
					+ " WHERE BI.petrolPumpId ="+valueObject.getInt("petrolPumpId",0)+" AND BI.companyId ="+valueObject.getInt("companyId",0)+" AND BI.balanceStock > 0.01 AND BI.markForDelete = 0 ORDER BY BI.fuelInvoiceId ASC", Object[].class);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				fuelInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					fuelInvoiceDto = new FuelInvoiceDto();
					
					fuelInvoiceDto.setFuelInvoiceId((Long) result[0]);
					fuelInvoiceDto.setFuelInvoiceNumber((Long) result[1]);
					if( result[2] != null)
					fuelInvoiceDto.setQuantity((Double) result[2]);
					if( result[3] != null)
					fuelInvoiceDto.setRate((Double) result[3]);
					if(result[4] != null)
					fuelInvoiceDto.setDiscount((Double) result[4]);
					if(result[5] != null)
					fuelInvoiceDto.setGst((Double) result[5]);
					fuelInvoiceDto.setDiscountGstTypeId((short) result[6]);
					if(result[7] != null)
					fuelInvoiceDto.setTotalAmount((Double) result[7]);
					fuelInvoiceDto.setInvoiceDate((Date) result[8]);
					fuelInvoiceDto.setInvoiceNumber((String) result[9]);
					if(result[10] != null)
					fuelInvoiceDto.setInvoiceAmount((Double) result[10]);
					fuelInvoiceDto.setVendorId((Integer) result[11]);
					fuelInvoiceDto.setPetrolPumpId((Integer) result[12]);
					fuelInvoiceDto.setVendorName((String) result[13]);
					fuelInvoiceDto.setPetrolPumpName((String) result[14]);
					fuelInvoiceDto.setDocumentId((Long) result[15]);
					
					fuelInvoiceDto.setInvoiceDateStr(dateFormat.format(fuelInvoiceDto.getInvoiceDate()));
					if(result[16] != null) {
						fuelInvoiceDto.setBalanceStock((Double) result[16]);
					}
					fuelInvoiceList.add(fuelInvoiceDto);
				}
			}
			return fuelInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			fuelInvoiceList 		= null;
			fuelInvoiceDto			= null;
		}
	}
	
	

}
