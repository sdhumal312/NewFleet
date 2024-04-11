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
import javax.transaction.Transactional;

import org.fleetopgroup.constant.ApprovalType;
import org.fleetopgroup.constant.BatteryConstant;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.TallyVoucherTypeContant;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.dao.BatteryInvoiceRepository;
import org.fleetopgroup.persistence.dao.BatteryRepository;
import org.fleetopgroup.persistence.dto.BatteryInvoiceDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.VendorPaymentNotPaidDto;
import org.fleetopgroup.persistence.model.BatteryInvoice;
import org.fleetopgroup.persistence.serviceImpl.IBatteryInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BatteryInvoiceService implements IBatteryInvoiceService {

	@PersistenceContext EntityManager entityManager;
	@Autowired private BatteryInvoiceRepository		batteryInvoiceRepository;
	@Autowired private BatteryRepository			batteryRepository;
	@Autowired private IPartLocationsService		partLocationsService;	
	private static final int PAGE_SIZE = 10;
	
	SimpleDateFormat tallyFormat		= new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat sqlDateFormat 	= new SimpleDateFormat("dd-MM-yyyy");
	
	PartLocationsBL PLBL = new PartLocationsBL();
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	
	@Override
	public List<BatteryInvoiceDto> getBatteryInvoiceDetails(BatteryInvoiceDto battery) throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT BI.batteryInvoiceId, BI.batteryInvoiceNumber, BI.invoiceNumber, BI.invoiceDate, BI.invoiceAmount,"
							+ " BI.paymentNumber, BI.totalBatteryAmount, BI.description, BI.vendorPaymentStatus, BI.paymentTypeId,"
							+ " BI.vendorPaymentDate, BI.poNumber, PL.partlocation_name, BI.wareHouseLocation, V.vendorName, V.vendorLocation,"
							+ " BI.vendorId "
							+ " FROM BatteryInvoice AS BI "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BI.wareHouseLocation "
							+ " LEFT JOIN Vendor V ON V.vendorId = BI.vendorId"
							+ " where BI.batteryInvoiceId = "+battery.getBatteryInvoiceId()+" AND BI.companyId = "+battery.getCompanyId()+" AND  BI.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<BatteryInvoiceDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					BatteryInvoiceDto	batteryInvoice = new BatteryInvoiceDto();
					
					batteryInvoice.setBatteryInvoiceId((Long) result[0]);
					batteryInvoice.setBatteryInvoiceNumber((Long) result[1]);
					batteryInvoice.setInvoiceNumber((String) result[2]);
					batteryInvoice.setInvoiceDate((Timestamp) result[3]);
					batteryInvoice.setInvoiceAmount((Double) result[4]);
					batteryInvoice.setPaymentNumber((String) result[5]);
					batteryInvoice.setTotalBatteryAmount((Double) result[6]);
					batteryInvoice.setDescription((String) result[7]);
					batteryInvoice.setVendorPaymentStatus((short) result[8]);
					batteryInvoice.setPaymentTypeId((short) result[9]);
					batteryInvoice.setVendorPaymentDate((Timestamp) result[10]);
					batteryInvoice.setPoNumber((String) result[11]);
					batteryInvoice.setBatteryLocation((String) result[12]);
					batteryInvoice.setWareHouseLocation((Integer) result[13]);
					batteryInvoice.setVendorName((String) result[14]);
					batteryInvoice.setVendorLocation((String) result[15]);
					batteryInvoice.setVendorId((Integer) result[16]);
					
					
					if(batteryInvoice.getVendorName() == null) {
						batteryInvoice.setVendorName("--");
					}
					
					list.add(batteryInvoice);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public BatteryInvoiceDto getBatteryInvoice(BatteryInvoiceDto battery) throws Exception {
		try {
			Query query  = entityManager
					.createQuery("SELECT BI.batteryInvoiceId, BI.batteryInvoiceNumber, BI.invoiceNumber, BI.invoiceDate, BI.invoiceAmount,"
							+ " BI.paymentNumber, BI.totalBatteryAmount, BI.description, BI.vendorPaymentStatus, BI.paymentTypeId,"
							+ " BI.vendorPaymentDate, BI.poNumber, PL.partlocation_name, BI.wareHouseLocation, V.vendorName, V.vendorLocation,"
							+ " BI.vendorId, BI.createdOn, BI.lastModifiedBy, BI.batteryApprovalId, BI.tallyCompanyId, TC.companyName,"
							+ " BI.battery_document, BI.battery_document_id, BI.subLocationId, PSL.partlocation_name, BI.purchaseOrderId,VSA.approvalId,VA.approvalNumber "
							+ " FROM BatteryInvoice AS BI "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BI.wareHouseLocation "
							+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = BI.subLocationId "
							+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = BI.tallyCompanyId"
							+ " LEFT JOIN Vendor V ON V.vendorId = BI.vendorId"
							+ " LEFT JOIN VendorSubApprovalDetails VSA ON VSA.invoiceId = BI.batteryInvoiceId AND VSA.approvalPlaceId = "+ApprovalType.APPROVAL_TYPE_INVENTORY_BATTERY_INVOICE+""
							+ " LEFT JOIN VendorApproval VA ON VA.approvalId = VSA.approvalId "
							+ " where BI.batteryInvoiceId = "+battery.getBatteryInvoiceId()+" AND BI.companyId = "+battery.getCompanyId()+" AND  BI.markForDelete = 0 ");
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			BatteryInvoiceDto	batteryInvoice = null;
				if (result != null) {
					batteryInvoice = new BatteryInvoiceDto();
					
					batteryInvoice.setBatteryInvoiceId((Long) result[0]);
					batteryInvoice.setBatteryInvoiceNumber((Long) result[1]);
					batteryInvoice.setInvoiceNumber((String) result[2]);
					batteryInvoice.setInvoiceDate((Timestamp) result[3]);
					batteryInvoice.setInvoiceAmount((Double) result[4]);
					batteryInvoice.setPaymentNumber((String) result[5]);
					batteryInvoice.setTotalBatteryAmount((Double) result[6]);
					batteryInvoice.setDescription((String) result[7]);
					batteryInvoice.setVendorPaymentStatus((short) result[8]);
					batteryInvoice.setPaymentTypeId((short) result[9]);
					batteryInvoice.setPaymentStatus(PaymentTypeConstant.getPaymentTypeName(batteryInvoice.getPaymentTypeId()));
					batteryInvoice.setVendorPaymentDate((Timestamp) result[10]);
					batteryInvoice.setPoNumber((String) result[11]);
					batteryInvoice.setBatteryLocation((String) result[12]);
					batteryInvoice.setWareHouseLocation((Integer) result[13]);
					batteryInvoice.setVendorName((String) result[14]);
					batteryInvoice.setVendorLocation((String) result[15]);
					batteryInvoice.setVendorId((Integer) result[16]);
					batteryInvoice.setCreatedOn((Timestamp) result[17]);
					batteryInvoice.setLastModifiedBy((Timestamp) result[18]);
					batteryInvoice.setBatteryApprovalId((Long) result[19]);
					if(result[20] != null)
						batteryInvoice.setTallyCompanyId((Long) result[20]);
					if(result[21] != null)
						batteryInvoice.setTallyCompanyName((String) result[21]);
					
					
					if(batteryInvoice.getInvoiceDate() != null) {
						batteryInvoice.setInvoiceDateStr(DateTimeUtility.getDateFromTimeStamp(batteryInvoice.getInvoiceDate(), DateTimeUtility.DD_MM_YYYY));
					}
					if(batteryInvoice.getVendorName() == null) {
						batteryInvoice.setVendorName("--");
					}
					batteryInvoice.setBattery_document((boolean) result[22]);
					if(result[23] != null) {
						batteryInvoice.setBattery_document_id((Long) result[23]);
					}
					if(result[24] != null) {
						batteryInvoice.setSubLocationId((Integer)result[24]);
						batteryInvoice.setSubLocation((String)result[25]);
					}
					if(result[26] != null) {
						batteryInvoice.setPurchaseOrderId((Long) result[26]);
					}
					if(result[27] != null) {
						batteryInvoice.setApprovalId((Long) result[27]);
						batteryInvoice.setApprovalNumber("A-"+(Long) result[28]);
					}
				}
			return batteryInvoice;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	@Transactional
	public void delete(Long id, Timestamp lastupdate, Long batteryInvoiceID) throws Exception {
		try {
			batteryInvoiceRepository.delete(id, lastupdate, batteryInvoiceID);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getBatteryInvoiceList(ValueObject valueObject) throws Exception {
		CustomUserDetails	   	userDetails				= null;
		Integer				   	pageNumber				= null;
		Long					batteryAvailableCount	= null;
		Long					batteryServiceCount		= null;
		Long					batteryScrapedCount		= null;
		try {

			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber			= valueObject.getInt("pageNumber",0);
			Page<BatteryInvoice> page = getDeployment_Page_VendorPayment(pageNumber, userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("BatteryInvoiceCount", page.getTotalElements());
			
			batteryAvailableCount 	= getbatteryCountByStatus(userDetails.getCompany_id(), BatteryConstant.BATTERY_ASIGNED_STATUS_AVAILABLE);
			batteryServiceCount 	= getbatteryCountByStatus(userDetails.getCompany_id(),BatteryConstant.BATTERY_ASIGNED_STATUS_IN_SERVICE);
			batteryScrapedCount 	= getbatteryCountByStatus(userDetails.getCompany_id(),BatteryConstant.BATTERY_ASIGNED_STATUS_IN_SCRAPED);

			List<BatteryInvoiceDto> pageList = getBatteryInvoiceList(pageNumber, userDetails.getCompany_id());

			valueObject.put("batteryAvailableCount", batteryAvailableCount);
			valueObject.put("batteryServiceCount", batteryServiceCount);
			valueObject.put("batteryScrapedCount", batteryScrapedCount);
			valueObject.put("BatteryInvoice", pageList);
			valueObject.put("SelectPage", pageNumber);
			valueObject.put("PartLocations", PLBL.prepareListofBean(partLocationsService.listPartLocationsByCompanyId(userDetails.getCompany_id())));
		
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			 userDetails				= null;
		}
	}
	
	public Page<BatteryInvoice> getDeployment_Page_VendorPayment(Integer pageNumber, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "batteryInvoiceId");
		return batteryInvoiceRepository.getDeployment_Page_BatteryInvoice(companyId, pageable);
	}

	@Override
	public Page<BatteryInvoice> getDeployment_Page_BatteryInvoice(String term, Integer pageNumber, Integer companyId)
			throws Exception {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "batteryInvoiceId");
		return batteryInvoiceRepository.getDeployment_Page_BatteryInvoice(term, companyId, pageable);
	}
	
	@Override
	public List<BatteryInvoiceDto> getBatteryInvoiceList(Integer pageNumber, Integer companyId) throws Exception {

		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]> 					resultList 				= null; 
		List<BatteryInvoiceDto> 		batteryInvoiceList 		= null;
		BatteryInvoiceDto 				batteryInvoiceDto		= null;

		try {

			typedQuery = entityManager.createQuery("SELECT BI.batteryInvoiceId, BI.batteryInvoiceNumber, BI.vendorId, V.vendorName, BI.wareHouseLocation,"
					+ " PL.partlocation_name, BI.invoiceNumber, BI.invoiceDate, BI.invoiceAmount, BI.createdById, U.firstName, U.lastName, BI.vendorPaymentDate,"
					+ " BI.battery_document, BI.battery_document_id, BI.vendorPaymentStatus , BI.subLocationId, PSL.partlocation_name, BI.paymentTypeId   "
					+ " FROM BatteryInvoice AS BI"
					+ " INNER JOIN User AS U ON U.id = BI.createdById "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BI.wareHouseLocation"
					+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = BI.subLocationId"
					+ " LEFT JOIN Vendor AS V ON V.vendorId = BI.vendorId "
					+ " WHERE BI.companyId ="+companyId+" AND BI.markForDelete = 0 ORDER BY BI.batteryInvoiceId DESC", Object[].class);

			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				batteryInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					batteryInvoiceDto = new BatteryInvoiceDto();
					
					batteryInvoiceDto.setBatteryInvoiceId((Long) result[0]);
					batteryInvoiceDto.setBatteryInvoiceNumber((Long) result[1]);
					batteryInvoiceDto.setVendorId((Integer) result[2]);
					batteryInvoiceDto.setVendorName((String) result[3]);
					batteryInvoiceDto.setWareHouseLocation((Integer) result[4]);
					batteryInvoiceDto.setBatteryLocation((String) result[5]);
					batteryInvoiceDto.setInvoiceNumber((String) result[6]);
					batteryInvoiceDto.setInvoiceDate((Timestamp) result[7]);
					batteryInvoiceDto.setInvoiceAmount((Double) result[8]);
				

					if(batteryInvoiceDto.getInvoiceDate() != null) {
						batteryInvoiceDto.setInvoiceDateStr(DateTimeUtility.getDateFromTimeStamp(batteryInvoiceDto.getInvoiceDate(), DateTimeUtility.DD_MM_YYYY));
					}
					if(batteryInvoiceDto.getVendorName() == null) {
						batteryInvoiceDto.setVendorName("--");
					}
					
					
					if((Long) result[9] != null) {
					batteryInvoiceDto.setCreatedById((Long) result[9]);
					}
					if((String) result[10] != null) {
					batteryInvoiceDto.setFirstName((String) result[10]);
					}
					if((String) result[11] != null) {
					batteryInvoiceDto.setLastName((String) result[11]);
					}
					if((Timestamp) result[12] != null) {
						batteryInvoiceDto.setVendorPaymentDate((Timestamp) result[12]);
					}
					batteryInvoiceDto.setBattery_document((boolean) result[13]);
					if(result[14] != null) {
						batteryInvoiceDto.setBattery_document_id((Long) result[14]);
					}
					batteryInvoiceDto.setVendorPaymentStatus((short) result[15]);
					batteryInvoiceDto.setVendorPaymentStatusStr(FuelVendorPaymentMode.getPaymentMode(batteryInvoiceDto.getVendorPaymentStatus()));
					if(result[16] != null) {
						batteryInvoiceDto.setSubLocationId((Integer) result[16]);
						batteryInvoiceDto.setSubLocation((String) result[17]);
					}else {
						batteryInvoiceDto.setSubLocation("");
					}
					batteryInvoiceDto.setPaymentTypeId((short) result[18]);
					batteryInvoiceList.add(batteryInvoiceDto);
					
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
	@Transactional
	public void updateBatteryInvoice(BatteryInvoiceDto batteryInvoiceDto) throws Exception {
		try {
			entityManager.createQuery("Update BatteryInvoice SET wareHouseLocation = "+batteryInvoiceDto.getWareHouseLocation()+" "
					+ ", poNumber = '"+batteryInvoiceDto.getPoNumber()+"', invoiceNumber = '"+batteryInvoiceDto.getInvoiceNumber()+"' "
					+ ", invoiceDate = '"+batteryInvoiceDto.getInvoiceDate()+"' , invoiceAmount = "+batteryInvoiceDto.getInvoiceAmount()+", balanceAmount = '"+batteryInvoiceDto.getInvoiceAmount()+"' "
					+ ", vendorId = "+batteryInvoiceDto.getVendorId()+" , description = '"+batteryInvoiceDto.getDescription()+"', vendorPaymentStatus = "+batteryInvoiceDto.getVendorPaymentStatus()+" "
					+ ", lastModifiedById = "+batteryInvoiceDto.getLastModifiedById()+", lastModifiedBy = '"+batteryInvoiceDto.getLastModifiedBy()+"'"
					+ " , tallyCompanyId = "+batteryInvoiceDto.getTallyCompanyId()+", battery_document = "+batteryInvoiceDto.isBattery_document()+", paymentTypeId = "+batteryInvoiceDto.getPaymentTypeId()+" , subLocationId="+batteryInvoiceDto.getSubLocationId()+" " 
					+ " where batteryInvoiceId = "+batteryInvoiceDto.getBatteryInvoiceId()+" AND companyId = "+batteryInvoiceDto.getCompanyId()+" ").executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject searchBatteryInvoice(ValueObject valueObject) throws Exception {
		CustomUserDetails	   userDetails				= null;
		Integer				   pageNumber				= null;
		String				   term						= null;
		try {

			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber			= valueObject.getInt("pageNumber",1);
			term				= valueObject.getString("term");
			Page<BatteryInvoice> page = getDeployment_Page_BatteryInvoice(term, pageNumber, userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("BatteryInvoiceCount", page.getTotalElements());

			List<BatteryInvoiceDto> pageList = getBatteryInvoiceList(term, pageNumber, userDetails.getCompany_id());

			valueObject.put("BatteryInvoice", pageList);
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
	public List<BatteryInvoiceDto> getBatteryInvoiceList(String term, Integer pageNumber, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]> 					resultList 				= null; 
		List<BatteryInvoiceDto> 		batteryInvoiceList 		= null;
		BatteryInvoiceDto 				batteryInvoiceDto		= null;

		try {
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			typedQuery = entityManager.createQuery("SELECT BI.batteryInvoiceId, BI.batteryInvoiceNumber, BI.vendorId, V.vendorName, BI.wareHouseLocation,"
					+ " PL.partlocation_name, BI.invoiceNumber, BI.invoiceDate, BI.invoiceAmount, BI.createdById, U.firstName, U.lastName "
					+ " FROM BatteryInvoice AS BI"
					+ " INNER JOIN User AS U ON U.id = BI.createdById "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BI.wareHouseLocation"
					+ " LEFT JOIN Vendor AS V ON V.vendorId = BI.vendorId"
					+ " WHERE lower(BI.invoiceNumber) like '%"+term+"%' AND BI.companyId ="+companyId+" AND BI.markForDelete = 0 ORDER BY BI.batteryInvoiceId DESC", Object[].class);

			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				batteryInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					batteryInvoiceDto = new BatteryInvoiceDto();
					
					batteryInvoiceDto.setBatteryInvoiceId((Long) result[0]);
					batteryInvoiceDto.setBatteryInvoiceNumber((Long) result[1]);
					batteryInvoiceDto.setVendorId((Integer) result[2]);
					batteryInvoiceDto.setVendorName((String) result[3]);
					batteryInvoiceDto.setWareHouseLocation((Integer) result[4]);
					batteryInvoiceDto.setBatteryLocation((String) result[5]);
					batteryInvoiceDto.setInvoiceNumber((String) result[6]);
					batteryInvoiceDto.setInvoiceDate((Timestamp) result[7]);
					batteryInvoiceDto.setInvoiceAmount((Double) result[8]);

					if(batteryInvoiceDto.getInvoiceDate() != null) {
						batteryInvoiceDto.setInvoiceDateStr(DateTimeUtility.getDateFromTimeStamp(batteryInvoiceDto.getInvoiceDate(), DateTimeUtility.DD_MM_YYYY));
					}
					
					if(batteryInvoiceDto.getVendorName() == null) {
						batteryInvoiceDto.setVendorName("--");
					}
					
					if((Long) result[9] != null) {
					batteryInvoiceDto.setCreatedById((Long) result[9]);
					}
					if((String) result[10] != null) {
					batteryInvoiceDto.setFirstName((String) result[10]);
					}
					if((String) result[11] != null) {
					batteryInvoiceDto.setLastName((String) result[11]);
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
	public void registerInvoice(BatteryInvoice batteryInvoice) throws Exception {
		batteryInvoiceRepository.save(batteryInvoice);
	}

	@Override
	public List<BatteryInvoiceDto> FilterVendorCreditListInventoryBatteryInvoice(Integer vendorId, String dateRangeFrom, String dateRangeTo, Integer company_id) throws Exception {

		List<BatteryInvoiceDto>			batteryInvoiceList		= null;
		BatteryInvoiceDto				batteryInvoice 			= null;
		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]>					results 				= null;
		

		try {
			
			typedQuery = entityManager
					.createQuery("SELECT BI.batteryInvoiceId, BI.batteryInvoiceNumber, BI.invoiceNumber, BI.invoiceDate, BI.invoiceAmount,"
							+ " BI.paymentNumber, BI.totalBatteryAmount, BI.description, BI.vendorPaymentStatus, BI.paymentTypeId,"
							+ " BI.vendorPaymentDate, BI.poNumber, PL.partlocation_name, BI.wareHouseLocation, V.vendorName, V.vendorLocation,"
							+ " BI.vendorId,BI.paidAmount, BI.balanceAmount  "
							+ " FROM BatteryInvoice AS BI "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BI.wareHouseLocation "
							+ " LEFT JOIN Vendor V ON V.vendorId = BI.vendorId"
							+ " where BI.invoiceDate BETWEEN '"+dateRangeFrom+"' AND '"+dateRangeTo+"'"
							+ " AND BI.vendorId = "+vendorId+" AND BI.paymentTypeId = "+PaymentTypeConstant.PAYMENT_TYPE_CREDIT
							+ " AND (BI.vendorPaymentStatus = " + FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID + " "
							+ " OR BI.vendorPaymentStatus = " + FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID + " AND BI.balanceAmount > 0) "
							+ " AND BI.companyId = "+company_id+" AND  BI.markForDelete = 0 ORDER BY BI.invoiceDate DESC", Object[].class);
			
			results = typedQuery.getResultList();
			
			if(results != null && !results.isEmpty()) {
				batteryInvoiceList	=	new ArrayList<>();
				for (Object[] result : results) {
					batteryInvoice = new BatteryInvoiceDto();
					
					batteryInvoice.setBatteryInvoiceId((Long) result[0]);
					batteryInvoice.setBatteryInvoiceNumber((Long) result[1]);
					batteryInvoice.setInvoiceNumber((String) result[2]);
					batteryInvoice.setInvoiceDate((Timestamp) result[3]);
					if(result[4] != null)
					batteryInvoice.setInvoiceAmount(Double.parseDouble(toFixedTwo.format(result[4])));
					batteryInvoice.setPaymentNumber((String) result[5]);
					if( result[6] != null)
					batteryInvoice.setTotalBatteryAmount(Double.parseDouble(toFixedTwo.format(result[6])));
					batteryInvoice.setDescription((String) result[7]);
					batteryInvoice.setVendorPaymentStatus((short) result[8]);
					batteryInvoice.setPaymentTypeId((short) result[9]);
					batteryInvoice.setVendorPaymentDate((Timestamp) result[10]);
					batteryInvoice.setPoNumber((String) result[11]);
					batteryInvoice.setBatteryLocation((String) result[12]);
					batteryInvoice.setWareHouseLocation((Integer) result[13]);
					batteryInvoice.setVendorName((String) result[14]);
					batteryInvoice.setVendorLocation((String) result[15]);
					batteryInvoice.setVendorId((Integer) result[16]);
					if(result[17]!=null) {
					batteryInvoice.setPaidAmount(Double.parseDouble(toFixedTwo.format(result[17])));
					}
					if(result[18]!=null) {
					batteryInvoice.setBalanceAmount(Double.parseDouble(toFixedTwo.format(result[18])));
					}
					batteryInvoiceList.add(batteryInvoice);
				}
			}
			
			return batteryInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			batteryInvoiceList		= null;
			batteryInvoice 			= null;
			typedQuery 				= null;
			results 				= null;
		}
	
	}
	
	@Transactional
	public void update_Vendor_ApprovalTO_Status_MULTIP_InventoryBatteryInvoice_ID(String ApprovalInvoice_ID,
			Long approvalId, short approval_Status) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		entityManager.createQuery("  UPDATE BatteryInvoice SET batteryApprovalId=" + approvalId
				+ ", vendorPaymentStatus=" + approval_Status + " WHERE batteryInvoiceId IN (" + ApprovalInvoice_ID + ") AND companyId = "+userDetails.getCompany_id()+" ")
				.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentDetails(Long approvalId, String paymentDate, short paymentStatus) throws Exception {

		entityManager.createQuery("  UPDATE BatteryInvoice SET vendorPaymentDate ='" + paymentDate+"' , "
				+ " vendorPaymentStatus ="+paymentStatus+""
				+ " WHERE batteryApprovalId = "+approvalId+" ").executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception {
		entityManager.createQuery("  UPDATE BatteryInvoice SET vendorPaymentDate =" + paymentDate+" , "
				+ " vendorPaymentStatus="+paymentStatus+", batteryApprovalId = "+approvalId+""
				+ " WHERE batteryInvoiceId = "+invoiceId+" ").executeUpdate();
		
	}
	
	@Transactional
	public void update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryBatteryInvoice_ID(String ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp approval_date, Integer companyId,double paidAmount,double discountAmount ) throws Exception {
		double balanceAmount = 0;
		entityManager.createQuery("  UPDATE BatteryInvoice SET batteryApprovalId=" + Approval_ID
				+ ",vendorPaymentDate='" + approval_date + "', vendorPaymentStatus='" + approval_Status 
				+ "', paidAmount = '"+paidAmount+"', discountAmount = '"+discountAmount+"' , balanceAmount = '"+balanceAmount+"' "
				+ " WHERE batteryInvoiceId IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();
	}
	
	@Transactional
	public List<BatteryInvoiceDto> get_Amount_VendorApproval_IN_InventoryBatteryInvoice(Long VendorApproval_Id, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT bi.batteryInvoiceId, bi.invoiceAmount From BatteryInvoice as bi WHERE bi.batteryApprovalId=:approval AND bi.companyId =:companyId AND bi.markForDelete = 0",
				Object[].class);
		queryt.setParameter("approval", VendorApproval_Id);
		queryt.setParameter("companyId", companyId);

		List<Object[]> results = queryt.getResultList();

		List<BatteryInvoiceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<BatteryInvoiceDto>();
			BatteryInvoiceDto list = null;
			for (Object[] result : results) {
				list = new BatteryInvoiceDto();

				list.setBatteryInvoiceId((Long) result[0]);
				list.setInvoiceAmount((Double) result[1]);

				Dtos.add(list);
			}
		}

		return Dtos;
	}

	@Override
	public List<BatteryInvoiceDto> getVendorApproval_IN_InventorybatteryInvoice_List(Long approvalId, Integer company_id) throws Exception {


		List<BatteryInvoiceDto>			batteryInvoiceList		= null;
		BatteryInvoiceDto				batteryInvoice 			= null;
		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]>					results 				= null;

		try {
			
			typedQuery = entityManager
					.createQuery("SELECT BI.batteryInvoiceId, BI.batteryInvoiceNumber, BI.invoiceNumber, BI.invoiceDate, BI.invoiceAmount,"
							+ " BI.paymentNumber, BI.totalBatteryAmount, BI.description, BI.vendorPaymentStatus, BI.paymentTypeId,"
							+ " BI.vendorPaymentDate, BI.poNumber, PL.partlocation_name, BI.wareHouseLocation, V.vendorName, V.vendorLocation,"
							+ " BI.vendorId,BI.paidAmount "
							+ " FROM BatteryInvoice AS BI "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = BI.wareHouseLocation "
							+ " LEFT JOIN Vendor V ON V.vendorId = BI.vendorId"
							+ " where BI.batteryApprovalId = "+approvalId+" AND BI.companyId = "+company_id+" AND  BI.markForDelete = 0 ", Object[].class);
			
			results = typedQuery.getResultList();
			
			if(results != null && !results.isEmpty()) {
				batteryInvoiceList	=	new ArrayList<>();
				for (Object[] result : results) {
					batteryInvoice = new BatteryInvoiceDto();
					
					batteryInvoice.setBatteryInvoiceId((Long) result[0]);
					batteryInvoice.setBatteryInvoiceNumber((Long) result[1]);
					batteryInvoice.setInvoiceNumber((String) result[2]);
					batteryInvoice.setInvoiceDate((Timestamp) result[3]);
					batteryInvoice.setInvoiceAmount((Double) result[4]);
					batteryInvoice.setPaymentNumber((String) result[5]);
					batteryInvoice.setTotalBatteryAmount((Double) result[6]);
					batteryInvoice.setDescription((String) result[7]);
					batteryInvoice.setVendorPaymentStatus((short) result[8]);
					batteryInvoice.setPaymentTypeId((short) result[9]);
					batteryInvoice.setVendorPaymentDate((Timestamp) result[10]);
					batteryInvoice.setPoNumber((String) result[11]);
					batteryInvoice.setBatteryLocation((String) result[12]);
					batteryInvoice.setWareHouseLocation((Integer) result[13]);
					batteryInvoice.setVendorName((String) result[14]);
					batteryInvoice.setVendorLocation((String) result[15]);
					batteryInvoice.setVendorId((Integer) result[16]);
					if((Double) result[17] != null)
					batteryInvoice.setPaidAmount((Double) result[17]);
					
					batteryInvoiceList.add(batteryInvoice);
				}
			}
			
			return batteryInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			batteryInvoiceList		= null;
			batteryInvoice 			= null;
			typedQuery 				= null;
			results 				= null;
		}
	
	
	}
	
	@Override
	public long getbatteryCountByStatus(Integer companyId, short status) throws Exception {
		try {
			return batteryRepository.getbatteryCountByStatus(companyId,status );
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Transactional
	public void updatePaymentApprovedBatteryDetails(Long ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId) throws Exception {
		entityManager.createQuery("  UPDATE BatteryInvoice SET batteryApprovalId=" + Approval_ID
				+ ",expectedPaymentDate='" + expectedPaymentDate + "', vendorPaymentStatus='" + approval_Status +"' "
				+ " WHERE batteryInvoiceId IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();
	}
	
	@Transactional
	public void updatePaymentPaidBatteryDetails(String ApprovalInvoice_ID, Timestamp approval_date, Integer companyId ,short vendorPaymentStatus) throws Exception {
	
		entityManager.createQuery("  UPDATE BatteryInvoice SET vendorPaymentDate='" + approval_date+"' ,vendorPaymentStatus ="+vendorPaymentStatus+" WHERE batteryInvoiceId IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> getBatteryInvoiceListForTallyImport(String fromDate, String toDate,
			Integer companyId, String tallyCompany) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT SE.batteryInvoiceId, SE.batteryInvoiceNumber, VA.approvalCreateDate, V.vendorName,"
					+ " TC.companyName, SE.createdOn, SE.paymentTypeId, VSD.subApprovalpaidAmount, SE.isPendingForTally,"
					+ " SE.invoiceDate, SE.invoiceNumber"
					+ " FROM BatteryInvoice SE "
					+ " INNER JOIN Vendor AS V ON V.vendorId = SE.vendorId"
					+ " INNER JOIN VendorApproval VA on VA.approvalId = SE.batteryApprovalId AND VA.markForDelete = 0"
					+ " INNER JOIN VendorSubApprovalDetails VSD ON VSD.approvalId = VA.approvalId AND VSD.invoiceId = SE.batteryInvoiceId AND VSD.approvalPlaceId = "+ApprovalType.APPROVAL_TYPE_INVENTORY_BATTERY_INVOICE+""
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
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_BATTERY);
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
						
						 select.setRemark("Battery Invoice Date: "+sqlDateFormat.format((java.util.Date)vehicle[9])+" Invoice Number : "+(String)vehicle[10]+" From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						select.setTripSheetNumberStr("BI-"+select.getTripSheetNumber());
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
	public List<VendorPaymentNotPaidDto> vendorPaymentNotPaidBatteryInvoiceList(Integer vendor_id, String dateFrom, String dateTo, Integer companyId) throws Exception {
		List<VendorPaymentNotPaidDto>	batteryInvoiceList		= null;
		VendorPaymentNotPaidDto			list 					= null;
		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]>					results 				= null;
		try {
			
			typedQuery = entityManager
					.createQuery("SELECT BI.batteryInvoiceId, BI.batteryInvoiceNumber, V.vendorName, BI.invoiceNumber, "
							+ " BI.invoiceDate, BI.paymentTypeId, BI.invoiceAmount, BI.balanceAmount "
							+ " FROM BatteryInvoice AS BI "
							+ " LEFT JOIN Vendor V ON V.vendorId = BI.vendorId "
							+ " where BI.invoiceDate BETWEEN '"+dateFrom+"' AND '"+dateTo+"'"
							+ " AND BI.vendorId = "+vendor_id+" AND BI.paymentTypeId = "+PaymentTypeConstant.PAYMENT_TYPE_CREDIT
							+ " AND (BI.vendorPaymentStatus = " + FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID + " "
							+ " OR BI.vendorPaymentStatus = " + FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID + " AND BI.balanceAmount > 0) "
							+ " AND BI.companyId = "+companyId+" AND  BI.markForDelete = 0 ", Object[].class);
			
			results = typedQuery.getResultList();
			
			if(results != null && !results.isEmpty()) {
				batteryInvoiceList	=	new ArrayList<>();
				for (Object[] result : results) {
					list = new VendorPaymentNotPaidDto();
					
					list.setSerialNumberId((Long) result[0]);
					list.setSerialNumber((Long) result[1]);
					list.setSerialNumberStr("BI-"+list.getSerialNumber());
					list.setVendorName((String) result[2]);
					
					if(result[3] != null) {
						list.setInvoiceNumber((String) result[3]);
					} else {
						list.setInvoiceNumber("-");
					}
					
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
					
					batteryInvoiceList.add(list);
				}
			}
			
			return batteryInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			batteryInvoiceList		= null;
			list 					= null;
			typedQuery 				= null;
			results 				= null;
		}
	
	}
	@Override
	public BatteryInvoice getBatteryInvoiceByBatteryInvoiceId(Long batteryInvoiceId, Integer companyId) throws Exception{		
		BatteryInvoice batteryInvoice= null;
		try {
			batteryInvoice = batteryInvoiceRepository.getBatteryInvoice(batteryInvoiceId, companyId);
			return batteryInvoice;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void updateSublocationInBattery(Long batteryInvoiceId, Integer subLocationId, Integer companyId) throws Exception {
		batteryRepository.updateSublocationInBattery(batteryInvoiceId, subLocationId, companyId);
	}
	
	@Override
	public BatteryInvoice getBatteryInvoiceByInvoiceNumber(String invoiceNumber, Integer companyId)throws Exception{
		try {
			
			javax.persistence.Query query = entityManager.createQuery(
					"SELECT BI.batteryInvoiceId, BI.batteryInvoiceNumber FROM BatteryInvoice  as BI WHERE "
					+ " BI.invoiceNumber = :invoiceNumber  AND BI.companyId ="+companyId+" AND  BI.markForDelete = 0");
		
			
			Object[] result = null;
			try {
				query.setParameter("invoiceNumber", invoiceNumber);
				query.setMaxResults(1);
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			BatteryInvoice	batteryInvoice = null;
			if (result != null) {
				batteryInvoice = new BatteryInvoice();
				batteryInvoice.setBatteryInvoiceId((Long) result[0]);
			}
			return batteryInvoice;
		} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
		
}
	
	@Override
	public List<TripSheetExpenseDto> getBatteryInvoiceListForTallyImportATC(String fromDate, String toDate,
			Integer companyId, String tallyCompany) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT SE.batteryInvoiceId, SE.batteryInvoiceNumber, SE.invoiceDate, V.vendorName,"
					+ "  SE.createdOn, SE.paymentTypeId, SE.invoiceAmount, SE.isPendingForTally,"
					+ " SE.invoiceDate, SE.invoiceNumber"
					+ " FROM BatteryInvoice SE "
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
						select.setExpenseAmount((Double) vehicle[6]);
						select.setPendingForTally((boolean) vehicle[7]);
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_BATTERY);
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
						
						 select.setRemark("Battery Invoice Date: "+sqlDateFormat.format((java.util.Date)vehicle[8])+" Invoice Number : "+(String)vehicle[9]+" From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						select.setTripSheetNumberStr("BI-"+select.getTripSheetNumber());
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
