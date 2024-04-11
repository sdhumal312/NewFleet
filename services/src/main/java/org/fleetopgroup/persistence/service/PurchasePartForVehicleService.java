/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.bl.PurchasePartForVehicleBL;
import org.fleetopgroup.persistence.dao.PurchasePartForVehicleRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.PurchasePartForVehicleDto;
import org.fleetopgroup.persistence.model.PurchasePartForVehicle;
import org.fleetopgroup.persistence.serviceImpl.IPurchasePartForVehicleService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("PurchasePartForVehicleService")
@Transactional
public class PurchasePartForVehicleService implements IPurchasePartForVehicleService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PurchasePartForVehicleRepository 		purchasePartForVehicleRepository;
	
	PurchasePartForVehicleBL	purchasePartForVehicleBL 		= new PurchasePartForVehicleBL();

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject savePurchasePartForVehicle(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails						= null;
		List<PurchasePartForVehicle>			purchasePartForVehicle			= null;
		ArrayList<ValueObject>					dataArrayObjColl				= null;
		try {
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			purchasePartForVehicle	= new ArrayList<PurchasePartForVehicle>();
			dataArrayObjColl 		= new ArrayList<ValueObject>();
			
			dataArrayObjColl		= (ArrayList<ValueObject>) valueObject.get("vehicleDetailsFinalList");
			
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				for (ValueObject object : dataArrayObjColl) {
					purchasePartForVehicle.add(purchasePartForVehicleBL.preparePurchasePartForVehicle(object, userDetails));
				}
			}
			purchasePartForVehicleRepository.saveAll(purchasePartForVehicle);
			valueObject.put("save", true);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;  
			purchasePartForVehicle					= null;  
		}
	}
	
	@Override
	public ValueObject getAllPurchasePartForVehicle(ValueObject valueObject) throws Exception {
		CustomUserDetails					userDetails						= null;
		TypedQuery<Object[]> 				query							= null;
		List<Object[]> 						results							= null;	
		PurchasePartForVehicleDto			purchasePartForVehicleDto		= null;
		List<PurchasePartForVehicleDto>		purchasePartForVehicleList		= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			query = entityManager.createQuery(
					"SELECT P.purchasePartForVehicleId, V.vehicle_registration, P.partQuantity "
							+ " FROM PurchasePartForVehicle AS P "
							+ " INNER JOIN Vehicle AS V ON V.vid = P.vid "
							+ " WHERE P.companyId = "+userDetails.getCompany_id()+" AND P.purchaseOrderId = "+valueObject.getLong("purchaseOrderId")+" "
							+ " AND P.purchaseorderToPartId = "+valueObject.getLong("purchaseorderToPartId")+" AND P.markForDelete = 0", Object[].class);
			
			results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				purchasePartForVehicleList 	= new ArrayList<PurchasePartForVehicleDto>();
				
				for (Object[] result : results) {
					purchasePartForVehicleDto 		= new PurchasePartForVehicleDto();
					purchasePartForVehicleDto.setPurchasePartForVehicleId((Long)result[0]);
					purchasePartForVehicleDto.setVehicleRegistration((String)result[1]);
					purchasePartForVehicleDto.setPartQuantity((Double)result[2]);
					purchasePartForVehicleList.add(purchasePartForVehicleDto);
				}
			}
			
			valueObject.put("purchasePartForVehicleList", purchasePartForVehicleList);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;     
		}
	}
	
	
	@Transactional
	@Override
	public ValueObject deletePurchasePartForVehicle(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			purchasePartForVehicleRepository.deletePurchasePartForVehicle(valueObject.getLong("purchasePartForVehicleId"),userDetails.getCompany_id());
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;
		}
	}
	
	@Transactional
	@Override
	public void deletePurchasePartForVehicleByPurchaseOrderToPartId(Long purchaseorderToPartId, Long purchaseorder_id) throws Exception {
		CustomUserDetails			userDetails						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			purchasePartForVehicleRepository.deletePurchasePartForVehicle(purchaseorderToPartId,purchaseorder_id,userDetails.getCompany_id());
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;
		}
	}


}
