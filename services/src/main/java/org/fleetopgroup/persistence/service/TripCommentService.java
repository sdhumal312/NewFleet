package org.fleetopgroup.persistence.service;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.fleetopgroup.persistence.dao.TripSheetCommentRepository;
import org.fleetopgroup.persistence.model.TripSheetComment;
import org.fleetopgroup.persistence.serviceImpl.ITripCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("TripCommentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TripCommentService implements ITripCommentService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private TripSheetCommentRepository TripSheetCommentDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCommentService#
	 * add_TripSheet_Comment(org.fleetop.persistence.model.TripSheetComment)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void add_TripSheet_Comment(TripSheetComment sheetComment) throws Exception {

		TripSheetCommentDao.save(sheetComment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCommentService#
	 * list_TripSheetComment()
	 */
	@Transactional
	public List<TripSheetComment> list_TripSheetComment() throws Exception {

		return TripSheetCommentDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCommentService#
	 * get_TripSheetComment(java.lang.Long)
	 */
	@Transactional
	public TripSheetComment get_TripSheetComment(Long TRIPCID) throws Exception {

		return TripSheetCommentDao.getOne(TRIPCID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCommentService#
	 * deleteTripSheetComment(java.lang.Long)
	 */
	@Transactional
	public void deleteTripSheetComment(Long TRIPCID, Integer companyId) throws Exception {

		TripSheetCommentDao.deleteTripSheetComment(TRIPCID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCommentService#
	 * list_TripSheet_ID_TO_TripSheetComment(java.lang.Long)
	 */
	@Transactional
	public List<TripSheetComment> list_TripSheet_ID_TO_TripSheetComment(Long TripSheet, int companyId) throws Exception {
		
		return TripSheetCommentDao.list_TripSheet_ID_TO_TripSheetComment(TripSheet, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ITripCommentService#
	 * Delete_TripSheet_to_TripSheetComment(java.lang.Long)
	 */
	@Transactional
	public void Delete_TripSheet_to_TripSheetComment(Long tripSheetID, Integer companyId) {

		TripSheetCommentDao.Delete_TripSheet_to_TripSheetComment(tripSheetID, companyId);
	}
	
	@Override
	@Transactional
	public void removeTripSheetCommentById(Long TRIPCID) throws Exception {
		
		TripSheetCommentDao.removeTripSheetCommentById(TRIPCID);
	}

}