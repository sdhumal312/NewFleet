package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.VehiclePhoType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehiclePhoTypeRepository extends JpaRepository<VehiclePhoType, Long> {

	@Modifying
	@Query("update VehiclePhoType VPT set VPT.vPhoType = ?1, VPT.lastModifiedBy = ?2, VPT.lastModifiedOn = ?3, vehiclePhotoTypeId =?4 where VPT.ptid = ?5 and VPT.company_Id = ?6")
	void updateVehiclePhoType(String vPhoType, String modifiedBy, Timestamp  modifiedOn,short vehiclePhotoTypeId,  long ptid, Integer companyId);
	
    List<VehiclePhoType> findAll(Sort sort);

    @Override
    void delete(VehiclePhoType role);
     
    @Modifying
	@Query("update VehiclePhoType VPT set VPT.markForDelete = 1 where VPT.ptid = ?1 and VPT.company_Id = ?2")
	void deleteVehiclePhoType(long ptid, Integer companyId);
    
    long count();
    
    @Query("FROM VehiclePhoType VPT where VPT.vPhoType = ?1 and VPT.company_Id = ?2 and VPT.markForDelete = 0")
    List<VehiclePhoType> findByVPhoTypeByCompanyId(String vPhoType, Integer company_Id);

	@Query("FROM VehiclePhoType VPT where VPT.ptid = ?1 AND VPT.company_Id = ?2 AND VPT.markForDelete = 0")
	VehiclePhoType getVehiclePhoTypeByID(long ptid, Integer companyId);
	
	@Query("FROM VehiclePhoType VPT where VPT.company_Id = ?1 and VPT.markForDelete = 0")
	List<VehiclePhoType> findAllVehiclePhoToTypeByCompanyId(Integer company_Id);
	
	@Query("Select count(VPT.ptid)	FROM VehiclePhoType VPT where VPT.company_Id = ?1 and VPT.markForDelete = 0")
	long countVehiclePhotoTypeByCompanyId(Integer company_Id);
	
	 @Query("FROM VehiclePhoType VPT where VPT.vPhoType = ?1 and VPT.company_Id = ?2 and VPT.ptid <> ?3 and VPT.markForDelete = 0")
	    List<VehiclePhoType> validateUpdateVehiclePhotoType(String vPhoType, Integer company_Id, long ptid);

	  @Query("FROM VehiclePhoType VPT where VPT.vehiclePhotoTypeId = ?1 and VPT.company_Id = ?2 and VPT.markForDelete = 0")
	List<VehiclePhoType> getAllVehicleAccidentDocumentType(short vehiclePhotoTypeId, int company_Id);

}
