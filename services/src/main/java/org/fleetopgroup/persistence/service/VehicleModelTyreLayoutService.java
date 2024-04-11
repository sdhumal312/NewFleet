/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.TyreModalConstant;
import org.fleetopgroup.persistence.bl.VehicleModelTyreLayoutBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.dao.VehicleModelTyreLayoutPositionRepository;
import org.fleetopgroup.persistence.dao.VehicleModelTyreLayoutRepository;
import org.fleetopgroup.persistence.dao.VehicleRepository;
import org.fleetopgroup.persistence.dao.VehicleTyreLayoutPositionRepository;
import org.fleetopgroup.persistence.dto.VehicleModelTyreLayoutDto;
import org.fleetopgroup.persistence.dto.VehicleModelTyreLayoutPositionDto;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleModelTyreLayout;
import org.fleetopgroup.persistence.model.VehicleModelTyreLayoutPosition;
import org.fleetopgroup.persistence.model.VehicleTyreLayoutPosition;
import org.fleetopgroup.persistence.serviceImpl.IVehicleModelTyreLayoutService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("VehicleModelTyreLayoutService")
@Transactional
public class VehicleModelTyreLayoutService implements IVehicleModelTyreLayoutService {
	
	@Autowired	private VehicleRepository 							vehicleDao;
	@Autowired	private VehicleModelTyreLayoutRepository 			vModalTyreLayoutDao;
	@Autowired	private VehicleModelTyreLayoutPositionRepository 	vModalTyreLayoutPositionDao;
	@Autowired	private VehicleTyreLayoutPositionRepository 		vehicleTyreLayoutPositionRepository;
	
	VehicleOdometerHistoryBL 	VOHBL 					= new VehicleOdometerHistoryBL();
	VehicleModelTyreLayoutBL	vModelTyreLayoutBL 		= new VehicleModelTyreLayoutBL();

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public ValueObject saveVehicleModelTyreLayout(ValueObject valueObject) throws Exception {
		VehicleModelTyreLayout						vehicleModelTyreLayout						= null;
		String[] 									positionArr 								= null;
		String[] 									positionModelId 							= null;
		VehicleModelTyreLayoutPosition				vehicleModelTyreLayoutPosition				= null;
		List<VehicleModelTyreLayoutPosition>		vehicleModelTyreLayoutPositionList			= null;
		List<Vehicle>								vehicleList									= null;
		VehicleModelTyreLayout						validateVehicleModelTyreLayout				= null;
		VehicleTyreLayoutPosition       			vehicleTyreLayoutPosition					= null;
		List<VehicleTyreLayoutPosition>       		vehicleTyreLayoutPositionList				= null;
		try {
			vehicleList							= new ArrayList<>();
			vehicleModelTyreLayoutPositionList  = new ArrayList<>();
			vehicleTyreLayoutPositionList  		= new ArrayList<>();
			validateVehicleModelTyreLayout  	= validateVehicleModelTyreLayout(valueObject);
			if(validateVehicleModelTyreLayout != null) {
				valueObject.put("alredyExist", true);
				return valueObject;
			}
			
			vehicleModelTyreLayout = vModelTyreLayoutBL.prepareVehicleModelTyreLayout(valueObject);
			vehicleModelTyreLayout = vModalTyreLayoutDao.save(vehicleModelTyreLayout);
			
			vehicleList = vehicleDao.getvehicleByModelId(valueObject.getLong("vehicleModelId",0),valueObject.getInt("companyId"));
		
			
			valueObject.put("vehicleModelTyreLayoutId", vehicleModelTyreLayout.getVehicleModelTyreLayoutId());
			
			positionArr 		= valueObject.getString("position").split(",");
			positionModelId 	= valueObject.getString("positionModelId").split(",");
			
			for(int i = 0; i < positionArr.length; i++ ) {
				vehicleModelTyreLayoutPosition = vModelTyreLayoutBL.prepareVehicleModelTyreLayoutPosition(positionArr[i],Integer.parseInt(positionModelId[i]),valueObject);
				vehicleModelTyreLayoutPositionList.add(vehicleModelTyreLayoutPosition);
			}
			if(vehicleList != null && !vehicleList.isEmpty()) {
				for(Vehicle vehicle : vehicleList) {
					valueObject.put("vid", vehicle.getVid());
					//	vehicleTyreLayoutRepository(positionArr[i],Integer.parseInt(positionModelId[i]),valueObject);
					for(int i = 0; i < positionArr.length; i++ ) {
						vehicleTyreLayoutPosition = vModelTyreLayoutBL.prepareMultipleVehicleTyreLayoutPosition(positionArr[i],Integer.parseInt(positionModelId[i]),valueObject);
						vehicleTyreLayoutPositionList.add(vehicleTyreLayoutPosition);
					}
				}
			}
			vehicleTyreLayoutPositionRepository.saveAll(vehicleTyreLayoutPositionList);
			vModalTyreLayoutPositionDao.saveAll(vehicleModelTyreLayoutPositionList);
			
			valueObject.put("vehicleModelTyreLayoutId", vehicleModelTyreLayout.getVehicleModelTyreLayoutId());
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Override
	public VehicleModelTyreLayout validateVehicleModelTyreLayout(ValueObject valueObject) throws Exception {
		VehicleModelTyreLayout						vehicleModelTyreLayout						= null;
		try {
			vehicleModelTyreLayout = vModalTyreLayoutDao.getvehicleTyreModelLayoutByModelId(valueObject.getLong("vehicleModelId",0),valueObject.getInt("companyId"));
			return vehicleModelTyreLayout;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public ValueObject getVehicleModelTyreLayout(ValueObject valueObject) throws Exception {
		VehicleModelTyreLayoutDto						vehicleModelTyreLayout						= null;
		try {
			vehicleModelTyreLayout 				= getVehicleModelTyreLayoutDetails(valueObject);
			valueObject.put("vehicleModelTyreLayout", vehicleModelTyreLayout);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public VehicleModelTyreLayoutDto getVehicleModelTyreLayoutDetails(ValueObject valueObject) throws Exception {
		Query 							query 				= null;
		VehicleModelTyreLayoutDto vehicleModelTyreLayoutDto = null;
		try {
			query = entityManager.createQuery(
					"SELECT VTL.vehicleModelTyreLayoutId, VTL.vehicleModelId, VM.vehicleModelName, VTL.numberOfAxle, "
					+ " VTL.frontTyreModelId, TMS.TYRE_MODEL_SUBTYPE, VTL.rearTyreModelId, TMS1.TYRE_MODEL_SUBTYPE, VTL.spareTyreModelId, TMS2.TYRE_MODEL_SUBTYPE, "
					+ " VTS.TYRE_SIZE, VTS1.TYRE_SIZE, VTS2.TYRE_SIZE, VTL.isSpareTyrePresent, VTL.dualTyrePresentAxle "
					+ " FROM VehicleModelTyreLayout AS VTL "
					+ " INNER JOIN VehicleModel VM ON VM.vehicleModelId = VTL.vehicleModelId "
					+ " INNER JOIN VehicleTyreModelSubType TMS ON TMS.TYRE_MST_ID = VTL.frontTyreModelId "
					+ " LEFT JOIN VehicleTyreSize VTS ON VTS.TS_ID = TMS.tyreModelSizeId "
					+ " INNER JOIN VehicleTyreModelSubType TMS1 ON TMS1.TYRE_MST_ID = VTL.rearTyreModelId "
					+ " LEFT JOIN VehicleTyreSize VTS1 ON VTS1.TS_ID = TMS1.tyreModelSizeId "
					+ " LEFT JOIN VehicleTyreModelSubType TMS2 ON TMS2.TYRE_MST_ID = VTL.spareTyreModelId "
					+ " LEFT JOIN VehicleTyreSize VTS2 ON VTS2.TS_ID = TMS2.tyreModelSizeId "
					+ " WHERE VTL.vehicleModelId = "+valueObject.getLong("vehicleModelId",0)+" AND VTL.companyId = "+valueObject.getInt("companyId")+" AND VTL.markForDelete = 0");

			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			
			vehicleModelTyreLayoutDto = new VehicleModelTyreLayoutDto();
			if (result != null) {
				vehicleModelTyreLayoutDto.setVehicleModelTyreLayoutId((Long)result[0]);
				vehicleModelTyreLayoutDto.setVehicleModelId((Long)result[1]);
				vehicleModelTyreLayoutDto.setVehicleModel((String)result[2]);
				vehicleModelTyreLayoutDto.setNumberOfAxle((Integer)result[3]);
				vehicleModelTyreLayoutDto.setFrontTyreModelId((Integer)result[4]);
				vehicleModelTyreLayoutDto.setFrontTyreModel((String)result[5]);
				vehicleModelTyreLayoutDto.setRearTyreModelId((Integer)result[6]);
				vehicleModelTyreLayoutDto.setRearTyreModel((String)result[7]);
				if(result[8] != null) {
					vehicleModelTyreLayoutDto.setSpareTyreModelId((Integer)result[8]);
					vehicleModelTyreLayoutDto.setSpareTyreModel((String)result[9]);
				}
				if(result[10] != null) {
					vehicleModelTyreLayoutDto.setFrontTyreSize((String)result[10]);
				}
				if(result[11] != null) {
					vehicleModelTyreLayoutDto.setRearTyreSize((String)result[11]);
				}
				if(result[12] != null) {
					vehicleModelTyreLayoutDto.setSpareTyreSize((String)result[12]);
				}
				vehicleModelTyreLayoutDto.setSpareTyrePresent((boolean)result[13]);
				vehicleModelTyreLayoutDto.setDualTyrePresentAxle((String)result[14]);
			} 
			return vehicleModelTyreLayoutDto;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}
	
	public ValueObject getVehicleModelTyreLayoutPositionByPosition(ValueObject valueObject) throws Exception {
		Query 							query 				= null;
		VehicleModelTyreLayoutPositionDto vehicleModelTyreLayoutPositionDto = null;
		try {
			query = entityManager.createQuery(
					"SELECT VTLP.vehicleModelTyreLayoutPositionId, VTLP.axle, VTLP.position, TMS.TYRE_MODEL_SUBTYPE, "
					+ " TMS.tyreModelTypeId, TMS.gauageMeasurementLine, TMS.tyreGauge, TMS.tyreTubeTypeId, TMS.ply, TMS.psi, VTS.TYRE_SIZE "
					+ " FROM VehicleModelTyreLayoutPosition AS VTLP "
					+ " INNER JOIN VehicleTyreModelSubType TMS ON TMS.TYRE_MST_ID = VTLP.tyreModelId "
					+ " LEFT JOIN VehicleTyreSize VTS ON VTS.TS_ID = TMS.tyreModelSizeId "
					+ " WHERE VTLP.position = '"+valueObject.getString("position")+"' AND VTLP.vehicleModelTyreLayoutId = "+valueObject.getLong("vehicleModelTyreLayoutId")+""
					+ " AND VTLP.companyId = "+valueObject.getInt("companyId")+" AND VTLP.markForDelete = 0");

			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			
			vehicleModelTyreLayoutPositionDto = new VehicleModelTyreLayoutPositionDto();
			if (result != null) {
				vehicleModelTyreLayoutPositionDto.setVehicleModelTyreLayoutPositionId((Long)result[0]);
				vehicleModelTyreLayoutPositionDto.setAxle((Integer)result[1]);
				vehicleModelTyreLayoutPositionDto.setPosition((String)result[2]);
				vehicleModelTyreLayoutPositionDto.setTyreModel((String)result[3]);
				vehicleModelTyreLayoutPositionDto.setTyreModelType(TyreModalConstant.getTyreModelType((short)result[4]));
				vehicleModelTyreLayoutPositionDto.setGauageMeasurementLine((Integer)result[5]);
				vehicleModelTyreLayoutPositionDto.setTyreGauge((Integer)result[6]);
				vehicleModelTyreLayoutPositionDto.setTyreTubeType(TyreModalConstant.getTyreTubeType((short)result[7]));
				vehicleModelTyreLayoutPositionDto.setPly((Integer)result[8]);
				vehicleModelTyreLayoutPositionDto.setPsi((Integer)result[9]);
				vehicleModelTyreLayoutPositionDto.setTyreModelSize((String)result[10]);
			} 
			
			
			valueObject.put("vehicleModelTyreLayoutPosition", vehicleModelTyreLayoutPositionDto);
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}
	@Override
	@Transactional
	public ValueObject deleteVehicleModelTyreLayout(ValueObject valueObject) throws Exception {
		Long				assignCount			= null;
		List<Vehicle>		vehicleList			= null;
		StringBuffer vids = new StringBuffer();
		try {
			vehicleList	= new ArrayList<>();
			assignCount = checkAssignTyre(valueObject);
			if(assignCount != null && assignCount > 0) {
				valueObject.put("tyreExist", true);
				return valueObject;
			}
			vehicleList = vehicleDao.getvehicleByModelId(valueObject.getLong("vehicleModelId",0),valueObject.getInt("companyId"));
			if(vehicleList != null && !vehicleList.isEmpty()) {
				for(int i = 0; i < vehicleList.size(); i++) {
					if (i < vehicleList.size() - 1) {
						vids = vids.append(vehicleList.get(i).getVid() + ",");
					} else {
						vids = vids.append(vehicleList.get(i).getVid());
					}

				}
				
			entityManager.createQuery("UPDATE VehicleTyreLayoutPosition SET markForDelete = 1 WHERE VEHICLE_ID IN ("+vids+")  AND COMPANY_ID = "+valueObject.getInt("companyId")+"").executeUpdate();
			}
			vModalTyreLayoutDao.deleteVehicleModelTyreLayout(valueObject.getLong("vehicleModelTyreLayoutId"), valueObject.getInt("companyId"));
			vModalTyreLayoutPositionDao.deleteVehicleModelTyreLayoutPosition(valueObject.getLong("vehicleModelTyreLayoutId"), valueObject.getInt("companyId"));
			
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public Long checkAssignTyre(ValueObject valueObject) throws Exception {
		TypedQuery<Long> query 							= null;
		try {
			query = entityManager.createQuery(
					"SELECT COUNT(*) FROM VehicleTyreLayoutPosition AS VTP "
					+ " INNER JOIN Vehicle V ON V.vid = VTP.VEHICLE_ID " 
					+ " WHERE V.vehicleModalId = "+valueObject.getLong("vehicleModelId",0)+" "
					+ " AND VTP.TYRE_ID  > 0 AND VTP.COMPANY_ID = "+valueObject.getInt("companyId")+" AND VTP.markForDelete = 0", Long.class);

			
			return query.getSingleResult();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}
	
	@Override
	public ValueObject getVehicleModelTyreDetails(ValueObject valueObject) throws Exception {
		try {
			Vehicle vehicle = vehicleDao.getVehicel(valueObject.getInt("vid",0), valueObject.getInt("companyId"));
			if(vehicle != null) {
				valueObject.put("vehicleModelId", vehicle.getVehicleModalId());
				getVehicleModelTyreLayoutPositionByVehicleModelId(valueObject);
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public ValueObject getVehicleModelTyreLayoutPositionByVehicleModelId(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> 		query 							= null;
		List<VehicleModelTyreLayoutPositionDto> vehicleModelTyreLayoutPositionList = null;
		VehicleModelTyreLayoutPositionDto vehicleModelTyreLayoutPositionDto = null;
		try {
			query = entityManager.createQuery(
					"SELECT VTLP.vehicleModelTyreLayoutPositionId, VTLP.axle, VTLP.position, TMS.TYRE_MODEL_SUBTYPE, "
					+ " TMS.tyreModelTypeId, TMS.gauageMeasurementLine, TMS.tyreGauge, TMS.tyreTubeTypeId, TMS.ply, TMS.psi, VTS.TYRE_SIZE,VTLP.tyreModelId "
					+ " FROM VehicleModelTyreLayoutPosition AS VTLP "
					+ " INNER JOIN VehicleModelTyreLayout VTL ON VTL.vehicleModelTyreLayoutId = VTLP.vehicleModelTyreLayoutId "
					+ " INNER JOIN VehicleTyreModelSubType TMS ON TMS.TYRE_MST_ID = VTLP.tyreModelId "
					+ " LEFT JOIN VehicleTyreSize VTS ON VTS.TS_ID = TMS.tyreModelSizeId "
					+ " WHERE VTL.vehicleModelId = "+valueObject.getLong("vehicleModelId",0)+""
					+ " AND VTLP.companyId = "+valueObject.getInt("companyId")+" AND VTLP.markForDelete = 0",
					Object[].class);

			
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				vehicleModelTyreLayoutPositionList = new ArrayList<VehicleModelTyreLayoutPositionDto>();
				
				for (Object[] result : results) {
					vehicleModelTyreLayoutPositionDto = new VehicleModelTyreLayoutPositionDto();
					vehicleModelTyreLayoutPositionDto.setVehicleModelTyreLayoutPositionId((Long)result[0]);
					vehicleModelTyreLayoutPositionDto.setAxle((Integer)result[1]);
					vehicleModelTyreLayoutPositionDto.setPosition((String)result[2]);
					vehicleModelTyreLayoutPositionDto.setTyreModel((String)result[3]);
					vehicleModelTyreLayoutPositionDto.setTyreModelType(TyreModalConstant.getTyreModelType((short)result[4]));
					vehicleModelTyreLayoutPositionDto.setGauageMeasurementLine((Integer)result[5]);
					vehicleModelTyreLayoutPositionDto.setTyreGauge((Integer)result[6]);
					vehicleModelTyreLayoutPositionDto.setTyreTubeType(TyreModalConstant.getTyreTubeType((short)result[7]));
					vehicleModelTyreLayoutPositionDto.setPly((Integer)result[8]);
					vehicleModelTyreLayoutPositionDto.setPsi((Integer)result[9]);
					vehicleModelTyreLayoutPositionDto.setTyreModelSize((String)result[10]);
					vehicleModelTyreLayoutPositionDto.setTyreModelId((Integer)result[11]);
					vehicleModelTyreLayoutPositionList.add(vehicleModelTyreLayoutPositionDto);
					
					}
				}

			
			valueObject.put("vehicleModelTyreLayoutPositionList", vehicleModelTyreLayoutPositionList);
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}

}
