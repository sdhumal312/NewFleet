package org.fleetopgroup.persistence.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.fleetopgroup.persistence.dto.VehicleLaundryDetailsDto;
import org.fleetopgroup.persistence.report.dao.VehicleLaundryDetailsDao;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleLaundryDetailsDaoImpl implements VehicleLaundryDetailsDao {

	@PersistenceContext EntityManager entityManager;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleLaundryDetailsDto> getVehicleLaundryDetailsList(Long invoiceId, Long clothTypeId)
			throws Exception {
		Query query = entityManager.createQuery(
				"SELECT VL.vehicleLaundryDetailsId, VL.vid, VL.clothTypesId, VL.laundryInvoiceId, VL.quantity, CT.clothTypeName, V.vehicle_registration"
				+ " FROM VehicleLaundryDetails VL"
				+ " INNER JOIN ClothTypes CT ON CT.clothTypesId = VL.clothTypesId "
				+ " INNER JOIN Vehicle V ON V.vid = VL.vid"
				+ " WHERE VL.laundryInvoiceId = "+invoiceId+" AND VL.clothTypesId = "+clothTypeId+" AND VL.markForDelete = 0");
		
		List<Object[]> results = null;
		try {
			results = query.getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		List<VehicleLaundryDetailsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleLaundryDetailsDto>();
			VehicleLaundryDetailsDto select;
			for (Object[] vehicle : results) {
				if (vehicle != null) {
					select = new VehicleLaundryDetailsDto();
					select.setVehicleLaundryDetailsId((Long) vehicle[0]);
					select.setVid((Integer) vehicle[1]);
					select.setClothTypesId((Long) vehicle[2]);
					select.setLaundryInvoiceId((Long) vehicle[3]);
					select.setQuantity((Double) vehicle[4]);
					select.setClothTypesStr((String) vehicle[5]);
					select.setVehicleRegistration((String) vehicle[6]);
					
					Dtos.add(select);
				}
			}
			
		}
		return Dtos;
		
	}
	
}
