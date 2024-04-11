package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.DateFormat;
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
import org.fleetopgroup.constant.InventoryStockTypeConstant;
import org.fleetopgroup.constant.InventoryTransferStatus;
import org.fleetopgroup.constant.InventoryTransferViaType;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PartInventoryConfigurationConstants;
import org.fleetopgroup.constant.PartType;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SequenceTypeContant;
import org.fleetopgroup.constant.TallyVoucherTypeContant;
import org.fleetopgroup.persistence.bl.InventoryBL;
import org.fleetopgroup.persistence.bl.PartInvoiceBL;
import org.fleetopgroup.persistence.dao.InventoryAllRepository;
import org.fleetopgroup.persistence.dao.InventoryLocationRepository;
import org.fleetopgroup.persistence.dao.InventoryRepository;
import org.fleetopgroup.persistence.dao.InventoryTransferRepository;
import org.fleetopgroup.persistence.dao.PartInvoiceRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryAllDto;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.InventoryLocationDto;
import org.fleetopgroup.persistence.dto.InventoryTransferDto;
import org.fleetopgroup.persistence.dto.PartInvoiceDto;
import org.fleetopgroup.persistence.dto.PurchaseOrderReportDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.model.Inventory;
import org.fleetopgroup.persistence.model.InventoryAll;
import org.fleetopgroup.persistence.model.InventoryLocation;
import org.fleetopgroup.persistence.model.InventorySequenceCounter;
import org.fleetopgroup.persistence.model.InventoryTransfer;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.model.PartInvoice;
import org.fleetopgroup.persistence.model.PartMeasurementUnit;
import org.fleetopgroup.persistence.model.RepairFromAssetPart;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDayWiseInventoryStockService;
import org.fleetopgroup.persistence.serviceImpl.IInventorySequenceService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.IPartInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IPartMeasurementUnitService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service("InventoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class InventoryService implements IInventoryService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IInventorySequenceService 			inventorySequenceService;
	@Autowired IPartInvoiceService		partInvoiceService;
	@Autowired IBankPaymentService		bankPaymentService;
	@Autowired IVendorService			vendorService;
	
	InventoryBL 	inventoryBL	 	= new InventoryBL();
	PartInvoiceBL	PIBL			= new PartInvoiceBL();
	
	
	SimpleDateFormat sqlDateFormat 	= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateName = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	java.util.Date currentDate = new java.util.Date();
	DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat tallyFormat		= new SimpleDateFormat("yyyyMMdd");
	DecimalFormat toFixedTwo    =new DecimalFormat("#.##");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	public static Long DEFAULT_PURCHAGE_ORDER_VALUE = (long) 0;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private InventoryRepository InventoryDao;

	@Autowired
	private InventoryAllRepository InventoryAllRepository;

	@Autowired
	private InventoryLocationRepository InventoryLocationRepository;

	@Autowired
	private InventoryTransferRepository InventoryTransferRepository;
	
	@Autowired
	private PartInvoiceRepository PartInvoiceRepository;

	@Autowired private	ICompanyConfigurationService		companyConfigurationService;
	@Autowired private	IMasterPartsService					masterPartsService;
	@Autowired private	IDayWiseInventoryStockService		dayWiseInventoryStockService;
	@Autowired private	IPendingVendorPaymentService		pendingVendorPaymentService;
	@Autowired private	IPartMeasurementUnitService 		partMeasurementUnitService;

	private static final int PAGE_SIZE = 10;

	private static final int PAGE_SIZE_FIFTY = 50;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateInventory(Inventory inventory) throws Exception {
		InventoryDao.save(inventory);
	}

	@Transactional
	public List<Inventory> listInventory() throws Exception {

		TypedQuery<Inventory> query = entityManager.createQuery("FROM Inventory as inv ORDER BY inv.inventoryid desc",
				Inventory.class);
		query.setFirstResult(0);
		query.setMaxResults(5);
		return query.getResultList();
	}

	@Transactional
	public List<Inventory> findAllListInventory(Integer companyId) throws Exception {
		return InventoryDao.findAllInventoryList(companyId);
	}

	@Transactional
	public List<Inventory> SearchlistInventory(String Search) throws Exception {
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<Inventory> query = entityManager.createQuery(
				"FROM Inventory WHERE lower(partnumber) Like ('%" + Search + "%') OR lower(partname) Like ('%" + Search
						+ "%') OR lower(location) Like ('%" + Search + "%')",
				Inventory.class);
		return query.getResultList();
		}else {
			return null;
		}
	}

	@Transactional
	public List<InventoryDto> SearchlistInventoryINVoice(String Search, Integer companyId) throws Exception {
		List<InventoryDto> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> query = entityManager.createQuery("SELECT I.inventory_id, I.inventory_Number, I.partid, MP.partnumber, MP.partname, MP.partTypeId, PC.pcName,  PM.pmName, PL.partlocation_name,  I.quantity,"
				+ " I.history_quantity, I.unitprice, I.purchaseorder_id, PO.purchaseorder_Number, I.invoice_number, I.invoice_amount, I.invoice_date, I.vendor_id, V.vendorName, V.vendorLocation, "
				+ " MU.pmuSymbol, I.discount, I.tax, I.total, I.description, I.inventory_location_id, "
				+ " I.inventory_all_id, I.locationId, I.createdById, U.firstName, U.lastName, MP.unitTypeId "
				+ "  FROM Inventory AS I "
				+ " INNER JOIN User AS U ON U.id = I.createdById "			
				+ " INNER JOIN MasterParts AS MP ON MP.partid = I.partid "
				+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
				+ " INNER JOIN PartLocations PL ON PL.partlocation_id = I.locationId "
				+ " INNER JOIN InventoryAll IAL ON IAL.inventory_all_id = I.inventory_all_id "
				+ " LEFT JOIN Vendor AS V ON V.vendorId = I.vendor_id "
				+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
				+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = I.purchaseorder_id "
				+ " LEFT JOIN PartMeasurementUnit MU ON MU.pmuid = MP.unitTypeId"
				+ " WHERE lower(invoice_number) Like ('%" + Search + "%') AND IAL.companyId = " + companyId
				+ " AND IAL.markForDelete = 0", Object[].class);
		List<Object[]> results = query.getResultList();

		
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
					Dto.setQuantity((Double) result[9]);
				}

				if ((Double) result[10] != null) {
					Dto.setHistory_quantity((Double) result[10]);
				}

				if ((Double) result[11] != null) {
					Dto.setUnitprice(round((Double) result[11], 2));
				}
				Dto.setPurchaseorder_id((Long) result[12]);
				Dto.setPurchaseorder_Number((Long) result[13]);
				Dto.setInvoice_number((String) result[14]);
				Dto.setInvoice_amount((String) result[15]);
				if ((Date) result[16] != null) {
					Dto.setInvoice_date(dateName.format((Date) result[16]));
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
				
				Dto.setLocationId((Integer) result[27]);
				
				if((Long) result[28] != null ) {					
				Dto.setCreatedById((Long) result[28]);
				}
				if((String) result[29] != null){
				Dto.setFirstName((String) result[29]);
				}
				if((String) result[30] != null) {
					Dto.setLastName((String) result[30]);
				}
				Dto.setUnitTypeId((long) result[31]);

				Dtos.add(Dto);
			}
		}
		}
		return Dtos;
	}

	@Transactional
	public List<InventoryAllDto> SearchlistInventoryAll(String Search, Integer companyId) throws Exception {

		try {
			TypedQuery<Object[]> queryt = null;
			List<InventoryAllDto> Dtos = null;
			
			HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG);
			if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
			if(!(boolean) configuration.get("groupInventoryOnPartName")) {
				queryt = entityManager
						.createQuery(
								"SELECT inv.inventory_all_id, inv.partid, MP.partnumber, MP.partname, PC.pcName, "
										+ "inv.all_quantity, MP.unitTypeId FROM InventoryAll as inv "
										+ " INNER JOIN MasterParts AS MP ON MP.partid = inv.partid "
										+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
										+ " WHERE (lower(MP.partnumber) Like ('%" + Search
										+ "%') OR lower(MP.partname) Like ('%" + Search + "%') ) AND inv.companyId = "
										+ companyId + " AND inv.markForDelete = 0 ORDER BY inv.inventory_all_id desc ",
								Object[].class);
			}else {
				queryt = entityManager
						.createQuery(
								"SELECT inv.inventory_all_id, inv.partid, MP.partnumber, MP.partname, PC.pcName, "
										+ " SUM(inv.all_quantity), MP.unitTypeId FROM InventoryAll as inv "
										+ " INNER JOIN MasterParts AS MP ON MP.partid = inv.partid "
										+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
										+ " WHERE (lower(MP.partnumber) Like ('%" + Search
										+ "%') OR lower(MP.partname) Like ('%" + Search + "%') ) AND inv.companyId = "
										+ companyId + " AND inv.markForDelete = 0 GROUP BY MP.partname  ORDER BY inv.inventory_all_id desc ",
								Object[].class);
			}
			

			List<Object[]> results = queryt.getResultList();

			
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryAllDto>();
				InventoryAllDto inve = null;
				for (Object[] result : results) {
					inve = new InventoryAllDto();

					inve.setInventory_all_id((Long) result[0]);
					inve.setPartid((Long) result[1]);
					inve.setPartnumber((String) result[2]);
					inve.setPartname((String) result[3]);
					inve.setCategory((String) result[4]);
					inve.setAll_quantity((Double) result[5]);
					inve.setUnitTypeId((long) result[6]);

					Dtos.add(inve);
				}
			}
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<InventoryLocationDto> SearchlistInventoryLocationOnLocation(String Search, Integer companyId,
			Integer locationId) throws Exception {
		List<InventoryLocationDto> Dtos = null;
		HashMap<String, Object> 	configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG);
		TypedQuery<Object[]> queryt =  null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		if(!(boolean) configuration.get("groupInventoryOnPartName")) {
			queryt = entityManager.createQuery(
					"SELECT R.inventory_location_id, R.partid, MP.partnumber, MP.partname, PC.pcName, PL.partlocation_name, "
							+ " R.location_quantity, PM.pmName, MP.isWarrantyApplicable, MP.isRepairable, R.locationId,"
							+ " MP.unitTypeId, R.inventoryall.inventory_all_id"
							+ " FROM InventoryLocation AS R "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = R.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.locationId "
							+ " WHERE (lower(MP.partnumber) Like ('%" + Search + "%') OR lower(MP.partname) Like ('%"
							+ Search + "%') OR lower(PL.partlocation_name) Like ('%" + Search + "%') OR lower(MP.localPartName) Like ('%" + Search + "%') "
							+ " OR lower(MP.partNameOnBill) Like ('%" + Search + "%') )  AND R.companyId = "
							+ companyId + " AND R.locationId = "+locationId+" AND R.markForDelete = 0 AND R.location_quantity > 0 ORDER BY R.inventory_location_id desc",
					Object[].class);
		}else {
			queryt = entityManager.createQuery(
					"SELECT R.inventory_location_id, R.partid, MP.partnumber, MP.partname, PC.pcName, PL.partlocation_name, "
							+ " SUM(R.location_quantity), PM.pmName, MP.isWarrantyApplicable, MP.isRepairable, R.locationId,"
							+ " MP.unitTypeId, R.inventoryall.inventory_all_id"
							+ " FROM InventoryLocation AS R "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = R.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.locationId "
							+ " WHERE (lower(MP.partnumber) Like ('%" + Search + "%') OR lower(MP.partname) Like ('%"
							+ Search + "%') OR lower(PL.partlocation_name) Like ('%" + Search + "%') OR lower(MP.localPartName) Like ('%" + Search + "%') "
							+ " OR lower(MP.partNameOnBill) Like ('%" + Search + "%') )  AND R.companyId = "
							+ companyId + " AND R.locationId = "+locationId+" AND R.markForDelete = 0 AND R.location_quantity > 0 group by MP.partname ORDER BY R.inventory_location_id desc",
					Object[].class);
		}
		
		List<Object[]> results = queryt.getResultList();

		
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryLocationDto>();
			InventoryLocationDto list = null;
			for (Object[] result : results) {
				list = new InventoryLocationDto();

				list.setInventory_location_id((Long) result[0]);
				list.setPartid((Long) result[1]);
				list.setPartnumber((String) result[2]);
				list.setPartname((String) result[3]);
				list.setCategory((String) result[4]);
				list.setLocation((String) result[5]);
				list.setLocation_quantity((Double) result[6]);
				list.setPartManufacturer((String) result[7]);
				list.setWarrantyApplicable((boolean) result[8]);
				list.setRepairable((boolean) result[9]);
				list.setLocationId((Integer) result[10]);
				list.setUnitTypeId((long) result[11]);
				list.setInventory_all_id((long) result[12]);

				Dtos.add(list);
			}
		}
		
		if(Dtos != null && !Dtos.isEmpty()) {
			Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
			inventoryBL.getFinalInventoryLocationDto(Dtos, measermentHM);
		}
		}
		return Dtos;
	}

	@Transactional
	public List<InventoryLocationDto> SearchlistInventoryLocation(String Search, Integer companyId) throws Exception {
		
		TypedQuery<Object[]> queryt =  null;
		List<InventoryLocationDto> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
			queryt = entityManager.createQuery(
					"SELECT R.inventory_location_id, R.partid, MP.partnumber, MP.partname, PC.pcName, PL.partlocation_name, "
							+ " R.location_quantity, PM.pmName, MP.isWarrantyApplicable, MP.isRepairable, R.locationId,"
							+ " MP.unitTypeId, R.inventoryall.inventory_all_id"
							+ " FROM InventoryLocation AS R "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = R.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.locationId "
							+ " WHERE (lower(MP.partnumber) Like ('%" + Search + "%') OR lower(MP.partname) Like ('%"
							+ Search + "%') OR lower(PL.partlocation_name) Like ('%" + Search + "%') OR lower(MP.localPartName) Like ('%" + Search + "%') "
							+ " OR lower(MP.partNameOnBill) Like ('%" + Search + "%') )  AND R.companyId = "
							+ companyId + " AND R.markForDelete = 0 AND R.location_quantity > 0 ORDER BY R.inventory_location_id desc",
					Object[].class);
		
		
		List<Object[]> results = queryt.getResultList();

		
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryLocationDto>();
			InventoryLocationDto list = null;
			for (Object[] result : results) {
				list = new InventoryLocationDto();

				list.setInventory_location_id((Long) result[0]);
				list.setPartid((Long) result[1]);
				list.setPartnumber((String) result[2]);
				list.setPartname((String) result[3]);
				list.setCategory((String) result[4]);
				list.setLocation((String) result[5]);
				list.setLocation_quantity((Double) result[6]);
				list.setPartManufacturer((String) result[7]);
				list.setWarrantyApplicable((boolean) result[8]);
				list.setRepairable((boolean) result[9]);
				list.setLocationId((Integer) result[10]);
				list.setUnitTypeId((long) result[11]);
				list.setInventory_all_id((long) result[12]);

				Dtos.add(list);
			}
		}
		
		if(Dtos != null && !Dtos.isEmpty()) {
			Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
			inventoryBL.getFinalInventoryLocationDto(Dtos, measermentHM);
		}
		}
		return Dtos;
	}

	@Transactional
	public List<InventoryAll> Validate_InventoryAll(Long part_id, Integer companyId) throws Exception {

		return InventoryAllRepository.Validate_InventoryAll(part_id, companyId);

	}

	@Transactional
	public InventoryAllDto Purchase_Order_Validate_InventoryAll(Long part_id) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {
			Query query = entityManager
					.createQuery("SELECT inv.inventory_all_id, inv.partid, MP.partnumber, MP.partname, PC.pcName, "
							+ "inv.all_quantity, LO.unitCost, LO.discount, LO.tax, MP.unitTypeId "
							+ " FROM InventoryAll as inv "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = inv.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " LEFT JOIN LowStockInventory LO ON LO.partid = inv.partid AND LO.companyId = " + userDetails.getCompany_id()
							+ " WHERE inv.partid = "+part_id+" AND inv.all_quantity > 0.0 AND inv.companyId = " + userDetails.getCompany_id()
							+ " AND inv.markForDelete = 0 ORDER BY inv.inventory_all_id desc ");

			Object[] results = null;
			try {
				results = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			InventoryAllDto inve = null;
			if (results != null) {
				inve = new InventoryAllDto();

				inve.setInventory_all_id((Long) results[0]);
				inve.setPartid((Long) results[1]);
				inve.setPartnumber((String) results[2]);
				inve.setPartname((String) results[3]);
				inve.setCategory((String) results[4]);
				inve.setAll_quantity((Double) results[5]);
				if(results[6] != null) {
					inve.setUnitCost((double) results[6]);
					}
					if(results[7] != null) {
						inve.setDiscount((double) results[7]);
					}
					if(results[8] != null) {
						inve.setTax((double) results[8]);
					}
					inve.setUnitTypeId((long) results[9]);

			} else {
				return null;
			}
			return inve;

		} catch (Exception e) {
			throw e;
		}

	}

	@Transactional
	public List<InventoryLocation> Validate_Inventory_Location(Long part_id, Integer locationId) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return InventoryLocationRepository.Validate_Inventory_Location(part_id, locationId,
				userDetails.getCompany_id());
	}

	@Transactional
	public InventoryDto getInventory(Long sid) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Query query = null; 
		try {
			query = entityManager.createQuery(
					"SELECT I.inventory_id, I.inventory_Number, I.partid, MP.partnumber, MP.partname, MP.partTypeId, I.locationId, PC.pcName,  PM.pmName, MP.part_photoid, PL.partlocation_name,  I.quantity,"
							+ " I.history_quantity, I.unitprice, I.purchaseorder_id, PO.purchaseorder_Number, I.invoice_number, I.invoice_amount, I.invoice_date, I.vendor_id, V.vendorName, V.vendorLocation, "
							+ " MU.pmuSymbol, I.discount, I.tax, I.total, I.description, I.inventory_location_id, I.inventory_all_id, V.vendorTypeId,"
							+ " U.email, U2.email, I.created, I.lastupdated,I.partInvoiceId,I.subLocationId, "
							+ " PSL.partlocation_name, MP.unitTypeId, I.mainQuantity,I.mainUnitprice  "
							+ " FROM Inventory AS I "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = I.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = I.locationId "
							+ " INNER JOIN InventoryAll IAL ON IAL.inventory_all_id = I.inventory_all_id "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = I.vendor_id "
							+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
							+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = I.purchaseorder_id "
							+ " LEFT JOIN PartMeasurementUnit MU ON MU.pmuid = MP.unitTypeId"
							+ " LEFT JOIN User U ON U.id = I.createdById"
							+ " LEFT JOIN User U2 ON U2.id = I.lastModifiedById"
							+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = I.subLocationId "
							+ " WHERE I.inventory_id = :Invid AND IAL.companyId = :comID AND I.markForDelete = 0 ",
					Object[].class);
			query.setParameter("Invid", sid);
			query.setParameter("comID", userDetails.getCompany_id());

			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			InventoryDto Dto = null;
			if (result != null) {
				Dto = new InventoryDto();

				Dto.setInventory_id((Long) result[0]);
				Dto.setInventory_Number((Long) result[1]);
				Dto.setPartid((Long) result[2]);
				Dto.setPartnumber((String) result[3]);
				Dto.setPartname((String) result[4]);
				Dto.setParttype(PartType.getPartTypeName((short) result[5]));
				Dto.setLocationId((Integer) result[6]);
				Dto.setCategory((String) result[7]);
				Dto.setMake((String) result[8]);
				if(result[9] != null) {
					Dto.setPart_photoid((long) result[9]);
				}
				Dto.setLocation((String) result[10]);
				if ((Double) result[11] != null) {
					Dto.setQuantity((Double) result[11]);
				}

				if ((Double) result[12] != null) {
					Dto.setHistory_quantity((Double) result[12]);
				}

				if ((Double) result[13] != null) {
					Dto.setUnitprice(round((Double) result[13], 2));
				}
				Dto.setPurchaseorder_id((Long) result[14]);
				Dto.setPurchaseorder_Number((Long) result[15]);
				Dto.setInvoice_number((String) result[16]);
				Dto.setInvoice_amount((String) result[17]);
				if ((Date) result[18] != null) {
					Dto.setInvoiceDate((Date) result[18]);
					Dto.setInvoice_date(sqlDateFormat.format((Date) result[18]));
				}
				if(result[19] != null) 
					Dto.setVendor_id((Integer) result[19]);
				if(result[20] != null) 
					Dto.setVendor_name((String) result[20]);
				if(result[21] != null) 
					Dto.setVendor_location((String) result[21]);
				Dto.setUnittype((String) result[22]);
				Dto.setDiscount((Double) result[23]);
				Dto.setTax((Double) result[24]);
				if ((Double) result[25] != null) {
					Dto.setTotal(round((Double) result[25], 2));
				}
				Dto.setDescription((String) result[26]);

				Dto.setInventory_location_id((Long) result[27]);
				Dto.setInventory_all_id((Long) result[28]);
				
				if(result[29] != null)
					Dto.setVendorTypeId((long) result[29]);
				Dto.setCreatedBy((String) result[30]);
				Dto.setLastModifiedBy((String) result[31]);
				Dto.setCreated(CreatedDateTime.format((Date) result[32]));
				Dto.setLastupdated(CreatedDateTime.format((Date) result[33]));
				
				if(result[34] != null)
				Dto.setPartInvoiceId((long) result[34]);
				
				if(result[35] != null) {
					Dto.setSubLocationId((Integer) result[35]);
					Dto.setSubLocation((String) result[36]);
				}
				Dto.setUnitTypeId((long) result[37]);
				
				if(result[38] != null)
					Dto.setMainQuantity((Double) result[38]);
					
				if(result[39] != null)
					Dto.setMainUnitprice((Double) result[39]);
			}
			return Dto;
		} catch (Exception e) {
			throw e;
		} finally {
			query = null;
		}

	}

	@Transactional
	public void deleteInventory(Long Inventory_id, Integer companyId) throws Exception {
		InventoryDao.deleteInventory(Inventory_id, companyId);
	}

	@Transactional
	public List<Inventory> listOnlyStatus() throws Exception {
		// return InventoryDao.listOnlyStatus();

		TypedQuery<Inventory> query = entityManager.createQuery("SELECT e.partnumber, e.partname FROM Inventory e",
				Inventory.class);
		return query.getResultList();
	}

	@Transactional
	public List<Inventory> getInventoryPartQuantity_Location(String location) throws Exception {
		return InventoryDao.getInventoryPartQuantity_Location(location);
	}

	@Transactional
	public Long countInventory() throws Exception {

		return InventoryDao.countInventory();
	}

	@Transactional
	public List<Long> countLocationTotalQty() throws Exception {

		return InventoryDao.countLocationTotalQty();
	}

	@Transactional
	public List<Long> countLocationTotalQty(String Location) throws Exception {

		return InventoryDao.countLocationTotalQty(Location);
	}

	@Transactional
	public void updateInventoryQuantity(Double Quantiy, Long Inventoryid) throws Exception {

		InventoryDao.updateInventoryQuantity(Quantiy, Inventoryid);
	}

	
	@Transactional
	public void Subtract_update_Inventory_from_Workorder(Double Quantiy, Long Inventoryid) throws Exception {
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			entityManager.createQuery("UPDATE Inventory  SET quantity = quantity - " + Quantiy + " where inventory_id="
					+ Inventoryid + " AND companyId = " + userDetails.getCompany_id()).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	@Transactional
	public void upadateInTransitQntyInInventory(Double quantiy, Long inventoryid) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		entityManager.createQuery("UPDATE Inventory  SET inTransitQuantity = COALESCE(inTransitQuantity,0) + " + quantiy + " where inventory_id="
				+ inventoryid + " AND companyId = " + userDetails.getCompany_id()).executeUpdate();
	}
	@Override
	@Transactional
	public void upadateInTransitQntyInLoncationInv(Long partId, int locationId,int companyId) throws Exception {
		entityManager.createQuery(
				"UPDATE InventoryLocation AS invLo SET invLo.inTransitQuantity=(SELECT COALESCE(ROUND(SUM(inv.inTransitQuantity),2),0) FROM Inventory AS inv WHERE inv.partid="
						+ partId + " AND inv.locationId =" + locationId
						+ " AND inv.inTransitQuantity > 0.0  AND inv.companyId = " + companyId
						+ " AND inv.markForDelete = 0 ) WHERE invLo.partid=" + partId + " AND invLo.locationId="
						+ locationId + " AND invLo.companyId = " + companyId
						+ " AND invLo.markForDelete = 0 ")
				.executeUpdate();
	}
	
	@Transactional
	public void Subtract_update_InventoryLocation_from_Workorder(Long PARTID, Integer Inventory_location_id,
			Integer companyId) throws Exception {

		entityManager.createQuery(
				"UPDATE InventoryLocation AS invLo SET invLo.location_quantity=(SELECT COALESCE(ROUND(SUM(inv.quantity),2),0) FROM Inventory AS inv WHERE inv.partid="
						+ PARTID + " AND inv.locationId =" + Inventory_location_id
						+ " AND inv.quantity > 0.0  AND inv.companyId = " + companyId
						+ " AND inv.markForDelete = 0 ) WHERE invLo.partid=" + PARTID + " AND invLo.locationId="
						+ Inventory_location_id + " AND invLo.companyId = " + companyId
						+ " AND invLo.markForDelete = 0 ")
				.executeUpdate();

	}

	@Transactional
	public void Subtract_update_InventoryAll_from_Workorder(Long PARTID, Integer companyId)
			throws Exception {

		entityManager.createQuery(
				"UPDATE InventoryAll AS inAll SET inAll.all_quantity=(SELECT COALESCE(ROUND(SUM(quantity),2),0) FROM Inventory AS inv WHERE inv.partid="
						+ PARTID + " AND inv.markForDelete = 0 AND inv.companyId = " + companyId
						+ " ) WHERE inAll.partid=" + PARTID + " AND inAll.companyId = " + companyId
						+ " AND inAll.markForDelete = 0 ")
				.executeUpdate();
	}

	@Transactional
	public void Add_update_Inventory_from_Workorder(Double Quantiy, Long Inventoryid, Integer companyId)
			throws Exception {

		// InventoryDao.Add_update_Inventory_from_Workorder(Quantiy,
		// Inventoryid);

		entityManager.createQuery("UPDATE Inventory  SET quantity = quantity + " + Quantiy + " where inventory_id="
				+ Inventoryid + " AND companyId = " + companyId).executeUpdate();
	}

	@Transactional
	public void Add_update_InventoryLocation_from_Workorder(Double Quantiy, Long Inventory_location_id,
			Integer companyId) throws Exception {

		// InventoryLocationRepository.Add_update_InventoryLocation_from_Workorder(Quantiy,
		// Inventory_location_id);

		entityManager
				.createQuery("UPDATE InventoryLocation  SET location_quantity = location_quantity + " + Quantiy
						+ " where inventory_location_id=" + Inventory_location_id + " AND companyId = " + companyId)
				.executeUpdate();
	}

	@Transactional
	public void Add_update_InventoryAll_from_Workorder(Double Quantiy, Long Inventory_All_id, Integer companyId)
			throws Exception {

		// InventoryAllRepository.Add_update_InventoryAll_from_Workorder(Quantiy,
		// Inventory_All_id);

		entityManager
				.createQuery("UPDATE InventoryAll  SET all_quantity = all_quantity + " + Quantiy
						+ " where inventory_all_id=" + Inventory_All_id + " AND companyId = " + companyId)
				.executeUpdate();

	}

	/**
	 * This Page get InventoryAll table to get pagination values
	 * 
	 * @throws Exception
	 */
	@Transactional
	public Page<InventoryAll> getDeployment_Page_InventoryAll(Integer pageNumber, Integer companyId, HashMap<String, Object> configuration) throws Exception {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "partid");
		
		if(!(boolean) configuration.get("groupInventoryOnPartName")) {
			return InventoryAllRepository.getDeployment_Page_InventoryAll(companyId, request);
		}else {
			return InventoryAllRepository.getDeployment_Page_InventoryAllOnName(companyId, request);
		}
		
		
	}
	
	@Override
	public Page<PartInvoice> getDeployment_Page_PartInvoice(Integer pageNumber, Integer companyId) throws Exception {
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "partInvoiceId");
		return PartInvoiceRepository.getDeployment_Page_PartInvoice(companyId, request);
	}
	
	@Override
	public Page<InventoryLocation> getDeployment_Page_LowStockInventoryLocation(Integer pageNumber, CustomUserDetails userDetails, Integer partlocation_id)
			throws Exception {
		HashMap<String, Object>		configuration		= null;	
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			@SuppressWarnings("deprecation")
			PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "partid");
			if((boolean) configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
				return InventoryLocationRepository.getDeployment_Page_LowStockInventoryLocation(userDetails.getCompany_id(), userDetails.getId(), partlocation_id, request);
			}else {
				return InventoryLocationRepository.getDeployment_Page_LowStockInventoryLocation(userDetails.getCompany_id(), partlocation_id, request);
			}	
			
			} catch (Exception e) {
				throw e;
			}finally {
				configuration		= null;
			}
	}

	/**
	 * This List get InventoryAll table to get pagination last 10 entries values
	 */
	@Transactional
	public List<InventoryAllDto> list_Of_All_Inventory_Quantity(Integer pageNumber, Integer companyId, HashMap<String, Object>  configuration)
			throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
			
			if((boolean) configuration.get("groupInventoryOnPartName")) {
				
				queryt = entityManager
						.createQuery("SELECT inv.inventory_all_id, inv.partid, MP.partnumber, MP.partname, PC.pcName, "
								+ "SUM(inv.all_quantity), MP.unitTypeId FROM InventoryAll as inv "
								+ " INNER JOIN MasterParts AS MP ON MP.partid = inv.partid "
								+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
								+ " WHERE inv.all_quantity > 0.0 AND inv.companyId = " + companyId
								+ " AND inv.markForDelete = 0 group by  MP.partname order by inv.inventory_all_id desc", Object[].class);
			}else {
				queryt = entityManager
						.createQuery("SELECT inv.inventory_all_id, inv.partid, MP.partnumber, MP.partname, PC.pcName, "
								+ "inv.all_quantity, MP.unitTypeId FROM InventoryAll as inv "
								+ " INNER JOIN MasterParts AS MP ON MP.partid = inv.partid "
								+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
								+ " WHERE inv.all_quantity > 0.0 AND inv.companyId = " + companyId
								+ " AND inv.markForDelete = 0 ORDER BY inv.inventory_all_id desc ", Object[].class);
			}
			
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]> results = queryt.getResultList();

			List<InventoryAllDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryAllDto>();
				InventoryAllDto inve = null;
				for (Object[] result : results) {
					inve = new InventoryAllDto();

					inve.setInventory_all_id((Long) result[0]);
					inve.setPartid((Long) result[1]);
					inve.setPartnumber((String) result[2]);
					inve.setPartname((String) result[3]);
					inve.setCategory((String) result[4]);
					if(result[5] != null)
					inve.setAll_quantity(Double.parseDouble(toFixedTwo.format(result[5])));
					inve.setUnitTypeId((long) result[6]);
					
					Dtos.add(inve);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}

	// New algorithm Inventory

	/** This Page get Inventory table to get pagination values */
	@Transactional
	public Page<Inventory> getDeployment_Page_Inventory_GROUPBY_PARTID_PARTNAME(Integer pageNumber) {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return InventoryDao.getDeployment_Page_Inventory_GROUPBY_PARTID_PARTNAME(pageable);
	}

	/**
	 * This List get InventoryAll table to get pagination last 10 entries values
	 */
	@Transactional
	public List<InventoryDto> list_Of_All_Inventory_GROUPBY_PARTID_PARTNAME(Integer pageNumber) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT R.inventory_id, R.partid, MP.partnumber, MP.partname, PC.pcName, SUM(R.quantity) "
						+ " FROM Inventory AS R " + " INNER JOIN MasterParts AS MP ON MP.partid = R.partid "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
						+ " WHERE R.quantity > 0.0  AND R.companyId = " + userDetails.getCompany_id()
						+ " AND R.markForDelete = 0 GROUP BY R.partid ", Object[].class);
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		List<Object[]> results = queryt.getResultList();

		List<InventoryDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryDto>();
			InventoryDto list = null;
			for (Object[] result : results) {
				list = new InventoryDto();

				list.setInventory_id((Long) result[0]);
				list.setPartid((Long) result[1]);
				list.setPartnumber((String) result[2]);
				list.setPartname((String) result[3]);
				list.setCategory((String) result[4]);
				list.setQuantity((Double) result[5]);

				Dtos.add(list);
			}
		}
		return Dtos;

	}

	/** This Page get InventoryAll table to get pagination values */
	@Transactional
	public Page<InventoryLocation> getDeployment_Page_InventoryLocation(Integer pageNumber, Integer locationId, 
			Integer companyId, HashMap<String, Object>  configuration) {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		
		if(!(boolean) configuration.get("groupInventoryOnPartName")) {
			return InventoryLocationRepository.getDeployment_Page_InventoryLocation(locationId, companyId, pageable);
		}else {
			return InventoryLocationRepository.getDeployment_Page_InventoryLocationOnName(locationId, companyId, pageable);
		}
		
	}

	@Transactional
	public List<InventoryLocationDto> list_Of_Location_Inventory_Quantity(Integer pageNumber, Integer locationId, 
				Integer companyId, HashMap<String, Object> configuration) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
			if(!(boolean) configuration.get("groupInventoryOnPartName")) {
				queryt = entityManager.createQuery(
						"SELECT R.inventory_location_id, R.partid, MP.partnumber, MP.partname, PC.pcName, PL.partlocation_name, "
								+ " R.location_quantity, MP.unitTypeId, R.inventoryall.inventory_all_id "
								+ " ,MPL.Aisle,MPL.row,MPL.bin"
								+ " FROM InventoryLocation AS R "
								+ " INNER JOIN MasterParts AS MP ON MP.partid = R.partid "
								+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
								+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.locationId " 
								+ " LEFT JOIN MasterPartsLocation MPL ON MPL.masterparts.partid = MP.partid AND MPL.locationId =PL.partlocation_id " 
								+ " WHERE R.locationId="
								+ locationId + " AND R.location_quantity > 0.0 AND R.companyId = " + companyId
								+ " AND R.markForDelete = 0 ORDER BY R.inventory_location_id desc",
						Object[].class);
			}else {
				queryt = entityManager.createQuery(
						"SELECT R.inventory_location_id, R.partid, MP.partnumber, MP.partname, PC.pcName, PL.partlocation_name, "
								+ " SUM(R.location_quantity), MP.unitTypeId, R.inventoryall.inventory_all_id "
								+ " ,MPL.Aisle,MPL.row,MPL.bin"
								+ " FROM InventoryLocation AS R "
								+ " INNER JOIN MasterParts AS MP ON MP.partid = R.partid "
								+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
								+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.locationId " 
								+ " LEFT JOIN MasterPartsLocation MPL ON MPL.masterparts.partid = MP.partid AND MPL.locationId =PL.partlocation_id "
								+ " WHERE R.locationId="
								+ locationId + " AND R.location_quantity > 0.0 AND R.companyId = " + companyId
								+ " AND R.markForDelete = 0 group by MP.partname ORDER BY R.inventory_location_id desc",
						Object[].class);
			}
			
			
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]> results = queryt.getResultList();
			List<InventoryLocationDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryLocationDto>();
				InventoryLocationDto list = null;
				for (Object[] result : results) {
					list = new InventoryLocationDto();
	
					list.setInventory_location_id((Long) result[0]);
					list.setPartid((Long) result[1]);
					list.setPartnumber((String) result[2]);
					list.setPartname((String) result[3]);
					list.setCategory((String) result[4]);
					list.setLocation((String) result[5]);
					if(result[6] != null)
					list.setLocation_quantity(Double.parseDouble(toFixedTwo.format(result[6])));
					list.setUnitTypeId((long) result[7]);
					list.setInventory_all_id((long) result[8]);
					list.setAisle((String) result[9]);
					list.setRow((String) result[10]);
					list.setBin((String)result[11]);
					Dtos.add(list);
				}
			}
			return Dtos;
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Transactional
	public void add_InventoryAll(InventoryAll InventoryAll) throws Exception {

		InventoryAllRepository.save(InventoryAll);

	}

	@Transactional
	public InventoryLocation add_InventoryLocation(InventoryLocation inventory_Location) throws Exception {

		InventoryLocation inventoryLocation =	InventoryLocationRepository.save(inventory_Location);
		return inventoryLocation;
	}
	
	@Transactional
	public InventoryLocation addInventoryLocation(InventoryLocation inventory_Location) throws Exception {

		return InventoryLocationRepository.save(inventory_Location);
	}

	@Transactional
	public void add_Inventory(Inventory Quantity) throws Exception {

		InventoryDao.save(Quantity);
	}

	@Transactional
	public void update_InventoryAll_PARTID_PARTNAME_GENRAL(Long PARTID) throws Exception {
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			entityManager.createQuery(
					"UPDATE InventoryAll AS inAll SET inAll.all_quantity=(SELECT COALESCE(ROUND(SUM(quantity),2),0) FROM Inventory AS inv WHERE inv.partid="
							+ PARTID + " AND inv.markForDelete = 0 AND inv.companyId = " + userDetails.getCompany_id()
							+ " ) WHERE inAll.partid=" + PARTID + " AND inAll.companyId = " + userDetails.getCompany_id()
							+ " AND inAll.markForDelete = 0 ")
					.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Transactional
	public void update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(Long PARTID, Integer PartLocation_ID)
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {
			entityManager.createQuery(
					"UPDATE InventoryLocation AS invLo SET invLo.location_quantity=(SELECT COALESCE(ROUND(SUM(inv.quantity),2),0) FROM Inventory AS inv WHERE inv.partid="
							+ PARTID + " AND inv.locationId =" + PartLocation_ID
							+ " AND inv.quantity > 0.0  AND inv.companyId = " + userDetails.getCompany_id()
							+ " AND inv.markForDelete = 0 ) WHERE invLo.partid=" + PARTID + " AND invLo.locationId="
							+ PartLocation_ID + " AND invLo.companyId = " + userDetails.getCompany_id()
							+ " AND invLo.markForDelete = 0 ")
					.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public List<InventoryAllDto> getInventoryAllGPONName(String partName, Integer companyId) throws Exception {

		try {
			Query query = entityManager.createQuery(
					"SELECT inv.inventory_all_id, inv.partid, MP.partnumber, MP.partname"
							+ ", inv.all_quantity FROM InventoryAll as inv "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = inv.partid "
							+ " WHERE MP.partname = '" + partName + "' AND inv.companyId = " + companyId
							+ " AND inv.markForDelete = 0 ");

			@SuppressWarnings("unchecked")
			List<Object[]> results = query.getResultList();
			
			List<InventoryAllDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryAllDto>();
				InventoryAllDto inve = null;
				for (Object[] result : results) {
					inve = new InventoryAllDto();

					inve.setInventory_all_id((Long) result[0]);
					inve.setPartid((Long) result[1]);
					inve.setPartnumber((String) result[2]);
					inve.setPartname((String) result[3]);
					if(result[4] != null)
					inve.setAll_quantity(Double.parseDouble(toFixedTwo.format(result[4])));
					
					Dtos.add(inve);

				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public InventoryAllDto getInventoryAll(Long sid, Integer companyId) throws Exception {

		// return InventoryAllRepository.getInventoryAll(sid, companyId);
		try {
			Query query = entityManager.createQuery(
					"SELECT inv.inventory_all_id, inv.partid, MP.partnumber, MP.partname, MP.part_photoid, PC.pcName, "
							+ "inv.all_quantity, MP.unitTypeId FROM InventoryAll as inv "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = inv.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " WHERE inv.inventory_all_id =" + sid + " AND inv.companyId = " + companyId
							+ " AND inv.markForDelete = 0 ");

			Object[] results = null;
			try {
				results = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			InventoryAllDto inve = null;
			if (results != null) {
				inve = new InventoryAllDto();

				inve.setInventory_all_id((Long) results[0]);
				inve.setPartid((Long) results[1]);
				inve.setPartnumber((String) results[2]);
				inve.setPartname((String) results[3]);
				inve.setPart_photoid((Long) results[4]);
				inve.setCategory((String) results[5]);
				if(results[6] != null)
				inve.setAll_quantity(Double.parseDouble(toFixedTwo.format(results[6])));
				inve.setUnitTypeId((long) results[7]);

			} else {
				return null;
			}
			return inve;

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public InventoryAllDto getInventoryAllOnName(String partname, Integer companyId) throws Exception {

		// return InventoryAllRepository.getInventoryAll(sid, companyId);
		try {
			Query query = entityManager.createQuery(
					"SELECT MP.partname, SUM(inv.all_quantity)"
							+ " FROM InventoryAll as inv "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = inv.partid "
							+ " WHERE MP.partname ='" + partname + "' AND inv.companyId = " + companyId
							+ " AND inv.markForDelete = 0 group by MP.partname ");

			Object[] results = null;
			try {
				results = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			InventoryAllDto inve = null;
			if (results != null) {
				inve = new InventoryAllDto();

				inve.setPartname((String) results[0]);
				inve.setAll_quantity(Double.parseDouble(toFixedTwo.format(results[1])));

			} else {
				return null;
			}
			return inve;

		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public InventoryAllDto Get_InventoryAll_USEING_PART_ID(Long PART_ID, Integer companyId) throws Exception {

		try {
			Query query = entityManager
					.createQuery("SELECT inv.inventory_all_id, inv.partid, MP.partnumber, MP.partname, MP.part_photoid, PC.pcName, "
							+ "inv.all_quantity  FROM InventoryAll as inv "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = inv.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " WHERE inv.partid ="+PART_ID+" AND inv.companyId = " + companyId
							+ " AND inv.markForDelete = 0  ");

			Object[] results = null;
			try {
				results = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			InventoryAllDto inve = null;
			if (results != null) {
				inve = new InventoryAllDto();

				inve.setInventory_all_id((Long) results[0]);
				inve.setPartid((Long) results[1]);
				inve.setPartnumber((String) results[2]);
				inve.setPartname((String) results[3]);
				inve.setPart_photoid((Long) results[4]);
				inve.setCategory((String) results[5]);
				inve.setAll_quantity((Double) results[6]);

			} else {
				return null;
			}
			return inve;

		} catch (Exception e) {
			throw e;
		}

	}

	@Transactional
	public InventoryLocationDto getInventoryLocationByLocationId(Long Location_id, Integer companyId) throws Exception {
		InventoryLocationDto	location	= getInventoryLocationDtoByLocation(Location_id, companyId);
		if(location != null) {
			PartMeasurementUnit	measurementUnit	= null;
			PartMeasurementUnit	conversionUnit	= null;
			Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
			measurementUnit	= measermentHM.get(Integer.parseInt(location.getUnitTypeId()+""));
			if(measurementUnit != null && measurementUnit.getConvertTo() != null && measurementUnit.getConvertTo() > 0) {
				conversionUnit	= measermentHM.get(measurementUnit.getConvertTo());
				if(conversionUnit != null) {
					location.setConvertToStr("( "+(location.getLocation_quantity() / measurementUnit.getConversionRate()) 
													+" "+ measurementUnit.getPmuName()+" )");
				}
			}
		}
		return location;
	}
	
	@Transactional
	public InventoryLocation getInventoryLocation(Long Location_id) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return InventoryLocationRepository.getInventoryLocation(Location_id, userDetails.getCompany_id());
	}
	
	
	public InventoryLocationDto getInventoryLocationByLocation(Long locationId, Integer companyId) throws Exception{
		
		try {
			Query query = entityManager.createQuery(
					"SELECT inv.inventory_location_id, inv.partid, MP.partnumber, MP.partname, MP.part_photoid, "
							+ "inv.location_quantity, inv.inventoryall.inventory_all_id, MP.unitTypeId "
							+ " FROM InventoryLocation as inv "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = inv.partid "
							+ " WHERE inv.locationId =" + locationId + " AND inv.companyId = " + companyId
							+ " AND inv.markForDelete = 0 ");

			Object[] results = null;
			try {
				results = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			InventoryLocationDto inve = null;
			if (results != null) {
				inve = new InventoryLocationDto();

				inve.setInventory_location_id((Long) results[0]);
				inve.setPartid((Long) results[1]);
				inve.setPartnumber((String) results[2]);
				inve.setPartname((String) results[3]);
				inve.setPart_photoid((Long) results[4]);
				inve.setLocation_quantity((Double)results[5]);
				inve.setLocationId(Integer.parseInt(locationId+""));
				inve.setCompanyId(companyId);
				inve.setInventory_all_id((Long) results[6]);
				inve.setUnitTypeId((long) results[7]);

			} else {
				return null;
			}
			return inve;

		} catch (Exception e) {
			throw e;
		}
	
	}
	
	public InventoryLocationDto getInventoryLocationDtoByLocation(Long locationId, Integer companyId) throws Exception{
		
		try {
			Query query = entityManager.createQuery(
					"SELECT inv.inventory_location_id, inv.partid, MP.partnumber, MP.partname, MP.part_photoid, "
							+ "inv.location_quantity, inv.inventoryall.inventory_all_id, MP.unitTypeId "
							+ " FROM InventoryLocation as inv "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = inv.partid "
							+ " WHERE inv.inventory_location_id =" + locationId + " AND inv.companyId = " + companyId
							+ " AND inv.markForDelete = 0 ");

			Object[] results = null;
			try {
				results = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			InventoryLocationDto inve = null;
			if (results != null) {
				inve = new InventoryLocationDto();

				inve.setInventory_location_id((Long) results[0]);
				inve.setPartid((Long) results[1]);
				inve.setPartnumber((String) results[2]);
				inve.setPartname((String) results[3]);
				inve.setPart_photoid((Long) results[4]);
				inve.setLocation_quantity((Double)results[5]);
				inve.setLocationId(Integer.parseInt(locationId+""));
				inve.setCompanyId(companyId);
				inve.setInventory_all_id((Long) results[6]);
				inve.setUnitTypeId((long) results[7]);

			} else {
				return null;
			}
			return inve;

		} catch (Exception e) {
			throw e;
		}
	
	}

	@Transactional
	public List<InventoryLocationDto> Get_InventoryAll_id_To_Inventory_Location(Long inventory_all_id,
			Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.inventory_location_id, R.partid, MP.partnumber, MP.partname, MP.partTypeId, PC.pcName, PL.partlocation_name, "
						+ " R.location_quantity, MP.unitTypeId  FROM InventoryLocation AS R "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = R.partid "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.locationId "
						+ " WHERE R.inventoryall.inventory_all_id=" + inventory_all_id + "  AND R.companyId = "
						+ companyId + " AND R.markForDelete = 0 ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<InventoryLocationDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryLocationDto>();
			InventoryLocationDto list = null;
			for (Object[] result : results) {
				list = new InventoryLocationDto();

				list.setInventory_location_id((Long) result[0]);
				list.setPartid((Long) result[1]);
				list.setPartnumber((String) result[2]);
				list.setPartname((String) result[3]);
				list.setParttype(PartType.getPartTypeName((short) result[4]));
				list.setCategory((String) result[5]);
				list.setLocation((String) result[6]);
				if(result[7] != null)
				list.setLocation_quantity(Double.parseDouble(toFixedTwo.format(result[7])));
				
				list.setUnitTypeId((long) result[8]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}
	
	@Override
	public List<InventoryLocationDto> Get_InventoryAll_id_To_Inventory_LocationOnName(String inventory_all_id,
			Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.inventory_location_id, R.partid, MP.partnumber, MP.partname, MP.partTypeId, PC.pcName, PL.partlocation_name, "
						+ " R.location_quantity, MP.unitTypeId  FROM InventoryLocation AS R "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = R.partid "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.locationId "
						+ " WHERE R.inventoryall.inventory_all_id IN (" + inventory_all_id + ")  AND R.companyId = "
						+ companyId + " AND R.markForDelete = 0 ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<InventoryLocationDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryLocationDto>();
			InventoryLocationDto list = null;
			for (Object[] result : results) {
				list = new InventoryLocationDto();

				list.setInventory_location_id((Long) result[0]);
				list.setPartid((Long) result[1]);
				list.setPartnumber((String) result[2]);
				list.setPartname((String) result[3]);
				list.setParttype(PartType.getPartTypeName((short) result[4]));
				list.setCategory((String) result[5]);
				list.setLocation((String) result[6]);
				if(result[7] != null)
				list.setLocation_quantity(Double.parseDouble(toFixedTwo.format(result[7])));
				
				list.setUnitTypeId((long) result[8]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<InventoryDto> Get_InventoryAll_id_To_Inventory(Long inventory_all_id, Integer companyId)
			throws Exception {

		// return InventoryDao.Get_InventoryAll_id_To_Inventory(inventory_all_id,
		// companyId);

		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager.createQuery(
					"SELECT I.inventory_id, I.inventory_Number, I.partid, MP.partnumber, MP.partname, MP.partTypeId, PC.pcName,  PM.pmName, PL.partlocation_name,  I.quantity,"
							+ " I.history_quantity, I.unitprice, I.purchaseorder_id, PO.purchaseorder_Number, I.invoice_number, I.invoice_amount, I.invoice_date, I.vendor_id, V.vendorName, V.vendorLocation, "
							+ " MU.pmuSymbol, I.discount, I.tax, I.total, I.description, I.inventory_location_id, I.inventory_all_id FROM Inventory AS I "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = I.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = I.locationId "
							+ " INNER JOIN InventoryAll IAL ON IAL.inventory_all_id = I.inventory_all_id "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = I.vendor_id "
							+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
							+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = I.purchaseorder_id "
							+ " LEFT JOIN PartMeasurementUnit MU ON MU.pmuid = MP.unitTypeId"
							+ " WHERE I.inventory_location_id = :Invid AND I.quantity > 0.0 AND IAL.companyId = :comID AND I.markForDelete = 0 ",
					Object[].class);
			queryt.setParameter("Invid", inventory_all_id);
			queryt.setParameter("comID", companyId);
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
						Dto.setQuantity((Double) result[9]);
					}

					if ((Double) result[10] != null) {
						Dto.setHistory_quantity((Double) result[10]);
					}

					if ((Double) result[11] != null) {
						Dto.setUnitprice(round((Double) result[11], 2));
					}
					Dto.setPurchaseorder_id((Long) result[12]);
					Dto.setPurchaseorder_Number((Long) result[13]);
					Dto.setInvoice_number((String) result[14]);
					Dto.setInvoice_amount((String) result[15]);
					if ((Date) result[16] != null) {
						Dto.setInvoice_date(dateName.format((Date) result[16]));
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
	public List<InventoryDto> Show_AvailableQYT_InventoryAll_id_To_InventoryOnName(String inventory_all_id,
			Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager.createQuery(
					"SELECT I.inventory_id, I.inventory_Number, I.partid, MP.partnumber, MP.partname, MP.partTypeId, PC.pcName,  PM.pmName, PL.partlocation_name,  I.quantity,"
							+ " I.history_quantity, I.unitprice, I.purchaseorder_id, PO.purchaseorder_Number, I.invoice_number, I.invoice_amount, I.invoice_date, I.vendor_id, V.vendorName, V.vendorLocation, "
							+ " MU.pmuSymbol, I.discount, I.tax, I.total, I.description, I.inventory_location_id, "
							+ " I.inventory_all_id, V.vendorTypeId, I.partInvoiceId, I.subLocationId, "
							+ " PSL.partlocation_name, MP.unitTypeId "
							+ " FROM Inventory AS I "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = I.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = I.locationId "
							+ " INNER JOIN InventoryAll IAL ON IAL.inventory_all_id = I.inventory_all_id "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = I.vendor_id "
							+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
							+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = I.purchaseorder_id "
							+ " LEFT JOIN PartMeasurementUnit MU ON MU.pmuid = MP.unitTypeId"
							+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = I.subLocationId "
							+ " WHERE I.inventory_all_id IN ("+inventory_all_id+" ) AND I.quantity > 0.0 AND IAL.companyId = :comID AND I.markForDelete = 0 ",
					Object[].class);
			queryt.setParameter("comID", companyId);
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
						Dto.setInvoice_date(dateName.format((Date) result[16]));
					}
					Dto.setVendor_id((Integer) result[17]);
					Dto.setVendor_name((String) result[18]);
					Dto.setVendor_location((String) result[19]);
					Dto.setUnittype((String) result[20]);
					Dto.setDiscount((Double) result[21]);
					if(result[22] != null)
					Dto.setTax(Double.parseDouble(toFixedTwo.format(result[22])));
					if ((Double) result[23] != null) {
						Dto.setTotal(round((Double) result[23], 2));
					}
					Dto.setDescription((String) result[24]);

					Dto.setInventory_location_id((Long) result[25]);
					Dto.setInventory_all_id((Long) result[26]);
					if(result[27] != null)
						Dto.setVendorTypeId((long) result[27]);
					if(result[28] != null)
						Dto.setPartInvoiceId((long) result[28]);
					if(result[29] != null) {
						Dto.setSubLocationId((Integer) result[29]);
						Dto.setSubLocation((String) result[30]);
					}
					
					Dto.setUnitTypeId((long) result[31]);

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
	
	
	@Transactional
	public List<InventoryDto> Show_AvailableQYT_InventoryAll_id_To_Inventory(Long inventory_all_id, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager.createQuery(
					"SELECT I.inventory_id, I.inventory_Number, I.partid, MP.partnumber, MP.partname, MP.partTypeId, PC.pcName,  PM.pmName, PL.partlocation_name,  I.quantity,"
							+ " I.history_quantity, I.unitprice, I.purchaseorder_id, PO.purchaseorder_Number, I.invoice_number, I.invoice_amount, I.invoice_date, I.vendor_id, V.vendorName, V.vendorLocation, "
							+ " MU.pmuSymbol, I.discount, I.tax, I.total, I.description, I.inventory_location_id, "
							+ " I.inventory_all_id, V.vendorTypeId, I.partInvoiceId, I.subLocationId, "
							+ " PSL.partlocation_name, MP.unitTypeId "
							+ " FROM Inventory AS I "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = I.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = I.locationId "
							+ " INNER JOIN InventoryAll IAL ON IAL.inventory_all_id = I.inventory_all_id "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = I.vendor_id "
							+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
							+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = I.purchaseorder_id "
							+ " LEFT JOIN PartMeasurementUnit MU ON MU.pmuid = MP.unitTypeId"
							+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = I.subLocationId "
							+ " WHERE I.inventory_all_id = :Invid AND I.quantity > 0.0 AND IAL.companyId = :comID AND I.markForDelete = 0 ",
					Object[].class);
			queryt.setParameter("Invid", inventory_all_id);
			queryt.setParameter("comID", companyId);
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
						Dto.setInvoice_date(dateName.format((Date) result[16]));
					}
					Dto.setVendor_id((Integer) result[17]);
					Dto.setVendor_name((String) result[18]);
					Dto.setVendor_location((String) result[19]);
					Dto.setUnittype((String) result[20]);
					Dto.setDiscount((Double) result[21]);
					if(result[22] != null)
					Dto.setTax(Double.parseDouble(toFixedTwo.format(result[22])));
					if ((Double) result[23] != null) {
						Dto.setTotal(round((Double) result[23], 2));
					}
					Dto.setDescription((String) result[24]);

					Dto.setInventory_location_id((Long) result[25]);
					Dto.setInventory_all_id((Long) result[26]);
					if(result[27] != null)
						Dto.setVendorTypeId((long) result[27]);
					if(result[28] != null)
						Dto.setPartInvoiceId((long) result[28]);
					if(result[29] != null) {
						Dto.setSubLocationId((Integer) result[29]);
						Dto.setSubLocation((String) result[30]);
					}
					
					Dto.setUnitTypeId((long) result[31]);

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
	
	

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public List<InventoryDto> Get_WorkOrder_InventoryLocation_id_To_Inventory(Long inventory_location_id,
			Integer companyId,String Query) throws Exception {
		
		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager.createQuery(
					"SELECT I.inventory_id, I.inventory_Number, I.partid, MP.partnumber, MP.partname, MP.partTypeId, PC.pcName,  PM.pmName, PL.partlocation_name,  I.quantity,"
							+ " I.history_quantity, I.unitprice, I.purchaseorder_id, PO.purchaseorder_Number, I.invoice_number, I.invoice_amount, I.invoice_date, I.vendor_id, V.vendorName, V.vendorLocation, "
							+ " MU.pmuSymbol, I.discount, I.tax, I.total, I.description, I.inventory_location_id, I.inventory_all_id, "
							+ " I.locationId, I.partInvoiceId, MP.unitTypeId, I.serialNoAddedForParts  "
							+ " FROM Inventory AS I "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = I.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = I.locationId "
							+ " INNER JOIN InventoryAll IAL ON IAL.inventory_all_id = I.inventory_all_id "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = I.vendor_id "
							+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
							+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = I.purchaseorder_id "
							+ " LEFT JOIN PartMeasurementUnit MU ON MU.pmuid = MP.unitTypeId"
							+ " WHERE I.inventory_location_id = :Invid AND I.quantity > 0.0 AND IAL.companyId = :comID "+Query+" AND I.markForDelete = 0  ",
					Object[].class);
			System.err.println("*** inventory_location_id"+inventory_location_id);
			queryt.setParameter("Invid", inventory_location_id);
			queryt.setParameter("comID", companyId);
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
						Dto.setQuantity((Double) result[9]);
					}

					if ((Double) result[10] != null) {
						Dto.setHistory_quantity((Double) result[10]);
					}

					if ((Double) result[11] != null) {
						Dto.setUnitprice(round((Double) result[11], 2));
					}
					Dto.setPurchaseorder_id((Long) result[12]);
					Dto.setPurchaseorder_Number((Long) result[13]);
					Dto.setInvoice_number((String) result[14]);
					Dto.setInvoice_amount((String) result[15]);
					if ((Date) result[16] != null) {
						Dto.setInvoice_date(dateName.format((Date) result[16]));
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
					
					Dto.setLocationId((Integer) result[27]);
					Dto.setPartInvoiceId((Long) result[28]);
					Dto.setUnitTypeId((Long) result[29]);
					if(Dto.getQuantity() > 0) {
						Dtos.add(Dto);
					}
					Dto.setSerialNoAddedForParts((Integer) result[30]);
				}
			}
			
			/*
			 * if(Dtos != null && !Dtos.isEmpty()) { Map<Integer, PartMeasurementUnit>
			 * measermentHM = partMeasurementUnitService.getPartMeasurementUnitHM();
			 * inventoryBL.getFinalInventoryForWOQty(Dtos, measermentHM); }
			 */
			
			return Dtos;
		} catch (Exception e) {
			throw e;
		} finally {
			queryt = null;
		}
	}

	@Transactional
	public void deleteLocationInventory(Long Inventory_Location_id, Integer companyId) throws Exception {

		InventoryLocationRepository.deleteLocationInventory(Inventory_Location_id, companyId);
	}

	@Transactional
	public void deleteInventoryAll(Long Inventory_All_id, Integer companyId) throws Exception {

		InventoryAllRepository.deleteInventoryAll(Inventory_All_id, companyId);
	}

	@Transactional
	public List<Inventory> validate_Inventory_Location_id_To_Inventory(Long inventory_location_id) throws Exception {

		return InventoryDao.validate_Inventory_Location_id_To_Inventory(inventory_location_id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void add_InventoryTransfer(InventoryTransfer inventoryTransfer) throws Exception {

		InventoryTransferRepository.save(inventoryTransfer);
	}

	@Transactional
	public List<InventoryTransferDto> Get_InventoryAll_id_To_InventoryTransfer(Long inventory_all_id, Integer companyId)
			throws Exception {
		

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.inventory_transfer_id, R.transfer_partid, MP.partnumber, MP.partname, PC.pcName, PL.partlocation_name, PLT.partlocation_name, "
						+ " R.transfer_quantity, R.transfer_description, US.firstName, R.transfer_via_ID, R.transfer_date, UR.firstName, R.transfer_receiveddate, "
						+ " R.TRANSFER_STATUS_ID, R.transfer_inventory_all_id, R.transfer_receivedby_ID, R.transfer_inventory_id FROM InventoryTransfer AS R "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = R.transfer_partid "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.transfer_from_locationId "
						+ " INNER JOIN PartLocations PLT ON PLT.partlocation_id = R.transfer_to_locationId "
						+ " INNER JOIN User AS US ON US.id = R.transfer_by_ID "
						+ " INNER JOIN User AS UR ON UR.id = R.transfer_receivedby_ID "
						+ " WHERE R.transfer_inventory_all_id=" + inventory_all_id + "  AND R.companyId = " + companyId
						+ " AND R.markForDelete = 0 ORDER BY R.inventory_transfer_id desc",
				Object[].class);
		
		// queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE_FIFTY);
		queryt.setMaxResults(PAGE_SIZE_FIFTY);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTransferDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTransferDto>();
			InventoryTransferDto list = null;
			for (Object[] result : results) {
				list = new InventoryTransferDto();

				list.setInventory_transfer_id((long) result[0]);
				list.setTransfer_partid((Long) result[1]);
				list.setTransfer_partnumber((String) result[2]);
				list.setTransfer_partname((String) result[3]);
				list.setTransfer_Category((String) result[4]);
				list.setTransfer_from_location((String) result[5]);
				list.setTransfer_to_location((String) result[6]);
				list.setTransfer_quantity((Double) result[7]);
				list.setTransfer_description((String) result[8]);
				list.setTransfer_by((String) result[9]);
				list.setTransfer_via_ID((short) result[10]);
				list.setTransfer_via(InventoryTransferViaType.getInventoryTransferViaTypeName((short) result[10]));
				if ((Date) result[11] != null) {
					list.setTransfer_date(dateName.format((Date) result[11]));
				}
				list.setTransfer_receivedby((String) result[12]);
				if ((Date) result[13] != null) {
					list.setTransfer_receiveddate(dateName.format((Date) result[13]));
				}
				
				list.setTRANSFER_STATUS(InventoryTransferStatus.getInventoryTransferName((short) result[14]));
				list.setTransfer_inventory_all_id((Long) result[15]);
				list.setTransfer_receivedby_ID((Long) result[16]);
				list.setTransfer_inventory_id((Long) result[17]);
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<InventoryDto> Report_listInventory(InventoryDto inventoryReport, Integer companyId) throws Exception {

		String partid = "", category = "", make = "", location = "", invoice = "", vendor = "", subLocationId = "";

		if (inventoryReport.getPartid() > 0 && inventoryReport.getPartid() != null) {
			partid = "AND I.partid =" + inventoryReport.getPartid() + "";
		}
		if (inventoryReport.getCategory() != "") {
			category = "AND MP.categoryId =" + inventoryReport.getCategory() + "";
		}
		if (inventoryReport.getMake() != "") {
			make = "AND MP.makerId =" + inventoryReport.getMake() + "";
		}
		if (inventoryReport.getLocationId() != null && inventoryReport.getLocationId() > 0) {
			location = "AND I.locationId =" + inventoryReport.getLocationId() + "";
		}
		if (inventoryReport.getInvoice_number() != "") {
			invoice = "AND I.invoice_number ='" + inventoryReport.getInvoice_number() + "'";
		}
		if (inventoryReport.getVendor_id() > 0 && inventoryReport.getVendor_id() != null) {
			vendor = "AND I.vendor_id =" + inventoryReport.getVendor_id() + "";
		}
		if (inventoryReport.getSubLocationId() != null && inventoryReport.getSubLocationId() > 0) {
			subLocationId = "AND I.subLocationId =" + inventoryReport.getSubLocationId() + "";
		}

		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager.createQuery(
					"SELECT I.inventory_id, I.inventory_Number, I.partid, MP.partnumber, MP.partname, MP.partTypeId, PC.pcName,  PM.pmName, PL.partlocation_name,  I.quantity,"
							+ " I.history_quantity, I.unitprice, I.purchaseorder_id, PO.purchaseorder_Number, I.invoice_number, I.invoice_amount, I.invoice_date, I.vendor_id, V.vendorName, V.vendorLocation, "
							+ " PMU.pmuSymbol, I.discount, I.tax, I.total, I.description, I.inventory_location_id, "
							+ " I.inventory_all_id, MP.unitTypeId FROM Inventory AS I "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = I.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = I.locationId "
							+ " INNER JOIN InventoryAll IAL ON IAL.inventory_all_id = I.inventory_all_id "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = I.vendor_id "
							+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
							+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = I.purchaseorder_id "
							+ " LEFT JOIN PartMeasurementUnit PMU ON PMU.pmuid = MP.partTypeId"
							+ " WHERE IAL.companyId = :comID AND I.markForDelete = 0 " + partid + " " + category + " "
							+ make + " " + location + " " + invoice + " " + vendor + " "+subLocationId+" ",
					Object[].class);
			queryt.setParameter("comID", companyId);
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
						Dto.setQuantity((Double) result[9]);
					}

					if ((Double) result[10] != null) {
						Dto.setHistory_quantity((Double) result[10]);
					}

					if ((Double) result[11] != null) {
						Dto.setUnitprice(round((Double) result[11], 2));
					}
					Dto.setPurchaseorder_id((Long) result[12]);
					Dto.setPurchaseorder_Number((Long) result[13]);
					Dto.setInvoice_number((String) result[14]);
					Dto.setInvoice_amount((String) result[15]);
					if ((Date) result[16] != null) {
						Dto.setInvoice_date(dateName.format((Date) result[16]));
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
					Dto.setUnitTypeId((long) result[27]);

					Dtos.add(Dto);
				}
			}
			
			if(Dtos != null && !Dtos.isEmpty()) {
				Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
				Dtos	= inventoryBL.getFinalInventoryDto(Dtos, measermentHM);
			}
			
			return Dtos;
		} catch (Exception e) {
			throw e;
		} finally {
			queryt = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryService#
	 * countTotal_Inventrory_AND_Location_Qty()
	 */
	@Transactional
	public Object[] countTotal_Inventrory_AND_Location_Qty() throws Exception {

		Query queryt = entityManager.createQuery(
				"SELECT COUNT(co),(SELECT  sum(P.all_quantity) FROM InventoryAll AS P  ) " + " FROM Inventory As co");

		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryService#
	 * countTotal_Inventory_Qty_AND_Location_Qty(java.lang.String)
	 */
	@Transactional
	public Object[] countTotal_Inventory_Qty_AND_Location_Qty(Integer locationId, Integer companyId) throws Exception {

		Query queryt = entityManager.createQuery(
				"SELECT sum(co.location_quantity),(SELECT  sum(p.location_quantity) from InventoryLocation AS p "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = p.partid "
						+ " where MP.partTypeId ="
						+ PartType.PART_TYPE_QUANTITY
						+ " AND  p.locationId=:locationId AND  p.companyId =:companyId AND p.markForDelete = 0 ) "
						+ " FROM InventoryLocation As co "
						+ " INNER JOIN MasterParts AS M ON M.partid = co.partid "
						+ " where M.partTypeId =" + PartType.PART_TYPE_LITER
						+ " AND co.locationId=:locationId AND  co.companyId =:companyId AND co.markForDelete = 0");
		queryt.setParameter("locationId", locationId);
		queryt.setParameter("companyId", companyId);
		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryService#
	 * countTotal_Inventory_Qty_AND_Location_Qty(java.lang.String)
	 */
	@Transactional
	public Object[] countTotal_Inventory_Qty_Liter(Integer companyId) throws Exception {

		Query queryt = entityManager.createQuery(
				"SELECT sum(co.all_quantity),(SELECT  sum(p.all_quantity) from InventoryAll AS p "
				+ " INNER JOIN MasterParts AS MP ON MP.partid = p.partid "
				+ " where MP.partTypeId = "
						+ PartType.PART_TYPE_QUANTITY + " AND p.companyId = " + companyId
						+ " AND p.markForDelete = 0  ) " + " FROM InventoryAll As co "
						+ " INNER JOIN MasterParts AS M ON M.partid = co.partid "
						+ " where M.partTypeId ="
						+ PartType.PART_TYPE_LITER + " AND co.companyId = " + companyId + " AND co.markForDelete = 0");
		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryService#
	 * Report_list_Of_Location_Inventory(java.lang.String)
	 */
	@Transactional
	public List<InventoryLocationDto> Report_list_Of_Location_Inventory(String Query, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.inventory_location_id, R.partid, MP.partnumber, MP.partname, MP.partTypeId, PC.pcName, PL.partlocation_name, "
						+ " R.location_quantity FROM InventoryLocation AS R "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = R.partid "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.locationId " + " WHERE (" + Query
						+ ")  AND R.companyId = " + companyId
						+ " AND R.markForDelete = 0 ORDER BY R.inventory_location_id desc",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<InventoryLocationDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryLocationDto>();
			InventoryLocationDto list = null;
			for (Object[] result : results) {
				list = new InventoryLocationDto();

				list.setInventory_location_id((Long) result[0]);
				list.setPartid((Long) result[1]);
				list.setPartnumber((String) result[2]);
				list.setPartname((String) result[3]);
				list.setParttype(PartType.getPartTypeName((short) result[4]));
				list.setCategory((String) result[5]);
				list.setLocation((String) result[6]);
				if(list.getParttype() != "QUANTITY") {
					list.setLiter_quantity((Double) result[7]);	
				}else {
				list.setLocation_quantity((Double) result[7]);
				}

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryService#
	 * Report_Purchase_Inventory(java.lang.String)
	 */
	@Transactional
	public List<InventoryDto> Report_Purchase_Inventory(String Query, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager.createQuery(
					"SELECT I.inventory_id, I.inventory_Number, I.partid, MP.partnumber, MP.partname, MP.partTypeId, PC.pcName,  PM.pmName, PL.partlocation_name,  I.quantity,"
							+ " I.history_quantity, I.unitprice, I.purchaseorder_id, PO.purchaseorder_Number, I.invoice_number, I.invoice_amount, I.invoice_date, I.vendor_id, V.vendorName, V.vendorLocation, "
							+ " MU.pmuSymbol, I.discount, I.tax, I.total, I.description, I.inventory_location_id, I.inventory_all_id "
							+ " FROM Inventory AS I "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = I.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = I.locationId "
							+ " INNER JOIN InventoryAll IAL ON IAL.inventory_all_id = I.inventory_all_id "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = I.vendor_id "
							+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
							+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = I.purchaseorder_id "
							+ " LEFT JOIN PartMeasurementUnit MU ON MU.pmuid = MP.unitTypeId"
							+ " WHERE IAL.companyId = :comID AND I.markForDelete = 0 " + Query + " ",
					Object[].class);
			queryt.setParameter("comID", companyId);
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
						Dto.setQuantity((Double) result[9]);
					}

					if ((Double) result[10] != null) {
						Dto.setHistory_quantity((Double) result[10]);
					}

					if ((Double) result[11] != null) {
						Dto.setUnitprice(round((Double) result[11], 2));
					}
					Dto.setPurchaseorder_id((Long) result[12]);
					Dto.setPurchaseorder_Number((Long) result[13]);
					Dto.setInvoice_number((String) result[14]);
					Dto.setInvoice_amount((String) result[15]);
					if ((Date) result[16] != null) {
						Dto.setInvoice_date(dateName.format((Date) result[16]));
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

	@Transactional
	public List<InventoryDto> Report_Part_Inventory_Stock_Invoice_DateRange_Report(String Query, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager.createQuery(
					"SELECT I.inventory_id, I.inventory_Number, I.partid, MP.partnumber, MP.partname, MP.partTypeId, PC.pcName,  PM.pmName, PL.partlocation_name,  I.quantity,"
							+ " I.history_quantity, I.unitprice, I.purchaseorder_id, PO.purchaseorder_Number, I.invoice_number, I.invoice_amount, I.invoice_date, I.vendor_id, V.vendorName, V.vendorLocation, "
							+ " MU.pmuSymbol, I.discount, I.tax, I.total, I.description, I.inventory_location_id, I.inventory_all_id FROM Inventory AS I "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = I.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = I.locationId "
							+ " INNER JOIN InventoryAll IAL ON IAL.inventory_all_id = I.inventory_all_id "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = I.vendor_id "
							+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
							+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = I.purchaseorder_id "
							+ " LEFT JOIN PartMeasurementUnit MU ON MU.pmuid = MP.unitTypeId"
							+ " WHERE IAL.companyId = :comID AND I.markForDelete = 0 " + Query + " ",
					Object[].class);
			queryt.setParameter("comID", companyId);
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
						Dto.setQuantity((Double) result[9]);
					}

					if ((Double) result[10] != null) {
						Dto.setHistory_quantity((Double) result[10]);
					}

					if ((Double) result[11] != null) {
						Dto.setUnitprice(round((Double) result[11], 2));
					}
					Dto.setPurchaseorder_id((Long) result[12]);
					Dto.setPurchaseorder_Number((Long) result[13]);
					Dto.setInvoice_number((String) result[14]);
					Dto.setInvoice_amount((String) result[15]);
					if ((Date) result[16] != null) {
						Dto.setInvoice_date(dateName.format((Date) result[16]));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryService#
	 * Report_list_Of_Location_Inventory_Total_Inventory_Amount(java.lang. String)
	 */
	@Transactional
	public double Report_list_Of_Location_Inventory_Total_Inventory_Amount(String inventoryquery, Integer companyId) {

		Double results;
		try {
			Query queryt = entityManager.createQuery("SELECT SUM(R.total) FROM Inventory AS R WHERE " + inventoryquery
					+ " AND R.companyId = " + companyId + " AND R.markForDelete = 0 ");
			results = (Double) queryt.getSingleResult();
			if (results == null) {
				return 0.0;
			}
		} catch (Exception e) {
			return 0.0;
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryService#
	 * Report_list_Of_Location_Inventory_Total_Inventory_Amount(java.lang. String)
	 */
	@Transactional
	public double Report_Part_Inventory_Stock_Invoice_DateRange_Report_Amount(String inventoryquery) {

		Double results;
		try {
			Query queryt = entityManager.createQuery(
					"SELECT SUM(I.total) FROM Inventory AS I WHERE I.markForDelete=0 " + inventoryquery + "");
			results = (Double) queryt.getSingleResult();
			if (results == null) {
				return 0.0;
			}
		} catch (Exception e) {
			return 0.0;
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryService#
	 * Get_Validate_InventoryPurchaseOrderId_Details(java.lang.String)
	 */
	@Transactional
	public List<Inventory> Get_Validate_InventoryPurchaseOrderId_Details(String purchaseorder_id, Integer companyid)
			throws Exception {

		return InventoryDao.Get_Validate_InventoryPurchaseOrderId_Details(purchaseorder_id, companyid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryService#
	 * Find_InventoryTranfer_Details_ID(java.lang.Long)
	 */
	@Transactional
	public InventoryTransfer Find_InventoryTranfer_Details_ID(Long inventory_transfer_id, Integer companyId) {
		// Find Transfer Id details
		return InventoryTransferRepository.Find_InventoryTranfer_Details_ID(inventory_transfer_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryService#
	 * Update_InventoryTransfer_ReceivedBy_Details(java.util.Date, java.lang.String,
	 * java.lang.String, java.util.Date, java.lang.Long)
	 */
	@Transactional
	public void Update_InventoryTransfer_ReceivedBy_Details(Date transfer_receiveddate, String transfer_receivedReason,
			String lastmodifiedby, Date lastupdated_DATE,  short TRANSFER_STATUS_ID, Long inventory_transfer_id, Integer companyId) {
		// Update Received Details
		InventoryTransferRepository.Update_InventoryTransfer_ReceivedBy_Details(transfer_receiveddate,
				transfer_receivedReason, lastmodifiedby, lastupdated_DATE, TRANSFER_STATUS_ID,  inventory_transfer_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryService#
	 * getDeployment_InventoryAll_id_To_InventoryTransfer_UserName_Status(java.
	 * lang.String, java.lang.Integer)
	 */
	@Transactional
	public Page<InventoryTransfer> getDeployment_InventoryAll_id_To_InventoryTransfer_transfer_byEmail_Status(
			Long transfer_by_ID, Integer pageNumber, Integer companyId) {

		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return InventoryTransferRepository.getDeployment_InventoryAll_id_To_InventoryTransfer_transfer_byEmail_Status(
				transfer_by_ID, companyId, pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryService#
	 * Get_InventoryAll_id_To_InventoryTransfer_UserName_Status(java.lang. String,
	 * java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public List<InventoryTransferDto> Get_InventoryAll_id_To_InventoryTransfer_transfer_byEmail_Status(Long UserID,
			Integer pageNumber, Integer companyId) throws Exception {

		
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.inventory_transfer_id, R.transfer_partid, MP.partnumber, MP.partname, PC.pcName, PL.partlocation_name, PLT.partlocation_name, "
						+ " R.transfer_quantity, R.transfer_description, US.firstName, R.transfer_via_ID, R.transfer_date, UR.firstName, R.transfer_receiveddate, "
						+ " R.TRANSFER_STATUS_ID, R.transfer_inventory_all_id, R.transfer_inventory_id FROM InventoryTransfer AS R "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = R.transfer_partid "
						+ " INNER JOIN PartCategories AS PC ON PC.pcid = MP.categoryId "
						+ " INNER JOIN User AS US ON US.id = R.transfer_by_ID "
						+ " INNER JOIN User AS UR ON UR.id = R.transfer_receivedby_ID "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.transfer_from_locationId "
						+ " INNER JOIN PartLocations PLT ON PLT.partlocation_id = R.transfer_to_locationId "
						+ " WHERE R.transfer_by_ID=" + UserID + "  AND R.companyId = " + companyId
						+ " AND R.markForDelete = 0 ORDER BY R.inventory_transfer_id desc",
				Object[].class);
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE_FIFTY);
		queryt.setMaxResults(PAGE_SIZE_FIFTY);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTransferDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTransferDto>();
			InventoryTransferDto list = null;
			for (Object[] result : results) {
				list = new InventoryTransferDto();

				list.setInventory_transfer_id((Long) result[0]);
				list.setTransfer_partid((Long) result[1]);
				list.setTransfer_partnumber((String) result[2]);
				list.setTransfer_partname((String) result[3]);
				list.setTransfer_Category((String) result[4]);
				list.setTransfer_from_location((String) result[5]);
				list.setTransfer_to_location((String) result[6]);
				list.setTransfer_quantity((Double) result[7]);
				list.setTransfer_description((String) result[8]);
				list.setTransfer_by((String) result[9]);
				list.setTransfer_via_ID((short) result[10]);
				list.setTransfer_via(InventoryTransferViaType.getInventoryTransferViaTypeName((short) result[10]));
				if ((Date) result[11] != null) {
					list.setTransfer_date(dateName.format((Date) result[11]));
				}
				list.setTransfer_receivedby((String) result[12]);
				if ((Date) result[13] != null) {
					list.setTransfer_receiveddate(dateName.format((Date) result[13]));
				}
				list.setTRANSFER_STATUS(InventoryTransferStatus.getInventoryTransferName((short) result[14]));
				list.setTransfer_inventory_all_id((Long) result[15]);
				list.setTransfer_inventory_id((Long) result[16]);
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryService#
	 * getDeployment_InventoryAll_id_To_InventoryTransfer_UserName_Status(java.
	 * lang.String, java.lang.Integer)
	 */
	@Transactional
	public Page<InventoryTransfer> getDeployment_InventoryAll_id_To_InventoryTransfer_transfer_receivedbyEmail(
			Long transfer_receivedby_ID, Integer pageNumber, Integer companyId) {

		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return InventoryTransferRepository.getDeployment_InventoryAll_id_To_InventoryTransfer_transfer_receivedbyEmail(
				transfer_receivedby_ID, companyId, pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryService#
	 * Get_InventoryAll_id_To_InventoryTransfer_UserName_Status(java.lang. String,
	 * java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public List<InventoryTransferDto> Get_InventoryAll_id_To_InventoryTransfer_transfer_receivedbyEmail(
			Long receivedby_ID, Integer pageNumber, Integer companyId) throws Exception {


		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.inventory_transfer_id, R.transfer_partid, MP.partnumber, MP.partname, PC.pcName, PL.partlocation_name, PLT.partlocation_name, "
						+ " R.transfer_quantity, R.transfer_description, US.firstName, R.transfer_via_ID, R.transfer_date, UR.firstName, R.transfer_receiveddate, "
						+ " R.TRANSFER_STATUS_ID, R.transfer_inventory_all_id, R.transfer_inventory_id, R.transfer_receivedby_ID,R.inventoryRequisitionId FROM InventoryTransfer AS R "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = R.transfer_partid "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
						+ " INNER JOIN User AS US ON US.id = R.transfer_by_ID "
						+ " INNER JOIN User AS UR ON UR.id = R.transfer_receivedby_ID "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.transfer_from_locationId "
						+ " INNER JOIN PartLocations PLT ON PLT.partlocation_id = R.transfer_to_locationId "
						+ " WHERE R.transfer_receivedby_ID=" + receivedby_ID + " AND R.companyId = " + companyId
						+ " AND R.markForDelete = 0 ORDER BY R.inventory_transfer_id desc",
				Object[].class);
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE_FIFTY);
		queryt.setMaxResults(PAGE_SIZE_FIFTY);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTransferDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTransferDto>();
			InventoryTransferDto list = null;
			for (Object[] result : results) {
				list = new InventoryTransferDto();

				list.setInventory_transfer_id((Long) result[0]);
				list.setTransfer_partid((Long) result[1]);
				list.setTransfer_partnumber((String) result[2]);
				list.setTransfer_partname((String) result[3]);
				list.setTransfer_Category((String) result[4]);
				list.setTransfer_from_location((String) result[5]);
				list.setTransfer_to_location((String) result[6]);
				list.setTransfer_quantity((Double) result[7]);
				list.setTransfer_description((String) result[8]);
				list.setTransfer_by((String) result[9]);
				list.setTransfer_via_ID((short) result[10]);
				list.setTransfer_via(InventoryTransferViaType.getInventoryTransferViaTypeName((short) result[10]));
				if ((Date) result[11] != null) {
					list.setTransfer_date(dateName.format((Date) result[11]));
				}
				list.setTransfer_receivedby((String) result[12]);
				if ((Date) result[13] != null) {
					list.setTransfer_receiveddate(dateName.format((Date) result[13]));
				}
				list.setTRANSFER_STATUS_ID((short) result[14]);
				list.setTRANSFER_STATUS(InventoryTransferStatus.getInventoryTransferName((short) result[14]));
				list.setTransfer_inventory_all_id((Long) result[15]);
				list.setTransfer_inventory_id((Long) result[16]);
				list.setTransfer_receivedby_ID((Long) result[17]);
				if( result[18] != null) {
				list.setINVRID((Long) result[18]);
				}
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryService#
	 * Delete_InventoryTransfer_History_Delete_By_ID(java.lang.Long)
	 */
	@Transactional
	public void Delete_InventoryTransfer_History_Delete_By_ID(Long inventory_transfer_id, Integer companyId) {

		InventoryTransferRepository.Delete_InventoryTransfer_History_Delete_By_ID(inventory_transfer_id, companyId);
	}

	@Override
	@Transactional
	public Inventory save_Inventory(Inventory inventory) throws Exception {
		CustomUserDetails userDetails = null;
		InventorySequenceCounter sequenceCounter = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			/**
			 * getting next number
			 */
			/*
			 * sequenceCounter =
			 * inventorySequenceRepository.findNextInventoryNumber(userDetails.getCompany_id
			 * (), SequenceTypeContant.SEQUENCE_TYPE_PART_INVENTORY);
			 */
			sequenceCounter = inventorySequenceService.findNextInventoryNumber(userDetails.getCompany_id(),
					SequenceTypeContant.SEQUENCE_TYPE_PART_INVENTORY);
			inventory.setInventory_Number(sequenceCounter.getNextVal());
			/**
			 * Saving Inventory
			 */
			return InventoryDao.save(inventory);
			/**
			 * updating next number
			 *//*
				 * inventorySequenceRepository.updateNextInventoryNumber(sequenceCounter.
				 * getNextVal() + 1, userDetails.getCompany_id(),
				 * sequenceCounter.getSequence_Id(),
				 * SequenceTypeContant.SEQUENCE_TYPE_PART_INVENTORY);
				 */
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
			sequenceCounter = null;
		}
	}

	@Override
	public List<InventoryLocation> getInventoryLocationById(Long inventory_all_id, Integer companyId) throws Exception {
		
		return InventoryLocationRepository.getInventoryLocationById(inventory_all_id, companyId);
	}

	@Transactional
	public List<InventoryTransferDto> Get_Transfer_Inventory_Report_Wise_location(String dateRangeFrom, String dateRangeTo,
			String query, Integer companyId) {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.inventory_transfer_id, R.transfer_partid, MP.partnumber, MP.partname, PC.pcName, PL.partlocation_name, PLT.partlocation_name, "
						+ " R.transfer_quantity, R.transfer_description, US.firstName, R.transfer_via_ID, R.transfer_date, UR.firstName, R.transfer_receiveddate, "
						+ " R.TRANSFER_STATUS_ID, R.transfer_inventory_all_id FROM InventoryTransfer AS R "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = R.transfer_partid "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
						+ " LEFT JOIN User AS US ON US.id = R.transfer_by_ID "
						+ " LEFT JOIN User AS UR ON UR.id = R.transfer_receivedby_ID "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.transfer_from_locationId "
						+ " INNER JOIN PartLocations PLT ON PLT.partlocation_id = R.transfer_to_locationId "
						+ " WHERE R.companyId ="+companyId+" AND R.transfer_date BETWEEN '" + dateRangeFrom + "' AND  '"
						+ dateRangeTo + "' " + query + " AND R.markForDelete = 0 ORDER BY R.inventory_transfer_id desc",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTransferDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTransferDto>();
			InventoryTransferDto list = null;
			for (Object[] result : results) {
				list = new InventoryTransferDto();

				list.setInventory_transfer_id((Long) result[0]);
				list.setTransfer_partid((Long) result[1]);
				list.setTransfer_partnumber((String) result[2]);
				list.setTransfer_partname((String) result[3]);
				list.setTransfer_Category((String) result[4]);
				list.setTransfer_from_location((String) result[5]);
				list.setTransfer_to_location((String) result[6]);
				list.setTransfer_quantity((Double) result[7]);
				list.setTransfer_description((String) result[8]);
				list.setTransfer_by((String) result[9]);
				list.setTransfer_via_ID((short) result[10]);
				list.setTransfer_via(InventoryTransferViaType.getInventoryTransferViaTypeName((short) result[10]));
				if ((Date) result[11] != null) {
					list.setTransfer_date(dateName.format((Date) result[11]));
				}
				list.setTransfer_receivedby((String) result[12]);
				if ((Date) result[13] != null) {
					list.setTransfer_receiveddate(dateName.format((Date) result[13]));
				}
				list.setTRANSFER_STATUS(InventoryTransferStatus.getInventoryTransferName((short) result[14]));
				list.setTransfer_inventory_all_id((Long) result[15]);
				Dtos.add(list);
			}
		}
		return Dtos;
	}
	
	@Override
	public List<InventoryLocationDto> GetLowStockInventorLocationDetails(Integer pageNumber, CustomUserDetails	userDetails, Integer locationId) throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager.createQuery(
					"SELECT IL.inventory_location_id, IL.partid, MP.partnumber, MP.partname, IL.location_quantity, LO.lowstocklevel,"
					+ " LO.reorderquantity, PL.partlocation_name, IL.locationId  "
					+ " FROM InventoryLocation AS IL "
					+ " INNER JOIN MasterParts MP ON MP.partid = IL.partid"
					+ " INNER JOIN LowStockInventory LO ON LO.partid = IL.partid AND LO.locationId = "+locationId+"" 
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = IL.locationId"
					+ " WHERE IL.locationId = "+locationId+" AND IL.companyId = "+userDetails.getCompany_id()+" AND (IL.location_quantity <= LO.lowstocklevel) AND IL.location_quantity > 0.00 AND IL.markForDelete = 0",
					Object[].class);
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]> results = queryt.getResultList();

			List<InventoryLocationDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryLocationDto>();
				InventoryLocationDto list = null;
				for (Object[] result : results) {
					list = new InventoryLocationDto();

					list.setInventory_location_id((Long) result[0]);
					list.setPartid((Long) result[1]);
					list.setPartnumber((String) result[2]);
					list.setPartname((String) result[3]);
					list.setLocation_quantity((Double) result[4]);
					list.setLowstocklevel((Integer) result[5]);
					list.setReorderquantity((Integer) result[6]);
					list.setLocation((String) result[7]);
					list.setLocationId((Integer) result[8]);
					

					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public Inventory getInventoryDetails(Long inventory_id, Integer companyId) throws Exception {
		return InventoryDao.getInventoryDetails(inventory_id, companyId);

	}
	
	@Transactional
	public void update_anyPartNumberAsign_InPartInvoice(Long partinvoiceId, Integer companyId) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		 PartInvoiceRepository.update_anyPartNumberAsign_InPartInvoice(partinvoiceId, userDetails.getCompany_id());

	}
	
	
	@Transactional
	public void update_Part_Inventory (Inventory inventory) throws Exception {
		
		InventoryDao.update_Part_Inventory( inventory.getLocationId(), inventory.getInvoice_number(),
				inventory.getInvoice_date(), inventory.getInvoice_amount(), inventory.getVendor_id(),
				inventory.getDescription(), inventory.getLastModifiedById(), inventory.getLastupdated(),
				inventory.getInventory_id(), inventory.getCompanyId());
		
	}
	
	@Transactional
	public void update_Part_Inventory_Invoice (PartInvoice PartInvoice) throws Exception {
		double balanceAmount= Double.parseDouble(PartInvoice.getInvoiceAmount());
		PartInvoiceRepository.update_Part_Inventory_Invoice(PartInvoice.getWareHouseLocation(),
				PartInvoice.getInvoiceNumber(), PartInvoice.getInvoiceDate(),
				PartInvoice.getInvoiceAmount(), PartInvoice.getVendorId(), PartInvoice.getDescription(), 
				PartInvoice.getLastModifiedById(), PartInvoice.getLastUpdated_Date(), PartInvoice.getQuantity(), balanceAmount,
				PartInvoice.getPartInvoiceId(), PartInvoice.getCompanyId(), PartInvoice.getPaymentTypeId(), PartInvoice.getLabourCharge(), PartInvoice.getTallyCompanyId(),PartInvoice.getSubLocationId(),PartInvoice.getVendorPaymentStatus());
		
	}
	
	@Override
	public List<InventoryDto> getPartInvoiceAmountDetailsToEdit(Long invoiceId , Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT I.inventory_id, I.inventory_Number, I.partid, I.locationId, I.quantity, I.history_quantity, I.unitprice, I.discount, I.tax,"
						+ " I.total, I.purchaseorder_id, I.invoice_number, I.invoice_date, I.invoice_amount, I.vendor_id, I.description,  "
						+ " I.inventory_all_id, I.partInvoiceId, V.vendorName, V.vendorLocation, MP.partnumber, MP.partname, MP.partTypeId, PC.pcName, "
						+ " PM.pmName, PL.partlocation_name, PO.purchaseorder_Number, MU.pmuSymbol, I.inventory_location_id, "
						+ " I.discountTaxTypeId, I.mainQuantity, I.mainUnitprice, MP.unitTypeId "
						+ " FROM Inventory AS I "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = I.partid "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = I.locationId "
						+ " INNER JOIN InventoryAll IAL ON IAL.inventory_all_id = I.inventory_all_id "
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
						+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = I.purchaseorder_id "
						+ " LEFT JOIN PartMeasurementUnit MU ON MU.pmuid = MP.unitTypeId "
						+ " LEFT JOIN Vendor AS V ON V.vendorId = I.vendor_id "
						+ " WHERE I.partInvoiceId = "+invoiceId+" AND I.companyId = "+companyId+" AND I.markForDelete = 0 ",
						Object[].class);

		List<Object[]> results = queryt.getResultList();

		List<InventoryDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryDto>();
			InventoryDto list = null;

			for (Object[] result : results) {
				list = new InventoryDto();
				
				if(result != null) {
				list.setInventory_id((long) result[0]);
				if(result[1] != null) {
				list.setInventory_Number((long) result[1]);
				}
				list.setPartid((long) result[2]);
				list.setLocationId((int) result[3]);
				list.setQuantity((double) result[4]);
				list.setHistory_quantity((double) result[5]);
				list.setUnitprice((double) result[6]);
				list.setDiscount((double) result[7]);
				list.setTax((double) result[8]);
				list.setTotal((double) result[9]);
				list.setPurchaseorder_id((long) result[10]);
				list.setInvoice_number((String) result[11]);
				list.setInv_date((Date) result[12]);
				//list.setInvoice_date((String) result[12]);
				list.setInvoice_amount((String) result[13]);
				list.setVendor_id((int) result[14]);
				//list.setDescription((String) result[15]);
				list.setInventory_all_id((long) result[16]);
				list.setPartInvoiceId((long) result[17]);
				list.setVendor_name((String) result[18]);
				list.setVendor_location((String) result[19]);
				list.setPartnumber((String) result[20]);
				list.setPartname((String) result[21]);
				list.setPartTypeId((short) result[22]);
				list.setCategory((String) result[23]);
				list.setMake((String) result[24]);
				list.setLocation((String) result[25]);
				if(result[26] != null) {
				list.setPurchaseorder_Number((long) result[26]);
				}
				list.setUnittype((String) result[27]);
				list.setInventory_location_id((long) result[28]);
				if(result[29] != null) {
					list.setDiscountTaxTypeId((short) result[29]);
				} else {
					list.setDiscountTaxTypeId((short) 1);
				}
				
				if(result[30] != null)
					list.setMainQuantity((Double) result[30]);
					
				if(result[31] != null)
					list.setMainUnitprice((Double) result[31]);
				
				list.setUnitTypeId((long) result[32]);
				
				Dtos.add(list);
				}
			}
		}
		return Dtos;
	}
	
	@Override
	public PartInvoiceDto Get_list_PartInvoiceDetails(Long partInvoiceId, Integer companyId)
			throws Exception {
		Query query = entityManager.createQuery(
						  " SELECT R.partInvoiceId, R.partInvoiceNumber, PL.partlocation_name, R.wareHouseLocation, R.invoiceNumber, R.invoiceAmount,"
						+ " R.invoiceDate, R.vendorId, VN.vendorName, VN.vendorLocation, R.paymentTypeId, R.description,"
						+ " R.createdOn, R.lastUpdated_Date, R.createdById, R.lastModifiedById, R.anyPartNumberAsign, R.balanceAmount,"
						+ " R.labourCharge, R.tallyCompanyId, TC.companyName, R.subLocationId, PSL.partlocation_name  "
						+ " FROM PartInvoice AS R "
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.wareHouseLocation "
						+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = R.subLocationId "
						+ " LEFT JOIN Vendor VN ON VN.vendorId = R.vendorId "
						+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = R.tallyCompanyId"
						+ " WHERE R.partInvoiceId = "+partInvoiceId+" AND R.companyId = "+companyId+" AND R.markForDelete = 0 ");

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		PartInvoiceDto select;
		if (result != null) {
			
			select	= new PartInvoiceDto();
			select.setPartInvoiceId((long) result[0]);
			select.setPartInvoiceNumber((long) result[1]);
			select.setPartLocation((String) result[2]);
			select.setWareHouseLocation((int) result[3]);
			select.setInvoiceNumber((String) result[4]);
			select.setInvoiceAmount((String) result[5]);
			select.setInvoiceDate((Timestamp) result[6]);
			select.setInvoiceDateOn(DateTimeUtility.getDateFromTimeStamp(select.getInvoiceDate(), DateTimeUtility.DD_MM_YYYY));
			select.setVendorId((int) result[7]);
			select.setVendorName((String) result[8]);
			select.setVendorLocation((String) result[9]);
			select.setPaymentTypeId((short) result[10]);
			select.setDescription((String) result[11]);
			select.setCreatedOn((Timestamp) result[12]);
			if(result[13] != null) {
			select.setLastUpdated_Date((Timestamp) result[13]);
			}
			select.setCreatedById((long) result[14]);
			select.setLastModifiedById((long) result[15]);
			select.setAnyPartNumberAsign((boolean) result[16]);
			if( result[17] != null) {
			select.setBalanceAmount((double) result[17]);
			}
			if(result[18] != null) {
				select.setLabourCharge((double) result[18]);
			}else {
				select.setLabourCharge((double) 0);
			}
			if(result[19] != null)
				select.setTallyCompanyId((Long) result[19]);
			if(result[20] != null)
				select.setTallyCompanyName((String) result[20]);
			if(result[21] != null) {
				select.setSubLocationId((Integer) result[21]);
				select.setSubLocation((String) result[22]);
			}
			
		} else {
			return null;
		}

		return select;
	}
	
	@Transactional
	public void update_InventoryAll_Quantity_From_PartInvoice(Long inventory_all_id, Integer companyId, Double all_quantity)
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {
			entityManager.createQuery(
					"UPDATE InventoryAll SET all_quantity = all_quantity - "+all_quantity+", "
						+ " lastupdated = '"+DateTimeUtility.getCurrentTimeStamp()+"', lastModifiedById = "+userDetails.getId()+" "
						+ " WHERE inventory_all_id = "+inventory_all_id+" AND companyId = "+userDetails.getCompany_id()+" "
						+ " AND markForDelete = 0 ")
					.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Transactional
	public void update_InventoryLocation_Quantity_From_PartInvoice(Long inventory_location_id, Integer companyId, Double location_quantity)
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {
			entityManager.createQuery(
					"UPDATE InventoryLocation SET location_quantity = location_quantity - "+location_quantity+" "
						+ " WHERE inventory_location_id = "+inventory_location_id+" AND companyId = "+userDetails.getCompany_id()+" "
						+ " AND markForDelete = 0 ")
					.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Transactional
	public List<Inventory> Get_InventoryDetails_From_PartInvoiceId(long partInvoiceId, Integer companyId) 
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return InventoryDao.Get_InventoryDetails_From_PartInvoiceId(partInvoiceId, userDetails.getCompany_id());
	}
	
	@Transactional
	public void update_InvoiceAmountOf_Inventory(Long partInvoiceId, Integer companyId, String invoiceAmount)
			throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		try {
			entityManager.createQuery(
					"UPDATE Inventory SET invoice_amount = "+invoiceAmount+", "
						+ " lastupdated = '"+DateTimeUtility.getCurrentTimeStamp()+"', lastModifiedById = "+userDetails.getId()+" "
						+ " WHERE partInvoiceId = "+partInvoiceId+" AND companyId = "+userDetails.getCompany_id()+" "
						+ " AND markForDelete = 0 ")
					.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	@Transactional
	public Map<String, Object> updateAddMorePartInventoryInvoice(ValueObject valueObject) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		InventoryDto InventoryDto = null;
		final String[] partid;
		final String[] make;
		final String[] quantity;
		final String[] unitprice;
		final String[] discount;
		final String[] tax;
		
		try {
			InventoryDto = (InventoryDto) valueObject.get("InventoryDto");
			partid		 = (String[]) valueObject.get("partid");
			make		 = (String[]) valueObject.get("make");
			quantity	 = (String[]) valueObject.get("quantity");
			unitprice	 = (String[]) valueObject.get("unitprice");
			discount	 = (String[]) valueObject.get("discount");
			tax		 	 = (String[]) valueObject.get("tax");
			
			System.err.println("quantity : "+quantity);
			
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Double Qty = 0.0;
			Double originalQty = 0.0;
			Double InvoiceAmt = 0.0;
			
			if (InventoryDto.getPartInvoiceId() != null) {
				//PartInvoice PartInvoice = prepare_UpdatePartInvoice(PartInvoiceDto);\
				PartInvoice invoiceValidate =partInvoiceService.getPartInvoiceDetails(InventoryDto.getPartInvoiceId(), userDetails.getCompany_id());
				HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG);
				HashMap<String,Object> companyConfiguration =companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG );
				Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
				MasterParts	masterParts	= null;
				PartMeasurementUnit	measurementUnit	= null;
				if (partid != null) {
					for (int i = 0; i < partid.length; i++) {
						
						Double originalQuantity		= Double.parseDouble(quantity[i]);
						Double originalUnitPrice	= Double.parseDouble(unitprice[i]);
						
						originalQty += originalQuantity;
						
						masterParts	= masterPartsService.getMasterParts(Long.parseLong(partid[i]));
						
						measurementUnit	= measermentHM.get(Integer.parseInt(masterParts.getUnitTypeId()+""));
						
						if(measurementUnit != null && measurementUnit.getConvertTo() != null && measurementUnit.getConvertTo() > 0) {
							quantity[i] = (Double.parseDouble(quantity[i]) * measurementUnit.getConversionRate())+"";
							unitprice[i] = (Double.parseDouble(unitprice[i]) / measurementUnit.getConversionRate())+"";
						}

							InventoryAll Inventoryall = inventoryBL.prepareInventoryAll(Long.parseLong(partid[i]+""));
							InventoryLocation InventoryLocation = inventoryBL.prepareInventoryLocation(InventoryDto, Long.parseLong(partid[i]+""));
							InventoryLocation.setCompanyId(Inventoryall.getCompanyId());
							Qty += Double.parseDouble(quantity[i]);
							
							Inventory inventory = new Inventory();
							inventory.setInventory_id(InventoryDto.getInventory_id());	
							inventory.setPartid(Long.parseLong(partid[i]+""));
							inventory.setLocationId(InventoryDto.getLocationId());
							inventory.setUnitprice(Double.parseDouble(unitprice[i]));
							inventory.setPurchaseorder_id(DEFAULT_PURCHAGE_ORDER_VALUE);
							inventory.setInvoice_number(InventoryDto.getInvoice_number());
							inventory.setInvoice_amount(InventoryDto.getInvoice_amount());
							inventory.setMainQuantity(originalQuantity);
							inventory.setMainUnitprice(originalUnitPrice);
							
							if (InventoryDto.getInvoice_date() != null) {
								java.util.Date date = sqlDateFormat.parse(InventoryDto.getInvoice_date());
								java.sql.Date start_date = new java.sql.Date(date.getTime());
								inventory.setInvoice_date(start_date);
							}

							if (InventoryDto.getVendor_id() != 0) {
								inventory.setVendor_id(InventoryDto.getVendor_id());
							} else {
								inventory.setVendor_id(0);
							}

							inventory.setDescription(InventoryDto.getDescription());
							inventory.setCreatedById(userDetails.getId());
							inventory.setLastModifiedById(userDetails.getId());

							Date currentDateUpdate = new Date();
							Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
							inventory.setCreated(toDate);
							inventory.setLastupdated(toDate);
							inventory.setMarkForDelete(false);
							inventory.setPartInvoiceId(InventoryDto.getPartInvoiceId());
							inventory.setCompanyId(Inventoryall.getCompanyId());
							inventory.setSubLocationId(InventoryDto.getSubLocationId());
							// save part Manufacturers Service in Master part
							try {
								// first validate part_id is there are not in Inventory
								// All Table
							List<InventoryAll> validate = Validate_InventoryAll(Long.parseLong(partid[i]+""),	Inventoryall.getCompanyId());
							if (validate != null && !validate.isEmpty()) {

								Long Inventory_all_id = (long) 0;
								Long Inventory_Location_id = (long) 0;

								for (InventoryAll updateInventory : validate) {
									// get part and AllQuantity in all_Ready in db

									Inventory_all_id = updateInventory.getInventory_all_id();

									break;
								}
								
								List<InventoryLocation> validate_Location = Validate_Inventory_Location(Long.parseLong(partid[i]+""), InventoryDto.getLocationId());
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
									add_InventoryLocation(InventoryLocation);

									Inventory_Location_id = InventoryLocation.getInventory_location_id();

								}
								
								// Quantity all ready save to update in all ,
								// Location Quantity details
								inventory.setHistory_quantity(Double.parseDouble(quantity[i]));
								inventory.setQuantity(Double.parseDouble(quantity[i]));
								inventory.setUnitprice(Double.parseDouble(unitprice[i]));
								inventory.setDiscount(Double.parseDouble(discount[i]));
								inventory.setTax(Double.parseDouble(tax[i]));

								Double Quantity = Double.parseDouble(quantity[i]);
								Double eaccost = Double.parseDouble(unitprice[i]);
								Double dis_Ca = Double.parseDouble(discount[i]);
								Double tax_Ca = Double.parseDouble(tax[i]);

								Double discountCost = 0.0;
								Double TotalCost = 0.0;

								discountCost = (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
								TotalCost = round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
								inventory.setTotal(TotalCost);
								InvoiceAmt += inventory.getTotal();

								if (make[i] != null) {

									inventory.setMake(make[i]);
								}
								inventory.setInventory_all_id(Inventory_all_id);
								inventory.setInventory_location_id(Inventory_Location_id);
								inventory.setSerialNoAddedForParts(0);
								
								
								save_Inventory(inventory);

								// Insert Inventory QUANTITY Details to Update
								// LOCATION AND ALL LOCATION QUANTITY DETAILS

								update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(Long.parseLong(partid[i]+""), InventoryDto.getLocationId());

								update_InventoryAll_PARTID_PARTNAME_GENRAL(Long.parseLong(partid[i]+""));
								
								
							} else {
								// get part and AllQuantity save
								Inventoryall.setAll_quantity(Double.parseDouble(quantity[i]));
								add_InventoryAll(Inventoryall);

								// get part and Location Quantity save
								InventoryLocation.setLocation_quantity(Double.parseDouble(quantity[i]));
								InventoryLocation.setInventoryall(Inventoryall);
								add_InventoryLocation(InventoryLocation);

								inventory.setHistory_quantity(Double.parseDouble(quantity[i]));
								inventory.setQuantity(Double.parseDouble(quantity[i]));
								inventory.setUnitprice(Double.parseDouble(unitprice[i]));

								inventory.setDiscount(Double.parseDouble(discount[i]));
								inventory.setTax(Double.parseDouble(tax[i]));

								Double Quantity = Double.parseDouble(quantity[i]);
								Double eaccost = Double.parseDouble(unitprice[i]);
								Double dis_Ca = Double.parseDouble(discount[i]);
								Double tax_Ca = Double.parseDouble(tax[i]);

								Double discountCost = 0.0;
								Double TotalCost = 0.0;

								discountCost = (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
								TotalCost = round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
								inventory.setTotal(TotalCost);

								if (make[i] != null) {

									inventory.setMake(make[i]);
								}
								inventory.setInventory_all_id(Inventoryall.getInventory_all_id());
								inventory.setInventory_location_id(InventoryLocation.getInventory_location_id());
								inventory.setSerialNoAddedForParts(0);
								
								save_Inventory(inventory);
								
							 }
							
							if((boolean) configuration.get("showRefreshmentOption")) {
								
								if(masterParts != null && masterParts.isRefreshment()) {
									ValueObject	valueObject2	= new ValueObject();
									if(inventory.getInvoice_date() != null) {
										valueObject2.put("transactionDate", inventory.getInvoice_date());
									}else {
										valueObject2.put("transactionDate", DateTimeUtility.getCurrentDate());
									}
									
									valueObject2.put("partId", inventory.getPartid());
									valueObject2.put("locationId", inventory.getLocationId());
									valueObject2.put("quantity", inventory.getQuantity());
									valueObject2.put("companyId", userDetails.getCompany_id());
									valueObject2.put("addedQuantity", inventory.getQuantity());
									valueObject2.put("usedQuantity", 0.0);
									valueObject2.put("numberType", "I-"+ inventory.getInventory_Number()+" Part Invoice Add");
									valueObject2.put("transactionId", inventory.getInventory_id());
									valueObject2.put("primaryType", InventoryStockTypeConstant.STOCK_TYPE_ADD);
									valueObject2.put("transactionType", InventoryStockTypeConstant.STOCK_TYPE_ADD);
									dayWiseInventoryStockService.processLocationWiseInventoryStockDetails(valueObject2);	
								}
							}
							
						   } catch (Exception e) {
								LOGGER.error("Error"+ e);
								e.printStackTrace();
								model.put("redirect:/NewInventory/1.in?danger=true", true);
						   }
					}
					
					Double previousInvoiceAmnt =  Double.parseDouble(InventoryDto.getInvoice_amount());
					InvoiceAmt = InvoiceAmt + previousInvoiceAmnt;
					String finalInvoiceAmount = InvoiceAmt+"";
					
					partInvoiceService.update_QuantityAndInvoiceAmount_Of_PartInvoice(InventoryDto.getPartInvoiceId(), userDetails.getCompany_id(),
							originalQty, finalInvoiceAmount);
					
					pendingVendorPaymentService.updatePendingVendorPaymentAmt(InventoryDto.getPartInvoiceId(), PendingPaymentType.PAYMENT_TYPE_PART_INVOICE, InvoiceAmt);
					
					update_InvoiceAmountOf_Inventory(InventoryDto.getPartInvoiceId(),userDetails.getCompany_id(),finalInvoiceAmount);
					
					if((boolean) companyConfiguration.getOrDefault("allowBankPaymentDetails",false)) {
					ValueObject bankPaymentValueObject=new ValueObject();
					bankPaymentValueObject.put("oldPaymentTypeId",invoiceValidate.getPaymentTypeId());
					bankPaymentValueObject.put("bankPaymentTypeId", invoiceValidate.getPaymentTypeId());
					bankPaymentValueObject.put("currentPaymentTypeId", invoiceValidate.getPaymentTypeId());
					bankPaymentValueObject.put("userId",userDetails.getId());
					bankPaymentValueObject.put("companyId",userDetails.getCompany_id());
					bankPaymentValueObject.put("moduleId",InventoryDto.getPartInvoiceId());
					bankPaymentValueObject.put("moduleNo", InventoryDto.getInventory_Number());
					bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.PART_INVENTORY);
					bankPaymentValueObject.put("amount",finalInvoiceAmount);
					
					Vendor	vendor	=  vendorService.getVendor(InventoryDto.getVendor_id());
					bankPaymentValueObject.put("remark", "Update Part Inventory F-"+InventoryDto.getInventory_Number()+" vendor : "+vendor.getVendorName());
				
					
					bankPaymentService.updatePaymentDetailsFromValuObject(bankPaymentValueObject);
					}
					
					model.put("updateInventory", true);
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
	
	@Override
	@Transactional
	public Map<String, Object> editPartInventory(ValueObject valueObject) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails			userDetails		= null;
		PartInvoiceDto				invoiceDto		= null;
		HashMap<String, Object> 	configuration	= null;
		long 						partInvoice_id;
		try {
			
			
			Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
			
			partInvoice_id = valueObject.getLong("partInvoice_id");
			ObjectMapper objectMapper = new ObjectMapper();
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("bpmsn", objectMapper.writeValueAsString(inventoryBL.getFinalInventoryFordit(getPartInvoiceAmountDetailsToEdit(partInvoice_id, userDetails.getCompany_id()), measermentHM)));
			model.put("PartInvoiceAmount", getPartInvoiceAmountDetailsToEdit(partInvoice_id, userDetails.getCompany_id()));
			
			invoiceDto	= Get_list_PartInvoiceDetails(partInvoice_id, userDetails.getCompany_id());
			model.put("PartInvoice", PIBL.Edit_PartInvoiceInventory(invoiceDto));
			model.put("anyPartNumberAsign",invoiceDto.isAnyPartNumberAsign());
			
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG);
			model.put("editAllPartInvoiceInventory", configuration.get(PartInventoryConfigurationConstants.EDIT_ALL_PART_INVENTORY));
			
			return model;
			
		} catch (Exception e) {
			throw e;
		}finally {
			
		}
	}
	
	@Transactional
	public List<InventoryDto> Part_Purchase_Invoice_Report(String Query, Integer companyId) throws Exception {
		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager.createQuery(
					" SELECT I.inventory_id, I.partInvoiceId, PI.partInvoiceNumber, PI.invoiceNumber, PI.invoiceDate, PI.invoiceAmount, "
							+ " I.partid, MP.partnumber, MP.partname, MP.partTypeId, I.inventory_location_id, I.inventory_all_id, "
							+ " I.history_quantity, I.unitprice, I.discount, I.tax, I.total, I.description,"
							+ " I.vendor_id, V.vendorName, V.vendorLocation, I.quantity, I.discountTaxTypeId "
							+ " FROM Inventory AS I "
							+ " INNER JOIN PartInvoice PI ON PI.partInvoiceId = I.partInvoiceId "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = I.partid "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = I.vendor_id "
							+ " where I.companyId = "+companyId+" AND I.markForDelete = 0 AND PI.markForDelete = 0 " + Query + " ",
					Object[].class);
			//queryt.setParameter("comID", companyId);
			List<Object[]> results = queryt.getResultList();

			List<InventoryDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryDto>();
				InventoryDto Dto = null;
				for (Object[] result : results) {
					Dto = new InventoryDto();

					Dto.setInventory_id((Long) result[0]);
					if(result[1] != null) {
						Dto.setPartInvoiceId((long) result[1]);
					}
					if(result[2] != null) {
						Dto.setPartInvoiceNumber((long) result[2]);
					}
					Dto.setInvoice_number((String) result[3]);
					if ((Date) result[4] != null) {
						Dto.setInvoice_date(dateName.format((Date) result[4]));
					}
					Dto.setInvoice_amount(toFixedTwo.format(Double.parseDouble((String)result[5]))); 
					Dto.setPartid((Long) result[6]);
					Dto.setPartnumber((String) result[7]);
					Dto.setPartname((String) result[8]);
					Dto.setParttype(PartType.getPartTypeName((short) result[9]));
					Dto.setInventory_location_id((Long) result[10]);
					Dto.setInventory_all_id((Long) result[11]);
					
					if ((Double) result[12] != null) {
						Dto.setHistory_quantity(Double.parseDouble(toFixedTwo.format(result[12])));
					}
					if ((Double) result[13] != null) {
						Dto.setUnitprice(round((Double) result[13], 2));
					}
					Dto.setDiscount(Double.parseDouble(toFixedTwo.format(result[14])));
					Dto.setTax((Double) result[15]);
					if ((Double) result[16] != null) {
						Dto.setTotal(round((Double) result[16], 2));
					}
					Dto.setDescription((String) result[17]);
					Dto.setVendor_id((Integer) result[18]);
					Dto.setVendor_name((String) result[19]);
					Dto.setVendor_location((String) result[20]);
					if ((Double) result[21] != null) {
						Dto.setQuantity((Double) result[21]);
					}
					Dto.setDiscountTaxTypeId((short) result[22]);

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
	
	@Transactional
	public void Update_PartDocument_ID_to_Part(Long fueldocid, boolean b, Long fuel_id, Integer companyId) throws Exception {
		// Note: Update to Fuel Document id to Fuel

		PartInvoiceRepository.Update_PartDocument_ID_to_Part(fueldocid, b, fuel_id, companyId);

	}	
	
	@Transactional
	public List<InventoryTransferDto> getAllRejectedRequisition(
			Long receivedby_ID, Integer pageNumber, Integer companyId, String status) throws Exception {
		try {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.inventory_transfer_id, R.transfer_partid, MP.partnumber, MP.partname, PC.pcName, PL.partlocation_name, PLT.partlocation_name, "
						+ " R.transfer_quantity, R.transfer_description, US.firstName, R.transfer_via_ID, R.transfer_date, UR.firstName, R.transfer_receiveddate, "
						+ " R.TRANSFER_STATUS_ID, R.transfer_inventory_all_id, R.transfer_inventory_id, R.transfer_receivedby_ID,"
						+ " R.inventoryRequisitionId, IR.INVRID_NUMBER,R.LASTUPDATED_DATE FROM InventoryTransfer AS R "
						+ " INNER JOIN InventoryRequisition AS IR ON IR.INVRID = R.inventoryRequisitionId "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = R.transfer_partid "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
						+ " INNER JOIN User AS US ON US.id = R.transfer_by_ID "
						+ " INNER JOIN User AS UR ON UR.id = R.transfer_receivedby_ID "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.transfer_from_locationId "
						+ " INNER JOIN PartLocations PLT ON PLT.partlocation_id = R.transfer_to_locationId "
						+ " WHERE R.transfer_receivedby_ID=" + receivedby_ID + " AND R.TRANSFER_STATUS_ID IN("+status+") AND R.companyId = " + companyId
						+ " AND R.markForDelete = 0 ORDER BY R.inventory_transfer_id desc",
				Object[].class);
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE_FIFTY);
		queryt.setMaxResults(PAGE_SIZE_FIFTY);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTransferDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTransferDto>();
			InventoryTransferDto list = null;
			for (Object[] result : results) {
				list = new InventoryTransferDto();

				list.setInventory_transfer_id((Long) result[0]);
				list.setTransfer_partid((Long) result[1]);
				list.setTransfer_partnumber((String) result[2]);
				list.setTransfer_partname((String) result[3]);
				list.setTransfer_Category((String) result[4]);
				list.setTransfer_from_location((String) result[5]);
				list.setTransfer_to_location((String) result[6]);
				list.setTransfer_quantity((Double) result[7]);
				list.setTransfer_description((String) result[8]);
				list.setTransfer_by((String) result[9]);
				list.setTransfer_via_ID((short) result[10]);
				list.setTransfer_via(InventoryTransferViaType.getInventoryTransferViaTypeName((short) result[10]));
				if ((Date) result[11] != null) {
					list.setTransfer_date(dateName.format((Date) result[11]));
				}
				list.setTransfer_receivedby((String) result[12]);
				if ((Date) result[13] != null) {
					list.setTransfer_receiveddate(dateName.format((Date) result[13]));
				}
				list.setTRANSFER_STATUS_ID((short) result[14]);
				list.setTRANSFER_STATUS(InventoryTransferStatus.getInventoryTransferName((short) result[14]));
				list.setTransfer_inventory_all_id((Long) result[15]);
				list.setTransfer_inventory_id((Long) result[16]);
				list.setTransfer_receivedby_ID((Long) result[17]);
				if( result[18] != null) {
				list.setINVRID((Long) result[18]);
				}
				if(result[19] != null) {
				list.setINVRID_NUMBER((Long)result[19]);
				}
				if ((Date) result[20] != null) {
					list.setLASTUPDATED_DATE(dateName.format((Date) result[20]));
				}
				Dtos.add(list);
			}
		}
		return Dtos;
	}catch(Exception e) {
		LOGGER.error("err"+e);
		throw e;
	}
}	
	
	
	@Override
	public List<InventoryLocationDto> getPartForRefreshment(String Search, Integer locationId, Integer companyId)
			throws Exception {
		List<InventoryLocationDto> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.inventory_location_id, R.partid, MP.partnumber, MP.partname, PC.pcName, PL.partlocation_name, "
						+ " R.location_quantity, PM.pmName FROM InventoryLocation AS R "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = R.partid AND MP.isRefreshment = 1"
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.locationId "
						+ " WHERE R.locationId = "+locationId+" AND (lower(MP.partnumber) Like ('%" + Search + "%') OR lower(MP.partname) Like ('%"
						+ Search + "%') OR lower(PL.partlocation_name) Like ('%" + Search + "%') )  AND R.companyId = "
						+ companyId + " AND R.markForDelete = 0 ORDER BY R.inventory_location_id desc",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryLocationDto>();
			InventoryLocationDto list = null;
			for (Object[] result : results) {
				list = new InventoryLocationDto();

				list.setInventory_location_id((Long) result[0]);
				list.setPartid((Long) result[1]);
				list.setPartnumber((String) result[2]);
				list.setPartname((String) result[3]);
				list.setCategory((String) result[4]);
				list.setLocation((String) result[5]);
				list.setLocation_quantity((Double) result[6]);
				list.setPartManufacturer((String) result[7]);

				Dtos.add(list);
			}
		}
		}
		return Dtos;
	}
	
	@Override
	public List<InventoryLocationDto> getPartForRefreshment(String Search, Integer companyId) throws Exception {
		List<InventoryLocationDto> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT MP.partid, MP.partnumber, MP.partname "
						+ " FROM MasterParts AS MP "
						+ " WHERE (lower(MP.partnumber) Like ('%" + Search + "%') OR lower(MP.partname) Like ('%"
						+ Search + "%') )AND MP.isRefreshment = 1 AND MP.companyId = "+ companyId + " ",Object[].class);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryLocationDto>();
			InventoryLocationDto list = null;
			for (Object[] result : results) {
				list = new InventoryLocationDto();

				list.setPartid((Long) result[0]);
				list.setPartnumber((String) result[1]);
				list.setPartname((String) result[2]);

				Dtos.add(list);
			}
		}
		}
		return Dtos;
	}
	
	@Override
	public List<InventoryDto> getInventoryLocation_id_To_Inventory(Long partid, Integer locationId, Integer companyId)
			throws Exception {
		
		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager.createQuery(
					"SELECT I.inventory_id, I.inventory_Number, I.partid, MP.partnumber, MP.partname, MP.partTypeId, PC.pcName,  PM.pmName, PL.partlocation_name,  I.quantity,"
							+ " I.history_quantity, I.unitprice, I.purchaseorder_id, PO.purchaseorder_Number, I.invoice_number, I.invoice_amount, I.invoice_date, I.vendor_id, V.vendorName, V.vendorLocation, "
							+ " MU.pmuSymbol, I.discount, I.tax, I.total, I.description, I.inventory_location_id, I.inventory_all_id, I.locationId  "
							+ " FROM Inventory AS I "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = I.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = I.locationId "
							+ " INNER JOIN InventoryAll IAL ON IAL.inventory_all_id = I.inventory_all_id "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = I.vendor_id "
							+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
							+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = I.purchaseorder_id "
							+ " LEFT JOIN PartMeasurementUnit MU ON MU.pmuid = MP.unitTypeId"
							+ " WHERE I.partid = :partid AND I.locationId = :Invid AND I.quantity > 0.0 AND IAL.companyId = :comID AND I.markForDelete = 0  ",
					Object[].class);
			queryt.setParameter("partid", partid);
			queryt.setParameter("Invid", locationId);
			queryt.setParameter("comID", companyId);
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
						Dto.setQuantity((Double) result[9]);
					}

					if ((Double) result[10] != null) {
						Dto.setHistory_quantity((Double) result[10]);
					}

					if ((Double) result[11] != null) {
						Dto.setUnitprice(round((Double) result[11], 2));
					}
					Dto.setPurchaseorder_id((Long) result[12]);
					Dto.setPurchaseorder_Number((Long) result[13]);
					Dto.setInvoice_number((String) result[14]);
					Dto.setInvoice_amount((String) result[15]);
					if ((Date) result[16] != null) {
						Dto.setInvoice_date(dateName.format((Date) result[16]));
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
					
					Dto.setLocationId((Integer) result[27]);

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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> getPartInvoiceListForTallyImport(String fromDate, String toDate, Integer companyId,
			String tallyCompany) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT SE.partInvoiceId, SE.partInvoiceNumber, VA.approvalCreateDate, V.vendorName,"
					+ " TC.companyName, SE.createdOn, SE.paymentTypeId, VSD.subApprovalpaidAmount, SE.isPendingForTally,"
					+ " SE.invoiceDate, SE.invoiceNumber "
					+ " FROM PartInvoice SE "
					+ " INNER JOIN Vendor AS V ON V.vendorId = SE.vendorId"
					+ " INNER JOIN VendorApproval VA on VA.approvalId = SE.partApprovalId AND VA.markForDelete = 0"
					+ " INNER JOIN VendorSubApprovalDetails VSD ON VSD.approvalId = VA.approvalId AND VSD.invoiceId = SE.partInvoiceId AND VSD.approvalPlaceId = "+ApprovalType.APPROVAL_TYPE_INVENTORY_PART_INVOICE+""
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
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_PART_INVENTORY);
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
						
						 select.setRemark("Part Invoice Date: "+sqlDateFormat.format((java.util.Date)vehicle[9])+" Invoice Number : "+(String)vehicle[10]+"  From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						select.setTripSheetNumberStr("PI-"+select.getTripSheetNumber());
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
	public List<TripSheetExpenseDto> getPartInvoiceListForTallyImportATC(String fromDate, String toDate,
			Integer companyId, String tallyCompany) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT SE.partInvoiceId, SE.partInvoiceNumber, SE.invoiceDate, V.vendorName,"
					+ " SE.createdOn, SE.paymentTypeId, SE.invoiceAmount, SE.isPendingForTally,"
					+ " SE.invoiceDate, SE.invoiceNumber "
					+ " FROM PartInvoice SE "
					+ " INNER JOIN Vendor AS V ON V.vendorId = SE.vendorId"
					+ " WHERE SE.invoiceDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND SE.companyId = "+companyId+" AND SE.markForDelete = 0 AND SE.invoiceAmount > 0 ");
			
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
						select.setExpenseAmount(Double.parseDouble(vehicle[6]+""));
						select.setPendingForTally((boolean) vehicle[7]);
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_PART_INVENTORY);
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
						
						 select.setRemark("Part Invoice Date: "+sqlDateFormat.format((java.util.Date)vehicle[8])+" Invoice Number : "+(String)vehicle[9]+"  From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						select.setTripSheetNumberStr("PI-"+select.getTripSheetNumber());
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
	public List<InventoryDto>  getsubLocationPartDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails 					userDetails	 		= null;
		TypedQuery<Object[]> 				query 				= null;
		String								mainLocationQuery 	= "";
		try {
			if(valueObject.getInt("mainLocationId",0) > 0) {
				mainLocationQuery = " AND I.locationId ="+valueObject.getInt("mainLocationId")+" ";
			}
			
			List<InventoryDto> 				Dtos 					= new ArrayList<>();
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			query = entityManager.createQuery(
					"SELECT I.inventory_id, I.partid,I.subLocationId, PSL.partlocation_name , I.quantity "
						+ " FROM Inventory AS I "
						+ " LEFT JOIN PartLocations AS PSL ON PSL.partlocation_id = I.subLocationId"	
						+ " WHERE I.partid ="+valueObject.getInt("partId") +" "+mainLocationQuery+" "
						+ " AND I.companyId = "+ userDetails.getCompany_id() + " AND I.markForDelete = 0 ",Object[].class);
			List<Object[]> results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				InventoryDto list = null;
				for (Object[] result : results) {
					list = new InventoryDto();
					list.setInventory_id((Long) result[0]);
					list.setPartid((Long) result[1]);
					if(result[2] != null) {
						list.setSubLocationId((Integer) result[2]);
						list.setSubLocation((String) result[3]);
					}else {
						list.setSubLocation("Not Defined");
					}
						list.setQuantity((Double) result[4]);
					Dtos.add(list);
					
				}
			}
			return 	Dtos;
			 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Transactional
	public List<InventoryLocationDto> searchlistInventorySubLocation(String search, Integer subLocationId,  Integer companyId) throws Exception {
		try {
			List<InventoryLocationDto> Dtos = null;
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			TypedQuery<Object[]> queryt = entityManager.createQuery(
					"SELECT R.inventory_location_id, R.partid, MP.partnumber, MP.partname, PC.pcName, PL.partlocation_name, "
							+ " I.quantity, PM.pmName FROM InventoryLocation AS R "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = R.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.locationId "
							+ " INNER JOIN Inventory I ON I.inventory_location_id = R.inventory_location_id "
							+ " WHERE (lower(MP.partnumber) Like ('%" + search + "%') OR lower(MP.partname) Like ('%"
							+ search + "%') OR lower(PL.partlocation_name) Like ('%" + search + "%') ) AND I.subLocationId = "+subLocationId+"  AND R.companyId = "
							+ companyId + " AND R.markForDelete = 0 ORDER BY R.inventory_location_id desc",
							Object[].class);
			List<Object[]> results = queryt.getResultList();
			
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryLocationDto>();
				InventoryLocationDto list = null;
				for (Object[] result : results) {
					list = new InventoryLocationDto();
						list.setInventory_location_id((Long) result[0]);
						list.setPartid((Long) result[1]);
						list.setPartnumber((String) result[2]);
						list.setPartname((String) result[3]);
						list.setCategory((String) result[4]);
						list.setLocation((String) result[5]);
						list.setLocation_quantity((Double) result[6]);
						list.setPartManufacturer((String) result[7]);
					
					Dtos.add(list);
				}
			}
			}
			return Dtos;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Transactional
	public void subtractInvenotryFromSubLocation(Double Quantiy, Long Inventoryid, Integer subLocationId) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		entityManager.createQuery("UPDATE Inventory  SET quantity = quantity - " + Quantiy + " where inventory_id="
				+ Inventoryid + "AND subLocationId="+subLocationId+" AND companyId = " + userDetails.getCompany_id()).executeUpdate();
	}
	
	@Transactional
	public List<InventoryDto> getInvoiceWisePartList(String Search,String locationQuery , Integer companyId) throws Exception {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.inventory_id, R.locationId, R.partid, MP.partnumber, MP.partname, PC.pcName, R.quantity, PM.pmName, R.partInvoiceId , R.inventory_location_id "
						+ " from Inventory as R "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = R.partid "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
						+ " WHERE (lower(MP.partnumber) Like ('%" + Search + "%') OR lower(MP.partname) Like ('%" + Search + "%') )  "
						+ " AND R.companyId = " + companyId + " "+locationQuery+" AND R.markForDelete = 0 AND R.quantity > 0 ORDER BY R.inventory_id desc",
				Object[].class);
		List<Object[]> results = queryt.getResultList();
		List<InventoryDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryDto>();
			InventoryDto list = null;
			for (Object[] result : results) {
				list = new InventoryDto();
				list.setInventory_id((Long) result[0]);
				list.setLocationId((Integer) result[1]);
				list.setPartid((Long) result[2]);
				list.setPartnumber((String) result[3]);
				list.setPartname((String) result[4]);
				list.setCategory((String) result[5]);
				list.setQuantity((Double) result[6]);
				list.setMake((String) result[7]);
				list.setPartInvoiceId((Long) result[8]);
				list.setInventory_location_id((Long) result[9]);
				Dtos.add(list);
			}
		}
		return Dtos;
	}
	
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public List<InventoryDto> getWorkorderInventoryPartDetails(Integer companyId, String Query) throws Exception {
		
		TypedQuery<Object[]> queryt = null;
		try {
			
			queryt = entityManager.createQuery(
					"SELECT I.inventory_id, I.quantity, I.total, I.history_quantity, I.partid, I.locationId, "
					+ " I.partInvoiceId, MP.unitTypeId "
							+ " FROM Inventory AS I "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = I.partid "
							+ " WHERE I.quantity > 0.0 AND I.companyId = "+companyId+" "+Query+" AND I.markForDelete = 0  ",
					Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<InventoryDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryDto>();
				InventoryDto Dto = null;
				for (Object[] result : results) {
					Dto = new InventoryDto();
					Dto.setInventory_id((Long) result[0]);
					if ((Double) result[1] != null) {
						Dto.setQuantity((Double) result[1]);
					}
					if ((Double) result[2] != null) {
						Dto.setTotal(round((Double) result[2], 2));
					}
					if ((Double) result[3] != null) {
						Dto.setHistory_quantity((Double) result[3]);
					}
					Dto.setPartid((Long) result[4]);
					Dto.setLocationId((Integer) result[5]);
					Dto.setPartInvoiceId((Long) result[6]);
					Dto.setUnitTypeId((Long) result[7]);

					Dtos.add(Dto);
				}
			}
			
			/*
			 * if(Dtos != null && !Dtos.isEmpty()) { Map<Integer, PartMeasurementUnit>
			 * measermentHM = partMeasurementUnitService.getPartMeasurementUnitHM();
			 * inventoryBL.getFinalInventoryForWOQty(Dtos, measermentHM); }
			 */
			
			return Dtos;
		} catch (Exception e) {
			throw e;
		} finally {
			queryt = null;
		}
	}

	@Transactional
	public ValueObject getLocationWisePartQuantity(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> 						query 					= null;
		List<InventoryDto> 							inventoryDtoList 		= null;
		try {
			query = entityManager.createQuery("SELECT P.location_quantity, P.partid, P.locationId, PL.partlocation_name,MPL.Aisle,MPL.row, MPL.bin"
					+ " FROM InventoryLocation AS P "
					+ " INNER JOIN MasterParts AS MP ON MP.partid = P.partid "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = P.locationId "
					+ " LEFT JOIN MasterPartsLocation MPL ON MPL.masterparts.partid = MP.partid AND MPL.locationId =PL.partlocation_id AND MPL.markForDelete=0 " 
					+ " WHERE P.partid = "+valueObject.getLong("partId")+" AND P.companyId = "+valueObject.getLong("companyId")+"  AND P.location_quantity > 0.00 AND P.markForDelete = 0  ", Object[].class);
			List<Object[]> results = query.getResultList();

			
			if (results != null && !results.isEmpty()) {
				inventoryDtoList = new ArrayList<>();
				InventoryDto inventoryDto = null;
				for (Object[] result : results) {
					inventoryDto = new InventoryDto();
					inventoryDto.setQuantity((Double)result[0]);
					inventoryDto.setPartid((Long)result[1]);
					inventoryDto.setLocationId((Integer)result[2]);
					inventoryDto.setLocation((String)result[3]);
					inventoryDto.setAisle((String) result[4]);
					inventoryDto.setRow((String) result[5]);
					inventoryDto.setBin((String)result[6]);
					
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
	public void updateSerialNoAddedForParts(Long inventoryId, Integer companyId, Integer	count) throws Exception {
	
		entityManager.createQuery("UPDATE Inventory  SET serialNoAddedForParts = serialNoAddedForParts + "+count+"  "
				+ " where inventory_id=" + inventoryId+" AND companyId = "+companyId+" ").executeUpdate();
	}
	@Transactional
	public void Subtract_update_Inventory_from_Workorder(Double Quantiy, Long Inventoryid,int companyId) throws Exception {
		try{
		entityManager.createQuery("UPDATE Inventory  SET quantity = quantity - " + Quantiy + " where inventory_id="
				+ Inventoryid + " AND companyId = " + companyId).executeUpdate();
		}catch(Exception e){
			throw e;
		}
	}
	
	@Transactional
	public void subtractInvenotryFromSubLocation(Double Quantiy, Long Inventoryid, Integer subLocationId,int companyId) throws Exception {
		try{
		entityManager.createQuery("UPDATE Inventory  SET quantity = quantity - " + Quantiy + " where inventory_id="
				+ Inventoryid + "AND subLocationId="+subLocationId+" AND companyId = " + companyId).executeUpdate();
		}catch(Exception e){
			throw e;
		}
	}
	
	@Transactional
	public InventoryLocation getInventoryLocation(Long Location_id,int companyId) throws Exception {
		try{
			return InventoryLocationRepository.getInventoryLocation(Location_id, companyId);
		}catch(Exception e){
			throw e;
		}
	}
	
@Transactional
	public InventoryDto getInventory(Long sid,int companyId) throws Exception {
		Query query = null; 
		// return InventoryDao.getInventory(sid, userDetails.getCompany_id());
		try {
			query = entityManager.createQuery(
					"SELECT I.inventory_id, I.inventory_Number, I.partid, MP.partnumber, MP.partname, MP.partTypeId, I.locationId, PC.pcName,  PM.pmName, MP.part_photoid, PL.partlocation_name,  I.quantity,"
							+ " I.history_quantity, I.unitprice, I.purchaseorder_id, PO.purchaseorder_Number, I.invoice_number, I.invoice_amount, I.invoice_date, I.vendor_id, V.vendorName, V.vendorLocation, "
							+ " MU.pmuSymbol, I.discount, I.tax, I.total, I.description, I.inventory_location_id, I.inventory_all_id, V.vendorTypeId,"
							+ " U.email, U2.email, I.created, I.lastupdated,I.partInvoiceId,I.subLocationId, PSL.partlocation_name FROM Inventory AS I "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = I.partid "
							+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = I.locationId "
							+ " INNER JOIN InventoryAll IAL ON IAL.inventory_all_id = I.inventory_all_id "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = I.vendor_id "
							+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
							+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = I.purchaseorder_id "
							+ " LEFT JOIN PartMeasurementUnit MU ON MU.pmuid = MP.unitTypeId"
							+ " LEFT JOIN User U ON U.id = I.createdById"
							+ " LEFT JOIN User U2 ON U2.id = I.lastModifiedById"
							+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = I.subLocationId "
							+ " WHERE I.inventory_id = :Invid AND IAL.companyId = :comID AND I.markForDelete = 0 ",
					Object[].class);
			query.setParameter("Invid", sid);
			query.setParameter("comID", companyId);

			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			InventoryDto Dto = null;
			if (result != null) {
				Dto = new InventoryDto();

				Dto.setInventory_id((Long) result[0]);
				Dto.setInventory_Number((Long) result[1]);
				Dto.setPartid((Long) result[2]);
				Dto.setPartnumber((String) result[3]);
				Dto.setPartname((String) result[4]);
				Dto.setParttype(PartType.getPartTypeName((short) result[5]));
				Dto.setLocationId((Integer) result[6]);
				Dto.setCategory((String) result[7]);
				Dto.setMake((String) result[8]);
				if(result[9] != null){
					Dto.setPart_photoid((long) result[9]);
				}
				Dto.setLocation((String) result[10]);
				if ((Double) result[11] != null) {
					Dto.setQuantity((Double) result[11]);
				}

				if ((Double) result[12] != null) {
					Dto.setHistory_quantity((Double) result[12]);
				}

				if ((Double) result[13] != null) {
					Dto.setUnitprice(round((Double) result[13], 2));
				}
				Dto.setPurchaseorder_id((Long) result[14]);
				Dto.setPurchaseorder_Number((Long) result[15]);
				Dto.setInvoice_number((String) result[16]);
				Dto.setInvoice_amount((String) result[17]);
				if ((Date) result[18] != null) {
					Dto.setInvoiceDate((Date) result[18]);
					Dto.setInvoice_date(sqlDateFormat.format((Date) result[18]));
				}
				if(result[19] != null) 
					Dto.setVendor_id((Integer) result[19]);
				if(result[20] != null) 
					Dto.setVendor_name((String) result[20]);
				if(result[21] != null) 
					Dto.setVendor_location((String) result[21]);
				Dto.setUnittype((String) result[22]);
				Dto.setDiscount((Double) result[23]);
				Dto.setTax((Double) result[24]);
				if ((Double) result[25] != null) {
					Dto.setTotal(round((Double) result[25], 2));
				}
				Dto.setDescription((String) result[26]);

				Dto.setInventory_location_id((Long) result[27]);
				Dto.setInventory_all_id((Long) result[28]);
				
				if(result[29] != null)
					Dto.setVendorTypeId((long) result[29]);
				Dto.setCreatedBy((String) result[30]);
				Dto.setLastModifiedBy((String) result[31]);
				Dto.setCreated(CreatedDateTime.format((Date) result[32]));
				Dto.setLastupdated(CreatedDateTime.format((Date) result[33]));
				
				if(result[34] != null)
				Dto.setPartInvoiceId((long) result[34]);
				
				if(result[35] != null) {
					Dto.setSubLocationId((Integer) result[35]);
					Dto.setSubLocation((String) result[36]);
				}

			}
			return Dto;
		} catch (Exception e) {
			throw e;
		} finally {
			query = null;
		}

	}


@Transactional
@Override
public Double getLocationWisePartSum( long partId,int locationId,int companyId ) {
	Double count = 0.0; 
	
	try {
	
		Query queryt = entityManager.createQuery(
				"SELECT sum(R.location_quantity) "
						+ "  FROM InventoryLocation AS R "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = R.partid "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.locationId " 
						+ " WHERE R.locationId="
						+ locationId + " AND R.partid="+partId+" AND R.location_quantity > 0.0 AND R.companyId = " + companyId
						+ " AND R.markForDelete = 0 ORDER BY R.inventory_location_id desc",
						Object[].class);
		count =(Double) queryt.getSingleResult();
	} catch (Exception e) {
		e.printStackTrace();
	}

	return count;
}


@Transactional
@Override
public Double getOtherWisePartSum( long partId,int locationId,int companyId) {
	Double count = 0.0; 
	try {
		Query queryt = entityManager.createQuery(
				"SELECT sum(R.location_quantity) "
						+ "  FROM InventoryLocation AS R "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = R.partid "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
						+ " WHERE R.partid="+partId+" AND "
						+ " R.locationId NOT IN("+locationId+")  AND R.location_quantity > 0.0 AND R.companyId = " + companyId
						+ " AND R.markForDelete = 0 ORDER BY R.inventory_location_id desc",
						Object[].class);

		count =(Double) queryt.getSingleResult();
	} catch (Exception e) {
		e.printStackTrace();
	}

	return count;
}

@Override
public List<InventoryLocationDto> preparePartIdsString(List<InventoryLocationDto> partList,int companyId){
	StringBuilder partIds = new StringBuilder();
	List<InventoryLocationDto> partInventoryList = null;
	try {
		for(InventoryLocationDto part :partList ) {
			partIds.append(part.getPartid()+",");
		}
		if(partIds.length() > 0) {
			partInventoryList=getSubstitudePartList(Utility.removeLastComma(partIds.toString()), companyId);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return partInventoryList;
}

@Transactional
public List<InventoryLocationDto> getSubstitudePartList(String partIds ,int companyId) throws Exception {
	TypedQuery<Object[]> queryt =  null;
		queryt = entityManager.createQuery(
				"SELECT R.inventory_location_id, R.partid, MP.partnumber, MP.partname, PC.pcName, PL.partlocation_name, "
						+ " R.location_quantity, PM.pmName, MP.isWarrantyApplicable, MP.isRepairable, R.locationId,"
						+ " MP.unitTypeId, R.inventoryall.inventory_all_id"
						+ " FROM InventoryLocation AS R "
						+ " INNER JOIN SubstitudePartDetails AS SPD ON SPD.partId = R.partid AND SPD.markForDelete = 0 "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = R.partid "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.locationId "
						+ " WHERE SPD.mainPartId IN("+partIds+")  AND R.companyId = "
						+ companyId + " AND R.markForDelete = 0 AND R.location_quantity > 0 ORDER BY R.inventory_location_id desc",
				Object[].class);
		
	List<Object[]> results = queryt.getResultList();

	List<InventoryLocationDto> Dtos = null;
	if (results != null && !results.isEmpty()) {
		Dtos = new ArrayList<>();
		InventoryLocationDto list = null;
		for (Object[] result : results) {
			list = new InventoryLocationDto();
			list.setInventory_location_id((Long) result[0]);
			list.setPartid((Long) result[1]);
			list.setPartnumber((String) result[2]);
			list.setPartname((String) result[3]);
			list.setCategory((String) result[4]);
			list.setLocation((String) result[5]);
			list.setLocation_quantity((Double) result[6]);
			list.setPartManufacturer((String) result[7]);
			list.setWarrantyApplicable((boolean) result[8]);
			list.setRepairable((boolean) result[9]);
			list.setLocationId((Integer) result[10]);
			list.setUnitTypeId((long) result[11]);
			list.setInventory_all_id((long) result[12]);
			Dtos.add(list);
		}
	}
	if(Dtos != null && !Dtos.isEmpty()) {
		Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
		inventoryBL.getFinalInventoryLocationDto(Dtos, measermentHM);
	}
	return Dtos;
}
@Transactional
public List<InventoryLocationDto> allpartList(String Search, Integer companyId) throws Exception {
	
	List<InventoryLocationDto> Dtos = null;
	TypedQuery<Object[]> queryt =  null;
	if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		queryt = entityManager.createQuery(
				"SELECT R.inventory_location_id, R.partid, MP.partnumber, MP.partname, PC.pcName, PL.partlocation_name, "
						+ " R.location_quantity, PM.pmName, MP.isWarrantyApplicable, MP.isRepairable, R.locationId,"
						+ " MP.unitTypeId, R.inventoryall.inventory_all_id"
						+ " FROM InventoryLocation AS R "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = R.partid "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.locationId "
						+ " WHERE (lower(MP.partnumber) Like ('%" + Search + "%') OR lower(MP.partname) Like ('%"
						+ Search + "%') OR lower(PL.partlocation_name) Like ('%" + Search + "%') OR lower(MP.localPartName) Like ('%" + Search + "%') "
						+ " OR lower(MP.partNameOnBill) Like ('%" + Search + "%') )  AND R.companyId = "
						+ companyId + " AND R.markForDelete = 0  ORDER BY R.inventory_location_id desc",
				Object[].class);
	
	
	List<Object[]> results = queryt.getResultList();

	if (results != null && !results.isEmpty()) {
		Dtos = new ArrayList<InventoryLocationDto>();
		InventoryLocationDto list = null;
		for (Object[] result : results) {
			list = new InventoryLocationDto();

			list.setInventory_location_id((Long) result[0]);
			list.setPartid((Long) result[1]);
			list.setPartnumber((String) result[2]);
			list.setPartname((String) result[3]);
			list.setCategory((String) result[4]);
			list.setLocation((String) result[5]);
			list.setLocation_quantity((Double) result[6]);
			list.setPartManufacturer((String) result[7]);
			list.setWarrantyApplicable((boolean) result[8]);
			list.setRepairable((boolean) result[9]);
			list.setLocationId((Integer) result[10]);
			list.setUnitTypeId((long) result[11]);
			list.setInventory_all_id((long) result[12]);

			Dtos.add(list);
		}
	}
	
	if(Dtos != null && !Dtos.isEmpty()) {
		Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
		inventoryBL.getFinalInventoryLocationDto(Dtos, measermentHM);
	}
	}
	return Dtos;
}


@Transactional
public List<InventoryLocationDto> searchRepairablePartByLocation(String Search, Integer companyId) throws Exception {
	
	TypedQuery<Object[]> queryt =  null;
	List<InventoryLocationDto> Dtos = null;
	if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		queryt = entityManager.createQuery(
				"SELECT R.inventory_location_id, R.partid, MP.partnumber, MP.partname, PC.pcName, PL.partlocation_name, "
						+ " R.location_quantity, PM.pmName, MP.isWarrantyApplicable, MP.isRepairable, R.locationId,"
						+ " MP.unitTypeId, R.inventoryall.inventory_all_id, I.inventory_id, I.serialNoAddedForParts "
						+ " FROM InventoryLocation AS R "
						+ " INNER JOIN MasterParts AS MP ON MP.partid = R.partid AND isRepairable = 1 "
						+ " INNER JOIN Inventory AS I ON I.inventory_location_id = R.inventory_location_id  "
						+ " INNER JOIN PartCategories PC ON PC.pcid = MP.categoryId "
						+ " INNER JOIN PartManufacturer PM ON PM.pmid = MP.makerId "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.locationId "
						+ " WHERE (lower(MP.partnumber) Like ('%" + Search + "%') OR lower(MP.partname) Like ('%"
						+ Search + "%') OR lower(PL.partlocation_name) Like ('%" + Search + "%') OR lower(MP.localPartName) Like ('%" + Search + "%') "
						+ " OR lower(MP.partNameOnBill) Like ('%" + Search + "%') )  AND R.companyId = "
						+ companyId + " AND R.markForDelete = 0 AND R.location_quantity > 0 ORDER BY R.inventory_location_id desc",
				Object[].class);
	
	
	List<Object[]> results = queryt.getResultList();

	
	if (results != null && !results.isEmpty()) {
		Dtos = new ArrayList<InventoryLocationDto>();
		InventoryLocationDto list = null;
		for (Object[] result : results) {
			list = new InventoryLocationDto();

			list.setInventory_location_id((Long) result[0]);
			list.setPartid((Long) result[1]);
			list.setPartnumber((String) result[2]);
			list.setPartname((String) result[3]);
			list.setCategory((String) result[4]);
			list.setLocation((String) result[5]);
			list.setLocation_quantity((Double) result[6]);
			list.setPartManufacturer((String) result[7]);
			list.setWarrantyApplicable((boolean) result[8]);
			list.setRepairable((boolean) result[9]);
			list.setLocationId((Integer) result[10]);
			list.setUnitTypeId((long) result[11]);
			list.setInventory_all_id((long) result[12]);
			list.setInventory_id((long) result[13]);
			list.setSerialNoAddedForParts((int) result[14]);
			Dtos.add(list);
		}
	}
	
	if(Dtos != null && !Dtos.isEmpty()) {
		Map<Integer, PartMeasurementUnit>	measermentHM	=	partMeasurementUnitService.getPartMeasurementUnitHM();
		inventoryBL.getFinalInventoryLocationDto(Dtos, measermentHM);
	}
	}
	return Dtos;
}

@SuppressWarnings("unchecked")
@Override
@Transactional
public ValueObject transferInventoryToRepairLocation(ValueObject valueObject) throws Exception {
	double							quantity						= 0;
	List<RepairFromAssetPart> 		repairFromAssetPartList 		= null;
	Long 							Inventory_all_id 				= (long) 0;
	Long 							Inventory_Location_id 			= (long) 0;
	InventoryDto 					FROM_transferInventory			= null;
	InventoryAll 					Inventoryall 					= null;
	InventoryLocation 				InventoryLocation 				= null; 
	Inventory 						Part							= null;
	

	try {
		repairFromAssetPartList = new ArrayList<>();
		repairFromAssetPartList = (List<RepairFromAssetPart>) valueObject.get("repairFromAssetPartList");
		FROM_transferInventory 	= getInventory(valueObject.getLong("inventoryId"),valueObject.getInt("companyId"));
		Inventoryall 			= inventoryBL.prepareInventoryAll_To_TransferInventory(FROM_transferInventory);
		InventoryLocation 		= inventoryBL.prepareInventoryLocation_To_TransferInventory(FROM_transferInventory, Inventoryall);
		Part 					= inventoryBL.prepareModel_To_TransferInventory(FROM_transferInventory);

		
		if(repairFromAssetPartList != null && !repairFromAssetPartList.isEmpty()) {
			quantity = repairFromAssetPartList.size();
		}else {
			quantity = 1;
		}
		
		try {

			/** Sub track Transfer part quantity value */
		//1	Subtract_update_Inventory_from_Workorder(InventoryTransfer.getTransfer_quantity(),	FROM_transferInventory.getInventory_id());

			/** This Transfer location sub track value to update */
		//2	update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(InventoryTransfer.getTransfer_partid(), InventoryTransfer.getTransfer_from_locationId());

			
			Inventory_all_id = FROM_transferInventory.getInventory_all_id();

			// Add in InventoryLocation Quantity So Transfer Quantity
			// Search InventoryLocation Quantity to Transfer Quantity
			// and
			// Location there or not
			List<InventoryLocation> validate_Location = Validate_Inventory_Location(FROM_transferInventory.getPartid(), valueObject.getInt("toLocationId"));
			if (validate_Location != null && !validate_Location.isEmpty()) {
				for (InventoryLocation validateLocation : validate_Location) {
					// get part and Location Quantity in

					Inventory_Location_id = validateLocation.getInventory_location_id();
					break;
				}
			} else {
				// get part and Location Quantity Search that
				// location part not in db Create new
				InventoryLocation.setLocation_quantity(quantity);
				// InventoryLocation.setLocation(InventoryTransfer.getTransfer_to_location());
				InventoryLocation.setLocationId( valueObject.getInt("toLocationId"));
				InventoryAll NewAll = new InventoryAll();
				NewAll.setInventory_all_id(Inventory_all_id);
				InventoryLocation.setInventoryall(NewAll);
				add_InventoryLocation(InventoryLocation);

				Inventory_Location_id = InventoryLocation.getInventory_location_id();

			}

			// add New Create Transfer From Quantity Location to
			// Transfer
			// location
			Part.setHistory_quantity(quantity);
			Part.setQuantity(quantity);
			// Part.setLocation(InventoryTransfer.getTransfer_to_location());
			Part.setLocationId( valueObject.getInt("toLocationId"));

			// calculation to inventory total cost
			Double Quantity = 0.0;
			if (quantity > 0) {
				Quantity = quantity;
			}
			Double eaccost = 0.0;
			if (FROM_transferInventory.getUnitprice() != null) {
				eaccost = FROM_transferInventory.getUnitprice();
			}
			Double dis_Ca = 0.0;
			if (FROM_transferInventory.getDiscount() != null) {
				dis_Ca = FROM_transferInventory.getDiscount();
			}
			Double tax_Ca = 0.0;
			if (FROM_transferInventory.getTax() != null) {
				tax_Ca = FROM_transferInventory.getTax();
			}

			Double discountCost = 0.0;
			Double TotalCost = 0.0;

			discountCost = (Quantity * eaccost) - ((Quantity * eaccost) * (dis_Ca / 100));
			TotalCost = round(((discountCost) + (discountCost * (tax_Ca / 100))), 2);
			Part.setTotal(TotalCost);

			Part.setInventory_all_id(Inventory_all_id);
			Part.setInventory_location_id(Inventory_Location_id);
			Part.setSerialNoAddedForParts(0);

			Part = save_Inventory(Part);
			

			valueObject.put("savedInventory", Part);

			// Insert Inventory QUANTITY Details to Update
			// LOCATION AND ALL LOCATION QUANTITY DETAILS

			update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(valueObject.getLong("partId"),  valueObject.getInt("toLocationId"));

			update_InventoryAll_PARTID_PARTNAME_GENRAL(valueObject.getLong("partId"));

			// get Current days
			java.util.Date currentDate = new java.util.Date();
			Timestamp Transfer_receiveddate = new java.sql.Timestamp(currentDate.getTime());

			/*Update_InventoryTransfer_ReceivedBy_Details(Transfer_receiveddate,	"--", "--", Transfer_receiveddate,
				 InventoryTransferStatus.RECEIVED,	(long)1, valueObject.getInt("companyId"));// long *****1
*/		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}



public Inventory getInventoryByRepairStockId(long partId,long repairStockId)throws Exception{
	Inventory inventory = null;
	try {
		inventory = InventoryDao.getInventoryByRepairStockId(partId,repairStockId);
		return inventory;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

public void deleteTransferdInventoryIdFromInventory(long inventoryId)throws Exception{
	try {
		 InventoryDao.deleteTransferdInventoryIdFromInventory(inventoryId);
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}

}

@Transactional
public List<InventoryDto> getWarrantyPartList(String Search, Integer companyId) throws Exception {
	
	TypedQuery<Object[]> 		queryt 	= null;
	List<InventoryDto> 			Dtos 	= null;
	
	if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		queryt = entityManager.createQuery("SELECT I.inventory_id, I.partid, MP.partname, MP.partnumber FROM Inventory AS I "
				+ " INNER JOIN MasterParts AS MP ON MP.partid = I.partid AND MP.isWarrantyApplicable = 1 "
				+ " WHERE I.serialNoAddedForParts > 0 AND (lower(MP.partnumber) Like ('%" + Search + "%') OR lower(MP.partname) Like ('%"
				+ Search + "%') OR lower(MP.localPartName) Like ('%" + Search + "%') "
				+ " OR lower(MP.partNameOnBill) Like ('%" + Search + "%') )  AND I.companyId = "
				+ companyId + " AND I.markForDelete = 0 ",
				Object[].class);
	
	List<Object[]> results = queryt.getResultList();

	
	if (results != null && !results.isEmpty()) {
		Dtos = new ArrayList<>();
		InventoryDto list = null;
		for (Object[] result : results) {
			list = new InventoryDto();

			list.setInventory_id((Long) result[0]);
			list.setPartid((Long) result[1]);
			list.setPartname((String) result[2]);
			list.setPartnumber((String) result[3]);
			Dtos.add(list);
		}
	}
	
	
	}
	return Dtos;
}
	@Transactional
	public List<PurchaseOrderReportDto> Purchase_Order_StatusWise_Report(String Query, Integer companyId) throws Exception {
		TypedQuery<Object[]> queryt = null;
		try {
			queryt = entityManager.createQuery(
					"SELECT P.purchaseorder_id, P.purchaseorder_Number, V.vendorName, P.purchaseorder_created_on, P.purchaseorder_requied_on,"
					+ "P.purchaseorder_invoiceno, P.purchaseorder_invoice_date, P.purchaseorder_indentno,P.purchaseorder_statusId,"
					+ " MP.partname, MP.partnumber, PT.quantity, PT.received_quantity,"
					+ " PT.parteachcost, PT.discount, PT.tax, PT.totalcost, TM.manufacturerName, TSM.partNumber,"
					+ " TSM.batteryType, VT.TYRE_MODEL, VS.TYRE_SIZE, TSM2.TYRE_MODEL_SUBTYPE, P.purchaseorder_typeId,TSM.warrantyPeriod, TSM.warrantyTypeId "
	
					+ "FROM PurchaseOrders AS P "
					+ "INNER JOIN PurchaseOrdersToParts AS PT on P.purchaseorder_id = PT.purchaseorders.purchaseorder_id and PT.markForDelete = 0"
					+ " INNER JOIN Vendor V ON V.vendorId = P.purchaseorder_vendor_id "
					+ " LEFT JOIN MasterParts MP ON MP.partid = PT.partid"
					+ " LEFT JOIN BatteryManufacturer TM ON TM.batteryManufacturerId = PT.BATTERY_MANUFACTURER_ID"
					+ " LEFT JOIN VehicleTyreModelType VT ON VT.TYRE_MT_ID = PT.TYRE_MANUFACTURER_ID"
					+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = PT.TYRE_SIZE_ID"
					+ " LEFT JOIN BatteryType TSM ON TSM.batteryTypeId = PT.BATTERY_TYPE_ID"
					+ " LEFT JOIN VehicleTyreModelSubType TSM2 ON TSM2.TYRE_MST_ID = PT.TYRE_MODEL_ID"

					+ " where P.companyId = "+ companyId +" "+ Query + " AND P.markForDelete = 0 ",
					Object[].class);
			
			List<Object[]> results = queryt.getResultList();
			
			List<PurchaseOrderReportDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				
				Dtos = new ArrayList<PurchaseOrderReportDto>();
				
				PurchaseOrderReportDto Dto = null;
				
				for (Object[] result : results) {
					Dto = new PurchaseOrderReportDto();
					
					Dto.setPurchaseOrder_id((Long) result[0]);
					
					Dto.setPurchaseorder_Number((Long) result[1]);	
					Dto.setPurchaseorder_vendor_name((String) result[2]);
					
					
					Dto.setDate_opended(dateName.format((Date) result[3]));
					Dto.setData_required(dateName.format((Date) result[4]));
					
					Dto.setPurchaseorder_invoiceno((String) result[5]);
					
					if(result[6]!=null)
					{
						Dto.setPurchaseorder_invoice_date(dateName.format((Date) result[6]));
					}
					
					
					Dto.setPurchaseorder_indentno((String) result[7]);
					Dto.setPurchaseorder_status((short) result[8]);
					
					Dto.setPurchaseorder_partname((String) result[9]);
					Dto.setPurchaseorder_partnumber((String) result[10]);
				
					
					
					Dto.setQuantity((Double) result[11]);
					Dto.setReceived_quantity((Double) result[12]);
					Dto.setParteachcost((Double) result[13]);
					Dto.setDiscount((Double) result[14]);
					Dto.setTax((Double) result[15]);
					Dto.setTotalcost((Double) result[16]);
					
					Dto.setBATTERY_MANUFACTURER((String) result[17]);
					Dto.setBattery_partNumber((String) result[19]);
					
					
					Dto.setTYRE_MANUFACTURER((String) result[22]);
					Dto.setTYRE_SIZE((String) result[21]);
					Dto.setPurchaseOrderTypeId((short) result[23]);
					
					Dto.setWarrantyPeriod((Integer) result[24]);
					if(result[25]!=null)
					{
						Dto.setWarrantyTypeId((short) result[25]);
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
	public List<PurchaseOrderReportDto> Purchase_Order_StatusWise_Report_urea(String Query, Integer companyId) throws Exception {
		TypedQuery<Object[]> queryt = null;
		try {
			queryt = entityManager.createQuery(
					"SELECT P.purchaseorder_id, P.purchaseorder_Number, V.vendorName, P.purchaseorder_created_on, P.purchaseorder_requied_on,"
					+ "P.purchaseorder_invoiceno, P.purchaseorder_invoice_date, P.purchaseorder_indentno,P.purchaseorder_statusId,"
					+ " UM.manufacturerName, POU.quantity, POU.notRecivedQuantity, POU.recivedQuantity, POU.unitCost, POU.discount,"
					+ " POU.tax, POU.totalcost, P.purchaseorder_typeId "
					+ "FROM PurchaseOrders AS P "
					+ " INNER JOIN PurchaseOrdersToUrea AS POU ON POU.purchaseOrderId = P.purchaseorder_id AND POU.markForDelete = 0"
					+ " INNER JOIN Vendor V ON V.vendorId = P.purchaseorder_vendor_id "
					+ " INNER JOIN UreaManufacturer UM ON UM.ureaManufacturerId = POU.ureaManufacturerId"
					+ " where P.companyId = "+ companyId +" "+ Query + " AND P.markForDelete = 0 ",
					Object[].class);
			
			
			
			List<Object[]> results = queryt.getResultList();
			
			List<PurchaseOrderReportDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				
				Dtos = new ArrayList<PurchaseOrderReportDto>();
				
				PurchaseOrderReportDto Dto = null;
				
				for (Object[] result : results) {
					Dto = new PurchaseOrderReportDto();
					
					Dto.setPurchaseOrder_id((Long) result[0]);
					Dto.setPurchaseorder_Number((Long) result[1]);	
					Dto.setPurchaseorder_vendor_name((String) result[2]);
					
					Dto.setDate_opended(dateName.format((Date) result[3]));
					Dto.setData_required(dateName.format((Date) result[4]));
					
					Dto.setPurchaseorder_invoiceno((String) result[5]);
					
					if(result[6]!=null)
					{
						Dto.setPurchaseorder_invoice_date(dateName.format((Date) result[6]));
					}
					
					Dto.setPurchaseorder_indentno((String) result[7]);
					Dto.setPurchaseorder_status((short) result[8]);
					
					Dto.setPurchaseorder_partname((String) result[9]);
					
					Dto.setQuantity((Double) result[10]);
					Dto.setReceived_quantity((Double) result[12]);
					
					Dto.setParteachcost((Double) result[13]);
					Dto.setDiscount((Double) result[14]);
					Dto.setTax((Double) result[15]);
					Dto.setTotalcost((Double) result[16]);
					Dto.setPurchaseOrderTypeId((short) result[17]);
					
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
public InventoryAll getInventoryAllById(Long inventory_all_id, Integer companyId) throws Exception {
	
	return InventoryAllRepository.getInventoryAll(inventory_all_id, companyId);
}

@Override
public Inventory getInventoryDetailsByPartId(Long poToPartId, Integer companyId) throws Exception {
	return InventoryDao.getInventoryDetailsByPartId(poToPartId, companyId);

	}


}