/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.constant.SequenceTypeContant;
import org.fleetopgroup.constant.TallyVoucherTypeContant;
import org.fleetopgroup.constant.TyreAssignmentConstant;
import org.fleetopgroup.constant.TyreTypeConstant;
import org.fleetopgroup.persistence.bl.InventoryTyreInvoiceBL;
import org.fleetopgroup.persistence.dao.InventoryTyreAmountRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreHistoryRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreInvoiceRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreLifeHistoryRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreRetreadAmountRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreRetreadDocumentRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreRetreadRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreTransferRepository;
import org.fleetopgroup.persistence.dao.TyreExpenseDetailsRepository;
import org.fleetopgroup.persistence.dao.TyreSoldDetailsRepository;
import org.fleetopgroup.persistence.dao.TyreSoldInvoiceDetailsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.InventoryTyreAmountDto;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.InventoryTyreHistoryDto;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadAmountDto;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadDto;
import org.fleetopgroup.persistence.dto.InventoryTyreTransferDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TyreSoldInvoiceDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleModelTyreLayoutDto;
import org.fleetopgroup.persistence.dto.VendorPaymentNotPaidDto;
import org.fleetopgroup.persistence.model.InventorySequenceCounter;
import org.fleetopgroup.persistence.model.InventoryTyre;
import org.fleetopgroup.persistence.model.InventoryTyreAmount;
import org.fleetopgroup.persistence.model.InventoryTyreHistory;
import org.fleetopgroup.persistence.model.InventoryTyreInvoice;
import org.fleetopgroup.persistence.model.InventoryTyreLifeHistory;
import org.fleetopgroup.persistence.model.InventoryTyreRetread;
import org.fleetopgroup.persistence.model.InventoryTyreRetreadAmount;
import org.fleetopgroup.persistence.model.InventoryTyreRetreadDocument;
import org.fleetopgroup.persistence.model.InventoryTyreSequenceCounter;
import org.fleetopgroup.persistence.model.InventoryTyreTransfer;
import org.fleetopgroup.persistence.model.TyreExpenseDetails;
import org.fleetopgroup.persistence.model.TyreSoldDetails;
import org.fleetopgroup.persistence.model.TyreSoldInvoiceDetails;
import org.fleetopgroup.persistence.model.TyreSoldInvoiceSequenceCounter;
import org.fleetopgroup.persistence.model.VehicleTyreModelSubType;
import org.fleetopgroup.persistence.report.dao.ITyreDaoImpl;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IInventorySequenceService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.ITyreSoldInvoiceSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleModelTyreLayoutService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreModelSubTypeService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fleetop
 *
 */
@Service("InventoryTyreService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class InventoryTyreService implements IInventoryTyreService {

	SimpleDateFormat SQLdateFormat 		= new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat SQLdate 			= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat CreatedDateTime 	= new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	SimpleDateFormat dateFormat 		= new SimpleDateFormat("dd-MMM-yy");
	SimpleDateFormat format 			= new SimpleDateFormat("yyyy-MM-dd hh:mm aaa");
	SimpleDateFormat tallyFormat		= new SimpleDateFormat("yyyyMMdd");
	DecimalFormat toFixedTwo            = new DecimalFormat("#.##");
	@PersistenceContext	EntityManager entityManager;

	@Autowired	private InventoryTyreInvoiceRepository 				TyreInvoiceRepository;
	@Autowired	private TyreExpenseDetailsRepository				TyreExpenseDetailsRepository;
	@Autowired	private InventoryTyreAmountRepository 				TyreAmountRepository;
	@Autowired	private InventoryTyreRepository 					TyreRepository;
	@Autowired	private InventoryTyreHistoryRepository 				TyreHistoryRepository;
	@Autowired	private InventoryTyreRetreadRepository				TyreRetreadRepository;
	@Autowired	private InventoryTyreRetreadAmountRepository 		TyreRetreadAmountRepository;
	@Autowired	private InventoryTyreLifeHistoryRepository 			TyreLifeHistoryRepository;
	@Autowired	private InventoryTyreTransferRepository 			TyreTransferRepository;
	@Autowired	private IInventorySequenceService					inventorySequenceService;
	@Autowired	IInventoryTyreSequenceService						inventoryTyreSequenceService;	
	@Autowired private InventoryTyreRetreadDocumentRepository		inventoryTyreRetreadDocumentRepository;
	@Autowired private MongoTemplate								mongoTemplate;
	@Autowired private ISequenceCounterService						sequenceCounterService;
	@Autowired private ITyreDaoImpl									TyreDaoImpl;
	InventoryTyreInvoiceBL ITBL = new InventoryTyreInvoiceBL();
	@Autowired private ICompanyConfigurationService		companyConfigurationService;
	@Autowired private IUserProfileService						userProfileService;
	@Autowired	private TyreSoldInvoiceDetailsRepository 			tyreSoldInvoiceDetailsRepository;
	@Autowired	private TyreSoldDetailsRepository 			tyreSoldDetailsRepository;
	@Autowired private 	ITyreSoldInvoiceSequenceService 					tyreSoldInvoiceSequenceService;
	@Autowired 	IVehicleModelTyreLayoutService   		vehicleModelTyreLayoutService;
	@Autowired private IVehicleTyreModelSubTypeService	modelSubTypeService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private static final int PAGE_SIZE = 10;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * add_Tyre_Inventory_Invoice(org.fleetop.persistence.model.
	 * InventoryTyreInvoice)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void add_Tyre_Inventory_Invoice(InventoryTyreInvoice TyreInvoice) throws Exception {

		TyreInvoiceRepository.save(TyreInvoice);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * add_Tyre_Inventory_Amount(org.fleetop.persistence.model.
	 * InventoryTyreAmount)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void add_Tyre_Inventory_Amount(InventoryTyreAmount TyreAmount) throws Exception {

		TyreAmountRepository.save(TyreAmount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * add_Inventory_TYRE(org.fleetop.persistence.model.InventoryTyre)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void add_Inventory_TYRE(InventoryTyre Tyre) throws Exception {

		TyreRepository.save(Tyre);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * add_Inventory_TYRE(org.fleetop.persistence.model.InventoryTyre)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void save_Multiple_Inventory_TYRE(ArrayList<InventoryTyre>	inventoryTyreList) throws Exception {
		TyreRepository.saveAll(inventoryTyreList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * add_Inventory_TYRE_History(org.fleetop.persistence.model.
	 * InventoryTyreHistory)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void add_Inventory_TYRE_History(InventoryTyreHistory TyreHistory) throws Exception {

		TyreHistoryRepository.save(TyreHistory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Tyre_Inventory_Invoice(org.fleetop.persistence.model.
	 * InventoryTyreInvoice)
	 */
	@Transactional
	public void update_Tyre_Inventory_Invoice(InventoryTyreInvoice TyreInvoice) throws Exception {
//here getINVOICE_AMOUNT isused to store balanceAmount
		TyreInvoiceRepository.update_Tyre_Inventory_Invoice(TyreInvoice.getWAREHOUSE_LOCATION_ID(),
				TyreInvoice.getPO_NUMBER(), TyreInvoice.getINVOICE_NUMBER(), TyreInvoice.getINVOICE_DATE(),
				TyreInvoice.getINVOICE_AMOUNT(), TyreInvoice.getINVOICE_AMOUNT(),TyreInvoice.getVENDOR_ID(),TyreInvoice.getDESCRIPTION(), 
				TyreInvoice.getLASTMODIFIEDBYID(), TyreInvoice.getLASTUPDATED_DATE(), TyreInvoice.getITYRE_ID(), TyreInvoice.getCOMPANY_ID(), 
				TyreInvoice.getTallyCompanyId(),TyreInvoice.getPAYMENT_TYPE_ID(),TyreInvoice.getSubLocationId(),TyreInvoice.getVENDOR_PAYMODE_STATUS_ID());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Tyre_Inventory_Amount(org.fleetop.persistence.model.
	 * InventoryTyreAmount)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update_Tyre_Inventory_Amount(InventoryTyreAmount TyreAmount) throws Exception {

		TyreAmountRepository.save(TyreAmount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Inventory_TYRE(org.fleetop.persistence.model.InventoryTyre)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update_Inventory_TYRE(InventoryTyre Tyre) throws Exception {

		TyreRepository.save(Tyre);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Inventory_TYRE_History(org.fleetop.persistence.model.
	 * InventoryTyreHistory)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update_Inventory_TYRE_History(InventoryTyreHistory TyreHistory) throws Exception {

		TyreHistoryRepository.save(TyreHistory);
	}

	public Page<InventoryTyreInvoice> getDeployment_Page_TyreInvoice(Integer pageNumber, Integer companyId) throws Exception {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "ITYRE_ID");
		return TyreInvoiceRepository.getDeployment_Page_TyreInvoice(companyId, request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * find_list_InventoryTyreInvoice()
	 */
	@Transactional
	public List<InventoryTyreInvoiceDto> find_list_InventoryTyreInvoice(Integer pageNumber, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.ITYRE_ID, VN.vendorName, R.INVOICE_NUMBER, PL.partlocation_name, R.INVOICE_AMOUNT, R.INVOICE_DATE, R.ITYRE_NUMBER, R.CREATEDBYID, U.firstName, U.lastName, R.VENDOR_PAYMODE_DATE, R.PAYMENT_TYPE_ID,"
				+ " R.tyre_document, R.tyre_document_id, R.VENDOR_PAYMODE_STATUS_ID, R.subLocationId, PSL.partlocation_name  "
						+ " FROM InventoryTyreInvoice AS R "
						+ "	INNER JOIN User AS U ON U.id =	R.CREATEDBYID "		
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID "
						+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = R.subLocationId "
						+ " LEFT JOIN Vendor VN ON VN.vendorId = R.VENDOR_ID "
						+ " WHERE R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.ITYRE_ID desc",
						Object[].class);
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreInvoiceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreInvoiceDto>();
			InventoryTyreInvoiceDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreInvoiceDto();

				list.setITYRE_ID((Long) result[0]);
				list.setVENDOR_NAME((String) result[1]);
				list.setINVOICE_NUMBER((String) result[2]);
				list.setWAREHOUSE_LOCATION((String) result[3]);
				list.setINVOICE_AMOUNT((Double) result[4]);
				list.setINVOICE_DATE_ON((Date) result[5]);
				list.setITYRE_NUMBER((Long) result[6]);
				if((Long) result[7] != null) {
				list.setCREATEDBYID((Long) result[7]);
				}
				if((String) result[8] != null) {
				list.setFirstName((String) result[8]);
				}
				if((String) result[9] != null) {
				list.setLastName((String) result[9]);
				}		
				if(result[10] != null) {
					list.setVENDOR_PAYMODE_DATE(DateTimeUtility.getDateFromTimeStamp((Timestamp)result[10], DateTimeUtility.DD_MM_YYYY) );
				}
				if(result[11] != null)
					list.setPAYMENT_TYPE_ID((short) result[11]);
				
				if(result[12] != null)
				list.setTyre_document((boolean) result[12]);
				
				if(result[13] != null) {
					list.setTyre_document_id((Long) result[13]);
				}
				list.setVENDOR_PAYMODE_STATUS_ID((Short) result[14]);
				
				if(result[15] != null)
					list.setSubLocationId((Integer) result[15]);
					list.setSubLocation((String)result[16]);
				
				Dtos.add(list);
			}
		}

		return Dtos;
	}


	public Page<InventoryTyre> getDeploymentLog(Integer pageNumber, Integer	companyId) {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "CREATEDBYID");
		return TyreRepository.getDeploymentLog(companyId, request);
	}

	@Transactional
	public List<InventoryTyreDto> find_List_InventoryTyre(Integer pageNumber, Integer	companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TYRE_ID, R.TYRE_NUMBER, TM.TYRE_MODEL, TSM.TYRE_MODEL_SUBTYPE, PL.partlocation_name, VS.TYRE_SIZE, R.TYRE_AMOUNT, R.TYRE_ASSIGN_STATUS_ID, R.ITYRE_INVOICE_ID, R.TYRE_USEAGE, R.TYRE_RETREAD_COUNT, R.TYRE_IN_NUMBER, "
						+ " R.OPEN_ODOMETER, V.vehicle_Odometer"
						+ " FROM InventoryTyre AS R "
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = R.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = R.TYRE_MODEL_ID"
						+ " LEFT JOIN Vehicle V ON V.vid = R.VEHICLE_ID "
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID "
						+ " WHERE R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.TYRE_ID desc",
						Object[].class);
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreDto>();
			InventoryTyreDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreDto();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				list.setTYRE_MANUFACTURER((String) result[2]);
				list.setTYRE_MODEL((String) result[3]);
				list.setWAREHOUSE_LOCATION((String) result[4]);
				list.setTYRE_SIZE((String) result[5]);
				list.setTYRE_AMOUNT((Double) result[6]);
				list.setTYRE_ASSIGN_STATUS_ID((short) result[7]);
				list.setTYRE_ASSIGN_STATUS(InventoryTyreDto.getPaymentModeName((short) result[7]));
				list.setITYRE_INVOICE_ID((Long) result[8]);
				list.setTYRE_USEAGE((Integer) result[9]);
				list.setTYRE_RETREAD_COUNT((Integer) result[10]);
				list.setTYRE_IN_NUMBER((Long) result[11]);
				list.setOPEN_ODOMETER((Integer) result[12]);
				list.setVEHICLE_ODOMETER((Integer) result[13]);
				if(list.getTYRE_ASSIGN_STATUS_ID() == InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE) {
				if(list.getTYRE_USEAGE() != null && list.getVEHICLE_ODOMETER() != null && list.getOPEN_ODOMETER() != null) {
					list.setTYRE_USEAGE(list.getTYRE_USEAGE() + (list.getVEHICLE_ODOMETER() - list.getOPEN_ODOMETER()));
					}
				}
				Dtos.add(list);
			}
		}

		return Dtos;
	}

	public Page<InventoryTyre> getDeploymentLog_Location(Integer Location, Integer pageNumber, Integer companyId) {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return TyreRepository.getDeployment_Page_InventoryLocation(Location, companyId, pageable);

	}

	@Transactional
	public List<InventoryTyreDto> find_List_InventoryTyre_Location(Integer Location, Integer pageNumber, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TYRE_ID, R.TYRE_NUMBER, TM.TYRE_MODEL, TSM.TYRE_MODEL_SUBTYPE, PL.partlocation_name, VS.TYRE_SIZE, R.TYRE_AMOUNT, R.TYRE_ASSIGN_STATUS_ID, R.ITYRE_INVOICE_ID, R.TYRE_USEAGE, R.TYRE_RETREAD_COUNT,"
				+ " R.TYRE_IN_NUMBER, R.OPEN_ODOMETER, V.vehicle_Odometer,R.subLocationId, PSL.partlocation_name "
						+ "FROM InventoryTyre AS R "
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = R.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = R.TYRE_MODEL_ID"
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID"
						+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = R.subLocationId"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID "
						+ " LEFT JOIN Vehicle V ON V.vid = R.VEHICLE_ID "
						+ " WHERE R.WAREHOUSE_LOCATION_ID=" + Location
						+ " AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.TYRE_ID desc",
						Object[].class);
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreDto>();
			InventoryTyreDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreDto();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				list.setTYRE_MANUFACTURER((String) result[2]);
				list.setTYRE_MODEL((String) result[3]);
				list.setWAREHOUSE_LOCATION((String) result[4]);
				list.setTYRE_SIZE((String) result[5]);
				list.setTYRE_AMOUNT((Double) result[6]);
				list.setTYRE_ASSIGN_STATUS_ID((short) result[7]);
				list.setTYRE_ASSIGN_STATUS(InventoryTyreDto.getPaymentModeName((short) result[7]));
				list.setITYRE_INVOICE_ID((Long) result[8]);
				list.setTYRE_USEAGE((Integer) result[9]);
				list.setTYRE_RETREAD_COUNT((Integer) result[10]);
				list.setTYRE_IN_NUMBER((Long) result[11]);
				list.setOPEN_ODOMETER((Integer) result[12]);
				list.setVEHICLE_ODOMETER((Integer) result[13]);
				
				if(list.getTYRE_ASSIGN_STATUS_ID() == InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE) {
					if(list.getTYRE_USEAGE() != null && list.getVEHICLE_ODOMETER() != null && list.getOPEN_ODOMETER() != null) {
						list.setTYRE_USEAGE(list.getTYRE_USEAGE() + (list.getVEHICLE_ODOMETER() - list.getOPEN_ODOMETER()));
						}
					}
				if(result[14] != null) {
					list.setSubLocationId((Integer)result[14]);
					list.setSubLocation((String)result[15]);
				}
				
				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * find_List_InventoryTyreHistory()
	 */
	@Transactional
	public List<InventoryTyreHistoryDto> find_List_InventoryTyreHistory(Long ITYRE_ID, Integer companyId) throws Exception {
		//return TyreHistoryRepository.find_List_InventoryTyreHistory(ITYRE_ID, companyId);

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TYRE_ID, R.TYRE_NUMBER, R.VEHICLE_ID, V.vehicle_registration, R.POSITION, R.AXLE,"
						+ " R.TYRE_STATUS_ID, R.OPEN_ODOMETER, R.TYRE_USEAGE, R.TYRE_ASSIGN_DATE, R.TYRE_COMMENT "
						+ " FROM InventoryTyreHistory AS R "
						+ " LEFT JOIN Vehicle V ON V.vid = R.VEHICLE_ID"
						+ " WHERE R.TYRE_ID = "+ITYRE_ID+" AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.TYRE_HIS_ID DESC",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreHistoryDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreHistoryDto>();
			InventoryTyreHistoryDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreHistoryDto();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				list.setVEHICLE_ID((Integer) result[2]);
				list.setVEHICLE_REGISTRATION((String) result[3]);
				list.setPOSITION((String) result[4]);
				list.setAXLE((String) result[5]);
				list.setTYRE_STATUS_ID((short) result[6]);
				list.setTYRE_STATUS(InventoryTyreHistoryDto.getStatusName((short) result[6]));
				list.setOPEN_ODOMETER((Integer) result[7]);
				list.setTYRE_USEAGE((Integer) result[8]);

				if (result[9] != null) {
					list.setTYRE_ASSIGN_DATE(SQLdateFormat.format(result[9]));
				}
				list.setTYRE_COMMENT((String) result[10]);

				Dtos.add(list);
			}
		}

		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Get_list_InventoryTyreInvoice(java.lang.Long)
	 */
	@Transactional
	public InventoryTyreInvoice Get_list_InventoryTyreInvoice(Long ITYRE_ID, Integer companyId) throws Exception {

		return TyreInvoiceRepository.Get_list_InventoryTyreInvoice(ITYRE_ID, companyId);
	}

	@Override
	public InventoryTyreInvoiceDto Get_list_InventoryTyreInvoiceDetails(Long ITYRE_ID, Integer companyId)
			throws Exception {
		Query query = entityManager.createQuery(
						  " SELECT R.ITYRE_ID, R.ITYRE_NUMBER, PL.partlocation_name, R.WAREHOUSE_LOCATION_ID, R.PO_NUMBER, R.INVOICE_NUMBER, R.INVOICE_AMOUNT,"
						+ " R.INVOICE_DATE, R.VENDOR_ID, VN.vendorName, VN.vendorLocation, R.PAYMENT_TYPE_ID, R.PAYMENT_NUMBER, R.DESCRIPTION,"
						+ " R.VENDOR_PAYMODE_STATUS_ID, U.email, U.email, R.CREATED_DATE, R.LASTUPDATED_DATE, R.CREATEDBYID, R.LASTMODIFIEDBYID,"
						+ " R.anyTyreNumberAsign, R.STATUS_OF_TYRE, R.tallyCompanyId, TC.companyName,R.subLocationId, PSL.partlocation_name, PO.purchaseorder_Number,VSA.approvalId,VA.approvalNumber "
						+ " FROM InventoryTyreInvoice AS R "
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID "
						+ " LEFT JOIN Vendor VN ON VN.vendorId = R.VENDOR_ID "
						+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = R.tallyCompanyId"
						+ " INNER JOIN User U ON U.id = R.CREATEDBYID"
						+ " INNER JOIN User U2 ON U2.id = R.LASTMODIFIEDBYID"
						+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = R.subLocationId "
						+ " LEFT JOIN PurchaseOrders PO ON PO.purchaseorder_id = R.PO_NUMBER "
						+ " LEFT JOIN VendorSubApprovalDetails VSA ON VSA.invoiceId = R.ITYRE_ID AND VSA.approvalPlaceId = "+ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_INVOICE+" "
						+ " LEFT JOIN VendorApproval VA ON VA.approvalId = VSA.approvalId "
						+ " WHERE R.ITYRE_ID = "+ITYRE_ID+" AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.ITYRE_ID desc");


		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		InventoryTyreInvoiceDto select;
		if (result != null) {
			select	= new InventoryTyreInvoiceDto();
			select.setITYRE_ID((Long) result[0]);
			select.setITYRE_NUMBER((Long) result[1]);
			select.setWAREHOUSE_LOCATION((String) result[2]);
			select.setWAREHOUSE_LOCATION_ID((Integer) result[3]);
			if(result[4] != null && !((String)result[4]).equals("")) {
				select.setPurchaseOrderId(Long.parseLong(""+(String) result[4]));
			}
			select.setINVOICE_NUMBER((String) result[5]);
			if(result[6] != null)
			select.setINVOICE_AMOUNT(Double.parseDouble(toFixedTwo.format(result[6])));
			if(result[7] != null)
				select.setINVOICE_DATE(SQLdate.format((Date)result[7]));
			select.setVENDOR_ID((Integer) result[8]);
			select.setVENDOR_NAME((String) result[9]);
			select.setVENDOR_LOCATION((String) result[10]);
			if(result[11] != null)
				select.setPAYMENT_TYPE_ID((short) result[11]);
			select.setPAYMENT_NUMBER((String) result[12]);
			select.setDESCRIPTION((String) result[13]);
			select.setVENDOR_PAYMODE_STATUS_ID((short) result[14]);
			select.setVENDOR_PAYMODE_STATUS(FuelVendorPaymentMode.getPaymentMode(select.getVENDOR_PAYMODE_STATUS_ID()));
			select.setCREATEDBY((String) result[15]);
			select.setLASTMODIFIEDBY((String) result[16]);
			if(result[17] != null)
				select.setCREATED_DATE(CreatedDateTime.format((Date)result[17]));
			if(result[18] != null)
				select.setLASTUPDATED_DATE(CreatedDateTime.format((Date)result[18]));
			select.setCREATEDBYID((Long) result[19]);
			select.setLASTMODIFIEDBYID((Long) result[20]);
			select.setAnyTyreNumberAsign((boolean) result[21]);
			select.setSTATUS_OF_TYRE((short) result[22]);
			if(result[23] != null)
			select.setTallyCompanyId((Long) result[23]);
			if(result[24] != null)
			select.setTallyCompanyName((String) result[24]);
			if(result[25] != null) {
				select.setSubLocationId((Integer) result[25]);
				select.setSubLocation((String) result[26]);
			}
			if(result[27] != null) {
				select.setPO_NUMBER("PO-"+(Long) result[27]);
			}
			if(result[28] != null) {
				select.setApprovalId((Long) result[28]);
				select.setApprovalNumber("A-"+(Long) result[29]);
			}
			
		} else {
			return null;
		}

		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Get_List_InventoryTyreAmount(java.lang.Long)
	 */
	@Transactional
	public List<InventoryTyreAmountDto> Get_List_InventoryTyreAmount(Long ITYRE_ID, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.ITYRE_AMD_ID, TM.TYRE_MODEL, TSM.TYRE_MODEL_SUBTYPE, PL.partlocation_name, VS.TYRE_SIZE, R.TYRE_TREAD, R.TYRE_QUANTITY, R.UNIT_COST,"
						+ " R.DISCOUNT, R.TAX, R.TOTAL_AMOUNT, R.TYRE_ASSIGN_NO, R.discountTaxTypeId "
						+ " FROM InventoryTyreAmount AS R "
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = R.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = R.TYRE_MODEL_ID"
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID "
						+ " WHERE R.InventoryTyreInvoice.ITYRE_ID = "+ITYRE_ID+" AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.ITYRE_AMD_ID desc",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreAmountDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreAmountDto>();
			InventoryTyreAmountDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreAmountDto();
				list.setITYRE_AMD_ID((Long) result[0]);
				list.setTYRE_MANUFACTURER((String) result[1]);
				list.setTYRE_MODEL((String) result[2]);
				list.setWAREHOUSE_LOCATION((String) result[3]);
				list.setTYRE_SIZE((String) result[4]);
				list.setTYRE_TREAD((String) result[5]);
				if(result[6] != null)
				list.setTYRE_QUANTITY(Double.parseDouble(toFixedTwo.format(result[6])));
				if(result[7] != null)
				list.setUNIT_COST(Double.parseDouble(toFixedTwo.format(result[7])));
				if(result[8] != null)
				list.setDISCOUNT(Double.parseDouble(toFixedTwo.format(result[8])));
				if(result[9] != null)
				list.setTAX(Double.parseDouble(toFixedTwo.format( result[9])));
				if(result[10] != null)
				list.setTOTAL_AMOUNT(Double.parseDouble(toFixedTwo.format(result[10])));
				list.setTYRE_ASSIGN_NO((Integer) result[11]);
				
				if(result[12] != null) {
					list.setDiscountTaxTypeId((short) result[12]);
				} else {
					list.setDiscountTaxTypeId((short) 1);
				}

				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Get__List_InventoryTyre(java.lang.Long)
	 */
	@Transactional
	public List<InventoryTyreDto> Get__List_InventoryTyre(Long ITYRE_ID, Integer companyId) throws Exception {
		//return TyreRepository.Get__List_InventoryTyre(ITYRE_ID, companyId);

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TYRE_ID, R.TYRE_NUMBER, TM.TYRE_MODEL, TSM.TYRE_MODEL_SUBTYPE, PL.partlocation_name, VS.TYRE_SIZE, R.TYRE_AMOUNT, R.TYRE_ASSIGN_STATUS_ID, R.ITYRE_INVOICE_ID, R.TYRE_USEAGE, R.TYRE_RETREAD_COUNT,"
						+ " R.TYRE_IN_NUMBER, R.TYRE_TREAD, R.ITYRE_AMOUNT_ID, R.VEHICLE_ID, V.vehicle_registration, R.OPEN_ODOMETER, R.CLOSE_ODOMETER,"
						+ " R.TYRE_USEAGE, R.TYRE_PURCHASE_DATE, R.TYRE_ASSIGN_DATE, R.TYRE_RETREAD_ID, U.email, R.TYRE_SCRAPED_DATE "
						+ " FROM InventoryTyre AS R "
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = R.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = R.TYRE_MODEL_ID"
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID "
						+ " LEFT JOIN Vehicle V ON V.vid = R.VEHICLE_ID"
						+ " LEFT JOIN User U ON U.id = R.TYRE_SCRAPED_BY_ID"
						+ " WHERE R.ITYRE_INVOICE_ID = "+ITYRE_ID+" AND   R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.TYRE_ID desc",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreDto>();
			InventoryTyreDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreDto();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				list.setTYRE_MANUFACTURER((String) result[2]);
				list.setTYRE_MODEL((String) result[3]);
				list.setWAREHOUSE_LOCATION((String) result[4]);
				list.setTYRE_SIZE((String) result[5]);
				list.setTYRE_AMOUNT((Double) result[6]);
				list.setTYRE_ASSIGN_STATUS_ID((short) result[7]);
				list.setTYRE_ASSIGN_STATUS(InventoryTyreDto.getPaymentModeName((short) result[7]));
				list.setITYRE_INVOICE_ID((Long) result[8]);
				list.setTYRE_USEAGE((Integer) result[9]);
				list.setTYRE_RETREAD_COUNT((Integer) result[10]);
				list.setTYRE_IN_NUMBER((Long) result[11]);
				list.setTYRE_TREAD((String) result[12]);
				list.setITYRE_AMOUNT_ID((Long) result[13]);
				list.setVEHICLE_ID((Integer) result[14]);
				list.setVEHICLE_REGISTRATION((String) result[15]);
				list.setOPEN_ODOMETER((Integer) result[16]);
				list.setCLOSE_ODOMETER((Integer) result[17]);
				list.setTYRE_USEAGE((Integer) result[18]);
				if(result[19] != null)
					list.setTYRE_PURCHASE_DATE(SQLdateFormat.format((Date)result[19]));
				if(result[20] != null)
					list.setTYRE_ASSIGN_DATE(SQLdateFormat.format((Date)result[20]));
				list.setTYRE_RETREAD_ID((Long) result[21]);
				list.setTYRE_SCRAPED_BY((String) result[22]);
				if(result[23] != null)
					list.setTYRE_SCRAPED_DATE(SQLdateFormat.format((Date)result[23]));
				Dtos.add(list);
			}
		}

		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Get_AMount_ID_InventoryTyreAmount(java.lang.Long)
	 */
	@Transactional
	public InventoryTyreAmountDto Get_AMount_ID_InventoryTyreAmount(Long ITYRE_Aomunt_ID, Integer companyId) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT R.ITYRE_AMD_ID, TM.TYRE_MODEL, TSM.TYRE_MODEL_SUBTYPE, PL.partlocation_name, VS.TYRE_SIZE, R.TYRE_TREAD, R.TYRE_QUANTITY, R.UNIT_COST,"
						+ " R.DISCOUNT, R.TAX, R.TOTAL_AMOUNT, R.TYRE_ASSIGN_NO, R.InventoryTyreInvoice.ITYRE_ID, R.TYRE_MANUFACTURER_ID, R.TYRE_MODEL_ID,"
						+ " R.WAREHOUSE_LOCATION_ID, R.TYRE_SIZE_ID, TI.INVOICE_AMOUNT "
						+ " FROM InventoryTyreAmount AS R "
						+ " INNER JOIN InventoryTyreInvoice TI ON TI.ITYRE_ID = R.InventoryTyreInvoice.ITYRE_ID"
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = R.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = R.TYRE_MODEL_ID"
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID "
						+ " WHERE R.ITYRE_AMD_ID= "+ITYRE_Aomunt_ID+" AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.ITYRE_AMD_ID desc");


		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		InventoryTyreAmountDto list;
		if (result != null) {
			list = new InventoryTyreAmountDto();

			list = new InventoryTyreAmountDto();
			list.setITYRE_AMD_ID((Long) result[0]);
			list.setTYRE_MANUFACTURER((String) result[1]);
			list.setTYRE_MODEL((String) result[2]);
			list.setWAREHOUSE_LOCATION((String) result[3]);
			list.setTYRE_SIZE((String) result[4]);
			list.setTYRE_TREAD((String) result[5]);
			list.setTYRE_QUANTITY((Double) result[6]);
			list.setUNIT_COST((Double) result[7]);
			list.setDISCOUNT((Double) result[8]);
			list.setTAX((Double) result[9]);
			list.setTOTAL_AMOUNT((Double) result[10]);
			list.setTYRE_ASSIGN_NO((Integer) result[11]);
			list.setITYRE_ID((Long) result[12]);
			list.setTYRE_MANUFACTURER_ID((Integer) result[13]);
			list.setTYRE_MODEL_ID((Integer) result[14]);
			list.setWAREHOUSE_LOCATION_ID((Integer) result[15]);
			list.setTYRE_SIZE_ID((Integer) result[16]);
			list.setInvoiceAmount((Double) result[17]);

		} else {
			return null;
		}

		return list;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Validate_InventoryTyre(java.lang.String)
	 */
	@Transactional
	public InventoryTyre Validate_InventoryTyre(String Tyre_SerivalNo, Integer companyId) throws Exception {

		Query query = entityManager
				.createQuery("SELECT f.TYRE_ID, f.TYRE_NUMBER from InventoryTyre AS f where f.TYRE_NUMBER = :name AND f.COMPANY_ID = :companyId AND f.markForDelete = 0");

		query.setParameter("name", Tyre_SerivalNo);
		query.setParameter("companyId", companyId);

		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		InventoryTyre select = new InventoryTyre();
		if (vehicle != null) {
			select.setTYRE_ID((Long) vehicle[0]);
			select.setTYRE_NUMBER((String) vehicle[1]);
		} else {
			return null;
		}
		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Update_Inventory_Amount_Complete_Tyre_Number(java.lang.Integer,
	 * java.lang.Long)
	 */
	@Transactional
	public void Update_Inventory_Amount_Complete_Tyre_Number(Integer done_TyreSerialNo, Long Amount_ID, Integer companyId)
			throws Exception {

		entityManager.createQuery("UPDATE InventoryTyreAmount  SET TYRE_ASSIGN_NO = TYRE_ASSIGN_NO - "
				+ done_TyreSerialNo + " where ITYRE_AMD_ID=" + Amount_ID+" AND COMPANY_ID = "+companyId+"").executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * countTotal_TyreInventrory_AND_Qty()
	 */
	@Transactional
	public Object[] countTotal_TyreInventrory_AND_Qty() throws Exception {

		Query queryt = entityManager.createQuery(
				"SELECT COUNT(co),(SELECT  COUNT(P.TYRE_ID) FROM InventoryTyre AS P WHERE P.TYRE_ASSIGN_STATUS='AVAILABLE' ) "
						+ " FROM InventoryTyreInvoice As co");

		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}

	/*public Page<InventoryTyre> Search_getDeploymentLog(Integer pageNumber, String Search) {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "STATUS");
		return TyreRepository.fullText_InventoryTyre_Search(Search, request);
	}*/

	/*@Transactional
	public List<InventoryTyre> Search_List_InventoryTyre(Integer pageNumber, String Search) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TYRE_ID, R.TYRE_NUMBER, R.TYRE_MANUFACTURER, R.TYRE_MODEL, R.WAREHOUSE_LOCATION, R.TYRE_SIZE, R.TYRE_AMOUNT, R.TYRE_ASSIGN_STATUS, R.ITYRE_INVOICE_ID FROM InventoryTyre AS R WHERE  lower(TYRE_NUMBER) Like ('%"
						+ Search + "%') OR lower(TYRE_SIZE) Like ('%" + Search + "%') ORDER BY R.TYRE_ID desc",
				Object[].class);
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyre> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyre>();
			InventoryTyre list = null;
			for (Object[] result : results) {
				list = new InventoryTyre();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				list.setTYRE_MANUFACTURER((String) result[2]);
				list.setTYRE_MODEL((String) result[3]);
				list.setWAREHOUSE_LOCATION((String) result[4]);
				list.setTYRE_SIZE((String) result[5]);
				list.setTYRE_AMOUNT((Double) result[6]);
				list.setTYRE_ASSIGN_STATUS((String) result[7]);
				list.setITYRE_INVOICE_ID((Long) result[8]);
				Dtos.add(list);
			}
		}

		return Dtos;
	}
	 */
	@Transactional
	public List<InventoryTyreDto> Search_List_InventoryTyre(String Search, Integer companyId) throws Exception {
		List<InventoryTyreDto> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TYRE_ID, R.TYRE_NUMBER, TM.TYRE_MODEL, TSM.TYRE_MODEL_SUBTYPE, PL.partlocation_name, VS.TYRE_SIZE, R.TYRE_AMOUNT, R.TYRE_ASSIGN_STATUS_ID, R.ITYRE_INVOICE_ID, R.TYRE_IN_NUMBER FROM InventoryTyre AS R "
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = R.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = R.TYRE_MODEL_ID"
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID "
						+ " WHERE ( lower(R.TYRE_NUMBER) Like ('%"
						+ Search + "%') OR lower(VS.TYRE_SIZE) Like ('%" + Search + "%') ) AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.TYRE_ID desc",
						Object[].class);
		queryt.setFirstResult(0);
		queryt.setMaxResults(50);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreDto>();
			InventoryTyreDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreDto();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				list.setTYRE_MANUFACTURER((String) result[2]);
				list.setTYRE_MODEL((String) result[3]);
				list.setWAREHOUSE_LOCATION((String) result[4]);
				list.setTYRE_SIZE((String) result[5]);
				list.setTYRE_AMOUNT((Double) result[6]);
				list.setTYRE_ASSIGN_STATUS_ID((short) result[7]);
				list.setTYRE_ASSIGN_STATUS(InventoryTyreDto.getPaymentModeName((short) result[7]));
				list.setITYRE_INVOICE_ID((Long) result[8]);
				list.setTYRE_IN_NUMBER((Long) result[9]);
				Dtos.add(list);
			}
		}
		}
		return Dtos;
	}

	@Transactional
	public List<InventoryTyreDto> Show_PurchaseOrderTyre_to_List_InventoryTyre(Integer Size, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TYRE_ID, R.TYRE_NUMBER, TM.TYRE_MODEL, TSM.TYRE_MODEL_SUBTYPE, PL.partlocation_name, VS.TYRE_SIZE, R.TYRE_AMOUNT, R.TYRE_ASSIGN_STATUS_ID, R.ITYRE_INVOICE_ID, "
						+ " R.TYRE_IN_NUMBER FROM InventoryTyre AS R "
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = R.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = R.TYRE_MODEL_ID"
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID "
						+ " WHERE R.TYRE_ASSIGN_STATUS_ID="+InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE+" AND R.TYRE_SIZE_ID ="
						+ Size + " AND R.COMPANY_ID = "+companyId+" ORDER BY R.TYRE_ID desc",
						Object[].class);
		List<Object[]> results = queryt.getResultList();
		List<InventoryTyreDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreDto>();
			InventoryTyreDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreDto();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				list.setTYRE_MANUFACTURER((String) result[2]);
				list.setTYRE_MODEL((String) result[3]);
				list.setWAREHOUSE_LOCATION((String) result[4]);
				list.setTYRE_SIZE((String) result[5]);
				list.setTYRE_AMOUNT((Double) result[6]);
				list.setTYRE_ASSIGN_STATUS_ID((short) result[7]);
				list.setTYRE_ASSIGN_STATUS(InventoryTyreDto.getPaymentModeName((short) result[7]));
				list.setITYRE_INVOICE_ID((Long) result[8]);
				list.setTYRE_IN_NUMBER((Long) result[9]);
				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Search_List_InventoryTyreInvoice(java.lang.String)
	 */
	@Transactional
	public List<InventoryTyreInvoiceDto> Search_List_InventoryTyreInvoice(String Search, Integer companyId) throws Exception {
		List<InventoryTyreInvoiceDto> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.ITYRE_ID, VN.vendorName, R.INVOICE_NUMBER, PL.partlocation_name, R.INVOICE_AMOUNT, R.ITYRE_NUMBER, R.CREATEDBYID, U.firstName, U.lastName FROM InventoryTyreInvoice AS R "
						+ " INNER JOIN User AS U ON U.id = R.CREATEDBYID "
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = R.VENDOR_ID"
						+ " WHERE (lower(R.INVOICE_NUMBER) Like ('%"
						+ Search + "%') OR lower(R.ITYRE_ID) Like ('%" + Search + "%')) AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.ITYRE_ID desc",
						Object[].class);
		queryt.setFirstResult(0);
		queryt.setMaxResults(50);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreInvoiceDto>();
			InventoryTyreInvoiceDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreInvoiceDto();

				list.setITYRE_ID((Long) result[0]);
				list.setVENDOR_NAME((String) result[1]);
				list.setINVOICE_NUMBER((String) result[2]);
				list.setWAREHOUSE_LOCATION((String) result[3]);
				list.setINVOICE_AMOUNT((Double) result[4]);
				list.setITYRE_NUMBER((Long) result[5]);
				
				if((Long) result[6] != null) {
				list.setCREATEDBYID((Long) result[6]);
				}
				if((String) result[7] != null) {
				list.setFirstName((String) result[7]);
				}
				if((String) result[8] != null) {
				list.setLastName((String) result[8]);
				}

				Dtos.add(list);
			}
		}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Get_TYRE_ID_InventoryTyre(java.lang.Long)
	 */
	@Transactional
	public InventoryTyre Get_TYRE_ID_InventoryTyre(Long TYRE_ID) throws Exception {
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return TyreRepository.Get_TYRE_ID_InventoryTyre(TYRE_ID, userDetails.getCompany_id());
	}

	@Override
	public InventoryTyreDto Get_TYRE_ID_InventoryTyre(Long TYRE_ID, Integer companyId) throws Exception {
		//return TyreAmountRepository.Get_AMount_ID_InventoryTyreAmount(ITYRE_Aomunt_ID, companyId);

		Query query = entityManager.createQuery(" SELECT R.TYRE_ID, R.TYRE_IN_NUMBER, R.ITYRE_INVOICE_ID, R.ITYRE_AMOUNT_ID, R.TYRE_NUMBER, R.TYRE_AMOUNT,"
				+ " TM.TYRE_MODEL, TSM.TYRE_MODEL_SUBTYPE, R.TYRE_SIZE_ID, VS.TYRE_SIZE, R.TYRE_TREAD, PL.partlocation_name, R.VEHICLE_ID,"
				+ " V.vehicle_registration, R.TYRE_PURCHASE_DATE, R.TYRE_ASSIGN_DATE, R.OPEN_ODOMETER, R.CLOSE_ODOMETER, R.TYRE_USEAGE,"
				+ " R.TYRE_RETREAD_COUNT, U.email, R.TYRE_SCRAPED_DATE, R.TYRE_ASSIGN_STATUS_ID, U2.email, U3.email, R.CREATED_DATE,"
				+ " R.LASTUPDATED_DATE, R.WAREHOUSE_LOCATION_ID, R.TYRE_MANUFACTURER_ID, R.TYRE_MODEL_ID , V.vehicle_Odometer, R.subLocationId,"
				+ " PSL.partlocation_name, R.dismountedTyreStatusId, VTLP.POSITION, VTLP.remark  "
				+ " from InventoryTyre R "
				+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = R.TYRE_MANUFACTURER_ID"
				+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = R.TYRE_MODEL_ID"
				+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID"
				+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = R.subLocationId"
				+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID"
				+ " LEFT JOIN Vehicle V ON V.vid = R.VEHICLE_ID "
				+ " LEFT JOIN User U ON U.id = R.TYRE_SCRAPED_BY_ID "
				+ " LEFT JOIN User U2 ON U2.id = R.CREATEDBYID "
				+ " LEFT JOIN User U3 ON U3.id = R.LASTMODIFIEDBYID "
				+ " LEFT JOIN VehicleTyreLayoutPosition VTLP ON VTLP.TYRE_ID = R.TYRE_ID "
				+ " WHERE R.TYRE_ID="+TYRE_ID+" AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0");


		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		InventoryTyreDto list;
		if (result != null) {
			list = new InventoryTyreDto();
			list.setTYRE_ID((Long) result[0]);
			list.setTYRE_IN_NUMBER((Long) result[1]);
			list.setITYRE_INVOICE_ID((Long) result[2]);
			list.setITYRE_AMOUNT_ID((Long) result[3]);
			list.setTYRE_NUMBER((String) result[4]);
			list.setTYRE_AMOUNT((Double) result[5]);
			//System.out.println("Amount>>>"+result[5]);
			list.setTYRE_MANUFACTURER((String) result[6]);			
			list.setTYRE_MODEL((String) result[7]);
			list.setTYRE_SIZE_ID((Integer) result[8]);
			list.setTYRE_SIZE((String) result[9]);			
			list.setTYRE_TREAD((String) result[10]);
			list.setWAREHOUSE_LOCATION((String) result[11]);
			list.setVEHICLE_ID((Integer) result[12]);
			list.setVEHICLE_REGISTRATION((String) result[13]);
			list.setTYRE_PURCHASE_DATE_ON((Date) result[14]);
			list.setTYRE_ASSIGN_DATE_ON((Date) result[15]);
			list.setOPEN_ODOMETER((Integer) result[16]);
			list.setCLOSE_ODOMETER((Integer) result[17]);
			list.setTYRE_USEAGE((Integer) result[18]);			
			list.setTYRE_RETREAD_COUNT((Integer) result[19]);
			list.setTYRE_SCRAPED_BY((String) result[20]);
			list.setTYRE_SCRAPED_DATE_ON((Date) result[21]);
			list.setTYRE_ASSIGN_STATUS_ID((short) result[22]);			
			list.setCREATEDBY((String) result[23]);
			list.setLASTMODIFIEDBY((String) result[24]);
			list.setCREATED_DATE_ON((Date) result[25]);
			list.setLASTUPDATED_DATE_ON((Date) result[26]);
			list.setWAREHOUSE_LOCATION_ID((Integer) result[27]);
			list.setTYRE_MANUFACTURER_ID((Integer) result[28]);
			list.setTYRE_MODEL_ID((Integer) result[29]);
			list.setVEHICLE_ODOMETER((Integer) result[30]);
			
			if(result[31] != null) {
				list.setSubLocationId((Integer) result[31]);
				list.setSubLocation((String) result[32]);
			}
			if(result[33] != null && (short)result[33] > 0) {
				list.setDismountedTyreStatusId((short) result[33]);
				list.setDismountedTyreStatus(TyreAssignmentConstant.getOldTyreMovedTo((short) result[33]));
			}
			if(result[34] != null) {
				list.setPOSITION((String) result[34]);
			}else {
				list.setPOSITION("");
			}
			if(list.getTYRE_ASSIGN_STATUS_ID() == InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE && !list.getPOSITION().equals("SP-0")) {
				if(list.getTYRE_USEAGE() != null && list.getVEHICLE_ODOMETER() != null && list.getOPEN_ODOMETER() != null)
					list.setTYRE_USEAGE(list.getTYRE_USEAGE() + (list.getVEHICLE_ODOMETER() - list.getOPEN_ODOMETER()));
			}else if(list.getTYRE_ASSIGN_STATUS_ID() == InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE && list.getPOSITION().equals("SP-0")){
				list.setTYRE_USEAGE(list.getOPEN_ODOMETER());
			}else if(list.getTYRE_ASSIGN_STATUS_ID() == InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE && list.getCLOSE_ODOMETER() != null && list.getCLOSE_ODOMETER() > 0) {
				list.setTYRE_USEAGE(list.getCLOSE_ODOMETER());
			}
			if(result[35] != null)
				list.setRemark((String) result[35]);			
		} else {
			return null;
		}

		return list;

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * delete_Tyre_Inventory(java.lang.Long)
	 */
	@Transactional
	public void delete_Tyre_Inventory(Long Tyre_id, Integer companyId) throws Exception {

		TyreRepository.delete_Tyre_Inventory(Tyre_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Tyre_Inventory(java.lang.String, java.lang.Long)
	 */
	@Transactional
	public void update_Tyre_Inventory(String TyreSerialNO, Long LASTUPDATEBY, Date LASTUPDATEDATE, Long Tyre_id, Integer companyid)
			throws Exception {

		TyreRepository.update_Tyre_Inventory(TyreSerialNO, LASTUPDATEBY, LASTUPDATEDATE, Tyre_id, companyid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Validate_Amount_InventoryTyre(java.lang.Long)
	 */
	@Transactional
	public List<InventoryTyre> Validate_Amount_InventoryTyre(Long ITYRE_Amount_ID, Integer companyId) throws Exception {

		return TyreRepository.Validate_Amount_InventoryTyre(ITYRE_Amount_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * delete_Tyre_Inventory_Amount(java.lang.Long)
	 */
	@Transactional
	public void delete_Tyre_Inventory_Amount(Long ITYRE_Amount_ID, Integer companyId) throws Exception {

		TyreAmountRepository.delete_Tyre_Inventory_Amount(ITYRE_Amount_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Subtrack_InventoryAmount_to_InvoiceAmount(java.lang.Double,
	 * java.lang.Long)
	 */
	@Transactional
	public void update_Subtrack_InventoryAmount_to_InvoiceAmount(Double Amount, Long Tyre_INVOICE_ID, Integer companyId) throws Exception {

		entityManager.createQuery("UPDATE InventoryTyreInvoice  SET INVOICE_AMOUNT = INVOICE_AMOUNT - " + Amount
				+ " where ITYRE_ID=" + Tyre_INVOICE_ID+" AND COMPANY_ID = "+companyId).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * delete_Tyre_Inventory_Invoice(java.lang.Long)
	 */
	@Transactional
	public void delete_Tyre_Inventory_Invoice(Long ITYRE_Invoice_ID, Integer companyId) throws Exception {

		TyreInvoiceRepository.delete_Tyre_Inventory_Invoice(ITYRE_Invoice_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Add_InventoryAmount_to_tyreRemove(java.lang.Integer,
	 * java.lang.Long)
	 */
	@Transactional
	public void update_Add_InventoryAmount_to_tyreRemove(Integer Remove_TyreSerialNo, Long Tyre_Amount_ID, Integer companyId)
			throws Exception {

		entityManager.createQuery("UPDATE InventoryTyreAmount  SET TYRE_ASSIGN_NO = TYRE_ASSIGN_NO + "
				+ Remove_TyreSerialNo + " where ITYRE_AMD_ID=" + Tyre_Amount_ID+" AND COMPANY_ID = "+companyId).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Report_List_InventoryTyreInvoice(java.lang.String)
	 */
	@Transactional
	public List<InventoryTyreDto> Report_List_InventoryTyre(String Query, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TYRE_ID, R.TYRE_NUMBER, TM.TYRE_MODEL, TSM.TYRE_MODEL_SUBTYPE, PL.partlocation_name, VS.TYRE_SIZE, R.TYRE_AMOUNT, R.TYRE_ASSIGN_STATUS_ID, R.ITYRE_INVOICE_ID, R.TYRE_IN_NUMBER "
						+ " FROM InventoryTyre AS R "
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = R.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = R.TYRE_MODEL_ID"
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID "
						+ " WHERE  (R.markForDelete=0 " + Query + ") AND R.COMPANY_ID = "+companyId+" ",
						Object[].class);
		queryt.setFirstResult(0);
		queryt.setMaxResults(500);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreDto>();
			InventoryTyreDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreDto();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				list.setTYRE_MANUFACTURER((String) result[2]);
				list.setTYRE_MODEL((String) result[3]);
				list.setWAREHOUSE_LOCATION((String) result[4]);
				list.setTYRE_SIZE((String) result[5]);
				list.setTYRE_AMOUNT((Double) result[6]);
				list.setTYRE_ASSIGN_STATUS_ID((short) result[7]);
				list.setTYRE_ASSIGN_STATUS(InventoryTyreDto.getPaymentModeName((short) result[7]));
				list.setITYRE_INVOICE_ID((Long) result[8]);
				list.setTYRE_IN_NUMBER((Long) result[9]);
				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Report_List_InventoryTyreInvoice(java.lang.String)
	 */
	@Transactional
	public List<InventoryTyre> Tyre_Mount_InVehicle(String Search, Integer size, Integer companyId) throws Exception {
		List<InventoryTyre> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TYRE_ID, R.TYRE_NUMBER FROM InventoryTyre AS R " + "WHERE  R.TYRE_SIZE_ID=" + size
				+ " AND lower(R.TYRE_NUMBER) Like ('%" + Search + "%') AND R.TYRE_ASSIGN_STATUS_ID="+InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE+""
				+ " AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0",
				Object[].class);

		queryt.setFirstResult(0);
		queryt.setMaxResults(10);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyre>();
			InventoryTyre list = null;
			for (Object[] result : results) {
				list = new InventoryTyre();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				Dtos.add(list);
			}
		}
		}
		return Dtos;
	}
	
	@Override
	public List<InventoryTyre> Tyre_Mount_InVehicleAllSize(String search, Integer companyId) throws Exception {
		List<InventoryTyre> Dtos = null;
		if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TYRE_ID, R.TYRE_NUMBER FROM InventoryTyre AS R " + "WHERE  lower(R.TYRE_NUMBER) Like ('%" + search + "%')"
						+ " AND R.TYRE_ASSIGN_STATUS_ID="+InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE+""
						+ " AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0",
				Object[].class);

		queryt.setFirstResult(0);
		queryt.setMaxResults(10);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyre>();
			InventoryTyre list = null;
			for (Object[] result : results) {
				list = new InventoryTyre();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				Dtos.add(list);
			}
		}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Assign_TYRE_To_Vehicle_Mount(java.lang.Integer, java.lang.String,
	 * java.lang.Integer, java.lang.String, java.lang.Long)
	 */
	@Transactional
	public void update_Assign_TYRE_To_Vehicle_Mount(Integer VEHICLE_ID, 
			Integer OPEN_ODOMETER, short TYRE_ASSIGN_STATUS, Date TYRE_ASSIGN_DATE, Long TYRE_ID, Integer companyId, short dismountStatusId) throws Exception {

		TyreRepository.update_Assign_TYRE_To_Vehicle_Mount(VEHICLE_ID, OPEN_ODOMETER,
				TYRE_ASSIGN_STATUS, TYRE_ASSIGN_DATE, TYRE_ID, companyId,dismountStatusId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Assign_TYRE_To_Vehicle_DisMount(java.lang.Integer,
	 * java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String,
	 * java.lang.Long)
	 */
	@Transactional
	public void update_Assign_TYRE_To_Vehicle_DisMount(Integer VEHICLE_ID, 
			Integer CLOSE_ODOMETER, Integer TYRE_USEAGE, short TYRE_ASSIGN_STATUS, Long TYRE_ID) throws Exception {

		TyreRepository.update_Assign_TYRE_To_Vehicle_DisMount(VEHICLE_ID, CLOSE_ODOMETER,
				TYRE_USEAGE, TYRE_ASSIGN_STATUS, TYRE_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Assign_TYRE_To_Vehicle_DisMount(java.lang.Integer,
	 * java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String,
	 * java.lang.Long)
	 */
	@Transactional
	public void update_Assign_TYRE_To_Vehicle_Rotation(Integer TYRE_USEAGE, Long TYRE_ID) throws Exception {

		TyreRepository.update_Assign_TYRE_To_Vehicle_Rotation(TYRE_USEAGE, TYRE_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Filter_Retread_Tyre_Inventory(java.lang.String)
	 */
	@Transactional
	public List<InventoryTyreDto> Filter_Retread_Tyre_Inventory(String Query, Integer companyid) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TYRE_ID, R.TYRE_NUMBER, TM.TYRE_MODEL, TSM.TYRE_MODEL_SUBTYPE, PL.partlocation_name, VS.TYRE_SIZE, R.TYRE_AMOUNT, R.TYRE_ASSIGN_STATUS_ID, R.TYRE_USEAGE "
						+ " FROM InventoryTyre AS R "
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = R.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = R.TYRE_MODEL_ID"
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID "
						+ " WHERE ( R.TYRE_ASSIGN_STATUS_ID="+InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE+" " + Query + " ) AND R.COMPANY_ID = "+companyid+" AND R.markForDelete = 0",
						Object[].class);
		queryt.setFirstResult(0);
		queryt.setMaxResults(100);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreDto>();
			InventoryTyreDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreDto();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				list.setTYRE_MANUFACTURER((String) result[2]);
				list.setTYRE_MODEL((String) result[3]);
				list.setWAREHOUSE_LOCATION((String) result[4]);
				list.setTYRE_SIZE((String) result[5]);
				list.setTYRE_AMOUNT((Double) result[6]);
				list.setTYRE_ASSIGN_STATUS_ID((short) result[7]);
				list.setTYRE_ASSIGN_STATUS(InventoryTyreDto.getPaymentModeName((short) result[7]));
				list.setTYRE_USEAGE((Integer) result[8]);
				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Search_Only_InventoryTyre_ID(java.lang.String)
	 */
	@Transactional
	public List<InventoryTyre> Search_Only_InventoryTyre_ID(String Search, Integer companyid) throws Exception {
		List<InventoryTyre> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT R.TYRE_ID, R.TYRE_NUMBER, R.TYRE_IN_NUMBER FROM InventoryTyre AS R "
						+ "WHERE lower(R.TYRE_NUMBER) Like ('%" + Search + "%') AND R.COMPANY_ID = "+companyid+" AND R.markForDelete = 0", Object[].class);
		queryt.setFirstResult(0);
		queryt.setMaxResults(20);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyre>();
			InventoryTyre list = null;
			for (Object[] result : results) {
				list = new InventoryTyre();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				list.setTYRE_IN_NUMBER((Long) result[2]);

				Dtos.add(list);
			}
		}
		}
		return Dtos;
	}
	
	@Override
	public List<InventoryTyre> Search_Only_AvailableTyre_ID(String Search, Integer companyid) throws Exception {
		List<InventoryTyre> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT R.TYRE_ID, R.TYRE_NUMBER, R.TYRE_IN_NUMBER FROM InventoryTyre AS R "
						+ "WHERE lower(R.TYRE_NUMBER) Like ('%" + Search + "%') AND R.COMPANY_ID = "+companyid+" "
								+ " AND R.TYRE_ASSIGN_STATUS_ID = "+InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE+" AND R.markForDelete = 0", Object[].class);
		queryt.setFirstResult(0);
		queryt.setMaxResults(20);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyre>();
			InventoryTyre list = null;
			for (Object[] result : results) {
				list = new InventoryTyre();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				list.setTYRE_IN_NUMBER((Long) result[2]);

				Dtos.add(list);
			}
		}
		}	
		return Dtos;
	}

	@Override
	public List<InventoryTyre> Search_Only_InventoryTyre_ID(String Search, Integer location, Integer companyid)
			throws Exception {
		List<InventoryTyre> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT R.TYRE_ID, R.TYRE_NUMBER, R.TYRE_IN_NUMBER FROM InventoryTyre AS R "
						+ "WHERE lower(R.TYRE_NUMBER) Like ('%" + Search + "%') AND WAREHOUSE_LOCATION_ID = "+location+" AND R.COMPANY_ID = "+companyid+" AND R.markForDelete = 0", Object[].class);
		queryt.setFirstResult(0);
		queryt.setMaxResults(20);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyre>();
			InventoryTyre list = null;
			for (Object[] result : results) {
				list = new InventoryTyre();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				list.setTYRE_IN_NUMBER((Long) result[2]);

				Dtos.add(list);
			}
		}
		}
		return Dtos;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * add_Inventory_Retread_Tyre(org.fleetop.persistence.model.
	 * InventoryTyreRetread)
	 */
	@Transactional
	public void add_Inventory_Retread_Tyre(InventoryTyreRetread TyreRetread) throws Exception {

		InventorySequenceCounter		sequenceCounter	= null;
		try {
			/**
			 * getting next number 
			 */
			//sequenceCounter	= inventorySequenceRepository.findNextInventoryNumber(TyreRetread.getCOMPANY_ID(), SequenceTypeContant.SEQUENCE_TYPE_INVENTORY_TYRE_RETREAD);
			sequenceCounter	= inventorySequenceService.findNextInventoryNumber(TyreRetread.getCOMPANY_ID(), SequenceTypeContant.SEQUENCE_TYPE_INVENTORY_TYRE_RETREAD);
			TyreRetread.setTRNUMBER(sequenceCounter.getNextVal());

			TyreRetreadRepository.save(TyreRetread);

			/**
			 * updating next number 
			 */
			//inventorySequenceRepository.updateNextInventoryNumber(sequenceCounter.getNextVal() + 1, TyreRetread.getCOMPANY_ID(), sequenceCounter.getSequence_Id(), SequenceTypeContant.SEQUENCE_TYPE_INVENTORY_TYRE_RETREAD);

		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * add_Inventory_Retread_Tyre_Amount(org.fleetop.persistence.model.
	 * InventoryTyreRetreadAmount)
	 */
	@Transactional
	public void add_Inventory_Retread_Tyre_Amount(InventoryTyreRetreadAmount TyreRetreadAmount) throws Exception {

		TyreRetreadAmountRepository.save(TyreRetreadAmount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Get_Only_TYRE_ID_And_TyreSize(java.lang.Long)
	 */
	@Transactional
	public InventoryTyreDto Get_Only_TYRE_ID_And_TyreSize(Long TYRE_ID, Integer companyId) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT f.TYRE_ID, f.TYRE_NUMBER, VT.TYRE_SIZE, f.TYRE_USEAGE, f.TYRE_SIZE_ID from InventoryTyre AS f "
						+ " LEFT JOIN VehicleTyreSize VT ON VT.TS_ID = f.TYRE_SIZE_ID"
						+ " where f.TYRE_ID = :id AND f.COMPANY_ID = "+companyId+" AND f.markForDelete = 0");

		query.setParameter("id", TYRE_ID);
		Object[] Tyre = (Object[]) query.getSingleResult();

		InventoryTyreDto select = new InventoryTyreDto();
		if (Tyre != null) {
			select.setTYRE_ID((Long) Tyre[0]);
			select.setTYRE_NUMBER((String) Tyre[1]);
			select.setTYRE_SIZE((String) Tyre[2]);
			select.setTYRE_USEAGE((Integer) Tyre[3]);
			select.setTYRE_SIZE_ID( (Integer) Tyre[4]);
		}
		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Update_Inventory_Tyre_RetreadingStatus(java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public void Update_Inventory_Tyre_RetreadingStatus(Long TyreRetread_id, short Status, String Multiple_Tyre_ID, Integer companyId)
			throws Exception {

		entityManager.createQuery("update InventoryTyre set TYRE_RETREAD_ID=" + TyreRetread_id
				+ ", TYRE_ASSIGN_STATUS_ID=" + Status + "  where TYRE_ID IN (" + Multiple_Tyre_ID + ") AND COMPANY_ID = "+companyId+" ").executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * getDeploymentInventoryTyreRetreadLog(java.lang.Integer)
	 */
	@Override
	public Page<InventoryTyreRetread> getDeploymentInventoryTyreRetreadLog(Integer pageNumber, Integer companyId) throws Exception {

		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "TRID");
		return TyreRetreadRepository.getDeploymentInventoryTyreRetreadLog(companyId, request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * find_List_InventoryTyreRetread(java.lang.Integer)
	 */
	@Override
	public List<InventoryTyreRetreadDto> find_List_InventoryTyreRetread(Integer pageNumber, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TRID, R.TR_OPEN_DATE, R.TR_REQUIRED_DATE, R.TR_VENDOR_ID, VN.vendorName, R.TR_PAYMENT_TYPE_ID, R.TR_QUOTE_NO, R.TR_STATUS_ID, R.TRNUMBER, R.TR_VENDOR_PAYMODE_STATUS_ID "
						+ " FROM InventoryTyreRetread AS R "
						+ " LEFT JOIN Vendor VN ON VN.vendorId = R.TR_VENDOR_ID"
						+ " WHERE R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.TRID desc",
						Object[].class);
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreRetreadDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreRetreadDto>();
			InventoryTyreRetreadDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreRetreadDto();

				list.setTRID((Long) result[0]);
				list.setTR_OPEN_DATE_ON((Date) result[1]);
				list.setTR_REQUIRED_DATE_ON((Date) result[2]);
				list.setTR_VENDOR_ID((Integer) result[3]);
				list.setTR_VENDOR_NAME((String) result[4]);
				list.setTR_PAYMENT_TYPE_ID((short) result[5]);
				list.setTR_QUOTE_NO((String) result[6]);
				list.setTR_STATUS_ID((short) result[7]);
				list.setTRNUMBER((Long) result[8]);
				list.setTR_VENDOR_PAYMODE_STATUS_ID((short) result[9]);

				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Get_InventoryTyreRetread(java.lang.Long)
	 */
	@Override
	public InventoryTyreRetreadDto Get_InventoryTyreRetread(Long TRID, Integer companyId) throws Exception {
		// return TyreRetreadRepository.Get_InventoryTyreRetread(TRID, companyId);

		Query query = entityManager.createQuery(
				"SELECT f.TRID, f.TRNUMBER, f.TR_OPEN_DATE, f.TR_REQUIRED_DATE, f.TR_VENDOR_ID, VN.vendorName, VN.vendorLocation,"
						+ " f.TR_PAYMENT_TYPE_ID, f.TR_PAYMENT_NUMBER, f.TR_QUOTE_NO, f.TR_MANUAL_NO, f.TR_RECEIVE_TYRE, f.TR_DESCRIPTION,"
						+ " f.TR_RE_DESCRIPTION, f.TR_AMOUNT, f.TR_ROUNT_AMOUNT, f.TR_STATUS_ID, f.TR_INVOICE_NUMBER, f.TR_INVOICE_DATE,"
						+ " U.email, U2.email, f.CREATED_DATE, f.LASTUPDATED_DATE, f.TR_VENDOR_PAYMODE_STATUS_ID,VSA.approvalId,VA.approvalNumber "
						+ " from InventoryTyreRetread AS f "
						+ " LEFT JOIN Vendor VN ON VN.vendorId = f.TR_VENDOR_ID "
						+ " LEFT JOIN User U ON U.id = f.CREATEDBYID"
						+ " LEFT JOIN User U2 ON U2.id = f.LASTMODIFIEDBYID"
						+ " LEFT JOIN VendorSubApprovalDetails VSA ON VSA.invoiceId = f.TRID AND VSA.approvalPlaceId = "+ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_RETREAD+" "
						+ " LEFT JOIN VendorApproval VA ON VA.approvalId = VSA.approvalId "
						+ " where f.TRID= "+TRID+" AND f.COMPANY_ID = "+companyId+" AND f.markForDelete = 0");

		Object[] Tyre = (Object[]) query.getSingleResult();

		InventoryTyreRetreadDto TyreRetread = new InventoryTyreRetreadDto();
		if (Tyre != null) {
			TyreRetread.setTRID((Long) Tyre[0]);
			TyreRetread.setTRNUMBER((Long) Tyre[1]);

			if (Tyre[2] != null) {
				try {
					TyreRetread.setTR_OPEN_DATE(SQLdateFormat.format(Tyre[2]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (Tyre[3] != null) {
				try {
					TyreRetread.setTR_REQUIRED_DATE(SQLdateFormat.format(Tyre[3]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			TyreRetread.setTR_VENDOR_ID((Integer) Tyre[4]);
			TyreRetread.setTR_VENDOR_NAME((String) Tyre[5]);
			TyreRetread.setTR_VENDOR_LOCATION((String) Tyre[6]);

			TyreRetread.setTR_PAYMENT_TYPE_ID((short) Tyre[7]);
			TyreRetread.setTR_PAYMENT_TYPE(PaymentTypeConstant.getPaymentTypeName((short) Tyre[7]));
			TyreRetread.setTR_PAYMENT_NUMBER((String) Tyre[8]);

			TyreRetread.setTR_QUOTE_NO((String) Tyre[9]);
			TyreRetread.setTR_MANUAL_NO((String) Tyre[10]);

			TyreRetread.setTR_RECEIVE_TYRE((Integer) Tyre[11]);

			TyreRetread.setTR_DESCRIPTION((String) Tyre[12]);
			TyreRetread.setTR_RE_DESCRIPTION((String) Tyre[13]);

			TyreRetread.setTR_AMOUNT(Double.parseDouble(toFixedTwo.format(Tyre[14])));
			TyreRetread.setTR_ROUNT_AMOUNT(Double.parseDouble(toFixedTwo.format(Tyre[15])));

			TyreRetread.setTR_STATUS_ID((short) Tyre[16]);
			TyreRetread.setTR_STATUS(InventoryTyreRetreadDto.getTRStatusName((short) Tyre[16]));

			TyreRetread.setTR_INVOICE_NUMBER((String) Tyre[17]);
			if (Tyre[18] != null) {
				try {
					TyreRetread.setTR_INVOICE_DATE(SQLdateFormat.format(Tyre[18]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			TyreRetread.setCREATEDBY((String) Tyre[19]);
			TyreRetread.setLASTMODIFIEDBY((String) Tyre[20]);

			if (Tyre[21] != null) {
				TyreRetread.setCREATED_DATE(CreatedDateTime.format(Tyre[21]));
			}
			if (Tyre[22] != null) {
				TyreRetread.setLASTUPDATED_DATE(CreatedDateTime.format(Tyre[22]));
			}
			
			TyreRetread.setTR_VENDOR_PAYMODE_STATUS_ID((short) Tyre[23]);
			TyreRetread.setTR_VENDOR_PAYMODE_STATUS(InventoryTyreRetreadDto.getVendorPaymentModeName(TyreRetread.getTR_VENDOR_PAYMODE_STATUS_ID()));
			
			if(Tyre[24] != null) {
				TyreRetread.setApprovalId((Long) Tyre[24]);
				TyreRetread.setApprovalNumber("A-"+(Long) Tyre[25]);
			}
		}
		return TyreRetread;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Get_InventoryTyreRetread(java.lang.Long)
	 */
	@Override
	public InventoryTyreRetread Get_ApprovalID_InventoryTyreRetread(Long TRID) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT f.TRID, f.TR_APPROVAL_ID, f.TR_ROUNT_AMOUNT from InventoryTyreRetread AS f where f.TRID = :id AND f.markForDelete = 0");

		query.setParameter("id", TRID);
		Object[] Tyre = (Object[]) query.getSingleResult();

		InventoryTyreRetread select = new InventoryTyreRetread();
		if (Tyre != null) {
			select.setTRID((Long) Tyre[0]);
			select.setTR_APPROVAL_ID((Long) Tyre[1]);
			select.setTR_ROUNT_AMOUNT((Double) Tyre[2]);
		}
		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Get_InventoryTyreRetread_Amount(java.lang.Long)
	 */
	@Override
	public List<InventoryTyreRetreadAmountDto> Get_InventoryTyreRetread_Amount(Long TRID, Integer companyId) throws Exception {
		//return TyreRetreadAmountRepository.Get_InventoryTyreRetread_Amount(TRID, companyId);
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TR_AMOUNT_ID, R.TYRE_ID, R.TYRE_NUMBER, TS.TYRE_SIZE, R.RETREAD_AMOUNT, R.RETREAD_DISCOUNT, R.RETREAD_TAX, R.RETREAD_COST, R.TRA_STATUS_ID"
						+ " FROM InventoryTyreRetreadAmount AS R "
						+ " LEFT JOIN VehicleTyreSize TS ON TS.TS_ID = R.TYRE_SIZE_ID"
						+ " WHERE R.inventoryTyreRetread.TRID= "+TRID+" AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreRetreadAmountDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreRetreadAmountDto>();
			InventoryTyreRetreadAmountDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreRetreadAmountDto();

				list.setTR_AMOUNT_ID((Long) result[0]);
				list.setTYRE_ID((Long) result[1]);
				list.setTYRE_NUMBER((String) result[2]);
				list.setTYRE_SIZE((String) result[3]);
				list.setRETREAD_AMOUNT((Double) result[4]);
				if(result[5] != null) {
				list.setRETREAD_DISCOUNT(Double.parseDouble(toFixedTwo.format(result[5])));
				}else {
					list.setRETREAD_DISCOUNT(0.0);
				}
				list.setRETREAD_TAX((Double) result[6]);
				list.setRETREAD_COST(Double.parseDouble(toFixedTwo.format(result[7])));
				if(result[8] != null) {
					list.setTRA_STATUS_ID((short) result[8]);
					list.setTRA_STATUS(InventoryTyreRetreadAmountDto.getTraStatusName((short) result[8]));
				}


				Dtos.add(list);
			}
		}

		return Dtos;

	}

	/*
	@Override
	public InventoryTyreRetreadAmountDto Get_InventoryTyreRetread_AmountSingle(Long TRID, Integer companyId) throws Exception {
		System.err.println("TRID>>>>>>>>>>>"+TRID);
		//return TyreRetreadAmountRepository.Get_InventoryTyreRetread_Amount(TRID, companyId);
		Query query = entityManager.createQuery(
				"SELECT R.TR_AMOUNT_ID, R.TYRE_ID, R.TYRE_NUMBER, R.RETREAD_AMOUNT, R.RETREAD_DISCOUNT, R.RETREAD_TAX, R.RETREAD_COST, R.TRA_STATUS_ID"
				+ " from InventoryTyreRetreadAmount AS R "
				+ " where R.inventoryTyreRetread= "+TRID+" AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0");
				System.err.println("TRID"+TRID+"companyId"+companyId);
				System.err.println("query"+query);

		Object[] Tyre = (Object[]) query.getSingleResult();

		InventoryTyreRetreadAmountDto TyreRetread = new InventoryTyreRetreadAmountDto();
		if (Tyre != null) {
			TyreRetread.setTR_AMOUNT_ID((Long) Tyre[0]);
			TyreRetread.setTYRE_ID((Long) Tyre[1]);
			TyreRetread.setTYRE_NUMBER((String) Tyre[2]);
			TyreRetread.setTYRE_SIZE((String) Tyre[3]);
			TyreRetread.setRETREAD_AMOUNT((Double) Tyre[4]);
			TyreRetread.setRETREAD_DISCOUNT((Double) Tyre[5]);
			TyreRetread.setRETREAD_TAX((Double) Tyre[6]);
			TyreRetread.setRETREAD_COST((Double) Tyre[7]);
			TyreRetread.setTRA_STATUS_ID((short) Tyre[8]);
			TyreRetread.setTRID(TRID);




			}
		return TyreRetread;	
	}*/





	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Inventory_Retread_Tyre_cost(org.fleetop.persistence.model.
	 * InventoryTyreRetreadAmount)
	 */
	@Transactional
	public void update_Inventory_Retread_Amount_Tyre_cost(InventoryTyreRetreadAmount RetreadAmount) throws Exception {

		TyreRetreadAmountRepository.update_Inventory_Retread_Tyre_cost(RetreadAmount.getRETREAD_COST(),
				RetreadAmount.getRETREAD_DISCOUNT(), RetreadAmount.getRETREAD_TAX(), RetreadAmount.getRETREAD_AMOUNT(),
				RetreadAmount.getTR_AMOUNT_ID(), RetreadAmount.getCOMPANY_ID());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Inventory_Retread_Tyre_cost(java.lang.Double, java.lang.Long)
	 */
	@Transactional
	public void update_Inventory_Retread_Tyre_cost(Double TotalCost, Integer TR_RECEIVE_TYRE, Long TRID, Integer companyId)
			throws Exception {

		entityManager
		.createQuery("UPDATE InventoryTyreRetread  SET TR_AMOUNT = TR_AMOUNT + " + TotalCost+", balanceAmount = " + TotalCost
				+ ", TR_ROUNT_AMOUNT = TR_ROUNT_AMOUNT + " + TotalCost
				+ ", TR_RECEIVE_TYRE = TR_RECEIVE_TYRE + " + TR_RECEIVE_TYRE + "  where TRID=" + TRID+" AND COMPANY_ID = "+companyId)
		.executeUpdate();
	}

	@Transactional
	public void update_Edit_Inventory_Retread_Tyre_cost_ADD(Double TotalCost, Long TRID, Integer companyId) throws Exception {

		entityManager
		.createQuery("UPDATE InventoryTyreRetread  SET TR_AMOUNT = TR_AMOUNT + " + TotalCost
				+ ", TR_ROUNT_AMOUNT = TR_ROUNT_AMOUNT + " + TotalCost + "  where TRID=" + TRID+" COMPANY_ID = "+companyId)
		.executeUpdate();
	}

	@Transactional
	public void update_Edit_Inventory_Retread_Tyre_cost_Subtract(Double TotalCost, Long TRID, Integer companyid) throws Exception {

		entityManager
		.createQuery("UPDATE InventoryTyreRetread  SET TR_AMOUNT = TR_AMOUNT - " + TotalCost
				+ ", TR_ROUNT_AMOUNT = TR_ROUNT_AMOUNT - " + TotalCost + "  where TRID=" + TRID+" COMPANY_ID = "+companyid)
		.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Get_TYRE_ID_AND_TOTAL_AMOUNT_InventoryTyreRetreadAmount(java.lang.Long)
	 */
	@Transactional
	public InventoryTyreRetreadAmount Get_TYRE_ID_AND_TOTAL_AMOUNT_InventoryTyreRetreadAmount(Long TYRE_AMOUNT_ID, Integer companyid)
			throws Exception {

		Query query = entityManager.createQuery(
				"SELECT R.TR_AMOUNT_ID, R.TYRE_ID, R.TYRE_NUMBER, R.RETREAD_AMOUNT FROM InventoryTyreRetreadAmount AS R WHERE R.TR_AMOUNT_ID="
						+ TYRE_AMOUNT_ID + " AND R.COMPANY_ID = "+companyid+" AND R.markForDelete = 0",
						Object[].class);
		Object[] results = null;
		try {
			results = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		InventoryTyreRetreadAmount list = null;
		if (results != null) {
			{
				list = new InventoryTyreRetreadAmount();

				list.setTR_AMOUNT_ID((Long) results[0]);
				list.setTYRE_ID((Long) results[1]);
				list.setTYRE_NUMBER((String) results[2]);
				list.setRETREAD_AMOUNT((Double) results[3]);

			}
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Inventory_Retread_Amount_Tyre_cost_DELETE(org.fleetop.persistence.
	 * model.InventoryTyreRetreadAmount)
	 */
	@Transactional
	public void SUBTRACK_Inventory_Retread_Amount_Tyre_cost_DELETE(Double TotalCost, Integer TR_RECEIVE_TYRE, Long TRID, Integer companyid)
			throws Exception {

		entityManager
		.createQuery("UPDATE InventoryTyreRetread  SET TR_AMOUNT = TR_AMOUNT - " + TotalCost
				+ ", TR_ROUNT_AMOUNT = TR_ROUNT_AMOUNT - " + TotalCost
				+ ", TR_RECEIVE_TYRE = TR_RECEIVE_TYRE - " + TR_RECEIVE_TYRE + "  where TRID=" + TRID+" COMPANY_Id = "+companyid)
		.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * delete_Inventory_Retread_Amount_Tyre_cost(java.lang.Long)
	 */
	@Transactional
	public void delete_Inventory_Retread_Amount_Tyre_cost(Long IR_Amount_ID, Integer companyId) throws Exception {

		TyreRetreadAmountRepository.delete_Inventory_Retread_Amount_Tyre_cost(IR_Amount_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * delete_Tyre_InventoryRetread(java.lang.Long)
	 */
	@Transactional
	public void delete_Tyre_InventoryRetread(Long IRID, Integer companyId) throws Exception {

		TyreRetreadRepository.delete_Tyre_InventoryRetread(IRID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Update_Inventory_Tyre_AVALABLE_Status(java.lang.String, java.lang.String)
	 */
	@Transactional
	public void Update_Inventory_Tyre_AVALABLE_Status(short Status, Long Tyre_ID, Integer companyId) throws Exception {

		TyreRepository.Update_Inventory_Tyre_AVALABLE_Status(Status, Tyre_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Update_Inventory_ReTread_Status_and_Description(java.lang.String,
	 * java.lang.String, java.lang.Long)
	 */
	@Transactional
	public void Update_Inventory_ReTread_Status_and_Description(short Status, String Description, Long LastupdateBy,
			Date Lastupdated, Long TyreRetread_id, Integer companyId) throws Exception {

		TyreRetreadRepository.Update_Inventory_ReTread_Status_and_Description(Status, Description, LastupdateBy,
				Lastupdated, TyreRetread_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Update_Completed_Inventory_ReTread_Status_and_Description(java.lang.
	 * String, java.lang.String, java.lang.Long)
	 */
	@Transactional
	public void Update_Completed_Inventory_ReTread_Status_and_Description(short Status, String invoiceNumber,
			Date invoiceDate, String Description, Long LastupdateBy, Date Lastupdated, Long TyreRetread_id, Integer companyid)
					throws Exception {

		TyreRetreadRepository.Update_Completed_Inventory_ReTread_Status_and_Description(Status, invoiceNumber,
				invoiceDate, Description, LastupdateBy, Lastupdated, TyreRetread_id, companyid);
	}

	@Transactional
	public void Update_Completed_Inventory_ReTread_Status_and_Description(short Status, String Description,
			Long TyreRetread_id, Integer companyId) throws Exception {

		TyreRetreadRepository.Update_Completed_Inventory_ReTread_Status_and_Description(Status, Description,
				TyreRetread_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Update_RECEVIED_Status(java.lang.String, java.lang.Long)
	 */
	@Transactional
	public void Update_RECEVIED_RetreadAmount_Status(short Status, Long TyreRetread_id, Integer companyId) throws Exception {

		TyreRetreadAmountRepository.Update_RECEVIED_RetreadAmount_Status(Status, TyreRetread_id,companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * GET_RECEVIED_Retread_TYRE_Amount(java.lang.Long)
	 */
	@Transactional
	public InventoryTyre GET_RECEVIED_Retread_TYRE_Amount(Long Tyre_id, Integer companyId) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT R.TYRE_ID, R.TYRE_NUMBER, R.TYRE_AMOUNT, R.TYRE_USEAGE, R.TYRE_RETREAD_COUNT FROM InventoryTyre AS R WHERE R.TYRE_ID="
						+ Tyre_id + " AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0",
						Object[].class);
		Object[] results = null;
		try {
			results = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		InventoryTyre list = null;
		if (results != null) {
			{
				list = new InventoryTyre();

				list.setTYRE_ID((Long) results[0]);
				list.setTYRE_NUMBER((String) results[1]);
				list.setTYRE_AMOUNT((Double) results[2]);
				list.setTYRE_USEAGE((Integer) results[3]);
				list.setTYRE_RETREAD_COUNT((Integer) results[4]);

			}
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * add_Tyre_Inventory_TyreLifeHistory(org.fleetop.persistence.model.
	 * InventoryTyreLifeHistory)
	 */
	@Transactional
	public void add_Tyre_Inventory_TyreLifeHistory(InventoryTyreLifeHistory TyreLifeHistory) throws Exception {

		TyreLifeHistoryRepository.save(TyreLifeHistory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * find_list_InventoryTyreLifeHistory(java.lang.Long)
	 */
	@Transactional
	public List<InventoryTyreLifeHistory> find_list_InventoryTyreLifeHistory(Long Tyre_ID, Integer companyId) throws Exception {

		return TyreLifeHistoryRepository.find_list_InventoryTyreLifeHistory(Tyre_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Update_REtread_Amount_Usage_RetreadPeriod_InventoryTYRE(java.lang.
	 * Integer, java.lang.Double, java.lang.Integer, java.lang.Long)
	 */
	@Transactional
	public void Update_REtread_Amount_Usage_RetreadPeriod_Status_InventoryTYRE(Integer RetreadUsage,
			Double RetreadAmount, Integer RetreadCount, short Status, Long Tyre_id, Integer companyId) throws Exception {

		TyreRepository.Update_REtread_Amount_Usage_RetreadPeriod_Status_InventoryTYRE(RetreadUsage, RetreadAmount,
				RetreadCount, Status, Tyre_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Update_Inventory_Tyre_ScropStatus(java.lang.String, java.lang.String,
	 * java.util.Date, java.lang.String, java.lang.String)
	 */
	@Transactional
	public void Update_Inventory_Tyre_ScropStatus(Long scropedby, Date scropedDate, short Status,
			String Multiple_Tyre_ID, Integer companyId) throws Exception {
		entityManager.createQuery("update InventoryTyre set TYRE_SCRAPED_BY_ID=" + scropedby + ", TYRE_SCRAPED_DATE='"
				+ scropedDate + "', TYRE_ASSIGN_STATUS_ID='" + Status + "' where TYRE_ID IN (" + Multiple_Tyre_ID + ") AND COMPANY_ID = "+companyId+"")
		.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Get_Validate_TYRE_Document(java.lang.Long)
	 */
	@Transactional
	public org.fleetopgroup.persistence.document.InventoryTyreRetreadDocument Get_Validate_TYRE_Document(Long ITYRE_ID, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("TRID").is(ITYRE_ID).and("COMPANY_ID").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.InventoryTyreRetreadDocument.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * add_Tyre_InventoryTyreRetreadDocument(org.fleetop.persistence.model.
	 * InventoryTyreRetreadDocument)
	 */
	@Transactional
	public void add_Tyre_InventoryTyreRetreadDocument(org.fleetopgroup.persistence.document.InventoryTyreRetreadDocument TyreRetreadDocument)
			throws Exception {
		TyreRetreadDocument.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_INVENTORY_TYRE_RETREAD_DOCUMENT));
		mongoTemplate.save(TyreRetreadDocument);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Tyre_InventoryTyreRetreadDocument(org.fleetop.persistence.model.
	 * InventoryTyreRetreadDocument)
	 */
	@Transactional
	public void update_Tyre_InventoryTyreRetreadDocument(org.fleetopgroup.persistence.document.InventoryTyreRetreadDocument TyreRetreadDocument)
			throws Exception {
		mongoTemplate.save(TyreRetreadDocument);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Filter_Vendor_creditList_InventoryTyreInvoice(java.lang.Integer,
	 * java.lang.String)
	 */
	@Transactional
	public List<InventoryTyreInvoiceDto> Filter_Vendor_creditList_InventoryTyreInvoice(Integer vendor_ID,
			String dateRangeFrom, String dateRangeTo, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery("SELECT R.ITYRE_ID, VN.vendorName, R.INVOICE_NUMBER, R.INVOICE_DATE, R.INVOICE_AMOUNT, "
				+ " R.PAYMENT_TYPE_ID, R.VENDOR_PAYMODE_STATUS_ID, R.ITYRE_NUMBER, R.paidAmount, R.balanceAmount, R.tyre_document_id, R.VENDOR_ID "
				+ " FROM InventoryTyreInvoice AS R "
				+ " LEFT JOIN Vendor VN ON VN.vendorId = R.VENDOR_ID "
				+ " WHERE  ( R.VENDOR_ID=:vid AND R.PAYMENT_TYPE_ID="+PaymentTypeConstant.PAYMENT_TYPE_CREDIT+" and R.VENDOR_PAYMODE_STATUS_ID ="+InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID+" OR  "
				+ " R.VENDOR_ID=:vid AND R.PAYMENT_TYPE_ID= "+PaymentTypeConstant.PAYMENT_TYPE_CREDIT+" AND R.VENDOR_PAYMODE_STATUS_ID = "+InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_APPROVED+"OR "
				+ " R.VENDOR_ID=:vid AND R.PAYMENT_TYPE_ID= "+PaymentTypeConstant.PAYMENT_TYPE_CREDIT+" AND R.VENDOR_PAYMODE_STATUS_ID = "+InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_PARTIALLY_PAID+" AND R.balanceAmount > 0) "
				+ " AND R.INVOICE_DATE  between '"
				+ dateRangeFrom + "' AND '" + dateRangeTo + "' AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.INVOICE_DATE DESC",
				Object[].class);
		queryt.setParameter("vid", vendor_ID);

		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreInvoiceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreInvoiceDto>();
			InventoryTyreInvoiceDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreInvoiceDto();

				list.setITYRE_ID((Long) result[0]);
				list.setVENDOR_NAME((String) result[1]);
				list.setINVOICE_NUMBER((String) result[2]);
				list.setINVOICE_DATE_ON((Date) result[3]);
				if(result[4] != null)
				list.setINVOICE_AMOUNT(Double.parseDouble(toFixedTwo.format(result[4])));
				list.setPAYMENT_TYPE_ID((short) result[5]);
				list.setVENDOR_PAYMODE_STATUS_ID((short) result[6]);
				list.setITYRE_NUMBER((Long) result[7]);
				if(result[8]!= null) {
					list.setPaidAmount(Double.parseDouble(toFixedTwo.format(result[8])));
				}
				if(result[9]!= null) {
					list.setBalanceAmount(Double.parseDouble(toFixedTwo.format(result[9])));
				}
				if(result[10]!= null) {
					list.setTyre_document_id((Long) result[10]);
				}
				if(result[11]!= null) {
					list.setVENDOR_ID((Integer) result[11]);
				}

				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Filter_Vendor_creditList_InventoryTyreRetread(java.lang.Integer,
	 * java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<InventoryTyreRetreadDto> Filter_Vendor_creditList_InventoryTyreRetread(Integer vendor_ID,
			String dateRangeFrom, String dateRangeTo, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery("SELECT R.TRID, R.TR_VENDOR_ID, VN.vendorName, R.TR_PAYMENT_TYPE_ID, R.TR_INVOICE_NUMBER, R.TR_INVOICE_DATE, "
				+ " R.TR_VENDOR_PAYMODE_STATUS_ID, R.TR_AMOUNT, R.TRNUMBER, R.paidAmount, R.balanceAmount, R.TR_VENDOR_ID "
				+ " FROM InventoryTyreRetread AS R "
				+ " LEFT JOIN Vendor VN ON VN.vendorId = R.TR_VENDOR_ID "
				+ " WHERE  ( R.TR_VENDOR_ID=:vid AND R.TR_PAYMENT_TYPE_ID= "+PaymentTypeConstant.PAYMENT_TYPE_CREDIT+" and R.TR_VENDOR_PAYMODE_STATUS_ID ="+InventoryTyreRetreadDto.TR_VENDOR_PAYMODE_STATUS_NOTPAID+" OR  "
				+ " R.TR_VENDOR_ID=:vid AND R.TR_PAYMENT_TYPE_ID="+PaymentTypeConstant.PAYMENT_TYPE_CREDIT+" AND R.TR_VENDOR_PAYMODE_STATUS_ID ="+InventoryTyreRetreadDto.TR_VENDOR_PAYMODE_STATUS_APPROVED+ " OR "
				+ " R.TR_VENDOR_ID=:vid AND R.TR_PAYMENT_TYPE_ID="+PaymentTypeConstant.PAYMENT_TYPE_CREDIT+" AND R.TR_VENDOR_PAYMODE_STATUS_ID ="+InventoryTyreRetreadDto.TR_VENDOR_PAYMODE_STATUS_PARTIALLY_PAID+ " AND R.balanceAmount > 0) "
				+ " AND R.TR_INVOICE_DATE  between '"
				+ dateRangeFrom + "' AND '" + dateRangeTo + "' AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.TR_INVOICE_DATE DESC",
				Object[].class);
		queryt.setParameter("vid", vendor_ID);

		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreRetreadDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreRetreadDto>();
			InventoryTyreRetreadDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreRetreadDto();

				list.setTRID((Long) result[0]);
				list.setTR_VENDOR_ID((Integer) result[1]);
				list.setTR_VENDOR_NAME((String) result[2]);
				list.setTR_PAYMENT_TYPE_ID((short) result[3]);
				list.setTR_INVOICE_NUMBER((String) result[4]);
				list.setTR_INVOICE_DATE_ON((Date) result[5]);
				list.setTR_VENDOR_PAYMODE_STATUS_ID((short) result[6]);
				if(result[7] != null)
				list.setTR_AMOUNT(Double.parseDouble(toFixedTwo.format(result[7])));
				list.setTRNUMBER((Long) result[8]);
				if(result[9]!= null) {
				list.setPaidAmount(Double.parseDouble(toFixedTwo.format(result[9])));
				}
				if(result[10]!= null) {
				list.setBalanceAmount(Double.parseDouble(toFixedTwo.format(result[10])));
				}
				if(result[11]!= null) {
					list.setTR_VENDOR_ID((Integer) result[11]);
				}
				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreInvoice_ID(java.lang.
	 * String, java.lang.Long, java.lang.String)
	 */
	@Transactional
	public void update_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreInvoice_ID(String ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		entityManager.createQuery("  UPDATE InventoryTyreInvoice SET TYRE_APPROVAL_ID=" + Approval_ID
				+ ", VENDOR_PAYMODE_STATUS_ID=" + approval_Status + " WHERE ITYRE_ID IN (" + ApprovalInvoice_ID + ") AND COMPANY_ID = "+userDetails.getCompany_id()+" ")
		.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentDetails(Long approvalId, String paymentDate, short paymentStatus) throws Exception {

		entityManager.createQuery("  UPDATE InventoryTyreInvoice SET VENDOR_PAYMODE_DATE ='" + paymentDate+"' , "
				+ " VENDOR_PAYMODE_STATUS_ID ="+paymentStatus+""
				+ " WHERE TYRE_APPROVAL_ID = "+approvalId+" ").executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception {
		entityManager.createQuery("  UPDATE InventoryTyreInvoice SET VENDOR_PAYMODE_DATE =" + paymentDate+" , "
				+ " VENDOR_PAYMODE_STATUS_ID="+paymentStatus+", TYRE_APPROVAL_ID = "+approvalId+""
				+ " WHERE ITYRE_ID = "+invoiceId+" ").executeUpdate();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * getVendorApproval_IN_InventoryTyreInvoice_List(java.lang.Long)
	 */
	@Transactional
	public List<InventoryTyreInvoiceDto> getVendorApproval_IN_InventoryTyreInvoice_List(Long VendorApproval_Id, Integer companyId)
			throws Exception {
		//return TyreInvoiceRepository.getVendorApproval_IN_InventoryTyreInvoice_List(VendorApproval_Id);

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.ITYRE_ID, VN.vendorName, R.INVOICE_NUMBER, PL.partlocation_name, R.INVOICE_AMOUNT, R.INVOICE_DATE, R.ITYRE_NUMBER,"
						+ " VN.vendorLocation, R.PO_NUMBER, R.INVOICE_AMOUNT,R.paidAmount "
						+ " FROM InventoryTyreInvoice AS R"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = R.VENDOR_ID "
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID "
						+ " WHERE R.TYRE_APPROVAL_ID= "+VendorApproval_Id+" AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreInvoiceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreInvoiceDto>();
			InventoryTyreInvoiceDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreInvoiceDto();

				list.setITYRE_ID((Long) result[0]);
				list.setVENDOR_NAME((String) result[1]);
				list.setINVOICE_NUMBER((String) result[2]);
				list.setWAREHOUSE_LOCATION((String) result[3]);
				list.setINVOICE_AMOUNT((Double) result[4]);
				list.setINVOICE_DATE_ON((Date) result[5]);
				list.setITYRE_NUMBER((Long) result[6]);
				list.setVENDOR_LOCATION((String) result[7]);
				list.setPO_NUMBER((String) result[8]);
				list.setINVOICE_AMOUNT((Double) result[9]);
				if((Double) result[10] != null)
				list.setPaidAmount((Double) result[10]);
				Dtos.add(list);
			}
		}

		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * getVendorApproval_IN_InventoryTyreInvoice_List(java.lang.Long)
	 */
	@Transactional
	public List<InventoryTyreInvoiceDto> get_Amount_VendorApproval_IN_InventoryTyreInvoice(Long VendorApproval_Id, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT t.ITYRE_ID, t.INVOICE_AMOUNT From InventoryTyreInvoice as t WHERE t.TYRE_APPROVAL_ID=:approval AND t.COMPANY_ID =:companyId AND t.markForDelete = 0",
				Object[].class);
		queryt.setParameter("approval", VendorApproval_Id);
		queryt.setParameter("companyId", companyId);

		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreInvoiceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreInvoiceDto>();
			InventoryTyreInvoiceDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreInvoiceDto();

				list.setITYRE_ID((Long) result[0]);
				list.setINVOICE_AMOUNT((Double) result[1]);

				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreRetread_ID(java.lang.
	 * String, java.lang.Long, java.lang.String)
	 */
	@Transactional
	public void update_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreRetread_ID(String ApprovalService_ID,
			Long Approval_ID, short approval_Status) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		entityManager.createQuery(" UPDATE From InventoryTyreRetread SET TR_APPROVAL_ID=" + Approval_ID
				+ ", TR_VENDOR_PAYMODE_STATUS_ID=" + approval_Status + " WHERE TRID IN (" + ApprovalService_ID + ") AND COMPANY_ID = "+userDetails.getCompany_id()+" ")
		.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentDetailsForRt(Long approvalId, String paymentDate, short paymentStatus) throws Exception {

		entityManager.createQuery("  UPDATE InventoryTyreRetread SET TR_VENDOR_PAYMODE_DATE ='" + paymentDate+"' , "
				+ " TR_VENDOR_PAYMODE_STATUS_ID ="+paymentStatus+""
				+ " WHERE TR_APPROVAL_ID = "+approvalId+" ").executeUpdate();
	}
	
	
	@Override
	@Transactional
	public void updateVendorPaymentCancelDetailsForRT(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception {
		entityManager.createQuery("  UPDATE InventoryTyreRetread SET TR_VENDOR_PAYMODE_DATE =" + paymentDate+" , "
				+ " TR_VENDOR_PAYMODE_STATUS_ID="+paymentStatus+", TR_APPROVAL_ID = "+approvalId+""
				+ " WHERE TRID = "+invoiceId+" ").executeUpdate();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * getVendorApproval_IN_InventoryTyreRetread_List(java.lang.Long)
	 */
	@Transactional
	public List<InventoryTyreRetreadDto> getVendorApproval_IN_InventoryTyreRetread_List(Long VendorApproval_Id, Integer companyId)
			throws Exception {
		//return TyreRetreadRepository.getVendorApproval_IN_InventoryTyreRetread_List(VendorApproval_Id);

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TRID, R.TR_OPEN_DATE, R.TR_REQUIRED_DATE, R.TR_VENDOR_ID, VN.vendorName, R.TR_PAYMENT_TYPE_ID, R.TR_QUOTE_NO, R.TR_STATUS_ID, R.TRNUMBER,"
						+ " R.TR_INVOICE_NUMBER, R.TR_INVOICE_DATE, R.TR_MANUAL_NO, R.TR_ROUNT_AMOUNT, R.TR_VENDOR_PAYMODE_STATUS_ID,R.paidAmount "
						+ " FROM InventoryTyreRetread AS R "
						+ " LEFT JOIN Vendor VN ON VN.vendorId = R.TR_VENDOR_ID "
						+ " WHERE R.TR_APPROVAL_ID = "+VendorApproval_Id+" AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.TRID desc",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreRetreadDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreRetreadDto>();
			InventoryTyreRetreadDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreRetreadDto();

				list.setTRID((Long) result[0]);
				list.setTR_OPEN_DATE_ON((Date) result[1]);
				list.setTR_REQUIRED_DATE_ON((Date) result[2]);
				list.setTR_VENDOR_ID((Integer) result[3]);
				list.setTR_VENDOR_NAME((String) result[4]);
				list.setTR_PAYMENT_TYPE_ID((short) result[5]);
				list.setTR_QUOTE_NO((String) result[6]);
				list.setTR_STATUS_ID((short) result[7]);
				list.setTRNUMBER((Long) result[8]);
				list.setTR_INVOICE_NUMBER((String) result[9]);
				list.setTR_INVOICE_DATE_ON((Date) result[10]);
				list.setTR_MANUAL_NO((String) result[11]);
				list.setTR_ROUNT_AMOUNT((Double) result[12]);
				list.setTR_VENDOR_PAYMODE_STATUS_ID((short) result[13]);
				if((Double) result[14] != null)
				list.setPaidAmount((Double) result[14]);

				Dtos.add(list);
			}
		}

		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * getVendorApproval_IN_InventoryTyreRetread_List(java.lang.Long)
	 */
	@Transactional
	public List<InventoryTyreRetreadDto> get_Amount_VendorApproval_IN_InventoryTyreRetread(Long VendorApproval_Id, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TRID, R.TR_ROUNT_AMOUNT From InventoryTyreRetread As R WHERE R.TR_APPROVAL_ID=:approval AND R.COMPANY_ID =:companyId AND R.markForDelete = 0",
				Object[].class);
		queryt.setParameter("approval", VendorApproval_Id);
		queryt.setParameter("companyId", companyId);

		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreRetreadDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreRetreadDto>();
			InventoryTyreRetreadDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreRetreadDto();

				list.setTRID((Long) result[0]);
				list.setTR_ROUNT_AMOUNT((Double) result[1]);

				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreInvoice_ID(
	 * java.lang.String, java.lang.Long, java.lang.String, java.util.Date)
	 */
	@Transactional
	public void update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreInvoice_ID(String ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Date approval_date, Integer companyId,double paidAmount,double discountAmount) throws Exception {
		double balanceAmount = 0;
		entityManager.createQuery("  UPDATE InventoryTyreInvoice SET TYRE_APPROVAL_ID=" + Approval_ID
				+ ",VENDOR_PAYMODE_DATE='" + approval_date + "', VENDOR_PAYMODE_STATUS_ID=' " + approval_Status 
				+ "', paidAmount = '"+paidAmount+"', discountAmount = '"+discountAmount+"' , balanceAmount = '"+balanceAmount+"' "
				+ " WHERE ITYRE_ID IN (" + ApprovalInvoice_ID + ") AND COMPANY_ID = "+companyId+" ").executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreRetread_ID(
	 * java.lang.String, java.lang.Long, java.lang.String, java.util.Date)
	 */
	@Transactional
	public void update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreRetread_ID(String ApprovalService_ID,
			Long Approval_ID, short approval_Status, Date approval_date, Integer companyId,double paidAmount, double discountAmount) throws Exception {
			double balanceAmount = 0;
		entityManager.createQuery(" UPDATE From InventoryTyreRetread SET TR_APPROVAL_ID=" + Approval_ID
				+ ", TR_VENDOR_PAYMODE_DATE='" + approval_date + "', TR_VENDOR_PAYMODE_STATUS_ID=' " + approval_Status
				+ "', paidAmount = '"+paidAmount+"', discountAmount = '"+discountAmount+"' , balanceAmount = '"+balanceAmount+"' "
				+ " WHERE TRID IN (" + ApprovalService_ID + ") AND COMPANY_ID = "+companyId+" ").executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Tyre_Purchase_Report(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<InventoryTyreInvoiceDto> Tyre_Purchase_Report(String dateRangeFrom, String DateRangeTo, String Query)
			throws Exception {
		/*TypedQuery<InventoryTyreInvoice> queryt = entityManager
				.createQuery("FROM InventoryTyreInvoice as R WHERE R.INVOICE_DATE BETWEEN '" + dateRangeFrom + "' AND '"
						+ DateRangeTo + "' " + Query + " ", InventoryTyreInvoice.class);*/
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.ITYRE_ID, VN.vendorName, R.INVOICE_NUMBER, PL.partlocation_name, R.INVOICE_AMOUNT, R.INVOICE_DATE, R.ITYRE_NUMBER "
						+ " , R.PO_NUMBER, R.INVOICE_NUMBER, R.VENDOR_ID, VN.vendorLocation, R.PAYMENT_TYPE_ID, R.VENDOR_PAYMODE_STATUS_ID, R.STATUS_OF_TYRE"
						+ " FROM InventoryTyreInvoice AS R"
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID "
						+ " LEFT JOIN Vendor VN ON VN.vendorId = R.VENDOR_ID"
						+ " WHERE R.INVOICE_DATE BETWEEN '" + dateRangeFrom + "' AND '"
						+ DateRangeTo + "' " + Query + " ",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreInvoiceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreInvoiceDto>();
			InventoryTyreInvoiceDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreInvoiceDto();

				list.setITYRE_ID((Long) result[0]);
				list.setVENDOR_NAME((String) result[1]);
				list.setINVOICE_NUMBER((String) result[2]);
				list.setWAREHOUSE_LOCATION((String) result[3]);
				if(result[4] != null)
				list.setINVOICE_AMOUNT(Double.parseDouble(toFixedTwo.format(result[4])));
				list.setINVOICE_DATE_ON((Date) result[5]);
				list.setITYRE_NUMBER((Long) result[6]);
				list.setPO_NUMBER((String) result[7]);
				list.setINVOICE_NUMBER((String) result[8]);
				list.setVENDOR_ID((Integer) result[9]);
				list.setVENDOR_LOCATION((String) result[10]);
				list.setPAYMENT_TYPE_ID((short) result[11]);
				list.setVENDOR_PAYMODE_STATUS_ID((short) result[12]);
				list.setSTATUS_OF_TYRE((short) result[13]);
				

				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Tyre_Purchase_Report_TyreAmount(java.lang.String)
	 */
	@Transactional
	public List<InventoryTyreAmountDto> Tyre_Purchase_Report_TyreAmount(String multiple_ID, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT A.ITYRE_AMD_ID, TM.TYRE_MODEL, TSM.TYRE_MODEL_SUBTYPE, VS.TYRE_SIZE, A.TYRE_QUANTITY, A.TOTAL_AMOUNT, PL.partlocation_name, A.InventoryTyreInvoice.ITYRE_ID "
						+ " From InventoryTyreAmount AS A  "
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = A.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = A.TYRE_MODEL_ID"
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = A.WAREHOUSE_LOCATION_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = A.TYRE_SIZE_ID "
						+ " WHERE A.InventoryTyreInvoice IN ( "
						+ multiple_ID + ") AND A.COMPANY_ID = "+companyId+" AND A.markForDelete = 0 ) ",
						Object[].class);

		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreAmountDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreAmountDto>();
			InventoryTyreAmountDto list = null;

			for (Object[] result : results) {
				list = new InventoryTyreAmountDto();

				list.setITYRE_AMD_ID((Long) result[0]);
				list.setTYRE_MANUFACTURER((String) result[1]);
				list.setTYRE_MODEL((String) result[2]);
				list.setTYRE_SIZE((String) result[3]);
				list.setTYRE_QUANTITY((Double) result[4]);
				list.setTOTAL_AMOUNT((Double) result[5]);
				list.setWAREHOUSE_LOCATION((String) result[6]);
				list.setITYRE_ID( (Long) result[7]);

				Dtos.add(list);
			}
		}

		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Tyre_Purchase_Report_InventoryTyre(java.lang.String)
	 */
	@Transactional
	public List<InventoryTyreDto> Tyre_Purchase_Report_InventoryTyre(String multiple_ID, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT A.TYRE_ID, A.ITYRE_AMOUNT_ID, A.ITYRE_INVOICE_ID, A.TYRE_NUMBER, A.TYRE_AMOUNT From InventoryTyre AS A  WHERE A.ITYRE_INVOICE_ID IN ( "
						+ multiple_ID + " ) AND A.COMPANY_ID = "+companyId+" AND A.markForDelete = 0) ",
						Object[].class);

		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreDto>();
			InventoryTyreDto list = null;

			for (Object[] result : results) {
				list = new InventoryTyreDto();

				list.setTYRE_ID((Long) result[0]);
				list.setITYRE_AMOUNT_ID((Long) result[1]);
				list.setITYRE_INVOICE_ID((Long) result[2]);
				list.setTYRE_NUMBER((String) result[3]);
				list.setTYRE_AMOUNT((Double) result[4]);

				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Tyre_Purchase_Report_InventoryTyre_TotalAmount(java.lang.String)
	 */
	@Transactional
	public List<Double> Tyre_Purchase_Report_InventoryTyre_TotalAmount(String multiple_ID, Integer companyId) {

		TypedQuery<Double> query = entityManager.createQuery(
				"select sum(INVOICE_AMOUNT) from InventoryTyreInvoice where ITYRE_ID IN (" + multiple_ID + ")"
						+ " AND COMPANY_ID = "+companyId+" AND markForDelete = 0 ",
						Double.class);

		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Tyre_Stock_Report(java.lang.String)
	 */
	@Transactional
	public List<InventoryTyreDto> Tyre_Stock_Report(String Query) throws Exception {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TYRE_ID, R.TYRE_NUMBER, TM.TYRE_MODEL, TSM.TYRE_MODEL_SUBTYPE, PL.partlocation_name, VS.TYRE_SIZE, R.TYRE_ASSIGN_STATUS_ID, R.TYRE_RETREAD_COUNT, R.TYRE_USEAGE, "
						+ " R.VEHICLE_ID, V.vehicle_registration, V.vehicle_Odometer, R.OPEN_ODOMETER, R.TYRE_AMOUNT, VP.POSITION  "
						+ " FROM InventoryTyre AS R "
						+ " LEFT JOIN VehicleTyreLayoutPosition as VP ON R.TYRE_ID = VP.TYRE_ID "		
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = R.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = R.TYRE_MODEL_ID"
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID "
						+ " LEFT JOIN Vehicle V ON V.vid = R.VEHICLE_ID"
						+ " WHERE R.markForDelete = 0 " + Query + " ORDER BY R.TYRE_ID desc",
						Object[].class);
		List<Object[]> results = queryt.getResultList();
		List<InventoryTyreDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreDto>();
			InventoryTyreDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreDto();
				NumberFormat	formatter = new DecimalFormat("0.00");
				double costPerKM	= 0.0;
				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				list.setTYRE_MANUFACTURER((String) result[2]);
				list.setTYRE_MODEL((String) result[3]);
				list.setWAREHOUSE_LOCATION((String) result[4]);
				list.setTYRE_SIZE((String) result[5]);
				list.setTYRE_ASSIGN_STATUS_ID((short) result[6]);
				list.setTYRE_ASSIGN_STATUS(InventoryTyreDto.getPaymentModeName((short) result[6]));
				list.setTYRE_RETREAD_COUNT((Integer) result[7]);
				list.setTYRE_USEAGE((Integer) result[8]);
				list.setVEHICLE_ID((Integer) result[9]);
				list.setVEHICLE_REGISTRATION((String) result[10]);
				list.setVEHICLE_ODOMETER((Integer) result[11]);
				list.setOPEN_ODOMETER((Integer) result[12]);
				list.setTYRE_AMOUNT((Double) result[13]);
				list.setPOSITION((String) result[14]);
				if(list.getTYRE_ASSIGN_STATUS_ID() == InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE) {
					if(list.getTYRE_USEAGE() != null && list.getVEHICLE_ODOMETER() != null && list.getOPEN_ODOMETER() != null)
						list.setTYRE_USEAGE(list.getTYRE_USEAGE() + (list.getVEHICLE_ODOMETER() - list.getOPEN_ODOMETER()));
				}
				
				if(list.getTYRE_AMOUNT() > 0)
					costPerKM	= list.getTYRE_USEAGE()/list.getTYRE_AMOUNT();
				
					costPerKM	= Double.parseDouble(formatter.format(costPerKM));
				
				list.setCOST_PER_KM(costPerKM);			
				
				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Purchase_Order_Validate_InventoryTyre(java.lang.Long)
	 */
	@Transactional
	public List<InventoryTyreDto> Purchase_Order_Validate_InventoryTyre(Integer TYRE_SIZE_ID) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TYRE_ID, R.TYRE_NUMBER, R.TYRE_RETREAD_COUNT, VS.TYRE_SIZE FROM InventoryTyre AS R "
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID "
						+ " WHERE R.TYRE_ASSIGN_STATUS_ID="+InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE+" AND R.TYRE_SIZE_ID=" + TYRE_SIZE_ID + " AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreDto>();
			InventoryTyreDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreDto();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				list.setTYRE_RETREAD_COUNT((Integer) result[2]);
				list.setTYRE_SIZE((String) result[3]);
				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * add_Inventory_Tyre_Transfer(org.fleetop.persistence.model.
	 * InventoryTyreTransfer)
	 */
	@Transactional
	public void add_Inventory_Tyre_Transfer(InventoryTyreTransfer createTransferIn) {

		TyreTransferRepository.save(createTransferIn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Find_InventoryTyreTranfer_Details_ID(java.lang.Long)
	 */
	@Transactional
	public InventoryTyreTransfer Find_InventoryTyreTranfer_Details_ID(Long inventory_transfer_id, Integer companyId) {

		return TyreTransferRepository.Find_InventoryTyreTransfer_Details_ID(inventory_transfer_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Update_InventoryTyreTransfer_ReceivedBy_Details(java.sql.Timestamp,
	 * java.lang.String, java.lang.String, java.sql.Timestamp, java.lang.Long)
	 */
	@Transactional
	public void Update_InventoryTyreTransfer_ReceivedBy_Details(Timestamp transfer_receiveddate,
			String transfer_receivedReason, Long lASTMODIFIEDBY, Timestamp transfer_receiveddate2, Long ittid, Integer companyId) {

		TyreTransferRepository.Update_InventoryTyreTransfer_ReceivedBy_Details(transfer_receiveddate,
				transfer_receivedReason, lASTMODIFIEDBY, transfer_receiveddate2, ittid, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Get_Tyre_id_To_InventoryTyreTransfer(java.lang.Long)
	 */
	@Transactional
	public List<InventoryTyreTransferDto> Get_Tyre_id_To_InventoryTyreTransfer(Long tyre_id, Integer companyId) {

		TypedQuery<Object[]> query = entityManager.createQuery(
				" SELECT IT.ITTID, IT.TYRE_ID, INVT.TYRE_NUMBER, PL.partlocation_name, Pm.partlocation_name, IT.TRA_QUANTITY, IT.TRANSFER_DATE, U.email,"
						+ " IT.TRANSFER_VIA_ID, U2.firstName, U2.email, IT.TRANSFER_RECEIVEDREASON, IT.TRANSFER_STATUS_ID, IT.TRANSFER_RECEIVEDDATE,"
						+ " IT.TRANSFER_REASON, IT.TRA_INVENTORY_INVOCEID, IT.CREATED_DATE, IT.LASTUPDATED_DATE, U3.email, U4.email, IT.TRANSFER_RECEIVEDBYID"
						+ " FROM InventoryTyreTransfer AS IT "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = IT.TRA_FROM_LOCATION_ID"
						+ " INNER JOIN PartLocations Pm ON Pm.partlocation_id = IT.TRA_TO_LOCATION_ID"
						+ " INNER JOIN User U ON U.id = IT.TRANSFER_BY_ID"
						+ " INNER JOIN User U2 ON U2.id = IT.TRANSFER_RECEIVEDBYID"
						+ " INNER JOIN User U3 ON U3.id = IT.CREATEDBYID"
						+ " INNER JOIN User U4 ON U4.id = IT.LASTMODIFIEDBYID"
						+ " INNER JOIN InventoryTyre INVT ON INVT.TYRE_ID = IT.TYRE_ID"
						+ " WHERE IT.TYRE_ID=" + tyre_id + " AND IT.COMPANY_ID = "+companyId+" AND IT.markForDelete = 0  ORDER BY IT.ITTID desc",
						Object[].class);
		query.setFirstResult(0);
		query.setMaxResults(50);
		List<Object[]> results = query.getResultList();

		List<InventoryTyreTransferDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreTransferDto>();
			InventoryTyreTransferDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreTransferDto();

				list.setITTID((Long) result[0]);
				list.setTYRE_ID((Long) result[1]);
				list.setTYRE_NUMBER((String) result[2]);
				list.setTRA_FROM_LOCATION((String) result[3]);
				list.setTRA_TO_LOCATION((String) result[4]);
				list.setTRA_QUANTITY((Double) result[5]);
				list.setTRANSFER_DATE_ON((Date) result[6]);
				list.setTRANSFER_BY((String) result[7]);
				list.setTRANSFER_VIA_ID((short) result[8]);
				list.setTRANSFER_RECEIVEDBY((String) result[9]);
				list.setTRANSFER_RECEIVEDBYEMAIL((String) result[10]);
				list.setTRANSFER_RECEIVEDREASON((String) result[11]);
				list.setTRANSFER_STATUS_ID((short) result[12]);
				list.setTRANSFER_RECEIVEDDATE_ON((Date) result[13]);
				list.setTRANSFER_REASON((String) result[14]);
				list.setTRA_INVENTORY_INVOCEID((Long) result[15]);
				list.setCREATED_DATE_ON((Date) result[16]);
				list.setLASTUPDATED_DATE_ON((Date) result[17]);
				list.setCREATEDBY((String) result[18]);
				list.setLASTMODIFIEDBY((String) result[19]);
				list.setTRANSFER_RECEIVEDBY_ID((Long) result[20]);

				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IInventoryTyreService#
	 * Update_Transfer_Location_InventoryTyre(java.lang.String, java.lang.Long)
	 */
	@Transactional
	public void Update_Transfer_Location_InventoryTyre(Integer tra_TO_LOCATION, Long tyre_ID, Integer companyId) {

		TyreRepository.Update_Transfer_Location_InventoryTyre(tra_TO_LOCATION, tyre_ID, companyId);
	}

	@Override
	@Transactional
	public void save_Tyre_Inventory_Invoice(InventoryTyreInvoice TyreInvoice) throws Exception {
		TyreInvoiceRepository.save(TyreInvoice);
	}

	@Override
	@Transactional
	public InventoryTyre save_Inventory_TYRE(InventoryTyre Tyre) throws Exception {
		InventoryTyreSequenceCounter		sequenceCounter	= null;
		try {
			/**
			 * getting next number 
			 */
			sequenceCounter	= inventoryTyreSequenceService.findNextTyreNumber(Tyre.getCOMPANY_ID());
			Tyre.setTYRE_IN_NUMBER(sequenceCounter.getNextVal());
			/**
			 * Saving Inventory
			 */

			InventoryTyre dto = TyreRepository.save(Tyre);

			return dto;
			/**
			 * updating next number 
			 */
			//inventoryTyreSequenceRepository.updateNextTyreNumber(sequenceCounter.getNextVal() + 1, Tyre.getCOMPANY_ID(), sequenceCounter.getSequence_Id());
		} catch (Exception e) {
			throw e;
		}finally {
			sequenceCounter	= null;
		}
	}

	@Override
	public void transferInventoryTyreRetreadDocument(List<InventoryTyreRetreadDocument> list) throws Exception {
		org.fleetopgroup.persistence.document.InventoryTyreRetreadDocument				inventoryTyreRetreadDocument		= null;
		List<org.fleetopgroup.persistence.document.InventoryTyreRetreadDocument> 		inventoryTyreRetreadDocumentList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				inventoryTyreRetreadDocumentList	= new ArrayList<>();
				for(InventoryTyreRetreadDocument	document : list) {
					inventoryTyreRetreadDocument	= new org.fleetopgroup.persistence.document.InventoryTyreRetreadDocument();

					inventoryTyreRetreadDocument.set_id(document.getTRDOCID());
					inventoryTyreRetreadDocument.setTRID(document.getTRID());
					inventoryTyreRetreadDocument.setTR_CONTENT(document.getTR_CONTENT());
					inventoryTyreRetreadDocument.setTR_CONTENT_TYRE(document.getTR_CONTENT_TYRE());
					inventoryTyreRetreadDocument.setTR_FILENAME(document.getTR_FILENAME());
					inventoryTyreRetreadDocument.setCREATED_BY(document.getCREATED_BY());
					inventoryTyreRetreadDocument.setCREATED_DATE(document.getCREATED_DATE());
					inventoryTyreRetreadDocument.setCOMPANY_ID(document.getCOMPANY_ID());
					inventoryTyreRetreadDocument.setMarkForDelete(document.isMarkForDelete());

					inventoryTyreRetreadDocumentList.add(inventoryTyreRetreadDocument);
				}
				System.err.println("Saving InventoryTyreRetreadDocument ....");
				mongoTemplate.insert(inventoryTyreRetreadDocumentList, org.fleetopgroup.persistence.document.InventoryTyreRetreadDocument.class);
				System.err.println("Saved Successfully....");
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}

	@Override
	public long getInventoryTyreRetreadDocumentMaxId() throws Exception {
		try {
			return inventoryTyreRetreadDocumentRepository.getInventoryTyreRetreadDocumentMaxId();
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<InventoryTyreTransferDto> Get_Tyre_id_To_InventoryTyreTransfer(CustomUserDetails userDetails) {

		TypedQuery<Object[]> query = entityManager.createQuery(
				" SELECT IT.ITTID, IT.TYRE_ID, INVT.TYRE_NUMBER, PL.partlocation_name, Pm.partlocation_name, IT.TRA_QUANTITY, IT.TRANSFER_DATE, U.email,"
						+ " IT.TRANSFER_VIA_ID, U2.firstName, U2.email, IT.TRANSFER_RECEIVEDREASON, IT.TRANSFER_STATUS_ID, IT.TRANSFER_RECEIVEDDATE,"
						+ " IT.TRANSFER_REASON, IT.TRA_INVENTORY_INVOCEID, IT.CREATED_DATE, IT.LASTUPDATED_DATE, IT.TRANSFER_RECEIVEDBYID"
						+ " FROM InventoryTyreTransfer AS IT "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = IT.TRA_FROM_LOCATION_ID"
						+ " INNER JOIN PartLocations Pm ON Pm.partlocation_id = IT.TRA_TO_LOCATION_ID"
						+ " INNER JOIN User U ON U.id = IT.TRANSFER_BY_ID"
						+ " INNER JOIN User U2 ON U2.id = IT.TRANSFER_RECEIVEDBYID"
						+ " INNER JOIN InventoryTyre INVT ON INVT.TYRE_ID = IT.TYRE_ID"
						+ " WHERE IT.TRANSFER_RECEIVEDBYID=" + userDetails.getId() + " AND IT.COMPANY_ID = "+userDetails.getCompany_id()+""
						+ " AND IT.TRANSFER_STATUS_ID = "+InventoryTyreTransferDto.TRANSFER_STATUS_TRANSFER+" AND IT.markForDelete = 0  ORDER BY IT.ITTID desc",
						Object[].class);
		query.setFirstResult(0);
		query.setMaxResults(50);
		List<Object[]> results = query.getResultList();

		List<InventoryTyreTransferDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreTransferDto>();
			InventoryTyreTransferDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreTransferDto();

				list.setITTID((Long) result[0]);
				list.setTYRE_ID((Long) result[1]);
				list.setTYRE_NUMBER((String) result[2]);
				list.setTRA_FROM_LOCATION((String) result[3]);
				list.setTRA_TO_LOCATION((String) result[4]);
				list.setTRA_QUANTITY((Double) result[5]);
				list.setTRANSFER_DATE_ON((Date) result[6]);
				list.setTRANSFER_BY((String) result[7]);
				list.setTRANSFER_VIA_ID((short) result[8]);
				list.setTRANSFER_RECEIVEDBY((String) result[9]);
				list.setTRANSFER_RECEIVEDBYEMAIL((String) result[10]);
				list.setTRANSFER_RECEIVEDREASON((String) result[11]);
				list.setTRANSFER_STATUS_ID((short) result[12]);
				list.setTRANSFER_RECEIVEDDATE_ON((Date) result[13]);
				list.setTRANSFER_REASON((String) result[14]);
				list.setTRA_INVENTORY_INVOCEID((Long) result[15]);
				list.setCREATED_DATE_ON((Date) result[16]);
				list.setLASTUPDATED_DATE_ON((Date) result[17]);
				list.setTRANSFER_RECEIVEDBY_ID((Long) result[18]);

				Dtos.add(list);
			}
		}

		return Dtos;
	}

	@Override
	public ValueObject getInventoryTyreTransferedReport(ValueObject valueInObject) throws Exception {

		ValueObject				valueOutObject				= null;
		String					dateRange					= null;
		long					partLocationFrom						= 0;
		long					partLocationTo						= 0;
		short					TransferStatus						= 0;

		try {

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			dateRange			= valueInObject.getString("dateRange");
			partLocationFrom	= valueInObject.getLong("partLocationFrom",0);
			partLocationTo		= valueInObject.getLong("partLocationTo",0);
			TransferStatus		= valueInObject.getShort("TransferStatus",(short)0);

			String locationFROM = "", locationTO = "", Status = "";

			if (partLocationFrom > 0) {
				locationFROM = "AND ITT.TRA_FROM_LOCATION_ID =" + partLocationFrom + " ";
			}

			if (partLocationTo > 0) {
				locationTO = "AND ITT.TRA_TO_LOCATION_ID =" + partLocationTo + " ";
			}

			if (TransferStatus > 0 ) {
				Status = "AND ITT.TRANSFER_STATUS_ID=" + TransferStatus + "";
			}

			String dateRangeFrom = "", dateRangeTo = "";

			String[] From_TO_DateRange = null;

			From_TO_DateRange = dateRange.split(" to ");

			dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


			String query = "" + locationFROM + " " + locationTO + " " + Status + " ";			

			TypedQuery<Object[]> typedQuery = null;

			typedQuery = entityManager.createQuery(
					"SELECT ITT.ITTID, ITT.TYRE_ID, ITT.TYRE_NUMBER, PL.partlocation_name, PLT.partlocation_name, "
							+ " US.firstName, ITT.TRANSFER_DATE, UR.firstName, ITT.TRANSFER_RECEIVEDDATE, "
							+ " ITT.TRA_QUANTITY, ITT.TRANSFER_STATUS_ID FROM InventoryTyreTransfer AS ITT "
							+ " INNER JOIN User AS US ON US.id = ITT.TRANSFER_BY_ID "
							+ " LEFT JOIN User AS UR ON UR.id = ITT.TRANSFER_RECEIVEDBYID "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = ITT.TRA_FROM_LOCATION_ID "
							+ " INNER JOIN PartLocations PLT ON PLT.partlocation_id = ITT.TRA_TO_LOCATION_ID "
							+ " WHERE ITT.COMPANY_ID ="+userDetails.getCompany_id()+" AND ITT.TRANSFER_DATE BETWEEN '" + dateRangeFrom + "' AND  '"
							+ dateRangeTo + "' " + query + " AND ITT.markForDelete = 0 ORDER BY ITT.ITTID desc",
							Object[].class);

			List<Object[]> results = typedQuery.getResultList();

			List<InventoryTyreTransferDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryTyreTransferDto>();
				InventoryTyreTransferDto select = null;
				for (Object[] vehicle : results) {

					select = new InventoryTyreTransferDto();
					select.setITTID((Long) vehicle[0]);
					select.setTYRE_ID((Long) vehicle[1]);
					select.setTYRE_NUMBER((String) vehicle[2]);
					select.setTRA_FROM_LOCATION((String) vehicle[3]);
					select.setTRA_TO_LOCATION((String) vehicle[4]);
					select.setTRANSFER_BY((String) vehicle[5]);
					select.setTRANSFER_DATE(dateFormat.format((Date) vehicle[6]));
					if(vehicle[7] != null) {
						select.setTRANSFER_RECEIVEDBY((String) vehicle[7]);						
					} else {
						select.setTRANSFER_RECEIVEDBY("--");												
					}
					if(vehicle[8] != null) {
						select.setTRANSFER_RECEIVEDDATE(dateFormat.format((Date) vehicle[8]));						
					} else {
						select.setTRANSFER_RECEIVEDDATE("--");												
					}
					select.setTRA_QUANTITY((Double) vehicle[9]);
					select.setTRANSFER_STATUS(InventoryTyreTransferDto.getStatusName((Short) vehicle[10]));

					Dtos.add(select);
				}
			}

			valueOutObject	= new ValueObject();
			valueOutObject.put("InventoryTyreTransferList", Dtos);

			return	valueOutObject;
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

	@Override
	public ValueObject getTyreSentForRetreadingReport(ValueObject valueInObject) throws Exception {

		ValueObject				valueOutObject				= null;
		//String					dateRange					= null;
		Integer					vendorId					= null;
		Integer					locationId					= null;
		Integer					tyreSize					= null;
		String					queryString					= "";

		try {

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			//	dateRange			= valueInObject.getString("dateRange");
			locationId	= valueInObject.getInt("WarehouseLocation",0);
			vendorId		= valueInObject.getInt("TyreRetreadVendor",0);
			tyreSize		= valueInObject.getInt("ReporttyreSize",0);



			if (locationId > 0) {
				queryString  += "AND IT.WAREHOUSE_LOCATION_ID =" + locationId + " ";
			}

			if (vendorId > 0) {
				queryString += "AND ITR.TR_VENDOR_ID =" + vendorId + " ";
			}

			if (tyreSize > 0 ) {
				queryString += "AND ITT.TYRE_SIZE_ID=" + tyreSize + "";
			}

			TypedQuery<Object[]> typedQuery = null;

			typedQuery = entityManager.createQuery(
					"SELECT ITT.TR_AMOUNT_ID, IT.TYRE_NUMBER, ITR.TRNUMBER, PLT.partlocation_name, VTS.TYRE_SIZE, V.vendorName, V.vendorLocation,"
							+ " ITR.TR_OPEN_DATE, ITR.TR_REQUIRED_DATE FROM InventoryTyreRetreadAmount AS ITT"
							+ " INNER JOIN InventoryTyre  IT ON IT.TYRE_ID = ITT.TYRE_ID"
							+ " INNER JOIN InventoryTyreRetread ITR ON  ITR.TRID = ITT.inventoryTyreRetread.TRID"
							+ " INNER JOIN VehicleTyreSize VTS ON VTS.TS_ID = ITT.TYRE_SIZE_ID"
							+ " INNER JOIN PartLocations PLT ON PLT.partlocation_id = IT.WAREHOUSE_LOCATION_ID "
							+ " INNER JOIN Vendor V ON V.vendorId = ITR.TR_VENDOR_ID"
							+ " WHERE ITT.COMPANY_ID ="+userDetails.getCompany_id()+" " + queryString + " AND ITT.TRA_STATUS_ID = "+InventoryTyreRetreadAmountDto.TRA_STATUS_OPEN+" AND ITT.markForDelete = 0 ORDER BY ITT.TR_AMOUNT_ID desc",
							Object[].class);

			List<Object[]> results = typedQuery.getResultList();


			List<InventoryTyreRetreadAmountDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryTyreRetreadAmountDto>();
				InventoryTyreRetreadAmountDto select = null;
				for (Object[] vehicle : results) {

					select = new InventoryTyreRetreadAmountDto();

					select.setTR_AMOUNT_ID((Long) vehicle[0]);
					select.setTYRE_NUMBER((String) vehicle[1]);
					select.setTRNUMBER((Long) vehicle[2]);
					select.setPartLocationName((String) vehicle[3]);
					select.setTYRE_SIZE((String) vehicle[4]);
					select.setVENDOR_NAME((String) vehicle[5]);
					select.setVENDOR_LOCATION((String) vehicle[6]);
					select.setTR_OPEN_DATE(dateFormat.format((Date) vehicle[7]));
					select.setTR_REQUIRED_DATE(dateFormat.format((Date) vehicle[8]));


					Dtos.add(select);
				}
			}
			valueOutObject	= new ValueObject();
			valueOutObject.put("InventoryTyreRetreadAmountList", Dtos);


			return	valueOutObject;
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}



	@Override
	public List<InventoryTyreAmountDto> getInventoryAmountDetailsToEdit(Long invoiceId , Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT A.ITYRE_AMD_ID, TM.TYRE_MODEL, TSM.TYRE_MODEL_SUBTYPE, VS.TYRE_SIZE, A.TYRE_QUANTITY, A.TOTAL_AMOUNT, PL.partlocation_name, A.InventoryTyreInvoice.ITYRE_ID,"
						+ " A.TYRE_MANUFACTURER_ID, A.TYRE_MODEL_ID, A.WAREHOUSE_LOCATION_ID, A.TYRE_SIZE_ID, A.UNIT_COST, A.DISCOUNT, A.TAX, A.TYRE_ASSIGN_NO "
						+ " From InventoryTyreAmount AS A  "
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = A.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = A.TYRE_MODEL_ID"
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = A.WAREHOUSE_LOCATION_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = A.TYRE_SIZE_ID "
						+ " WHERE A.InventoryTyreInvoice.ITYRE_ID = "+invoiceId+" AND A.COMPANY_ID = "+companyId+" AND A.markForDelete = 0 ) ",
						Object[].class);

		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreAmountDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreAmountDto>();
			InventoryTyreAmountDto list = null;

			for (Object[] result : results) {
				list = new InventoryTyreAmountDto();

				list.setITYRE_AMD_ID((Long) result[0]);
				list.setTYRE_MANUFACTURER((String) result[1]);
				list.setTYRE_MODEL((String) result[2]);
				list.setTYRE_SIZE((String) result[3]);
				list.setTYRE_QUANTITY((Double) result[4]);
				list.setTOTAL_AMOUNT((Double) result[5]);
				list.setWAREHOUSE_LOCATION((String) result[6]);
				list.setITYRE_ID( (Long) result[7]);
				list.setTYRE_MANUFACTURER_ID((Integer) result[8]);
				list.setTYRE_MODEL_ID((Integer) result[9]);
				list.setWAREHOUSE_LOCATION_ID((Integer) result[10]);
				list.setTYRE_SIZE_ID((Integer) result[11]);
				list.setUNIT_COST((Double) result[12]);
				list.setDISCOUNT((Double) result[13]);
				list.setTAX((Double) result[14]);
				list.setTYRE_ASSIGN_NO((Integer) result[15]);

				Dtos.add(list);
			}
		}

		return Dtos;

	}

	@Override
	@Transactional
	public void updateTyreInvoice(Long inventoryInvoice, boolean asigned) throws Exception {

		TyreInvoiceRepository.update_Inventory_Invoice(inventoryInvoice , asigned);
	}
	
	@Override
	public ValueObject getTyreRetreadInvoiceReport(ValueObject valueObject) throws Exception {
		String										dateRange				= null;
		int											tyreSizeId				= 0;
		int											vendorId				= 0;
		int											modelId					= 0;
		int											locationId				= 0;
		CustomUserDetails							userDetails				= null;
		List<InventoryTyreRetreadAmountDto> 		RetreadInvoiceReport	= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tyreSizeId		= valueObject.getInt("tyreSizeId", 0);
			vendorId		= valueObject.getInt("TyrePurchaseVendorId", 0);
			modelId			= valueObject.getInt("tyreModelId", 0);
			locationId		= valueObject.getInt("warehouselocationId", 0);
			dateRange		= valueObject.getString("dateRange");
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				
				String tyreSize = "", vendor = "", model = "", location = "", Date = "";
				
				if(tyreSizeId > 0)
				{
					tyreSize = " AND ITRA.TYRE_SIZE_ID = "+ tyreSizeId +" ";
				}
				
				if(vendorId > 0)
				{
					vendor = " AND ITR.TR_VENDOR_ID = "+ vendorId +" ";
				}
				
				if(modelId > 0)
				{
					model = " AND IT.TYRE_MODEL_ID = "+ modelId +" ";
				}
				
				if(locationId > 0)
				{
					location = " AND IT.WAREHOUSE_LOCATION_ID = "+ locationId +" ";
				}
				
				Date =  " AND ITR.TR_INVOICE_DATE between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;
				String query = " " + tyreSize + " " + vendor + " " + model + " " + location + " " + Date + " ";
				
				RetreadInvoiceReport = TyreDaoImpl.TyreRetreadInvoiceReportList(query, userDetails.getCompany_id());

			}
			valueObject.put("RetreadInvoiceReport", RetreadInvoiceReport);
			
			if(vendorId > 0 ) {
				if(RetreadInvoiceReport != null)
				valueObject.put("VendorName", RetreadInvoiceReport.get(0).getVENDOR_NAME());
			} else {
			valueObject.put("VendorName", "All");
			}
			
			if(tyreSizeId > 0 ) {
				if(RetreadInvoiceReport != null)
				valueObject.put("TyreSize", RetreadInvoiceReport.get(0).getTYRE_SIZE());
			} else {
				valueObject.put("TyreSize", "All");
			}
			
			if(modelId > 0 ) {
				if(RetreadInvoiceReport != null)
					valueObject.put("Model", RetreadInvoiceReport.get(0).getTYRE_MODEL_SUBTYPE());
			} else {
				valueObject.put("Model", "All");
			}
			
			if(locationId > 0 ) {
				if(RetreadInvoiceReport != null)
					valueObject.put("Location", RetreadInvoiceReport.get(0).getLOCATION());
			} else {
				valueObject.put("Location", "All");
			}
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			RetreadInvoiceReport		= null;
			userDetails					= null;
			dateRange					= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject updateTyreRetreadInvoice(ValueObject object) throws Exception {
		long 	TRID 					= object.getLong("TRID");
		int 	TR_VENDOR_ID 			= object.getInt("vendorId");
		short	TR_PAYMENT_TYPE_ID		= object.getShort("paymentTypeId");
		String	TR_PAYMENT_NUMBER 		= object.getString("paymentNumber");
		String 	TR_OPEN_DATEsrt 			= object.getString("retreadDate");
		String	TR_REQUIRED_DATEStr 	= object.getString("requiredRetreadDate");
		String 	TR_QUOTE_NO				= object.getString("quoteNo");
		String 	TR_MANUAL_NO			= object.getString("manualNo");
		
		SimpleDateFormat sdf1 = new SimpleDateFormat(DateTimeUtility.DD_MM_YY);

		
		Timestamp TR_REQUIRED_DATE = DateTimeUtility.getTimeStamp(TR_REQUIRED_DATEStr);
		Date TR_OPEN_DATE =sdf1.parse(TR_OPEN_DATEsrt);
		//String DateTimeUtility.DAY_END_TIME;
		CustomUserDetails userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		

		TyreInvoiceRepository.update_RetreadTyre_Invoice(TRID , TR_VENDOR_ID, TR_PAYMENT_TYPE_ID, TR_PAYMENT_NUMBER, TR_OPEN_DATE,
				TR_REQUIRED_DATE, TR_QUOTE_NO, TR_MANUAL_NO, DateTimeUtility.getCurrentTimeStamp(),userDetails.getId(),userDetails.getCompany_id()	);
		return object;
	}
	
	@Override
	public List<InventoryTyreRetreadDto> getAllRetreadTyreList(String term) throws Exception {//header Marquee Message
		CustomUserDetails 			userDetails			= null;
		List<InventoryTyreRetreadDto> 		Dtos 				= null;
		Integer 					companyId			= 0;
		TypedQuery<Object[]> 		query				= null;
		List<Object[]> 				results				= null;
		InventoryTyreRetreadDto 			inventoryTyreRetreadDto 	= null;
		
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			companyId	= userDetails.getCompany_id();
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			query = entityManager.createQuery(
					"SELECT I.TYRE_ID ,I.TYRE_NUMBER, P.pmName FROM InventoryTyre AS I "
							+ " INNER JOIN PartManufacturer as P ON P.pmid = I.TYRE_MANUFACTURER_ID"
							+ " WHERE I.COMPANY_ID ="+companyId+ " AND I.TYRE_RETREAD_COUNT = 1 AND I.TYRE_ASSIGN_STATUS_ID =1 "
							+ " AND lower(I.TYRE_NUMBER) like ('%" + term + "%')   and I.markForDelete = 0 " ,Object[].class);
				
			results = query.getResultList();
			
			if (results != null) {
			Dtos = new ArrayList<InventoryTyreRetreadDto>();
			
				for (Object[] result : results) {
					inventoryTyreRetreadDto = new InventoryTyreRetreadDto();
					if(result!= null) {
						inventoryTyreRetreadDto.setTRID((Long) result[0]);
						
						inventoryTyreRetreadDto.setTyre_Number((String)result[1]);
						
						inventoryTyreRetreadDto.setTyre_Manufacturer((String)result[2]);
						
						Dtos.add(inventoryTyreRetreadDto);
					}
				}
			}
		}
		}
		catch(Exception e) {
			throw e;
		}
		return Dtos;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject saveMultipleRetreadTyre(ValueObject valueObject) throws Exception {
	ArrayList<ValueObject> dataArrayObjColl = null;
	List<InventoryTyreRetreadAmount> expenseTransfer	= null;
	Integer 					companyId			= 0;
	CustomUserDetails 			userDetails			= null;
	double	amnt	= 0.0;
	try {
		
		
		InventoryTyreRetread obj = new InventoryTyreRetread();
		obj.setTRID(valueObject.getLong("TRID"));
		valueObject.put("invetoryTyreRetreadId", obj);
		dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("finalRetreadObject");
	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	companyId	= userDetails.getCompany_id();
	if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
	expenseTransfer = new ArrayList<InventoryTyreRetreadAmount>();
	
	/*InventoryTyreDto Tyre =Get_Only_TYRE_ID_And_TyreSize(Long.parseLong(TyreIDSelect), TyreRetread.getCOMPANY_ID());

	InventoryTyreRetreadAmount TyreRetreadAmount = prepare_RetreadAmount_Tyre(Tyre);

	TyreRetreadAmount.setInventoryTyreRetread(TyreRetread);

	// Create Sent Retread Details TyreRetread Entries
	iventoryTyreService.add_Inventory_Retread_Tyre_Amount(TyreRetreadAmount);*/
	
	for(ValueObject chargeTransfer : dataArrayObjColl) {
	
		
	InventoryTyreDto Tyre =Get_Only_TYRE_ID_And_TyreSize(chargeTransfer.getLong("retreadTyreId"), companyId);	
	
	
	expenseTransfer.add(ITBL.saveInventoryTyreRetreadAmount(chargeTransfer,Tyre,valueObject));
	
	updateTyreRetreadStatus(chargeTransfer.getLong("retreadTyreId"));
	
	
	amnt += chargeTransfer.getDouble("totalId");
	}

	TyreRetreadAmountRepository.saveAll(expenseTransfer);

	updateInventoryTyreRetread(amnt, valueObject.getLong("TRID"));
	
	

	}

	return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
	throw e;
	}finally {
	dataArrayObjColl	= null;
	expenseTransfer	   = null;
	}
	}
	
	
	@Override
	@Transactional
	public void updateInventoryTyreRetread(double amount, long id) throws Exception {
	CustomUserDetails	userDetails	= null;
	java.util.Date lastModifiedOn ;
	try {
	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	lastModifiedOn = new java.util.Date();
	Timestamp sqlDate = new java.sql.Timestamp(lastModifiedOn.getTime());
	entityManager.createQuery(
	" UPDATE InventoryTyreRetread AS IT SET IT.balanceAmount = IT.balanceAmount + "+amount+", "
	+ " IT.TR_AMOUNT = IT.TR_AMOUNT + "+amount+", IT.TR_ROUNT_AMOUNT = IT.TR_ROUNT_AMOUNT + "+amount+", "
	+ " IT.LASTUPDATED_DATE = '"+sqlDate+"', IT.LASTMODIFIEDBYID = "+userDetails.getId()+" "
	+ " WHERE IT.TRID = "+id+" "
	+ " AND IT.markForDelete = 0 ")
	.executeUpdate();

	}catch(Exception e) {
	throw e;
	}
	}
	@Override
	@Transactional
	public void updateTyreRetreadStatus(long id) throws Exception {
		try {
			entityManager.createQuery(
			" UPDATE InventoryTyre AS IT SET IT.TYRE_ASSIGN_STATUS_ID = "+InventoryTyreDto.TYRE_ASSIGN_STATUS_SENT_RETREAD+" "
			+ " WHERE IT.TYRE_ID = "+id+" "
			+ " AND IT.markForDelete = 0 ")
			.executeUpdate();

			}catch(Exception e) {
			throw e;
		}
	}
	


@Override
public long getInventoryTyreCountByStatus(CustomUserDetails userDetails ,short tyreStatusId) throws Exception {
	HashMap<String, Object> 	configuration	= null;
	try {
		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
		if((boolean)configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
			return TyreRepository.getInventoryTyreCountByStatus(userDetails.getCompany_id(),tyreStatusId,userDetails.getId());
		} else {
			return TyreRepository.getInventoryTyreCountByStatus(userDetails.getCompany_id(),tyreStatusId);
		}
	} catch (Exception e) {
		return 0;
	}
}


@Override
public long getLocationWiseInventoryTyreCount(CustomUserDetails userDetails ,short tyreStatusAvailableId,Integer locationId) throws Exception {
	try {
		return TyreRepository.getLocationWiseInventoryTyreCount(userDetails.getCompany_id(),tyreStatusAvailableId,locationId);
	} catch (Exception e) {
		return 0;
	}
}


public Map<Integer, List<InventoryTyreHistoryDto>> getAllTyreAssignmentDetails(String startDate, String endDate) throws Exception {
	TypedQuery<Object[]> 									typedQuery  	= null;
	Map<Integer, List<InventoryTyreHistoryDto>>				companyHM		= null;
	List<InventoryTyreHistoryDto>							companyLIst 	= null;
	try {
		typedQuery = entityManager.createQuery(
				" Select IH.TYRE_ID, VM.TYRE_MODEL, IH.TYRE_NUMBER, IH.VEHICLE_ID, V.vehicle_registration, IH.POSITION, IH.AXLE, "
				+ " IH.TYRE_ASSIGN_DATE, IH.OPEN_ODOMETER, IH.COMPANY_ID, IH.TYRE_COMMENT, IH.TYRE_STATUS_ID, IH.PRE_TYRE_ASSIGN_DATE, "
				+ " IH.PRE_TYRE_NUMBER, IH.PRE_OPEN_ODOMETER "
				+ " FROM InventoryTyreHistory IH "
				+ " INNER JOIN InventoryTyre IT on IT.TYRE_ID = IH.TYRE_ID "
				+ " INNER JOIN VehicleTyreModelType VM on VM.TYRE_MT_ID = IT.TYRE_MANUFACTURER_ID "
				+ " INNER JOIN Vehicle as V on V.vid = IH.VEHICLE_ID "
				+ " WHERE IH.markForDelete = 0 "
				+ " AND IH.TYRE_ASSIGN_DATE BETWEEN '"+startDate+"' AND '"+endDate+"'  "
				,Object[].class);
		
		List<Object[]> results = typedQuery.getResultList();
		if (results != null && !results.isEmpty()) {
			
			companyHM	= new HashMap<Integer, List<InventoryTyreHistoryDto>>();
			
			InventoryTyreHistoryDto	inventoryTyreHistoryDto = null;
			for (Object[] result : results) {
				inventoryTyreHistoryDto	= new InventoryTyreHistoryDto();
				inventoryTyreHistoryDto.setTYRE_ID((Long) result[0]);
				inventoryTyreHistoryDto.setTyreModel((String) result[1]);
				inventoryTyreHistoryDto.setTYRE_NUMBER((String) result[2]);
				inventoryTyreHistoryDto.setVEHICLE_ID((Integer) result[3]);
				inventoryTyreHistoryDto.setVEHICLE_REGISTRATION((String) result[4]);
				inventoryTyreHistoryDto.setPOSITION((String) result[5]);
				inventoryTyreHistoryDto.setAXLE((String) result[6]);
				inventoryTyreHistoryDto.setTYRE_ASSIGN_DATE(SQLdate.format((Timestamp)result[7]));
				inventoryTyreHistoryDto.setOPEN_ODOMETER((Integer)result[8]);
				inventoryTyreHistoryDto.setCompanyId((Integer)result[9]);
				inventoryTyreHistoryDto.setTYRE_COMMENT((String)result[10]);
				inventoryTyreHistoryDto.setTYRE_STATUS_ID((short)result[11]);
				inventoryTyreHistoryDto.setTYRE_STATUS(InventoryTyreHistoryDto.getStatusName((short)result[11]));
				if(result[12] != null) {
				inventoryTyreHistoryDto.setPRE_TYRE_ASSIGN_DATEStr(SQLdate.format((Timestamp)result[12]));
				}else {
					inventoryTyreHistoryDto.setPRE_TYRE_ASSIGN_DATEStr("-");
				}
				if(result[13] != null) {
				inventoryTyreHistoryDto.setPRE_TYRE_NUMBER((String)result[13]);
				}else {
					inventoryTyreHistoryDto.setPRE_TYRE_NUMBER("-");
				}
				if(result[14] != null) {
				inventoryTyreHistoryDto.setPRE_OPEN_ODOMETER((Integer)result[14]);
				}else {
					inventoryTyreHistoryDto.setPRE_OPEN_ODOMETER(0);
				}
				companyLIst	= companyHM.get(inventoryTyreHistoryDto.getCompanyId());
				
				if(companyLIst == null) {
					companyLIst	= new ArrayList<>();
				}
				companyLIst.add(inventoryTyreHistoryDto);
				
				companyHM.put(inventoryTyreHistoryDto.getCompanyId(), companyLIst);
			
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

@Transactional
public InventoryTyreHistoryDto getPreTyreMountDismountHistory(long tyreID, InventoryTyreHistory inventoryTyreHistory,short tyreStatusId) throws Exception {
	try {
		Query queryt = entityManager.createQuery(
				" SELECT TH.TYRE_HIS_ID, TH.TYRE_NUMBER, TH.TYRE_ASSIGN_DATE, TH.OPEN_ODOMETER FROM InventoryTyreHistory AS TH "
						+ " WHERE  TH.VEHICLE_ID = "+inventoryTyreHistory.getVEHICLE_ID()+" AND TH.TYRE_STATUS_ID = "+tyreStatusId+" "
						+ " AND TH.POSITION = '"+inventoryTyreHistory.getPOSITION()+"' AND TH.AXLE ="+inventoryTyreHistory.getAXLE()+" "
						+ " AND TH.COMPANY_ID = "+ inventoryTyreHistory.getCOMPANY_ID() +" AND TH.markForDelete = 0 ORDER BY TH.TYRE_HIS_ID desc ");
						queryt.setMaxResults(1);
		Object[] result = null;
		InventoryTyreHistoryDto select  = new InventoryTyreHistoryDto();
		try {
			result = (Object[]) queryt.getSingleResult();
			
		} catch (NoResultException nre) {
			
			return null;
		}
		
		if (result != null) {
			
			select.setTYRE_HIS_ID((Long)result[0]);
			select.setTYRE_NUMBER((String)result[1]);
			select.setTYRE_ASSIGN_DATE_TIMESTAMP((Timestamp)result[2]);
			select.setOPEN_ODOMETER((Integer)result[3]);
		}
		return select;
		} catch (Exception e) {
			LOGGER.error("Excep", e);
			throw e;
		}
	}

@Transactional
public InventoryTyreHistoryDto getPreTyreRotationHistory(long tyreID, InventoryTyreHistory inventoryTyreHistory,short tyreStatusId) throws Exception {
	try {
		Query queryt = entityManager.createQuery(
				" SELECT TH.TYRE_HIS_ID, TH.TYRE_NUMBER, TH.TYRE_ASSIGN_DATE, TH.OPEN_ODOMETER FROM InventoryTyreHistory AS TH "
						+ " WHERE  TH.VEHICLE_ID = "+inventoryTyreHistory.getVEHICLE_ID()+" AND TH.TYRE_ID ="+tyreID+" AND TH.TYRE_STATUS_ID = "+tyreStatusId+" "
						+ " AND TH.COMPANY_ID = "+ inventoryTyreHistory.getCOMPANY_ID() +" AND TH.markForDelete = 0 ORDER BY TH.TYRE_HIS_ID desc ");
						queryt.setMaxResults(1);
		Object[] result = null;
		InventoryTyreHistoryDto select  = new InventoryTyreHistoryDto();
		try {
			result = (Object[]) queryt.getSingleResult();
			
		} catch (NoResultException nre) {
			
			return null;
		}
		
		if (result != null) {
			
			select.setTYRE_HIS_ID((Long)result[0]);
			select.setTYRE_NUMBER((String)result[1]);
			select.setTYRE_ASSIGN_DATE_TIMESTAMP((Timestamp)result[2]);
			select.setOPEN_ODOMETER((Integer)result[3]);
		}
		return select;
		} catch (Exception e) {
			LOGGER.error("Excep", e);
			throw e;
		}
	}

	@Override
	public ValueObject getVehicleTyreAssignmentHistoryReport(ValueObject valueObject) throws Exception {
		
		Integer 								vid						= 0;
		String 									dateRange				= "";	
		String									startDate				= "";
		String									endDate					= "";
		String 									vehicleId 				= "";
		CustomUserDetails						userDetails				= null;
		List<InventoryTyreHistoryDto> 			Tyre			        = null;
		ValueObject						        tableConfig				= null;
		
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vid				= valueObject.getInt("vid", 0);
			startDate		= valueObject.getString("startDate","");
			endDate			= valueObject.getString("endDate","");
			
			startDate	= DateTimeUtility.getSqlStrDateFromStrDate(startDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			endDate	= DateTimeUtility.getSqlStrDateFromStrDate(endDate.trim()+DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			
			if(vid>0) {
			
				if(startDate != "" && endDate != "") {
					dateRange		= "AND ITH.TYRE_ASSIGN_DATE BETWEEN '"+startDate+"' AND '"+endDate+"' ";	
					
				}
				
			
				vehicleId = " ITH.VEHICLE_ID = " + vid +" ";
			 
				String query = " "+vehicleId+" "+dateRange+" ";
				
					
				tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.VEHICLE_TYRE_ASSIGNMENT_HISTORY_REPORT_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					
				Tyre = TyreDaoImpl.getVehicleTyreAssignmentHistoryReportList(query, userDetails.getCompany_id());
				}
				
				valueObject.put("Tyre", Tyre);
				valueObject.put("tableConfig", tableConfig.getHtData());
				valueObject.put("company",
				userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
			Tyre				= null;
			userDetails			= null;
		}
	}

	@Transactional
	public void Update_TyreDocument_ID_to_Tyre(Long fueldocid, boolean b, Long fuel_id, Integer companyId) {
		TyreInvoiceRepository.Update_TyreDocument_ID_to_Tyre(fueldocid, b, fuel_id, companyId);

	}
	
	@Override
	public ValueObject getTyreCountListDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails	   					userDetails				= null;
		Integer				   					locationId				= null;
		short				   					status					= 0;
		List<InventoryTyreDto>					tyreCountList			= null;
		String 									query					= "";
		Integer				   					pageNumber				= null;
		Page<InventoryTyre> 					page					= null;
		try {
			pageNumber	 = valueObject.getInt("pageNo",0);
			
			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			status				= valueObject.getShort("status");
			locationId			= valueObject.getInt("locationId");

			if(locationId > 0) {
				query	= " R.COMPANY_ID ="+userDetails.getCompany_id()+"  AND R.TYRE_ASSIGN_STATUS_ID ="+status+" AND R.WAREHOUSE_LOCATION_ID="+locationId+" AND R.markForDelete = 0";
/*loctionWise*/	page 	= getDeployment_Page_ShowInLocationWiseInventoryTyreList(pageNumber, status,locationId, userDetails.getCompany_id());
			}else {
				page 	= getDeployment_Page_ShowInInventoryTyreList(pageNumber, status,userDetails.getCompany_id());
				query	= " R.COMPANY_ID ="+userDetails.getCompany_id()+"  AND R.TYRE_ASSIGN_STATUS_ID ="+status+"  AND R.markForDelete = 0";
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
			
			tyreCountList = getTyreCountList(query,pageNumber);
			

			valueObject.put("TyreCountList", tyreCountList);
		//	valueObject.put("SelectPage", pageNumber);
		
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			 userDetails				= null;
		}
	}
	
	@Override
	public List<InventoryTyreDto> getTyreCountList(String query, Integer pageNumber) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.TYRE_ID, R.TYRE_NUMBER, TM.TYRE_MODEL, TSM.TYRE_MODEL_SUBTYPE, PL.partlocation_name, VS.TYRE_SIZE, R.TYRE_AMOUNT, R.TYRE_ASSIGN_STATUS_ID, R.ITYRE_INVOICE_ID, R.TYRE_USEAGE, R.TYRE_RETREAD_COUNT,"
				+ " R.TYRE_IN_NUMBER, R.OPEN_ODOMETER, V.vehicle_Odometer,R.WAREHOUSE_LOCATION_ID "
						+ "FROM InventoryTyre AS R "
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = R.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = R.TYRE_MODEL_ID"
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID "
						+ " LEFT JOIN Vehicle V ON V.vid = R.VEHICLE_ID "
						+ " WHERE "+query+" ORDER BY R.TYRE_ID desc",
						Object[].class);
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyreDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryTyreDto>();
			InventoryTyreDto list = null;
			for (Object[] result : results) {
				list = new InventoryTyreDto();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER((String) result[1]);
				list.setTYRE_MANUFACTURER((String) result[2]);
				list.setTYRE_MODEL((String) result[3]);
				list.setWAREHOUSE_LOCATION((String) result[4]);
				list.setTYRE_SIZE((String) result[5]);
				list.setTYRE_AMOUNT((Double) result[6]);
				list.setTYRE_ASSIGN_STATUS_ID((short) result[7]);
				list.setTYRE_ASSIGN_STATUS(InventoryTyreDto.getPaymentModeName((short) result[7]));
				list.setITYRE_INVOICE_ID((Long) result[8]);
				list.setTYRE_USEAGE((Integer) result[9]);
				list.setTYRE_RETREAD_COUNT((Integer) result[10]);
				list.setTYRE_IN_NUMBER((Long) result[11]);
				list.setOPEN_ODOMETER((Integer) result[12]);
				list.setVEHICLE_ODOMETER((Integer) result[13]);
				
				if(list.getTYRE_ASSIGN_STATUS_ID() == InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE) {
					if(list.getTYRE_USEAGE() != null && list.getVEHICLE_ODOMETER() != null && list.getOPEN_ODOMETER() != null) {
						list.setTYRE_USEAGE(list.getTYRE_USEAGE() + (list.getVEHICLE_ODOMETER() - list.getOPEN_ODOMETER()));
						}
					}
				list.setWAREHOUSE_LOCATION_ID((Integer) result[14]);
				
				Dtos.add(list);
			}
		}

		return Dtos;
	}
	
	public Page<InventoryTyre> getDeployment_Page_ShowInLocationWiseInventoryTyreList(Integer pageNumber,short status, Integer locationId, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "TYRE_ID");
		return TyreRepository.getDeployment_Page_ShowInLocationWiseInventoryTyreList(companyId,status,locationId, pageable);
	}
	
	public Page<InventoryTyre> getDeployment_Page_ShowInInventoryTyreList(Integer pageNumber,short status, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "TYRE_ID");
		return TyreRepository.getDeployment_Page_ShowInInventoryTyreList(companyId,status, pageable);
	}
	
	
	@Transactional// Tyre Approval Approved Here
	public void updatePaymentApprovedTyreDetails(Long ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId) throws Exception {
		entityManager.createQuery("  UPDATE InventoryTyreInvoice SET TYRE_APPROVAL_ID=" + Approval_ID
				+ ",expectedPaymentDate='" + expectedPaymentDate + "', VENDOR_PAYMODE_STATUS_ID=' " + approval_Status+ "'"
				+ " WHERE ITYRE_ID IN (" + ApprovalInvoice_ID + ") AND COMPANY_ID = "+companyId+" ").executeUpdate();
	}
	
	@Transactional// Tyre Approval Paid Here
	public void updatePaymentPaidTyreDetails(String ApprovalInvoice_ID, Timestamp approval_date, Integer companyId, short VENDOR_PAYMODE_STATUS_ID) throws Exception {
		
		entityManager.createQuery("UPDATE InventoryTyreInvoice SET VENDOR_PAYMODE_DATE='" + approval_date+"', VENDOR_PAYMODE_STATUS_ID="+VENDOR_PAYMODE_STATUS_ID+"  WHERE ITYRE_ID IN (" + ApprovalInvoice_ID + ") AND COMPANY_ID = "+companyId+" ").executeUpdate();
	}
	
	@Transactional// RetreadTyre Approval Approved Here
	public void updatePaymentApprovedTyreReteadDetails(Long ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId) throws Exception {
		entityManager.createQuery(" UPDATE From InventoryTyreRetread SET TR_APPROVAL_ID=" + Approval_ID
				+ ", expectedPaymentDate='" + expectedPaymentDate + "', TR_VENDOR_PAYMODE_STATUS_ID=' " + approval_Status+"' "
				+ " WHERE TRID IN (" + ApprovalInvoice_ID + ") AND COMPANY_ID = "+companyId+" ").executeUpdate();
	}
	
	@Transactional// Retread Tyre Approval Paid Here
	public void updatePaymentPaidTyreRetreadDetails(String ApprovalInvoice_ID, Timestamp approval_date, Integer companyId, short TR_VENDOR_PAYMODE_STATUS_ID) throws Exception {
		
		entityManager.createQuery("UPDATE InventoryTyreRetread SET TR_VENDOR_PAYMODE_DATE='" + approval_date+"', TR_VENDOR_PAYMODE_STATUS_ID= "+TR_VENDOR_PAYMODE_STATUS_ID+"  WHERE TRID IN (" + ApprovalInvoice_ID + ") AND COMPANY_ID = "+companyId+" ").executeUpdate();
	}
	
	@Override
	@Transactional
	public void saveTyreExpenseDetails(TyreExpenseDetails tyreExpenseDetails) throws Exception {
		TyreExpenseDetailsRepository.save(tyreExpenseDetails);
	}
	
	@Transactional
	public void deleteTyreExpensedetails(Long Tyre_id, Integer companyId) throws Exception {

		TyreExpenseDetailsRepository.deleteTyreExpensedetails(Tyre_id, companyId);
	}
	@Override
	public InventoryTyreRetreadAmount getRetreadTyreAmountDetailsByTR_AMOUNT_ID(Long tr_AMOUNT_ID) throws Exception {
		InventoryTyreRetreadAmount			inventoryTyreRetreadAmount					= null;
		try {
			inventoryTyreRetreadAmount 	= TyreRetreadAmountRepository.getRetreadTyreAmountDetailsByTR_AMOUNT_ID(tr_AMOUNT_ID);
			
			return inventoryTyreRetreadAmount;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			inventoryTyreRetreadAmount					= null;
		}
	}
	
	@Override
	public ValueObject getAllTyreListByStatus(String search, String status) throws Exception {
		CustomUserDetails			userDetails						= null;
		List<InventoryTyreDto>		inventoryTyreDtoList			= null;
		InventoryTyreDto 			inventoryTyreDto 				= null;
		ValueObject 				valueObject						= null;
		TypedQuery<Object[]> 		query							= null;
		List<Object[]> 				results							= null;	
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject					= new ValueObject();
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			query = entityManager.createQuery(
					"SELECT I.TYRE_ID ,I.TYRE_NUMBER FROM InventoryTyre AS I "
							+ " WHERE I.COMPANY_ID ="+userDetails.getCompany_id()+ " AND I.TYRE_ASSIGN_STATUS_ID ="+status+" "
							+ " AND lower(I.TYRE_NUMBER) like ('%" + search + "%')   and I.markForDelete = 0 " ,Object[].class);
			
			query.setFirstResult(0);
			query.setMaxResults(20);
			
			results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				inventoryTyreDtoList = new ArrayList<InventoryTyreDto>();
				
				for (Object[] result : results) {
					inventoryTyreDto = new InventoryTyreDto();
					inventoryTyreDto.setTYRE_ID((Long) result[0]);
					inventoryTyreDto.setTYRE_NUMBER((String) result[1]);
					inventoryTyreDtoList.add(inventoryTyreDto);
				}
			}
			
			valueObject.put("tyreList", inventoryTyreDtoList);
			}
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;    
			inventoryTyreDtoList			= null;    
			inventoryTyreDto 				= null;    
			valueObject						= null;    
			query							= null;    
			results							= null;	   
		}
	}
	
	@Override
	public List<InventoryTyreDto> getTyreSoldDetails(String tyreId , short status) throws Exception {
		CustomUserDetails			userDetails						= null;
		List<Object[]> 				results							= null;	
		String						tyreidStr						= "";
		String						statusStr						= "";
		String						finalQuery						= "";
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		/*	tyreId 						= valueObject.getString("tyreId");
			status						= valueObject.getString("status");
			*/
			if(tyreId != "") {
				tyreidStr = "AND R.TYRE_ID IN ("+tyreId+") ";
			}if(status > 0) {
				statusStr ="AND R.TYRE_ASSIGN_STATUS_ID = "+status+"";
			}
			
			finalQuery =""+tyreidStr+" "+statusStr+"";
			System.err.println("finalQuery"+finalQuery);
			
			TypedQuery<Object[]> queryt = entityManager.createQuery(
					"SELECT R.TYRE_ID, R.TYRE_NUMBER, TM.TYRE_MODEL, TSM.TYRE_MODEL_SUBTYPE, PL.partlocation_name, VS.TYRE_SIZE, R.TYRE_AMOUNT, R.TYRE_ASSIGN_STATUS_ID, R.TYRE_USEAGE "
							+ " FROM InventoryTyre AS R "
							+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = R.TYRE_MANUFACTURER_ID"
							+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = R.TYRE_MODEL_ID"
							+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID"
							+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID "
							+ " WHERE R.COMPANY_ID = "+userDetails.getCompany_id()+" " + finalQuery + "  AND R.markForDelete = 0",
							Object[].class);
			queryt.setFirstResult(0);
			queryt.setMaxResults(100);
			 results = queryt.getResultList();

			List<InventoryTyreDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryTyreDto>();
				InventoryTyreDto list = null;
				for (Object[] result : results) {
					list = new InventoryTyreDto();

					list.setTYRE_ID((Long) result[0]);
					list.setTYRE_NUMBER((String) result[1]);
					list.setTYRE_MANUFACTURER((String) result[2]);
					list.setTYRE_MODEL((String) result[3]);
					list.setWAREHOUSE_LOCATION((String) result[4]);
					list.setTYRE_SIZE((String) result[5]);
					list.setTYRE_AMOUNT((Double) result[6]);
					list.setTYRE_ASSIGN_STATUS_ID((short) result[7]);
					list.setTYRE_ASSIGN_STATUS(InventoryTyreDto.getPaymentModeName((short) result[7]));
					list.setTYRE_USEAGE((Integer) result[8]);
					Dtos.add(list);
				}
			}
			
			
			return Dtos;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;    
			results							= null;	   
		}
	}
	@Transactional
	@Override
	public ValueObject saveTyreSoldInvoice(ValueObject valueObject) throws Exception {
		CustomUserDetails					userDetails					= null;
		TyreSoldInvoiceDetails				tyreSoldInvoiceDetails		= null;
		TyreSoldDetails						tyreSoldDetailsBL			= null;
		List<TyreSoldDetails>				savedTyreSoldDetails		= null;
		ArrayList<TyreSoldDetails>			tyreSoldDetailsList			= null;
		TyreSoldInvoiceSequenceCounter		sequenceCounter	 			= null;
		ValueObject							valueOutObject				= null;
		TyreSoldInvoiceDetails 				savedTyreSoldInvoiceDetails	= null;
		List<String> 						tyreList 					= null;
		try {
			tyreSoldDetailsList			= new ArrayList<TyreSoldDetails>();
			valueOutObject 				= new ValueObject();
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			sequenceCounter	 = tyreSoldInvoiceSequenceService.findNextInvoiceNumber(userDetails.getCompany_id());
			if(sequenceCounter == null) {
				valueOutObject.put("sequenceNotFound", true);
				return valueOutObject;
			}
			tyreSoldInvoiceDetails = ITBL.prepareTyreSoldInvoice(valueObject, userDetails);
			tyreSoldInvoiceDetails.setTyreSoldInvoiceNumber("TSI-"+sequenceCounter.getNextVal());
			savedTyreSoldInvoiceDetails = tyreSoldInvoiceDetailsRepository.save(tyreSoldInvoiceDetails);
			
			tyreList = Arrays.asList(valueObject.getString("tyreId").split(","));
			
				for(String soldTyreId : tyreList) {
					valueObject.put("soldTyreId", soldTyreId);
					valueObject.put("soldTyreInvoiceId", savedTyreSoldInvoiceDetails.getTyreSoldInvoiceId());
					tyreSoldDetailsBL = ITBL.prepareTyreSoldDetails(valueObject, userDetails);
					
					tyreSoldDetailsList.add(tyreSoldDetailsBL);
				}
				savedTyreSoldDetails = tyreSoldDetailsRepository.saveAll(tyreSoldDetailsList);
				valueOutObject.put("tyreSoldInvoiceId", savedTyreSoldDetails.get(0).getTyreSoldInvoiceId());
				if(valueObject.getShort("soldType") == TyreTypeConstant.AVAILABLE_TO_SOLD) {
				updateTyreStatusByTyreId(valueObject.getString("tyreId"), InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_PROCESS);
			}else {
				updateTyreStatusByTyreId(valueObject.getString("tyreId"), InventoryTyreDto.TYRE_ASSIGN_STATUS_SOLD);
				valueOutObject.put("tyreSoldInvoiceId", savedTyreSoldInvoiceDetails.getTyreSoldInvoiceId());
				
			}
		//	tyreSoldDetails = ITBL.prepareTyreSoldDetails(valueObject, userDetails);
			
			
			valueOutObject.put("save", true);
			return valueOutObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;  
			tyreSoldInvoiceDetails					= null;  
		}
	}
	
	@Override
	@Transactional
	public void updateTyreStatusByTyreId(String tyreId, short tyreAssignStatusSold) throws Exception {
		try {
			entityManager.createQuery(
			" UPDATE InventoryTyre AS IT SET IT.TYRE_ASSIGN_STATUS_ID = "+tyreAssignStatusSold+" "
			+ " WHERE IT.TYRE_ID IN("+tyreId+") "
			+ " AND IT.markForDelete = 0 ")
			.executeUpdate();

			}catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getTyreSoldDetailsByTyreSoldInvoiceId(ValueObject valueInObject) throws Exception {
		CustomUserDetails				userDetails						= null;
		TyreSoldInvoiceDetails 			tyreSoldInvoiceDetails			= null; 
		ValueObject 					valueOutObject					= null;
		List<Object[]> 					results							= null;	
		try {
			valueOutObject = new ValueObject();
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
			TypedQuery<Object[]> queryt = entityManager.createQuery(
					"SELECT ITS.tyreId, IT.TYRE_NUMBER,VS.TYRE_SIZE,ITS.discount, ITS.gst, ITS.tyreSoldAmount, ITS.tyreSoldNetAmount"
							+ " FROM TyreSoldDetails AS ITS "
							+ " INNER JOIN InventoryTyre IT ON IT.TYRE_ID = ITS.tyreId "
							+ " INNER JOIN VehicleTyreSize VS ON VS.TS_ID = IT.TYRE_SIZE_ID "
							+ " WHERE ITS.companyId = "+userDetails.getCompany_id()+" AND ITS.tyreSoldInvoiceId="+valueInObject.getLong("invoiceId")+"  AND ITS.markForDelete = 0",
							Object[].class);
			results = queryt.getResultList();
			List<InventoryTyreDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryTyreDto>();
				InventoryTyreDto list = null;
				for (Object[] result : results) {
					list = new InventoryTyreDto();

					list.setTYRE_ID((Long) result[0]);
					list.setTYRE_NUMBER((String) result[1]);
					list.setTYRE_SIZE((String) result[2]);
					list.setDiscount((Double) result[3]);
					list.setGst((Double) result[4]);
					list.setTYRE_AMOUNT((Double) result[5]);
					list.setTyreNetAmount((Double) result[6]);
					Dtos.add(list);
				}
			}
			valueOutObject.put("tyreSoldDetails", Dtos);
			
			tyreSoldInvoiceDetails		= tyreSoldInvoiceDetailsRepository.findByTyreSoldInvoiceId(valueInObject.getLong("invoiceId"), userDetails.getCompany_id());
			valueOutObject.put("soldDate", SQLdate.format(tyreSoldInvoiceDetails.getTyreSoldInvoiceDate()));
			valueOutObject.put("soldInvoiceNumber", tyreSoldInvoiceDetails.getTyreSoldInvoiceNumber());
			valueOutObject.put("tyreStatus", InventoryTyreDto.getPaymentModeName(tyreSoldInvoiceDetails.getTyreStatus()));
			
			valueOutObject.put("tyreSoldInvoiceDetails", tyreSoldInvoiceDetails); // for Scrap to sold (complete Status)
			
			
			return valueOutObject;
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void saveSoldTyreCost(ValueObject valuObject) throws Exception {
		CustomUserDetails		userDetails		= null;
		long 					tyreId 			= 0;
		double 					comission 		= 0;
		double 					gst 			= 0;
		double 					soldAmount 		= 0;
		double 					soldNetAmount 	= 0;
		
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tyreId 			= valuObject.getLong("tyreId",0);
			comission 		= valuObject.getDouble("comission",0);
			gst 			= valuObject.getDouble("gst",0);
			soldAmount 		= valuObject.getDouble("soldAmount",0);
			soldNetAmount	= valuObject.getDouble("soldNetAmount",0);
			
			
			tyreSoldDetailsRepository.saveSoldTyreCost(comission,gst,soldAmount,soldNetAmount,tyreId,userDetails.getCompany_id());

			}catch(Exception e) {
			throw e;
		}
	}
	
	
	@Override
	@Transactional
	public void updateTyreSoldInvoiceDetils(ValueObject valuObject) throws Exception {
		CustomUserDetails		userDetails					= null;
		long 					tyreSoldInvoiceId 			= 0;
		double 					tyreSoldInvoiceAmount 		= 0;
		short 					status 						= 0;
		String					description					= "";
		
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tyreSoldInvoiceId 			= valuObject.getLong("tyreSoldInvoiceId",0);
			tyreSoldInvoiceAmount 		= valuObject.getDouble("tyreSoldInvoiceAmount",0);
			description 				= valuObject.getString("description");
			status 						= InventoryTyreDto.TYRE_ASSIGN_STATUS_SOLD;
			
			tyreSoldInvoiceDetailsRepository.updateTyreSoldInvoiceDetils(tyreSoldInvoiceAmount,status,description,tyreSoldInvoiceId,userDetails.getCompany_id());
			updateTyreStatusByTyreId(valuObject.getString("tyreArray"), InventoryTyreDto.TYRE_ASSIGN_STATUS_SOLD);
			
			}catch(Exception e) {
			throw e;
		}
	}
	
	public Page<TyreSoldInvoiceDetails> getDeployment_Page_TyreSoldInvoiceDetails(Integer pageNumber, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "tyreSoldInvoiceId");
		return tyreSoldInvoiceDetailsRepository.getDeployment_Page_TyreSoldInvoiceDetails(companyId, pageable);
	}

	
	@Override
	public ValueObject getPageWiseTyreSoldInvoiceDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails	  		 		userDetails						= null;
		Integer				   				pageNumber						= null;
		List<TyreSoldInvoiceDetailsDto> 	TyreSoldInvoiceDetailsDtoList		= null;
		try {

			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber			= valueObject.getInt("pageNumber",0);
			Page<TyreSoldInvoiceDetails> page = getDeployment_Page_TyreSoldInvoiceDetails(pageNumber, userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);
			valueObject.put("invoiceCount", page.getTotalElements());

			TyreSoldInvoiceDetailsDtoList = getTyreSoldInvoiceList(pageNumber, userDetails.getCompany_id());
			valueObject.put("tyreSoldInvoiceList", TyreSoldInvoiceDetailsDtoList);
			valueObject.put("SelectPage", pageNumber);
		
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			 userDetails				= null;
		}
	}
	
	@Override
	public List<TyreSoldInvoiceDetailsDto> getTyreSoldInvoiceList(Integer pageNumber, Integer companyId) throws Exception {

		TypedQuery<Object[]> 				typedQuery 						= null;
		List<Object[]> 						resultList 						= null; 
		List<TyreSoldInvoiceDetailsDto> 	tyreSoldInvoiceDetailsList 		= null;
		TyreSoldInvoiceDetailsDto 			tyreSoldInvoiceDetailsDto		= null;
		
		try {

			typedQuery = entityManager.createQuery(
					"SELECT TIS.tyreSoldInvoiceId, TIS.tyreSoldInvoiceNumber,TIS.tyreSoldInvoiceDate,TIS.tyreSoldInvoiceNetAmount, U.firstName,TIS.soldType"
							+ " FROM TyreSoldInvoiceDetails AS TIS "
							+ " INNER JOIN User U ON U.id = TIS.createdById "
							+ " WHERE TIS.companyId = "+companyId+"  AND TIS.markForDelete = 0",
							Object[].class);

			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				tyreSoldInvoiceDetailsList = new ArrayList<TyreSoldInvoiceDetailsDto>();

				for (Object[] result : resultList) {
					tyreSoldInvoiceDetailsDto = new TyreSoldInvoiceDetailsDto();
					
					tyreSoldInvoiceDetailsDto.setTyreSoldInvoiceId((Long) result[0]);
					tyreSoldInvoiceDetailsDto.setTyreSoldInvoiceNumber((String) result[1]);
					tyreSoldInvoiceDetailsDto.setTyreSoldInvoiceDateStr(SQLdate.format((Timestamp)result[2]));
					tyreSoldInvoiceDetailsDto.setTyreSoldInvoiceNetAmount((Double) result[3]);
					tyreSoldInvoiceDetailsDto.setCreatedBy((String) result[4]);
					tyreSoldInvoiceDetailsDto.setSoldTypeStr(TyreTypeConstant.getTyreStatus((short) result[5]));
					tyreSoldInvoiceDetailsList.add(tyreSoldInvoiceDetailsDto);
				}
			}

			return tyreSoldInvoiceDetailsList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			tyreSoldInvoiceDetailsList 		= null;
			tyreSoldInvoiceDetailsDto			= null;
		}
	}

	@Transactional
	@Override
	public ValueObject deleteTyreSoldInvoiceDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		List<TyreSoldDetails>		tyreSoldDetails					= null;
		TyreSoldInvoiceDetails		tyreSoldInvoiceDetails			= null;
		short						tyreStatus						= 0;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tyreSoldInvoiceDetails		= tyreSoldInvoiceDetailsRepository.findByTyreSoldInvoiceId(valueObject.getLong("invoiceId"), userDetails.getCompany_id());
			tyreSoldDetails 			= tyreSoldDetailsRepository.findByTyreSoldInvoiceId(valueObject.getLong("invoiceId"), userDetails.getCompany_id());
			
			if(tyreSoldInvoiceDetails.getSoldType() == TyreTypeConstant.AVAILABLE_TO_SOLD) {
				tyreStatus = InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE;
			}else if(tyreSoldInvoiceDetails.getSoldType() == TyreTypeConstant.SCRAPED_TO_SOLD) {
				tyreStatus = InventoryTyreDto.TYRE_ASSIGN_STATUS_SCRAPED;
			}
			tyreSoldInvoiceDetailsRepository.deleteTyreSoldInvoiceDetails(valueObject.getLong("invoiceId"),userDetails.getCompany_id());
			
			for(TyreSoldDetails dto : tyreSoldDetails) {
				
				updateTyreStatusByTyreId(dto.getTyreId().toString() , tyreStatus);
			}
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> getTyreInvoiceListForTallyImport(String fromDate, String toDate, Integer companyId,
			String tallyCompany, String ledgerame) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT SE.ITYRE_ID, SE.ITYRE_NUMBER, VA.approvalCreateDate, V.vendorName,"
					+ " TC.companyName, SE.CREATED_DATE, SE.PAYMENT_TYPE_ID, VSD.subApprovalpaidAmount, SE.isPendingForTally,"
					+ " SE.INVOICE_DATE, SE.INVOICE_NUMBER"
					+ " FROM InventoryTyreInvoice SE "
					+ " INNER JOIN Vendor AS V ON V.vendorId = SE.VENDOR_ID"
					+ " INNER JOIN VendorApproval VA on VA.approvalId = SE.TYRE_APPROVAL_ID AND VA.markForDelete = 0"
					+ " INNER JOIN VendorSubApprovalDetails VSD ON VSD.approvalId = VA.approvalId AND VSD.invoiceId = SE.ITYRE_ID AND VSD.approvalPlaceId = "+ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_INVOICE+""
					+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = SE.tallyCompanyId AND TC.companyName = '"+tallyCompany+"'"
					+ " WHERE VA.approvalCreateDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND SE.COMPANY_ID = "+companyId+" AND SE.markForDelete = 0 AND VSD.subApprovalpaidAmount > 0 AND VSD.markForDelete = 0");
			
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
						
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_TYRE);
						select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
						select.setTripSheetId(select.getTripExpenseID());
						select.setCredit(false);
						select.setVid(0);
						select.setLedgerName(ledgerame);
						select.setExpenseName(ledgerame);
						
						if(select.getExpenseFixedId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
							select.setCredit(true);
						}else {
							select.setCredit(false);
						}
						
						if(select.getVendorName() == null ) {
							select.setVendorName("--");
						}
						if(vehicle[2] != null ) {
							select.setCreatedStr(SQLdate.format((java.util.Date)vehicle[2]));
						}
						
						 select.setRemark("Tyre Invoice Date: "+SQLdate.format((java.util.Date)vehicle[9])+" Invoice Number : "+(String)vehicle[10]+" From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						select.setTripSheetNumberStr("TI-"+select.getTripSheetNumber());
						
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
	public List<VendorPaymentNotPaidDto> vendorPaymentNotPaidTyreInvoiceList(Integer vendor_id, String dateFrom, String dateTo, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
			"SELECT R.ITYRE_ID, R.ITYRE_NUMBER, VN.vendorName, R.INVOICE_NUMBER, R.INVOICE_DATE, R.PAYMENT_TYPE_ID,  "
				+ " R.INVOICE_AMOUNT,  R.balanceAmount "
				+ " FROM InventoryTyreInvoice AS R "
				+ " LEFT JOIN Vendor VN ON VN.vendorId = R.VENDOR_ID "
				+ " WHERE ( R.VENDOR_ID="+vendor_id+" AND R.PAYMENT_TYPE_ID="+PaymentTypeConstant.PAYMENT_TYPE_CREDIT+" and R.VENDOR_PAYMODE_STATUS_ID ="+InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_NOTPAID+" OR  "
				+ " R.VENDOR_ID="+vendor_id+" AND R.PAYMENT_TYPE_ID= "+PaymentTypeConstant.PAYMENT_TYPE_CREDIT+" AND R.VENDOR_PAYMODE_STATUS_ID = "+InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_APPROVED+"OR "
				+ " R.VENDOR_ID="+vendor_id+" AND R.PAYMENT_TYPE_ID= "+PaymentTypeConstant.PAYMENT_TYPE_CREDIT+" AND R.VENDOR_PAYMODE_STATUS_ID = "+InventoryTyreInvoiceDto.VENDOR_PAYMODE_STATUS_PARTIALLY_PAID+" AND R.balanceAmount > 0) "
				+ " AND R.INVOICE_DATE  between '"
				+ dateFrom + "' AND '" + dateTo + "' AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0",
				Object[].class);

		List<Object[]> results = queryt.getResultList();

		List<VendorPaymentNotPaidDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VendorPaymentNotPaidDto>();
			VendorPaymentNotPaidDto list = null;
			for (Object[] result : results) {
				list = new VendorPaymentNotPaidDto();
				
				list.setSerialNumberId((Long) result[0]);
				list.setSerialNumber((Long) result[1]);
				list.setSerialNumberStr("TI-"+list.getSerialNumber());
				list.setVendorName((String) result[2]);
				list.setInvoiceNumber((String) result[3]);
				
				list.setInvoiceDateStr(dateFormat.format((java.util.Date) result[4]));
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
	public List<VendorPaymentNotPaidDto> vendorPaymentNotPaidTyreRethreadList(Integer vendor_id, String dateFrom, String dateTo, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
			"SELECT R.TRID, R.TRNUMBER, VN.vendorName, R.TR_INVOICE_NUMBER, R.TR_INVOICE_DATE, R.TR_PAYMENT_TYPE_ID, "
				+ " R.TR_AMOUNT, R.balanceAmount "
				+ " FROM InventoryTyreRetread AS R "
				+ " LEFT JOIN Vendor VN ON VN.vendorId = R.TR_VENDOR_ID "
				+ " WHERE  ( R.TR_VENDOR_ID="+vendor_id+" AND R.TR_PAYMENT_TYPE_ID= "+PaymentTypeConstant.PAYMENT_TYPE_CREDIT+" and R.TR_VENDOR_PAYMODE_STATUS_ID ="+InventoryTyreRetreadDto.TR_VENDOR_PAYMODE_STATUS_NOTPAID+" OR  "
				+ " R.TR_VENDOR_ID="+vendor_id+" AND R.TR_PAYMENT_TYPE_ID="+PaymentTypeConstant.PAYMENT_TYPE_CREDIT+" AND R.TR_VENDOR_PAYMODE_STATUS_ID ="+InventoryTyreRetreadDto.TR_VENDOR_PAYMODE_STATUS_APPROVED+ " OR "
				+ " R.TR_VENDOR_ID="+vendor_id+" AND R.TR_PAYMENT_TYPE_ID="+PaymentTypeConstant.PAYMENT_TYPE_CREDIT+" AND R.TR_VENDOR_PAYMODE_STATUS_ID ="+InventoryTyreRetreadDto.TR_VENDOR_PAYMODE_STATUS_PARTIALLY_PAID+ " AND R.balanceAmount > 0) "
				+ " AND R.TR_INVOICE_DATE  between '"
				+ dateFrom + "' AND '" + dateTo + "' AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0",
				Object[].class);

		List<Object[]> results = queryt.getResultList();

		List<VendorPaymentNotPaidDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VendorPaymentNotPaidDto>();
			VendorPaymentNotPaidDto list = null;
			for (Object[] result : results) {
				list = new VendorPaymentNotPaidDto();

				list.setSerialNumberId((Long) result[0]);
				list.setSerialNumber((Long) result[1]);
				list.setSerialNumberStr("TR-"+list.getSerialNumber());
				list.setVendorName((String) result[2]);
				list.setInvoiceNumber((String) result[3]);
				
				list.setInvoiceDateStr(dateFormat.format((java.util.Date) result[4]));
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
	public InventoryTyreHistory getTyreMountCreatedBetweenDates(String startDate, String endDate, Integer companyId) throws Exception {
		TypedQuery<InventoryTyreHistory> query = entityManager.createQuery(
				" SELECT T "
						+ " From InventoryTyreHistory as T "
						+ " WHERE T.createdOn between '"+ startDate +"' AND '"+ endDate + "' AND T.COMPANY_ID = " +companyId+" "						
						+ " AND T.markForDelete = 0 ",InventoryTyreHistory.class);
		

		InventoryTyreHistory	inventoryTyreHistory = null;
		try {
				query.setMaxResults(1);
				inventoryTyreHistory = query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		return inventoryTyreHistory;
	}
	
	@Transactional
	public void updateSublocationInInventoryTyre(Long ITYRE_ID, Integer subLocationId, Integer companyId) throws Exception {
		TyreRepository.updateSublocationInInventoryTyre(ITYRE_ID, subLocationId, companyId);
	}
	
	@Override
	public InventoryTyreInvoice getInventoryTyreInvoiceByInvoiceNumber(String INVOICE_NUMBER, Integer companyId)throws Exception{
		try {
			Query query = entityManager.createQuery(
					"SELECT IT.ITYRE_ID, IT.ITYRE_NUMBER FROM InventoryTyreInvoice  as IT WHERE "
					+ " IT.INVOICE_NUMBER = :INVOICE_NUMBER  AND IT.COMPANY_ID ="+companyId+" AND  IT.markForDelete = 0");
		
			
			Object[] result = null;
			try {
				query.setParameter("INVOICE_NUMBER", INVOICE_NUMBER);
				query.setMaxResults(1);
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			InventoryTyreInvoice	entries = null;
			if (result != null) {
				entries = new InventoryTyreInvoice();
				entries.setITYRE_ID((Long) result[0]);
			}
			return entries;
		} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
		
}
	
	public  List<InventoryTyreHistoryDto> getTyreConsumptionList(ValueObject object) throws Exception {
		TypedQuery<Object[]> 									typedQuery  	= null;
		List<InventoryTyreHistoryDto>							InventoryTyreHistoryList 	= null;
		try {
			typedQuery = entityManager.createQuery(
					" Select IH.TYRE_ID, VM.TYRE_MODEL, IH.TYRE_NUMBER, IH.VEHICLE_ID, V.vehicle_registration, IH.POSITION, IH.AXLE, "
					+ " IH.TYRE_ASSIGN_DATE, IH.OPEN_ODOMETER, IH.COMPANY_ID, IH.TYRE_COMMENT, IH.TYRE_STATUS_ID, IH.PRE_TYRE_ASSIGN_DATE, "
					+ " IH.PRE_TYRE_NUMBER, IH.PRE_OPEN_ODOMETER, IT.TYRE_AMOUNT "
					+ " FROM InventoryTyreHistory IH "
					+ " INNER JOIN InventoryTyre IT on IT.TYRE_ID = IH.TYRE_ID "
					+ " INNER JOIN VehicleTyreModelType VM on VM.TYRE_MT_ID = IT.TYRE_MANUFACTURER_ID "
					+ " INNER JOIN Vehicle as V on V.vid = IH.VEHICLE_ID "
					+ " WHERE IH.markForDelete = 0 "
					+ " AND "+object.getString("queryStr")+"  AND IH.COMPANY_ID ="+object.getInt("companyId")+" ORDER BY IH.TYRE_ASSIGN_DATE DESC "
					,Object[].class);
		/*	typedQuery.setFirstResult((object.getInt("pageNumber") - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);*/
			List<Object[]> results = typedQuery.getResultList();
			if (results != null && !results.isEmpty()) {
				InventoryTyreHistoryList	= new ArrayList<>();
				InventoryTyreHistoryDto	inventoryTyreHistoryDto = null;
				for (Object[] result : results) {
					inventoryTyreHistoryDto	= new InventoryTyreHistoryDto();
					inventoryTyreHistoryDto.setTYRE_ID((Long) result[0]);
					inventoryTyreHistoryDto.setTyreModel((String) result[1]);
					inventoryTyreHistoryDto.setTYRE_NUMBER((String) result[2]);
					inventoryTyreHistoryDto.setVEHICLE_ID((Integer) result[3]);
					inventoryTyreHistoryDto.setVEHICLE_REGISTRATION((String) result[4]);
					inventoryTyreHistoryDto.setPOSITION((String) result[5]);
					inventoryTyreHistoryDto.setAXLE((String) result[6]);
					inventoryTyreHistoryDto.setTYRE_ASSIGN_DATE(SQLdate.format((Timestamp)result[7]));
					inventoryTyreHistoryDto.setOPEN_ODOMETER((Integer)result[8]);
					inventoryTyreHistoryDto.setCompanyId((Integer)result[9]);
					inventoryTyreHistoryDto.setTYRE_COMMENT((String)result[10]);
					inventoryTyreHistoryDto.setTYRE_STATUS_ID((short)result[11]);
					inventoryTyreHistoryDto.setTYRE_STATUS(InventoryTyreHistoryDto.getStatusName((short)result[11]));
					if(result[12] != null) {
					inventoryTyreHistoryDto.setPRE_TYRE_ASSIGN_DATEStr(SQLdate.format((Timestamp)result[12]));
					}else {
						inventoryTyreHistoryDto.setPRE_TYRE_ASSIGN_DATEStr("-");
					}
					if(result[13] != null) {
					inventoryTyreHistoryDto.setPRE_TYRE_NUMBER((String)result[13]);
					}else {
						inventoryTyreHistoryDto.setPRE_TYRE_NUMBER("-");
					}
					if(result[14] != null) {
					inventoryTyreHistoryDto.setPRE_OPEN_ODOMETER((Integer)result[14]);
					}else {
						inventoryTyreHistoryDto.setPRE_OPEN_ODOMETER(0);
					}
					if(result[15] != null) {
						if(inventoryTyreHistoryDto.getTYRE_STATUS_ID() == InventoryTyreHistoryDto.INVENTORY_TYRE_HISTORY_STATUS_MOUNT) {
							inventoryTyreHistoryDto.setTYRE_COST((Double)result[15]);
						}else{
							inventoryTyreHistoryDto.setTYRE_COST(0.0);
						}
					}
					
					
					InventoryTyreHistoryList.add(inventoryTyreHistoryDto);
				}
				
			}
			return InventoryTyreHistoryList;
		} catch (Exception e) {
			throw e;
		}finally {
			typedQuery  	= null;
		}
	}
	@Transactional
	public ValueObject getLocationWiseTyreQuantity(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> 						query 					= null;
		List<InventoryDto> 							inventoryDtoList 		= null;
		try {
			
			String strQuery = "select  count(*) , B.WAREHOUSE_LOCATION_ID ,PL.partlocation_name from InventoryTyre AS B "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = B.WAREHOUSE_LOCATION_ID "
					+ " where " ;
			if(!valueObject.getBoolean("withoutManufacturerType", false))
				strQuery+= "B.TYRE_MANUFACTURER_ID ="+valueObject.getLong("manufacturerId",0)+" and B.TYRE_SIZE_ID = "+valueObject.getLong("sizeId",0)+" and ";
				strQuery += " B.TYRE_MODEL_ID="+valueObject.getLong("typeId",0)+" " ;
				strQuery += " AND B.TYRE_ASSIGN_STATUS_ID = "+ InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE+" AND B.COMPANY_ID ="+valueObject.getInt("companyId",0)+" AND B.markForDelete=0  group by B.WAREHOUSE_LOCATION_ID ";
			
					query = entityManager.createQuery(strQuery, Object[].class);
			
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
	public ValueObject getlocationWiseTyreCount(ValueObject valueobject) throws Exception {
		try {
			valueobject.put("locationWiseCount", TyreRepository.getLocationWiseTyreCount(valueobject.getInt("companyId",0), InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE, valueobject.getInt("location"),valueobject.getInt("manufacturer",0),valueobject.getInt("model",0), valueobject.getInt("capacity",0)));
			valueobject.put("otherLocationCount", TyreRepository.getOtherLocationWiseTyreCount(valueobject.getInt("companyId",0), InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE, valueobject.getInt("location"),valueobject.getInt("manufacturer",0),valueobject.getInt("model",0), valueobject.getInt("capacity",0)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueobject;
	}
	
	@Override
	@Transactional
	public ValueObject getlocationTyreCount(ValueObject valueobject) throws Exception {
		try {
			valueobject.put("stockQty", TyreRepository.getLocationWiseTyreCount(valueobject.getInt("companyId",0), InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE, valueobject.getInt("location"),valueobject.getInt("model",0)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueobject;
	}
	
	
	@Override
	@Transactional
	public void updateMultipleTyreStatus(short status ,String tyreIds ,int companyId) {
		entityManager.createQuery("UPDATE From InventoryTyre SET TYRE_ASSIGN_STATUS_ID="+status+" WHERE TYRE_ID IN ("+tyreIds+") AND COMPANY_ID = "+companyId+"")
		.executeUpdate();
	}	
	
	@Override
	@Transactional
	public void updateMultipleTyreStatusAndLocation(short status ,String tyreIds ,int companyId,long location) {
		try {
		entityManager.createQuery("UPDATE From InventoryTyre SET TYRE_ASSIGN_STATUS_ID="+status+",WAREHOUSE_LOCATION_ID="+location+" WHERE TYRE_ID IN ("+tyreIds+") AND COMPANY_ID = "+companyId+"")
		.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional
	public List<InventoryTyre> getlocationTyreList(ValueObject valueobject) throws Exception {
		return TyreRepository.getLocationWiseTyreList(valueobject.getInt("companyId",0), InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE, valueobject.getInt("location"),valueobject.getInt("manufacturer",0),valueobject.getInt("model",0), valueobject.getInt("capacity",0));
	}

	@Override
	@Transactional
	public List<InventoryTyre> getTyreListByIds(List<Long> ids ,ValueObject valueobject) throws Exception {
		return TyreRepository.getTyreListByIds(ids ,valueobject.getInt("companyId",0), InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE, valueobject.getInt("location"));
	}
	
	
	@Override
	public List<InventoryTyre> getTyreByTyreModel(String term, Long tyreModelId, short tyreStatusId, Integer company_id,Integer locationId)
			throws Exception {
		List<InventoryTyre> Dtos = null;
		VehicleTyreModelSubType subType  = 	modelSubTypeService.get_VehicleTyre_ModelSubTypeById(Integer.parseInt(tyreModelId+""), company_id);
		if(subType != null) {
			String query = "";
			if(tyreStatusId == TyreTypeConstant.WORKSHOP) {
				query = "AND  R.dismountedTyreStatusId="+TyreTypeConstant.WORKSHOP+"";
			}else {
				query = "AND  R.dismountedTyreStatusId = 0 ";
				
			}
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT R.TYRE_ID, R.TYRE_NUMBER, R.TYRE_IN_NUMBER FROM InventoryTyre AS R "
							+ " INNER JOIN VehicleTyreModelSubType VTS ON VTS.TYRE_MST_ID = R.TYRE_MODEL_ID "
							+ "WHERE lower(R.TYRE_NUMBER) Like ('%" + term + "%') AND R.COMPANY_ID = "+company_id+" AND WAREHOUSE_LOCATION_ID = "+locationId+" "
							+ " AND  R.TYRE_ASSIGN_STATUS_ID="+InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE+" "+query+" "
							+ " AND R.TYRE_SIZE_ID = "+subType.getTyreModelSizeId()+" AND R.markForDelete = 0", Object[].class);
			
			queryt.setFirstResult(0);
			queryt.setMaxResults(20);
			List<Object[]> results = queryt.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryTyre>();
				InventoryTyre list = null;
				for (Object[] result : results) {
					list = new InventoryTyre();

					list.setTYRE_ID((Long) result[0]);
					list.setTYRE_NUMBER((String) result[1]);
					list.setTYRE_IN_NUMBER((Long) result[2]);

					Dtos.add(list);
				}
			}
			}

		}
		
		
			return Dtos;
	}
	
	@Override
	@Transactional
	public void updateTyreStatusToAvailable(Integer VEHICLE_ID, Integer CLOSE_ODOMETER, Integer TYRE_USEAGE, short TYRE_ASSIGN_STATUS,short tyreStatusId, Long TYRE_ID) throws Exception {

		TyreRepository.updateTyreStatusToAvailable(VEHICLE_ID, CLOSE_ODOMETER, TYRE_USEAGE, TYRE_ASSIGN_STATUS,tyreStatusId, TYRE_ID);
	}
	
	@Override
	public ValueObject getAvailabeTyreForAssignment(ValueObject valueObject) throws Exception {
		String query = "";
		VehicleModelTyreLayoutDto	vehicleModelTyreLayoutDto = null;
		try {
			
			vehicleModelTyreLayoutDto = 	vehicleModelTyreLayoutService.getVehicleModelTyreLayoutDetails(valueObject);
			
			if(vehicleModelTyreLayoutDto != null) {
				if(valueObject.getShort("assignFromId") == TyreTypeConstant.WORKSHOP) {
					query = "AND  R.dismountedTyreStatusId="+TyreTypeConstant.WORKSHOP+"";
					
				}else {
					query = "AND  R.dismountedTyreStatusId = 0 ";
				}
				TypedQuery<Object[]> queryt = entityManager
						.createQuery("SELECT R.TYRE_ID, R.TYRE_NUMBER, R.TYRE_IN_NUMBER, VM.TYRE_MODEL_SUBTYPE, PL.partlocation_name FROM InventoryTyre AS R "
								+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.WAREHOUSE_LOCATION_ID "
								+ " LEFT JOIN VehicleTyreModelSubType AS VM ON VM.TYRE_MST_ID = R.TYRE_MODEL_ID "
								+ " WHERE R.COMPANY_ID = "+valueObject.getInt("companyId")+" AND  R.TYRE_ASSIGN_STATUS_ID="+InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE+" "+query+" AND  R.TYRE_MODEL_ID IN ("+vehicleModelTyreLayoutDto.getFrontTyreModelId()+","+vehicleModelTyreLayoutDto.getRearTyreModelId()+","+vehicleModelTyreLayoutDto.getSpareTyreModelId()+") AND R.markForDelete = 0 ", Object[].class);
				
				List<Object[]> results = queryt.getResultList();
	
				List<InventoryTyreDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<InventoryTyreDto>();
					InventoryTyreDto list = null;
					for (Object[] result : results) {
						list = new InventoryTyreDto();
	
						list.setTYRE_ID((Long) result[0]);
						list.setTYRE_NUMBER((String) result[1]);
						list.setTYRE_IN_NUMBER((Long) result[2]);
						list.setTYRE_MODEL((String) result[3]);
						list.setWAREHOUSE_LOCATION((String) result[4]);
	
						Dtos.add(list);
					}
				}
				
				valueObject.put("availableTyreList", Dtos);
			}
			return valueObject;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}	
	
	@Override
	public ValueObject getTyreGuageByTyreId(ValueObject valueObject) throws Exception {
		
		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT R.TYRE_ID, VT.tyreGauge  FROM InventoryTyre AS R "
						+ " LEFT JOIN VehicleTyreModelSubType AS VT ON VT.TYRE_MST_ID = R.TYRE_MODEL_ID "
						+ " WHERE R.TYRE_ID = "+valueObject.getInt("tyreId",0)+" AND R.COMPANY_ID = "+valueObject.getInt("companyId")+"  AND R.markForDelete = 0", Object[].class);
		
		queryt.setMaxResults(1);
		Object[] result = null;

		try {
			result = (Object[]) queryt.getSingleResult();
		} catch (NoResultException e) {
			
		}
		InventoryTyreDto dto = new InventoryTyreDto();
		if (result != null) {
			dto.setTYRE_ID((Long) result[0]);
			if(result[1] != null) {
				dto.setTyreGuage((Integer) result[1]);
			}
		} 
		valueObject.put("inventoryTyre", dto);
		return valueObject;
	}
	
	@Override
	public Long validateTyreByTyreSize(Integer tyreSizeId, Integer companyId) throws Exception {
		try {
			return TyreRepository.validateTyreByTyreSize(tyreSizeId,companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public Long validateTyreByTyreModel(Integer tyreModelId, Integer companyId) throws Exception {
		try {
			return TyreRepository.validateTyreByTyreModel(tyreModelId,companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public List<InventoryTyreDto>  getMaxRunTyre(ValueObject valueObject) throws Exception {
	try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT R.TYRE_ID, R.TYRE_NUMBER, R.TYRE_USEAGE FROM InventoryTyre AS R "
							+ " WHERE R.COMPANY_ID = "+valueObject.getInt("companyId")+" "+valueObject.getString("query","")+" AND R.markForDelete = 0 ", Object[].class);
			
			
			List<Object[]> results = queryt.getResultList();

			List<InventoryTyreDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryTyreDto>();
				InventoryTyreDto list = null;
				for (Object[] result : results) {
					list = new InventoryTyreDto();

					list.setTYRE_ID((Long) result[0]);
					list.setTYRE_NUMBER((String) result[1]);
					list.setTYRE_USEAGE((Integer) result[2]);
					Dtos.add(list);
				}
			}
			
			return Dtos;
	
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	}
	
	@Override
	public List<InventoryTyreDto>  getMinRunScrapTyre(ValueObject valueObject) throws Exception {
		try {
			
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT R.TYRE_ID, R.TYRE_NUMBER, R.TYRE_USEAGE  FROM InventoryTyre AS R "
							+ " WHERE R.COMPANY_ID = "+valueObject.getInt("companyId")+" "+valueObject.getString("query","")+"  AND R.markForDelete = 0 ", Object[].class);
			
			List<Object[]> results = queryt.getResultList();

			List<InventoryTyreDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryTyreDto>();
				InventoryTyreDto list = null;
				for (Object[] result : results) {
					list = new InventoryTyreDto();

					list.setTYRE_ID((Long) result[0]);
					list.setTYRE_NUMBER((String) result[1]);
					list.setTYRE_USEAGE((Integer) result[2]);

					Dtos.add(list);
				}
			}
			
return Dtos;
	
	} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<InventoryTyre> tyreListDropDown(String term, Integer location, Integer companyid,long model)
			throws Exception {

		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT R.TYRE_ID, R.TYRE_NUMBER, R.TYRE_IN_NUMBER,TM.TYRE_MODEL ,TSM.TYRE_MODEL,VS.TYRE_SIZE FROM InventoryTyre AS R "
						+ " LEFT JOIN VehicleTyreModelType TM ON TM.TYRE_MT_ID = R.TYRE_MANUFACTURER_ID"
						+ " LEFT JOIN VehicleTyreModelSubType TSM ON TSM.TYRE_MST_ID = R.TYRE_MODEL_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = R.TYRE_SIZE_ID "
						+ "WHERE lower(R.TYRE_NUMBER) Like (:term) AND R.TYRE_ASSIGN_STATUS_ID="+InventoryTyreDto.TYRE_ASSIGN_STATUS_AVAILABLE+" AND WAREHOUSE_LOCATION_ID = "+location+" "
						+ "AND R.TYRE_MODEL_ID ="+model+" AND R.COMPANY_ID = "+companyid+" AND R.markForDelete = 0", Object[].class);
		
		queryt.setParameter("term", "%"+term+"%");
		queryt.setFirstResult(0);
		queryt.setMaxResults(20);
		List<Object[]> results = queryt.getResultList();

		List<InventoryTyre> dtos = null;
		if (results != null && !results.isEmpty()) {
			dtos = new ArrayList<>();
			InventoryTyre list = null;
			for (Object[] result : results) {
				list = new InventoryTyre();

				list.setTYRE_ID((Long) result[0]);
				list.setTYRE_NUMBER(result[1]+"-"+result[3]+" "+result[5]);
				list.setTYRE_IN_NUMBER((Long) result[2]);

				dtos.add(list);
			}
		}

		return dtos;
	}
	
	@Override
	public void updateOpenOdometer(Long tyre_ID, int RotationOdometer, Integer companyId){
		try {
			 TyreRepository.updateOpenOdometer(tyre_ID,RotationOdometer,companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		}
	
	@Override
	public List<InventoryTyre> getMoveToRepairTyre(Integer company_id, Integer locationId) throws Exception {
		List<InventoryTyre> Dtos = null;
		try {
				TypedQuery<Object[]> queryt = entityManager
						.createQuery("SELECT R.TYRE_ID, R.TYRE_NUMBER, R.TYRE_IN_NUMBER FROM InventoryTyre AS R "
								+ " WHERE  R.COMPANY_ID = "+company_id+" AND R.dismountedTyreStatusId = "+TyreAssignmentConstant.OLD_TYRE_MOVED_TO_REPAIR+" AND R.WAREHOUSE_LOCATION_ID="+locationId+" "
								+ " AND  R.TYRE_ASSIGN_STATUS_ID="+InventoryTyreDto.TYRE_ASSIGN_STATUS_UNAVAILABLE+" AND R.markForDelete = 0", Object[].class);
				queryt.setFirstResult(0);
				queryt.setMaxResults(20);
				List<Object[]> results = queryt.getResultList();

				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<InventoryTyre>();
					InventoryTyre list = null;
					for (Object[] result : results) {
						list = new InventoryTyre();

						list.setTYRE_ID((Long) result[0]);
						list.setTYRE_NUMBER((String) result[1]);
						list.setTYRE_IN_NUMBER((Long) result[2]);

						Dtos.add(list);
					}
				}
				return Dtos;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Override
	@Transactional
	public void updateInventoryTyreRejectStatus(Long scropedby, Date scropedDate, short Status,short dismountStatus,
			String Multiple_Tyre_ID, Integer companyId) throws Exception {
		entityManager.createQuery("update InventoryTyre set TYRE_SCRAPED_BY_ID=" + scropedby + ", TYRE_SCRAPED_DATE='"
				+ scropedDate + "', TYRE_ASSIGN_STATUS_ID='" + Status + "', dismountedTyreStatusId = "+dismountStatus+" where TYRE_ID IN (" + Multiple_Tyre_ID + ") AND COMPANY_ID = "+companyId+"")
		.executeUpdate();
	}
	
	@Override
	public List<InventoryTyre> getLocationWiseTyreList(Integer company_id, Integer locationId) throws Exception {
		List<InventoryTyre> Dtos = null;
		try {
				TypedQuery<Object[]> queryt = entityManager
						.createQuery("SELECT R.TYRE_ID, R.TYRE_NUMBER, R.TYRE_IN_NUMBER FROM InventoryTyre AS R "
								+ " WHERE  R.COMPANY_ID = "+company_id+" AND R.WAREHOUSE_LOCATION_ID="+locationId+" "
								+ " AND R.markForDelete = 0", Object[].class);
				queryt.setFirstResult(0);
				List<Object[]> results = queryt.getResultList();

				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<InventoryTyre>();
					InventoryTyre list = null;
					for (Object[] result : results) {
						list = new InventoryTyre();

						list.setTYRE_ID((Long) result[0]);
						list.setTYRE_NUMBER((String) result[1]);
						list.setTYRE_IN_NUMBER((Long) result[2]);

						Dtos.add(list);
					}
				}
				return Dtos;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	public List<InventoryTyreDto> getTyreHistoryDetails(String query) throws Exception {
		List<InventoryTyreDto> tyreList= null;
		TypedQuery<Object[]> queryt = entityManager
				.createQuery(" SELECT T.TYRE_ID,T.TYRE_NUMBER,TH.TYRE_ASSIGN_DATE,T.TYRE_AMOUNT,TH.VEHICLE_ID FROM InventoryTyreHistory AS TH "
						+ " INNER JOIN InventoryTyre AS T ON T.TYRE_ID = TH.TYRE_ID"
						+ " WHERE TH.markForDelete = 0  "+query+" ", Object[].class);
		try {
			List<Object[]> resultList=	queryt.getResultList();
			if(resultList != null && !resultList.isEmpty()) {
				tyreList=new ArrayList<>();
				InventoryTyreDto tyre = null;
				for(Object[] result : resultList) {
					tyre = new InventoryTyreDto();
					tyre.setTYRE_ID((Long) result[0]);
					tyre.setTYRE_NUMBER((String) result[1]);
					tyre.setTYRE_ASSIGN_DATE_ON( (Date) result[2]);
					if(result[2] != null)
						tyre.setTYRE_ASSIGN_DATE(SQLdate.format( result[2]));
					tyre.setTYRE_AMOUNT((Double) result[3]);
					tyre.setVEHICLE_ID((Integer) result[4]);
					tyreList.add(tyre);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return tyreList;
	}
	
	@Override
	public List<TripSheetExpenseDto> getTyreInvoiceListForTallyImportATC(String fromDate, String toDate,
			Integer companyId, String tallyCompany, String ledgerame) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT SE.ITYRE_ID, SE.ITYRE_NUMBER, SE.INVOICE_DATE, V.vendorName,"
					+ " SE.CREATED_DATE, SE.PAYMENT_TYPE_ID, SE.INVOICE_AMOUNT, SE.isPendingForTally,"
					+ " SE.INVOICE_DATE, SE.INVOICE_NUMBER"
					+ " FROM InventoryTyreInvoice SE "
					+ " INNER JOIN Vendor AS V ON V.vendorId = SE.VENDOR_ID"
					+ " WHERE SE.INVOICE_DATE between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND SE.COMPANY_ID = "+companyId+" AND SE.markForDelete = 0 AND SE.INVOICE_AMOUNT > 0");
			
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
						
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_TYRE);
						select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
						select.setTripSheetId(select.getTripExpenseID());
						select.setCredit(false);
						select.setVid(0);
						select.setLedgerName(ledgerame);
						select.setExpenseName(ledgerame);
						
						if(select.getExpenseFixedId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
							select.setCredit(true);
						}else {
							select.setCredit(false);
						}
						
						if(select.getVendorName() == null ) {
							select.setVendorName("--");
						}
						if(vehicle[2] != null ) {
							select.setCreatedStr(SQLdate.format((java.util.Date)vehicle[2]));
						}
						
						 select.setRemark("Tyre Invoice Date: "+SQLdate.format((java.util.Date)vehicle[8])+" Invoice Number : "+(String)vehicle[9]+" From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						select.setTripSheetNumberStr("TI-"+select.getTripSheetNumber());
						
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