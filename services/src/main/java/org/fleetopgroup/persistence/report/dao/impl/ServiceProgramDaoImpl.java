package org.fleetopgroup.persistence.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.ServiceReminderType;
import org.fleetopgroup.persistence.dto.ServiceProgramSchedulesDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.VehicleServiceProgramDto;
import org.fleetopgroup.persistence.report.dao.ServiceProgramDao;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceProgramDaoImpl implements ServiceProgramDao {
	
	@PersistenceContext	EntityManager entityManager;
	
	SimpleDateFormat	format	= new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public VehicleServiceProgramDto getServiceProgramDetailsById(Long id) throws Exception {
		Query query = entityManager.createQuery(
				"SELECT VP.vehicleServiceProgramId, VP.programName, VP.description, VP.companyId, VP.createdById,"
				+ " VP.createdOn, VP.lastModifiedById, VP.lastModifiedOn, VP.isVendorProgram, U.firstName, "
				+ " U1.firstName, VP.companyId "
						+ " FROM VehicleServiceProgram VP "
						+ " INNER JOIN User U ON U.id = VP.createdById"
						+ " INNER JOIN User U1 ON U1.id = VP.lastModifiedById"
						+ " WHERE VP.vehicleServiceProgramId = "+id+" AND VP.markForDelete = 0");
		
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			
			VehicleServiceProgramDto list;
			if (result != null) {
				list = new VehicleServiceProgramDto();
	
				list.setVehicleServiceProgramId((Long) result[0]);
				list.setProgramName((String) result[1]);
				list.setDescription((String) result[2]);
				list.setCompanyId((Integer) result[3]);
				list.setCreatedById((Long) result[4]);
				list.setCreatedOn((Date) result[5]);
				list.setLastModifiedById((Long) result[6]);
				list.setLastModifiedOn((Date) result[7]);
				list.setVendorProgram((boolean) result[8]);
				list.setCreatedBy((String) result[9]);
				list.setLastModifedBy((String) result[10]);
				list.setCompanyId((Integer) result[11]);
				
				
				if(list.getCreatedOn() != null) {
					list.setCreatedOnStr(format.format(list.getCreatedOn()));
				}
				if(list.getLastModifiedOn() != null) {
					list.setLastModifiedOnOnStr(format.format(list.getLastModifiedOn()));
				}
				
	
			} else {
				return null;
			}

		return list;
	
	}
	
	@Override
	public List<ServiceProgramSchedulesDto> getServiceProgramSchedulesBtId(Long id) throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					"SELECT SE.serviceScheduleId, SE.vehicleServiceProgramId, SE.jobTypeId, SE.jobSubTypeId, SE.meterInterval,"
							+ " SE.timeInterval, SE.meterThreshold, SE.timeThreshold, SE.timeIntervalType, SE.companyId, JT.Job_Type,"
							+ " JST.Job_ROT, SE.timeThresholdType, SE.serviceTypeId, SE.service_subScribedUserId "
							+ " FROM ServiceProgramSchedules  AS SE "
							+ " INNER JOIN JobType JT ON JT.Job_id = SE.jobTypeId"
							+ " INNER JOIN JobSubType JST ON JST.Job_Subid = SE.jobSubTypeId"
							+ " WHERE SE.vehicleServiceProgramId = " +id
							+ " AND SE.markForDelete = 0 ORDER BY SE.vehicleServiceProgramId DESC ",
					Object[].class);
			
			List<Object[]> results = query.getResultList();

			List<ServiceProgramSchedulesDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceProgramSchedulesDto>();
				ServiceProgramSchedulesDto list = null;
				for (Object[] result : results) {
					list = new ServiceProgramSchedulesDto();

					list.setServiceScheduleId((Long) result[0]);
					list.setVehicleServiceProgramId((Long) result[1]);
					list.setJobTypeId((Integer) result[2]);
					list.setJobSubTypeId((Integer) result[3]);
					list.setMeterInterval((Integer) result[4]);
					list.setTimeInterval((Integer) result[5]);
					list.setMeterThreshold((Integer) result[6]);
					list.setTimeThreshold((Integer) result[7]);
					list.setTimeIntervalType((short) result[8]);
					list.setCompanyId((Integer) result[9]);
					list.setJobType((String) result[10]);
					list.setJobSubType((String) result[11]);
					list.setTimeThresholdType((short) result[12]);
					if(result[13] != null) {
						list.setServiceTypeId((short) result[13]);
						list.setServiceType(ServiceReminderType.getServiceReminderType(list.getServiceTypeId()));
					}
					
					if(list.getMeterInterval() > 0 && list.getTimeInterval() > 0) {
						list.setTimeThresholdStr(list.getMeterThreshold()+" KM OR "+list.getTimeThreshold()+" "+ServiceReminderDto.getTimeInterValName((short) result[12])+" Before ");
						list.setTimeIntervalTypeStr("Every "+list.getMeterInterval()+" KM OR "+list.getTimeInterval()+" "+ServiceReminderDto.getTimeInterValName((short) result[8]));
					}else if(list.getMeterInterval() > 0 && list.getTimeInterval() <= 0) {
						list.setTimeThresholdStr(list.getMeterThreshold()+" KM Before ");
						list.setTimeIntervalTypeStr("Every "+list.getMeterInterval()+" KM ");
					}else if(list.getMeterInterval() <= 0 && list.getTimeInterval() > 0) {
						list.setTimeThresholdStr(list.getTimeThreshold()+" "+ServiceReminderDto.getTimeInterValName((short) result[12])+" Before ");
						list.setTimeIntervalTypeStr("Every "+list.getTimeInterval()+" "+ServiceReminderDto.getTimeInterValName((short) result[8]));
					}
					list.setService_subScribedUserId((String)result[14]);

					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public HashMap<Long, Long> getServiceProgramWiseCountHM(Integer companyId) throws Exception {
		HashMap<Long, Long> 	scheduleCountHM		= null;
		try {
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					"SELECT SE.vehicleServiceProgramId, count(SE.serviceScheduleId)"
							+ " FROM ServiceProgramSchedules  AS SE "
							+ " WHERE SE.companyId = " +companyId
							+ " AND SE.markForDelete = 0 GROUP BY SE.vehicleServiceProgramId ",
					Object[].class);
			
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				scheduleCountHM = new HashMap<Long, Long>();
				for (Object[] result : results) {
					scheduleCountHM.put((Long) result[0], (Long) result[1]);
				}
			}
			
			return scheduleCountHM;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
}
