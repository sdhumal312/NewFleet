package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.TripCollectionIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripCollectionIncomeRepository extends JpaRepository<TripCollectionIncome, Long> {

	@Query("from TripCollectionIncome where TRIPCOLLID= ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<TripCollectionIncome> findAll_TripCollection_Income(Long TRIPCOLLID, Integer companyId) throws Exception;

	@Query("FROM TripCollectionIncome WHERE incomeId=?1 AND TRIPCOLLID = ?2 AND companyId = ?3 AND markForDelete = 0")
	public List<TripCollectionIncome> Validate_TripCollection_Income(Integer IncomeName, Long TRIPCOLLID, Integer companyId)
			throws Exception;

	@Query("FROM TripCollectionIncome AS T WHERE T.tripincomeID=?1 AND T.companyId = ?2 AND T.markForDelete = 0")
	public TripCollectionIncome Get_TripCollection_Income(Long TripSheet_Incomeid, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE TripCollectionIncome AS T SET T.markForDelete = 1 WHERE T.tripincomeID=?1 AND T.companyId = ?2")
	public void delete_TripCollectionIncome(Long TripIncomeid, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE TripCollectionIncome SET markForDelete = 1 WHERE TRIPCOLLID=?1 AND companyId = ?2")
	public void delete_TripCollectionIncome_collectionId(Long tripCollectionID, Integer companyId) throws Exception;

}
