/**
 * 
 */
package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleTyreModelSubTypeDto;
import org.fleetopgroup.persistence.model.VehicleTyreModelSubType;
import org.fleetopgroup.web.util.ValueObject;

/**
 * @author fleetop
 *
 */
public interface IVehicleTyreModelSubTypeService {

	VehicleTyreModelSubType registerNew_VehicleTyreModelType(VehicleTyreModelSubType accountDto) throws Exception;

	void update_VehicleTyreModelSubType(Integer TYRE_MODEL, String TYRE_MODEL_SUBTYPE,  String TYRE_MODEL_DESCRITION, Long updateById, Date updateDate , Integer warrantyPeriod, short	warrantyTypeId, String	warrentyterm,  Integer TYRE_MST_ID, Integer companyId) throws Exception;

	List<VehicleTyreModelSubType> findAll();

	VehicleTyreModelSubType get_VehicleTyre_ModelSubType(String verificationToken, Integer companyId) throws Exception;

	VehicleTyreModelSubType get_VehicleTyre_ModelSubTypeById(Integer TYRE_MT_ID, Integer companyId) throws Exception;

	void delete_VehicleTyreModel_SubType(Integer TYRE_MT_ID, Integer companyId);

	VehicleTyreModelSubTypeDto getVehicleTyreModel_SubTypeByID(Integer TYRE_MT_ID, Integer companyId) throws Exception;

	long count();

	public List<VehicleTyreModelSubType> SearchOnly_ModelSubType(Integer Model_name, Integer companyId)  throws Exception;

	//This interface search sub model name in to all
	public List<VehicleTyreModelSubType> Search_VehicleTyreSubModelType_select(String search, Integer companyId)  throws Exception;

	
	List<VehicleTyreModelSubTypeDto> findAllByCompanyId(Integer companyId) throws Exception;

	ValueObject getVehicleTyreModelSubTypeDetails(ValueObject valueOutObject)throws Exception;

	public List<VehicleTyreModelSubType> getSearchTyreSubModelByTyreSize(String search, Integer companyId,
			boolean validateTyreSizeConfig, Integer tyreSize) throws Exception;

	public Long validateTyreManufacturer(Integer tM_ID, Integer company_id) throws Exception;
}
