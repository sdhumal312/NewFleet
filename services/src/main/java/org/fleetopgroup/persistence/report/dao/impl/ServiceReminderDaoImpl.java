package org.fleetopgroup.persistence.report.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.ServiceReminderType;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.model.ServiceReminderWorkOrderHistory;
import org.fleetopgroup.persistence.report.dao.ServiceReminderDao;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceReminderDaoImpl implements ServiceReminderDao {
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired private ICompanyConfigurationService		companyConfigurationService;
	
	@Override
	public List<ServiceReminderDto> getServiceReminderList(ServiceReminderDto serviceReminderDto) throws Exception {
		
		TypedQuery<Object[]> query =	null;
		try {
			
			if(companyConfigurationService.getVehicleGroupWisePermission(serviceReminderDto.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT SR.service_id, SR.service_Number, SR.vid, V.vehicle_registration, SR.time_servicethreshold_date, SR.time_servicedate "
						+ " FROM ServiceReminder AS SR "
						+ " INNER JOIN Vehicle AS V ON V.vid = SR.vid "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+serviceReminderDto.getUserId()+" "
						+ " WHERE SR.time_servicethreshold_date between '"+serviceReminderDto.getFromDate()+"' AND '"+serviceReminderDto.getToDate()+"'"
								+ "  AND SR.companyId = "+serviceReminderDto.getCompanyId()+" AND SR.markForDelete = 0",
						Object[].class);
			}else {
				 query = entityManager.createQuery(
						"SELECT SR.service_id, SR.service_Number, SR.vid, V.vehicle_registration, SR.time_servicethreshold_date, SR.time_servicedate "
						+ " FROM ServiceReminder AS SR "
						+ " INNER JOIN Vehicle AS V ON V.vid = SR.vid "
						+ " WHERE SR.time_servicethreshold_date between '"+serviceReminderDto.getFromDate()+"' AND '"+serviceReminderDto.getToDate()+"'"
								+ "  AND SR.companyId = "+serviceReminderDto.getCompanyId()+" AND SR.markForDelete = 0",
						Object[].class);
			}
			
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			Dtos = new ArrayList<ServiceReminderDto>();
			if (results != null && !results.isEmpty()) {
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					list.setService_Number((Long) result[1]);
					list.setVid((Integer) result[2]);
					list.setVehicle_registration((String) result[3]);
					list.setTime_servicethreshold_date( (Date) result[4]);
					list.setTime_servicedate( (Date) result[5]);
					
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}finally {
			query =	null;
		}
	}
	
	@Override
	public List<ServiceReminderDto> getServiceReminderListByOdometer(ServiceReminderDto serviceReminderDto)
			throws Exception {
		TypedQuery<Object[]> query = null;
		try {
			if(companyConfigurationService.getVehicleGroupWisePermission(serviceReminderDto.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT SR.service_id, SR.service_Number, SR.vid, SR.serviceOdometerUpdatedDate, SR.time_servicedate "
						+ " FROM ServiceReminder AS SR "
						+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+serviceReminderDto.getUserId()+" "
						+ " WHERE SR.serviceOdometerUpdatedDate between '"+serviceReminderDto.getFromDate()+"' AND '"+serviceReminderDto.getToDate()+"'"
							+ " AND SR.time_servicethreshold_date NOT between '"+serviceReminderDto.getFromDate()+"' AND '"+serviceReminderDto.getToDate()+"'"
							+ "  AND SR.companyId = "+serviceReminderDto.getCompanyId()+" AND SR.markForDelete = 0",
						Object[].class);
			}else {
				query = entityManager.createQuery(
						"SELECT SR.service_id, SR.service_Number, SR.vid, SR.serviceOdometerUpdatedDate, SR.time_servicedate "
						+ " FROM ServiceReminder AS SR "
						+ " WHERE SR.serviceOdometerUpdatedDate between '"+serviceReminderDto.getFromDate()+"' AND '"+serviceReminderDto.getToDate()+"'"
						+ " AND SR.time_servicethreshold_date NOT between '"+serviceReminderDto.getFromDate()+"' AND '"+serviceReminderDto.getToDate()+"'"
						+ "  AND SR.companyId = "+serviceReminderDto.getCompanyId()+" AND SR.markForDelete = 0",
						Object[].class);
			}
			
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			Dtos = new ArrayList<ServiceReminderDto>();
			if (results != null && !results.isEmpty()) {
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					list.setService_Number((Long) result[1]);
					list.setVid((Integer) result[2]);
					list.setTime_servicethreshold_date( (Date) result[3]);
					list.setTime_servicedate( (Date) result[4]);
					
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}finally {
			query =	null;
		}
	}
	
	@Override
	public List<ServiceReminderWorkOrderHistory> getServiceWorkOrderList(ServiceReminderDto serviceReminderDto)
			throws Exception {
		TypedQuery<Object[]> query =	null;
		try {
			
			if(companyConfigurationService.getVehicleGroupWisePermission(serviceReminderDto.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT SR.service_id, SR.service_Number, SR.serviceThresholdDate, SR.serviceDate "
						+ " FROM ServiceReminderWorkOrderHistory AS SR "
						+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+serviceReminderDto.getUserId()+" "
						+ " WHERE SR.serviceThresholdDate between '"+serviceReminderDto.getFromDate()+"' AND '"+serviceReminderDto.getToDate()+"'"
								+ "  AND SR.companyId = "+serviceReminderDto.getCompanyId()+" AND SR.markForDelete = 0",
						Object[].class);
			}else {
				query = entityManager.createQuery(
						"SELECT SR.service_id, SR.service_Number, SR.serviceThresholdDate, SR.serviceDate "
						+ " FROM ServiceReminderWorkOrderHistory AS SR "
						+ " WHERE SR.serviceThresholdDate between '"+serviceReminderDto.getFromDate()+"' AND '"+serviceReminderDto.getToDate()+"'"
								+ "  AND SR.companyId = "+serviceReminderDto.getCompanyId()+" AND SR.markForDelete = 0",
						Object[].class);
			}
			
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderWorkOrderHistory> Dtos = null;
			Dtos = new ArrayList<ServiceReminderWorkOrderHistory>();
			if (results != null && !results.isEmpty()) {
				ServiceReminderWorkOrderHistory list = null;
				for (Object[] result : results) {
					list = new ServiceReminderWorkOrderHistory();
					
					list.setService_id((Long) result[0]);
					list.setService_Number((Long) result[1]);
					list.setServiceThresholdDate( (Date) result[2]);
					list.setServiceDate( (Date) result[3]);
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}finally {
			query =	null;
		}
	}

	@Override
	public List<ServiceReminderDto> getServiceReminderListOfDay(ServiceReminderDto serviceReminderDto)
			throws Exception {
		
		TypedQuery<Object[]> query =	null;
		try {
			
			if(companyConfigurationService.getVehicleGroupWisePermission(serviceReminderDto.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT SR.service_id, SR.service_Number, SR.vid, V.vehicle_registration, SR.time_servicethreshold_date, SR.time_servicedate,"
						+ "JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT "
						+ " FROM ServiceReminder AS SR "
						+ " INNER JOIN Vehicle AS V ON V.vid = SR.vid "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+serviceReminderDto.getUserId()+" "
						+ " LEFT JOIN JobType JT ON JT.Job_id = SR.serviceTypeId"
						+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = SR.serviceSubTypeId"
						+ " WHERE SR.time_servicethreshold_date between '"+serviceReminderDto.getFromDate()+"' AND '"+serviceReminderDto.getToDate()+"'"
								+ "  AND SR.companyId = "+serviceReminderDto.getCompanyId()+" AND SR.markForDelete = 0",
						Object[].class);
			}else {
				 query = entityManager.createQuery(
						"SELECT SR.service_id, SR.service_Number, SR.vid, V.vehicle_registration, SR.time_servicethreshold_date, SR.time_servicedate, "
						+ "JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT  "
						+ " FROM ServiceReminder AS SR "
						+ " INNER JOIN Vehicle AS V ON V.vid = SR.vid "
						+ " LEFT JOIN JobType JT ON JT.Job_id = SR.serviceTypeId"
						+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = SR.serviceSubTypeId"
						+ " WHERE SR.time_servicethreshold_date between '"+serviceReminderDto.getFromDate()+"' AND '"+serviceReminderDto.getToDate()+"'"
								+ "  AND SR.companyId = "+serviceReminderDto.getCompanyId()+" AND SR.markForDelete = 0",
						Object[].class);
			}
			
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			Dtos = new ArrayList<ServiceReminderDto>();
			if (results != null && !results.isEmpty()) {
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					list.setService_Number((Long) result[1]);
					list.setVid((Integer) result[2]);
					list.setVehicle_registration((String) result[3]);
					list.setTime_servicethreshold_date( (Date) result[4]);
					list.setTime_servicedate( (Date) result[5]);
					list.setService_type((String) result[6]+" - "+(String) result[7]+" - "+(String) result[8]);					
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}finally {
			query =	null;
		}
	}

	@Override
	public List<ServiceReminderDto> getServiceReminderListByOdometerOfDay(ServiceReminderDto serviceReminderDto)
			throws Exception {
		TypedQuery<Object[]> query = null;
		try {
			if(companyConfigurationService.getVehicleGroupWisePermission(serviceReminderDto.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT SR.service_id, SR.service_Number, SR.vid, V.vehicle_registration, SR.serviceOdometerUpdatedDate, SR.time_servicedate,"
						+ "JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT  "
						+ " FROM ServiceReminder AS SR "
						+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+serviceReminderDto.getUserId()+" "
						+ " LEFT JOIN JobType JT ON JT.Job_id = SR.serviceTypeId"
						+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = SR.serviceSubTypeId"
						+ " WHERE SR.serviceOdometerUpdatedDate between '"+serviceReminderDto.getFromDate()+"' AND '"+serviceReminderDto.getToDate()+"'"
						+ " AND SR.time_servicethreshold_date NOT between '"+serviceReminderDto.getFromDate()+"' AND '"+serviceReminderDto.getToDate()+"'"
						+ "  AND SR.companyId = "+serviceReminderDto.getCompanyId()+" AND SR.markForDelete = 0",
						Object[].class);
			}else {
				query = entityManager.createQuery(
						"SELECT SR.service_id, SR.service_Number, SR.vid, V.vehicle_registration, SR.serviceOdometerUpdatedDate, SR.time_servicedate,"
						+ "JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT  "
						+ " FROM ServiceReminder AS SR "
						+ " INNER JOIN Vehicle AS V ON V.vid = SR.vid "
						+ " LEFT JOIN JobType JT ON JT.Job_id = SR.serviceTypeId"
						+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = SR.serviceSubTypeId"
						+ " WHERE SR.serviceOdometerUpdatedDate between '"+serviceReminderDto.getFromDate()+"' AND '"+serviceReminderDto.getToDate()+"'"
						+ " AND SR.time_servicethreshold_date NOT between '"+serviceReminderDto.getFromDate()+"' AND '"+serviceReminderDto.getToDate()+"'"
						+ "  AND SR.companyId = "+serviceReminderDto.getCompanyId()+" AND SR.markForDelete = 0",
						Object[].class);
			}
			
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			Dtos = new ArrayList<ServiceReminderDto>();
			if (results != null && !results.isEmpty()) {
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					list.setService_Number((Long) result[1]);
					list.setVid((Integer) result[2]);
					list.setVehicle_registration((String) result[3]);
					list.setTime_servicethreshold_date( (Date) result[4]);
					list.setTime_servicedate( (Date) result[5]);
					list.setService_type((String) result[6]+" - "+(String) result[7]+" - "+(String) result[8]);
					
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}finally {
			query =	null;
		}
	}

	@Override
	public List<ServiceReminderDto> getServiceWorkOrderListOfDay(ServiceReminderDto serviceReminderDto)
			throws Exception {
		TypedQuery<Object[]> query =	null;
		try {
			
			if(companyConfigurationService.getVehicleGroupWisePermission(serviceReminderDto.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT SR.service_id, SR.service_Number, SR.vid, V.vehicle_registration, S.time_servicethreshold_date, S.time_servicedate,"
								+ "JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT, SR.workOrderId, WO.workorders_Number, WO.vehicle_Odometer, WO.completed_date  "
								+ " FROM ServiceReminderWorkOrderHistory AS SR "
								+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
								+ " INNER JOIN WorkOrders AS WO ON WO.workorders_id = SR.workOrderId"
								+ " INNER JOIN ServiceReminder S ON S.service_id = SR.service_id"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+serviceReminderDto.getUserId()+" "
								+ " LEFT JOIN JobType JT ON JT.Job_id = S.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = S.serviceSubTypeId"
								+ " WHERE SR.serviceThresholdDate between '"+serviceReminderDto.getFromDate()+"' AND '"+serviceReminderDto.getToDate()+"'"
								+ "  AND SR.companyId = "+serviceReminderDto.getCompanyId()+" AND SR.markForDelete = 0",
						Object[].class);
			}else {
				query = entityManager.createQuery(
						"SELECT SR.service_id, SR.service_Number, SR.vid, V.vehicle_registration, S.time_servicethreshold_date, S.time_servicedate,"
								+ "JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT, SR.workOrderId, WO.workorders_Number, WO.vehicle_Odometer, WO.completed_date  "
								+ " FROM ServiceReminderWorkOrderHistory AS SR "
								+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
								+ " INNER JOIN WorkOrders AS WO ON WO.workorders_id = SR.workOrderId"
								+ " INNER JOIN ServiceReminder S ON S.service_id = SR.service_id"
								+ " LEFT JOIN JobType JT ON JT.Job_id = S.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = S.serviceSubTypeId"
								+ " WHERE SR.serviceThresholdDate between '"+serviceReminderDto.getFromDate()+"' AND '"+serviceReminderDto.getToDate()+"'"
								+ "  AND SR.companyId = "+serviceReminderDto.getCompanyId()+" AND SR.markForDelete = 0",
						Object[].class);
			}
			
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			Dtos = new ArrayList<ServiceReminderDto>();
			if (results != null && !results.isEmpty()) {
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					list.setService_Number((Long) result[1]);
					list.setVid((Integer) result[2]);
					list.setVehicle_registration((String) result[3]);
					list.setTime_servicethreshold_date( (Date) result[4]);
					list.setTime_servicedate( (Date) result[5]);
					list.setService_type((String) result[6]+" - "+(String) result[7]+" - "+(String) result[8]);
					list.setWorkOrderId((Long) result[9]);
					list.setWorkOrderNumber((Long) result[10]);
					list.setServiceOdometer((Integer) result[11]);
					list.setServicedOn((Date) result[12]);
					
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}finally {
			query =	null;
		}
	}
	
	@Override
	public List<ServiceReminderDto> getReminderStatusForOverdue(Integer vid, String fromDate, String toDate) throws Exception {
		
		TypedQuery<Object[]> query =	null;
		try {
				query = entityManager.createQuery(
						"SELECT SR.service_id, SR.service_Number,SR.time_servicethreshold_date, SR.time_servicedate"
						+ " FROM ServiceReminder AS SR "
						+ " WHERE ( SR.time_servicedate between '"+fromDate+"' AND '"+toDate+"' OR "
						+ " SR.vehicle_currentOdometer >=SR.meter_serviceodometer) " 
						+ "  AND SR.vid = "+vid+" AND SR.serviceType = "+ServiceReminderType.SERVICE_TYPE_MANDATORY+" AND SR.markForDelete = 0",
						Object[].class);
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			Dtos = new ArrayList<ServiceReminderDto>();
			if (results != null && !results.isEmpty()) {
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					list.setService_Number((Long) result[1]);
					list.setTime_servicethreshold_date( (Date) result[2]);
					list.setTime_servicedate( (Date) result[3]);
					
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}finally {
			query =	null;
		}
	}
	
}
