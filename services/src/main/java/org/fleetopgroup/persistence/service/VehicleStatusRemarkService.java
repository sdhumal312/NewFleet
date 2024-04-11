package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.DriverStatus;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.persistence.bl.StatusRemarkBL;
import org.fleetopgroup.persistence.dao.StatusChangeRemarkRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.StatusChangeRemarkDto;
import org.fleetopgroup.persistence.model.StatusChangeRemark;
import org.fleetopgroup.persistence.serviceImpl.IStatusChangeRemarkService;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("VehicleStatusRemarkService")
@Transactional
public class VehicleStatusRemarkService implements IStatusChangeRemarkService {
	SimpleDateFormat	formatDate = new SimpleDateFormat("dd-MM-yyyy");
	@PersistenceContext
	EntityManager entityManager;

	
	@Autowired StatusRemarkBL statusRemarkBL;
	@Autowired
	StatusChangeRemarkRepository	statusRemarkRepository;
	@Override
	public ValueObject saveVehicleStatusRemark(ValueObject valueObject)throws Exception {
		StatusChangeRemark statusChangeRemark = null;
		try {
			statusChangeRemark = statusRemarkBL.prepareVehicleStatusRemark(valueObject);
			statusRemarkRepository.save(statusChangeRemark);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getStatusChangeRemarkHistory(ValueObject valueObject)throws Exception {
		try {
			CustomUserDetails userDetails = Utility.getObject(null);
			valueObject.put("companyId", userDetails.getCompany_id());
			List<StatusChangeRemarkDto> statusChangeRemarkList = getStatusChangeRemarkList(valueObject);
			valueObject.put("statusChangeRemarkList", statusChangeRemarkList);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<StatusChangeRemarkDto> getStatusChangeRemarkList(ValueObject valueObject)throws Exception {
		List<StatusChangeRemarkDto> 	statusChangeRemarkList 	= null;
		StatusChangeRemarkDto 			statusChangeRemark 		= null;
		TypedQuery<Object[]> 			query 					= null;
		List<Object[]>					resultList				= null;
		try {
			query =  entityManager.createQuery("SELECT VR.statusChangeRemarkId, VR.statusRemark, VR.transactionId, V.vehicle_registration, "
					+ " VR.currentStatusId, VR.changeToStatusId, VR.createdById, U.firstName, VR.creationOn,VR.typeId,"
					+ " VR.mobileNumber,VR.partyName,VR.soldAmount,VR.soldOn "
					+ "FROM StatusChangeRemark AS VR "
					+ " LEFT JOIN Vehicle AS V ON V.vid = VR.transactionId "
					+ " LEFT JOIN Driver AS D ON D.driver_id = VR.transactionId "
					+ " INNER JOIN User U ON U.id = VR.createdById "
					+ " WHERE VR.transactionId = "+valueObject.getInt("id",0)+" AND VR.typeId="+valueObject.getShort("typeId",(short) 0)+" AND VR.companyId="+valueObject.getInt("companyId",0)+" AND VR.markForDelete=0 ",Object[].class);
			
			resultList = query.getResultList();
			
			if(resultList != null) {
				statusChangeRemarkList = new ArrayList<>();
				for(Object[] result : resultList) {
					statusChangeRemark = new StatusChangeRemarkDto();
					statusChangeRemark.setStatusChangeRemarkId((Long)result[0]);
					statusChangeRemark.setStatusChangeRemark((String)result[1]);
					statusChangeRemark.setTransactionId ((Integer)result[2]);
					statusChangeRemark.setVehicleRegistration((String)result[3]);
					statusChangeRemark.setCurrentStatusId((short)result[4]);
					if((short)result[9] == StatusRemarkBL.DRIVER_STATUS_CHANGE) {
						statusChangeRemark.setCurrentStatus(DriverStatus.getDriverStatus((short)result[4]));
						statusChangeRemark.setChangeToStatus(DriverStatus.getDriverStatus((short)result[5]));
					}else{
						statusChangeRemark.setCurrentStatus(VehicleStatusConstant.getVehicleStatus((short)result[4]));
						statusChangeRemark.setChangeToStatus(VehicleStatusConstant.getVehicleStatus((short)result[5]));
					}
					statusChangeRemark.setChangeToStatusId((short)result[5]);
					
					statusChangeRemark.setCreatedById((Long)result[6]);
					statusChangeRemark.setCreatedBy((String)result[7]);
					statusChangeRemark.setCreationDate(formatDate.format((Date) result[8]));
					statusChangeRemark.setMobileNumber((String) result[10]);
					statusChangeRemark.setPartyName((String) result[11]);
					statusChangeRemark.setSoldAmount((Double) result[12]);
					if(result[13] != null)
					statusChangeRemark.setSoldOnstr(formatDate.format((Date)result[13]));
					statusChangeRemarkList.add(statusChangeRemark);
				}
			}
			return statusChangeRemarkList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
