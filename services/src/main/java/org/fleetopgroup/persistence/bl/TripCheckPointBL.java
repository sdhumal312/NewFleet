/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.document.TripCheckPointParameterInspectionPhoto;
import org.fleetopgroup.persistence.model.TripCheckPoint;
import org.fleetopgroup.persistence.model.TripCheckPointInspection;
import org.fleetopgroup.persistence.model.TripCheckPointParameter;
import org.fleetopgroup.persistence.model.TripCheckPointParameterInspection;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author fleetop
 *
 */
public class TripCheckPointBL {
	@Autowired private ISequenceCounterService			sequenceCounterService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	Date 		currentDateUpdate  = new Date();         
	Timestamp 	toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime()); 
	public TripCheckPointBL() {
		super();
	}

	// save the VehicleStatus Model
	public TripCheckPointParameter prepareTripCheckPointParameter(ValueObject valueObject) throws Exception {
		try {
			TripCheckPointParameter tripCheckPointParameter = new TripCheckPointParameter();
			tripCheckPointParameter.setTripCheckPointParameterId(valueObject.getLong("tripCheckPointParameterId"));
			tripCheckPointParameter.setTripCheckParameterName(valueObject.getString("checkPointParameterName"));
			tripCheckPointParameter.setDescription(valueObject.getString("description"));
			tripCheckPointParameter.setCreatedById(valueObject.getLong("userId"));
			tripCheckPointParameter.setLastUpdatedBy(valueObject.getLong("userId"));
			tripCheckPointParameter.setCreationDate(toDate);
			tripCheckPointParameter.setLastUpdatedOn(toDate);
			tripCheckPointParameter.setCompanyId(valueObject.getInt("companyId"));
			tripCheckPointParameter.setMarkForDelete(false);

			return tripCheckPointParameter;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			
		}
		

	}
	public TripCheckPoint prepareTripCheckPoint(ValueObject valueObject) throws Exception {
		try {
			TripCheckPoint tripCheckPoint= new TripCheckPoint();
			tripCheckPoint.setTripCheckPointId(valueObject.getLong("tripCheckPointId"));
			tripCheckPoint.setTripCheckPointName(valueObject.getString("checkPointName"));
			tripCheckPoint.setTripRouteId(valueObject.getLong("routeId"));
			tripCheckPoint.setDescription(valueObject.getString("description"));
			tripCheckPoint.setCreatedById(valueObject.getLong("userId"));
			tripCheckPoint.setLastUpdatedBy(valueObject.getLong("userId"));
			tripCheckPoint.setCreationDate(toDate);
			tripCheckPoint.setLastUpdatedOn(toDate);
			tripCheckPoint.setCompanyId(valueObject.getInt("companyId"));
			tripCheckPoint.setMarkForDelete(false);

			return tripCheckPoint;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			
		}
		

	}

	public TripCheckPointInspection prepareTripCheckPointInspection(ValueObject valueObject)throws Exception  {
		try {
			TripCheckPointInspection tripCheckPointInspection= new TripCheckPointInspection();
			tripCheckPointInspection.setTripCheckPointId(valueObject.getLong("tripCheckPointId"));
			tripCheckPointInspection.setTripSheetId(valueObject.getLong("tripSheetId"));
			tripCheckPointInspection.setCheckPointWayTypeId(valueObject.getShort("checkPointWayTypeId",(short)1));// 1 for sigle jurney 2 for return
			tripCheckPointInspection.setTripCheckPointInspectionDate(toDate);
			tripCheckPointInspection.setCreatedById(valueObject.getLong("userId"));
			tripCheckPointInspection.setLastUpdatedBy(valueObject.getLong("userId"));
			tripCheckPointInspection.setCreationDate(toDate);
			tripCheckPointInspection.setLastUpdatedOn(toDate);
			tripCheckPointInspection.setCompanyId(valueObject.getInt("companyId"));
			tripCheckPointInspection.setMarkForDelete(false);

			return tripCheckPointInspection;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			
		}
		

	}

	public TripCheckPointParameterInspection prepareTripCheckPointParameterInspection(ValueObject object, ValueObject valueObject )throws Exception {
		try {
			TripCheckPointParameterInspection tripCheckPointParameterInspection= new TripCheckPointParameterInspection();
		//	tripCheckPointParameterInspection.setTripCheckPointInspectionId(valueObject.getLong("tripCheckPointInspectionId"));
			tripCheckPointParameterInspection.setTripCheckPointParameterId(object.getLong("tripCheckPointParameterId"));
			tripCheckPointParameterInspection.setCheckPointParameterInspectStatusId(object.getBoolean("checkPointParameterInspectStatusId"));
			if(object.get("checkPointParameterPhoto") != "") {
				tripCheckPointParameterInspection.setCheckPointParameterPhoto(true);
			}else {
				tripCheckPointParameterInspection.setCheckPointParameterPhoto(false);
			}
			tripCheckPointParameterInspection.setRemark(object.getString("remark",""));
			tripCheckPointParameterInspection.setCreatedById(valueObject.getLong("userId"));
			tripCheckPointParameterInspection.setLastUpdatedBy(valueObject.getLong("userId"));
			tripCheckPointParameterInspection.setCreationDate(toDate);
			tripCheckPointParameterInspection.setLastUpdatedOn(toDate);
			tripCheckPointParameterInspection.setCompanyId(valueObject.getInt("companyId"));
			tripCheckPointParameterInspection.setMarkForDelete(false);
			

			return tripCheckPointParameterInspection;
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			
		}
		

	}

	public TripCheckPointParameterInspectionPhoto prepareTripCheckPointParameterInspectionPhoto(MultipartFile file, TripCheckPointParameterInspection tripCheckPointParameterInspection)throws Exception {
		try {
			
			TripCheckPointParameterInspectionPhoto doc = new TripCheckPointParameterInspectionPhoto();
			doc.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_TRIP_CHECKPOINT_PHOTO));
			doc.setTripCheckPointParameterInspectionFileName(file.getOriginalFilename());
			doc.setTripCheckPointParameterInspectionContent(file.getBytes());
			doc.setTripCheckPointParameterInspectionContentType(file.getContentType());
			doc.setMarkForDelete(false);
			doc.setCreatedById(tripCheckPointParameterInspection.getCreatedById());
			doc.setLastModifiedById(tripCheckPointParameterInspection.getCreatedById());
			doc.setCreated(toDate);
			doc.setLastupdated(toDate);
			doc.setCompanyId(tripCheckPointParameterInspection.getCompanyId());
			doc.setTripCheckPointParameterInspectionId(tripCheckPointParameterInspection.getTripCheckPointParameterInspectionId());

			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			
		}
		

	}
	
}
