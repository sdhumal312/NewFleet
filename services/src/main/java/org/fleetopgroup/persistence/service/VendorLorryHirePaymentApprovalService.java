package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.LorryHirePaymentStatus;
import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.ReceivedAsConstant;
import org.fleetopgroup.persistence.dao.LorryHirePaymentApprovalToHireDetailsRepository;
import org.fleetopgroup.persistence.dao.VendorLorryHirePaymentApprovalRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.LorryHirePaymentApprovalToHireDetailsDto;
import org.fleetopgroup.persistence.dto.VendorMarketLorryHireDetailsDto;
import org.fleetopgroup.persistence.model.LorryHirePaymentApprovalToHireDetails;
import org.fleetopgroup.persistence.model.VendorLorryHirePaymentApproval;
import org.fleetopgroup.persistence.report.dao.IVendorReportDao;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVendorLorryHireDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVendorLorryHirePaymentApprovalService;
import org.fleetopgroup.persistence.serviceImpl.IVendorMarketLorryHireDetailsService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class VendorLorryHirePaymentApprovalService implements IVendorLorryHirePaymentApprovalService {
	
	@Autowired	VendorLorryHirePaymentApprovalRepository			lorryHirePaymentApprovalRepository;
	@Autowired	LorryHirePaymentApprovalToHireDetailsRepository		approvalToHireDetailsRepository;
	@Autowired	IUserProfileService									userProfileService;
	@Autowired	IVendorReportDao									vendorReportDao;
	@Autowired	private ICompanyConfigurationService				companyConfigurationService;
	@Autowired	private IVendorLorryHireDetailsService				vendorLorryHireDetailsService;
	@Autowired	private IVendorMarketLorryHireDetailsService		vendorMarketLorryHireDetailsService;
	
	@PersistenceContext EntityManager entityManager;
	
	@Override
	public void saveVendorLorryHirePaymentApproval(VendorLorryHirePaymentApproval hirePaymentApproval)
			throws Exception {
		lorryHirePaymentApprovalRepository.save(hirePaymentApproval);
	}

	@Override
	public void saveVendorApprovalToHireDetails(LorryHirePaymentApprovalToHireDetails approvalToHireDetails)
			throws Exception {
		
		approvalToHireDetailsRepository.save(approvalToHireDetails);
	}
	
	@Override
	public ValueObject getVendorLorryPaymentDetailsReport(ValueObject valueObject) throws Exception {
		
		String						dateRange				= null;		
		CustomUserDetails			userDetails				= null;
		List<LorryHirePaymentApprovalToHireDetailsDto>		paymentList				= null;		
		ValueObject					tableConfig			    = null;
		int 						vendorId                = 0 ;		
			 
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dateRange	 	= valueObject.getString("dateRange");
			vendorId	    = valueObject.getInt("selectVendor", 0);		
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;			

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
	
				
				String Vendor_Name = "", Date = "";				
				
				if(vendorId > 0 )
				{
					Vendor_Name = " AND V.vendorId = "+ vendorId +" ";
				}				
				
				Date			   =  " AND VP.createdOn between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;											
				
				String query       = " " + Vendor_Name + " "+ Date + " ";
				
				tableConfig		= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.LORRY_HIRE_PAYMENT_REPORT_TABLE_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);				
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);				
				paymentList	    = getLorryHirePaymentList(query, userDetails.getCompany_id());
				
			}
			
			
			valueObject.put("paymentList", paymentList);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company",userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
			paymentList			= null;			
			userDetails			= null;
			dateRange			= null;
		}
	}
	
	@Override
	public ValueObject getVendorLorryPaymentOutStandingReport(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails				= null;
		List<VendorMarketLorryHireDetailsDto>	lorryHireDetailsList	= null;	
		String 									query 					= "";
		ValueObject					tableConfig			    = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(valueObject.getInt("vendorId", 0) > 0)
				query += " AND VP.vendorId = "+valueObject.getInt("vendorId", 0)+" ";
			if(valueObject.getLong("lorryHireDetailsId",0) > 0)
				query += " AND VP.lorryHireDetailsId = "+valueObject.getLong("lorryHireDetailsId", 0)+" ";
			
			lorryHireDetailsList	=	vendorMarketLorryHireDetailsService.getVendorLorryHirePaymentData(query, userDetails.getCompany_id());
			
			tableConfig		= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.LORRY_HIRE_OUTSTANDING_REPORT_TABLE_DATA_FILE_PATH);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);				
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);				
			
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("lorryHireDetailsList", lorryHireDetailsList);
			valueObject.put("paymentTypeList", PaymentTypeConstant.getPaymentTypeList());
			valueObject.put("PaymentStatusConstants", LorryHirePaymentStatus.getLorryHirePaymentStatus());
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;
		}
	}
	
	@Override
	public List<LorryHirePaymentApprovalToHireDetailsDto> getLorryHirePaymentList(String queryStr, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> 								typedQuery 				= null;
		List<Object[]> 										resultList 				= null; 
		ArrayList<LorryHirePaymentApprovalToHireDetailsDto> 			vendorHireList 			= null;
		LorryHirePaymentApprovalToHireDetailsDto 					vendorHire				= null;
		try {
			typedQuery = entityManager.createQuery("SELECT VP.approvalToHireDetailsId, VP.lorryHirePaymentId, VP.lorryHireDetailsId, VP.receiveAmount, VP.paymentTypeId,"
					+ " VP.transactionNumber, VP.transactionDate, VP.remark, VP.paymentStatusId, VP.createdOn, V.vendorName, VM.lorryHireDetailsNumber "
					+ " FROM LorryHirePaymentApprovalToHireDetails  AS VP"
					+ " INNER JOIN VendorMarketLorryHireDetails VM ON VM.lorryHireDetailsId = VP.lorryHireDetailsId"
					+ " INNER JOIN Vendor AS V ON V.vendorId = VM.vendorId "
					+ " WHERE VP.companyId ="+companyId+" "+queryStr+" AND VP.markForDelete = 0 ORDER BY VP.lorryHireDetailsId ASC", Object[].class);
			 

			resultList = typedQuery.getResultList();
			

			if (resultList != null && !resultList.isEmpty()) {
				vendorHireList = new ArrayList<>();
				for (Object[] result : resultList) {
					vendorHire = new LorryHirePaymentApprovalToHireDetailsDto();
					
					vendorHire.setApprovalToHireDetailsId((Long) result[0]);
					vendorHire.setLorryHirePaymentId((Long) result[1]);
					vendorHire.setLorryHireDetailsId((Long) result[2]);
					vendorHire.setReceiveAmount((Double) result[3]);
					vendorHire.setPaymentTypeId((short) result[4]);
					vendorHire.setTransactionNumber((String) result[5]);
					vendorHire.setTransactionDate((Timestamp) result[6]);
					vendorHire.setRemark((String) result[7]);
					vendorHire.setPaymentStatusId((short) result[8]);
					vendorHire.setCreatedOn((Timestamp) result[9]);
					vendorHire.setVendorName((String) result[10]);
					vendorHire.setLorryHireDetailsNumber((Long) result[11]);
					
					
					if(vendorHire.getTransactionDate() != null) {
						vendorHire.setTransactionDateStr(DateTimeUtility.getDateFromTimeStamp(vendorHire.getTransactionDate()));
					}
					if(vendorHire.getCreatedOn() != null) {
						vendorHire.setCreatedOnStr(DateTimeUtility.getDateFromTimeStamp(vendorHire.getCreatedOn()));
					}
					vendorHire.setPaymentType(PaymentTypeConstant.getPaymentTypeName(vendorHire.getPaymentTypeId()));
					vendorHire.setPaymentStatus(ReceivedAsConstant.getPaymentMode(vendorHire.getPaymentStatusId()));
					
					vendorHireList.add(vendorHire);
				}
			}
			
			
			return vendorHireList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			vendorHireList 			= null;
			vendorHire				= null;
		}
	}
	
	
	//Vendor Lorry Hire Details Report Start
	@Override
	public ValueObject getVendorLorryHireDetailsReport(ValueObject valueObject) throws Exception {
		String										dateRange					= null;
		int											vendorId					= 0;
		int											vehicleId					= 0;
		CustomUserDetails							userDetails					= null;
		List<VendorMarketLorryHireDetailsDto>   	vendorLorryHireDetailsList	= null;
		HashMap<String, Object> 					configuration				= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.LORRY_HIRE_CONFIG);
			
			vendorId		= valueObject.getInt("vendorId", 0); 
			vehicleId		= valueObject.getInt("vehicleId", 0); 
			dateRange		= valueObject.getString("dateRange"); 
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				
				String Vendor_Name = "", Vehicle_Type = "", Date = "";
				
				if(vehicleId > 0)
				{
					Vehicle_Type = " AND V.vid = "+ vehicleId +" "; 
				}
				if(vendorId > 0)
					Vendor_Name = " AND VN.vendorId = "+ vendorId +" ";  
				
				Date =  " AND VP.hireDate between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ; //mod needed
				
				String query = " " + Vendor_Name + " " + Vehicle_Type + " " + Date + " ";
				
				vendorLorryHireDetailsList = vendorReportDao.getVendorLorryHireDetailsReport(query, userDetails.getCompany_id());

			}
			
			valueObject.put("vendorLorryHireDetailsList", vendorLorryHireDetailsList);
			valueObject.put("configuration", configuration);
			
			
			if(vendorId > 0 ) {
				if(vendorLorryHireDetailsList != null)
				valueObject.put("VendorName", vendorLorryHireDetailsList.get(0).getVendorName());
			} else {
			valueObject.put("VendorName", "All");
			}
			if(vehicleId > 0  ) {
				if(vendorLorryHireDetailsList != null)
				valueObject.put("VehicleName", vendorLorryHireDetailsList.get(0).getVehicle_registration());
			} else {
				valueObject.put("VehicleName", "All");
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			vendorLorryHireDetailsList = null;
			userDetails			= null;
			dateRange			= null;
		}
	}
	//Vendor Lorry Hire Details Report Stop
	
		@Override
		public ValueObject getVendorPaymentInformation(ValueObject valueObject) throws Exception {
			CustomUserDetails						userDetails				= null;
			List<VendorMarketLorryHireDetailsDto>	lorryHireDetailsList	= null;
			try {
				
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				lorryHireDetailsList	=	getVendorPaymentInformationDetails(valueObject.getLong("lorryHireDetailsId"), userDetails.getCompany_id());
				
				valueObject.put("lorryHireDetailsList", lorryHireDetailsList);
				valueObject.put("paymentTypeList", PaymentTypeConstant.getPaymentTypeList());				
				valueObject.put("PaymentStatusConstants", LorryHirePaymentStatus.getLorryHirePaymentStatus());
				
				return valueObject;
			} catch (Exception e) {
				throw e;
			}finally {
				userDetails			= null;
			}
		}
		
		@Override			
		public List<VendorMarketLorryHireDetailsDto> getVendorPaymentInformationDetails(Long lorryHireDetailsId, Integer companyId)
				throws Exception {

			TypedQuery<Object[]> 								typedQuery 				= null;
			List<Object[]> 										resultList 				= null; 
			ArrayList<VendorMarketLorryHireDetailsDto> 			vendorHireList 			= null;
			VendorMarketLorryHireDetailsDto 					vendorHire				= null;
			try {
				typedQuery = entityManager.createQuery("SELECT VP.lorryHireDetailsId, VP.vendorId, VN.vendorName, VP.vid, V.vehicle_registration, "
						+ " VP.lorryHire, VP.advanceAmount, VP.balanceAmount, VP.paidAmount, LHPA.paymentStatusId, VP.hireDate, VP.otherCharges , "
						+ " VP.lorryHireDetailsNumber , LHPA.receiveAmount, LHPA.paymentTypeId, LHPA.remark, LHPA.transactionNumber, LHPA.transactionDate "
						+ " FROM VendorMarketLorryHireDetails  AS VP "
						+ " INNER JOIN LorryHirePaymentApprovalToHireDetails AS LHPA ON LHPA.lorryHireDetailsId = VP.lorryHireDetailsId "
						+ " INNER JOIN Vendor AS VN ON VP.vendorId = VN.vendorId "
						+ " INNER JOIN Vehicle V on V.vid = VP.vid"
						+ " WHERE  VP.companyId ="+companyId+" AND VP.lorryHireDetailsId="+lorryHireDetailsId
						+ " AND LHPA.paymentStatusId IN ("+LorryHirePaymentStatus.PAYMENT_MODE_PAID+", "+LorryHirePaymentStatus.PAYMENT_MODE_NEGOTIATED+","+LorryHirePaymentStatus.PAYMENT_MODE_PARTIALLY_PAID+") AND VP.markForDelete = 0 ORDER BY VP.lorryHireDetailsId ASC", Object[].class);
				

				resultList = typedQuery.getResultList();
				

				if (resultList != null && !resultList.isEmpty()) {
					vendorHireList = new ArrayList<>();
					
					for (Object[] result : resultList) {
						
						vendorHire = new VendorMarketLorryHireDetailsDto();
						
						
						if(result[0] != null)
						vendorHire.setLorryHireDetailsId((Long) result[0]);
						
						if(result[1] != null)
						vendorHire.setVendorId((Integer) result[1]);
						
						if(result[2] != null)
						vendorHire.setVendorName((String) result[2]);
						
						if(result[3] != null)
						vendorHire.setVid((Integer) result[3]);
						
						if(result[4] != null)
						vendorHire.setVehicle_registration((String) result[4]);
						
						if(result[5] != null)
						vendorHire.setLorryHire((Double) result[5]);
						
						if(result[6] != null)
						vendorHire.setAdvanceAmount((Double) result[6]);
						
						if(result[7] != null)
						vendorHire.setBalanceAmount((Double) result[7]);
						
						if(result[8] != null)
						vendorHire.setPaidAmount((Double) result[8]);
						
						if(result[9] != null)
						vendorHire.setPaymentStatusId((short) result[9]);
						
						if(result[10] != null) {
							vendorHire.setHireDate((Timestamp) result[10]);
							vendorHire.setHireDateStr(DateTimeUtility.getDateFromTimeStamp(vendorHire.getHireDate(), DateTimeUtility.DD_MM_YYYY));
						}
						
						if(result[11] != null)
						vendorHire.setOtherCharges((Double) result[11]);
						
						if(result[12] != null)
						vendorHire.setLorryHireDetailsNumber((Long) result[12]);
						
						if(result[13] != null)
						vendorHire.setReceiveAmount((Double) result[13]);
						
						if(result[14] != null)
						vendorHire.setPaymentTypeId((short) result[14]);
						
						vendorHire.setPaymentMode(PaymentTypeConstant.getPaymentTypeName((short) result[14]));
						
						if(result[15] != null)
						vendorHire.setRemark((String) result[15]);
						
						vendorHire.setPaymentStatus(LorryHirePaymentStatus.getPaymentMode(vendorHire.getPaymentStatusId()));
						
						if(result[16] != null)
						vendorHire.setTransactionNumber((String) result[16]);
						
						if(result[17] != null){
						vendorHire.setTransactionDate((Timestamp) result[17]);
						vendorHire.setTransactionDateStr(DateTimeUtility.getDateFromTimeStamp(vendorHire.getHireDate(), DateTimeUtility.DD_MM_YYYY));
						}
						vendorHireList.add(vendorHire); 

					}
				}
				return vendorHireList;
			} catch (Exception e) {
				throw e;
			} finally {
				typedQuery 				= null;
				resultList 				= null;
				vendorHireList 			= null;
				vendorHire				= null;
			}
		}
}
