package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.VendorMarketHireToChargesRepository;
import org.fleetopgroup.persistence.dto.VendorMarketHireToChargesDto;
import org.fleetopgroup.persistence.model.VendorMarketHireToCharges;
import org.fleetopgroup.persistence.serviceImpl.IVendorMarketHireToChargesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendorMarketHireToChargesService implements IVendorMarketHireToChargesService {

	@PersistenceContext EntityManager entityManager;
	@Autowired	VendorMarketHireToChargesRepository		vendorMarketHireToChargesRepository;
	
	@Override
	public List<VendorMarketHireToChargesDto> getVendorMarketHireToChargesList(Long lorryHireDetailsId,
			Integer companyId) throws Exception {

		TypedQuery<Object[]> 								typedQuery 				= null;
		List<Object[]> 										resultList 				= null; 
		ArrayList<VendorMarketHireToChargesDto> 			vendorHireList 			= null;
		VendorMarketHireToChargesDto	 					vendorHire				= null;
		try {
			typedQuery = entityManager.createQuery("SELECT VP.hireToChargesId, VP.chargeMasterId, VP.lorryHireDetailsId, VP.amount, VP.identifier,"
					+ " TE.expenseName"
					+ " FROM VendorMarketHireToCharges  AS VP "
					+ " INNER JOIN TripExpense TE ON TE.expenseID = VP.chargeMasterId"
					+ " WHERE VP.lorryHireDetailsId = "+lorryHireDetailsId+" AND VP.companyId ="+companyId+" AND VP.markForDelete = 0 ORDER BY VP.lorryHireDetailsId DESC", Object[].class);
			 

			resultList = typedQuery.getResultList();
			

			if (resultList != null && !resultList.isEmpty()) {
				vendorHireList = new ArrayList<>();
				for (Object[] result : resultList) {
					vendorHire = new VendorMarketHireToChargesDto();
					
					vendorHire.setHireToChargesId((Long) result[0]);
					vendorHire.setChargeMasterId((Integer) result[1]);
					vendorHire.setLorryHireDetailsId((Long) result[2]);
					vendorHire.setAmount((Double) result[3]);
					vendorHire.setIdentifier((short) result[4]);
					vendorHire.setChargeName((String) result[5]);
					
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

	@Override
	@Transactional
	public void deleteMarketHireCharges(Long hireToChargesId, Integer companyId) throws Exception {
		
		vendorMarketHireToChargesRepository.deleteMarketHireCharges(hireToChargesId, companyId);
	}
	
	@Override
	@Transactional
	public void deleteMarketHireChargesById(Long lorryHireDetailsId, Integer companyId) throws Exception {
		
		vendorMarketHireToChargesRepository.deleteMarketHireChargesById(lorryHireDetailsId, companyId);
	}

	@Override
	public VendorMarketHireToCharges getAmountFromVendorMarketHireToCharges(long hireToChargesId,
			Integer company_id) throws Exception {
		
		return vendorMarketHireToChargesRepository.getAmountFromVendorMarketHireToCharges(hireToChargesId,company_id);
		
	}

	

	
}
