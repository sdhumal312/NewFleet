package org.fleetopgroup.persistence.service;

import java.io.IOException;
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
import javax.transaction.Transactional;

import org.fleetopgroup.constant.ApprovalType;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.InventoryStockTypeConstant;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PartType;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.ServiceEntriesType;
import org.fleetopgroup.persistence.bl.InventoryBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.PendingVendorPaymentBL;
import org.fleetopgroup.persistence.dao.InventoryRepository;
import org.fleetopgroup.persistence.dao.PartInvoiceRepository;
import org.fleetopgroup.persistence.dto.BankPaymentDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.PartInvoiceDto;
import org.fleetopgroup.persistence.dto.PartLocationPermissionDto;
import org.fleetopgroup.persistence.dto.VendorPaymentNotPaidDto;
import org.fleetopgroup.persistence.model.Inventory;
import org.fleetopgroup.persistence.model.InventoryAll;
import org.fleetopgroup.persistence.model.InventoryLocation;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.model.PartInvoice;
import org.fleetopgroup.persistence.model.PartMeasurementUnit;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDayWiseInventoryStockService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.IPartDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IPartInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.persistence.serviceImpl.IPartMeasurementUnitService;
import org.fleetopgroup.persistence.serviceImpl.IPartWarrantyDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class PartInvoiceService implements IPartInvoiceService {
	
	private final Logger LOGGER 		= LoggerFactory.getLogger(getClass());
	SimpleDateFormat SQLdateFormat 		= new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat sqlDateForm 		= new SimpleDateFormat("dd-MM-yyyy");
	DecimalFormat toFixedTwo  =new DecimalFormat("#.##");
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	public static Long DEFAULT_PURCHAGE_ORDER_VALUE = (long) 0;
	
	@PersistenceContext EntityManager 						entityManager;
	@Autowired private  PartInvoiceRepository				partInvoiceRepository;
	@Autowired 	private IInventoryService 					inventoryService;
	@Autowired  private IPartLocationPermissionService 		partLocationPermissionService;
	@Autowired  private IPartDocumentService 				partDocumentService;
	@Autowired 	private	MongoOperations						mongoOperations;
	@Autowired  private	IVendorService 						vendorService;
	@Autowired	private ICompanyConfigurationService		companyConfigurationService;
	@Autowired	private	IMasterPartsService					masterPartsService;
	@Autowired	private IDayWiseInventoryStockService		dayWiseInventoryStockService;
	@Autowired 	private InventoryRepository 				inventoryRepository;
	@Autowired	private IPendingVendorPaymentService		pendingVendorPaymentService;
	@Autowired private	IPartMeasurementUnitService 		partMeasurementUnitService;
	@Autowired private	IPartWarrantyDetailsService			partWarrantyDetailsService;
	@Autowired 			IBankPaymentService 				bankPaymentService;
	
	
	private static final int PAGE_SIZE = 10;
	
	PartLocationsBL PLBL 			= new PartLocationsBL();
	InventoryBL     inventoryBL		= new InventoryBL();

	@Override
	public void saveInvoice(PartInvoice partInvoice) throws Exception {
		partInvoiceRepository.save(partInvoice);
	}
	
	@Transactional
	public List<PartInvoiceDto> list_Of_All_PartInvoice(Integer pageNumber, Integer companyId)
			throws Exception {
		
		try {
			
			TypedQuery<Object[]> queryt = null;
			queryt = entityManager
					.createQuery("SELECT PI.partInvoiceId, PI.partInvoiceNumber, PI.invoiceNumber, PI.invoiceDate, PI.invoiceAmount, "
							+ " PI.description, PI.paymentTypeId, PI.quantity, PL.partlocation_name, PI.wareHouseLocation, V.vendorName, V.vendorLocation, "
							+ " PI.vendorId, PI.createdOn, PI.lastModifiedBy, PI.createdById, U.firstName, U.lastName,"
							+ " PI.vendorPaymentDate, PI.part_document, PI.part_document_id, PI.vendorPaymentStatus, PI.subLocationId, PSL.partlocation_name "
							+ " FROM PartInvoice as PI "
							+ " INNER JOIN User AS U ON U.id =  PI.createdById "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = PI.wareHouseLocation "
							+ " LEFT JOIN Vendor V ON V.vendorId = PI.vendorId "
							+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = PI.subLocationId "
							+ " WHERE PI.companyId = " + companyId
							+ " AND PI.markForDelete = 0 ORDER BY PI.createdOn desc ", Object[].class);

			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]> results = queryt.getResultList();

			List<PartInvoiceDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<PartInvoiceDto>();
				PartInvoiceDto inve = null;
				for (Object[] result : results) {
					inve = new PartInvoiceDto();

					inve.setPartInvoiceId((long) result[0]);
					inve.setPartInvoiceNumber((long) result[1]);
					inve.setInvoiceNumber((String) result[2]);
					inve.setInvoiceDateOn(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[3], DateTimeUtility.DD_MM_YYYY));
					inve.setInvoiceAmount((String) result[4]);
					inve.setDescription((String) result[5]);
					inve.setPaymentTypeId((short) result[6]);
					if(result[7] != null)
					inve.setQuantity(Double.parseDouble(toFixedTwo.format(result[7])));
					inve.setPartLocation((String) result[8]);
					inve.setWareHouseLocation((Integer) result[9]);
					inve.setVendorName((String) result[10]);
					inve.setVendorLocation((String) result[11]);
					inve.setVendorId((Integer) result[12]);
					inve.setCreatedOn((Timestamp) result[13]);
					inve.setLastModifiedBy((Timestamp) result[14]);
					if(result[15] != null) {
					inve.setCreatedById((long) result[15]);
					}
					if(result[16] != null) {
					inve.setFirstName((String) result[16]);
					}
					if(result[17] != null) {
					inve.setLastName((String) result[17]);
					}
					if(result[18] != null) {
						inve.setVendorPaymentDate((Timestamp) result[18]);
					}
					if(result[19] != null && (boolean)result[19]) {
						inve.setPart_document((boolean) result[19]);
					}	
					if(result[20] != null && (Long)result[20] > 0 ) {
						inve.setPart_document_id((Long) result[20]);
					}
					inve.setVendorPaymentStatus((short) result[21]);
					inve.setVendorPaymentStatusStr(FuelVendorPaymentMode.getPaymentMode(inve.getVendorPaymentStatus()));
					if(result[22] != null) {
						inve.setSubLocationId((Integer) result[22]);
						inve.setSubLocation((String) result[23]);
					}else {
						inve.setSubLocation("");
					}
					
					Dtos.add(inve);
				}
			}
			
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public PartInvoiceDto details_of_PartInvoice(Long partInvoice_id, Integer companyId) throws Exception {
		
		Query query = entityManager.createQuery(
				"SELECT PI.partInvoiceId, PI.partInvoiceNumber, PI.invoiceNumber, PI.invoiceDate, PI.invoiceAmount, "
						+ " PI.description, PI.paymentTypeId, PI.quantity, PL.partlocation_name, PI.wareHouseLocation, V.vendorName, V.vendorLocation, "
						+ " PI.vendorId, PI.createdOn, PI.lastUpdated_Date, PI.createdById, U.firstName, U.lastName, PI.labourCharge,"
						+ " PI.tallyCompanyId , TC.companyName, PI.vendorPaymentStatus, PI.subLocationId, PSL.partlocation_name, PI.purchaseorder_id , PO.purchaseorder_Number,VSA.approvalId,VA.approvalNumber "
						+ " FROM PartInvoice as PI "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = PI.wareHouseLocation "
						+ " INNER JOIN User AS U ON U.id =  PI.createdById "
						+ " LEFT JOIN Vendor V ON V.vendorId = PI.vendorId "
						+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = PI.tallyCompanyId "
						+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = PI.subLocationId "
						+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = PI.purchaseorder_id "
						+ " LEFT JOIN VendorSubApprovalDetails VSA ON VSA.invoiceId = PI.partInvoiceId AND VSA.approvalPlaceId = "+ApprovalType.APPROVAL_TYPE_INVENTORY_PART_INVOICE+" "
						+ " LEFT JOIN VendorApproval VA ON VA.approvalId = VSA.approvalId "
						+ " WHERE PI.companyId = " + companyId
						+ " AND PI.partInvoiceId = "+partInvoice_id+" AND  PI.markForDelete = 0 ORDER BY PI.createdOn desc ");


		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		PartInvoiceDto inve;
		if (result != null) {
			inve	= new PartInvoiceDto();
			inve.setPartInvoiceId((long) result[0]);
			inve.setPartInvoiceNumber((long) result[1]);
			inve.setInvoiceNumber((String) result[2]);
			inve.setInvoiceDateOn(sqlDateForm.format((Date) result[3]));
			inve.setInvoiceAmount((String) result[4]);
			inve.setDescription((String) result[5]);
			inve.setPaymentTypeId((short) result[6]);
			inve.setPaymentType(PaymentTypeConstant.getPaymentTypeName(inve.getPaymentTypeId()));
			inve.setQuantity((double) result[7]);
			inve.setPartLocation((String) result[8]);
			inve.setWareHouseLocation((Integer) result[9]);
			inve.setVendorName((String) result[10]);
			inve.setVendorLocation((String) result[11]);
			inve.setVendorId((Integer) result[12]);
			inve.setCreatedOn((Timestamp) result[13]);
			inve.setLastUpdated_Date((Timestamp) result[14]);
			if(result[15] != null) {
			inve.setCreatedById((long) result[15]);
			}
			if(result[16] != null) {
			inve.setFirstName((String) result[16]);
			}
			if(result[17] != null) {
			inve.setLastName((String) result[17]);
			}
			if(result[18] != null) 
				inve.setLabourCharge((Double) result[18]);
			
			if(result[19] != null) 
				inve.setTallyCompanyId((Long) result[19]);
			
			if(result[20] != null) {
				inve.setTallyCompanyName((String) result[20]);
			}
				inve.setVendorPaymentStatus((short) result[21]);
				inve.setVendorPaymentStatusStr(FuelVendorPaymentMode.getPaymentMode(inve.getVendorPaymentStatus()));
			if(result[22] != null) 
				inve.setSubLocationId((Integer) result[22]);
				inve.setSubLocation((String) result[23]);
			if(result[24] != null) {
				inve.setPurchaseorder_id((Long) result[24]);
				inve.setPoNumber("PO-"+(Long) result[25]);
			}
			if(result[26] != null) {
				inve.setPartApprovalId((Long) result[26]);
				inve.setApprovalNumber("A-"+(Long) result[27]);
			}
			
		} else {
			return null;
		}

		return inve;
		
	}
	
	
	@Transactional
	public List<InventoryDto> details_of_Inventory(Long partInvoice_id, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager.createQuery(
					"SELECT I.inventory_id, I.inventory_Number, I.partid, MP.partnumber, MP.partname, MP.partTypeId, PC.pcName,  PM.pmName, PL.partlocation_name,  I.quantity,"
							+ " I.history_quantity, I.unitprice, I.purchaseorder_id, PO.purchaseorder_Number, I.invoice_number, I.invoice_amount, I.invoice_date, I.vendor_id, V.vendorName, V.vendorLocation, "
							+ " MU.pmuSymbol, I.discount, I.tax, I.total, I.description, I.inventory_location_id, "
							+ " I.inventory_all_id, V.vendorTypeId, I.discountTaxTypeId, MP.isWarrantyApplicable, I.serialNoAddedForParts, "
							+ " MP.unitTypeId, MU.convertTo, MP.assetIdRequired "
							+ " FROM Inventory AS I "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = I.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = I.locationId "
							+ " INNER JOIN InventoryAll IAL ON IAL.inventory_all_id = I.inventory_all_id "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = I.vendor_id "
							+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
							+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = I.purchaseorder_id "
							+ " LEFT JOIN PartMeasurementUnit MU ON MU.pmuid = MP.unitTypeId"
							+ " WHERE I.partInvoiceId = "+partInvoice_id+" AND IAL.companyId = "+companyId+" AND I.markForDelete = 0 ",
					Object[].class);
			/*queryt.setParameter("Invid", partInvoice_id);
			queryt.setParameter("comID", companyId);*/
			List<Object[]> results = queryt.getResultList();

			List<InventoryDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryDto>();
				InventoryDto Dto = null;
				for (Object[] result : results) {
					Dto = new InventoryDto();

					Dto.setInventory_id((Long) result[0]);
					Dto.setInventory_Number((Long) result[1]);
					Dto.setPartid((Long) result[2]);
					Dto.setPartnumber((String) result[3]);
					Dto.setPartname((String) result[4]);
					Dto.setParttype(PartType.getPartTypeName((short) result[5]));
					Dto.setCategory((String) result[6]);
					Dto.setMake((String) result[7]);
					Dto.setLocation((String) result[8]);
					if ((Double) result[9] != null) {
						Dto.setQuantity(Double.parseDouble(toFixedTwo.format(result[9])));
						
					}

					if ((Double) result[10] != null) {
						Dto.setHistory_quantity(Double.parseDouble(toFixedTwo.format(result[10])));
					}

					if ((Double) result[11] != null) {
						Dto.setUnitprice(round((Double) result[11], 2));
					}
					Dto.setPurchaseorder_id((Long) result[12]);
					Dto.setPurchaseorder_Number((Long) result[13]);
					Dto.setInvoice_number((String) result[14]);
					Dto.setInvoice_amount((String) result[15]);
					if ((Date) result[16] != null) {
						Dto.setInvoice_date(SQLdateFormat.format((Date) result[16]));
					}
					Dto.setVendor_id((Integer) result[17]);
					Dto.setVendor_name((String) result[18]);
					Dto.setVendor_location((String) result[19]);
					Dto.setUnittype((String) result[20]);
					Dto.setDiscount((Double) result[21]);
					Dto.setTax((Double) result[22]);
					if ((Double) result[23] != null) {
						Dto.setTotal(round((Double) result[23], 2));
					}
					Dto.setDescription((String) result[24]);

					Dto.setInventory_location_id((Long) result[25]);
					Dto.setInventory_all_id((Long) result[26]);
					if(result[27] != null)
						Dto.setVendorTypeId((long) result[27]);
					
					if(result[28] != null) {
						Dto.setDiscountTaxTypeId((short) result[28]);
					} else {
						Dto.setDiscountTaxTypeId((short) 1);
					}
					
					Dto.setWarranty((boolean) result[29]);
					Dto.setSerialNoAddedForParts((Integer) result[30]);
					Dto.setUnitTypeId((Long) result[31]);
					Dto.setConvertTo((Integer) result[32]);
					Dto.setAssetIdRequired((boolean) result[33]);
					
					if(Dto.isAssetIdRequired()) {
						Dto.setWarranty(Dto.isAssetIdRequired());
					}

					Dtos.add(Dto);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		} finally {
			queryt = null;
		}
	}
	
	@Override
	public List<PartInvoiceDto> FilterVendorCreditListInventoryPartInvoice(Integer vendorId, String dateRangeFrom, String dateRangeTo, Integer company_id) throws Exception {

		List<PartInvoiceDto>			partInvoiceList		= null;
		PartInvoiceDto					partInvoice 			= null;
		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]>					results 				= null;

		try {
			
			typedQuery = entityManager
					.createQuery("SELECT PI.partInvoiceId, PI.partInvoiceNumber, PI.invoiceNumber, PI.invoiceDate, "
							+ " PI.invoiceAmount, PI.vendorId, PI.paymentTypeId, PI.vendorPaymentStatus, V.vendorName, "
							+ " PI.purchaseorder_id,PI.paidAmount, PI.balanceAmount, PI.part_document_id "
							+ " FROM PartInvoice AS PI "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = PI.vendorId "
							+ " where PI.invoiceDate BETWEEN '"+dateRangeFrom+"' AND '"+dateRangeTo+"'"
							+ " AND PI.vendorId = "+vendorId+" AND PI.paymentTypeId = "+PaymentTypeConstant.PAYMENT_TYPE_CREDIT
							+ " AND PI.companyId = "+company_id+" AND  PI.markForDelete = 0 AND (purchaseorder_id is null OR purchaseorder_id <= 0) "
							+ " AND (PI.vendorPaymentStatus = " + FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID + " "
							+ " OR PI.vendorPaymentStatus = " + FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID + "AND PI.balanceAmount > 0)  ORDER BY PI.invoiceDate DESC "
							 , Object[].class);
							
			
			results = typedQuery.getResultList();
			
			if(results != null && !results.isEmpty()) {
				partInvoiceList	=	new ArrayList<>();
				for (Object[] result : results) {
					
					partInvoice = new PartInvoiceDto();
					partInvoice.setPartInvoiceId((Long) result[0]);
					partInvoice.setPartInvoiceNumber((Long) result[1]);
					partInvoice.setInvoiceNumber((String) result[2]);
					partInvoice.setInvoiceDate((Timestamp) result[3]);
					partInvoice.setInvoiceAmount((String) result[4]);
					partInvoice.setVendorId((Integer) result[5]);
					partInvoice.setPaymentTypeId((short) result[6]);
					if(result[7] != null ) {
					partInvoice.setVendorPaymentStatus((short) result[7]);
					}
					partInvoice.setVendorName((String) result[8]);
					if(result[9]!=null) {
					partInvoice.setPurchaseorder_id((long) result[9]);
					}
					if(result[10]!= null) {
					partInvoice.setPaidAmount(Double.parseDouble(toFixedTwo.format(result[10])));
					}
					if(result[11]!= null) {
					partInvoice.setBalanceAmount(Double.parseDouble(toFixedTwo.format(result[11])));
					}
					if(result[12]!= null) {
						partInvoice.setPart_document_id((Long) result[12]);
					}
				
					/*partInvoice.setPaymentNumber((String) result[5]);
					partInvoice.setDescription((String) result[7]);
					partInvoice.setVendorPaymentDate((Timestamp) result[10]);
					partInvoice.setPoNumber((String) result[11]);
					partInvoice.setBatteryLocation((String) result[12]);
					partInvoice.setWareHouseLocation((Integer) result[13]);
					partInvoice.setVendorLocation((String) result[15]);*/
					partInvoiceList.add(partInvoice);
					
				}
			}
			
			return partInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			partInvoiceList		= null;
			partInvoice 			= null;
			typedQuery 				= null;
			results 				= null;
		}
	
	}
	
	@Override
	public List<PartInvoiceDto> getVendorApproval_IN_InventoryPartInvoice_List(Long approvalId, Integer company_id) throws Exception {

		List<PartInvoiceDto>			partInvoiceList			= null;
		PartInvoiceDto					partInvoice 			= null;
		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]>					results 				= null;

		try {
			
			typedQuery = entityManager
					.createQuery("SELECT PI.partInvoiceId, PI.partInvoiceNumber, PI.invoiceNumber, PI.invoiceDate, PI.invoiceAmount,"
							+ " PI.description, PI.vendorPaymentStatus, PI.paymentTypeId,"
							+ " PI.vendorPaymentDate, PL.partlocation_name, PI.wareHouseLocation, V.vendorName, V.vendorLocation,"
							+ " PI.vendorId,PI.paidAmount,PI.balanceAmount "
							+ " FROM PartInvoice AS PI "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = PI.wareHouseLocation "
							+ " LEFT JOIN Vendor V ON V.vendorId = PI.vendorId"
							+ " where PI.partApprovalId = "+approvalId+" AND PI.companyId = "+company_id+" AND  PI.markForDelete = 0 ", Object[].class);
			
			results = typedQuery.getResultList();
			
			if(results != null && !results.isEmpty()) {
				partInvoiceList	=	new ArrayList<>();
				for (Object[] result : results) {
					partInvoice = new PartInvoiceDto();
					
					partInvoice.setPartInvoiceId((Long) result[0]);
					partInvoice.setPartInvoiceNumber((Long) result[1]);
					partInvoice.setInvoiceNumber((String) result[2]);
					partInvoice.setInvoiceDate((Timestamp) result[3]);
					partInvoice.setInvoiceAmount((String) result[4]);
					//partInvoice.setnumber((String) result[5]);
					//partInvoice.setTotalBatteryAmount((Double) result[6]);
					partInvoice.setDescription((String) result[5]);
					partInvoice.setVendorPaymentStatus((short) result[6]);
					partInvoice.setPaymentTypeId((short) result[7]);
					partInvoice.setVendorPaymentDate((Timestamp) result[8]);
					//partInvoice.setPoNumber((String) result[11]);
					partInvoice.setPartLocation((String) result[9]);
					partInvoice.setWareHouseLocation((Integer) result[10]);
					partInvoice.setVendorName((String) result[11]);
					partInvoice.setVendorLocation((String) result[12]);
					partInvoice.setVendorId((Integer) result[13]);
					if((Double) result[14] != null)
					partInvoice.setPaidAmount((Double) result[14]);
					if((Double) result[15] != null)
					partInvoice.setBalanceAmount((Double) result[15]);
					partInvoiceList.add(partInvoice);
					
					
				}
			}
			
			return partInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			partInvoiceList			= null;
			partInvoice 			= null;
			typedQuery 				= null;
			results 				= null;
		}
	
	
	}
	
	@Transactional
	public void update_Vendor_ApprovalTO_Status_MULTIP_InventoryPartInvoice_ID(String ApprovalInvoice_ID,
			Long approvalId, short approval_Status) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		entityManager.createQuery(" UPDATE PartInvoice SET partApprovalId=" + approvalId
				+ ", vendorPaymentStatus=" + approval_Status + " WHERE partInvoiceId IN (" + ApprovalInvoice_ID + ") AND companyId = "+userDetails.getCompany_id()+" ")
				.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentDetails(Long approvalId, String paymentDate, short paymentStatus) throws Exception {

		entityManager.createQuery("  UPDATE PartInvoice SET vendorPaymentDate ='" + paymentDate+"' , "
				+ " vendorPaymentStatus ="+paymentStatus+""
				+ " WHERE partApprovalId = "+approvalId+" ").executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception {
		entityManager.createQuery("  UPDATE PartInvoice SET vendorPaymentDate =" + paymentDate+" , "
				+ " vendorPaymentStatus="+paymentStatus+", partApprovalId = "+approvalId+""
				+ " WHERE partInvoiceId = "+invoiceId+" ").executeUpdate();
		
	}
	
	
	@Transactional
	public List<PartInvoiceDto> get_Amount_VendorApproval_IN_InventoryPartInvoice(Long VendorApproval_Id, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT PI.partInvoiceId, PI.invoiceAmount From PartInvoice as PI WHERE PI.partApprovalId=:approval AND PI.companyId =:companyId AND PI.markForDelete = 0",
				Object[].class);
		queryt.setParameter("approval", VendorApproval_Id);
		queryt.setParameter("companyId", companyId);

		List<Object[]> results = queryt.getResultList();

		List<PartInvoiceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PartInvoiceDto>();
			PartInvoiceDto list = null;
			for (Object[] result : results) {
				list = new PartInvoiceDto();

				list.setPartInvoiceId((Long) result[0]);
				list.setTotalPartAmount(Double.parseDouble((String)result[1]));

				Dtos.add(list);
			}
		}

		return Dtos;
	}
	
	@Override
	public PartInvoiceDto getPartInvoice(PartInvoiceDto part) throws Exception {

		Query query  = entityManager
				.createQuery("SELECT PI.partInvoiceId, PI.partInvoiceNumber,"
						+ " PI.invoiceDate, PI.invoiceAmount,"
						+ " PI.description, PI.vendorPaymentStatus, PI.paymentTypeId,"
						+ " PI.vendorPaymentDate,PL.partlocation_name, PI.wareHouseLocation, V.vendorName, V.vendorLocation,"
						+ " PI.vendorId, PI.createdOn, PI.lastModifiedBy, PI.partApprovalId "
						+ " FROM PartInvoice AS PI "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = PI.wareHouseLocation "
						+ " LEFT JOIN Vendor V ON V.vendorId = PI.vendorId"
						+ " where PI.partInvoiceId = "+part.getPartInvoiceId()+" AND PI.companyId = "+part.getCompanyId()+" AND  PI.markForDelete = 0 ");
		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		PartInvoiceDto	partInvoice = null;
			if (result != null) {
				partInvoice = new PartInvoiceDto();
				
				partInvoice.setPartInvoiceId((Long) result[0]);
				partInvoice.setPartInvoiceNumber((Long) result[1]);
				//partInvoice.setInvoiceNumber((String) result[2]);
				partInvoice.setInvoiceDate((Timestamp) result[2]);
				partInvoice.setInvoiceAmount((String) result[3]);
				//partInvoice.setPaymentNumber((String) result[5]);
				//partInvoice.setTotalPartAmount(Double.parseDouble(""+result[4]));
				partInvoice.setDescription((String) result[4]);
				partInvoice.setVendorPaymentStatus((short) result[5]);
				partInvoice.setPaymentTypeId((short) result[6]);
				partInvoice.setVendorPaymentDate((Timestamp) result[7]);
				//partInvoice.setPoNumber((String) result[11]);
				partInvoice.setPartLocation((String) result[8]);
				partInvoice.setWareHouseLocation((Integer) result[9]);
				partInvoice.setVendorName((String) result[10]);
				partInvoice.setVendorLocation((String) result[11]);
				partInvoice.setVendorId((Integer) result[12]);
				partInvoice.setCreatedOn((Timestamp) result[13]);
				partInvoice.setLastModifiedBy((Timestamp) result[14]);
				partInvoice.setPartApprovalId((Long) result[15]);
				
				
				if(partInvoice.getInvoiceDate() != null) {
					partInvoice.setPartInvoiceDateStr(DateTimeUtility.getDateFromTimeStamp(partInvoice.getInvoiceDate(), DateTimeUtility.DD_MM_YYYY));
				}
				if(partInvoice.getVendorName() == null) {
					partInvoice.setVendorName("--");
				}
				
			}
		return partInvoice;
	}
	
	
	@Transactional
	public void update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryPartInvoice_ID(String ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp approval_date, Integer companyId,double paidAmount,double discountAmount) throws Exception {
		double balanceAmount =	0;

		entityManager.createQuery("  UPDATE PartInvoice SET partApprovalId=" + Approval_ID
				+ ",vendorPaymentDate='" + approval_date + "', vendorPaymentStatus='" + approval_Status + "', paidAmount ='"+paidAmount+"', discountAmount ='"+discountAmount+"',balanceAmount='"+balanceAmount+"' "
				+ " WHERE partInvoiceId IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();
	}

	
	
	@Transactional
	public PartInvoice getPartInvoiceDetails(Long partInvoiceId, Integer companyId) throws Exception {
		return partInvoiceRepository.getPartInvoiceDetails(partInvoiceId, companyId);

	}
	
	@Transactional
	public void delete_PartInvoice(Long partInvoiceId, Integer companyId) throws Exception {

		partInvoiceRepository.delete_PartInvoice(partInvoiceId, companyId);
	}
	
	//Used this method when delete is executed
	@Transactional
	public void update_Quantity_Of_PartInvoice(Long partInvoiceId, Integer companyId, Double quantity, String InvoiceAmount)
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		try {
			entityManager.createQuery(
					"UPDATE PartInvoice SET quantity = quantity - "+quantity+", invoiceAmount = "+InvoiceAmount+"  "
						+ " WHERE partInvoiceId = "+partInvoiceId+" AND companyId = "+userDetails.getCompany_id()+" "
						+ " AND markForDelete = 0 ")
					.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	// Used this method when more parts are added.
	@Transactional
	public void update_QuantityAndInvoiceAmount_Of_PartInvoice(Long partInvoiceId, Integer companyId, Double quantity, String invoiceAmount)
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		
		try {
			entityManager.createQuery(
					"UPDATE PartInvoice SET quantity = quantity + "+quantity+", invoiceAmount = "+invoiceAmount+", "
						+ " lastUpdated_Date = '"+DateTimeUtility.getCurrentTimeStamp()+"', lastModifiedById = "+userDetails.getId()+"  "
						+ " WHERE partInvoiceId = "+partInvoiceId+" AND companyId = "+userDetails.getCompany_id()+" "
						+ " AND markForDelete = 0 ")
					.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	@Transactional
	public Map<String, Object> deletePartInvoiceInventory(ValueObject valueObject) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Long InvoiceID;
		Long InventoryId;
		boolean partAssign;
		double invoiceAmnt;
		String finalInvoiceAmount;
		Double	invoiceQuantity = 0.0;
		
		try {
			InventoryId = valueObject.getLong("inventory_id");
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String,Object> companyConfiguration =companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG );
			
			Inventory inventoryValidate = inventoryService.getInventoryDetails(InventoryId, userDetails.getCompany_id());
			
			if(inventoryValidate.getMainQuantity() != null && inventoryValidate.getMainQuantity() > 0) {
				invoiceQuantity	= inventoryValidate.getMainQuantity();
			}else {
				invoiceQuantity	= inventoryValidate.getQuantity();
			}			
			
			InvoiceID = inventoryValidate.getPartInvoiceId();
			
			PartInvoice invoiceValidate = getPartInvoiceDetails(inventoryValidate.getPartInvoiceId(), userDetails.getCompany_id());
			partAssign  = invoiceValidate.isAnyPartNumberAsign();
			if (partAssign) {
				model.put("PartAssigned", true);
			}else if(invoiceValidate.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && invoiceValidate.getVendorPaymentStatus() != ServiceEntriesType.PAYMENT_MODE_NOT_PAID) {
				model.put("paymentDone", true);
			} else {
				
				HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
				
				inventoryService.update_InventoryAll_Quantity_From_PartInvoice(inventoryValidate.getInventory_all_id(),
						userDetails.getCompany_id(), inventoryValidate.getQuantity());
				
				inventoryService.update_InventoryLocation_Quantity_From_PartInvoice(inventoryValidate.getInventory_location_id(),
						userDetails.getCompany_id(), inventoryValidate.getQuantity());
				
				invoiceAmnt= Double.parseDouble(invoiceValidate.getInvoiceAmount()) - inventoryValidate.getTotal();
				finalInvoiceAmount = invoiceAmnt+"";
				update_Quantity_Of_PartInvoice(inventoryValidate.getPartInvoiceId(), userDetails.getCompany_id(), 
							invoiceQuantity, finalInvoiceAmount);
				
				pendingVendorPaymentService.updatePendingVendorPaymentAmt(inventoryValidate.getPartInvoiceId(), PendingPaymentType.PAYMENT_TYPE_PART_INVOICE, invoiceAmnt);
				
				inventoryService.deleteInventory(InventoryId, userDetails.getCompany_id());
				
				inventoryService.update_InvoiceAmountOf_Inventory(inventoryValidate.getPartInvoiceId(), userDetails.getCompany_id(), finalInvoiceAmount);
				
				partWarrantyDetailsService.deleteWarrantyPartByInventoryId(InventoryId);
				if((boolean) companyConfiguration.getOrDefault("allowBankPaymentDetails",false)) {
				ValueObject bankPaymentValueObject=new ValueObject();
				bankPaymentValueObject.put("oldPaymentTypeId",invoiceValidate.getPaymentTypeId());
				bankPaymentValueObject.put("bankPaymentTypeId", invoiceValidate.getPaymentTypeId());
				bankPaymentValueObject.put("currentPaymentTypeId", invoiceValidate.getPaymentTypeId());
				bankPaymentValueObject.put("userId",userDetails.getId());
				bankPaymentValueObject.put("companyId",userDetails.getCompany_id());
				bankPaymentValueObject.put("moduleId",invoiceValidate.getPartInvoiceId());
				bankPaymentValueObject.put("moduleNo", invoiceValidate.getPartInvoiceNumber());
				bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.PART_INVENTORY);
				bankPaymentValueObject.put("amount",finalInvoiceAmount);
				
				Vendor	vendor	=  vendorService.getVendor(invoiceValidate.getVendorId());
				bankPaymentValueObject.put("remark", "Delete Part Invoice PI-"+invoiceValidate.getPartInvoiceNumber()+" vendor : "+vendor.getVendorName());
			
				
				bankPaymentService.updatePaymentDetailsFromValuObject(bankPaymentValueObject);
				}
				
				if((boolean) configuration.get("showRefreshmentOption")) {
					MasterParts	masterParts	=	masterPartsService.getMasterParts(inventoryValidate.getPartid());
					if(masterParts != null && masterParts.isRefreshment()) {
						ValueObject	valueObject2	= new ValueObject();
						if(inventoryValidate.getInvoice_date() != null) {
							valueObject2.put("transactionDate", inventoryValidate.getInvoice_date());
						}else {
							valueObject2.put("transactionDate", DateTimeUtility.getCurrentDate());
						}
						valueObject2.put("partId", inventoryValidate.getPartid());
						valueObject2.put("locationId",  inventoryValidate.getLocationId());
						valueObject2.put("quantity", - inventoryValidate.getQuantity());
						valueObject2.put("companyId", userDetails.getCompany_id());
						valueObject2.put("addedQuantity", inventoryValidate.getQuantity());
						valueObject2.put("usedQuantity", 0.0);
						valueObject2.put("numberType", "I-"+ inventoryValidate.getInventory_Number()+" Part Delete");
						valueObject2.put("transactionId", inventoryValidate.getInventory_id());
						valueObject2.put("primaryType", InventoryStockTypeConstant.STOCK_TYPE_ADD);
						valueObject2.put("transactionType", InventoryStockTypeConstant.STOCK_TYPE_DELETED);
						
						dayWiseInventoryStockService.processLocationWiseInventoryStockDetails(valueObject2);	
					}
				}
				
				model.put("deleteSuccess", true);
			}
			
			model.put("InvoiceID", InvoiceID);
			return model;
			
		} catch (Exception e) {
			throw e;
		}finally {
			
		}
	}
	
	@Override
	@Transactional
	public Map<String, Object> deletePartInvoice(ValueObject valueObject) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Inventory> inventory = null;
		long InvoiceID;
		try {
			InvoiceID = valueObject.getLong("InvoiceID");
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			inventory = inventoryService.Get_InventoryDetails_From_PartInvoiceId(InvoiceID, userDetails.getCompany_id());
			if (inventory != null && !inventory.isEmpty()) {
				model.put("deleteFrist", true);
			} else {
				delete_PartInvoice(InvoiceID, userDetails.getCompany_id());
				pendingVendorPaymentService.deletePendingVendorPayment(InvoiceID, PendingPaymentType.PAYMENT_TYPE_PART_INVOICE);
				
				bankPaymentService.deleteBankPaymentDetailsIfTransactionDeleted(InvoiceID, ModuleConstant.PART_INVENTORY, userDetails.getCompany_id(),userDetails.getId(),false);
				model.put("deleteSuccess", true);
				
			}
			
			return model;
			
		} catch (Exception e) {
			throw e;
		}finally {
			
		}
	}
	
	
	@Override
	@Transactional
	public Map<String, Object> updatePartInventoryInvoice(ValueObject valueObject, MultipartFile file) throws Exception {
		Map<String, Object> model = new HashMap<>();
		PartInvoiceDto PartInvoiceDto = null;
		final String[] inventory_id;
		final String[] partid;
		final String[] make;
		final String[] disTaxTypeId;
		final String[] quantity;
		final String[] unitprice;
		final String[] discount;
		final String[] tax;
		final boolean anyPartNumberAsign;
		PartInvoice  getPartInvoice = null;
		PendingVendorPayment	payment  = null;
		BankPaymentDto 	bankPaymentDto  = null;
		
		try {
			
			PartInvoiceDto   	= (PartInvoiceDto) valueObject.get("PartInvoiceDto");
			inventory_id	 	= (String[]) valueObject.get("inventory_id");
			partid		 	 	= (String[]) valueObject.get("partid");
			make			 	= (String[]) valueObject.get("make");
			disTaxTypeId		= (String[]) valueObject.get("disTaxTypeId");
			quantity		 	= (String[]) valueObject.get("quantity");
			unitprice	 	 	= (String[]) valueObject.get("unitprice");
			discount	 	 	= (String[]) valueObject.get("discount");
			tax		 	 	 	= (String[]) valueObject.get("tax");
			anyPartNumberAsign	= (boolean) valueObject.get("anyPartNumberAsign");
			bankPaymentDto		= (BankPaymentDto) valueObject.get("bankPaymentDto");

			
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtility.DD_MM_YY);
			Double Qty = 0.0;
			Double FinalQunatity = 0.0;
			boolean isInvoiceChanged	= false;
			
			if (PartInvoiceDto.getPartInvoiceId() != null) {
				
				getPartInvoice =	getPartInvoiceDetails(PartInvoiceDto.getPartInvoiceId() , userDetails.getCompany_id());
				
				PartInvoice PartInvoice = prepare_UpdatePartInvoice(PartInvoiceDto);
				
				short oldPaymentTypeId	= getPartInvoice.getPaymentTypeId();
				
				if(!getPartInvoice.equals(PartInvoice)) {
					isInvoiceChanged	= true;
				}
				if(oldPaymentTypeId != PartInvoice.getPaymentTypeId() && PartInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForPI(PartInvoice);
					pendingVendorPaymentService.savePendingVendorPayment(payment);
				}else if(oldPaymentTypeId == PartInvoice.getPaymentTypeId() && PartInvoice.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && isInvoiceChanged) {
					payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForPI(PartInvoice);
					pendingVendorPaymentService.updatePendingVendorPayment(payment);
				}else if(oldPaymentTypeId != PartInvoice.getPaymentTypeId() && PartInvoice.getPaymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					pendingVendorPaymentService.deletePendingVendorPayment(PartInvoice.getPartInvoiceId(), PendingPaymentType.PAYMENT_TYPE_PART_INVOICE);
				}
				//TO-Do neet to add permision condiion here
				if(bankPaymentDto.isAllowBankPaymentDetails()) {
				ValueObject bankPaymentValueObject=new ValueObject();
				bankPaymentValueObject.put("oldPaymentTypeId", oldPaymentTypeId);
				bankPaymentValueObject.put("currentPaymentTypeId", PartInvoice.getPaymentTypeId());
				bankPaymentValueObject.put("userId", userDetails.getId());
				bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
				bankPaymentValueObject.put("moduleId", PartInvoice.getPartInvoiceId());
				bankPaymentValueObject.put("moduleNo", PartInvoice.getPartInvoiceNumber());
				bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.PART_INVENTORY);
				bankPaymentValueObject.put("amount",PartInvoice.getInvoiceAmount());
				
				Vendor	vendor	= vendorService.getVendor(PartInvoice.getVendorId());
				bankPaymentValueObject.put("remark","Part Invoice Edit On PI-"+PartInvoice.getPartInvoiceNumber()+" Vendor : "+vendor.getVendorName()+"  ");
				
				bankPaymentService.updatePaymentDetails(bankPaymentValueObject, bankPaymentDto);
				}

				if (!file.isEmpty()) {
					PartInvoice.setPart_document(true);
				} else {
					PartInvoice.setPart_document(false);
				}

				if (!file.isEmpty()) {
					org.fleetopgroup.persistence.document.PartDocument partDoc = new org.fleetopgroup.persistence.document.PartDocument();
					partDoc.setPart_id(PartInvoice.getPartInvoiceId());
					try {

						byte[] bytes = file.getBytes();
						partDoc.setPart_filename(file.getOriginalFilename());
						partDoc.setPart_content(bytes);
						partDoc.setPart_contentType(file.getContentType());
						partDoc.setMarkForDelete(false);
						partDoc.setCreatedById(userDetails.getId());
						partDoc.setLastModifiedById(userDetails.getId());

						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

						partDoc.setCreated(toDate);
						partDoc.setLastupdated(toDate);
						partDoc.setCompanyId(userDetails.getCompany_id());
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					org.fleetopgroup.persistence.document.PartDocument doc = partDocumentService.getPartDocByPartInvoiceId(PartInvoice.getPartInvoiceId(), userDetails.getCompany_id());
					
					if (doc != null) {
						
						partDoc.set_id(doc.get_id());
						mongoOperations.save(partDoc);
						
						inventoryService.Update_PartDocument_ID_to_Part(partDoc.get_id(), true, PartInvoice.getPartInvoiceId(),
								userDetails.getCompany_id());
						
					}else {
						// Note: Save FuelDocument Details
						partDocumentService.add_Part_To_PartDocument(partDoc);

						// Note: FuelDocument to Save id to Fuel save
						inventoryService.Update_PartDocument_ID_to_Part(partDoc.get_id(), true, PartInvoice.getPartInvoiceId(),
								userDetails.getCompany_id());
					}
					
				}
				
				if (partid != null && !anyPartNumberAsign) {
					Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
					MasterParts	masterParts	= null;
					PartMeasurementUnit	measurementUnit	= null;
					for (int i = 0; i < partid.length; i++) {
							
							Long MasterPart_ID = Long.parseLong(partid[i]);
							masterParts		= masterPartsService.getMasterParts(MasterPart_ID);
							measurementUnit	= measermentHM.get(Integer.parseInt(masterParts.getUnitTypeId()+""));
							
							Double originalQuantity		= Double.parseDouble(quantity[i]);
							Double originalUnitPrice	= Double.parseDouble(unitprice[i]);
							if(measurementUnit != null && measurementUnit.getConvertTo() != null && measurementUnit.getConvertTo() > 0) {
								quantity[i] = (Double.parseDouble(quantity[i]) * measurementUnit.getConversionRate())+"";
								unitprice[i] = (Double.parseDouble(unitprice[i]) / measurementUnit.getConversionRate())+"";
							}
						
						List<PartLocationPermissionDto> PartLocPermission = PLBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

						// check user branch location and workOrder location if same
						if (inventoryBL.isFilled_GET_Permission(PartInvoice.getWareHouseLocation(), PartLocPermission)) {
							InventoryDto getOLd_Inventory = inventoryService.getInventory(Long.parseLong(inventory_id[i]+""));
							HashMap<String, Object>  configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
						
							InventoryAll Inventoryall = inventoryBL.prepareInventoryAll(Long.parseLong(partid[i]+""));
							InventoryLocation InventoryLocation = inventoryBL.updateInventoryLocation(Long.parseLong(inventory_id[i]+""),
									Long.parseLong(partid[i]+""), PartInvoice.getWareHouseLocation());
							InventoryLocation.setCompanyId(Inventoryall.getCompanyId());
							Qty += originalQuantity;
							
							Inventory inventory = new Inventory();
							inventory.setInventory_id(Long.parseLong(inventory_id[i]+""));	
							inventory.setPartid(Long.parseLong(partid[i]+""));
							inventory.setLocationId(PartInvoice.getWareHouseLocation());
							inventory.setUnitprice(Double.parseDouble(unitprice[i]));
							inventory.setPurchaseorder_id(DEFAULT_PURCHAGE_ORDER_VALUE);
							inventory.setInvoice_number(PartInvoice.getInvoiceNumber());
							inventory.setInvoice_amount(PartInvoice.getInvoiceAmount());
							if (PartInvoice.getInvoiceDate() != null) {
								
								inventory.setInvoice_date(new Date(PartInvoice.getInvoiceDate().getTime()));
							}

							if (PartInvoice.getVendorId() != 0) {
								inventory.setVendor_id(PartInvoice.getVendorId());
							} else {
								inventory.setVendor_id(0);
							}

							inventory.setDescription(PartInvoice.getDescription());
							inventory.setCreatedById(userDetails.getId());
							inventory.setLastModifiedById(userDetails.getId());
							inventory.setMainQuantity(originalQuantity);
							inventory.setMainUnitprice(originalUnitPrice);

							Date currentDateUpdate = new Date();
							Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
							//inventory.setCreated(toDate);
							inventory.setLastupdated(toDate);
							inventory.setMarkForDelete(false);
							inventory.setPartInvoiceId(PartInvoice.getPartInvoiceId());
							inventory.setCompanyId(Inventoryall.getCompanyId());
							inventory.setSubLocationId(PartInvoice.getSubLocationId());
							
							// To get the details of inventory before editing.
							Inventory inventoryDetails = inventoryService.getInventoryDetails(Long.parseLong(inventory_id[i]+""), userDetails.getCompany_id());
							Long previousPartId = inventoryDetails.getPartid();
							Long previousInventoryAllId = inventoryDetails.getInventory_all_id();
							Long previousLocationId = inventoryDetails.getInventory_location_id();
							Double previousQuantity = inventoryDetails.getQuantity();
							Double previousHistoryQuantity = inventoryDetails.getHistory_quantity();
							
							// save part Manufacturers Service in Master part
							try {
								// first validate part_id is there are not in Inventory
								// All Table
							List<InventoryAll> validate = inventoryService.Validate_InventoryAll(Long.parseLong(partid[i]+""),	Inventoryall.getCompanyId());
							if (validate != null && !validate.isEmpty()) {

								Long Inventory_all_id = (long) 0;
								Long Inventory_Location_id = (long) 0;

								for (InventoryAll updateInventory : validate) {
									// get part and AllQuantity in all_Ready in db

									Inventory_all_id = updateInventory.getInventory_all_id();

									break;
								}
								List<InventoryLocation> validate_Location = inventoryService.Validate_Inventory_Location(Long.parseLong(partid[i]+""), PartInvoice.getWareHouseLocation());
								if (validate_Location != null && !validate_Location.isEmpty()) {
									for (InventoryLocation validateLocation : validate_Location) {
										// get part and Location Quantity in
										// all_Ready in db Add New Quantity
										Inventory_Location_id = validateLocation.getInventory_location_id();
										break;
									}
								} else {

									// get part and Location Quantity Search that
									// location part not in db Create new
									InventoryLocation.setLocation_quantity(Double.parseDouble(quantity[i]));
									InventoryAll NewAll = new InventoryAll();
									NewAll.setInventory_all_id(Inventory_all_id);
									InventoryLocation.setInventoryall(NewAll);
									inventoryService.add_InventoryLocation(InventoryLocation);

									Inventory_Location_id = InventoryLocation.getInventory_location_id();

								}
								// Quantity all ready save to update in all ,
								// Location Quantity details
								inventory.setHistory_quantity(Double.parseDouble(quantity[i]));
								if(Double.parseDouble(quantity[i]) > previousHistoryQuantity ) {
									 FinalQunatity = Double.parseDouble(quantity[i]) - previousHistoryQuantity;
									 inventory.setQuantity(previousQuantity + FinalQunatity);
								} else {
									 FinalQunatity = previousHistoryQuantity - Double.parseDouble(quantity[i]);
									 inventory.setQuantity(previousQuantity - FinalQunatity);
								}
								inventory.setUnitprice(Double.parseDouble(unitprice[i]));
								inventory.setDiscount(Double.parseDouble(discount[i]));
								inventory.setTax(Double.parseDouble(tax[i]));
								inventory.setDiscountTaxTypeId(Short.parseShort(disTaxTypeId[i]));

								Double Quantity = Double.parseDouble(quantity[i]);
								Double eaccost = Double.parseDouble(unitprice[i]);
								Double dis_Ca = Double.parseDouble(discount[i]);
								Double tax_Ca = Double.parseDouble(tax[i]);

								Double discountCost = 0.0;
								Double TotalCost = 0.0;

								if(inventory.getDiscountTaxTypeId() == 1) {
									discountCost 	= (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
									TotalCost 		= round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
								} else {
									discountCost 	= ((Quantity * eaccost) - dis_Ca);
									TotalCost 		= round((discountCost + tax_Ca), 2);
								}
								inventory.setTotal(TotalCost);

								if (make[i] != null) {

									inventory.setMake(make[i]);
								}
								inventory.setInventory_all_id(Inventory_all_id);
								inventory.setInventory_location_id(Inventory_Location_id);
								
								inventoryService.updateInventory(inventory);

								// Insert Inventory QUANTITY Details to Update
								// LOCATION AND ALL LOCATION QUANTITY DETAILS

								inventoryService.update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(Long.parseLong(partid[i]+""), PartInvoice.getWareHouseLocation());
								
								inventoryService.update_InventoryAll_PARTID_PARTNAME_GENRAL(Long.parseLong(partid[i]+""));
								
								
								// while editing if some other part is selected then subtract previous quantity of that part in InventoryAll and Inventory Location Table.
								if(!previousPartId.equals(Long.parseLong(partid[i]+""))) {
									inventoryService.update_InventoryAll_Quantity_From_PartInvoice(previousInventoryAllId,
											userDetails.getCompany_id(), previousQuantity);
									
									inventoryService.update_InventoryLocation_Quantity_From_PartInvoice(previousLocationId,
											userDetails.getCompany_id(), previousQuantity);
								}
								
							} else {
								// get part and AllQuantity save
								Inventoryall.setAll_quantity(Double.parseDouble(quantity[i]));
								inventoryService.add_InventoryAll(Inventoryall);

								// get part and Location Quantity save
								InventoryLocation.setLocation_quantity(Double.parseDouble(quantity[i]));
								InventoryLocation.setInventoryall(Inventoryall);
								inventoryService.add_InventoryLocation(InventoryLocation);

								inventory.setHistory_quantity(Double.parseDouble(quantity[i]));
								inventory.setQuantity(Double.parseDouble(quantity[i]));
								inventory.setUnitprice(Double.parseDouble(unitprice[i]));

								inventory.setDiscount(Double.parseDouble(discount[i]));
								inventory.setTax(Double.parseDouble(tax[i]));
								inventory.setDiscountTaxTypeId(Short.parseShort(disTaxTypeId[i]));

								Double Quantity = Double.parseDouble(quantity[i]);
								Double eaccost = Double.parseDouble(unitprice[i]);
								Double dis_Ca = Double.parseDouble(discount[i]);
								Double tax_Ca = Double.parseDouble(tax[i]);

								Double discountCost = 0.0;
								Double TotalCost = 0.0;

								if(inventory.getDiscountTaxTypeId() == 1) {
									discountCost 	= (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
									TotalCost 		= round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
								} else {
									discountCost 	= ((Quantity * eaccost) - dis_Ca);
									TotalCost 		= round((discountCost + tax_Ca), 2);
								}
								inventory.setTotal(TotalCost);

								if (make[i] != null) {

									inventory.setMake(make[i]);
								}
								inventory.setInventory_all_id(Inventoryall.getInventory_all_id());
								inventory.setInventory_location_id(InventoryLocation.getInventory_location_id());
								inventory.setSerialNoAddedForParts(0);
								
								inventoryService.save_Inventory(inventory);
								
								// while editing if some other part is selected then subtract previous quantity of that part in InventoryAll and Inventory Location Table.
								if(!previousPartId.equals(Long.parseLong(partid[i]+""))) {
									inventoryService.update_InventoryAll_Quantity_From_PartInvoice(previousInventoryAllId,
											userDetails.getCompany_id(), previousQuantity);
									
									inventoryService.update_InventoryLocation_Quantity_From_PartInvoice(previousLocationId,
											userDetails.getCompany_id(), previousQuantity);
								}
							 }
						   } catch (Exception e) {
								LOGGER.error("Error"+ e);
								e.printStackTrace();
								model.put("redirect:/NewInventory/1.in?danger=true", true);
						   }
							
							if((boolean) configuration.get("showRefreshmentOption")) {
								if(masterParts != null && masterParts.isRefreshment()) {
									
									if((inventory.getQuantity() - getOLd_Inventory.getQuantity()) != 0 || !(inventory.getInvoice_date().equals(getOLd_Inventory.getInvoiceDate()))) {
										
										ValueObject	valueObject2	= new ValueObject();
										if(getOLd_Inventory.getInvoiceDate() != null) {
											valueObject2.put("transactionDate", inventory.getInvoice_date());
										}else {
											valueObject2.put("transactionDate", DateTimeUtility.getCurrentDate());
										}
										
										valueObject2.put("partId", getOLd_Inventory.getPartid());
										valueObject2.put("locationId",  getOLd_Inventory.getLocationId());
										valueObject2.put("companyId", userDetails.getCompany_id());
										valueObject2.put("addedQuantity", inventory.getQuantity());
										valueObject2.put("usedQuantity", 0.0);
										valueObject2.put("numberType", "I-"+ getOLd_Inventory.getInventory_Number()+" Part Edit");
										valueObject2.put("transactionId", getOLd_Inventory.getInventory_id());
										valueObject2.put("primaryType", InventoryStockTypeConstant.STOCK_TYPE_ADD);
										valueObject2.put("transactionType", InventoryStockTypeConstant.STOCK_TYPE_ADD);
										if(inventory.getInvoice_date().equals(getOLd_Inventory.getInvoiceDate())) {
											valueObject2.put("quantity", - (getOLd_Inventory.getQuantity() - inventory.getQuantity()));
											valueObject2.put("isDateChanged", false);
										}else {
											valueObject2.put("quantity", inventory.getQuantity());
											valueObject2.put("isDateChanged", true);
											
										}
										valueObject2.put("previousQuantity", getOLd_Inventory.getQuantity());
										valueObject2.put("previousDate", getOLd_Inventory.getInvoiceDate());
										
										dayWiseInventoryStockService.processLocationWiseInventoryStockDetails(valueObject2);	
									}
									
								}
							}
							
						}
						
					}
					
					PartInvoice.setQuantity(Qty);
					inventoryService.update_Part_Inventory_Invoice(PartInvoice);
					model.put("updateInventory", true);
				} else {  
					// If Part is not visible to edit so only invoice details will get updated.
					if(partid != null)
					for (int j = 0; j < partid.length; j++) {
						
						Qty += Double.parseDouble(quantity[j]);
						Inventory invent = new Inventory();
						
						invent.setInventory_id(Long.parseLong(inventory_id[j]+""));
						invent.setPartid(Long.parseLong(partid[j]+""));
						invent.setMake(make[j]);
						invent.setCompanyId(userDetails.getCompany_id());
						
						invent.setHistory_quantity(Double.parseDouble(quantity[j]));
						invent.setQuantity(Double.parseDouble(quantity[j]));
						invent.setUnitprice(Double.parseDouble(unitprice[j]));
						invent.setDiscount(Double.parseDouble(discount[j]));
						invent.setTax(Double.parseDouble(tax[j]));
						invent.setDiscountTaxTypeId(Short.parseShort(disTaxTypeId[j]));

						Double Quantity = Double.parseDouble(quantity[j]);
						Double eaccost = Double.parseDouble(unitprice[j]);
						Double dis_Ca = Double.parseDouble(discount[j]);
						Double tax_Ca = Double.parseDouble(tax[j]);

						Double discountCost = 0.0;
						Double TotalCost = 0.0;

						discountCost = (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
						TotalCost = round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
						invent.setTotal(TotalCost);
						
						Date currentDateUpdate = new Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

						//inventory.setCreated(toDate);
						invent.setLastupdated(toDate);
						invent.setMarkForDelete(false);
						invent.setPartInvoiceId(PartInvoice.getPartInvoiceId());
						invent.setInvoice_number(PartInvoice.getInvoiceNumber());
						invent.setInvoice_amount(PartInvoice.getInvoiceAmount());
						String invoiceDate = DateTimeUtility.getDateFromTimeStamp(PartInvoice.getInvoiceDate(), DateTimeUtility.DD_MM_YYYY);
						java.util.Date date = sdf.parse(invoiceDate);
						java.sql.Date sqlInvoiceDate = new java.sql.Date(date.getTime());
						invent.setInvoice_date(sqlInvoiceDate);
						invent.setVendor_id(PartInvoice.getVendorId());
						invent.setLocationId(PartInvoice.getWareHouseLocation());
						invent.setDescription(PartInvoice.getDescription());
						invent.setLastModifiedById(userDetails.getId());
						
						inventoryService.update_Part_Inventory(invent);
						
					}
					
					PartInvoice.setQuantity(Qty);
					inventoryService.update_Part_Inventory_Invoice(PartInvoice);
					model.put("updateInventory", true);
					if(getPartInvoice != null ) {
						if(getPartInvoice.getSubLocationId() != PartInvoiceDto.getSubLocationId() ) {
							inventoryRepository.updateSublocationInInventory(getPartInvoice.getPartInvoiceId() ,PartInvoiceDto.getSubLocationId(),userDetails.getCompany_id() );
						}
					}
					
				 }
				
			} else {
				model.put("redirect:/NewInventory/1.in?danger=true", true);
			}
			
			return model;
			
		} catch (Exception e) {
			throw e;
		}finally {
			
		}
	}
	
private PartInvoice prepare_UpdatePartInvoice(PartInvoiceDto PartInvoiceDto) throws Exception {
		
		CustomUserDetails	userDetails       = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Date 				currentDateUpdate = new Date();
		Timestamp 			toDate            = new java.sql.Timestamp(currentDateUpdate.getTime());
		PartInvoice 		part              = new PartInvoice();
		
		part.setPartInvoiceId(PartInvoiceDto.getPartInvoiceId());
		part.setPartInvoiceNumber(PartInvoiceDto.getPartInvoiceNumber());
		part.setWareHouseLocation(PartInvoiceDto.getWareHouseLocation());
		part.setInvoiceNumber(PartInvoiceDto.getInvoiceNumber());
		
		part.setInvoiceDate(DateTimeUtility.getTimeStamp(PartInvoiceDto.getInvoiceDateOn(), DateTimeUtility.DD_MM_YYYY));
		part.setInvoiceAmount(PartInvoiceDto.getInvoiceAmount());
		part.setBalanceAmount(Double.parseDouble(PartInvoiceDto.getInvoiceAmount()));
		
		part.setVendorId(PartInvoiceDto.getVendorId());
		if (PartInvoiceDto.getVendorIdStr() != null && FuelDto.getParseVendorID_STRING_TO_ID(PartInvoiceDto.getVendorIdStr()) != 0) {
			part.setVendorId(FuelDto.getParseVendorID_STRING_TO_ID(PartInvoiceDto.getVendorIdStr()));
		} else {
				Vendor vendor = vendorService.getVendorIdFromNew(PartInvoiceDto.getVendorIdStr(), userDetails.getCompany_id(), "PART-VENDOR", "Part Inventory");
				part.setVendorId(vendor.getVendorId());
		}
		PartInvoiceDto.setVendorId(part.getVendorId());
		part.setPaymentTypeId(PartInvoiceDto.getPaymentTypeId());
		if(PartInvoiceDto.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
			part.setVendorPaymentStatus(FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
		}else {
			part.setVendorPaymentStatus(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
			
		}
		part.setDescription(PartInvoiceDto.getDescription());
		part.setQuantity(PartInvoiceDto.getQuantity());
		part.setCreatedById(userDetails.getId());
		part.setLastModifiedById(userDetails.getId());
		part.setCreatedOn(toDate);
		part.setLastModifiedBy(toDate);
		part.setCompanyId(userDetails.getCompany_id());
		part.setLastUpdated_Date(toDate);
		part.setAnyPartNumberAsign(PartInvoiceDto.isAnyPartNumberAsign());
		part.setLabourCharge(PartInvoiceDto.getLabourCharge());
		part.setTallyCompanyId(PartInvoiceDto.getTallyCompanyId());
		part.setSubLocationId(PartInvoiceDto.getSubLocationId());
		return part;
		
	}


@Transactional
public void update_PartInvoiceAmountOf_Inventory(Object partInvoiceId, Object recievedAmount, Object balanceAmt, Object discountAmt, short vendorPaymentStatusId,long approvalId,Date date)
		throws Exception {
	CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
			.getPrincipal();

	try {
		
			entityManager.createQuery(
					"UPDATE PartInvoice SET paidAmount= "+recievedAmount+ ", discountAmount= "+ discountAmt +", balanceAmount= "+ balanceAmt + ", partApprovalId = " + approvalId + ",  "
							+ " vendorPaymentDate = '"+date+"', lastModifiedById = "+userDetails.getId()+", "
							+ " vendorPaymentStatus= " + vendorPaymentStatusId +" " 
							+ " WHERE partInvoiceId = "+partInvoiceId+" AND companyId = "+userDetails.getCompany_id()+" "
							+ " AND markForDelete = 0 ")
			.executeUpdate();
		
	} catch (Exception e) {
		throw e;
	}

}
@Transactional
public void update_PartInvoiceAmountOf_BatteryInventory(Object partInvoiceId, Object recievedAmount, Object balanceAmt, Object discountAmt,short vendorPaymentStatusId, long approvalId, Date date)
		throws Exception {
	CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
			.getPrincipal();
	try {
		
		entityManager.createQuery(
				"UPDATE BatteryInvoice SET paidAmount= "+recievedAmount+ ",batteryApprovalId = " +approvalId + ",discountAmount= "+ discountAmt +", balanceAmount= "+ balanceAmt + ", "
						+ " vendorPaymentDate = '"+date+"', lastModifiedById = "+userDetails.getId()+", "
						+ " vendorPaymentStatus= " + vendorPaymentStatusId +" " 
						+ " WHERE batteryInvoiceId = "+partInvoiceId+" AND companyId = "+userDetails.getCompany_id()+" "
						+ " AND markForDelete = 0 ")
		.executeUpdate();
		
	} catch (Exception e) {
		throw e;
	}

}

@Transactional
public void update_TyreInvoiceAmountOf_TyreInventory(Object partInvoiceId, Object recievedAmount, Object balanceAmt,Object discountAmt,short vendorPaymentStatusId, long approvalId, Date date)
		throws Exception {
	CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
			.getPrincipal();
	try {
		entityManager.createQuery(
				"UPDATE InventoryTyreInvoice SET  paidAmount= "+recievedAmount+ ", balanceAmount= "+balanceAmt+", discountAmount ="+discountAmt+", TYRE_APPROVAL_ID = " +approvalId + ","
						+ " VENDOR_PAYMODE_DATE = '"+date+"', LASTMODIFIEDBYID = "+userDetails.getId()+", "
						+ " VENDOR_PAYMODE_STATUS_ID= " + vendorPaymentStatusId +" " 
						+ " WHERE ITYRE_ID = "+partInvoiceId+" AND COMPANY_ID = "+userDetails.getCompany_id()+" "
						+ " AND markForDelete = 0 ")
		.executeUpdate();
		
	} catch (Exception e) {
		throw e;
	}

}
@Transactional
public void update_TyreRetreadInvoiceAmountOf_TyreRetreadInventory(Object partInvoiceId, Object recievedAmount, Object balanceAmt,Object discountAmt,short vendorPaymentStatusId, long approvalId, Date date)
		throws Exception {
	CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
			.getPrincipal();
	
	try {
		
		entityManager.createQuery(
				"UPDATE InventoryTyreRetread SET paidAmount= "+recievedAmount+ ", balanceAmount= "+balanceAmt+",discountAmount= "+discountAmt+", TR_APPROVAL_ID = " +approvalId + ","
						+ " TR_VENDOR_PAYMODE_DATE = '"+date+"', LASTMODIFIEDBYID = "+userDetails.getId()+", "
						+ " TR_VENDOR_PAYMODE_STATUS_ID= " + vendorPaymentStatusId +" " 
						+ " WHERE TRID = "+partInvoiceId+" AND COMPANY_ID = "+userDetails.getCompany_id()+" "
						+ " AND markForDelete = 0 ")
		.executeUpdate();
	
	} catch (Exception e) {
		throw e;
	}

}

@Transactional
public void update_ServiceEntriesApprovalAmount(Object partInvoiceId, Object recievedAmount, Object balanceAmt,Object discountAmt,short vendorPaymentStatusId, long approvalId, Date date)
		throws Exception {
	CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
			.getPrincipal();
	
	try {
		
		entityManager.createQuery(
				"UPDATE ServiceEntries SET paidAmount= "+recievedAmount+ ", "
						+ " balanceAmount= "+balanceAmt+", discountAmount= "+discountAmt+", service_vendor_approvalID = " +approvalId + ","
						+ " service_vendor_paymentdate = '"+date+"', lastModifiedById = "+userDetails.getId()+", "
						+ " service_vendor_paymodeId= " + vendorPaymentStatusId +" " 
						+ " WHERE serviceEntries_id = "+partInvoiceId+" AND companyId = "+userDetails.getCompany_id()+" "
						+ " AND markForDelete = 0 ")
		.executeUpdate();
		
	} catch (Exception e) {
		throw e;
	}

}


@Transactional
public void update_FuelInventoryAmount(Object partInvoiceId, Object recievedAmount, Object balanceAmt,Object discountAmt,short vendorPaymentStatusId, long approvalId, Date date)
		throws Exception {
	CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
			.getPrincipal();
	
	try {//DateTimeUtility.getCurrentTimeStamp()
		
		entityManager.createQuery(
				"UPDATE Fuel SET paidAmount= "+recievedAmount+ ", "
						+ " balanceAmount= "+balanceAmt+", discountAmount= "+discountAmt+", fuel_vendor_approvalID = " +approvalId + ","
						+ " fuel_vendor_paymentdate = '"+date+"', lastModifiedById = "+userDetails.getId()+", "
						+ " fuel_vendor_paymodeId= " + vendorPaymentStatusId +" " 
						+ " WHERE fuel_id = "+partInvoiceId+" AND companyId = "+userDetails.getCompany_id()+" "
						+ " AND markForDelete = 0 ")
		.executeUpdate();
		
	} catch (Exception e) {
		throw e;
	}

}

@Transactional
public void updatePaymentApprovedPartDetails(Long ApprovalInvoice_ID,
		Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId) throws Exception {

	entityManager.createQuery("  UPDATE PartInvoice SET partApprovalId=" + Approval_ID
			+ ",expectedPaymentDate='" + expectedPaymentDate + "', vendorPaymentStatus='" + approval_Status + "' "
			+ " WHERE partInvoiceId IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();
}
@Transactional
public void updatePaymentPaidDetails(String ApprovalInvoice_ID, Timestamp approval_date, Integer companyId, short vendorPaymentStatus) throws Exception {
	
	entityManager.createQuery("  UPDATE PartInvoice SET vendorPaymentDate='" + approval_date+"', vendorPaymentStatus= "+vendorPaymentStatus+"  WHERE partInvoiceId IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();
}
	
	@Override
	public List<VendorPaymentNotPaidDto> vendorPaymentNotPaidPartInvoiceList(Integer vendor_id, String dateFrom, String dateTo, Integer companyId) throws Exception {
		List<VendorPaymentNotPaidDto>			partInvoiceList			= null;
		VendorPaymentNotPaidDto					list 					= null;
		TypedQuery<Object[]> 					typedQuery 				= null;
		List<Object[]>							results 				= null;
		try {
			typedQuery = entityManager
					.createQuery("SELECT PI.partInvoiceId, PI.partInvoiceNumber, V.vendorName, PI.invoiceNumber, PI.invoiceDate, "
							+ " PI.paymentTypeId, PI.invoiceAmount, PI.balanceAmount "
							+ " FROM PartInvoice AS PI "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = PI.vendorId "
							+ " where PI.invoiceDate BETWEEN '"+dateFrom+"' AND '"+dateTo+"'"
							+ " AND PI.vendorId = "+vendor_id+" AND PI.paymentTypeId = "+PaymentTypeConstant.PAYMENT_TYPE_CREDIT
							+ " AND PI.companyId = "+companyId+" AND  PI.markForDelete = 0 AND (purchaseorder_id is null OR purchaseorder_id <= 0) "
							+ " AND (PI.vendorPaymentStatus = " + FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID + " "
							+ " OR PI.vendorPaymentStatus = " + FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID + "AND PI.balanceAmount > 0 ) "
							 , Object[].class);
			
			results = typedQuery.getResultList();
			
			if(results != null && !results.isEmpty()) {
				partInvoiceList	=	new ArrayList<>();
				for (Object[] result : results) {
					
					list = new VendorPaymentNotPaidDto();
					
					list.setSerialNumberId((Long) result[0]);
					list.setSerialNumber((Long) result[1]);
					list.setSerialNumberStr("PI-"+list.getSerialNumber());
					list.setVendorName((String) result[2]);
					
					if(result[3] != null) {
						list.setInvoiceNumber((String) result[3]);
					} else {
						list.setInvoiceNumber("-");
					}
					
					list.setInvoiceDateStr(sqlDateForm.format((java.util.Date) result[4]));
					list.setPaymentType(PaymentTypeConstant.getPaymentTypeName((short) result[5]));
					
					if(result[6] != null) {
						list.setInvoiceAmount(Double.parseDouble((String) result[6]));
					} else {
						list.setInvoiceAmount(0.0);
					}
					
					if(result[7] != null) {
						list.setBalanceAmount((Double) result[7]);
					} else {
						list.setBalanceAmount(0.0);
					}
					
					partInvoiceList.add(list);
					
				}
			}
			
			return partInvoiceList;
			
		} catch (Exception e) {
			throw e;
		} finally {
			partInvoiceList		= null;
			list 				= null;
			typedQuery 			= null;
			results 			= null;
		}
	
	}
	
	@Override
	public PartInvoice validatePartInvoiceToPO(Long purchaseOrderId, Integer companyId) throws Exception {
	
		return partInvoiceRepository.getPartInvoiceDetailsFromPurchaseOrder(purchaseOrderId, companyId);
	}
	
	@Override
	public PartInvoiceDto getLimitedPartInvoice(Long partInvoiceId) throws Exception {

		Query query  = entityManager
				.createQuery("SELECT PI.partInvoiceId, PI.partInvoiceNumber,"
						+ " PI.invoiceDate, PI.invoiceAmount, PI.vendorId "
						+ " FROM PartInvoice AS PI "
						+ " where PI.partInvoiceId = "+partInvoiceId+" AND  PI.markForDelete = 0 ");
		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		PartInvoiceDto	partInvoice = null;
			if (result != null) {
				partInvoice = new PartInvoiceDto();
				
				partInvoice.setPartInvoiceId((Long) result[0]);
				partInvoice.setPartInvoiceNumber((Long) result[1]);
				partInvoice.setInvoiceDate((Timestamp) result[2]);
				partInvoice.setInvoiceAmount((String) result[3]);
				partInvoice.setVendorId((Integer) result[4]);
				
			}
		return partInvoice;
	}
}	
