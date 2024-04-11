package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dto.UreaEntriesDto;
import org.fleetopgroup.persistence.report.dao.IUreaReportDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UreaReportDaoImpl implements IUreaReportDao {
	
	SimpleDateFormat simpleFormat = new SimpleDateFormat("dd-MM-yyyy");
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	@PersistenceContext	EntityManager entityManager;
	
	@Transactional
	public List<UreaEntriesDto> getUreaEntryDetails_Report(String query,Integer companyId) throws Exception {
		UreaEntriesDto 				ureaDto			    = null;	
		TypedQuery<Object[]> 		typedQuery 			= null;
		List<Object[]> 				results				= null;
		try {
			
			typedQuery = entityManager.createQuery(
				" SELECT U.ureaEntriesId, U.ureaEntriesNumber, U.ureaDate, U.ureaLiters, U.ureaRate ,U.ureaAmount, U.ureaOdometerOld, U.ureaOdometer,"
						+ " U.vid, V.vehicle_registration, D.driver_id, D.driver_firstname, U.secDriverID, D1.driver_firstname , "
						+ " U.cleanerID, D2.driver_firstname, U.routeID, T.routeName, U.created ,D.driver_Lastname,D.driver_fathername ,D1.driver_Lastname,D1.driver_fathername  "
						+ " From UreaEntries as U "
						+ " INNER JOIN Vehicle as V ON V.vid = U.vid "
						+ " LEFT JOIN Driver as D ON D.driver_id = U.driver_id "
						+ " LEFT JOIN Driver as D1 ON D1.driver_id = U.secDriverID "
						+ " LEFT JOIN Driver as D2 ON D2.driver_id = U.cleanerID "
						+ " LEFT JOIN TripRoute as T ON T.routeID = U.routeID "
						+ " WHERE U.companyId = "+companyId+" AND  U.markForDelete = 0 "+ query + " ", Object[].class);
			
		results = typedQuery.getResultList();
		List<UreaEntriesDto> dtoList = null;
		if (results != null && !results.isEmpty()) {
			
			dtoList = new ArrayList<UreaEntriesDto>();
			for (Object[] result : results) {
				ureaDto = new UreaEntriesDto();
				
				ureaDto.setUreaEntriesId((Long) result[0]);	
				ureaDto.setUreaEntriesNumberStr("UE-"+result[1]);
				ureaDto.setUreaDateStr(simpleFormat.format((Timestamp)result[2]));
				ureaDto.setUreaLiters((Double)result[3]);
				ureaDto.setUreaRate((Double)result[4]);
				ureaDto.setUreaAmount((Double)result[5]);
				ureaDto.setUreaOdometerOld((Double)result[6]);
				ureaDto.setUreaOdometer((Double)result[7]);
				ureaDto.setTotalOdometer((Double)result[7]-(Double)result[6]);
				ureaDto.setVid((Integer)result[8]);
				ureaDto.setVehicle_registration((String)result[9]);
				ureaDto.setDriver_id((Integer)result[10]);
				if(result[11] != null) {
					ureaDto.setFirsDriverName((String)result[11]);
				}else {
					ureaDto.setFirsDriverName("-");
				}
				
				ureaDto.setSecDriverID((Integer)result[12]);
				if(result[13] != null) {
					ureaDto.setSecDriverName((String)result[13]);
				}else {
					ureaDto.setSecDriverName("-");
				}
				
				ureaDto.setCleanerID((Integer)result[14]);
				
				if(result[15] != null) {
					ureaDto.setCleanerName((String)result[15]);
				}else {
					ureaDto.setCleanerName("-");
				}
				ureaDto.setRouteID((Integer)result[16]);
				
				if(result[17] != null) {
					ureaDto.setRouteName((String)result[17]);
				}else {
					ureaDto.setRouteName("-");
				}
				
				if(ureaDto.getTotalOdometer() != null) {
					ureaDto.setKmpl(ureaDto.getTotalOdometer()/ureaDto.getUreaLiters());
				}
				
				if(result[19] != null) 
					ureaDto.setFirsDriverName(ureaDto.getFirsDriverName()+" "+result[19]);
				if(result[20] != null) 
					ureaDto.setFirsDriverName(ureaDto.getFirsDriverName()+" - "+result[20]);
					
				if(result[21] != null) 
					ureaDto.setSecDriverName(ureaDto.getSecDriverName() +" "+result[21]);
				if(result[22] != null) 
					ureaDto.setSecDriverName(ureaDto.getSecDriverName() +" - "+result[22]);
					
					
				//ureaDto.setCreated((simpleFormat.format((Timestamp)result[18])));
				
				dtoList.add(ureaDto);
			}
		} 
		return dtoList;
		}catch (Exception e) {
			LOGGER.error("Excep", e);
			throw e;
		} finally {
		}
	}
	
	
	
}	