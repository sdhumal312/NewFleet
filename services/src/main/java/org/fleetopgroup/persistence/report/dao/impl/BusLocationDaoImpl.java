/**
 * 
 */
package org.fleetopgroup.persistence.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dto.BusLocationDto;
import org.fleetopgroup.persistence.model.BusLocation;
import org.fleetopgroup.persistence.report.dao.BusLocationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author SANJU
 *
 */
@Repository
public class BusLocationDaoImpl implements BusLocationDao {

	SimpleDateFormat sqldateTime = new SimpleDateFormat("dd-MM-yyyy");
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext	EntityManager entityManager;
	
	@Override
	public boolean checkVehicleStatus(BusLocation busLocation) throws Exception {
		TypedQuery<Object[]> queryt 			= null;
		
		try {
			queryt = entityManager.createQuery(
					" SELECT bl.busLocationId FROM BusLocation bl "
							+ " WHERE bl.companyId = " + busLocation.getCompanyId()
							+ " AND bl.vehicleNumberMasterId = " + busLocation.getVehicleNumberMasterId()
							+ " AND bl.driverId = " + busLocation.getDriverId()
							+ " AND bl.busOutDateTime IS NULL"
							+ " AND bl.markForDelete = 0",
					Object[].class);

			List<Object[]> results = queryt.getResultList();
			
			return results != null && !results.isEmpty();
		} catch (Exception e) {
			LOGGER.error("Excep", e);
			throw e;
		}
	}
	
	@Override
	public List<BusLocationDto> getBusLocationReportList(String Query, Integer companyId) throws Exception {
		TypedQuery<Object[]> queryt 			= null;
		List<BusLocationDto> busLocationDtoList = null;
		
		try {
			queryt = entityManager.createQuery(
					" SELECT bl.busLocationId, bl.vehicleNumberMasterId, bl.driverId, bl.sourceLocationId, bl.destinationLocationId, bl.busBookingDate"
							+ ", pl.partlocation_name, pl1.partlocation_name, V.vehicle_registration, d.driver_firstname, d.driver_Lastname"
							+ ", bl.busOutDateTime"
							+ " FROM BusLocation bl "
							+ " INNER JOIN Vehicle V on V.vid = bl.vehicleNumberMasterId "
							+ " INNER JOIN PartLocations pl on pl.partlocation_id = bl.sourceLocationId "
							+ " INNER JOIN PartLocations pl1 on pl1.partlocation_id = bl.destinationLocationId "
							+ " INNER JOIN Driver d on d.driver_id = bl.driverId "
							+ " WHERE bl.companyId = " + companyId
							+ " AND bl.markForDelete = 0 " + Query + " ",
					Object[].class);
			//queryt.setParameter("comID", companyId);
			List<Object[]> results = queryt.getResultList();
			
			busLocationDtoList	= new ArrayList<>();
			
			if (results != null && !results.isEmpty()) {
				for (Object[] result : results) {
					BusLocationDto	busLocationDto	= new BusLocationDto();
					busLocationDto.setBusLocationId((long) result[0]);
					busLocationDto.setVehicleNumberMatserId((long) result[1]);
					busLocationDto.setDriverMatserId((long) result[2]);
					busLocationDto.setSourceLocationId((long) result[3]);
					busLocationDto.setDestinationLocationId((long) result[4]);
					busLocationDto.setBusBookingDateStr(sqldateTime.format((Date) result[5]));
					busLocationDto.setSourceLocationName((String) result[6]);
					busLocationDto.setDestinationLocationName((String) result[7]);
					busLocationDto.setVehicleNumber((String) result[8]);
					busLocationDto.setDriverName((String) result[9] + " " + (String) result[10]);
					
					if(result[11] != null)
						busLocationDto.setBusOutDateStr(sqldateTime.format((Date) result[11]));
					
					busLocationDtoList.add(busLocationDto);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Excep", e);
			throw e;
		}
		
		return busLocationDtoList;
	}

}
