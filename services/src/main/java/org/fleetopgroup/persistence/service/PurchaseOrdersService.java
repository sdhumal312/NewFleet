package org.fleetopgroup.persistence.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.ApprovalType;
import org.fleetopgroup.constant.BatteryConstant;
import org.fleetopgroup.constant.ClothInvoiceStockType;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.InventoryStockTypeConstant;
import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.constant.PartType;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.PurchaseOrderState;
import org.fleetopgroup.constant.PurchaseOrderType;
import org.fleetopgroup.constant.PurchaseOrdersConfigurationConstant;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.constant.TallyVoucherTypeContant;
import org.fleetopgroup.persistence.bl.InventoryBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.PendingVendorPaymentBL;
import org.fleetopgroup.persistence.bl.PurchaseOrdersBL;
import org.fleetopgroup.persistence.dao.BatteryAmountRepository;
import org.fleetopgroup.persistence.dao.BatteryInvoiceRepository;
import org.fleetopgroup.persistence.dao.BatteryRepository;
import org.fleetopgroup.persistence.dao.ClothInventoryDetailsRepository;
import org.fleetopgroup.persistence.dao.ClothInventoryRepository;
import org.fleetopgroup.persistence.dao.InventoryRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreAmountRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreInvoiceRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreRepository;
import org.fleetopgroup.persistence.dao.PartInvoiceRepository;
import org.fleetopgroup.persistence.dao.PurchaseOrderToBatteryRepository;
import org.fleetopgroup.persistence.dao.PurchaseOrdersDocumentRepository;
import org.fleetopgroup.persistence.dao.PurchaseOrdersRepository;
import org.fleetopgroup.persistence.dao.PurchaseOrdersToDebitNoteRepository;
import org.fleetopgroup.persistence.dao.PurchaseOrdersToPartsRepository;
import org.fleetopgroup.persistence.dao.PurchaseOrdersToTyreRepository;
import org.fleetopgroup.persistence.dao.PurchaseOrdersToUreaRepository;
import org.fleetopgroup.persistence.dao.UreaInvoiceRepository;
import org.fleetopgroup.persistence.dao.UreaInvoiceToDetailsRepository;
import org.fleetopgroup.persistence.dto.BatteryTypeDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryAllDto;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.PartLocationPermissionDto;
import org.fleetopgroup.persistence.dto.PurchaseOrderReportDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersToDebitNoteDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersToPartsDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.VendorPaymentNotPaidDto;
import org.fleetopgroup.persistence.model.Battery;
import org.fleetopgroup.persistence.model.BatteryInvoice;
import org.fleetopgroup.persistence.model.ClothInventoryDetails;
import org.fleetopgroup.persistence.model.ClothInvoice;
import org.fleetopgroup.persistence.model.Inventory;
import org.fleetopgroup.persistence.model.InventoryTyre;
import org.fleetopgroup.persistence.model.InventoryTyreInvoice;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.model.PartInvoice;
import org.fleetopgroup.persistence.model.PartMeasurementUnit;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.PurchaseOrderToBattery;
import org.fleetopgroup.persistence.model.PurchaseOrders;
import org.fleetopgroup.persistence.model.PurchaseOrdersDocument;
import org.fleetopgroup.persistence.model.PurchaseOrdersSequeceCounter;
import org.fleetopgroup.persistence.model.PurchaseOrdersToDebitNote;
import org.fleetopgroup.persistence.model.PurchaseOrdersToParts;
import org.fleetopgroup.persistence.model.PurchaseOrdersToTyre;
import org.fleetopgroup.persistence.model.PurchaseOrdersToUrea;
import org.fleetopgroup.persistence.model.UreaInvoice;
import org.fleetopgroup.persistence.model.UreaInvoiceToDetails;
import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.persistence.model.VendorSubApprovalDetails;
import org.fleetopgroup.persistence.serviceImpl.IBatteryService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDayWiseInventoryStockService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.persistence.serviceImpl.IPartMeasurementUnitService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersToPartsService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IUreaInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IUserService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service("PurchaseOrdersService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PurchaseOrdersService implements IPurchaseOrdersService {

	@PersistenceContext
	EntityManager entityManager;
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired private MongoTemplate						mongoTemplate;
	@Autowired private ISequenceCounterService				sequenceCounterService;
	@Autowired private PurchaseOrdersRepository 			PurchaseOrdersDao;
	@Autowired private PurchaseOrdersToPartsRepository 		PurchaseOrdersToPartsRepository;
	@Autowired private PurchaseOrdersDocumentRepository 	PurchaseOrdersDocumentRepository;
	@Autowired private PurchaseOrdersToDebitNoteRepository 	PurchaseOrdersToDebitNoteRepository;
	@Autowired private PurchaseOrdersToTyreRepository 		PurchaseOrdersToTyre_Repository;
	@Autowired private PurchaseOrderToBatteryRepository		purchaseOrderToBatteryRepository;
	@Autowired private PurchaseOrdersToUreaRepository 		purchaseOrdersToUreaRepository;

	@Autowired private IUserService 						userService;
	@Autowired private IPurchaseOrdersService 				purchaseOrdersService;
	@Autowired private IPurchaseOrdersToPartsService 		purchaseOrdersToPartsService;
	@Autowired private IUreaInventoryService 				ureaInventoryService;
	@Autowired private IPartLocationPermissionService 		partLocationPermissionService;
	@Autowired private IInventoryService 					InventoryService;
	@Autowired private InventoryRepository 					InventoryRepository;
	@Autowired private ICompanyConfigurationService 		CompanyConfigurationService;
	@Autowired private IMasterPartsService 					MasterPartsService;
	@Autowired private IDayWiseInventoryStockService 		DayWiseInventoryStockService;
	@Autowired private PartInvoiceRepository 				PartInvoiceRepository;
	@Autowired private BatteryInvoiceRepository 			BatteryInvoiceRepository;
	@Autowired private BatteryRepository 					BatteryRepository;
	@Autowired private BatteryAmountRepository 				BatteryAmountRepository;
	@Autowired private InventoryTyreInvoiceRepository 		InventoryTyreInvoiceRepository;
	@Autowired private InventoryTyreRepository 				InventoryTyreRepository;
	@Autowired private InventoryTyreAmountRepository 		InventoryTyreAmountRepository;
	@Autowired private ClothInventoryRepository 			ClothInventoryRepository;
	@Autowired private ClothInventoryDetailsRepository 		ClothInventoryDetailsRepository;
	@Autowired private UreaInvoiceRepository 				UreaInvoiceRepository;
	@Autowired private UreaInvoiceToDetailsRepository 		UreaInvoiceToDetailsRepository;
	@Autowired private IInventoryTyreService 				iventoryTyreService;
	@Autowired private IPurchaseOrdersSequenceService		purchaseOrdersSequenceService;
	@Autowired private IBatteryService						batteryService;
	@Autowired private	IPartMeasurementUnitService 		partMeasurementUnitService;
	@Autowired private ICompanyConfigurationService 		companyConfigurationService;
	@Autowired private IPendingVendorPaymentService			pendingVendorPaymentService;
	
	PurchaseOrdersBL 	POBL 		= new PurchaseOrdersBL();
	PartLocationsBL		PLBL 		= new PartLocationsBL();
	InventoryBL 		InvenBL 	= new InventoryBL();
	
	SimpleDateFormat dateFormat 		= new SimpleDateFormat("dd-MM-yyyy");
	
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	DecimalFormat toFixedTwo = new DecimalFormat("##.##");

	private static final int PAGE_SIZE = 10;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPurchaseOrders(PurchaseOrders PurchaseOrders) throws Exception {

		PurchaseOrdersDao.save(PurchaseOrders);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPurchaseOrdersTask(PurchaseOrdersToParts PurchaseOrdersTask) throws Exception {

		PurchaseOrdersToPartsRepository.save(PurchaseOrdersTask);
	}

	@Transactional
	public PurchaseOrders getPurchaseOrders(long PurchaseOrders) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return PurchaseOrdersDao.getPurchaseOrders(PurchaseOrders, userDetails.getCompany_id());
	}

	@Override
	public PurchaseOrdersDto getPurchaseOrders(long purchaseOrders, CustomUserDetails	userDetails) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT P.purchaseorder_id, P.purchaseorder_Number, P.purchaseorder_typeId, P.purchaseorder_vendor_id, VN.vendorName,"
						+ " VN.vendorLocation, P.purchaseorder_created_on, P.purchaseorder_requied_on, P.purchaseorder_termsId, P.purchaseorder_shipviaId, P.purchaseorder_shiplocation_id,"
						+ " PL.partlocation_name, PL.shippartlocation_address, PL.contactSecName, PL.contactSecPhone,"
						+ " P.purchaseorder_quotenumber, WO.workorders_Number, P.purchaseorder_indentno, P.purchaseorder_notes, P.purchaseorder_statusId,"
						+ " P.purchaseorder_subtotal_cost, P.purchaseorder_totaltax_cost, P.purchaseorder_freight, P.purchaseorder_totalcost,"
						+ " P.purchaseorder_advancecost, P.purchaseorder_balancecost, P.purchaseorder_orderd_remark,"
						+ " P.purchaseorder_orderddate, P.purchaseorder_received_remark, P.purchaseorder_received_date,"
						+ " P.purchaseorder_invoiceno, P.purchaseorder_invoice_date, P.purchaseorder_total_debitnote_cost, P.purchaseorder_complete_date, "
						+ " P.purchaseorder_document, P.created, P.lastupdated, P.purchaseorder_orderdbyId, P.purchaseorder_receivedbyId,"
						+ " P.createdById, P.lastModifiedById, PL.shippartlocation_streetaddress, PL.shippartlocation_city, PL.partlocation_state,"
						+ " PL.shippartlocation_country, PL.shippartlocation_pincode, C.company_name, C.company_address, C.company_city, C.company_state, C.company_country, C.company_pincode,"
						+ " P.subCompanyId, SC.subCompanyName , SC.subCompanyAddress, SC.subCompanyCity, SC.subCompanyState, SC.subCompanyCountry, SC.subCompanyPinCode, VN.vendorGSTNO ,"
						+ " P.tallyCompanyId, TC.companyName, C.company_tax_no, SC.subCompanyTaxNo, P.purchaseorder_vendor_paymodeId, P.purchaseorder_vendor_approvalID, VA.approvalNumber,"
						+ " P.purchaseorder_document_id, P.approvalStatus, P.approvedById, P.approvalRemark, U.firstName, VN.vendorAddress1, VN.vendorFirName, VN.vendorFirPhone " 
						+ " FROM PurchaseOrders P "
						+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = P.purchaseorder_workordernumber"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = P.purchaseorder_vendor_id "
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = P.purchaseorder_shiplocation_id"
						+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = P.tallyCompanyId"
						+ " LEFT JOIN VendorApproval VA ON VA.approvalId = P.purchaseorder_vendor_approvalID"
						+ " INNER JOIN Company C ON C.company_id = P.purchaseorder_buyer_id"
						+ " LEFT JOIN SubCompany SC ON SC.subCompanyId = P.subCompanyId"
						+ " LEFT JOIN User U ON U.id = P.approvedById"
						+ " WHERE P.purchaseorder_id = :purchaseorder_id AND P.companyId = :companyId AND P.markForDelete = 0 ");

		query.setParameter("purchaseorder_id", purchaseOrders);
		query.setParameter("companyId", userDetails.getCompany_id());

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		PurchaseOrdersDto select;
		if (result != null) {
			
			select = new PurchaseOrdersDto();

			select.setPurchaseorder_id((Long) result[0]);
			select.setPurchaseorder_Number((Long) result[1]);
			select.setPurchaseorder_typeId((short) result[2]);
			select.setPurchaseorder_vendor_id((Integer) result[3]);
			select.setPurchaseorder_vendor_name((String) result[4]);
			select.setPurchaseorder_vendor_location((String) result[5]);
			select.setPurchaseorder_created((Date) result[6]);
			select.setPurchaseorder_requied((Date) result[7]);
			select.setPurchaseorder_termsId((short) result[8]);
			select.setPurchaseorder_shipviaId((short) result[9]);
			select.setPurchaseorder_shiplocation_id((Integer) result[10]);
			select.setPurchaseorder_shiplocation((String) result[11]);
			select.setPurchaseorder_shiplocation_address((String) result[12]+", "+(String) result[41]+", "+(String) result[42]+", "+(String) result[43]+", "+(String) result[44]+". Pin :"+(String) result[45] );
			
			select.setPurchaseorder_shiplocation_contact((String) result[13]);
			select.setPurchaseorder_shiplocation_mobile((String) result[14]);
			select.setPurchaseorder_quotenumber((String) result[15]);
			if (result[16] != null) {
				select.setPurchaseorder_workordernumber((Long) result[16] + "");
			} else {
				select.setPurchaseorder_workordernumber("0");
			}
			select.setPurchaseorder_indentno((String) result[17]);
			select.setPurchaseorder_notes((String) result[18]);
			select.setPurchaseorder_statusId((short) result[19]);
			select.setPurchaseorder_subtotal_cost((Double) result[20]);
			select.setPurchaseorder_totaltax_cost((Double) result[21]);
			select.setPurchaseorder_freight((Double) result[22]);
			select.setPurchaseorder_totalcost((Double) result[23]);
			select.setPurchaseorder_advancecost((Double) result[24]);
			select.setPurchaseorder_balancecost((Double) result[25]);
			select.setPurchaseorder_orderd_remark((String) result[26]);
			select.setPurchaseorder_orderddate((Date) result[27]);
			if(select.getPurchaseorder_orderddate() != null)
			select.setPurchaseorderOrderdate(dateFormatTime.format(select.getPurchaseorder_orderddate()));
			select.setPurchaseorder_received_remark((String) result[28]);
			select.setPurchaseorder_received_date((Date) result[29]);
			if(select.getPurchaseorder_received_date() != null)
				select.setPurchaseorderReceivedDate(dateFormatTime.format(select.getPurchaseorder_received_date() ));
				
			select.setPurchaseorder_invoiceno((String) result[30]);
			select.setPurchaseorder_invoice_date_On((Date) result[31]);
			select.setPurchaseorder_total_debitnote_cost((Double) result[32]);
			select.setPurchaseorder_complete_date((Date) result[33]);
			select.setPurchaseorder_document((boolean) result[34]);
			select.setCreatedOn((Date) result[35]);
			select.setLastupdatedOn((Date) result[36]);
			select.setPurchaseorder_orderdbyId((Long) result[37]);
			select.setPurchaseorder_receivedbyId((Long) result[38]);
			select.setCreatedById((Long) result[39]);
			select.setLastModifiedById((Long) result[40]);
			if(result[52] != null && ((Long)result[52] > 0)) {
				select.setSubCompanyId((Long) result[52]);
				select.setPurchaseorder_buyer((String) result[53]);
				select.setPurchaseorder_buyeraddress((String) result[54]+" , "+ (String) result[55]+"  , "+(String) result[56]+" , "+(String) result[57]+" . PIN- "+(String) result[58]);
				select.setBuyerGstNumber((String) result[63]);
			}else {
				select.setPurchaseorder_buyer((String) result[46]);
				select.setPurchaseorder_buyeraddress((String) result[47]+" , "+ (String) result[48]+"  , "+(String) result[49]+" , "+(String) result[50]+" . PIN- "+(String) result[51]);
				select.setBuyerGstNumber((String) result[62]);
			}
			if(result[59] != null) {
				select.setVendorGstNumber((String) result[59]);
			}
			
			
			if(result[60] != null) {
				select.setTallyCompanyId((Long) result[60]);
			} else {
				select.setTallyCompanyId((long)0);
			}
			
			if(result[61] != null) {
				select.setTallyCompanyName((String) result[61]);
			} else {
				select.setTallyCompanyName("");
			}
			select.setPurchaseorder_vendor_paymodeId((short) result[64]);
		
			select.setPurchaseorder_vendor_paymode(FuelVendorPaymentMode.getPaymentMode((short) result[64]));
			if(result[65] != null) {
				select.setPurchaseorder_vendor_approvalID((Long)result[65]);
			}else {
				select.setPurchaseorder_vendor_approvalID((long)0);
			}
			if(result[66] != null) {
				select.setVendorApprovalNumber((Long)result[66]);
			}
			if(result[67] != null) {
				select.setPurchaseorder_document_id((Long) result[67]);
			}
			
			select.setApprovalStatus((Short) result[68]);
			if(result[69] != null)
			select.setApprovedById((Long) result[69]);
			if(result[70] != null)
			select.setApprovalRemark((String) result[70]);
			if(result[71] != null)
			select.setApprovedBy((String) result[71]);
			select.setApprovalStatusStr(PurchaseOrderType.getApprrovalStatusStr(select.getApprovalStatus()));
			select.setVendorAddress1((String) result[72]);
			select.setContactPerson((String) result[73]);
			select.setVendorFirPhone((String) result[74]);
			return select;
			
		} else {
			return null;
		}
		
	}

	@Transactional
	public PurchaseOrdersToParts getPurchaseOrdersToParts(long purchaseOrderstask_ID) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return PurchaseOrdersToPartsRepository.getPurchaseOrdersToParts(purchaseOrderstask_ID,
				userDetails.getCompany_id());
	}

	@Transactional
	public List<PurchaseOrdersToParts> getPurchaseOrdersTasksToParts(long purchaseOrders_id) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return PurchaseOrdersToPartsRepository.getPurchaseOrdersTasksToParts(purchaseOrders_id,
				userDetails.getCompany_id());
	}

	@Override
	public List<PurchaseOrdersToPartsDto> getPurchaseOrdersTasksToParts(long purchaseOrders_id, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> query = entityManager.createQuery(
				" SELECT R.purchaseorderto_partid, R.partid, MP.partname, MP.partnumber, MP.partTypeId, TM.TYRE_MODEL,"
						+ " R.TYRE_MANUFACTURER_ID, TSM.TYRE_MODEL_SUBTYPE, R.TYRE_MODEL_ID, VS.TYRE_SIZE, R.TYRE_SIZE_ID, R.INVENTORY_TYRE_QUANTITY, R.INVENTORY_TYRE_NEW_RT,"
						+ " R.quantity, R.received_quantity, R.received_quantity_remark, R.notreceived_quantity, PMU.pmuName,"
						+ " R.discount, R.tax, R.totalcost, R.inventory_all_id, R.inventory_all_quantity, R.parteachcost, CT.clothTypeName, "
						+ " R.approvalPartStatusId, MP.unitTypeId "
						+ " FROM PurchaseOrdersToParts R "
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = R.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = R.TYRE_MODEL_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID "
						+ " LEFT JOIN MasterParts MP ON MP.partid = R.partid "
						+ " LEFT JOIN ClothTypes CT ON CT.clothTypesId = R.clothTypesId"
						+ " LEFT JOIN PartMeasurementUnit PMU ON PMU.pmuid = R.unittypeId"
						+ " WHERE  R.purchaseorders.purchaseorder_id = " + purchaseOrders_id + " AND R.companyId = "
						+ companyId + " AND R.markForDelete = 0",
				Object[].class);

		List<Object[]> results = query.getResultList();

		List<PurchaseOrdersToPartsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersToPartsDto>();
			PurchaseOrdersToPartsDto list = null;
			for (Object[] result : results) {
				list = new PurchaseOrdersToPartsDto();

				list.setPurchaseorderto_partid((Long) result[0]);
				list.setPartid((Long) result[1]);
				list.setPurchaseorder_partname((String) result[2]);
				list.setPurchaseorder_partnumber((String) result[3]);
				if(result[4] != null)
				 list.setParttype(PartType.getPartTypeName((short) result[4]));
				list.setTYRE_MANUFACTURER((String) result[5]);
				list.setTYRE_MANUFACTURER_ID((Integer) result[6]);
				list.setTYRE_MODEL((String) result[7]);
				list.setTYRE_MODEL_ID((Integer) result[8]);
				list.setTYRE_SIZE((String) result[9]);
				list.setTYRE_SIZE_ID((Integer) result[10]);
				list.setINVENTORY_TYRE_QUANTITY((Integer) result[11]);
				list.setINVENTORY_TYRE_NEW_RT((String) result[12]);
				list.setQuantity((Double) result[13]);
				list.setReceived_quantity((Double) result[14]);
				list.setReceived_quantity_remark((String) result[15]);
				list.setNotreceived_quantity((Double) result[16]);
				list.setUnittype((String) result[17]);
				list.setDiscount((Double) result[18]);
				list.setTax((Double) result[19]);
				list.setTotalcost((Double) result[20]);
				list.setInventory_all_id((Long) result[21]);
				list.setInventory_all_quantity((Double) result[22]);
				list.setParteachcost((Double) result[23]);
				list.setPurchaseorder_id(purchaseOrders_id);
				list.setClothTypeName((String) result[24]);
				list.setApprovalPartStatusId((short) result[25]);
				list.setApprovalPartStatus(PurchaseOrderState.getPurchaseOrderApprovalStatus(list.getApprovalPartStatusId()));
				if(result[26] != null)
					list.setUnittypeId(Integer.parseInt((Long) result[26]+""));
				
				
				double eachReceivedPartAmount = list.getParteachcost() * list.getReceived_quantity();
				double costWithTax = eachReceivedPartAmount + (eachReceivedPartAmount * list.getTax() / 100);
			    double ReceivedPartCost = costWithTax - (costWithTax * list.getDiscount() /100);
			    
			    list.setFinalReceivedAmount(Double.parseDouble(toFixedTwo.format(ReceivedPartCost)));
			    
				Dtos.add(list);
			}
		}
		
		if(Dtos != null && !Dtos.isEmpty()) {
			Map<Integer, PartMeasurementUnit>  measurementUnitHM = partMeasurementUnitService.getPartMeasurementUnitHM();
			Dtos	= InventoryBL.getFinalPOList(Dtos, measurementUnitHM);
		}
		
		return Dtos;

	}
	
	@Override
	public List<PurchaseOrdersToPartsDto> getPurchaseOrdersTasksToCloth(long purchaseOrders_id, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> query = entityManager.createQuery(
				" SELECT R.purchaseorderto_partid, CT.clothTypeName,"
						+ " R.quantity, R.received_quantity, R.received_quantity_remark, R.notreceived_quantity, PMU.pmuName,"
						+ " R.discount, R.tax, R.totalcost, R.parteachcost, R.clothTypesId,R.approvalPartStatusId "
						+ " FROM PurchaseOrdersToParts R "
						+ " LEFT JOIN ClothTypes CT ON CT.clothTypesId = R.clothTypesId"
						+ " LEFT JOIN PartMeasurementUnit PMU ON PMU.pmuid = R.unittypeId"
						+ " WHERE  R.purchaseorders.purchaseorder_id = " + purchaseOrders_id + " AND R.companyId = "
						+ companyId + " AND R.markForDelete = 0",
				Object[].class);

		List<Object[]> results = query.getResultList();

		List<PurchaseOrdersToPartsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersToPartsDto>();
			PurchaseOrdersToPartsDto list = null;
			for (Object[] result : results) {
				list = new PurchaseOrdersToPartsDto();

				list.setPurchaseorderto_partid((Long) result[0]);
				list.setClothTypeName((String) result[1]);
				list.setQuantity((Double) result[2]);
				list.setReceived_quantity((Double) result[3]);
				list.setReceived_quantity_remark((String) result[4]);
				list.setNotreceived_quantity((Double) result[5]);
				list.setUnittype((String) result[6]);
				list.setDiscount((Double) result[7]);
				list.setTax((Double) result[8]);
				list.setTotalcost((Double) result[9]);
				list.setParteachcost((Double) result[10]);
				list.setClothTypesId((Long) result[11]);
				list.setPurchaseorder_id(purchaseOrders_id);
				list.setApprovalPartStatusId((Short) result[12]);
				list.setApprovalPartStatus(PurchaseOrderState.getPurchaseOrderApprovalStatus(list.getApprovalPartStatusId()));
				
				double eachReceivedPartAmount = list.getParteachcost() * list.getReceived_quantity();
				double costWithTax = eachReceivedPartAmount + (eachReceivedPartAmount * list.getTax() / 100);
			    double ReceivedPartCost = costWithTax - (costWithTax * list.getDiscount() /100);

			    list.setFinalReceivedAmount(Double.parseDouble(toFixedTwo.format(ReceivedPartCost)));
				Dtos.add(list);
			}
		}
		return Dtos;

	}

	@Override
	public List<PurchaseOrdersToPartsDto> getPurchaseOrdersToPartsforBattery(long purchaseOrders_id, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> query = entityManager.createQuery(
				" SELECT R.purchaseorderto_partid, R.partid, MP.partname, MP.partnumber, MP.partTypeId, TM.manufacturerName,"
						+ " R.BATTERY_MANUFACTURER_ID, TSM.batteryType, R.BATTERY_TYPE_ID, VS.batteryCapacity, R.BATTERY_CAPACITY_ID, R.BATTERY_QUANTITY, R.INVENTORY_TYRE_NEW_RT,"
						+ " R.quantity, R.received_quantity, R.received_quantity_remark, R.notreceived_quantity, PMU.pmuName,"
						+ " R.discount, R.tax, R.totalcost, R.inventory_all_id, R.inventory_all_quantity, R.parteachcost, TSM.partNumber, "
						+ " TSM.warrantyPeriod, TSM.warrantyTypeId, R.approvalPartStatusId "
						+ " FROM PurchaseOrdersToParts R "
						+ " LEFT JOIN BatteryManufacturer TM ON TM.batteryManufacturerId = R.BATTERY_MANUFACTURER_ID"
						+ " LEFT JOIN BatteryType TSM ON TSM.batteryTypeId = R.BATTERY_TYPE_ID"
						+ " LEFT JOIN BatteryCapacity VS ON VS.batteryCapacityId = R.BATTERY_CAPACITY_ID "
						+ " LEFT JOIN MasterParts MP ON MP.partid = R.partid"
						+ " LEFT JOIN PartMeasurementUnit PMU ON PMU.pmuid = R.unittypeId"
						+ " WHERE  R.purchaseorders.purchaseorder_id = " + purchaseOrders_id + " AND R.companyId = "
						+ companyId + " AND R.markForDelete = 0",
				Object[].class);

		List<Object[]> results = query.getResultList();

		List<PurchaseOrdersToPartsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersToPartsDto>();
			PurchaseOrdersToPartsDto list = null;
			for (Object[] result : results) {
				list = new PurchaseOrdersToPartsDto();

				list.setPurchaseorderto_partid((Long) result[0]);
				list.setPartid((Long) result[1]);
				list.setPurchaseorder_partname((String) result[2]);
				list.setPurchaseorder_partnumber((String) result[3]);
				if(result[4] != null)
				 list.setParttype(PartType.getPartTypeName((short) result[4]));
				list.setTYRE_MANUFACTURER((String) result[5]);
				list.setBATTERY_MANUFACTURER_ID((Long) result[6]);
				list.setTYRE_MODEL((String) result[7]);
				list.setBATTERY_TYPE_ID((Long) result[8]);
				list.setTYRE_SIZE((String) result[9]);
				list.setBATTERY_CAPACITY_ID((Long) result[10]);
				list.setBATTERY_QUANTITY((Long) result[11]);
				list.setINVENTORY_TYRE_NEW_RT((String) result[12]);
				list.setQuantity((Double) result[13]);
				list.setReceived_quantity((Double) result[14]);
				list.setReceived_quantity_remark((String) result[15]);
				list.setNotreceived_quantity((Double) result[16]);
				list.setUnittype((String) result[17]);
				list.setDiscount((Double) result[18]);
				list.setTax((Double) result[19]);
				list.setTotalcost((Double) result[20]);
				list.setInventory_all_id((Long) result[21]);
				list.setInventory_all_quantity((Double) result[22]);
				list.setParteachcost((Double) result[23]);
				list.setPurchaseorder_id(purchaseOrders_id);
				list.setBatteryPartNumber((String) result[24]);
				list.setWarrantyPeriod((Integer) result[25]);
				if(result[26] != null)
				list.setWarrantyTypeId((short) result[26]);
				
				if(list.getBatteryPartNumber() != null) {
					list.setTYRE_MODEL(list.getTYRE_MODEL() + "-"+list.getBatteryPartNumber()+"("+list.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(list.getWarrantyTypeId())+")");
				}else {
					list.setTYRE_MODEL(list.getTYRE_MODEL() +"("+list.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(list.getWarrantyTypeId())+")");
				}
				list.setApprovalPartStatusId((short) result[27]);
				list.setApprovalPartStatus(PurchaseOrderState.getPurchaseOrderApprovalStatus(list.getApprovalPartStatusId()));
				
				double eachReceivedPartAmount = list.getParteachcost() * list.getReceived_quantity();
				double costWithTax = eachReceivedPartAmount + (eachReceivedPartAmount * list.getTax() / 100);
			    double ReceivedPartCost= costWithTax - (costWithTax * list.getDiscount() /100);
			    
			    list.setFinalReceivedAmount(Double.parseDouble(toFixedTwo.format(ReceivedPartCost)));
			    
				Dtos.add(list);
			}
		}
		return Dtos;

	}
	
	@Transactional
	public List<PurchaseOrdersToTyre> getPurchaseOrdersTasksToTyre(long purchaseOrders_id, Integer companyId) {

		return PurchaseOrdersToTyre_Repository.getPurchaseOrdersTasksIDToTyreList(purchaseOrders_id, companyId);
	}
	
	@Override
	public List<PurchaseOrderToBattery> getPurchaseOrdersTasksToBattery(long purchaseOrders_id, Integer companyId) {
		
		return purchaseOrderToBatteryRepository.getPurchaseOrdersTasksIDToBatteryList(purchaseOrders_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IPurchaseOrdersService#
	 * getDeployment_Page_PurchaseOrders(java.lang.String, java.lang.Integer)
	 */
	@Transactional

	public Page<PurchaseOrders> getDeployment_Page_PurchaseOrders(short status, Integer pageNumber, Integer companyId) {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		
		CustomUserDetails userDetails = null;
		HashMap<String, Object> 				poConfiguration       	 		= null;
		
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			poConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if((boolean)poConfiguration.get("requisitionApproved") && status == PurchaseOrderType.PURCHASEORDER_STATUS_REQUISITION )
		{
			return PurchaseOrdersDao.getDeployment_Page_PurchaseOrdersByApprovalStatus(status, companyId, pageable);
		}
		
		return PurchaseOrdersDao.getDeployment_Page_PurchaseOrders(status, companyId, pageable);
	}

	@Transactional
	public List<PurchaseOrdersDto> listOpenPurchaseOrders(short status, Integer pageNumber, Integer companyId) {
		CustomUserDetails userDetails = null;
		HashMap<String, Object> 				poConfiguration       	 		= null;
		
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			poConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query2;
		if((boolean)poConfiguration.get("requisitionApproved") && status == PurchaseOrderType.PURCHASEORDER_STATUS_REQUISITION)
		{
			query2 = " AND p.markForDelete = 0 AND p.approvalStatus = "+ PurchaseOrderType.PURCHASE_STATUS_NOT_APPROVED+ "ORDER BY p.purchaseorder_id desc ";
		}
		else
		{
			query2 = " AND p.markForDelete = 0 ORDER BY p.purchaseorder_id desc ";
		}
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT p.purchaseorder_id, p.purchaseorder_Number, p.purchaseorder_typeId, p.purchaseorder_vendor_id, VN.vendorName,"
						+ " VN.vendorLocation, p.purchaseorder_created_on, p.purchaseorder_requied_on,"
						+ " p.purchaseorder_termsId, p.purchaseorder_quotenumber, p.purchaseorder_indentno, WO.workorders_Number, PL.partlocation_name,"
						+ " p.purchaseorder_statusId, p.purchaseorder_document, p.purchaseorder_document_id, C.company_name, C.company_address, C.company_city, C.company_state, C.company_country, C.company_pincode,"
						+ " p.purchaseorder_invoiceno,p.purchaseorder_vendor_paymentdate, p.subCompanyId, SC.subCompanyName "
						+ " From PurchaseOrders as p "
						+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = p.purchaseorder_workordernumber"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = p.purchaseorder_vendor_id "
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = p.purchaseorder_shiplocation_id"
						+ " LEFT JOIN Company C ON C.company_id = p.purchaseorder_buyer_id"
						+ " LEFT JOIN SubCompany SC ON SC.subCompanyId = p.subCompanyId"
						+ " where p.purchaseorder_statusId=" + status + " AND p.companyId = " + companyId
						+   query2 ,
						//+ " AND p.markForDelete = 0 AND p.approvalStatus = "+ PurchaseOrderType.PURCHASE_STATUS_NOT_APPROVED+ "ORDER BY p.purchaseorder_id desc ",
				Object[].class);
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		List<Object[]> results = query.getResultList();

		List<PurchaseOrdersDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersDto>();
			PurchaseOrdersDto list = null;
			for (Object[] result : results) {
				list = new PurchaseOrdersDto();

				list.setPurchaseorder_id((Long) result[0]);
				list.setPurchaseorder_Number((Long) result[1]);
				list.setPurchaseorder_typeId((short) result[2]);
				list.setPurchaseorder_vendor_id((Integer) result[3]);
				list.setPurchaseorder_vendor_name((String) result[4]);
				list.setPurchaseorder_vendor_location((String) result[5]);
				list.setPurchaseorder_created((Date) result[6]);
				list.setPurchaseorder_requied((Date) result[7]);
				list.setPurchaseorder_termsId((short) result[8]);
				list.setPurchaseorder_quotenumber((String) result[9]);
				list.setPurchaseorder_indentno((String) result[10]);
				list.setPurchaseorder_workordernumber((Long) result[11] + "");
				list.setPurchaseorder_shiplocation((String) result[12]);
				list.setPurchaseorder_statusId((short) result[13]);
				list.setPurchaseorder_document((boolean) result[14]);
				list.setPurchaseorder_document_id((Long) result[15]);
				if(result[24] != null ) {
					list.setPurchaseorder_buyer((String) result[25]);
				}else {
					list.setPurchaseorder_buyer((String) result[16]);
				}
				list.setPurchaseorder_buyeraddress((String) result[17]+" , "+ (String) result[18]+"  , "+(String) result[19]+" , "+(String) result[20]+" . PIN- "+(String) result[21]);
				list.setPurchaseorder_invoiceno((String) result[22]);
				list.setPurchaseorder_vendor_paymentdate((Timestamp) result[23]);
				
				Dtos.add(list);
			}
		}
		return Dtos;

	}

	@Transactional
	public PurchaseOrdersToParts addPurchaseOrdersToParts(PurchaseOrdersToParts purchaseOrdersTask) throws Exception {

		return PurchaseOrdersToPartsRepository.save(purchaseOrdersTask);

	}

	@Transactional
	public Long countPurchaseOrder(Integer companyId) throws Exception {

		return PurchaseOrdersDao.countPurchaseOrder(companyId);
	}

	@Transactional
	public List<PurchaseOrdersToParts> getPurchaseOrdersTasksIDToPartsList(long purchaseOrders_id) {

		return PurchaseOrdersToPartsRepository.getPurchaseOrdersTasksIDToPartsList(purchaseOrders_id);
	}

	@Transactional
	public void updatePurchaseOrderMainTotalCost(Long purchaseOdresId) throws Exception {
		CustomUserDetails 					userDetails 					= null;
		HashMap<String, Object> 			configuration	        		= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration				= CompanyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			if((boolean) configuration.get("totalGstCost")) {
				//update subTotalCost
				entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_subtotal_cost = (SELECT SUM(POP.totalcost) FROM PurchaseOrdersToParts AS POP "
						+ " WHERE POP.purchaseorders.purchaseorder_id= "+ purchaseOdresId + " AND POP.markForDelete = 0 AND POP.companyId=" + userDetails.getCompany_id()+"  AND POP.approvalPartStatusId ="+PurchaseOrderState.APPROVED+" )"
						+ " WHERE purchaseorder_id=" + purchaseOdresId + " AND companyId = " + userDetails.getCompany_id() + " " ).executeUpdate();
				//update totalCost
				entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_totalcost = ((SELECT SUM(POP.totalcost) FROM PurchaseOrdersToParts AS POP "
						+ " WHERE POP.purchaseorders.purchaseorder_id= "+ purchaseOdresId + " AND POP.markForDelete = 0 AND POP.companyId=" + userDetails.getCompany_id()+"  AND POP.approvalPartStatusId ="+PurchaseOrderState.APPROVED+" )+ purchaseorder_freight ) "
						+ " where purchaseorder_id=" + purchaseOdresId + " AND companyId = " + userDetails.getCompany_id() + "" ).executeUpdate();
				//update PO Balance Cost
				entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_balancecost = (((SELECT SUM(POP.totalcost) FROM PurchaseOrdersToParts AS POP "
						+ " WHERE POP.purchaseorders.purchaseorder_id= "+ purchaseOdresId + " AND POP.markForDelete = 0 AND POP.companyId=" + userDetails.getCompany_id()+"  AND POP.approvalPartStatusId ="+PurchaseOrderState.APPROVED+"  ) + purchaseorder_freight ) - (purchaseorder_total_debitnote_cost + purchaseorder_advancecost)) "
						+ " where purchaseorder_id=" + purchaseOdresId + " AND companyId = " + userDetails.getCompany_id() + "" ).executeUpdate();
				//update Balance Cost
				entityManager.createQuery("UPDATE PurchaseOrders SET balanceAmount = (((SELECT SUM(POP.totalcost) FROM PurchaseOrdersToParts AS POP "
						+ " WHERE POP.purchaseorders.purchaseorder_id= "+ purchaseOdresId + " AND POP.markForDelete = 0 AND POP.companyId=" + userDetails.getCompany_id()+"   AND POP.approvalPartStatusId ="+PurchaseOrderState.APPROVED+" ) + purchaseorder_freight ) - (purchaseorder_total_debitnote_cost + purchaseorder_advancecost)) "
						+ " where purchaseorder_id=" + purchaseOdresId + " AND companyId = " + userDetails.getCompany_id() + "" ).executeUpdate();
				
		
			
			}else {
				
				//update subTotalCost
				entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_subtotal_cost = (SELECT SUM(POP.totalcost) FROM PurchaseOrdersToParts AS POP "
						+ " WHERE POP.purchaseorders.purchaseorder_id= "+ purchaseOdresId + " AND POP.markForDelete = 0 AND POP.companyId=" + userDetails.getCompany_id()+"  AND POP.approvalPartStatusId ="+PurchaseOrderState.APPROVED+" )"
						+ " WHERE purchaseorder_id=" + purchaseOdresId + " AND companyId = " + userDetails.getCompany_id() + " " ).executeUpdate();
				//update totalCost
				entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_totalcost = ((SELECT SUM(POP.totalcost) FROM PurchaseOrdersToParts AS POP "
						+ " WHERE POP.purchaseorders.purchaseorder_id= "+ purchaseOdresId + " AND POP.markForDelete = 0 AND POP.companyId=" + userDetails.getCompany_id()+"  AND POP.approvalPartStatusId ="+PurchaseOrderState.APPROVED+" )+ purchaseorder_freight + purchaseorder_totaltax_cost) "
						+ " where purchaseorder_id=" + purchaseOdresId + " AND companyId = " + userDetails.getCompany_id() + "" ).executeUpdate();
				//update PO Balance Cost
				entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_balancecost = (((SELECT SUM(POP.totalcost) FROM PurchaseOrdersToParts AS POP "
						+ " WHERE POP.purchaseorders.purchaseorder_id= "+ purchaseOdresId + " AND POP.markForDelete = 0 AND POP.companyId=" + userDetails.getCompany_id()+"  AND POP.approvalPartStatusId ="+PurchaseOrderState.APPROVED+" ) + purchaseorder_freight + purchaseorder_totaltax_cost ) - (purchaseorder_total_debitnote_cost + purchaseorder_advancecost)) "
						+ " where purchaseorder_id=" + purchaseOdresId + " AND companyId = " + userDetails.getCompany_id() + "" ).executeUpdate();
				//update Balance Cost
				entityManager.createQuery("UPDATE PurchaseOrders SET balanceAmount = (((SELECT SUM(POP.totalcost) FROM PurchaseOrdersToParts AS POP "
						+ " WHERE POP.purchaseorders.purchaseorder_id= "+ purchaseOdresId + " AND POP.markForDelete = 0 AND POP.companyId=" + userDetails.getCompany_id()+"  AND POP.approvalPartStatusId ="+PurchaseOrderState.APPROVED+"  ) + purchaseorder_freight +purchaseorder_totaltax_cost ) - (purchaseorder_total_debitnote_cost + purchaseorder_advancecost)) "
						+ " where purchaseorder_id=" + purchaseOdresId + " AND companyId = " + userDetails.getCompany_id() + "" ).executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		

	}

	@Transactional
	public void updatePurchaseOrderedBYAdavanceCost(PurchaseOrders Update_OrderedBy) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_orderd_remark='" + Update_OrderedBy.getPurchaseorder_orderd_remark() + "', "
				+ " purchaseorder_advancecost = " + Update_OrderedBy.getPurchaseorder_advancecost() + ","
				+ " purchaseorder_balancecost = " + Update_OrderedBy.getPurchaseorder_balancecost() + ", "
				+ " purchaseorder_orderdbyId = " + userDetails.getId() + ", "
				+ " purchaseorder_orderddate = ' " + Update_OrderedBy.getPurchaseorder_orderddate() + "', "
				+ " purchaseorder_statusId = " + Update_OrderedBy.getPurchaseorder_statusId() + ", "
				+ " lastModifiedById=" + Update_OrderedBy.getLastModifiedById() + ", "
				+ " lastupdated = '" + Update_OrderedBy.getLastupdated() + "',"
				+ " balanceAmount = "+Update_OrderedBy.getPurchaseorder_balancecost()+" "
				+ " where Purchaseorder_id=" + Update_OrderedBy.getPurchaseorder_id() + " AND companyId = " + userDetails.getCompany_id() + "").executeUpdate();
	}

	@Transactional
	public void updatePurchaseOrderPart_ReceivedQuantity(Double TotalPurchaseOrderPart_ReceivedQty,
			String Received_Qty_Remark, Double TotalPurchaseOrderPart_NOT_ReceivedQty, Long TotalPurchaseOrderPart_Id)
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		PurchaseOrdersDao.updatePurchaseOrderPart_ReceivedQuantity(TotalPurchaseOrderPart_ReceivedQty,
				Received_Qty_Remark, TotalPurchaseOrderPart_NOT_ReceivedQty, TotalPurchaseOrderPart_Id,
				userDetails.getCompany_id());
	}

	@Transactional
	public void updatePurchaseOrderedToReceived_Quantity(PurchaseOrders Update_OrderedBy) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		entityManager
				.createQuery("UPDATE PurchaseOrders SET purchaseorder_received_remark='"
						+ Update_OrderedBy.getPurchaseorder_received_remark() + "', purchaseorder_receivedbyId = "
						+ Update_OrderedBy.getPurchaseorder_receivedbyId() + ", purchaseorder_received_date = '"
						+ Update_OrderedBy.getPurchaseorder_received_date() + "', purchaseorder_invoice_date = '"
						+ Update_OrderedBy.getPurchaseorder_invoice_date() + "', purchaseorder_invoiceno = '"
						+ Update_OrderedBy.getPurchaseorder_invoiceno() + "', purchaseorder_statusId = "
						+ Update_OrderedBy.getPurchaseorder_statusId() + ", lastModifiedById="
						+ Update_OrderedBy.getLastModifiedById() + ", lastupdated = '"
						+ Update_OrderedBy.getLastupdated() + "'  where Purchaseorder_id="
						+ Update_OrderedBy.getPurchaseorder_id() + " AND companyId = " + userDetails.getCompany_id())
				.executeUpdate();
	}

	@Transactional
	public void updatePurchaseOrder_Freight(PurchaseOrders Update_OrderedBy) throws Exception {

		// PurchaseOrdersDao.updatePurchaseOrder_Freight(Update_OrderedBy);
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_freight ="
				+ Update_OrderedBy.getPurchaseorder_freight() + ", purchaseorder_totalcost = "
				+ Update_OrderedBy.getPurchaseorder_totalcost() + ", purchaseorder_balancecost = "
				+ Update_OrderedBy.getPurchaseorder_balancecost() + ", lastModifiedById="
				+ Update_OrderedBy.getLastModifiedById() + ", Lastupdated = '" + Update_OrderedBy.getLastupdated()
				+ "' where Purchaseorder_id=" + Update_OrderedBy.getPurchaseorder_id() + " AND companyId = "
				+ userDetails.getCompany_id() + "").executeUpdate();
	}

	@Transactional
	public void updatePurchaseOrder_TaxCost(PurchaseOrders Update_OrderedBy) throws Exception {

		// PurchaseOrdersDao.updatePurchaseOrder_TaxCost(Update_OrderedBy);

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_totaltax_cost ="
				+ Update_OrderedBy.getPurchaseorder_totaltax_cost() + ", purchaseorder_totalcost = "
				+ Update_OrderedBy.getPurchaseorder_totalcost() + ", purchaseorder_balancecost = "
				+ Update_OrderedBy.getPurchaseorder_balancecost() + ", lastModifiedById="
				+ Update_OrderedBy.getLastModifiedById() + ", Lastupdated = '" + Update_OrderedBy.getLastupdated()
				+ "'  where Purchaseorder_id=" + Update_OrderedBy.getPurchaseorder_id() + " AND companyId = "
				+ userDetails.getCompany_id() + "").executeUpdate();
	}

	@Transactional
	public void addPurchaseOrdersToDebitNote(PurchaseOrdersToDebitNote purchaseOrdersToDebitNote) throws Exception {

		PurchaseOrdersToDebitNoteRepository.save(purchaseOrdersToDebitNote);
	}

	@Transactional
	public List<PurchaseOrdersToDebitNote> getPurchaseOrdersTasksToDebitNote(long purchaseOrders_id) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return PurchaseOrdersToDebitNoteRepository.getPurchaseOrdersTasksToDebitNote(purchaseOrders_id,
				userDetails.getCompany_id());
	}

	@Override
	public List<PurchaseOrdersToDebitNoteDto> getPurchaseOrdersTasksToDebitNote(long purchaseOrders_id,
			Integer companyId) throws Exception {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT p.purchaseorderto_debitnoteid, p.partid, MP.partname, MP.partnumber, TM.TYRE_MODEL,"
						+ " TSM.TYRE_MODEL_SUBTYPE, VS.TYRE_SIZE, p.received_quantity_remark, p.notreceived_quantity, p.parteachcost, PMU.pmuName,"
						+ " p.discount, p.tax, p.total_return_cost, p.purchaseorder_id, BM.manufacturerName, BT.batteryType, BT.partNumber, BT.warrantyPeriod, "
						+ " BT.warrantyTypeId, BC.batteryCapacity, CT.clothTypeName "
						+ " From PurchaseOrdersToDebitNote as p "
						+ " LEFT JOIN MasterParts MP ON MP.partid = p.partid"
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = p.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = p.TYRE_MODEL_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = p.TYRE_SIZE_ID "
						+ " LEFT JOIN BatteryManufacturer BM ON BM.batteryManufacturerId = p.batteryManufacturerId" 
						+ " LEFT JOIN BatteryType BT ON BT.batteryTypeId = p.batteryTypeId" 
						+ " LEFT JOIN BatteryCapacity BC ON BC.batteryCapacityId = p.batteryCapacityId" 
						+ " LEFT JOIN PartMeasurementUnit PMU ON PMU.pmuid = p.unittypeId"
						+ " LEFT JOIN ClothTypes CT ON CT.clothTypesId = p.clothTypesId"
						+ " where p.purchaseorder_id = "
						+ purchaseOrders_id + " AND p.companyId = " + companyId + " AND p.markForDelete = 0 ",
				Object[].class);

		List<Object[]> results = query.getResultList();

		List<PurchaseOrdersToDebitNoteDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersToDebitNoteDto>();
			PurchaseOrdersToDebitNoteDto list = null;
			for (Object[] result : results) {
				list = new PurchaseOrdersToDebitNoteDto();

				list.setPurchaseorderto_debitnoteid((Long) result[0]);
				list.setPartid((Long) result[1]);
				list.setPurchaseorder_partname((String) result[2]);
				list.setPurchaseorder_partnumber((String) result[3]);
				list.setTYRE_MANUFACTURER((String) result[4]);
				list.setTYRE_MODEL((String) result[5]);
				list.setTYRE_SIZE((String) result[6]);
				list.setReceived_quantity_remark((String) result[7]);
				list.setNotreceived_quantity(Double.parseDouble(toFixedTwo.format(result[8])));
				list.setParteachcost((Double) result[9]);
				list.setUnittype((String) result[10]);
				list.setDiscount((Double) result[11]);
				list.setTax((Double) result[12]);
				list.setTotal_return_cost((Double) result[13]);
				list.setPurchaseorder_id((Long) result[14]);
				list.setManufacturerName((String) result[15]);
				list.setBatteryType((String) result[16]);
				list.setPartNumber((String) result[17]);
				if(result[18] != null)
					list.setWarrantyPeriod((Integer) result[18]);
				if(result[19] != null)
					list.setWarrantyTypeId((short) result[19]);
				if(result[20] != null)
					list.setBatteryCapacity((String) result[20]);
				if(result[21] != null)
					list.setClothTypeName((String) result[21]);
				
				if(list.getPartNumber() != null) {
					list.setBatteryType(list.getBatteryType() + "-"+list.getPartNumber()+"("+list.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(list.getWarrantyTypeId())+")");
				}else {
					list.setBatteryType(list.getBatteryType() +"("+list.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(list.getWarrantyTypeId())+")");
				}

				Dtos.add(list);
			}
		}
		return Dtos;

	}

	@Override
	public List<PurchaseOrdersToDebitNoteDto> getPurchaseOrdersTasksToDebitNoteForCloth(long purchaseOrders_id,
			Integer companyId) throws Exception {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT p.purchaseorderto_debitnoteid, p.clothTypesId, CT.clothTypeName,"
						+ " p.received_quantity_remark, p.notreceived_quantity, p.parteachcost, PMU.pmuName,"
						+ " p.discount, p.tax, p.total_return_cost, p.purchaseorder_id"
						+ " From PurchaseOrdersToDebitNote as p "
						+ " LEFT JOIN ClothTypes CT ON CT.clothTypesId = p.clothTypesId"
						+ " LEFT JOIN PartMeasurementUnit PMU ON PMU.pmuid = p.unittypeId"
						+ " where p.purchaseorder_id = "
						+ purchaseOrders_id + " AND p.companyId = " + companyId + " AND p.markForDelete = 0 ",
				Object[].class);

		List<Object[]> results = query.getResultList();

		List<PurchaseOrdersToDebitNoteDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersToDebitNoteDto>();
			PurchaseOrdersToDebitNoteDto list = null;
			for (Object[] result : results) {
				list = new PurchaseOrdersToDebitNoteDto();

				list.setPurchaseorderto_debitnoteid((Long) result[0]);
				list.setClothTypesId((Long) result[1]);
				list.setClothTypeName((String) result[2]);
				list.setReceived_quantity_remark((String) result[3]);
				list.setNotreceived_quantity((Double) result[4]);
				list.setParteachcost((Double) result[5]);
				list.setUnittype((String) result[6]);
				list.setDiscount((Double) result[7]);
				list.setTax((Double) result[8]);
				list.setTotal_return_cost((Double) result[9]);
				list.setPurchaseorder_id((Long) result[10]);
				

				Dtos.add(list);
			}
		}
		return Dtos;

	}
	
	@Transactional
	public void updatePurchaseOrder_Total_DebitCost(Double TotalpurchaseOrder_Debitcost, Long purchaseOrder_ID)
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		PurchaseOrdersDao.updatePurchaseOrder_Total_DebitCost(TotalpurchaseOrder_Debitcost, purchaseOrder_ID,
				userDetails.getCompany_id());

	}

	@Transactional
	public void deletePurchaseOrdersTOParts(Long purchaseOrdersTask_id, Integer companyId) {
		PurchaseOrdersToPartsRepository.deletePurchaseOrdersTOParts(purchaseOrdersTask_id, companyId);
	}

	@Transactional
	public void uploadPurchaseOrdersDocument(org.fleetopgroup.persistence.document.PurchaseOrdersDocument purchaseOrdersdocument) throws Exception {
		purchaseOrdersdocument.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_PURCHASE_ORDER_DOCUMENT));
		mongoTemplate.save(purchaseOrdersdocument);
	}

	@Transactional
	public PurchaseOrdersDocument getPurchaseOrdersDocument(Long purchaseOrders_id) throws Exception {

		return PurchaseOrdersDocumentRepository.getPurchaseOrdersDocument(purchaseOrders_id);
	}

	@Override
	public org.fleetopgroup.persistence.document.PurchaseOrdersDocument getPurchaseOrdersDocumentDetails(Long purchaseorder_documentid) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(purchaseorder_documentid).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.PurchaseOrdersDocument.class);
	}

	@Transactional
	public org.fleetopgroup.persistence.document.PurchaseOrdersDocument ValidatePurchaseOrdersDocument(Long purchaseOrders_id, String purchaseorder_document)
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("purchaseorder_id").is(purchaseOrders_id).and("purchaseorder_document").is(purchaseorder_document).and("companyId").is(userDetails.getCompany_id()).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.PurchaseOrdersDocument.class);
	}

	@Transactional
	public void updateOldPurchaseOrdersDocument(org.fleetopgroup.persistence.document.PurchaseOrdersDocument purchaseOrdersDocument) throws Exception {

		// this save and update also same
		mongoTemplate.save(purchaseOrdersDocument);
	}

	@Transactional
	public void updatePurchaseOrder_Complete(PurchaseOrders Update_Complete) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_complete_date = '" + Update_Complete.getPurchaseorder_complete_date() + "', "
				+ " purchaseorder_balancecost = " + Update_Complete.getPurchaseorder_balancecost() + ", "
				+ " purchaseorder_statusId = " + Update_Complete.getPurchaseorder_statusId() + ", "
				+ " lastModifiedById=" + Update_Complete.getLastModifiedById() + ", "
				+ " Lastupdated = '" + Update_Complete.getLastupdated() + "', "
				+ " balanceAmount = " + Update_Complete.getPurchaseorder_balancecost() + " "
				+ " where Purchaseorder_id=" + Update_Complete.getPurchaseorder_id() + " AND companyId = " 	+ userDetails.getCompany_id() + "").executeUpdate();
	}

	@Transactional
	public void updatePurchaseOrder_ReOpen(PurchaseOrders Update_Complete) throws Exception {

		// PurchaseOrdersDao.updatePurchaseOrder_Complete(Update_Complete);
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		entityManager.createQuery("UPDATE PurchaseOrders SET  purchaseorder_statusId = "
				+ Update_Complete.getPurchaseorder_statusId() + ", lastModifiedById="
				+ Update_Complete.getLastModifiedById() + ", Lastupdated = '" + Update_Complete.getLastupdated()
				+ "'  where Purchaseorder_id=" + Update_Complete.getPurchaseorder_id() + " AND companyId =  "
				+ userDetails.getCompany_id() + "").executeUpdate();
	}

	@Transactional
	public List<PurchaseOrdersDto> SearchPurchaseOrders(String PurchaseOrders_Search, CustomUserDetails userDetails)
			throws Exception {

		List<PurchaseOrdersDto> Dtos = null;
		 //return PurchaseOrdersDao.SearchPurchaseOrders(purchaseOrders_Search);
		if(PurchaseOrders_Search != null && !PurchaseOrders_Search.trim().equalsIgnoreCase("") && PurchaseOrders_Search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT p.purchaseorder_id, p.purchaseorder_Number, p.purchaseorder_typeId, p.purchaseorder_vendor_id, VN.vendorName,"
						+ " VN.vendorLocation, p.purchaseorder_created_on, p.purchaseorder_requied_on,"
						+ " p.purchaseorder_termsId, p.purchaseorder_quotenumber, p.purchaseorder_indentno, WO.workorders_Number, PL.partlocation_name,"
						+ " p.purchaseorder_statusId, p.purchaseorder_document, p.purchaseorder_document_id , C.company_name, C.company_address, C.company_city, C.company_state, C.company_country, C.company_pincode"
						+ " , p.purchaseorder_invoiceno, p.purchaseorder_invoice_date "
						+ " From PurchaseOrders as p "
						+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = p.purchaseorder_workordernumber"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = p.purchaseorder_vendor_id "
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = p.purchaseorder_shiplocation_id"
						+ " INNER JOIN Company C ON C.company_id = p.purchaseorder_buyer_id"
						+ " where (lower(p.purchaseorder_Number) Like ('%" + PurchaseOrders_Search
						+ "%') OR lower(p.purchaseorder_indentno) Like ('%" + PurchaseOrders_Search
						+ "%') OR lower(p.purchaseorder_invoiceno) Like ('%" + PurchaseOrders_Search
						+ "%') OR lower(p.purchaseorder_quotenumber) Like ('%" + PurchaseOrders_Search
						+ "%') OR lower(WO.workorders_Number) Like ('%" + PurchaseOrders_Search
						+ "%')) AND p.companyId = " + userDetails.getCompany_id() + " AND p.markForDelete = 0 ORDER BY purchaseorder_id DESC",
				Object[].class);

		List<Object[]> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersDto>();
			PurchaseOrdersDto list = null;
			for (Object[] result : results) {
				list = new PurchaseOrdersDto();

				list.setPurchaseorder_id((Long) result[0]);
				list.setPurchaseorder_Number((Long) result[1]);
				list.setPurchaseorder_typeId((short) result[2]);
				list.setPurchaseorder_vendor_id((Integer) result[3]);
				list.setPurchaseorder_vendor_name((String) result[4]);
				list.setPurchaseorder_vendor_location((String) result[5]);
				list.setPurchaseorder_created((Date) result[6]);
				list.setPurchaseorder_requied((Date) result[7]);
				list.setPurchaseorder_termsId((short) result[8]);
				list.setPurchaseorder_quotenumber((String) result[9]);
				list.setPurchaseorder_indentno((String) result[10]);
				list.setPurchaseorder_workordernumber((Long) result[11] + "");
				list.setPurchaseorder_shiplocation((String) result[12]);
				list.setPurchaseorder_statusId((short) result[13]);
				list.setPurchaseorder_document((boolean) result[14]);
				list.setPurchaseorder_document_id((Long) result[15]);
				list.setPurchaseorder_buyer((String) result[16]);
				list.setPurchaseorder_buyeraddress((String) result[17]+" , "+ (String) result[18]+"  , "+(String) result[19]+" , "+(String) result[20]+" . PIN- "+(String) result[21]);
				list.setPurchaseorder_invoiceno((String) result[22]);
				list.setPurchaseorder_invoice_date_On((Date) result[23]);

				Dtos.add(list);
			}
		}
		}
		return Dtos;

	}

	@Transactional
	public Long countPurchaseOrderStatues(String Statues) throws Exception {

		return PurchaseOrdersDao.countPurchaseOrderStatues(Statues);
	}

	@Transactional
	public void deletePurchaseOrders(Long purchaseOrders_id, Integer companyId,Long userId) {
		PurchaseOrdersDao.deletePurchaseOrders(purchaseOrders_id, companyId,userId,DateTimeUtility.getCurrentTimeStamp());
	}

	@Transactional
	public List<PurchaseOrdersDto> Report_PurchaseOrders(String Query, String rangeFrom, String RangeTo,
			Integer companyId) {
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT p.purchaseorder_id, p.purchaseorder_Number, p.purchaseorder_typeId, p.purchaseorder_vendor_id, VN.vendorName,"
						+ " VN.vendorLocation, p.purchaseorder_created_on, p.purchaseorder_requied_on,"
						+ " p.purchaseorder_termsId, p.purchaseorder_quotenumber, p.purchaseorder_indentno, WO.workorders_Number, PL.partlocation_name,"
						+ " p.purchaseorder_statusId, p.purchaseorder_document, p.purchaseorder_document_id, p.purchaseorder_vendor_paymodeId,"
						+ " p.purchaseorder_totalcost, p.purchaseorder_balancecost , C.company_name, C.company_address, C.company_city, C.company_state, C.company_country, C.company_pincode"
						+ " , p.purchaseorder_invoiceno, p.purchaseorder_invoice_date, p.paidAmount, p.balanceAmount "  
						+ " From PurchaseOrders as p"
						+ " INNER JOIN Company C ON C.company_id = p.companyId"
						+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = p.purchaseorder_workordernumber"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = p.purchaseorder_vendor_id"
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = p.purchaseorder_shiplocation_id"
						+ " INNER JOIN Company C ON C.company_id = p.purchaseorder_buyer_id"
						+ " where ((p.purchaseorder_created_on BETWEEN '" + rangeFrom + "' AND  '" + RangeTo + "') "
						+ Query + ") AND p.companyId = " + companyId + " AND p.markForDelete = 0 order by p.purchaseorder_invoice_date desc",
				Object[].class);
		List<Object[]> results = query.getResultList();

		List<PurchaseOrdersDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersDto>();
			PurchaseOrdersDto list = null;
			for (Object[] result : results) {
				list = new PurchaseOrdersDto();

				list.setPurchaseorder_id((Long) result[0]);
				list.setPurchaseorder_Number((Long) result[1]);
				list.setPurchaseorder_typeId((short) result[2]);
				list.setPurchaseorder_vendor_id((Integer) result[3]);
				list.setPurchaseorder_vendor_name((String) result[4]);
				list.setPurchaseorder_vendor_location((String) result[5]);
				list.setPurchaseorder_created((Date) result[6]);
				list.setPurchaseorder_requied((Date) result[7]);
				list.setPurchaseorder_termsId((short) result[8]);
				list.setPurchaseorder_quotenumber((String) result[9]);
				list.setPurchaseorder_indentno((String) result[10]);
				list.setPurchaseorder_workordernumber((Long) result[11] + "");
				list.setPurchaseorder_shiplocation((String) result[12]);
				list.setPurchaseorder_statusId((short) result[13]);
				list.setPurchaseorder_document((boolean) result[14]);
				list.setPurchaseorder_document_id((Long) result[15]);
				list.setPurchaseorder_vendor_paymodeId((short) result[16]);
				if( result[17] != null )
				list.setPurchaseorder_totalcost(Double.parseDouble(toFixedTwo.format((double)result[17])));
				if( result[18] != null)
				list.setPurchaseorder_balancecost(Double.parseDouble(toFixedTwo.format((double)result[18])));
				
				list.setPurchaseorder_buyer((String) result[19]);
				list.setPurchaseorder_buyeraddress((String) result[20]+" , "+ (String) result[21]+"  , "+(String) result[22]+" , "+(String) result[23]+" . PIN- "+(String) result[24]);
				list.setPurchaseorder_invoiceno((String) result[25]);
				list.setPurchaseorder_invoice_date_On((Date) result[26]);
				
				if(result[27]!= null) {
					list.setPaidAmount(Double.parseDouble(toFixedTwo.format(result[27])));
				}
				if(result[28]!= null) {
					list.setBalanceAmount(Double.parseDouble(toFixedTwo.format(result[28])));
				}
				

				Dtos.add(list);
			}
		}
		return Dtos;

	}

	@Transactional
	public List<PurchaseOrders> listPurchaseOrders_InvoiceDate_vendor_APPROVALLISTFilter(String Query, String rangeFrom,
			String RangeTo) {

		TypedQuery<PurchaseOrders> Qurey = entityManager
				.createQuery("From PurchaseOrders as p where (p.purchaseorder_invoice_date BETWEEN '" + rangeFrom
						+ "' AND  '" + RangeTo + "') " + Query + "", PurchaseOrders.class);

		return Qurey.getResultList();
	}

	/** This save Purchase Order Tyre Serial Number Table */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPurchaseOrders_Tyre_Serial_Number(PurchaseOrdersToTyre Tyre_Serial_Number) throws Exception {

		PurchaseOrdersToTyre_Repository.save(Tyre_Serial_Number);
	}
	
	@Override
	public void addPurchaseOrders_Battery(PurchaseOrderToBattery purchaseOrderToBattery) throws Exception {
		
		purchaseOrderToBatteryRepository.save(purchaseOrderToBattery);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IPurchaseOrdersService#
	 * Validate_PurchaseOrder_Received_Invoice(java.lang.String)
	 */
	@Transactional
	public List<PurchaseOrders> Validate_PurchaseOrder_Received_Invoice(Integer VendorID,
			String purchaseorder_invoiceno) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return PurchaseOrdersDao.Validate_PurchaseOrder_Received_Invoice(VendorID, purchaseorder_invoiceno,
				userDetails.getCompany_id());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IPurchaseOrdersService#
	 * UpdateEdit_PurchaseOrders_details(java.util.Date, java.util.Date,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Transactional
	public void UpdateEdit_PurchaseOrders_details(Date purchaseorder_created_on, Date purchaseorder_requied_on,
			short purchaseorder_terms, short purchaseorder_shipvia,
			String purchaseorder_quotenumber, String purchaseorder_indentno, String purchaseorder_workordernumber,
			String purchaseorder_notes, Long lastModifiedBy, Date Lastupdated_date, Long purchaseorder_id,
			short vendorPaymodId,Integer companyId,Long subCompanyId, Integer purchaseorder_vendor_id, Integer purchaseorder_shiplocation_id) {

		PurchaseOrdersDao.UpdateEdit_PurchaseOrders_details(purchaseorder_created_on, purchaseorder_requied_on,
				purchaseorder_terms, purchaseorder_shipvia, purchaseorder_quotenumber,
				purchaseorder_indentno, purchaseorder_workordernumber, purchaseorder_notes, lastModifiedBy,
				Lastupdated_date, purchaseorder_id,vendorPaymodId, companyId, subCompanyId, purchaseorder_vendor_id, purchaseorder_shiplocation_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IPurchaseOrdersService#
	 * update_Vendor_ApprovalTO_Status_MULTIP_PurchaseOrders(java.lang.String,
	 * java.lang.Long, java.lang.String)
	 */
	@Transactional
	public void update_Vendor_ApprovalTO_Status_MULTIP_PurchaseOrders(String ApprovalPurchase_ID, Long Approval_ID,
			short approval_Status) throws Exception {

		entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_vendor_approvalID=" + Approval_ID
				+ ", purchaseorder_vendor_paymodeId=" + approval_Status + " WHERE purchaseorder_id IN ("
				+ ApprovalPurchase_ID + ") ").executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentDetails(Long approvalId, String paymentDate, short paymentStatus) throws Exception {
		entityManager.createQuery("  UPDATE PurchaseOrders SET purchaseorder_vendor_paymentdate ='" + paymentDate+"' , "
				+ " purchaseorder_vendor_paymodeId="+paymentStatus+""
				+ " WHERE purchaseorder_vendor_approvalID = "+approvalId+" ").executeUpdate();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IPurchaseOrdersService#
	 * get_Amount_VendorApproval_IN_PurchaseOrders(java.lang.Long)
	 */
	@Transactional
	public List<PurchaseOrders> get_Amount_VendorApproval_IN_PurchaseOrders(Long VendorApproval_Id) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.purchaseorder_id, R.purchaseorder_totalcost, R.purchaseorder_balancecost From PurchaseOrders As R where R.purchaseorder_vendor_approvalID =:approval",
				Object[].class);
		queryt.setParameter("approval", VendorApproval_Id);

		List<Object[]> results = queryt.getResultList();

		List<PurchaseOrders> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrders>();
			PurchaseOrders list = null;
			for (Object[] result : results) {
				list = new PurchaseOrders();

				list.setPurchaseorder_id((Long) result[0]);
				list.setPurchaseorder_totalcost((Double) result[1]);
				list.setPurchaseorder_balancecost((Double) result[2]);

				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IPurchaseOrdersService#
	 * getVendorApproval_IN_PurchaseOrders(java.lang.Long)
	 */
	@Transactional
	public List<PurchaseOrdersDto> getVendorApproval_IN_PurchaseOrders(Long VendorApproval_Id, Integer companyId)
			throws Exception {
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT p.purchaseorder_id, p.purchaseorder_Number, p.purchaseorder_typeId, p.purchaseorder_vendor_id, VN.vendorName,"
						+ " VN.vendorLocation, p.purchaseorder_created_on, p.purchaseorder_requied_on,"
						+ " p.purchaseorder_termsId, p.purchaseorder_quotenumber, p.purchaseorder_indentno, WO.workorders_Number, PL.partlocation_name,"
						+ " p.purchaseorder_statusId, p.purchaseorder_document, p.purchaseorder_document_id, p.purchaseorder_vendor_paymodeId,"
						+ " p.purchaseorder_totalcost, p.purchaseorder_balancecost , C.company_name, C.company_address, C.company_city, C.company_state, C.company_country, C.company_pincode" 
						+ " , p.purchaseorder_invoiceno, p.purchaseorder_invoice_date,p.paidAmount " 
						+ " From PurchaseOrders as p "
						+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = p.purchaseorder_workordernumber"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = p.purchaseorder_vendor_id"
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = p.purchaseorder_shiplocation_id"
						+ " INNER JOIN Company C ON C.company_id = p.purchaseorder_buyer_id"
						+ " WHERE p.purchaseorder_vendor_approvalID = "+VendorApproval_Id+" AND p.companyId = "+companyId+" AND p.markForDelete = 0 ",
				Object[].class);
		List<Object[]> results = query.getResultList();

		List<PurchaseOrdersDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersDto>();
			PurchaseOrdersDto list = null;
			for (Object[] result : results) {
				list = new PurchaseOrdersDto();

				list.setPurchaseorder_id((Long) result[0]);
				list.setPurchaseorder_Number((Long) result[1]);
				list.setPurchaseorder_typeId((short) result[2]);
				list.setPurchaseorder_vendor_id((Integer) result[3]);
				list.setPurchaseorder_vendor_name((String) result[4]);
				list.setPurchaseorder_vendor_location((String) result[5]);
				list.setPurchaseorder_created((Date) result[6]);
				list.setPurchaseorder_requied((Date) result[7]);
				list.setPurchaseorder_termsId((short) result[8]);
				list.setPurchaseorder_quotenumber((String) result[9]);
				list.setPurchaseorder_indentno((String) result[10]);
				list.setPurchaseorder_workordernumber((Long) result[11] + "");
				list.setPurchaseorder_shiplocation((String) result[12]);
				list.setPurchaseorder_statusId((short) result[13]);
				list.setPurchaseorder_document((boolean) result[14]);
				list.setPurchaseorder_document_id((Long) result[15]);
				list.setPurchaseorder_vendor_paymodeId((short) result[16]);
				list.setPurchaseorder_totalcost((Double) result[17]);
				list.setPurchaseorder_balancecost((Double) result[18]);
				list.setPurchaseorder_buyer((String) result[19]);
				list.setPurchaseorder_buyeraddress((String) result[20]+" , "+ (String) result[21]+"  , "+(String) result[22]+" , "+(String) result[23]+" . PIN- "+(String) result[24]);
				list.setPurchaseorder_invoiceno((String) result[25]);
				list.setPurchaseorder_invoice_date_On((Date) result[26]);
				if((Double) result[27] != null)
				list.setPaidAmount((Double) result[27]);

				Dtos.add(list);
			}
		}
		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IPurchaseOrdersService#
	 * get_ApprovalID_AMOUNT_PurchaseOrders(long)
	 */
	@Transactional
	public PurchaseOrders get_ApprovalID_AMOUNT_PurchaseOrders(long purchaseOrders) {

		Query query = entityManager.createQuery(
				"SELECT d.purchaseorder_id, d.purchaseorder_vendor_approvalID, d.purchaseorder_totalcost from PurchaseOrders AS d where d.purchaseorder_id = :id AND d.markForDelete = 0");

		query.setParameter("id", purchaseOrders);
		Object[] vehicle = (Object[]) query.getSingleResult();

		PurchaseOrders select = new PurchaseOrders();
		select.setPurchaseorder_id((Long) vehicle[0]);
		select.setPurchaseorder_vendor_approvalID((Long) vehicle[1]);
		select.setPurchaseorder_totalcost((Double) vehicle[2]);

		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IPurchaseOrdersService#
	 * update_Vendor_ApprovalTO_Status_PayDate_Multiple_PurchaseOrders_ID(java.
	 * lang.String, java.util.Date, java.lang.Long, java.lang.String)
	 */
	@Transactional
	public void update_Vendor_ApprovalTO_Status_PayDate_Multiple_PurchaseOrders_ID(String ApprovalPurchase_ID,
			Date paymentDate, Long Approval_ID, short approval_Status, Integer companyId,double paidAmount, double discountAmount) throws Exception {
		double balanceAmount = 0;
		entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_vendor_paymentdate='" + paymentDate
				+ "' , purchaseorder_vendor_approvalID=" + Approval_ID + ", purchaseorder_vendor_paymodeId='" + approval_Status 
				+ "', paidAmount = '"+paidAmount+"', discountAmount = '"+discountAmount+"' , balanceAmount = '"+balanceAmount+"' "
				+ " WHERE purchaseorder_id IN (" + ApprovalPurchase_ID + ")  AND companyId = "
				+ companyId + "").executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IPurchaseOrdersService#
	 * Get_PurchaseOrdersTasks_ToDebitNoteID(long)
	 */
	@Transactional
	public PurchaseOrdersToDebitNote Get_PurchaseOrdersTasks_ToDebitNoteID(Long purchaseorderto_debitnoteid,
			Integer companyid) {

		return PurchaseOrdersToDebitNoteRepository.Get_PurchaseOrdersTasks_ToDebitNoteID(purchaseorderto_debitnoteid,
				companyid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IPurchaseOrdersService#
	 * Update_PurchaseOrder_Delete_DebitNote_Amount(org.fleetop.persistence.
	 * model.PurchaseOrders)
	 */
	@Transactional
	public void Update_PurchaseOrder_Delete_DebitNote_Amount(PurchaseOrders Update_Complete) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		entityManager.createQuery(
				"UPDATE PurchaseOrders SET purchaseorder_total_debitnote_cost =  purchaseorder_total_debitnote_cost - '"
						+ Update_Complete.getPurchaseorder_total_debitnote_cost()
						+ "', purchaseorder_balancecost = purchaseorder_balancecost + "
						+ Update_Complete.getPurchaseorder_balancecost() + ", purchaseorder_statusId = "
						+ Update_Complete.getPurchaseorder_statusId() + ", lastModifiedById="
						+ Update_Complete.getLastModifiedById() + ", Lastupdated = '" + Update_Complete.getLastupdated()
						+ "'  where Purchaseorder_id=" + Update_Complete.getPurchaseorder_id() + " AND companyId = "
						+ userDetails.getCompany_id())
				.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IPurchaseOrdersService#
	 * Delete_PurchaseOrders_ToDebitNoteID(java.lang.Long)
	 */
	@Transactional
	public void Delete_PurchaseOrders_ToDebitNoteID(Long purchaseorderto_debitnoteid, Integer companyId) {

		PurchaseOrdersToDebitNoteRepository.Delete_PurchaseOrders_ToDebitNoteID(purchaseorderto_debitnoteid, companyId);
	}
	
	@Transactional
	@Override
	public void deleteDebitNoteByPOId(Long purchaseorder_id, Integer companyId) {
		PurchaseOrdersToDebitNoteRepository.deleteDebitNoteByPOId(purchaseorder_id, companyId);
	}
	
	@Override
	@Transactional
	public void deleteDebitNoteByPOIdByPartId(Long purchaseorderto_debitnoteid, Integer companyId) {
		PurchaseOrdersToDebitNoteRepository.deleteDebitNoteByPOIdByPartId(purchaseorderto_debitnoteid, companyId);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IPurchaseOrdersService#
	 * Update_PurchaseOrders_To_Document_True(boolean, java.lang.Long)
	 */
	@Transactional
	public void Update_PurchaseOrders_To_Document_True(Long purchaseorder_documentid, boolean purchaseOrder_document,
			Long purchaseorder_id, Integer companyid) {

		PurchaseOrdersDao.Update_PurchaseOrders_To_Document_True(purchaseorder_documentid, purchaseOrder_document,
				purchaseorder_id, companyid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IPurchaseOrdersService#
	 * Part_consuming_Report(java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<PurchaseOrdersToPartsDto> Part_consuming_Report(String dateRangeFrom, String dateRangeTo,
			Integer companyId) {
		// Part Consuming Report

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT  v.partid, MP.partname, MP.partnumber, COUNT(v.partid) as inventory_all_id  "
						+ " FROM PurchaseOrdersToParts AS v "
						+ " LEFT JOIN MasterParts MP ON MP.partid = v.partid"
						+ " WHERE v.purchaseorders.purchaseorder_id IN (SELECT p.purchaseorder_id "
						+ " FROM PurchaseOrders AS p "
						+ " WHERE (p.purchaseorder_orderddate BETWEEN '"
						+ dateRangeFrom + "' AND  '" + dateRangeTo + "') AND  p.companyId = " + companyId
						+ " AND p.markForDelete = 0) AND v.companyId = " + companyId
						+ " AND v.markForDelete = 0 GROUP BY v.partid ORDER BY MP.partname ASC",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<PurchaseOrdersToPartsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersToPartsDto>();
			PurchaseOrdersToPartsDto dropdown = null;
			for (Object[] result : results) {
				dropdown = new PurchaseOrdersToPartsDto();

				dropdown.setPartid((Long) result[0]);
				dropdown.setPurchaseorder_partname((String) result[1]);
				dropdown.setPurchaseorder_partnumber((String) result[2]);
				dropdown.setInventory_all_id((Long) result[3]);

				Dtos.add(dropdown);
			}
		}
		return Dtos;
	}

	@Override
	public PurchaseOrdersDto getPurchaseOrdersByNumber(long purchaseOrders, CustomUserDetails userDetails) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT P.purchaseorder_id, P.purchaseorder_Number, P.purchaseorder_typeId, P.purchaseorder_vendor_id, VN.vendorName,"
						+ " VN.vendorLocation, P.purchaseorder_created_on, P.purchaseorder_requied_on, P.purchaseorder_termsId, "
						+ " P.purchaseorder_shipviaId, P.purchaseorder_shiplocation_id,"
						+ " PL.partlocation_name, PL.shippartlocation_address, PL.contactSecName, PL.contactSecPhone,"
						+ " P.purchaseorder_quotenumber, WO.workorders_Number, P.purchaseorder_indentno, P.purchaseorder_notes, P.purchaseorder_statusId,"
						+ " P.purchaseorder_subtotal_cost, P.purchaseorder_totaltax_cost, P.purchaseorder_freight, P.purchaseorder_totalcost,"
						+ " P.purchaseorder_advancecost, P.purchaseorder_balancecost, P.purchaseorder_orderd_remark,"
						+ " P.purchaseorder_orderddate, P.purchaseorder_received_remark, P.purchaseorder_received_date,"
						+ " P.purchaseorder_invoiceno, P.purchaseorder_invoice_date, P.purchaseorder_total_debitnote_cost, P.purchaseorder_complete_date, "
						+ " P.purchaseorder_document, P.created, P.lastupdated, P.purchaseorder_orderdbyId, P.purchaseorder_receivedbyId,"
						+ " P.createdById, P.lastModifiedById, PL.shippartlocation_streetaddress, PL.shippartlocation_city, PL.partlocation_state,"
						+ " PL.shippartlocation_country, PL.shippartlocation_pincode , C.company_name, C.company_address, C.company_city, C.company_state, C.company_country, C.company_pincode"  
						+ " FROM PurchaseOrders P "
						+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = P.purchaseorder_workordernumber"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = P.purchaseorder_vendor_id "
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = P.purchaseorder_shiplocation_id"
						+ " INNER JOIN Company C ON C.company_id = P.purchaseorder_buyer_id"
						+ " WHERE P.purchaseorder_Number = :purchaseorder_id AND P.companyId = :companyId AND P.markForDelete = 0 ");

		query.setParameter("purchaseorder_id", purchaseOrders);
		query.setParameter("companyId", userDetails.getCompany_id());

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		PurchaseOrdersDto select;
		if (result != null) {
			select = new PurchaseOrdersDto();

			select.setPurchaseorder_id((Long) result[0]);
			select.setPurchaseorder_Number((Long) result[1]);
			select.setPurchaseorder_typeId((short) result[2]);
			select.setPurchaseorder_vendor_id((Integer) result[3]);
			select.setPurchaseorder_vendor_name((String) result[4]);
			select.setPurchaseorder_vendor_location((String) result[5]);
			select.setPurchaseorder_created((Date) result[6]);
			select.setPurchaseorder_requied((Date) result[7]);
			select.setPurchaseorder_termsId((short) result[8]);
			select.setPurchaseorder_shipviaId((short) result[9]);
			select.setPurchaseorder_shiplocation_id((Integer) result[10]);
			select.setPurchaseorder_shiplocation((String) result[11]);
			select.setPurchaseorder_shiplocation_address((String) result[12]+", "+(String) result[41]+", "+(String) result[42]+", "+(String) result[43]+", "+(String) result[44]+". Pin :"+(String) result[45] );
			select.setPurchaseorder_shiplocation_contact((String) result[13]);
			select.setPurchaseorder_shiplocation_mobile((String) result[14]);
			select.setPurchaseorder_quotenumber((String) result[15]);
			select.setPurchaseorder_workordernumber((String) result[16]);
			select.setPurchaseorder_indentno((String) result[17]);
			select.setPurchaseorder_notes((String) result[18]);
			select.setPurchaseorder_statusId((short) result[19]);
			select.setPurchaseorder_subtotal_cost((Double) result[20]);
			select.setPurchaseorder_totaltax_cost((Double) result[21]);
			select.setPurchaseorder_freight((Double) result[22]);
			select.setPurchaseorder_totalcost((Double) result[23]);
			select.setPurchaseorder_advancecost((Double) result[24]);
			select.setPurchaseorder_balancecost((Double) result[25]);
			select.setPurchaseorder_orderd_remark((String) result[26]);
			select.setPurchaseorder_orderddate((Date) result[27]);
			select.setPurchaseorder_received_remark((String) result[28]);
			select.setPurchaseorder_received_date((Date) result[29]);
			select.setPurchaseorder_invoiceno((String) result[30]);
			select.setPurchaseorder_invoice_date_On((Date) result[31]);
			select.setPurchaseorder_total_debitnote_cost((Double) result[32]);
			select.setPurchaseorder_complete_date((Date) result[33]);
			select.setPurchaseorder_document((boolean) result[34]);
			select.setCreatedOn((Date) result[35]);
			select.setLastupdatedOn((Date) result[36]);
			select.setPurchaseorder_orderdbyId((Long) result[37]);
			select.setPurchaseorder_receivedbyId((Long) result[38]);
			select.setCreatedById((Long) result[39]);
			select.setLastModifiedById((Long) result[40]);

			select.setPurchaseorder_buyer((String) result[46]);
			select.setPurchaseorder_buyeraddress((String) result[47]+" , "+ (String) result[48]+"  , "+(String) result[49]+" , "+(String) result[50]+" . PIN- "+(String) result[51]);
			

		} else {
			return null;
		}

		return select;
	}

	@Transactional
	public void updatePurchaseOrder_RE_Enter(PurchaseOrders Update_Complete) throws Exception {

		entityManager.createQuery("UPDATE PurchaseOrders SET  purchaseorder_statusId = "
				+ Update_Complete.getPurchaseorder_statusId() + ", lastModifiedById="
				+ Update_Complete.getLastModifiedById() + ", Lastupdated = '" + Update_Complete.getLastupdated()
				+ "'  where Purchaseorder_id=" + Update_Complete.getPurchaseorder_id()).executeUpdate();
	}

	@Override
	public HashMap<String, Object> getPurchaseOrderDetailsHM(Long purchaseOrder_id, CustomUserDetails userDetails)
			throws Exception {
		Thread purchaseOrderThread = null;
		Thread userThread = null;
		HashMap<String, Object> valueOutObject;

		try {
			valueOutObject = new HashMap<String, Object>();

			purchaseOrderThread = new Thread() {
				PurchaseOrdersDto purchaseOrders = null;

				public void run() {
					try {
						purchaseOrders = getPurchaseOrders(purchaseOrder_id, userDetails);
						valueOutObject.put("purchaseOrders", purchaseOrders);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						purchaseOrders = null;
					}
				}
			};
			purchaseOrderThread.start();
			userThread = new Thread() {
				public void run() {
					try {
						valueOutObject.put("userMap", userService.getUserListHM(userDetails.getCompany_id()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};

			userThread.start();
			purchaseOrderThread.join();
			userThread.join();

			return valueOutObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public HashMap<String, Object> getPurchaseOrderDetailsHMByNumber(Long purchaseOrder_id, CustomUserDetails userDetails)
			throws Exception {
		Thread purchaseOrderThread = null;
		Thread userThread = null;
		HashMap<String, Object> valueOutObject;

		try {
			valueOutObject = new HashMap<String, Object>();

			purchaseOrderThread = new Thread() {
				PurchaseOrdersDto purchaseOrders = null;

				public void run() {
					try {
						purchaseOrders = getPurchaseOrdersByNumber(purchaseOrder_id, userDetails);
						valueOutObject.put("purchaseOrders", purchaseOrders);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						purchaseOrders = null;
					}
				}
			};
			purchaseOrderThread.start();
			userThread = new Thread() {
				public void run() {
					try {
						valueOutObject.put("userMap", userService.getUserListHM(userDetails.getCompany_id()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};

			userThread.start();
			purchaseOrderThread.join();
			userThread.join();

			return valueOutObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	@Transactional
	public List<PurchaseOrdersToPartsDto> ReportPart_PurchaseOrdersPartsToPurchaseOrdersId(short PartType_ID, String Query,
			String dateRangeFrom, String dateRangeTo, Integer companyId) {

		TypedQuery<Object[]> query = entityManager.createQuery(
				" SELECT R.purchaseorderto_partid, R.partid, MP.partname, MP.partnumber, MP.partTypeId, "
						+ " R.quantity, R.received_quantity, R.received_quantity_remark, R.notreceived_quantity, PMU.pmuName,"
						+ " R.discount, R.tax, R.totalcost, R.inventory_all_id, R.inventory_all_quantity, R.parteachcost, PO.purchaseorder_id, PO.purchaseorder_Number "
						+ " FROM PurchaseOrdersToParts R " 
						+ " LEFT JOIN MasterParts MP ON MP.partid = R.partid "
						+ " INNER JOIN PurchaseOrders PO ON PO.purchaseorder_id = R.purchaseorders.purchaseorder_id"
						+ " LEFT JOIN PartMeasurementUnit PMU ON PMU.pmuid = R.unittypeId"
						+ " WHERE PO.purchaseorder_typeId = " + PartType_ID + " AND (PO.purchaseorder_created_on BETWEEN '"
						+ dateRangeFrom + "' AND  '" + dateRangeTo + "') AND PO.companyId =" + companyId + " " + Query
						+ " AND PO.markForDelete = 0 AND R.companyId = " + companyId + " AND R.markForDelete = 0",
				Object[].class);

		List<Object[]> results = query.getResultList();

		List<PurchaseOrdersToPartsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersToPartsDto>();
			PurchaseOrdersToPartsDto list = null;
			for (Object[] result : results) {
				list = new PurchaseOrdersToPartsDto();

				list.setPurchaseorderto_partid((Long) result[0]);
				list.setPartid((Long) result[1]);
				list.setPurchaseorder_partname((String) result[2]);
				list.setPurchaseorder_partnumber((String) result[3]);
				list.setParttype(PartType.getPartTypeName((short) result[4]));
				list.setQuantity((Double) result[5]);
				list.setReceived_quantity((Double) result[6]);
				list.setReceived_quantity_remark((String) result[7]);
				list.setNotreceived_quantity((Double) result[8]);
				list.setUnittype((String) result[9]);
				list.setDiscount((Double) result[10]);
				list.setTax((Double) result[11]);
				list.setTotalcost((Double) result[12]);
				list.setInventory_all_id((Long) result[13]);
				list.setInventory_all_quantity((Double) result[14]);
				list.setParteachcost((Double) result[15]);
				list.setPurchaseorder_id((Long) result[16]);
				list.setPurchaseorder_Number((Long) result[17]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Override
	@Transactional
	public List<PurchaseOrdersToPartsDto> ReportTyre_PurchaseOrdersPartsToPurchaseOrdersId(short PartType_ID, String Query,
			String dateRangeFrom, String dateRangeTo, Integer companyId) {

		TypedQuery<Object[]> query = entityManager.createQuery(
				" SELECT R.purchaseorderto_partid, TM.TYRE_MODEL,"
						+ " R.TYRE_MANUFACTURER_ID, TSM.TYRE_MODEL_SUBTYPE, R.TYRE_MODEL_ID, VS.TYRE_SIZE, R.TYRE_SIZE_ID, R.INVENTORY_TYRE_QUANTITY, R.INVENTORY_TYRE_NEW_RT,"
						+ " R.quantity, R.received_quantity, R.received_quantity_remark, R.notreceived_quantity, PMU.pmuName,"
						+ " R.discount, R.tax, R.totalcost, R.inventory_all_id, R.inventory_all_quantity, R.parteachcost, PO.purchaseorder_id, PO.purchaseorder_Number "
						+ " FROM PurchaseOrdersToParts R "
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = R.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = R.TYRE_MODEL_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID "
						+ " LEFT JOIN PartMeasurementUnit PMU ON PMU.pmuid = R.unittypeId"
						+ " INNER JOIN PurchaseOrders PO ON PO.purchaseorder_id = R.purchaseorders.purchaseorder_id"
						+ " WHERE PO.purchaseorder_typeId = " + PartType_ID + " AND (PO.purchaseorder_created_on BETWEEN '"
						+ dateRangeFrom + "' AND  '" + dateRangeTo + "') AND PO.companyId =" + companyId + " " + Query
						+ " AND PO.markForDelete = 0 AND R.companyId = " + companyId + " AND R.markForDelete = 0",
				Object[].class);

		List<Object[]> results = query.getResultList();

		List<PurchaseOrdersToPartsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersToPartsDto>();
			PurchaseOrdersToPartsDto list = null;
			for (Object[] result : results) {
				list = new PurchaseOrdersToPartsDto();

				list.setPurchaseorderto_partid((Long) result[0]);
				list.setTYRE_MANUFACTURER((String) result[1]);
				list.setTYRE_MANUFACTURER_ID((Integer) result[2]);
				list.setTYRE_MODEL((String) result[3]);
				list.setTYRE_MODEL_ID((Integer) result[4]);
				list.setTYRE_SIZE((String) result[5]);
				list.setTYRE_SIZE_ID((Integer) result[6]);
				list.setINVENTORY_TYRE_QUANTITY((Integer) result[7]);
				list.setINVENTORY_TYRE_NEW_RT((String) result[8]);
				list.setQuantity((Double) result[9]);
				list.setReceived_quantity((Double) result[10]);
				list.setReceived_quantity_remark((String) result[11]);
				list.setNotreceived_quantity((Double) result[12]);
				list.setUnittype((String) result[13]);
				list.setDiscount((Double) result[14]);
				list.setTax((Double) result[15]);
				list.setTotalcost((Double) result[16]);
				list.setInventory_all_id((Long) result[17]);
				list.setInventory_all_quantity((Double) result[18]);
				list.setParteachcost((Double) result[19]);
				list.setPurchaseorder_id((Long) result[20]);
				list.setPurchaseorder_Number((Long) result[21]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Override
	public void transferPurchaseOrderDocument(List<PurchaseOrdersDocument> list) throws Exception {
		org.fleetopgroup.persistence.document.PurchaseOrdersDocument			purchaseOrdersDocument		= null;
		List<org.fleetopgroup.persistence.document.PurchaseOrdersDocument> 		purchaseOrdersDocumentList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				purchaseOrdersDocumentList	= new ArrayList<>();
				for(PurchaseOrdersDocument	document : list) {
					purchaseOrdersDocument	= new org.fleetopgroup.persistence.document.PurchaseOrdersDocument();
					
					purchaseOrdersDocument.set_id(document.getPurchaseorder_documentid());
					purchaseOrdersDocument.setPurchaseorder_id(document.getPurchaseorder_id());
					purchaseOrdersDocument.setPurchaseorder_document(document.getPurchaseorder_document());
					purchaseOrdersDocument.setPurchaseorder_content(document.getPurchaseorder_content());
					purchaseOrdersDocument.setPurchaseorder_contentType(document.getPurchaseorder_contentType());
					purchaseOrdersDocument.setPurchaseorder_filename(document.getPurchaseorder_filename());
					purchaseOrdersDocument.setCreatedById(document.getCreatedById());
					purchaseOrdersDocument.setLastModifiedById(document.getLastModifiedById());
					purchaseOrdersDocument.setCreated(document.getCreated());
					purchaseOrdersDocument.setLastupdated(document.getLastupdated());
					purchaseOrdersDocument.setCompanyId(document.getCompanyId());
					purchaseOrdersDocument.setMarkForDelete(document.isMarkForDelete());
					
					purchaseOrdersDocumentList.add(purchaseOrdersDocument);
				}
				System.err.println("Saving purchaseOrdersDocument ....");
				mongoTemplate.insert(purchaseOrdersDocumentList, org.fleetopgroup.persistence.document.PurchaseOrdersDocument.class);
				System.err.println("Saved Successfully....");
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}
	
	@Override
	public long getPurchaseOrdersDocumentMaxId() throws Exception {
		try {
			return PurchaseOrdersDocumentRepository.getPurchaseOrdersDocumentMaxId();
		} catch (Exception e) {
			return 0;
		}
	}
	
	
	
	
	

@Transactional
public void update_PurchaseOrderApprovalAmount(Object partInvoiceId, Object recievedAmount, Object balanceAmt,Object discountAmt ,short vendorPaymentStatusId, long approvalId, Date date)
		throws Exception {
	CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
			.getPrincipal();
	
	try {
		
		entityManager.createQuery(
				"UPDATE PurchaseOrders SET paidAmount= "+recievedAmount+ ", balanceAmount= "+balanceAmt+",discountAmount="+discountAmt+", purchaseorder_vendor_approvalID = " +approvalId + ","
						+ " purchaseorder_vendor_paymentdate = '"+DateTimeUtility.getCurrentTimeStamp()+"', lastModifiedById = "+userDetails.getId()+", "
						+ " purchaseorder_vendor_paymodeId= " + vendorPaymentStatusId +" " 
						+ " WHERE purchaseorder_id = "+partInvoiceId+" AND companyId = "+userDetails.getCompany_id()+" "
						+ " AND markForDelete = 0 ")
		.executeUpdate();
		
	} catch (Exception e) {
		throw e;
	}

}

@Override
public PurchaseOrders getPurchaseOrderDeatils(Long purchaseOrderNumber, Integer companyId) throws Exception {
	TypedQuery<PurchaseOrders> query = entityManager.createQuery("FROM PurchaseOrders WHERE purchaseorder_Number = '"+purchaseOrderNumber+"' "
			+ " AND companyId = "+companyId+" AND markForDelete = 0",PurchaseOrders.class);
	return query.getSingleResult();
}
	
@Transactional
public void updatePaymentApprovedPurchaseOrderDetails(Long ApprovalInvoice_ID,
		Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId) throws Exception {
	entityManager.createQuery("  UPDATE PurchaseOrders SET purchaseorder_vendor_approvalID=" + Approval_ID
			+ ",expectedPaymentDate='" + expectedPaymentDate + "', purchaseorder_vendor_paymodeId='" + approval_Status + "' "
			+ " WHERE purchaseorder_id IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();
	}
@Transactional
public void updatePaymentPaidPurchaseOrderDetails(VendorSubApprovalDetails vendorSubApproval, Timestamp approval_date, Integer companyId, short purchaseorder_vendor_paymodeId) throws Exception {
	
	entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_vendor_paymentdate='" + approval_date+"', "
			+ " balanceAmount = 0, paidAmount = "+vendorSubApproval.getSubApprovalpaidAmount()+", discountAmount = " + (vendorSubApproval.getSubApprovalTotal()-vendorSubApproval.getSubApprovalpaidAmount())+" ,"
			+ " purchaseorder_vendor_paymodeId="+purchaseorder_vendor_paymodeId+"  WHERE purchaseorder_id ="+vendorSubApproval.getInvoiceId()+" AND companyId = "+companyId+" ").executeUpdate();
}

@Override
public ValueObject getPurchaseOrdersPartDetails(ValueObject valueObject) throws Exception {
	CustomUserDetails					userDetails						= null;
	List<PurchaseOrdersToPartsDto>		purchaseOrdersToPartsList		= null;
	try {
		userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		purchaseOrdersToPartsList 	= purchaseOrdersToPartsService.getPurchaseOrdersPartDetailsByType(valueObject,userDetails);
		
		valueObject.put("ureaPurchaseOrdersDetails", purchaseOrdersToPartsList);
		
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}finally {
		userDetails						= null; 
		purchaseOrdersToPartsList		= null;   
	}
}
@Transactional
@Override
public ValueObject savePurchaseOrderPart(ValueObject valueObject) throws Exception {
	CustomUserDetails					userDetails						= null;
	PurchaseOrders						purchaseOrders					= null;
	double								purchaseOrderPartTotalCost		= 0;
	double 								BalanceAmount 					= 0;
	double 								purchaseOrderTotalCost 			= 0;
	double 								purchaseOrderSubTotalCost 		= 0;
	double 								purchaseOrderBalanceCost 		= 0;
	java.util.Date 						currentDateUpdate 				= null;
	Timestamp 							toDate 							= null;
	HashMap<String, Object> 			configuration	        		= null;
	boolean					 			totalGstCostConfig	      		= false;
	double 								BalanceCost 					= 0;
	double 								subTotalCost 					= 0;
	double 								totalCost 						= 0;
	
	try {
		
		currentDateUpdate 			= new java.util.Date();
		toDate 						= new java.sql.Timestamp(currentDateUpdate.getTime());
		userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		configuration				= CompanyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
		totalGstCostConfig			= (boolean) configuration.getOrDefault(PurchaseOrdersConfigurationConstant.TOTAL_GST_COST, false);
		purchaseOrderPartTotalCost 	= valueObject.getDouble("totalCost",0);
		purchaseOrders				= PurchaseOrdersDao.getPurchaseOrders(valueObject.getLong("purchaseOrderId"), userDetails.getCompany_id());
		valueObject.put("totalGstCostConfig", totalGstCostConfig);
		valueObject.put("purchaseOrders", purchaseOrders);
		/*savePart*/
		purchaseOrdersToPartsService.savePurchaseOrderPartDetails(valueObject, userDetails);
		
		/*updateAmountInPurchaseOrder*/
		if(purchaseOrders != null) {
			if(purchaseOrders.getBalanceAmount() == null) {
				BalanceAmount 			= BalanceAmount+purchaseOrderPartTotalCost;
			}else {
				BalanceAmount 			= purchaseOrders.getBalanceAmount()+purchaseOrderPartTotalCost;
			}
			
			if(purchaseOrders.getPurchaseorder_totalcost() == null) {
				totalCost				= 0;
			}else {
				totalCost				= purchaseOrders.getPurchaseorder_totalcost();
			}
			if(purchaseOrders.getPurchaseorder_subtotal_cost() == null) {
				subTotalCost			= 0;
			}else {
				subTotalCost			= purchaseOrders.getPurchaseorder_subtotal_cost();
			}
			if(purchaseOrders.getPurchaseorder_balancecost() == null) {
				BalanceCost			= 0;
			}else {
				BalanceCost			= purchaseOrders.getPurchaseorder_balancecost();
			}
			
			
			purchaseOrderTotalCost 		=	totalCost+purchaseOrderPartTotalCost;
			purchaseOrderSubTotalCost 	=	subTotalCost+purchaseOrderPartTotalCost;
			purchaseOrderBalanceCost 	=	BalanceCost+purchaseOrderPartTotalCost;
		
		}
		
		PurchaseOrdersDao.UpdatePurchaseOrderCost(BalanceAmount, purchaseOrderTotalCost,purchaseOrderSubTotalCost,purchaseOrderBalanceCost,toDate, userDetails.getId(),valueObject.getLong("purchaseOrderId"), userDetails.getCompany_id());
		
		valueObject.put("save", true);
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}finally {
		userDetails						= null;           
		purchaseOrders					= null;           
		purchaseOrderPartTotalCost		= 0;              
		BalanceAmount 					= 0;              
		purchaseOrderTotalCost 			= 0;              
		purchaseOrderSubTotalCost 		= 0;              
		purchaseOrderBalanceCost 		= 0;              
		currentDateUpdate 				= null;           
		toDate 							= null;           
		configuration	        		= null;           
		totalGstCostConfig	      		= false;          
	}
}

@Transactional
@Override
public ValueObject deletePurchaseOrderPart(ValueObject valueObject) throws Exception {
	CustomUserDetails			userDetails						= null;
	PurchaseOrders				purchaseOrders					= null;
	double						purchaseOrderPartTotalCost		= 0;
	double 						BalanceAmount 					= 0;
	double 						purchaseOrderTotalCost 			= 0;
	double 						purchaseOrderSubTotalCost 		= 0;
	double 						purchaseOrderBalanceCost 		= 0;
	java.util.Date 				currentDateUpdate 				= null;
	Timestamp 					toDate 							= null;
	HashMap<String, Object> 	configuration	        		= null;
	boolean					 	totalGstCostConfig	      		= false;
	double						gstCost							= 0;
	double						poGstCost						= 0;
	double						poGstTotalCost					= 0;
	try {	
		currentDateUpdate 			= new java.util.Date();
		toDate 						= new java.sql.Timestamp(currentDateUpdate.getTime());
		userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		purchaseOrderPartTotalCost	= valueObject.getDouble("partTotalCost");
		configuration				= CompanyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
		totalGstCostConfig			= (boolean) configuration.getOrDefault(PurchaseOrdersConfigurationConstant.TOTAL_GST_COST, false);
		purchaseOrders				= PurchaseOrdersDao.getPurchaseOrders(valueObject.getLong("purchaseOrderId"), userDetails.getCompany_id());
		/*deletePart*/
		purchaseOrdersToPartsService.deletePurchaseOrderPartDetails(valueObject, userDetails);
		
		/*updateAmountInPurchaseOrder*/
		if(purchaseOrders != null) {
			BalanceAmount 				=	purchaseOrders.getBalanceAmount()-purchaseOrderPartTotalCost;
			purchaseOrderTotalCost 		=	purchaseOrders.getPurchaseorder_totalcost()-purchaseOrderPartTotalCost;
			purchaseOrderSubTotalCost 	=	purchaseOrders.getPurchaseorder_subtotal_cost()-purchaseOrderPartTotalCost;
			purchaseOrderBalanceCost 	=	purchaseOrders.getPurchaseorder_balancecost()-purchaseOrderPartTotalCost;
		}
		PurchaseOrdersDao.UpdatePurchaseOrderCost(BalanceAmount, purchaseOrderTotalCost,purchaseOrderSubTotalCost,purchaseOrderBalanceCost,toDate, userDetails.getId(),valueObject.getLong("purchaseOrderId"), userDetails.getCompany_id());
	
		if(purchaseOrders != null && totalGstCostConfig) {
			gstCost			= (valueObject.getDouble("quantity") * valueObject.getDouble("eachCost"))* (valueObject.getDouble("tax") / 100);
			poGstCost		= purchaseOrders.getPurchaseorder_totaltax_cost();
			poGstTotalCost	= poGstCost - gstCost ;
			valueObject.put("gstCost", poGstTotalCost);
			valueObject.put("companyId", userDetails.getCompany_id());
			
			updatePurchaseOrderVariousTotalCost(valueObject);
		}
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}finally {
		userDetails						= null;
	}
}

@Transactional
@Override
public ValueObject sentToPurchaseOrder(ValueObject valueObject) throws Exception {//common for all
	CustomUserDetails			userDetails						= null;
	PurchaseOrders				purchaseOrders					= null;
	double						purchaseOrderAdvanceCost		= 0;
	double 						BalanceAmount 					= 0;
	double 						purchaseOrderBalanceCost 		= 0;
	String 						purchaseOrderSentRemark 		= "";
	Timestamp					purchaseOrder_OrderDate			= null;
	Timestamp					lastUpdatedDate					= null;
	short						purchaseOrderStatusId			= 0;
	java.util.Date 				currentDateUpdate 				= null;
	
	
	try {
		currentDateUpdate 			= new java.util.Date();
		
		userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		purchaseOrders				= PurchaseOrdersDao.getPurchaseOrders(valueObject.getLong("purchaseOrderId"), userDetails.getCompany_id());
		
		BalanceAmount 				= purchaseOrders.getBalanceAmount() - valueObject.getDouble("purchaseOrderAdvanceCost",0);
		purchaseOrderAdvanceCost	= valueObject.getDouble("purchaseOrderAdvanceCost",0);
		purchaseOrderBalanceCost	= purchaseOrders.getPurchaseorder_balancecost() - valueObject.getDouble("purchaseOrderAdvanceCost",0);
		purchaseOrderSentRemark		= valueObject.getString("purchaseOrderRemark"); 
		purchaseOrder_OrderDate		= new java.sql.Timestamp(currentDateUpdate.getTime());
		lastUpdatedDate				= new java.sql.Timestamp(currentDateUpdate.getTime());
		purchaseOrderStatusId		= PurchaseOrderType.PURCHASEORDER_STATUS_ORDERED; 
		
		PurchaseOrdersDao.sentToPurchaseOrder(BalanceAmount,purchaseOrderAdvanceCost,purchaseOrderBalanceCost,purchaseOrderSentRemark,purchaseOrder_OrderDate,purchaseOrderStatusId,lastUpdatedDate,userDetails.getId(),userDetails.getId(),purchaseOrders.getPurchaseorder_id(),userDetails.getCompany_id());
		
		// mail code according to purchase type
		
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}finally {
		userDetails						= null;  

	}
}

@Transactional
@Override
public ValueObject receivedQuantityToPurchaseOrder(ValueObject valueObject) throws Exception {//common for all
	CustomUserDetails			userDetails						= null;
	double						orderedQuantity					= 0;
	double						noReceivedQuantity				= 0;
	double						receivedQuantity				= 0;
	String						receivedQuantityRemark			= "";
	long						partId							= 0;
	PurchaseOrders				purchase						= null;
	
	
	try {
		userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		receivedQuantity			= valueObject.getDouble("receivedQuantity",0);
		orderedQuantity				= valueObject.getDouble("orderedQuantity",0);
		noReceivedQuantity			= orderedQuantity-receivedQuantity;
		partId						= valueObject.getLong("partId");		
		receivedQuantityRemark		= valueObject.getString("receivedQuantityRemark");
		
		
		purchase				= purchaseOrdersService.getPurchaseOrders(valueObject.getLong("purchaseOrderId"));
	
		if(purchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED) {
			valueObject.put("alreadyReceived", true);
			return valueObject;
		}
		
		
		switch (valueObject.getShort("purchaseOrderType")) {
		case PurchaseOrderType.PURCHASEORDER_TYPE_UREA_PO:
			purchaseOrdersToUreaRepository.updateReceivedQuantityOfUrea(receivedQuantity,noReceivedQuantity,receivedQuantityRemark,partId,userDetails.getCompany_id());
			break;
		default:
			System.err.println("Default");
			break;
		}

		
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}finally {
		userDetails						= null;  

	}
}

@Transactional
@Override
public ValueObject reEnterPurchaseOrder(ValueObject valueObject) throws Exception {//common for all
	CustomUserDetails				userDetails						= null;
	PurchaseOrders					purchaseOrders					= null;
	double							purchaseOrderAdvanceCost		= 0;
	double 							BalanceAmount 					= 0;
	double 							purchaseOrderBalanceCost 		= 0;
	String 							purchaseOrderSentRemark 		= "";
	Timestamp						purchaseOrder_OrderDate			= null;
	Timestamp						lastUpdatedDate					= null;
	short							purchaseOrderStatusId			= 0;
	java.util.Date 					currentDateUpdate 				= null;
	
	
	try {
		currentDateUpdate 			= new java.util.Date();
		userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		purchaseOrders				= PurchaseOrdersDao.getPurchaseOrders(valueObject.getLong("purchaseOrderId"), userDetails.getCompany_id());
	
		/*PartTable changes*/
		purchaseOrdersToPartsService.updateReceivedQuantityOfPart(valueObject, userDetails);
		
		/*for purchaseOrder table changes*/
		BalanceAmount 				= purchaseOrders.getBalanceAmount() + purchaseOrders.getPurchaseorder_advancecost();
		purchaseOrderAdvanceCost	= 0;
		purchaseOrderBalanceCost	= purchaseOrders.getPurchaseorder_balancecost() + purchaseOrders.getPurchaseorder_advancecost();
		lastUpdatedDate				= new java.sql.Timestamp(currentDateUpdate.getTime());
		purchaseOrderStatusId		= PurchaseOrderType.PURCHASEORDER_STATUS_REQUISITION; 
		
		PurchaseOrdersDao.sentToPurchaseOrder(BalanceAmount,purchaseOrderAdvanceCost,purchaseOrderBalanceCost,purchaseOrderSentRemark,purchaseOrder_OrderDate,purchaseOrderStatusId,lastUpdatedDate,userDetails.getId(),userDetails.getId(),purchaseOrders.getPurchaseorder_id(),userDetails.getCompany_id());
		
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}finally {
		userDetails						= null;  

	}
}
@SuppressWarnings("unchecked")
@Transactional
@Override
public ValueObject receivedPartFromPurchaseOrder(ValueObject valueInObject) throws Exception {
	CustomUserDetails			userDetails						= null;
	java.util.Date 				currentDateUpdate 				= null;
	String 						purchaseorder_received_remark	= "";
	long						purchaseorder_receivedbyId		= 0;
	Date						purchaseorder_received_date		= null;
	Date						purchaseorder_invoice_date		= null;
	String						purchaseorder_invoiceno			= "";
	short						purchaseorder_statusId			= 0;
	long						lastModifiedById				= 0;
	Date						lastupdated						= null;
	ArrayList<ValueObject> 	 	dataArrayObjColl 				= null;
	PurchaseOrdersToDebitNote	debitNote						= null;
	double						totalReturnCost					= 0;
	MultipartFile				file							= null;
	PurchaseOrders				purchase						= null;
	

	try {
		userDetails						 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		currentDateUpdate 				 = new java.util.Date();
		purchaseorder_received_remark	 = valueInObject.getString("description","");
		purchaseorder_receivedbyId		 = userDetails.getId();
		purchaseorder_received_date		 = currentDateUpdate;
		purchaseorder_invoice_date		 = DateTimeUtility.getDateTimeFromStr(valueInObject.getString("invoiceDate"), DateTimeUtility.DD_MM_YYYY);
		purchaseorder_invoiceno			 = valueInObject.getString("invoiceNumber");
		purchaseorder_statusId			 = PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED;
		lastModifiedById				 = userDetails.getId();
		lastupdated						 = currentDateUpdate;
							
		
		purchase				= purchaseOrdersService.getPurchaseOrders(valueInObject.getLong("purchaseOrderId"));
		
		if(purchase.getPurchaseorder_statusId() == PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED) {
			valueInObject.put("alreadyReceived", true);
			return valueInObject;
		}
		
		switch (valueInObject.getShort("purchaseOrderType")) {
		case PurchaseOrderType.PURCHASEORDER_TYPE_UREA_PO:
			valueInObject.put("createdFromPO", true);
			valueInObject.put("paymentType", PaymentTypeConstant.PAYMENT_TYPE_PO);
			ureaInventoryService.saveUreaInventoryDetails(valueInObject,file);
		//	ureaInvoiceToDetailsService.updateUreaInvoiceToDetailsQuantity(valueInObject);// shift
			break;

		default:
			System.err.println("default");
			break;
		}
		
		dataArrayObjColl	= (ArrayList<ValueObject>) valueInObject.get("ureaDetails");
		if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
			for (ValueObject object : dataArrayObjColl) {
				if (object.getDouble("noReceivedQuantity",0) > 0 ) {
					debitNote  		 = POBL.preparedPurchaseOrdersToDebitNote(object,userDetails);
					if(debitNote != null) {
					totalReturnCost += debitNote.getTotal_return_cost();
					addPurchaseOrdersToDebitNote(debitNote);
					}
				}
			}
		}
		
		PurchaseOrdersDao.updateReceivedPartPurchaseOrder(purchaseorder_received_remark,purchaseorder_receivedbyId,purchaseorder_received_date,purchaseorder_invoice_date,purchaseorder_invoiceno,purchaseorder_statusId,lastModifiedById,lastupdated,totalReturnCost,valueInObject.getLong("purchaseOrderId"),userDetails.getCompany_id());
		
		return valueInObject;
		
	} catch (Exception e) {
		LOGGER.error("err"+e);
		throw e;
		
	}
	
}

@SuppressWarnings("unchecked")
@Transactional
@Override
public ValueObject completePurchaseOrder(ValueObject valueInObject) throws Exception {
	CustomUserDetails					userDetails					= null;
	List<PartLocationPermissionDto> 	partLocationPermission 		= null;
	HashMap<String, Object>  			threadHM					= null;
	PurchaseOrdersDto 					purchase					= null;

	try {
		userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		partLocationPermission 	= PLBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));
		threadHM			    = getPurchaseOrderDetailsHM(valueInObject.getLong("purchaseOrderId"), userDetails);
		purchase 				= POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
		
		// check user branch location and workOrder location if same
		if (InvenBL.isFilled_GET_Permission(purchase.getPurchaseorder_shiplocation_id(), partLocationPermission)) {
			PurchaseOrders Purchase_complete = POBL.Update_PurchaseOrder_Completed(purchase);
			updatePurchaseOrder_Complete(Purchase_complete);
			
			pendingVendorPaymentService.deletePendingVendorPayment(purchase.getPurchaseorder_id(), PendingPaymentType.PAYMENT_TYPE_PURCHASE_ORDER);
			if(purchase.getPurchaseorder_termsId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
				  PendingVendorPayment	payment	=	PendingVendorPaymentBL.createPendingVendorPaymentDTOForPO(purchase);
				  payment.setTransactionAmount(Purchase_complete.getPurchaseorder_balancecost());
				  payment.setBalanceAmount(Purchase_complete.getPurchaseorder_balancecost());
				  pendingVendorPaymentService.savePendingVendorPayment(payment);
			}
		} 
		return valueInObject;

	} catch (Exception e) {
		LOGGER.error("err"+e);
		throw e;

	}

}

@Override
public ValueObject getPurchaseOrdersDebitNoteDetails(ValueObject valueInObject) throws Exception {
	CustomUserDetails					userDetails							= null;
	List<PurchaseOrdersToDebitNoteDto>  purchaseOrdersToDebitNoteDtoList	= null;

	try {
		userDetails 						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		purchaseOrdersToDebitNoteDtoList 	= purchaseOrdersToPartsService.getPurchaseOrdersDebitNoteDetailsByPurchaseType(valueInObject,userDetails);

		valueInObject.put("purchaseOrderDebitDetails", purchaseOrdersToDebitNoteDtoList);
		return valueInObject;
	} catch (Exception e) {
		LOGGER.error("err"+e);
		throw e;

	}

}

@Transactional
@SuppressWarnings("unchecked")
@Override
public ValueObject deleteReturnPurchaseOrdersDetails(ValueObject valueInObject) throws Exception {
	CustomUserDetails					userDetails							= null;
	List<PartLocationPermissionDto>	 	PartLocPermission 					= null; 
	HashMap<String, Object>  			threadHM							= null;
	PurchaseOrdersDto 					Purchase 							= null;
	List<Inventory> 					validateOldRecevied 				= null;
	PurchaseOrdersToDebitNote 			DebitNote 							= null;
	PurchaseOrders 						Purchase_complete					= null;
	
	try {
		userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PartLocPermission 	= PLBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));
		threadHM			= getPurchaseOrderDetailsHM(valueInObject.getLong("purchaseOrderId"), userDetails);
		Purchase 			= POBL.getPurchaseOrders((PurchaseOrdersDto)threadHM.get("purchaseOrders"), (HashMap<Long, User>) threadHM.get("userMap"));
		
		if (InvenBL.isFilled_GET_Permission(Purchase.getPurchaseorder_shiplocation_id(), PartLocPermission)) {
			validateOldRecevied =  InventoryService.Get_Validate_InventoryPurchaseOrderId_Details( "" + valueInObject.getLong("purchaseOrderId"), userDetails.getCompany_id());
			
			if (validateOldRecevied != null && !validateOldRecevied.isEmpty()) {
				
			}else {
				DebitNote 			= Get_PurchaseOrdersTasks_ToDebitNoteID(valueInObject.getLong("debitNoteId"), userDetails.getCompany_id());
				Purchase_complete 	= POBL.Update_PurchaseOrder_DeleteReturnParts(DebitNote);
				
				Update_PurchaseOrder_Delete_DebitNote_Amount(Purchase_complete);
				Delete_PurchaseOrders_ToDebitNoteID(valueInObject.getLong("debitNoteId"),userDetails.getCompany_id());
			}
		}	
		
		return valueInObject;
		
	} catch (Exception e) {
		LOGGER.error("Err"+e);
		throw e;
	}
}
	
	@Override
	public List<VendorPaymentNotPaidDto> vendorPaymentNotPaidPOList(Integer vendor_id, String dateFrom, String dateTo, Integer companyId) {
		
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT p.purchaseorder_id, p.purchaseorder_Number, VN.vendorName, p.purchaseorder_invoiceno, "
						+ " p.purchaseorder_invoice_date, p.purchaseorder_termsId, p.purchaseorder_totalcost, p.balanceAmount "
						+ " From PurchaseOrders as p "
						+ " LEFT JOIN Vendor VN ON VN.vendorId = p.purchaseorder_vendor_id "
						+ " where ((p.purchaseorder_created_on BETWEEN '" + dateFrom + "' AND  '" + dateTo + "') "
						+ " AND (p.purchaseorder_vendor_paymodeId =" + FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID + " OR "
						+ " p.purchaseorder_vendor_paymodeId =" + FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID + ") ) "
						+ " AND p.companyId = " + companyId + " AND p.markForDelete = 0 "
						+ " AND p.purchaseorder_vendor_id= "+vendor_id+" ",
				Object[].class);
		List<Object[]> results = query.getResultList();
	
		List<VendorPaymentNotPaidDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VendorPaymentNotPaidDto>();
			VendorPaymentNotPaidDto list = null;
			for (Object[] result : results) {
				list = new VendorPaymentNotPaidDto();
	
				list.setSerialNumberId((Long) result[0]);
				list.setSerialNumber((Long) result[1]);
				list.setSerialNumberStr("PO-"+list.getSerialNumber());
				list.setVendorName((String) result[2]);
				
				if(result[5] != null) {
					list.setInvoiceNumber((String) result[3]);
				} else {
					list.setInvoiceNumber("-");
				}
				
				if(result[4] != null) {
					list.setInvoiceDateStr(dateFormat.format((java.util.Date) result[4]));
				} else {
					list.setInvoiceDateStr("-");
				}
				
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
				
	
				Dtos.add(list);
			}
		}
		return Dtos;
	
	}
	
	@Override
	@Transactional
	public ValueObject updateVendorDetailsInPurchaseOrder(ValueObject valueInObject) throws Exception {
		CustomUserDetails					userDetails							= null;
		try {
			userDetails 						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(valueInObject.getInt("vendorId") < 0 ||  valueInObject.getLong("purchaseOrderId") < 0) {
				valueInObject.put("validate", true);
				return valueInObject;
			}
			
			PurchaseOrdersDao.updateVendorDetailsInPurchaseOrder(valueInObject.getInt("vendorId"), valueInObject.getLong("purchaseOrderId"), userDetails.getCompany_id() );
			valueInObject.put("save", true);
			return valueInObject;
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;

		}

	}
	
	@Override
	@Transactional
	public ValueObject updateTallyCompaanyDetailsInPurchaseOrder(ValueObject valueInObject) throws Exception {
		CustomUserDetails					userDetails							= null;
		try {
			userDetails 						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(valueInObject.getLong("tallyCompanyId") <= 0) {
				valueInObject.put("invalidId", true);
				return valueInObject;
			}
			
			PurchaseOrdersDao.updateTallyCompanyDetailsInPurchaseOrder(valueInObject.getLong("tallyCompanyId"), valueInObject.getLong("purchaseOrderId"), userDetails.getCompany_id());
			valueInObject.put("tallyUpdated", true);
			
			return valueInObject;
			
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> getPurchaseListForTallyImport(String fromDate, String toDate, Integer companyId,
			String tallyCompany) throws Exception {
		try {
			
			SimpleDateFormat sqlDateFormat 	= new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat tallyFormat		= new SimpleDateFormat("yyyyMMdd");
			
			Query query = entityManager.createQuery(
					"SELECT SE.purchaseorder_id, SE.purchaseorder_Number, VA.approvalCreateDate, V.vendorName,"
					+ " TC.companyName, SE.created, SE.purchaseorder_termsId, VSD.subApprovalpaidAmount, SE.isPendingForTally, "
					+ " SE.purchaseorder_invoice_date, SE.purchaseorder_invoiceno"
					+ " FROM PurchaseOrders SE "
					+ " INNER JOIN Vendor AS V ON V.vendorId = SE.purchaseorder_vendor_id"
					+ " INNER JOIN VendorApproval VA on VA.approvalId = SE.purchaseorder_vendor_approvalID AND VA.markForDelete = 0"
					+ " INNER JOIN VendorSubApprovalDetails VSD ON VSD.approvalId = VA.approvalId AND VSD.invoiceId = SE.purchaseorder_id AND VSD.approvalPlaceId = "+ApprovalType.APPROVAL_TYPE_PURCHASE_ORDER+""
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
				Dtos = new ArrayList<>();
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
						
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_PURCHASE_ORDER);
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
						
						 select.setRemark("Invoice Date: "+sqlDateFormat.format((java.util.Date)vehicle[9])+" Invoice Number : "
						 		+ " "+(String)vehicle[10]+" From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						select.setTripSheetNumberStr("PO-"+select.getTripSheetNumber());
						
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
	public List<TripSheetExpenseDto> getPurchaseListForTallyImportATC(String fromDate, String toDate, Integer companyId,
			String tallyCompany) throws Exception {
		try {
			
			SimpleDateFormat sqlDateFormat 	= new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat tallyFormat		= new SimpleDateFormat("yyyyMMdd");
			
			Query query = entityManager.createQuery(
					"SELECT SE.purchaseorder_id, SE.purchaseorder_Number, SE.purchaseorder_invoice_date, V.vendorName,"
					+ " SE.created, SE.purchaseorder_termsId, SE.purchaseorder_totalcost, SE.isPendingForTally, "
					+ " SE.purchaseorder_invoice_date, SE.purchaseorder_invoiceno"
					+ " FROM PurchaseOrders SE "
					+ " INNER JOIN Vendor AS V ON V.vendorId = SE.purchaseorder_vendor_id"
					+ " WHERE SE.purchaseorder_invoice_date between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND SE.companyId = "+companyId+" AND SE.purchaseorder_statusId = "+PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED+" AND SE.markForDelete = 0 AND SE.purchaseorder_totalcost > 0");
			
			List<Object[]> results = null;
			try {
				results = query.getResultList();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			List<TripSheetExpenseDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<>();
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
						
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_PURCHASE_ORDER);
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
						
						 select.setRemark("Invoice Date: "+sqlDateFormat.format((java.util.Date)vehicle[8])+" Invoice Number : "
						 		+ " "+(String)vehicle[9]+" From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						select.setTripSheetNumberStr("PO-"+select.getTripSheetNumber());
						
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
	@Transactional
	public ValueObject updatePartPOStatusFromReceivedToOrdered(ValueObject object) throws Exception {
		CustomUserDetails					userDetails						= null;
		List<Inventory>						inven							= null;
		boolean								partAssign						= false;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			inven       = InventoryRepository.getPartInventoryDetailsFromPartPurchaseOrder(object.getLong("purchaseOrderId"), userDetails.getCompany_id());
			
			PartInvoice invoiceValidate = PartInvoiceRepository.getPartInvoiceDetailsFromPurchaseOrder(object.getLong("purchaseOrderId"), userDetails.getCompany_id());
			partAssign  = invoiceValidate.isAnyPartNumberAsign();
			
			if(partAssign) {
				object.put("partAlreadyUsed", true);
				return object;
			}
			
			if(inven != null) {
				
				for(Inventory inventory : inven) {
					
					InventoryDto getOLdInventory = InvenBL.prepareInventoryDto(InventoryService.getInventory(inventory.getInventory_id()));
					List<PartLocationPermissionDto> PartLocPermission = PLBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));
					
					if (getOLdInventory != null) {

						if (InvenBL.isFilled_GET_Permission(getOLdInventory.getLocationId(), PartLocPermission)) {

							HashMap<String, Object> configuration = CompanyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);

							InventoryService.deleteInventory(inventory.getInventory_id(), userDetails.getCompany_id());

							InventoryService.update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(getOLdInventory.getPartid(), getOLdInventory.getLocationId());

							InventoryService.update_InventoryAll_PARTID_PARTNAME_GENRAL(getOLdInventory.getPartid());
							
							PartInvoiceRepository.delete_PartInvoice(invoiceValidate.getPartInvoiceId(), userDetails.getCompany_id());
							
							object.put("deleteInventory", true);
							
							if((boolean) configuration.get("showRefreshmentOption")) {
								MasterParts	masterParts	=	MasterPartsService.getMasterParts(getOLdInventory.getPartid());
								if(masterParts != null && masterParts.isRefreshment()) {
									ValueObject	valueObject	= new ValueObject();
									if(getOLdInventory.getInvoiceDate() != null) {
										valueObject.put("transactionDate", getOLdInventory.getInvoiceDate());
									}else {
										valueObject.put("transactionDate", DateTimeUtility.getCurrentDate());
									}
									
									valueObject.put("partId", getOLdInventory.getPartid());
									valueObject.put("locationId",  getOLdInventory.getLocationId());
									valueObject.put("quantity", - getOLdInventory.getQuantity());
									valueObject.put("companyId", userDetails.getCompany_id());
									valueObject.put("addedQuantity", getOLdInventory.getQuantity());
									valueObject.put("usedQuantity", 0.0);
									valueObject.put("numberType", "I-"+ getOLdInventory.getInventory_Number()+" Part Delete");
									valueObject.put("transactionId", getOLdInventory.getInventory_id());
									valueObject.put("primaryType", InventoryStockTypeConstant.STOCK_TYPE_ADD);
									valueObject.put("transactionType", InventoryStockTypeConstant.STOCK_TYPE_DELETED);
									
									DayWiseInventoryStockService.processLocationWiseInventoryStockDetails(valueObject);	
								}
							}
							
							PurchaseOrdersToPartsRepository.updateFromReceivedToOrderedStatus(object.getLong("purchaseOrderId"), userDetails.getCompany_id());
							
							PurchaseOrdersDao.updatePurchaseOrderInvoiceNumberAndStatus("0", PurchaseOrderState.ORDERED_ID, 
									userDetails.getId(), new java.util.Date(), object.getLong("purchaseOrderId"), userDetails.getCompany_id());
							
							object.put("poUpdated", true);
							
						} else {
							object.put("NoAuthen", true);
						}
					}
					
				}
				
			}
			
			deleteDebitNoteEntryFromReceivedToOrderedStatus(object);
			
			return object;
			
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;

		}

	}
	
	public void deleteDebitNoteEntryFromReceivedToOrderedStatus(ValueObject object) throws Exception{
		CustomUserDetails					userDetails						= null;
		List<PurchaseOrdersToDebitNote>		debitNote						= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			debitNote   = PurchaseOrdersToDebitNoteRepository.getPurchaseOrdersTasksToDebitNote(object.getLong("purchaseOrderId"), userDetails.getCompany_id());
			
			if(debitNote != null && !debitNote.isEmpty()) {
				
				for(PurchaseOrdersToDebitNote debit : debitNote) {
					PurchaseOrdersToDebitNoteRepository.Delete_PurchaseOrders_ToDebitNoteID(debit.getPurchaseorderto_debitnoteid(), userDetails.getCompany_id());
				}
				
				PurchaseOrdersDao.updatePurchaseOrderDetailsFromDebitNote("0", PurchaseOrderState.ORDERED_ID, 
						userDetails.getId(), new java.util.Date(), object.getLong("purchaseOrderId"), userDetails.getCompany_id());
				
				object.put("poUpdated", true);
				
			}
			
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;
		}
	}
	
	
	@Override
	@Transactional
	public ValueObject updateBatteryPOStatusFromReceivedToOrdered(ValueObject object) throws Exception {
		CustomUserDetails					userDetails						= null;
		BatteryInvoice						batteryInven					= null;
		long								batteryInvoiceId				= 0;
		List<Battery>						battery							= null;
		try {
			userDetails  = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			batteryInven = BatteryInvoiceRepository.getBatteryInvoiceFromPurchaseOrder(object.getLong("purchaseOrderId"), userDetails.getCompany_id());
			
			if(batteryInven != null) {
				batteryInvoiceId = batteryInven.getBatteryInvoiceId();
				
				battery = BatteryRepository.getBatteryDetailsByInvoiceId(batteryInvoiceId, userDetails.getCompany_id());
				
				for(Battery bt : battery) {
					if(bt.getBatteryStatusId() == BatteryConstant.BATTERY_ASIGNED_STATUS_IN_SERVICE) {
						object.put("batteryAlreadyUsed", true);
						return object;
					}
				}
				
				BatteryRepository.deleteBatteryByInvoiceId(batteryInvoiceId, userDetails.getCompany_id());
				
				purchaseOrderToBatteryRepository.deletePurchaseOrderToBattery(object.getLong("purchaseOrderId"), userDetails.getCompany_id());
				
				BatteryAmountRepository.deleteBatteryAmountByInvoiceId(batteryInvoiceId, userDetails.getCompany_id());
					
				BatteryInvoiceRepository.deleteByBatteyInvoiceId(batteryInvoiceId, userDetails.getCompany_id());
				
				PurchaseOrdersToPartsRepository.updateFromReceivedToOrderedStatus(object.getLong("purchaseOrderId"), userDetails.getCompany_id());
				
				PurchaseOrdersDao.updatePurchaseOrderInvoiceNumberAndStatus("0", PurchaseOrderState.ORDERED_ID, 
						userDetails.getId(), new java.util.Date(), object.getLong("purchaseOrderId"), userDetails.getCompany_id());
				
				object.put("poUpdated", true);
			}
			
			deleteDebitNoteEntryFromReceivedToOrderedStatus(object);
			
			return object;
			
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;

		}

	}
	
	@Override
	@Transactional
	public ValueObject updateTyrePOStatusFromReceivedToOrdered(ValueObject object) throws Exception {
		CustomUserDetails					userDetails						= null;
		InventoryTyreInvoice				tyreInven						= null;
		long								tyreInvoiceId					= 0;
		List<InventoryTyre>					tyre							= null;
		try {
			userDetails  = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tyreInven = InventoryTyreInvoiceRepository.getTyreInvoiceFromPurchaseOrder(object.getString("purchaseOrderId"), userDetails.getCompany_id());
			
			if(tyreInven != null) {
				tyreInvoiceId = tyreInven.getITYRE_ID();
				
				tyre = InventoryTyreRepository.Get__List_InventoryTyre(tyreInvoiceId, userDetails.getCompany_id());
				
				for(InventoryTyre ty : tyre) {
					if(ty.getTYRE_ASSIGN_STATUS_ID() == InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE) {
						object.put("tyreAlreadyUsed", true);
						return object;
					}
				}
				
				InventoryTyreRepository.deleteTyreInventoryByInvoiceId(tyreInvoiceId, userDetails.getCompany_id());
				
				PurchaseOrdersToTyre_Repository.deletePurchaseOrdersToTyreByPOId(object.getLong("purchaseOrderId"), userDetails.getCompany_id());
				
				InventoryTyreAmountRepository.deleteTyreInventoryAmountByInvoiceId(tyreInvoiceId, userDetails.getCompany_id());
					
				InventoryTyreInvoiceRepository.delete_Tyre_Inventory_Invoice(tyreInvoiceId, userDetails.getCompany_id());
				
				PurchaseOrdersToPartsRepository.updateFromReceivedToOrderedStatus(object.getLong("purchaseOrderId"), userDetails.getCompany_id());
				
				PurchaseOrdersDao.updatePurchaseOrderInvoiceNumberAndStatus("0", PurchaseOrderState.ORDERED_ID, 
						userDetails.getId(), new java.util.Date(), object.getLong("purchaseOrderId"), userDetails.getCompany_id());
				
				object.put("poUpdated", true);
			}
			
			deleteDebitNoteEntryFromReceivedToOrderedStatus(object);
			
			return object;
			
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;

		}

	}
	
	@Override
	@Transactional
	public ValueObject updateUpholsteryPOStatusFromReceivedToOrdered(ValueObject object) throws Exception {
		CustomUserDetails					userDetails						= null;
		ClothInvoice						clothInven						= null;
		long								clothInvoiceId					= 0;
		List<ClothInventoryDetails>			inventoryDetails				= null;
		try {
			userDetails  = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			clothInven = ClothInventoryRepository.getClothInvoiceByPurchaseOrder(object.getString("purchaseOrderNumber"), userDetails.getCompany_id());
			
			if(clothInven != null) {
				clothInvoiceId = clothInven.getClothInvoiceId();
				
				inventoryDetails = ClothInventoryDetailsRepository.getClothInventoryDetailsList(clothInvoiceId);
				
				if(inventoryDetails != null) {
					
					for(ClothInventoryDetails invDet : inventoryDetails) {
						
						if(clothInven.getClothTypeId() == ClothInvoiceStockType.STOCK_TYPE_NEW) {
							entityManager.createQuery("Update ClothInventoryStockTypeDetails SET newStockQuantity = newStockQuantity - "+invDet.getQuantity()+" "
									+ " WHERE clothTypesId = "+invDet.getClothTypesId()+" AND wareHouseLocationId = "+invDet.getWareHouseLocation()+" AND companyId = "+invDet.getCompanyId()+" ").executeUpdate();
						}else {
							entityManager.createQuery("Update ClothInventoryStockTypeDetails SET usedStockQuantity = usedStockQuantity - "+invDet.getQuantity()+" "
									+ " WHERE clothTypesId = "+invDet.getClothTypesId()+" AND wareHouseLocationId = "+invDet.getWareHouseLocation()+" AND companyId = "+invDet.getCompanyId()+" ").executeUpdate();
						}
						
						ClothInventoryDetailsRepository.deleteClothInventoryDetails(invDet.getClothInventoryDetailsId());
					}
					
					entityManager.createQuery("UPDATE ClothInvoice SET markForDelete = 1, lastModifiedById = "+userDetails.getId()+" "
							+ " , lastModifiedBy = '"+DateTimeUtility.getCurrentTimeStamp()+"' where clothInvoiceId = "+clothInvoiceId+" ").executeUpdate();
					
					PurchaseOrdersToPartsRepository.updateFromReceivedToOrderedStatus(object.getLong("purchaseOrderId"), userDetails.getCompany_id());
					
					PurchaseOrdersDao.updatePurchaseOrderInvoiceNumberAndStatus("0", PurchaseOrderState.ORDERED_ID, 
							userDetails.getId(), new java.util.Date(), object.getLong("purchaseOrderId"), userDetails.getCompany_id());
					
					object.put("poUpdated", true);
				}
			}
			
			deleteDebitNoteEntryFromReceivedToOrderedStatus(object);
			
			return object;
			
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;

		}

	}
	
	@Override
	@Transactional
	public ValueObject updateUreaPOStatusFromReceivedToOrdered(ValueObject object) throws Exception {
		CustomUserDetails					userDetails						= null;
		UreaInvoice							ureaInven						= null;
		long								ureaInvoiceId					= 0;
		List<UreaInvoiceToDetails>			ureaDetails						= null;
		try {
			userDetails  = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ureaInven = UreaInvoiceRepository.getUreaInvoiceByPurchaseOrderId(object.getLong("purchaseOrderId"), userDetails.getCompany_id());
			
			if(ureaInven != null) {
				ureaInvoiceId = ureaInven.getUreaInvoiceId();
				
				ureaDetails = UreaInvoiceToDetailsRepository.getUreaInvoiceToDetailsList(ureaInvoiceId);
				
				if(ureaDetails != null) {
					
					for(UreaInvoiceToDetails ureaDet : ureaDetails) {
						
						entityManager.createQuery("Update UreaInvoice SET quantity = quantity - "+ureaDet.getQuantity()+",  "
								+ " lastModifiedById = "+userDetails.getId()+", lastModifiedBy = '"+DateTimeUtility.getCurrentDate()+"'"
								+ " WHERE ureaInvoiceId = "+ureaInvoiceId+" AND companyId = "+userDetails.getCompany_id()+" ").executeUpdate();
						
						UreaInvoiceToDetailsRepository.deleteUreaInventoryDetails(ureaDet.getUreaInvoiceToDetailsId());
					}
					
					entityManager.createQuery("UPDATE UreaInvoice SET markForDelete = 1, lastModifiedById = "+userDetails.getId()+" "
							+ " , lastModifiedBy = '"+DateTimeUtility.getCurrentTimeStamp()+"' where ureaInvoiceId = "+ureaInvoiceId+" ").executeUpdate();
					
					purchaseOrdersToUreaRepository.updateReceiveQuantityOfPurchaseOrdersToUrea(object.getLong("purchaseOrderId"), userDetails.getCompany_id());
					
					PurchaseOrdersToPartsRepository.updateFromReceivedToOrderedStatus(object.getLong("purchaseOrderId"), userDetails.getCompany_id());
					
					PurchaseOrdersDao.updatePurchaseOrderInvoiceNumberAndStatus("0", PurchaseOrderState.ORDERED_ID, 
							userDetails.getId(), new java.util.Date(), object.getLong("purchaseOrderId"), userDetails.getCompany_id());
					
					object.put("poUpdated", true);
				}
			}
			
			deleteDebitNoteEntryFromReceivedToOrderedStatus(object);
			
			return object;
			
		} catch (Exception e) {
			LOGGER.error("err"+e);
			throw e;

		}

	}

	@Override
	@Transactional
	public ValueObject updatePurchaseOrderPartDetails(ValueObject valueInObject) throws Exception {
		CustomUserDetails			userDetails				= null;
		HashMap<String, Object> 	configuration			= null;
		boolean 					totalGstCostConfig		= false;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= CompanyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			totalGstCostConfig	= (boolean) configuration.getOrDefault(PurchaseOrdersConfigurationConstant.TOTAL_GST_COST, false);
			
			valueInObject.put("companyId", userDetails.getCompany_id());
			valueInObject.put("userId", userDetails.getId());
			valueInObject.put("totalGstCostConfig", totalGstCostConfig);
			switch (valueInObject.getShort("purchaseorderTypeId")) {
			case PurchaseOrderType.PURCHASEORDER_TYPE_TYRE_PO:
				updatePurchaseOrderTyre(valueInObject);
				break;	
			case PurchaseOrderType.PURCHASEORDER_TYPE_BATTERY_PO:
				updatePurchaseOrderBattery(valueInObject);
				break;	
			case PurchaseOrderType.PURCHASEORDER_TYPE_CLOTH_PO:
				updatePurchaseOrderCloth(valueInObject);
				
				break;	
			case PurchaseOrderType.PURCHASEORDER_TYPE_UREA_PO:
				updatePurchaseOrderUrea(valueInObject);
				break;	
			default:
				updatePurchaseOrderPart(valueInObject);
				break;
			}
			
			valueInObject.put("update", true);
			return valueInObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}

	}
	
	@Transactional
	public void updatePurchaseOrderTyre(ValueObject valueInObject) throws Exception {
		Integer totalTyreAvailable 				= 0;
		Integer newTyre 						= 0;
		Integer rtTyre 							= 0;
		String 	inventoryTyreNewRT				= null;
		double 	purchaseOrderTotalGstCost 		= 0;
		double 	partGstCost 					= 0;
		double 	poGstCost 						= 0;
		double 	partOldGstCost 					= 0;
		try {
			PurchaseOrders purchaseOrders				= PurchaseOrdersDao.getPurchaseOrders(valueInObject.getLong("purchaseOrderId"), valueInObject.getInt("companyId"));
			List<InventoryTyreDto> inventoryTyreDto = iventoryTyreService.Purchase_Order_Validate_InventoryTyre(valueInObject.getInt("tyreSize",0));
			if (inventoryTyreDto != null && !inventoryTyreDto.isEmpty()) {
				for (InventoryTyreDto inventoryTyre : inventoryTyreDto) { 
					totalTyreAvailable += 1;
					if (inventoryTyre.getTYRE_RETREAD_COUNT() == 0) {
						newTyre += 1;
					} else {
						rtTyre += 1;
					}
				}
				inventoryTyreNewRT = " NEW= " + newTyre + " & RT= " + rtTyre + " " ;
				valueInObject.put("inventoryTyreQuantity", totalTyreAvailable);
				valueInObject.put("inventoryTyreNewRT", inventoryTyreNewRT);
			} 
		
		entityManager.createQuery("Update PurchaseOrdersToParts SET TYRE_MANUFACTURER_ID = "+valueInObject.getInt("tyreManufacturer")+ ","
				+ " TYRE_MODEL_ID = "+valueInObject.getInt("tyreModel")+", TYRE_SIZE_ID = "+valueInObject.getInt("tyreSize")+" ,"
				+ " INVENTORY_TYRE_QUANTITY = "+valueInObject.getInt("inventoryTyreQuantity")+", INVENTORY_TYRE_NEW_RT = '"+valueInObject.getString("inventoryTyreNewRT")+"' ,"
				+ " quantity = "+valueInObject.getDouble("quantity")+", notreceived_quantity = "+valueInObject.getDouble("notreceived_quantity")+" ,"
				+ " parteachcost = "+valueInObject.getDouble("parteachcost")+", discount = "+valueInObject.getDouble("discount")+" ,"
				+ " tax = "+valueInObject.getDouble("tax")+" , totalcost = "+valueInObject.getDouble("totalcost")+" "
				+ " WHERE purchaseorderto_partid = "+valueInObject.getLong("purchaseorderToPartId")+" AND companyId = "+valueInObject.getInt("companyId")+" AND markForDelete = 0 ").executeUpdate();
		
		updatePurchaseOrderMainTotalCost(valueInObject.getLong("purchaseOrderId"));	
		
		if(purchaseOrders != null && valueInObject.getBoolean("totalGstCostConfig",false) == true) {
			poGstCost					= purchaseOrders.getPurchaseorder_totaltax_cost();
			partOldGstCost				= (valueInObject.getDouble("oldQuantity") * valueInObject.getDouble("oldUnitPrice"))* (valueInObject.getDouble("oldGst") / 100);
			partGstCost					= (valueInObject.getDouble("quantity") * valueInObject.getDouble("parteachcost"))* (valueInObject.getDouble("tax") / 100);
			purchaseOrderTotalGstCost 	= ((poGstCost - partOldGstCost)+partGstCost);
			valueInObject.put("gstCost", purchaseOrderTotalGstCost);
			updatePurchaseOrderVariousTotalCost(valueInObject);
		}
		
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Transactional
	public void updatePurchaseOrderBattery(ValueObject valueInObject) throws Exception {
		double 								purchaseOrderTotalGstCost 		= 0;
		double 								partGstCost 					= 0;
		double 								poGstCost 						= 0;
		double 								partOldGstCost 					= 0;
		try {
		PurchaseOrders purchaseOrders				= PurchaseOrdersDao.getPurchaseOrders(valueInObject.getLong("purchaseOrderId"), valueInObject.getInt("companyId"));
		entityManager.createQuery("Update PurchaseOrdersToParts SET BATTERY_MANUFACTURER_ID = "+valueInObject.getInt("batteryManufacturer")+ ","
				+ " BATTERY_TYPE_ID = "+valueInObject.getInt("batteryModel")+", BATTERY_CAPACITY_ID = "+valueInObject.getLong("batteryCapacity")+" ,"
				+ " BATTERY_QUANTITY = "+valueInObject.getInt("batteyrQuantity",0)+" ,"
				+ " quantity = "+valueInObject.getDouble("quantity")+", notreceived_quantity = "+valueInObject.getDouble("notreceived_quantity")+" ,"
				+ " parteachcost = "+valueInObject.getDouble("parteachcost")+", discount = "+valueInObject.getDouble("discount")+" ,"
				+ " tax = "+valueInObject.getDouble("tax")+" , totalcost = "+valueInObject.getDouble("totalcost")+" "
				+ " WHERE purchaseorderto_partid = "+valueInObject.getLong("purchaseorderToPartId")+" AND companyId = "+valueInObject.getInt("companyId")+" AND markForDelete = 0 ").executeUpdate();
		
		updatePurchaseOrderMainTotalCost(valueInObject.getLong("purchaseOrderId"));	
		
		if(purchaseOrders != null && valueInObject.getBoolean("totalGstCostConfig",false) == true) {
			poGstCost					= purchaseOrders.getPurchaseorder_totaltax_cost();
			partOldGstCost				= (valueInObject.getDouble("oldQuantity") * valueInObject.getDouble("oldUnitPrice"))* (valueInObject.getDouble("oldGst") / 100);
			partGstCost					= (valueInObject.getDouble("quantity") * valueInObject.getDouble("parteachcost"))* (valueInObject.getDouble("tax") / 100);
			purchaseOrderTotalGstCost 	= ((poGstCost - partOldGstCost)+partGstCost);
			valueInObject.put("gstCost", purchaseOrderTotalGstCost);
			updatePurchaseOrderVariousTotalCost(valueInObject);
		}
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public void updatePurchaseOrderCloth(ValueObject valueInObject) throws Exception {
		double 	purchaseOrderTotalGstCost 		= 0;
		double 	partGstCost 					= 0;
		double 	poGstCost 						= 0;
		double 	partOldGstCost 					= 0;
		try {
			PurchaseOrders purchaseOrders				= PurchaseOrdersDao.getPurchaseOrders(valueInObject.getLong("purchaseOrderId"), valueInObject.getInt("companyId"));	
			entityManager.createQuery("Update PurchaseOrdersToParts SET clothTypesId = "+valueInObject.getLong("clothTypeId")+", "
					+ " quantity = "+valueInObject.getDouble("quantity")+", notreceived_quantity = "+valueInObject.getDouble("notreceived_quantity")+" ,"
					+ " parteachcost = "+valueInObject.getDouble("parteachcost")+", discount = "+valueInObject.getDouble("discount")+" ,"
					+ " tax = "+valueInObject.getDouble("tax")+" , totalcost = "+valueInObject.getDouble("totalcost")+" "
					+ " WHERE purchaseorderto_partid = "+valueInObject.getLong("purchaseorderToPartId")+" AND companyId = "+valueInObject.getInt("companyId")+" AND markForDelete = 0 ").executeUpdate();
			
		
		updatePurchaseOrderMainTotalCost(valueInObject.getLong("purchaseOrderId"));	
		if(purchaseOrders != null && valueInObject.getBoolean("totalGstCostConfig",false) == true) {
			poGstCost					= purchaseOrders.getPurchaseorder_totaltax_cost();
			partOldGstCost				= (valueInObject.getDouble("oldQuantity") * valueInObject.getDouble("oldUnitPrice"))* (valueInObject.getDouble("oldGst") / 100);
			partGstCost					= (valueInObject.getDouble("quantity") * valueInObject.getDouble("parteachcost"))* (valueInObject.getDouble("tax") / 100);
			purchaseOrderTotalGstCost 	= ((poGstCost - partOldGstCost)+partGstCost);
			valueInObject.put("gstCost", purchaseOrderTotalGstCost);
			updatePurchaseOrderVariousTotalCost(valueInObject);
		}
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Transactional
	public void updatePurchaseOrderUrea(ValueObject valueInObject) throws Exception {
		double 								balanceAmount 					= 0;
		double 								purchaseOrderTotalCost 			= 0;
		double 								purchaseOrderSubTotalCost 		= 0;
		double 								purchaseOrderBalanceCost 		= 0;
		double 								purchaseOrderTotalGstCost 		= 0;
		double 								partGstCost 					= 0;
		double 								poGstCost 						= 0;
		double 								partOldGstCost 					= 0;
		try {
			java.util.Date 			currentDateUpdate 			= new java.util.Date();
			Timestamp 	 			toDate 						= new java.sql.Timestamp(currentDateUpdate.getTime());
			PurchaseOrders 			purchaseOrders				= PurchaseOrdersDao.getPurchaseOrders(valueInObject.getLong("purchaseOrderId"), valueInObject.getInt("companyId"));
			try {
				entityManager.createQuery("Update PurchaseOrdersToUrea SET ureaManufacturerId = "+valueInObject.getLong("ureaManufacturerId")+", "
						+ " quantity = "+valueInObject.getDouble("quantity")+", notRecivedQuantity = "+valueInObject.getDouble("notreceived_quantity")+" ,"
						+ " unitCost = "+valueInObject.getDouble("parteachcost")+", discount = "+valueInObject.getDouble("discount")+" ,"
						+ " tax = "+valueInObject.getDouble("tax")+" , totalcost = "+valueInObject.getDouble("totalcost")+" "
						+ " WHERE purchaseOrdersToUreaId = "+valueInObject.getLong("purchaseorderToPartId")+" AND companyId = "+valueInObject.getInt("companyId")+" AND markForDelete = 0 ").executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(purchaseOrders != null) {
				if(purchaseOrders.getBalanceAmount() == null) {
					balanceAmount 			=	valueInObject.getDouble("totalcost");
				}else {
					balanceAmount 			=	(purchaseOrders.getBalanceAmount()-valueInObject.getDouble("purcahseOrderTotal"))+valueInObject.getDouble("totalcost");
				}
				purchaseOrderTotalCost 		=	(purchaseOrders.getPurchaseorder_totalcost()-valueInObject.getDouble("purcahseOrderTotal"))+valueInObject.getDouble("totalcost");
				purchaseOrderSubTotalCost 	=	(purchaseOrders.getPurchaseorder_subtotal_cost()-valueInObject.getDouble("purcahseOrderTotal"))+valueInObject.getDouble("totalcost");
				purchaseOrderBalanceCost 	=	(purchaseOrders.getPurchaseorder_balancecost()-valueInObject.getDouble("purcahseOrderTotal"))+valueInObject.getDouble("totalcost");
			
			}
			
			PurchaseOrdersDao.UpdatePurchaseOrderCost(balanceAmount, purchaseOrderTotalCost,purchaseOrderSubTotalCost,purchaseOrderBalanceCost,toDate, valueInObject.getLong("userId"),valueInObject.getLong("purchaseOrderId"), valueInObject.getInt("companyId"));
			
			if(purchaseOrders != null && valueInObject.getBoolean("totalGstCostConfig",false) == true) {
				poGstCost					= purchaseOrders.getPurchaseorder_totaltax_cost();
				partOldGstCost				= (valueInObject.getDouble("oldQuantity") * valueInObject.getDouble("oldUnitPrice"))* (valueInObject.getDouble("oldGst") / 100);
				partGstCost					= (valueInObject.getDouble("quantity") * valueInObject.getDouble("parteachcost"))* (valueInObject.getDouble("tax") / 100);
				purchaseOrderTotalGstCost 	= ((poGstCost - partOldGstCost)+partGstCost);
				valueInObject.put("gstCost", purchaseOrderTotalGstCost);
				updatePurchaseOrderVariousTotalCost(valueInObject);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public void updatePurchaseOrderPart(ValueObject valueInObject) throws Exception {
		double 								purchaseOrderTotalGstCost 		= 0;
		double 								partGstCost 					= 0;
		double 								poGstCost 						= 0;
		double 								partOldGstCost 					= 0;
		try {
			PurchaseOrders 				purchaseOrders	= PurchaseOrdersDao.getPurchaseOrders(valueInObject.getLong("purchaseOrderId"), valueInObject.getInt("companyId"));
			InventoryAllDto 			invent 			= InventoryService.Purchase_Order_Validate_InventoryAll(valueInObject.getLong("partId",0));

			if (invent != null) {
				valueInObject.put("inventoryAllId", invent.getInventory_all_id());
				valueInObject.put("inventoryAllQuantity", invent.getAll_quantity());
				
			}
			entityManager.createQuery("Update PurchaseOrdersToParts SET partId = "+valueInObject.getLong("partId")+", "
					+ " quantity = "+valueInObject.getDouble("quantity")+", notreceived_quantity = "+valueInObject.getDouble("notreceived_quantity")+" ,"
					+ " inventory_all_id = "+valueInObject.getLong("inventoryAllId",0)+", inventory_all_quantity = "+valueInObject.getDouble("inventoryAllQuantity",0)+" ,"
					+ " parteachcost = "+valueInObject.getDouble("parteachcost")+", discount = "+valueInObject.getDouble("discount")+" ,"
					+ " tax = "+valueInObject.getDouble("tax")+" , totalcost = "+valueInObject.getDouble("totalcost")+" "
					+ " WHERE purchaseorderto_partid = "+valueInObject.getLong("purchaseorderToPartId")+" AND companyId = "+valueInObject.getInt("companyId")+" AND markForDelete = 0 ").executeUpdate();
			
			// we can update GST cost because in default GST cost added separetly 
			if(purchaseOrders != null && valueInObject.getBoolean("totalGstCostConfig",false) == true) {
				poGstCost					= purchaseOrders.getPurchaseorder_totaltax_cost();
				partOldGstCost				= (valueInObject.getDouble("oldQuantity") * valueInObject.getDouble("oldUnitPrice"))* (valueInObject.getDouble("oldGst") / 100);
				partGstCost					= (valueInObject.getDouble("quantity") * valueInObject.getDouble("parteachcost"))* (valueInObject.getDouble("tax") / 100);
				purchaseOrderTotalGstCost 	= ((poGstCost - partOldGstCost)+partGstCost);
				valueInObject.put("gstCost", purchaseOrderTotalGstCost);
				updatePurchaseOrderVariousTotalCost(valueInObject);
			}
			
			updatePurchaseOrderMainTotalCost(valueInObject.getLong("purchaseOrderId"));	
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public void updatePurchaseOrderVariousTotalCost(ValueObject valueInObject) throws Exception {
		try {
			entityManager.createQuery("Update PurchaseOrders SET purchaseorder_totaltax_cost = "+valueInObject.getDouble("gstCost")+" "
					+ " WHERE purchaseorder_id = "+valueInObject.getLong("purchaseOrderId")+" AND companyId = "+valueInObject.getInt("companyId")+" AND markForDelete = 0 ").executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getTotalAmountOfPurchaseOrders(PurchaseOrdersDto purchase) throws Exception {
		List<PurchaseOrdersToPartsDto> 			purchaseToParts 				= null;
		List<PurchaseOrdersToPartsDto> 			purchaseToTyre 					= null; 
		List<PurchaseOrdersToPartsDto> 			purchaseToBattery 				= null;
		List<PurchaseOrdersToPartsDto> 			purchaseToCloth 				= null;
		List<PurchaseOrdersToUrea> 				purchaseToUrea 					= null;
		CustomUserDetails						userDetails 					= null;
		Double 									totalOrdered 					= 0.0;
		Double 									totalRecevied 					= 0.0;
		Double 									totalEachCost					= 0.0;
		double 									totalDiscountCost 				= 0;
		boolean partFound 														= false;
		
	
		double 									TotalReceivedPartCost				=0.0;
		
		try {
			ValueObject object = new ValueObject();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			switch (purchase.getPurchaseorder_typeId()) {

			case PurchaseOrderType.PURCHASEORDER_TYPE_PART_PO:
				purchaseToParts 	= getPurchaseOrdersTasksToParts(purchase.getPurchaseorder_id(), userDetails.getCompany_id());
				
				if (purchaseToParts != null && !purchaseToParts.isEmpty()) {
					partFound= true;
					for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToParts) {
						totalOrdered 		+= purchaseOrdersToParts.getQuantity();
						totalRecevied 		+= purchaseOrdersToParts.getReceived_quantity();
						
						double eachPartAmount	= purchaseOrdersToParts.getParteachcost() * purchaseOrdersToParts.getQuantity();
						totalEachCost		+= eachPartAmount; 
						double gstAmount     = eachPartAmount * purchaseOrdersToParts.getTax() / 100;
						totalDiscountCost 	+= (eachPartAmount + gstAmount) * (purchaseOrdersToParts.getDiscount() / 100);
						
						TotalReceivedPartCost +=  purchaseOrdersToParts.getFinalReceivedAmount();
					}
				}
				object = POBL.getTotalAmountOfPurchaseOrders(purchase,totalOrdered,totalRecevied,totalEachCost,totalDiscountCost,TotalReceivedPartCost);
				object.put("PurchaseOrderPart", purchaseToParts);
				object.put("partFound",partFound);
			return object;
			
			case PurchaseOrderType.PURCHASEORDER_TYPE_TYRE_PO:
				purchaseToTyre 		= getPurchaseOrdersTasksToParts(purchase.getPurchaseorder_id(), userDetails.getCompany_id());
				if (purchaseToTyre != null && !purchaseToTyre.isEmpty()) {
					partFound= true;
					for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToTyre) {
						totalOrdered 		+= purchaseOrdersToParts.getQuantity();
						totalRecevied 		+= purchaseOrdersToParts.getReceived_quantity();
						totalEachCost		+= purchaseOrdersToParts.getParteachcost() *purchaseOrdersToParts.getQuantity(); 
						double gstAmount     = (purchaseOrdersToParts.getParteachcost() *purchaseOrdersToParts.getQuantity())*(purchaseOrdersToParts.getTax()/100);
						totalDiscountCost 	+= ((purchaseOrdersToParts.getParteachcost() *purchaseOrdersToParts.getQuantity())+ gstAmount)*(purchaseOrdersToParts.getDiscount()/100);
						
						TotalReceivedPartCost += purchaseOrdersToParts.getFinalReceivedAmount();
					}
				
				}
				object = POBL.getTotalAmountOfPurchaseOrders(purchase,totalOrdered,totalRecevied,totalEachCost,totalDiscountCost,TotalReceivedPartCost);
				object.put("PurchaseOrderPart", purchaseToTyre);
				object.put("partFound",partFound);
				return object;
				
			case PurchaseOrderType.PURCHASEORDER_TYPE_BATTERY_PO:
				purchaseToBattery 	= getPurchaseOrdersToPartsforBattery(purchase.getPurchaseorder_id(), userDetails.getCompany_id());
				
				if (purchaseToBattery != null && !purchaseToBattery.isEmpty()) {
					for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToBattery) {
						partFound= true;
						totalOrdered 		+= purchaseOrdersToParts.getQuantity();
						totalRecevied 		+= purchaseOrdersToParts.getReceived_quantity();
						totalEachCost		+= purchaseOrdersToParts.getParteachcost() *purchaseOrdersToParts.getQuantity(); 
						double gstAmount     = (purchaseOrdersToParts.getParteachcost() *purchaseOrdersToParts.getQuantity())*(purchaseOrdersToParts.getTax()/100);
						totalDiscountCost 	+= ((purchaseOrdersToParts.getParteachcost() *purchaseOrdersToParts.getQuantity())+ gstAmount)*(purchaseOrdersToParts.getDiscount()/100);
						
						TotalReceivedPartCost +=purchaseOrdersToParts.getFinalReceivedAmount();
					}
				}
				object = POBL.getTotalAmountOfPurchaseOrders(purchase,totalOrdered,totalRecevied,totalEachCost,totalDiscountCost,TotalReceivedPartCost);
				object.put("PurchaseOrderPart", purchaseToBattery);
				object.put("partFound",partFound);
				return object;
				
			case PurchaseOrderType.PURCHASEORDER_TYPE_CLOTH_PO:
				purchaseToCloth 	= getPurchaseOrdersTasksToCloth(purchase.getPurchaseorder_id(), userDetails.getCompany_id());
				
				if (purchaseToCloth != null && !purchaseToCloth.isEmpty()) {
					partFound= true;
					for (PurchaseOrdersToPartsDto purchaseOrdersToParts : purchaseToCloth) {
						totalOrdered 		+= purchaseOrdersToParts.getQuantity();
						totalRecevied 		+= purchaseOrdersToParts.getReceived_quantity();
						totalEachCost		+= purchaseOrdersToParts.getParteachcost() *purchaseOrdersToParts.getQuantity(); 
						double gstAmount     = (purchaseOrdersToParts.getParteachcost() *purchaseOrdersToParts.getQuantity())*(purchaseOrdersToParts.getTax()/100);
						totalDiscountCost 	+= ((purchaseOrdersToParts.getParteachcost() *purchaseOrdersToParts.getQuantity())+ gstAmount)*(purchaseOrdersToParts.getDiscount()/100);
						
						TotalReceivedPartCost += purchaseOrdersToParts.getFinalReceivedAmount();
					}
				}
				object = POBL.getTotalAmountOfPurchaseOrders(purchase,totalOrdered,totalRecevied,totalEachCost,totalDiscountCost,TotalReceivedPartCost);
				object.put("PurchaseOrderPart", purchaseToCloth);
				object.put("partFound",partFound);
				return object;
		
			case PurchaseOrderType.PURCHASEORDER_TYPE_UREA_PO:
				purchaseToUrea = purchaseOrdersToPartsService.getPurchaseOrdersToUreaByPurchaseOrderId(purchase.getPurchaseorder_id(), userDetails.getCompany_id());
				
				if (purchaseToUrea != null && !purchaseToUrea.isEmpty()) {
					partFound= true;
					for (PurchaseOrdersToUrea purchaseOrdersToParts : purchaseToUrea) {
						totalOrdered 		+= purchaseOrdersToParts.getQuantity();
						totalRecevied 		+= purchaseOrdersToParts.getRecivedQuantity();
						totalEachCost		+= purchaseOrdersToParts.getUnitCost() *purchaseOrdersToParts.getQuantity(); 
						double gstAmount     = (purchaseOrdersToParts.getUnitCost() *purchaseOrdersToParts.getQuantity())*(purchaseOrdersToParts.getTax()/100);
						totalDiscountCost 	+= ((purchaseOrdersToParts.getUnitCost() *purchaseOrdersToParts.getQuantity())+ gstAmount)*(purchaseOrdersToParts.getDiscount()/100);
							 
					}
				}
				object = POBL.getTotalAmountOfPurchaseOrders(purchase,totalOrdered,totalRecevied,totalEachCost,totalDiscountCost,TotalReceivedPartCost);
				object.put("PurchaseOrderPart", purchaseToUrea);
				object.put("partFound",partFound);
				return object;

			default:
				return object;
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception {
		entityManager.createQuery("  UPDATE PurchaseOrders SET purchaseorder_vendor_paymentdate =" + paymentDate+" , "
				+ " purchaseorder_vendor_paymodeId="+paymentStatus+", purchaseorder_vendor_approvalID = "+approvalId+""
				+ " WHERE purchaseorder_id = "+invoiceId+" ").executeUpdate();
		
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject createPurchaseOrderPartApproval(ValueObject valueObject)
			throws Exception {
		ArrayList<ValueObject>			dataArrayObjColl			= null;
		PurchaseOrdersSequeceCounter 	sequeceCounter 				= null;
		PurchaseOrders 					purchaseOrders				= null;		
		PurchaseOrdersToParts			purchaseOrdersToParts		= null;
		PurchaseOrders 					existPurchaseOrders			= null;
		try {
			
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("tyreExpenseDetailsList");
			
			existPurchaseOrders	= getPurchaseOrders(valueObject.getLong("purchaseOrderId"));
			
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				for (ValueObject object : dataArrayObjColl) {
					sequeceCounter 		= purchaseOrdersSequenceService.findNextPurchaseOrderNumber(valueObject.getInt("companyId"));
					if(object.getShort("status") == PurchaseOrderState.VENDOR_CHANGE  &&( object.getShort("purchaseOrderType") == PurchaseOrderType.PURCHASEORDER_TYPE_PART_PO || object.getShort("purchaseOrderType") == PurchaseOrderType.PURCHASEORDER_TYPE_CLOTH_PO )) {
						purchaseOrders =	POBL.preparePurchaseOrdersForOtherPart(object,existPurchaseOrders);
						
						if (sequeceCounter == null) {
							object.put("sequenceNotFound", true);
							return object;
						}else {
							purchaseOrders.setPurchaseorder_Number(sequeceCounter.getNextVal());
						}
						purchaseOrders 			= PurchaseOrdersDao.save(purchaseOrders);
						
						purchaseOrdersToParts 	= preparePurchaseOrderPartDetail(object,purchaseOrders);
						addPurchaseOrdersToParts(purchaseOrdersToParts);
						
					}
					
					if(object.getShort("status") == PurchaseOrderState.VENDOR_CHANGE  && object.getShort("purchaseOrderType") == PurchaseOrderType.PURCHASEORDER_TYPE_TYRE_PO) {
						purchaseOrders =	POBL.preparePurchaseOrdersForOtherPart(object,existPurchaseOrders);
						if (sequeceCounter == null) {
							object.put("sequenceNotFound", true);
							return object;
						}else {
							purchaseOrders.setPurchaseorder_Number(sequeceCounter.getNextVal());
						}
						purchaseOrders 			= PurchaseOrdersDao.save(purchaseOrders);
						
						purchaseOrdersToParts 	= preparePurchaseOrderTyreDetail(object,purchaseOrders);
						addPurchaseOrdersToParts(purchaseOrdersToParts);
					}
					if(object.getShort("status") == PurchaseOrderState.VENDOR_CHANGE  && object.getShort("purchaseOrderType") == PurchaseOrderType.PURCHASEORDER_TYPE_BATTERY_PO) {
						purchaseOrders =	POBL.preparePurchaseOrdersForOtherPart(object,existPurchaseOrders);
						if (sequeceCounter == null) {
							object.put("sequenceNotFound", true);
							return object;
						}else {
							purchaseOrders.setPurchaseorder_Number(sequeceCounter.getNextVal());
						}
						purchaseOrders 			= PurchaseOrdersDao.save(purchaseOrders);
						
						purchaseOrdersToParts 	= preparePurchaseOrderBatteryDetail(object,purchaseOrders);
						addPurchaseOrdersToParts(purchaseOrdersToParts);
					}
					if(object.getShort("status") == PurchaseOrderState.VENDOR_CHANGE  && object.getShort("purchaseOrderType") == PurchaseOrderType.PURCHASEORDER_TYPE_UREA_PO) {
						purchaseOrders =	POBL.preparePurchaseOrdersForOtherPart(object,existPurchaseOrders);
						if (sequeceCounter == null) {
							object.put("sequenceNotFound", true);
							return object;
						}else {
							purchaseOrders.setPurchaseorder_Number(sequeceCounter.getNextVal());
						}
						purchaseOrders 			= PurchaseOrdersDao.save(purchaseOrders);
						
						PurchaseOrdersToUrea	purchaseOrdersToUrea 		= preparePurchaseOrderUreaDetail(object,purchaseOrders);
						purchaseOrdersToUreaRepository.save(purchaseOrdersToUrea);
					}
					
					if( object.getShort("purchaseOrderType") == PurchaseOrderType.PURCHASEORDER_TYPE_UREA_PO) {
						purchaseOrdersToUreaRepository.updatePurchaseOrderUreaQuantityAndStatus(object.getDouble("quantity"),object.getDouble("totalCost"),object.getShort("status"),object.getString("remark"),object.getLong("purchaseOrderToPartId"),object.getInt("companyId"));
					
					}else {
						PurchaseOrdersToPartsRepository.updatePurchaseOrderPartQuantityAndStatus(object.getDouble("quantity"),object.getDouble("totalCost"),object.getShort("status"),object.getString("remark"),object.getLong("purchaseOrderToPartId"),object.getInt("companyId"));
						
					}
					
				}
			}	
			if( existPurchaseOrders.getPurchaseorder_typeId() == PurchaseOrderType.PURCHASEORDER_TYPE_UREA_PO) {
				updatePurchaseOrderUreaMainTotalCost(valueObject.getLong("purchaseOrderId"));
			}else {
				updatePurchaseOrderMainTotalCost(valueObject.getLong("purchaseOrderId"));
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public PurchaseOrdersToParts preparePurchaseOrderPartDetail(ValueObject object,PurchaseOrders purchaseOrder) throws Exception {

		PurchaseOrdersToParts purchasePart = new PurchaseOrdersToParts();

		purchasePart.setPartid(object.getLong("partId"));
		purchasePart.setClothTypesId(object.getLong("clothId"));
		try {
			if (purchasePart.getPartid() != null) {
				InventoryAllDto invent = InventoryService.Purchase_Order_Validate_InventoryAll(purchasePart.getPartid());

				if (invent != null) {
					if (invent.getAll_quantity() != null && invent.getAll_quantity() != 0.0) {
						purchasePart.setInventory_all_id(invent.getInventory_all_id());
						purchasePart.setInventory_all_quantity(invent.getAll_quantity());
					} else {
						purchasePart.setInventory_all_id((long) 0);
						purchasePart.setInventory_all_quantity(0.0);
					}
				} else {
					purchasePart.setInventory_all_id((long) 0);
					purchasePart.setInventory_all_quantity(0.0);
				}
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}

		Double Quantity = 0.0;
		Double eachcost = 0.0;
		Double discount = 0.0;
		Double tax = 0.0;

		if (object.getDouble("quantity",0) > 0) {
			Quantity = object.getDouble("quantity",0);
		}
		if (object.getDouble("eachCost",0) > 0) {
			eachcost = object.getDouble("eachCost",0);
		}
		if (object.getDouble("discount",0) > 0) {
			discount = object.getDouble("discount",0);
		}
		if (object.getDouble("tax",0) > 0) {
			tax = object.getDouble("tax",0);
		}
		Double discountCost = 0.0;
		Double TotalCost = 0.0;
		

		discountCost 	= (Quantity * eachcost) - ((Quantity * eachcost) * (discount / 100));
		TotalCost 		= round(((discountCost) + (discountCost * (tax / 100))), 2);
		
		purchasePart.setQuantity(Quantity);
		purchasePart.setReceived_quantity(0.0);
		purchasePart.setNotreceived_quantity(Quantity);
		purchasePart.setRequestedQuantity(Quantity);
		purchasePart.setParteachcost(eachcost);
		purchasePart.setDiscount(discount);
		purchasePart.setTax(tax);

		purchasePart.setTotalcost(TotalCost);
		
		purchasePart.setApprovalPartStatusId(PurchaseOrderState.APPROVED);
		purchasePart.setPurchaseorders(purchaseOrder);
		purchasePart.setCompanyId(purchaseOrder.getCompanyId());
		return purchasePart;
	}

	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	
	
	public PurchaseOrdersToParts preparePurchaseOrderTyreDetail(ValueObject object,PurchaseOrders purchaseOrder) {

		PurchaseOrdersToParts PurchasePart = new PurchaseOrdersToParts();
		try {
			PurchasePart.setTYRE_MANUFACTURER_ID(object.getInt("tyreManufacturerId"));
			PurchasePart.setTYRE_MODEL_ID(object.getInt("tyreModelId"));
			PurchasePart.setTYRE_SIZE_ID(object.getInt("tyreSizeId"));
			// check Tyre Size empty or not
			if (PurchasePart.getTYRE_SIZE_ID() != null && PurchasePart.getTYRE_SIZE_ID() > 0) {
				List<InventoryTyreDto> invent = iventoryTyreService
						.Purchase_Order_Validate_InventoryTyre(PurchasePart.getTYRE_SIZE_ID());
				Integer TotalTyreAvailable = 0, NewTyre = 0, RtTyre = 0;
				if (invent != null && !invent.isEmpty()) {
					for (InventoryTyreDto inventoryTyre : invent) { 
						TotalTyreAvailable += 1;
						if (inventoryTyre.getTYRE_RETREAD_COUNT() == 0) {
							NewTyre += 1;
						} else {
							RtTyre += 1;
						}
					}
					PurchasePart.setINVENTORY_TYRE_QUANTITY(TotalTyreAvailable);
					PurchasePart.setINVENTORY_TYRE_NEW_RT("NEW= " + NewTyre + " & RT= " + RtTyre + "");
				} else {
					PurchasePart.setINVENTORY_TYRE_QUANTITY(0);
					PurchasePart.setINVENTORY_TYRE_NEW_RT(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Double Quantity = 0.0;
		Double eachcost = 0.0;
		Double discount = 0.0;
		Double tax = 0.0;

		if (object.getDouble("quantity",0) > 0) {
			Quantity = object.getDouble("quantity",0);
		}
		if (object.getDouble("eachCost",0) > 0) {
			eachcost = object.getDouble("eachCost",0);
		}
		if (object.getDouble("discount",0) > 0) {
			discount = object.getDouble("discount",0);
		}
		if (object.getDouble("tax",0) > 0) {
			tax = object.getDouble("tax",0);
		}
		Double discountCost = 0.0;
		Double TotalCost = 0.0;

		discountCost = (Quantity * eachcost) - ((Quantity * eachcost) * (discount / 100));
		TotalCost = round(((discountCost) + (discountCost * (tax / 100))), 2);

		PurchasePart.setQuantity(Quantity);
		PurchasePart.setReceived_quantity(0.0);
		PurchasePart.setNotreceived_quantity(Quantity);

		PurchasePart.setParteachcost(eachcost);
		PurchasePart.setDiscount(discount);
		PurchasePart.setTax(tax);

		PurchasePart.setTotalcost(TotalCost);
		PurchasePart.setApprovalPartStatusId(PurchaseOrderState.APPROVED);
		PurchasePart.setPurchaseorders(purchaseOrder);
		PurchasePart.setCompanyId(purchaseOrder.getCompanyId());

		return PurchasePart;
	}
	
	public PurchaseOrdersToParts preparePurchaseOrderBatteryDetail(ValueObject object,PurchaseOrders purchaseOrder) {

		PurchaseOrdersToParts PurchasePart = new PurchaseOrdersToParts();


		try {
			PurchasePart.setBATTERY_MANUFACTURER_ID(object.getLong("batteryManufacturerId"));
			PurchasePart.setBATTERY_TYPE_ID(object.getLong("batteryTypeId"));
			PurchasePart.setBATTERY_CAPACITY_ID(object.getLong("batteryCapacityId"));
			// check Tyre Size empty or not
			if (PurchasePart.getBATTERY_CAPACITY_ID() != null && PurchasePart.getBATTERY_CAPACITY_ID() > 0) {
				Long invent = batteryService.getBatteryCount(PurchasePart.getBATTERY_CAPACITY_ID(),object.getInt("companyId"));
				if (invent != null ) {
					
					PurchasePart.setBATTERY_QUANTITY(invent);
				} else {
					PurchasePart.setBATTERY_QUANTITY((long) 0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Double Quantity = 0.0;
		Double eachcost = 0.0;
		Double discount = 0.0;
		Double tax = 0.0;
		if (object.getDouble("quantity",0) > 0) {
			Quantity = object.getDouble("quantity",0);
		}
		if (object.getDouble("eachCost",0) > 0) {
			eachcost = object.getDouble("eachCost",0);
		}
		if (object.getDouble("discount",0) > 0) {
			discount = object.getDouble("discount",0);
		}
		if (object.getDouble("tax",0) > 0) {
			tax = object.getDouble("tax",0);
		}
		Double discountCost = 0.0;
		Double TotalCost = 0.0;

		discountCost = (Quantity * eachcost) - ((Quantity * eachcost) * (discount / 100));
		TotalCost = round(((discountCost) + (discountCost * (tax / 100))), 2);

		PurchasePart.setQuantity(Quantity);
		PurchasePart.setReceived_quantity(0.0);
		PurchasePart.setNotreceived_quantity(Quantity);

		PurchasePart.setParteachcost(eachcost);
		PurchasePart.setDiscount(discount);
		PurchasePart.setTax(tax);

		PurchasePart.setTotalcost(TotalCost);

		PurchasePart.setApprovalPartStatusId(PurchaseOrderState.APPROVED);
		PurchasePart.setPurchaseorders(purchaseOrder);
		PurchasePart.setCompanyId(purchaseOrder.getCompanyId());
		return PurchasePart;
	}
	
	
	public PurchaseOrdersToUrea preparePurchaseOrderUreaDetail(ValueObject valueObject,PurchaseOrders purchaseOrder) throws Exception {
		                              
		try {

			PurchaseOrdersToUrea purchaseOrdersToUrea = new PurchaseOrdersToUrea();
			purchaseOrdersToUrea.setUreaManufacturerId(valueObject.getLong("ureaManufacturerId",0));
			purchaseOrdersToUrea.setQuantity(valueObject.getDouble("quantity",0));
			purchaseOrdersToUrea.setUnitCost(valueObject.getDouble("eachCost",0));
			purchaseOrdersToUrea.setDiscount(valueObject.getDouble("discount",0));
			purchaseOrdersToUrea.setTax(valueObject.getDouble("tax",0));
			
		double	discountCost = (purchaseOrdersToUrea.getQuantity() * purchaseOrdersToUrea.getUnitCost()) - ((purchaseOrdersToUrea.getQuantity() * purchaseOrdersToUrea.getUnitCost()) * (purchaseOrdersToUrea.getDiscount() / 100));
		double	TotalCost = round(((discountCost) + (discountCost * (purchaseOrdersToUrea.getTax() / 100))), 2);
			purchaseOrdersToUrea.setTotalcost(TotalCost);
			purchaseOrdersToUrea.setNotRecivedQuantity(valueObject.getDouble("NotReceivedQuantity",0));
			purchaseOrdersToUrea.setRecivedQuantity(valueObject.getDouble("receivedQuantity",0));
			purchaseOrdersToUrea.setReceived_quantity_remark(valueObject.getString("receivedRemark", ""));
			purchaseOrdersToUrea.setPurchaseOrderId(valueObject.getLong("purchaseOrderId",0));
			purchaseOrdersToUrea.setCompanyId(purchaseOrder.getCompanyId());
			purchaseOrdersToUrea.setMarkForDelete(false);
			
			purchaseOrdersToUrea.setApprovalPartStatusId(PurchaseOrderState.APPROVED);
			purchaseOrdersToUrea.setPurchaseOrderId(purchaseOrder.getPurchaseorder_id());
			purchaseOrdersToUrea.setCompanyId(purchaseOrder.getCompanyId());

			return purchaseOrdersToUrea;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			
		}
		

	}
	
	@Transactional
	public List<PurchaseOrdersToParts> getPurchaseOrdersApprovedTasksToParts(Long purchaseOrders_id) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return PurchaseOrdersToPartsRepository.getPurchaseOrdersApprovedTasksToParts(purchaseOrders_id,
				userDetails.getCompany_id());
	}
	
	@Transactional
	public void deletePurchaseOrdersParts(Long purchaseorder_id, Integer company_id) {
	 PurchaseOrdersToPartsRepository.deletePurchaseOrdersParts(purchaseorder_id,  company_id);
	}
	
	@Transactional
	public void updatePurchaseOrderUreaMainTotalCost(Long purchaseOdresId) throws Exception {
		CustomUserDetails 					userDetails 					= null;
		HashMap<String, Object> 			configuration	        		= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration				= CompanyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
			if((boolean) configuration.get("totalGstCost")) {
				//update subTotalCost
				entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_subtotal_cost = (SELECT SUM(POP.totalcost) FROM PurchaseOrdersToUrea AS POP "
						+ " WHERE POP.purchaseOrderId= "+ purchaseOdresId + " AND POP.markForDelete = 0 AND POP.companyId=" + userDetails.getCompany_id()+"  AND POP.approvalPartStatusId ="+PurchaseOrderState.APPROVED+" )"
						+ " WHERE purchaseorder_id=" + purchaseOdresId + " AND companyId = " + userDetails.getCompany_id() + " " ).executeUpdate();
				//update totalCost
				entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_totalcost = ((SELECT SUM(POP.totalcost) FROM PurchaseOrdersToUrea AS POP "
						+ " WHERE POP.purchaseOrderId= "+ purchaseOdresId + " AND POP.markForDelete = 0 AND POP.companyId=" + userDetails.getCompany_id()+"  AND POP.approvalPartStatusId ="+PurchaseOrderState.APPROVED+" )+ purchaseorder_freight ) "
						+ " where purchaseorder_id=" + purchaseOdresId + " AND companyId = " + userDetails.getCompany_id() + "" ).executeUpdate();
				//update PO Balance Cost
				entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_balancecost = (((SELECT SUM(POP.totalcost) FROM PurchaseOrdersToUrea AS POP "
						+ " WHERE POP.purchaseOrderId= "+ purchaseOdresId + " AND POP.markForDelete = 0 AND POP.companyId=" + userDetails.getCompany_id()+"  AND POP.approvalPartStatusId ="+PurchaseOrderState.APPROVED+"  ) + purchaseorder_freight ) - (purchaseorder_total_debitnote_cost + purchaseorder_advancecost)) "
						+ " where purchaseorder_id=" + purchaseOdresId + " AND companyId = " + userDetails.getCompany_id() + "" ).executeUpdate();
				//update Balance Cost
				entityManager.createQuery("UPDATE PurchaseOrders SET balanceAmount = (((SELECT SUM(POP.totalcost) FROM PurchaseOrdersToUrea AS POP "
						+ " WHERE POP.purchaseOrderId= "+ purchaseOdresId + " AND POP.markForDelete = 0 AND POP.companyId=" + userDetails.getCompany_id()+"   AND POP.approvalPartStatusId ="+PurchaseOrderState.APPROVED+" ) + purchaseorder_freight ) - (purchaseorder_total_debitnote_cost + purchaseorder_advancecost)) "
						+ " where purchaseorder_id=" + purchaseOdresId + " AND companyId = " + userDetails.getCompany_id() + "" ).executeUpdate();
				
		
			
			}else {
				
				//update subTotalCost
				entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_subtotal_cost = (SELECT SUM(POP.totalcost) FROM PurchaseOrdersToUrea AS POP "
						+ " WHERE POP.purchaseOrderId= "+ purchaseOdresId + " AND POP.markForDelete = 0 AND POP.companyId=" + userDetails.getCompany_id()+"  AND POP.approvalPartStatusId ="+PurchaseOrderState.APPROVED+" )"
						+ " WHERE purchaseorder_id=" + purchaseOdresId + " AND companyId = " + userDetails.getCompany_id() + " " ).executeUpdate();
				//update totalCost
				entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_totalcost = ((SELECT SUM(POP.totalcost) FROM PurchaseOrdersToUrea AS POP "
						+ " WHERE POP.purchaseOrderId= "+ purchaseOdresId + " AND POP.markForDelete = 0 AND POP.companyId=" + userDetails.getCompany_id()+"  AND POP.approvalPartStatusId ="+PurchaseOrderState.APPROVED+" )+ purchaseorder_freight + purchaseorder_totaltax_cost) "
						+ " where purchaseorder_id=" + purchaseOdresId + " AND companyId = " + userDetails.getCompany_id() + "" ).executeUpdate();
				//update PO Balance Cost
				entityManager.createQuery("UPDATE PurchaseOrders SET purchaseorder_balancecost = (((SELECT SUM(POP.totalcost) FROM PurchaseOrdersToUrea AS POP "
						+ " WHERE POP.purchaseOrderId= "+ purchaseOdresId + " AND POP.markForDelete = 0 AND POP.companyId=" + userDetails.getCompany_id()+"  AND POP.approvalPartStatusId ="+PurchaseOrderState.APPROVED+" ) + purchaseorder_freight + purchaseorder_totaltax_cost ) - (purchaseorder_total_debitnote_cost + purchaseorder_advancecost)) "
						+ " where purchaseorder_id=" + purchaseOdresId + " AND companyId = " + userDetails.getCompany_id() + "" ).executeUpdate();
				//update Balance Cost
				entityManager.createQuery("UPDATE PurchaseOrders SET balanceAmount = (((SELECT SUM(POP.totalcost) FROM PurchaseOrdersToUrea AS POP "
						+ " WHERE POP.purchaseOrderId= "+ purchaseOdresId + " AND POP.markForDelete = 0 AND POP.companyId=" + userDetails.getCompany_id()+"  AND POP.approvalPartStatusId ="+PurchaseOrderState.APPROVED+"  ) + purchaseorder_freight +purchaseorder_totaltax_cost ) - (purchaseorder_total_debitnote_cost + purchaseorder_advancecost)) "
						+ " where purchaseorder_id=" + purchaseOdresId + " AND companyId = " + userDetails.getCompany_id() + "" ).executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<PurchaseOrdersToPartsDto> getLeatestPartFromPO(long partid,short partType) throws Exception {
		CustomUserDetails userDetails = null;
		List<PurchaseOrdersToPartsDto> purchaseOrdersToPartsDtoList = null;
		try {
			TypedQuery<Object[]> query = null;
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(partType == PurchaseOrderType.PURCHASEORDER_TYPE_PART_PO) {
				 query = entityManager.createQuery(" SELECT POP.purchaseorderto_partid,POP.parteachcost,POP.quantity,POP.tax,"
						+ "PO.purchaseorder_statusId,POP.discount ,PO.purchaseorder_id,PO.purchaseorder_Number,POP.totalcost,V.vendorName "
						+ "FROM PurchaseOrdersToParts AS POP " 
						+ " inner join PurchaseOrders PO ON PO.purchaseorder_id = POP.purchaseorders.purchaseorder_id AND PO.markForDelete = 0 "
						+ " LEFT JOIN Vendor as V ON V.vendorId = PO.purchaseorder_vendor_id " 
						+ " WHERE POP.partid = "+partid+" AND POP.companyId="+userDetails.getCompany_id()+" AND  PO.purchaseorder_statusId = "+ PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED+" AND POP.markForDelete = 0  order by purchaseorderto_partid desc",Object[].class) ;
				
			}else if(partType == PurchaseOrderType.PURCHASEORDER_TYPE_CLOTH_PO){
				 query = entityManager.createQuery(" SELECT POP.purchaseorderto_partid,POP.parteachcost,POP.quantity,POP.tax,"
						+ "PO.purchaseorder_statusId,POP.discount ,PO.purchaseorder_id,PO.purchaseorder_Number,POP.totalcost,V.vendorName  "
						+ "FROM PurchaseOrdersToParts AS POP " 
						+ " inner join PurchaseOrders PO ON PO.purchaseorder_id = POP.purchaseorders.purchaseorder_id AND PO.markForDelete = 0 "
						+ " LEFT JOIN Vendor as V ON V.vendorId = PO.purchaseorder_vendor_id " 
						+ " WHERE POP.clothTypesId = "+partid+" AND POP.companyId="+userDetails.getCompany_id()+" AND  PO.purchaseorder_statusId = "+ PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED+" AND POP.markForDelete = 0  order by purchaseorderto_partid desc",Object[].class) ;
			}
			query.setMaxResults(3);
			List<Object[]> resultList = null;
				resultList=(List<Object[]>) query.getResultList();
			if(resultList != null && !resultList.isEmpty()) {
				purchaseOrdersToPartsDtoList = new ArrayList<>();
				for(Object[] result:resultList) {
				PurchaseOrdersToPartsDto purchaseOrdersToPartsDto= new PurchaseOrdersToPartsDto();
				purchaseOrdersToPartsDto.setPurchaseorderto_partid((Long) result[0]);
				purchaseOrdersToPartsDto.setParteachcost((Double) result[1]);
				purchaseOrdersToPartsDto.setQuantity((Double) result[2]);
				purchaseOrdersToPartsDto.setTax((Double) result[3]);
				purchaseOrdersToPartsDto.setPurchaseOrderStatusId((short) result[4]);
				purchaseOrdersToPartsDto.setDiscount((Double) result[5]);
				purchaseOrdersToPartsDto.setPurchaseorder_id((Long) result[6]);
				purchaseOrdersToPartsDto.setPurchaseorder_Number((Long) result[7]);
				purchaseOrdersToPartsDto.setTotalcost((Double) result[8]);
				purchaseOrdersToPartsDto.setVendorName((String) result[9]);
				purchaseOrdersToPartsDtoList.add(purchaseOrdersToPartsDto);
				}
			}
			return purchaseOrdersToPartsDtoList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public PurchaseOrdersToPartsDto getLeatestTyreFromPO(long tyreSizeId,long tyreManufacturerId ,long tyreModelId) throws Exception {
		CustomUserDetails userDetails = null;
		PurchaseOrdersToPartsDto purchaseOrdersToPartsDto = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Query query = entityManager.createQuery(" SELECT POP.purchaseorderto_partid,POP.parteachcost,POP.quantity,POP.tax,"
					+ "PO.purchaseorder_statusId,POP.discount ,PO.purchaseorder_id,PO.purchaseorder_Number,POP.totalcost "
					+ " FROM PurchaseOrdersToParts AS POP " 
					+ " inner join PurchaseOrders PO ON PO.purchaseorder_id = POP.purchaseorders.purchaseorder_id AND PO.markForDelete = 0 " + 
					" WHERE POP.TYRE_MANUFACTURER_ID = "+tyreManufacturerId+" AND POP.TYRE_MODEL_ID="+tyreModelId+" AND POP.TYRE_SIZE_ID="+tyreSizeId+" AND POP.companyId="+userDetails.getCompany_id()+" AND  PO.purchaseorder_statusId = "+ PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED+" AND POP.markForDelete = 0  order by purchaseorderto_partid desc") ;
			
			Object[] result = null;
			try {
				query.setMaxResults(1);
				result=(Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
			}
			if(result != null) {
				purchaseOrdersToPartsDto= new PurchaseOrdersToPartsDto();
				purchaseOrdersToPartsDto.setPurchaseorderto_partid((Long) result[0]);
				purchaseOrdersToPartsDto.setParteachcost((Double) result[1]);
				purchaseOrdersToPartsDto.setQuantity((Double) result[2]);
				purchaseOrdersToPartsDto.setTax((Double) result[3]);
				purchaseOrdersToPartsDto.setPurchaseOrderStatusId((short) result[4]);
				purchaseOrdersToPartsDto.setDiscount((Double) result[5]);
				purchaseOrdersToPartsDto.setPurchaseorder_id((Long) result[6]);
				purchaseOrdersToPartsDto.setPurchaseorder_Number((Long) result[7]);
				purchaseOrdersToPartsDto.setTotalcost((Double) result[8]);
			}
			return purchaseOrdersToPartsDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public PurchaseOrdersToPartsDto getLeatestBatteryFromPO(long batteryCapacity,long manufacturer ,long batterrymodel) throws Exception {
		CustomUserDetails userDetails = null;
		PurchaseOrdersToPartsDto purchaseOrdersToPartsDto = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Query query = entityManager.createQuery(" SELECT POP.purchaseorderto_partid,POP.parteachcost,POP.quantity,POP.tax,"
					+ "PO.purchaseorder_statusId,POP.discount ,PO.purchaseorder_id,PO.purchaseorder_Number,POP.totalcost "
					+ " FROM PurchaseOrdersToParts AS POP " 
					+ " inner join PurchaseOrders PO ON PO.purchaseorder_id = POP.purchaseorders.purchaseorder_id AND PO.markForDelete = 0 " + 
					" WHERE POP.BATTERY_MANUFACTURER_ID = "+manufacturer+" AND POP.BATTERY_TYPE_ID="+batterrymodel+" AND POP.BATTERY_CAPACITY_ID="+batteryCapacity+" AND POP.companyId="+userDetails.getCompany_id()+" AND  PO.purchaseorder_statusId = "+ PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED+" AND POP.markForDelete = 0  order by purchaseorderto_partid desc") ;
			
			Object[] result = null;
			try {
				query.setMaxResults(1);
				result=(Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
			}
			if(result != null) {
				purchaseOrdersToPartsDto= new PurchaseOrdersToPartsDto();
				purchaseOrdersToPartsDto.setPurchaseorderto_partid((Long) result[0]);
				purchaseOrdersToPartsDto.setParteachcost((Double) result[1]);
				purchaseOrdersToPartsDto.setQuantity((Double) result[2]);
				purchaseOrdersToPartsDto.setTax((Double) result[3]);
				purchaseOrdersToPartsDto.setPurchaseOrderStatusId((short) result[4]);
				purchaseOrdersToPartsDto.setDiscount((Double) result[5]);
				purchaseOrdersToPartsDto.setPurchaseorder_id((Long) result[6]);
				purchaseOrdersToPartsDto.setPurchaseorder_Number((Long) result[7]);
				purchaseOrdersToPartsDto.setTotalcost((Double) result[8]);
			}
			return purchaseOrdersToPartsDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Override
	public PurchaseOrdersToPartsDto getLastUreaDetailsFromPO(long manufacturer) throws Exception {
		CustomUserDetails userDetails = null;
		PurchaseOrdersToPartsDto purchaseOrdersToPartsDto = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Query query = entityManager.createQuery( "SELECT PU.purchaseOrdersToUreaId,PU.unitCost,PU.quantity,PU.tax,PO.purchaseorder_statusId,PU.discount ,PO.purchaseorder_id,PO.purchaseorder_Number,PU.totalcost"+
					 " FROM PurchaseOrdersToUrea AS PU "+ 
					" inner join PurchaseOrders PO ON PO.purchaseorder_id = PU.purchaseOrderId AND PO.markForDelete = 0  "+
					" WHERE PU.ureaManufacturerId = "+manufacturer+" AND PU.companyId="+userDetails.getCompany_id()+"  AND  PO.purchaseorder_statusId = "+ PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED+" AND PU.markForDelete = 0  order by purchaseOrdersToUreaId desc") ;
			
			Object[] result = null;
			try {
				query.setMaxResults(1);
				result=(Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
			}
			if(result != null) {
				purchaseOrdersToPartsDto= new PurchaseOrdersToPartsDto();
				purchaseOrdersToPartsDto.setPurchaseorderto_partid((Long) result[0]);
				purchaseOrdersToPartsDto.setParteachcost((Double) result[1]);
				purchaseOrdersToPartsDto.setQuantity((Double) result[2]);
				purchaseOrdersToPartsDto.setTax((Double) result[3]);
				purchaseOrdersToPartsDto.setPurchaseOrderStatusId((short) result[4]);
				purchaseOrdersToPartsDto.setDiscount((Double) result[5]);
				purchaseOrdersToPartsDto.setPurchaseorder_id((Long) result[6]);
				purchaseOrdersToPartsDto.setPurchaseorder_Number((Long) result[7]);
				purchaseOrdersToPartsDto.setTotalcost((Double) result[8]);
			}
			return purchaseOrdersToPartsDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public Object[] getUserActivityCount(ValueObject object) throws Exception {
		
		Query query = null;
		String fromDate ="startDate",toDate="endDate";
		 query = entityManager.createQuery("select count(PO) ,(select count(CO)  From PurchaseOrders as CO where CO.companyId ="+object.getInt("companyId", 0)+" AND CO.created between '"+object.getString(fromDate)+"' AND '"+object.getString(toDate)+"' "+object.getString("userCreatedCond","")+" ),"
				 +"(select count(UO)  From PurchaseOrders as UO where UO.companyId ="+object.getInt("companyId", 0)+" AND UO.lastupdated between '"+object.getString(fromDate)+"' AND '"+object.getString(toDate)+"' AND UO.lastupdated <> UO.created AND UO.markForDelete = 0 "+object.getString("userUpdatedCond","")+"  ) " 
				+" from PurchaseOrders as PO where PO.companyId = "+object.getInt("companyId", 0)+" AND PO.markForDelete = 1 AND PO.lastupdated between '"+object.getString(fromDate)+"' AND '"+object.getString(toDate)+"' "+object.getString("userDeletedCond","")+" ");
		 
		return (Object[]) query.getSingleResult();
	}
	
	public List<PurchaseOrdersDto> getUserActivityData(String Query, String innerJoin,
			Integer companyId) {
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT p.purchaseorder_id, p.purchaseorder_Number, p.purchaseorder_typeId, p.purchaseorder_vendor_id, VN.vendorName,"
						+ " VN.vendorLocation, p.created, p.purchaseorder_requied_on,"
						+ " p.purchaseorder_termsId, p.purchaseorder_quotenumber, p.purchaseorder_indentno, WO.workorders_Number, PL.partlocation_name,"
						+ " p.purchaseorder_statusId, p.purchaseorder_document, p.purchaseorder_document_id, p.purchaseorder_vendor_paymodeId,"
						+ " p.purchaseorder_totalcost, p.purchaseorder_balancecost , C.company_name, C.company_address, C.company_city, C.company_state, C.company_country, C.company_pincode"
						+ " , p.purchaseorder_invoiceno, p.purchaseorder_invoice_date, p.paidAmount, p.balanceAmount,p.markForDelete,p.lastupdated,U.firstName, U.lastName ,U.id "  
						+ " From PurchaseOrders as p"
						+ " INNER JOIN Company C ON C.company_id = p.companyId"
						+ " "+innerJoin+" "
						+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = p.purchaseorder_workordernumber"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = p.purchaseorder_vendor_id"
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = p.purchaseorder_shiplocation_id"
						+ " where  p.companyId = " + companyId + " "
						+ Query + "  order by p.purchaseorder_invoice_date desc",
				Object[].class);
		List<Object[]> results = query.getResultList();

		List<PurchaseOrdersDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersDto>();
			PurchaseOrdersDto list = null;
			for (Object[] result : results) {
				list = new PurchaseOrdersDto();

				list.setPurchaseorder_id((Long) result[0]);
				list.setPurchaseorder_Number((Long) result[1]);
				list.setPurchaseorderNumberStr("");
				list.setPurchaseorder_typeId((short) result[2]);
				list.setPurchaseorder_type(PurchaseOrderType.getPurchaseorderTypeName(list.getPurchaseorder_typeId()));
				list.setPurchaseorder_vendor_id((Integer) result[3]);
				list.setPurchaseorder_vendor_name((String) result[4]);
				list.setPurchaseorder_vendor_location((String) result[5]);
				if(result[6] != null) {
					list.setPurchaseorder_created((Date) result[6]);
					list.setPurchaseorder_created_on(dateFormatTime.format((Date) result[6]));
				}
				list.setPurchaseorder_requied((Date) result[7]);
				if(list.getPurchaseorder_requied() != null)
				list.setPurchaseorder_requied_on(dateFormat.format(list.getPurchaseorder_requied()));	
				list.setPurchaseorder_termsId((short) result[8]);
				list.setPurchaseorder_terms(PaymentTypeConstant.getPaymentTypeName(list.getPurchaseorder_termsId()));
				
				list.setPurchaseorder_quotenumber((String) result[9]);
				list.setPurchaseorder_indentno((String) result[10]);
				list.setPurchaseorder_workordernumber((Long) result[11] + "");
				list.setPurchaseorder_shiplocation((String) result[12]);
				list.setPurchaseorder_statusId((short) result[13]);
				list.setPurchaseorder_document((boolean) result[14]);
				list.setPurchaseorder_document_id((Long) result[15]);
				list.setPurchaseorder_vendor_paymodeId((short) result[16]);
				if( result[17] != null )
				list.setPurchaseorder_totalcost(Double.parseDouble(toFixedTwo.format((double)result[17])));
				if( result[18] != null)
				list.setPurchaseorder_balancecost(Double.parseDouble(toFixedTwo.format((double)result[18])));
				
				list.setPurchaseorder_buyer((String) result[19]);
				list.setPurchaseorder_buyeraddress((String) result[20]+" , "+ (String) result[21]+"  , "+(String) result[22]+" , "+(String) result[23]+" . PIN- "+(String) result[24]);
				list.setPurchaseorder_invoiceno((String) result[25]);
				list.setPurchaseorder_invoice_date_On((Date) result[26]);
				
				if(result[27]!= null) {
					list.setPaidAmount(Double.parseDouble(toFixedTwo.format(result[27])));
				}
				if(result[28]!= null) {
					list.setBalanceAmount(Double.parseDouble(toFixedTwo.format(result[28])));
				}
				
				list.setMarkForDelete((boolean)result[29]);
				if(!list.isMarkForDelete()) {
					list.setPurchaseorderNumberStr("<a target=\"_blank\" style=\"color: blue; background: #ffc;\"  href=\"/PurchaseOrders_Parts.in?ID="+list.getPurchaseorder_id()+" \"> PO-"+list.getPurchaseorder_Number()+" </a>");
				}else {
					list.setPurchaseorderNumberStr("<a class=\"tooltipClass\" style=\"color: red; background: #ffc;\"  href=\"# \" data-toggle=\"tip\" data-original-title=\"Deleted Data\"> PO-"+list.getPurchaseorder_Number()+" </a>");
				}
				if(result[30] != null)
				list.setLastupdated(dateFormatTime.format((Date)result[30]));
				
				if(list.getLastupdated() != null && list.getPurchaseorder_created_on() != null && list.getLastupdated().equals(list.getPurchaseorder_created_on()))
				list.setLastupdated("");
				
				list.setUserName(result[31]+" "+result[32]);

				Dtos.add(list);
			}
		}
		return Dtos;

	}

	@Override
	public PurchaseOrdersToParts preparePurchaseOrdersTaskToPart(String partId, String quantityStr, String eachCostStr,
			String discountStr, String taxStr) {

		PurchaseOrdersToParts purchasePart = new PurchaseOrdersToParts();
		if(partId != null && !partId.trim().equals("")) {
			purchasePart.setPartid(Long.valueOf(partId));
			Double quantity = 0.0;
			Double eachCost = 0.0;
			Double discount = 0.0;
			Double tax = 0.0;
			if(quantityStr != null && !quantityStr.equals("")) 
				quantity = Double.valueOf(quantityStr);
			if(eachCostStr != null && ! eachCostStr.equals("")) 
				eachCost = Double.valueOf(eachCostStr);
			if(discountStr != null && ! discountStr.equals("")) 
				discount = Double.valueOf(discountStr);
			if(taxStr != null && ! taxStr.equals("")) 
				tax = Double.valueOf(taxStr);
			InventoryAllDto invent = null;
			try {
				invent = InventoryService.Purchase_Order_Validate_InventoryAll(purchasePart.getPartid());
				if (invent != null && invent.getAll_quantity() != null && invent.getAll_quantity() != 0.0) {
					purchasePart.setInventory_all_id(invent.getInventory_all_id());
					purchasePart.setInventory_all_quantity(invent.getAll_quantity());
			} else {
				purchasePart.setInventory_all_id((long) 0);
				purchasePart.setInventory_all_quantity(0.0);
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Double discountCost = 0.0;
			Double TotalCost = 0.0;
			discountCost 	= (quantity * eachCost) - ((quantity * eachCost) * (discount / 100));
			TotalCost 		= round(((discountCost) + (discountCost * (tax / 100))), 2);
			purchasePart.setQuantity(quantity);
			purchasePart.setReceived_quantity(0.0);
			purchasePart.setNotreceived_quantity(quantity);
			purchasePart.setRequestedQuantity(quantity);
			purchasePart.setParteachcost(eachCost);
			purchasePart.setDiscount(discount);
			purchasePart.setTax(tax);
			purchasePart.setTotalcost(TotalCost);
		}
		return purchasePart;
	
	}
	
	@Override
	@Transactional
	public ValueObject approvalPO(ValueObject valueObject) throws Exception {
		try {
			
			updateApprovalStatusOfPO(PurchaseOrderType.PURCHASE_STATUS_APPROVED, valueObject.getString("remark"), 
									valueObject.getLong("userId",0), DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getCurrentTimeStamp(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS), 
									valueObject.getLong("purchaseOrderId"));
			
			valueObject.put("success", true);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	private void updateApprovalStatusOfPO(short status, String remark, Long userId, String	updatedTime, Long poId) throws Exception {
		try {
			entityManager.createQuery("UPDATE PurchaseOrders SET approvalStatus = "+status+", approvedById = "+userId+", "
					+ " approvalRemark = '"+remark+"', lastupdated = '"+updatedTime+"',lastModifiedById = "+userId+" "
					+ " where purchaseorder_id = "+poId+"  " ).executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updatePOApprovalStatus(short status, Long purchaserOrderId) throws Exception {
		try {
			entityManager.createQuery("UPDATE PurchaseOrders SET approvalStatus = "+status+""
					+ " where purchaseorder_id = "+purchaserOrderId+"  " ).executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getPurchaseOrderStatusWiseReport(ValueObject valueObject) throws Exception {
		
		String							dateRange				= null;
		int								status 					= 0;
		int 							vendorName 				= 0;
		CustomUserDetails				userDetails				= null;
		List<PurchaseOrderReportDto> 	Inven					= null;
		List<PurchaseOrderReportDto> 	ureaList				= null;
		List<PurchaseOrderReportDto> 	finalList				= null;
		
		StringBuffer query = new StringBuffer();
		
		try
		{
			HashMap<Long, List<PurchaseOrderReportDto>>  map = new HashMap<Long, List<PurchaseOrderReportDto>>();
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dateRange		= valueObject.getString("dateRange");
			status		= valueObject.getInt("status",0);
			vendorName			= valueObject.getInt("vendorName",0);
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				
				
				if(vendorName > 0)
					query.append(" AND P.purchaseorder_vendor_id =  "+vendorName+" ");
				
				if(status > 0)
					query.append("AND P.purchaseorder_statusId = "+status+" ");
				
				query.append("AND P.purchaseorder_created_on between '" + dateRangeFrom + "' AND '" + dateRangeTo +"'");
				
				
				Inven 	= InventoryService.Purchase_Order_StatusWise_Report(query.toString(), userDetails.getCompany_id());
				ureaList = InventoryService.Purchase_Order_StatusWise_Report_urea(query.toString(), userDetails.getCompany_id());
				
				
				if((Inven != null && !Inven.isEmpty()) || (ureaList != null && !ureaList.isEmpty()) ) {
					
					finalList = new ArrayList<>();
					
					if(Inven != null && !Inven.isEmpty())
					{
						finalList.addAll(Inven);
					}
					if(ureaList != null && !ureaList.isEmpty())
					{
						finalList.addAll(ureaList);
					}
					
					
				}
				
				List<PurchaseOrderReportDto> purchseOrderDataList	= null;
				
				if(Inven != null && !Inven.isEmpty() || (ureaList != null && !ureaList.isEmpty())) {
					
					
					PurchaseOrderReportDto tempDto = null;
					for (PurchaseOrderReportDto inventoryDto : finalList) {
			
						
						tempDto = inventoryDto;
						if(inventoryDto.getPurchaseOrderTypeId() == PurchaseOrderType.PURCHASEORDER_TYPE_PART_PO)
						{
							tempDto.setPurchaseorder_partname(inventoryDto.getPurchaseorder_partnumber()+"_"+inventoryDto.getPurchaseorder_partname());
						}
						if(inventoryDto.getPurchaseOrderTypeId() == PurchaseOrderType.PURCHASEORDER_TYPE_TYRE_PO)
						{
							tempDto.setPurchaseorder_partname(inventoryDto.getTYRE_MANUFACTURER());
						}
						if(inventoryDto.getPurchaseOrderTypeId() == PurchaseOrderType.PURCHASEORDER_TYPE_BATTERY_PO)
						{
							tempDto.setPurchaseorder_partname(inventoryDto.getBATTERY_MANUFACTURER()+ "_" + inventoryDto.getBattery_partNumber() +"-"+"("+inventoryDto.getWarrantyPeriod()+"-"+BatteryTypeDto.getWarrantyTypeName(inventoryDto.getWarrantyTypeId())+")");
						}
						
						if(inventoryDto.getReceived_quantity() < inventoryDto.getQuantity() )
						{
							tempDto.setPartialReceived(0);
						}
						else
						{
							tempDto.setPartialReceived(1);
						}
						
						tempDto.setPurchaseOrder_StatusName(PurchaseOrderState.getPurchaseOrderStateName(inventoryDto.getPurchaseorder_status()));
						
						purchseOrderDataList = map.get(inventoryDto.getPurchaseOrder_id());
						
						if(purchseOrderDataList == null || purchseOrderDataList.isEmpty()) {
							purchseOrderDataList = new ArrayList<PurchaseOrderReportDto>();
						}
						purchseOrderDataList.add(tempDto);
						map.put(inventoryDto.getPurchaseOrder_id(), purchseOrderDataList);
					}
				}
				
				valueObject.put("PurchaseOrderReport", finalList);
				valueObject.put("PurchaseOrderMap", map);
				
				if(vendorName > 0)
				{ 
					if(finalList != null)
					{
						valueObject.put("VendorName", finalList.get(0).getPurchaseorder_vendor_name());
					}
				}
				else {
					valueObject.put("VendorName", "All");
				}
			
				
				if(status > 0)
				{
					if(finalList != null)
					{
						valueObject.put("status", PurchaseOrderState.getPurchaseOrderStateName( finalList.get(0).getPurchaseorder_status()));
					}
				} else {
						valueObject.put("status", "All");
				}
			}
			
			
		}
		catch (Exception e) {
			LOGGER.error("Exception", e);
			throw e;
		}finally {
			Inven				= null;
			userDetails			= null;
			dateRange			= null;
		}
		
		return valueObject;
	}
	
	
	@Transactional
	public Page<PurchaseOrders> getDeployment_Page_PurchaseOrdersApproval(short status, Integer pageNumber, Integer companyId) {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		
		short poStatusId = PurchaseOrderType.PURCHASEORDER_STATUS_REQUISITION;
		return PurchaseOrdersDao.getDeployment_Page_PurchaseOrdersByApprovalStatus(status, companyId, poStatusId,pageable );
		
	}
	
	@Transactional
	public List<PurchaseOrdersDto> listOpenPurchaseOrdersByApprovalStatus(short status, Integer pageNumber, Integer companyId) {
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT p.purchaseorder_id, p.purchaseorder_Number, p.purchaseorder_typeId, p.purchaseorder_vendor_id, VN.vendorName,"
						+ " VN.vendorLocation, p.purchaseorder_created_on, p.purchaseorder_requied_on,"
						+ " p.purchaseorder_termsId, p.purchaseorder_quotenumber, p.purchaseorder_indentno, WO.workorders_Number, PL.partlocation_name,"
						+ " p.purchaseorder_statusId, p.purchaseorder_document, p.purchaseorder_document_id, C.company_name, C.company_address, C.company_city, C.company_state, C.company_country, C.company_pincode,"
						+ " p.purchaseorder_invoiceno,p.purchaseorder_vendor_paymentdate, p.subCompanyId, SC.subCompanyName "
						+ " From PurchaseOrders as p "
						+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = p.purchaseorder_workordernumber"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = p.purchaseorder_vendor_id "
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = p.purchaseorder_shiplocation_id"
						+ " LEFT JOIN Company C ON C.company_id = p.purchaseorder_buyer_id"
						+ " LEFT JOIN SubCompany SC ON SC.subCompanyId = p.subCompanyId"
						+ " where p.approvalStatus=" + status + " AND p.companyId = " + companyId 
						+ " AND p.markForDelete = 0 AND  p.purchaseorder_statusId = "+ PurchaseOrderType.PURCHASEORDER_STATUS_REQUISITION+ " ORDER BY p.purchaseorder_id desc ",
				Object[].class);
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		List<Object[]> results = query.getResultList();

		List<PurchaseOrdersDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<PurchaseOrdersDto>();
			PurchaseOrdersDto list = null;
			for (Object[] result : results) {
				list = new PurchaseOrdersDto();

				list.setPurchaseorder_id((Long) result[0]);
				list.setPurchaseorder_Number((Long) result[1]);
				list.setPurchaseorder_typeId((short) result[2]);
				list.setPurchaseorder_vendor_id((Integer) result[3]);
				list.setPurchaseorder_vendor_name((String) result[4]);
				list.setPurchaseorder_vendor_location((String) result[5]);
				list.setPurchaseorder_created((Date) result[6]);
				list.setPurchaseorder_requied((Date) result[7]);
				list.setPurchaseorder_termsId((short) result[8]);
				list.setPurchaseorder_quotenumber((String) result[9]);
				list.setPurchaseorder_indentno((String) result[10]);
				list.setPurchaseorder_workordernumber((Long) result[11] + "");
				list.setPurchaseorder_shiplocation((String) result[12]);
				list.setPurchaseorder_statusId((short) result[13]);
				list.setPurchaseorder_document((boolean) result[14]);
				list.setPurchaseorder_document_id((Long) result[15]);
				if(result[24] != null ) {
					list.setPurchaseorder_buyer((String) result[25]);
				}else {
					list.setPurchaseorder_buyer((String) result[16]);
				}
				list.setPurchaseorder_buyeraddress((String) result[17]+" , "+ (String) result[18]+"  , "+(String) result[19]+" , "+(String) result[20]+" . PIN- "+(String) result[21]);
				list.setPurchaseorder_invoiceno((String) result[22]);
				list.setPurchaseorder_vendor_paymentdate((Timestamp) result[23]);
				
				Dtos.add(list);
			}
		}
		return Dtos;

	}
	
	@Override
	@Transactional
	public ValueObject SavePoRemark(ValueObject valueObject) throws Exception {
		try {
			updateRemarkOfPO(valueObject.getString("remark"), valueObject.getLong("purchaseOrderId"));
			valueObject.put("success", true);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	private void updateRemarkOfPO(String remark, Long poId) throws Exception {
		try {
			entityManager.createQuery("UPDATE PurchaseOrders SET  purchaseorder_orderd_remark = '"+remark+"'  where purchaseorder_id = "+poId+"  " ).executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject getPoRemark(ValueObject valueObject) throws Exception {
		try {
			String remark = getRemarkOfPO(valueObject.getLong("purchaseOrderId"));
			valueObject.put("success", true);
			valueObject.put("remark", remark);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	private String getRemarkOfPO(Long poId) throws Exception {
		try {
			Query query = entityManager.createQuery("SELECT po.purchaseorder_orderd_remark FROM PurchaseOrders po WHERE po.purchaseorder_id = :poId");
			query.setParameter("poId", poId);
			Object result = query.getSingleResult();
			String remark = (String) result;
			return remark;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public PurchaseOrders getPurchaseOrderId(Long purchaseorder_Number ,Integer companyId) {
		
		return PurchaseOrdersDao.getPurchaseOrdersID(purchaseorder_Number, companyId);
	}
	
	@Override
	public Object[] countTotalPurchaseOrder(CustomUserDetails userDetails) throws Exception{
		
		String cond ="";
		HashMap<String, Object> 				poConfiguration       	 		= null;
		
		try {
			poConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PURCHASE_ORDER_CONFIG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if((boolean)poConfiguration.get("requisitionApproved")) {
			cond = " AND P.approvalStatus = "+PurchaseOrderType.PURCHASE_STATUS_NOT_APPROVED+" ";
		}
		
		Query queryt =	null;
			queryt = entityManager
					.createQuery("SELECT COUNT(co),"
							+ "(SELECT  COUNT(P) FROM PurchaseOrders AS P where  P.purchaseorder_statusId ="+PurchaseOrderType.PURCHASEORDER_STATUS_REQUISITION+" "
							+ cond
							+ "AND  P.companyId = "+userDetails.getCompany_id()+" AND P.markForDelete = 0 ) ,"
							+ "(SELECT  COUNT(OD) FROM PurchaseOrders AS OD where OD.purchaseorder_statusId ="+PurchaseOrderType.PURCHASEORDER_STATUS_ORDERED+" AND OD.companyId = "+userDetails.getCompany_id()+" AND OD.markForDelete = 0 ), " 
							+ "(SELECT  COUNT(R) FROM PurchaseOrders AS R where R.purchaseorder_statusId ="+PurchaseOrderType.PURCHASEORDER_STATUS_RECEIVED+" AND R.companyId = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ), " 
							+ "(SELECT  COUNT(C) FROM PurchaseOrders AS C where C.purchaseorder_statusId ="+PurchaseOrderType.PURCHASEORDER_STATUS_COMPLETED+" AND C.companyId = "+userDetails.getCompany_id()+" AND C.markForDelete = 0 ), " 
							+ "(SELECT  COUNT(RC) FROM PurchaseOrders AS RC where  RC.purchaseorder_statusId ="+PurchaseOrderType.PURCHASEORDER_STATUS_REQUISITION+" AND RC.approvalStatus = "+PurchaseOrderType.PURCHASE_STATUS_APPROVED+" AND  RC.companyId = "+userDetails.getCompany_id()+" AND RC.markForDelete = 0 ) "
							+ " FROM PurchaseOrders As co  "
							+ " where co.companyId = "+userDetails.getCompany_id()+" AND co.markForDelete = 0 ");
			
			Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}
	
}
