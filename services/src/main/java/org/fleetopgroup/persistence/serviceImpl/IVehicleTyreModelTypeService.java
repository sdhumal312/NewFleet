/**
 * 
 */
package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleTyreModelType;

/**
 * @author fleetop
 *
 */
public interface IVehicleTyreModelTypeService {

	VehicleTyreModelType registerNew_VehicleTyreModelType(VehicleTyreModelType accountDto) throws Exception;

	void update_VehicleTyreModelType(String TYRE_MODEL, String TYRE_MODEL_DESCRITION, Long updateById, Date updateDate,
			Integer TYRE_MT_ID, Integer companyId) throws Exception;

	List<VehicleTyreModelType> findAll() throws Exception;

	VehicleTyreModelType get_VehicleTyreModelType(String verificationToken, Integer companyId) throws Exception;

	void delete_VehicleTyreModelType(Integer TYRE_MT_ID, Integer companyId);

	VehicleTyreModelType getVehicleTyreModelTypeByID(Integer TYRE_MT_ID, Integer companyId) throws Exception;

	long count();

	// drop down Inventory VehicleTyreModelType
	public List<VehicleTyreModelType> Search_VehicleTyreModelType_select(String search, Integer companyId) throws Exception;

}
