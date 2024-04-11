package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.dao.VendorFixedLaundryRateRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VendorDto;
import org.fleetopgroup.persistence.dto.VendorFixedLaundryRateDto;
import org.fleetopgroup.persistence.model.VendorFixedLaundryRate;
import org.fleetopgroup.persistence.serviceImpl.IVendorFixedLaundryRateService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendorFixedLaundryRateService implements IVendorFixedLaundryRateService {

	@Autowired	private IVendorService							vendorService;
	@Autowired	private VendorFixedLaundryRateRepository		vendorFixedLaundryRateRepository;
	@PersistenceContext	EntityManager entityManager;

	VendorBL VenBL = new VendorBL();
	
	private static final int PAGE_SIZE = 10;
	
	@Override
	public ValueObject getPageWiseVendorLaundryRate(ValueObject valueObject) throws Exception {
		VendorDto		vendor		= null;
		Integer			vendorId	= 0;
		Integer			pageNumber	= 0;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vendor	= vendorService.getVendorDetails(valueObject.getInt("vendorId", 0), userDetails.getCompany_id());
			pageNumber	= valueObject.getInt("pageNumber");
			vendorId	= valueObject.getInt("vendorId");
			if(vendor != null) {
				Page<VendorFixedLaundryRate> page = Get_Deployment_Page_VendorFixedLaundryPrice(vendorId, pageNumber, userDetails.getCompany_id());
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());
				
				valueObject.put("deploymentLog", page);
				valueObject.put("beginIndex", begin);
				valueObject.put("endIndex", end);
				valueObject.put("currentIndex", current);
				valueObject.put("VendorCount", page.getTotalElements());
				
				List<VendorFixedLaundryRateDto> pageList = list_VendorFixedLaundryPrice(vendorId, pageNumber, userDetails.getCompany_id());
				
				valueObject.put("vendorFixed", pageList);
				
				VendorDto vendorDto = VenBL.getVendorDetails(vendor);
				
				valueObject.put("vendor", vendorDto);
				
				valueObject.put("SelectPage", pageNumber);
				valueObject.put("SelectType", vendorDto.getVendorTypeId());
			}
			valueObject.put("vendor", vendor);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			vendor		= null;
		}
	}
	
	@Override
	public Page<VendorFixedLaundryRate> Get_Deployment_Page_VendorFixedLaundryPrice(Integer vendorId,
			Integer pageNumber, Integer companyId) {

		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return vendorFixedLaundryRateRepository.Get_Deployment_Page_VendorFixedLaundryPrice(vendorId, companyId, pageable);
	}
	
	@Override
	public List<VendorFixedLaundryRateDto> list_VendorFixedLaundryPrice(Integer vendorId, Integer pageNumber,
			Integer companyId) {
		// Note: this VendorFixed Price List

		/* this only Select column */
		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT R.vendorLaundryRateId, R.vendorId, R.clothTypesId, R.clothEachCost, R.clothDiscount, R.clothGst, "
						+ "R.clothTotal, R.createdById, CT.clothTypeName, U.firstName "
						+ " FROM VendorFixedLaundryRate AS R"
						+ " INNER JOIN ClothTypes CT ON CT.clothTypesId = R.clothTypesId "
						+ " INNER JOIN User U ON U.id = R.createdById"
						+ " WHERE R.vendorId="
						+ vendorId + " AND R.markForDelete = 0  AND R.companyId = "+companyId+" ORDER BY R.vendorLaundryRateId desc", Object[].class);
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		List<Object[]> results = queryt.getResultList();

		List<VendorFixedLaundryRateDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VendorFixedLaundryRateDto>();
			VendorFixedLaundryRateDto list = null;
			for (Object[] result : results) {
				list = new VendorFixedLaundryRateDto();

				list.setVendorLaundryRateId((Long) result[0]);
				list.setVendorId((Integer) result[1]);
				list.setClothTypesId((Long) result[2]);
				list.setClothEachCost((Double) result[3]);
				list.setClothDiscount((Double) result[4]);
				list.setClothGst((Double) result[5]);
				list.setClothTotal((Double) result[6]);
				list.setCreatedById((Long) result[7]);
				list.setClothTypeName((String) result[8]);
				list.setCreatedBy((String) result[9]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}
	
	@Override
	public ValueObject saveVendorLaundryRate(ValueObject valueObject) throws Exception {
		CustomUserDetails					userDetails			= null;
		List<VendorFixedLaundryRate>		validate			= null;
		try {
			
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			
			VendorFixedLaundryRate	laundryRate = new VendorFixedLaundryRate();

			laundryRate.setVendorId(valueObject.getInt("vendorId"));
			laundryRate.setClothEachCost(valueObject.getDouble("parteachcost", 0));
			laundryRate.setClothDiscount(valueObject.getDouble("discount", 0));
			laundryRate.setClothTotal(valueObject.getDouble("tatalcost", 0));
			laundryRate.setClothGst(valueObject.getDouble("tax", 0));
			laundryRate.setClothTypesId(valueObject.getLong("clothTypes"));
			laundryRate.setCompanyId(userDetails.getCompany_id());
			laundryRate.setCreatedById(userDetails.getId());
			laundryRate.setCreationDate(DateTimeUtility.getCurrentDate());
			laundryRate.setLastModifiedById(userDetails.getId());
			laundryRate.setLastUpdated(DateTimeUtility.getCurrentDate());
			
			validate	=	vendorFixedLaundryRateRepository.validateVendorFixedLaundryRate(laundryRate.getClothTypesId(), laundryRate.getVendorId());
			
			if(validate == null || validate.isEmpty()) {
				vendorFixedLaundryRateRepository.save(laundryRate);
				valueObject.put("saved", true);
			}else {
				valueObject.put("already", true);
			}
			
			
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			valueObject.put("error", true);
			return valueObject;
		}finally {
			userDetails		= null;
		}
	}
	
	@Override
	public ValueObject updateVendorLaundryRate(ValueObject valueObject) throws Exception {
		CustomUserDetails	userDetails			= null;
		try {
			
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			
			VendorFixedLaundryRate	laundryRate = new VendorFixedLaundryRate();
			if(valueObject.containsKey("vendorLaundryRateId"))
				laundryRate.setVendorLaundryRateId(valueObject.getLong("vendorLaundryRateId"));
			laundryRate.setVendorId(valueObject.getInt("vendorId"));
			laundryRate.setClothEachCost(valueObject.getDouble("parteachcost", 0));
			laundryRate.setClothDiscount(valueObject.getDouble("discount", 0));
			laundryRate.setClothTotal(valueObject.getDouble("tatalcost", 0));
			laundryRate.setClothGst(valueObject.getDouble("tax", 0));
			laundryRate.setClothTypesId(valueObject.getLong("clothTypes"));
			laundryRate.setCompanyId(userDetails.getCompany_id());
			laundryRate.setCreatedById(userDetails.getId());
			laundryRate.setCreationDate(DateTimeUtility.getCurrentDate());
			laundryRate.setLastModifiedById(userDetails.getId());
			laundryRate.setLastUpdated(DateTimeUtility.getCurrentDate());
			
			vendorFixedLaundryRateRepository.save(laundryRate);
			
			valueObject.put("saved", true);
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			valueObject.put("error", true);
			return valueObject;
		}finally {
			userDetails		= null;
		}
	}
	
	@Override
	public ValueObject getLaundryRateById(ValueObject valueObject) throws Exception {
		try {
			valueObject.put("rate", vendorFixedLaundryRateRepository.findById(valueObject.getLong("rateId")).get());
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteLaundryRate(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vendorFixedLaundryRateRepository.deleteLaundryRate(valueObject.getLong("rateId"), userDetails.getCompany_id());
		
			valueObject.put("deleted", true);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails		= null;
		}
	}
	
	@Override
	public VendorFixedLaundryRateDto getVendorRateAndLocationQuantity(Integer locationId, Long clothTypesId,
			Integer vendorId) throws Exception {
		
		Query query = entityManager.createQuery(
				"SELECT VR.vendorLaundryRateId, VR.vendorId, VR.clothTypesId, VR.clothEachCost, VR.clothDiscount, VR.clothGst, VR.clothTotal,"
				+ " CS.usedStockQuantity, CS.newStockQuantity "
				+ " FROM ClothInventoryStockTypeDetails CS "
				+ " LEFT JOIN VendorFixedLaundryRate VR ON VR.vendorId = "+vendorId+" AND VR.clothTypesId = CS.clothTypesId AND VR.markForDelete = 0  "
				+ " WHERE CS.wareHouseLocationId = "+locationId+" AND CS.clothTypesId = "+clothTypesId+" AND CS.markForDelete = 0");

		
		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
			
		} catch (NoResultException nre) {
			
			// Ignore this because as per your logic this is ok!
		}

		VendorFixedLaundryRateDto select;
			select = new VendorFixedLaundryRateDto();
			
			if(result != null) {
				if(result[0] != null) {
					select.setVendorLaundryRateId((Long) result[0]);
				} else {
					select.setVendorLaundryRateId((long) 0);	 
				}
				if(result[1] != null)
				select.setVendorId((Integer) result[1]);
				if(result[2] != null)
				select.setClothTypesId((Long) result[2]);
				if(result[3] != null)
				select.setClothEachCost((Double) result[3]);
				if(result[4] != null)
				select.setClothDiscount((Double) result[4]);
				if(result[5] != null)
				select.setClothGst((Double) result[5]);
				if(result[6] != null)
				select.setClothTotal((Double) result[6]);
				select.setUsedStockQuantity((Double) result[7]);
				select.setNewStockQuantity((Double) result[8]);
			} else {
				return null;
			}
		
		return select;
	}
	
}
