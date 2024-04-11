package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PartLocationsType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.PartLocationsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.PartLocationsDto;
import org.fleetopgroup.persistence.model.PartLocations;
import org.fleetopgroup.persistence.dto.UserDto;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("IPartLocationsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PartLocationsService implements IPartLocationsService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private PartLocationsRepository 			partLocationsDao;
	
	@Autowired 
	private ICompanyConfigurationService		companyConfigurationService;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public PartLocations addPartLocations(PartLocations status) throws Exception {
		return partLocationsDao.save(status);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updatePartLocations(PartLocations status) throws Exception {
		partLocationsDao.save(status);
	}

	@Transactional
	public List<PartLocations> listPartLocations() throws Exception {
		return partLocationsDao.findAll();
	}

	@Transactional
	public PartLocations getPartLocations(int sid) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return partLocationsDao.getPartLocations(sid, userDetails.getCompany_id());
	}
	
	
	@Override
	public PartLocationsDto getPartLocationsDetails(Integer locationId) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Query query = entityManager.createQuery(
				"SELECT P.partlocation_id, P.partlocation_name, P.partlocation_address, P.partlocation_streetaddress, P.partlocation_city, P.partlocation_state, P.partlocation_pincode, P.partlocation_country, "
				+ " P.shippartlocation_address, P.shippartlocation_streetaddress, P.shippartlocation_city, P.shippartlocation_state, P.shippartlocation_pincode, P.shippartlocation_country,  "
				+ " P.partlocation_description, P.contactFirName, P.contactFirPhone, P.contactFirdescription, P.contactSecName, P.contactSecPhone, P.contactSecdescription, P.mainPartLocationId, PL.partlocation_name , P.partLocationType"
				+ " FROM PartLocations P "
				+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = P.mainPartLocationId " 
				+ " WHERE P.partlocation_id ="+locationId+"  AND P.companyId ="+userDetails.getCompany_id()+" AND P.markForDelete = 0");

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		PartLocationsDto select;
		if (result != null) {
			select = new PartLocationsDto();
			select.setPartlocation_id((Integer) result[0]);
			select.setPartlocation_name((String) result[1]);
			select.setPartlocation_address((String) result[2]);
			select.setPartlocation_streetaddress((String) result[3]);
			select.setPartlocation_city((String) result[4]);
			select.setPartlocation_state((String) result[5]);
			select.setPartlocation_pincode((String) result[6]);
			select.setPartlocation_country((String) result[7]);
			select.setShippartlocation_address((String) result[8]);
			select.setShippartlocation_streetaddress((String) result[9]);
			select.setShippartlocation_city((String) result[10]);
			select.setShippartlocation_state((String) result[11]);
			select.setShippartlocation_pincode((String) result[12]);
			select.setShippartlocation_country((String) result[13]);
			select.setPartlocation_description((String) result[14]);
			select.setContactFirName((String) result[15]);
			select.setContactFirPhone((String) result[16]);
			select.setContactFirdescription((String) result[17]);
			select.setContactSecName((String) result[18]);
			select.setContactSecPhone((String) result[19]);
			select.setContactSecdescription((String) result[20]);
			if(result[21] != null) {
				select.setMainPartLocationId((Integer) result[21]);
				select.setMainPartLocation((String) result[22]);
			}
			select.setPartLocationType((short) result[23]);
			select.setPartLocationTypeStr(PartLocationsType.getPartLocationType((short) result[23]));
		} else {
			
			return null;
		}
		return select;
	}
	
	

	@Transactional
	public void deletePartLocations(Integer partlocID, Integer companyId) throws Exception {
		partLocationsDao.deletePartLocations(partlocID, companyId);
	}

	@Transactional
	public List<PartLocations> listOnlyStatus() throws Exception {
		HashMap<String, Object> 	configuration	= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if((boolean)configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
				return partLocationsDao.listPartLocationsByCompanyId(userDetails.getCompany_id(), userDetails.getId());
			}
			return partLocationsDao.listPartLocationsByCompanyId(userDetails.getCompany_id());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Transactional
	public List<PartLocations> SearchOnlyPartLocations(String search, Integer companyId) throws Exception {

		HashMap<String, Object> 	configuration	= null;
		TypedQuery<PartLocations> 	query 			= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			if((boolean)configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
				query = entityManager.createQuery(
						"from PartLocations AS p where  "
						+ " INNER JOIN PartLocationPermission PLP ON PLP.partLocationId = p.partlocation_id AND PLP.user_Id = "+userDetails.getId()+""
						+ " lower(p.partlocation_name) Like ('%" + search + "%') AND p.companyId = "+companyId+" AND p.partLocationType = "+PartLocationsType.PART_LOCATION_TYPE_MAIN_LOCATION+" AND p.markForDelete = 0 AND p.isAutoCreated = 0", PartLocations.class);
			}else {
				query = entityManager.createQuery(
						"from PartLocations AS p where  lower(p.partlocation_name) Like ('%" + search + "%') AND p.companyId = "+companyId+" AND p.partLocationType = "+PartLocationsType.PART_LOCATION_TYPE_MAIN_LOCATION+" AND p.markForDelete = 0 AND p.isAutoCreated = 0", PartLocations.class);
			}
			return query.getResultList();
			}else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	
		
		
	}

	@Transactional
	public PartLocations ValidatePartLocations(Integer locationName, Integer companyId) throws Exception {
		return partLocationsDao.validatePartLocations(locationName, companyId);
	}

	@Override
	public PartLocations validatePartLocations(String locationName, Integer companyId) throws Exception {
		
		return partLocationsDao.validatePartLocations(locationName, companyId);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IPartLocationsService#
	 * Search_PartLocations_select(java.lang.String)
	 */
	@Transactional
	@Override
	public List<PartLocations> Search_PartLocations_select(String partname, Integer companyId) throws Exception {
		HashMap<String, Object> 	configuration	= null;
		TypedQuery<Object[]>		 query 			= null;
		try {
			List<PartLocations> dtos = new ArrayList<>();
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if(partname != null && !partname.trim().equalsIgnoreCase("") && partname.indexOf('\'') != 0 ) {
			if((boolean)configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
				 query = entityManager.createQuery(
					"SELECT c.partlocation_id, c.partlocation_name"
					+ " from PartLocations AS c "
					+ " INNER JOIN PartLocationPermission PLP ON PLP.partLocationId = c.partlocation_id AND PLP.user_Id = "+userDetails.getId()+""
					+ " where lower(c.partlocation_name) Like ('%"
							+ partname + "%') and c.companyId = "+companyId+"  AND c.partLocationType = "+PartLocationsType.PART_LOCATION_TYPE_MAIN_LOCATION+" AND c.markForDelete = 0 AND c.isAutoCreated = 0",
					Object[].class);
			}else {
				query = entityManager.createQuery(
					"SELECT c.partlocation_id, c.partlocation_name from PartLocations AS c where lower(c.partlocation_name) Like ('%"
							+ partname + "%') and c.companyId = "+companyId+" AND c.partLocationType = "+PartLocationsType.PART_LOCATION_TYPE_MAIN_LOCATION+" AND c.markForDelete = 0 AND c.isAutoCreated = 0",
					Object[].class);
			}
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				

				PartLocations tyresize = null;
				for (Object[] result : results) {
					tyresize = new PartLocations();
					tyresize.setPartlocation_id((Integer) result[0]);
					tyresize.setPartlocation_name((String) result[1]);

					dtos.add(tyresize);
				}
			}
			}
			return dtos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IPartLocationsService#
	 * Get All Part Location By Term and Comoany Id 
	 */
	@Transactional
	@Override
	public List<PartLocations> getAllPartLocations(String partname, Integer companyId) throws Exception {
		TypedQuery<Object[]>		 query 			= null;
		try {
			List<PartLocations>	dtos = new ArrayList<>();
			if(partname != null && !partname.trim().equalsIgnoreCase("") && partname.indexOf('\'') != 0 ) {
			query = entityManager.createQuery(
					"SELECT c.partlocation_id, c.partlocation_name from PartLocations AS c where lower(c.partlocation_name) Like ('%"
							+ partname + "%') and c.companyId = "+companyId+" AND c.partLocationType = "+PartLocationsType.PART_LOCATION_TYPE_MAIN_LOCATION+"AND c.markForDelete = 0 AND c.isAutoCreated = 0",
							Object[].class);
			
			List<Object[]> results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				
				
				PartLocations tyresize = null;
				for (Object[] result : results) {
					tyresize = new PartLocations();
					tyresize.setPartlocation_id((Integer) result[0]);
					tyresize.setPartlocation_name((String) result[1]);
					
					dtos.add(tyresize);
				}
			}
			}
			return dtos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IPartLocationsService#
	 * vaildate_TotalPartName_Location_PART_TYRE_WO_PO(java.lang.String)
	 */
	@Transactional
	@Override
	public Object[] vaildate_TotalPartName_Location_PART_TYRE_WO_PO(String partLocationName, Integer companyId, Integer locationId) throws Exception {
		try {
			Query queryt = entityManager.createQuery(
					"SELECT COUNT(co),(SELECT COUNT(Pho) FROM InventoryLocation AS Pho where  Pho.locationId =:locationId and Pho.companyId =:companyId), "
							+ " (SELECT  COUNT(Pur) FROM InventoryTyre AS Pur where  Pur.WAREHOUSE_LOCATION_ID =:locationId and Pur.COMPANY_ID =:companyId),"
							+ " (SELECT  COUNT(RR) FROM WorkOrders AS RR where  RR.workorders_location_ID =:locationId and RR.companyId =:companyId),"
							+ " (SELECT  COUNT(FE) FROM PurchaseOrders AS FE where  FE.purchaseorder_shiplocation_id =:locationId and FE.companyId =:companyId)"
							+ " FROM MasterPartsLocation As co Where co.locationId=:locationId and co.companyId =:companyId");
			queryt.setParameter("companyId", companyId);
			queryt.setParameter("locationId", locationId);
			Object[] count = (Object[]) queryt.getSingleResult();
			
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	@Override
	public Object[] validateSubLocationIsPresentInInventory_WO_SE_PO(String partLocationName, Integer companyId, Integer locationId) throws Exception {
		try {
			Query queryt = entityManager.createQuery(
					"SELECT COUNT(co),(SELECT COUNT(Pho) FROM PartInvoice AS Pho where  Pho.subLocationId =:locationId and Pho.companyId =:companyId and Pho.markForDelete = 0), "
							+ " (SELECT  COUNT(Pur) FROM InventoryTyre AS Pur where  Pur.subLocationId =:locationId and Pur.COMPANY_ID =:companyId and Pur.markForDelete = 0),"
							+ " (SELECT  COUNT(UP) FROM ClothInvoice AS UP where  UP.subLocationId =:locationId and UP.companyId =:companyId and UP.markForDelete = 0 ),"
							+ " (SELECT  COUNT(UR) FROM UreaInvoice AS UR where  UR.subLocationId =:locationId and UR.companyId =:companyId and UR.markForDelete = 0 ),"
							+ " (SELECT  COUNT(RR) FROM WorkOrders AS RR where  RR.subLocationId =:locationId and RR.companyId =:companyId and RR.markForDelete = 0 )"
							+ " FROM MasterPartsLocation As co Where co.locationId=:locationId and co.companyId =:companyId and co.markForDelete = 0" );
			queryt.setParameter("companyId", companyId);
			queryt.setParameter("locationId", locationId);
			Object[] count = (Object[]) queryt.getSingleResult();
			
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<PartLocations> listPartLocationsByCompanyId(Integer companyId) throws Exception {
		HashMap<String, Object> 	configuration	= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if((boolean)configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
				return partLocationsDao.listPartLocationsByCompanyId(companyId, userDetails.getId());
			}
			return partLocationsDao.listPartLocationsByCompanyId(companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<PartLocations> listAllPartLocationsByCompanyId(Integer companyId) throws Exception {
		return partLocationsDao.listPartLocationsByCompanyId(companyId);
	}
	
	@Override
	public List<PartLocations> getPartLocationListByType(short partLocationType) throws Exception {
		HashMap<String, Object> 	configuration	= null;
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if((boolean)configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
				return partLocationsDao.getPartLocationListByType(partLocationType,userDetails.getCompany_id(), userDetails.getId());
			}
			return partLocationsDao.getPartLocationListByType(partLocationType,userDetails.getCompany_id());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	@Override
	public List<PartLocations> searchPartLocationListByMainLocatinId(String term, short locationType, Integer mainLocationId,ValueObject valueObject) throws Exception {
		HashMap<String, Object> 	configuration	= null;
		TypedQuery<Object[]>		query 			= null;
		CustomUserDetails	        userDetails     = null;
		try {
			List<PartLocations> dtos = new ArrayList<>();
			userDetails     = Utility.getObject(valueObject);
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			if((boolean)configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
				 query = entityManager.createQuery(
					"SELECT c.partlocation_id, c.partlocation_name"
					+ " from PartLocations AS c "
					+ " INNER JOIN PartLocationPermission PLP ON PLP.partLocationId = c.partlocation_id AND PLP.user_Id = "+userDetails.getId()+""
					+ " where lower(c.partlocation_name) Like ('%"
							+ term + "%') and c.partLocationType ="+locationType+" and c.mainPartLocationId ="+mainLocationId+" and c.companyId = "+userDetails.getCompany_id()+" AND c.markForDelete = 0 AND c.isAutoCreated = 0",
					Object[].class);
			}else {
				query = entityManager.createQuery(
					"SELECT c.partlocation_id, c.partlocation_name from PartLocations AS c where lower(c.partlocation_name) Like ('%"
							+ term + "%') and c.partLocationType ="+locationType+" and c.mainPartLocationId ="+mainLocationId+" and c.companyId = "+userDetails.getCompany_id()+" AND c.markForDelete = 0 AND c.isAutoCreated = 0",
					Object[].class);
			}
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {

				PartLocations tyresize = null;
				for (Object[] result : results) {
					tyresize = new PartLocations();
					tyresize.setPartlocation_id((Integer) result[0]);
					tyresize.setPartlocation_name((String) result[1]);

					dtos.add(tyresize);
				}
			}
			}
			return dtos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	
	}
	
	@Override
	public ValueObject getSubLocationByMainLocationId(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		List<PartLocations>			partSubLocationsList				= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			partSubLocationsList 		= partLocationsDao.getSubLocationByMainLocationId(valueObject.getInt("mainLocationId"),userDetails.getCompany_id());
			valueObject.put("partSubLocationsList", partSubLocationsList);
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			userDetails						= null;     
		}
	}
	
	@Transactional
	public PartLocations validatePartLocation(Integer partLocationId) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return partLocationsDao.validatePartLocation(partLocationId, userDetails.getCompany_id());
	}
	

	@Override
	public List<PartLocations> listOfAllTypePartLocation(Integer companyId) throws Exception {
		return partLocationsDao.listOfAllTypePartLocation(companyId);
	}
	
	@Transactional
	public List<PartLocations> getAllSubLocations(String term, Integer companyId) throws Exception {
		TypedQuery<Object[]>		 query 			= null;
		try {
			List<PartLocations>	dtos = new ArrayList<>();
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			query = entityManager.createQuery(
					"SELECT c.partlocation_id, c.partlocation_name from PartLocations AS c where lower(c.partlocation_name) Like (:term) and c.companyId = "+companyId+" AND c.partLocationType = "+PartLocationsType.PART_LOCATION_TYPE_SUB_LOCATION+" AND c.markForDelete = 0 AND c.isAutoCreated = 0",
							Object[].class);
			query.setParameter("term", "%"+term+"%");
			List<Object[]> results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				
				
				PartLocations location = null;
				for (Object[] result : results) {
					location = new PartLocations();
					location.setPartlocation_id((Integer) result[0]);
					location.setPartlocation_name((String) result[1]);
					
					dtos.add(location);
				}
			}
			}
			return dtos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Transactional
	@Override
	public List<PartLocations> allLocationDropdown() throws Exception {
		HashMap<String, Object> 	configuration	= null;
		TypedQuery<Object[]>		 query 			= null;
		try {
			List<PartLocations> dtos = new ArrayList<>();
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if((boolean)configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
				 query = entityManager.createQuery(
					"SELECT c.partlocation_id, c.partlocation_name"
					+ " from PartLocations AS c "
					+ " INNER JOIN PartLocationPermission PLP ON PLP.partLocationId = c.partlocation_id AND PLP.user_Id = "+userDetails.getId()+""
					+ " where c.companyId = "+userDetails.getCompany_id()+"  AND c.partLocationType = "+PartLocationsType.PART_LOCATION_TYPE_MAIN_LOCATION+" AND c.markForDelete = 0 AND c.isAutoCreated = 0",
					Object[].class);
			}else {
				query = entityManager.createQuery(
					"SELECT c.partlocation_id, c.partlocation_name from PartLocations AS c where c.companyId = "+userDetails.getCompany_id()+" AND c.partLocationType = "+PartLocationsType.PART_LOCATION_TYPE_MAIN_LOCATION+" AND c.markForDelete = 0 AND c.isAutoCreated = 0",
					Object[].class);
			}
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				

				PartLocations tyresize = null;
				for (Object[] result : results) {
					tyresize = new PartLocations();
					tyresize.setPartlocation_id((Integer) result[0]);
					tyresize.setPartlocation_name((String) result[1]);

					dtos.add(tyresize);
				}
			}
			return dtos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	
	}
	
	@Transactional
	@Override
	public List<UserDto> Search_User(String user, Integer companyId) throws Exception {
		TypedQuery<Object[]>		 query 			= null;
		try {
			List<UserDto>	dtos = new ArrayList<>();
			if(user != null && !user.trim().equalsIgnoreCase("") && user.indexOf('\'') != 0 ) {
			query = entityManager.createQuery(
				"SELECT u.firstName, u.lastName, u.id from User AS u where lower(u.firstName) Like ('%"
						+ user + "%') and u.company_id = "+companyId ,
							Object[].class);
			
			List<Object[]> results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {

				UserDto User_details = null;
				for (Object[] result : results) {
					User_details = new UserDto();
					User_details.setFirstName((String) result[0] );
					User_details.setLastName((String) result[1] );
					User_details.setId(((long) result[2])+"");

					dtos.add(User_details);
				}
			}
			}
			return dtos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

}