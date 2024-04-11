package org.fleetopgroup.persistence.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dto.ClothInventoryDetailsDto;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClothInventoryDetailsService implements IClothInventoryDetailsService {

	@PersistenceContext EntityManager entityManager;
	
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	
	@Override
	public List<ClothInventoryDetailsDto> getClothInventoryDetailsListById(Long invoiceId, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]> 					resultList 				= null; 
		List<ClothInventoryDetailsDto> 			clothInvoiceList 		= null;
		ClothInventoryDetailsDto 				clothInvoiceDto			= null;

		try {

			typedQuery = entityManager.createQuery("SELECT CD.clothInventoryDetailsId, CD.clothInvoiceId, CD.clothTypesId, CD.quantity, CD.unitprice, CD.discount, CD.tax,"
					+ " CD.total, CD.purchaseorder_id, CD.vendor_id, CD.wareHouseLocation, V.vendorName, PL.partlocation_name, CT.clothTypeName, CD.discountTaxTypeId"
					+ " FROM ClothInventoryDetails AS CD"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = CD.wareHouseLocation "
					+ " INNER JOIN ClothTypes CT ON CT.clothTypesId = CD.clothTypesId"
					+ " LEFT JOIN Vendor AS V ON V.vendorId = CD.vendor_id "
					+ " WHERE CD.clothInvoiceId = "+invoiceId+" AND CD.companyId ="+companyId+" AND CD.markForDelete = 0 ORDER BY CD.clothInventoryDetailsId DESC", Object[].class);


			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				clothInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					clothInvoiceDto = new ClothInventoryDetailsDto();
					
					clothInvoiceDto.setClothInventoryDetailsId((Long) result[0]);
					clothInvoiceDto.setClothInvoiceId((Long) result[1]);
					clothInvoiceDto.setClothTypesId((Long) result[2]);
					
					clothInvoiceDto.setQuantity(Double.parseDouble(toFixedTwo.format(result[3])));
					clothInvoiceDto.setUnitprice(Double.parseDouble(toFixedTwo.format(result[4])));
					if(result[5] != null) {
						clothInvoiceDto.setDiscount(Double.parseDouble(toFixedTwo.format(result[5])));
					}else {
						clothInvoiceDto.setDiscount(0.0);
					}
					clothInvoiceDto.setTax(Double.parseDouble(toFixedTwo.format(result[6])));
					clothInvoiceDto.setTotal(Double.parseDouble(toFixedTwo.format(result[7])) );
					clothInvoiceDto.setPurchaseorder_id((Long) result[8]);
					clothInvoiceDto.setVendor_id((Integer) result[9]);
					clothInvoiceDto.setWareHouseLocation((Integer) result[10]);
					clothInvoiceDto.setVendorName((String) result[11]);
					clothInvoiceDto.setLocationName((String) result[12]);
					clothInvoiceDto.setClothTypesName((String) result[13]);
					
					if(result[14] != null) {
						clothInvoiceDto.setDiscountTaxTypeId((short) result[14]);
					}else {
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
	public void updateClothInventoryQuantity(Double quantity,int locationId,long inventoryId) {
		entityManager.createQuery(
				" UPDATE ClothInventoryDetails AS VC SET VC.quantity = VC.quantity - "+quantity+", "
						+ " VC.inTransitQuantity = (COALESCE(VC.inTransitQuantity,0) + "+quantity+")"
						+ " WHERE VC.clothInventoryDetailsId = "+inventoryId+" "
						+ " AND VC.markForDelete = 0 ")
				.executeUpdate();
	}
}
