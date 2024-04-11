/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.bl.TripCheckPointBL;
import org.fleetopgroup.persistence.dao.TripCheckPointInspectionRepository;
import org.fleetopgroup.persistence.dao.TripCheckPointParameterInspectionRepository;
import org.fleetopgroup.persistence.dao.TripCheckPointParameterRepository;
import org.fleetopgroup.persistence.dao.TripCheckPointRepository;
import org.fleetopgroup.persistence.document.TripCheckPointParameterInspectionPhoto;
import org.fleetopgroup.persistence.dto.TripCheckPointInspectionDto;
import org.fleetopgroup.persistence.model.TripCheckPoint;
import org.fleetopgroup.persistence.model.TripCheckPointInspection;
import org.fleetopgroup.persistence.model.TripCheckPointParameter;
import org.fleetopgroup.persistence.model.TripCheckPointParameterInspection;
import org.fleetopgroup.persistence.serviceImpl.ITripCheckPointService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author fleetop
 *
 */
@Service("TripCheckPointService")
@Transactional
public class TripCheckPointService implements ITripCheckPointService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TripCheckPointParameterRepository 				tripCheckPointParameterRepository;
	
	@Autowired
	private TripCheckPointRepository 						tripCheckPointRepository;
	
	@Autowired
	private TripCheckPointInspectionRepository 				tripCheckPointInspectionRepository;
	
	@Autowired
	private TripCheckPointParameterInspectionRepository 	tripCheckPointParameterInspectionRepository;
	
	@Autowired 
	private MongoTemplate					mongoTemplate;
	
	TripCheckPointBL	tripCheckPointBL 		= new TripCheckPointBL();

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public ValueObject getAllTripCheckPointParameter(ValueObject object) throws Exception {
		List<TripCheckPointParameter>			tripCheckPointParameterList				= null;
		try {
			tripCheckPointParameterList 	= tripCheckPointParameterRepository.findBycompanyId(object.getInt("companyId",0));
			object.put("allTripCheckPointParameter", tripCheckPointParameterList);
			return object;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			tripCheckPointParameterList				= null;     
		}
	}
	
	@Transactional
	public TripCheckPointParameter validateTripCheckPointParameter(ValueObject valueObject) throws Exception {
		try {
			return tripCheckPointParameterRepository.findByName(valueObject.getString("checkPointParameterName"),valueObject.getInt("companyId",0));
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
		
	}
	
	@Override
	public ValueObject saveTripCheckPointParameter(ValueObject valueObject) throws Exception {
		TripCheckPointParameter		tripCheckPointParameter					= null;
		TripCheckPointParameter 	validateTripCheckPointParameter			= null;
		try {
			validateTripCheckPointParameter		=	validateTripCheckPointParameter(valueObject);
			
			if(validateTripCheckPointParameter != null) {
				valueObject.put("alreadyExist", true);
			}else {
				tripCheckPointParameter = tripCheckPointBL.prepareTripCheckPointParameter(valueObject);
				tripCheckPointParameterRepository.save(tripCheckPointParameter);
				valueObject.put("save", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			tripCheckPointParameter						= null;  
			validateTripCheckPointParameter				= null;  
		}
	}
	
	@Override
	public ValueObject deleteTripCheckPointParameter(ValueObject object) throws Exception {
		try {
			tripCheckPointParameterRepository.deleteTripCheckPointParameter(object.getLong("tripCheckPointParameterId"));
			object.put("deleteTripCheckPointParameter", true);
			return object;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}
	
	@Override
	public ValueObject getAvailabilityOfTripCheckPointParamterInInspection(ValueObject object) throws Exception {
		TripCheckPointParameterInspection tripCheckPointParameterInspection = null;
		try {
			tripCheckPointParameterInspection = tripCheckPointParameterInspectionRepository.getAvailabilityOfTripCheckPointParamterInInspection(object.getLong("tripCheckPointParameterId"), object.getInt("companyId") );
			if(tripCheckPointParameterInspection != null) {
				object.put("parameterUsed", true);
			}
			return object;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}
	
	@Override
	public ValueObject getRouteWiseTripCheckPoint(ValueObject object) throws Exception {
		List<TripCheckPoint>			tripCheckPointList				= null;
		try {
			tripCheckPointList 	= tripCheckPointRepository.findByRoueIdAndcompanyId(object.getLong("routeId",0),object.getInt("companyId",0));
			object.put("routeWiseTripCheckPointList", tripCheckPointList);
			return object;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			tripCheckPointList				= null;     
		}
	}
	
	@Transactional
	public TripCheckPoint validateTripCheckPoint(ValueObject valueObject) throws Exception {
		try {
			return tripCheckPointRepository.findByName(valueObject.getString("checkPointName"),valueObject.getInt("companyId",0));
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
		
	}
	
	@Override
	public ValueObject saveTripCheckPoint(ValueObject valueObject) throws Exception {
		TripCheckPoint		tripCheckPoint					= null;
		TripCheckPoint 		validateTripCheckPoint			= null;
		try {
			validateTripCheckPoint		=	validateTripCheckPoint(valueObject);
			
			if(validateTripCheckPoint != null) {
				valueObject.put("alreadyExist", true);
			}else {
				tripCheckPoint = tripCheckPointBL.prepareTripCheckPoint(valueObject);
				tripCheckPointRepository.save(tripCheckPoint);
				valueObject.put("save", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			tripCheckPoint						= null;  
			validateTripCheckPoint				= null;  
		}
	}
	
	@Override
	public ValueObject deleteTripCheckPoint(ValueObject object) throws Exception {
		try {
			tripCheckPointRepository.deleteTripCheckPoint(object.getLong("tripCheckPointId"));
			object.put("deleteTripCheckPoint", true);
			return object;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}
	
	@Override
	public ValueObject getAvailabilityOfTripCheckPointInInspection(ValueObject object) throws Exception {
		TripCheckPointInspection tripCheckPointInspection = null;
		try {
			tripCheckPointInspection = tripCheckPointInspectionRepository.getAvailabilityOfTripCheckPointInInspection(object.getLong("tripCheckPointId"), object.getInt("companyId") );
			if(tripCheckPointInspection != null) {
				object.put("checkPointUsed", true);
			}
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
/*	@Override
	public ValueObject getInspectedTripCheckPointParameter(ValueObject object) throws Exception {
		TripCheckPointInspection			tripCheckPointInspection				= null;
		try {
			tripCheckPointInspection 	= tripCheckPointInspectionRepository.getTripCheckPointInspection(object.getLong("tripCheckPointId",0),object.getInt("companyId",0));
			
			object.put("tripCheckPointInspection", tripCheckPointInspection);
			return object;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			tripCheckPointInspection				= null;     
		}
	}*/
	
	@Override
	public ValueObject getInspectedTripCheckPointParameter(ValueObject object) throws Exception {
		List<TripCheckPointInspectionDto>			tripCheckPointParameterInspectionList	= null;
		TripCheckPointInspectionDto					tripCheckPointParameterInspection		= null;
		TypedQuery<Object[]> 						query									= null;
		List<Object[]> 								results									= null;	
		TripCheckPointInspection					tripCheckPointInspection				= null;
		try {
			
			tripCheckPointInspection 	= tripCheckPointInspectionRepository.getTripCheckPointInspection(object.getLong("tripCheckPointId",0),object.getLong("tripSheetId",0),object.getInt("companyId",0));
			if(tripCheckPointInspection == null) {
				object.put("noRecordFound", true);
				return object;
			}
			query = entityManager.createQuery(
					"SELECT TCPPI.tripCheckPointParameterInspectionId,TCPP.tripCheckParameterName, TCPPI.checkPointParameterInspectStatusId, TCPPI.tripCheckPointInspection.tripCheckPointInspectionId "
							+ " FROM TripCheckPointParameterInspection AS TCPPI "
							+ " INNER JOIN TripCheckPointParameter AS TCPP ON TCPP.tripCheckPointParameterId = TCPPI.tripCheckPointParameterId  "
							+ " WHERE TCPPI.markForDelete=0  AND TCPPI.companyId = "+object.getInt("companyId")+" AND TCPPI.tripCheckPointInspection.tripCheckPointInspectionId = "+tripCheckPointInspection.getTripCheckPointInspectionId()+" AND TCPPI.markForDelete = 0", Object[].class);
			
			results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				tripCheckPointParameterInspectionList 	= new ArrayList<TripCheckPointInspectionDto>();
				
				for (Object[] result : results) {
					tripCheckPointParameterInspection 		= new TripCheckPointInspectionDto();
					
					tripCheckPointParameterInspection.setTripCheckPointParameterInspectionId((Long)result[0]);
					tripCheckPointParameterInspection.setTripCheckPointParameterName((String)result[1]);
					tripCheckPointParameterInspection.setCheckPointParameterInspectStatusId((Boolean)result[2]);
					if(tripCheckPointParameterInspection.isCheckPointParameterInspectStatusId()) {
						tripCheckPointParameterInspection.setCheckPointParameterInspectStatus("Pass");
					}else {
						tripCheckPointParameterInspection.setCheckPointParameterInspectStatus("Fail");
						
					}
					tripCheckPointParameterInspection.setTripCheckPointInspectionId((Long)result[3]);
					tripCheckPointParameterInspectionList.add(tripCheckPointParameterInspection);
				}
			}
			object.put("tripCheckPointParameterInspectionList", tripCheckPointParameterInspectionList);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject inspectTripCheckPointParameter(ValueObject valueObject,MultipartFile[] file) throws Exception {
		ArrayList<ValueObject>							dataArrayObjColl								= null;
		int 											i												= 0;
		ValueObject										valueOutObject									= null;
		TripCheckPointInspection						tripCheckPointInspectionBL						= null;
		TripCheckPointInspection						tripCheckPointInspection						= null;
		TripCheckPointInspection						getTripCheckPointInspection						= null;
		TripCheckPointParameterInspection				tripCheckPointParameterInspectionBL 			= null;
		Set<TripCheckPointParameterInspection>			tripCheckPointParameterInspectionList 			= null;
		Set<TripCheckPointParameterInspection>			tripCheckPointParameterInspectionSavedList 		= null;
		TripCheckPointParameterInspectionPhoto 			tripCheckPointParameterInspectionPhotoBL 		= null;
		List<TripCheckPointParameterInspectionPhoto> 	tripCheckPointParameterInspectionPhotoList 		= null;
		TripCheckPointParameterInspection				validateTripCheckPointParameterInspection		= null;
		try {
			valueOutObject 								= new ValueObject();
			dataArrayObjColl 							= new ArrayList<ValueObject>();
			tripCheckPointParameterInspectionSavedList	= new HashSet<>();
			tripCheckPointParameterInspectionPhotoList	= new ArrayList<>();
			tripCheckPointParameterInspectionList		= new HashSet<>();
			tripCheckPointInspectionBL 		= tripCheckPointBL.prepareTripCheckPointInspection(valueObject);
			getTripCheckPointInspection 	= tripCheckPointInspectionRepository.getTripCheckPointInspection(valueObject.getLong("tripCheckPointId",0),valueObject.getLong("tripSheetId"),valueObject.getInt("companyId",0));
			if(getTripCheckPointInspection != null) {
				tripCheckPointInspectionBL.setTripCheckPointInspectionId(getTripCheckPointInspection.getTripCheckPointInspectionId());
				valueObject.put("tripCheckPointInspectionId", getTripCheckPointInspection.getTripCheckPointInspectionId());
			}
			if(tripCheckPointInspectionBL != null) {
				dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("parameterList");
				if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
					for (ValueObject object : dataArrayObjColl) {
						validateTripCheckPointParameterInspection	= tripCheckPointParameterInspectionRepository.validateDuplicateParameterByTripCheckPointInspectionId(object.getLong("tripCheckPointParameterId"),valueObject.getLong("tripCheckPointInspectionId",0), valueObject.getInt("companyId"));
						if(validateTripCheckPointParameterInspection != null) {
							continue;
						}
						tripCheckPointParameterInspectionBL =	tripCheckPointBL.prepareTripCheckPointParameterInspection(object,valueObject);
						tripCheckPointParameterInspectionBL.setTripCheckPointInspection(tripCheckPointInspectionBL);
						tripCheckPointParameterInspectionList.add(tripCheckPointParameterInspectionBL);
						
					}
					tripCheckPointInspectionBL.setTripCheckPointParameterInspectionList(tripCheckPointParameterInspectionList);
					tripCheckPointInspection	= tripCheckPointInspectionRepository.save(tripCheckPointInspectionBL);
					
					tripCheckPointParameterInspectionSavedList = tripCheckPointInspection.getTripCheckPointParameterInspectionList();
					
					if(tripCheckPointParameterInspectionSavedList != null && !tripCheckPointParameterInspectionSavedList.isEmpty()) {
						for(TripCheckPointParameterInspection obj : tripCheckPointParameterInspectionSavedList) {
							if(obj.isCheckPointParameterPhoto()) {
								tripCheckPointParameterInspectionPhotoBL =	tripCheckPointBL.prepareTripCheckPointParameterInspectionPhoto(file[i],obj);
								tripCheckPointParameterInspectionPhotoList.add(tripCheckPointParameterInspectionPhotoBL);
							}
							i++;
						}
						mongoTemplate.insertAll(tripCheckPointParameterInspectionPhotoList);
					}
					
					valueOutObject.put("save", true);
				}
			}
			
			return valueOutObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject removeInspectedParameter(ValueObject object) throws Exception {
		try {
			
			 tripCheckPointParameterInspectionRepository.removeInspectedParameter(object.getLong("tripCheckPointParameterInspectionId"));
			
			TripCheckPointParameterInspection tripCheckPointParameterInspection = tripCheckPointParameterInspectionRepository.getAvailabilityOfParamterByTripCheckPointInspectionId(object.getLong("tripCheckPointInspectionId"));
			
			if(tripCheckPointParameterInspection == null) {
				tripCheckPointInspectionRepository.removeInspectedCheckPoint(object.getLong("tripCheckPointInspectionId"));
			}
			
			object.put("deleteTripCheckPointParameter", true);
			return object;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}

}
