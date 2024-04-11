package org.fleetopgroup.persistence.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.report.dao.DriverReportDao;
import org.springframework.stereotype.Repository;

@Repository
public class DriverReportDaoImpl implements DriverReportDao{
	
	@PersistenceContext
	EntityManager entityManager;
	
	SimpleDateFormat 					dateFormat_Name 			= new SimpleDateFormat("dd-MMM-yyyy");
	
	@Override
	public List<TripSheetDto> getDriverTripsheetAdvanceDetails(String advanceDetailQuery, int companyId) throws Exception {
		List<TripSheetDto>			tripSheetList			= null;		
		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
					" SELECT TS.tripSheetID, TS.tripSheetNumber, TS.vid, TS.tripOpenDate, TS.closetripDate, TS.routeName, "
							+ "	TS.tripFristDriverID, TS.closeTripAmount, TS.tripTotalAdvance, TS.tripTotalexpense, "
							+ "	D.driver_firstname, V.vehicle_registration,D.driver_Lastname, D.driver_fathername"
							+ "	FROM TripSheet TS "
							+ " INNER JOIN Driver D ON D.driver_id = TS.tripFristDriverID "
							+ " INNER JOIN Vehicle V ON V.vid = TS.vid "
							+ " WHERE "+advanceDetailQuery+" AND TS.companyId = "+companyId+" " 
							+ " AND TS.markForDelete = 0 "
							+ " ORDER BY TS.tripOpenDate DESC ",
							Object[].class);
			
			List<Object[]> results = query.getResultList();

			tripSheetList	= new ArrayList<TripSheetDto>();
			if (results != null && !results.isEmpty()) {
				TripSheetDto tripSheet = null;
				for (Object[] result : results) {
					tripSheet = new TripSheetDto();
					tripSheet.setTripSheetID((Long) result[0]);
					tripSheet.setTripSheetNumber((Long) result[1]);
					tripSheet.setVid((Integer) result[2]);
					tripSheet.setTripOpenDateOn((java.util.Date) result[3]);
					tripSheet.setTripOpenDate(dateFormat_Name.format((java.util.Date) result[3]));
					if(result[4] != null) {
						tripSheet.setClosetripDateOn((java.util.Date) result[4]);
						tripSheet.setClosetripDate(dateFormat_Name.format((java.util.Date) result[4]));
					} else {
						tripSheet.setClosetripDate("--");
					}
					tripSheet.setRouteName((String) result[5]);
					tripSheet.setTripFristDriverID((int) result[6]);
					if(result[7] != null) {
						tripSheet.setCloseTripAmount((double) result[7]);
					} else {
						tripSheet.setCloseTripAmount(0.0);
					}
					if(result[8] != null) {
						
						tripSheet.setTripTotalAdvance((double) result[8]);
					}else {
						tripSheet.setTripTotalAdvance(0.0);
					}
					if(result[9] != null) {
						tripSheet.setTripTotalexpense((double) result[9]);
					}else {
						tripSheet.setTripTotalexpense(0.0);
					}
					tripSheet.setTripFristDriverName((String) result[10]);
					tripSheet.setVehicle_registration((String) result[11]);
					if(result[12] != null && !((String) result[12]).trim().equals(""))
					tripSheet.setTripFristDriverName(tripSheet.getTripFristDriverName()+" "+result[12]);
					
					if(result[13] != null && !((String) result[13]).trim().equals(""))
					tripSheet.setTripFristDriverName(tripSheet.getTripFristDriverName()+" - "+result[13]);
					tripSheetList.add(tripSheet);
				}
			}
			
			return tripSheetList;
		} catch (Exception e) {
			throw e;
		} finally {
			tripSheetList		= null;
		}
	}
	
}