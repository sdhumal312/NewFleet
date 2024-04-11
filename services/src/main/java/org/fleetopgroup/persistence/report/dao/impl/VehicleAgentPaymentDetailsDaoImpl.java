package org.fleetopgroup.persistence.report.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.fleetopgroup.persistence.dao.VehicleAgentPaymentDetailsRepository;
import org.fleetopgroup.persistence.model.VehicleAgentPaymentDetails;
import org.fleetopgroup.persistence.report.dao.VehicleAgentPaymentDetailsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleAgentPaymentDetailsDaoImpl implements VehicleAgentPaymentDetailsDao {

	@PersistenceContext
	public EntityManager entityManager;

	@Autowired	VehicleAgentPaymentDetailsRepository	vehicleAgentPaymentDetailsRepository;
	
	@Override
	public void saveVehicleAgentPayment(VehicleAgentPaymentDetails vehicleAgentPaymentDetails) throws Exception {
		
		vehicleAgentPaymentDetailsRepository.save(vehicleAgentPaymentDetails);
	}
	
	@Override
	public VehicleAgentPaymentDetails validatePaymentAfterDate(String date, Integer vid) throws Exception {
		VehicleAgentPaymentDetails		vehicleAgentPaymentDetails	= null;
		try {

			Query	query = entityManager.createQuery("SELECT VP FROM VehicleAgentPaymentDetails AS VP where vid = "+vid+" AND paymentDate > '"+date+"' "
							+ " AND markForDelete = 0 order by paymentDate desc", VehicleAgentPaymentDetails.class);
	
			try {
				query.setMaxResults(1);
				vehicleAgentPaymentDetails = (VehicleAgentPaymentDetails) query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
		
		return vehicleAgentPaymentDetails;

		} catch (Exception e) {
			throw e;
		}finally {
			vehicleAgentPaymentDetails	= null;
		}
	}
}
