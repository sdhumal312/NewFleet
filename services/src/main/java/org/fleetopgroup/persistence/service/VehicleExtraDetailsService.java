package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.fleetopgroup.persistence.dto.VehicleExtraDetailsDto;
import org.fleetopgroup.persistence.serviceImpl.IVehicleExtraDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VehicleExtraDetailsService implements IVehicleExtraDetailsService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@PersistenceContext
	public EntityManager entityManager;

	@Override
	public VehicleExtraDetailsDto getVehicleExtraDetailsByVid(Integer vid) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT VE.vehicleExtraDetailsId, VE.vid, VE.busId, VE.deviceId, VE.companyId, VT.customerId, VT.vehicleTollDetailsId, VG.vehicleGPSCredentialId,"
					+ " VG.userName, VG.password, VT.walletId "
							+ " FROM VehicleExtraDetails VE  " 
							+ " LEFT JOIN VehicleTollDetails VT  ON VT.vehicleTollDetailsId = VE.vehicleTollDetailsId "
							+ " LEFT JOIN VehicleGPSCredential VG  ON VG.vehicleGPSCredentialId = VE.vehicleGPSCredentialId "
							+ " WHERE VE.vid = "+vid+"  AND VE.markForDelete = 0 order by VE.vehicleExtraDetailsId desc");

			
			Object[] result = null;
			try {
				query.setMaxResults(1);
				result = (Object[]) query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			
			VehicleExtraDetailsDto select	= null;
			if (result != null) {
				select = new VehicleExtraDetailsDto();
				
				select.setVehicleExtraDetailsId((Long) result[0]);
				select.setVid((Integer) result[1]);
				select.setBusId((long) result[2]);
				select.setDeviceId((long) result[3]);
				select.setCompanyId((Integer) result[4]);
				select.setCustomerId((String) result[5]);
				select.setVehicleTollDetailsId((Long) result[6]);
				select.setVehicleGPSCredentialId((Long) result[7]);
				select.setUserName((String) result[8]);
				select.setPassword((String) result[9]);
				select.setWalletId((String) result[10]);
			}
			
			return select;
		} catch (Exception e) {
			LOGGER.error("Eception : ", e);
			throw e;
		}
		
	}

}
