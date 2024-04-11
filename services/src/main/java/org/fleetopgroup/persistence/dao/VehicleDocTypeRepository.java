package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleDocType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleDocTypeRepository extends JpaRepository<VehicleDocType, Long> {

	@Modifying
	@Query("update VehicleDocType VDT set VDT.vDocType = ?1, VDT.lastModifiedById = ?2, VDT.lastModifiedOn = ?3 where VDT.dtid = ?4 AND VDT.company_Id = ?5")
	void updateVehicleDocType(String vDocType, Long modifiedById, Timestamp modifiedOn,  long dtid, Integer companyId);
	
    List<VehicleDocType> findAll(Sort sort);

    @Override
    void delete(VehicleDocType role);
     
    @Modifying
	@Query("UPDATE VehicleDocType VDT set VDT.markForDelete = 1 where VDT.dtid = ?1 AND VDT.company_Id = ?2")
	void deleteVehicleDocType(long dtid, Integer companyId);
    
    long count();
    
    VehicleDocType findByVDocType(String vDocType);

	@Query("From VehicleDocType VDT where VDT.dtid = ?1 AND VDT.company_Id = ?2")
	VehicleDocType getVehicleDocTypeByID(long dtid, Integer companyId);
	
	@Query("From VehicleDocType VDT where VDT.company_Id = ?1 and VDT.markForDelete = 0")
	List<VehicleDocType> findAllVehicleDocTypeByCompanyId(Integer company_Id);
	
	@Query("Select count(VDT.dtid) From VehicleDocType VDT where VDT.company_Id = ?1 and VDT.markForDelete = 0")
	long getVehicleDocTypeCountByCompanyId(Integer company_Id);
	
	@Query("From VehicleDocType VDT where VDT.company_Id = ?1 and VDT.vDocType = ?2 and VDT.markForDelete = 0")
	List<VehicleDocType> findByVDocTypeAndCompanyId(Integer company_Id, String vDocType);
}
