package org.fleetopgroup.persistence.report.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.bl.VehicleAccidentTypeMasterBL;
import org.fleetopgroup.persistence.dao.VehicleAccidentTypeMasterRepository;
import org.fleetopgroup.persistence.model.VehicleAccidentTypeMaster;
import org.fleetopgroup.persistence.report.dao.impl.IVehicleAccidentTypeMasterService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("VehicleAccidentTypeMasterService")
@Transactional
public class VehicleAccidentTypeMasterService implements IVehicleAccidentTypeMasterService{

	@Autowired	private VehicleAccidentTypeMasterRepository 	vehicleAccidentTypeMasterRepository;
	VehicleAccidentTypeMasterBL	vehicleAccidentTypeMasterBL 		= new VehicleAccidentTypeMasterBL();
	@Override
	public ValueObject getAllVehicleAccidentTypeMaster(ValueObject valueObject) throws Exception{
		List<VehicleAccidentTypeMaster> VehicleAccidentTypeList = null;
		try {
			VehicleAccidentTypeList = new ArrayList<>();
			VehicleAccidentTypeList = vehicleAccidentTypeMasterRepository.getAllVehicleAccidentTypeMaster(valueObject.getInt("companyId"));
			valueObject.put("vehicleAccidentTypeList", VehicleAccidentTypeList);
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getVehicleAccidentTypeMasterById(ValueObject valueObject) throws Exception{
		VehicleAccidentTypeMaster VehicleAccidentTypeMaster = null;
		try {
			VehicleAccidentTypeMaster = new VehicleAccidentTypeMaster();
			VehicleAccidentTypeMaster = vehicleAccidentTypeMasterRepository.getVehicleAccidentTypeMasterById(valueObject.getLong("vehicleAccidentTypeMasterId") ,valueObject.getInt("companyId"));
			valueObject.put("VehicleAccidentTypeMaster", VehicleAccidentTypeMaster);
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public VehicleAccidentTypeMaster validateVehicleAccidentTypeMaster(String vehicleAccidentTypeMasterName , Integer companyId) throws Exception{
		VehicleAccidentTypeMaster	VehicleAccidentTypeMaster	= null;
		try {
			VehicleAccidentTypeMaster = vehicleAccidentTypeMasterRepository.findByName(vehicleAccidentTypeMasterName, companyId);
			return VehicleAccidentTypeMaster;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject saveVehicleAccidentTypeMaster(ValueObject valueObject) throws Exception{
		VehicleAccidentTypeMaster			VehicleAccidentTypeMaster					= null;
		VehicleAccidentTypeMaster 		validateVehicleAccidentType			= null;
		try {
			validateVehicleAccidentType		=	validateVehicleAccidentTypeMaster(valueObject.getString("vehicleAccidentTypeMasterName"),valueObject.getInt("companyId"));
			
			if(validateVehicleAccidentType != null) {
				valueObject.put("alreadyExist", true);
			}else {
				VehicleAccidentTypeMaster = vehicleAccidentTypeMasterBL.prepareVehicleAccidentTypeMaster(valueObject);
				vehicleAccidentTypeMasterRepository.save(VehicleAccidentTypeMaster);
				valueObject.put("save", true);
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Override
	@Transactional
	public ValueObject updateVehicleAccidentTypeMaster(ValueObject valueObject) throws Exception {
		VehicleAccidentTypeMaster 				validateVehicleAccidentType			= null;
		VehicleAccidentTypeMaster 				VehicleAccidentTypeMaster					= null;
		ValueObject 							vehicleAccidentTypeObj				= null;
		try {
			vehicleAccidentTypeObj		= getVehicleAccidentTypeMasterById(valueObject);
			VehicleAccidentTypeMaster  		= (VehicleAccidentTypeMaster) vehicleAccidentTypeObj.get("VehicleAccidentTypeMaster");
			System.err.println("VehicleAccidentTypeMaster"+VehicleAccidentTypeMaster);
			if(VehicleAccidentTypeMaster != null && valueObject.getString("vehicleAccidentTypeMasterName").equalsIgnoreCase(VehicleAccidentTypeMaster.getVehicleAccidentTypeMasterName().trim())) {
				vehicleAccidentTypeMasterRepository.updateVehicleAccidentTypeMaster(VehicleAccidentTypeMaster.getVehicleAccidentTypeMasterName(),valueObject.getString("description"),
						 valueObject.getLong("userId"),DateTimeUtility.getCurrentTimeStamp(), valueObject.getLong("vehicleAccidentTypeMasterId"));
			}else {
				
				System.err.println("valueObject"+valueObject);
			
				validateVehicleAccidentType		= validateVehicleAccidentTypeMaster(valueObject.getString("vehicleAccidentTypeMasterName"),valueObject.getInt("companyId"));
				if(validateVehicleAccidentType != null) {
					valueObject.put("alreadyExist", true);
				}else {
					vehicleAccidentTypeMasterRepository.updateVehicleAccidentTypeMaster(valueObject.getString("vehicleAccidentTypeMasterName"),valueObject.getString("description"),
							 valueObject.getLong("userId"),DateTimeUtility.getCurrentTimeStamp(),valueObject.getLong("vehicleAccidentTypeMasterId"));
				}
			}
		
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteVehicleAccidentTypeMaster(ValueObject valueobject) throws Exception{
		try {
			vehicleAccidentTypeMasterRepository.deleteVehicleAccidentTypeMaster(valueobject.getLong("vehicleAccidentTypeMasterId"),valueobject.getInt("companyId"));
			return valueobject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
}
