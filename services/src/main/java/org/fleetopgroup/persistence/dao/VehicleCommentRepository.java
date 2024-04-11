/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface VehicleCommentRepository extends JpaRepository<VehicleComment, Long> {

	@Query("From VehicleComment as C Where C.VEHICLE_ID = ?1 AND COMPANY_ID = ?2 AND C.markForDelete = 0")
	public List<VehicleComment> Get_Vehicle_ID_Comment_Details(Integer vehicle_ID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE VehicleComment T SET T.markForDelete = 1 where T.VEHICLE_COMMENTID = ?1 AND COMPANY_ID = ?2")
	public void Delete_Vehicle_Comment_Details(Long vehicleComment_ID, Integer companyId) throws Exception;

}
