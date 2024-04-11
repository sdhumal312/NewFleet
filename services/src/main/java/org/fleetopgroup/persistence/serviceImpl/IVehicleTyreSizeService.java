/**
 * 
 */
package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleTyreSize;

/**
 * @author fleetop
 *
 */
public interface IVehicleTyreSizeService {

	public VehicleTyreSize registerNewVehicleType(VehicleTyreSize accountDto) throws Exception;

    public void update_VehicleTyre_size(String tyre_size, String tyre_size_descrition,  Long updateById, Date updateDate, Integer tid, Integer companyid) throws Exception;

    public List<VehicleTyreSize> findAll();

    public VehicleTyreSize findByTYRE_SIZE(String tyre_size, Integer companyId) throws Exception;
    
    public VehicleTyreSize findByTYRE_SIZE(String tyre_size) throws Exception;

	
    public void delete_Vehicle_TyreSize(Integer tid, Integer companyId);

    public VehicleTyreSize get_Vehicle_Tyre_ID(Integer TS_id, Integer companyid);
    
    public VehicleTyreSize get_Vehicle_Tyre_ID(Integer TS_id);

    public long count();
     
    public List<VehicleTyreSize> Search_VehicleTyreSizeType_select(String search, Integer companyId) throws Exception;
    
    public List<VehicleTyreSize> Search_VehicleTyreSizeType_select(String search) throws Exception;
    
    public List<VehicleTyreSize> findAllByCompanyId(Integer companyId) throws Exception;
}
