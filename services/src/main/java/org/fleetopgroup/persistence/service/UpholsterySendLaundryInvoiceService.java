package org.fleetopgroup.persistence.service;

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

import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.UpholsterySendLaundryInvoiceStatus;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.UpholsterySendLaundryInvoiceDto;
import org.fleetopgroup.persistence.serviceImpl.IUpholsterySendLaundryInvoiceService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpholsterySendLaundryInvoiceService implements IUpholsterySendLaundryInvoiceService {

	@PersistenceContext EntityManager entityManager;

	SimpleDateFormat	dateFormat	= new SimpleDateFormat("dd-MM-yyyy");
	
	SimpleDateFormat	format	= new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat	format1 = new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY_HH_MM_AA);
	DecimalFormat toFixed = new DecimalFormat("#.##");
	
	private static final int PAGE_SIZE_WASHING = 50;
	
	@Override
	public UpholsterySendLaundryInvoiceDto getLaundryInvoiceDetails(Long invoiceId, Integer companyId)
			throws Exception {

					Query query = entityManager.createQuery(
							"SELECT CI.laundryInvoiceId, CI.laundryInvoiceNumber, CI.wareHouseLocationId, CI.vendorId, CI.totalQuantity, CI.receivedQuantity, CI.losedQuantity, CI.damagedQuantity,"
							+ " CI.totalCost, CI.sentDate, CI.expectedReceiveDate, CI.paymentTypeId, CI.paymentNumber, CI.quoteNumber, CI.manualNumber, CI.description, CI.createdById,"
							+ " CI.lastModifiedById, CI.creationDate, CI.lastModifiedDate, PL.partlocation_name, U.firstName, U2.firstName,"
							+ " V.vendorName, V.vendorLocation, TC.tallyCompanyId, TC.companyName  "
							+ " FROM UpholsterySendLaundryInvoice CI "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CI.wareHouseLocationId "
							+ " INNER JOIN User U ON U.id = CI.createdById"
							+ " INNER JOIN User U2 ON U2.id = CI.lastModifiedById "
							+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = CI.tallyCompanyId"
							+ " LEFT JOIN Vendor V ON V.vendorId = CI.vendorId"
							+ " where CI.laundryInvoiceId = "+invoiceId+" AND CI.companyId = "+companyId+" AND CI.markForDelete = 0");
			
					Object[] result = null;
					try {
						result = (Object[]) query.getSingleResult();
						
					} catch (NoResultException nre) {
						
					}
			
					UpholsterySendLaundryInvoiceDto select;
					if (result != null) {
						select = new UpholsterySendLaundryInvoiceDto();
						
						select.setLaundryInvoiceId((Long) result[0]);
						select.setLaundryInvoiceNumber((Long) result[1]);
						select.setWareHouseLocationId((Integer) result[2]);
						select.setVendorId((Integer) result[3]);
						if(result[4] != null)
						select.setTotalQuantity(Double.parseDouble(toFixed.format(result[4])));
						if(result[5] != null)
						select.setReceivedQuantity(Double.parseDouble(toFixed.format(result[5])));
						if(result[6] != null)
						select.setLosedQuantity(Double.parseDouble(toFixed.format(result[6])));
						if(result[7] != null)
						select.setDamagedQuantity(Double.parseDouble(toFixed.format(result[7])));
						if(result[8] != null)
						select.setTotalCost(Double.parseDouble(toFixed.format(result[8])));
						select.setSentDate((Date) result[9]);
						select.setExpectedReceiveDate((Date) result[10]);
						select.setPaymentTypeId((short) result[11]);
						select.setPaymentNumber((String) result[12]);
						select.setQuoteNumber((String) result[13]);
						select.setManualNumber((String) result[14]);
						select.setDescription((String) result[15]);
						select.setCreatedById((Long) result[16]);
						select.setLastModifiedById((Long) result[17]);
						select.setCreationDate((Date) result[18]);
						select.setLastModifiedDate((Date) result[19]);
						select.setLocationName((String) result[20]);
						select.setCreatedBy((String) result[21]);
						select.setLastModifiedBy((String) result[22]);
						select.setVendorName((String) result[23]);
						select.setVendorLocation((String) result[24]);
						select.setTallyCompanyId((Long) result[25]);
						select.setTallyCompanyName((String) result[26]);
						
						if(select.getSentDate() != null) {
							select.setSentDateStr(dateFormat.format(select.getSentDate()));
						}
						if(select.getExpectedReceiveDate() != null) {
							select.setExpectedReceiveDateStr(dateFormat.format(select.getExpectedReceiveDate()));
						}
						
						if(select.getCreationDate() != null) {
							select.setCreationDateStr(format.format(select.getCreationDate()));
						}
						if(select.getLastModifiedDate() != null) {
							select.setLastModifiedOnStr(format.format(select.getLastModifiedDate()));
						}
						
						
						select.setPaymentType(PaymentTypeConstant.getPaymentTypeName(select.getPaymentTypeId()));
						
						
					} else {
						return null;
					}
			
					return select;
	}
	
	@Override
	@Transactional
	public void updateInvoiceDetailsToReceive(Long invoiceId, Double receiveQuantity) throws Exception {
		
		entityManager.createQuery(
				" UPDATE UpholsterySendLaundryInvoice AS VC SET VC.receivedQuantity = VC.receivedQuantity + "+receiveQuantity+" "
						+ " WHERE VC.laundryInvoiceId = "+invoiceId+" "
						+ "  AND VC.markForDelete = 0 ")
				.executeUpdate();
		
	}
	
	@Override
	@Transactional
	public void updateInvoiceDetailsToDamage(Long invoiceId, Double damageQuantity) throws Exception {
		
		entityManager.createQuery(
				" UPDATE UpholsterySendLaundryInvoice AS VC SET VC.damagedQuantity = VC.damagedQuantity + "+damageQuantity+" "
						+ " WHERE VC.laundryInvoiceId = "+invoiceId+" "
						+ "  AND VC.markForDelete = 0 ")
		.executeUpdate();
		
	}
	
	@Override
	@Transactional
	public void updateInvoiceDetailsToLost(Long invoiceId, Double lostQuantity) throws Exception {
		
		entityManager.createQuery(
				" UPDATE UpholsterySendLaundryInvoice AS VC SET VC.losedQuantity = VC.losedQuantity + "+lostQuantity+" "
						+ " WHERE VC.laundryInvoiceId = "+invoiceId+" "
						+ "  AND VC.markForDelete = 0 ")
		.executeUpdate();
		
	}
	
	@Override
	public UpholsterySendLaundryInvoiceDto getLaundryInvoiceStatus(Long invoiceId, Integer companyId)
			throws Exception {
					Double finalQty = 0.0;
					Query query = entityManager.createQuery(
							"SELECT CI.laundryInvoiceId, CI.totalQuantity, CI.receivedQuantity, CI.losedQuantity, CI.damagedQuantity "
							+ " FROM UpholsterySendLaundryInvoice CI "
							+ " where CI.laundryInvoiceId = "+invoiceId+" AND CI.companyId = "+companyId+" AND CI.markForDelete = 0");
			
					Object[] result = null;
					try {
						result = (Object[]) query.getSingleResult();
						
					} catch (NoResultException nre) {
						
					}
			
					UpholsterySendLaundryInvoiceDto select;
					if (result != null) {
						select = new UpholsterySendLaundryInvoiceDto();
						
						select.setLaundryInvoiceId((Long) result[0]);
						select.setTotalQuantity((Double) result[1]);
						select.setReceivedQuantity((Double) result[2]);
						select.setLosedQuantity((Double) result[3]);
						select.setDamagedQuantity((Double) result[4]);
						
						finalQty = select.getReceivedQuantity() + select.getLosedQuantity() + select.getDamagedQuantity();
						select.setFinalReceivedQty((select.getTotalQuantity() - finalQty));
						
					} else {
						return null;
					}
			
					return select;
	}
	
	@Override
	@Transactional
	public void updateLaundryInvoiceSatus(Long invoiceId, Integer companyId) throws Exception {
		
		entityManager.createQuery(
				" UPDATE UpholsterySendLaundryInvoice AS VC SET VC.laundryInvoiceStatus = "+UpholsterySendLaundryInvoiceStatus.LAUNDRY_INVOICE_STATUS_COMPLETED+" "
						+ " WHERE VC.laundryInvoiceId = "+invoiceId+" AND VC.companyId = "+companyId+" "
						+ "  AND VC.markForDelete = 0 ")
		.executeUpdate();
	}
	
	@Override
	public List<UpholsterySendLaundryInvoiceDto> getInWashingDetailsList(Integer pageNumber, long clothTypeId, Integer locationId) throws Exception {

		TypedQuery<Object[]> 							typedQuery 				= null;
		List<Object[]> 									resultList 				= null; 
		List<UpholsterySendLaundryInvoiceDto> 			inWashingDetailsList 	= null;
		UpholsterySendLaundryInvoiceDto 				inWashingDetails		= null;
		CustomUserDetails								userDetails				= null;

		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			double finalqty = 0.0;
			typedQuery = entityManager.createQuery(
				"SELECT USLI.laundryInvoiceId, USLI.wareHouseLocationId, SLCD.quantity, SLCD.receivedQuantity, SLCD.damagedQuantity, "
					+ " SLCD.losedQuantity, PL.partlocation_name, C.clothTypeName, U.firstName, USLI.creationDate "
					+ " FROM UpholsterySendLaundryInvoice as USLI "
					+ " INNER JOIN SentLaundryClothDetails SLCD ON SLCD.laundryInvoiceId = USLI.laundryInvoiceId "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = USLI.wareHouseLocationId "
					+ " INNER JOIN ClothTypes C ON C.clothTypesId = SLCD.clothTypesId "
					+ " INNER JOIN User U ON U.id = USLI.lastModifiedById "
					+ " WHERE SLCD.clothTypesId = "+clothTypeId+" AND USLI.wareHouseLocationId = "+locationId+" "
					+ " AND USLI.laundryInvoiceStatus = "+UpholsterySendLaundryInvoiceStatus.LAUNDRY_INVOICE_STATUS_OPEN+" "
					+ " AND USLI.companyId = "+userDetails.getCompany_id()+" AND USLI.markForDelete = 0 ORDER BY USLI.laundryInvoiceId DESC ",
					Object[].class);
				typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE_WASHING);
				typedQuery.setMaxResults(PAGE_SIZE_WASHING);
			
			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				inWashingDetailsList = new ArrayList<>();

				for (Object[] result : resultList) {
					inWashingDetails = new UpholsterySendLaundryInvoiceDto();
					
					inWashingDetails.setLaundryInvoiceId((long) result[0]);
					inWashingDetails.setWareHouseLocationId((int) result[1]);
					inWashingDetails.setTotalQuantity((Double.parseDouble(toFixed.format(result[2])))) ;
					 
					inWashingDetails.setReceivedQuantity((Double) result[3]);
					inWashingDetails.setDamagedQuantity((Double) result[4]);
					inWashingDetails.setLosedQuantity((Double) result[5]);
					inWashingDetails.setLocationName((String) result[6]);
					inWashingDetails.setClothName((String) result[7]);
					inWashingDetails.setLastModifiedBy((String) result[8]);
					inWashingDetails.setCreationDateStr(format1.format((Date) result[9]));
					
					finalqty = inWashingDetails.getReceivedQuantity() + inWashingDetails.getDamagedQuantity() + inWashingDetails.getLosedQuantity();
					inWashingDetails.setFinalReceivedQty(Double.parseDouble(toFixed.format(inWashingDetails.getTotalQuantity() - finalqty)));
					
					inWashingDetailsList.add(inWashingDetails);
				}
			}
			return inWashingDetailsList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			inWashingDetailsList 	= null;
			inWashingDetails		= null;
		}
	}
	
}
