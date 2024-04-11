package org.fleetopgroup.persistence.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.UreaEntriesRepository;
import org.fleetopgroup.persistence.dao.UreaInvoiceToDetailsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.UreaInvoiceToDetailsDto;
import org.fleetopgroup.persistence.model.UreaEntries;
import org.fleetopgroup.persistence.model.UreaInvoiceToDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IUreaInvoiceToDetailsService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UreaInvoiceToDetailsService implements IUreaInvoiceToDetailsService {
	
	@PersistenceContext EntityManager entityManager;
	
	@Autowired	private	UreaEntriesRepository					UreaEntriesRepository;
	@Autowired	private	UreaInvoiceToDetailsRepository			ureaInvoiceToDetailsRepository;
	@Autowired 	private ICompanyConfigurationService 			companyConfigurationService;
	
	
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");

	@Override
	public List<UreaInvoiceToDetailsDto> getUreaInvoiceToDetailsDtoList(Long invoiceId, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> 					typedQuery 				= null;
		List<Object[]> 							resultList 				= null; 
		List<UreaInvoiceToDetailsDto> 			clothInvoiceList 		= null;
		UreaInvoiceToDetailsDto 				clothInvoiceDto			= null;

		try {

			typedQuery = entityManager.createQuery("SELECT CD.ureaInvoiceToDetailsId, CD.ureaInvoiceId, CD.manufacturerId, CD.quantity, CD.unitprice, CD.discount, CD.tax,"
					+ " CD.total, CD.purchaseorder_id, CD.vendor_id, CD.wareHouseLocation, V.vendorName, PL.partlocation_name, CT.manufacturerName, CD.stockQuantity, "
					+ " CD.transferQuantity, CD.ureaTransferDetailsId, UTD.ureaTransferId , CD.discountTaxTypeId"
					+ " FROM UreaInvoiceToDetails AS CD"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CD.wareHouseLocation "
					+ " LEFT JOIN UreaManufacturer CT ON CT.ureaManufacturerId = CD.manufacturerId"
					+ " LEFT JOIN Vendor AS V ON V.vendorId = CD.vendor_id "
					+ " LEFT JOIN UreaTransferDetails AS UTD ON UTD.ureaTransferDetailsId = CD.ureaTransferDetailsId "
					+ " WHERE CD.ureaInvoiceId = "+invoiceId+" AND CD.companyId ="+companyId+" AND CD.markForDelete = 0 ORDER BY CD.ureaInvoiceToDetailsId DESC", Object[].class);


			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				clothInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					clothInvoiceDto = new UreaInvoiceToDetailsDto();
					
					clothInvoiceDto.setUreaInvoiceToDetailsId((Long) result[0]);
					clothInvoiceDto.setUreaInvoiceId((Long) result[1]);
					clothInvoiceDto.setManufacturerId((Long) result[2]);
					clothInvoiceDto.setQuantity(Double.parseDouble(toFixedTwo.format(result[3])));
					
					clothInvoiceDto.setUnitprice(Double.parseDouble(toFixedTwo.format(result[4])));
					if(result[5] != null) {
						clothInvoiceDto.setDiscount(Double.parseDouble(toFixedTwo.format(result[5])));
					}else {
						clothInvoiceDto.setDiscount(0.0);
					}
					clothInvoiceDto.setTax(Double.parseDouble(toFixedTwo.format(result[6])));
					clothInvoiceDto.setTotal(Double.parseDouble(toFixedTwo.format(result[7])));
					clothInvoiceDto.setPurchaseorder_id((Long) result[8]);
					clothInvoiceDto.setVendor_id((Integer) result[9]);
					clothInvoiceDto.setWareHouseLocation((Integer) result[10]);
					clothInvoiceDto.setVendorName((String) result[11]);
					clothInvoiceDto.setLocationName((String) result[12]);
					if(result[13] != null) {
						clothInvoiceDto.setManufacturerName((String) result[13]);
					}else {
						clothInvoiceDto.setManufacturerName("-");
					}
					clothInvoiceDto.setStockQuantity((Double) result[14]);
					if( result[15] != null) {
						clothInvoiceDto.setTransferQuantity((Double) result[15]);
					}
					if( result[16] != null) {
						clothInvoiceDto.setUreaTransferDetailsId((Long) result[16]);
					}
					if( result[17] != null) {
						clothInvoiceDto.setUreaTransferId((Long) result[17]);
					}
					if(result[18] != null) {
						clothInvoiceDto.setDiscountTaxTypeId((short) result[18]);
					} else {
						clothInvoiceDto.setDiscountTaxTypeId((short) 1);
					}
					clothInvoiceList.add(clothInvoiceDto);
				}
			}

			return clothInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			clothInvoiceList 		= null;
			clothInvoiceDto			= null;
		}
	}
	
	
	@Override
	@Transactional
	public void removeUreaInventoryDetailsFromInvoice(UreaInvoiceToDetails ureaInvoiceToDetails) throws Exception {
		try {

			entityManager.createQuery("Update UreaInvoice SET invoiceAmount = invoiceAmount - "+ureaInvoiceToDetails.getTotal()+", "
					+ " totalAmount = totalAmount - "+ureaInvoiceToDetails.getTotal()+", quantity = quantity - "+ureaInvoiceToDetails.getQuantity()+""
					+ ", lastModifiedById = "+ureaInvoiceToDetails.getLastModifiedById()+", lastModifiedBy = '"+DateTimeUtility.getCurrentDate()+"'"
					+ " where ureaInvoiceId = "+ureaInvoiceToDetails.getUreaInvoiceId()+" AND companyId = "+ureaInvoiceToDetails.getCompanyId()+" ").executeUpdate();
		
		} catch (Exception e) {
			throw e;
		}
	}


	@Override
	public List<UreaInvoiceToDetailsDto> getAllLocationUreaStockDetails(Integer companyId ,String query) throws Exception {

		TypedQuery<Object[]> 					typedQuery 				= null;
		List<Object[]> 							resultList 				= null; 
		List<UreaInvoiceToDetailsDto> 			clothInvoiceList 		= null;
		UreaInvoiceToDetailsDto 				clothInvoiceDto			= null;

		try {

			typedQuery = entityManager.createQuery("SELECT SUM(CD.quantity), SUM(CD.usedQuantity), CT.ureaManufacturerId, CT.manufacturerName ,SUM(CD.stockQuantity) ,PL.partlocation_name,CD.wareHouseLocation"
					+ " FROM UreaInvoiceToDetails AS CD "
					+ " INNER JOIN UreaInvoice UI ON UI.ureaInvoiceId = CD.ureaInvoiceId AND UI.markForDelete = 0 "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CD.wareHouseLocation "
					+ " LEFT JOIN UreaManufacturer CT ON CT.ureaManufacturerId = CD.manufacturerId"
					+ " WHERE CD.companyId ="+companyId+" AND CD.markForDelete = 0  " +query+ " ", Object[].class);


			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				clothInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					clothInvoiceDto = new UreaInvoiceToDetailsDto();
					
					clothInvoiceDto.setQuantity((Double) result[0]);
					clothInvoiceDto.setUsedQuantity((Double) result[1]);
					clothInvoiceDto.setManufacturerId((Long) result[2]);
					clothInvoiceDto.setManufacturerName((String) result[3]);
					clothInvoiceDto.setStockQuantity((Double) result[4]);
					clothInvoiceDto.setLocationName((String)result[5]);
					clothInvoiceDto.setWareHouseLocation((Integer) result[6]);
					clothInvoiceList.add(clothInvoiceDto);
				}
			}

			return clothInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			clothInvoiceList 		= null;
			clothInvoiceDto			= null;
		}
	}


	@Override
	public List<UreaInvoiceToDetailsDto> getLocationUreaStockDetails(Integer locationId, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> 					typedQuery 				= null;
		List<Object[]> 							resultList 				= null; 
		List<UreaInvoiceToDetailsDto> 			clothInvoiceList 		= null;
		UreaInvoiceToDetailsDto 				clothInvoiceDto			= null;

		try {

			typedQuery = entityManager.createQuery("SELECT SUM(CD.quantity), SUM(CD.usedQuantity), SUM(CD.stockQuantity), CT.ureaManufacturerId, CT.manufacturerName"
					+ " FROM UreaInvoiceToDetails AS CD"
					+ " INNER JOIN UreaInvoice UI ON UI.ureaInvoiceId = CD.ureaInvoiceId AND UI.markForDelete = 0 "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CD.wareHouseLocation "
					+ " LEFT JOIN UreaManufacturer CT ON CT.ureaManufacturerId = CD.manufacturerId"
					+ " WHERE CD.wareHouseLocation = "+locationId+" AND CD.companyId ="+companyId+" AND CD.markForDelete = 0 group by CD.manufacturerId ", Object[].class);


			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				clothInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					clothInvoiceDto = new UreaInvoiceToDetailsDto();
					
					clothInvoiceDto.setQuantity((Double) result[0]);
					clothInvoiceDto.setUsedQuantity((Double) result[1]);
					clothInvoiceDto.setStockQuantity((Double) result[2]);
					clothInvoiceDto.setManufacturerId((Long) result[3]);
					clothInvoiceDto.setManufacturerName((String) result[4]);
					
					clothInvoiceList.add(clothInvoiceDto);
				}
			}

			return clothInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			clothInvoiceList 		= null;
			clothInvoiceDto			= null;
		}
	}

	
	@Override 
	public List<UreaInvoiceToDetailsDto> getLocationUreaStockDetailsForEntry(String term , Integer companyId)
			throws Exception {

		TypedQuery<Object[]> 					typedQuery 				= null;
		List<Object[]> 							resultList 				= null; 
		List<UreaInvoiceToDetailsDto> 			clothInvoiceList 		= null;
		UreaInvoiceToDetailsDto 				clothInvoiceDto			= null;
		HashMap<String, Object> 				configuration 			= null;
		CustomUserDetails						userDetails				= null;

		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration = companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			if ((boolean) configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
			typedQuery = entityManager.createQuery("SELECT CD.quantity, CD.usedQuantity, CT.ureaManufacturerId, CT.manufacturerName, CD.stockQuantity,"
					+ " CD.wareHouseLocation, PL.partlocation_name, UI.ureaInvoiceNumber, UI.ureaInvoiceId, CD.ureaInvoiceToDetailsId, CD.unitprice, CD.discount, CD.tax, PSL.partlocation_name "
					+ " FROM UreaInvoiceToDetails AS CD "
					+ " INNER JOIN UreaInvoice UI ON UI.ureaInvoiceId = CD.ureaInvoiceId AND UI.markForDelete = 0"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CD.wareHouseLocation "
					+ " LEFT JOIN UreaManufacturer CT ON CT.ureaManufacturerId = CD.manufacturerId"
					+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = CD.subLocationId "
					+ " INNER JOIN PartLocationPermission PLP ON PLP.partLocationId = CD.wareHouseLocation AND PLP.user_Id =  "+userDetails.getId()+""
					+ " WHERE lower(PL.partlocation_name) Like (:term) AND CD.companyId ="+companyId+" AND CD.stockQuantity > 0.0 AND CD.markForDelete = 0 ", Object[].class);

			}else {
				typedQuery = entityManager.createQuery("SELECT CD.quantity, CD.usedQuantity, CT.ureaManufacturerId, CT.manufacturerName, CD.stockQuantity,"
						+ " CD.wareHouseLocation, PL.partlocation_name, UI.ureaInvoiceNumber, UI.ureaInvoiceId, CD.ureaInvoiceToDetailsId, CD.unitprice, CD.discount, CD.tax, PSL.partlocation_name "
						+ " FROM UreaInvoiceToDetails AS CD "
						+ " INNER JOIN UreaInvoice UI ON UI.ureaInvoiceId = CD.ureaInvoiceId AND UI.markForDelete = 0"
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CD.wareHouseLocation "
						+ " LEFT JOIN UreaManufacturer CT ON CT.ureaManufacturerId = CD.manufacturerId"
						+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = CD.subLocationId "
						+ " WHERE lower(PL.partlocation_name) Like (:term) AND CD.companyId ="+companyId+" AND CD.stockQuantity > 0.0 AND CD.markForDelete = 0 ", Object[].class);
			}
			
			
			typedQuery.setParameter("term", "%"+term+"%");
			
			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				clothInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					clothInvoiceDto = new UreaInvoiceToDetailsDto();
					if( result[0] != null)
					clothInvoiceDto.setQuantity(Double.parseDouble(toFixedTwo.format( result[0])));
					if(result[1] != null)
					clothInvoiceDto.setUsedQuantity(Double.parseDouble(toFixedTwo.format(result[1])));
					clothInvoiceDto.setManufacturerId((Long) result[2]);
					if( result[3] != null) {
						clothInvoiceDto.setManufacturerName((String) result[3]);
					}else {
						clothInvoiceDto.setManufacturerName(" ");
					}
					if( result[4] != null)
					clothInvoiceDto.setStockQuantity(Double.parseDouble(toFixedTwo.format(result[4])));
					clothInvoiceDto.setWareHouseLocation((Integer) result[5]);
					clothInvoiceDto.setLocationName((String) result[6]);
					clothInvoiceDto.setUreaInvoiceNumber((Long) result[7]);
					clothInvoiceDto.setUreaInvoiceId((Long) result[8]);
					clothInvoiceDto.setUreaInvoiceToDetailsId((Long) result[9]);
					if(result[10] != null)
					clothInvoiceDto.setUnitprice(Double.parseDouble(toFixedTwo.format(result[10])));
					if(result[11] != null)
					clothInvoiceDto.setDiscount(Double.parseDouble(toFixedTwo.format(result[11])));
					if(result[12] != null)
					clothInvoiceDto.setTax(Double.parseDouble(toFixedTwo.format(result[12])));
					if(result[13] != null) {
						clothInvoiceDto.setSubLocation((String) result[13]);
					}else {
						clothInvoiceDto.setSubLocation("");
					}
					
					clothInvoiceList.add(clothInvoiceDto);
				}
			}
			}
			return clothInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			clothInvoiceList 		= null;
			clothInvoiceDto			= null;
		}
	}
	
	
	@Override
	@Transactional
	public void updateStockQuantityOnUreaEntry(UreaEntries ureaEntries) throws Exception {
		CustomUserDetails		 userDetails					= null;
		Date					 updatedDate					;
		try {
			userDetails					 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			updatedDate					 = new Date();
			java.sql.Timestamp sqlDate   = new java.sql.Timestamp(updatedDate.getTime());
			
			entityManager.createQuery("Update UreaInvoiceToDetails SET stockQuantity = stockQuantity - "+ureaEntries.getUreaLiters()+", "
					+ " usedQuantity = usedQuantity + "+ureaEntries.getUreaLiters()+", lastupdated = '"+sqlDate+"', "
					+ " lastModifiedById = "+userDetails.getId()+" "
					+ " where ureaInvoiceToDetailsId = "+ureaEntries.getUreaInvoiceToDetailsId()+" AND companyId = "+ureaEntries.getCompanyId()+" ").executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updateStockQuantityOnEditUreaEntry(UreaEntries ureaEntries, double stckQty) throws Exception {
		CustomUserDetails		 userDetails					= null;
		Date					 updatedDate					;
		try {
			userDetails					 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			updatedDate					 = new Date();
			java.sql.Timestamp sqlDate   = new java.sql.Timestamp(updatedDate.getTime());
			
			entityManager.createQuery(
				"Update UreaInvoiceToDetails SET stockQuantity = stockQuantity + "+stckQty+", "
					+ " usedQuantity = usedQuantity - "+stckQty+", lastupdated = '"+sqlDate+"', "
					+ " lastModifiedById = "+userDetails.getId()+" "
					+ " where ureaInvoiceToDetailsId = "+ureaEntries.getUreaInvoiceToDetailsId()+" "
					+ " AND companyId = "+ureaEntries.getCompanyId()+" ").executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updateStckQtyOnEditDiffrentUreaEntry(long oldUreaInvoiceToDetailsId, double oldUreaLiters,
			long newUreaInvoiceToDetailsId, double newUreaLiters) throws Exception {
		
		CustomUserDetails		 userDetails					= null;
		Date					 updatedDate					;
		try {
			
			userDetails					 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			updatedDate					 = new Date();
			java.sql.Timestamp sqlDate   = new java.sql.Timestamp(updatedDate.getTime());
			
			entityManager.createQuery(
				"Update UreaInvoiceToDetails SET stockQuantity = stockQuantity + "+oldUreaLiters+", "
					+ " usedQuantity = usedQuantity - "+oldUreaLiters+", lastupdated = '"+sqlDate+"', "
					+ " lastModifiedById = "+userDetails.getId()+" "
					+ " where ureaInvoiceToDetailsId = "+oldUreaInvoiceToDetailsId+" "
					+ " AND companyId = "+userDetails.getCompany_id()+" ")
			.executeUpdate();
			
			entityManager.createQuery(
					"Update UreaInvoiceToDetails SET stockQuantity = stockQuantity - "+newUreaLiters+", "
						+ " usedQuantity = usedQuantity + "+newUreaLiters+", lastupdated = '"+sqlDate+"', "
						+ " lastModifiedById = "+userDetails.getId()+" "
						+ " where ureaInvoiceToDetailsId = "+newUreaInvoiceToDetailsId+" "
						+ " AND companyId = "+userDetails.getCompany_id()+" ")
			.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updateStockQuantityOnDeleteUreaEntry(long ureaEntriesId) throws Exception {
		UreaEntries     		 ureaEntryDetails   			= null;
		double					 stockQty		 				= 0.0;
		CustomUserDetails		 userDetails					= null;
		Date					 updatedDate					;	
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			updatedDate					= new Date();
			java.sql.Timestamp sqlDate  = new java.sql.Timestamp(updatedDate.getTime());
			
			ureaEntryDetails = UreaEntriesRepository.getUreaEntryDetailsById(ureaEntriesId, userDetails.getCompany_id());
			if(ureaEntryDetails != null)
			stockQty = ureaEntryDetails.getUreaLiters();
			
			entityManager.createQuery(
				"Update UreaInvoiceToDetails SET stockQuantity = stockQuantity + "+stockQty+", "
					+ " usedQuantity = usedQuantity - "+stockQty+", lastupdated = '"+sqlDate+"',  "
					+ " lastModifiedById = "+userDetails.getId()+" "
					+ " where ureaInvoiceToDetailsId = "+ureaEntryDetails.getUreaInvoiceToDetailsId()+" "
					+ " AND companyId = "+ureaEntryDetails.getCompanyId()+" ").executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getUreastockDetailsByManufactureIdAndLocation(ValueObject valueObject)
			throws Exception {
		
		long 									manufactureId  			= 0;
		int 									locationId 				= 0;
		int 									companyId				= 0;
		TypedQuery<Object[]> 					typedQuery 				= null;
		Object[] 								result 					= null; 
		UreaInvoiceToDetailsDto 				ureaInvoiceToDetailsDto			= null;
		CustomUserDetails		 				userDetails				= null;

		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			manufactureId				= valueObject.getLong("ureaManufacturerId",0);
			locationId					= valueObject.getInt("warehouselocation",0);
			companyId					= userDetails.getCompany_id();
			
			typedQuery = entityManager.createQuery("SELECT SUM(CD.quantity), SUM(CD.usedQuantity), CT.ureaManufacturerId, CT.manufacturerName"
					+ " FROM UreaInvoiceToDetails AS CD"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CD.wareHouseLocation "
					+ " INNER JOIN UreaManufacturer CT ON CT.ureaManufacturerId = CD.manufacturerId"
					+ " WHERE CD.wareHouseLocation = "+locationId+" AND CD.manufacturerId = "+manufactureId+" AND CD.companyId ="+companyId+" AND CD.markForDelete = 0 ", Object[].class);

			
			try {
				result = (Object[])typedQuery.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			if(result != null) {
				ureaInvoiceToDetailsDto = new UreaInvoiceToDetailsDto();
				ureaInvoiceToDetailsDto.setQuantity((Double) result[0]);
				ureaInvoiceToDetailsDto.setUsedQuantity((Double) result[1]);
				ureaInvoiceToDetailsDto.setManufacturerId((Long) result[2]);
				ureaInvoiceToDetailsDto.setManufacturerName((String) result[3]);
			}
			valueObject.put("stockDetails", ureaInvoiceToDetailsDto);

			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			ureaInvoiceToDetailsDto			= null;
			valueObject				= null;
		}
	}
	
	@Override
	public ValueObject getUreastockDetailsOfOtherLocationByManufactureId(ValueObject valueObject)
			throws Exception {
		
		long 									manufactureId  			= 0;
		int 									locationId 				= 0;
		int 									companyId				= 0;
		TypedQuery<Object[]> 					typedQuery 				= null;
		Object[] 								result 					= null; 
		UreaInvoiceToDetailsDto 				ureaInvoiceToDetailsDto			= null;
		CustomUserDetails		 				userDetails				= null;

		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			manufactureId				= valueObject.getLong("ureaManufacturerId",0);
			locationId					= valueObject.getInt("warehouselocation",0);
			companyId					= userDetails.getCompany_id();
			
			typedQuery = entityManager.createQuery("SELECT SUM(CD.quantity), SUM(CD.usedQuantity), CT.ureaManufacturerId, CT.manufacturerName"
					+ " FROM UreaInvoiceToDetails AS CD"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CD.wareHouseLocation "
					+ " INNER JOIN UreaManufacturer CT ON CT.ureaManufacturerId = CD.manufacturerId"
					+ " WHERE PL.partlocation_id NOT IN("+locationId+") AND CD.manufacturerId = "+manufactureId+" AND CD.companyId ="+companyId+" AND CD.markForDelete = 0 ", Object[].class);

			
			try {
				result = (Object[])typedQuery.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			if(result != null) {
				ureaInvoiceToDetailsDto = new UreaInvoiceToDetailsDto();
				ureaInvoiceToDetailsDto.setQuantity((Double) result[0]);
				ureaInvoiceToDetailsDto.setUsedQuantity((Double) result[1]);
				ureaInvoiceToDetailsDto.setManufacturerId((Long) result[2]);
				ureaInvoiceToDetailsDto.setManufacturerName((String) result[3]);
			}
			valueObject.put("stockDetails", ureaInvoiceToDetailsDto);

			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			ureaInvoiceToDetailsDto			= null;
			valueObject				= null;
		}
	}
	
	@Override
	public List<UreaInvoiceToDetailsDto> getLocationWiseUreaInvoiceDetails(String term ,Integer locationId, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> 					typedQuery 				= null;
		List<Object[]> 							resultList 				= null; 
		List<UreaInvoiceToDetailsDto> 			clothInvoiceList 		= null;
		UreaInvoiceToDetailsDto 				clothInvoiceDto			= null;

		try {
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			typedQuery = entityManager.createQuery("SELECT CD.quantity, CD.usedQuantity, CT.ureaManufacturerId, CT.manufacturerName, CD.stockQuantity,"
					+ " CD.wareHouseLocation, PL.partlocation_name, UI.ureaInvoiceNumber, UI.ureaInvoiceId, CD.ureaInvoiceToDetailsId, CD.unitprice, CD.discount, CD.tax "
					+ " FROM UreaInvoiceToDetails AS CD "
					+ " INNER JOIN UreaInvoice UI ON UI.ureaInvoiceId = CD.ureaInvoiceId AND UI.markForDelete = 0"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CD.wareHouseLocation "
					+ " INNER JOIN UreaManufacturer CT ON CT.ureaManufacturerId = CD.manufacturerId"
					+ " WHERE lower(CT.manufacturerName) Like ('%" + term + "%') AND CD.wareHouseLocation ="+locationId+" AND CD.companyId ="+companyId+" AND CD.stockQuantity > 0.0 AND CD.markForDelete = 0 ", Object[].class);


			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				clothInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					clothInvoiceDto = new UreaInvoiceToDetailsDto();
					
					clothInvoiceDto.setQuantity((Double) result[0]);
					clothInvoiceDto.setUsedQuantity((Double) result[1]);
					clothInvoiceDto.setManufacturerId((Long) result[2]);
					clothInvoiceDto.setManufacturerName((String) result[3]);
					clothInvoiceDto.setStockQuantity((Double) result[4]);
					clothInvoiceDto.setWareHouseLocation((Integer) result[5]);
					clothInvoiceDto.setLocationName((String) result[6]);
					clothInvoiceDto.setUreaInvoiceNumber((Long) result[7]);
					clothInvoiceDto.setUreaInvoiceId((Long) result[8]);
					clothInvoiceDto.setUreaInvoiceToDetailsId((Long) result[9]);
					clothInvoiceDto.setUnitprice((Double) result[10]);
					clothInvoiceDto.setDiscount((Double) result[11]);
					clothInvoiceDto.setTax((Double) result[12]);
					
					clothInvoiceList.add(clothInvoiceDto);
				}
			}
			}
			return clothInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			clothInvoiceList 		= null;
			clothInvoiceDto			= null;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void updateUreaInvoiceToDetailsQuantity(ValueObject valueObject) throws Exception {
		CustomUserDetails		 userDetails					= null;
		Date					 updatedDate					= null;
		ArrayList<ValueObject> 	 dataArrayObjColl 				= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			updatedDate					= new Date();
			java.sql.Timestamp sqlDate  = new java.sql.Timestamp(updatedDate.getTime());
			
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("ureaDetails");
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
			
				for (ValueObject object : dataArrayObjColl) {
					entityManager.createQuery(
							"Update UreaInvoiceToDetails SET stockQuantity = stockQuantity - "+object.getDouble("quantity")+", "
								+ " quantity = quantity - "+object.getDouble("quantity")+", lastupdated = '"+sqlDate+"',  "
								+ " lastModifiedById = "+userDetails.getId()+" "
								+ " where ureaInvoiceToDetailsId = "+object.getLong("ureaInvoiceToDetailsId")+" "
								+ " AND companyId = "+userDetails.getCompany_id()+" ").executeUpdate();
					
				}
				
			}
			
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<UreaInvoiceToDetailsDto> getsubLocationUreaDetails(Integer locationId) throws Exception {

		TypedQuery<Object[]> 					typedQuery 				= null;
		List<Object[]> 							resultList 				= null; 
		List<UreaInvoiceToDetailsDto> 			clothInvoiceList 		= null;
		UreaInvoiceToDetailsDto 				clothInvoiceDto			= null;
		CustomUserDetails						userDetails				= null;

		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			typedQuery = entityManager.createQuery("SELECT CD.quantity, CD.usedQuantity, CT.ureaManufacturerId, CT.manufacturerName, CD.stockQuantity,"
					+ " CD.wareHouseLocation, PSL.partlocation_name, UI.ureaInvoiceNumber, UI.ureaInvoiceId, CD.ureaInvoiceToDetailsId, CD.unitprice, CD.discount, CD.tax, CD.subLocationId "
					+ " FROM UreaInvoiceToDetails AS CD "
					+ " INNER JOIN UreaInvoice UI ON UI.ureaInvoiceId = CD.ureaInvoiceId AND UI.markForDelete = 0"
					+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = CD.subLocationId "
					+ " INNER JOIN UreaManufacturer CT ON CT.ureaManufacturerId = CD.manufacturerId"
					+ " WHERE CD.wareHouseLocation ="+locationId+" AND CD.companyId ="+userDetails.getCompany_id()+" AND CD.stockQuantity > 0.0 AND CD.markForDelete = 0 ", Object[].class);


			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				clothInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					clothInvoiceDto = new UreaInvoiceToDetailsDto();
					
					clothInvoiceDto.setQuantity((Double) result[0]);
					clothInvoiceDto.setUsedQuantity((Double) result[1]);
					clothInvoiceDto.setManufacturerId((Long) result[2]);
					clothInvoiceDto.setManufacturerName((String) result[3]);
					clothInvoiceDto.setStockQuantity((Double) result[4]);
					clothInvoiceDto.setWareHouseLocation((Integer) result[5]);
					if(result[6] != null) {
						clothInvoiceDto.setSubLocation((String) result[6]);
					}else {
						clothInvoiceDto.setSubLocation("Not Defined");
					}
					clothInvoiceDto.setUreaInvoiceNumber((Long) result[7]);
					clothInvoiceDto.setUreaInvoiceId((Long) result[8]);
					clothInvoiceDto.setUreaInvoiceToDetailsId((Long) result[9]);
					clothInvoiceDto.setUnitprice((Double) result[10]);
					clothInvoiceDto.setDiscount((Double) result[11]);
					clothInvoiceDto.setTax((Double) result[12]);
					if(result[13] != null) {
						clothInvoiceDto.setSubLocationId((Integer) result[13]);
					}
					
					clothInvoiceList.add(clothInvoiceDto);
				}
			}
			return clothInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			clothInvoiceList 		= null;
			clothInvoiceDto			= null;
		}
	}
	
	@Override
	public List<UreaInvoiceToDetails> getUreaInvoiceToDetailsByUreaInvoiceId(Long ureaInvoiceId) throws Exception {
		try {
			return ureaInvoiceToDetailsRepository.getUreaInvoiceToDetailsList(ureaInvoiceId);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@Override
	@Transactional
	public void updateSubLocationInUreaInvoiceToDetails(Integer subLocationId, Long ureaInvoiceId, Integer company_id) throws Exception {
		try {
			ureaInvoiceToDetailsRepository.updateSubLocationInUreaInvoiceToDetails(subLocationId,ureaInvoiceId,company_id);
			
		} catch (Exception e) {
			throw e;
		}finally {
		}
	}
	
	
	@Override
	@Transactional
	public void updateUreaTransferQuantityAndUpdateStockQuantityInUreaInvoiceToDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails		 userDetails					= null;
		Date					 updatedDate					;
		try {
			userDetails					 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			updatedDate					 = new Date();
			java.sql.Timestamp sqlDate   = new java.sql.Timestamp(updatedDate.getTime());
			
			entityManager.createQuery("Update UreaInvoiceToDetails AS UID SET UID.stockQuantity = "+valueObject.getDouble("updatedStockQuantity")+", "
					+ " UID.transferQuantity = "+valueObject.getDouble("updatedTransferQuantity")+", UID.lastupdated = '"+sqlDate+"', "
					+ " UID.lastModifiedById = "+userDetails.getId()+" "
					+ " where UID.ureaInvoiceToDetailsId = "+valueObject.getLong("ureaInvoiceToDetailsId")+" AND UID.companyId = "+userDetails.getCompany_id()+" ").executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public UreaInvoiceToDetails getUreaInvoiceToDetailsById(Long ureaInvoiceToDetailsId) throws Exception {
		UreaInvoiceToDetails	ureaInvoiceToDetails				= null	;
		try {
			ureaInvoiceToDetails = ureaInvoiceToDetailsRepository.getUreaInvoiceToDetailsById(ureaInvoiceToDetailsId);
			return ureaInvoiceToDetails;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override 
	public List<UreaInvoiceToDetailsDto> getLocationUreaStockDetailsByLocationId(String query , Integer companyId)
			throws Exception {

		TypedQuery<Object[]> 					typedQuery 				= null;
		List<Object[]> 							resultList 				= null; 
		List<UreaInvoiceToDetailsDto> 			clothInvoiceList 		= null;
		UreaInvoiceToDetailsDto 				clothInvoiceDto			= null;
		HashMap<String, Object> 				configuration 			= null;
		CustomUserDetails						userDetails				= null;

		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration = companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
			if ((boolean) configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
			typedQuery = entityManager.createQuery("SELECT CD.quantity, CD.usedQuantity, CT.ureaManufacturerId, CT.manufacturerName, CD.stockQuantity,"
					+ " CD.wareHouseLocation, PL.partlocation_name, UI.ureaInvoiceNumber, UI.ureaInvoiceId, CD.ureaInvoiceToDetailsId, CD.unitprice, CD.discount, CD.tax, PSL.partlocation_name "
					+ " FROM UreaInvoiceToDetails AS CD "
					+ " INNER JOIN UreaInvoice UI ON UI.ureaInvoiceId = CD.ureaInvoiceId AND UI.markForDelete = 0"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CD.wareHouseLocation "
					+ " INNER JOIN PartLocationPermission PLP ON PLP.partLocationId = CD.wareHouseLocation AND PLP.user_Id =  "+userDetails.getId()+""
					+ " LEFT JOIN UreaManufacturer CT ON CT.ureaManufacturerId = CD.manufacturerId"
					+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = CD.subLocationId "
					+ " WHERE CD.companyId ="+companyId+"  "+query+" AND CD.stockQuantity > 0.0 AND CD.markForDelete = 0 ", Object[].class);

			}else {
				typedQuery = entityManager.createQuery("SELECT CD.quantity, CD.usedQuantity, CT.ureaManufacturerId, CT.manufacturerName, CD.stockQuantity,"
						+ " CD.wareHouseLocation, PL.partlocation_name, UI.ureaInvoiceNumber, UI.ureaInvoiceId, CD.ureaInvoiceToDetailsId, CD.unitprice, CD.discount, CD.tax, PSL.partlocation_name "
						+ " FROM UreaInvoiceToDetails AS CD "
						+ " INNER JOIN UreaInvoice UI ON UI.ureaInvoiceId = CD.ureaInvoiceId AND UI.markForDelete = 0"
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CD.wareHouseLocation "
						+ " LEFT JOIN UreaManufacturer CT ON CT.ureaManufacturerId = CD.manufacturerId"
						+ " LEFT JOIN PartLocations PSL ON PSL.partlocation_id = CD.subLocationId "
						+ " WHERE CD.companyId ="+companyId+"  "+query+" AND CD.stockQuantity > 0.0 AND CD.markForDelete = 0 ", Object[].class);
			}
			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				clothInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					clothInvoiceDto = new UreaInvoiceToDetailsDto();
					
					clothInvoiceDto.setQuantity((Double) result[0]);
					clothInvoiceDto.setUsedQuantity((Double) result[1]);
					clothInvoiceDto.setManufacturerId((Long) result[2]);
					if(result[3] != null) {
						clothInvoiceDto.setManufacturerName((String) result[3]);
					}else {
						clothInvoiceDto.setManufacturerName(" ");
					}
					clothInvoiceDto.setStockQuantity((Double) result[4]);
					clothInvoiceDto.setWareHouseLocation((Integer) result[5]);
					clothInvoiceDto.setLocationName((String) result[6]);
					clothInvoiceDto.setUreaInvoiceNumber((Long) result[7]);
					clothInvoiceDto.setUreaInvoiceId((Long) result[8]);
					clothInvoiceDto.setUreaInvoiceToDetailsId((Long) result[9]);
					clothInvoiceDto.setUnitprice((Double) result[10]);
					clothInvoiceDto.setDiscount((Double) result[11]);
					clothInvoiceDto.setTax((Double) result[12]);
					if(result[13] != null) {
						clothInvoiceDto.setSubLocation((String) result[13]);
					}else {
						clothInvoiceDto.setSubLocation("");
					}
					
					clothInvoiceList.add(clothInvoiceDto);
				}
			}

			return clothInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			clothInvoiceList 		= null;
			clothInvoiceDto			= null;
		}
	}
	
	
	@Override
	@Transactional
	public void updateUreaTransferQuantity(Double quantity,long invoiceToDetailsId,long userId,int companyId) throws Exception {
		try {
			entityManager.createQuery("Update UreaInvoiceToDetails AS UID SET UID.stockQuantity = COALESCE(UID.stockQuantity,0)- "+quantity+", "
					+ " UID.transferQuantity = COALESCE(UID.transferQuantity,0)+"+quantity+", UID.lastupdated = '"+DateTimeUtility.getCurrentTimeStamp()+"', "
					+ " UID.lastModifiedById = "+userId+" "
					+ " where UID.ureaInvoiceToDetailsId = "+invoiceToDetailsId+" AND UID.companyId = "+companyId+" ").executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}
