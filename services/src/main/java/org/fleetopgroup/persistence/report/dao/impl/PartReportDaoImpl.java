package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.WorkOrdersType;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToPartsDto;
import org.fleetopgroup.persistence.report.dao.IPartReportDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Repository;

@Repository
public class PartReportDaoImpl implements IPartReportDao {
	
	SimpleDateFormat sqldateTime = new SimpleDateFormat("dd-MM-yyyy");
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	private static final int PAGE_SIZE = 10;
	@PersistenceContext	EntityManager entityManager;

	@Override
	public List<WorkOrdersTasksToPartsDto> getWorkOrderPartConsumptionList(String query, Integer companyId)
			throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, WTP.partid, WTP.quantity, WTP.parteachcost, WTP.totalcost, V.vehicle_registration,"
							+ " MP.partnumber, MP.partname, WO.completed_date, D.driver_firstname, D.driver_Lastname "
							+ " FROM WorkOrders  AS WO  "
							+ " INNER JOIN WorkOrdersTasksToParts WTP ON WTP.workorders_id= WO.workorders_id AND WTP.markForDelete = 0 "
							+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid "
							+ " INNER JOIN MasterParts MP ON MP.partid = WTP.partid "
							+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
							+ " where " + query + " AND WO.companyId = "+companyId+"  AND WO.workorders_statusId = "+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+" AND  WO.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<WorkOrdersTasksToPartsDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					WorkOrdersTasksToPartsDto	tasksToPartsDto = new WorkOrdersTasksToPartsDto();
					if(result != null) {
					tasksToPartsDto.setWorkorders_id((Long) result[0]);
					tasksToPartsDto.setWorkOrderNumberStr("WO-"+(Long) result[1]);
					tasksToPartsDto.setVehicle_vid((Integer) result[2]);
					tasksToPartsDto.setPartid((Long) result[3]);
					tasksToPartsDto.setQuantity((Double) result[4]);
					tasksToPartsDto.setParteachcost((Double) result[5]);
					tasksToPartsDto.setTotalcost((Double) result[6]);
					tasksToPartsDto.setVehicle_registration((String) result[7]);
					tasksToPartsDto.setPartnumber((String) result[8]);
					tasksToPartsDto.setPartname((String)tasksToPartsDto.getPartnumber()+"-"+ result[9]);
					if(result[10] != null) {
						Date	date	= (Date) result[10];
						tasksToPartsDto.setComplitionDate(sqldateTime.format(date));
					}
					
					tasksToPartsDto.setServiceTypeId((short) 1);
					
					if(result[11] != null) {
						tasksToPartsDto.setDriverName((String) result[11]+" "+(String) result[12]);
					}
					
					list.add(tasksToPartsDto);
					}
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public List<WorkOrdersTasksToPartsDto> getWorkOrderWarrantyPartDetails(String query, Integer companyId)
			throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, WTP.partid, WTP.quantity, WTP.parteachcost, WTP.totalcost, V.vehicle_registration,"
							+ " MP.partnumber, MP.partname, WO.completed_date, D.driver_firstname, D.driver_Lastname, WTP.workordertaskto_partid "
							+ " FROM WorkOrders  AS WO  "
							+ " INNER JOIN WorkOrdersTasksToParts WTP ON WTP.workorders_id= WO.workorders_id AND WTP.markForDelete = 0 "
							+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid "
							+ " INNER JOIN MasterParts MP ON MP.partid = WTP.partid AND MP.isWarrantyApplicable = 1 "
							+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
							+ " where " + query + " AND WO.companyId = "+companyId+"  AND  WO.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<WorkOrdersTasksToPartsDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					WorkOrdersTasksToPartsDto	tasksToPartsDto = new WorkOrdersTasksToPartsDto();
					if(result != null) {
						tasksToPartsDto.setWorkorders_id((Long) result[0]);
						tasksToPartsDto.setWorkOrderNumberStr("<a style=\"color: blue; background: #ffc;\" href='showWorkOrder?woId="+tasksToPartsDto.getWorkorders_id()+"' target='_blank'> WO-"+(Long) result[1] +"</a>");
						tasksToPartsDto.setVehicle_vid((Integer) result[2]);
						tasksToPartsDto.setPartid((Long) result[3]);
						tasksToPartsDto.setQuantity((Double) result[4]);
						tasksToPartsDto.setParteachcost((Double) result[5]);
						tasksToPartsDto.setTotalcost((Double) result[6]);
						tasksToPartsDto.setVehicle_registration((String) result[7]);
						tasksToPartsDto.setPartnumber((String) result[8]);
						if(result[10] != null) {
							Date	date	= (Date) result[10];
							tasksToPartsDto.setComplitionDate(sqldateTime.format(date));
						}
						
						tasksToPartsDto.setServiceTypeId((short) 1);
						
						if(result[11] != null) {
							tasksToPartsDto.setDriverName((String) result[11]+" "+(String) result[12]);
						}
						tasksToPartsDto.setWorkordertaskto_partid((Long) result[13]);
						tasksToPartsDto.setPartname("<a style=\"color: blue; background: #ffc;\" onclick=showAssetDetails("+tasksToPartsDto.getWorkordertaskto_partid()+") href=\"javascript:void(0)\" > "+tasksToPartsDto.getPartnumber()+"-"+ result[9]+" </a>");
						list.add(tasksToPartsDto);
					}
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	
	@Override
	public List<WorkOrdersTasksToPartsDto> getDSEPartDetails(String query, Integer companyId)
			throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT WO.dealerServiceEntriesId, WO.dealerServiceEntriesNumber, WO.vid, WTP.partId, WTP.quantity, WTP.partEchCost, WTP.partTotalCost, V.vehicle_registration,"
							+ " MP.partnumber, MP.partname, WO.completedDate, D.driver_firstname, D.driver_Lastname,WTP.DealerServiceEntriesPartId "
							+ " FROM DealerServiceEntries  AS WO  "
							+ " INNER JOIN DealerServiceEntriesPart WTP ON WTP.dealerServiceEntriesId= WO.dealerServiceEntriesId AND WO.markForDelete = 0 "
							+ " INNER JOIN Vehicle V ON V.vid = WO.vid "
							+ " INNER JOIN MasterParts MP ON MP.partid = WTP.partId AND MP.isWarrantyApplicable = 1 "
							+ " LEFT JOIN Driver D ON D.driver_id = WO.driverId"
							+ " where " + query + " AND WO.companyId = "+companyId+"  AND WO.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<WorkOrdersTasksToPartsDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					WorkOrdersTasksToPartsDto	tasksToPartsDto = new WorkOrdersTasksToPartsDto();
					if(result != null) {
						tasksToPartsDto.setWorkorders_id((Long) result[0]);
						tasksToPartsDto.setWorkOrderNumberStr("<a style=\"color: blue; background: #ffc;\" href='showDealerServiceEntries?dealerServiceEntriesId="+tasksToPartsDto.getWorkorders_id()+"' target='_blank'> DSE-"+(Long) result[1] +"</a>");
//						tasksToPartsDto.setWorkOrderNumberStr("DSE-"+(Long) result[1]);
						tasksToPartsDto.setVehicle_vid((Integer) result[2]);
						tasksToPartsDto.setPartid((Long) result[3]);
						tasksToPartsDto.setQuantity((Double) result[4]);
						tasksToPartsDto.setParteachcost((Double) result[5]);
						tasksToPartsDto.setTotalcost((Double) result[6]);
						tasksToPartsDto.setVehicle_registration((String) result[7]);
						tasksToPartsDto.setPartnumber((String) result[8]);
						tasksToPartsDto.setWorkordertaskto_partid((Long) result[13]);
						tasksToPartsDto.setPartname("<a style=\"color: blue; background: #ffc;\" onclick=showAssetDetails("+tasksToPartsDto.getWorkordertaskto_partid()+") href=\"javascript:void(0)\" > "+tasksToPartsDto.getPartnumber()+"-"+ result[9]+" </a>");
						if(result[10] != null) {
							Date	date	= (Date) result[10];
							tasksToPartsDto.setComplitionDate(sqldateTime.format(date));
						}
						
						tasksToPartsDto.setServiceTypeId((short) 2);
						
						if(result[11] != null) {
							tasksToPartsDto.setDriverName((String) result[11]+" "+(String) result[12]);
						}
						list.add(tasksToPartsDto);
					}
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
//	 " SELECT DSE.dealerServiceEntriesId, DSE.dealerServiceEntriesNumber, DSE.vid, V.vehicle_registration,"
//		+ " DSE.vendorId , VN.vendorName, DSE.invoiceNumber,DSE.jobNumber, DSE.invoiceDate, DSE.totalInvoiceCost, "
//		+ " U1.firstName, U2.firstName, DSE.statusId, DSE.paymentTypeId, DSE.vehicleOdometer,DSE.payNumber, DSE.paidDate,"
//		+ " DSE.dealerServiceDocumentId, V.vehicle_chasis, V.vehicle_engine, VN.vendorAddress1, VN.vendorAddress2, "
//		+ " DSE.serviceReminderIds, DSE.issueId, I.ISSUES_NUMBER, I.ISSUES_SUMMARY, DSE.driverId, D.driver_firstname, "
//		+ " D.driver_Lastname, D.driver_fathername, DSE.remark, DSE.isPartApplicable, DSE.assignToId, U3.firstName, "
//		+ " U4.firstName, U5.firstName, DSE.creationOn, DSE.lastUpdatedOn,DSE.isLabourApplicable , V.vehicleModalId, DSE.meterNotWorkingId "
//		+ " FROM DealerServiceEntries AS DSE "
//		+ " INNER JOIN Vehicle V ON V.vid = DSE.vid "
//		+ " INNER JOIN User U2 ON U2.id = DSE.lastModifiedById "
//		+ " INNER JOIN Vendor VN ON VN.vendorId = DSE.vendorId "
//		+ " LEFT JOIN Driver D ON D.driver_id = DSE.driverId "
//		+ " LEFT JOIN Issues I ON I.ISSUES_ID = DSE.issueId "
//		+ " LEFT JOIN User U1 ON U1.id = DSE.paidById "
//		+ " LEFT JOIN User U3 ON U3.id = DSE.assignToId "
//		+ " INNER JOIN User U4 ON U4.id = DSE.createdById "
//		+ " INNER JOIN User U5 ON U5.id = DSE.lastModifiedById "
//		+ " where DSE.companyId = "+valueObject.getInt("companyId")+" "+valueObject.getString("query","")+" AND  DSE.markForDelete = 0 ORDER BY DSE.dealerServiceEntriesId DESC
	
	
	
	
	@Override
	public List<WorkOrdersTasksToPartsDto> getServiceEntiresPartConsumptionList(String query ,Integer companyId) throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT WO.serviceEntries_id, WO.serviceEntries_Number, WO.vid, WTP.partid, WTP.quantity, WTP.parteachcost, WTP.totalcost, V.vehicle_registration,"
							+ " MP.partnumber, MP.partname, WO.completed_date, D.driver_firstname, D.driver_Lastname "
							+ " FROM ServiceEntries  AS WO  "
							+ " INNER JOIN ServiceEntriesTasksToParts WTP ON WTP.serviceEntries_id= WO.serviceEntries_id AND WTP.markForDelete = 0"
							+ " INNER JOIN Vehicle V ON V.vid = WO.vid "
							+ " INNER JOIN MasterParts MP ON MP.partid = WTP.partid "
							+ " LEFT JOIN Driver D ON D.driver_id = WO.driver_id"
							+ " where " +query+ " AND WO.companyId = "+companyId+" AND  WO.markForDelete = 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<WorkOrdersTasksToPartsDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					WorkOrdersTasksToPartsDto	tasksToPartsDto = new WorkOrdersTasksToPartsDto();
					
					tasksToPartsDto.setWorkorders_id((Long) result[0]);
					tasksToPartsDto.setWorkOrderNumberStr("SE-"+(Long) result[1]);
					tasksToPartsDto.setVehicle_vid((Integer) result[2]);
					tasksToPartsDto.setPartid((Long) result[3]);
					tasksToPartsDto.setQuantity((Double) result[4]);
					tasksToPartsDto.setParteachcost((Double) result[5]);
					tasksToPartsDto.setTotalcost((Double) result[6]);
					tasksToPartsDto.setVehicle_registration((String) result[7]);
					tasksToPartsDto.setPartnumber((String) result[8]);
					tasksToPartsDto.setPartname((String) result[9]);
					if(result[10] != null) {
						Date	date	= (Date) result[10];
						tasksToPartsDto.setComplitionDate(sqldateTime.format(date));
					}
					if(result[11] != null) {
						tasksToPartsDto.setDriverName((String) result[11]+" "+(String) result[12]);
					}
					tasksToPartsDto.setServiceTypeId((short) 2);
					
					list.add(tasksToPartsDto);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	//Technician Wise Part Report Start
	@Override
	public List<WorkOrdersDto> getTechnicianWisePartReportList(String query, Integer companyId)
			throws Exception {
		
		try {
			
			TypedQuery<Object[]> queryt = entityManager
						 .createQuery(" SELECT WOT.job_typetaskId, WOT.job_subtypetask_id, WOT.workordertaskid , "
						+ " W.vehicle_vid, V.vehicle_registration, "
						+ " W.workorders_id, W.completed_date, W.workorders_Number, JT.Job_Type, JSUB.Job_ROT, WOPA.partid, "
						+ " WOLAB.laberid, WOLAB.laberhourscost, WOLAB.eachlabercost, WOLAB.totalcost, WOLAB.labername, D.driver_id, D.driver_firstname, WOLAB.labername  "
						+ " FROM WorkOrdersTasks AS WOT "
						+ " INNER JOIN WorkOrders AS W ON W.workorders_id = WOT.workorders "
						+ " INNER JOIN Vehicle AS V ON V.vid = WOT.vehicle_vid "
						+ " INNER JOIN JobType AS JT ON JT.Job_id = WOT.job_typetaskId "
						+ " INNER JOIN JobSubType AS JSUB ON JSUB.Job_Subid = WOT.job_subtypetask_id   " 
						+ " INNER JOIN WorkOrdersTasksToParts AS WOPA ON WOPA.workordertaskid = WOT.workordertaskid  "
						+ " INNER JOIN WorkOrdersTasksToLabor AS WOLAB ON WOLAB.workordertaskid = WOT.workordertaskid   "
						+ " LEFT JOIN Driver AS D ON D.driver_id = WOLAB.laberid "
						+ " WHERE W.companyId = "+companyId+" AND  W.markForDelete = 0 "+ query + " ", Object[].class);			

			
			List<Object[]>	results = queryt.getResultList();
			
			List<WorkOrdersDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					WorkOrdersDto	technicianWiseInfo = new WorkOrdersDto();					
					technicianWiseInfo.setJob_typetaskId((Integer) result[0]);
					technicianWiseInfo.setJob_subtypetask_id((Integer) result[1]);
					technicianWiseInfo.setWorkordertaskid((Long) result[2]);
					technicianWiseInfo.setVehicle_vid((Integer) result[3]);
					if(result[4] != null)
					technicianWiseInfo.setVehicle_registration((String) result[4]);					
					technicianWiseInfo.setWorkorders_id((Long) result[5]);
					if(result[6] != null)
					technicianWiseInfo.setCompleteDateStr(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[6], DateTimeUtility.DD_MM_YY));
					if(result[7] != null)					
					technicianWiseInfo.setWorkorders_Numbers("WO-"+(Long) result[7]);
					if(result[8] != null)
					technicianWiseInfo.setJob_Type((String) result[8]);
					if(result[9] != null)
					technicianWiseInfo.setJob_ROT((String) result[9]);						
					technicianWiseInfo.setPartid((Long) result[10]);					
					technicianWiseInfo.setLaberid((Integer) result[11]);
					if(result[12] != null)
					technicianWiseInfo.setLaberhourscost(Double.parseDouble(toFixedTwo.format(result[12])));	
					if(result[13] != null)	
					technicianWiseInfo.setEachlabercost(Double.parseDouble(toFixedTwo.format(result[13])));
					if(result[14] != null)	
					technicianWiseInfo.setTotalcost(Double.parseDouble(toFixedTwo.format(result[14])));	
					if(result[15] != null)	
					technicianWiseInfo.setLabername((String) result[15]);
					if(result[16] != null) {
						technicianWiseInfo.setDriver_id((Integer) result[16]);					
						technicianWiseInfo.setDriver_firstname((String) result[17]);					
					}else {
						technicianWiseInfo.setDriver_firstname((String) result[18]);					
					}
					list.add(technicianWiseInfo);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
		
		
	}	
	
	@Override
	public List<WorkOrdersTasksToPartsDto> getWOPartConsumptionList(ValueObject object)
			throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT WS.workorders_id, WS.workorders_Number, WS.vehicle_vid, WTP.partid, WTP.quantity, WTP.parteachcost, WTP.totalcost, V.vehicle_registration,"
							+ " MP.partnumber, MP.partname, WS.completed_date, D.driver_firstname, D.driver_Lastname "
							+ " FROM WorkOrders AS WS  "
							+ " INNER JOIN WorkOrdersTasksToParts WTP ON WTP.workorders_id= WS.workorders_id "
							+ " INNER JOIN Vehicle V ON V.vid = WS.vehicle_vid "
							+ " INNER JOIN MasterParts MP ON MP.partid = WTP.partid "
							+ " LEFT JOIN Driver D ON D.driver_id = WS.workorders_driver_id"
							+ " where  WS.companyId = "+object.getInt("companyId")+" " + object.getString("queryStr") + " AND  WTP.markForDelete = 0 ORDER BY WS.completed_date DESC", Object[].class);
			/*queryt.setFirstResult((object.getInt("pageNumber") - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);*/
			List<Object[]>	results = queryt.getResultList();
			
			List<WorkOrdersTasksToPartsDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					WorkOrdersTasksToPartsDto	tasksToPartsDto = new WorkOrdersTasksToPartsDto();
					if(result != null) {
					tasksToPartsDto.setWorkorders_id((Long) result[0]);
					tasksToPartsDto.setWorkOrderNumberStr("WO-"+(Long) result[1]);
					tasksToPartsDto.setVehicle_vid((Integer) result[2]);
					tasksToPartsDto.setPartid((Long) result[3]);
					tasksToPartsDto.setQuantity((Double) result[4]);
					tasksToPartsDto.setParteachcost((Double) result[5]);
					tasksToPartsDto.setTotalcost((Double) result[6]);
					tasksToPartsDto.setVehicle_registration((String) result[7]);
					tasksToPartsDto.setPartnumber((String) result[8]);
					tasksToPartsDto.setPartname((String)tasksToPartsDto.getPartnumber()+"-"+ result[9]);
					if(result[10] != null) {
						Date	date	= (Date) result[10];
						tasksToPartsDto.setComplitionDate(sqldateTime.format(date));
					}else {
						tasksToPartsDto.setComplitionDate("--");
						
					}
					
					tasksToPartsDto.setServiceTypeId((short) 1);
					
					if(result[11] != null) {
						tasksToPartsDto.setDriverName((String) result[11]+" "+(String) result[12]);
					}
					
					list.add(tasksToPartsDto);
					}
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<WorkOrdersTasksToPartsDto> getSEPartConsumptionList(ValueObject object) throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT WS.serviceEntries_id, WS.serviceEntries_Number, WS.vid, WTP.partid, WTP.quantity, WTP.parteachcost, WTP.totalcost, V.vehicle_registration,"
							+ " MP.partnumber, MP.partname, WS.completed_date, D.driver_firstname, D.driver_Lastname "
							+ " FROM ServiceEntries  AS WS  "
							+ " INNER JOIN ServiceEntriesTasksToParts WTP ON WTP.serviceEntries_id= WS.serviceEntries_id "
							+ " INNER JOIN Vehicle V ON V.vid = WS.vid "
							+ " INNER JOIN MasterParts MP ON MP.partid = WTP.partid "
							+ " LEFT JOIN Driver D ON D.driver_id = WS.driver_id"
							+ " where  WS.companyId = "+object.getInt("companyId")+" " +object.getString("queryStr")+ " AND  WTP.markForDelete = 0 ORDER BY WS.completed_date DESC", Object[].class);
			
			/*queryt.setFirstResult((object.getInt("pageNumber") - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);*/
			List<Object[]>	results = queryt.getResultList();
			
			List<WorkOrdersTasksToPartsDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					WorkOrdersTasksToPartsDto	tasksToPartsDto = new WorkOrdersTasksToPartsDto();
					
					tasksToPartsDto.setWorkorders_id((Long) result[0]);
					tasksToPartsDto.setWorkOrderNumberStr("SE-"+(Long) result[1]);
					tasksToPartsDto.setVehicle_vid((Integer) result[2]);
					tasksToPartsDto.setPartid((Long) result[3]);
					tasksToPartsDto.setQuantity((Double) result[4]);
					tasksToPartsDto.setParteachcost((Double) result[5]);
					tasksToPartsDto.setTotalcost((Double) result[6]);
					tasksToPartsDto.setVehicle_registration((String) result[7]);
					tasksToPartsDto.setPartnumber((String) result[8]);
					tasksToPartsDto.setPartname((String) result[9]);
					if(result[10] != null) {
						Date	date	= (Date) result[10];
						tasksToPartsDto.setComplitionDate(sqldateTime.format(date));
					}else {
						tasksToPartsDto.setComplitionDate("--");
					}
					if(result[11] != null) {
						tasksToPartsDto.setDriverName((String) result[11]+" "+(String) result[12]);
					}
					tasksToPartsDto.setServiceTypeId((short) 2);
					
					list.add(tasksToPartsDto);
				}
			}
			
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	//Technician Wise Part Report Stop
}
