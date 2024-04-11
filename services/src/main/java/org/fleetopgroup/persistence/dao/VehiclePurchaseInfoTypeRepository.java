package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.VehiclePurchaseInfoType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehiclePurchaseInfoTypeRepository extends JpaRepository<VehiclePurchaseInfoType, Long> {

	@Modifying
	@Query("update VehiclePurchaseInfoType VPT set VPT.vPurchaseInfoType = ?1, VPT.lastModifiedById = ?2, VPT.lastModifiedOn = ?3 where VPT.ptid = ?4 and VPT.company_Id = ?5")
	void updateVehiclePurchaseInfoType(String vPurchaseInfoType, Long modifiedById, Timestamp modifiedOn, long ptid, Integer company_Id);
	
    List<VehiclePurchaseInfoType> findAll(Sort sort);

    @Override
    void delete(VehiclePurchaseInfoType role);
     
    @Modifying
    @Query("UPDATE FROM VehiclePurchaseInfoType VPT set VPT.markForDelete = 1  where VPT.ptid = ?1 and VPT.company_Id = ?2")
	void deleteVehiclePurchaseInfoType(long ptid, Integer companyId);
    
    @Query("FROM VehiclePurchaseInfoType VPT where VPT.vPurchaseInfoType = ?1 and VPT.company_Id = ?2 and VPT.markForDelete = 0")
    VehiclePurchaseInfoType findByVPurchaseInfoType(String vPurchaseInfoType, Integer company_Id);
    
    long count();

	@Query("FROM VehiclePurchaseInfoType VPT where VPT.ptid = ?1 AND VPT.company_Id = ?2")
	VehiclePurchaseInfoType getVehiclePurchaseInfoTypeByID(long ptid, Integer companyId);
	
	@Query("FROM VehiclePurchaseInfoType VPT where VPT.company_Id = ?1 and VPT.markForDelete = 0")
	List<VehiclePurchaseInfoType> findAllByCompanyId(Integer company_Id);
	
	@Query("Select count(VPT.ptid) from VehiclePurchaseInfoType VPT where VPT.company_Id = ?1 and VPT.markForDelete = 0")
	long getCountByCompanyId(Integer company_Id);
}
