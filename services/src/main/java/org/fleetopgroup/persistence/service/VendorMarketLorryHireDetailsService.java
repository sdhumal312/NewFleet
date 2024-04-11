package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.LorryHirePaymentStatus;
import org.fleetopgroup.persistence.dao.VendorMarketHireToChargesRepository;
import org.fleetopgroup.persistence.dao.VendorMarketLorryHireDetailsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VendorMarketLorryHireDetailsDto;
import org.fleetopgroup.persistence.model.VendorMarketLorryHireDetails;
import org.fleetopgroup.persistence.serviceImpl.IVendorMarketLorryHireDetailsService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendorMarketLorryHireDetailsService implements IVendorMarketLorryHireDetailsService {
	
	@PersistenceContext EntityManager entityManager;
	@Autowired	VendorMarketLorryHireDetailsRepository		vendorMarketLorryHireDetailsRepository;
	@Autowired	VendorMarketHireToChargesRepository			vendorMarketHireToChargesRepository;
	
	private static final int PAGE_SIZE = 10;
	
	@Override
	public void saveVendorMarketLorryHireDetails(VendorMarketLorryHireDetails lorryHireDetails) throws Exception {
		
		vendorMarketLorryHireDetailsRepository.save(lorryHireDetails);
	}

	@Override
	@Transactional
	public void updateLorryHireBalanceAmount(Double	amDouble, Long lorryHireDetailsId) throws Exception {
		
		entityManager.createQuery(
				" UPDATE VendorMarketLorryHireDetails  SET balanceAmount = balanceAmount -  "+amDouble+", "
					+ " paidAmount = paidAmount + "+amDouble+", otherCharges = "+amDouble+" "
					+ " WHERE lorryHireDetailsId = "+lorryHireDetailsId+" AND markForDelete = 0 ")
				.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updatePaymentStatus(long companyId,Long lorryHireDetailsId) throws Exception {
	
			try {	
				
				entityManager.createQuery(
						" UPDATE VendorMarketLorryHireDetails SET paymentStatusId = " + LorryHirePaymentStatus.PAYMENT_MODE_PAID
								+ " WHERE lorryHireDetailsId= "+lorryHireDetailsId
								+ " AND companyId = "+companyId
								+ " AND markForDelete = 0 ").executeUpdate();
		
			}	
			catch(Exception e) {
				throw e;
			}
	
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public Page<VendorMarketLorryHireDetails> getDeployment_Page_VendorLorryHire(Integer pageNumber,
			Integer companyId) {
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "lorryHireDetailsId");
		return vendorMarketLorryHireDetailsRepository.getDeployment_Page_VendorLorryHire(companyId, pageable);
	
	}
	
	@Override
	public List<VendorMarketLorryHireDetailsDto> getPageWiseLorryHireDetails(Integer pageNumber, Integer companyId)
			throws Exception {
		TypedQuery<Object[]> 								typedQuery 				= null;
		List<Object[]> 										resultList 				= null; 
		List<VendorMarketLorryHireDetailsDto> 				vendorHire				= null;
		try {
			typedQuery = entityManager.createQuery("SELECT VP.lorryHireDetailsId, VP.vendorId, VN.vendorName, VP.vid, V.vehicle_registration,"
					+ " VP.lorryHire, VP.advanceAmount, VP.balanceAmount, VP.paidAmount, VP.paymentStatusId, VP.hireDate, VP.otherCharges, VP.lorryHireDetailsNumber "
					+ " FROM VendorMarketLorryHireDetails  AS VP"
					+ " INNER JOIN Vendor AS VN ON VP.vendorId = VN.vendorId"
					+ " INNER JOIN Vehicle V on V.vid = VP.vid"
					+ " WHERE VP.companyId ="+companyId+" AND VP.markForDelete = 0 ORDER BY VP.lorryHireDetailsId DESC", Object[].class);
			 
			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				vendorHire = new ArrayList<>();
				for (Object[] result : resultList) {
					VendorMarketLorryHireDetailsDto  vendorHiredto = new VendorMarketLorryHireDetailsDto(); 
					vendorHiredto.setLorryHireDetailsId((Long) result[0]);
					vendorHiredto.setVendorId((Integer) result[1]);
					vendorHiredto.setVendorName((String) result[2]);
					vendorHiredto.setVid((Integer) result[3]);
					vendorHiredto.setVehicle_registration((String) result[4]);
					vendorHiredto.setLorryHire((Double) result[5]);					
					vendorHiredto.setAdvanceAmount((Double) result[6]);
					vendorHiredto.setBalanceAmount((Double) result[7]);
					vendorHiredto.setPaidAmount((Double) result[8]);
					vendorHiredto.setPaymentStatusId((short) result[9]);
					if(result[10] != null) {
						vendorHiredto.setHireDate((Timestamp) result[10]);
						vendorHiredto.setHireDateStr(DateTimeUtility.getDateFromTimeStamp(vendorHiredto.getHireDate(), DateTimeUtility.DD_MM_YYYY));
					}
					vendorHiredto.setOtherCharges((Double) result[11]);
					vendorHiredto.setLorryHireDetailsNumber((Long) result[12]);
					vendorHiredto.setPaymentStatus(LorryHirePaymentStatus.getPaymentMode(vendorHiredto.getPaymentStatusId()));
					vendorHire.add(vendorHiredto);
				}
			}
			return vendorHire;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			vendorHire				= null;
		}
	}
	
	@Override
	public VendorMarketLorryHireDetailsDto getVendorMarketLorryHireDetailsById(Long lorryHireDetailsId,
			Integer companyId) throws Exception {
		try {

			Query	query = entityManager.createQuery("SELECT VP.lorryHireDetailsId, VP.vendorId, VN.vendorName, VP.vid, V.vehicle_registration,"
					+ " VP.lorryHire, VP.advanceAmount, VP.balanceAmount, VP.paidAmount, VP.paymentStatusId, VP.hireDate, VP.lorryHireDetailsNumber,"
					+ " U.firstName, U2.firstName, VP.createdOn, VP.lastModifiedOn, T.tripSheetID, T.tripSheetNumber, D.driver_id, D.driver_firstname,"
					+ " D.driver_Lastname, VP.incomeId, TI.incomeName, VP.remark  "
					+ " FROM VendorMarketLorryHireDetails  AS VP"
					+ " INNER JOIN Vendor AS VN ON VP.vendorId = VN.vendorId"
					+ " INNER JOIN Vehicle V on V.vid = VP.vid "
					+ " INNER JOIN User AS U ON U.id = VP.createdById"
					+ " INNER JOIN User AS U2 ON U2.id = VP.lastModifiedById "
					+ " LEFT JOIN TripSheet T ON T.tripSheetID = VP.tripSheetId"
					+ " LEFT JOIN Driver D ON D.driver_id = VP.driverId "
					+ " LEFT JOIN TripIncome TI ON TI.incomeID = VP.incomeId"
					+ " WHERE VP.lorryHireDetailsId = "+lorryHireDetailsId+" AND VP.companyId ="+companyId+" AND VP.markForDelete = 0 ORDER BY VP.lorryHireDetailsId DESC");
			 

			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			VendorMarketLorryHireDetailsDto vendorHire;
			if (result != null) {
			
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
				
				vendorHire.setLorryHireDetailsNumber((Long) result[11]);
				vendorHire.setCreatedBy((String) result[12]);
				vendorHire.setLastModifiedBy((String) result[13]);
				vendorHire.setCreatedOnStr(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[14], DateTimeUtility.DD_MM_YYYY_HH_MM_AA));
				vendorHire.setLastModifiedOnStr(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[15], DateTimeUtility.DD_MM_YYYY_HH_MM_AA));
				vendorHire.setTripSheetId((Long) result[16]);
				vendorHire.setTripSheetNumber("TS-"+(Long) result[17]);
				vendorHire.setDriverId(Long.parseLong(result[18]+""));
				vendorHire.setDriverName((String) result[19]);
				if((String) result[20] != null )
					vendorHire.setDriverName(vendorHire.getDriverName() + (String) result[20]);
				vendorHire.setIncomeId((Integer) result[21]);
				vendorHire.setIncomeName((String) result[22]);
				vendorHire.setRemark((String) result[23]);
				
				vendorHire.setPaymentStatus(LorryHirePaymentStatus.getPaymentMode(vendorHire.getPaymentStatusId()));

			} else {
				return null;
			}

			return vendorHire;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updatePaidAmountAndBalanceOnChargeDelete(Long lorryHireDetailsId, Integer companyId) throws Exception {
		try {
			VendorMarketLorryHireDetailsDto	detailsDto	=	getVendorMarketLorryHireDetailsById(lorryHireDetailsId, companyId);
			Double allCharges	= vendorMarketHireToChargesRepository.getMarketHireCharges(lorryHireDetailsId, companyId);
			if(allCharges == null) {
				allCharges	= 0.0;
			}
			entityManager.createQuery(
					" UPDATE VendorMarketLorryHireDetails  SET balanceAmount = "+(detailsDto.getLorryHire() - (detailsDto.getAdvanceAmount()+allCharges)) +", paidAmount = "+(detailsDto.getAdvanceAmount()+allCharges)+" "
							+ " WHERE lorryHireDetailsId = "+lorryHireDetailsId+" AND markForDelete = 0 ")
					.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	@Transactional
	public void deleteVendorMarketLorryHireDetails(Long lorryHireDetailsId, CustomUserDetails userDetails)
			throws Exception {
		entityManager.createQuery(
				" UPDATE VendorMarketLorryHireDetails  SET markForDelete = 1, lastModifiedById = "+userDetails.getId()+", lastModifiedOn = '"+DateTimeUtility.getCurrentTimeStamp()+"' "
						+ " WHERE lorryHireDetailsId = "+lorryHireDetailsId+" AND companyId = "+userDetails.getCompany_id()+" ")
				.executeUpdate();
		
	}
	
	@Override
	public double getLorryHireBalanceByVendorId(Integer vendorId) throws Exception {
			double balanceAmount	= 0.0;
		Query	query = entityManager.createQuery("SELECT SUM(balanceAmount) FROM VendorMarketLorryHireDetails where paymentStatusId IN ("+LorryHirePaymentStatus.PAYMENT_MODE_NOT_PAID+","+LorryHirePaymentStatus.PAYMENT_MODE_PARTIALLY_PAID+") AND markForDelete = 0");

		try {
			balanceAmount = (double) query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		
		return Utility.round(balanceAmount, 2);
	}
	
	@Override			//refer
	public List<VendorMarketLorryHireDetailsDto> getVendorLorryHirePaymentData(Integer vendorId, Integer companyId)
			throws Exception {

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
					+ " WHERE VP.vendorId = "+vendorId+" AND VP.companyId ="+companyId+" AND VP.paymentStatusId IN ("+LorryHirePaymentStatus.PAYMENT_MODE_NOT_PAID+", "+LorryHirePaymentStatus.PAYMENT_MODE_PARTIALLY_PAID+") AND VP.markForDelete = 0 ORDER BY VP.lorryHireDetailsId ASC", Object[].class);
			 

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
	
	@Override
	public List<VendorMarketLorryHireDetailsDto> getVendorLorryHirePaymentData(String query, Integer companyId)
			throws Exception {

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
					+ " WHERE VP.companyId ="+companyId+" "+query+" AND VP.paymentStatusId IN ("+LorryHirePaymentStatus.PAYMENT_MODE_NOT_PAID+", "+LorryHirePaymentStatus.PAYMENT_MODE_PARTIALLY_PAID+") AND VP.markForDelete = 0 ORDER BY VP.lorryHireDetailsId ASC", Object[].class);
			 

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
					
					vendorHire.setPaymentStatusAction("<a style=\"color: blue; background: #ffc;\" target='_blank' href='makeLorryHirePayment.in?vendorId="+vendorHire.getVendorId()+"&lorryHireDetailsId="+vendorHire.getLorryHireDetailsId()+"' >"+LorryHirePaymentStatus.getPaymentMode(vendorHire.getPaymentStatusId())+"</a>");
					
					
					
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
	
	@Override
	@Transactional
	public void updateLorryHireDetails(ValueObject	valueObject, ValueObject	object) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) valueObject.get("userDetails");
		entityManager.createQuery(
				" UPDATE VendorMarketLorryHireDetails  SET balanceAmount = balanceAmount - "+object.getDouble("receiveAmt")+", paymentStatusId = "+object.getShort("paymentType")+", paymentDate = '"+DateTimeUtility.getCurrentTimeStamp()+"' "
						+ " ,lorryHirePaymentId = "+valueObject.getLong("lorryHirePaymentId")+" , paymentById ="+userDetails.getId()+", paidAmount = paidAmount + "+object.getDouble("receiveAmt")+"  "
						+ " WHERE lorryHireDetailsId = "+object.getLong("lorryHireDetailsId")+" AND companyId = "+userDetails.getCompany_id()+" ")
				.executeUpdate();
		
	
		
	}
	//check
	@Override
	public VendorMarketLorryHireDetails getVendorMarketLorryHireDetailsByLorryHireIdentity(Integer companyId,Long lorryHireDetailsId) throws Exception {
		
		return vendorMarketLorryHireDetailsRepository.getVendorMarketLorryHireDetailsInformation(companyId,lorryHireDetailsId);
	}
}
