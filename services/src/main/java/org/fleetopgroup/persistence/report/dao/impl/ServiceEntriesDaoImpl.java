package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.ServiceEntriesType;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.report.dao.IServiceEntriesDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ServiceEntriesDaoImpl implements IServiceEntriesDao {

	@PersistenceContext
	EntityManager entityManager;
	
	SimpleDateFormat dateFormat 		= new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public List<ServiceEntriesDto> getServiceEntriesList(ServiceEntriesDto serviceEntriesDto) throws Exception {
		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
					"SELECT SR.serviceEntries_id, SR.serviceEntries_Number, SR.vid, SR.invoiceDate, SR.totalserviceROUND_cost "
							+ "	from ServiceEntries SR "
							+ " where SR.vid = " + serviceEntriesDto.getVid() + " AND SR.invoiceDate BETWEEN '"+serviceEntriesDto.getFromDate()+"'"
							+ "AND '"+serviceEntriesDto.getToDate()+"' AND SR.companyId = "
							+ serviceEntriesDto.getCompanyId() + " AND SR.markForDelete  = 0 ORDER BY SR.serviceEntries_id ASC",
					Object[].class);
			List<Object[]> results = query.getResultList();

			List<ServiceEntriesDto> Dtos = null;
			Dtos = new ArrayList<ServiceEntriesDto>();
			if (results != null && !results.isEmpty()) {
				ServiceEntriesDto list = null;
				for (Object[] result : results) {
					list = new ServiceEntriesDto();

					list.setServiceEntries_id((Long) result[0]);
					list.setServiceEntries_Number((Long) result[1]);
					list.setVid((Integer) result[2]);
					list.setInvoiceDateOn((java.util.Date) result[3]);
					list.setTotalserviceROUND_cost((Double) result[4]);
					
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<ServiceEntriesDto> getServiceEntriesPendingList(Integer companyId, Integer vid) throws Exception {
		TypedQuery<Object[]> 		typedQuery 	= null;
		ServiceEntriesDto 			list 		= null;
		List<ServiceEntriesDto> 	Dtos 		= null;
		try {
			typedQuery = entityManager.createQuery(
					"SELECT SE.serviceEntries_id, SE.serviceEntries_Number, V.vehicle_registration, VD.vendorName, SE.invoiceNumber, "
							+ " SE.created, SE.serviceEntries_statusId, SE.vid, VD.vendorId, SE.totalservice_cost, SE.tallyCompanyId, "
							+ " TC.companyName, SE.service_paymentTypeId  "
							+ " From ServiceEntries as SE "
							+ " INNER JOIN Vehicle AS V ON V.vid = SE.vid "
							+ " INNER JOIN TallyCompany AS TC ON TC.tallyCompanyId = SE.tallyCompanyId "
							+ " LEFT JOIN Vendor AS VD ON VD.vendorId = SE.vendor_id "
							+ " WHERE SE.vid = "+vid+" AND SE.isPendingForTally = 0 AND SE.totalserviceROUND_cost > 0 "
							+ " AND SE.companyId ="+companyId+" AND SE.markForDelete = 0 AND SE.serviceEntries_statusId ="+ServiceEntriesType.SERVICE_ENTRIES_STATUS_COMPLETE+"  ",
							Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();
			
			
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<>();
				
				for(Object[] result : results) {
					list = new ServiceEntriesDto();
					
					list.setServiceEntries_id((Long)result[0]);
					list.setServiceEntries_Number((Long)result[1]);
					list.setVehicle_registration((String)result[2]);
					list.setVendor_name((String)result[3]);
					list.setInvoiceNumber((String)result[4]);
					list.setCreated(dateFormat.format((Timestamp)result[5]));
					list.setServiceEntries_statusId((short)result[6]);
					list.setServiceEntries_status(ServiceEntriesType.getStatusName((short)result[6]));
					list.setVid((Integer)result[7]);
					list.setVendor_id((Integer) result[8]);
					list.setTotalserviceROUND_cost((Double) result[9]);
					list.setTallyCompanyId((Long) result[10]);
					list.setTallyCompanyName((String)result[11]);
					list.setService_paymentTypeId((short) result[12]);
					list.setService_paymentType(PaymentTypeConstant.getPaymentTypeName((short) result[12]));
					
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			System.err.println("ERR"+e);
			throw e;
		}
		
	}
	
	@Override
	@Transactional
	public void updatePendingStatus(Long serviceId, short status) throws Exception {
		entityManager.createQuery(
				" UPDATE ServiceEntries AS ST SET ST.isPendingForTally = "+status+"  where ST.serviceEntries_id = "+serviceId+"")
				.executeUpdate();
		
	}
}
