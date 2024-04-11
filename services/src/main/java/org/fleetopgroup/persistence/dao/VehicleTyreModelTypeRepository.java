/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleTyreModelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface VehicleTyreModelTypeRepository  extends JpaRepository<VehicleTyreModelType, Integer>{

	//VehicleTyreModelType registerNew_VehicleTyreModelType(VehicleTyreModelType accountDto) throws Exception;

	@Modifying
	@Query("update VehicleTyreModelType VT set VT.TYRE_MODEL = ?1, VT.TYRE_MODEL_DESCRITION= ?2,  VT.LASTMODIFIEDBYID =?3,  VT.LASTMODIFIED_DATE = ?4 where VT.TYRE_MT_ID = ?5 AND VT.COMPANY_ID = ?6")
	void update_VehicleTyreModelType(String TYRE_MODEL, String TYRE_MODEL_DESCRITION, Long updateById, Date updateDate, Integer TYRE_MT_ID, Integer companyId) throws Exception;

	@Query("FROM VehicleTyreModelType VT Where VT.TYRE_MODEL = ?1 AND VT.COMPANY_ID = ?2 AND VT.markForDelete = 0 AND VT.isOwnTyreModel = 1")
	VehicleTyreModelType get_VehicleTyreModelType(String verificationToken, Integer companyId);
	
	@Query("FROM VehicleTyreModelType VT Where VT.TYRE_MODEL = ?1 AND VT.markForDelete = 0 AND VT.isOwnTyreModel = 0")
	VehicleTyreModelType get_VehicleTyreModelType(String verificationToken);


	@Modifying
	@Query("UPDATE VehicleTyreModelType VT SET VT.markForDelete = 1 where VT.TYRE_MT_ID = ?1 AND VT.COMPANY_ID = ?2")
	void delete_VehicleTyreModelType(Integer TYRE_MT_ID, Integer companyId);

	@Query("FROM VehicleTyreModelType VT Where VT.TYRE_MT_ID = ?1 AND VT.COMPANY_ID = ?2 AND VT.markForDelete = 0 AND VT.isOwnTyreModel = 1")
	VehicleTyreModelType getVehicleTyreModelTypeByID(Integer TYRE_MT_ID, Integer companyId);
	
	@Query("FROM VehicleTyreModelType VT Where VT.TYRE_MT_ID = ?1  AND VT.markForDelete = 0 AND VT.isOwnTyreModel = 0")
	VehicleTyreModelType getVehicleTyreModelTypeByID(Integer TYRE_MT_ID);
	
	
	@Query("FROM VehicleTyreModelType VT where VT.markForDelete = 0 AND VT.COMPANY_ID = ?1 AND VT.isOwnTyreModel = 1")
 	public List<VehicleTyreModelType> findAll(Integer companyId);
	
	@Override
	@Query("FROM VehicleTyreModelType VT where VT.markForDelete = 0 AND VT.isOwnTyreModel = 0")
 	public List<VehicleTyreModelType> findAll();

}
