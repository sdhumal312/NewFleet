package org.fleetopgroup.persistence.serviceImpl;

/**
 * @author fleetop
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.TripSheetComment;


public interface ITripCommentService {

	public void add_TripSheet_Comment(TripSheetComment sheetComment) throws Exception;

	public List<TripSheetComment> list_TripSheetComment() throws Exception;

	public TripSheetComment get_TripSheetComment(Long TRIPCID) throws Exception;

	public void deleteTripSheetComment(Long TRIPCID, Integer companyId) throws Exception;

	public List<TripSheetComment> list_TripSheet_ID_TO_TripSheetComment(Long TripSheet, int companyId) throws Exception;

	/**
	 * @param tripSheetID
	 */
	public void Delete_TripSheet_to_TripSheetComment(Long tripSheetID, Integer companyId);
	
	public void removeTripSheetCommentById(Long TRIPCID) throws Exception;

}