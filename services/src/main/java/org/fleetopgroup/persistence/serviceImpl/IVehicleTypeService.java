package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleTypeDto;
import org.fleetopgroup.persistence.model.VehicleType;
import org.springframework.data.domain.Page;

public interface IVehicleTypeService {

    VehicleType registerNewVehicleType(VehicleType accountDto) throws Exception;
    
    void updateVehicleType(String vType, String updateBy, Date updateDate, long tid, Integer companyId, Integer maxAllowedOdometer, Long serviceProgramId) throws Exception;
    
    List<VehicleType> findAll();
    
    VehicleType findByVtype(String vtype, Integer company_Id) throws Exception;
    
    VehicleType getVehicleType(String verificationToken);

    void deleteVehicleType(long tid, Integer companyId);

    VehicleType getVehicleTypeByID(long tid, Integer companyId);
    
    VehicleTypeDto getVehicleTypeDtoByID(long tid, Integer companyId) throws Exception;

    long count();

    List<VehicleType> findAllVehileTypeByCompanyId(Integer company_Id) throws Exception;

    List<VehicleType> findAllVehileTypeByOnlyCompanyId(Integer company_Id) throws Exception;
    
    long	vehicleTypeCountForCompany(Integer company_Id) throws Exception;

	Page<VehicleType> getDeployment_Page_VehileType(Integer pageNumber, CustomUserDetails userDetails) throws Exception;

	List<VehicleTypeDto> GET_list_Of_VehileType(Integer pageNumber, CustomUserDetails userDetails);
	
	public List<VehicleType> getVehicleTypeByName(String term, Integer companyId) throws Exception;
	
	public HashMap<Long, VehicleType> getVehicleTypeHMByCompanyId(Integer companyId) throws Exception; 
   
	public List<VehicleTypeDto> getCompanyWiseVehcileType(Integer pageNumber, CustomUserDetails userDetails) ;
}
