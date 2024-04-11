/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleTyreModelSubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface VehicleTyreModelSubTypeRepository extends JpaRepository<VehicleTyreModelSubType, Integer> {

	// VehicleTyreModelSubType
	// registerNew_VehicleTyreModelType(VehicleTyreModelSubType accountDto)
	// throws Exception;

	@Modifying
	@Query("update VehicleTyreModelSubType VT set VT.TYRE_MT_ID = ?1, VT.TYRE_MODEL_SUBTYPE=?2, VT.TYRE_MODEL_DESCRITION= ?3,  VT.LASTMODIFIEDBYID =?4,  VT.LASTMODIFIED_DATE = ?5 , VT.warrantyPeriod = ?6 ,VT.warrantyTypeId = ?7 , VT.warrentyterm = ?8 where VT.TYRE_MST_ID = ?9 and VT.COMPANY_ID = ?10 ")
	void update_VehicleTyreModelSubType(Integer TYRE_MODEL, String TYRE_MODEL_SUBTYPE,  String TYRE_MODEL_DESCRITION, Long updateById, Date updateDate, Integer warrantyPeriod, short	warrantyTypeId, String	warrentyterm,  Integer TYRE_MST_ID, Integer companyId) throws Exception;

	@Query("FROM VehicleTyreModelSubType VT Where VT.TYRE_MODEL_SUBTYPE = ?1 and VT.COMPANY_ID = ?2 and VT.markForDelete = 0 AND VT.isOwnTyreModel = 1")
	VehicleTyreModelSubType get_VehicleTyre_ModelSubType(String verificationToken, Integer companyId);
	
	@Query("FROM VehicleTyreModelSubType VT Where VT.TYRE_MODEL_SUBTYPE = ?1 and VT.markForDelete = 0 AND VT.isOwnTyreModel = 0")
	VehicleTyreModelSubType get_VehicleTyre_ModelSubType(String verificationToken);


	@Query("FROM VehicleTyreModelSubType VT Where VT.TYRE_MST_ID = ?1 and VT.COMPANY_ID = ?2 AND VT.isOwnTyreModel = 1 and VT.markForDelete = 0")
	VehicleTyreModelSubType get_VehicleTyre_ModelSubTypeById(Integer TYRE_MT_ID, Integer companyId);
	
	@Query("FROM VehicleTyreModelSubType VT Where VT.TYRE_MST_ID = ?1 AND VT.isOwnTyreModel = 0 and VT.markForDelete = 0")
	VehicleTyreModelSubType get_VehicleTyre_ModelSubTypeById(Integer TYRE_MT_ID);


	@Modifying
	@Query("update VehicleTyreModelSubType VT set VT.markForDelete = 1 where VT.TYRE_MST_ID = ?1 and VT.COMPANY_ID = ?2 ")
	void delete_VehicleTyreModel_SubType(Integer TYRE_MT_ID, Integer companyId);

	@Query("FROM VehicleTyreModelSubType VT Where VT.TYRE_MST_ID = ?1 and VT.COMPANY_ID = ?2 AND VT.markForDelete = 0")
	VehicleTyreModelSubType getVehicleTyreModel_SubTypeByID(Integer TYRE_MT_ID, Integer companyId);
	
	//@Query("update VehicleTyreModelSubType VT set VT.markForDelete = 1 where VT.companyId = ?1")
	@Query("FROM VehicleTyreModelSubType VT Where VT.COMPANY_ID = ?1 and markForDelete = 0")
	List<VehicleTyreModelSubType> findAllByCompanyId(Integer companyId);

	@Query(nativeQuery = true, value = "SELECT TYRE_MST_ID FROM VehicleTyreModelSubType WHERE TYRE_MT_ID=?1 AND COMPANY_ID = ?2 AND markForDelete = 0 ORDER BY TYRE_MST_ID DESC LIMIT 1 ")
	Long validateTyreManufacturer(Integer tyreManufacturerId, Integer companyId);

}
