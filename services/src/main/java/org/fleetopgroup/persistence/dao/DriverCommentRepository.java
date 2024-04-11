package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.DriverComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;



public interface DriverCommentRepository extends JpaRepository<DriverComment, Long> {
	

	//public void addDriverComment(DriverComment diverComment);

	//public void updateDriverComment(DriverComment diverComment);

	@Query("From DriverComment where driver_id= ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<DriverComment> listDriverComment(int diverComment, Integer companyId);

	@Query("From DriverComment where driver_commentid= ?1 AND markForDelete = 0")
	public DriverComment getDriverComment(Long driver_commentid);

	@Modifying
	@Query("UPDATE DriverComment SET markForDelete = 1 WHERE driver_commentid = ?1 AND companyId = ?2")
	public void deleteDriverComment(Long driver_commentid, Integer companyId);

}
