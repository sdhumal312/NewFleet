package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.fleetopgroup.constant.VehiclePhotoTypeConstant;
import org.fleetopgroup.persistence.dao.VehiclePhoTypeRepository;
import org.fleetopgroup.persistence.model.VehiclePhoType;
import org.fleetopgroup.persistence.serviceImpl.IVehiclePhoTypeService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VehiclePhoTypeService implements IVehiclePhoTypeService {
	@Autowired
	private VehiclePhoTypeRepository vehiclePhoTyperepository;

	// API

	@Override
	public VehiclePhoType registerNewVehiclePhoType(final VehiclePhoType accountDto) throws Exception {

		return vehiclePhoTyperepository.save(accountDto);
	}

	@Override
	public void updateVehiclePhoType(String vPhoType, String modifiedBy, Timestamp  modifiedOn, short vehiclePhotoTypeId ,long ptid, Integer companyId) throws Exception {

		vehiclePhoTyperepository.updateVehiclePhoType(vPhoType, modifiedBy, modifiedOn ,vehiclePhotoTypeId, ptid, companyId);
	}

	@Override
	public List<VehiclePhoType> findAll() {

		return vehiclePhoTyperepository.findAll();
	}

	@Override
	public VehiclePhoType getVehiclePhoType(String verificationToken) {
		return null;
	}

	@Override
	public void deleteVehiclePhoType(long ptid, Integer companyId) {

		vehiclePhoTyperepository.deleteVehiclePhoType(ptid, companyId);
	}

	@Override
	public VehiclePhoType getVehiclePhoTypeByID(long ptid, Integer companyId) {

		return vehiclePhoTyperepository.getVehiclePhoTypeByID(ptid, companyId);
	}

	@Override
	public long count() {

		return vehiclePhoTyperepository.count();
	}

	@Override
	public List<VehiclePhoType> findByVPhoTypeByCompanyId(String vPhoType, Integer company_Id) {

		return vehiclePhoTyperepository.findByVPhoTypeByCompanyId(vPhoType, company_Id);
	}

	@Override
	public List<VehiclePhoType> findAllVehiclePhoToTypeByCompanyId(Integer company_Id) throws Exception {
		try {
			return vehiclePhoTyperepository.findAllVehiclePhoToTypeByCompanyId(company_Id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public long countVehiclePhotoTypeByCompanyId(Integer company_Id) throws Exception {
		try {
			return vehiclePhoTyperepository.countVehiclePhotoTypeByCompanyId(company_Id);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VehiclePhoType> validateUpdateVehiclePhotoType(String vPhoType, Integer company_Id, long ptid) throws Exception {

		return vehiclePhoTyperepository.validateUpdateVehiclePhotoType(vPhoType, company_Id, ptid);
	}
	
	@Override
	public List<VehiclePhoType> getAllVehicleAccidentDocumentType(ValueObject valueObject) throws Exception {

		return vehiclePhoTyperepository.getAllVehicleAccidentDocumentType(VehiclePhotoTypeConstant.VEHICLE_ACCIDENT_PHOTO,valueObject.getInt("companyId"));
	}


}