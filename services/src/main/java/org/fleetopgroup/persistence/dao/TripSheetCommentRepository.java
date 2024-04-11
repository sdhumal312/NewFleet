/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.TripSheetComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface TripSheetCommentRepository extends JpaRepository<TripSheetComment, Long> {

	@Query("From TripSheetComment as C Where C.TRIPCID = ?1 AND C.markForDelete = 0")
	public List<TripSheetComment> Get_TripSheet_ID_Comment_Details(Long issue_ID) throws Exception;

	@Modifying
	@Query("UPDATE TripSheetComment T SET markForDelete = 1 where T.TRIPCID = ?1 AND T.COMPANY_ID = ?2")
	public void deleteTripSheetComment(Long TRIPCID, Integer companyId) throws Exception;

	@Query("From TripSheetComment as C Where C.TRIPSHEETID = ?1 AND C.markForDelete = 0 AND C.COMPANY_ID = ?2 ORDER BY C.CREATED_DATE DESC ")
	public List<TripSheetComment> list_TripSheet_ID_TO_TripSheetComment(Long TripSheet, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE TripSheetComment T SET T.markForDelete = 1 where T.TRIPSHEETID = ?1 AND T.COMPANY_ID = ?2")
	public void Delete_TripSheet_to_TripSheetComment(Long tripSheetID, Integer companyId);
	
	@Modifying
	@Query("UPDATE TripSheetComment T SET T.markForDelete = 1 where T.TRIPCID = ?1")
	public void removeTripSheetCommentById(Long tripSheetID);
}
