package org.fleetopgroup.persistence.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToPartsDto;
import org.fleetopgroup.persistence.report.dao.WorkOrdersDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class WorkOrdersDaoImpl implements WorkOrdersDao {

	@PersistenceContext
	EntityManager entityManager;
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Override
	public List<WorkOrdersDto> getWorkOrdersList(WorkOrdersDto workOrdersDto) throws Exception {
		List<WorkOrdersDto>			workOrdersList			= null;		
		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
					"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, WO.start_date,WO.due_date, WO.totalworkorder_cost"
							+ "	from WorkOrders WO "
							+ " where WO.vehicle_vid = " + workOrdersDto.getVehicle_vid() 
							+ " AND (WO.start_date BETWEEN '"+workOrdersDto.getStart_date()+"' AND '"+workOrdersDto.getCompleted_date()+"'"
							+ " OR WO.due_date BETWEEN '"+workOrdersDto.getStart_date() +"' AND '"+workOrdersDto.getCompleted_date()+"')"
							+ " AND WO.companyId = "+workOrdersDto.getCompanyId()+" AND WO.markForDelete  = 0 ORDER BY WO.workorders_id ASC",
							Object[].class);
			
			List<Object[]> results = query.getResultList();

			workOrdersList	= new ArrayList<WorkOrdersDto>();
			if (results != null && !results.isEmpty()) {
				WorkOrdersDto ordersDto = null;
				for (Object[] result : results) {
					ordersDto = new WorkOrdersDto();
					ordersDto.setWorkorders_id((Long) result[0]);
					ordersDto.setWorkorders_Number((Long) result[1]);
					ordersDto.setVehicle_vid((Integer) result[2]);
					ordersDto.setStart_dateOn((java.util.Date) result[3]);
					ordersDto.setDue_dateOn((java.util.Date) result[4]);
					ordersDto.setTotalworkorder_cost((Double) result[5]);

					workOrdersList.add(ordersDto);
				}
			}
			
			return workOrdersList;
		} catch (Exception e) {
			throw e;
		} finally {
			workOrdersList		= null;
		}
	}
	
	@Override
	public List<WorkOrdersTasksToPartsDto> getOldPartReceivedByVehicleGroup(WorkOrdersTasksToPartsDto	workOrders, String sqlQuery) throws Exception {
		List<WorkOrdersTasksToPartsDto>			workOrdersList			= null;		
		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
					"SELECT WTP.workordertaskto_partid, WTP.partid, MP.partnumber, MP.partname, WO.workorders_Number, V.vehicle_registration,"
					+ " PL.partlocation_name, WTP.quantity, WTP.parteachcost, WTP.totalcost, WO.workorders_id "
					+ " FROM WorkOrdersTasksToParts AS WTP"
					+ " INNER JOIN WorkOrders AS WO ON WO.workorders_id = WTP.workorders_id"
					+ " INNER JOIN Vehicle AS V ON V.vid = WO.vehicle_vid "
					+ " INNER JOIN MasterParts MP ON MP.partid = WTP.partid"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = WTP.locationId"
					+ " WHERE WTP.companyId = "+workOrders.getCompanyId()+" AND WTP.oldpart = 'RECEIVED' "+sqlQuery+""
					+ " AND WO.start_date BETWEEN '" + workOrders.getFromDate() + "' AND  '" + workOrders.getToDate() + "' "
					+ " AND WTP.markForDelete = 0 order by WTP.workordertaskto_partid DESC",
							Object[].class);
			
			List<Object[]> results = query.getResultList();

			workOrdersList	= new ArrayList<WorkOrdersTasksToPartsDto>();
			if (results != null && !results.isEmpty()) {
				WorkOrdersTasksToPartsDto ordersDto = null;
				for (Object[] result : results) {
					ordersDto = new WorkOrdersTasksToPartsDto();
					
					ordersDto.setWorkordertaskto_partid((Long) result[0]);
					ordersDto.setPartid((Long) result[1]);
					ordersDto.setPartnumber((String) result[2]);
					ordersDto.setPartname((String) result[3]);
					ordersDto.setWorkOrderNumber((Long) result[4]);
					ordersDto.setVehicle_registration((String) result[5]);
					ordersDto.setLocation((String) result[6]);
					ordersDto.setQuantity((Double) result[7]);
					ordersDto.setParteachcost((Double) result[8]);
					ordersDto.setTotalcost((Double) result[9]);
					ordersDto.setWorkorders_id((Long) result[10]);
					
					workOrdersList.add(ordersDto);
				}
			}
			
			return workOrdersList;
		} catch (Exception e) {
			throw e;
		} finally {
			workOrdersList		= null;
		}
	}
	
	@Override
	public List<WorkOrdersTasksToPartsDto> getOldPartNotReceived(WorkOrdersTasksToPartsDto workOrders,
			String sqlQuery) throws Exception {
		List<WorkOrdersTasksToPartsDto>			workOrdersList			= null;		
		SimpleDateFormat dateName = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
					"SELECT WTP.workordertaskto_partid, WTP.partid, MP.partnumber, MP.partname, WO.workorders_Number, V.vehicle_registration,"
					+ " PL.partlocation_name, WTP.quantity, WTP.parteachcost, WTP.totalcost, WO.workorders_id, WO.completed_date "
					+ " FROM WorkOrdersTasksToParts AS WTP"
					+ " INNER JOIN WorkOrders AS WO ON WO.workorders_id = WTP.workorders_id"
					+ " INNER JOIN Vehicle AS V ON V.vid = WO.vehicle_vid "
					+ " INNER JOIN MasterParts MP ON MP.partid = WTP.partid"
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = WTP.locationId"
					+ " WHERE WTP.companyId = "+workOrders.getCompanyId()+" AND ( WTP.oldpart = '' OR WTP.oldpart = "+null+")  "+sqlQuery+""
					+ " AND WO.start_date BETWEEN '" + workOrders.getFromDate() + "' AND  '" + workOrders.getToDate() + "' "
					+ " AND WTP.markForDelete = 0 order by WTP.workordertaskto_partid DESC",
							Object[].class);
			
			List<Object[]> results = query.getResultList();
			
			workOrdersList	= new ArrayList<WorkOrdersTasksToPartsDto>();
			if (results != null && !results.isEmpty()) {
				WorkOrdersTasksToPartsDto ordersDto = null;
				
				
				for (Object[] result : results) {
									
					ordersDto = new WorkOrdersTasksToPartsDto();
					
					ordersDto.setWorkordertaskto_partid((Long) result[0]);
					ordersDto.setPartid((Long) result[1]);
					ordersDto.setPartnumber((String) result[2]);
					ordersDto.setPartname((String) result[3]);
					ordersDto.setWorkOrderNumber((Long) result[4]);
					ordersDto.setVehicle_registration((String) result[5]);
					ordersDto.setLocation((String) result[6]);
					ordersDto.setQuantity((Double) result[7]);
					ordersDto.setParteachcost((Double) result[8]);
					ordersDto.setTotalcost((Double) result[9]);
					ordersDto.setWorkorders_id((Long) result[10]);
					if((Date) result[11] !=null) {					
						ordersDto.setComplitionDate(dateName.format((Date) result[11]));
					}
					else{
						ordersDto.setComplitionDate("-");
					}
					workOrdersList.add(ordersDto);
				}
			}
			
			return workOrdersList;
		} catch (Exception e) {
			LOGGER.error("Error In Controller : ", e);
			throw e;
		} finally {
			workOrdersList		= null;
		}
	}
}
