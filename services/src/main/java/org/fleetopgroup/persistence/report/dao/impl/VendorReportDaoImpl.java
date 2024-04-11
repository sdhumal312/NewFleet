package org.fleetopgroup.persistence.report.dao.impl;

//import java.sql.Date;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.ApprovalType;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.LorryHirePaymentStatus;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.persistence.dto.VendorApprovalDto;
import org.fleetopgroup.persistence.dto.VendorMarketLorryHireDetailsDto;
import org.fleetopgroup.persistence.report.dao.IVendorReportDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.springframework.stereotype.Repository;

@Repository
public class VendorReportDaoImpl implements IVendorReportDao{
	
	SimpleDateFormat sqldateTime = new SimpleDateFormat("dd-MM-yyyy");
	
	@PersistenceContext	EntityManager entityManager;

	//Vendor Payment History Report Start
	
	@Override
	public List<VendorApprovalDto> getVendorPaymentHistoryReportList(String query, Integer companyId)
			throws Exception {

		try {			

			TypedQuery<Object[]> queryt = entityManager
					.createQuery(" SELECT VA.approvalId, VA.approvalNumber, VA.approvalvendorID, "							
							+ " V.vendorName, VT.vendor_TypeName, VA.approvalCreateDate, "							
							+ " U.email, VA.approvalPlaceId, VA.approvalTotal, V.vendorLocation "							
							+ " FROM VendorApproval AS VA "							
							+ " LEFT JOIN Vendor AS V ON V.vendorId = VA.approvalvendorID "							
							+ " LEFT JOIN VendorType AS VT ON VT.vendor_Typeid = V.vendorTypeId "							
							+ " LEFT JOIN User AS U ON U.id = VA.approvalCreateById "							
							+ " WHERE VA.companyId = "+companyId+" AND  VA.markForDelete = 0 "+query+" ORDER BY VA.approvalId DESC ", Object[].class);


			List<Object[]>	results = queryt.getResultList();

			List<VendorApprovalDto>	list	= null;

			if(results != null && !results.isEmpty()) {
				
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					VendorApprovalDto	vendorInfo = new VendorApprovalDto();	

					vendorInfo.setApprovalId((Long) result[0]);
					if(result[1] != null)
					//vendorInfo.setApprovalNumber((Long) result[1]);
					vendorInfo.setApprovalNumbers("A-"+(Long) result[1]);					
					vendorInfo.setApprovalvendorID((Integer) result[2]);
					if(result[3] != null)
					vendorInfo.setApprovalvendorName((String) result[3]);
					if(result[4] != null)
					vendorInfo.setApprovalvendorType((String) result[4]);
					if(result[5] != null)					
					vendorInfo.setApprovalCreateDateStr(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[5], DateTimeUtility.DD_MM_YYYY));
					if(result[6] != null)
					vendorInfo.setApprovalCreateBy((String) result[6]);
					vendorInfo.setApprovalPlaceId((short) result[7]);
					if(result[8] != null)
					vendorInfo.setApprovalTotal((Double) result[8]);
					vendorInfo.setApprovalPlace(ApprovalType.getApprovalType(vendorInfo.getApprovalPlaceId()));
					
					vendorInfo.setApprovalvendorLocation((String) result[9]);

					list.add(vendorInfo);
				}
				
			}

			return list;
		} catch (Exception e) {
			throw e;
		}


	}	
	
	//Vendor Payment History Report Start
	
	/*****/
	
	//Vendor Lorry Hire Details Report Start
	@Override
	public List<VendorMarketLorryHireDetailsDto> getVendorLorryHireDetailsReport(String query, Integer companyId)
			throws Exception {

		try {			

			TypedQuery<Object[]> queryt = entityManager
					.createQuery(" SELECT V.vid, V.vehicle_registration, VP.hireDate, VP.lorryHire, "	
							+ " VP.advanceAmount, TE.expenseName, VC.amount, VP.paidAmount, VP.balanceAmount, VP.paymentStatusId, "					
							+ " VN.vendorId, VN.vendorName, VN.vendorLocation, VP.lorryHireDetailsId, VP.lorryHireDetailsNumber "							
							+ " FROM VendorMarketLorryHireDetails AS VP " 	
							+ " INNER JOIN Vehicle AS V ON V.vid = VP.vid "				
							+ " INNER JOIN Vendor AS VN ON VP.vendorId = VN.vendorId "
							+ " LEFT JOIN VendorMarketHireToCharges AS  VC ON VC.lorryHireDetailsId = VP.lorryHireDetailsId "	
							+ " LEFT JOIN TripExpense TE ON TE.expenseID = VC.chargeMasterId "	
							+ " WHERE VP.companyId = " + companyId + ""                                                
							+ query + "  ORDER BY VP.createdOn desc ",		
							Object[].class);

			List<Object[]>	results = queryt.getResultList();

			List<VendorMarketLorryHireDetailsDto>	list	= null;

			if(results != null && !results.isEmpty()) {
				
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					VendorMarketLorryHireDetailsDto	lorryDetailsList = new VendorMarketLorryHireDetailsDto();	
					
					lorryDetailsList.setVid((Integer) result[0] );
					
					if(result[1] != null)
					lorryDetailsList.setVehicle_registration((String) result[1]);
					
					if(result[2] != null)
					lorryDetailsList.setHireDateStr(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[2], DateTimeUtility.DD_MM_YYYY));
					
					if(result[3] != null)
					lorryDetailsList.setLorryHire((Double) result[3]);
					
					if(result[4] != null)
					lorryDetailsList.setAdvanceAmount((Double) result[4]);
					
					if(result[5] != null)
					lorryDetailsList.setExpenseName((String) result[5]);
					
					if(result[6] != null)
					lorryDetailsList.setAmount((Double) result[6]);
					
					if(result[7] != null)
					lorryDetailsList.setPaidAmount((Double) result[7]);
					
					if(result[8] != null)
					lorryDetailsList.setBalanceAmount((Double) result[8]);
					
					if(result[9] != null)
					lorryDetailsList.setPaymentStatusId((short) result[9]);
					lorryDetailsList.setPaymentStatus(LorryHirePaymentStatus.getPaymentMode(lorryDetailsList.getPaymentStatusId()));
					
					if(result[10] != null)
					lorryDetailsList.setVendorId((Integer) result[10]);
					
					if(result[11] != null)
					lorryDetailsList.setVendorName((String) result[11]);
					
					if(result[12] != null)
					lorryDetailsList.setVendorLocation((String) result[12]);
					
					if(result[13] != null)
					lorryDetailsList.setLorryHireDetailsId((Long) result[13]);

					if(result[14] != null)
					lorryDetailsList.setLorryHireDetailsNumber((Long) result[14]);
					list.add(lorryDetailsList);
					
				}
				
			}

			return list;
		} catch (Exception e) {
			throw e;
		}


	}	
	//Vendor Lorry Hire Details Report Stop
	
	@Override
	public List<VendorApprovalDto> vendorWiseApprovedStatusReport(int vendorId, String dateFrom, String dateTo, Integer companyId) throws Exception {
		try {			

			TypedQuery<Object[]> queryt = entityManager
					.createQuery(" SELECT VA.approvalId, VA.approvalNumber, VA.approvalvendorID, V.vendorName, VT.vendor_TypeName, "							
							+ " VA.approvalCreateDate, U.email, VA.approvalPlaceId, VA.approvalTotal, V.vendorLocation, "							
							+ " VA.approvalPaymentTypeId, VA.approvalPayNumber, VSA.subApprovalTotal, VSA.subApprovalpaidAmount, "
							+ " VSA.approvedPaymentStatusId "
							+ " FROM VendorApproval AS VA "
							+ " INNER JOIN VendorSubApprovalDetails AS VSA ON VSA.approvalId = VA.approvalId "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = VA.approvalvendorID "							
							+ " LEFT JOIN VendorType AS VT ON VT.vendor_Typeid = V.vendorTypeId "							
							+ " LEFT JOIN User AS U ON U.id = VA.approvalCreateById "							
							+ " WHERE V.vendorId = "+vendorId+" AND VA.companyId = "+companyId+" AND VA.markForDelete = 0 "
							+ " AND VA.approvalCreateDate between '" +dateFrom+"' AND '"+dateTo+"' "
							+ " AND VA.approvalStatusId = "+FuelVendorPaymentMode.PAYMENT_MODE_APPROVED+" "
							+ " ORDER BY VA.approvalId DESC ", Object[].class);


			List<Object[]>	results = queryt.getResultList();

			List<VendorApprovalDto>	list	= null;

			if(results != null && !results.isEmpty()) {
				
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					VendorApprovalDto	vendorInfo = new VendorApprovalDto();	

					vendorInfo.setApprovalId((Long) result[0]);
					if(result[1] != null)
					//vendorInfo.setApprovalNumber((Long) result[1]);
					vendorInfo.setApprovalNumbers("A-"+(Long) result[1]);					
					vendorInfo.setApprovalvendorID((Integer) result[2]);
					if(result[3] != null)
					vendorInfo.setApprovalvendorName((String) result[3]);
					if(result[4] != null)
					vendorInfo.setApprovalvendorType((String) result[4]);
					if(result[5] != null)					
					vendorInfo.setApprovalCreateDateStr(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[5], DateTimeUtility.DD_MM_YYYY));
					if(result[6] != null)
					vendorInfo.setApprovalCreateBy((String) result[6]);
					vendorInfo.setApprovalPlaceId((short) result[7]);
					if(result[8] != null)
					vendorInfo.setApprovalTotal((Double) result[8]);
					vendorInfo.setApprovalPlace(ApprovalType.getApprovalType(vendorInfo.getApprovalPlaceId()));
					vendorInfo.setApprovalvendorLocation((String) result[9]);
					vendorInfo.setApprovalPaymentType(PaymentTypeConstant.getPaymentTypeName((short) result[10]));
					vendorInfo.setApprovalPayNumber((String) result[11]);
					vendorInfo.setSubApprovalTotal((Double) result[12]);
					vendorInfo.setSubApprovalpaidAmount((Double) result[13]);
					vendorInfo.setApprovalStatus(FuelVendorPaymentMode.getPaymentMode((short) result[14]));

					list.add(vendorInfo);
				}
				
			}

			return list;
			
		} catch (Exception e) {
			throw e;
		}

	}
	
	
	@Override
	public List<VendorApprovalDto> vendorWisePaidStatusReport(int vendorId, String dateFrom, String dateTo, Integer companyId) throws Exception {
		try {			

			TypedQuery<Object[]> queryt = entityManager
					.createQuery(" SELECT VA.approvalId, VA.approvalNumber, VA.approvalvendorID, V.vendorName, VT.vendor_TypeName, "							
							+ " VA.approvalDateofpayment, U.email, VA.approvalPlaceId, VA.approvalTotal, V.vendorLocation, "							
							+ " VSA.approvalPaymentTypeId, VA.approvalPayNumber, VSA.subApprovalTotal, VSA.subApprovalpaidAmount, "
							+ " VSA.approvedPaymentStatusId "
							+ " FROM VendorApproval AS VA "
							+ " INNER JOIN VendorSubApprovalDetails AS VSA ON VSA.approvalId = VA.approvalId "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = VA.approvalvendorID "							
							+ " LEFT JOIN VendorType AS VT ON VT.vendor_Typeid = V.vendorTypeId "							
							+ " LEFT JOIN User AS U ON U.id = VA.approvalpaidbyId "							
							+ " WHERE V.vendorId = "+vendorId+" AND VA.companyId = "+companyId+" AND VA.markForDelete = 0 "
							+ " AND VA.approvalStatusId IN ( "+FuelVendorPaymentMode.PAYMENT_MODE_PAID+" , "
							+ " "+FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID+" , "+FuelVendorPaymentMode.PAYMENT_MODE_NEGOTIABLE_PAID+" ) "
							+ " AND VA.approvalDateofpayment between '" +dateFrom+"' AND '"+dateTo+"' "
							+ " ORDER BY VA.approvalId DESC ", Object[].class);


			List<Object[]>	results = queryt.getResultList();

			List<VendorApprovalDto>	list	= null;

			if(results != null && !results.isEmpty()) {
				
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					VendorApprovalDto	vendorInfo = new VendorApprovalDto();	

					vendorInfo.setApprovalId((Long) result[0]);
					if(result[1] != null)
					//vendorInfo.setApprovalNumber((Long) result[1]);
					vendorInfo.setApprovalNumbers("A-"+(Long) result[1]);					
					vendorInfo.setApprovalvendorID((Integer) result[2]);
					if(result[3] != null)
					vendorInfo.setApprovalvendorName((String) result[3]);
					if(result[4] != null)
					vendorInfo.setApprovalvendorType((String) result[4]);
					if(result[5] != null)					
					vendorInfo.setApprovalCreateDateStr(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[5], DateTimeUtility.DD_MM_YYYY));
					if(result[6] != null)
					vendorInfo.setApprovalCreateBy((String) result[6]);
					vendorInfo.setApprovalPlaceId((short) result[7]);
					if(result[8] != null)
					vendorInfo.setApprovalTotal((Double) result[8]);
					vendorInfo.setApprovalPlace(ApprovalType.getApprovalType(vendorInfo.getApprovalPlaceId()));
					vendorInfo.setApprovalvendorLocation((String) result[9]);
					vendorInfo.setApprovalPaymentType(PaymentTypeConstant.getPaymentTypeName((short) result[10]));
					vendorInfo.setApprovalPayNumber((String) result[11]);
					
					if(result[13] != null) {
						vendorInfo.setSubApprovalTotal((Double) result[12]);
						vendorInfo.setSubApprovalTotalStr(""+vendorInfo.getSubApprovalTotal());
						vendorInfo.setSubApprovalpaidAmount((Double) result[13]);
					} else {
						vendorInfo.setSubApprovalTotal((Double) result[12]);
						vendorInfo.setSubApprovalTotalStr("-");
						vendorInfo.setSubApprovalpaidAmount((Double) result[12]);
					}
					
					vendorInfo.setApprovalStatus(FuelVendorPaymentMode.getPaymentMode((short) result[14]));

					list.add(vendorInfo);
				}
				
			}

			return list;
			
		} catch (Exception e) {
			throw e;
		}

	}	

}
