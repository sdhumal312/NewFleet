package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.LorryHirePaymentStatus;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.persistence.bl.TripSheetMobileBL;
import org.fleetopgroup.persistence.bl.VendorLorryHireBL;
import org.fleetopgroup.persistence.dao.VendorMarketHireToChargesRepository;
import org.fleetopgroup.persistence.dao.VendorMarketLorryHireDetailsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VendorMarketHireToChargesDto;
import org.fleetopgroup.persistence.dto.VendorMarketLorryHireDetailsDto;
import org.fleetopgroup.persistence.model.LorryHirePaymentApprovalToHireDetails;
import org.fleetopgroup.persistence.model.LorryHirePaymentSequenceCounter;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetHistory;
import org.fleetopgroup.persistence.model.TripSheetIncome;
import org.fleetopgroup.persistence.model.VendorLorryHirePaymentApproval;
import org.fleetopgroup.persistence.model.VendorLorryHireSequenceCounter;
import org.fleetopgroup.persistence.model.VendorMarketHireToCharges;
import org.fleetopgroup.persistence.model.VendorMarketLorryHireDetails;
import org.fleetopgroup.persistence.serviceImpl.ILorryHirePaymentSequenceService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetHistoryService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVendorLorryHireDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVendorLorryHirePaymentApprovalService;
import org.fleetopgroup.persistence.serviceImpl.IVendorLorryHireSequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IVendorMarketHireToChargesService;
import org.fleetopgroup.persistence.serviceImpl.IVendorMarketLorryHireDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendorLorryHireDetailsService implements IVendorLorryHireDetailsService {

	@PersistenceContext EntityManager entityManager;
	@Autowired	IVendorMarketLorryHireDetailsService		vendorMarketLorryHireDetailsService;
	@Autowired	VendorMarketHireToChargesRepository			vendorMarketHireToChargesRepository;
	@Autowired	IVendorLorryHireSequenceCounterService		vendorLorryHireSequenceCounterService;
	@Autowired	IVendorMarketHireToChargesService			vendorMarketHireToChargesService;
	@Autowired	ILorryHirePaymentSequenceService			lorryHirePaymentSequenceService;
	@Autowired	IVendorLorryHirePaymentApprovalService		vendorLorryHirePaymentApprovalService;
	@Autowired	VendorMarketLorryHireDetailsRepository		vendorMarketLorryHireDetailsRepository;
	@Autowired	private ITripSheetService					tripSheetService;
	@Autowired	private IUserProfileService					userProfileService;
	@Autowired	private	IVendorService						vendorService;
	@Autowired  private ITripSheetHistoryService 			tripSheetHistoryService; 
	
	TripSheetMobileBL	tripSheetMobileBL			= new TripSheetMobileBL();
	
	VendorLorryHireBL	lorryHireBL	= new VendorLorryHireBL();
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject saveVendorLorryHireDetails(ValueObject valueObject) throws Exception {
		ArrayList<ValueObject> 				dataArrayObjColl 					= null;
		CustomUserDetails					userDetails							= null;
		VendorMarketLorryHireDetails		lorryHireDetails					= null;
		VendorMarketHireToCharges			vendorMarketHireToCharges			= null;
		Double								totalCharges						= 0.0;
		VendorLorryHireSequenceCounter		sequenceCounter						= null;
	    Double								getBalanceAmount					= 0.0;
	    Double								lorryHireAmount						= 0.0;
	    Double								advance								= 0.0;
	    						
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			valueObject.put("userDetails", userDetails);
			
			sequenceCounter		= vendorLorryHireSequenceCounterService.findNextNumber(userDetails.getCompany_id());
		
			if(sequenceCounter != null) {
				
				lorryHireDetails	= lorryHireBL.getVendorMarketLorryHireDetails(valueObject);
				lorryHireDetails.setLorryHireDetailsNumber(sequenceCounter.getNextVal());
				
				vendorMarketLorryHireDetailsService.saveVendorMarketLorryHireDetails(lorryHireDetails);
				
				dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("expenseDetails");
				
				if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
					for (ValueObject object : dataArrayObjColl) {
							vendorMarketHireToCharges	= lorryHireBL.getVendorMarketHireToCharges(valueObject, object);
						if(vendorMarketHireToCharges != null) {
							vendorMarketHireToCharges.setLorryHireDetailsId(lorryHireDetails.getLorryHireDetailsId());
							totalCharges += vendorMarketHireToCharges.getAmount();
							
							vendorMarketHireToChargesRepository.save(vendorMarketHireToCharges);
						}
					}
				}
				
				if(totalCharges > 0) {
					vendorMarketLorryHireDetailsService.updateLorryHireBalanceAmount(totalCharges, lorryHireDetails.getLorryHireDetailsId());
				}
				
				lorryHireAmount = lorryHireDetails.getLorryHire();
				advance = lorryHireDetails.getAdvanceAmount();
				getBalanceAmount = lorryHireAmount - (advance + totalCharges);
				
				if(getBalanceAmount == 0){
					vendorMarketLorryHireDetailsService.updatePaymentStatus(userDetails.getCompany_id(),lorryHireDetails.getLorryHireDetailsId());
				}
				
				if(lorryHireAmount > 0 && lorryHireDetails.getTripSheetId() > 0) {
					
					UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getId());

					TripSheet	tripSheet = new TripSheet(lorryHireDetails.getTripSheetId());
					
					TripSheetIncome	sheetIncome	= new TripSheetIncome();
					sheetIncome.setIncomeId(valueObject.getInt("incomeId",0));
					sheetIncome.setIncomeAmount(lorryHireAmount);
					sheetIncome.setNetIncomeAmount(lorryHireAmount);
					sheetIncome.setIncomePlaceId(Orderingname.getBranch_id());
					sheetIncome.setIncomeRefence("LH-"+lorryHireDetails.getLorryHireDetailsNumber());
					sheetIncome.setIncomeFixedId((short) 0);
					sheetIncome.setCompanyId(userDetails.getCompany_id());
					sheetIncome.setCreatedById(userDetails.getId());
					sheetIncome.setCreated(new Date());
					sheetIncome.setTripsheet(tripSheet);
					sheetIncome.setIncomeCollectedById(userDetails.getId());
					
					TripSheet SheetOLDData = tripSheetService.getTripSheetDetails(valueObject.getLong("tripSheetId"));
					TripSheetHistory	tripSheetHistory	= tripSheetMobileBL.prepareTripSheetHistory(SheetOLDData);
					
					tripSheetService.addTripSheetIncome(sheetIncome);
					
					tripSheetHistoryService.addTripSheetHistory(tripSheetHistory);
					
				}
				
				
			}else {
				valueObject.put("sequenceNotFound", true);
			}
			
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	

	@Override
	public ValueObject getPageWiseVendorLorryHireDetails(ValueObject valueObject) throws Exception {

		CustomUserDetails				userDetails 				= null;
		Integer							pageNumber					= null;
		ValueObject						valOutObject				= null;
		try {
			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valOutObject		= new ValueObject();
			pageNumber			= valueObject.getInt("pageNumber",0);
			Page<VendorMarketLorryHireDetails> page = vendorMarketLorryHireDetailsService.getDeployment_Page_VendorLorryHire(pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valOutObject.put("deploymentLog", page);
			valOutObject.put("beginIndex", begin);
			valOutObject.put("endIndex", end);
			valOutObject.put("currentIndex", current);

			valOutObject.put("vendorLorryHireCount", page.getTotalElements());
			
			List<VendorMarketLorryHireDetailsDto> vendorLorryHireList = vendorMarketLorryHireDetailsService.getPageWiseLorryHireDetails(pageNumber, userDetails.getCompany_id());
			valOutObject.put("vendorLorryHireList", vendorLorryHireList);
			valOutObject.put("SelectPage", pageNumber);
			
			return valOutObject;
		} catch (NullPointerException e) {
			throw e;
		} finally {
			userDetails			= null;
			pageNumber			= null;
			valOutObject		= null;
		}
	
	}
	
	@Override
	public ValueObject getLorryHireDetailsById(ValueObject valueObject) throws Exception {
		
		VendorMarketLorryHireDetailsDto			lorryHireDetailsDto	= null;
		List<VendorMarketHireToChargesDto>		hireToChargesList	= null;
		CustomUserDetails						userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			lorryHireDetailsDto	=	vendorMarketLorryHireDetailsService.getVendorMarketLorryHireDetailsById(valueObject.getLong("lorryHireDetailsId"), userDetails.getCompany_id());
			hireToChargesList	=	vendorMarketHireToChargesService.getVendorMarketHireToChargesList(valueObject.getLong("lorryHireDetailsId"), userDetails.getCompany_id());
			
			valueObject.put("lorryHireDetails", lorryHireDetailsDto);
			valueObject.put("hireToChargesList", hireToChargesList);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			lorryHireDetailsDto	= null;
			hireToChargesList	= null;
			userDetails			= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteLorryHireChargesDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails			= null;
		VendorMarketHireToCharges			data  	= null;
		double 								amount	= 0.0;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vendorMarketHireToChargesService.deleteMarketHireCharges(valueObject.getLong("hireToChargesId"), userDetails.getCompany_id());
			
			vendorMarketLorryHireDetailsService.updatePaidAmountAndBalanceOnChargeDelete(valueObject.getLong("lorryHireDetailsId"), userDetails.getCompany_id());
			
			data=vendorMarketHireToChargesService.getAmountFromVendorMarketHireToCharges(valueObject.getLong("hireToChargesId"), userDetails.getCompany_id());
			amount=data.getAmount();
			
			updateLorryExpenseDetailsInfoDetailsDelete(amount, valueObject.getLong("lorryHireDetailsId"));
			
			valueObject.put("deleted", true);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteLorryHireInvoice(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails			= null;
		
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vendorMarketHireToChargesService.deleteMarketHireChargesById(valueObject.getLong("lorryHireDetailsId"), userDetails.getCompany_id());
			
			vendorMarketLorryHireDetailsService.deleteVendorMarketLorryHireDetails(valueObject.getLong("lorryHireDetailsId"), userDetails);
			
			valueObject.put("deleted", true);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;
		}
	}
	
	//new
	@Override
	@Transactional
	public void updateLorryExpenseDetailsInfoDetailsDelete(double amount, long id) throws Exception {
		CustomUserDetails						userDetails					= null;
		java.util.Date lastModifiedOn ;
		try {
		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		lastModifiedOn = new java.util.Date();
		Timestamp sqlDate = new java.sql.Timestamp(lastModifiedOn.getTime());
		
		entityManager.createQuery(
				" UPDATE VendorMarketLorryHireDetails AS VC SET VC.otherCharges = VC.otherCharges - "+amount+", "
						+ " VC.lastModifiedOn = '"+sqlDate+"', VC.lastModifiedById = "+userDetails.getId()+" "
						+ " WHERE VC.lorryHireDetailsId = "+id+" "
						+ " AND VC.markForDelete = 0 ")
		.executeUpdate();
		
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getVendorLorryHirePaymentData(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails				= null;
		List<VendorMarketLorryHireDetailsDto>	lorryHireDetailsList	= null;	
		try {
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			String query = "";
			if(valueObject.getInt("vendorId", 0) > 0)
				query += " AND VP.vendorId = "+valueObject.getInt("vendorId", 0)+" ";
			if(valueObject.getLong("lorryHireDetailsId",0) > 0)
				query += " AND VP.lorryHireDetailsId = "+valueObject.getLong("lorryHireDetailsId", 0)+" ";
			
			lorryHireDetailsList	=	vendorMarketLorryHireDetailsService.getVendorLorryHirePaymentData(query, userDetails.getCompany_id());
			
			valueObject.put("lorryHireDetailsList", lorryHireDetailsList);
			valueObject.put("paymentTypeList", PaymentTypeConstant.getPaymentTypeList());
			valueObject.put("PaymentStatusConstants", LorryHirePaymentStatus.getLorryHirePaymentStatus());
			valueObject.put("vendor", vendorService.getVendor(valueObject.getInt("vendorId", 0)));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject savLorryHirePaymentDetails(ValueObject valueObject) throws Exception {
		ArrayList<ValueObject> 					dataArrayObjColl 					= null;
		CustomUserDetails						userDetails							= null;
		VendorLorryHirePaymentApproval			lorryHireDetails					= null;
		LorryHirePaymentApprovalToHireDetails	vendorMarketHireToCharges			= null;
		LorryHirePaymentSequenceCounter			sequenceCounter						= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			valueObject.put("userDetails", userDetails);
			
			sequenceCounter		= lorryHirePaymentSequenceService.findNextNumber(userDetails.getCompany_id());
		
			if(sequenceCounter != null) {

				lorryHireDetails	= lorryHireBL.getVendorLorryHirePaymentApproval(valueObject);
				lorryHireDetails.setLorryHirePaymentNumber(sequenceCounter.getNextVal());
				
				vendorLorryHirePaymentApprovalService.saveVendorLorryHirePaymentApproval(lorryHireDetails);
				
				valueObject.put("lorryHirePaymentId", lorryHireDetails.getLorryHirePaymentId());
				
				dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("lorryHirePaymentDetails");
				
				if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
					for (ValueObject object : dataArrayObjColl) {
							vendorMarketHireToCharges	= lorryHireBL.getLorryHirePaymentApprovalToHireDetails(valueObject, object);
						if(vendorMarketHireToCharges != null) {
							vendorLorryHirePaymentApprovalService.saveVendorApprovalToHireDetails(vendorMarketHireToCharges);
							
							vendorMarketLorryHireDetailsService.updateLorryHireDetails(valueObject, object);
						}
					}
				}
				
					
			}else {
				valueObject.put("sequenceNotFound", true);
			}
			
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject saveExpenseDetailsInfo(ValueObject valueObject) throws Exception {
		ArrayList<ValueObject> 				dataArrayObjColl 								= null;
		List<VendorMarketHireToCharges>		expenseTransfer									= null;
		double								amnt											= 0.0;
		double								lorryHireAmount									= 0.0;
		double								advance											= 0.0;
		CustomUserDetails					userDetails										= null;
		VendorMarketLorryHireDetails		vendorMarketLorryHireDetailsForBalance			= null;
		Double								getBalanceAmount								= 0.0;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			valueObject.put("userDetails", userDetails);
			
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("ExpenseTransfer");
			
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				expenseTransfer = new ArrayList<VendorMarketHireToCharges>();
				for(ValueObject chargeTransfer : dataArrayObjColl) {
					expenseTransfer.add(lorryHireBL.saveExpenseDetailsInfo(chargeTransfer, valueObject));
					amnt += chargeTransfer.getDouble("routeAmount");
				}
				
				vendorMarketHireToChargesRepository.saveAll(expenseTransfer);
				
				if(amnt > 0) {
					updateLorryExpenseDetailsInfoDetails(amnt, valueObject.getLong("lorryHireDetailsId"));
				}
				
				vendorMarketLorryHireDetailsForBalance = vendorMarketLorryHireDetailsService.getVendorMarketLorryHireDetailsByLorryHireIdentity(userDetails.getCompany_id(),valueObject.getLong("lorryHireDetailsId"));
				
				lorryHireAmount = vendorMarketLorryHireDetailsForBalance.getLorryHire();
				
				advance = vendorMarketLorryHireDetailsForBalance.getAdvanceAmount();
				
				getBalanceAmount = vendorMarketLorryHireDetailsForBalance.getBalanceAmount();
				
				getBalanceAmount = lorryHireAmount - (advance + vendorMarketLorryHireDetailsForBalance.getOtherCharges());
				
				if(getBalanceAmount == 0){
					vendorMarketLorryHireDetailsService.updatePaymentStatus(userDetails.getCompany_id(),vendorMarketLorryHireDetailsForBalance.getLorryHireDetailsId());
				}
				
			}
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			throw e;
		}finally {
			dataArrayObjColl	= null;
			expenseTransfer	    = null;
		}
	}

	@Override
	@Transactional
	public void updateLorryExpenseDetailsInfoDetails(double amount, long id) throws Exception {
		CustomUserDetails						userDetails					= null;
		java.util.Date lastModifiedOn ;
		try {
		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		lastModifiedOn = new java.util.Date();
		Timestamp sqlDate = new java.sql.Timestamp(lastModifiedOn.getTime());
		entityManager.createQuery(
				" UPDATE VendorMarketLorryHireDetails AS VC SET VC.balanceAmount = VC.balanceAmount - "+amount+", "
						+ " VC.paidAmount = VC.paidAmount + "+amount+", VC.otherCharges = VC.otherCharges + "+amount+","
						+ " VC.lastModifiedOn = '"+sqlDate+"', VC.lastModifiedById = "+userDetails.getId()+" "
						+ " WHERE VC.lorryHireDetailsId = "+id+" "
						+ " AND VC.markForDelete = 0 ")
		.executeUpdate();
		
		}catch(Exception e) {
			throw e;
		}
	}
	
	//new
	@Override
	public VendorMarketLorryHireDetailsDto getPaymentStatusIdByLorryHireDetailsId(Long lorryHireDetailsId ) throws Exception {
		CustomUserDetails						userDetails				= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			Query query1 = entityManager.createQuery(
					" SELECT VM.lorryHireDetailsId, VM.paymentStatusId FROM VendorMarketLorryHireDetails as VM  " 
							+ " WHERE VM.lorryHireDetailsId = " +lorryHireDetailsId
							+ " AND VM.companyId = "+userDetails.getCompany_id() 
							+ " AND VM.markForDelete = 0 ");
			Object[] result1 = null;
			try {
				result1 = (Object[]) query1.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			VendorMarketLorryHireDetailsDto list = null;
			if (result1 != null) {
				list = new VendorMarketLorryHireDetailsDto();
				list.setLorryHireDetailsId((Long) result1[0] );
				list.setPaymentStatusId((short) result1[1]);
				
			}
			
			
			return list;
			//return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;
		}
	}

	@Override
	public List<VendorMarketLorryHireDetailsDto> getLorryHireListByTripSheetId(Long tripSheetId) throws Exception {

		TypedQuery<Object[]> 								typedQuery 				= null;
		List<Object[]> 										resultList 				= null; 
		ArrayList<VendorMarketLorryHireDetailsDto> 			vendorHireList 			= null;
		VendorMarketLorryHireDetailsDto 					vendorHire				= null;
		
		try {
			
			typedQuery = entityManager.createQuery("SELECT VP.lorryHireDetailsId, VP.vendorId, VN.vendorName, VP.vid, V.vehicle_registration,"
					+ " VP.lorryHire, VP.advanceAmount, VP.balanceAmount, VP.paidAmount, VP.paymentStatusId, VP.hireDate, VP.otherCharges , VP.lorryHireDetailsNumber "
					+ " FROM VendorMarketLorryHireDetails  AS VP"
					+ " INNER JOIN Vendor AS VN ON VP.vendorId = VN.vendorId"
					+ " INNER JOIN Vehicle V on V.vid = VP.vid"
					+ " WHERE VP.tripSheetId = "+tripSheetId+" AND VP.markForDelete = 0 ORDER BY VP.lorryHireDetailsId ASC", Object[].class);
			 

			resultList = typedQuery.getResultList();
			

			if (resultList != null && !resultList.isEmpty()) {
				vendorHireList = new ArrayList<>();
				
				for (Object[] result : resultList) {
					vendorHire = new VendorMarketLorryHireDetailsDto();
					
					vendorHire.setLorryHireDetailsId((Long) result[0]);
					vendorHire.setVendorId((Integer) result[1]);
					vendorHire.setVendorName((String) result[2]);
					vendorHire.setVid((Integer) result[3]);
					vendorHire.setVehicle_registration((String) result[4]);
					vendorHire.setLorryHire((Double) result[5]);					
					vendorHire.setAdvanceAmount((Double) result[6]);
					vendorHire.setBalanceAmount((Double) result[7]);
					vendorHire.setPaidAmount((Double) result[8]);
					vendorHire.setPaymentStatusId((short) result[9]);
					if(result[10] != null) {
						vendorHire.setHireDate((Timestamp) result[10]);
						vendorHire.setHireDateStr(DateTimeUtility.getDateFromTimeStamp(vendorHire.getHireDate(), DateTimeUtility.DD_MM_YYYY));
					}
					
					vendorHire.setOtherCharges((Double) result[11]);
					vendorHire.setLorryHireDetailsNumber((Long) result[12]);
					vendorHire.setPaymentStatus(LorryHirePaymentStatus.getPaymentMode(vendorHire.getPaymentStatusId()));
					
					
					vendorHireList.add(vendorHire); //latest

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
